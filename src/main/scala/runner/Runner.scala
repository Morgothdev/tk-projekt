package runner

import ast._
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import parser.{RegularsLexer, RegularsParser}


object Runner {

  def extend(elem: ASTNode) = elem match {
    case prev@ZeroOrMore(Regex(elems)) if elems.size > 1 =>
      val more = Regex(elems.map(ZeroOrMore))
      println(s"extending ${prev.toRegex} => ${more.toRegex}")
      more
    case prev@OneOrMore(Regex(elems)) if elems.size > 1 =>
      val more = Regex(elems.map(OneOrMore))
      println(s"extending ${prev.toRegex} => ${more.toRegex}")
      more
    case prev@ZeroOrOne(Regex(elems)) if elems.size > 1 =>
      val more = Regex(elems.map(ZeroOrOne))
      println(s"extending ${prev.toRegex} => ${more.toRegex}")
      more
    case _ => elem
  }


  def simplifier(node1: ASTNode): ASTNode = {
    def reduceList(elems: List[ASTNode]) = {
      import ast.Reducer._
      elems.map(simplifier).reduceConiunction
    }
    println(s"simplifier $node1 as ${node1.toRegex}")
    val node = extend(node1)
    val sb = node.toPrettyString(StringBuilder.newBuilder)
    println(sb.toString())
    node match {
      //agregaty
      case Regex(m) =>
        import ast.AlternativeReducer._
        // regex jest zbiorem alternatyw, więc redukujemy po alternatywach
        val alternatives: List[ASTNode] = m.map(simplifier).reduceAlternatives
        alternatives.size match {
          case 1 => alternatives.head
          case _ => Regex(alternatives)
        }
      case SimpleRegex(m) =>
        val reduced: List[ASTNode] = reduceList(m)
        reduced.size match {
          case 1 => reduced.head
          case _ => SimpleRegex(reduced)
        }
      case SetItems(items) => SetItems(reduceList(items))
      //dekoratory
      case Set(elem) => Set(simplifier(elem))
      case PositiveSet(items) => PositiveSet(simplifier(items))
      case NegativeSet(items) => NegativeSet(simplifier(items))
      case ZeroOrMore(elem) => ZeroOrMore(simplifier(elem))
      case ZeroOrOne(elem) => ZeroOrOne(simplifier(elem))
      case OneOrMore(elem) => OneOrMore(simplifier(elem))
      case a@FixedRepeting(elem, _, _) => a.copy(elem = simplifier(elem))
      case l => l
    }
  }

  def simple(regex: String) = {
    val tokens = new CommonTokenStream(new RegularsLexer(new ANTLRInputStream(regex)))
    val parser = new RegularsParser(tokens)
    val visitor = new OurTreeVisitorTrait
      //with LoggableTreeVisitor
      with OurAbstractTreeVisitorTrait
    val tree: ASTNode = parser.start().accept(visitor)
    println(tree)
    println(tree.toRegex)
    val result: ASTNode = simplifier(tree)
    val extended = Extender.extend(result)
    println(extended.toRegex)
    val reducedExtended = simplifier(extended)
    reducedExtended
  }

  def main(args: Array[String]) {
    args.toList match {
      case elem :: tail =>
        //        val a = elem.replace( '""", """\""")
        val a = elem
        val result = simple(a)
        println(result + " dupa")
        println(result.toRegex + " dupa2")
      case _ => println("nie podano argumentów")
    }
  }
}

/*

 */