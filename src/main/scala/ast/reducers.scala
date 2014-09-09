package ast

object Reducer {
  implicit def wrapToReducer(list: List[ASTNode]) = new Reducer(list)
}

class Reducer(list: List[ASTNode]) {

  def reduceConiunction = reducePrv(list)

  private def reducePrv(list: List[ASTNode]): List[ASTNode] = {
    println(s"reducing $list")
    import ast.FixedRepeting.INFINITY
    list match {
      // ========================================================================================
      // * multiple nesed agregators
      case (e@ZeroOrMore(i@ZeroOrMore(elem))) :: tail =>
        println(s"executed ${e.toRegex} => ${i.toRegex}")
        reducePrv(i :: tail)
      case (e@ZeroOrMore(OneOrMore(elem))) :: tail =>
        val more = ZeroOrMore(elem)
        println(s"executed ${e.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)
      case (e@ZeroOrMore(ZeroOrOne(elem))) :: tail =>
        val more = ZeroOrMore(elem)
        println(s"executed ${e.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      case (e@OneOrMore(i@OneOrMore(elem))) :: tail =>
        println(s"executed ${e.toRegex} => ${i.toRegex}")
        reducePrv(i :: tail)
      case (e@OneOrMore(i@ZeroOrMore(elem))) :: tail =>
        println(s"executed ${e.toRegex} => ${i.toRegex}")
        reducePrv(i :: tail)
      case (e@OneOrMore(ZeroOrOne(elem))) :: tail =>
        val more = ZeroOrMore(elem)
        println(s"executed ${e.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      case (e@ZeroOrOne(ZeroOrMore(elem))) :: tail =>
        val more = ZeroOrMore(elem)
        println(s"executed ${e.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)
      case (e@ZeroOrOne(OneOrMore(elem))) :: tail =>
        val more = ZeroOrMore(elem)
        println(s"executed ${e.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)
      case (e@ZeroOrOne(i@ZeroOrOne(elem))) :: tail =>
        println(s"executed ${e.toRegex} => ${i.toRegex}")
        reducePrv(i :: tail)

      // ========================================================================================
      // *
      case (elem1: ASTNode) :: (elem2@SimpleRegex(items)) :: tail => reducePrv(elem1 :: items ::: tail)
      case (elem2@SimpleRegex(items)) :: (elem1: ASTNode) :: tail => reducePrv(elem1 :: items ::: tail)

      // ========================================================================================
      // * reducers with *
      case (f@ZeroOrMore(firstElem)) :: (secondElem: ASTNode) :: tail if firstElem.equals(secondElem) =>
        val more = OneOrMore(secondElem)
        println(s"executed ${f.toRegex} & ${secondElem.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)
      case (f@ZeroOrMore(firstElem)) :: (s@ZeroOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${f.toRegex}")
        reducePrv(f :: tail)
      case (f@ZeroOrMore(firstElem)) :: (s@OneOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${f.toRegex}")
        reducePrv(s :: tail)
      case (f@ZeroOrMore(firstElem)) :: (s@ZeroOrOne(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${f.toRegex}")
        reducePrv(f :: tail)
      case (f@ZeroOrMore(firstElem)) :: (s@FixedRepeting(secondElem, min, _)) :: tail if firstElem.equals(secondElem) =>
        val n = min match {
          case 0 => f
          case 1 => OneOrMore(firstElem)
          case _ => FixedRepeting(firstElem, min, FixedRepeting.INFINITY)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (firstElem: ASTNode) :: (s@ZeroOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val more = OneOrMore(firstElem)
        println(s"executed ${firstElem.toRegex} & ${s.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      // ========================================================================================
      // reducers with +
      case (f@OneOrMore(firstElem)) :: (secondElem: ASTNode) :: tail if firstElem.equals(secondElem) =>
        val n = FixedRepeting(firstElem, 2)
        println(s"executed ${f.toRegex} & ${secondElem.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@OneOrMore(firstElem)) :: (s@ZeroOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${f.toRegex}")
        reducePrv(f :: tail)
      case (f@OneOrMore(firstElem)) :: (s@OneOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val n = FixedRepeting(firstElem, 2)
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@OneOrMore(firstElem)) :: (s@ZeroOrOne(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${f.toRegex}")
        reducePrv(f :: tail)
      case (f@OneOrMore(firstElem)) :: (s@FixedRepeting(secondElem, min, _)) :: tail if firstElem.equals(secondElem) =>
        val n = min match {
          case 0 => f
          case _ => FixedRepeting(firstElem, min + 1, FixedRepeting.INFINITY)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (firstElem: ASTNode) :: (s@OneOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val more = FixedRepeting(firstElem, 2)
        println(s"executed ${firstElem.toRegex} & ${s.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      // ========================================================================================
      // reducers with ?
      case (f@ZeroOrOne(firstElem)) :: (s@ZeroOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${s.toRegex}")
        reducePrv(s :: tail)
      case (f@ZeroOrOne(firstElem)) :: (s@OneOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${s.toRegex}")
        reducePrv(s :: tail)
      case (f@ZeroOrOne(firstElem)) :: (s@ZeroOrOne(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val n = FixedRepeting(firstElem, 0, 2)
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@ZeroOrOne(firstElem)) :: (s@FixedRepeting(secondElem, min, max)) :: tail if firstElem.equals(secondElem) =>
        val n = (min, max) match {
          case (0, 0) => f
          case (1, INFINITY) => OneOrMore(firstElem)
          case (0, INFINITY) => ZeroOrMore(firstElem)
          case (_, INFINITY) => s
          case (_, _) => FixedRepeting(firstElem, min, max + 1)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@FixedRepeting(firstElem, min1, max1)) :: (s@FixedRepeting(secondElem, min2, max2)) :: tail if firstElem.equals(secondElem) =>
        val n = (max1, max2) match {
          case (_, INFINITY) | (INFINITY, _) =>
            min1 + min2 match {
              case 0 => ZeroOrMore(firstElem)
              case 1 => OneOrMore(firstElem)
              case number => FixedRepeting(firstElem, number)
            }
          case (_, _) => FixedRepeting(firstElem, min1 + min2, max1 + max2)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (firstElem: ASTNode) :: (s@ZeroOrOne(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val more = FixedRepeting(firstElem, 2)
        println(s"executed ${firstElem.toRegex} & ${s.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      // ========================================================================================
      // reducers with {}
      case (f@FixedRepeting(firstElem, min, _)) :: (s@ZeroOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val n = min match {
          case 1 => OneOrMore(firstElem)
          case number if number > 1 => FixedRepeting(firstElem, min, FixedRepeting.INFINITY)
          case _ => s
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@FixedRepeting(firstElem, min, _)) :: (s@OneOrMore(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val n = min match {
          case 0 => s
          case number if number > 0 => FixedRepeting(firstElem, min + 1, FixedRepeting.INFINITY)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (f@FixedRepeting(firstElem, min, max)) :: (s@ZeroOrOne(secondElem)) :: tail if firstElem.equals(secondElem) =>
        val n = (min, max) match {
          case (0, 0) => s
          case (1, 1) => FixedRepeting(firstElem, 1, 2)
          case (0, INFINITY) => ZeroOrMore(firstElem)
          case (1, INFINITY) => OneOrMore(firstElem)
          case (_, INFINITY) => f
          case (_, _) => FixedRepeting(firstElem, min, max + 1)
        }
        println(s"executed ${f.toRegex} & ${s.toRegex} => ${n.toRegex}")
        reducePrv(n :: tail)
      case (firstElem: ASTNode) :: (s@FixedRepeting(secondElem, min, _)) :: tail if firstElem.equals(secondElem) =>
        val more = FixedRepeting(firstElem, 2)
        println(s"executed ${firstElem.toRegex} & ${s.toRegex} => ${more.toRegex}")
        reducePrv(more :: tail)

      // ========================================================================================
      // defaults
      case head :: tail => head :: reducePrv(tail)
      case Nil => Nil
    }
  }
}

