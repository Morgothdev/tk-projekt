package ast

import parser.RegularsParser._
import parser.RegularsBaseVisitor

trait OurAbstractTreeVisitorTrait extends RegularsBaseVisitor[ASTNode] {
  override def visitStart(ctx: StartContext): ASTNode = super.visitStart(ctx)

  override def visitRegex(ctx: RegexContext): ASTNode = super.visitRegex(ctx)

  override def visitGroup(ctx: GroupContext): ASTNode = super.visitGroup(ctx)

  override def visitPositive_set(ctx: Positive_setContext): ASTNode = super.visitPositive_set(ctx)

  override def visitAny(ctx: AnyContext): ASTNode = super.visitAny(ctx)

  override def visitBasic_regex(ctx: Basic_regexContext): ASTNode = super.visitBasic_regex(ctx)

  override def visitEos(ctx: EosContext): ASTNode = super.visitEos(ctx)

  override def visitElementary_regex(ctx: Elementary_regexContext): ASTNode = super.visitElementary_regex(ctx)

  override def visitSet_item(ctx: Set_itemContext): ASTNode = super.visitSet_item(ctx)

  override def visitCharacter(ctx: CharacterContext): ASTNode = super.visitCharacter(ctx)

  override def visitNegative_set(ctx: Negative_setContext): ASTNode = super.visitNegative_set(ctx)

  override def visitSet_items(ctx: Set_itemsContext): ASTNode = super.visitSet_items(ctx)

  override def visitStar(ctx: StarContext): ASTNode = super.visitStar(ctx)

  override def visitSet(ctx: SetContext): ASTNode = super.visitSet(ctx)


  override def visitFixed_repeat_regex(ctx: Fixed_repeat_regexContext): ASTNode = super.visitFixed_repeat_regex(ctx)

  override def visitNumber(ctx: NumberContext): ASTNode = super.visitNumber(ctx)

  override def visitOne_or_none(ctx: One_or_noneContext): ASTNode = super.visitOne_or_none(ctx)

  override def visitSimple_regex(ctx: Simple_regexContext): ASTNode = super.visitSimple_regex(ctx)

  override def visitRange(ctx: RangeContext): ASTNode = super.visitRange(ctx)

  override def visitPlus(ctx: PlusContext): ASTNode = super.visitPlus(ctx)

  override def visitMetacharacter(ctx: MetacharacterContext): ASTNode = super.visitMetacharacter(ctx)

  override def visitNonmetacharacter(ctx: NonmetacharacterContext): ASTNode = super.visitNonmetacharacter(ctx)
}
