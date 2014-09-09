package ast

object Concater {

  def concat(node: ASTNode): ASTNode = node match {
    case Regex(elems) => Regex(concatRegex(elems))
    //    case SimpleRegex(elems) => SimpleRegex(concatSimpleRegex(elems))
  }

  //    def concatSimpleRegex(nodes: List[ASTNode]): List[ASTNode] = nodes match {
  //      case Nil => Nil
  //      case ZeroOrMore(left) :: ZeroOrMore(right) :: tail => concatSimpleRegex(ZeroOrMore(SimpleRegex(List(left, right))) :: tail)
  //      case OneOrMore(left) :: OneOrMore(right) :: tail => concatSimpleRegex(OneOrMore(SimpleRegex(List(left, right))) :: tail)
  //      case ZeroOrOne(left) :: ZeroOrOne(right) :: tail => concatSimpleRegex(ZeroOrOne(SimpleRegex(List(left, right))) :: tail)
  //      case FixedRepeting(elem1, min1, max1) :: FixedRepeting(elem2, min2, max2) :: tail if min1 == min2 && max1 == max2 => concatSimpleRegex(FixedRepeting(SimpleRegex(elem1 :: elem2 :: Nil), min1, max1) :: tail)
  //      case FixedRepeting(SimpleRegex(elems), min1, max1) :: FixedRepeting(elem2, min2, max2) :: tail if min1 == min2 && max1 == max2 => concatSimpleRegex(FixedRepeting(SimpleRegex(elems ++ List(elem2)), min1, max1) :: tail)
  //      case ZeroOrMore(SimpleRegex(elems)) :: ZeroOrMore(right) :: tail => concatSimpleRegex(ZeroOrMore(SimpleRegex(elems ++ List(right))) :: tail)
  //      case OneOrMore(SimpleRegex(elems)) :: OneOrMore(right) :: tail => concatSimpleRegex(OneOrMore(SimpleRegex(elems ++ List(right))) :: tail)
  //      case ZeroOrOne(SimpleRegex(elems)) :: ZeroOrOne(right) :: tail => concatSimpleRegex(ZeroOrOne(SimpleRegex(elems ++ List(right))) :: tail)
  //      case uncasedHead :: tail => uncasedHead :: concatSimpleRegex(tail)
  //    }

  def concatRegex(nodes: List[ASTNode]): List[ASTNode] = nodes match {
    case Nil => Nil
    case ZeroOrMore(left) :: ZeroOrMore(right) :: tail => concatRegex(ZeroOrMore(Regex(List(left, right))) :: tail)
    case OneOrMore(left) :: OneOrMore(right) :: tail => concatRegex(OneOrMore(Regex(List(left, right))) :: tail)
    case ZeroOrOne(left) :: ZeroOrOne(right) :: tail => concatRegex(ZeroOrOne(Regex(List(left, right))) :: tail)
    case FixedRepeting(elem1, min1, max1) :: FixedRepeting(elem2, min2, max2) :: tail if min1 == min2 && max1 == max2 => concatRegex(FixedRepeting(Regex(elem1 :: elem2 :: Nil), min1, max1) :: tail)
    case FixedRepeting(Regex(elems), min1, max1) :: FixedRepeting(elem2, min2, max2) :: tail if min1 == min2 && max1 == max2 => concatRegex(FixedRepeting(Regex(elems ++ List(elem2)), min1, max1) :: tail)
    case ZeroOrMore(Regex(elems)) :: ZeroOrMore(right) :: tail => concatRegex(ZeroOrMore(Regex(elems ++ List(right))) :: tail)
    case OneOrMore(Regex(elems)) :: OneOrMore(right) :: tail => concatRegex(OneOrMore(Regex(elems ++ List(right))) :: tail)
    case ZeroOrOne(Regex(elems)) :: ZeroOrOne(right) :: tail => concatRegex(ZeroOrOne(Regex(elems ++ List(right))) :: tail)
    case uncasedHead :: tail => uncasedHead :: concatRegex(tail)
  }
}
