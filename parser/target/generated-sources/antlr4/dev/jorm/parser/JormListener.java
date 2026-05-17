// Generated from dev/jorm/parser/Jorm.g4 by ANTLR 4.13.1
package dev.jorm.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JormParser}.
 */
public interface JormListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JormParser#schema}.
	 * @param ctx the parse tree
	 */
	void enterSchema(JormParser.SchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#schema}.
	 * @param ctx the parse tree
	 */
	void exitSchema(JormParser.SchemaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(JormParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(JormParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#configBlock}.
	 * @param ctx the parse tree
	 */
	void enterConfigBlock(JormParser.ConfigBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#configBlock}.
	 * @param ctx the parse tree
	 */
	void exitConfigBlock(JormParser.ConfigBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#configEntry}.
	 * @param ctx the parse tree
	 */
	void enterConfigEntry(JormParser.ConfigEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#configEntry}.
	 * @param ctx the parse tree
	 */
	void exitConfigEntry(JormParser.ConfigEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#modelBlock}.
	 * @param ctx the parse tree
	 */
	void enterModelBlock(JormParser.ModelBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#modelBlock}.
	 * @param ctx the parse tree
	 */
	void exitModelBlock(JormParser.ModelBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(JormParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(JormParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#fieldType}.
	 * @param ctx the parse tree
	 */
	void enterFieldType(JormParser.FieldTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#fieldType}.
	 * @param ctx the parse tree
	 */
	void exitFieldType(JormParser.FieldTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(JormParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(JormParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#attributeArg}.
	 * @param ctx the parse tree
	 */
	void enterAttributeArg(JormParser.AttributeArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#attributeArg}.
	 * @param ctx the parse tree
	 */
	void exitAttributeArg(JormParser.AttributeArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#enumBlock}.
	 * @param ctx the parse tree
	 */
	void enterEnumBlock(JormParser.EnumBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#enumBlock}.
	 * @param ctx the parse tree
	 */
	void exitEnumBlock(JormParser.EnumBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JormParser#enumValue}.
	 * @param ctx the parse tree
	 */
	void enterEnumValue(JormParser.EnumValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JormParser#enumValue}.
	 * @param ctx the parse tree
	 */
	void exitEnumValue(JormParser.EnumValueContext ctx);
}