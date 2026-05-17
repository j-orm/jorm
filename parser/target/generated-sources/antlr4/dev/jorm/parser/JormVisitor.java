// Generated from dev/jorm/parser/Jorm.g4 by ANTLR 4.13.1
package dev.jorm.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JormParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JormVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JormParser#schema}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema(JormParser.SchemaContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(JormParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#configBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfigBlock(JormParser.ConfigBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#configEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfigEntry(JormParser.ConfigEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#modelBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModelBlock(JormParser.ModelBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(JormParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#fieldType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldType(JormParser.FieldTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(JormParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#attributeArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeArg(JormParser.AttributeArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#enumBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBlock(JormParser.EnumBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JormParser#enumValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumValue(JormParser.EnumValueContext ctx);
}