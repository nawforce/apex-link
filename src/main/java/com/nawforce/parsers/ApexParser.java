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
		RULE_runAsStatement = 62, RULE_expressionStatement = 63, RULE_propertyBlock = 64, 
		RULE_getter = 65, RULE_setter = 66, RULE_catchClause = 67, RULE_catchType = 68, 
		RULE_finallyBlock = 69, RULE_forControl = 70, RULE_forInit = 71, RULE_enhancedForControl = 72, 
		RULE_forUpdate = 73, RULE_parExpression = 74, RULE_expressionList = 75, 
		RULE_expression = 76, RULE_primary = 77, RULE_creator = 78, RULE_createdName = 79, 
		RULE_idCreatedNamePair = 80, RULE_noRest = 81, RULE_classCreatorRest = 82, 
		RULE_arrayCreatorRest = 83, RULE_mapCreatorRest = 84, RULE_mapCreatorRestPair = 85, 
		RULE_setCreatorRest = 86, RULE_arguments = 87, RULE_soqlLiteral = 88, 
		RULE_id = 89;
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
			"expressionStatement", "propertyBlock", "getter", "setter", "catchClause", 
			"catchType", "finallyBlock", "forControl", "forInit", "enhancedForControl", 
			"forUpdate", "parExpression", "expressionList", "expression", "primary", 
			"creator", "createdName", "idCreatedNamePair", "noRest", "classCreatorRest", 
			"arrayCreatorRest", "mapCreatorRest", "mapCreatorRestPair", "setCreatorRest", 
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
			setState(180);
			typeDeclaration();
			setState(181);
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
			setState(204);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(183);
					modifier();
					}
					}
					setState(188);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(189);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(190);
					modifier();
					}
					}
					setState(195);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(196);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(197);
					modifier();
					}
					}
					setState(202);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(203);
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
			setState(206);
			match(CLASS);
			setState(207);
			id();
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(208);
				match(EXTENDS);
				setState(209);
				typeRef();
				}
			}

			setState(214);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(212);
				match(IMPLEMENTS);
				setState(213);
				typeList();
				}
			}

			setState(216);
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
			setState(218);
			match(ENUM);
			setState(219);
			id();
			setState(220);
			match(LBRACE);
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier || _la==AT) {
				{
				setState(221);
				enumConstants();
				}
			}

			setState(224);
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
			setState(226);
			enumConstant();
			setState(231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(227);
				match(COMMA);
				setState(228);
				enumConstant();
				}
				}
				setState(233);
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
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(234);
					modifier();
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(240);
			id();
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(241);
				arguments();
				}
			}

			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(244);
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
			setState(247);
			match(INTERFACE);
			setState(248);
			id();
			setState(251);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(249);
				match(EXTENDS);
				setState(250);
				typeList();
				}
			}

			setState(253);
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
			setState(255);
			typeRef();
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(256);
				match(COMMA);
				setState(257);
				typeRef();
				}
				}
				setState(262);
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
			setState(263);
			match(LBRACE);
			setState(267);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || ((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (LBRACE - 69)) | (1L << (SEMI - 69)) | (1L << (Identifier - 69)) | (1L << (AT - 69)))) != 0)) {
				{
				{
				setState(264);
				classBodyDeclaration();
				}
				}
				setState(269);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(270);
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
			setState(272);
			match(LBRACE);
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || ((((_la - 73)) & ~0x3f) == 0 && ((1L << (_la - 73)) & ((1L << (SEMI - 73)) | (1L << (Identifier - 73)) | (1L << (AT - 73)))) != 0)) {
				{
				{
				setState(273);
				interfaceBodyDeclaration();
				}
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(279);
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
			setState(293);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(281);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(282);
					match(STATIC);
					}
				}

				setState(285);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(286);
						modifier();
						}
						} 
					}
					setState(291);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				}
				setState(292);
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
			setState(314);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(295);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(296);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(297);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(298);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(299);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(300);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(301);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(302);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(303);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(304);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(305);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(306);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(307);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(308);
				match(WITH);
				setState(309);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(310);
				match(WITHOUT);
				setState(311);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(312);
				match(INHERITED);
				setState(313);
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
			setState(323);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(316);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(317);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(318);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(319);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(320);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(321);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(322);
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
			setState(328);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(325);
					modifier();
					}
					} 
				}
				setState(330);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			setState(333);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(331);
				typeRef();
				}
				break;
			case 2:
				{
				setState(332);
				match(VOID);
				}
				break;
			}
			setState(335);
			id();
			setState(336);
			formalParameters();
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(337);
				block();
				}
				break;
			case SEMI:
				{
				setState(338);
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
			setState(341);
			qualifiedName();
			setState(342);
			formalParameters();
			setState(343);
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
			setState(345);
			typeRef();
			setState(346);
			variableDeclarators();
			setState(347);
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
			setState(349);
			typeRef();
			setState(350);
			variableDeclarators();
			setState(351);
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
			setState(353);
			match(LBRACE);
			setState(354);
			propertyBlock();
			setState(356);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				setState(355);
				propertyBlock();
				}
			}

			setState(358);
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
			setState(368);
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
				setState(363);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(360);
						modifier();
						}
						} 
					}
					setState(365);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
				setState(366);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(367);
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
			setState(375);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(371);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(373);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(374);
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
			setState(377);
			typeRef();
			setState(378);
			constantDeclarator();
			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(379);
				match(COMMA);
				setState(380);
				constantDeclarator();
				}
				}
				setState(385);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(386);
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
			setState(388);
			id();
			setState(393);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(389);
				match(LBRACK);
				setState(390);
				match(RBRACK);
				}
				}
				setState(395);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(396);
			match(ASSIGN);
			setState(397);
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
			setState(401);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(399);
				typeRef();
				}
				break;
			case 2:
				{
				setState(400);
				match(VOID);
				}
				break;
			}
			setState(403);
			id();
			setState(404);
			formalParameters();
			setState(405);
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
			setState(407);
			variableDeclarator();
			setState(412);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(408);
				match(COMMA);
				setState(409);
				variableDeclarator();
				}
				}
				setState(414);
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
			setState(415);
			id();
			setState(418);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(416);
				match(ASSIGN);
				setState(417);
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
			setState(422);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(420);
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
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(421);
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
			setState(424);
			match(LBRACE);
			setState(436);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(425);
				variableInitializer();
				setState(430);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(426);
						match(COMMA);
						setState(427);
						variableInitializer();
						}
						} 
					}
					setState(432);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				setState(434);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(433);
					match(COMMA);
					}
				}

				}
			}

			setState(438);
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
			setState(440);
			typeName();
			setState(445);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(441);
					match(DOT);
					setState(442);
					typeName();
					}
					} 
				}
				setState(447);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			}
			setState(448);
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
			setState(454);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(450);
					match(LBRACK);
					setState(451);
					match(RBRACK);
					}
					} 
				}
				setState(456);
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
			setState(457);
			id();
			setState(459);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(458);
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
			setState(461);
			match(LT);
			setState(462);
			typeList();
			setState(463);
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
			setState(465);
			match(LPAREN);
			setState(467);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier || _la==AT) {
				{
				setState(466);
				formalParameterList();
				}
			}

			setState(469);
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
			setState(471);
			formalParameter();
			setState(476);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(472);
				match(COMMA);
				setState(473);
				formalParameter();
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
			setState(482);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(479);
					modifier();
					}
					} 
				}
				setState(484);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			setState(485);
			typeRef();
			setState(486);
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
			setState(488);
			id();
			setState(493);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(489);
				match(DOT);
				setState(490);
				id();
				}
				}
				setState(495);
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
			setState(496);
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
			setState(498);
			match(AT);
			setState(499);
			qualifiedName();
			setState(506);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(500);
				match(LPAREN);
				setState(503);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(501);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(502);
					elementValue();
					}
					break;
				}
				setState(505);
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
			setState(508);
			elementValuePair();
			setState(515);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==COMMA || _la==Identifier) {
				{
				{
				setState(510);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(509);
					match(COMMA);
					}
				}

				setState(512);
				elementValuePair();
				}
				}
				setState(517);
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
			setState(518);
			id();
			setState(519);
			match(ASSIGN);
			setState(520);
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
			setState(525);
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
			case BANG:
			case TILDE:
			case INC:
			case DEC:
			case ADD:
			case SUB:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(522);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(523);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(524);
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
			setState(527);
			match(LBRACE);
			setState(536);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(528);
				elementValue();
				setState(533);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(529);
						match(COMMA);
						setState(530);
						elementValue();
						}
						} 
					}
					setState(535);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
				}
				}
			}

			setState(539);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(538);
				match(COMMA);
				}
			}

			setState(541);
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
			setState(543);
			match(LBRACE);
			setState(547);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(544);
				statement();
				}
				}
				setState(549);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(550);
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
			setState(552);
			localVariableDeclaration();
			setState(553);
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
			setState(558);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(555);
					modifier();
					}
					} 
				}
				setState(560);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			setState(561);
			typeRef();
			setState(562);
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
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
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
			setState(584);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(564);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(565);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(566);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(567);
				switchStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(568);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(569);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(570);
				doWhileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(571);
				tryStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(572);
				returnStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(573);
				throwStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(574);
				breakStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(575);
				continueStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(576);
				insertStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(577);
				updateStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(578);
				deleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(579);
				undeleteStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(580);
				upsertStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(581);
				mergeStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(582);
				runAsStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(583);
				expressionStatement();
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
			setState(586);
			match(IF);
			setState(587);
			parExpression();
			setState(588);
			statement();
			setState(591);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(589);
				match(ELSE);
				setState(590);
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
			setState(593);
			match(SWITCH);
			setState(594);
			match(ON);
			setState(595);
			expression(0);
			setState(596);
			match(LBRACE);
			setState(598); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(597);
				whenControl();
				}
				}
				setState(600); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(602);
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
			setState(604);
			match(WHEN);
			setState(607);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(605);
				expressionList();
				}
				break;
			case 2:
				{
				setState(606);
				match(ELSE);
				}
				break;
			}
			setState(609);
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
			setState(611);
			match(FOR);
			setState(612);
			match(LPAREN);
			setState(613);
			forControl();
			setState(614);
			match(RPAREN);
			setState(615);
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
			setState(617);
			match(WHILE);
			setState(618);
			parExpression();
			setState(619);
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
			setState(621);
			match(DO);
			setState(622);
			statement();
			setState(623);
			match(WHILE);
			setState(624);
			parExpression();
			setState(625);
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
			setState(627);
			match(TRY);
			setState(628);
			block();
			setState(638);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(630); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(629);
						catchClause();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(632); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(635);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
				case 1:
					{
					setState(634);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case FINALLY:
				{
				setState(637);
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
			setState(640);
			match(RETURN);
			setState(642);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(641);
				expression(0);
				}
			}

			setState(644);
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
			setState(646);
			match(THROW);
			setState(647);
			expression(0);
			setState(648);
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
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(650);
			match(BREAK);
			setState(651);
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
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(653);
			match(CONTINUE);
			setState(654);
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
			setState(656);
			match(INSERT);
			setState(657);
			expression(0);
			setState(658);
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
			setState(660);
			match(UPDATE);
			setState(661);
			expression(0);
			setState(662);
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
			setState(664);
			match(DELETE);
			setState(665);
			expression(0);
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
			setState(668);
			match(UNDELETE);
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
			setState(672);
			match(UPSERT);
			setState(673);
			expression(0);
			setState(675);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(674);
				qualifiedName();
				}
			}

			setState(677);
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
			setState(679);
			match(MERGE);
			setState(680);
			expression(0);
			setState(681);
			expression(0);
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
			setState(684);
			match(RUNAS);
			setState(685);
			match(LPAREN);
			setState(687);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(686);
				expressionList();
				}
			}

			setState(689);
			match(RPAREN);
			setState(691);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				{
				setState(690);
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
		enterRule(_localctx, 126, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
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
		enterRule(_localctx, 128, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(696);
				modifier();
				}
				}
				setState(701);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(704);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(702);
				getter();
				}
				break;
			case SET:
				{
				setState(703);
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
		enterRule(_localctx, 130, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(706);
			match(GET);
			setState(709);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(707);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(708);
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
		enterRule(_localctx, 132, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(711);
			match(SET);
			setState(714);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(712);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(713);
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
		enterRule(_localctx, 134, RULE_catchClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(716);
			match(CATCH);
			setState(717);
			match(LPAREN);
			setState(721);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(718);
					modifier();
					}
					} 
				}
				setState(723);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,68,_ctx);
			}
			setState(724);
			catchType();
			setState(725);
			id();
			setState(726);
			match(RPAREN);
			setState(727);
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
		enterRule(_localctx, 136, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(729);
			qualifiedName();
			setState(734);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(730);
				match(BITOR);
				setState(731);
				qualifiedName();
				}
				}
				setState(736);
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
		enterRule(_localctx, 138, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(737);
			match(FINALLY);
			setState(738);
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
		enterRule(_localctx, 140, RULE_forControl);
		int _la;
		try {
			setState(752);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(740);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(742);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(741);
					forInit();
					}
				}

				setState(744);
				match(SEMI);
				setState(746);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(745);
					expression(0);
					}
				}

				setState(748);
				match(SEMI);
				setState(750);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(749);
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
		enterRule(_localctx, 142, RULE_forInit);
		try {
			setState(756);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(754);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(755);
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
		enterRule(_localctx, 144, RULE_enhancedForControl);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(761);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(758);
					modifier();
					}
					} 
				}
				setState(763);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,75,_ctx);
			}
			setState(764);
			typeRef();
			setState(765);
			id();
			setState(766);
			match(COLON);
			setState(767);
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
		enterRule(_localctx, 146, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(769);
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
		enterRule(_localctx, 148, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			match(LPAREN);
			setState(772);
			expression(0);
			setState(773);
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
		enterRule(_localctx, 150, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(775);
			expression(0);
			setState(780);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(776);
				match(COMMA);
				setState(777);
				expression(0);
				}
				}
				setState(782);
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
	public static class NewExpressionContext extends ExpressionContext {
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
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
	public static class Alt12ExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TILDE() { return getToken(ApexParser.TILDE, 0); }
		public TerminalNode BANG() { return getToken(ApexParser.BANG, 0); }
		public Alt12ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
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
	public static class Alt26ExpressionContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public Alt26ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
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
		int _startState = 152;
		enterRecursionRule(_localctx, 152, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(796);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(784);
				match(NEW);
				setState(785);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(786);
				match(LPAREN);
				setState(787);
				typeRef();
				setState(788);
				match(RPAREN);
				setState(789);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(791);
				_la = _input.LA(1);
				if ( !(((((_la - 92)) & ~0x3f) == 0 && ((1L << (_la - 92)) & ((1L << (INC - 92)) | (1L << (DEC - 92)) | (1L << (ADD - 92)) | (1L << (SUB - 92)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(792);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(793);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(794);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(795);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(875);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(873);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(798);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(799);
						_la = _input.LA(1);
						if ( !(((((_la - 96)) & ~0x3f) == 0 && ((1L << (_la - 96)) & ((1L << (MUL - 96)) | (1L << (DIV - 96)) | (1L << (MOD - 96)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(800);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(801);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(802);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(803);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(804);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(812);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
						case 1:
							{
							setState(805);
							match(LT);
							setState(806);
							match(LT);
							}
							break;
						case 2:
							{
							setState(807);
							match(GT);
							setState(808);
							match(GT);
							setState(809);
							match(GT);
							}
							break;
						case 3:
							{
							setState(810);
							match(GT);
							setState(811);
							match(GT);
							}
							break;
						}
						setState(814);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(815);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(824);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
						case 1:
							{
							setState(816);
							match(LT);
							setState(817);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(818);
							match(GT);
							setState(819);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(820);
							match(LE);
							}
							break;
						case 4:
							{
							setState(821);
							match(GE);
							}
							break;
						case 5:
							{
							setState(822);
							match(GT);
							}
							break;
						case 6:
							{
							setState(823);
							match(LT);
							}
							break;
						}
						setState(826);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(827);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(828);
						_la = _input.LA(1);
						if ( !(((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (EQUAL - 85)) | (1L << (TRIPLEEQUAL - 85)) | (1L << (NOTEQUAL - 85)) | (1L << (LESSANDGREATER - 85)) | (1L << (TRIPLENOTEQUAL - 85)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(829);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(830);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(831);
						match(BITAND);
						setState(832);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(833);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(834);
						match(CARET);
						setState(835);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(836);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(837);
						match(BITOR);
						setState(838);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(839);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(840);
						match(AND);
						setState(841);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(842);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(843);
						match(OR);
						setState(844);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(845);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(846);
						match(QUESTION);
						setState(847);
						expression(0);
						setState(848);
						match(COLON);
						setState(849);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(851);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(852);
						_la = _input.LA(1);
						if ( !(((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & ((1L << (ASSIGN - 76)) | (1L << (ADD_ASSIGN - 76)) | (1L << (SUB_ASSIGN - 76)) | (1L << (MUL_ASSIGN - 76)) | (1L << (DIV_ASSIGN - 76)) | (1L << (AND_ASSIGN - 76)) | (1L << (OR_ASSIGN - 76)) | (1L << (XOR_ASSIGN - 76)) | (1L << (MOD_ASSIGN - 76)) | (1L << (LSHIFT_ASSIGN - 76)) | (1L << (RSHIFT_ASSIGN - 76)) | (1L << (URSHIFT_ASSIGN - 76)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(853);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(854);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(855);
						match(DOT);
						setState(856);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(857);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(858);
						match(LBRACK);
						setState(859);
						expression(0);
						setState(860);
						match(RBRACK);
						}
						break;
					case 15:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(862);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(863);
						match(LPAREN);
						setState(865);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(864);
							expressionList();
							}
						}

						setState(867);
						match(RPAREN);
						}
						break;
					case 16:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(868);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(869);
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
					case 17:
						{
						_localctx = new Alt17ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(870);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(871);
						match(INSTANCEOF);
						setState(872);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(877);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,82,_ctx);
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
	public static class ThisPrimaryContext extends PrimaryContext {
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public ThisPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class SoqlPrimaryContext extends PrimaryContext {
		public SoqlLiteralContext soqlLiteral() {
			return getRuleContext(SoqlLiteralContext.class,0);
		}
		public SoqlPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class SuperPrimaryContext extends PrimaryContext {
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public SuperPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class LiteralPrimaryContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class RefPrimaryContext extends PrimaryContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public RefPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class SubPrimaryContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public SubPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_primary);
		try {
			setState(887);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				_localctx = new SubPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(878);
				match(LPAREN);
				setState(879);
				expression(0);
				setState(880);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ThisPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(882);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new SuperPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(883);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new LiteralPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(884);
				literal();
				}
				break;
			case 5:
				_localctx = new RefPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(885);
				typeRef();
				}
				break;
			case 6:
				_localctx = new SoqlPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(886);
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
		public CreatedNameContext createdName() {
			return getRuleContext(CreatedNameContext.class,0);
		}
		public NoRestContext noRest() {
			return getRuleContext(NoRestContext.class,0);
		}
		public ClassCreatorRestContext classCreatorRest() {
			return getRuleContext(ClassCreatorRestContext.class,0);
		}
		public ArrayCreatorRestContext arrayCreatorRest() {
			return getRuleContext(ArrayCreatorRestContext.class,0);
		}
		public MapCreatorRestContext mapCreatorRest() {
			return getRuleContext(MapCreatorRestContext.class,0);
		}
		public SetCreatorRestContext setCreatorRest() {
			return getRuleContext(SetCreatorRestContext.class,0);
		}
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_creator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(889);
			createdName();
			setState(895);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				{
				setState(890);
				noRest();
				}
				break;
			case 2:
				{
				setState(891);
				classCreatorRest();
				}
				break;
			case 3:
				{
				setState(892);
				arrayCreatorRest();
				}
				break;
			case 4:
				{
				setState(893);
				mapCreatorRest();
				}
				break;
			case 5:
				{
				setState(894);
				setCreatorRest();
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
		enterRule(_localctx, 158, RULE_createdName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(897);
			idCreatedNamePair();
			setState(902);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(898);
				match(DOT);
				setState(899);
				idCreatedNamePair();
				}
				}
				setState(904);
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
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TypeListContext typeList() {
			return getRuleContext(TypeListContext.class,0);
		}
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public IdCreatedNamePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idCreatedNamePair; }
	}

	public final IdCreatedNamePairContext idCreatedNamePair() throws RecognitionException {
		IdCreatedNamePairContext _localctx = new IdCreatedNamePairContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(905);
			id();
			setState(910);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(906);
				match(LT);
				setState(907);
				typeList();
				setState(908);
				match(GT);
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

	public static class NoRestContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public NoRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noRest; }
	}

	public final NoRestContext noRest() throws RecognitionException {
		NoRestContext _localctx = new NoRestContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_noRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(912);
			match(LBRACE);
			setState(913);
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

	public static class ClassCreatorRestContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ClassCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classCreatorRest; }
	}

	public final ClassCreatorRestContext classCreatorRest() throws RecognitionException {
		ClassCreatorRestContext _localctx = new ClassCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_classCreatorRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(915);
			arguments();
			}
		}
		catch (RecognitionException re) {
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
		public TerminalNode LBRACK() { return getToken(ApexParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(ApexParser.RBRACK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayInitializerContext arrayInitializer() {
			return getRuleContext(ArrayInitializerContext.class,0);
		}
		public ArrayCreatorRestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayCreatorRest; }
	}

	public final ArrayCreatorRestContext arrayCreatorRest() throws RecognitionException {
		ArrayCreatorRestContext _localctx = new ArrayCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_arrayCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(917);
			match(LBRACK);
			setState(919);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(918);
				expression(0);
				}
			}

			setState(921);
			match(RBRACK);
			setState(923);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
			case 1:
				{
				setState(922);
				arrayInitializer();
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
		enterRule(_localctx, 168, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(925);
			match(LBRACE);
			setState(926);
			mapCreatorRestPair();
			setState(931);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(927);
				match(COMMA);
				setState(928);
				mapCreatorRestPair();
				}
				}
				setState(933);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(934);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MAP() { return getToken(ApexParser.MAP, 0); }
		public MapCreatorRestPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapCreatorRestPair; }
	}

	public final MapCreatorRestPairContext mapCreatorRestPair() throws RecognitionException {
		MapCreatorRestPairContext _localctx = new MapCreatorRestPairContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(936);
			expression(0);
			setState(937);
			match(MAP);
			setState(938);
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

	public static class SetCreatorRestContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		enterRule(_localctx, 172, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(940);
			match(LBRACE);
			setState(941);
			expression(0);
			setState(946);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(942);
				match(COMMA);
				{
				setState(943);
				expression(0);
				}
				}
				}
				setState(948);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(949);
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
		enterRule(_localctx, 174, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(951);
			match(LPAREN);
			setState(953);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(952);
				expressionList();
				}
			}

			setState(955);
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
		enterRule(_localctx, 176, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(957);
			match(LBRACK);
			setState(962);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(960);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
					case 1:
						{
						setState(958);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(959);
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
				setState(964);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
			}
			setState(965);
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
		enterRule(_localctx, 178, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(967);
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
		case 76:
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
			return precpred(_ctx, 22);
		case 13:
			return precpred(_ctx, 21);
		case 14:
			return precpred(_ctx, 20);
		case 15:
			return precpred(_ctx, 17);
		case 16:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3y\u03cc\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\3\2\3\2\3\2\3\3\7\3\u00bb\n"+
		"\3\f\3\16\3\u00be\13\3\3\3\3\3\7\3\u00c2\n\3\f\3\16\3\u00c5\13\3\3\3\3"+
		"\3\7\3\u00c9\n\3\f\3\16\3\u00cc\13\3\3\3\5\3\u00cf\n\3\3\4\3\4\3\4\3\4"+
		"\5\4\u00d5\n\4\3\4\3\4\5\4\u00d9\n\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\u00e1"+
		"\n\5\3\5\3\5\3\6\3\6\3\6\7\6\u00e8\n\6\f\6\16\6\u00eb\13\6\3\7\7\7\u00ee"+
		"\n\7\f\7\16\7\u00f1\13\7\3\7\3\7\5\7\u00f5\n\7\3\7\5\7\u00f8\n\7\3\b\3"+
		"\b\3\b\3\b\5\b\u00fe\n\b\3\b\3\b\3\t\3\t\3\t\7\t\u0105\n\t\f\t\16\t\u0108"+
		"\13\t\3\n\3\n\7\n\u010c\n\n\f\n\16\n\u010f\13\n\3\n\3\n\3\13\3\13\7\13"+
		"\u0115\n\13\f\13\16\13\u0118\13\13\3\13\3\13\3\f\3\f\5\f\u011e\n\f\3\f"+
		"\3\f\7\f\u0122\n\f\f\f\16\f\u0125\13\f\3\f\5\f\u0128\n\f\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u013d"+
		"\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0146\n\16\3\17\7\17\u0149"+
		"\n\17\f\17\16\17\u014c\13\17\3\17\3\17\5\17\u0150\n\17\3\17\3\17\3\17"+
		"\3\17\5\17\u0156\n\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\5\23\u0167\n\23\3\23\3\23\3\24\7\24\u016c\n"+
		"\24\f\24\16\24\u016f\13\24\3\24\3\24\5\24\u0173\n\24\3\25\3\25\3\25\3"+
		"\25\3\25\5\25\u017a\n\25\3\26\3\26\3\26\3\26\7\26\u0180\n\26\f\26\16\26"+
		"\u0183\13\26\3\26\3\26\3\27\3\27\3\27\7\27\u018a\n\27\f\27\16\27\u018d"+
		"\13\27\3\27\3\27\3\27\3\30\3\30\5\30\u0194\n\30\3\30\3\30\3\30\3\30\3"+
		"\31\3\31\3\31\7\31\u019d\n\31\f\31\16\31\u01a0\13\31\3\32\3\32\3\32\5"+
		"\32\u01a5\n\32\3\33\3\33\5\33\u01a9\n\33\3\34\3\34\3\34\3\34\7\34\u01af"+
		"\n\34\f\34\16\34\u01b2\13\34\3\34\5\34\u01b5\n\34\5\34\u01b7\n\34\3\34"+
		"\3\34\3\35\3\35\3\35\7\35\u01be\n\35\f\35\16\35\u01c1\13\35\3\35\3\35"+
		"\3\36\3\36\7\36\u01c7\n\36\f\36\16\36\u01ca\13\36\3\37\3\37\5\37\u01ce"+
		"\n\37\3 \3 \3 \3 \3!\3!\5!\u01d6\n!\3!\3!\3\"\3\"\3\"\7\"\u01dd\n\"\f"+
		"\"\16\"\u01e0\13\"\3#\7#\u01e3\n#\f#\16#\u01e6\13#\3#\3#\3#\3$\3$\3$\7"+
		"$\u01ee\n$\f$\16$\u01f1\13$\3%\3%\3&\3&\3&\3&\3&\5&\u01fa\n&\3&\5&\u01fd"+
		"\n&\3\'\3\'\5\'\u0201\n\'\3\'\7\'\u0204\n\'\f\'\16\'\u0207\13\'\3(\3("+
		"\3(\3(\3)\3)\3)\5)\u0210\n)\3*\3*\3*\3*\7*\u0216\n*\f*\16*\u0219\13*\5"+
		"*\u021b\n*\3*\5*\u021e\n*\3*\3*\3+\3+\7+\u0224\n+\f+\16+\u0227\13+\3+"+
		"\3+\3,\3,\3,\3-\7-\u022f\n-\f-\16-\u0232\13-\3-\3-\3-\3.\3.\3.\3.\3.\3"+
		".\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u024b\n.\3/\3/\3/\3/\3"+
		"/\5/\u0252\n/\3\60\3\60\3\60\3\60\3\60\6\60\u0259\n\60\r\60\16\60\u025a"+
		"\3\60\3\60\3\61\3\61\3\61\5\61\u0262\n\61\3\61\3\61\3\62\3\62\3\62\3\62"+
		"\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65"+
		"\3\65\6\65\u0279\n\65\r\65\16\65\u027a\3\65\5\65\u027e\n\65\3\65\5\65"+
		"\u0281\n\65\3\66\3\66\5\66\u0285\n\66\3\66\3\66\3\67\3\67\3\67\3\67\3"+
		"8\38\38\39\39\39\3:\3:\3:\3:\3;\3;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3>\3"+
		">\3>\5>\u02a6\n>\3>\3>\3?\3?\3?\3?\3?\3@\3@\3@\5@\u02b2\n@\3@\3@\5@\u02b6"+
		"\n@\3A\3A\3A\3B\7B\u02bc\nB\fB\16B\u02bf\13B\3B\3B\5B\u02c3\nB\3C\3C\3"+
		"C\5C\u02c8\nC\3D\3D\3D\5D\u02cd\nD\3E\3E\3E\7E\u02d2\nE\fE\16E\u02d5\13"+
		"E\3E\3E\3E\3E\3E\3F\3F\3F\7F\u02df\nF\fF\16F\u02e2\13F\3G\3G\3G\3H\3H"+
		"\5H\u02e9\nH\3H\3H\5H\u02ed\nH\3H\3H\5H\u02f1\nH\5H\u02f3\nH\3I\3I\5I"+
		"\u02f7\nI\3J\7J\u02fa\nJ\fJ\16J\u02fd\13J\3J\3J\3J\3J\3J\3K\3K\3L\3L\3"+
		"L\3L\3M\3M\3M\7M\u030d\nM\fM\16M\u0310\13M\3N\3N\3N\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\5N\u031f\nN\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N"+
		"\u032f\nN\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N\u033b\nN\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N\u0364\nN\3N\3N\3N\3N\3N\3N\7N\u036c"+
		"\nN\fN\16N\u036f\13N\3O\3O\3O\3O\3O\3O\3O\3O\3O\5O\u037a\nO\3P\3P\3P\3"+
		"P\3P\3P\5P\u0382\nP\3Q\3Q\3Q\7Q\u0387\nQ\fQ\16Q\u038a\13Q\3R\3R\3R\3R"+
		"\3R\5R\u0391\nR\3S\3S\3S\3T\3T\3U\3U\5U\u039a\nU\3U\3U\5U\u039e\nU\3V"+
		"\3V\3V\3V\7V\u03a4\nV\fV\16V\u03a7\13V\3V\3V\3W\3W\3W\3W\3X\3X\3X\3X\7"+
		"X\u03b3\nX\fX\16X\u03b6\13X\3X\3X\3Y\3Y\5Y\u03bc\nY\3Y\3Y\3Z\3Z\3Z\7Z"+
		"\u03c3\nZ\fZ\16Z\u03c6\13Z\3Z\3Z\3[\3[\3[\3\u03c4\3\u009a\\\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\"+
		"^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090"+
		"\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8"+
		"\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\2\f\4\2!!@C\3\2^a\3\2ST\4\2bcgg\3"+
		"\2`a\3\2W[\4\2NNis\3\2^_\3\2JJ\4\2\3?tt\2\u0419\2\u00b6\3\2\2\2\4\u00ce"+
		"\3\2\2\2\6\u00d0\3\2\2\2\b\u00dc\3\2\2\2\n\u00e4\3\2\2\2\f\u00ef\3\2\2"+
		"\2\16\u00f9\3\2\2\2\20\u0101\3\2\2\2\22\u0109\3\2\2\2\24\u0112\3\2\2\2"+
		"\26\u0127\3\2\2\2\30\u013c\3\2\2\2\32\u0145\3\2\2\2\34\u014a\3\2\2\2\36"+
		"\u0157\3\2\2\2 \u015b\3\2\2\2\"\u015f\3\2\2\2$\u0163\3\2\2\2&\u0172\3"+
		"\2\2\2(\u0179\3\2\2\2*\u017b\3\2\2\2,\u0186\3\2\2\2.\u0193\3\2\2\2\60"+
		"\u0199\3\2\2\2\62\u01a1\3\2\2\2\64\u01a8\3\2\2\2\66\u01aa\3\2\2\28\u01ba"+
		"\3\2\2\2:\u01c8\3\2\2\2<\u01cb\3\2\2\2>\u01cf\3\2\2\2@\u01d3\3\2\2\2B"+
		"\u01d9\3\2\2\2D\u01e4\3\2\2\2F\u01ea\3\2\2\2H\u01f2\3\2\2\2J\u01f4\3\2"+
		"\2\2L\u01fe\3\2\2\2N\u0208\3\2\2\2P\u020f\3\2\2\2R\u0211\3\2\2\2T\u0221"+
		"\3\2\2\2V\u022a\3\2\2\2X\u0230\3\2\2\2Z\u024a\3\2\2\2\\\u024c\3\2\2\2"+
		"^\u0253\3\2\2\2`\u025e\3\2\2\2b\u0265\3\2\2\2d\u026b\3\2\2\2f\u026f\3"+
		"\2\2\2h\u0275\3\2\2\2j\u0282\3\2\2\2l\u0288\3\2\2\2n\u028c\3\2\2\2p\u028f"+
		"\3\2\2\2r\u0292\3\2\2\2t\u0296\3\2\2\2v\u029a\3\2\2\2x\u029e\3\2\2\2z"+
		"\u02a2\3\2\2\2|\u02a9\3\2\2\2~\u02ae\3\2\2\2\u0080\u02b7\3\2\2\2\u0082"+
		"\u02bd\3\2\2\2\u0084\u02c4\3\2\2\2\u0086\u02c9\3\2\2\2\u0088\u02ce\3\2"+
		"\2\2\u008a\u02db\3\2\2\2\u008c\u02e3\3\2\2\2\u008e\u02f2\3\2\2\2\u0090"+
		"\u02f6\3\2\2\2\u0092\u02fb\3\2\2\2\u0094\u0303\3\2\2\2\u0096\u0305\3\2"+
		"\2\2\u0098\u0309\3\2\2\2\u009a\u031e\3\2\2\2\u009c\u0379\3\2\2\2\u009e"+
		"\u037b\3\2\2\2\u00a0\u0383\3\2\2\2\u00a2\u038b\3\2\2\2\u00a4\u0392\3\2"+
		"\2\2\u00a6\u0395\3\2\2\2\u00a8\u0397\3\2\2\2\u00aa\u039f\3\2\2\2\u00ac"+
		"\u03aa\3\2\2\2\u00ae\u03ae\3\2\2\2\u00b0\u03b9\3\2\2\2\u00b2\u03bf\3\2"+
		"\2\2\u00b4\u03c9\3\2\2\2\u00b6\u00b7\5\4\3\2\u00b7\u00b8\7\2\2\3\u00b8"+
		"\3\3\2\2\2\u00b9\u00bb\5\30\r\2\u00ba\u00b9\3\2\2\2\u00bb\u00be\3\2\2"+
		"\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bf\3\2\2\2\u00be\u00bc"+
		"\3\2\2\2\u00bf\u00cf\5\6\4\2\u00c0\u00c2\5\30\r\2\u00c1\u00c0\3\2\2\2"+
		"\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6"+
		"\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00cf\5\b\5\2\u00c7\u00c9\5\30\r\2"+
		"\u00c8\u00c7\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb"+
		"\3\2\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00cf\5\16\b\2"+
		"\u00ce\u00bc\3\2\2\2\u00ce\u00c3\3\2\2\2\u00ce\u00ca\3\2\2\2\u00cf\5\3"+
		"\2\2\2\u00d0\u00d1\7\b\2\2\u00d1\u00d4\5\u00b4[\2\u00d2\u00d3\7\20\2\2"+
		"\u00d3\u00d5\58\35\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d8"+
		"\3\2\2\2\u00d6\u00d7\7\31\2\2\u00d7\u00d9\5\20\t\2\u00d8\u00d6\3\2\2\2"+
		"\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\5\22\n\2\u00db\7"+
		"\3\2\2\2\u00dc\u00dd\7\17\2\2\u00dd\u00de\5\u00b4[\2\u00de\u00e0\7G\2"+
		"\2\u00df\u00e1\5\n\6\2\u00e0\u00df\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2"+
		"\3\2\2\2\u00e2\u00e3\7H\2\2\u00e3\t\3\2\2\2\u00e4\u00e9\5\f\7\2\u00e5"+
		"\u00e6\7L\2\2\u00e6\u00e8\5\f\7\2\u00e7\u00e5\3\2\2\2\u00e8\u00eb\3\2"+
		"\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\13\3\2\2\2\u00eb\u00e9"+
		"\3\2\2\2\u00ec\u00ee\5\30\r\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2"+
		"\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef"+
		"\3\2\2\2\u00f2\u00f4\5\u00b4[\2\u00f3\u00f5\5\u00b0Y\2\u00f4\u00f3\3\2"+
		"\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00f8\5\22\n\2\u00f7"+
		"\u00f6\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\r\3\2\2\2\u00f9\u00fa\7\35\2"+
		"\2\u00fa\u00fd\5\u00b4[\2\u00fb\u00fc\7\20\2\2\u00fc\u00fe\5\20\t\2\u00fd"+
		"\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\5\24"+
		"\13\2\u0100\17\3\2\2\2\u0101\u0106\58\35\2\u0102\u0103\7L\2\2\u0103\u0105"+
		"\58\35\2\u0104\u0102\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106"+
		"\u0107\3\2\2\2\u0107\21\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010d\7G\2\2"+
		"\u010a\u010c\5\26\f\2\u010b\u010a\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010b"+
		"\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0110\3\2\2\2\u010f\u010d\3\2\2\2\u0110"+
		"\u0111\7H\2\2\u0111\23\3\2\2\2\u0112\u0116\7G\2\2\u0113\u0115\5&\24\2"+
		"\u0114\u0113\3\2\2\2\u0115\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u0116\3\2\2\2\u0119\u011a\7H\2\2\u011a"+
		"\25\3\2\2\2\u011b\u0128\7K\2\2\u011c\u011e\7.\2\2\u011d\u011c\3\2\2\2"+
		"\u011d\u011e\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0128\5T+\2\u0120\u0122"+
		"\5\30\r\2\u0121\u0120\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121\3\2\2\2"+
		"\u0123\u0124\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u0123\3\2\2\2\u0126\u0128"+
		"\5\32\16\2\u0127\u011b\3\2\2\2\u0127\u011d\3\2\2\2\u0127\u0123\3\2\2\2"+
		"\u0128\27\3\2\2\2\u0129\u013d\5J&\2\u012a\u013d\7\26\2\2\u012b\u013d\7"+
		"\'\2\2\u012c\u013d\7&\2\2\u012d\u013d\7%\2\2\u012e\u013d\7\64\2\2\u012f"+
		"\u013d\7.\2\2\u0130\u013d\7\3\2\2\u0131\u013d\7\21\2\2\u0132\u013d\7;"+
		"\2\2\u0133\u013d\7#\2\2\u0134\u013d\79\2\2\u0135\u013d\7\61\2\2\u0136"+
		"\u0137\7>\2\2\u0137\u013d\7,\2\2\u0138\u0139\7?\2\2\u0139\u013d\7,\2\2"+
		"\u013a\u013b\7\32\2\2\u013b\u013d\7,\2\2\u013c\u0129\3\2\2\2\u013c\u012a"+
		"\3\2\2\2\u013c\u012b\3\2\2\2\u013c\u012c\3\2\2\2\u013c\u012d\3\2\2\2\u013c"+
		"\u012e\3\2\2\2\u013c\u012f\3\2\2\2\u013c\u0130\3\2\2\2\u013c\u0131\3\2"+
		"\2\2\u013c\u0132\3\2\2\2\u013c\u0133\3\2\2\2\u013c\u0134\3\2\2\2\u013c"+
		"\u0135\3\2\2\2\u013c\u0136\3\2\2\2\u013c\u0138\3\2\2\2\u013c\u013a\3\2"+
		"\2\2\u013d\31\3\2\2\2\u013e\u0146\5\34\17\2\u013f\u0146\5 \21\2\u0140"+
		"\u0146\5\36\20\2\u0141\u0146\5\16\b\2\u0142\u0146\5\6\4\2\u0143\u0146"+
		"\5\b\5\2\u0144\u0146\5\"\22\2\u0145\u013e\3\2\2\2\u0145\u013f\3\2\2\2"+
		"\u0145\u0140\3\2\2\2\u0145\u0141\3\2\2\2\u0145\u0142\3\2\2\2\u0145\u0143"+
		"\3\2\2\2\u0145\u0144\3\2\2\2\u0146\33\3\2\2\2\u0147\u0149\5\30\r\2\u0148"+
		"\u0147\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2\2\2\u014a\u014b\3\2"+
		"\2\2\u014b\u014f\3\2\2\2\u014c\u014a\3\2\2\2\u014d\u0150\58\35\2\u014e"+
		"\u0150\7:\2\2\u014f\u014d\3\2\2\2\u014f\u014e\3\2\2\2\u0150\u0151\3\2"+
		"\2\2\u0151\u0152\5\u00b4[\2\u0152\u0155\5@!\2\u0153\u0156\5T+\2\u0154"+
		"\u0156\7K\2\2\u0155\u0153\3\2\2\2\u0155\u0154\3\2\2\2\u0156\35\3\2\2\2"+
		"\u0157\u0158\5F$\2\u0158\u0159\5@!\2\u0159\u015a\5T+\2\u015a\37\3\2\2"+
		"\2\u015b\u015c\58\35\2\u015c\u015d\5\60\31\2\u015d\u015e\7K\2\2\u015e"+
		"!\3\2\2\2\u015f\u0160\58\35\2\u0160\u0161\5\60\31\2\u0161\u0162\5$\23"+
		"\2\u0162#\3\2\2\2\u0163\u0164\7G\2\2\u0164\u0166\5\u0082B\2\u0165\u0167"+
		"\5\u0082B\2\u0166\u0165\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u0168\3\2\2"+
		"\2\u0168\u0169\7H\2\2\u0169%\3\2\2\2\u016a\u016c\5\30\r\2\u016b\u016a"+
		"\3\2\2\2\u016c\u016f\3\2\2\2\u016d\u016b\3\2\2\2\u016d\u016e\3\2\2\2\u016e"+
		"\u0170\3\2\2\2\u016f\u016d\3\2\2\2\u0170\u0173\5(\25\2\u0171\u0173\7K"+
		"\2\2\u0172\u016d\3\2\2\2\u0172\u0171\3\2\2\2\u0173\'\3\2\2\2\u0174\u017a"+
		"\5*\26\2\u0175\u017a\5.\30\2\u0176\u017a\5\16\b\2\u0177\u017a\5\6\4\2"+
		"\u0178\u017a\5\b\5\2\u0179\u0174\3\2\2\2\u0179\u0175\3\2\2\2\u0179\u0176"+
		"\3\2\2\2\u0179\u0177\3\2\2\2\u0179\u0178\3\2\2\2\u017a)\3\2\2\2\u017b"+
		"\u017c\58\35\2\u017c\u0181\5,\27\2\u017d\u017e\7L\2\2\u017e\u0180\5,\27"+
		"\2\u017f\u017d\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182"+
		"\3\2\2\2\u0182\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0185\7K\2\2\u0185"+
		"+\3\2\2\2\u0186\u018b\5\u00b4[\2\u0187\u0188\7I\2\2\u0188\u018a\7J\2\2"+
		"\u0189\u0187\3\2\2\2\u018a\u018d\3\2\2\2\u018b\u0189\3\2\2\2\u018b\u018c"+
		"\3\2\2\2\u018c\u018e\3\2\2\2\u018d\u018b\3\2\2\2\u018e\u018f\7N\2\2\u018f"+
		"\u0190\5\64\33\2\u0190-\3\2\2\2\u0191\u0194\58\35\2\u0192\u0194\7:\2\2"+
		"\u0193\u0191\3\2\2\2\u0193\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196"+
		"\5\u00b4[\2\u0196\u0197\5@!\2\u0197\u0198\7K\2\2\u0198/\3\2\2\2\u0199"+
		"\u019e\5\62\32\2\u019a\u019b\7L\2\2\u019b\u019d\5\62\32\2\u019c\u019a"+
		"\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f"+
		"\61\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1\u01a4\5\u00b4[\2\u01a2\u01a3\7N"+
		"\2\2\u01a3\u01a5\5\64\33\2\u01a4\u01a2\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5"+
		"\63\3\2\2\2\u01a6\u01a9\5\66\34\2\u01a7\u01a9\5\u009aN\2\u01a8\u01a6\3"+
		"\2\2\2\u01a8\u01a7\3\2\2\2\u01a9\65\3\2\2\2\u01aa\u01b6\7G\2\2\u01ab\u01b0"+
		"\5\64\33\2\u01ac\u01ad\7L\2\2\u01ad\u01af\5\64\33\2\u01ae\u01ac\3\2\2"+
		"\2\u01af\u01b2\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b4"+
		"\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01b5\7L\2\2\u01b4\u01b3\3\2\2\2\u01b4"+
		"\u01b5\3\2\2\2\u01b5\u01b7\3\2\2\2\u01b6\u01ab\3\2\2\2\u01b6\u01b7\3\2"+
		"\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01b9\7H\2\2\u01b9\67\3\2\2\2\u01ba\u01bf"+
		"\5<\37\2\u01bb\u01bc\7M\2\2\u01bc\u01be\5<\37\2\u01bd\u01bb\3\2\2\2\u01be"+
		"\u01c1\3\2\2\2\u01bf\u01bd\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0\u01c2\3\2"+
		"\2\2\u01c1\u01bf\3\2\2\2\u01c2\u01c3\5:\36\2\u01c39\3\2\2\2\u01c4\u01c5"+
		"\7I\2\2\u01c5\u01c7\7J\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01ca\3\2\2\2\u01c8"+
		"\u01c6\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9;\3\2\2\2\u01ca\u01c8\3\2\2\2"+
		"\u01cb\u01cd\5\u00b4[\2\u01cc\u01ce\5> \2\u01cd\u01cc\3\2\2\2\u01cd\u01ce"+
		"\3\2\2\2\u01ce=\3\2\2\2\u01cf\u01d0\7R\2\2\u01d0\u01d1\5\20\t\2\u01d1"+
		"\u01d2\7Q\2\2\u01d2?\3\2\2\2\u01d3\u01d5\7E\2\2\u01d4\u01d6\5B\"\2\u01d5"+
		"\u01d4\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d8\7F"+
		"\2\2\u01d8A\3\2\2\2\u01d9\u01de\5D#\2\u01da\u01db\7L\2\2\u01db\u01dd\5"+
		"D#\2\u01dc\u01da\3\2\2\2\u01dd\u01e0\3\2\2\2\u01de\u01dc\3\2\2\2\u01de"+
		"\u01df\3\2\2\2\u01dfC\3\2\2\2\u01e0\u01de\3\2\2\2\u01e1\u01e3\5\30\r\2"+
		"\u01e2\u01e1\3\2\2\2\u01e3\u01e6\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e4\u01e5"+
		"\3\2\2\2\u01e5\u01e7\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7\u01e8\58\35\2\u01e8"+
		"\u01e9\5\u00b4[\2\u01e9E\3\2\2\2\u01ea\u01ef\5\u00b4[\2\u01eb\u01ec\7"+
		"M\2\2\u01ec\u01ee\5\u00b4[\2\u01ed\u01eb\3\2\2\2\u01ee\u01f1\3\2\2\2\u01ef"+
		"\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0G\3\2\2\2\u01f1\u01ef\3\2\2\2"+
		"\u01f2\u01f3\t\2\2\2\u01f3I\3\2\2\2\u01f4\u01f5\7u\2\2\u01f5\u01fc\5F"+
		"$\2\u01f6\u01f9\7E\2\2\u01f7\u01fa\5L\'\2\u01f8\u01fa\5P)\2\u01f9\u01f7"+
		"\3\2\2\2\u01f9\u01f8\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb"+
		"\u01fd\7F\2\2\u01fc\u01f6\3\2\2\2\u01fc\u01fd\3\2\2\2\u01fdK\3\2\2\2\u01fe"+
		"\u0205\5N(\2\u01ff\u0201\7L\2\2\u0200\u01ff\3\2\2\2\u0200\u0201\3\2\2"+
		"\2\u0201\u0202\3\2\2\2\u0202\u0204\5N(\2\u0203\u0200\3\2\2\2\u0204\u0207"+
		"\3\2\2\2\u0205\u0203\3\2\2\2\u0205\u0206\3\2\2\2\u0206M\3\2\2\2\u0207"+
		"\u0205\3\2\2\2\u0208\u0209\5\u00b4[\2\u0209\u020a\7N\2\2\u020a\u020b\5"+
		"P)\2\u020bO\3\2\2\2\u020c\u0210\5\u009aN\2\u020d\u0210\5J&\2\u020e\u0210"+
		"\5R*\2\u020f\u020c\3\2\2\2\u020f\u020d\3\2\2\2\u020f\u020e\3\2\2\2\u0210"+
		"Q\3\2\2\2\u0211\u021a\7G\2\2\u0212\u0217\5P)\2\u0213\u0214\7L\2\2\u0214"+
		"\u0216\5P)\2\u0215\u0213\3\2\2\2\u0216\u0219\3\2\2\2\u0217\u0215\3\2\2"+
		"\2\u0217\u0218\3\2\2\2\u0218\u021b\3\2\2\2\u0219\u0217\3\2\2\2\u021a\u0212"+
		"\3\2\2\2\u021a\u021b\3\2\2\2\u021b\u021d\3\2\2\2\u021c\u021e\7L\2\2\u021d"+
		"\u021c\3\2\2\2\u021d\u021e\3\2\2\2\u021e\u021f\3\2\2\2\u021f\u0220\7H"+
		"\2\2\u0220S\3\2\2\2\u0221\u0225\7G\2\2\u0222\u0224\5Z.\2\u0223\u0222\3"+
		"\2\2\2\u0224\u0227\3\2\2\2\u0225\u0223\3\2\2\2\u0225\u0226\3\2\2\2\u0226"+
		"\u0228\3\2\2\2\u0227\u0225\3\2\2\2\u0228\u0229\7H\2\2\u0229U\3\2\2\2\u022a"+
		"\u022b\5X-\2\u022b\u022c\7K\2\2\u022cW\3\2\2\2\u022d\u022f\5\30\r\2\u022e"+
		"\u022d\3\2\2\2\u022f\u0232\3\2\2\2\u0230\u022e\3\2\2\2\u0230\u0231\3\2"+
		"\2\2\u0231\u0233\3\2\2\2\u0232\u0230\3\2\2\2\u0233\u0234\58\35\2\u0234"+
		"\u0235\5\60\31\2\u0235Y\3\2\2\2\u0236\u024b\5T+\2\u0237\u024b\5V,\2\u0238"+
		"\u024b\5\\/\2\u0239\u024b\5^\60\2\u023a\u024b\5b\62\2\u023b\u024b\5d\63"+
		"\2\u023c\u024b\5f\64\2\u023d\u024b\5h\65\2\u023e\u024b\5j\66\2\u023f\u024b"+
		"\5l\67\2\u0240\u024b\5n8\2\u0241\u024b\5p9\2\u0242\u024b\5r:\2\u0243\u024b"+
		"\5t;\2\u0244\u024b\5v<\2\u0245\u024b\5x=\2\u0246\u024b\5z>\2\u0247\u024b"+
		"\5|?\2\u0248\u024b\5~@\2\u0249\u024b\5\u0080A\2\u024a\u0236\3\2\2\2\u024a"+
		"\u0237\3\2\2\2\u024a\u0238\3\2\2\2\u024a\u0239\3\2\2\2\u024a\u023a\3\2"+
		"\2\2\u024a\u023b\3\2\2\2\u024a\u023c\3\2\2\2\u024a\u023d\3\2\2\2\u024a"+
		"\u023e\3\2\2\2\u024a\u023f\3\2\2\2\u024a\u0240\3\2\2\2\u024a\u0241\3\2"+
		"\2\2\u024a\u0242\3\2\2\2\u024a\u0243\3\2\2\2\u024a\u0244\3\2\2\2\u024a"+
		"\u0245\3\2\2\2\u024a\u0246\3\2\2\2\u024a\u0247\3\2\2\2\u024a\u0248\3\2"+
		"\2\2\u024a\u0249\3\2\2\2\u024b[\3\2\2\2\u024c\u024d\7\30\2\2\u024d\u024e"+
		"\5\u0096L\2\u024e\u0251\5Z.\2\u024f\u0250\7\16\2\2\u0250\u0252\5Z.\2\u0251"+
		"\u024f\3\2\2\2\u0251\u0252\3\2\2\2\u0252]\3\2\2\2\u0253\u0254\7\60\2\2"+
		"\u0254\u0255\7\"\2\2\u0255\u0256\5\u009aN\2\u0256\u0258\7G\2\2\u0257\u0259"+
		"\5`\61\2\u0258\u0257\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u0258\3\2\2\2\u025a"+
		"\u025b\3\2\2\2\u025b\u025c\3\2\2\2\u025c\u025d\7H\2\2\u025d_\3\2\2\2\u025e"+
		"\u0261\7<\2\2\u025f\u0262\5\u0098M\2\u0260\u0262\7\16\2\2\u0261\u025f"+
		"\3\2\2\2\u0261\u0260\3\2\2\2\u0262\u0263\3\2\2\2\u0263\u0264\5T+\2\u0264"+
		"a\3\2\2\2\u0265\u0266\7\24\2\2\u0266\u0267\7E\2\2\u0267\u0268\5\u008e"+
		"H\2\u0268\u0269\7F\2\2\u0269\u026a\5Z.\2\u026ac\3\2\2\2\u026b\u026c\7"+
		"=\2\2\u026c\u026d\5\u0096L\2\u026d\u026e\5Z.\2\u026ee\3\2\2\2\u026f\u0270"+
		"\7\r\2\2\u0270\u0271\5Z.\2\u0271\u0272\7=\2\2\u0272\u0273\5\u0096L\2\u0273"+
		"\u0274\7K\2\2\u0274g\3\2\2\2\u0275\u0276\7\65\2\2\u0276\u0280\5T+\2\u0277"+
		"\u0279\5\u0088E\2\u0278\u0277\3\2\2\2\u0279\u027a\3\2\2\2\u027a\u0278"+
		"\3\2\2\2\u027a\u027b\3\2\2\2\u027b\u027d\3\2\2\2\u027c\u027e\5\u008cG"+
		"\2\u027d\u027c\3\2\2\2\u027d\u027e\3\2\2\2\u027e\u0281\3\2\2\2\u027f\u0281"+
		"\5\u008cG\2\u0280\u0278\3\2\2\2\u0280\u027f\3\2\2\2\u0281i\3\2\2\2\u0282"+
		"\u0284\7(\2\2\u0283\u0285\5\u009aN\2\u0284\u0283\3\2\2\2\u0284\u0285\3"+
		"\2\2\2\u0285\u0286\3\2\2\2\u0286\u0287\7K\2\2\u0287k\3\2\2\2\u0288\u0289"+
		"\7\63\2\2\u0289\u028a\5\u009aN\2\u028a\u028b\7K\2\2\u028bm\3\2\2\2\u028c"+
		"\u028d\7\4\2\2\u028d\u028e\7K\2\2\u028eo\3\2\2\2\u028f\u0290\7\n\2\2\u0290"+
		"\u0291\7K\2\2\u0291q\3\2\2\2\u0292\u0293\7\33\2\2\u0293\u0294\5\u009a"+
		"N\2\u0294\u0295\7K\2\2\u0295s\3\2\2\2\u0296\u0297\7\67\2\2\u0297\u0298"+
		"\5\u009aN\2\u0298\u0299\7K\2\2\u0299u\3\2\2\2\u029a\u029b\7\f\2\2\u029b"+
		"\u029c\5\u009aN\2\u029c\u029d\7K\2\2\u029dw\3\2\2\2\u029e\u029f\7\66\2"+
		"\2\u029f\u02a0\5\u009aN\2\u02a0\u02a1\7K\2\2\u02a1y\3\2\2\2\u02a2\u02a3"+
		"\78\2\2\u02a3\u02a5\5\u009aN\2\u02a4\u02a6\5F$\2\u02a5\u02a4\3\2\2\2\u02a5"+
		"\u02a6\3\2\2\2\u02a6\u02a7\3\2\2\2\u02a7\u02a8\7K\2\2\u02a8{\3\2\2\2\u02a9"+
		"\u02aa\7\36\2\2\u02aa\u02ab\5\u009aN\2\u02ab\u02ac\5\u009aN\2\u02ac\u02ad"+
		"\7K\2\2\u02ad}\3\2\2\2\u02ae\u02af\7)\2\2\u02af\u02b1\7E\2\2\u02b0\u02b2"+
		"\5\u0098M\2\u02b1\u02b0\3\2\2\2\u02b1\u02b2\3\2\2\2\u02b2\u02b3\3\2\2"+
		"\2\u02b3\u02b5\7F\2\2\u02b4\u02b6\5T+\2\u02b5\u02b4\3\2\2\2\u02b5\u02b6"+
		"\3\2\2\2\u02b6\177\3\2\2\2\u02b7\u02b8\5\u009aN\2\u02b8\u02b9\7K\2\2\u02b9"+
		"\u0081\3\2\2\2\u02ba\u02bc\5\30\r\2\u02bb\u02ba\3\2\2\2\u02bc\u02bf\3"+
		"\2\2\2\u02bd\u02bb\3\2\2\2\u02bd\u02be\3\2\2\2\u02be\u02c2\3\2\2\2\u02bf"+
		"\u02bd\3\2\2\2\u02c0\u02c3\5\u0084C\2\u02c1\u02c3\5\u0086D\2\u02c2\u02c0"+
		"\3\2\2\2\u02c2\u02c1\3\2\2\2\u02c3\u0083\3\2\2\2\u02c4\u02c7\7\25\2\2"+
		"\u02c5\u02c8\7K\2\2\u02c6\u02c8\5T+\2\u02c7\u02c5\3\2\2\2\u02c7\u02c6"+
		"\3\2\2\2\u02c8\u0085\3\2\2\2\u02c9\u02cc\7+\2\2\u02ca\u02cd\7K\2\2\u02cb"+
		"\u02cd\5T+\2\u02cc\u02ca\3\2\2\2\u02cc\u02cb\3\2\2\2\u02cd\u0087\3\2\2"+
		"\2\u02ce\u02cf\7\6\2\2\u02cf\u02d3\7E\2\2\u02d0\u02d2\5\30\r\2\u02d1\u02d0"+
		"\3\2\2\2\u02d2\u02d5\3\2\2\2\u02d3\u02d1\3\2\2\2\u02d3\u02d4\3\2\2\2\u02d4"+
		"\u02d6\3\2\2\2\u02d5\u02d3\3\2\2\2\u02d6\u02d7\5\u008aF\2\u02d7\u02d8"+
		"\5\u00b4[\2\u02d8\u02d9\7F\2\2\u02d9\u02da\5T+\2\u02da\u0089\3\2\2\2\u02db"+
		"\u02e0\5F$\2\u02dc\u02dd\7e\2\2\u02dd\u02df\5F$\2\u02de\u02dc\3\2\2\2"+
		"\u02df\u02e2\3\2\2\2\u02e0\u02de\3\2\2\2\u02e0\u02e1\3\2\2\2\u02e1\u008b"+
		"\3\2\2\2\u02e2\u02e0\3\2\2\2\u02e3\u02e4\7\22\2\2\u02e4\u02e5\5T+\2\u02e5"+
		"\u008d\3\2\2\2\u02e6\u02f3\5\u0092J\2\u02e7\u02e9\5\u0090I\2\u02e8\u02e7"+
		"\3\2\2\2\u02e8\u02e9\3\2\2\2\u02e9\u02ea\3\2\2\2\u02ea\u02ec\7K\2\2\u02eb"+
		"\u02ed\5\u009aN\2\u02ec\u02eb\3\2\2\2\u02ec\u02ed\3\2\2\2\u02ed\u02ee"+
		"\3\2\2\2\u02ee\u02f0\7K\2\2\u02ef\u02f1\5\u0094K\2\u02f0\u02ef\3\2\2\2"+
		"\u02f0\u02f1\3\2\2\2\u02f1\u02f3\3\2\2\2\u02f2\u02e6\3\2\2\2\u02f2\u02e8"+
		"\3\2\2\2\u02f3\u008f\3\2\2\2\u02f4\u02f7\5X-\2\u02f5\u02f7\5\u0098M\2"+
		"\u02f6\u02f4\3\2\2\2\u02f6\u02f5\3\2\2\2\u02f7\u0091\3\2\2\2\u02f8\u02fa"+
		"\5\30\r\2\u02f9\u02f8\3\2\2\2\u02fa\u02fd\3\2\2\2\u02fb\u02f9\3\2\2\2"+
		"\u02fb\u02fc\3\2\2\2\u02fc\u02fe\3\2\2\2\u02fd\u02fb\3\2\2\2\u02fe\u02ff"+
		"\58\35\2\u02ff\u0300\5\u00b4[\2\u0300\u0301\7V\2\2\u0301\u0302\5\u009a"+
		"N\2\u0302\u0093\3\2\2\2\u0303\u0304\5\u0098M\2\u0304\u0095\3\2\2\2\u0305"+
		"\u0306\7E\2\2\u0306\u0307\5\u009aN\2\u0307\u0308\7F\2\2\u0308\u0097\3"+
		"\2\2\2\u0309\u030e\5\u009aN\2\u030a\u030b\7L\2\2\u030b\u030d\5\u009aN"+
		"\2\u030c\u030a\3\2\2\2\u030d\u0310\3\2\2\2\u030e\u030c\3\2\2\2\u030e\u030f"+
		"\3\2\2\2\u030f\u0099\3\2\2\2\u0310\u030e\3\2\2\2\u0311\u0312\bN\1\2\u0312"+
		"\u0313\7 \2\2\u0313\u031f\5\u009eP\2\u0314\u0315\7E\2\2\u0315\u0316\5"+
		"8\35\2\u0316\u0317\7F\2\2\u0317\u0318\5\u009aN\24\u0318\u031f\3\2\2\2"+
		"\u0319\u031a\t\3\2\2\u031a\u031f\5\u009aN\22\u031b\u031c\t\4\2\2\u031c"+
		"\u031f\5\u009aN\21\u031d\u031f\5\u009cO\2\u031e\u0311\3\2\2\2\u031e\u0314"+
		"\3\2\2\2\u031e\u0319\3\2\2\2\u031e\u031b\3\2\2\2\u031e\u031d\3\2\2\2\u031f"+
		"\u036d\3\2\2\2\u0320\u0321\f\20\2\2\u0321\u0322\t\5\2\2\u0322\u036c\5"+
		"\u009aN\21\u0323\u0324\f\17\2\2\u0324\u0325\t\6\2\2\u0325\u036c\5\u009a"+
		"N\20\u0326\u032e\f\16\2\2\u0327\u0328\7R\2\2\u0328\u032f\7R\2\2\u0329"+
		"\u032a\7Q\2\2\u032a\u032b\7Q\2\2\u032b\u032f\7Q\2\2\u032c\u032d\7Q\2\2"+
		"\u032d\u032f\7Q\2\2\u032e\u0327\3\2\2\2\u032e\u0329\3\2\2\2\u032e\u032c"+
		"\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u036c\5\u009aN\17\u0331\u033a\f\r\2"+
		"\2\u0332\u0333\7R\2\2\u0333\u033b\7N\2\2\u0334\u0335\7Q\2\2\u0335\u033b"+
		"\7N\2\2\u0336\u033b\7O\2\2\u0337\u033b\7P\2\2\u0338\u033b\7Q\2\2\u0339"+
		"\u033b\7R\2\2\u033a\u0332\3\2\2\2\u033a\u0334\3\2\2\2\u033a\u0336\3\2"+
		"\2\2\u033a\u0337\3\2\2\2\u033a\u0338\3\2\2\2\u033a\u0339\3\2\2\2\u033b"+
		"\u033c\3\2\2\2\u033c\u036c\5\u009aN\16\u033d\u033e\f\13\2\2\u033e\u033f"+
		"\t\7\2\2\u033f\u036c\5\u009aN\f\u0340\u0341\f\n\2\2\u0341\u0342\7d\2\2"+
		"\u0342\u036c\5\u009aN\13\u0343\u0344\f\t\2\2\u0344\u0345\7f\2\2\u0345"+
		"\u036c\5\u009aN\n\u0346\u0347\f\b\2\2\u0347\u0348\7e\2\2\u0348\u036c\5"+
		"\u009aN\t\u0349\u034a\f\7\2\2\u034a\u034b\7\\\2\2\u034b\u036c\5\u009a"+
		"N\b\u034c\u034d\f\6\2\2\u034d\u034e\7]\2\2\u034e\u036c\5\u009aN\7\u034f"+
		"\u0350\f\5\2\2\u0350\u0351\7U\2\2\u0351\u0352\5\u009aN\2\u0352\u0353\7"+
		"V\2\2\u0353\u0354\5\u009aN\6\u0354\u036c\3\2\2\2\u0355\u0356\f\4\2\2\u0356"+
		"\u0357\t\b\2\2\u0357\u036c\5\u009aN\4\u0358\u0359\f\30\2\2\u0359\u035a"+
		"\7M\2\2\u035a\u036c\5\u00b4[\2\u035b\u035c\f\27\2\2\u035c\u035d\7I\2\2"+
		"\u035d\u035e\5\u009aN\2\u035e\u035f\7J\2\2\u035f\u036c\3\2\2\2\u0360\u0361"+
		"\f\26\2\2\u0361\u0363\7E\2\2\u0362\u0364\5\u0098M\2\u0363\u0362\3\2\2"+
		"\2\u0363\u0364\3\2\2\2\u0364\u0365\3\2\2\2\u0365\u036c\7F\2\2\u0366\u0367"+
		"\f\23\2\2\u0367\u036c\t\t\2\2\u0368\u0369\f\f\2\2\u0369\u036a\7\34\2\2"+
		"\u036a\u036c\58\35\2\u036b\u0320\3\2\2\2\u036b\u0323\3\2\2\2\u036b\u0326"+
		"\3\2\2\2\u036b\u0331\3\2\2\2\u036b\u033d\3\2\2\2\u036b\u0340\3\2\2\2\u036b"+
		"\u0343\3\2\2\2\u036b\u0346\3\2\2\2\u036b\u0349\3\2\2\2\u036b\u034c\3\2"+
		"\2\2\u036b\u034f\3\2\2\2\u036b\u0355\3\2\2\2\u036b\u0358\3\2\2\2\u036b"+
		"\u035b\3\2\2\2\u036b\u0360\3\2\2\2\u036b\u0366\3\2\2\2\u036b\u0368\3\2"+
		"\2\2\u036c\u036f\3\2\2\2\u036d\u036b\3\2\2\2\u036d\u036e\3\2\2\2\u036e"+
		"\u009b\3\2\2\2\u036f\u036d\3\2\2\2\u0370\u0371\7E\2\2\u0371\u0372\5\u009a"+
		"N\2\u0372\u0373\7F\2\2\u0373\u037a\3\2\2\2\u0374\u037a\7\62\2\2\u0375"+
		"\u037a\7/\2\2\u0376\u037a\5H%\2\u0377\u037a\58\35\2\u0378\u037a\5\u00b2"+
		"Z\2\u0379\u0370\3\2\2\2\u0379\u0374\3\2\2\2\u0379\u0375\3\2\2\2\u0379"+
		"\u0376\3\2\2\2\u0379\u0377\3\2\2\2\u0379\u0378\3\2\2\2\u037a\u009d\3\2"+
		"\2\2\u037b\u0381\5\u00a0Q\2\u037c\u0382\5\u00a4S\2\u037d\u0382\5\u00a6"+
		"T\2\u037e\u0382\5\u00a8U\2\u037f\u0382\5\u00aaV\2\u0380\u0382\5\u00ae"+
		"X\2\u0381\u037c\3\2\2\2\u0381\u037d\3\2\2\2\u0381\u037e\3\2\2\2\u0381"+
		"\u037f\3\2\2\2\u0381\u0380\3\2\2\2\u0382\u009f\3\2\2\2\u0383\u0388\5\u00a2"+
		"R\2\u0384\u0385\7M\2\2\u0385\u0387\5\u00a2R\2\u0386\u0384\3\2\2\2\u0387"+
		"\u038a\3\2\2\2\u0388\u0386\3\2\2\2\u0388\u0389\3\2\2\2\u0389\u00a1\3\2"+
		"\2\2\u038a\u0388\3\2\2\2\u038b\u0390\5\u00b4[\2\u038c\u038d\7R\2\2\u038d"+
		"\u038e\5\20\t\2\u038e\u038f\7Q\2\2\u038f\u0391\3\2\2\2\u0390\u038c\3\2"+
		"\2\2\u0390\u0391\3\2\2\2\u0391\u00a3\3\2\2\2\u0392\u0393\7G\2\2\u0393"+
		"\u0394\7H\2\2\u0394\u00a5\3\2\2\2\u0395\u0396\5\u00b0Y\2\u0396\u00a7\3"+
		"\2\2\2\u0397\u0399\7I\2\2\u0398\u039a\5\u009aN\2\u0399\u0398\3\2\2\2\u0399"+
		"\u039a\3\2\2\2\u039a\u039b\3\2\2\2\u039b\u039d\7J\2\2\u039c\u039e\5\66"+
		"\34\2\u039d\u039c\3\2\2\2\u039d\u039e\3\2\2\2\u039e\u00a9\3\2\2\2\u039f"+
		"\u03a0\7G\2\2\u03a0\u03a5\5\u00acW\2\u03a1\u03a2\7L\2\2\u03a2\u03a4\5"+
		"\u00acW\2\u03a3\u03a1\3\2\2\2\u03a4\u03a7\3\2\2\2\u03a5\u03a3\3\2\2\2"+
		"\u03a5\u03a6\3\2\2\2\u03a6\u03a8\3\2\2\2\u03a7\u03a5\3\2\2\2\u03a8\u03a9"+
		"\7H\2\2\u03a9\u00ab\3\2\2\2\u03aa\u03ab\5\u009aN\2\u03ab\u03ac\7h\2\2"+
		"\u03ac\u03ad\5\u009aN\2\u03ad\u00ad\3\2\2\2\u03ae\u03af\7G\2\2\u03af\u03b4"+
		"\5\u009aN\2\u03b0\u03b1\7L\2\2\u03b1\u03b3\5\u009aN\2\u03b2\u03b0\3\2"+
		"\2\2\u03b3\u03b6\3\2\2\2\u03b4\u03b2\3\2\2\2\u03b4\u03b5\3\2\2\2\u03b5"+
		"\u03b7\3\2\2\2\u03b6\u03b4\3\2\2\2\u03b7\u03b8\7H\2\2\u03b8\u00af\3\2"+
		"\2\2\u03b9\u03bb\7E\2\2\u03ba\u03bc\5\u0098M\2\u03bb\u03ba\3\2\2\2\u03bb"+
		"\u03bc\3\2\2\2\u03bc\u03bd\3\2\2\2\u03bd\u03be\7F\2\2\u03be\u00b1\3\2"+
		"\2\2\u03bf\u03c4\7I\2\2\u03c0\u03c3\5\u00b2Z\2\u03c1\u03c3\n\n\2\2\u03c2"+
		"\u03c0\3\2\2\2\u03c2\u03c1\3\2\2\2\u03c3\u03c6\3\2\2\2\u03c4\u03c5\3\2"+
		"\2\2\u03c4\u03c2\3\2\2\2\u03c5\u03c7\3\2\2\2\u03c6\u03c4\3\2\2\2\u03c7"+
		"\u03c8\7J\2\2\u03c8\u00b3\3\2\2\2\u03c9\u03ca\t\13\2\2\u03ca\u00b5\3\2"+
		"\2\2`\u00bc\u00c3\u00ca\u00ce\u00d4\u00d8\u00e0\u00e9\u00ef\u00f4\u00f7"+
		"\u00fd\u0106\u010d\u0116\u011d\u0123\u0127\u013c\u0145\u014a\u014f\u0155"+
		"\u0166\u016d\u0172\u0179\u0181\u018b\u0193\u019e\u01a4\u01a8\u01b0\u01b4"+
		"\u01b6\u01bf\u01c8\u01cd\u01d5\u01de\u01e4\u01ef\u01f9\u01fc\u0200\u0205"+
		"\u020f\u0217\u021a\u021d\u0225\u0230\u024a\u0251\u025a\u0261\u027a\u027d"+
		"\u0280\u0284\u02a5\u02b1\u02b5\u02bd\u02c2\u02c7\u02cc\u02d3\u02e0\u02e8"+
		"\u02ec\u02f0\u02f2\u02f6\u02fb\u030e\u031e\u032e\u033a\u0363\u036b\u036d"+
		"\u0379\u0381\u0388\u0390\u0399\u039d\u03a5\u03b4\u03bb\u03c2\u03c4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}