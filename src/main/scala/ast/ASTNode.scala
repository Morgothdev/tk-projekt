package ast

sealed abstract class ASTNode {
  def toRegex: String = this.getClass.getSimpleName

  def toPrettyString(sb: StringBuilder)(implicit prefix: String = "", tab: String = "|    ", lineSep: String = util.Properties.lineSeparator): StringBuilder = {
    sb ++= prefix ++= this.toString ++= lineSep
  }
}

// Agregaty

case class Regex(items: List[ASTNode]) extends ASTNode {
  //override def toString: String = if (items.nonEmpty) items.map(_.toString).reduce(_ + _) else ""
  override def toString: String = items.mkString("REGEX {", ",", "}")

  override def toPrettyString(sb: StringBuilder)(implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "REGEX{" ++= lineSep
    items.foreach {
      _.toPrettyString(sb)(prefix + tab)
    }
    sb ++= prefix ++= "}" ++= lineSep
  }

  override def toRegex: String =
    if (items.nonEmpty)
      items.map(_.toRegex).reduce(_ + "|" + _)
    else ""
}

case class SimpleRegex(items: List[ASTNode]) extends ASTNode {
  override def toString: String = items.mkString("SIMPLE {", ",", "}")

  override def toRegex: String =
    if (items.nonEmpty) items.map(_.toRegex).reduce(_ + _) else ""

  override def toPrettyString(sb: StringBuilder)
                             (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "SIMPLE{" ++= lineSep
    items.foreach {
      _.toPrettyString(sb)(prefix + tab)
    }
    sb ++= prefix ++= "}" ++= lineSep
  }
}

case class BasicRegex(elem: ASTNode) extends ASTNode {
  override def toRegex: String = elem.toRegex

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "Basic{" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

//modyfikatory

case class ZeroOrMore(elem: ASTNode) extends ASTNode {
  override def toRegex: String = elem.toRegex + "*"

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "ZeroOrMore{" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

case class OneOrMore(elem: ASTNode) extends ASTNode {
  override def toRegex: String = elem.toRegex + "+"

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "OneOrMore{" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

case class ZeroOrOne(elem: ASTNode) extends ASTNode {
  override def toRegex: String = elem.toRegex + "?"

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "ZeroOrOne{" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

case class FixedRepeting(elem: ASTNode, min: Int, max: Int = FixedRepeting.INFINITY) extends ASTNode {

  import ast.FixedRepeting._

  override def toRegex: String = elem.toRegex + "{" + min.toString + (
    if (max == INFINITY)
      ","
    else if (max < INFINITY && max > min)
      s",${max.toString}"
    ) + "}"

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= s"FixedRepeting{ min=$min, max=${if (max == INFINITY) "inf" else max}" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

object FixedRepeting {
  val INFINITY = Int.MaxValue
}

//

case class ElementaryRegex(elem: ASTNode) extends ASTNode {
  override def toRegex: String = elem.toRegex
}

case class Group(elem: ASTNode) extends ASTNode {
  override def toRegex: String = '(' + elem.toRegex + ')'

  override def toString: String = "GROUP {" + elem.toString + "}"

  override def toPrettyString
  (sb: StringBuilder)
  (implicit prefix: String, tab: String, lineSep: String): StringBuilder = {
    sb ++= prefix ++= "Group{" ++= lineSep
    elem.toPrettyString(sb)(prefix + tab)
    sb ++= prefix ++= "}" ++= lineSep
  }
}

case class Any() extends ASTNode

case class End() extends ASTNode

case class Set(set: ASTNode) extends ASTNode

case class PositiveSet(set: ASTNode) extends ASTNode

case class NegativeSet(set: ASTNode) extends ASTNode

case class SetItems(items: List[ASTNode]) extends ASTNode

case class SetItem(item: ASTNode) extends ASTNode

case class Character(char: Char) extends ASTNode

case class Range(beg: NonMeta, end: NonMeta) extends ASTNode

case class Meta(character: Char) extends ASTNode {
  override def toRegex: String = "\"" + character.toString
}


case class NonMeta(character: Char) extends ASTNode {
  override def toRegex: String = character.toString
}

case class Number(value: Int) extends ASTNode {

}

//itd
