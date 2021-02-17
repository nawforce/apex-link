// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/VFParser.g4 by ANTLR 4.8
package com.nawforce.runtime.parsers;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link VFParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface VFParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link VFParser#vfUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVfUnit(VFParser.VfUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(VFParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(VFParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#attributeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeName(VFParser.AttributeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#attributeValues}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeValues(VFParser.AttributeValuesContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(VFParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#chardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChardata(VFParser.ChardataContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#processingInstruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcessingInstruction(VFParser.ProcessingInstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#scriptChardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptChardata(VFParser.ScriptChardataContext ctx);
}