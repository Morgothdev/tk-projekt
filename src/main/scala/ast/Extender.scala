package ast

object Extender {

  def extend(node: ASTNode): ASTNode = node match {
    // TODO sprawdzić wszystkie opcje, może jeszcze gdzieś przyda się jakiś case
    case Regex(elems) => Regex(elems.map(extend))
    case OneOrMore(SimpleRegex(elems)) => SimpleRegex(elems.map(OneOrMore))
    case ZeroOrMore(SimpleRegex(elems)) => SimpleRegex(elems.map(ZeroOrMore))
    case ZeroOrOne(SimpleRegex(elems)) => SimpleRegex(elems.map(ZeroOrOne))
    case FixedRepeting(SimpleRegex(elems), min, max) =>
      val PartialFixedRepeating: (ASTNode) => FixedRepeting = FixedRepeting(_: ASTNode, min, max)
      SimpleRegex(elems.map(PartialFixedRepeating))
    case elem => elem
  }
}


