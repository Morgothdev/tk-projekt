package ast

import parser.RegularsParser._

import scala.collection.JavaConverters._

trait OurTreeVisitorTrait extends OurAbstractTreeVisitorTrait with LoggableTreeVisitor {
  override def visitStart(ctx: StartContext): Regex =
    visitRegex(ctx.regex())

  override def visitRegex(ctx: RegexContext): Regex = {
    val list = ctx.simple_regex().asScala.toList.map(_.accept(this))
    //    println(s"visitRegex with elements $list size ${list.size}")
    Regex(list)
  }

  override def visitSimple_regex(ctx: Simple_regexContext): SimpleRegex = {
    val basicRegex = ctx.basic_regex()
    val result = basicRegex.asScala.toList.map(_.accept(this))
    //    println(s"visitSimpleRegex $result, size=${result.size}")
    SimpleRegex(result)
  }

  override def visitBasic_regex(ctx: Basic_regexContext): ASTNode = {
    val notNulls = List(ctx.star(), ctx.plus(), ctx.elementary_regex(), ctx.one_or_none(), ctx.fixed_repeat_regex()).filter(_ != null)
    assert(notNulls.size == 1, s"WTF, not one not null ? -> $notNulls")
    notNulls.head.accept(this)
  }

  override def visitPlus(ctx: PlusContext): OneOrMore = {
    val elementary_regex: ASTNode = visitElementary_regex(ctx.elementary_regex())
    OneOrMore(elementary_regex)
  }

  override def visitStar(ctx: StarContext): ZeroOrMore = {
    val elementary_regex = ZeroOrMore(visitElementary_regex(ctx.elementary_regex()))
    //    println(s"visitStar $elementary_regex")
    elementary_regex
  }

  override def visitOne_or_none(ctx: One_or_noneContext): ZeroOrOne =
    ZeroOrOne(visitElementary_regex(ctx.elementary_regex()))

  override def visitElementary_regex(ctx: Elementary_regexContext): ASTNode = {
    val notNulls = List(ctx.any(), ctx.group(), ctx.eos(), ctx.character(), ctx.set()).filter(_ != null)
    assert(notNulls.size == 1, s"WTF, not one not null ? -> $notNulls")
    notNulls.head.accept(this)
  }

  override def visitGroup(ctx: GroupContext): ASTNode =
  //      Group(visitRegex(ctx.regex()))
    visitRegex(ctx.regex())

  override def visitAny(ctx: AnyContext): ASTNode = Any()

  override def visitEos(ctx: EosContext): ASTNode = End()

  override def visitSet(ctx: SetContext): ASTNode =
    if (ctx.negative_set() != null && ctx.positive_set() == null) {
      Set(visitNegative_set(ctx.negative_set()))
    } else if (ctx.positive_set() != null && ctx.negative_set() == null) {
      Set(visitPositive_set(ctx.positive_set()))
    } else {
      throw new WTFException
    }

  override def visitPositive_set(ctx: Positive_setContext): PositiveSet =
    PositiveSet(visitSet_items(ctx.set_items()))

  override def visitNegative_set(ctx: Negative_setContext): NegativeSet =
    NegativeSet(visitSet_items(ctx.set_items()))

  override def visitSet_items(ctx: Set_itemsContext): SetItems =
    SetItems(ctx.set_item.asScala.toList.map(visitSet_item))

  override def visitSet_item(ctx: Set_itemContext): SetItem =
    if (ctx.range() != null && ctx.character() == null) {
      SetItem(visitRange(ctx.range()))
    } else if (ctx.character() != null && ctx.range() == null) {
      SetItem(visitCharacter(ctx.character()))
    } else {
      throw new WTFException
    }

  override def visitCharacter(ctx: CharacterContext): ASTNode = {
    val meta =
      if (ctx.metacharacter() != null && ctx.nonmetacharacter() == null) {
        ctx.metacharacter().accept(this)
      } else if (ctx.metacharacter() == null && ctx.nonmetacharacter() != null) {
        ctx.nonmetacharacter().accept(this)
      } else {
        throw new WTFException
      }
    //    println(s"visitCharacter $meta")
    meta
  }

  override def visitRange(ctx: RangeContext): Range =
    Range(visitNonmetacharacter(ctx.nonmetacharacter(0)), visitNonmetacharacter(ctx.nonmetacharacter(1)))

  override def visitMetacharacter(ctx: MetacharacterContext): Meta = {
    //    println(s"visiting metcharacter ${ctx.METACHARACTER().toString}")
    assert(ctx.METACHARACTER().getSymbol.getText.length == 1, "not one symbol!")
    Meta(ctx.METACHARACTER().getSymbol.getText.charAt(0))
  }

  override def visitNonmetacharacter(ctx: NonmetacharacterContext): NonMeta = {
    assert(ctx.ANONMETACHARACTER().getSymbol.getText.length == 1, "not one symbol!")
    val nonMeta: Char = ctx.ANONMETACHARACTER().getSymbol.getText.charAt(0)
    //    println(s"visitNonmetacharacter $nonMeta")
    NonMeta(nonMeta)
  }

  override def visitFixed_min_max(ctx: Fixed_min_maxContext): ASTNode = {
    val min = ctx.number(0).accept(this).asInstanceOf[Number]
    val max = ctx.number(1).accept(this).asInstanceOf[Number]
    val elem = ctx.elementary_regex().accept(this)
    FixedRepeting(elem, min.value, max.value)
  }

  override def visitFixed_min_inf(ctx: Fixed_min_infContext): ASTNode = {
    val min = ctx.number.accept(this).asInstanceOf[Number]
    val elem = ctx.elementary_regex().accept(this)
    FixedRepeting(elem, min.value)
  }

  override def visitFixed_exact(ctx: Fixed_exactContext): ASTNode = {
    val number = ctx.number.accept(this).asInstanceOf[Number]
    val elem = ctx.elementary_regex().accept(this)
    FixedRepeting(elem, number.value, number.value)
  }

  override def visitFixed_repeat_regex(ctx: Fixed_repeat_regexContext): ASTNode = {
    val notNulls = List(ctx.fixed_exact(), ctx.fixed_min_inf(), ctx.fixed_min_max()).filter(_ != null)
    assert(notNulls.size == 1, s"WTF, not one not null ? -> $notNulls")
    notNulls.head.accept(this)
  }


  override def visitNumber(ctx: NumberContext): ASTNode = {
    val number = ctx.NUMBER().asScala.map(_.toString).reduce(_ + _)
    val parsedInt = number.toInt
    Number(parsedInt)
  }
}