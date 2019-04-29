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
		ABSTRACT=1, BREAK=2, BYTE=3, CATCH=4, CHAR=5, CLASS=6, CONST=7, CONTINUE=8, 
		DEFAULT=9, DELETE=10, DO=11, ELSE=12, ENUM=13, EXTENDS=14, FINAL=15, FINALLY=16, 
		FLOAT=17, FOR=18, GET=19, GLOBAL=20, GOTO=21, IF=22, IMPLEMENTS=23, INHERITED=24, 
		INSERT=25, INSTANCEOF=26, INTERFACE=27, MERGE=28, NATIVE=29, NEW=30, NULL=31, 
		ON=32, OVERRIDE=33, PACKAGE=34, PRIVATE=35, PROTECTED=36, PUBLIC=37, RETURN=38, 
		RUNAS=39, SELECT=40, SET=41, SHARING=42, SHORT=43, STATIC=44, SUPER=45, 
		SWITCH=46, TESTMETHOD=47, THIS=48, THROW=49, TRANSIENT=50, TRY=51, UNDELETE=52, 
		UPDATE=53, UPSERT=54, VIRTUAL=55, VOID=56, WEBSERVICE=57, WHEN=58, WHILE=59, 
		WITH=60, WITHOUT=61, IntegerLiteral=62, NumberLiteral=63, BooleanLiteral=64, 
		StringLiteral=65, NullLiteral=66, LPAREN=67, RPAREN=68, LBRACE=69, RBRACE=70, 
		LBRACK=71, RBRACK=72, SEMI=73, COMMA=74, DOT=75, ASSIGN=76, LE=77, GE=78, 
		GT=79, LT=80, BANG=81, TILDE=82, QUESTION=83, COLON=84, EQUAL=85, TRIPLEEQUAL=86, 
		NOTEQUAL=87, LESSANDGREATER=88, TRIPLENOTEQUAL=89, AND=90, OR=91, INC=92, 
		DEC=93, ADD=94, SUB=95, MUL=96, DIV=97, BITAND=98, BITOR=99, CARET=100, 
		MOD=101, MAP=102, ADD_ASSIGN=103, SUB_ASSIGN=104, MUL_ASSIGN=105, DIV_ASSIGN=106, 
		AND_ASSIGN=107, OR_ASSIGN=108, XOR_ASSIGN=109, MOD_ASSIGN=110, LSHIFT_ASSIGN=111, 
		RSHIFT_ASSIGN=112, URSHIFT_ASSIGN=113, Identifier=114, AT=115, WS=116, 
		DOC_COMMENT=117, COMMENT=118, LINE_COMMENT=119;
	public static final int
		RULE_compilationUnit = 0, RULE_typeDeclaration = 1, RULE_classDeclaration = 2, 
		RULE_enumDeclaration = 3, RULE_enumConstants = 4, RULE_enumConstant = 5, 
		RULE_interfaceDeclaration = 6, RULE_typeList = 7, RULE_classBody = 8, 
		RULE_interfaceBody = 9, RULE_classBodyDeclaration = 10, RULE_modifier = 11, 
		RULE_memberDeclaration = 12, RULE_methodDeclaration = 13, RULE_constructorDeclaration = 14, 
		RULE_fieldDeclaration = 15, RULE_propertyDeclaration = 16, RULE_propertyBodyDeclaration = 17, 
		RULE_interfaceBodyDeclaration = 18, RULE_interfaceMemberDeclaration = 19, 
		RULE_constDeclaration = 20, RULE_constantDeclarator = 21, RULE_interfaceMethodDeclaration = 22, 
		RULE_variableDeclarators = 23, RULE_variableDeclarator = 24, RULE_variableInitializer = 25, 
		RULE_arrayInitializer = 26, RULE_typeRef = 27, RULE_arraySubscripts = 28, 
		RULE_typeName = 29, RULE_typeArguments = 30, RULE_formalParameters = 31, 
		RULE_formalParameterList = 32, RULE_formalParameter = 33, RULE_qualifiedName = 34, 
		RULE_literal = 35, RULE_annotation = 36, RULE_elementValuePairs = 37, 
		RULE_elementValuePair = 38, RULE_elementValue = 39, RULE_elementValueArrayInitializer = 40, 
		RULE_block = 41, RULE_localVariableDeclarationStatement = 42, RULE_localVariableDeclaration = 43, 
		RULE_statement = 44, RULE_ifStatement = 45, RULE_switchStatement = 46, 
		RULE_whenControl = 47, RULE_forStatement = 48, RULE_whileStatement = 49, 
		RULE_doWhileStatement = 50, RULE_tryStatement = 51, RULE_returnStatement = 52, 
		RULE_throwStatement = 53, RULE_breakStatement = 54, RULE_continueStatement = 55, 
		RULE_insertStatement = 56, RULE_updateStatement = 57, RULE_deleteStatement = 58, 
		RULE_undeleteStatement = 59, RULE_upsertStatement = 60, RULE_mergeStatement = 61, 
		RULE_runAsStatement = 62, RULE_emptyStatement = 63, RULE_expressionStatement = 64, 
		RULE_idStatement = 65, RULE_propertyBlock = 66, RULE_getter = 67, RULE_setter = 68, 
		RULE_catchClause = 69, RULE_catchType = 70, RULE_finallyBlock = 71, RULE_forControl = 72, 
		RULE_forInit = 73, RULE_enhancedForControl = 74, RULE_forUpdate = 75, 
		RULE_parExpression = 76, RULE_expressionList = 77, RULE_expression = 78, 
		RULE_primary = 79, RULE_creator = 80, RULE_createdName = 81, RULE_idCreatedNamePair = 82, 
		RULE_innerCreator = 83, RULE_arrayCreatorRest = 84, RULE_mapCreatorRest = 85, 
		RULE_mapCreatorRestPair = 86, RULE_setCreatorRest = 87, RULE_literalOrExpression = 88, 
		RULE_idOrExpression = 89, RULE_classCreatorRest = 90, RULE_explicitGenericInvocation = 91, 
		RULE_nonWildcardTypeArguments = 92, RULE_typeArgumentsOrDiamond = 93, 
		RULE_nonWildcardTypeArgumentsOrDiamond = 94, RULE_superSuffix = 95, RULE_explicitGenericInvocationSuffix = 96, 
		RULE_arguments = 97, RULE_soqlLiteral = 98, RULE_id = 99;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "typeDeclaration", "classDeclaration", "enumDeclaration", 
			"enumConstants", "enumConstant", "interfaceDeclaration", "typeList", 
			"classBody", "interfaceBody", "classBodyDeclaration", "modifier", "memberDeclaration", 
			"methodDeclaration", "constructorDeclaration", "fieldDeclaration", "propertyDeclaration", 
			"propertyBodyDeclaration", "interfaceBodyDeclaration", "interfaceMemberDeclaration", 
			"constDeclaration", "constantDeclarator", "interfaceMethodDeclaration", 
			"variableDeclarators", "variableDeclarator", "variableInitializer", "arrayInitializer", 
			"typeRef", "arraySubscripts", "typeName", "typeArguments", "formalParameters", 
			"formalParameterList", "formalParameter", "qualifiedName", "literal", 
			"annotation", "elementValuePairs", "elementValuePair", "elementValue", 
			"elementValueArrayInitializer", "block", "localVariableDeclarationStatement", 
			"localVariableDeclaration", "statement", "ifStatement", "switchStatement", 
			"whenControl", "forStatement", "whileStatement", "doWhileStatement", 
			"tryStatement", "returnStatement", "throwStatement", "breakStatement", 
			"continueStatement", "insertStatement", "updateStatement", "deleteStatement", 
			"undeleteStatement", "upsertStatement", "mergeStatement", "runAsStatement", 
			"emptyStatement", "expressionStatement", "idStatement", "propertyBlock", 
			"getter", "setter", "catchClause", "catchType", "finallyBlock", "forControl", 
			"forInit", "enhancedForControl", "forUpdate", "parExpression", "expressionList", 
			"expression", "primary", "creator", "createdName", "idCreatedNamePair", 
			"innerCreator", "arrayCreatorRest", "mapCreatorRest", "mapCreatorRestPair", 
			"setCreatorRest", "literalOrExpression", "idOrExpression", "classCreatorRest", 
			"explicitGenericInvocation", "nonWildcardTypeArguments", "typeArgumentsOrDiamond", 
			"nonWildcardTypeArgumentsOrDiamond", "superSuffix", "explicitGenericInvocationSuffix", 
			"arguments", "soqlLiteral", "id"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'abstract'", "'break'", "'byte'", "'catch'", "'char'", "'class'", 
			"'const'", "'continue'", "'default'", "'delete'", "'do'", "'else'", "'enum'", 
			"'extends'", "'final'", "'finally'", "'float'", "'for'", "'get'", "'global'", 
			"'goto'", "'if'", "'implements'", "'inherited'", "'insert'", "'instanceof'", 
			"'interface'", "'merge'", "'native'", "'new'", "'null'", "'on'", "'override'", 
			"'package'", "'private'", "'protected'", "'public'", "'return'", "'system.runas'", 
			"'select'", "'set'", "'sharing'", "'short'", "'static'", "'super'", "'switch'", 
			"'testmethod'", "'this'", "'throw'", "'transient'", "'try'", "'undelete'", 
			"'update'", "'upsert'", "'virtual'", "'void'", "'webservice'", "'when'", 
			"'while'", "'with'", "'without'", null, null, null, null, null, "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'<='", 
			"'>='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", "'==='", "'!='", 
			"'<>'", "'!=='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", "'*'", 
			"'/'", "'&'", "'|'", "'^'", "'%'", "'=>'", "'+='", "'-='", "'*='", "'/='", 
			"'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", "CONST", 
			"CONTINUE", "DEFAULT", "DELETE", "DO", "ELSE", "ENUM", "EXTENDS", "FINAL", 
			"FINALLY", "FLOAT", "FOR", "GET", "GLOBAL", "GOTO", "IF", "IMPLEMENTS", 
			"INHERITED", "INSERT", "INSTANCEOF", "INTERFACE", "MERGE", "NATIVE", 
			"NEW", "NULL", "ON", "OVERRIDE", "PACKAGE", "PRIVATE", "PROTECTED", "PUBLIC", 
			"RETURN", "RUNAS", "SELECT", "SET", "SHARING", "SHORT", "STATIC", "SUPER", 
			"SWITCH", "TESTMETHOD", "THIS", "THROW", "TRANSIENT", "TRY", "UNDELETE", 
			"UPDATE", "UPSERT", "VIRTUAL", "VOID", "WEBSERVICE", "WHEN", "WHILE", 
			"WITH", "WITHOUT", "IntegerLiteral", "NumberLiteral", "BooleanLiteral", 
			"StringLiteral", "NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", "LE", "GE", "GT", 
			"LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", "TRIPLEEQUAL", "NOTEQUAL", 
			"LESSANDGREATER", "TRIPLENOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", 
			"SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", "MOD", "MAP", "ADD_ASSIGN", 
			"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", 
			"XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", 
			"Identifier", "AT", "WS", "DOC_COMMENT", "COMMENT", "LINE_COMMENT"
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
		public TypeDeclarationContext typeDeclaration() {
			return getRuleContext(TypeDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ApexParser.EOF, 0); }
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			typeDeclaration();
			setState(201);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public InterfaceDeclarationContext interfaceDeclaration() {
			return getRuleContext(InterfaceDeclarationContext.class,0);
		}
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
			setState(224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(203);
					modifier();
					}
					}
					setState(208);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(209);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(210);
					modifier();
					}
					}
					setState(215);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(216);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(217);
					modifier();
					}
					}
					setState(222);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(223);
				interfaceDeclaration();
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
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(CLASS);
			setState(227);
			id();
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(228);
				match(EXTENDS);
				setState(229);
				typeRef();
				}
			}

			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(232);
				match(IMPLEMENTS);
				setState(233);
				typeList();
				}
			}

			setState(236);
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
		public EnumConstantsContext enumConstants() {
			return getRuleContext(EnumConstantsContext.class,0);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(ENUM);
			setState(239);
			id();
			setState(240);
			match(LBRACE);
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier || _la==AT) {
				{
				setState(241);
				enumConstants();
				}
			}

			setState(244);
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
		enterRule(_localctx, 8, RULE_enumConstants);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			enumConstant();
			setState(251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(247);
				match(COMMA);
				setState(248);
				enumConstant();
				}
				}
				setState(253);
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

	public static class EnumConstantContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
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
		enterRule(_localctx, 10, RULE_enumConstant);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(254);
					modifier();
					}
					} 
				}
				setState(259);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(260);
			id();
			setState(262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(261);
				arguments();
				}
			}

			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(264);
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
		enterRule(_localctx, 12, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(INTERFACE);
			setState(268);
			id();
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(269);
				match(EXTENDS);
				setState(270);
				typeList();
				}
			}

			setState(273);
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
		enterRule(_localctx, 14, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			typeRef();
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(276);
				match(COMMA);
				setState(277);
				typeRef();
				}
				}
				setState(282);
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
		enterRule(_localctx, 16, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(LBRACE);
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (LBRACE - 69)) | (1L << (SEMI - 69)) | (1L << (Identifier - 69)) | (1L << (AT - 69)))) != 0)) {
				{
				{
				setState(284);
				classBodyDeclaration();
				}
				}
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(290);
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
		enterRule(_localctx, 18, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			match(LBRACE);
			setState(296);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || ((((_la - 73)) & ~0x3f) == 0 && ((1L << (_la - 73)) & ((1L << (SEMI - 73)) | (1L << (Identifier - 73)) | (1L << (AT - 73)))) != 0)) {
				{
				{
				setState(293);
				interfaceBodyDeclaration();
				}
				}
				setState(298);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
		enterRule(_localctx, 20, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(313);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(301);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(302);
					match(STATIC);
					}
				}

				setState(305);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(309);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(306);
						modifier();
						}
						} 
					}
					setState(311);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				}
				setState(312);
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

	public static class ModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode GLOBAL() { return getToken(ApexParser.GLOBAL, 0); }
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode ABSTRACT() { return getToken(ApexParser.ABSTRACT, 0); }
		public TerminalNode FINAL() { return getToken(ApexParser.FINAL, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public TerminalNode WITHOUT() { return getToken(ApexParser.WITHOUT, 0); }
		public TerminalNode INHERITED() { return getToken(ApexParser.INHERITED, 0); }
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_modifier);
		try {
			setState(334);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(315);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(316);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(317);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(318);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(319);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(320);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(321);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(322);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(323);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(324);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(325);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(326);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(327);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(328);
				match(WITH);
				setState(329);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(330);
				match(WITHOUT);
				setState(331);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(332);
				match(INHERITED);
				setState(333);
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
		enterRule(_localctx, 24, RULE_memberDeclaration);
		try {
			setState(343);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(336);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(338);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(339);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(340);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(341);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(342);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_methodDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(345);
					modifier();
					}
					} 
				}
				setState(350);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			setState(353);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(351);
				typeRef();
				}
				break;
			case 2:
				{
				setState(352);
				match(VOID);
				}
				break;
			}
			setState(355);
			id();
			setState(356);
			formalParameters();
			setState(359);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(357);
				block();
				}
				break;
			case SEMI:
				{
				setState(358);
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
		enterRule(_localctx, 28, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			qualifiedName();
			setState(362);
			formalParameters();
			setState(363);
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
		enterRule(_localctx, 30, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			typeRef();
			setState(366);
			variableDeclarators();
			setState(367);
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
		enterRule(_localctx, 32, RULE_propertyDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			typeRef();
			setState(370);
			variableDeclarators();
			setState(371);
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
		enterRule(_localctx, 34, RULE_propertyBodyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			match(LBRACE);
			setState(374);
			propertyBlock();
			setState(376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				setState(375);
				propertyBlock();
				}
			}

			setState(378);
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
		enterRule(_localctx, 36, RULE_interfaceBodyDeclaration);
		try {
			int _alt;
			setState(388);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DELETE:
			case DO:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case GET:
			case GLOBAL:
			case GOTO:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SELECT:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case UNDELETE:
			case UPDATE:
			case UPSERT:
			case VIRTUAL:
			case VOID:
			case WEBSERVICE:
			case WHEN:
			case WHILE:
			case WITH:
			case WITHOUT:
			case Identifier:
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(383);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(380);
						modifier();
						}
						} 
					}
					setState(385);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
				setState(386);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
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
		enterRule(_localctx, 38, RULE_interfaceMemberDeclaration);
		try {
			setState(395);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(390);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(391);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(392);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(393);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(394);
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
		enterRule(_localctx, 40, RULE_constDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			typeRef();
			setState(398);
			constantDeclarator();
			setState(403);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(399);
				match(COMMA);
				setState(400);
				constantDeclarator();
				}
				}
				setState(405);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(406);
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
		enterRule(_localctx, 42, RULE_constantDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			id();
			setState(413);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(409);
				match(LBRACK);
				setState(410);
				match(RBRACK);
				}
				}
				setState(415);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(416);
			match(ASSIGN);
			setState(417);
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
		enterRule(_localctx, 44, RULE_interfaceMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(419);
				typeRef();
				}
				break;
			case 2:
				{
				setState(420);
				match(VOID);
				}
				break;
			}
			setState(423);
			id();
			setState(424);
			formalParameters();
			setState(425);
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
		enterRule(_localctx, 46, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(427);
			variableDeclarator();
			setState(432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(428);
				match(COMMA);
				setState(429);
				variableDeclarator();
				}
				}
				setState(434);
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
		enterRule(_localctx, 48, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(435);
			id();
			setState(438);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(436);
				match(ASSIGN);
				setState(437);
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
		enterRule(_localctx, 50, RULE_variableInitializer);
		try {
			setState(442);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(440);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DELETE:
			case DO:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case GET:
			case GLOBAL:
			case GOTO:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SELECT:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case UNDELETE:
			case UPDATE:
			case UPSERT:
			case VIRTUAL:
			case VOID:
			case WEBSERVICE:
			case WHEN:
			case WHILE:
			case WITH:
			case WITHOUT:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
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
				setState(441);
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
		enterRule(_localctx, 52, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			match(LBRACE);
			setState(456);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(445);
				variableInitializer();
				setState(450);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(446);
						match(COMMA);
						setState(447);
						variableInitializer();
						}
						} 
					}
					setState(452);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				setState(454);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(453);
					match(COMMA);
					}
				}

				}
			}

			setState(458);
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
		public List<TypeNameContext> typeName() {
			return getRuleContexts(TypeNameContext.class);
		}
		public TypeNameContext typeName(int i) {
			return getRuleContext(TypeNameContext.class,i);
		}
		public ArraySubscriptsContext arraySubscripts() {
			return getRuleContext(ArraySubscriptsContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(ApexParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ApexParser.DOT, i);
		}
		public TypeRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeRef; }
	}

	public final TypeRefContext typeRef() throws RecognitionException {
		TypeRefContext _localctx = new TypeRefContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_typeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(460);
			typeName();
			setState(465);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(461);
					match(DOT);
					setState(462);
					typeName();
					}
					} 
				}
				setState(467);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			}
			setState(468);
			arraySubscripts();
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
		enterRule(_localctx, 56, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(474);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(470);
					match(LBRACK);
					setState(471);
					match(RBRACK);
					}
					} 
				}
				setState(476);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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

	public static class TypeNameContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_typeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(477);
			id();
			setState(479);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(478);
				typeArguments();
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
		enterRule(_localctx, 60, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			match(LT);
			setState(482);
			typeList();
			setState(483);
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
		enterRule(_localctx, 62, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			match(LPAREN);
			setState(487);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier || _la==AT) {
				{
				setState(486);
				formalParameterList();
				}
			}

			setState(489);
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
		enterRule(_localctx, 64, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(491);
			formalParameter();
			setState(496);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(492);
				match(COMMA);
				setState(493);
				formalParameter();
				}
				}
				setState(498);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public FormalParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParameter; }
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_formalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(502);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(499);
					modifier();
					}
					} 
				}
				setState(504);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			setState(505);
			typeRef();
			setState(506);
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
		enterRule(_localctx, 68, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(508);
			id();
			setState(513);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(509);
				match(DOT);
				setState(510);
				id();
				}
				}
				setState(515);
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
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(516);
			_la = _input.LA(1);
			if ( !(((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & ((1L << (NULL - 31)) | (1L << (IntegerLiteral - 31)) | (1L << (NumberLiteral - 31)) | (1L << (BooleanLiteral - 31)) | (1L << (StringLiteral - 31)))) != 0)) ) {
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
		enterRule(_localctx, 72, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(518);
			match(AT);
			setState(519);
			qualifiedName();
			setState(526);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(520);
				match(LPAREN);
				setState(523);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(521);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(522);
					elementValue();
					}
					break;
				}
				setState(525);
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
		enterRule(_localctx, 74, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
			elementValuePair();
			setState(535);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==COMMA || _la==Identifier) {
				{
				{
				setState(530);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(529);
					match(COMMA);
					}
				}

				setState(532);
				elementValuePair();
				}
				}
				setState(537);
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
		enterRule(_localctx, 76, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(538);
			id();
			setState(539);
			match(ASSIGN);
			setState(540);
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
		enterRule(_localctx, 78, RULE_elementValue);
		try {
			setState(545);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DELETE:
			case DO:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case GET:
			case GLOBAL:
			case GOTO:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SELECT:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case UNDELETE:
			case UPDATE:
			case UPSERT:
			case VIRTUAL:
			case VOID:
			case WEBSERVICE:
			case WHEN:
			case WHILE:
			case WITH:
			case WITHOUT:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
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
				setState(542);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(543);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(544);
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
		enterRule(_localctx, 80, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(LBRACE);
			setState(556);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(548);
				elementValue();
				setState(553);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(549);
						match(COMMA);
						setState(550);
						elementValue();
						}
						} 
					}
					setState(555);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
				}
				}
			}

			setState(559);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(558);
				match(COMMA);
				}
			}

			setState(561);
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
		enterRule(_localctx, 82, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(563);
			match(LBRACE);
			setState(567);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (SEMI - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(564);
				statement();
				}
				}
				setState(569);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(570);
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
		enterRule(_localctx, 84, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			localVariableDeclaration();
			setState(573);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public LocalVariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localVariableDeclaration; }
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_localVariableDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(578);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(575);
					modifier();
					}
					} 
				}
				setState(580);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			setState(581);
			typeRef();
			setState(582);
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
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
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
		enterRule(_localctx, 88, RULE_statement);
		try {
			setState(606);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(584);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(585);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(586);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(587);
				switchStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(588);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(589);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(590);
				doWhileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(591);
				tryStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(592);
				returnStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(593);
				throwStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(594);
				breakStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(595);
				continueStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(596);
				insertStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(597);
				updateStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(598);
				deleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(599);
				undeleteStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(600);
				upsertStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(601);
				mergeStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(602);
				runAsStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(603);
				emptyStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(604);
				expressionStatement();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(605);
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
		enterRule(_localctx, 90, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(608);
			match(IF);
			setState(609);
			parExpression();
			setState(610);
			statement();
			setState(613);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(611);
				match(ELSE);
				setState(612);
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

	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
		public TerminalNode ON() { return getToken(ApexParser.ON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<WhenControlContext> whenControl() {
			return getRuleContexts(WhenControlContext.class);
		}
		public WhenControlContext whenControl(int i) {
			return getRuleContext(WhenControlContext.class,i);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(615);
			match(SWITCH);
			setState(616);
			match(ON);
			setState(617);
			expression(0);
			setState(618);
			match(LBRACE);
			setState(620); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(619);
				whenControl();
				}
				}
				setState(622); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
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

	public static class WhenControlContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(ApexParser.WHEN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public WhenControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenControl; }
	}

	public final WhenControlContext whenControl() throws RecognitionException {
		WhenControlContext _localctx = new WhenControlContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_whenControl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(626);
			match(WHEN);
			setState(629);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(627);
				expressionList();
				}
				break;
			case 2:
				{
				setState(628);
				match(ELSE);
				}
				break;
			}
			setState(631);
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
		enterRule(_localctx, 96, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(633);
			match(FOR);
			setState(634);
			match(LPAREN);
			setState(635);
			forControl();
			setState(636);
			match(RPAREN);
			setState(637);
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
		enterRule(_localctx, 98, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(639);
			match(WHILE);
			setState(640);
			parExpression();
			setState(641);
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
		enterRule(_localctx, 100, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(643);
			match(DO);
			setState(644);
			statement();
			setState(645);
			match(WHILE);
			setState(646);
			parExpression();
			setState(647);
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
		enterRule(_localctx, 102, RULE_tryStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(649);
			match(TRY);
			setState(650);
			block();
			setState(660);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(652); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(651);
						catchClause();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(654); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(657);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
				case 1:
					{
					setState(656);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case FINALLY:
				{
				setState(659);
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
		enterRule(_localctx, 104, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(662);
			match(RETURN);
			setState(664);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(663);
				expression(0);
				}
			}

			setState(666);
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
		enterRule(_localctx, 106, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(668);
			match(THROW);
			setState(669);
			expression(0);
			setState(670);
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
		enterRule(_localctx, 108, RULE_breakStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(672);
			match(BREAK);
			setState(674);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(673);
				id();
				}
			}

			setState(676);
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
		enterRule(_localctx, 110, RULE_continueStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(678);
			match(CONTINUE);
			setState(680);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(679);
				id();
				}
			}

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
		enterRule(_localctx, 112, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(684);
			match(INSERT);
			setState(685);
			expression(0);
			setState(686);
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
		enterRule(_localctx, 114, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(688);
			match(UPDATE);
			setState(689);
			expression(0);
			setState(690);
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
		enterRule(_localctx, 116, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(692);
			match(DELETE);
			setState(693);
			expression(0);
			setState(694);
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
		enterRule(_localctx, 118, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(696);
			match(UNDELETE);
			setState(697);
			expression(0);
			setState(698);
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
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public UpsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_upsertStatement; }
	}

	public final UpsertStatementContext upsertStatement() throws RecognitionException {
		UpsertStatementContext _localctx = new UpsertStatementContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(700);
			match(UPSERT);
			setState(701);
			expression(0);
			setState(703);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(702);
				qualifiedName();
				}
			}

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
		enterRule(_localctx, 122, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			match(MERGE);
			setState(708);
			expression(0);
			setState(709);
			expression(0);
			setState(710);
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
		enterRule(_localctx, 124, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(712);
			match(RUNAS);
			setState(713);
			match(LPAREN);
			setState(715);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(714);
				expressionList();
				}
			}

			setState(717);
			match(RPAREN);
			setState(719);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(718);
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
		enterRule(_localctx, 126, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
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
		enterRule(_localctx, 128, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
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
		enterRule(_localctx, 130, RULE_idStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(726);
			id();
			setState(727);
			match(COLON);
			setState(728);
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
		enterRule(_localctx, 132, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(733);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(730);
				modifier();
				}
				}
				setState(735);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(738);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(736);
				getter();
				}
				break;
			case SET:
				{
				setState(737);
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
		enterRule(_localctx, 134, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(740);
			match(GET);
			setState(743);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(741);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(742);
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
		enterRule(_localctx, 136, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(745);
			match(SET);
			setState(748);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(746);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(747);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public CatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchClause; }
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_catchClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(750);
			match(CATCH);
			setState(751);
			match(LPAREN);
			setState(755);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(752);
					modifier();
					}
					} 
				}
				setState(757);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
			}
			setState(758);
			catchType();
			setState(759);
			id();
			setState(760);
			match(RPAREN);
			setState(761);
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
		enterRule(_localctx, 140, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(763);
			qualifiedName();
			setState(768);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(764);
				match(BITOR);
				setState(765);
				qualifiedName();
				}
				}
				setState(770);
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
		enterRule(_localctx, 142, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			match(FINALLY);
			setState(772);
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
		enterRule(_localctx, 144, RULE_forControl);
		int _la;
		try {
			setState(786);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(774);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(776);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(775);
					forInit();
					}
				}

				setState(778);
				match(SEMI);
				setState(780);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(779);
					expression(0);
					}
				}

				setState(782);
				match(SEMI);
				setState(784);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(783);
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
		enterRule(_localctx, 146, RULE_forInit);
		try {
			setState(790);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(788);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(789);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public EnhancedForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhancedForControl; }
	}

	public final EnhancedForControlContext enhancedForControl() throws RecognitionException {
		EnhancedForControlContext _localctx = new EnhancedForControlContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_enhancedForControl);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(795);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,77,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(792);
					modifier();
					}
					} 
				}
				setState(797);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,77,_ctx);
			}
			setState(798);
			typeRef();
			setState(799);
			id();
			setState(800);
			match(COLON);
			setState(801);
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
		enterRule(_localctx, 150, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(803);
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
		enterRule(_localctx, 152, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			match(LPAREN);
			setState(806);
			expression(0);
			setState(807);
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
		enterRule(_localctx, 154, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(809);
			expression(0);
			setState(814);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(810);
				match(COMMA);
				setState(811);
				expression(0);
				}
				}
				setState(816);
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
		int _startState = 156;
		enterRecursionRule(_localctx, 156, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(830);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(818);
				match(NEW);
				setState(819);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(820);
				match(LPAREN);
				setState(821);
				typeRef();
				setState(822);
				match(RPAREN);
				setState(823);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(825);
				_la = _input.LA(1);
				if ( !(((((_la - 92)) & ~0x3f) == 0 && ((1L << (_la - 92)) & ((1L << (INC - 92)) | (1L << (DEC - 92)) | (1L << (ADD - 92)) | (1L << (SUB - 92)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(826);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(827);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(828);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(829);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(926);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(924);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(832);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(833);
						_la = _input.LA(1);
						if ( !(((((_la - 96)) & ~0x3f) == 0 && ((1L << (_la - 96)) & ((1L << (MUL - 96)) | (1L << (DIV - 96)) | (1L << (MOD - 96)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(834);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(835);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(836);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(837);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(838);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(846);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
						case 1:
							{
							setState(839);
							match(LT);
							setState(840);
							match(LT);
							}
							break;
						case 2:
							{
							setState(841);
							match(GT);
							setState(842);
							match(GT);
							setState(843);
							match(GT);
							}
							break;
						case 3:
							{
							setState(844);
							match(GT);
							setState(845);
							match(GT);
							}
							break;
						}
						setState(848);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(849);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(858);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
						case 1:
							{
							setState(850);
							match(LT);
							setState(851);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(852);
							match(GT);
							setState(853);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(854);
							match(LE);
							}
							break;
						case 4:
							{
							setState(855);
							match(GE);
							}
							break;
						case 5:
							{
							setState(856);
							match(GT);
							}
							break;
						case 6:
							{
							setState(857);
							match(LT);
							}
							break;
						}
						setState(860);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(861);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(862);
						_la = _input.LA(1);
						if ( !(((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (EQUAL - 85)) | (1L << (TRIPLEEQUAL - 85)) | (1L << (NOTEQUAL - 85)) | (1L << (LESSANDGREATER - 85)) | (1L << (TRIPLENOTEQUAL - 85)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(863);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(864);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(865);
						match(BITAND);
						setState(866);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(867);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(868);
						match(CARET);
						setState(869);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(870);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(871);
						match(BITOR);
						setState(872);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(873);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(874);
						match(AND);
						setState(875);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(876);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(877);
						match(OR);
						setState(878);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(879);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(880);
						match(QUESTION);
						setState(881);
						expression(0);
						setState(882);
						match(COLON);
						setState(883);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(885);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(886);
						_la = _input.LA(1);
						if ( !(((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (ASSIGN - 76)) | (1L << (ADD_ASSIGN - 76)) | (1L << (SUB_ASSIGN - 76)) | (1L << (MUL_ASSIGN - 76)) | (1L << (DIV_ASSIGN - 76)) | (1L << (AND_ASSIGN - 76)) | (1L << (OR_ASSIGN - 76)) | (1L << (XOR_ASSIGN - 76)) | (1L << (MOD_ASSIGN - 76)) | (1L << (LSHIFT_ASSIGN - 76)) | (1L << (RSHIFT_ASSIGN - 76)) | (1L << (URSHIFT_ASSIGN - 76)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(887);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(888);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(889);
						match(DOT);
						setState(890);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(891);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(892);
						match(DOT);
						setState(893);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(894);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(895);
						match(DOT);
						setState(896);
						match(NEW);
						setState(898);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(897);
							nonWildcardTypeArguments();
							}
						}

						setState(900);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(901);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(902);
						match(DOT);
						setState(903);
						match(SUPER);
						setState(904);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(905);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(906);
						match(DOT);
						setState(907);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(908);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(909);
						match(LBRACK);
						setState(910);
						expression(0);
						setState(911);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(913);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(914);
						match(LPAREN);
						setState(916);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(915);
							expressionList();
							}
						}

						setState(918);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(919);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(920);
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
						setState(921);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(922);
						match(INSTANCEOF);
						setState(923);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(928);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
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
		enterRule(_localctx, 158, RULE_primary);
		try {
			setState(951);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(929);
				match(LPAREN);
				setState(930);
				expression(0);
				setState(931);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(933);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(934);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(935);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(936);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(937);
				typeRef();
				setState(938);
				match(DOT);
				setState(939);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(941);
				match(VOID);
				setState(942);
				match(DOT);
				setState(943);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(944);
				nonWildcardTypeArguments();
				setState(948);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
				case 1:
					{
					setState(945);
					explicitGenericInvocationSuffix();
					}
					break;
				case 2:
					{
					setState(946);
					match(THIS);
					setState(947);
					arguments();
					}
					break;
				}
				}
				break;
			case 9:
				_localctx = new Alt9PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(950);
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
		enterRule(_localctx, 160, RULE_creator);
		try {
			setState(964);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(953);
				nonWildcardTypeArguments();
				setState(954);
				createdName();
				setState(955);
				classCreatorRest();
				}
				break;
			case ABSTRACT:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DELETE:
			case DO:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case GET:
			case GLOBAL:
			case GOTO:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SELECT:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case UNDELETE:
			case UPDATE:
			case UPSERT:
			case VIRTUAL:
			case VOID:
			case WEBSERVICE:
			case WHEN:
			case WHILE:
			case WITH:
			case WITHOUT:
			case Identifier:
				_localctx = new Alt2CreatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(957);
				createdName();
				setState(962);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
				case 1:
					{
					setState(958);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(959);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(960);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(961);
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
		public CreatedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createdName; }
	}

	public final CreatedNameContext createdName() throws RecognitionException {
		CreatedNameContext _localctx = new CreatedNameContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_createdName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(966);
			idCreatedNamePair();
			setState(971);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(967);
				match(DOT);
				setState(968);
				idCreatedNamePair();
				}
				}
				setState(973);
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
		enterRule(_localctx, 164, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(974);
			id();
			setState(976);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(975);
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
		enterRule(_localctx, 166, RULE_innerCreator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(978);
			id();
			setState(980);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(979);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(982);
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
		enterRule(_localctx, 168, RULE_arrayCreatorRest);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(984);
			match(LBRACK);
			setState(1002);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(985);
				match(RBRACK);
				setState(986);
				arraySubscripts();
				setState(987);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DELETE:
			case DO:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case GET:
			case GLOBAL:
			case GOTO:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SELECT:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case UNDELETE:
			case UPDATE:
			case UPSERT:
			case VIRTUAL:
			case VOID:
			case WEBSERVICE:
			case WHEN:
			case WHILE:
			case WITH:
			case WITHOUT:
			case IntegerLiteral:
			case NumberLiteral:
			case BooleanLiteral:
			case StringLiteral:
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
				setState(989);
				expression(0);
				setState(990);
				match(RBRACK);
				setState(997);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(991);
						match(LBRACK);
						setState(992);
						expression(0);
						setState(993);
						match(RBRACK);
						}
						} 
					}
					setState(999);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				}
				setState(1000);
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
		enterRule(_localctx, 170, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1004);
			match(LBRACE);
			setState(1005);
			mapCreatorRestPair();
			setState(1010);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1006);
				match(COMMA);
				setState(1007);
				mapCreatorRestPair();
				}
				}
				setState(1012);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1013);
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
		enterRule(_localctx, 172, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1015);
			idOrExpression();
			setState(1016);
			match(MAP);
			setState(1017);
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
		enterRule(_localctx, 174, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1019);
			match(LBRACE);
			setState(1020);
			literalOrExpression();
			setState(1025);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1021);
				match(COMMA);
				{
				setState(1022);
				literalOrExpression();
				}
				}
				}
				setState(1027);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1028);
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
		enterRule(_localctx, 176, RULE_literalOrExpression);
		try {
			setState(1032);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1030);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1031);
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
		enterRule(_localctx, 178, RULE_idOrExpression);
		try {
			setState(1036);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1034);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1035);
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
		enterRule(_localctx, 180, RULE_classCreatorRest);
		int _la;
		try {
			setState(1044);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1038);
				arguments();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1039);
				match(LBRACE);
				setState(1041);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1040);
					expressionList();
					}
				}

				setState(1043);
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
		enterRule(_localctx, 182, RULE_explicitGenericInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1046);
			nonWildcardTypeArguments();
			setState(1047);
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
		enterRule(_localctx, 184, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1049);
			match(LT);
			setState(1050);
			typeList();
			setState(1051);
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
		enterRule(_localctx, 186, RULE_typeArgumentsOrDiamond);
		try {
			setState(1056);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,101,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1053);
				match(LT);
				setState(1054);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1055);
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
		enterRule(_localctx, 188, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1061);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1058);
				match(LT);
				setState(1059);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1060);
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
		enterRule(_localctx, 190, RULE_superSuffix);
		try {
			setState(1069);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1063);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1064);
				match(DOT);
				setState(1065);
				id();
				setState(1067);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
				case 1:
					{
					setState(1066);
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
		enterRule(_localctx, 192, RULE_explicitGenericInvocationSuffix);
		try {
			setState(1076);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1071);
				match(SUPER);
				setState(1072);
				superSuffix();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1073);
				id();
				setState(1074);
				arguments();
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
		enterRule(_localctx, 194, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1078);
			match(LPAREN);
			setState(1080);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1079);
				expressionList();
				}
			}

			setState(1082);
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
		enterRule(_localctx, 196, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1084);
			match(LBRACK);
			setState(1089);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1087);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
					case 1:
						{
						setState(1085);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1086);
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
				setState(1091);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			}
			setState(1092);
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ApexParser.Identifier, 0); }
		public TerminalNode ABSTRACT() { return getToken(ApexParser.ABSTRACT, 0); }
		public TerminalNode BREAK() { return getToken(ApexParser.BREAK, 0); }
		public TerminalNode BYTE() { return getToken(ApexParser.BYTE, 0); }
		public TerminalNode CATCH() { return getToken(ApexParser.CATCH, 0); }
		public TerminalNode CHAR() { return getToken(ApexParser.CHAR, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public TerminalNode CONST() { return getToken(ApexParser.CONST, 0); }
		public TerminalNode CONTINUE() { return getToken(ApexParser.CONTINUE, 0); }
		public TerminalNode DEFAULT() { return getToken(ApexParser.DEFAULT, 0); }
		public TerminalNode DELETE() { return getToken(ApexParser.DELETE, 0); }
		public TerminalNode DO() { return getToken(ApexParser.DO, 0); }
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public TerminalNode ENUM() { return getToken(ApexParser.ENUM, 0); }
		public TerminalNode EXTENDS() { return getToken(ApexParser.EXTENDS, 0); }
		public TerminalNode FINAL() { return getToken(ApexParser.FINAL, 0); }
		public TerminalNode FINALLY() { return getToken(ApexParser.FINALLY, 0); }
		public TerminalNode FLOAT() { return getToken(ApexParser.FLOAT, 0); }
		public TerminalNode FOR() { return getToken(ApexParser.FOR, 0); }
		public TerminalNode GET() { return getToken(ApexParser.GET, 0); }
		public TerminalNode GLOBAL() { return getToken(ApexParser.GLOBAL, 0); }
		public TerminalNode GOTO() { return getToken(ApexParser.GOTO, 0); }
		public TerminalNode IF() { return getToken(ApexParser.IF, 0); }
		public TerminalNode IMPLEMENTS() { return getToken(ApexParser.IMPLEMENTS, 0); }
		public TerminalNode INHERITED() { return getToken(ApexParser.INHERITED, 0); }
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TerminalNode INTERFACE() { return getToken(ApexParser.INTERFACE, 0); }
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public TerminalNode NATIVE() { return getToken(ApexParser.NATIVE, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public TerminalNode ON() { return getToken(ApexParser.ON, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode PACKAGE() { return getToken(ApexParser.PACKAGE, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode RETURN() { return getToken(ApexParser.RETURN, 0); }
		public TerminalNode RUNAS() { return getToken(ApexParser.RUNAS, 0); }
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public TerminalNode SHORT() { return getToken(ApexParser.SHORT, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public TerminalNode THROW() { return getToken(ApexParser.THROW, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public TerminalNode TRY() { return getToken(ApexParser.TRY, 0); }
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public TerminalNode UPSERT() { return getToken(ApexParser.UPSERT, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode VOID() { return getToken(ApexParser.VOID, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
		public TerminalNode WHEN() { return getToken(ApexParser.WHEN, 0); }
		public TerminalNode WHILE() { return getToken(ApexParser.WHILE, 0); }
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode WITHOUT() { return getToken(ApexParser.WITHOUT, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1094);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) ) {
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
		case 78:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3y\u044b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\3\2\3\2\3\2\3\3\7\3\u00cf\n\3\f\3\16"+
		"\3\u00d2\13\3\3\3\3\3\7\3\u00d6\n\3\f\3\16\3\u00d9\13\3\3\3\3\3\7\3\u00dd"+
		"\n\3\f\3\16\3\u00e0\13\3\3\3\5\3\u00e3\n\3\3\4\3\4\3\4\3\4\5\4\u00e9\n"+
		"\4\3\4\3\4\5\4\u00ed\n\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\u00f5\n\5\3\5\3\5"+
		"\3\6\3\6\3\6\7\6\u00fc\n\6\f\6\16\6\u00ff\13\6\3\7\7\7\u0102\n\7\f\7\16"+
		"\7\u0105\13\7\3\7\3\7\5\7\u0109\n\7\3\7\5\7\u010c\n\7\3\b\3\b\3\b\3\b"+
		"\5\b\u0112\n\b\3\b\3\b\3\t\3\t\3\t\7\t\u0119\n\t\f\t\16\t\u011c\13\t\3"+
		"\n\3\n\7\n\u0120\n\n\f\n\16\n\u0123\13\n\3\n\3\n\3\13\3\13\7\13\u0129"+
		"\n\13\f\13\16\13\u012c\13\13\3\13\3\13\3\f\3\f\5\f\u0132\n\f\3\f\3\f\7"+
		"\f\u0136\n\f\f\f\16\f\u0139\13\f\3\f\5\f\u013c\n\f\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0151\n\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u015a\n\16\3\17\7\17\u015d\n"+
		"\17\f\17\16\17\u0160\13\17\3\17\3\17\5\17\u0164\n\17\3\17\3\17\3\17\3"+
		"\17\5\17\u016a\n\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\5\23\u017b\n\23\3\23\3\23\3\24\7\24\u0180\n"+
		"\24\f\24\16\24\u0183\13\24\3\24\3\24\5\24\u0187\n\24\3\25\3\25\3\25\3"+
		"\25\3\25\5\25\u018e\n\25\3\26\3\26\3\26\3\26\7\26\u0194\n\26\f\26\16\26"+
		"\u0197\13\26\3\26\3\26\3\27\3\27\3\27\7\27\u019e\n\27\f\27\16\27\u01a1"+
		"\13\27\3\27\3\27\3\27\3\30\3\30\5\30\u01a8\n\30\3\30\3\30\3\30\3\30\3"+
		"\31\3\31\3\31\7\31\u01b1\n\31\f\31\16\31\u01b4\13\31\3\32\3\32\3\32\5"+
		"\32\u01b9\n\32\3\33\3\33\5\33\u01bd\n\33\3\34\3\34\3\34\3\34\7\34\u01c3"+
		"\n\34\f\34\16\34\u01c6\13\34\3\34\5\34\u01c9\n\34\5\34\u01cb\n\34\3\34"+
		"\3\34\3\35\3\35\3\35\7\35\u01d2\n\35\f\35\16\35\u01d5\13\35\3\35\3\35"+
		"\3\36\3\36\7\36\u01db\n\36\f\36\16\36\u01de\13\36\3\37\3\37\5\37\u01e2"+
		"\n\37\3 \3 \3 \3 \3!\3!\5!\u01ea\n!\3!\3!\3\"\3\"\3\"\7\"\u01f1\n\"\f"+
		"\"\16\"\u01f4\13\"\3#\7#\u01f7\n#\f#\16#\u01fa\13#\3#\3#\3#\3$\3$\3$\7"+
		"$\u0202\n$\f$\16$\u0205\13$\3%\3%\3&\3&\3&\3&\3&\5&\u020e\n&\3&\5&\u0211"+
		"\n&\3\'\3\'\5\'\u0215\n\'\3\'\7\'\u0218\n\'\f\'\16\'\u021b\13\'\3(\3("+
		"\3(\3(\3)\3)\3)\5)\u0224\n)\3*\3*\3*\3*\7*\u022a\n*\f*\16*\u022d\13*\5"+
		"*\u022f\n*\3*\5*\u0232\n*\3*\3*\3+\3+\7+\u0238\n+\f+\16+\u023b\13+\3+"+
		"\3+\3,\3,\3,\3-\7-\u0243\n-\f-\16-\u0246\13-\3-\3-\3-\3.\3.\3.\3.\3.\3"+
		".\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u0261\n.\3/\3/\3"+
		"/\3/\3/\5/\u0268\n/\3\60\3\60\3\60\3\60\3\60\6\60\u026f\n\60\r\60\16\60"+
		"\u0270\3\60\3\60\3\61\3\61\3\61\5\61\u0278\n\61\3\61\3\61\3\62\3\62\3"+
		"\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3"+
		"\65\3\65\3\65\6\65\u028f\n\65\r\65\16\65\u0290\3\65\5\65\u0294\n\65\3"+
		"\65\5\65\u0297\n\65\3\66\3\66\5\66\u029b\n\66\3\66\3\66\3\67\3\67\3\67"+
		"\3\67\38\38\58\u02a5\n8\38\38\39\39\59\u02ab\n9\39\39\3:\3:\3:\3:\3;\3"+
		";\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3>\5>\u02c2\n>\3>\3>\3?\3?\3?\3"+
		"?\3?\3@\3@\3@\5@\u02ce\n@\3@\3@\5@\u02d2\n@\3A\3A\3B\3B\3B\3C\3C\3C\3"+
		"C\3D\7D\u02de\nD\fD\16D\u02e1\13D\3D\3D\5D\u02e5\nD\3E\3E\3E\5E\u02ea"+
		"\nE\3F\3F\3F\5F\u02ef\nF\3G\3G\3G\7G\u02f4\nG\fG\16G\u02f7\13G\3G\3G\3"+
		"G\3G\3G\3H\3H\3H\7H\u0301\nH\fH\16H\u0304\13H\3I\3I\3I\3J\3J\5J\u030b"+
		"\nJ\3J\3J\5J\u030f\nJ\3J\3J\5J\u0313\nJ\5J\u0315\nJ\3K\3K\5K\u0319\nK"+
		"\3L\7L\u031c\nL\fL\16L\u031f\13L\3L\3L\3L\3L\3L\3M\3M\3N\3N\3N\3N\3O\3"+
		"O\3O\7O\u032f\nO\fO\16O\u0332\13O\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\5P\u0341\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\5P\u0351\nP"+
		"\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\5P\u035d\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\5P\u0385\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\5P\u0397\nP\3P\3P\3P\3P\3P\3P\7P\u039f\nP\fP\16P\u03a2\13P\3"+
		"Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\5Q\u03b7\nQ\3"+
		"Q\5Q\u03ba\nQ\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u03c5\nR\5R\u03c7\nR\3S\3"+
		"S\3S\7S\u03cc\nS\fS\16S\u03cf\13S\3T\3T\5T\u03d3\nT\3U\3U\5U\u03d7\nU"+
		"\3U\3U\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\7V\u03e6\nV\fV\16V\u03e9\13V\3"+
		"V\3V\5V\u03ed\nV\3W\3W\3W\3W\7W\u03f3\nW\fW\16W\u03f6\13W\3W\3W\3X\3X"+
		"\3X\3X\3Y\3Y\3Y\3Y\7Y\u0402\nY\fY\16Y\u0405\13Y\3Y\3Y\3Z\3Z\5Z\u040b\n"+
		"Z\3[\3[\5[\u040f\n[\3\\\3\\\3\\\5\\\u0414\n\\\3\\\5\\\u0417\n\\\3]\3]"+
		"\3]\3^\3^\3^\3^\3_\3_\3_\5_\u0423\n_\3`\3`\3`\5`\u0428\n`\3a\3a\3a\3a"+
		"\5a\u042e\na\5a\u0430\na\3b\3b\3b\3b\3b\5b\u0437\nb\3c\3c\5c\u043b\nc"+
		"\3c\3c\3d\3d\3d\7d\u0442\nd\fd\16d\u0445\13d\3d\3d\3e\3e\3e\3\u0443\3"+
		"\u009ef\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:"+
		"<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a"+
		"\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2"+
		"\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba"+
		"\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\2\f\4\2!!@C\3\2^a\3\2ST\4\2"+
		"bcgg\3\2`a\3\2W[\4\2NNis\3\2^_\3\2JJ\4\2\3?tt\2\u04a5\2\u00ca\3\2\2\2"+
		"\4\u00e2\3\2\2\2\6\u00e4\3\2\2\2\b\u00f0\3\2\2\2\n\u00f8\3\2\2\2\f\u0103"+
		"\3\2\2\2\16\u010d\3\2\2\2\20\u0115\3\2\2\2\22\u011d\3\2\2\2\24\u0126\3"+
		"\2\2\2\26\u013b\3\2\2\2\30\u0150\3\2\2\2\32\u0159\3\2\2\2\34\u015e\3\2"+
		"\2\2\36\u016b\3\2\2\2 \u016f\3\2\2\2\"\u0173\3\2\2\2$\u0177\3\2\2\2&\u0186"+
		"\3\2\2\2(\u018d\3\2\2\2*\u018f\3\2\2\2,\u019a\3\2\2\2.\u01a7\3\2\2\2\60"+
		"\u01ad\3\2\2\2\62\u01b5\3\2\2\2\64\u01bc\3\2\2\2\66\u01be\3\2\2\28\u01ce"+
		"\3\2\2\2:\u01dc\3\2\2\2<\u01df\3\2\2\2>\u01e3\3\2\2\2@\u01e7\3\2\2\2B"+
		"\u01ed\3\2\2\2D\u01f8\3\2\2\2F\u01fe\3\2\2\2H\u0206\3\2\2\2J\u0208\3\2"+
		"\2\2L\u0212\3\2\2\2N\u021c\3\2\2\2P\u0223\3\2\2\2R\u0225\3\2\2\2T\u0235"+
		"\3\2\2\2V\u023e\3\2\2\2X\u0244\3\2\2\2Z\u0260\3\2\2\2\\\u0262\3\2\2\2"+
		"^\u0269\3\2\2\2`\u0274\3\2\2\2b\u027b\3\2\2\2d\u0281\3\2\2\2f\u0285\3"+
		"\2\2\2h\u028b\3\2\2\2j\u0298\3\2\2\2l\u029e\3\2\2\2n\u02a2\3\2\2\2p\u02a8"+
		"\3\2\2\2r\u02ae\3\2\2\2t\u02b2\3\2\2\2v\u02b6\3\2\2\2x\u02ba\3\2\2\2z"+
		"\u02be\3\2\2\2|\u02c5\3\2\2\2~\u02ca\3\2\2\2\u0080\u02d3\3\2\2\2\u0082"+
		"\u02d5\3\2\2\2\u0084\u02d8\3\2\2\2\u0086\u02df\3\2\2\2\u0088\u02e6\3\2"+
		"\2\2\u008a\u02eb\3\2\2\2\u008c\u02f0\3\2\2\2\u008e\u02fd\3\2\2\2\u0090"+
		"\u0305\3\2\2\2\u0092\u0314\3\2\2\2\u0094\u0318\3\2\2\2\u0096\u031d\3\2"+
		"\2\2\u0098\u0325\3\2\2\2\u009a\u0327\3\2\2\2\u009c\u032b\3\2\2\2\u009e"+
		"\u0340\3\2\2\2\u00a0\u03b9\3\2\2\2\u00a2\u03c6\3\2\2\2\u00a4\u03c8\3\2"+
		"\2\2\u00a6\u03d0\3\2\2\2\u00a8\u03d4\3\2\2\2\u00aa\u03da\3\2\2\2\u00ac"+
		"\u03ee\3\2\2\2\u00ae\u03f9\3\2\2\2\u00b0\u03fd\3\2\2\2\u00b2\u040a\3\2"+
		"\2\2\u00b4\u040e\3\2\2\2\u00b6\u0416\3\2\2\2\u00b8\u0418\3\2\2\2\u00ba"+
		"\u041b\3\2\2\2\u00bc\u0422\3\2\2\2\u00be\u0427\3\2\2\2\u00c0\u042f\3\2"+
		"\2\2\u00c2\u0436\3\2\2\2\u00c4\u0438\3\2\2\2\u00c6\u043e\3\2\2\2\u00c8"+
		"\u0448\3\2\2\2\u00ca\u00cb\5\4\3\2\u00cb\u00cc\7\2\2\3\u00cc\3\3\2\2\2"+
		"\u00cd\u00cf\5\30\r\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce"+
		"\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00e3\5\6\4\2\u00d4\u00d6\5\30\r\2\u00d5\u00d4\3\2\2\2\u00d6\u00d9\3"+
		"\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00da\u00e3\5\b\5\2\u00db\u00dd\5\30\r\2\u00dc\u00db\3"+
		"\2\2\2\u00dd\u00e0\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e1\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1\u00e3\5\16\b\2\u00e2\u00d0\3"+
		"\2\2\2\u00e2\u00d7\3\2\2\2\u00e2\u00de\3\2\2\2\u00e3\5\3\2\2\2\u00e4\u00e5"+
		"\7\b\2\2\u00e5\u00e8\5\u00c8e\2\u00e6\u00e7\7\20\2\2\u00e7\u00e9\58\35"+
		"\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00eb"+
		"\7\31\2\2\u00eb\u00ed\5\20\t\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2"+
		"\u00ed\u00ee\3\2\2\2\u00ee\u00ef\5\22\n\2\u00ef\7\3\2\2\2\u00f0\u00f1"+
		"\7\17\2\2\u00f1\u00f2\5\u00c8e\2\u00f2\u00f4\7G\2\2\u00f3\u00f5\5\n\6"+
		"\2\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7"+
		"\7H\2\2\u00f7\t\3\2\2\2\u00f8\u00fd\5\f\7\2\u00f9\u00fa\7L\2\2\u00fa\u00fc"+
		"\5\f\7\2\u00fb\u00f9\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd"+
		"\u00fe\3\2\2\2\u00fe\13\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0102\5\30\r"+
		"\2\u0101\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104"+
		"\3\2\2\2\u0104\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0108\5\u00c8e"+
		"\2\u0107\u0109\5\u00c4c\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109"+
		"\u010b\3\2\2\2\u010a\u010c\5\22\n\2\u010b\u010a\3\2\2\2\u010b\u010c\3"+
		"\2\2\2\u010c\r\3\2\2\2\u010d\u010e\7\35\2\2\u010e\u0111\5\u00c8e\2\u010f"+
		"\u0110\7\20\2\2\u0110\u0112\5\20\t\2\u0111\u010f\3\2\2\2\u0111\u0112\3"+
		"\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\5\24\13\2\u0114\17\3\2\2\2\u0115"+
		"\u011a\58\35\2\u0116\u0117\7L\2\2\u0117\u0119\58\35\2\u0118\u0116\3\2"+
		"\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b"+
		"\21\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u0121\7G\2\2\u011e\u0120\5\26\f"+
		"\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u011f\3\2\2\2\u0121\u0122"+
		"\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0125\7H\2\2\u0125"+
		"\23\3\2\2\2\u0126\u012a\7G\2\2\u0127\u0129\5&\24\2\u0128\u0127\3\2\2\2"+
		"\u0129\u012c\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012d"+
		"\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u012e\7H\2\2\u012e\25\3\2\2\2\u012f"+
		"\u013c\7K\2\2\u0130\u0132\7.\2\2\u0131\u0130\3\2\2\2\u0131\u0132\3\2\2"+
		"\2\u0132\u0133\3\2\2\2\u0133\u013c\5T+\2\u0134\u0136\5\30\r\2\u0135\u0134"+
		"\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138"+
		"\u013a\3\2\2\2\u0139\u0137\3\2\2\2\u013a\u013c\5\32\16\2\u013b\u012f\3"+
		"\2\2\2\u013b\u0131\3\2\2\2\u013b\u0137\3\2\2\2\u013c\27\3\2\2\2\u013d"+
		"\u0151\5J&\2\u013e\u0151\7\26\2\2\u013f\u0151\7\'\2\2\u0140\u0151\7&\2"+
		"\2\u0141\u0151\7%\2\2\u0142\u0151\7\64\2\2\u0143\u0151\7.\2\2\u0144\u0151"+
		"\7\3\2\2\u0145\u0151\7\21\2\2\u0146\u0151\7;\2\2\u0147\u0151\7#\2\2\u0148"+
		"\u0151\79\2\2\u0149\u0151\7\61\2\2\u014a\u014b\7>\2\2\u014b\u0151\7,\2"+
		"\2\u014c\u014d\7?\2\2\u014d\u0151\7,\2\2\u014e\u014f\7\32\2\2\u014f\u0151"+
		"\7,\2\2\u0150\u013d\3\2\2\2\u0150\u013e\3\2\2\2\u0150\u013f\3\2\2\2\u0150"+
		"\u0140\3\2\2\2\u0150\u0141\3\2\2\2\u0150\u0142\3\2\2\2\u0150\u0143\3\2"+
		"\2\2\u0150\u0144\3\2\2\2\u0150\u0145\3\2\2\2\u0150\u0146\3\2\2\2\u0150"+
		"\u0147\3\2\2\2\u0150\u0148\3\2\2\2\u0150\u0149\3\2\2\2\u0150\u014a\3\2"+
		"\2\2\u0150\u014c\3\2\2\2\u0150\u014e\3\2\2\2\u0151\31\3\2\2\2\u0152\u015a"+
		"\5\34\17\2\u0153\u015a\5 \21\2\u0154\u015a\5\36\20\2\u0155\u015a\5\16"+
		"\b\2\u0156\u015a\5\6\4\2\u0157\u015a\5\b\5\2\u0158\u015a\5\"\22\2\u0159"+
		"\u0152\3\2\2\2\u0159\u0153\3\2\2\2\u0159\u0154\3\2\2\2\u0159\u0155\3\2"+
		"\2\2\u0159\u0156\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u0158\3\2\2\2\u015a"+
		"\33\3\2\2\2\u015b\u015d\5\30\r\2\u015c\u015b\3\2\2\2\u015d\u0160\3\2\2"+
		"\2\u015e\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0163\3\2\2\2\u0160\u015e"+
		"\3\2\2\2\u0161\u0164\58\35\2\u0162\u0164\7:\2\2\u0163\u0161\3\2\2\2\u0163"+
		"\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0166\5\u00c8e\2\u0166\u0169"+
		"\5@!\2\u0167\u016a\5T+\2\u0168\u016a\7K\2\2\u0169\u0167\3\2\2\2\u0169"+
		"\u0168\3\2\2\2\u016a\35\3\2\2\2\u016b\u016c\5F$\2\u016c\u016d\5@!\2\u016d"+
		"\u016e\5T+\2\u016e\37\3\2\2\2\u016f\u0170\58\35\2\u0170\u0171\5\60\31"+
		"\2\u0171\u0172\7K\2\2\u0172!\3\2\2\2\u0173\u0174\58\35\2\u0174\u0175\5"+
		"\60\31\2\u0175\u0176\5$\23\2\u0176#\3\2\2\2\u0177\u0178\7G\2\2\u0178\u017a"+
		"\5\u0086D\2\u0179\u017b\5\u0086D\2\u017a\u0179\3\2\2\2\u017a\u017b\3\2"+
		"\2\2\u017b\u017c\3\2\2\2\u017c\u017d\7H\2\2\u017d%\3\2\2\2\u017e\u0180"+
		"\5\30\r\2\u017f\u017e\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2"+
		"\u0181\u0182\3\2\2\2\u0182\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0187"+
		"\5(\25\2\u0185\u0187\7K\2\2\u0186\u0181\3\2\2\2\u0186\u0185\3\2\2\2\u0187"+
		"\'\3\2\2\2\u0188\u018e\5*\26\2\u0189\u018e\5.\30\2\u018a\u018e\5\16\b"+
		"\2\u018b\u018e\5\6\4\2\u018c\u018e\5\b\5\2\u018d\u0188\3\2\2\2\u018d\u0189"+
		"\3\2\2\2\u018d\u018a\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018c\3\2\2\2\u018e"+
		")\3\2\2\2\u018f\u0190\58\35\2\u0190\u0195\5,\27\2\u0191\u0192\7L\2\2\u0192"+
		"\u0194\5,\27\2\u0193\u0191\3\2\2\2\u0194\u0197\3\2\2\2\u0195\u0193\3\2"+
		"\2\2\u0195\u0196\3\2\2\2\u0196\u0198\3\2\2\2\u0197\u0195\3\2\2\2\u0198"+
		"\u0199\7K\2\2\u0199+\3\2\2\2\u019a\u019f\5\u00c8e\2\u019b\u019c\7I\2\2"+
		"\u019c\u019e\7J\2\2\u019d\u019b\3\2\2\2\u019e\u01a1\3\2\2\2\u019f\u019d"+
		"\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\3\2\2\2\u01a1\u019f\3\2\2\2\u01a2"+
		"\u01a3\7N\2\2\u01a3\u01a4\5\64\33\2\u01a4-\3\2\2\2\u01a5\u01a8\58\35\2"+
		"\u01a6\u01a8\7:\2\2\u01a7\u01a5\3\2\2\2\u01a7\u01a6\3\2\2\2\u01a8\u01a9"+
		"\3\2\2\2\u01a9\u01aa\5\u00c8e\2\u01aa\u01ab\5@!\2\u01ab\u01ac\7K\2\2\u01ac"+
		"/\3\2\2\2\u01ad\u01b2\5\62\32\2\u01ae\u01af\7L\2\2\u01af\u01b1\5\62\32"+
		"\2\u01b0\u01ae\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3"+
		"\3\2\2\2\u01b3\61\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01b8\5\u00c8e\2\u01b6"+
		"\u01b7\7N\2\2\u01b7\u01b9\5\64\33\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3"+
		"\2\2\2\u01b9\63\3\2\2\2\u01ba\u01bd\5\66\34\2\u01bb\u01bd\5\u009eP\2\u01bc"+
		"\u01ba\3\2\2\2\u01bc\u01bb\3\2\2\2\u01bd\65\3\2\2\2\u01be\u01ca\7G\2\2"+
		"\u01bf\u01c4\5\64\33\2\u01c0\u01c1\7L\2\2\u01c1\u01c3\5\64\33\2\u01c2"+
		"\u01c0\3\2\2\2\u01c3\u01c6\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c4\u01c5\3\2"+
		"\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01c9\7L\2\2\u01c8"+
		"\u01c7\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01cb\3\2\2\2\u01ca\u01bf\3\2"+
		"\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01cd\7H\2\2\u01cd"+
		"\67\3\2\2\2\u01ce\u01d3\5<\37\2\u01cf\u01d0\7M\2\2\u01d0\u01d2\5<\37\2"+
		"\u01d1\u01cf\3\2\2\2\u01d2\u01d5\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d4"+
		"\3\2\2\2\u01d4\u01d6\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d6\u01d7\5:\36\2\u01d7"+
		"9\3\2\2\2\u01d8\u01d9\7I\2\2\u01d9\u01db\7J\2\2\u01da\u01d8\3\2\2\2\u01db"+
		"\u01de\3\2\2\2\u01dc\u01da\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd;\3\2\2\2"+
		"\u01de\u01dc\3\2\2\2\u01df\u01e1\5\u00c8e\2\u01e0\u01e2\5> \2\u01e1\u01e0"+
		"\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2=\3\2\2\2\u01e3\u01e4\7R\2\2\u01e4\u01e5"+
		"\5\20\t\2\u01e5\u01e6\7Q\2\2\u01e6?\3\2\2\2\u01e7\u01e9\7E\2\2\u01e8\u01ea"+
		"\5B\"\2\u01e9\u01e8\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb"+
		"\u01ec\7F\2\2\u01ecA\3\2\2\2\u01ed\u01f2\5D#\2\u01ee\u01ef\7L\2\2\u01ef"+
		"\u01f1\5D#\2\u01f0\u01ee\3\2\2\2\u01f1\u01f4\3\2\2\2\u01f2\u01f0\3\2\2"+
		"\2\u01f2\u01f3\3\2\2\2\u01f3C\3\2\2\2\u01f4\u01f2\3\2\2\2\u01f5\u01f7"+
		"\5\30\r\2\u01f6\u01f5\3\2\2\2\u01f7\u01fa\3\2\2\2\u01f8\u01f6\3\2\2\2"+
		"\u01f8\u01f9\3\2\2\2\u01f9\u01fb\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fb\u01fc"+
		"\58\35\2\u01fc\u01fd\5\u00c8e\2\u01fdE\3\2\2\2\u01fe\u0203\5\u00c8e\2"+
		"\u01ff\u0200\7M\2\2\u0200\u0202\5\u00c8e\2\u0201\u01ff\3\2\2\2\u0202\u0205"+
		"\3\2\2\2\u0203\u0201\3\2\2\2\u0203\u0204\3\2\2\2\u0204G\3\2\2\2\u0205"+
		"\u0203\3\2\2\2\u0206\u0207\t\2\2\2\u0207I\3\2\2\2\u0208\u0209\7u\2\2\u0209"+
		"\u0210\5F$\2\u020a\u020d\7E\2\2\u020b\u020e\5L\'\2\u020c\u020e\5P)\2\u020d"+
		"\u020b\3\2\2\2\u020d\u020c\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u020f\3\2"+
		"\2\2\u020f\u0211\7F\2\2\u0210\u020a\3\2\2\2\u0210\u0211\3\2\2\2\u0211"+
		"K\3\2\2\2\u0212\u0219\5N(\2\u0213\u0215\7L\2\2\u0214\u0213\3\2\2\2\u0214"+
		"\u0215\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0218\5N(\2\u0217\u0214\3\2\2"+
		"\2\u0218\u021b\3\2\2\2\u0219\u0217\3\2\2\2\u0219\u021a\3\2\2\2\u021aM"+
		"\3\2\2\2\u021b\u0219\3\2\2\2\u021c\u021d\5\u00c8e\2\u021d\u021e\7N\2\2"+
		"\u021e\u021f\5P)\2\u021fO\3\2\2\2\u0220\u0224\5\u009eP\2\u0221\u0224\5"+
		"J&\2\u0222\u0224\5R*\2\u0223\u0220\3\2\2\2\u0223\u0221\3\2\2\2\u0223\u0222"+
		"\3\2\2\2\u0224Q\3\2\2\2\u0225\u022e\7G\2\2\u0226\u022b\5P)\2\u0227\u0228"+
		"\7L\2\2\u0228\u022a\5P)\2\u0229\u0227\3\2\2\2\u022a\u022d\3\2\2\2\u022b"+
		"\u0229\3\2\2\2\u022b\u022c\3\2\2\2\u022c\u022f\3\2\2\2\u022d\u022b\3\2"+
		"\2\2\u022e\u0226\3\2\2\2\u022e\u022f\3\2\2\2\u022f\u0231\3\2\2\2\u0230"+
		"\u0232\7L\2\2\u0231\u0230\3\2\2\2\u0231\u0232\3\2\2\2\u0232\u0233\3\2"+
		"\2\2\u0233\u0234\7H\2\2\u0234S\3\2\2\2\u0235\u0239\7G\2\2\u0236\u0238"+
		"\5Z.\2\u0237\u0236\3\2\2\2\u0238\u023b\3\2\2\2\u0239\u0237\3\2\2\2\u0239"+
		"\u023a\3\2\2\2\u023a\u023c\3\2\2\2\u023b\u0239\3\2\2\2\u023c\u023d\7H"+
		"\2\2\u023dU\3\2\2\2\u023e\u023f\5X-\2\u023f\u0240\7K\2\2\u0240W\3\2\2"+
		"\2\u0241\u0243\5\30\r\2\u0242\u0241\3\2\2\2\u0243\u0246\3\2\2\2\u0244"+
		"\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0247\3\2\2\2\u0246\u0244\3\2"+
		"\2\2\u0247\u0248\58\35\2\u0248\u0249\5\60\31\2\u0249Y\3\2\2\2\u024a\u0261"+
		"\5T+\2\u024b\u0261\5V,\2\u024c\u0261\5\\/\2\u024d\u0261\5^\60\2\u024e"+
		"\u0261\5b\62\2\u024f\u0261\5d\63\2\u0250\u0261\5f\64\2\u0251\u0261\5h"+
		"\65\2\u0252\u0261\5j\66\2\u0253\u0261\5l\67\2\u0254\u0261\5n8\2\u0255"+
		"\u0261\5p9\2\u0256\u0261\5r:\2\u0257\u0261\5t;\2\u0258\u0261\5v<\2\u0259"+
		"\u0261\5x=\2\u025a\u0261\5z>\2\u025b\u0261\5|?\2\u025c\u0261\5~@\2\u025d"+
		"\u0261\5\u0080A\2\u025e\u0261\5\u0082B\2\u025f\u0261\5\u0084C\2\u0260"+
		"\u024a\3\2\2\2\u0260\u024b\3\2\2\2\u0260\u024c\3\2\2\2\u0260\u024d\3\2"+
		"\2\2\u0260\u024e\3\2\2\2\u0260\u024f\3\2\2\2\u0260\u0250\3\2\2\2\u0260"+
		"\u0251\3\2\2\2\u0260\u0252\3\2\2\2\u0260\u0253\3\2\2\2\u0260\u0254\3\2"+
		"\2\2\u0260\u0255\3\2\2\2\u0260\u0256\3\2\2\2\u0260\u0257\3\2\2\2\u0260"+
		"\u0258\3\2\2\2\u0260\u0259\3\2\2\2\u0260\u025a\3\2\2\2\u0260\u025b\3\2"+
		"\2\2\u0260\u025c\3\2\2\2\u0260\u025d\3\2\2\2\u0260\u025e\3\2\2\2\u0260"+
		"\u025f\3\2\2\2\u0261[\3\2\2\2\u0262\u0263\7\30\2\2\u0263\u0264\5\u009a"+
		"N\2\u0264\u0267\5Z.\2\u0265\u0266\7\16\2\2\u0266\u0268\5Z.\2\u0267\u0265"+
		"\3\2\2\2\u0267\u0268\3\2\2\2\u0268]\3\2\2\2\u0269\u026a\7\60\2\2\u026a"+
		"\u026b\7\"\2\2\u026b\u026c\5\u009eP\2\u026c\u026e\7G\2\2\u026d\u026f\5"+
		"`\61\2\u026e\u026d\3\2\2\2\u026f\u0270\3\2\2\2\u0270\u026e\3\2\2\2\u0270"+
		"\u0271\3\2\2\2\u0271\u0272\3\2\2\2\u0272\u0273\7H\2\2\u0273_\3\2\2\2\u0274"+
		"\u0277\7<\2\2\u0275\u0278\5\u009cO\2\u0276\u0278\7\16\2\2\u0277\u0275"+
		"\3\2\2\2\u0277\u0276\3\2\2\2\u0278\u0279\3\2\2\2\u0279\u027a\5T+\2\u027a"+
		"a\3\2\2\2\u027b\u027c\7\24\2\2\u027c\u027d\7E\2\2\u027d\u027e\5\u0092"+
		"J\2\u027e\u027f\7F\2\2\u027f\u0280\5Z.\2\u0280c\3\2\2\2\u0281\u0282\7"+
		"=\2\2\u0282\u0283\5\u009aN\2\u0283\u0284\5Z.\2\u0284e\3\2\2\2\u0285\u0286"+
		"\7\r\2\2\u0286\u0287\5Z.\2\u0287\u0288\7=\2\2\u0288\u0289\5\u009aN\2\u0289"+
		"\u028a\7K\2\2\u028ag\3\2\2\2\u028b\u028c\7\65\2\2\u028c\u0296\5T+\2\u028d"+
		"\u028f\5\u008cG\2\u028e\u028d\3\2\2\2\u028f\u0290\3\2\2\2\u0290\u028e"+
		"\3\2\2\2\u0290\u0291\3\2\2\2\u0291\u0293\3\2\2\2\u0292\u0294\5\u0090I"+
		"\2\u0293\u0292\3\2\2\2\u0293\u0294\3\2\2\2\u0294\u0297\3\2\2\2\u0295\u0297"+
		"\5\u0090I\2\u0296\u028e\3\2\2\2\u0296\u0295\3\2\2\2\u0297i\3\2\2\2\u0298"+
		"\u029a\7(\2\2\u0299\u029b\5\u009eP\2\u029a\u0299\3\2\2\2\u029a\u029b\3"+
		"\2\2\2\u029b\u029c\3\2\2\2\u029c\u029d\7K\2\2\u029dk\3\2\2\2\u029e\u029f"+
		"\7\63\2\2\u029f\u02a0\5\u009eP\2\u02a0\u02a1\7K\2\2\u02a1m\3\2\2\2\u02a2"+
		"\u02a4\7\4\2\2\u02a3\u02a5\5\u00c8e\2\u02a4\u02a3\3\2\2\2\u02a4\u02a5"+
		"\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6\u02a7\7K\2\2\u02a7o\3\2\2\2\u02a8\u02aa"+
		"\7\n\2\2\u02a9\u02ab\5\u00c8e\2\u02aa\u02a9\3\2\2\2\u02aa\u02ab\3\2\2"+
		"\2\u02ab\u02ac\3\2\2\2\u02ac\u02ad\7K\2\2\u02adq\3\2\2\2\u02ae\u02af\7"+
		"\33\2\2\u02af\u02b0\5\u009eP\2\u02b0\u02b1\7K\2\2\u02b1s\3\2\2\2\u02b2"+
		"\u02b3\7\67\2\2\u02b3\u02b4\5\u009eP\2\u02b4\u02b5\7K\2\2\u02b5u\3\2\2"+
		"\2\u02b6\u02b7\7\f\2\2\u02b7\u02b8\5\u009eP\2\u02b8\u02b9\7K\2\2\u02b9"+
		"w\3\2\2\2\u02ba\u02bb\7\66\2\2\u02bb\u02bc\5\u009eP\2\u02bc\u02bd\7K\2"+
		"\2\u02bdy\3\2\2\2\u02be\u02bf\78\2\2\u02bf\u02c1\5\u009eP\2\u02c0\u02c2"+
		"\5F$\2\u02c1\u02c0\3\2\2\2\u02c1\u02c2\3\2\2\2\u02c2\u02c3\3\2\2\2\u02c3"+
		"\u02c4\7K\2\2\u02c4{\3\2\2\2\u02c5\u02c6\7\36\2\2\u02c6\u02c7\5\u009e"+
		"P\2\u02c7\u02c8\5\u009eP\2\u02c8\u02c9\7K\2\2\u02c9}\3\2\2\2\u02ca\u02cb"+
		"\7)\2\2\u02cb\u02cd\7E\2\2\u02cc\u02ce\5\u009cO\2\u02cd\u02cc\3\2\2\2"+
		"\u02cd\u02ce\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d1\7F\2\2\u02d0\u02d2"+
		"\5T+\2\u02d1\u02d0\3\2\2\2\u02d1\u02d2\3\2\2\2\u02d2\177\3\2\2\2\u02d3"+
		"\u02d4\7K\2\2\u02d4\u0081\3\2\2\2\u02d5\u02d6\5\u009eP\2\u02d6\u02d7\7"+
		"K\2\2\u02d7\u0083\3\2\2\2\u02d8\u02d9\5\u00c8e\2\u02d9\u02da\7V\2\2\u02da"+
		"\u02db\5Z.\2\u02db\u0085\3\2\2\2\u02dc\u02de\5\30\r\2\u02dd\u02dc\3\2"+
		"\2\2\u02de\u02e1\3\2\2\2\u02df\u02dd\3\2\2\2\u02df\u02e0\3\2\2\2\u02e0"+
		"\u02e4\3\2\2\2\u02e1\u02df\3\2\2\2\u02e2\u02e5\5\u0088E\2\u02e3\u02e5"+
		"\5\u008aF\2\u02e4\u02e2\3\2\2\2\u02e4\u02e3\3\2\2\2\u02e5\u0087\3\2\2"+
		"\2\u02e6\u02e9\7\25\2\2\u02e7\u02ea\7K\2\2\u02e8\u02ea\5T+\2\u02e9\u02e7"+
		"\3\2\2\2\u02e9\u02e8\3\2\2\2\u02ea\u0089\3\2\2\2\u02eb\u02ee\7+\2\2\u02ec"+
		"\u02ef\7K\2\2\u02ed\u02ef\5T+\2\u02ee\u02ec\3\2\2\2\u02ee\u02ed\3\2\2"+
		"\2\u02ef\u008b\3\2\2\2\u02f0\u02f1\7\6\2\2\u02f1\u02f5\7E\2\2\u02f2\u02f4"+
		"\5\30\r\2\u02f3\u02f2\3\2\2\2\u02f4\u02f7\3\2\2\2\u02f5\u02f3\3\2\2\2"+
		"\u02f5\u02f6\3\2\2\2\u02f6\u02f8\3\2\2\2\u02f7\u02f5\3\2\2\2\u02f8\u02f9"+
		"\5\u008eH\2\u02f9\u02fa\5\u00c8e\2\u02fa\u02fb\7F\2\2\u02fb\u02fc\5T+"+
		"\2\u02fc\u008d\3\2\2\2\u02fd\u0302\5F$\2\u02fe\u02ff\7e\2\2\u02ff\u0301"+
		"\5F$\2\u0300\u02fe\3\2\2\2\u0301\u0304\3\2\2\2\u0302\u0300\3\2\2\2\u0302"+
		"\u0303\3\2\2\2\u0303\u008f\3\2\2\2\u0304\u0302\3\2\2\2\u0305\u0306\7\22"+
		"\2\2\u0306\u0307\5T+\2\u0307\u0091\3\2\2\2\u0308\u0315\5\u0096L\2\u0309"+
		"\u030b\5\u0094K\2\u030a\u0309\3\2\2\2\u030a\u030b\3\2\2\2\u030b\u030c"+
		"\3\2\2\2\u030c\u030e\7K\2\2\u030d\u030f\5\u009eP\2\u030e\u030d\3\2\2\2"+
		"\u030e\u030f\3\2\2\2\u030f\u0310\3\2\2\2\u0310\u0312\7K\2\2\u0311\u0313"+
		"\5\u0098M\2\u0312\u0311\3\2\2\2\u0312\u0313\3\2\2\2\u0313\u0315\3\2\2"+
		"\2\u0314\u0308\3\2\2\2\u0314\u030a\3\2\2\2\u0315\u0093\3\2\2\2\u0316\u0319"+
		"\5X-\2\u0317\u0319\5\u009cO\2\u0318\u0316\3\2\2\2\u0318\u0317\3\2\2\2"+
		"\u0319\u0095\3\2\2\2\u031a\u031c\5\30\r\2\u031b\u031a\3\2\2\2\u031c\u031f"+
		"\3\2\2\2\u031d\u031b\3\2\2\2\u031d\u031e\3\2\2\2\u031e\u0320\3\2\2\2\u031f"+
		"\u031d\3\2\2\2\u0320\u0321\58\35\2\u0321\u0322\5\u00c8e\2\u0322\u0323"+
		"\7V\2\2\u0323\u0324\5\u009eP\2\u0324\u0097\3\2\2\2\u0325\u0326\5\u009c"+
		"O\2\u0326\u0099\3\2\2\2\u0327\u0328\7E\2\2\u0328\u0329\5\u009eP\2\u0329"+
		"\u032a\7F\2\2\u032a\u009b\3\2\2\2\u032b\u0330\5\u009eP\2\u032c\u032d\7"+
		"L\2\2\u032d\u032f\5\u009eP\2\u032e\u032c\3\2\2\2\u032f\u0332\3\2\2\2\u0330"+
		"\u032e\3\2\2\2\u0330\u0331\3\2\2\2\u0331\u009d\3\2\2\2\u0332\u0330\3\2"+
		"\2\2\u0333\u0334\bP\1\2\u0334\u0335\7 \2\2\u0335\u0341\5\u00a2R\2\u0336"+
		"\u0337\7E\2\2\u0337\u0338\58\35\2\u0338\u0339\7F\2\2\u0339\u033a\5\u009e"+
		"P\24\u033a\u0341\3\2\2\2\u033b\u033c\t\3\2\2\u033c\u0341\5\u009eP\22\u033d"+
		"\u033e\t\4\2\2\u033e\u0341\5\u009eP\21\u033f\u0341\5\u00a0Q\2\u0340\u0333"+
		"\3\2\2\2\u0340\u0336\3\2\2\2\u0340\u033b\3\2\2\2\u0340\u033d\3\2\2\2\u0340"+
		"\u033f\3\2\2\2\u0341\u03a0\3\2\2\2\u0342\u0343\f\20\2\2\u0343\u0344\t"+
		"\5\2\2\u0344\u039f\5\u009eP\21\u0345\u0346\f\17\2\2\u0346\u0347\t\6\2"+
		"\2\u0347\u039f\5\u009eP\20\u0348\u0350\f\16\2\2\u0349\u034a\7R\2\2\u034a"+
		"\u0351\7R\2\2\u034b\u034c\7Q\2\2\u034c\u034d\7Q\2\2\u034d\u0351\7Q\2\2"+
		"\u034e\u034f\7Q\2\2\u034f\u0351\7Q\2\2\u0350\u0349\3\2\2\2\u0350\u034b"+
		"\3\2\2\2\u0350\u034e\3\2\2\2\u0351\u0352\3\2\2\2\u0352\u039f\5\u009eP"+
		"\17\u0353\u035c\f\r\2\2\u0354\u0355\7R\2\2\u0355\u035d\7N\2\2\u0356\u0357"+
		"\7Q\2\2\u0357\u035d\7N\2\2\u0358\u035d\7O\2\2\u0359\u035d\7P\2\2\u035a"+
		"\u035d\7Q\2\2\u035b\u035d\7R\2\2\u035c\u0354\3\2\2\2\u035c\u0356\3\2\2"+
		"\2\u035c\u0358\3\2\2\2\u035c\u0359\3\2\2\2\u035c\u035a\3\2\2\2\u035c\u035b"+
		"\3\2\2\2\u035d\u035e\3\2\2\2\u035e\u039f\5\u009eP\16\u035f\u0360\f\13"+
		"\2\2\u0360\u0361\t\7\2\2\u0361\u039f\5\u009eP\f\u0362\u0363\f\n\2\2\u0363"+
		"\u0364\7d\2\2\u0364\u039f\5\u009eP\13\u0365\u0366\f\t\2\2\u0366\u0367"+
		"\7f\2\2\u0367\u039f\5\u009eP\n\u0368\u0369\f\b\2\2\u0369\u036a\7e\2\2"+
		"\u036a\u039f\5\u009eP\t\u036b\u036c\f\7\2\2\u036c\u036d\7\\\2\2\u036d"+
		"\u039f\5\u009eP\b\u036e\u036f\f\6\2\2\u036f\u0370\7]\2\2\u0370\u039f\5"+
		"\u009eP\7\u0371\u0372\f\5\2\2\u0372\u0373\7U\2\2\u0373\u0374\5\u009eP"+
		"\2\u0374\u0375\7V\2\2\u0375\u0376\5\u009eP\6\u0376\u039f\3\2\2\2\u0377"+
		"\u0378\f\4\2\2\u0378\u0379\t\b\2\2\u0379\u039f\5\u009eP\4\u037a\u037b"+
		"\f\34\2\2\u037b\u037c\7M\2\2\u037c\u039f\5\u00c8e\2\u037d\u037e\f\33\2"+
		"\2\u037e\u037f\7M\2\2\u037f\u039f\7\62\2\2\u0380\u0381\f\32\2\2\u0381"+
		"\u0382\7M\2\2\u0382\u0384\7 \2\2\u0383\u0385\5\u00ba^\2\u0384\u0383\3"+
		"\2\2\2\u0384\u0385\3\2\2\2\u0385\u0386\3\2\2\2\u0386\u039f\5\u00a8U\2"+
		"\u0387\u0388\f\31\2\2\u0388\u0389\7M\2\2\u0389\u038a\7/\2\2\u038a\u039f"+
		"\5\u00c0a\2\u038b\u038c\f\30\2\2\u038c\u038d\7M\2\2\u038d\u039f\5\u00b8"+
		"]\2\u038e\u038f\f\27\2\2\u038f\u0390\7I\2\2\u0390\u0391\5\u009eP\2\u0391"+
		"\u0392\7J\2\2\u0392\u039f\3\2\2\2\u0393\u0394\f\26\2\2\u0394\u0396\7E"+
		"\2\2\u0395\u0397\5\u009cO\2\u0396\u0395\3\2\2\2\u0396\u0397\3\2\2\2\u0397"+
		"\u0398\3\2\2\2\u0398\u039f\7F\2\2\u0399\u039a\f\23\2\2\u039a\u039f\t\t"+
		"\2\2\u039b\u039c\f\f\2\2\u039c\u039d\7\34\2\2\u039d\u039f\58\35\2\u039e"+
		"\u0342\3\2\2\2\u039e\u0345\3\2\2\2\u039e\u0348\3\2\2\2\u039e\u0353\3\2"+
		"\2\2\u039e\u035f\3\2\2\2\u039e\u0362\3\2\2\2\u039e\u0365\3\2\2\2\u039e"+
		"\u0368\3\2\2\2\u039e\u036b\3\2\2\2\u039e\u036e\3\2\2\2\u039e\u0371\3\2"+
		"\2\2\u039e\u0377\3\2\2\2\u039e\u037a\3\2\2\2\u039e\u037d\3\2\2\2\u039e"+
		"\u0380\3\2\2\2\u039e\u0387\3\2\2\2\u039e\u038b\3\2\2\2\u039e\u038e\3\2"+
		"\2\2\u039e\u0393\3\2\2\2\u039e\u0399\3\2\2\2\u039e\u039b\3\2\2\2\u039f"+
		"\u03a2\3\2\2\2\u03a0\u039e\3\2\2\2\u03a0\u03a1\3\2\2\2\u03a1\u009f\3\2"+
		"\2\2\u03a2\u03a0\3\2\2\2\u03a3\u03a4\7E\2\2\u03a4\u03a5\5\u009eP\2\u03a5"+
		"\u03a6\7F\2\2\u03a6\u03ba\3\2\2\2\u03a7\u03ba\7\62\2\2\u03a8\u03ba\7/"+
		"\2\2\u03a9\u03ba\5H%\2\u03aa\u03ba\5\u00c8e\2\u03ab\u03ac\58\35\2\u03ac"+
		"\u03ad\7M\2\2\u03ad\u03ae\7\b\2\2\u03ae\u03ba\3\2\2\2\u03af\u03b0\7:\2"+
		"\2\u03b0\u03b1\7M\2\2\u03b1\u03ba\7\b\2\2\u03b2\u03b6\5\u00ba^\2\u03b3"+
		"\u03b7\5\u00c2b\2\u03b4\u03b5\7\62\2\2\u03b5\u03b7\5\u00c4c\2\u03b6\u03b3"+
		"\3\2\2\2\u03b6\u03b4\3\2\2\2\u03b7\u03ba\3\2\2\2\u03b8\u03ba\5\u00c6d"+
		"\2\u03b9\u03a3\3\2\2\2\u03b9\u03a7\3\2\2\2\u03b9\u03a8\3\2\2\2\u03b9\u03a9"+
		"\3\2\2\2\u03b9\u03aa\3\2\2\2\u03b9\u03ab\3\2\2\2\u03b9\u03af\3\2\2\2\u03b9"+
		"\u03b2\3\2\2\2\u03b9\u03b8\3\2\2\2\u03ba\u00a1\3\2\2\2\u03bb\u03bc\5\u00ba"+
		"^\2\u03bc\u03bd\5\u00a4S\2\u03bd\u03be\5\u00b6\\\2\u03be\u03c7\3\2\2\2"+
		"\u03bf\u03c4\5\u00a4S\2\u03c0\u03c5\5\u00aaV\2\u03c1\u03c5\5\u00b6\\\2"+
		"\u03c2\u03c5\5\u00acW\2\u03c3\u03c5\5\u00b0Y\2\u03c4\u03c0\3\2\2\2\u03c4"+
		"\u03c1\3\2\2\2\u03c4\u03c2\3\2\2\2\u03c4\u03c3\3\2\2\2\u03c5\u03c7\3\2"+
		"\2\2\u03c6\u03bb\3\2\2\2\u03c6\u03bf\3\2\2\2\u03c7\u00a3\3\2\2\2\u03c8"+
		"\u03cd\5\u00a6T\2\u03c9\u03ca\7M\2\2\u03ca\u03cc\5\u00a6T\2\u03cb\u03c9"+
		"\3\2\2\2\u03cc\u03cf\3\2\2\2\u03cd\u03cb\3\2\2\2\u03cd\u03ce\3\2\2\2\u03ce"+
		"\u00a5\3\2\2\2\u03cf\u03cd\3\2\2\2\u03d0\u03d2\5\u00c8e\2\u03d1\u03d3"+
		"\5\u00bc_\2\u03d2\u03d1\3\2\2\2\u03d2\u03d3\3\2\2\2\u03d3\u00a7\3\2\2"+
		"\2\u03d4\u03d6\5\u00c8e\2\u03d5\u03d7\5\u00be`\2\u03d6\u03d5\3\2\2\2\u03d6"+
		"\u03d7\3\2\2\2\u03d7\u03d8\3\2\2\2\u03d8\u03d9\5\u00b6\\\2\u03d9\u00a9"+
		"\3\2\2\2\u03da\u03ec\7I\2\2\u03db\u03dc\7J\2\2\u03dc\u03dd\5:\36\2\u03dd"+
		"\u03de\5\66\34\2\u03de\u03ed\3\2\2\2\u03df\u03e0\5\u009eP\2\u03e0\u03e7"+
		"\7J\2\2\u03e1\u03e2\7I\2\2\u03e2\u03e3\5\u009eP\2\u03e3\u03e4\7J\2\2\u03e4"+
		"\u03e6\3\2\2\2\u03e5\u03e1\3\2\2\2\u03e6\u03e9\3\2\2\2\u03e7\u03e5\3\2"+
		"\2\2\u03e7\u03e8\3\2\2\2\u03e8\u03ea\3\2\2\2\u03e9\u03e7\3\2\2\2\u03ea"+
		"\u03eb\5:\36\2\u03eb\u03ed\3\2\2\2\u03ec\u03db\3\2\2\2\u03ec\u03df\3\2"+
		"\2\2\u03ed\u00ab\3\2\2\2\u03ee\u03ef\7G\2\2\u03ef\u03f4\5\u00aeX\2\u03f0"+
		"\u03f1\7L\2\2\u03f1\u03f3\5\u00aeX\2\u03f2\u03f0\3\2\2\2\u03f3\u03f6\3"+
		"\2\2\2\u03f4\u03f2\3\2\2\2\u03f4\u03f5\3\2\2\2\u03f5\u03f7\3\2\2\2\u03f6"+
		"\u03f4\3\2\2\2\u03f7\u03f8\7H\2\2\u03f8\u00ad\3\2\2\2\u03f9\u03fa\5\u00b4"+
		"[\2\u03fa\u03fb\7h\2\2\u03fb\u03fc\5\u00b2Z\2\u03fc\u00af\3\2\2\2\u03fd"+
		"\u03fe\7G\2\2\u03fe\u0403\5\u00b2Z\2\u03ff\u0400\7L\2\2\u0400\u0402\5"+
		"\u00b2Z\2\u0401\u03ff\3\2\2\2\u0402\u0405\3\2\2\2\u0403\u0401\3\2\2\2"+
		"\u0403\u0404\3\2\2\2\u0404\u0406\3\2\2\2\u0405\u0403\3\2\2\2\u0406\u0407"+
		"\7H\2\2\u0407\u00b1\3\2\2\2\u0408\u040b\5H%\2\u0409\u040b\5\u009eP\2\u040a"+
		"\u0408\3\2\2\2\u040a\u0409\3\2\2\2\u040b\u00b3\3\2\2\2\u040c\u040f\5\u00c8"+
		"e\2\u040d\u040f\5\u009eP\2\u040e\u040c\3\2\2\2\u040e\u040d\3\2\2\2\u040f"+
		"\u00b5\3\2\2\2\u0410\u0417\5\u00c4c\2\u0411\u0413\7G\2\2\u0412\u0414\5"+
		"\u009cO\2\u0413\u0412\3\2\2\2\u0413\u0414\3\2\2\2\u0414\u0415\3\2\2\2"+
		"\u0415\u0417\7H\2\2\u0416\u0410\3\2\2\2\u0416\u0411\3\2\2\2\u0417\u00b7"+
		"\3\2\2\2\u0418\u0419\5\u00ba^\2\u0419\u041a\5\u00c2b\2\u041a\u00b9\3\2"+
		"\2\2\u041b\u041c\7R\2\2\u041c\u041d\5\20\t\2\u041d\u041e\7Q\2\2\u041e"+
		"\u00bb\3\2\2\2\u041f\u0420\7R\2\2\u0420\u0423\7Q\2\2\u0421\u0423\5> \2"+
		"\u0422\u041f\3\2\2\2\u0422\u0421\3\2\2\2\u0423\u00bd\3\2\2\2\u0424\u0425"+
		"\7R\2\2\u0425\u0428\7Q\2\2\u0426\u0428\5\u00ba^\2\u0427\u0424\3\2\2\2"+
		"\u0427\u0426\3\2\2\2\u0428\u00bf\3\2\2\2\u0429\u0430\5\u00c4c\2\u042a"+
		"\u042b\7M\2\2\u042b\u042d\5\u00c8e\2\u042c\u042e\5\u00c4c\2\u042d\u042c"+
		"\3\2\2\2\u042d\u042e\3\2\2\2\u042e\u0430\3\2\2\2\u042f\u0429\3\2\2\2\u042f"+
		"\u042a\3\2\2\2\u0430\u00c1\3\2\2\2\u0431\u0432\7/\2\2\u0432\u0437\5\u00c0"+
		"a\2\u0433\u0434\5\u00c8e\2\u0434\u0435\5\u00c4c\2\u0435\u0437\3\2\2\2"+
		"\u0436\u0431\3\2\2\2\u0436\u0433\3\2\2\2\u0437\u00c3\3\2\2\2\u0438\u043a"+
		"\7E\2\2\u0439\u043b\5\u009cO\2\u043a\u0439\3\2\2\2\u043a\u043b\3\2\2\2"+
		"\u043b\u043c\3\2\2\2\u043c\u043d\7F\2\2\u043d\u00c5\3\2\2\2\u043e\u0443"+
		"\7I\2\2\u043f\u0442\5\u00c6d\2\u0440\u0442\n\n\2\2\u0441\u043f\3\2\2\2"+
		"\u0441\u0440\3\2\2\2\u0442\u0445\3\2\2\2\u0443\u0444\3\2\2\2\u0443\u0441"+
		"\3\2\2\2\u0444\u0446\3\2\2\2\u0445\u0443\3\2\2\2\u0446\u0447\7J\2\2\u0447"+
		"\u00c7\3\2\2\2\u0448\u0449\t\13\2\2\u0449\u00c9\3\2\2\2o\u00d0\u00d7\u00de"+
		"\u00e2\u00e8\u00ec\u00f4\u00fd\u0103\u0108\u010b\u0111\u011a\u0121\u012a"+
		"\u0131\u0137\u013b\u0150\u0159\u015e\u0163\u0169\u017a\u0181\u0186\u018d"+
		"\u0195\u019f\u01a7\u01b2\u01b8\u01bc\u01c4\u01c8\u01ca\u01d3\u01dc\u01e1"+
		"\u01e9\u01f2\u01f8\u0203\u020d\u0210\u0214\u0219\u0223\u022b\u022e\u0231"+
		"\u0239\u0244\u0260\u0267\u0270\u0277\u0290\u0293\u0296\u029a\u02a4\u02aa"+
		"\u02c1\u02cd\u02d1\u02df\u02e4\u02e9\u02ee\u02f5\u0302\u030a\u030e\u0312"+
		"\u0314\u0318\u031d\u0330\u0340\u0350\u035c\u0384\u0396\u039e\u03a0\u03b6"+
		"\u03b9\u03c4\u03c6\u03cd\u03d2\u03d6\u03e7\u03ec\u03f4\u0403\u040a\u040e"+
		"\u0413\u0416\u0422\u0427\u042d\u042f\u0436\u043a\u0441\u0443";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}