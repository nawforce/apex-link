// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/VFParser.g4 by ANTLR 4.8
package com.nawforce.runtime.parsers;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link VFParser}.
 */
public interface VFParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link VFParser#vfUnit}.
	 * @param ctx the parse tree
	 */
	void enterVfUnit(VFParser.VfUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#vfUnit}.
	 * @param ctx the parse tree
	 */
	void exitVfUnit(VFParser.VfUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#prolog}.
	 * @param ctx the parse tree
	 */
	void enterProlog(VFParser.PrologContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#prolog}.
	 * @param ctx the parse tree
	 */
	void exitProlog(VFParser.PrologContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(VFParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(VFParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(VFParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(VFParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(VFParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(VFParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#attributeValues}.
	 * @param ctx the parse tree
	 */
	void enterAttributeValues(VFParser.AttributeValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#attributeValues}.
	 * @param ctx the parse tree
	 */
	void exitAttributeValues(VFParser.AttributeValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(VFParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(VFParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#chardata}.
	 * @param ctx the parse tree
	 */
	void enterChardata(VFParser.ChardataContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#chardata}.
	 * @param ctx the parse tree
	 */
	void exitChardata(VFParser.ChardataContext ctx);
	/**
	 * Enter a parse tree produced by {@link VFParser#misc}.
	 * @param ctx the parse tree
	 */
	void enterMisc(VFParser.MiscContext ctx);
	/**
	 * Exit a parse tree produced by {@link VFParser#misc}.
	 * @param ctx the parse tree
	 */
	void exitMisc(VFParser.MiscContext ctx);
}