package ast

object AlternativeReducer {
  implicit def wrapToAlternativeReducer(list: List[ASTNode]) = new AlternativeReducer(list)
}

class AlternativeReducer(list: List[ASTNode]) {

  def reduceAlternatives = reduceAlternativesList(list, new GroupExtractor with AgregatesReducer with FixedParametersReducer)

  def reduceAlternativesList(list: List[ASTNode], f: ReducerFunction[ASTNode]) = {
    def reduceReplies(list: List[ASTNode]) = list.distinct
    def reducePrv(list: List[ASTNode]): List[ASTNode] = {
      list match {
        case Nil => Nil
        case element :: Nil => List(element)
        case element :: rest =>
          f(element, rest.head) match {
            case Some(x) => reducePrv(x ::: rest.tail)
            case None =>
              f(rest.head, element) match {
                case Some(x) => reducePrv(x.reverse ::: rest.tail)
                case None => rest.head :: reducePrv(element :: rest.tail)
              }
          }
      }
    }
    val result: List[ASTNode] = reduceReplies(list) match {
      case Nil => Nil
      case head :: tail => head :: reducePrv(tail)
    }
    reducePrv(result)

  }

  type ReducerFunction[A] = (A, A) => Option[List[A]]

  trait BaseReducer extends ReducerFunction[ASTNode] {
    override def apply(v1: ASTNode, v2: ASTNode): Option[List[ASTNode]] = None
  }

  trait GroupExtractor extends BaseReducer {
    override def apply(v1: ASTNode, v2: ASTNode): Option[List[ASTNode]] =
      (v1, v2) match {
        case (Regex(alternatives), element) => Some(element :: alternatives)
        case _ => super.apply(v1, v2)
      }
  }

  trait AgregatesReducer extends BaseReducer {

    object Element {
      def unapply(elem: ASTNode) =
        elem match {
          case plus@OneOrMore(pIn) => Some((plus, pIn))
          case star@ZeroOrMore(sIn) => Some((star, sIn))
          case ask@ZeroOrOne(askIn) => Some((ask, askIn))
          case _ => None
        }
    }

    override def apply(e1: ASTNode, e2: ASTNode): Option[List[ASTNode]] =
      (e1, e2) match {
        case (Element(externalElem, internalElem), elem) if elem.equals(internalElem) =>
          println(s"executed ${e1.toRegex} | ${elem.toRegex} => ${internalElem.toRegex}")
          Some(List(externalElem))
        case (plus@OneOrMore(pIn), ask@ZeroOrOne(askIn)) if pIn.equals(askIn) =>
          println(s"executed ${plus.toRegex} | ${ask.toRegex} => ${plus.toRegex}")
          Some(List(plus))
        case (plus@OneOrMore(pIn), star@ZeroOrMore(sIn)) if pIn.equals(sIn) =>
          println(s"executed ${plus.toRegex} | ${star.toRegex} => ${star.toRegex}")
          Some(List(star))
        case (star@ZeroOrMore(sIn), ask@ZeroOrOne(askIn)) if sIn.equals(askIn) =>
          println(s"executed ${star.toRegex} | ${ask.toRegex} => ${star.toRegex}")
          Some(List(star))
        case _ => super.apply(e1, e2)
      }
  }

  trait FixedParametersReducer extends BaseReducer {

    override def apply(v1: ASTNode, v2: ASTNode): Option[List[ASTNode]] =
      (v1, v2) match {
        case (f1@FixedRepeting(e1, min1, max1), f2@FixedRepeting(e2, min2, max2))
          if e1.equals(e2) && (min1 <= min2 && max1 >= max2) =>
          println(s"executed ${f1.toRegex} | ${f2.toRegex} => ${f1.toRegex}")
          Some(List(f1))
        case (f1@ZeroOrMore(elem), f2@FixedRepeting(elem2, _, _))
          if elem.equals(elem2) =>
          println(s"executed ${f1.toRegex} | ${f2.toRegex} => ${f1.toRegex}")
          Some(List(f1))
        case (f1@OneOrMore(elem), f2@FixedRepeting(elem2, min, _))
          if elem.equals(elem2) && min >= 1 =>
          println(s"executed ${f1.toRegex} | ${f2.toRegex} => ${f1.toRegex}")
          Some(List(f1))
        case (f1@ZeroOrOne(elem), f2@FixedRepeting(elem2, _, max))
          if elem.equals(elem2) && max <= 1 =>
          println(s"executed ${f1.toRegex} | ${f2.toRegex} => ${f1.toRegex}")
          Some(List(f1))
        case _ => super.apply(v1, v2)
      }

  }

}
