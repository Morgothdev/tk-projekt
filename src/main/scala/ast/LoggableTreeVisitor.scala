package ast

import parser.RegularsParser._

trait LoggableTreeVisitor extends OurAbstractTreeVisitorTrait {
  override def visitPlus(ctx: PlusContext): ASTNode = {
    logger.fine(s"enter visitPlus with context $ctx")
    super.visitPlus(ctx)
  }

  override def visitRange(ctx: RangeContext): ASTNode = {
    logger.fine(s"enter visitRange with context $ctx")
    super.visitRange(ctx)
  }

  override def visitSimple_regex(ctx: Simple_regexContext): ASTNode = {
    logger.fine(s"enter visitSimple_regex with context $ctx")
    super.visitSimple_regex(ctx)
  }

  override def visitOne_or_none(ctx: One_or_noneContext): ASTNode = {
    logger.fine(s"enter visitOne_or_none with context $ctx")
    super.visitOne_or_none(ctx)
  }

  override def visitRegex(ctx: RegexContext): ASTNode = {
    logger.fine(s"enter visitRegex with context $ctx")
    super.visitRegex(ctx)
  }

  override def visitSet(ctx: SetContext): ASTNode = {
    logger.fine(s"enter visitSet with context $ctx")
    super.visitSet(ctx)
  }

  override def visitStar(ctx: StarContext): ASTNode = {
    logger.fine(s"enter visitStar with context $ctx")
    super.visitStar(ctx)
  }

  override def visitSet_items(ctx: Set_itemsContext): ASTNode = {
    logger.fine(s"enter visitSet_items with context $ctx")
    super.visitSet_items(ctx)
  }

  override def visitNegative_set(ctx: Negative_setContext): ASTNode = {
    logger.fine(s"enter visitNegative_set with context $ctx")
    super.visitNegative_set(ctx)
  }

  override def visitCharacter(ctx: CharacterContext): ASTNode = {
    logger.fine(s"enter visitCharacter with context $ctx")
    super.visitCharacter(ctx)
  }

  override def visitSet_item(ctx: Set_itemContext): ASTNode = {
    logger.fine(s"enter visitSet_item with context $ctx")
    super.visitSet_item(ctx)
  }

  override def visitElementary_regex(ctx: Elementary_regexContext): ASTNode = {
    logger.fine(s"enter visitElementary_regex with context $ctx")
    super.visitElementary_regex(ctx)
  }

  override def visitEos(ctx: EosContext): ASTNode = {
    logger.fine(s"enter visitEos with context $ctx")
    super.visitEos(ctx)
  }

  override def visitBasic_regex(ctx: Basic_regexContext): ASTNode = {
    logger.fine(s"enter visitBasic_regex with context $ctx")
    super.visitBasic_regex(ctx)
  }

  override def visitStart(ctx: StartContext): ASTNode = {
    logger.fine(s"enter visitStart with context $ctx")
    super.visitStart(ctx)
  }

  override def visitAny(ctx: AnyContext): ASTNode = {
    logger.fine(s"enter visitAny with context $ctx")
    super.visitAny(ctx)
  }

  override def visitPositive_set(ctx: Positive_setContext): ASTNode = {
    logger.fine(s"enter visitPositive_set with context $ctx")
    super.visitPositive_set(ctx)
  }

  override def visitGroup(ctx: GroupContext): ASTNode = {
    logger.fine(s"enter visitGroup with context $ctx")
    super.visitGroup(ctx)
  }

  override def visitNonmetacharacter(ctx: NonmetacharacterContext): ASTNode = {
    logger.fine(s"enter visitNonmetacharacter with context $ctx")
    super.visitNonmetacharacter(ctx)
  }

  override def visitMetacharacter(ctx: MetacharacterContext): ASTNode = {
    logger.fine(s"enter visitMetacharacter with context $ctx")
    super.visitMetacharacter(ctx)
  }

  override def visitFixed_repeat_regex(ctx: Fixed_repeat_regexContext): ASTNode = {
    logger.fine(s"enter visitFixed_repeat_regex with context $ctx")
    super.visitFixed_repeat_regex(ctx)
  }

  override def visitNumber(ctx: NumberContext): ASTNode = {
    logger.fine(s"enter visitNumber with context $ctx")
    super.visitNumber(ctx)
  }
}
