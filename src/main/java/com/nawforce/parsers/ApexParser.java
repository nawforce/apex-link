// Generated from /Users/kevin/Projects/ApexLink/src/main/antlr4/com/nawforce/parsers/ApexParser.g4 by ANTLR 4.7.2
package com.nawforce.parsers;

import java.util.*; 

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ApexParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABSTRACT=1, BOOLEAN=2, BREAK=3, BYTE=4, CATCH=5, CHAR=6, CLASS=7, CONST=8, 
		CONTINUE=9, DEFAULT=10, DO=11, DOUBLE=12, ELSE=13, ENUM=14, EXTENDS=15, 
		FINAL=16, FINALLY=17, FLOAT=18, FOR=19, IF=20, GOTO=21, IMPLEMENTS=22, 
		INSTANCEOF=23, INTERFACE=24, LONG=25, NATIVE=26, NEW=27, PACKAGE=28, PRIVATE=29, 
		PROTECTED=30, PUBLIC=31, RETURN=32, SHORT=33, STATIC=34, VIRTUAL=35, SUPER=36, 
		SYNCHRONIZED=37, THIS=38, THROW=39, TRANSIENT=40, TRY=41, VOID=42, VOLATILE=43, 
		WHILE=44, GLOBAL=45, WEBSERVICE=46, SELECT=47, INSERT=48, UPSERT=49, UPDATE=50, 
		DELETE=51, UNDELETE=52, MERGE=53, TESTMETHOD=54, OVERRIDE=55, GET=56, 
		SET=57, BLOB=58, DATE=59, DATETIME=60, DECIMAL=61, ID=62, INTEGER=63, 
		OBJECT=64, STRING=65, TIME=66, RUNAS=67, WITH=68, WITHOUT=69, SHARING=70, 
		WITHSHARING=71, WITHOUTSHARING=72, IntegerLiteral=73, NumberLiteral=74, 
		BooleanLiteral=75, StringLiteral=76, NullLiteral=77, LPAREN=78, RPAREN=79, 
		LBRACE=80, RBRACE=81, LBRACK=82, RBRACK=83, SEMI=84, COMMA=85, DOT=86, 
		ASSIGN=87, LE=88, GE=89, GT=90, LT=91, BANG=92, TILDE=93, QUESTION=94, 
		COLON=95, EQUAL=96, TRIPLEEQUAL=97, NOTEQUAL=98, LESSANDGREATER=99, TRIPLENOTEQUAL=100, 
		AND=101, OR=102, INC=103, DEC=104, ADD=105, SUB=106, MUL=107, DIV=108, 
		BITAND=109, BITOR=110, CARET=111, MOD=112, MAP=113, ADD_ASSIGN=114, SUB_ASSIGN=115, 
		MUL_ASSIGN=116, DIV_ASSIGN=117, AND_ASSIGN=118, OR_ASSIGN=119, XOR_ASSIGN=120, 
		MOD_ASSIGN=121, LSHIFT_ASSIGN=122, RSHIFT_ASSIGN=123, URSHIFT_ASSIGN=124, 
		Identifier=125, AT=126, ELLIPSIS=127, WS=128, DOC_COMMENT=129, COMMENT=130, 
		LINE_COMMENT=131;
	public static final int
		RULE_compilationUnit = 0, RULE_typeDeclaration = 1, RULE_modifier = 2, 
		RULE_classOrInterfaceModifier = 3, RULE_variableModifier = 4, RULE_classDeclaration = 5, 
		RULE_enumDeclaration = 6, RULE_enumConstants = 7, RULE_enumConstant = 8, 
		RULE_enumBodyDeclarations = 9, RULE_interfaceDeclaration = 10, RULE_typeList = 11, 
		RULE_classBody = 12, RULE_interfaceBody = 13, RULE_classBodyDeclaration = 14, 
		RULE_memberDeclaration = 15, RULE_methodModifier = 16, RULE_methodDeclaration = 17, 
		RULE_constructorDeclaration = 18, RULE_fieldDeclaration = 19, RULE_propertyDeclaration = 20, 
		RULE_propertyBodyDeclaration = 21, RULE_interfaceBodyDeclaration = 22, 
		RULE_interfaceMemberDeclaration = 23, RULE_constDeclaration = 24, RULE_constantDeclarator = 25, 
		RULE_interfaceMethodDeclaration = 26, RULE_variableDeclarators = 27, RULE_variableDeclarator = 28, 
		RULE_variableInitializer = 29, RULE_arrayInitializer = 30, RULE_typeRef = 31, 
		RULE_arraySubscripts = 32, RULE_classOrInterfaceType = 33, RULE_primitiveType = 34, 
		RULE_typeArguments = 35, RULE_formalParameters = 36, RULE_formalParameterList = 37, 
		RULE_formalParameter = 38, RULE_qualifiedName = 39, RULE_literal = 40, 
		RULE_annotation = 41, RULE_elementValuePairs = 42, RULE_elementValuePair = 43, 
		RULE_elementValue = 44, RULE_elementValueArrayInitializer = 45, RULE_block = 46, 
		RULE_localVariableDeclarationStatement = 47, RULE_localVariableDeclaration = 48, 
		RULE_statement = 49, RULE_ifStatement = 50, RULE_forStatement = 51, RULE_whileStatement = 52, 
		RULE_doWhileStatement = 53, RULE_tryStatement = 54, RULE_returnStatement = 55, 
		RULE_throwStatement = 56, RULE_breakStatement = 57, RULE_continueStatement = 58, 
		RULE_insertStatement = 59, RULE_updateStatement = 60, RULE_deleteStatement = 61, 
		RULE_undeleteStatement = 62, RULE_upsertStatement = 63, RULE_mergeStatement = 64, 
		RULE_runAsStatement = 65, RULE_emptyStatement = 66, RULE_expressionStatement = 67, 
		RULE_idStatement = 68, RULE_propertyBlock = 69, RULE_getter = 70, RULE_setter = 71, 
		RULE_catchClause = 72, RULE_catchType = 73, RULE_finallyBlock = 74, RULE_forControl = 75, 
		RULE_forInit = 76, RULE_enhancedForControl = 77, RULE_forUpdate = 78, 
		RULE_parExpression = 79, RULE_expressionList = 80, RULE_expression = 81, 
		RULE_primary = 82, RULE_creator = 83, RULE_createdName = 84, RULE_idCreatedNamePair = 85, 
		RULE_innerCreator = 86, RULE_arrayCreatorRest = 87, RULE_mapCreatorRest = 88, 
		RULE_mapCreatorRestPair = 89, RULE_setCreatorRest = 90, RULE_literalOrExpression = 91, 
		RULE_idOrExpression = 92, RULE_classCreatorRest = 93, RULE_explicitGenericInvocation = 94, 
		RULE_nonWildcardTypeArguments = 95, RULE_typeArgumentsOrDiamond = 96, 
		RULE_nonWildcardTypeArgumentsOrDiamond = 97, RULE_superSuffix = 98, RULE_explicitGenericInvocationSuffix = 99, 
		RULE_arguments = 100, RULE_soqlLiteral = 101, RULE_withSharing = 102, 
		RULE_withoutSharing = 103, RULE_id = 104;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "typeDeclaration", "modifier", "classOrInterfaceModifier", 
			"variableModifier", "classDeclaration", "enumDeclaration", "enumConstants", 
			"enumConstant", "enumBodyDeclarations", "interfaceDeclaration", "typeList", 
			"classBody", "interfaceBody", "classBodyDeclaration", "memberDeclaration", 
			"methodModifier", "methodDeclaration", "constructorDeclaration", "fieldDeclaration", 
			"propertyDeclaration", "propertyBodyDeclaration", "interfaceBodyDeclaration", 
			"interfaceMemberDeclaration", "constDeclaration", "constantDeclarator", 
			"interfaceMethodDeclaration", "variableDeclarators", "variableDeclarator", 
			"variableInitializer", "arrayInitializer", "typeRef", "arraySubscripts", 
			"classOrInterfaceType", "primitiveType", "typeArguments", "formalParameters", 
			"formalParameterList", "formalParameter", "qualifiedName", "literal", 
			"annotation", "elementValuePairs", "elementValuePair", "elementValue", 
			"elementValueArrayInitializer", "block", "localVariableDeclarationStatement", 
			"localVariableDeclaration", "statement", "ifStatement", "forStatement", 
			"whileStatement", "doWhileStatement", "tryStatement", "returnStatement", 
			"throwStatement", "breakStatement", "continueStatement", "insertStatement", 
			"updateStatement", "deleteStatement", "undeleteStatement", "upsertStatement", 
			"mergeStatement", "runAsStatement", "emptyStatement", "expressionStatement", 
			"idStatement", "propertyBlock", "getter", "setter", "catchClause", "catchType", 
			"finallyBlock", "forControl", "forInit", "enhancedForControl", "forUpdate", 
			"parExpression", "expressionList", "expression", "primary", "creator", 
			"createdName", "idCreatedNamePair", "innerCreator", "arrayCreatorRest", 
			"mapCreatorRest", "mapCreatorRestPair", "setCreatorRest", "literalOrExpression", 
			"idOrExpression", "classCreatorRest", "explicitGenericInvocation", "nonWildcardTypeArguments", 
			"typeArgumentsOrDiamond", "nonWildcardTypeArgumentsOrDiamond", "superSuffix", 
			"explicitGenericInvocationSuffix", "arguments", "soqlLiteral", "withSharing", 
			"withoutSharing", "id"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'abstract'", "'boolean'", "'break'", "'byte'", "'catch'", "'char'", 
			"'class'", "'const'", "'continue'", "'default'", "'do'", "'double'", 
			"'else'", "'enum'", "'extends'", "'final'", "'finally'", "'float'", "'for'", 
			"'if'", "'goto'", "'implements'", "'instanceof'", "'interface'", "'long'", 
			"'native'", "'new'", "'package'", "'private'", "'protected'", "'public'", 
			"'return'", "'short'", "'static'", "'virtual'", "'super'", "'synchronized'", 
			"'this'", "'throw'", "'transient'", "'try'", "'void'", "'volatile'", 
			"'while'", "'global'", "'webservice'", "'select'", "'insert'", "'upsert'", 
			"'update'", "'delete'", "'undelete'", "'merge'", "'testmethod'", "'override'", 
			"'get'", "'set'", "'blob'", "'date'", "'datetime'", "'decimal'", "'id'", 
			"'integer'", "'object'", "'string'", "'time'", "'system.runas'", "'with'", 
			"'without'", "'sharing'", "'withsharing'", "'withoutsharing'", null, 
			null, null, null, "'null'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"';'", "','", "'.'", "'='", "'<='", "'>='", "'>'", "'<'", "'!'", "'~'", 
			"'?'", "':'", "'=='", "'==='", "'!='", "'<>'", "'!=='", "'&&'", "'||'", 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", 
			"'=>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", 
			"'<<='", "'>>='", "'>>>='", null, "'@'", "'...'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BOOLEAN", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", 
			"CONST", "CONTINUE", "DEFAULT", "DO", "DOUBLE", "ELSE", "ENUM", "EXTENDS", 
			"FINAL", "FINALLY", "FLOAT", "FOR", "IF", "GOTO", "IMPLEMENTS", "INSTANCEOF", 
			"INTERFACE", "LONG", "NATIVE", "NEW", "PACKAGE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "SHORT", "STATIC", "VIRTUAL", "SUPER", "SYNCHRONIZED", 
			"THIS", "THROW", "TRANSIENT", "TRY", "VOID", "VOLATILE", "WHILE", "GLOBAL", 
			"WEBSERVICE", "SELECT", "INSERT", "UPSERT", "UPDATE", "DELETE", "UNDELETE", 
			"MERGE", "TESTMETHOD", "OVERRIDE", "GET", "SET", "BLOB", "DATE", "DATETIME", 
			"DECIMAL", "ID", "INTEGER", "OBJECT", "STRING", "TIME", "RUNAS", "WITH", 
			"WITHOUT", "SHARING", "WITHSHARING", "WITHOUTSHARING", "IntegerLiteral", 
			"NumberLiteral", "BooleanLiteral", "StringLiteral", "NullLiteral", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", 
			"ASSIGN", "LE", "GE", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", 
			"EQUAL", "TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", 
			"AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", 
			"CARET", "MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
			"DOC_COMMENT", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ApexParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ApexParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CompilationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ApexParser.EOF, 0); }
		public List<TypeDeclarationContext> typeDeclaration() {
			return getRuleContexts(TypeDeclarationContext.class);
		}
		public TypeDeclarationContext typeDeclaration(int i) {
			return getRuleContext(TypeDeclarationContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << INTERFACE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (SEMI - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(210);
				typeDeclaration();
				}
				}
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(216);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDeclarationContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public List<ClassOrInterfaceModifierContext> classOrInterfaceModifier() {
			return getRuleContexts(ClassOrInterfaceModifierContext.class);
		}
		public ClassOrInterfaceModifierContext classOrInterfaceModifier(int i) {
			return getRuleContext(ClassOrInterfaceModifierContext.class,i);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_typeDeclaration);
		int _la;
		try {
			setState(240);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(218);
					classOrInterfaceModifier();
					}
					}
					setState(223);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(224);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(225);
					classOrInterfaceModifier();
					}
					}
					setState(230);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(231);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(232);
					classOrInterfaceModifier();
					}
					}
					setState(237);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(238);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(239);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierContext extends ParserRuleContext {
		public ClassOrInterfaceModifierContext classOrInterfaceModifier() {
			return getRuleContext(ClassOrInterfaceModifierContext.class,0);
		}
		public TerminalNode NATIVE() { return getToken(ApexParser.NATIVE, 0); }
		public TerminalNode SYNCHRONIZED() { return getToken(ApexParser.SYNCHRONIZED, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_modifier);
		int _la;
		try {
			setState(244);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case FINAL:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case STATIC:
			case VIRTUAL:
			case GLOBAL:
			case WEBSERVICE:
			case TESTMETHOD:
			case OVERRIDE:
			case WITH:
			case WITHOUT:
			case WITHSHARING:
			case WITHOUTSHARING:
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(242);
				classOrInterfaceModifier();
				}
				break;
			case NATIVE:
			case SYNCHRONIZED:
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(243);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NATIVE) | (1L << SYNCHRONIZED) | (1L << TRANSIENT))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassOrInterfaceModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode ABSTRACT() { return getToken(ApexParser.ABSTRACT, 0); }
		public TerminalNode FINAL() { return getToken(ApexParser.FINAL, 0); }
		public TerminalNode GLOBAL() { return getToken(ApexParser.GLOBAL, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public WithSharingContext withSharing() {
			return getRuleContext(WithSharingContext.class,0);
		}
		public WithoutSharingContext withoutSharing() {
			return getRuleContext(WithoutSharingContext.class,0);
		}
		public ClassOrInterfaceModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceModifier; }
	}

	public final ClassOrInterfaceModifierContext classOrInterfaceModifier() throws RecognitionException {
		ClassOrInterfaceModifierContext _localctx = new ClassOrInterfaceModifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classOrInterfaceModifier);
		try {
			setState(262);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(246);
				annotation();
				}
				break;
			case ABSTRACT:
			case FINAL:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case STATIC:
			case VIRTUAL:
			case GLOBAL:
			case WEBSERVICE:
			case TESTMETHOD:
			case OVERRIDE:
			case WITH:
			case WITHOUT:
			case WITHSHARING:
			case WITHOUTSHARING:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PUBLIC:
					{
					setState(247);
					match(PUBLIC);
					}
					break;
				case PROTECTED:
					{
					setState(248);
					match(PROTECTED);
					}
					break;
				case PRIVATE:
					{
					setState(249);
					match(PRIVATE);
					}
					break;
				case STATIC:
					{
					setState(250);
					match(STATIC);
					}
					break;
				case ABSTRACT:
					{
					setState(251);
					match(ABSTRACT);
					}
					break;
				case FINAL:
					{
					setState(252);
					match(FINAL);
					}
					break;
				case GLOBAL:
					{
					setState(253);
					match(GLOBAL);
					}
					break;
				case WEBSERVICE:
					{
					setState(254);
					match(WEBSERVICE);
					}
					break;
				case OVERRIDE:
					{
					setState(255);
					match(OVERRIDE);
					}
					break;
				case VIRTUAL:
					{
					setState(256);
					match(VIRTUAL);
					}
					break;
				case TESTMETHOD:
					{
					setState(257);
					match(TESTMETHOD);
					}
					break;
				case WITH:
				case WITHSHARING:
					{
					setState(258);
					withSharing();
					}
					break;
				case WITHOUT:
				case WITHOUTSHARING:
					{
					setState(259);
					withoutSharing();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableModifierContext extends ParserRuleContext {
		public TerminalNode FINAL() { return getToken(ApexParser.FINAL, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public VariableModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableModifier; }
	}

	public final VariableModifierContext variableModifier() throws RecognitionException {
		VariableModifierContext _localctx = new VariableModifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variableModifier);
		try {
			setState(267);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				match(FINAL);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(265);
				match(TRANSIENT);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 3);
				{
				setState(266);
				annotation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode EXTENDS() { return getToken(ApexParser.EXTENDS, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode IMPLEMENTS() { return getToken(ApexParser.IMPLEMENTS, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(CLASS);
			setState(270);
			id();
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(271);
				match(EXTENDS);
				setState(272);
				typeRef();
				}
			}

			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(275);
				match(IMPLEMENTS);
				setState(276);
				typeList();
				}
			}

			setState(279);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDeclarationContext extends ParserRuleContext {
		public TerminalNode ENUM() { return getToken(ApexParser.ENUM, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public TerminalNode IMPLEMENTS() { return getToken(ApexParser.IMPLEMENTS, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public EnumConstantsContext enumConstants() {
			return getRuleContext(EnumConstantsContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(ApexParser.COMMA, 0); }
		public EnumBodyDeclarationsContext enumBodyDeclarations() {
			return getRuleContext(EnumBodyDeclarationsContext.class,0);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(ENUM);
			setState(282);
			id();
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(283);
				match(IMPLEMENTS);
				setState(284);
				typeList();
				}
			}

			setState(287);
			match(LBRACE);
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(288);
				enumConstants();
				}
			}

			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(291);
				match(COMMA);
				}
			}

			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(294);
				enumBodyDeclarations();
				}
			}

			setState(297);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumConstantsContext extends ParserRuleContext {
		public List<EnumConstantContext> enumConstant() {
			return getRuleContexts(EnumConstantContext.class);
		}
		public EnumConstantContext enumConstant(int i) {
			return getRuleContext(EnumConstantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public EnumConstantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstants; }
	}

	public final EnumConstantsContext enumConstants() throws RecognitionException {
		EnumConstantsContext _localctx = new EnumConstantsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_enumConstants);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			enumConstant();
			setState(304);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(300);
					match(COMMA);
					setState(301);
					enumConstant();
					}
					} 
				}
				setState(306);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumConstantContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public EnumConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumConstant; }
	}

	public final EnumConstantContext enumConstant() throws RecognitionException {
		EnumConstantContext _localctx = new EnumConstantContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_enumConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(307);
				annotation();
				}
				}
				setState(312);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(313);
			id();
			setState(315);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(314);
				arguments();
				}
			}

			setState(318);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(317);
				classBody();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumBodyDeclarationsContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public EnumBodyDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBodyDeclarations; }
	}

	public final EnumBodyDeclarationsContext enumBodyDeclarations() throws RecognitionException {
		EnumBodyDeclarationsContext _localctx = new EnumBodyDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_enumBodyDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(SEMI);
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(321);
				classBodyDeclaration();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDeclarationContext extends ParserRuleContext {
		public TerminalNode INTERFACE() { return getToken(ApexParser.INTERFACE, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public InterfaceBodyContext interfaceBody() {
			return getRuleContext(InterfaceBodyContext.class,0);
		}
		public TerminalNode EXTENDS() { return getToken(ApexParser.EXTENDS, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public InterfaceDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDeclaration; }
	}

	public final InterfaceDeclarationContext interfaceDeclaration() throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(INTERFACE);
			setState(328);
			id();
			setState(331);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(329);
				match(EXTENDS);
				setState(330);
				typeList();
				}
			}

			setState(333);
			interfaceBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeListContext extends ParserRuleContext {
		public List<TypeRefContext> typeRef() {
			return getRuleContexts(TypeRefContext.class);
		}
		public TypeRefContext typeRef(int i) {
			return getRuleContext(TypeRefContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public TypeListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeList; }
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			typeRef();
			setState(340);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(336);
				match(COMMA);
				setState(337);
				typeRef();
				}
				}
				setState(342);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<ClassBodyDeclarationContext> classBodyDeclaration() {
			return getRuleContexts(ClassBodyDeclarationContext.class);
		}
		public ClassBodyDeclarationContext classBodyDeclaration(int i) {
			return getRuleContext(ClassBodyDeclarationContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(LBRACE);
			setState(347);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(344);
				classBodyDeclaration();
				}
				}
				setState(349);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(350);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<InterfaceBodyDeclarationContext> interfaceBodyDeclaration() {
			return getRuleContexts(InterfaceBodyDeclarationContext.class);
		}
		public InterfaceBodyDeclarationContext interfaceBodyDeclaration(int i) {
			return getRuleContext(InterfaceBodyDeclarationContext.class,i);
		}
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			match(LBRACE);
			setState(356);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(353);
				interfaceBodyDeclaration();
				}
				}
				setState(358);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(359);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyDeclarationContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public MemberDeclarationContext memberDeclaration() {
			return getRuleContext(MemberDeclarationContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ClassBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBodyDeclaration; }
	}

	public final ClassBodyDeclarationContext classBodyDeclaration() throws RecognitionException {
		ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(373);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(361);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(363);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(362);
					match(STATIC);
					}
				}

				setState(365);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(369);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(366);
						modifier();
						}
						} 
					}
					setState(371);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(372);
				memberDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_memberDeclaration);
		try {
			setState(382);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(375);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(376);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(377);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(378);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(379);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(380);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(381);
				propertyDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodModifierContext extends ParserRuleContext {
		public TerminalNode GLOBAL() { return getToken(ApexParser.GLOBAL, 0); }
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public MethodModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodModifier; }
	}

	public final MethodModifierContext methodModifier() throws RecognitionException {
		MethodModifierContext _localctx = new MethodModifierContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_methodModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode VOID() { return getToken(ApexParser.VOID, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public List<MethodModifierContext> methodModifier() {
			return getRuleContexts(MethodModifierContext.class);
		}
		public MethodModifierContext methodModifier(int i) {
			return getRuleContext(MethodModifierContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AT) {
				{
				setState(386);
				annotation();
				}
			}

			setState(392);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0)) {
				{
				{
				setState(389);
				methodModifier();
				}
				}
				setState(394);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(397);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case Identifier:
				{
				setState(395);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(396);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(399);
			id();
			setState(400);
			formalParameters();
			setState(403);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(401);
				block();
				}
				break;
			case SEMI:
				{
				setState(402);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
			qualifiedName();
			setState(406);
			formalParameters();
			setState(407);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldDeclarationContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			typeRef();
			setState(410);
			variableDeclarators();
			setState(411);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyDeclarationContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public PropertyBodyDeclarationContext propertyBodyDeclaration() {
			return getRuleContext(PropertyBodyDeclarationContext.class,0);
		}
		public PropertyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyDeclaration; }
	}

	public final PropertyDeclarationContext propertyDeclaration() throws RecognitionException {
		PropertyDeclarationContext _localctx = new PropertyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_propertyDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(413);
			typeRef();
			setState(414);
			variableDeclarators();
			setState(415);
			propertyBodyDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyBodyDeclarationContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public List<PropertyBlockContext> propertyBlock() {
			return getRuleContexts(PropertyBlockContext.class);
		}
		public PropertyBlockContext propertyBlock(int i) {
			return getRuleContext(PropertyBlockContext.class,i);
		}
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public PropertyBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyBodyDeclaration; }
	}

	public final PropertyBodyDeclarationContext propertyBodyDeclaration() throws RecognitionException {
		PropertyBodyDeclarationContext _localctx = new PropertyBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_propertyBodyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(417);
			match(LBRACE);
			setState(418);
			propertyBlock();
			setState(420);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				setState(419);
				propertyBlock();
				}
			}

			setState(422);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceBodyDeclarationContext extends ParserRuleContext {
		public InterfaceMemberDeclarationContext interfaceMemberDeclaration() {
			return getRuleContext(InterfaceMemberDeclarationContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public InterfaceBodyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBodyDeclaration; }
	}

	public final InterfaceBodyDeclarationContext interfaceBodyDeclaration() throws RecognitionException {
		InterfaceBodyDeclarationContext _localctx = new InterfaceBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_interfaceBodyDeclaration);
		try {
			int _alt;
			setState(432);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BOOLEAN:
			case CLASS:
			case DOUBLE:
			case ENUM:
			case FINAL:
			case FOR:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case STATIC:
			case VIRTUAL:
			case SYNCHRONIZED:
			case TRANSIENT:
			case VOID:
			case GLOBAL:
			case WEBSERVICE:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case TESTMETHOD:
			case OVERRIDE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITH:
			case WITHOUT:
			case WITHSHARING:
			case WITHOUTSHARING:
			case Identifier:
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(427);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(424);
						modifier();
						}
						} 
					}
					setState(429);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				setState(430);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(431);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMemberDeclarationContext extends ParserRuleContext {
		public ConstDeclarationContext constDeclaration() {
			return getRuleContext(ConstDeclarationContext.class,0);
		}
		public InterfaceMethodDeclarationContext interfaceMethodDeclaration() {
			return getRuleContext(InterfaceMethodDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMemberDeclaration; }
	}

	public final InterfaceMemberDeclarationContext interfaceMemberDeclaration() throws RecognitionException {
		InterfaceMemberDeclarationContext _localctx = new InterfaceMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_interfaceMemberDeclaration);
		try {
			setState(439);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(434);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(435);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(436);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(437);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(438);
				enumDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstDeclarationContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public List<ConstantDeclaratorContext> constantDeclarator() {
			return getRuleContexts(ConstantDeclaratorContext.class);
		}
		public ConstantDeclaratorContext constantDeclarator(int i) {
			return getRuleContext(ConstantDeclaratorContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ConstDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constDeclaration; }
	}

	public final ConstDeclarationContext constDeclaration() throws RecognitionException {
		ConstDeclarationContext _localctx = new ConstDeclarationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_constDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			typeRef();
			setState(442);
			constantDeclarator();
			setState(447);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(443);
				match(COMMA);
				setState(444);
				constantDeclarator();
				}
				}
				setState(449);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(450);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantDeclaratorContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public VariableInitializerContext variableInitializer() {
			return getRuleContext(VariableInitializerContext.class,0);
		}
		public List<TerminalNode> LBRACK() { return getTokens(ApexParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(ApexParser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(ApexParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(ApexParser.RBRACK, i);
		}
		public ConstantDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDeclarator; }
	}

	public final ConstantDeclaratorContext constantDeclarator() throws RecognitionException {
		ConstantDeclaratorContext _localctx = new ConstantDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_constantDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			id();
			setState(457);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(453);
				match(LBRACK);
				setState(454);
				match(RBRACK);
				}
				}
				setState(459);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(460);
			match(ASSIGN);
			setState(461);
			variableInitializer();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceMethodDeclarationContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public FormalParametersContext formalParameters() {
			return getRuleContext(FormalParametersContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode VOID() { return getToken(ApexParser.VOID, 0); }
		public InterfaceMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMethodDeclaration; }
	}

	public final InterfaceMethodDeclarationContext interfaceMethodDeclaration() throws RecognitionException {
		InterfaceMethodDeclarationContext _localctx = new InterfaceMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_interfaceMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case Identifier:
				{
				setState(463);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(464);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(467);
			id();
			setState(468);
			formalParameters();
			setState(469);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorsContext extends ParserRuleContext {
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public VariableDeclaratorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarators; }
	}

	public final VariableDeclaratorsContext variableDeclarators() throws RecognitionException {
		VariableDeclaratorsContext _localctx = new VariableDeclaratorsContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(471);
			variableDeclarator();
			setState(476);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(472);
				match(COMMA);
				setState(473);
				variableDeclarator();
				}
				}
				setState(478);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public VariableInitializerContext variableInitializer() {
			return getRuleContext(VariableInitializerContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			id();
			setState(482);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(480);
				match(ASSIGN);
				setState(481);
				variableInitializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableInitializerContext extends ParserRuleContext {
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableInitializer; }
	}

	public final VariableInitializerContext variableInitializer() throws RecognitionException {
		VariableInitializerContext _localctx = new VariableInitializerContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_variableInitializer);
		try {
			setState(486);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(484);
				arrayInitializer();
				}
				break;
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SUPER:
			case THIS:
			case VOID:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case LBRACK:
			case LT:
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(485);
				expression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<VariableInitializerContext> variableInitializer() {
			return getRuleContexts(VariableInitializerContext.class);
		}
		public VariableInitializerContext variableInitializer(int i) {
			return getRuleContext(VariableInitializerContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			match(LBRACE);
			setState(500);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(489);
				variableInitializer();
				setState(494);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(490);
						match(COMMA);
						setState(491);
						variableInitializer();
						}
						} 
					}
					setState(496);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				}
				setState(498);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(497);
					match(COMMA);
					}
				}

				}
			}

			setState(502);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeRefContext extends ParserRuleContext {
		public ClassOrInterfaceTypeContext classOrInterfaceType() {
			return getRuleContext(ClassOrInterfaceTypeContext.class,0);
		}
		public ArraySubscriptsContext arraySubscripts() {
			return getRuleContext(ArraySubscriptsContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeRef; }
	}

	public final TypeRefContext typeRef() throws RecognitionException {
		TypeRefContext _localctx = new TypeRefContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_typeRef);
		try {
			setState(510);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(504);
				classOrInterfaceType();
				setState(505);
				arraySubscripts();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(507);
				primitiveType();
				setState(508);
				arraySubscripts();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArraySubscriptsContext extends ParserRuleContext {
		public List<TerminalNode> LBRACK() { return getTokens(ApexParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(ApexParser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(ApexParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(ApexParser.RBRACK, i);
		}
		public ArraySubscriptsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arraySubscripts; }
	}

	public final ArraySubscriptsContext arraySubscripts() throws RecognitionException {
		ArraySubscriptsContext _localctx = new ArraySubscriptsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(516);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(512);
					match(LBRACK);
					setState(513);
					match(RBRACK);
					}
					} 
				}
				setState(518);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassOrInterfaceTypeContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TypeArgumentsContext> typeArguments() {
			return getRuleContexts(TypeArgumentsContext.class);
		}
		public TypeArgumentsContext typeArguments(int i) {
			return getRuleContext(TypeArgumentsContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(ApexParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ApexParser.DOT, i);
		}
		public ClassOrInterfaceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrInterfaceType; }
	}

	public final ClassOrInterfaceTypeContext classOrInterfaceType() throws RecognitionException {
		ClassOrInterfaceTypeContext _localctx = new ClassOrInterfaceTypeContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_classOrInterfaceType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(519);
			id();
			setState(521);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(520);
				typeArguments();
				}
				break;
			}
			setState(530);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(523);
					match(DOT);
					setState(524);
					id();
					setState(526);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
					case 1:
						{
						setState(525);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(532);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public TerminalNode BLOB() { return getToken(ApexParser.BLOB, 0); }
		public TerminalNode BOOLEAN() { return getToken(ApexParser.BOOLEAN, 0); }
		public TerminalNode DATE() { return getToken(ApexParser.DATE, 0); }
		public TerminalNode DATETIME() { return getToken(ApexParser.DATETIME, 0); }
		public TerminalNode DECIMAL() { return getToken(ApexParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(ApexParser.DOUBLE, 0); }
		public TerminalNode ID() { return getToken(ApexParser.ID, 0); }
		public TerminalNode INTEGER() { return getToken(ApexParser.INTEGER, 0); }
		public TerminalNode LONG() { return getToken(ApexParser.LONG, 0); }
		public TerminalNode OBJECT() { return getToken(ApexParser.OBJECT, 0); }
		public TerminalNode STRING() { return getToken(ApexParser.STRING, 0); }
		public TerminalNode TIME() { return getToken(ApexParser.TIME, 0); }
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << LONG) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(535);
			match(LT);
			setState(536);
			typeList();
			setState(537);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParametersContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public FormalParameterListContext formalParameterList() {
			return getRuleContext(FormalParameterListContext.class,0);
		}
		public FormalParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameters; }
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
			match(LPAREN);
			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << TRANSIENT) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(540);
				formalParameterList();
				}
			}

			setState(543);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterListContext extends ParserRuleContext {
		public List<FormalParameterContext> formalParameter() {
			return getRuleContexts(FormalParameterContext.class);
		}
		public FormalParameterContext formalParameter(int i) {
			return getRuleContext(FormalParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public FormalParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameterList; }
	}

	public final FormalParameterListContext formalParameterList() throws RecognitionException {
		FormalParameterListContext _localctx = new FormalParameterListContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			formalParameter();
			setState(550);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(546);
				match(COMMA);
				setState(547);
				formalParameter();
				}
				}
				setState(552);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParameterContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(553);
				variableModifier();
				}
				}
				setState(558);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(559);
			typeRef();
			setState(560);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(ApexParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ApexParser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			id();
			setState(567);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(563);
				match(DOT);
				setState(564);
				id();
				}
				}
				setState(569);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public TerminalNode NumberLiteral() { return getToken(ApexParser.NumberLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(ApexParser.StringLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ApexParser.BooleanLiteral, 0); }
		public TerminalNode NullLiteral() { return getToken(ApexParser.NullLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(570);
			_la = _input.LA(1);
			if ( !(((((_la - 73)) & ~0x3f) == 0 && ((1L << (_la - 73)) & ((1L << (IntegerLiteral - 73)) | (1L << (NumberLiteral - 73)) | (1L << (BooleanLiteral - 73)) | (1L << (StringLiteral - 73)) | (1L << (NullLiteral - 73)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(ApexParser.AT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ElementValuePairsContext elementValuePairs() {
			return getRuleContext(ElementValuePairsContext.class,0);
		}
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			match(AT);
			setState(573);
			qualifiedName();
			setState(580);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(574);
				match(LPAREN);
				setState(577);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(575);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(576);
					elementValue();
					}
					break;
				}
				setState(579);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValuePairsContext extends ParserRuleContext {
		public List<ElementValuePairContext> elementValuePair() {
			return getRuleContexts(ElementValuePairContext.class);
		}
		public ElementValuePairContext elementValuePair(int i) {
			return getRuleContext(ElementValuePairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ElementValuePairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePairs; }
	}

	public final ElementValuePairsContext elementValuePairs() throws RecognitionException {
		ElementValuePairsContext _localctx = new ElementValuePairsContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			elementValuePair();
			setState(589);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (COMMA - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(584);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(583);
					match(COMMA);
					}
				}

				setState(586);
				elementValuePair();
				}
				}
				setState(591);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValuePairContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePair; }
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(592);
			id();
			setState(593);
			match(ASSIGN);
			setState(594);
			elementValue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public ElementValueArrayInitializerContext elementValueArrayInitializer() {
			return getRuleContext(ElementValueArrayInitializerContext.class,0);
		}
		public ElementValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValue; }
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_elementValue);
		try {
			setState(599);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SUPER:
			case THIS:
			case VOID:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case LBRACK:
			case LT:
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(596);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(597);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(598);
				elementValueArrayInitializer();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementValueArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<ElementValueContext> elementValue() {
			return getRuleContexts(ElementValueContext.class);
		}
		public ElementValueContext elementValue(int i) {
			return getRuleContext(ElementValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueArrayInitializer; }
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
			match(LBRACE);
			setState(610);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(602);
				elementValue();
				setState(607);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(603);
						match(COMMA);
						setState(604);
						elementValue();
						}
						} 
					}
					setState(609);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				}
				}
			}

			setState(613);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(612);
				match(COMMA);
				}
			}

			setState(615);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			match(LBRACE);
			setState(621);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << BREAK) | (1L << CONTINUE) | (1L << DO) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << IF) | (1L << LONG) | (1L << NEW) | (1L << RETURN) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (SEMI - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(618);
				statement();
				}
				}
				setState(623);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(624);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalVariableDeclarationStatementContext extends ParserRuleContext {
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public LocalVariableDeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclarationStatement; }
	}

	public final LocalVariableDeclarationStatementContext localVariableDeclarationStatement() throws RecognitionException {
		LocalVariableDeclarationStatementContext _localctx = new LocalVariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(626);
			localVariableDeclaration();
			setState(627);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalVariableDeclarationContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public VariableDeclaratorsContext variableDeclarators() {
			return getRuleContext(VariableDeclaratorsContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclaration; }
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_localVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(632);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(629);
				variableModifier();
				}
				}
				setState(634);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(635);
			typeRef();
			setState(636);
			variableDeclarators();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LocalVariableDeclarationStatementContext localVariableDeclarationStatement() {
			return getRuleContext(LocalVariableDeclarationStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public DoWhileStatementContext doWhileStatement() {
			return getRuleContext(DoWhileStatementContext.class,0);
		}
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public ThrowStatementContext throwStatement() {
			return getRuleContext(ThrowStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public UpdateStatementContext updateStatement() {
			return getRuleContext(UpdateStatementContext.class,0);
		}
		public DeleteStatementContext deleteStatement() {
			return getRuleContext(DeleteStatementContext.class,0);
		}
		public UndeleteStatementContext undeleteStatement() {
			return getRuleContext(UndeleteStatementContext.class,0);
		}
		public UpsertStatementContext upsertStatement() {
			return getRuleContext(UpsertStatementContext.class,0);
		}
		public MergeStatementContext mergeStatement() {
			return getRuleContext(MergeStatementContext.class,0);
		}
		public RunAsStatementContext runAsStatement() {
			return getRuleContext(RunAsStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public IdStatementContext idStatement() {
			return getRuleContext(IdStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_statement);
		try {
			setState(659);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(638);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(639);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(640);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(641);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(642);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(643);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(644);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(645);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(646);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(647);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(648);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(649);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(650);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(651);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(652);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(653);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(654);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(655);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(656);
				emptyStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(657);
				expressionStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(658);
				idStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(ApexParser.IF, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(661);
			match(IF);
			setState(662);
			parExpression();
			setState(663);
			statement();
			setState(666);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(664);
				match(ELSE);
				setState(665);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ApexParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(668);
			match(FOR);
			setState(669);
			match(LPAREN);
			setState(670);
			forControl();
			setState(671);
			match(RPAREN);
			setState(672);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(ApexParser.WHILE, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			match(WHILE);
			setState(675);
			parExpression();
			setState(676);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(ApexParser.DO, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode WHILE() { return getToken(ApexParser.WHILE, 0); }
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public DoWhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileStatement; }
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(678);
			match(DO);
			setState(679);
			statement();
			setState(680);
			match(WHILE);
			setState(681);
			parExpression();
			setState(682);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryStatementContext extends ParserRuleContext {
		public TerminalNode TRY() { return getToken(ApexParser.TRY, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext finallyBlock() {
			return getRuleContext(FinallyBlockContext.class,0);
		}
		public List<CatchClauseContext> catchClause() {
			return getRuleContexts(CatchClauseContext.class);
		}
		public CatchClauseContext catchClause(int i) {
			return getRuleContext(CatchClauseContext.class,i);
		}
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(684);
			match(TRY);
			setState(685);
			block();
			setState(695);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(687); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(686);
					catchClause();
					}
					}
					setState(689); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CATCH );
				setState(692);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(691);
					finallyBlock();
					}
				}

				}
				break;
			case FINALLY:
				{
				setState(694);
				finallyBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(ApexParser.RETURN, 0); }
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(697);
			match(RETURN);
			setState(699);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(698);
				expression(0);
				}
			}

			setState(701);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThrowStatementContext extends ParserRuleContext {
		public TerminalNode THROW() { return getToken(ApexParser.THROW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public ThrowStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwStatement; }
	}

	public final ThrowStatementContext throwStatement() throws RecognitionException {
		ThrowStatementContext _localctx = new ThrowStatementContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			match(THROW);
			setState(704);
			expression(0);
			setState(705);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(ApexParser.BREAK, 0); }
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_breakStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			match(BREAK);
			setState(709);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(708);
				id();
				}
			}

			setState(711);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(ApexParser.CONTINUE, 0); }
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_continueStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(713);
			match(CONTINUE);
			setState(715);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(714);
				id();
				}
			}

			setState(717);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(719);
			match(INSERT);
			setState(720);
			expression(0);
			setState(721);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpdateStatementContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public UpdateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateStatement; }
	}

	public final UpdateStatementContext updateStatement() throws RecognitionException {
		UpdateStatementContext _localctx = new UpdateStatementContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(723);
			match(UPDATE);
			setState(724);
			expression(0);
			setState(725);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteStatementContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(ApexParser.DELETE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public DeleteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteStatement; }
	}

	public final DeleteStatementContext deleteStatement() throws RecognitionException {
		DeleteStatementContext _localctx = new DeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(727);
			match(DELETE);
			setState(728);
			expression(0);
			setState(729);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UndeleteStatementContext extends ParserRuleContext {
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public UndeleteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_undeleteStatement; }
	}

	public final UndeleteStatementContext undeleteStatement() throws RecognitionException {
		UndeleteStatementContext _localctx = new UndeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(731);
			match(UNDELETE);
			setState(732);
			expression(0);
			setState(733);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpsertStatementContext extends ParserRuleContext {
		public TerminalNode UPSERT() { return getToken(ApexParser.UPSERT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public UpsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_upsertStatement; }
	}

	public final UpsertStatementContext upsertStatement() throws RecognitionException {
		UpsertStatementContext _localctx = new UpsertStatementContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(735);
			match(UPSERT);
			setState(736);
			expression(0);
			setState(738);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(737);
				id();
				}
			}

			setState(740);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MergeStatementContext extends ParserRuleContext {
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public MergeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mergeStatement; }
	}

	public final MergeStatementContext mergeStatement() throws RecognitionException {
		MergeStatementContext _localctx = new MergeStatementContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(742);
			match(MERGE);
			setState(743);
			expression(0);
			setState(744);
			expression(0);
			setState(745);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RunAsStatementContext extends ParserRuleContext {
		public TerminalNode RUNAS() { return getToken(ApexParser.RUNAS, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public RunAsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_runAsStatement; }
	}

	public final RunAsStatementContext runAsStatement() throws RecognitionException {
		RunAsStatementContext _localctx = new RunAsStatementContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(747);
			match(RUNAS);
			setState(748);
			match(LPAREN);
			setState(750);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(749);
				expressionList();
				}
			}

			setState(752);
			match(RPAREN);
			setState(754);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(753);
				block();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStatementContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(756);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(758);
			expression(0);
			setState(759);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdStatementContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public IdStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idStatement; }
	}

	public final IdStatementContext idStatement() throws RecognitionException {
		IdStatementContext _localctx = new IdStatementContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_idStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(761);
			id();
			setState(762);
			match(COLON);
			setState(763);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyBlockContext extends ParserRuleContext {
		public GetterContext getter() {
			return getRuleContext(GetterContext.class,0);
		}
		public SetterContext setter() {
			return getRuleContext(SetterContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public PropertyBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyBlock; }
	}

	public final PropertyBlockContext propertyBlock() throws RecognitionException {
		PropertyBlockContext _localctx = new PropertyBlockContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(768);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(765);
				modifier();
				}
				}
				setState(770);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(773);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(771);
				getter();
				}
				break;
			case SET:
				{
				setState(772);
				setter();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetterContext extends ParserRuleContext {
		public TerminalNode GET() { return getToken(ApexParser.GET, 0); }
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public GetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getter; }
	}

	public final GetterContext getter() throws RecognitionException {
		GetterContext _localctx = new GetterContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			match(GET);
			setState(778);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(776);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(777);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetterContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setter; }
	}

	public final SetterContext setter() throws RecognitionException {
		SetterContext _localctx = new SetterContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780);
			match(SET);
			setState(783);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(781);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(782);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchClauseContext extends ParserRuleContext {
		public TerminalNode CATCH() { return getToken(ApexParser.CATCH, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public CatchTypeContext catchType() {
			return getRuleContext(CatchTypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public CatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchClause; }
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_catchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(785);
			match(CATCH);
			setState(786);
			match(LPAREN);
			setState(790);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(787);
				variableModifier();
				}
				}
				setState(792);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(793);
			catchType();
			setState(794);
			id();
			setState(795);
			match(RPAREN);
			setState(796);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchTypeContext extends ParserRuleContext {
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TerminalNode> BITOR() { return getTokens(ApexParser.BITOR); }
		public TerminalNode BITOR(int i) {
			return getToken(ApexParser.BITOR, i);
		}
		public CatchTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchType; }
	}

	public final CatchTypeContext catchType() throws RecognitionException {
		CatchTypeContext _localctx = new CatchTypeContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(798);
			qualifiedName();
			setState(803);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(799);
				match(BITOR);
				setState(800);
				qualifiedName();
				}
				}
				setState(805);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinallyBlockContext extends ParserRuleContext {
		public TerminalNode FINALLY() { return getToken(ApexParser.FINALLY, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyBlock; }
	}

	public final FinallyBlockContext finallyBlock() throws RecognitionException {
		FinallyBlockContext _localctx = new FinallyBlockContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(806);
			match(FINALLY);
			setState(807);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForControlContext extends ParserRuleContext {
		public EnhancedForControlContext enhancedForControl() {
			return getRuleContext(EnhancedForControlContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(ApexParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(ApexParser.SEMI, i);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForUpdateContext forUpdate() {
			return getRuleContext(ForUpdateContext.class,0);
		}
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forControl; }
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_forControl);
		int _la;
		try {
			setState(821);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(809);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(811);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << TRANSIENT) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(810);
					forInit();
					}
				}

				setState(813);
				match(SEMI);
				setState(815);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(814);
					expression(0);
					}
				}

				setState(817);
				match(SEMI);
				setState(819);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(818);
					forUpdate();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitContext extends ParserRuleContext {
		public LocalVariableDeclarationContext localVariableDeclaration() {
			return getRuleContext(LocalVariableDeclarationContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_forInit);
		try {
			setState(825);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(823);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(824);
				expressionList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnhancedForControlContext extends ParserRuleContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<VariableModifierContext> variableModifier() {
			return getRuleContexts(VariableModifierContext.class);
		}
		public VariableModifierContext variableModifier(int i) {
			return getRuleContext(VariableModifierContext.class,i);
		}
		public EnhancedForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhancedForControl; }
	}

	public final EnhancedForControlContext enhancedForControl() throws RecognitionException {
		EnhancedForControlContext _localctx = new EnhancedForControlContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_enhancedForControl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(830);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(827);
				variableModifier();
				}
				}
				setState(832);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(833);
			typeRef();
			setState(834);
			id();
			setState(835);
			match(COLON);
			setState(836);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForUpdateContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ForUpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forUpdate; }
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(838);
			expressionList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ParExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parExpression; }
	}

	public final ParExpressionContext parExpression() throws RecognitionException {
		ParExpressionContext _localctx = new ParExpressionContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(840);
			match(LPAREN);
			setState(841);
			expression(0);
			setState(842);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(844);
			expression(0);
			setState(849);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(845);
				match(COMMA);
				setState(846);
				expression(0);
				}
				}
				setState(851);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Alt23ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ApexParser.OR, 0); }
		public Alt23ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt1ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Alt1ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt10ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(ApexParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ApexParser.DEC, 0); }
		public Alt10ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt8ExpressionContext extends ExpressionContext {
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public Alt8ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt19ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BITAND() { return getToken(ApexParser.BITAND, 0); }
		public Alt19ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt17ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public Alt17ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt25ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public TerminalNode ADD_ASSIGN() { return getToken(ApexParser.ADD_ASSIGN, 0); }
		public TerminalNode SUB_ASSIGN() { return getToken(ApexParser.SUB_ASSIGN, 0); }
		public TerminalNode MUL_ASSIGN() { return getToken(ApexParser.MUL_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(ApexParser.DIV_ASSIGN, 0); }
		public TerminalNode AND_ASSIGN() { return getToken(ApexParser.AND_ASSIGN, 0); }
		public TerminalNode OR_ASSIGN() { return getToken(ApexParser.OR_ASSIGN, 0); }
		public TerminalNode XOR_ASSIGN() { return getToken(ApexParser.XOR_ASSIGN, 0); }
		public TerminalNode RSHIFT_ASSIGN() { return getToken(ApexParser.RSHIFT_ASSIGN, 0); }
		public TerminalNode URSHIFT_ASSIGN() { return getToken(ApexParser.URSHIFT_ASSIGN, 0); }
		public TerminalNode LSHIFT_ASSIGN() { return getToken(ApexParser.LSHIFT_ASSIGN, 0); }
		public TerminalNode MOD_ASSIGN() { return getToken(ApexParser.MOD_ASSIGN, 0); }
		public Alt25ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt12ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TILDE() { return getToken(ApexParser.TILDE, 0); }
		public TerminalNode BANG() { return getToken(ApexParser.BANG, 0); }
		public Alt12ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt3ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public InnerCreatorContext innerCreator() {
			return getRuleContext(InnerCreatorContext.class,0);
		}
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public Alt3ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt14ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(ApexParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public Alt14ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt5ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public ExplicitGenericInvocationContext explicitGenericInvocation() {
			return getRuleContext(ExplicitGenericInvocationContext.class,0);
		}
		public Alt5ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt16ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TerminalNode LE() { return getToken(ApexParser.LE, 0); }
		public TerminalNode GE() { return getToken(ApexParser.GE, 0); }
		public Alt16ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class FunctionCallExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FunctionCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt22ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ApexParser.AND, 0); }
		public Alt22ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt9ExpressionContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Alt9ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt18ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TRIPLEEQUAL() { return getToken(ApexParser.TRIPLEEQUAL, 0); }
		public TerminalNode TRIPLENOTEQUAL() { return getToken(ApexParser.TRIPLENOTEQUAL, 0); }
		public TerminalNode EQUAL() { return getToken(ApexParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(ApexParser.NOTEQUAL, 0); }
		public TerminalNode LESSANDGREATER() { return getToken(ApexParser.LESSANDGREATER, 0); }
		public Alt18ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt24ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode QUESTION() { return getToken(ApexParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public Alt24ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt11ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ADD() { return getToken(ApexParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public TerminalNode INC() { return getToken(ApexParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ApexParser.DEC, 0); }
		public Alt11ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt2ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public Alt2ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt20ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode CARET() { return getToken(ApexParser.CARET, 0); }
		public Alt20ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt13ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(ApexParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(ApexParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(ApexParser.MOD, 0); }
		public Alt13ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt4ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public SuperSuffixContext superSuffix() {
			return getRuleContext(SuperSuffixContext.class,0);
		}
		public Alt4ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt26ExpressionContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public Alt26ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt21ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BITOR() { return getToken(ApexParser.BITOR, 0); }
		public Alt21ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt15ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> LT() { return getTokens(ApexParser.LT); }
		public TerminalNode LT(int i) {
			return getToken(ApexParser.LT, i);
		}
		public List<TerminalNode> GT() { return getTokens(ApexParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(ApexParser.GT, i);
		}
		public Alt15ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Alt6ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(ApexParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(ApexParser.RBRACK, 0); }
		public Alt6ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 162;
		enterRecursionRule(_localctx, 162, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(865);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(853);
				match(NEW);
				setState(854);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(855);
				match(LPAREN);
				setState(856);
				typeRef();
				setState(857);
				match(RPAREN);
				setState(858);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(860);
				_la = _input.LA(1);
				if ( !(((((_la - 103)) & ~0x3f) == 0 && ((1L << (_la - 103)) & ((1L << (INC - 103)) | (1L << (DEC - 103)) | (1L << (ADD - 103)) | (1L << (SUB - 103)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(861);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(862);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(863);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(864);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(961);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(959);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,93,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(867);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(868);
						_la = _input.LA(1);
						if ( !(((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & ((1L << (MUL - 107)) | (1L << (DIV - 107)) | (1L << (MOD - 107)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(869);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(870);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(871);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(872);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(873);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(881);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
						case 1:
							{
							setState(874);
							match(LT);
							setState(875);
							match(LT);
							}
							break;
						case 2:
							{
							setState(876);
							match(GT);
							setState(877);
							match(GT);
							setState(878);
							match(GT);
							}
							break;
						case 3:
							{
							setState(879);
							match(GT);
							setState(880);
							match(GT);
							}
							break;
						}
						setState(883);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(884);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(893);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
						case 1:
							{
							setState(885);
							match(LT);
							setState(886);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(887);
							match(GT);
							setState(888);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(889);
							match(LE);
							}
							break;
						case 4:
							{
							setState(890);
							match(GE);
							}
							break;
						case 5:
							{
							setState(891);
							match(GT);
							}
							break;
						case 6:
							{
							setState(892);
							match(LT);
							}
							break;
						}
						setState(895);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(896);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(897);
						_la = _input.LA(1);
						if ( !(((((_la - 96)) & ~0x3f) == 0 && ((1L << (_la - 96)) & ((1L << (EQUAL - 96)) | (1L << (TRIPLEEQUAL - 96)) | (1L << (NOTEQUAL - 96)) | (1L << (LESSANDGREATER - 96)) | (1L << (TRIPLENOTEQUAL - 96)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(898);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(899);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(900);
						match(BITAND);
						setState(901);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(902);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(903);
						match(CARET);
						setState(904);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(905);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(906);
						match(BITOR);
						setState(907);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(908);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(909);
						match(AND);
						setState(910);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(911);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(912);
						match(OR);
						setState(913);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(914);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(915);
						match(QUESTION);
						setState(916);
						expression(0);
						setState(917);
						match(COLON);
						setState(918);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(920);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(921);
						_la = _input.LA(1);
						if ( !(((((_la - 87)) & ~0x3f) == 0 && ((1L << (_la - 87)) & ((1L << (ASSIGN - 87)) | (1L << (ADD_ASSIGN - 87)) | (1L << (SUB_ASSIGN - 87)) | (1L << (MUL_ASSIGN - 87)) | (1L << (DIV_ASSIGN - 87)) | (1L << (AND_ASSIGN - 87)) | (1L << (OR_ASSIGN - 87)) | (1L << (XOR_ASSIGN - 87)) | (1L << (MOD_ASSIGN - 87)) | (1L << (LSHIFT_ASSIGN - 87)) | (1L << (RSHIFT_ASSIGN - 87)) | (1L << (URSHIFT_ASSIGN - 87)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(922);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(923);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(924);
						match(DOT);
						setState(925);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(926);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(927);
						match(DOT);
						setState(928);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(929);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(930);
						match(DOT);
						setState(931);
						match(NEW);
						setState(933);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(932);
							nonWildcardTypeArguments();
							}
						}

						setState(935);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(936);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(937);
						match(DOT);
						setState(938);
						match(SUPER);
						setState(939);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(940);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(941);
						match(DOT);
						setState(942);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(943);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(944);
						match(LBRACK);
						setState(945);
						expression(0);
						setState(946);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(948);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(949);
						match(LPAREN);
						setState(951);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(950);
							expressionList();
							}
						}

						setState(953);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(954);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(955);
						_la = _input.LA(1);
						if ( !(_la==INC || _la==DEC) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 21:
						{
						_localctx = new Alt17ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(956);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(957);
						match(INSTANCEOF);
						setState(958);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(963);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Alt7PrimaryContext extends PrimaryContext {
		public TerminalNode VOID() { return getToken(ApexParser.VOID, 0); }
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public Alt7PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt9PrimaryContext extends PrimaryContext {
		public SoqlLiteralContext soqlLiteral() {
			return getRuleContext(SoqlLiteralContext.class,0);
		}
		public Alt9PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt1PrimaryContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public Alt1PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt8PrimaryContext extends PrimaryContext {
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public ExplicitGenericInvocationSuffixContext explicitGenericInvocationSuffix() {
			return getRuleContext(ExplicitGenericInvocationSuffixContext.class,0);
		}
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public Alt8PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt2PrimaryContext extends PrimaryContext {
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public Alt2PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt3PrimaryContext extends PrimaryContext {
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public Alt3PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt4PrimaryContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Alt4PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt5PrimaryContext extends PrimaryContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Alt5PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class Alt6PrimaryContext extends PrimaryContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public Alt6PrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_primary);
		try {
			setState(986);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(964);
				match(LPAREN);
				setState(965);
				expression(0);
				setState(966);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(968);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(969);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(970);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(971);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(972);
				typeRef();
				setState(973);
				match(DOT);
				setState(974);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(976);
				match(VOID);
				setState(977);
				match(DOT);
				setState(978);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(979);
				nonWildcardTypeArguments();
				setState(983);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case BOOLEAN:
				case DOUBLE:
				case FOR:
				case LONG:
				case NEW:
				case SUPER:
				case SELECT:
				case INSERT:
				case UPSERT:
				case UPDATE:
				case DELETE:
				case UNDELETE:
				case MERGE:
				case GET:
				case SET:
				case BLOB:
				case DATE:
				case DATETIME:
				case DECIMAL:
				case ID:
				case INTEGER:
				case OBJECT:
				case STRING:
				case TIME:
				case WITHSHARING:
				case WITHOUTSHARING:
				case Identifier:
					{
					setState(980);
					explicitGenericInvocationSuffix();
					}
					break;
				case THIS:
					{
					setState(981);
					match(THIS);
					setState(982);
					arguments();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 9:
				_localctx = new Alt9PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(985);
				soqlLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreatorContext extends ParserRuleContext {
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
	 
		public CreatorContext() { }
		public void copyFrom(CreatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Alt1CreatorContext extends CreatorContext {
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public CreatedNameContext createdName() {
			return getRuleContext(CreatedNameContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public Alt1CreatorContext(CreatorContext ctx) { copyFrom(ctx); }
	}
	public static class Alt2CreatorContext extends CreatorContext {
		public CreatedNameContext createdName() {
			return getRuleContext(CreatedNameContext.class,0);
		}
		public ArrayCreatorRestContext arrayCreatorRest() {
			return getRuleContext(ArrayCreatorRestContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public MapCreatorRestContext mapCreatorRest() {
			return getRuleContext(MapCreatorRestContext.class,0);
		}
		public SetCreatorRestContext setCreatorRest() {
			return getRuleContext(SetCreatorRestContext.class,0);
		}
		public Alt2CreatorContext(CreatorContext ctx) { copyFrom(ctx); }
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_creator);
		try {
			setState(999);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(988);
				nonWildcardTypeArguments();
				setState(989);
				createdName();
				setState(990);
				classCreatorRest();
				}
				break;
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case Identifier:
				_localctx = new Alt2CreatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(992);
				createdName();
				setState(997);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(993);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(994);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(995);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(996);
					setCreatorRest();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreatedNameContext extends ParserRuleContext {
		public List<IdCreatedNamePairContext> idCreatedNamePair() {
			return getRuleContexts(IdCreatedNamePairContext.class);
		}
		public IdCreatedNamePairContext idCreatedNamePair(int i) {
			return getRuleContext(IdCreatedNamePairContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(ApexParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ApexParser.DOT, i);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public CreatedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createdName; }
	}

	public final CreatedNameContext createdName() throws RecognitionException {
		CreatedNameContext _localctx = new CreatedNameContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_createdName);
		int _la;
		try {
			setState(1010);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1001);
				idCreatedNamePair();
				setState(1006);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(1002);
					match(DOT);
					setState(1003);
					idCreatedNamePair();
					}
					}
					setState(1008);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1009);
				primitiveType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdCreatedNamePairContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() {
			return getRuleContext(TypeArgumentsOrDiamondContext.class,0);
		}
		public IdCreatedNamePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idCreatedNamePair; }
	}

	public final IdCreatedNamePairContext idCreatedNamePair() throws RecognitionException {
		IdCreatedNamePairContext _localctx = new IdCreatedNamePairContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1012);
			id();
			setState(1014);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1013);
				typeArgumentsOrDiamond();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InnerCreatorContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public NonWildcardTypeArgumentsOrDiamondContext nonWildcardTypeArgumentsOrDiamond() {
			return getRuleContext(NonWildcardTypeArgumentsOrDiamondContext.class,0);
		}
		public InnerCreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_innerCreator; }
	}

	public final InnerCreatorContext innerCreator() throws RecognitionException {
		InnerCreatorContext _localctx = new InnerCreatorContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_innerCreator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1016);
			id();
			setState(1018);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1017);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(1020);
			classCreatorRest();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayCreatorRestContext extends ParserRuleContext {
		public List<TerminalNode> LBRACK() { return getTokens(ApexParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(ApexParser.LBRACK, i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(ApexParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(ApexParser.RBRACK, i);
		}
		public ArraySubscriptsContext arraySubscripts() {
			return getRuleContext(ArraySubscriptsContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayCreatorRest; }
	}

	public final ArrayCreatorRestContext arrayCreatorRest() throws RecognitionException {
		ArrayCreatorRestContext _localctx = new ArrayCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_arrayCreatorRest);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1022);
			match(LBRACK);
			setState(1040);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(1023);
				match(RBRACK);
				setState(1024);
				arraySubscripts();
				setState(1025);
				arrayInitializer();
				}
				break;
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SUPER:
			case THIS:
			case VOID:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
			case NullLiteral:
			case LPAREN:
			case LBRACK:
			case LT:
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case Identifier:
				{
				setState(1027);
				expression(0);
				setState(1028);
				match(RBRACK);
				setState(1035);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1029);
						match(LBRACK);
						setState(1030);
						expression(0);
						setState(1031);
						match(RBRACK);
						}
						} 
					}
					setState(1037);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				}
				setState(1038);
				arraySubscripts();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MapCreatorRestContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public List<MapCreatorRestPairContext> mapCreatorRestPair() {
			return getRuleContexts(MapCreatorRestPairContext.class);
		}
		public MapCreatorRestPairContext mapCreatorRestPair(int i) {
			return getRuleContext(MapCreatorRestPairContext.class,i);
		}
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public MapCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapCreatorRest; }
	}

	public final MapCreatorRestContext mapCreatorRest() throws RecognitionException {
		MapCreatorRestContext _localctx = new MapCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1042);
			match(LBRACE);
			setState(1043);
			mapCreatorRestPair();
			setState(1048);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1044);
				match(COMMA);
				setState(1045);
				mapCreatorRestPair();
				}
				}
				setState(1050);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1051);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MapCreatorRestPairContext extends ParserRuleContext {
		public IdOrExpressionContext idOrExpression() {
			return getRuleContext(IdOrExpressionContext.class,0);
		}
		public TerminalNode MAP() { return getToken(ApexParser.MAP, 0); }
		public LiteralOrExpressionContext literalOrExpression() {
			return getRuleContext(LiteralOrExpressionContext.class,0);
		}
		public MapCreatorRestPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapCreatorRestPair; }
	}

	public final MapCreatorRestPairContext mapCreatorRestPair() throws RecognitionException {
		MapCreatorRestPairContext _localctx = new MapCreatorRestPairContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1053);
			idOrExpression();
			setState(1054);
			match(MAP);
			setState(1055);
			literalOrExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetCreatorRestContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public List<LiteralOrExpressionContext> literalOrExpression() {
			return getRuleContexts(LiteralOrExpressionContext.class);
		}
		public LiteralOrExpressionContext literalOrExpression(int i) {
			return getRuleContext(LiteralOrExpressionContext.class,i);
		}
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public SetCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setCreatorRest; }
	}

	public final SetCreatorRestContext setCreatorRest() throws RecognitionException {
		SetCreatorRestContext _localctx = new SetCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1057);
			match(LBRACE);
			setState(1058);
			literalOrExpression();
			setState(1063);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1059);
				match(COMMA);
				{
				setState(1060);
				literalOrExpression();
				}
				}
				}
				setState(1065);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1066);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralOrExpressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LiteralOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalOrExpression; }
	}

	public final LiteralOrExpressionContext literalOrExpression() throws RecognitionException {
		LiteralOrExpressionContext _localctx = new LiteralOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_literalOrExpression);
		try {
			setState(1070);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1068);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1069);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdOrExpressionContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOrExpression; }
	}

	public final IdOrExpressionContext idOrExpression() throws RecognitionException {
		IdOrExpressionContext _localctx = new IdOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_idOrExpression);
		try {
			setState(1074);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1072);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1073);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassCreatorRestContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ClassCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classCreatorRest; }
	}

	public final ClassCreatorRestContext classCreatorRest() throws RecognitionException {
		ClassCreatorRestContext _localctx = new ClassCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_classCreatorRest);
		int _la;
		try {
			setState(1082);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1076);
				arguments();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1077);
				match(LBRACE);
				setState(1079);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1078);
					expressionList();
					}
				}

				setState(1081);
				match(RBRACE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitGenericInvocationContext extends ParserRuleContext {
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public ExplicitGenericInvocationSuffixContext explicitGenericInvocationSuffix() {
			return getRuleContext(ExplicitGenericInvocationSuffixContext.class,0);
		}
		public ExplicitGenericInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitGenericInvocation; }
	}

	public final ExplicitGenericInvocationContext explicitGenericInvocation() throws RecognitionException {
		ExplicitGenericInvocationContext _localctx = new ExplicitGenericInvocationContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_explicitGenericInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1084);
			nonWildcardTypeArguments();
			setState(1085);
			explicitGenericInvocationSuffix();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonWildcardTypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public NonWildcardTypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonWildcardTypeArguments; }
	}

	public final NonWildcardTypeArgumentsContext nonWildcardTypeArguments() throws RecognitionException {
		NonWildcardTypeArgumentsContext _localctx = new NonWildcardTypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1087);
			match(LT);
			setState(1088);
			typeList();
			setState(1089);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsOrDiamondContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArgumentsOrDiamond; }
	}

	public final TypeArgumentsOrDiamondContext typeArgumentsOrDiamond() throws RecognitionException {
		TypeArgumentsOrDiamondContext _localctx = new TypeArgumentsOrDiamondContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_typeArgumentsOrDiamond);
		try {
			setState(1094);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,111,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1091);
				match(LT);
				setState(1092);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1093);
				typeArguments();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonWildcardTypeArgumentsOrDiamondContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public NonWildcardTypeArgumentsContext nonWildcardTypeArguments() {
			return getRuleContext(NonWildcardTypeArgumentsContext.class,0);
		}
		public NonWildcardTypeArgumentsOrDiamondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonWildcardTypeArgumentsOrDiamond; }
	}

	public final NonWildcardTypeArgumentsOrDiamondContext nonWildcardTypeArgumentsOrDiamond() throws RecognitionException {
		NonWildcardTypeArgumentsOrDiamondContext _localctx = new NonWildcardTypeArgumentsOrDiamondContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1099);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1096);
				match(LT);
				setState(1097);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1098);
				nonWildcardTypeArguments();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SuperSuffixContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public SuperSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_superSuffix; }
	}

	public final SuperSuffixContext superSuffix() throws RecognitionException {
		SuperSuffixContext _localctx = new SuperSuffixContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_superSuffix);
		try {
			setState(1107);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1101);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1102);
				match(DOT);
				setState(1103);
				id();
				setState(1105);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
				case 1:
					{
					setState(1104);
					arguments();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitGenericInvocationSuffixContext extends ParserRuleContext {
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public SuperSuffixContext superSuffix() {
			return getRuleContext(SuperSuffixContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ExplicitGenericInvocationSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitGenericInvocationSuffix; }
	}

	public final ExplicitGenericInvocationSuffixContext explicitGenericInvocationSuffix() throws RecognitionException {
		ExplicitGenericInvocationSuffixContext _localctx = new ExplicitGenericInvocationSuffixContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_explicitGenericInvocationSuffix);
		try {
			setState(1114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1109);
				match(SUPER);
				setState(1110);
				superSuffix();
				}
				break;
			case BOOLEAN:
			case DOUBLE:
			case FOR:
			case LONG:
			case NEW:
			case SELECT:
			case INSERT:
			case UPSERT:
			case UPDATE:
			case DELETE:
			case UNDELETE:
			case MERGE:
			case GET:
			case SET:
			case BLOB:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case ID:
			case INTEGER:
			case OBJECT:
			case STRING:
			case TIME:
			case WITHSHARING:
			case WITHOUTSHARING:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1111);
				id();
				setState(1112);
				arguments();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1116);
			match(LPAREN);
			setState(1118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1117);
				expressionList();
				}
			}

			setState(1120);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SoqlLiteralContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(ApexParser.LBRACK, 0); }
		public List<TerminalNode> RBRACK() { return getTokens(ApexParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(ApexParser.RBRACK, i);
		}
		public List<SoqlLiteralContext> soqlLiteral() {
			return getRuleContexts(SoqlLiteralContext.class);
		}
		public SoqlLiteralContext soqlLiteral(int i) {
			return getRuleContext(SoqlLiteralContext.class,i);
		}
		public SoqlLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_soqlLiteral; }
	}

	public final SoqlLiteralContext soqlLiteral() throws RecognitionException {
		SoqlLiteralContext _localctx = new SoqlLiteralContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1122);
			match(LBRACK);
			setState(1127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,118,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1125);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,117,_ctx) ) {
					case 1:
						{
						setState(1123);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1124);
						_la = _input.LA(1);
						if ( _la <= 0 || (_la==RBRACK) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(1129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,118,_ctx);
			}
			setState(1130);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithSharingContext extends ParserRuleContext {
		public TerminalNode WITHSHARING() { return getToken(ApexParser.WITHSHARING, 0); }
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public WithSharingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withSharing; }
	}

	public final WithSharingContext withSharing() throws RecognitionException {
		WithSharingContext _localctx = new WithSharingContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_withSharing);
		try {
			setState(1135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1132);
				match(WITHSHARING);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 2);
				{
				setState(1133);
				match(WITH);
				setState(1134);
				match(SHARING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithoutSharingContext extends ParserRuleContext {
		public TerminalNode WITHOUTSHARING() { return getToken(ApexParser.WITHOUTSHARING, 0); }
		public TerminalNode WITHOUT() { return getToken(ApexParser.WITHOUT, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public WithoutSharingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withoutSharing; }
	}

	public final WithoutSharingContext withoutSharing() throws RecognitionException {
		WithoutSharingContext _localctx = new WithoutSharingContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_withoutSharing);
		try {
			setState(1140);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHOUTSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1137);
				match(WITHOUTSHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1138);
				match(WITHOUT);
				setState(1139);
				match(SHARING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ApexParser.Identifier, 0); }
		public TerminalNode GET() { return getToken(ApexParser.GET, 0); }
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode BLOB() { return getToken(ApexParser.BLOB, 0); }
		public TerminalNode BOOLEAN() { return getToken(ApexParser.BOOLEAN, 0); }
		public TerminalNode DATE() { return getToken(ApexParser.DATE, 0); }
		public TerminalNode DATETIME() { return getToken(ApexParser.DATETIME, 0); }
		public TerminalNode DECIMAL() { return getToken(ApexParser.DECIMAL, 0); }
		public TerminalNode DOUBLE() { return getToken(ApexParser.DOUBLE, 0); }
		public TerminalNode ID() { return getToken(ApexParser.ID, 0); }
		public TerminalNode INTEGER() { return getToken(ApexParser.INTEGER, 0); }
		public TerminalNode LONG() { return getToken(ApexParser.LONG, 0); }
		public TerminalNode OBJECT() { return getToken(ApexParser.OBJECT, 0); }
		public TerminalNode STRING() { return getToken(ApexParser.STRING, 0); }
		public TerminalNode TIME() { return getToken(ApexParser.TIME, 0); }
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public TerminalNode UPSERT() { return getToken(ApexParser.UPSERT, 0); }
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public TerminalNode DELETE() { return getToken(ApexParser.DELETE, 0); }
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public TerminalNode WITHSHARING() { return getToken(ApexParser.WITHSHARING, 0); }
		public TerminalNode WITHOUTSHARING() { return getToken(ApexParser.WITHOUTSHARING, 0); }
		public TerminalNode FOR() { return getToken(ApexParser.FOR, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1142);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 81:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		case 6:
			return precpred(_ctx, 7);
		case 7:
			return precpred(_ctx, 6);
		case 8:
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 4);
		case 10:
			return precpred(_ctx, 3);
		case 11:
			return precpred(_ctx, 2);
		case 12:
			return precpred(_ctx, 26);
		case 13:
			return precpred(_ctx, 25);
		case 14:
			return precpred(_ctx, 24);
		case 15:
			return precpred(_ctx, 23);
		case 16:
			return precpred(_ctx, 22);
		case 17:
			return precpred(_ctx, 21);
		case 18:
			return precpred(_ctx, 20);
		case 19:
			return precpred(_ctx, 17);
		case 20:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0085\u047b\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\3\2\7"+
		"\2\u00d6\n\2\f\2\16\2\u00d9\13\2\3\2\3\2\3\3\7\3\u00de\n\3\f\3\16\3\u00e1"+
		"\13\3\3\3\3\3\7\3\u00e5\n\3\f\3\16\3\u00e8\13\3\3\3\3\3\7\3\u00ec\n\3"+
		"\f\3\16\3\u00ef\13\3\3\3\3\3\5\3\u00f3\n\3\3\4\3\4\5\4\u00f7\n\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0107\n\5\5\5\u0109"+
		"\n\5\3\6\3\6\3\6\5\6\u010e\n\6\3\7\3\7\3\7\3\7\5\7\u0114\n\7\3\7\3\7\5"+
		"\7\u0118\n\7\3\7\3\7\3\b\3\b\3\b\3\b\5\b\u0120\n\b\3\b\3\b\5\b\u0124\n"+
		"\b\3\b\5\b\u0127\n\b\3\b\5\b\u012a\n\b\3\b\3\b\3\t\3\t\3\t\7\t\u0131\n"+
		"\t\f\t\16\t\u0134\13\t\3\n\7\n\u0137\n\n\f\n\16\n\u013a\13\n\3\n\3\n\5"+
		"\n\u013e\n\n\3\n\5\n\u0141\n\n\3\13\3\13\7\13\u0145\n\13\f\13\16\13\u0148"+
		"\13\13\3\f\3\f\3\f\3\f\5\f\u014e\n\f\3\f\3\f\3\r\3\r\3\r\7\r\u0155\n\r"+
		"\f\r\16\r\u0158\13\r\3\16\3\16\7\16\u015c\n\16\f\16\16\16\u015f\13\16"+
		"\3\16\3\16\3\17\3\17\7\17\u0165\n\17\f\17\16\17\u0168\13\17\3\17\3\17"+
		"\3\20\3\20\5\20\u016e\n\20\3\20\3\20\7\20\u0172\n\20\f\20\16\20\u0175"+
		"\13\20\3\20\5\20\u0178\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0181"+
		"\n\21\3\22\3\22\3\23\5\23\u0186\n\23\3\23\7\23\u0189\n\23\f\23\16\23\u018c"+
		"\13\23\3\23\3\23\5\23\u0190\n\23\3\23\3\23\3\23\3\23\5\23\u0196\n\23\3"+
		"\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3"+
		"\27\5\27\u01a7\n\27\3\27\3\27\3\30\7\30\u01ac\n\30\f\30\16\30\u01af\13"+
		"\30\3\30\3\30\5\30\u01b3\n\30\3\31\3\31\3\31\3\31\3\31\5\31\u01ba\n\31"+
		"\3\32\3\32\3\32\3\32\7\32\u01c0\n\32\f\32\16\32\u01c3\13\32\3\32\3\32"+
		"\3\33\3\33\3\33\7\33\u01ca\n\33\f\33\16\33\u01cd\13\33\3\33\3\33\3\33"+
		"\3\34\3\34\5\34\u01d4\n\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\7\35\u01dd"+
		"\n\35\f\35\16\35\u01e0\13\35\3\36\3\36\3\36\5\36\u01e5\n\36\3\37\3\37"+
		"\5\37\u01e9\n\37\3 \3 \3 \3 \7 \u01ef\n \f \16 \u01f2\13 \3 \5 \u01f5"+
		"\n \5 \u01f7\n \3 \3 \3!\3!\3!\3!\3!\3!\5!\u0201\n!\3\"\3\"\7\"\u0205"+
		"\n\"\f\"\16\"\u0208\13\"\3#\3#\5#\u020c\n#\3#\3#\3#\5#\u0211\n#\7#\u0213"+
		"\n#\f#\16#\u0216\13#\3$\3$\3%\3%\3%\3%\3&\3&\5&\u0220\n&\3&\3&\3\'\3\'"+
		"\3\'\7\'\u0227\n\'\f\'\16\'\u022a\13\'\3(\7(\u022d\n(\f(\16(\u0230\13"+
		"(\3(\3(\3(\3)\3)\3)\7)\u0238\n)\f)\16)\u023b\13)\3*\3*\3+\3+\3+\3+\3+"+
		"\5+\u0244\n+\3+\5+\u0247\n+\3,\3,\5,\u024b\n,\3,\7,\u024e\n,\f,\16,\u0251"+
		"\13,\3-\3-\3-\3-\3.\3.\3.\5.\u025a\n.\3/\3/\3/\3/\7/\u0260\n/\f/\16/\u0263"+
		"\13/\5/\u0265\n/\3/\5/\u0268\n/\3/\3/\3\60\3\60\7\60\u026e\n\60\f\60\16"+
		"\60\u0271\13\60\3\60\3\60\3\61\3\61\3\61\3\62\7\62\u0279\n\62\f\62\16"+
		"\62\u027c\13\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\5\63"+
		"\u0296\n\63\3\64\3\64\3\64\3\64\3\64\5\64\u029d\n\64\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38"+
		"\38\68\u02b2\n8\r8\168\u02b3\38\58\u02b7\n8\38\58\u02ba\n8\39\39\59\u02be"+
		"\n9\39\39\3:\3:\3:\3:\3;\3;\5;\u02c8\n;\3;\3;\3<\3<\5<\u02ce\n<\3<\3<"+
		"\3=\3=\3=\3=\3>\3>\3>\3>\3?\3?\3?\3?\3@\3@\3@\3@\3A\3A\3A\5A\u02e5\nA"+
		"\3A\3A\3B\3B\3B\3B\3B\3C\3C\3C\5C\u02f1\nC\3C\3C\5C\u02f5\nC\3D\3D\3E"+
		"\3E\3E\3F\3F\3F\3F\3G\7G\u0301\nG\fG\16G\u0304\13G\3G\3G\5G\u0308\nG\3"+
		"H\3H\3H\5H\u030d\nH\3I\3I\3I\5I\u0312\nI\3J\3J\3J\7J\u0317\nJ\fJ\16J\u031a"+
		"\13J\3J\3J\3J\3J\3J\3K\3K\3K\7K\u0324\nK\fK\16K\u0327\13K\3L\3L\3L\3M"+
		"\3M\5M\u032e\nM\3M\3M\5M\u0332\nM\3M\3M\5M\u0336\nM\5M\u0338\nM\3N\3N"+
		"\5N\u033c\nN\3O\7O\u033f\nO\fO\16O\u0342\13O\3O\3O\3O\3O\3O\3P\3P\3Q\3"+
		"Q\3Q\3Q\3R\3R\3R\7R\u0352\nR\fR\16R\u0355\13R\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\5S\u0364\nS\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\5S\u0374\nS\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\5S\u0380\nS\3S\3S\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\5S\u03a8\nS\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\3S\3S\5S\u03ba\nS\3S\3S\3S\3S\3S\3S\7S\u03c2\nS\fS\16"+
		"S\u03c5\13S\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5"+
		"T\u03da\nT\3T\5T\u03dd\nT\3U\3U\3U\3U\3U\3U\3U\3U\3U\5U\u03e8\nU\5U\u03ea"+
		"\nU\3V\3V\3V\7V\u03ef\nV\fV\16V\u03f2\13V\3V\5V\u03f5\nV\3W\3W\5W\u03f9"+
		"\nW\3X\3X\5X\u03fd\nX\3X\3X\3Y\3Y\3Y\3Y\3Y\3Y\3Y\3Y\3Y\3Y\3Y\7Y\u040c"+
		"\nY\fY\16Y\u040f\13Y\3Y\3Y\5Y\u0413\nY\3Z\3Z\3Z\3Z\7Z\u0419\nZ\fZ\16Z"+
		"\u041c\13Z\3Z\3Z\3[\3[\3[\3[\3\\\3\\\3\\\3\\\7\\\u0428\n\\\f\\\16\\\u042b"+
		"\13\\\3\\\3\\\3]\3]\5]\u0431\n]\3^\3^\5^\u0435\n^\3_\3_\3_\5_\u043a\n"+
		"_\3_\5_\u043d\n_\3`\3`\3`\3a\3a\3a\3a\3b\3b\3b\5b\u0449\nb\3c\3c\3c\5"+
		"c\u044e\nc\3d\3d\3d\3d\5d\u0454\nd\5d\u0456\nd\3e\3e\3e\3e\3e\5e\u045d"+
		"\ne\3f\3f\5f\u0461\nf\3f\3f\3g\3g\3g\7g\u0468\ng\fg\16g\u046b\13g\3g\3"+
		"g\3h\3h\3h\5h\u0472\nh\3i\3i\3i\5i\u0477\ni\3j\3j\3j\3\u0469\3\u00a4k"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH"+
		"JLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c"+
		"\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4"+
		"\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc"+
		"\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\2\17"+
		"\5\2\34\34\'\'**\6\2\37!$%/\6089\6\2\4\4\16\16\33\33<D\3\2KO\3\2il\3\2"+
		"^_\4\2mnrr\3\2kl\3\2bf\4\2YYt~\3\2ij\3\2UU\13\2\4\4\16\16\25\25\33\33"+
		"\35\35\61\67:DIJ\177\177\2\u04da\2\u00d7\3\2\2\2\4\u00f2\3\2\2\2\6\u00f6"+
		"\3\2\2\2\b\u0108\3\2\2\2\n\u010d\3\2\2\2\f\u010f\3\2\2\2\16\u011b\3\2"+
		"\2\2\20\u012d\3\2\2\2\22\u0138\3\2\2\2\24\u0142\3\2\2\2\26\u0149\3\2\2"+
		"\2\30\u0151\3\2\2\2\32\u0159\3\2\2\2\34\u0162\3\2\2\2\36\u0177\3\2\2\2"+
		" \u0180\3\2\2\2\"\u0182\3\2\2\2$\u0185\3\2\2\2&\u0197\3\2\2\2(\u019b\3"+
		"\2\2\2*\u019f\3\2\2\2,\u01a3\3\2\2\2.\u01b2\3\2\2\2\60\u01b9\3\2\2\2\62"+
		"\u01bb\3\2\2\2\64\u01c6\3\2\2\2\66\u01d3\3\2\2\28\u01d9\3\2\2\2:\u01e1"+
		"\3\2\2\2<\u01e8\3\2\2\2>\u01ea\3\2\2\2@\u0200\3\2\2\2B\u0206\3\2\2\2D"+
		"\u0209\3\2\2\2F\u0217\3\2\2\2H\u0219\3\2\2\2J\u021d\3\2\2\2L\u0223\3\2"+
		"\2\2N\u022e\3\2\2\2P\u0234\3\2\2\2R\u023c\3\2\2\2T\u023e\3\2\2\2V\u0248"+
		"\3\2\2\2X\u0252\3\2\2\2Z\u0259\3\2\2\2\\\u025b\3\2\2\2^\u026b\3\2\2\2"+
		"`\u0274\3\2\2\2b\u027a\3\2\2\2d\u0295\3\2\2\2f\u0297\3\2\2\2h\u029e\3"+
		"\2\2\2j\u02a4\3\2\2\2l\u02a8\3\2\2\2n\u02ae\3\2\2\2p\u02bb\3\2\2\2r\u02c1"+
		"\3\2\2\2t\u02c5\3\2\2\2v\u02cb\3\2\2\2x\u02d1\3\2\2\2z\u02d5\3\2\2\2|"+
		"\u02d9\3\2\2\2~\u02dd\3\2\2\2\u0080\u02e1\3\2\2\2\u0082\u02e8\3\2\2\2"+
		"\u0084\u02ed\3\2\2\2\u0086\u02f6\3\2\2\2\u0088\u02f8\3\2\2\2\u008a\u02fb"+
		"\3\2\2\2\u008c\u0302\3\2\2\2\u008e\u0309\3\2\2\2\u0090\u030e\3\2\2\2\u0092"+
		"\u0313\3\2\2\2\u0094\u0320\3\2\2\2\u0096\u0328\3\2\2\2\u0098\u0337\3\2"+
		"\2\2\u009a\u033b\3\2\2\2\u009c\u0340\3\2\2\2\u009e\u0348\3\2\2\2\u00a0"+
		"\u034a\3\2\2\2\u00a2\u034e\3\2\2\2\u00a4\u0363\3\2\2\2\u00a6\u03dc\3\2"+
		"\2\2\u00a8\u03e9\3\2\2\2\u00aa\u03f4\3\2\2\2\u00ac\u03f6\3\2\2\2\u00ae"+
		"\u03fa\3\2\2\2\u00b0\u0400\3\2\2\2\u00b2\u0414\3\2\2\2\u00b4\u041f\3\2"+
		"\2\2\u00b6\u0423\3\2\2\2\u00b8\u0430\3\2\2\2\u00ba\u0434\3\2\2\2\u00bc"+
		"\u043c\3\2\2\2\u00be\u043e\3\2\2\2\u00c0\u0441\3\2\2\2\u00c2\u0448\3\2"+
		"\2\2\u00c4\u044d\3\2\2\2\u00c6\u0455\3\2\2\2\u00c8\u045c\3\2\2\2\u00ca"+
		"\u045e\3\2\2\2\u00cc\u0464\3\2\2\2\u00ce\u0471\3\2\2\2\u00d0\u0476\3\2"+
		"\2\2\u00d2\u0478\3\2\2\2\u00d4\u00d6\5\4\3\2\u00d5\u00d4\3\2\2\2\u00d6"+
		"\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da\3\2"+
		"\2\2\u00d9\u00d7\3\2\2\2\u00da\u00db\7\2\2\3\u00db\3\3\2\2\2\u00dc\u00de"+
		"\5\b\5\2\u00dd\u00dc\3\2\2\2\u00de\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u00e2\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00f3\5\f"+
		"\7\2\u00e3\u00e5\5\b\5\2\u00e4\u00e3\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e9\3\2\2\2\u00e8\u00e6\3\2"+
		"\2\2\u00e9\u00f3\5\16\b\2\u00ea\u00ec\5\b\5\2\u00eb\u00ea\3\2\2\2\u00ec"+
		"\u00ef\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00f0\3\2"+
		"\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f3\5\26\f\2\u00f1\u00f3\7V\2\2\u00f2"+
		"\u00df\3\2\2\2\u00f2\u00e6\3\2\2\2\u00f2\u00ed\3\2\2\2\u00f2\u00f1\3\2"+
		"\2\2\u00f3\5\3\2\2\2\u00f4\u00f7\5\b\5\2\u00f5\u00f7\t\2\2\2\u00f6\u00f4"+
		"\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\7\3\2\2\2\u00f8\u0109\5T+\2\u00f9\u0107"+
		"\7!\2\2\u00fa\u0107\7 \2\2\u00fb\u0107\7\37\2\2\u00fc\u0107\7$\2\2\u00fd"+
		"\u0107\7\3\2\2\u00fe\u0107\7\22\2\2\u00ff\u0107\7/\2\2\u0100\u0107\7\60"+
		"\2\2\u0101\u0107\79\2\2\u0102\u0107\7%\2\2\u0103\u0107\78\2\2\u0104\u0107"+
		"\5\u00ceh\2\u0105\u0107\5\u00d0i\2\u0106\u00f9\3\2\2\2\u0106\u00fa\3\2"+
		"\2\2\u0106\u00fb\3\2\2\2\u0106\u00fc\3\2\2\2\u0106\u00fd\3\2\2\2\u0106"+
		"\u00fe\3\2\2\2\u0106\u00ff\3\2\2\2\u0106\u0100\3\2\2\2\u0106\u0101\3\2"+
		"\2\2\u0106\u0102\3\2\2\2\u0106\u0103\3\2\2\2\u0106\u0104\3\2\2\2\u0106"+
		"\u0105\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u00f8\3\2\2\2\u0108\u0106\3\2"+
		"\2\2\u0109\t\3\2\2\2\u010a\u010e\7\22\2\2\u010b\u010e\7*\2\2\u010c\u010e"+
		"\5T+\2\u010d\u010a\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010c\3\2\2\2\u010e"+
		"\13\3\2\2\2\u010f\u0110\7\t\2\2\u0110\u0113\5\u00d2j\2\u0111\u0112\7\21"+
		"\2\2\u0112\u0114\5@!\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0117"+
		"\3\2\2\2\u0115\u0116\7\30\2\2\u0116\u0118\5\30\r\2\u0117\u0115\3\2\2\2"+
		"\u0117\u0118\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011a\5\32\16\2\u011a\r"+
		"\3\2\2\2\u011b\u011c\7\20\2\2\u011c\u011f\5\u00d2j\2\u011d\u011e\7\30"+
		"\2\2\u011e\u0120\5\30\r\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120"+
		"\u0121\3\2\2\2\u0121\u0123\7R\2\2\u0122\u0124\5\20\t\2\u0123\u0122\3\2"+
		"\2\2\u0123\u0124\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u0127\7W\2\2\u0126"+
		"\u0125\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129\3\2\2\2\u0128\u012a\5\24"+
		"\13\2\u0129\u0128\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\7S\2\2\u012c\17\3\2\2\2\u012d\u0132\5\22\n\2\u012e\u012f\7W\2\2"+
		"\u012f\u0131\5\22\n\2\u0130\u012e\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130"+
		"\3\2\2\2\u0132\u0133\3\2\2\2\u0133\21\3\2\2\2\u0134\u0132\3\2\2\2\u0135"+
		"\u0137\5T+\2\u0136\u0135\3\2\2\2\u0137\u013a\3\2\2\2\u0138\u0136\3\2\2"+
		"\2\u0138\u0139\3\2\2\2\u0139\u013b\3\2\2\2\u013a\u0138\3\2\2\2\u013b\u013d"+
		"\5\u00d2j\2\u013c\u013e\5\u00caf\2\u013d\u013c\3\2\2\2\u013d\u013e\3\2"+
		"\2\2\u013e\u0140\3\2\2\2\u013f\u0141\5\32\16\2\u0140\u013f\3\2\2\2\u0140"+
		"\u0141\3\2\2\2\u0141\23\3\2\2\2\u0142\u0146\7V\2\2\u0143\u0145\5\36\20"+
		"\2\u0144\u0143\3\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147"+
		"\3\2\2\2\u0147\25\3\2\2\2\u0148\u0146\3\2\2\2\u0149\u014a\7\32\2\2\u014a"+
		"\u014d\5\u00d2j\2\u014b\u014c\7\21\2\2\u014c\u014e\5\30\r\2\u014d\u014b"+
		"\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\5\34\17\2"+
		"\u0150\27\3\2\2\2\u0151\u0156\5@!\2\u0152\u0153\7W\2\2\u0153\u0155\5@"+
		"!\2\u0154\u0152\3\2\2\2\u0155\u0158\3\2\2\2\u0156\u0154\3\2\2\2\u0156"+
		"\u0157\3\2\2\2\u0157\31\3\2\2\2\u0158\u0156\3\2\2\2\u0159\u015d\7R\2\2"+
		"\u015a\u015c\5\36\20\2\u015b\u015a\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b"+
		"\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u0160\3\2\2\2\u015f\u015d\3\2\2\2\u0160"+
		"\u0161\7S\2\2\u0161\33\3\2\2\2\u0162\u0166\7R\2\2\u0163\u0165\5.\30\2"+
		"\u0164\u0163\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0167"+
		"\3\2\2\2\u0167\u0169\3\2\2\2\u0168\u0166\3\2\2\2\u0169\u016a\7S\2\2\u016a"+
		"\35\3\2\2\2\u016b\u0178\7V\2\2\u016c\u016e\7$\2\2\u016d\u016c\3\2\2\2"+
		"\u016d\u016e\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u0178\5^\60\2\u0170\u0172"+
		"\5\6\4\2\u0171\u0170\3\2\2\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0173"+
		"\u0174\3\2\2\2\u0174\u0176\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u0178\5 "+
		"\21\2\u0177\u016b\3\2\2\2\u0177\u016d\3\2\2\2\u0177\u0173\3\2\2\2\u0178"+
		"\37\3\2\2\2\u0179\u0181\5$\23\2\u017a\u0181\5(\25\2\u017b\u0181\5&\24"+
		"\2\u017c\u0181\5\26\f\2\u017d\u0181\5\f\7\2\u017e\u0181\5\16\b\2\u017f"+
		"\u0181\5*\26\2\u0180\u0179\3\2\2\2\u0180\u017a\3\2\2\2\u0180\u017b\3\2"+
		"\2\2\u0180\u017c\3\2\2\2\u0180\u017d\3\2\2\2\u0180\u017e\3\2\2\2\u0180"+
		"\u017f\3\2\2\2\u0181!\3\2\2\2\u0182\u0183\t\3\2\2\u0183#\3\2\2\2\u0184"+
		"\u0186\5T+\2\u0185\u0184\3\2\2\2\u0185\u0186\3\2\2\2\u0186\u018a\3\2\2"+
		"\2\u0187\u0189\5\"\22\2\u0188\u0187\3\2\2\2\u0189\u018c\3\2\2\2\u018a"+
		"\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018b\u018f\3\2\2\2\u018c\u018a\3\2"+
		"\2\2\u018d\u0190\5@!\2\u018e\u0190\7,\2\2\u018f\u018d\3\2\2\2\u018f\u018e"+
		"\3\2\2\2\u0190\u0191\3\2\2\2\u0191\u0192\5\u00d2j\2\u0192\u0195\5J&\2"+
		"\u0193\u0196\5^\60\2\u0194\u0196\7V\2\2\u0195\u0193\3\2\2\2\u0195\u0194"+
		"\3\2\2\2\u0196%\3\2\2\2\u0197\u0198\5P)\2\u0198\u0199\5J&\2\u0199\u019a"+
		"\5^\60\2\u019a\'\3\2\2\2\u019b\u019c\5@!\2\u019c\u019d\58\35\2\u019d\u019e"+
		"\7V\2\2\u019e)\3\2\2\2\u019f\u01a0\5@!\2\u01a0\u01a1\58\35\2\u01a1\u01a2"+
		"\5,\27\2\u01a2+\3\2\2\2\u01a3\u01a4\7R\2\2\u01a4\u01a6\5\u008cG\2\u01a5"+
		"\u01a7\5\u008cG\2\u01a6\u01a5\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01a8"+
		"\3\2\2\2\u01a8\u01a9\7S\2\2\u01a9-\3\2\2\2\u01aa\u01ac\5\6\4\2\u01ab\u01aa"+
		"\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae"+
		"\u01b0\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b3\5\60\31\2\u01b1\u01b3\7"+
		"V\2\2\u01b2\u01ad\3\2\2\2\u01b2\u01b1\3\2\2\2\u01b3/\3\2\2\2\u01b4\u01ba"+
		"\5\62\32\2\u01b5\u01ba\5\66\34\2\u01b6\u01ba\5\26\f\2\u01b7\u01ba\5\f"+
		"\7\2\u01b8\u01ba\5\16\b\2\u01b9\u01b4\3\2\2\2\u01b9\u01b5\3\2\2\2\u01b9"+
		"\u01b6\3\2\2\2\u01b9\u01b7\3\2\2\2\u01b9\u01b8\3\2\2\2\u01ba\61\3\2\2"+
		"\2\u01bb\u01bc\5@!\2\u01bc\u01c1\5\64\33\2\u01bd\u01be\7W\2\2\u01be\u01c0"+
		"\5\64\33\2\u01bf\u01bd\3\2\2\2\u01c0\u01c3\3\2\2\2\u01c1\u01bf\3\2\2\2"+
		"\u01c1\u01c2\3\2\2\2\u01c2\u01c4\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c4\u01c5"+
		"\7V\2\2\u01c5\63\3\2\2\2\u01c6\u01cb\5\u00d2j\2\u01c7\u01c8\7T\2\2\u01c8"+
		"\u01ca\7U\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01cd\3\2\2\2\u01cb\u01c9\3\2"+
		"\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01ce\3\2\2\2\u01cd\u01cb\3\2\2\2\u01ce"+
		"\u01cf\7Y\2\2\u01cf\u01d0\5<\37\2\u01d0\65\3\2\2\2\u01d1\u01d4\5@!\2\u01d2"+
		"\u01d4\7,\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d2\3\2\2\2\u01d4\u01d5\3\2"+
		"\2\2\u01d5\u01d6\5\u00d2j\2\u01d6\u01d7\5J&\2\u01d7\u01d8\7V\2\2\u01d8"+
		"\67\3\2\2\2\u01d9\u01de\5:\36\2\u01da\u01db\7W\2\2\u01db\u01dd\5:\36\2"+
		"\u01dc\u01da\3\2\2\2\u01dd\u01e0\3\2\2\2\u01de\u01dc\3\2\2\2\u01de\u01df"+
		"\3\2\2\2\u01df9\3\2\2\2\u01e0\u01de\3\2\2\2\u01e1\u01e4\5\u00d2j\2\u01e2"+
		"\u01e3\7Y\2\2\u01e3\u01e5\5<\37\2\u01e4\u01e2\3\2\2\2\u01e4\u01e5\3\2"+
		"\2\2\u01e5;\3\2\2\2\u01e6\u01e9\5> \2\u01e7\u01e9\5\u00a4S\2\u01e8\u01e6"+
		"\3\2\2\2\u01e8\u01e7\3\2\2\2\u01e9=\3\2\2\2\u01ea\u01f6\7R\2\2\u01eb\u01f0"+
		"\5<\37\2\u01ec\u01ed\7W\2\2\u01ed\u01ef\5<\37\2\u01ee\u01ec\3\2\2\2\u01ef"+
		"\u01f2\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f4\3\2"+
		"\2\2\u01f2\u01f0\3\2\2\2\u01f3\u01f5\7W\2\2\u01f4\u01f3\3\2\2\2\u01f4"+
		"\u01f5\3\2\2\2\u01f5\u01f7\3\2\2\2\u01f6\u01eb\3\2\2\2\u01f6\u01f7\3\2"+
		"\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01f9\7S\2\2\u01f9?\3\2\2\2\u01fa\u01fb"+
		"\5D#\2\u01fb\u01fc\5B\"\2\u01fc\u0201\3\2\2\2\u01fd\u01fe\5F$\2\u01fe"+
		"\u01ff\5B\"\2\u01ff\u0201\3\2\2\2\u0200\u01fa\3\2\2\2\u0200\u01fd\3\2"+
		"\2\2\u0201A\3\2\2\2\u0202\u0203\7T\2\2\u0203\u0205\7U\2\2\u0204\u0202"+
		"\3\2\2\2\u0205\u0208\3\2\2\2\u0206\u0204\3\2\2\2\u0206\u0207\3\2\2\2\u0207"+
		"C\3\2\2\2\u0208\u0206\3\2\2\2\u0209\u020b\5\u00d2j\2\u020a\u020c\5H%\2"+
		"\u020b\u020a\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u0214\3\2\2\2\u020d\u020e"+
		"\7X\2\2\u020e\u0210\5\u00d2j\2\u020f\u0211\5H%\2\u0210\u020f\3\2\2\2\u0210"+
		"\u0211\3\2\2\2\u0211\u0213\3\2\2\2\u0212\u020d\3\2\2\2\u0213\u0216\3\2"+
		"\2\2\u0214\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u0215E\3\2\2\2\u0216\u0214"+
		"\3\2\2\2\u0217\u0218\t\4\2\2\u0218G\3\2\2\2\u0219\u021a\7]\2\2\u021a\u021b"+
		"\5\30\r\2\u021b\u021c\7\\\2\2\u021cI\3\2\2\2\u021d\u021f\7P\2\2\u021e"+
		"\u0220\5L\'\2\u021f\u021e\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0221\3\2"+
		"\2\2\u0221\u0222\7Q\2\2\u0222K\3\2\2\2\u0223\u0228\5N(\2\u0224\u0225\7"+
		"W\2\2\u0225\u0227\5N(\2\u0226\u0224\3\2\2\2\u0227\u022a\3\2\2\2\u0228"+
		"\u0226\3\2\2\2\u0228\u0229\3\2\2\2\u0229M\3\2\2\2\u022a\u0228\3\2\2\2"+
		"\u022b\u022d\5\n\6\2\u022c\u022b\3\2\2\2\u022d\u0230\3\2\2\2\u022e\u022c"+
		"\3\2\2\2\u022e\u022f\3\2\2\2\u022f\u0231\3\2\2\2\u0230\u022e\3\2\2\2\u0231"+
		"\u0232\5@!\2\u0232\u0233\5\u00d2j\2\u0233O\3\2\2\2\u0234\u0239\5\u00d2"+
		"j\2\u0235\u0236\7X\2\2\u0236\u0238\5\u00d2j\2\u0237\u0235\3\2\2\2\u0238"+
		"\u023b\3\2\2\2\u0239\u0237\3\2\2\2\u0239\u023a\3\2\2\2\u023aQ\3\2\2\2"+
		"\u023b\u0239\3\2\2\2\u023c\u023d\t\5\2\2\u023dS\3\2\2\2\u023e\u023f\7"+
		"\u0080\2\2\u023f\u0246\5P)\2\u0240\u0243\7P\2\2\u0241\u0244\5V,\2\u0242"+
		"\u0244\5Z.\2\u0243\u0241\3\2\2\2\u0243\u0242\3\2\2\2\u0243\u0244\3\2\2"+
		"\2\u0244\u0245\3\2\2\2\u0245\u0247\7Q\2\2\u0246\u0240\3\2\2\2\u0246\u0247"+
		"\3\2\2\2\u0247U\3\2\2\2\u0248\u024f\5X-\2\u0249\u024b\7W\2\2\u024a\u0249"+
		"\3\2\2\2\u024a\u024b\3\2\2\2\u024b\u024c\3\2\2\2\u024c\u024e\5X-\2\u024d"+
		"\u024a\3\2\2\2\u024e\u0251\3\2\2\2\u024f\u024d\3\2\2\2\u024f\u0250\3\2"+
		"\2\2\u0250W\3\2\2\2\u0251\u024f\3\2\2\2\u0252\u0253\5\u00d2j\2\u0253\u0254"+
		"\7Y\2\2\u0254\u0255\5Z.\2\u0255Y\3\2\2\2\u0256\u025a\5\u00a4S\2\u0257"+
		"\u025a\5T+\2\u0258\u025a\5\\/\2\u0259\u0256\3\2\2\2\u0259\u0257\3\2\2"+
		"\2\u0259\u0258\3\2\2\2\u025a[\3\2\2\2\u025b\u0264\7R\2\2\u025c\u0261\5"+
		"Z.\2\u025d\u025e\7W\2\2\u025e\u0260\5Z.\2\u025f\u025d\3\2\2\2\u0260\u0263"+
		"\3\2\2\2\u0261\u025f\3\2\2\2\u0261\u0262\3\2\2\2\u0262\u0265\3\2\2\2\u0263"+
		"\u0261\3\2\2\2\u0264\u025c\3\2\2\2\u0264\u0265\3\2\2\2\u0265\u0267\3\2"+
		"\2\2\u0266\u0268\7W\2\2\u0267\u0266\3\2\2\2\u0267\u0268\3\2\2\2\u0268"+
		"\u0269\3\2\2\2\u0269\u026a\7S\2\2\u026a]\3\2\2\2\u026b\u026f\7R\2\2\u026c"+
		"\u026e\5d\63\2\u026d\u026c\3\2\2\2\u026e\u0271\3\2\2\2\u026f\u026d\3\2"+
		"\2\2\u026f\u0270\3\2\2\2\u0270\u0272\3\2\2\2\u0271\u026f\3\2\2\2\u0272"+
		"\u0273\7S\2\2\u0273_\3\2\2\2\u0274\u0275\5b\62\2\u0275\u0276\7V\2\2\u0276"+
		"a\3\2\2\2\u0277\u0279\5\n\6\2\u0278\u0277\3\2\2\2\u0279\u027c\3\2\2\2"+
		"\u027a\u0278\3\2\2\2\u027a\u027b\3\2\2\2\u027b\u027d\3\2\2\2\u027c\u027a"+
		"\3\2\2\2\u027d\u027e\5@!\2\u027e\u027f\58\35\2\u027fc\3\2\2\2\u0280\u0296"+
		"\5^\60\2\u0281\u0296\5`\61\2\u0282\u0296\5f\64\2\u0283\u0296\5h\65\2\u0284"+
		"\u0296\5j\66\2\u0285\u0296\5l\67\2\u0286\u0296\5n8\2\u0287\u0296\5p9\2"+
		"\u0288\u0296\5r:\2\u0289\u0296\5t;\2\u028a\u0296\5v<\2\u028b\u0296\5x"+
		"=\2\u028c\u0296\5z>\2\u028d\u0296\5|?\2\u028e\u0296\5~@\2\u028f\u0296"+
		"\5\u0080A\2\u0290\u0296\5\u0082B\2\u0291\u0296\5\u0084C\2\u0292\u0296"+
		"\5\u0086D\2\u0293\u0296\5\u0088E\2\u0294\u0296\5\u008aF\2\u0295\u0280"+
		"\3\2\2\2\u0295\u0281\3\2\2\2\u0295\u0282\3\2\2\2\u0295\u0283\3\2\2\2\u0295"+
		"\u0284\3\2\2\2\u0295\u0285\3\2\2\2\u0295\u0286\3\2\2\2\u0295\u0287\3\2"+
		"\2\2\u0295\u0288\3\2\2\2\u0295\u0289\3\2\2\2\u0295\u028a\3\2\2\2\u0295"+
		"\u028b\3\2\2\2\u0295\u028c\3\2\2\2\u0295\u028d\3\2\2\2\u0295\u028e\3\2"+
		"\2\2\u0295\u028f\3\2\2\2\u0295\u0290\3\2\2\2\u0295\u0291\3\2\2\2\u0295"+
		"\u0292\3\2\2\2\u0295\u0293\3\2\2\2\u0295\u0294\3\2\2\2\u0296e\3\2\2\2"+
		"\u0297\u0298\7\26\2\2\u0298\u0299\5\u00a0Q\2\u0299\u029c\5d\63\2\u029a"+
		"\u029b\7\17\2\2\u029b\u029d\5d\63\2\u029c\u029a\3\2\2\2\u029c\u029d\3"+
		"\2\2\2\u029dg\3\2\2\2\u029e\u029f\7\25\2\2\u029f\u02a0\7P\2\2\u02a0\u02a1"+
		"\5\u0098M\2\u02a1\u02a2\7Q\2\2\u02a2\u02a3\5d\63\2\u02a3i\3\2\2\2\u02a4"+
		"\u02a5\7.\2\2\u02a5\u02a6\5\u00a0Q\2\u02a6\u02a7\5d\63\2\u02a7k\3\2\2"+
		"\2\u02a8\u02a9\7\r\2\2\u02a9\u02aa\5d\63\2\u02aa\u02ab\7.\2\2\u02ab\u02ac"+
		"\5\u00a0Q\2\u02ac\u02ad\7V\2\2\u02adm\3\2\2\2\u02ae\u02af\7+\2\2\u02af"+
		"\u02b9\5^\60\2\u02b0\u02b2\5\u0092J\2\u02b1\u02b0\3\2\2\2\u02b2\u02b3"+
		"\3\2\2\2\u02b3\u02b1\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b6\3\2\2\2\u02b5"+
		"\u02b7\5\u0096L\2\u02b6\u02b5\3\2\2\2\u02b6\u02b7\3\2\2\2\u02b7\u02ba"+
		"\3\2\2\2\u02b8\u02ba\5\u0096L\2\u02b9\u02b1\3\2\2\2\u02b9\u02b8\3\2\2"+
		"\2\u02bao\3\2\2\2\u02bb\u02bd\7\"\2\2\u02bc\u02be\5\u00a4S\2\u02bd\u02bc"+
		"\3\2\2\2\u02bd\u02be\3\2\2\2\u02be\u02bf\3\2\2\2\u02bf\u02c0\7V\2\2\u02c0"+
		"q\3\2\2\2\u02c1\u02c2\7)\2\2\u02c2\u02c3\5\u00a4S\2\u02c3\u02c4\7V\2\2"+
		"\u02c4s\3\2\2\2\u02c5\u02c7\7\5\2\2\u02c6\u02c8\5\u00d2j\2\u02c7\u02c6"+
		"\3\2\2\2\u02c7\u02c8\3\2\2\2\u02c8\u02c9\3\2\2\2\u02c9\u02ca\7V\2\2\u02ca"+
		"u\3\2\2\2\u02cb\u02cd\7\13\2\2\u02cc\u02ce\5\u00d2j\2\u02cd\u02cc\3\2"+
		"\2\2\u02cd\u02ce\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d0\7V\2\2\u02d0"+
		"w\3\2\2\2\u02d1\u02d2\7\62\2\2\u02d2\u02d3\5\u00a4S\2\u02d3\u02d4\7V\2"+
		"\2\u02d4y\3\2\2\2\u02d5\u02d6\7\64\2\2\u02d6\u02d7\5\u00a4S\2\u02d7\u02d8"+
		"\7V\2\2\u02d8{\3\2\2\2\u02d9\u02da\7\65\2\2\u02da\u02db\5\u00a4S\2\u02db"+
		"\u02dc\7V\2\2\u02dc}\3\2\2\2\u02dd\u02de\7\66\2\2\u02de\u02df\5\u00a4"+
		"S\2\u02df\u02e0\7V\2\2\u02e0\177\3\2\2\2\u02e1\u02e2\7\63\2\2\u02e2\u02e4"+
		"\5\u00a4S\2\u02e3\u02e5\5\u00d2j\2\u02e4\u02e3\3\2\2\2\u02e4\u02e5\3\2"+
		"\2\2\u02e5\u02e6\3\2\2\2\u02e6\u02e7\7V\2\2\u02e7\u0081\3\2\2\2\u02e8"+
		"\u02e9\7\67\2\2\u02e9\u02ea\5\u00a4S\2\u02ea\u02eb\5\u00a4S\2\u02eb\u02ec"+
		"\7V\2\2\u02ec\u0083\3\2\2\2\u02ed\u02ee\7E\2\2\u02ee\u02f0\7P\2\2\u02ef"+
		"\u02f1\5\u00a2R\2\u02f0\u02ef\3\2\2\2\u02f0\u02f1\3\2\2\2\u02f1\u02f2"+
		"\3\2\2\2\u02f2\u02f4\7Q\2\2\u02f3\u02f5\5^\60\2\u02f4\u02f3\3\2\2\2\u02f4"+
		"\u02f5\3\2\2\2\u02f5\u0085\3\2\2\2\u02f6\u02f7\7V\2\2\u02f7\u0087\3\2"+
		"\2\2\u02f8\u02f9\5\u00a4S\2\u02f9\u02fa\7V\2\2\u02fa\u0089\3\2\2\2\u02fb"+
		"\u02fc\5\u00d2j\2\u02fc\u02fd\7a\2\2\u02fd\u02fe\5d\63\2\u02fe\u008b\3"+
		"\2\2\2\u02ff\u0301\5\6\4\2\u0300\u02ff\3\2\2\2\u0301\u0304\3\2\2\2\u0302"+
		"\u0300\3\2\2\2\u0302\u0303\3\2\2\2\u0303\u0307\3\2\2\2\u0304\u0302\3\2"+
		"\2\2\u0305\u0308\5\u008eH\2\u0306\u0308\5\u0090I\2\u0307\u0305\3\2\2\2"+
		"\u0307\u0306\3\2\2\2\u0308\u008d\3\2\2\2\u0309\u030c\7:\2\2\u030a\u030d"+
		"\7V\2\2\u030b\u030d\5^\60\2\u030c\u030a\3\2\2\2\u030c\u030b\3\2\2\2\u030d"+
		"\u008f\3\2\2\2\u030e\u0311\7;\2\2\u030f\u0312\7V\2\2\u0310\u0312\5^\60"+
		"\2\u0311\u030f\3\2\2\2\u0311\u0310\3\2\2\2\u0312\u0091\3\2\2\2\u0313\u0314"+
		"\7\7\2\2\u0314\u0318\7P\2\2\u0315\u0317\5\n\6\2\u0316\u0315\3\2\2\2\u0317"+
		"\u031a\3\2\2\2\u0318\u0316\3\2\2\2\u0318\u0319\3\2\2\2\u0319\u031b\3\2"+
		"\2\2\u031a\u0318\3\2\2\2\u031b\u031c\5\u0094K\2\u031c\u031d\5\u00d2j\2"+
		"\u031d\u031e\7Q\2\2\u031e\u031f\5^\60\2\u031f\u0093\3\2\2\2\u0320\u0325"+
		"\5P)\2\u0321\u0322\7p\2\2\u0322\u0324\5P)\2\u0323\u0321\3\2\2\2\u0324"+
		"\u0327\3\2\2\2\u0325\u0323\3\2\2\2\u0325\u0326\3\2\2\2\u0326\u0095\3\2"+
		"\2\2\u0327\u0325\3\2\2\2\u0328\u0329\7\23\2\2\u0329\u032a\5^\60\2\u032a"+
		"\u0097\3\2\2\2\u032b\u0338\5\u009cO\2\u032c\u032e\5\u009aN\2\u032d\u032c"+
		"\3\2\2\2\u032d\u032e\3\2\2\2\u032e\u032f\3\2\2\2\u032f\u0331\7V\2\2\u0330"+
		"\u0332\5\u00a4S\2\u0331\u0330\3\2\2\2\u0331\u0332\3\2\2\2\u0332\u0333"+
		"\3\2\2\2\u0333\u0335\7V\2\2\u0334\u0336\5\u009eP\2\u0335\u0334\3\2\2\2"+
		"\u0335\u0336\3\2\2\2\u0336\u0338\3\2\2\2\u0337\u032b\3\2\2\2\u0337\u032d"+
		"\3\2\2\2\u0338\u0099\3\2\2\2\u0339\u033c\5b\62\2\u033a\u033c\5\u00a2R"+
		"\2\u033b\u0339\3\2\2\2\u033b\u033a\3\2\2\2\u033c\u009b\3\2\2\2\u033d\u033f"+
		"\5\n\6\2\u033e\u033d\3\2\2\2\u033f\u0342\3\2\2\2\u0340\u033e\3\2\2\2\u0340"+
		"\u0341\3\2\2\2\u0341\u0343\3\2\2\2\u0342\u0340\3\2\2\2\u0343\u0344\5@"+
		"!\2\u0344\u0345\5\u00d2j\2\u0345\u0346\7a\2\2\u0346\u0347\5\u00a4S\2\u0347"+
		"\u009d\3\2\2\2\u0348\u0349\5\u00a2R\2\u0349\u009f\3\2\2\2\u034a\u034b"+
		"\7P\2\2\u034b\u034c\5\u00a4S\2\u034c\u034d\7Q\2\2\u034d\u00a1\3\2\2\2"+
		"\u034e\u0353\5\u00a4S\2\u034f\u0350\7W\2\2\u0350\u0352\5\u00a4S\2\u0351"+
		"\u034f\3\2\2\2\u0352\u0355\3\2\2\2\u0353\u0351\3\2\2\2\u0353\u0354\3\2"+
		"\2\2\u0354\u00a3\3\2\2\2\u0355\u0353\3\2\2\2\u0356\u0357\bS\1\2\u0357"+
		"\u0358\7\35\2\2\u0358\u0364\5\u00a8U\2\u0359\u035a\7P\2\2\u035a\u035b"+
		"\5@!\2\u035b\u035c\7Q\2\2\u035c\u035d\5\u00a4S\24\u035d\u0364\3\2\2\2"+
		"\u035e\u035f\t\6\2\2\u035f\u0364\5\u00a4S\22\u0360\u0361\t\7\2\2\u0361"+
		"\u0364\5\u00a4S\21\u0362\u0364\5\u00a6T\2\u0363\u0356\3\2\2\2\u0363\u0359"+
		"\3\2\2\2\u0363\u035e\3\2\2\2\u0363\u0360\3\2\2\2\u0363\u0362\3\2\2\2\u0364"+
		"\u03c3\3\2\2\2\u0365\u0366\f\20\2\2\u0366\u0367\t\b\2\2\u0367\u03c2\5"+
		"\u00a4S\21\u0368\u0369\f\17\2\2\u0369\u036a\t\t\2\2\u036a\u03c2\5\u00a4"+
		"S\20\u036b\u0373\f\16\2\2\u036c\u036d\7]\2\2\u036d\u0374\7]\2\2\u036e"+
		"\u036f\7\\\2\2\u036f\u0370\7\\\2\2\u0370\u0374\7\\\2\2\u0371\u0372\7\\"+
		"\2\2\u0372\u0374\7\\\2\2\u0373\u036c\3\2\2\2\u0373\u036e\3\2\2\2\u0373"+
		"\u0371\3\2\2\2\u0374\u0375\3\2\2\2\u0375\u03c2\5\u00a4S\17\u0376\u037f"+
		"\f\r\2\2\u0377\u0378\7]\2\2\u0378\u0380\7Y\2\2\u0379\u037a\7\\\2\2\u037a"+
		"\u0380\7Y\2\2\u037b\u0380\7Z\2\2\u037c\u0380\7[\2\2\u037d\u0380\7\\\2"+
		"\2\u037e\u0380\7]\2\2\u037f\u0377\3\2\2\2\u037f\u0379\3\2\2\2\u037f\u037b"+
		"\3\2\2\2\u037f\u037c\3\2\2\2\u037f\u037d\3\2\2\2\u037f\u037e\3\2\2\2\u0380"+
		"\u0381\3\2\2\2\u0381\u03c2\5\u00a4S\16\u0382\u0383\f\13\2\2\u0383\u0384"+
		"\t\n\2\2\u0384\u03c2\5\u00a4S\f\u0385\u0386\f\n\2\2\u0386\u0387\7o\2\2"+
		"\u0387\u03c2\5\u00a4S\13\u0388\u0389\f\t\2\2\u0389\u038a\7q\2\2\u038a"+
		"\u03c2\5\u00a4S\n\u038b\u038c\f\b\2\2\u038c\u038d\7p\2\2\u038d\u03c2\5"+
		"\u00a4S\t\u038e\u038f\f\7\2\2\u038f\u0390\7g\2\2\u0390\u03c2\5\u00a4S"+
		"\b\u0391\u0392\f\6\2\2\u0392\u0393\7h\2\2\u0393\u03c2\5\u00a4S\7\u0394"+
		"\u0395\f\5\2\2\u0395\u0396\7`\2\2\u0396\u0397\5\u00a4S\2\u0397\u0398\7"+
		"a\2\2\u0398\u0399\5\u00a4S\6\u0399\u03c2\3\2\2\2\u039a\u039b\f\4\2\2\u039b"+
		"\u039c\t\13\2\2\u039c\u03c2\5\u00a4S\4\u039d\u039e\f\34\2\2\u039e\u039f"+
		"\7X\2\2\u039f\u03c2\5\u00d2j\2\u03a0\u03a1\f\33\2\2\u03a1\u03a2\7X\2\2"+
		"\u03a2\u03c2\7(\2\2\u03a3\u03a4\f\32\2\2\u03a4\u03a5\7X\2\2\u03a5\u03a7"+
		"\7\35\2\2\u03a6\u03a8\5\u00c0a\2\u03a7\u03a6\3\2\2\2\u03a7\u03a8\3\2\2"+
		"\2\u03a8\u03a9\3\2\2\2\u03a9\u03c2\5\u00aeX\2\u03aa\u03ab\f\31\2\2\u03ab"+
		"\u03ac\7X\2\2\u03ac\u03ad\7&\2\2\u03ad\u03c2\5\u00c6d\2\u03ae\u03af\f"+
		"\30\2\2\u03af\u03b0\7X\2\2\u03b0\u03c2\5\u00be`\2\u03b1\u03b2\f\27\2\2"+
		"\u03b2\u03b3\7T\2\2\u03b3\u03b4\5\u00a4S\2\u03b4\u03b5\7U\2\2\u03b5\u03c2"+
		"\3\2\2\2\u03b6\u03b7\f\26\2\2\u03b7\u03b9\7P\2\2\u03b8\u03ba\5\u00a2R"+
		"\2\u03b9\u03b8\3\2\2\2\u03b9\u03ba\3\2\2\2\u03ba\u03bb\3\2\2\2\u03bb\u03c2"+
		"\7Q\2\2\u03bc\u03bd\f\23\2\2\u03bd\u03c2\t\f\2\2\u03be\u03bf\f\f\2\2\u03bf"+
		"\u03c0\7\31\2\2\u03c0\u03c2\5@!\2\u03c1\u0365\3\2\2\2\u03c1\u0368\3\2"+
		"\2\2\u03c1\u036b\3\2\2\2\u03c1\u0376\3\2\2\2\u03c1\u0382\3\2\2\2\u03c1"+
		"\u0385\3\2\2\2\u03c1\u0388\3\2\2\2\u03c1\u038b\3\2\2\2\u03c1\u038e\3\2"+
		"\2\2\u03c1\u0391\3\2\2\2\u03c1\u0394\3\2\2\2\u03c1\u039a\3\2\2\2\u03c1"+
		"\u039d\3\2\2\2\u03c1\u03a0\3\2\2\2\u03c1\u03a3\3\2\2\2\u03c1\u03aa\3\2"+
		"\2\2\u03c1\u03ae\3\2\2\2\u03c1\u03b1\3\2\2\2\u03c1\u03b6\3\2\2\2\u03c1"+
		"\u03bc\3\2\2\2\u03c1\u03be\3\2\2\2\u03c2\u03c5\3\2\2\2\u03c3\u03c1\3\2"+
		"\2\2\u03c3\u03c4\3\2\2\2\u03c4\u00a5\3\2\2\2\u03c5\u03c3\3\2\2\2\u03c6"+
		"\u03c7\7P\2\2\u03c7\u03c8\5\u00a4S\2\u03c8\u03c9\7Q\2\2\u03c9\u03dd\3"+
		"\2\2\2\u03ca\u03dd\7(\2\2\u03cb\u03dd\7&\2\2\u03cc\u03dd\5R*\2\u03cd\u03dd"+
		"\5\u00d2j\2\u03ce\u03cf\5@!\2\u03cf\u03d0\7X\2\2\u03d0\u03d1\7\t\2\2\u03d1"+
		"\u03dd\3\2\2\2\u03d2\u03d3\7,\2\2\u03d3\u03d4\7X\2\2\u03d4\u03dd\7\t\2"+
		"\2\u03d5\u03d9\5\u00c0a\2\u03d6\u03da\5\u00c8e\2\u03d7\u03d8\7(\2\2\u03d8"+
		"\u03da\5\u00caf\2\u03d9\u03d6\3\2\2\2\u03d9\u03d7\3\2\2\2\u03da\u03dd"+
		"\3\2\2\2\u03db\u03dd\5\u00ccg\2\u03dc\u03c6\3\2\2\2\u03dc\u03ca\3\2\2"+
		"\2\u03dc\u03cb\3\2\2\2\u03dc\u03cc\3\2\2\2\u03dc\u03cd\3\2\2\2\u03dc\u03ce"+
		"\3\2\2\2\u03dc\u03d2\3\2\2\2\u03dc\u03d5\3\2\2\2\u03dc\u03db\3\2\2\2\u03dd"+
		"\u00a7\3\2\2\2\u03de\u03df\5\u00c0a\2\u03df\u03e0\5\u00aaV\2\u03e0\u03e1"+
		"\5\u00bc_\2\u03e1\u03ea\3\2\2\2\u03e2\u03e7\5\u00aaV\2\u03e3\u03e8\5\u00b0"+
		"Y\2\u03e4\u03e8\5\u00bc_\2\u03e5\u03e8\5\u00b2Z\2\u03e6\u03e8\5\u00b6"+
		"\\\2\u03e7\u03e3\3\2\2\2\u03e7\u03e4\3\2\2\2\u03e7\u03e5\3\2\2\2\u03e7"+
		"\u03e6\3\2\2\2\u03e8\u03ea\3\2\2\2\u03e9\u03de\3\2\2\2\u03e9\u03e2\3\2"+
		"\2\2\u03ea\u00a9\3\2\2\2\u03eb\u03f0\5\u00acW\2\u03ec\u03ed\7X\2\2\u03ed"+
		"\u03ef\5\u00acW\2\u03ee\u03ec\3\2\2\2\u03ef\u03f2\3\2\2\2\u03f0\u03ee"+
		"\3\2\2\2\u03f0\u03f1\3\2\2\2\u03f1\u03f5\3\2\2\2\u03f2\u03f0\3\2\2\2\u03f3"+
		"\u03f5\5F$\2\u03f4\u03eb\3\2\2\2\u03f4\u03f3\3\2\2\2\u03f5\u00ab\3\2\2"+
		"\2\u03f6\u03f8\5\u00d2j\2\u03f7\u03f9\5\u00c2b\2\u03f8\u03f7\3\2\2\2\u03f8"+
		"\u03f9\3\2\2\2\u03f9\u00ad\3\2\2\2\u03fa\u03fc\5\u00d2j\2\u03fb\u03fd"+
		"\5\u00c4c\2\u03fc\u03fb\3\2\2\2\u03fc\u03fd\3\2\2\2\u03fd\u03fe\3\2\2"+
		"\2\u03fe\u03ff\5\u00bc_\2\u03ff\u00af\3\2\2\2\u0400\u0412\7T\2\2\u0401"+
		"\u0402\7U\2\2\u0402\u0403\5B\"\2\u0403\u0404\5> \2\u0404\u0413\3\2\2\2"+
		"\u0405\u0406\5\u00a4S\2\u0406\u040d\7U\2\2\u0407\u0408\7T\2\2\u0408\u0409"+
		"\5\u00a4S\2\u0409\u040a\7U\2\2\u040a\u040c\3\2\2\2\u040b\u0407\3\2\2\2"+
		"\u040c\u040f\3\2\2\2\u040d\u040b\3\2\2\2\u040d\u040e\3\2\2\2\u040e\u0410"+
		"\3\2\2\2\u040f\u040d\3\2\2\2\u0410\u0411\5B\"\2\u0411\u0413\3\2\2\2\u0412"+
		"\u0401\3\2\2\2\u0412\u0405\3\2\2\2\u0413\u00b1\3\2\2\2\u0414\u0415\7R"+
		"\2\2\u0415\u041a\5\u00b4[\2\u0416\u0417\7W\2\2\u0417\u0419\5\u00b4[\2"+
		"\u0418\u0416\3\2\2\2\u0419\u041c\3\2\2\2\u041a\u0418\3\2\2\2\u041a\u041b"+
		"\3\2\2\2\u041b\u041d\3\2\2\2\u041c\u041a\3\2\2\2\u041d\u041e\7S\2\2\u041e"+
		"\u00b3\3\2\2\2\u041f\u0420\5\u00ba^\2\u0420\u0421\7s\2\2\u0421\u0422\5"+
		"\u00b8]\2\u0422\u00b5\3\2\2\2\u0423\u0424\7R\2\2\u0424\u0429\5\u00b8]"+
		"\2\u0425\u0426\7W\2\2\u0426\u0428\5\u00b8]\2\u0427\u0425\3\2\2\2\u0428"+
		"\u042b\3\2\2\2\u0429\u0427\3\2\2\2\u0429\u042a\3\2\2\2\u042a\u042c\3\2"+
		"\2\2\u042b\u0429\3\2\2\2\u042c\u042d\7S\2\2\u042d\u00b7\3\2\2\2\u042e"+
		"\u0431\5R*\2\u042f\u0431\5\u00a4S\2\u0430\u042e\3\2\2\2\u0430\u042f\3"+
		"\2\2\2\u0431\u00b9\3\2\2\2\u0432\u0435\5\u00d2j\2\u0433\u0435\5\u00a4"+
		"S\2\u0434\u0432\3\2\2\2\u0434\u0433\3\2\2\2\u0435\u00bb\3\2\2\2\u0436"+
		"\u043d\5\u00caf\2\u0437\u0439\7R\2\2\u0438\u043a\5\u00a2R\2\u0439\u0438"+
		"\3\2\2\2\u0439\u043a\3\2\2\2\u043a\u043b\3\2\2\2\u043b\u043d\7S\2\2\u043c"+
		"\u0436\3\2\2\2\u043c\u0437\3\2\2\2\u043d\u00bd\3\2\2\2\u043e\u043f\5\u00c0"+
		"a\2\u043f\u0440\5\u00c8e\2\u0440\u00bf\3\2\2\2\u0441\u0442\7]\2\2\u0442"+
		"\u0443\5\30\r\2\u0443\u0444\7\\\2\2\u0444\u00c1\3\2\2\2\u0445\u0446\7"+
		"]\2\2\u0446\u0449\7\\\2\2\u0447\u0449\5H%\2\u0448\u0445\3\2\2\2\u0448"+
		"\u0447\3\2\2\2\u0449\u00c3\3\2\2\2\u044a\u044b\7]\2\2\u044b\u044e\7\\"+
		"\2\2\u044c\u044e\5\u00c0a\2\u044d\u044a\3\2\2\2\u044d\u044c\3\2\2\2\u044e"+
		"\u00c5\3\2\2\2\u044f\u0456\5\u00caf\2\u0450\u0451\7X\2\2\u0451\u0453\5"+
		"\u00d2j\2\u0452\u0454\5\u00caf\2\u0453\u0452\3\2\2\2\u0453\u0454\3\2\2"+
		"\2\u0454\u0456\3\2\2\2\u0455\u044f\3\2\2\2\u0455\u0450\3\2\2\2\u0456\u00c7"+
		"\3\2\2\2\u0457\u0458\7&\2\2\u0458\u045d\5\u00c6d\2\u0459\u045a\5\u00d2"+
		"j\2\u045a\u045b\5\u00caf\2\u045b\u045d\3\2\2\2\u045c\u0457\3\2\2\2\u045c"+
		"\u0459\3\2\2\2\u045d\u00c9\3\2\2\2\u045e\u0460\7P\2\2\u045f\u0461\5\u00a2"+
		"R\2\u0460\u045f\3\2\2\2\u0460\u0461\3\2\2\2\u0461\u0462\3\2\2\2\u0462"+
		"\u0463\7Q\2\2\u0463\u00cb\3\2\2\2\u0464\u0469\7T\2\2\u0465\u0468\5\u00cc"+
		"g\2\u0466\u0468\n\r\2\2\u0467\u0465\3\2\2\2\u0467\u0466\3\2\2\2\u0468"+
		"\u046b\3\2\2\2\u0469\u046a\3\2\2\2\u0469\u0467\3\2\2\2\u046a\u046c\3\2"+
		"\2\2\u046b\u0469\3\2\2\2\u046c\u046d\7U\2\2\u046d\u00cd\3\2\2\2\u046e"+
		"\u0472\7I\2\2\u046f\u0470\7F\2\2\u0470\u0472\7H\2\2\u0471\u046e\3\2\2"+
		"\2\u0471\u046f\3\2\2\2\u0472\u00cf\3\2\2\2\u0473\u0477\7J\2\2\u0474\u0475"+
		"\7G\2\2\u0475\u0477\7H\2\2\u0476\u0473\3\2\2\2\u0476\u0474\3\2\2\2\u0477"+
		"\u00d1\3\2\2\2\u0478\u0479\t\16\2\2\u0479\u00d3\3\2\2\2{\u00d7\u00df\u00e6"+
		"\u00ed\u00f2\u00f6\u0106\u0108\u010d\u0113\u0117\u011f\u0123\u0126\u0129"+
		"\u0132\u0138\u013d\u0140\u0146\u014d\u0156\u015d\u0166\u016d\u0173\u0177"+
		"\u0180\u0185\u018a\u018f\u0195\u01a6\u01ad\u01b2\u01b9\u01c1\u01cb\u01d3"+
		"\u01de\u01e4\u01e8\u01f0\u01f4\u01f6\u0200\u0206\u020b\u0210\u0214\u021f"+
		"\u0228\u022e\u0239\u0243\u0246\u024a\u024f\u0259\u0261\u0264\u0267\u026f"+
		"\u027a\u0295\u029c\u02b3\u02b6\u02b9\u02bd\u02c7\u02cd\u02e4\u02f0\u02f4"+
		"\u0302\u0307\u030c\u0311\u0318\u0325\u032d\u0331\u0335\u0337\u033b\u0340"+
		"\u0353\u0363\u0373\u037f\u03a7\u03b9\u03c1\u03c3\u03d9\u03dc\u03e7\u03e9"+
		"\u03f0\u03f4\u03f8\u03fc\u040d\u0412\u041a\u0429\u0430\u0434\u0439\u043c"+
		"\u0448\u044d\u0453\u0455\u045c\u0460\u0467\u0469\u0471\u0476";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}