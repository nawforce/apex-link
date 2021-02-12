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
	 * Visit a parse tree produced by {@link VFParser#prolog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProlog(VFParser.PrologContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(VFParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(VFParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(VFParser.ContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(VFParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link VFParser#elExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElExpression(VFParser.ElExpressionContext ctx);
}