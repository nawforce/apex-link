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
		Identifier=125, AT=126, ELLIPSIS=127, WS=128, BANG_STATEMENT=129, DOC_COMMENT=130, 
		COMMENT=131, LINE_COMMENT=132;
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
		RULE_idStatement = 68, RULE_bangStatement = 69, RULE_propertyBlock = 70, 
		RULE_getter = 71, RULE_setter = 72, RULE_catchClause = 73, RULE_catchType = 74, 
		RULE_finallyBlock = 75, RULE_forControl = 76, RULE_forInit = 77, RULE_enhancedForControl = 78, 
		RULE_forUpdate = 79, RULE_parExpression = 80, RULE_expressionList = 81, 
		RULE_expression = 82, RULE_primary = 83, RULE_creator = 84, RULE_createdName = 85, 
		RULE_idCreatedNamePair = 86, RULE_innerCreator = 87, RULE_arrayCreatorRest = 88, 
		RULE_mapCreatorRest = 89, RULE_mapCreatorRestPair = 90, RULE_setCreatorRest = 91, 
		RULE_literalOrExpression = 92, RULE_idOrExpression = 93, RULE_classCreatorRest = 94, 
		RULE_explicitGenericInvocation = 95, RULE_nonWildcardTypeArguments = 96, 
		RULE_typeArgumentsOrDiamond = 97, RULE_nonWildcardTypeArgumentsOrDiamond = 98, 
		RULE_superSuffix = 99, RULE_explicitGenericInvocationSuffix = 100, RULE_arguments = 101, 
		RULE_soqlLiteral = 102, RULE_withSharing = 103, RULE_withoutSharing = 104, 
		RULE_id = 105;
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
			"idStatement", "bangStatement", "propertyBlock", "getter", "setter", 
			"catchClause", "catchType", "finallyBlock", "forControl", "forInit", 
			"enhancedForControl", "forUpdate", "parExpression", "expressionList", 
			"expression", "primary", "creator", "createdName", "idCreatedNamePair", 
			"innerCreator", "arrayCreatorRest", "mapCreatorRest", "mapCreatorRestPair", 
			"setCreatorRest", "literalOrExpression", "idOrExpression", "classCreatorRest", 
			"explicitGenericInvocation", "nonWildcardTypeArguments", "typeArgumentsOrDiamond", 
			"nonWildcardTypeArgumentsOrDiamond", "superSuffix", "explicitGenericInvocationSuffix", 
			"arguments", "soqlLiteral", "withSharing", "withoutSharing", "id"
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
			"BANG_STATEMENT", "DOC_COMMENT", "COMMENT", "LINE_COMMENT"
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
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << INTERFACE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (SEMI - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(212);
				typeDeclaration();
				}
				}
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(218);
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
			setState(242);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(220);
					classOrInterfaceModifier();
					}
					}
					setState(225);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(226);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(227);
					classOrInterfaceModifier();
					}
					}
					setState(232);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(233);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(237);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(234);
					classOrInterfaceModifier();
					}
					}
					setState(239);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(240);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(241);
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
			setState(246);
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
				setState(244);
				classOrInterfaceModifier();
				}
				break;
			case NATIVE:
			case SYNCHRONIZED:
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
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
			setState(264);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(248);
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
				setState(262);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PUBLIC:
					{
					setState(249);
					match(PUBLIC);
					}
					break;
				case PROTECTED:
					{
					setState(250);
					match(PROTECTED);
					}
					break;
				case PRIVATE:
					{
					setState(251);
					match(PRIVATE);
					}
					break;
				case STATIC:
					{
					setState(252);
					match(STATIC);
					}
					break;
				case ABSTRACT:
					{
					setState(253);
					match(ABSTRACT);
					}
					break;
				case FINAL:
					{
					setState(254);
					match(FINAL);
					}
					break;
				case GLOBAL:
					{
					setState(255);
					match(GLOBAL);
					}
					break;
				case WEBSERVICE:
					{
					setState(256);
					match(WEBSERVICE);
					}
					break;
				case OVERRIDE:
					{
					setState(257);
					match(OVERRIDE);
					}
					break;
				case VIRTUAL:
					{
					setState(258);
					match(VIRTUAL);
					}
					break;
				case TESTMETHOD:
					{
					setState(259);
					match(TESTMETHOD);
					}
					break;
				case WITH:
				case WITHSHARING:
					{
					setState(260);
					withSharing();
					}
					break;
				case WITHOUT:
				case WITHOUTSHARING:
					{
					setState(261);
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
			setState(269);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(266);
				match(FINAL);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				match(TRANSIENT);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 3);
				{
				setState(268);
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
			setState(271);
			match(CLASS);
			setState(272);
			id();
			setState(275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(273);
				match(EXTENDS);
				setState(274);
				typeRef();
				}
			}

			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(277);
				match(IMPLEMENTS);
				setState(278);
				typeList();
				}
			}

			setState(281);
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
			setState(283);
			match(ENUM);
			setState(284);
			id();
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(285);
				match(IMPLEMENTS);
				setState(286);
				typeList();
				}
			}

			setState(289);
			match(LBRACE);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(290);
				enumConstants();
				}
			}

			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(293);
				match(COMMA);
				}
			}

			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(296);
				enumBodyDeclarations();
				}
			}

			setState(299);
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
			setState(301);
			enumConstant();
			setState(306);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(302);
					match(COMMA);
					setState(303);
					enumConstant();
					}
					} 
				}
				setState(308);
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
			setState(312);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(309);
				annotation();
				}
				}
				setState(314);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(315);
			id();
			setState(317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(316);
				arguments();
				}
			}

			setState(320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(319);
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
			setState(322);
			match(SEMI);
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(323);
				classBodyDeclaration();
				}
				}
				setState(328);
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
			setState(329);
			match(INTERFACE);
			setState(330);
			id();
			setState(333);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(331);
				match(EXTENDS);
				setState(332);
				typeList();
				}
			}

			setState(335);
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
			setState(337);
			typeRef();
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(338);
				match(COMMA);
				setState(339);
				typeRef();
				}
				}
				setState(344);
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
			setState(345);
			match(LBRACE);
			setState(349);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(346);
				classBodyDeclaration();
				}
				}
				setState(351);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(352);
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
			setState(354);
			match(LBRACE);
			setState(358);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(355);
				interfaceBodyDeclaration();
				}
				}
				setState(360);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(361);
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
			setState(375);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(363);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(365);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(364);
					match(STATIC);
					}
				}

				setState(367);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(371);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(368);
						modifier();
						}
						} 
					}
					setState(373);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(374);
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
			setState(384);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(377);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(378);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(379);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(380);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(381);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(382);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(383);
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
			setState(386);
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
			setState(389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AT) {
				{
				setState(388);
				annotation();
				}
			}

			setState(394);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0)) {
				{
				{
				setState(391);
				methodModifier();
				}
				}
				setState(396);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(399);
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
				setState(397);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(398);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(401);
			id();
			setState(402);
			formalParameters();
			setState(405);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(403);
				block();
				}
				break;
			case SEMI:
				{
				setState(404);
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
			setState(407);
			qualifiedName();
			setState(408);
			formalParameters();
			setState(409);
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
			setState(411);
			typeRef();
			setState(412);
			variableDeclarators();
			setState(413);
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
			setState(415);
			typeRef();
			setState(416);
			variableDeclarators();
			setState(417);
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
			setState(419);
			match(LBRACE);
			setState(420);
			propertyBlock();
			setState(422);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				setState(421);
				propertyBlock();
				}
			}

			setState(424);
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
			setState(434);
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
				setState(429);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(426);
						modifier();
						}
						} 
					}
					setState(431);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				setState(432);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(433);
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
			setState(441);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(436);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(437);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(438);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(439);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(440);
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
			setState(443);
			typeRef();
			setState(444);
			constantDeclarator();
			setState(449);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(445);
				match(COMMA);
				setState(446);
				constantDeclarator();
				}
				}
				setState(451);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(452);
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
			setState(454);
			id();
			setState(459);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(455);
				match(LBRACK);
				setState(456);
				match(RBRACK);
				}
				}
				setState(461);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(462);
			match(ASSIGN);
			setState(463);
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
			setState(467);
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
				setState(465);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(466);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(469);
			id();
			setState(470);
			formalParameters();
			setState(471);
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
			setState(473);
			variableDeclarator();
			setState(478);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(474);
				match(COMMA);
				setState(475);
				variableDeclarator();
				}
				}
				setState(480);
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
			setState(481);
			id();
			setState(484);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(482);
				match(ASSIGN);
				setState(483);
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
			setState(488);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(486);
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
				setState(487);
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
			setState(490);
			match(LBRACE);
			setState(502);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(491);
				variableInitializer();
				setState(496);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(492);
						match(COMMA);
						setState(493);
						variableInitializer();
						}
						} 
					}
					setState(498);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				}
				setState(500);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(499);
					match(COMMA);
					}
				}

				}
			}

			setState(504);
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
			setState(512);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(506);
				classOrInterfaceType();
				setState(507);
				arraySubscripts();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(509);
				primitiveType();
				setState(510);
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
			setState(518);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(514);
					match(LBRACK);
					setState(515);
					match(RBRACK);
					}
					} 
				}
				setState(520);
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
			setState(521);
			id();
			setState(523);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(522);
				typeArguments();
				}
				break;
			}
			setState(532);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(525);
					match(DOT);
					setState(526);
					id();
					setState(528);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
					case 1:
						{
						setState(527);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(534);
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
			setState(535);
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
			setState(537);
			match(LT);
			setState(538);
			typeList();
			setState(539);
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
			setState(541);
			match(LPAREN);
			setState(543);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << TRANSIENT) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(542);
				formalParameterList();
				}
			}

			setState(545);
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
			setState(547);
			formalParameter();
			setState(552);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(548);
				match(COMMA);
				setState(549);
				formalParameter();
				}
				}
				setState(554);
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
			setState(558);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(555);
				variableModifier();
				}
				}
				setState(560);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(561);
			typeRef();
			setState(562);
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
			setState(564);
			id();
			setState(569);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(565);
				match(DOT);
				setState(566);
				id();
				}
				}
				setState(571);
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
			setState(572);
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
			setState(574);
			match(AT);
			setState(575);
			qualifiedName();
			setState(582);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(576);
				match(LPAREN);
				setState(579);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(577);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(578);
					elementValue();
					}
					break;
				}
				setState(581);
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
			setState(584);
			elementValuePair();
			setState(591);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (COMMA - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(586);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(585);
					match(COMMA);
					}
				}

				setState(588);
				elementValuePair();
				}
				}
				setState(593);
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
			setState(594);
			id();
			setState(595);
			match(ASSIGN);
			setState(596);
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
			setState(601);
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
				setState(598);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(599);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(600);
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
			setState(603);
			match(LBRACE);
			setState(612);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(604);
				elementValue();
				setState(609);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(605);
						match(COMMA);
						setState(606);
						elementValue();
						}
						} 
					}
					setState(611);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,59,_ctx);
				}
				}
			}

			setState(615);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(614);
				match(COMMA);
				}
			}

			setState(617);
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
			setState(619);
			match(LBRACE);
			setState(623);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (BOOLEAN - 2)) | (1L << (BREAK - 2)) | (1L << (CONTINUE - 2)) | (1L << (DO - 2)) | (1L << (DOUBLE - 2)) | (1L << (FINAL - 2)) | (1L << (FOR - 2)) | (1L << (IF - 2)) | (1L << (LONG - 2)) | (1L << (NEW - 2)) | (1L << (RETURN - 2)) | (1L << (SUPER - 2)) | (1L << (THIS - 2)) | (1L << (THROW - 2)) | (1L << (TRANSIENT - 2)) | (1L << (TRY - 2)) | (1L << (VOID - 2)) | (1L << (WHILE - 2)) | (1L << (SELECT - 2)) | (1L << (INSERT - 2)) | (1L << (UPSERT - 2)) | (1L << (UPDATE - 2)) | (1L << (DELETE - 2)) | (1L << (UNDELETE - 2)) | (1L << (MERGE - 2)) | (1L << (GET - 2)) | (1L << (SET - 2)) | (1L << (BLOB - 2)) | (1L << (DATE - 2)) | (1L << (DATETIME - 2)) | (1L << (DECIMAL - 2)) | (1L << (ID - 2)) | (1L << (INTEGER - 2)) | (1L << (OBJECT - 2)) | (1L << (STRING - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (TIME - 66)) | (1L << (RUNAS - 66)) | (1L << (WITHSHARING - 66)) | (1L << (WITHOUTSHARING - 66)) | (1L << (IntegerLiteral - 66)) | (1L << (NumberLiteral - 66)) | (1L << (BooleanLiteral - 66)) | (1L << (StringLiteral - 66)) | (1L << (NullLiteral - 66)) | (1L << (LPAREN - 66)) | (1L << (LBRACE - 66)) | (1L << (LBRACK - 66)) | (1L << (SEMI - 66)) | (1L << (LT - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)) | (1L << (AT - 66)) | (1L << (BANG_STATEMENT - 66)))) != 0)) {
				{
				{
				setState(620);
				statement();
				}
				}
				setState(625);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(626);
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
			setState(628);
			localVariableDeclaration();
			setState(629);
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
			setState(634);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(631);
				variableModifier();
				}
				}
				setState(636);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(637);
			typeRef();
			setState(638);
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
		public BangStatementContext bangStatement() {
			return getRuleContext(BangStatementContext.class,0);
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
			setState(662);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(640);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(641);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(642);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(643);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(644);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(645);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(646);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(647);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(648);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(649);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(650);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(651);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(652);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(653);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(654);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(655);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(656);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(657);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(658);
				emptyStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(659);
				expressionStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(660);
				idStatement();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(661);
				bangStatement();
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
			setState(664);
			match(IF);
			setState(665);
			parExpression();
			setState(666);
			statement();
			setState(669);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(667);
				match(ELSE);
				setState(668);
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
			setState(671);
			match(FOR);
			setState(672);
			match(LPAREN);
			setState(673);
			forControl();
			setState(674);
			match(RPAREN);
			setState(675);
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
			setState(677);
			match(WHILE);
			setState(678);
			parExpression();
			setState(679);
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
			setState(681);
			match(DO);
			setState(682);
			statement();
			setState(683);
			match(WHILE);
			setState(684);
			parExpression();
			setState(685);
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
			setState(687);
			match(TRY);
			setState(688);
			block();
			setState(698);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(690); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(689);
					catchClause();
					}
					}
					setState(692); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CATCH );
				setState(695);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(694);
					finallyBlock();
					}
				}

				}
				break;
			case FINALLY:
				{
				setState(697);
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
			setState(700);
			match(RETURN);
			setState(702);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(701);
				expression(0);
				}
			}

			setState(704);
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
			setState(706);
			match(THROW);
			setState(707);
			expression(0);
			setState(708);
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
			setState(710);
			match(BREAK);
			setState(712);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(711);
				id();
				}
			}

			setState(714);
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
			setState(716);
			match(CONTINUE);
			setState(718);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(717);
				id();
				}
			}

			setState(720);
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
			setState(722);
			match(INSERT);
			setState(723);
			expression(0);
			setState(724);
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
			setState(726);
			match(UPDATE);
			setState(727);
			expression(0);
			setState(728);
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
			setState(730);
			match(DELETE);
			setState(731);
			expression(0);
			setState(732);
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
			setState(734);
			match(UNDELETE);
			setState(735);
			expression(0);
			setState(736);
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
			setState(738);
			match(UPSERT);
			setState(739);
			expression(0);
			setState(741);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(740);
				id();
				}
			}

			setState(743);
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
			setState(745);
			match(MERGE);
			setState(746);
			expression(0);
			setState(747);
			expression(0);
			setState(748);
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
			setState(750);
			match(RUNAS);
			setState(751);
			match(LPAREN);
			setState(753);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(752);
				expressionList();
				}
			}

			setState(755);
			match(RPAREN);
			setState(757);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(756);
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
			setState(761);
			expression(0);
			setState(762);
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
			setState(764);
			id();
			setState(765);
			match(COLON);
			setState(766);
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

	public static class BangStatementContext extends ParserRuleContext {
		public TerminalNode BANG_STATEMENT() { return getToken(ApexParser.BANG_STATEMENT, 0); }
		public BangStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bangStatement; }
	}

	public final BangStatementContext bangStatement() throws RecognitionException {
		BangStatementContext _localctx = new BangStatementContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_bangStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(768);
			match(BANG_STATEMENT);
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
		enterRule(_localctx, 140, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(773);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(770);
				modifier();
				}
				}
				setState(775);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(778);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(776);
				getter();
				}
				break;
			case SET:
				{
				setState(777);
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
		enterRule(_localctx, 142, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780);
			match(GET);
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
		enterRule(_localctx, 144, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(785);
			match(SET);
			setState(788);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(786);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(787);
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
		enterRule(_localctx, 146, RULE_catchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			match(CATCH);
			setState(791);
			match(LPAREN);
			setState(795);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(792);
				variableModifier();
				}
				}
				setState(797);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(798);
			catchType();
			setState(799);
			id();
			setState(800);
			match(RPAREN);
			setState(801);
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
		enterRule(_localctx, 148, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(803);
			qualifiedName();
			setState(808);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(804);
				match(BITOR);
				setState(805);
				qualifiedName();
				}
				}
				setState(810);
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
		enterRule(_localctx, 150, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(811);
			match(FINALLY);
			setState(812);
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
		enterRule(_localctx, 152, RULE_forControl);
		int _la;
		try {
			setState(826);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(814);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(816);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << TRANSIENT) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(815);
					forInit();
					}
				}

				setState(818);
				match(SEMI);
				setState(820);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(819);
					expression(0);
					}
				}

				setState(822);
				match(SEMI);
				setState(824);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(823);
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
		enterRule(_localctx, 154, RULE_forInit);
		try {
			setState(830);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(828);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(829);
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
		enterRule(_localctx, 156, RULE_enhancedForControl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(835);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(832);
				variableModifier();
				}
				}
				setState(837);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(838);
			typeRef();
			setState(839);
			id();
			setState(840);
			match(COLON);
			setState(841);
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
		enterRule(_localctx, 158, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(843);
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
		enterRule(_localctx, 160, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(845);
			match(LPAREN);
			setState(846);
			expression(0);
			setState(847);
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
		enterRule(_localctx, 162, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(849);
			expression(0);
			setState(854);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(850);
				match(COMMA);
				setState(851);
				expression(0);
				}
				}
				setState(856);
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
		int _startState = 164;
		enterRecursionRule(_localctx, 164, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(870);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(858);
				match(NEW);
				setState(859);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(860);
				match(LPAREN);
				setState(861);
				typeRef();
				setState(862);
				match(RPAREN);
				setState(863);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(865);
				_la = _input.LA(1);
				if ( !(((((_la - 103)) & ~0x3f) == 0 && ((1L << (_la - 103)) & ((1L << (INC - 103)) | (1L << (DEC - 103)) | (1L << (ADD - 103)) | (1L << (SUB - 103)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(866);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(867);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(868);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(869);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(966);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,94,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(964);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,93,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(872);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(873);
						_la = _input.LA(1);
						if ( !(((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & ((1L << (MUL - 107)) | (1L << (DIV - 107)) | (1L << (MOD - 107)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(874);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(875);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(876);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(877);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(878);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(886);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
						case 1:
							{
							setState(879);
							match(LT);
							setState(880);
							match(LT);
							}
							break;
						case 2:
							{
							setState(881);
							match(GT);
							setState(882);
							match(GT);
							setState(883);
							match(GT);
							}
							break;
						case 3:
							{
							setState(884);
							match(GT);
							setState(885);
							match(GT);
							}
							break;
						}
						setState(888);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(889);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(898);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
						case 1:
							{
							setState(890);
							match(LT);
							setState(891);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(892);
							match(GT);
							setState(893);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(894);
							match(LE);
							}
							break;
						case 4:
							{
							setState(895);
							match(GE);
							}
							break;
						case 5:
							{
							setState(896);
							match(GT);
							}
							break;
						case 6:
							{
							setState(897);
							match(LT);
							}
							break;
						}
						setState(900);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(901);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(902);
						_la = _input.LA(1);
						if ( !(((((_la - 96)) & ~0x3f) == 0 && ((1L << (_la - 96)) & ((1L << (EQUAL - 96)) | (1L << (TRIPLEEQUAL - 96)) | (1L << (NOTEQUAL - 96)) | (1L << (LESSANDGREATER - 96)) | (1L << (TRIPLENOTEQUAL - 96)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(903);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(904);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(905);
						match(BITAND);
						setState(906);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(907);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(908);
						match(CARET);
						setState(909);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(910);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(911);
						match(BITOR);
						setState(912);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(913);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(914);
						match(AND);
						setState(915);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(916);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(917);
						match(OR);
						setState(918);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(919);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(920);
						match(QUESTION);
						setState(921);
						expression(0);
						setState(922);
						match(COLON);
						setState(923);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(925);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(926);
						_la = _input.LA(1);
						if ( !(((((_la - 87)) & ~0x3f) == 0 && ((1L << (_la - 87)) & ((1L << (ASSIGN - 87)) | (1L << (ADD_ASSIGN - 87)) | (1L << (SUB_ASSIGN - 87)) | (1L << (MUL_ASSIGN - 87)) | (1L << (DIV_ASSIGN - 87)) | (1L << (AND_ASSIGN - 87)) | (1L << (OR_ASSIGN - 87)) | (1L << (XOR_ASSIGN - 87)) | (1L << (MOD_ASSIGN - 87)) | (1L << (LSHIFT_ASSIGN - 87)) | (1L << (RSHIFT_ASSIGN - 87)) | (1L << (URSHIFT_ASSIGN - 87)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(927);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(928);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(929);
						match(DOT);
						setState(930);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(931);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(932);
						match(DOT);
						setState(933);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(934);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(935);
						match(DOT);
						setState(936);
						match(NEW);
						setState(938);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(937);
							nonWildcardTypeArguments();
							}
						}

						setState(940);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(941);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(942);
						match(DOT);
						setState(943);
						match(SUPER);
						setState(944);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(945);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(946);
						match(DOT);
						setState(947);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(948);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(949);
						match(LBRACK);
						setState(950);
						expression(0);
						setState(951);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(953);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(954);
						match(LPAREN);
						setState(956);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(955);
							expressionList();
							}
						}

						setState(958);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(959);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(960);
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
						setState(961);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(962);
						match(INSTANCEOF);
						setState(963);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(968);
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
		enterRule(_localctx, 166, RULE_primary);
		try {
			setState(991);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(969);
				match(LPAREN);
				setState(970);
				expression(0);
				setState(971);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(973);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(974);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(975);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(976);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(977);
				typeRef();
				setState(978);
				match(DOT);
				setState(979);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(981);
				match(VOID);
				setState(982);
				match(DOT);
				setState(983);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(984);
				nonWildcardTypeArguments();
				setState(988);
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
					setState(985);
					explicitGenericInvocationSuffix();
					}
					break;
				case THIS:
					{
					setState(986);
					match(THIS);
					setState(987);
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
				setState(990);
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
		enterRule(_localctx, 168, RULE_creator);
		try {
			setState(1004);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(993);
				nonWildcardTypeArguments();
				setState(994);
				createdName();
				setState(995);
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
				setState(997);
				createdName();
				setState(1002);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(998);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(999);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(1000);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(1001);
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
		enterRule(_localctx, 170, RULE_createdName);
		int _la;
		try {
			setState(1015);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1006);
				idCreatedNamePair();
				setState(1011);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(1007);
					match(DOT);
					setState(1008);
					idCreatedNamePair();
					}
					}
					setState(1013);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1014);
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
		enterRule(_localctx, 172, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1017);
			id();
			setState(1019);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1018);
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
		enterRule(_localctx, 174, RULE_innerCreator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			id();
			setState(1023);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1022);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(1025);
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
		enterRule(_localctx, 176, RULE_arrayCreatorRest);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1027);
			match(LBRACK);
			setState(1045);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(1028);
				match(RBRACK);
				setState(1029);
				arraySubscripts();
				setState(1030);
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
				setState(1032);
				expression(0);
				setState(1033);
				match(RBRACK);
				setState(1040);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1034);
						match(LBRACK);
						setState(1035);
						expression(0);
						setState(1036);
						match(RBRACK);
						}
						} 
					}
					setState(1042);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				}
				setState(1043);
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
		enterRule(_localctx, 178, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1047);
			match(LBRACE);
			setState(1048);
			mapCreatorRestPair();
			setState(1053);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1049);
				match(COMMA);
				setState(1050);
				mapCreatorRestPair();
				}
				}
				setState(1055);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1056);
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
		enterRule(_localctx, 180, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1058);
			idOrExpression();
			setState(1059);
			match(MAP);
			setState(1060);
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
		enterRule(_localctx, 182, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1062);
			match(LBRACE);
			setState(1063);
			literalOrExpression();
			setState(1068);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1064);
				match(COMMA);
				{
				setState(1065);
				literalOrExpression();
				}
				}
				}
				setState(1070);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1071);
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
		enterRule(_localctx, 184, RULE_literalOrExpression);
		try {
			setState(1075);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1073);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1074);
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
		enterRule(_localctx, 186, RULE_idOrExpression);
		try {
			setState(1079);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1077);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1078);
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
		enterRule(_localctx, 188, RULE_classCreatorRest);
		int _la;
		try {
			setState(1087);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1081);
				arguments();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1082);
				match(LBRACE);
				setState(1084);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1083);
					expressionList();
					}
				}

				setState(1086);
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
		enterRule(_localctx, 190, RULE_explicitGenericInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1089);
			nonWildcardTypeArguments();
			setState(1090);
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
		enterRule(_localctx, 192, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1092);
			match(LT);
			setState(1093);
			typeList();
			setState(1094);
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
		enterRule(_localctx, 194, RULE_typeArgumentsOrDiamond);
		try {
			setState(1099);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,111,_ctx) ) {
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
		enterRule(_localctx, 196, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1101);
				match(LT);
				setState(1102);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1103);
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
		enterRule(_localctx, 198, RULE_superSuffix);
		try {
			setState(1112);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1106);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1107);
				match(DOT);
				setState(1108);
				id();
				setState(1110);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
				case 1:
					{
					setState(1109);
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
		enterRule(_localctx, 200, RULE_explicitGenericInvocationSuffix);
		try {
			setState(1119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1114);
				match(SUPER);
				setState(1115);
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
				setState(1116);
				id();
				setState(1117);
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
		enterRule(_localctx, 202, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1121);
			match(LPAREN);
			setState(1123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1122);
				expressionList();
				}
			}

			setState(1125);
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
		enterRule(_localctx, 204, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1127);
			match(LBRACK);
			setState(1132);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,118,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1130);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,117,_ctx) ) {
					case 1:
						{
						setState(1128);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1129);
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
				setState(1134);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,118,_ctx);
			}
			setState(1135);
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
		enterRule(_localctx, 206, RULE_withSharing);
		try {
			setState(1140);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1137);
				match(WITHSHARING);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 2);
				{
				setState(1138);
				match(WITH);
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
		enterRule(_localctx, 208, RULE_withoutSharing);
		try {
			setState(1145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHOUTSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1142);
				match(WITHOUTSHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1143);
				match(WITHOUT);
				setState(1144);
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
		enterRule(_localctx, 210, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1147);
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
		case 82:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0086\u0480\4\2\t"+
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
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\3\2\7\2\u00d8\n\2\f\2\16\2\u00db\13\2\3\2\3\2\3\3\7\3\u00e0\n\3\f\3"+
		"\16\3\u00e3\13\3\3\3\3\3\7\3\u00e7\n\3\f\3\16\3\u00ea\13\3\3\3\3\3\7\3"+
		"\u00ee\n\3\f\3\16\3\u00f1\13\3\3\3\3\3\5\3\u00f5\n\3\3\4\3\4\5\4\u00f9"+
		"\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0109"+
		"\n\5\5\5\u010b\n\5\3\6\3\6\3\6\5\6\u0110\n\6\3\7\3\7\3\7\3\7\5\7\u0116"+
		"\n\7\3\7\3\7\5\7\u011a\n\7\3\7\3\7\3\b\3\b\3\b\3\b\5\b\u0122\n\b\3\b\3"+
		"\b\5\b\u0126\n\b\3\b\5\b\u0129\n\b\3\b\5\b\u012c\n\b\3\b\3\b\3\t\3\t\3"+
		"\t\7\t\u0133\n\t\f\t\16\t\u0136\13\t\3\n\7\n\u0139\n\n\f\n\16\n\u013c"+
		"\13\n\3\n\3\n\5\n\u0140\n\n\3\n\5\n\u0143\n\n\3\13\3\13\7\13\u0147\n\13"+
		"\f\13\16\13\u014a\13\13\3\f\3\f\3\f\3\f\5\f\u0150\n\f\3\f\3\f\3\r\3\r"+
		"\3\r\7\r\u0157\n\r\f\r\16\r\u015a\13\r\3\16\3\16\7\16\u015e\n\16\f\16"+
		"\16\16\u0161\13\16\3\16\3\16\3\17\3\17\7\17\u0167\n\17\f\17\16\17\u016a"+
		"\13\17\3\17\3\17\3\20\3\20\5\20\u0170\n\20\3\20\3\20\7\20\u0174\n\20\f"+
		"\20\16\20\u0177\13\20\3\20\5\20\u017a\n\20\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\5\21\u0183\n\21\3\22\3\22\3\23\5\23\u0188\n\23\3\23\7\23\u018b"+
		"\n\23\f\23\16\23\u018e\13\23\3\23\3\23\5\23\u0192\n\23\3\23\3\23\3\23"+
		"\3\23\5\23\u0198\n\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\5\27\u01a9\n\27\3\27\3\27\3\30\7\30\u01ae\n"+
		"\30\f\30\16\30\u01b1\13\30\3\30\3\30\5\30\u01b5\n\30\3\31\3\31\3\31\3"+
		"\31\3\31\5\31\u01bc\n\31\3\32\3\32\3\32\3\32\7\32\u01c2\n\32\f\32\16\32"+
		"\u01c5\13\32\3\32\3\32\3\33\3\33\3\33\7\33\u01cc\n\33\f\33\16\33\u01cf"+
		"\13\33\3\33\3\33\3\33\3\34\3\34\5\34\u01d6\n\34\3\34\3\34\3\34\3\34\3"+
		"\35\3\35\3\35\7\35\u01df\n\35\f\35\16\35\u01e2\13\35\3\36\3\36\3\36\5"+
		"\36\u01e7\n\36\3\37\3\37\5\37\u01eb\n\37\3 \3 \3 \3 \7 \u01f1\n \f \16"+
		" \u01f4\13 \3 \5 \u01f7\n \5 \u01f9\n \3 \3 \3!\3!\3!\3!\3!\3!\5!\u0203"+
		"\n!\3\"\3\"\7\"\u0207\n\"\f\"\16\"\u020a\13\"\3#\3#\5#\u020e\n#\3#\3#"+
		"\3#\5#\u0213\n#\7#\u0215\n#\f#\16#\u0218\13#\3$\3$\3%\3%\3%\3%\3&\3&\5"+
		"&\u0222\n&\3&\3&\3\'\3\'\3\'\7\'\u0229\n\'\f\'\16\'\u022c\13\'\3(\7(\u022f"+
		"\n(\f(\16(\u0232\13(\3(\3(\3(\3)\3)\3)\7)\u023a\n)\f)\16)\u023d\13)\3"+
		"*\3*\3+\3+\3+\3+\3+\5+\u0246\n+\3+\5+\u0249\n+\3,\3,\5,\u024d\n,\3,\7"+
		",\u0250\n,\f,\16,\u0253\13,\3-\3-\3-\3-\3.\3.\3.\5.\u025c\n.\3/\3/\3/"+
		"\3/\7/\u0262\n/\f/\16/\u0265\13/\5/\u0267\n/\3/\5/\u026a\n/\3/\3/\3\60"+
		"\3\60\7\60\u0270\n\60\f\60\16\60\u0273\13\60\3\60\3\60\3\61\3\61\3\61"+
		"\3\62\7\62\u027b\n\62\f\62\16\62\u027e\13\62\3\62\3\62\3\62\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\63\3\63\5\63\u0299\n\63\3\64\3\64\3\64\3\64\3\64"+
		"\5\64\u02a0\n\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\38\38\38\68\u02b5\n8\r8\168\u02b6\38\58\u02ba"+
		"\n8\38\58\u02bd\n8\39\39\59\u02c1\n9\39\39\3:\3:\3:\3:\3;\3;\5;\u02cb"+
		"\n;\3;\3;\3<\3<\5<\u02d1\n<\3<\3<\3=\3=\3=\3=\3>\3>\3>\3>\3?\3?\3?\3?"+
		"\3@\3@\3@\3@\3A\3A\3A\5A\u02e8\nA\3A\3A\3B\3B\3B\3B\3B\3C\3C\3C\5C\u02f4"+
		"\nC\3C\3C\5C\u02f8\nC\3D\3D\3E\3E\3E\3F\3F\3F\3F\3G\3G\3H\7H\u0306\nH"+
		"\fH\16H\u0309\13H\3H\3H\5H\u030d\nH\3I\3I\3I\5I\u0312\nI\3J\3J\3J\5J\u0317"+
		"\nJ\3K\3K\3K\7K\u031c\nK\fK\16K\u031f\13K\3K\3K\3K\3K\3K\3L\3L\3L\7L\u0329"+
		"\nL\fL\16L\u032c\13L\3M\3M\3M\3N\3N\5N\u0333\nN\3N\3N\5N\u0337\nN\3N\3"+
		"N\5N\u033b\nN\5N\u033d\nN\3O\3O\5O\u0341\nO\3P\7P\u0344\nP\fP\16P\u0347"+
		"\13P\3P\3P\3P\3P\3P\3Q\3Q\3R\3R\3R\3R\3S\3S\3S\7S\u0357\nS\fS\16S\u035a"+
		"\13S\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u0369\nT\3T\3T\3T\3T\3"+
		"T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u0379\nT\3T\3T\3T\3T\3T\3T\3T\3T\3T\3"+
		"T\5T\u0385\nT\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3"+
		"T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u03ad\n"+
		"T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u03bf\nT\3T\3T\3"+
		"T\3T\3T\3T\7T\u03c7\nT\fT\16T\u03ca\13T\3U\3U\3U\3U\3U\3U\3U\3U\3U\3U"+
		"\3U\3U\3U\3U\3U\3U\3U\3U\3U\5U\u03df\nU\3U\5U\u03e2\nU\3V\3V\3V\3V\3V"+
		"\3V\3V\3V\3V\5V\u03ed\nV\5V\u03ef\nV\3W\3W\3W\7W\u03f4\nW\fW\16W\u03f7"+
		"\13W\3W\5W\u03fa\nW\3X\3X\5X\u03fe\nX\3Y\3Y\5Y\u0402\nY\3Y\3Y\3Z\3Z\3"+
		"Z\3Z\3Z\3Z\3Z\3Z\3Z\3Z\3Z\7Z\u0411\nZ\fZ\16Z\u0414\13Z\3Z\3Z\5Z\u0418"+
		"\nZ\3[\3[\3[\3[\7[\u041e\n[\f[\16[\u0421\13[\3[\3[\3\\\3\\\3\\\3\\\3]"+
		"\3]\3]\3]\7]\u042d\n]\f]\16]\u0430\13]\3]\3]\3^\3^\5^\u0436\n^\3_\3_\5"+
		"_\u043a\n_\3`\3`\3`\5`\u043f\n`\3`\5`\u0442\n`\3a\3a\3a\3b\3b\3b\3b\3"+
		"c\3c\3c\5c\u044e\nc\3d\3d\3d\5d\u0453\nd\3e\3e\3e\3e\5e\u0459\ne\5e\u045b"+
		"\ne\3f\3f\3f\3f\3f\5f\u0462\nf\3g\3g\5g\u0466\ng\3g\3g\3h\3h\3h\7h\u046d"+
		"\nh\fh\16h\u0470\13h\3h\3h\3i\3i\3i\5i\u0477\ni\3j\3j\3j\5j\u047c\nj\3"+
		"k\3k\3k\3\u046e\3\u00a6l\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c"+
		"\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4"+
		"\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc"+
		"\u00ce\u00d0\u00d2\u00d4\2\17\5\2\34\34\'\'**\6\2\37!$%/\6089\6\2\4\4"+
		"\16\16\33\33<D\3\2KO\3\2il\3\2^_\4\2mnrr\3\2kl\3\2bf\4\2YYt~\3\2ij\3\2"+
		"UU\13\2\4\4\16\16\25\25\33\33\35\35\61\67:DIJ\177\177\2\u04df\2\u00d9"+
		"\3\2\2\2\4\u00f4\3\2\2\2\6\u00f8\3\2\2\2\b\u010a\3\2\2\2\n\u010f\3\2\2"+
		"\2\f\u0111\3\2\2\2\16\u011d\3\2\2\2\20\u012f\3\2\2\2\22\u013a\3\2\2\2"+
		"\24\u0144\3\2\2\2\26\u014b\3\2\2\2\30\u0153\3\2\2\2\32\u015b\3\2\2\2\34"+
		"\u0164\3\2\2\2\36\u0179\3\2\2\2 \u0182\3\2\2\2\"\u0184\3\2\2\2$\u0187"+
		"\3\2\2\2&\u0199\3\2\2\2(\u019d\3\2\2\2*\u01a1\3\2\2\2,\u01a5\3\2\2\2."+
		"\u01b4\3\2\2\2\60\u01bb\3\2\2\2\62\u01bd\3\2\2\2\64\u01c8\3\2\2\2\66\u01d5"+
		"\3\2\2\28\u01db\3\2\2\2:\u01e3\3\2\2\2<\u01ea\3\2\2\2>\u01ec\3\2\2\2@"+
		"\u0202\3\2\2\2B\u0208\3\2\2\2D\u020b\3\2\2\2F\u0219\3\2\2\2H\u021b\3\2"+
		"\2\2J\u021f\3\2\2\2L\u0225\3\2\2\2N\u0230\3\2\2\2P\u0236\3\2\2\2R\u023e"+
		"\3\2\2\2T\u0240\3\2\2\2V\u024a\3\2\2\2X\u0254\3\2\2\2Z\u025b\3\2\2\2\\"+
		"\u025d\3\2\2\2^\u026d\3\2\2\2`\u0276\3\2\2\2b\u027c\3\2\2\2d\u0298\3\2"+
		"\2\2f\u029a\3\2\2\2h\u02a1\3\2\2\2j\u02a7\3\2\2\2l\u02ab\3\2\2\2n\u02b1"+
		"\3\2\2\2p\u02be\3\2\2\2r\u02c4\3\2\2\2t\u02c8\3\2\2\2v\u02ce\3\2\2\2x"+
		"\u02d4\3\2\2\2z\u02d8\3\2\2\2|\u02dc\3\2\2\2~\u02e0\3\2\2\2\u0080\u02e4"+
		"\3\2\2\2\u0082\u02eb\3\2\2\2\u0084\u02f0\3\2\2\2\u0086\u02f9\3\2\2\2\u0088"+
		"\u02fb\3\2\2\2\u008a\u02fe\3\2\2\2\u008c\u0302\3\2\2\2\u008e\u0307\3\2"+
		"\2\2\u0090\u030e\3\2\2\2\u0092\u0313\3\2\2\2\u0094\u0318\3\2\2\2\u0096"+
		"\u0325\3\2\2\2\u0098\u032d\3\2\2\2\u009a\u033c\3\2\2\2\u009c\u0340\3\2"+
		"\2\2\u009e\u0345\3\2\2\2\u00a0\u034d\3\2\2\2\u00a2\u034f\3\2\2\2\u00a4"+
		"\u0353\3\2\2\2\u00a6\u0368\3\2\2\2\u00a8\u03e1\3\2\2\2\u00aa\u03ee\3\2"+
		"\2\2\u00ac\u03f9\3\2\2\2\u00ae\u03fb\3\2\2\2\u00b0\u03ff\3\2\2\2\u00b2"+
		"\u0405\3\2\2\2\u00b4\u0419\3\2\2\2\u00b6\u0424\3\2\2\2\u00b8\u0428\3\2"+
		"\2\2\u00ba\u0435\3\2\2\2\u00bc\u0439\3\2\2\2\u00be\u0441\3\2\2\2\u00c0"+
		"\u0443\3\2\2\2\u00c2\u0446\3\2\2\2\u00c4\u044d\3\2\2\2\u00c6\u0452\3\2"+
		"\2\2\u00c8\u045a\3\2\2\2\u00ca\u0461\3\2\2\2\u00cc\u0463\3\2\2\2\u00ce"+
		"\u0469\3\2\2\2\u00d0\u0476\3\2\2\2\u00d2\u047b\3\2\2\2\u00d4\u047d\3\2"+
		"\2\2\u00d6\u00d8\5\4\3\2\u00d7\u00d6\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dc\3\2\2\2\u00db\u00d9\3\2"+
		"\2\2\u00dc\u00dd\7\2\2\3\u00dd\3\3\2\2\2\u00de\u00e0\5\b\5\2\u00df\u00de"+
		"\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2"+
		"\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00f5\5\f\7\2\u00e5\u00e7\5\b"+
		"\5\2\u00e6\u00e5\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8"+
		"\u00e9\3\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00f5\5\16"+
		"\b\2\u00ec\u00ee\5\b\5\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef"+
		"\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef\3\2"+
		"\2\2\u00f2\u00f5\5\26\f\2\u00f3\u00f5\7V\2\2\u00f4\u00e1\3\2\2\2\u00f4"+
		"\u00e8\3\2\2\2\u00f4\u00ef\3\2\2\2\u00f4\u00f3\3\2\2\2\u00f5\5\3\2\2\2"+
		"\u00f6\u00f9\5\b\5\2\u00f7\u00f9\t\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f7"+
		"\3\2\2\2\u00f9\7\3\2\2\2\u00fa\u010b\5T+\2\u00fb\u0109\7!\2\2\u00fc\u0109"+
		"\7 \2\2\u00fd\u0109\7\37\2\2\u00fe\u0109\7$\2\2\u00ff\u0109\7\3\2\2\u0100"+
		"\u0109\7\22\2\2\u0101\u0109\7/\2\2\u0102\u0109\7\60\2\2\u0103\u0109\7"+
		"9\2\2\u0104\u0109\7%\2\2\u0105\u0109\78\2\2\u0106\u0109\5\u00d0i\2\u0107"+
		"\u0109\5\u00d2j\2\u0108\u00fb\3\2\2\2\u0108\u00fc\3\2\2\2\u0108\u00fd"+
		"\3\2\2\2\u0108\u00fe\3\2\2\2\u0108\u00ff\3\2\2\2\u0108\u0100\3\2\2\2\u0108"+
		"\u0101\3\2\2\2\u0108\u0102\3\2\2\2\u0108\u0103\3\2\2\2\u0108\u0104\3\2"+
		"\2\2\u0108\u0105\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0107\3\2\2\2\u0109"+
		"\u010b\3\2\2\2\u010a\u00fa\3\2\2\2\u010a\u0108\3\2\2\2\u010b\t\3\2\2\2"+
		"\u010c\u0110\7\22\2\2\u010d\u0110\7*\2\2\u010e\u0110\5T+\2\u010f\u010c"+
		"\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u010e\3\2\2\2\u0110\13\3\2\2\2\u0111"+
		"\u0112\7\t\2\2\u0112\u0115\5\u00d4k\2\u0113\u0114\7\21\2\2\u0114\u0116"+
		"\5@!\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0119\3\2\2\2\u0117"+
		"\u0118\7\30\2\2\u0118\u011a\5\30\r\2\u0119\u0117\3\2\2\2\u0119\u011a\3"+
		"\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\5\32\16\2\u011c\r\3\2\2\2\u011d"+
		"\u011e\7\20\2\2\u011e\u0121\5\u00d4k\2\u011f\u0120\7\30\2\2\u0120\u0122"+
		"\5\30\r\2\u0121\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\3\2\2\2"+
		"\u0123\u0125\7R\2\2\u0124\u0126\5\20\t\2\u0125\u0124\3\2\2\2\u0125\u0126"+
		"\3\2\2\2\u0126\u0128\3\2\2\2\u0127\u0129\7W\2\2\u0128\u0127\3\2\2\2\u0128"+
		"\u0129\3\2\2\2\u0129\u012b\3\2\2\2\u012a\u012c\5\24\13\2\u012b\u012a\3"+
		"\2\2\2\u012b\u012c\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012e\7S\2\2\u012e"+
		"\17\3\2\2\2\u012f\u0134\5\22\n\2\u0130\u0131\7W\2\2\u0131\u0133\5\22\n"+
		"\2\u0132\u0130\3\2\2\2\u0133\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135"+
		"\3\2\2\2\u0135\21\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u0139\5T+\2\u0138"+
		"\u0137\3\2\2\2\u0139\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2"+
		"\2\2\u013b\u013d\3\2\2\2\u013c\u013a\3\2\2\2\u013d\u013f\5\u00d4k\2\u013e"+
		"\u0140\5\u00ccg\2\u013f\u013e\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0142"+
		"\3\2\2\2\u0141\u0143\5\32\16\2\u0142\u0141\3\2\2\2\u0142\u0143\3\2\2\2"+
		"\u0143\23\3\2\2\2\u0144\u0148\7V\2\2\u0145\u0147\5\36\20\2\u0146\u0145"+
		"\3\2\2\2\u0147\u014a\3\2\2\2\u0148\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149"+
		"\25\3\2\2\2\u014a\u0148\3\2\2\2\u014b\u014c\7\32\2\2\u014c\u014f\5\u00d4"+
		"k\2\u014d\u014e\7\21\2\2\u014e\u0150\5\30\r\2\u014f\u014d\3\2\2\2\u014f"+
		"\u0150\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0152\5\34\17\2\u0152\27\3\2"+
		"\2\2\u0153\u0158\5@!\2\u0154\u0155\7W\2\2\u0155\u0157\5@!\2\u0156\u0154"+
		"\3\2\2\2\u0157\u015a\3\2\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159"+
		"\31\3\2\2\2\u015a\u0158\3\2\2\2\u015b\u015f\7R\2\2\u015c\u015e\5\36\20"+
		"\2\u015d\u015c\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u015d\3\2\2\2\u015f\u0160"+
		"\3\2\2\2\u0160\u0162\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0163\7S\2\2\u0163"+
		"\33\3\2\2\2\u0164\u0168\7R\2\2\u0165\u0167\5.\30\2\u0166\u0165\3\2\2\2"+
		"\u0167\u016a\3\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u016b"+
		"\3\2\2\2\u016a\u0168\3\2\2\2\u016b\u016c\7S\2\2\u016c\35\3\2\2\2\u016d"+
		"\u017a\7V\2\2\u016e\u0170\7$\2\2\u016f\u016e\3\2\2\2\u016f\u0170\3\2\2"+
		"\2\u0170\u0171\3\2\2\2\u0171\u017a\5^\60\2\u0172\u0174\5\6\4\2\u0173\u0172"+
		"\3\2\2\2\u0174\u0177\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2\2\2\u0176"+
		"\u0178\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u017a\5 \21\2\u0179\u016d\3\2"+
		"\2\2\u0179\u016f\3\2\2\2\u0179\u0175\3\2\2\2\u017a\37\3\2\2\2\u017b\u0183"+
		"\5$\23\2\u017c\u0183\5(\25\2\u017d\u0183\5&\24\2\u017e\u0183\5\26\f\2"+
		"\u017f\u0183\5\f\7\2\u0180\u0183\5\16\b\2\u0181\u0183\5*\26\2\u0182\u017b"+
		"\3\2\2\2\u0182\u017c\3\2\2\2\u0182\u017d\3\2\2\2\u0182\u017e\3\2\2\2\u0182"+
		"\u017f\3\2\2\2\u0182\u0180\3\2\2\2\u0182\u0181\3\2\2\2\u0183!\3\2\2\2"+
		"\u0184\u0185\t\3\2\2\u0185#\3\2\2\2\u0186\u0188\5T+\2\u0187\u0186\3\2"+
		"\2\2\u0187\u0188\3\2\2\2\u0188\u018c\3\2\2\2\u0189\u018b\5\"\22\2\u018a"+
		"\u0189\3\2\2\2\u018b\u018e\3\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2"+
		"\2\2\u018d\u0191\3\2\2\2\u018e\u018c\3\2\2\2\u018f\u0192\5@!\2\u0190\u0192"+
		"\7,\2\2\u0191\u018f\3\2\2\2\u0191\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193"+
		"\u0194\5\u00d4k\2\u0194\u0197\5J&\2\u0195\u0198\5^\60\2\u0196\u0198\7"+
		"V\2\2\u0197\u0195\3\2\2\2\u0197\u0196\3\2\2\2\u0198%\3\2\2\2\u0199\u019a"+
		"\5P)\2\u019a\u019b\5J&\2\u019b\u019c\5^\60\2\u019c\'\3\2\2\2\u019d\u019e"+
		"\5@!\2\u019e\u019f\58\35\2\u019f\u01a0\7V\2\2\u01a0)\3\2\2\2\u01a1\u01a2"+
		"\5@!\2\u01a2\u01a3\58\35\2\u01a3\u01a4\5,\27\2\u01a4+\3\2\2\2\u01a5\u01a6"+
		"\7R\2\2\u01a6\u01a8\5\u008eH\2\u01a7\u01a9\5\u008eH\2\u01a8\u01a7\3\2"+
		"\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ab\7S\2\2\u01ab"+
		"-\3\2\2\2\u01ac\u01ae\5\6\4\2\u01ad\u01ac\3\2\2\2\u01ae\u01b1\3\2\2\2"+
		"\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01b2\3\2\2\2\u01b1\u01af"+
		"\3\2\2\2\u01b2\u01b5\5\60\31\2\u01b3\u01b5\7V\2\2\u01b4\u01af\3\2\2\2"+
		"\u01b4\u01b3\3\2\2\2\u01b5/\3\2\2\2\u01b6\u01bc\5\62\32\2\u01b7\u01bc"+
		"\5\66\34\2\u01b8\u01bc\5\26\f\2\u01b9\u01bc\5\f\7\2\u01ba\u01bc\5\16\b"+
		"\2\u01bb\u01b6\3\2\2\2\u01bb\u01b7\3\2\2\2\u01bb\u01b8\3\2\2\2\u01bb\u01b9"+
		"\3\2\2\2\u01bb\u01ba\3\2\2\2\u01bc\61\3\2\2\2\u01bd\u01be\5@!\2\u01be"+
		"\u01c3\5\64\33\2\u01bf\u01c0\7W\2\2\u01c0\u01c2\5\64\33\2\u01c1\u01bf"+
		"\3\2\2\2\u01c2\u01c5\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4"+
		"\u01c6\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c6\u01c7\7V\2\2\u01c7\63\3\2\2\2"+
		"\u01c8\u01cd\5\u00d4k\2\u01c9\u01ca\7T\2\2\u01ca\u01cc\7U\2\2\u01cb\u01c9"+
		"\3\2\2\2\u01cc\u01cf\3\2\2\2\u01cd\u01cb\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce"+
		"\u01d0\3\2\2\2\u01cf\u01cd\3\2\2\2\u01d0\u01d1\7Y\2\2\u01d1\u01d2\5<\37"+
		"\2\u01d2\65\3\2\2\2\u01d3\u01d6\5@!\2\u01d4\u01d6\7,\2\2\u01d5\u01d3\3"+
		"\2\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d8\5\u00d4k\2"+
		"\u01d8\u01d9\5J&\2\u01d9\u01da\7V\2\2\u01da\67\3\2\2\2\u01db\u01e0\5:"+
		"\36\2\u01dc\u01dd\7W\2\2\u01dd\u01df\5:\36\2\u01de\u01dc\3\2\2\2\u01df"+
		"\u01e2\3\2\2\2\u01e0\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e19\3\2\2\2"+
		"\u01e2\u01e0\3\2\2\2\u01e3\u01e6\5\u00d4k\2\u01e4\u01e5\7Y\2\2\u01e5\u01e7"+
		"\5<\37\2\u01e6\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7;\3\2\2\2\u01e8"+
		"\u01eb\5> \2\u01e9\u01eb\5\u00a6T\2\u01ea\u01e8\3\2\2\2\u01ea\u01e9\3"+
		"\2\2\2\u01eb=\3\2\2\2\u01ec\u01f8\7R\2\2\u01ed\u01f2\5<\37\2\u01ee\u01ef"+
		"\7W\2\2\u01ef\u01f1\5<\37\2\u01f0\u01ee\3\2\2\2\u01f1\u01f4\3\2\2\2\u01f2"+
		"\u01f0\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3\u01f6\3\2\2\2\u01f4\u01f2\3\2"+
		"\2\2\u01f5\u01f7\7W\2\2\u01f6\u01f5\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7"+
		"\u01f9\3\2\2\2\u01f8\u01ed\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9\u01fa\3\2"+
		"\2\2\u01fa\u01fb\7S\2\2\u01fb?\3\2\2\2\u01fc\u01fd\5D#\2\u01fd\u01fe\5"+
		"B\"\2\u01fe\u0203\3\2\2\2\u01ff\u0200\5F$\2\u0200\u0201\5B\"\2\u0201\u0203"+
		"\3\2\2\2\u0202\u01fc\3\2\2\2\u0202\u01ff\3\2\2\2\u0203A\3\2\2\2\u0204"+
		"\u0205\7T\2\2\u0205\u0207\7U\2\2\u0206\u0204\3\2\2\2\u0207\u020a\3\2\2"+
		"\2\u0208\u0206\3\2\2\2\u0208\u0209\3\2\2\2\u0209C\3\2\2\2\u020a\u0208"+
		"\3\2\2\2\u020b\u020d\5\u00d4k\2\u020c\u020e\5H%\2\u020d\u020c\3\2\2\2"+
		"\u020d\u020e\3\2\2\2\u020e\u0216\3\2\2\2\u020f\u0210\7X\2\2\u0210\u0212"+
		"\5\u00d4k\2\u0211\u0213\5H%\2\u0212\u0211\3\2\2\2\u0212\u0213\3\2\2\2"+
		"\u0213\u0215\3\2\2\2\u0214\u020f\3\2\2\2\u0215\u0218\3\2\2\2\u0216\u0214"+
		"\3\2\2\2\u0216\u0217\3\2\2\2\u0217E\3\2\2\2\u0218\u0216\3\2\2\2\u0219"+
		"\u021a\t\4\2\2\u021aG\3\2\2\2\u021b\u021c\7]\2\2\u021c\u021d\5\30\r\2"+
		"\u021d\u021e\7\\\2\2\u021eI\3\2\2\2\u021f\u0221\7P\2\2\u0220\u0222\5L"+
		"\'\2\u0221\u0220\3\2\2\2\u0221\u0222\3\2\2\2\u0222\u0223\3\2\2\2\u0223"+
		"\u0224\7Q\2\2\u0224K\3\2\2\2\u0225\u022a\5N(\2\u0226\u0227\7W\2\2\u0227"+
		"\u0229\5N(\2\u0228\u0226\3\2\2\2\u0229\u022c\3\2\2\2\u022a\u0228\3\2\2"+
		"\2\u022a\u022b\3\2\2\2\u022bM\3\2\2\2\u022c\u022a\3\2\2\2\u022d\u022f"+
		"\5\n\6\2\u022e\u022d\3\2\2\2\u022f\u0232\3\2\2\2\u0230\u022e\3\2\2\2\u0230"+
		"\u0231\3\2\2\2\u0231\u0233\3\2\2\2\u0232\u0230\3\2\2\2\u0233\u0234\5@"+
		"!\2\u0234\u0235\5\u00d4k\2\u0235O\3\2\2\2\u0236\u023b\5\u00d4k\2\u0237"+
		"\u0238\7X\2\2\u0238\u023a\5\u00d4k\2\u0239\u0237\3\2\2\2\u023a\u023d\3"+
		"\2\2\2\u023b\u0239\3\2\2\2\u023b\u023c\3\2\2\2\u023cQ\3\2\2\2\u023d\u023b"+
		"\3\2\2\2\u023e\u023f\t\5\2\2\u023fS\3\2\2\2\u0240\u0241\7\u0080\2\2\u0241"+
		"\u0248\5P)\2\u0242\u0245\7P\2\2\u0243\u0246\5V,\2\u0244\u0246\5Z.\2\u0245"+
		"\u0243\3\2\2\2\u0245\u0244\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0247\3\2"+
		"\2\2\u0247\u0249\7Q\2\2\u0248\u0242\3\2\2\2\u0248\u0249\3\2\2\2\u0249"+
		"U\3\2\2\2\u024a\u0251\5X-\2\u024b\u024d\7W\2\2\u024c\u024b\3\2\2\2\u024c"+
		"\u024d\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u0250\5X-\2\u024f\u024c\3\2\2"+
		"\2\u0250\u0253\3\2\2\2\u0251\u024f\3\2\2\2\u0251\u0252\3\2\2\2\u0252W"+
		"\3\2\2\2\u0253\u0251\3\2\2\2\u0254\u0255\5\u00d4k\2\u0255\u0256\7Y\2\2"+
		"\u0256\u0257\5Z.\2\u0257Y\3\2\2\2\u0258\u025c\5\u00a6T\2\u0259\u025c\5"+
		"T+\2\u025a\u025c\5\\/\2\u025b\u0258\3\2\2\2\u025b\u0259\3\2\2\2\u025b"+
		"\u025a\3\2\2\2\u025c[\3\2\2\2\u025d\u0266\7R\2\2\u025e\u0263\5Z.\2\u025f"+
		"\u0260\7W\2\2\u0260\u0262\5Z.\2\u0261\u025f\3\2\2\2\u0262\u0265\3\2\2"+
		"\2\u0263\u0261\3\2\2\2\u0263\u0264\3\2\2\2\u0264\u0267\3\2\2\2\u0265\u0263"+
		"\3\2\2\2\u0266\u025e\3\2\2\2\u0266\u0267\3\2\2\2\u0267\u0269\3\2\2\2\u0268"+
		"\u026a\7W\2\2\u0269\u0268\3\2\2\2\u0269\u026a\3\2\2\2\u026a\u026b\3\2"+
		"\2\2\u026b\u026c\7S\2\2\u026c]\3\2\2\2\u026d\u0271\7R\2\2\u026e\u0270"+
		"\5d\63\2\u026f\u026e\3\2\2\2\u0270\u0273\3\2\2\2\u0271\u026f\3\2\2\2\u0271"+
		"\u0272\3\2\2\2\u0272\u0274\3\2\2\2\u0273\u0271\3\2\2\2\u0274\u0275\7S"+
		"\2\2\u0275_\3\2\2\2\u0276\u0277\5b\62\2\u0277\u0278\7V\2\2\u0278a\3\2"+
		"\2\2\u0279\u027b\5\n\6\2\u027a\u0279\3\2\2\2\u027b\u027e\3\2\2\2\u027c"+
		"\u027a\3\2\2\2\u027c\u027d\3\2\2\2\u027d\u027f\3\2\2\2\u027e\u027c\3\2"+
		"\2\2\u027f\u0280\5@!\2\u0280\u0281\58\35\2\u0281c\3\2\2\2\u0282\u0299"+
		"\5^\60\2\u0283\u0299\5`\61\2\u0284\u0299\5f\64\2\u0285\u0299\5h\65\2\u0286"+
		"\u0299\5j\66\2\u0287\u0299\5l\67\2\u0288\u0299\5n8\2\u0289\u0299\5p9\2"+
		"\u028a\u0299\5r:\2\u028b\u0299\5t;\2\u028c\u0299\5v<\2\u028d\u0299\5x"+
		"=\2\u028e\u0299\5z>\2\u028f\u0299\5|?\2\u0290\u0299\5~@\2\u0291\u0299"+
		"\5\u0080A\2\u0292\u0299\5\u0082B\2\u0293\u0299\5\u0084C\2\u0294\u0299"+
		"\5\u0086D\2\u0295\u0299\5\u0088E\2\u0296\u0299\5\u008aF\2\u0297\u0299"+
		"\5\u008cG\2\u0298\u0282\3\2\2\2\u0298\u0283\3\2\2\2\u0298\u0284\3\2\2"+
		"\2\u0298\u0285\3\2\2\2\u0298\u0286\3\2\2\2\u0298\u0287\3\2\2\2\u0298\u0288"+
		"\3\2\2\2\u0298\u0289\3\2\2\2\u0298\u028a\3\2\2\2\u0298\u028b\3\2\2\2\u0298"+
		"\u028c\3\2\2\2\u0298\u028d\3\2\2\2\u0298\u028e\3\2\2\2\u0298\u028f\3\2"+
		"\2\2\u0298\u0290\3\2\2\2\u0298\u0291\3\2\2\2\u0298\u0292\3\2\2\2\u0298"+
		"\u0293\3\2\2\2\u0298\u0294\3\2\2\2\u0298\u0295\3\2\2\2\u0298\u0296\3\2"+
		"\2\2\u0298\u0297\3\2\2\2\u0299e\3\2\2\2\u029a\u029b\7\26\2\2\u029b\u029c"+
		"\5\u00a2R\2\u029c\u029f\5d\63\2\u029d\u029e\7\17\2\2\u029e\u02a0\5d\63"+
		"\2\u029f\u029d\3\2\2\2\u029f\u02a0\3\2\2\2\u02a0g\3\2\2\2\u02a1\u02a2"+
		"\7\25\2\2\u02a2\u02a3\7P\2\2\u02a3\u02a4\5\u009aN\2\u02a4\u02a5\7Q\2\2"+
		"\u02a5\u02a6\5d\63\2\u02a6i\3\2\2\2\u02a7\u02a8\7.\2\2\u02a8\u02a9\5\u00a2"+
		"R\2\u02a9\u02aa\5d\63\2\u02aak\3\2\2\2\u02ab\u02ac\7\r\2\2\u02ac\u02ad"+
		"\5d\63\2\u02ad\u02ae\7.\2\2\u02ae\u02af\5\u00a2R\2\u02af\u02b0\7V\2\2"+
		"\u02b0m\3\2\2\2\u02b1\u02b2\7+\2\2\u02b2\u02bc\5^\60\2\u02b3\u02b5\5\u0094"+
		"K\2\u02b4\u02b3\3\2\2\2\u02b5\u02b6\3\2\2\2\u02b6\u02b4\3\2\2\2\u02b6"+
		"\u02b7\3\2\2\2\u02b7\u02b9\3\2\2\2\u02b8\u02ba\5\u0098M\2\u02b9\u02b8"+
		"\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bd\3\2\2\2\u02bb\u02bd\5\u0098M"+
		"\2\u02bc\u02b4\3\2\2\2\u02bc\u02bb\3\2\2\2\u02bdo\3\2\2\2\u02be\u02c0"+
		"\7\"\2\2\u02bf\u02c1\5\u00a6T\2\u02c0\u02bf\3\2\2\2\u02c0\u02c1\3\2\2"+
		"\2\u02c1\u02c2\3\2\2\2\u02c2\u02c3\7V\2\2\u02c3q\3\2\2\2\u02c4\u02c5\7"+
		")\2\2\u02c5\u02c6\5\u00a6T\2\u02c6\u02c7\7V\2\2\u02c7s\3\2\2\2\u02c8\u02ca"+
		"\7\5\2\2\u02c9\u02cb\5\u00d4k\2\u02ca\u02c9\3\2\2\2\u02ca\u02cb\3\2\2"+
		"\2\u02cb\u02cc\3\2\2\2\u02cc\u02cd\7V\2\2\u02cdu\3\2\2\2\u02ce\u02d0\7"+
		"\13\2\2\u02cf\u02d1\5\u00d4k\2\u02d0\u02cf\3\2\2\2\u02d0\u02d1\3\2\2\2"+
		"\u02d1\u02d2\3\2\2\2\u02d2\u02d3\7V\2\2\u02d3w\3\2\2\2\u02d4\u02d5\7\62"+
		"\2\2\u02d5\u02d6\5\u00a6T\2\u02d6\u02d7\7V\2\2\u02d7y\3\2\2\2\u02d8\u02d9"+
		"\7\64\2\2\u02d9\u02da\5\u00a6T\2\u02da\u02db\7V\2\2\u02db{\3\2\2\2\u02dc"+
		"\u02dd\7\65\2\2\u02dd\u02de\5\u00a6T\2\u02de\u02df\7V\2\2\u02df}\3\2\2"+
		"\2\u02e0\u02e1\7\66\2\2\u02e1\u02e2\5\u00a6T\2\u02e2\u02e3\7V\2\2\u02e3"+
		"\177\3\2\2\2\u02e4\u02e5\7\63\2\2\u02e5\u02e7\5\u00a6T\2\u02e6\u02e8\5"+
		"\u00d4k\2\u02e7\u02e6\3\2\2\2\u02e7\u02e8\3\2\2\2\u02e8\u02e9\3\2\2\2"+
		"\u02e9\u02ea\7V\2\2\u02ea\u0081\3\2\2\2\u02eb\u02ec\7\67\2\2\u02ec\u02ed"+
		"\5\u00a6T\2\u02ed\u02ee\5\u00a6T\2\u02ee\u02ef\7V\2\2\u02ef\u0083\3\2"+
		"\2\2\u02f0\u02f1\7E\2\2\u02f1\u02f3\7P\2\2\u02f2\u02f4\5\u00a4S\2\u02f3"+
		"\u02f2\3\2\2\2\u02f3\u02f4\3\2\2\2\u02f4\u02f5\3\2\2\2\u02f5\u02f7\7Q"+
		"\2\2\u02f6\u02f8\5^\60\2\u02f7\u02f6\3\2\2\2\u02f7\u02f8\3\2\2\2\u02f8"+
		"\u0085\3\2\2\2\u02f9\u02fa\7V\2\2\u02fa\u0087\3\2\2\2\u02fb\u02fc\5\u00a6"+
		"T\2\u02fc\u02fd\7V\2\2\u02fd\u0089\3\2\2\2\u02fe\u02ff\5\u00d4k\2\u02ff"+
		"\u0300\7a\2\2\u0300\u0301\5d\63\2\u0301\u008b\3\2\2\2\u0302\u0303\7\u0083"+
		"\2\2\u0303\u008d\3\2\2\2\u0304\u0306\5\6\4\2\u0305\u0304\3\2\2\2\u0306"+
		"\u0309\3\2\2\2\u0307\u0305\3\2\2\2\u0307\u0308\3\2\2\2\u0308\u030c\3\2"+
		"\2\2\u0309\u0307\3\2\2\2\u030a\u030d\5\u0090I\2\u030b\u030d\5\u0092J\2"+
		"\u030c\u030a\3\2\2\2\u030c\u030b\3\2\2\2\u030d\u008f\3\2\2\2\u030e\u0311"+
		"\7:\2\2\u030f\u0312\7V\2\2\u0310\u0312\5^\60\2\u0311\u030f\3\2\2\2\u0311"+
		"\u0310\3\2\2\2\u0312\u0091\3\2\2\2\u0313\u0316\7;\2\2\u0314\u0317\7V\2"+
		"\2\u0315\u0317\5^\60\2\u0316\u0314\3\2\2\2\u0316\u0315\3\2\2\2\u0317\u0093"+
		"\3\2\2\2\u0318\u0319\7\7\2\2\u0319\u031d\7P\2\2\u031a\u031c\5\n\6\2\u031b"+
		"\u031a\3\2\2\2\u031c\u031f\3\2\2\2\u031d\u031b\3\2\2\2\u031d\u031e\3\2"+
		"\2\2\u031e\u0320\3\2\2\2\u031f\u031d\3\2\2\2\u0320\u0321\5\u0096L\2\u0321"+
		"\u0322\5\u00d4k\2\u0322\u0323\7Q\2\2\u0323\u0324\5^\60\2\u0324\u0095\3"+
		"\2\2\2\u0325\u032a\5P)\2\u0326\u0327\7p\2\2\u0327\u0329\5P)\2\u0328\u0326"+
		"\3\2\2\2\u0329\u032c\3\2\2\2\u032a\u0328\3\2\2\2\u032a\u032b\3\2\2\2\u032b"+
		"\u0097\3\2\2\2\u032c\u032a\3\2\2\2\u032d\u032e\7\23\2\2\u032e\u032f\5"+
		"^\60\2\u032f\u0099\3\2\2\2\u0330\u033d\5\u009eP\2\u0331\u0333\5\u009c"+
		"O\2\u0332\u0331\3\2\2\2\u0332\u0333\3\2\2\2\u0333\u0334\3\2\2\2\u0334"+
		"\u0336\7V\2\2\u0335\u0337\5\u00a6T\2\u0336\u0335\3\2\2\2\u0336\u0337\3"+
		"\2\2\2\u0337\u0338\3\2\2\2\u0338\u033a\7V\2\2\u0339\u033b\5\u00a0Q\2\u033a"+
		"\u0339\3\2\2\2\u033a\u033b\3\2\2\2\u033b\u033d\3\2\2\2\u033c\u0330\3\2"+
		"\2\2\u033c\u0332\3\2\2\2\u033d\u009b\3\2\2\2\u033e\u0341\5b\62\2\u033f"+
		"\u0341\5\u00a4S\2\u0340\u033e\3\2\2\2\u0340\u033f\3\2\2\2\u0341\u009d"+
		"\3\2\2\2\u0342\u0344\5\n\6\2\u0343\u0342\3\2\2\2\u0344\u0347\3\2\2\2\u0345"+
		"\u0343\3\2\2\2\u0345\u0346\3\2\2\2\u0346\u0348\3\2\2\2\u0347\u0345\3\2"+
		"\2\2\u0348\u0349\5@!\2\u0349\u034a\5\u00d4k\2\u034a\u034b\7a\2\2\u034b"+
		"\u034c\5\u00a6T\2\u034c\u009f\3\2\2\2\u034d\u034e\5\u00a4S\2\u034e\u00a1"+
		"\3\2\2\2\u034f\u0350\7P\2\2\u0350\u0351\5\u00a6T\2\u0351\u0352\7Q\2\2"+
		"\u0352\u00a3\3\2\2\2\u0353\u0358\5\u00a6T\2\u0354\u0355\7W\2\2\u0355\u0357"+
		"\5\u00a6T\2\u0356\u0354\3\2\2\2\u0357\u035a\3\2\2\2\u0358\u0356\3\2\2"+
		"\2\u0358\u0359\3\2\2\2\u0359\u00a5\3\2\2\2\u035a\u0358\3\2\2\2\u035b\u035c"+
		"\bT\1\2\u035c\u035d\7\35\2\2\u035d\u0369\5\u00aaV\2\u035e\u035f\7P\2\2"+
		"\u035f\u0360\5@!\2\u0360\u0361\7Q\2\2\u0361\u0362\5\u00a6T\24\u0362\u0369"+
		"\3\2\2\2\u0363\u0364\t\6\2\2\u0364\u0369\5\u00a6T\22\u0365\u0366\t\7\2"+
		"\2\u0366\u0369\5\u00a6T\21\u0367\u0369\5\u00a8U\2\u0368\u035b\3\2\2\2"+
		"\u0368\u035e\3\2\2\2\u0368\u0363\3\2\2\2\u0368\u0365\3\2\2\2\u0368\u0367"+
		"\3\2\2\2\u0369\u03c8\3\2\2\2\u036a\u036b\f\20\2\2\u036b\u036c\t\b\2\2"+
		"\u036c\u03c7\5\u00a6T\21\u036d\u036e\f\17\2\2\u036e\u036f\t\t\2\2\u036f"+
		"\u03c7\5\u00a6T\20\u0370\u0378\f\16\2\2\u0371\u0372\7]\2\2\u0372\u0379"+
		"\7]\2\2\u0373\u0374\7\\\2\2\u0374\u0375\7\\\2\2\u0375\u0379\7\\\2\2\u0376"+
		"\u0377\7\\\2\2\u0377\u0379\7\\\2\2\u0378\u0371\3\2\2\2\u0378\u0373\3\2"+
		"\2\2\u0378\u0376\3\2\2\2\u0379\u037a\3\2\2\2\u037a\u03c7\5\u00a6T\17\u037b"+
		"\u0384\f\r\2\2\u037c\u037d\7]\2\2\u037d\u0385\7Y\2\2\u037e\u037f\7\\\2"+
		"\2\u037f\u0385\7Y\2\2\u0380\u0385\7Z\2\2\u0381\u0385\7[\2\2\u0382\u0385"+
		"\7\\\2\2\u0383\u0385\7]\2\2\u0384\u037c\3\2\2\2\u0384\u037e\3\2\2\2\u0384"+
		"\u0380\3\2\2\2\u0384\u0381\3\2\2\2\u0384\u0382\3\2\2\2\u0384\u0383\3\2"+
		"\2\2\u0385\u0386\3\2\2\2\u0386\u03c7\5\u00a6T\16\u0387\u0388\f\13\2\2"+
		"\u0388\u0389\t\n\2\2\u0389\u03c7\5\u00a6T\f\u038a\u038b\f\n\2\2\u038b"+
		"\u038c\7o\2\2\u038c\u03c7\5\u00a6T\13\u038d\u038e\f\t\2\2\u038e\u038f"+
		"\7q\2\2\u038f\u03c7\5\u00a6T\n\u0390\u0391\f\b\2\2\u0391\u0392\7p\2\2"+
		"\u0392\u03c7\5\u00a6T\t\u0393\u0394\f\7\2\2\u0394\u0395\7g\2\2\u0395\u03c7"+
		"\5\u00a6T\b\u0396\u0397\f\6\2\2\u0397\u0398\7h\2\2\u0398\u03c7\5\u00a6"+
		"T\7\u0399\u039a\f\5\2\2\u039a\u039b\7`\2\2\u039b\u039c\5\u00a6T\2\u039c"+
		"\u039d\7a\2\2\u039d\u039e\5\u00a6T\6\u039e\u03c7\3\2\2\2\u039f\u03a0\f"+
		"\4\2\2\u03a0\u03a1\t\13\2\2\u03a1\u03c7\5\u00a6T\4\u03a2\u03a3\f\34\2"+
		"\2\u03a3\u03a4\7X\2\2\u03a4\u03c7\5\u00d4k\2\u03a5\u03a6\f\33\2\2\u03a6"+
		"\u03a7\7X\2\2\u03a7\u03c7\7(\2\2\u03a8\u03a9\f\32\2\2\u03a9\u03aa\7X\2"+
		"\2\u03aa\u03ac\7\35\2\2\u03ab\u03ad\5\u00c2b\2\u03ac\u03ab\3\2\2\2\u03ac"+
		"\u03ad\3\2\2\2\u03ad\u03ae\3\2\2\2\u03ae\u03c7\5\u00b0Y\2\u03af\u03b0"+
		"\f\31\2\2\u03b0\u03b1\7X\2\2\u03b1\u03b2\7&\2\2\u03b2\u03c7\5\u00c8e\2"+
		"\u03b3\u03b4\f\30\2\2\u03b4\u03b5\7X\2\2\u03b5\u03c7\5\u00c0a\2\u03b6"+
		"\u03b7\f\27\2\2\u03b7\u03b8\7T\2\2\u03b8\u03b9\5\u00a6T\2\u03b9\u03ba"+
		"\7U\2\2\u03ba\u03c7\3\2\2\2\u03bb\u03bc\f\26\2\2\u03bc\u03be\7P\2\2\u03bd"+
		"\u03bf\5\u00a4S\2\u03be\u03bd\3\2\2\2\u03be\u03bf\3\2\2\2\u03bf\u03c0"+
		"\3\2\2\2\u03c0\u03c7\7Q\2\2\u03c1\u03c2\f\23\2\2\u03c2\u03c7\t\f\2\2\u03c3"+
		"\u03c4\f\f\2\2\u03c4\u03c5\7\31\2\2\u03c5\u03c7\5@!\2\u03c6\u036a\3\2"+
		"\2\2\u03c6\u036d\3\2\2\2\u03c6\u0370\3\2\2\2\u03c6\u037b\3\2\2\2\u03c6"+
		"\u0387\3\2\2\2\u03c6\u038a\3\2\2\2\u03c6\u038d\3\2\2\2\u03c6\u0390\3\2"+
		"\2\2\u03c6\u0393\3\2\2\2\u03c6\u0396\3\2\2\2\u03c6\u0399\3\2\2\2\u03c6"+
		"\u039f\3\2\2\2\u03c6\u03a2\3\2\2\2\u03c6\u03a5\3\2\2\2\u03c6\u03a8\3\2"+
		"\2\2\u03c6\u03af\3\2\2\2\u03c6\u03b3\3\2\2\2\u03c6\u03b6\3\2\2\2\u03c6"+
		"\u03bb\3\2\2\2\u03c6\u03c1\3\2\2\2\u03c6\u03c3\3\2\2\2\u03c7\u03ca\3\2"+
		"\2\2\u03c8\u03c6\3\2\2\2\u03c8\u03c9\3\2\2\2\u03c9\u00a7\3\2\2\2\u03ca"+
		"\u03c8\3\2\2\2\u03cb\u03cc\7P\2\2\u03cc\u03cd\5\u00a6T\2\u03cd\u03ce\7"+
		"Q\2\2\u03ce\u03e2\3\2\2\2\u03cf\u03e2\7(\2\2\u03d0\u03e2\7&\2\2\u03d1"+
		"\u03e2\5R*\2\u03d2\u03e2\5\u00d4k\2\u03d3\u03d4\5@!\2\u03d4\u03d5\7X\2"+
		"\2\u03d5\u03d6\7\t\2\2\u03d6\u03e2\3\2\2\2\u03d7\u03d8\7,\2\2\u03d8\u03d9"+
		"\7X\2\2\u03d9\u03e2\7\t\2\2\u03da\u03de\5\u00c2b\2\u03db\u03df\5\u00ca"+
		"f\2\u03dc\u03dd\7(\2\2\u03dd\u03df\5\u00ccg\2\u03de\u03db\3\2\2\2\u03de"+
		"\u03dc\3\2\2\2\u03df\u03e2\3\2\2\2\u03e0\u03e2\5\u00ceh\2\u03e1\u03cb"+
		"\3\2\2\2\u03e1\u03cf\3\2\2\2\u03e1\u03d0\3\2\2\2\u03e1\u03d1\3\2\2\2\u03e1"+
		"\u03d2\3\2\2\2\u03e1\u03d3\3\2\2\2\u03e1\u03d7\3\2\2\2\u03e1\u03da\3\2"+
		"\2\2\u03e1\u03e0\3\2\2\2\u03e2\u00a9\3\2\2\2\u03e3\u03e4\5\u00c2b\2\u03e4"+
		"\u03e5\5\u00acW\2\u03e5\u03e6\5\u00be`\2\u03e6\u03ef\3\2\2\2\u03e7\u03ec"+
		"\5\u00acW\2\u03e8\u03ed\5\u00b2Z\2\u03e9\u03ed\5\u00be`\2\u03ea\u03ed"+
		"\5\u00b4[\2\u03eb\u03ed\5\u00b8]\2\u03ec\u03e8\3\2\2\2\u03ec\u03e9\3\2"+
		"\2\2\u03ec\u03ea\3\2\2\2\u03ec\u03eb\3\2\2\2\u03ed\u03ef\3\2\2\2\u03ee"+
		"\u03e3\3\2\2\2\u03ee\u03e7\3\2\2\2\u03ef\u00ab\3\2\2\2\u03f0\u03f5\5\u00ae"+
		"X\2\u03f1\u03f2\7X\2\2\u03f2\u03f4\5\u00aeX\2\u03f3\u03f1\3\2\2\2\u03f4"+
		"\u03f7\3\2\2\2\u03f5\u03f3\3\2\2\2\u03f5\u03f6\3\2\2\2\u03f6\u03fa\3\2"+
		"\2\2\u03f7\u03f5\3\2\2\2\u03f8\u03fa\5F$\2\u03f9\u03f0\3\2\2\2\u03f9\u03f8"+
		"\3\2\2\2\u03fa\u00ad\3\2\2\2\u03fb\u03fd\5\u00d4k\2\u03fc\u03fe\5\u00c4"+
		"c\2\u03fd\u03fc\3\2\2\2\u03fd\u03fe\3\2\2\2\u03fe\u00af\3\2\2\2\u03ff"+
		"\u0401\5\u00d4k\2\u0400\u0402\5\u00c6d\2\u0401\u0400\3\2\2\2\u0401\u0402"+
		"\3\2\2\2\u0402\u0403\3\2\2\2\u0403\u0404\5\u00be`\2\u0404\u00b1\3\2\2"+
		"\2\u0405\u0417\7T\2\2\u0406\u0407\7U\2\2\u0407\u0408\5B\"\2\u0408\u0409"+
		"\5> \2\u0409\u0418\3\2\2\2\u040a\u040b\5\u00a6T\2\u040b\u0412\7U\2\2\u040c"+
		"\u040d\7T\2\2\u040d\u040e\5\u00a6T\2\u040e\u040f\7U\2\2\u040f\u0411\3"+
		"\2\2\2\u0410\u040c\3\2\2\2\u0411\u0414\3\2\2\2\u0412\u0410\3\2\2\2\u0412"+
		"\u0413\3\2\2\2\u0413\u0415\3\2\2\2\u0414\u0412\3\2\2\2\u0415\u0416\5B"+
		"\"\2\u0416\u0418\3\2\2\2\u0417\u0406\3\2\2\2\u0417\u040a\3\2\2\2\u0418"+
		"\u00b3\3\2\2\2\u0419\u041a\7R\2\2\u041a\u041f\5\u00b6\\\2\u041b\u041c"+
		"\7W\2\2\u041c\u041e\5\u00b6\\\2\u041d\u041b\3\2\2\2\u041e\u0421\3\2\2"+
		"\2\u041f\u041d\3\2\2\2\u041f\u0420\3\2\2\2\u0420\u0422\3\2\2\2\u0421\u041f"+
		"\3\2\2\2\u0422\u0423\7S\2\2\u0423\u00b5\3\2\2\2\u0424\u0425\5\u00bc_\2"+
		"\u0425\u0426\7s\2\2\u0426\u0427\5\u00ba^\2\u0427\u00b7\3\2\2\2\u0428\u0429"+
		"\7R\2\2\u0429\u042e\5\u00ba^\2\u042a\u042b\7W\2\2\u042b\u042d\5\u00ba"+
		"^\2\u042c\u042a\3\2\2\2\u042d\u0430\3\2\2\2\u042e\u042c\3\2\2\2\u042e"+
		"\u042f\3\2\2\2\u042f\u0431\3\2\2\2\u0430\u042e\3\2\2\2\u0431\u0432\7S"+
		"\2\2\u0432\u00b9\3\2\2\2\u0433\u0436\5R*\2\u0434\u0436\5\u00a6T\2\u0435"+
		"\u0433\3\2\2\2\u0435\u0434\3\2\2\2\u0436\u00bb\3\2\2\2\u0437\u043a\5\u00d4"+
		"k\2\u0438\u043a\5\u00a6T\2\u0439\u0437\3\2\2\2\u0439\u0438\3\2\2\2\u043a"+
		"\u00bd\3\2\2\2\u043b\u0442\5\u00ccg\2\u043c\u043e\7R\2\2\u043d\u043f\5"+
		"\u00a4S\2\u043e\u043d\3\2\2\2\u043e\u043f\3\2\2\2\u043f\u0440\3\2\2\2"+
		"\u0440\u0442\7S\2\2\u0441\u043b\3\2\2\2\u0441\u043c\3\2\2\2\u0442\u00bf"+
		"\3\2\2\2\u0443\u0444\5\u00c2b\2\u0444\u0445\5\u00caf\2\u0445\u00c1\3\2"+
		"\2\2\u0446\u0447\7]\2\2\u0447\u0448\5\30\r\2\u0448\u0449\7\\\2\2\u0449"+
		"\u00c3\3\2\2\2\u044a\u044b\7]\2\2\u044b\u044e\7\\\2\2\u044c\u044e\5H%"+
		"\2\u044d\u044a\3\2\2\2\u044d\u044c\3\2\2\2\u044e\u00c5\3\2\2\2\u044f\u0450"+
		"\7]\2\2\u0450\u0453\7\\\2\2\u0451\u0453\5\u00c2b\2\u0452\u044f\3\2\2\2"+
		"\u0452\u0451\3\2\2\2\u0453\u00c7\3\2\2\2\u0454\u045b\5\u00ccg\2\u0455"+
		"\u0456\7X\2\2\u0456\u0458\5\u00d4k\2\u0457\u0459\5\u00ccg\2\u0458\u0457"+
		"\3\2\2\2\u0458\u0459\3\2\2\2\u0459\u045b\3\2\2\2\u045a\u0454\3\2\2\2\u045a"+
		"\u0455\3\2\2\2\u045b\u00c9\3\2\2\2\u045c\u045d\7&\2\2\u045d\u0462\5\u00c8"+
		"e\2\u045e\u045f\5\u00d4k\2\u045f\u0460\5\u00ccg\2\u0460\u0462\3\2\2\2"+
		"\u0461\u045c\3\2\2\2\u0461\u045e\3\2\2\2\u0462\u00cb\3\2\2\2\u0463\u0465"+
		"\7P\2\2\u0464\u0466\5\u00a4S\2\u0465\u0464\3\2\2\2\u0465\u0466\3\2\2\2"+
		"\u0466\u0467\3\2\2\2\u0467\u0468\7Q\2\2\u0468\u00cd\3\2\2\2\u0469\u046e"+
		"\7T\2\2\u046a\u046d\5\u00ceh\2\u046b\u046d\n\r\2\2\u046c\u046a\3\2\2\2"+
		"\u046c\u046b\3\2\2\2\u046d\u0470\3\2\2\2\u046e\u046f\3\2\2\2\u046e\u046c"+
		"\3\2\2\2\u046f\u0471\3\2\2\2\u0470\u046e\3\2\2\2\u0471\u0472\7U\2\2\u0472"+
		"\u00cf\3\2\2\2\u0473\u0477\7I\2\2\u0474\u0475\7F\2\2\u0475\u0477\7H\2"+
		"\2\u0476\u0473\3\2\2\2\u0476\u0474\3\2\2\2\u0477\u00d1\3\2\2\2\u0478\u047c"+
		"\7J\2\2\u0479\u047a\7G\2\2\u047a\u047c\7H\2\2\u047b\u0478\3\2\2\2\u047b"+
		"\u0479\3\2\2\2\u047c\u00d3\3\2\2\2\u047d\u047e\t\16\2\2\u047e\u00d5\3"+
		"\2\2\2{\u00d9\u00e1\u00e8\u00ef\u00f4\u00f8\u0108\u010a\u010f\u0115\u0119"+
		"\u0121\u0125\u0128\u012b\u0134\u013a\u013f\u0142\u0148\u014f\u0158\u015f"+
		"\u0168\u016f\u0175\u0179\u0182\u0187\u018c\u0191\u0197\u01a8\u01af\u01b4"+
		"\u01bb\u01c3\u01cd\u01d5\u01e0\u01e6\u01ea\u01f2\u01f6\u01f8\u0202\u0208"+
		"\u020d\u0212\u0216\u0221\u022a\u0230\u023b\u0245\u0248\u024c\u0251\u025b"+
		"\u0263\u0266\u0269\u0271\u027c\u0298\u029f\u02b6\u02b9\u02bc\u02c0\u02ca"+
		"\u02d0\u02e7\u02f3\u02f7\u0307\u030c\u0311\u0316\u031d\u032a\u0332\u0336"+
		"\u033a\u033c\u0340\u0345\u0358\u0368\u0378\u0384\u03ac\u03be\u03c6\u03c8"+
		"\u03de\u03e1\u03ec\u03ee\u03f5\u03f9\u03fd\u0401\u0412\u0417\u041f\u042e"+
		"\u0435\u0439\u043e\u0441\u044d\u0452\u0458\u045a\u0461\u0465\u046c\u046e"+
		"\u0476\u047b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}