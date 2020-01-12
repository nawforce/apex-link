// Generated from /Users/kevin/Projects/ApexLink/src/main/antlr4/com/nawforce/parsers/ApexParser.g4 by ANTLR 4.7.2
package com.nawforce.runtime.parsers;

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
		FOR=17, GET=18, GLOBAL=19, IF=20, IMPLEMENTS=21, INHERITED=22, INSERT=23, 
		INSTANCEOF=24, INTERFACE=25, MERGE=26, NEW=27, NULL=28, ON=29, OVERRIDE=30, 
		PRIVATE=31, PROTECTED=32, PUBLIC=33, RETURN=34, RUNAS=35, SET=36, SHARING=37, 
		SHORT=38, STATIC=39, SUPER=40, SWITCH=41, TESTMETHOD=42, THIS=43, THROW=44, 
		TRANSIENT=45, TRY=46, UNDELETE=47, UPDATE=48, UPSERT=49, VIRTUAL=50, VOID=51, 
		WEBSERVICE=52, WHEN=53, WHILE=54, WITH=55, WITHOUT=56, IntegerLiteral=57, 
		NumberLiteral=58, BooleanLiteral=59, StringLiteral=60, NullLiteral=61, 
		LPAREN=62, RPAREN=63, LBRACE=64, RBRACE=65, LBRACK=66, RBRACK=67, SEMI=68, 
		COMMA=69, DOT=70, ASSIGN=71, LE=72, GE=73, GT=74, LT=75, BANG=76, TILDE=77, 
		QUESTION=78, COLON=79, EQUAL=80, TRIPLEEQUAL=81, NOTEQUAL=82, LESSANDGREATER=83, 
		TRIPLENOTEQUAL=84, AND=85, OR=86, INC=87, DEC=88, ADD=89, SUB=90, MUL=91, 
		DIV=92, BITAND=93, BITOR=94, CARET=95, MOD=96, MAP=97, ADD_ASSIGN=98, 
		SUB_ASSIGN=99, MUL_ASSIGN=100, DIV_ASSIGN=101, AND_ASSIGN=102, OR_ASSIGN=103, 
		XOR_ASSIGN=104, MOD_ASSIGN=105, LSHIFT_ASSIGN=106, RSHIFT_ASSIGN=107, 
		URSHIFT_ASSIGN=108, AT=109, Identifier=110, WS=111, DOC_COMMENT=112, COMMENT=113, 
		LINE_COMMENT=114;
	public static final int
		RULE_compilationUnit = 0, RULE_typeDeclaration = 1, RULE_classDeclaration = 2, 
		RULE_enumDeclaration = 3, RULE_enumConstants = 4, RULE_interfaceDeclaration = 5, 
		RULE_typeList = 6, RULE_classBody = 7, RULE_interfaceBody = 8, RULE_classBodyDeclaration = 9, 
		RULE_modifier = 10, RULE_memberDeclaration = 11, RULE_methodDeclaration = 12, 
		RULE_constructorDeclaration = 13, RULE_fieldDeclaration = 14, RULE_propertyDeclaration = 15, 
		RULE_interfaceMethodDeclaration = 16, RULE_variableDeclarators = 17, RULE_variableDeclarator = 18, 
		RULE_arrayInitializer = 19, RULE_typeRef = 20, RULE_arraySubscripts = 21, 
		RULE_typeName = 22, RULE_typeArguments = 23, RULE_formalParameters = 24, 
		RULE_formalParameterList = 25, RULE_formalParameter = 26, RULE_qualifiedName = 27, 
		RULE_literal = 28, RULE_annotation = 29, RULE_elementValuePairs = 30, 
		RULE_elementValuePair = 31, RULE_elementValue = 32, RULE_elementValueArrayInitializer = 33, 
		RULE_block = 34, RULE_localVariableDeclarationStatement = 35, RULE_localVariableDeclaration = 36, 
		RULE_statement = 37, RULE_ifStatement = 38, RULE_switchStatement = 39, 
		RULE_whenControl = 40, RULE_forStatement = 41, RULE_whileStatement = 42, 
		RULE_doWhileStatement = 43, RULE_tryStatement = 44, RULE_returnStatement = 45, 
		RULE_throwStatement = 46, RULE_breakStatement = 47, RULE_continueStatement = 48, 
		RULE_insertStatement = 49, RULE_updateStatement = 50, RULE_deleteStatement = 51, 
		RULE_undeleteStatement = 52, RULE_upsertStatement = 53, RULE_mergeStatement = 54, 
		RULE_runAsStatement = 55, RULE_expressionStatement = 56, RULE_propertyBlock = 57, 
		RULE_getter = 58, RULE_setter = 59, RULE_catchClause = 60, RULE_finallyBlock = 61, 
		RULE_forControl = 62, RULE_forInit = 63, RULE_enhancedForControl = 64, 
		RULE_forUpdate = 65, RULE_parExpression = 66, RULE_expressionList = 67, 
		RULE_expression = 68, RULE_primary = 69, RULE_methodCall = 70, RULE_creator = 71, 
		RULE_createdName = 72, RULE_idCreatedNamePair = 73, RULE_noRest = 74, 
		RULE_classCreatorRest = 75, RULE_arrayCreatorRest = 76, RULE_mapCreatorRest = 77, 
		RULE_mapCreatorRestPair = 78, RULE_setCreatorRest = 79, RULE_arguments = 80, 
		RULE_soqlLiteral = 81, RULE_id = 82;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "typeDeclaration", "classDeclaration", "enumDeclaration", 
			"enumConstants", "interfaceDeclaration", "typeList", "classBody", "interfaceBody", 
			"classBodyDeclaration", "modifier", "memberDeclaration", "methodDeclaration", 
			"constructorDeclaration", "fieldDeclaration", "propertyDeclaration", 
			"interfaceMethodDeclaration", "variableDeclarators", "variableDeclarator", 
			"arrayInitializer", "typeRef", "arraySubscripts", "typeName", "typeArguments", 
			"formalParameters", "formalParameterList", "formalParameter", "qualifiedName", 
			"literal", "annotation", "elementValuePairs", "elementValuePair", "elementValue", 
			"elementValueArrayInitializer", "block", "localVariableDeclarationStatement", 
			"localVariableDeclaration", "statement", "ifStatement", "switchStatement", 
			"whenControl", "forStatement", "whileStatement", "doWhileStatement", 
			"tryStatement", "returnStatement", "throwStatement", "breakStatement", 
			"continueStatement", "insertStatement", "updateStatement", "deleteStatement", 
			"undeleteStatement", "upsertStatement", "mergeStatement", "runAsStatement", 
			"expressionStatement", "propertyBlock", "getter", "setter", "catchClause", 
			"finallyBlock", "forControl", "forInit", "enhancedForControl", "forUpdate", 
			"parExpression", "expressionList", "expression", "primary", "methodCall", 
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
			"'extends'", "'final'", "'finally'", "'for'", "'get'", "'global'", "'if'", 
			"'implements'", "'inherited'", "'insert'", "'instanceof'", "'interface'", 
			"'merge'", "'new'", "'null'", "'on'", "'override'", "'private'", "'protected'", 
			"'public'", "'return'", "'system.runas'", "'set'", "'sharing'", "'short'", 
			"'static'", "'super'", "'switch'", "'testmethod'", "'this'", "'throw'", 
			"'transient'", "'try'", "'undelete'", "'update'", "'upsert'", "'virtual'", 
			"'void'", "'webservice'", "'when'", "'while'", "'with'", "'without'", 
			null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"';'", "','", "'.'", "'='", "'<='", "'>='", "'>'", "'<'", "'!'", "'~'", 
			"'?'", "':'", "'=='", "'==='", "'!='", "'<>'", "'!=='", "'&&'", "'||'", 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", 
			"'=>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", 
			"'<<='", "'>>='", "'>>>='", "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", "CONST", 
			"CONTINUE", "DEFAULT", "DELETE", "DO", "ELSE", "ENUM", "EXTENDS", "FINAL", 
			"FINALLY", "FOR", "GET", "GLOBAL", "IF", "IMPLEMENTS", "INHERITED", "INSERT", 
			"INSTANCEOF", "INTERFACE", "MERGE", "NEW", "NULL", "ON", "OVERRIDE", 
			"PRIVATE", "PROTECTED", "PUBLIC", "RETURN", "RUNAS", "SET", "SHARING", 
			"SHORT", "STATIC", "SUPER", "SWITCH", "TESTMETHOD", "THIS", "THROW", 
			"TRANSIENT", "TRY", "UNDELETE", "UPDATE", "UPSERT", "VIRTUAL", "VOID", 
			"WEBSERVICE", "WHEN", "WHILE", "WITH", "WITHOUT", "IntegerLiteral", "NumberLiteral", 
			"BooleanLiteral", "StringLiteral", "NullLiteral", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", 
			"LE", "GE", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", 
			"TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", 
			"MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "AT", "Identifier", "WS", "DOC_COMMENT", 
			"COMMENT", "LINE_COMMENT"
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
			setState(166);
			typeDeclaration();
			setState(167);
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
			setState(190);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(169);
					modifier();
					}
					}
					setState(174);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(175);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(176);
					modifier();
					}
					}
					setState(181);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(182);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
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
			setState(192);
			match(CLASS);
			setState(193);
			id();
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(194);
				match(EXTENDS);
				setState(195);
				typeRef();
				}
			}

			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(198);
				match(IMPLEMENTS);
				setState(199);
				typeList();
				}
			}

			setState(202);
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
			setState(204);
			match(ENUM);
			setState(205);
			id();
			setState(206);
			match(LBRACE);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(207);
				enumConstants();
				}
			}

			setState(210);
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
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
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
			setState(212);
			id();
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(213);
				match(COMMA);
				setState(214);
				id();
				}
				}
				setState(219);
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
		enterRule(_localctx, 10, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(INTERFACE);
			setState(221);
			id();
			setState(224);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(222);
				match(EXTENDS);
				setState(223);
				typeList();
				}
			}

			setState(226);
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
		enterRule(_localctx, 12, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			typeRef();
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(229);
				match(COMMA);
				setState(230);
				typeRef();
				}
				}
				setState(235);
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
		enterRule(_localctx, 14, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			match(LBRACE);
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (AT - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(237);
				classBodyDeclaration();
				}
				}
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(243);
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
		public List<InterfaceMethodDeclarationContext> interfaceMethodDeclaration() {
			return getRuleContexts(InterfaceMethodDeclarationContext.class);
		}
		public InterfaceMethodDeclarationContext interfaceMethodDeclaration(int i) {
			return getRuleContext(InterfaceMethodDeclarationContext.class,i);
		}
		public InterfaceBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceBody; }
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(LBRACE);
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT || _la==Identifier) {
				{
				{
				setState(246);
				interfaceMethodDeclaration();
				}
				}
				setState(251);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(252);
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
		enterRule(_localctx, 18, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(266);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(254);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(255);
					match(STATIC);
					}
				}

				setState(258);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(262);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(259);
						modifier();
						}
						} 
					}
					setState(264);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				setState(265);
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
		enterRule(_localctx, 20, RULE_modifier);
		try {
			setState(287);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(269);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(270);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(271);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(272);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(273);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(274);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(275);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(276);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(277);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(278);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(279);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(280);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(281);
				match(WITH);
				setState(282);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(283);
				match(WITHOUT);
				setState(284);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(285);
				match(INHERITED);
				setState(286);
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
		enterRule(_localctx, 22, RULE_memberDeclaration);
		try {
			setState(296);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(289);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(291);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(292);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(293);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(294);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(295);
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
		enterRule(_localctx, 24, RULE_methodDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(298);
					modifier();
					}
					} 
				}
				setState(303);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			setState(306);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(304);
				typeRef();
				}
				break;
			case 2:
				{
				setState(305);
				match(VOID);
				}
				break;
			}
			setState(308);
			id();
			setState(309);
			formalParameters();
			setState(312);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(310);
				block();
				}
				break;
			case SEMI:
				{
				setState(311);
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
		enterRule(_localctx, 26, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			qualifiedName();
			setState(315);
			formalParameters();
			setState(316);
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
		enterRule(_localctx, 28, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			typeRef();
			setState(319);
			variableDeclarators();
			setState(320);
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
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
		public List<PropertyBlockContext> propertyBlock() {
			return getRuleContexts(PropertyBlockContext.class);
		}
		public PropertyBlockContext propertyBlock(int i) {
			return getRuleContext(PropertyBlockContext.class,i);
		}
		public PropertyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyDeclaration; }
	}

	public final PropertyDeclarationContext propertyDeclaration() throws RecognitionException {
		PropertyDeclarationContext _localctx = new PropertyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_propertyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			typeRef();
			setState(323);
			id();
			setState(324);
			match(LBRACE);
			setState(328);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(325);
				propertyBlock();
				}
				}
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(331);
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
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public InterfaceMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceMethodDeclaration; }
	}

	public final InterfaceMethodDeclarationContext interfaceMethodDeclaration() throws RecognitionException {
		InterfaceMethodDeclarationContext _localctx = new InterfaceMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_interfaceMethodDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(333);
					modifier();
					}
					} 
				}
				setState(338);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			setState(341);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(339);
				typeRef();
				}
				break;
			case 2:
				{
				setState(340);
				match(VOID);
				}
				break;
			}
			setState(343);
			id();
			setState(344);
			formalParameters();
			setState(345);
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
		enterRule(_localctx, 34, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			variableDeclarator();
			setState(352);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(348);
				match(COMMA);
				setState(349);
				variableDeclarator();
				}
				}
				setState(354);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			id();
			setState(358);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(356);
				match(ASSIGN);
				setState(357);
				expression(0);
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

	public static class ArrayInitializerContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(ApexParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(ApexParser.RBRACE, 0); }
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
		public ArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayInitializer; }
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			match(LBRACE);
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
				{
				setState(361);
				expression(0);
				setState(366);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(362);
						match(COMMA);
						setState(363);
						expression(0);
						}
						} 
					}
					setState(368);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(370);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(369);
					match(COMMA);
					}
				}

				}
			}

			setState(374);
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
		enterRule(_localctx, 40, RULE_typeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			typeName();
			setState(381);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(377);
					match(DOT);
					setState(378);
					typeName();
					}
					} 
				}
				setState(383);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			setState(384);
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
		enterRule(_localctx, 42, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(386);
					match(LBRACK);
					setState(387);
					match(RBRACK);
					}
					} 
				}
				setState(392);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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
		enterRule(_localctx, 44, RULE_typeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			id();
			setState(395);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(394);
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
		enterRule(_localctx, 46, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(LT);
			setState(398);
			typeList();
			setState(399);
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
		enterRule(_localctx, 48, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			match(LPAREN);
			setState(403);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT || _la==Identifier) {
				{
				setState(402);
				formalParameterList();
				}
			}

			setState(405);
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
		enterRule(_localctx, 50, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(407);
			formalParameter();
			setState(412);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(408);
				match(COMMA);
				setState(409);
				formalParameter();
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
		enterRule(_localctx, 52, RULE_formalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(415);
					modifier();
					}
					} 
				}
				setState(420);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			setState(421);
			typeRef();
			setState(422);
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
		enterRule(_localctx, 54, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			id();
			setState(429);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(425);
				match(DOT);
				setState(426);
				id();
				}
				}
				setState(431);
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
		enterRule(_localctx, 56, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral))) != 0)) ) {
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
		enterRule(_localctx, 58, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			match(AT);
			setState(435);
			qualifiedName();
			setState(442);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(436);
				match(LPAREN);
				setState(439);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
				case 1:
					{
					setState(437);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(438);
					elementValue();
					}
					break;
				}
				setState(441);
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
		enterRule(_localctx, 60, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			elementValuePair();
			setState(451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==COMMA || _la==Identifier) {
				{
				{
				setState(446);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(445);
					match(COMMA);
					}
				}

				setState(448);
				elementValuePair();
				}
				}
				setState(453);
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
		enterRule(_localctx, 62, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(454);
			id();
			setState(455);
			match(ASSIGN);
			setState(456);
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
		enterRule(_localctx, 64, RULE_elementValue);
		try {
			setState(461);
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
			case FOR:
			case GET:
			case GLOBAL:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
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
				setState(458);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(459);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(460);
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
		enterRule(_localctx, 66, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(463);
			match(LBRACE);
			setState(472);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (AT - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(464);
				elementValue();
				setState(469);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(465);
						match(COMMA);
						setState(466);
						elementValue();
						}
						} 
					}
					setState(471);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				}
				}
			}

			setState(475);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(474);
				match(COMMA);
				}
			}

			setState(477);
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
		enterRule(_localctx, 68, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
			match(LBRACE);
			setState(483);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (AT - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(480);
				statement();
				}
				}
				setState(485);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(486);
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
		enterRule(_localctx, 70, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			localVariableDeclaration();
			setState(489);
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
		enterRule(_localctx, 72, RULE_localVariableDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(494);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(491);
					modifier();
					}
					} 
				}
				setState(496);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			}
			setState(497);
			typeRef();
			setState(498);
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
		public LocalVariableDeclarationStatementContext localVariableDeclarationStatement() {
			return getRuleContext(LocalVariableDeclarationStatementContext.class,0);
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
		enterRule(_localctx, 74, RULE_statement);
		try {
			setState(520);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(500);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(501);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(502);
				switchStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(503);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(504);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(505);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(506);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(507);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(508);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(509);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(510);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(511);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(512);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(513);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(514);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(515);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(516);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(517);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(518);
				localVariableDeclarationStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(519);
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
		enterRule(_localctx, 76, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
			match(IF);
			setState(523);
			parExpression();
			setState(524);
			statement();
			setState(527);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(525);
				match(ELSE);
				setState(526);
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
		enterRule(_localctx, 78, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			match(SWITCH);
			setState(530);
			match(ON);
			setState(531);
			expression(0);
			setState(532);
			match(LBRACE);
			setState(534); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(533);
				whenControl();
				}
				}
				setState(536); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(538);
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
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public WhenControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenControl; }
	}

	public final WhenControlContext whenControl() throws RecognitionException {
		WhenControlContext _localctx = new WhenControlContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_whenControl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			match(WHEN);
			setState(543);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(541);
				match(ELSE);
				}
				break;
			case 2:
				{
				setState(542);
				expressionList();
				}
				break;
			}
			setState(545);
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
		enterRule(_localctx, 82, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(FOR);
			setState(548);
			match(LPAREN);
			setState(549);
			forControl();
			setState(550);
			match(RPAREN);
			setState(551);
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
		enterRule(_localctx, 84, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(553);
			match(WHILE);
			setState(554);
			parExpression();
			setState(555);
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
		enterRule(_localctx, 86, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(557);
			match(DO);
			setState(558);
			statement();
			setState(559);
			match(WHILE);
			setState(560);
			parExpression();
			setState(561);
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
		enterRule(_localctx, 88, RULE_tryStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(563);
			match(TRY);
			setState(564);
			block();
			setState(574);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(566); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(565);
						catchClause();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(568); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(571);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
				case 1:
					{
					setState(570);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case FINALLY:
				{
				setState(573);
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
		enterRule(_localctx, 90, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(576);
			match(RETURN);
			setState(578);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
				{
				setState(577);
				expression(0);
				}
			}

			setState(580);
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
		enterRule(_localctx, 92, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(582);
			match(THROW);
			setState(583);
			expression(0);
			setState(584);
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
		enterRule(_localctx, 94, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(586);
			match(BREAK);
			setState(587);
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
		enterRule(_localctx, 96, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589);
			match(CONTINUE);
			setState(590);
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
		enterRule(_localctx, 98, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(592);
			match(INSERT);
			setState(593);
			expression(0);
			setState(594);
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
		enterRule(_localctx, 100, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(596);
			match(UPDATE);
			setState(597);
			expression(0);
			setState(598);
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
		enterRule(_localctx, 102, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(600);
			match(DELETE);
			setState(601);
			expression(0);
			setState(602);
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
		enterRule(_localctx, 104, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			match(UNDELETE);
			setState(605);
			expression(0);
			setState(606);
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
		enterRule(_localctx, 106, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(608);
			match(UPSERT);
			setState(609);
			expression(0);
			setState(611);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(610);
				qualifiedName();
				}
			}

			setState(613);
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
		enterRule(_localctx, 108, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(615);
			match(MERGE);
			setState(616);
			expression(0);
			setState(617);
			expression(0);
			setState(618);
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
		enterRule(_localctx, 110, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(620);
			match(RUNAS);
			setState(621);
			match(LPAREN);
			setState(623);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
				{
				setState(622);
				expressionList();
				}
			}

			setState(625);
			match(RPAREN);
			setState(627);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				{
				setState(626);
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
		enterRule(_localctx, 112, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(629);
			expression(0);
			setState(630);
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
		enterRule(_localctx, 114, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(635);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(632);
				modifier();
				}
				}
				setState(637);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(640);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(638);
				getter();
				}
				break;
			case SET:
				{
				setState(639);
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
		enterRule(_localctx, 116, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(642);
			match(GET);
			setState(645);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(643);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(644);
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
		enterRule(_localctx, 118, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			match(SET);
			setState(650);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(648);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(649);
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
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
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
		enterRule(_localctx, 120, RULE_catchClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(652);
			match(CATCH);
			setState(653);
			match(LPAREN);
			setState(657);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(654);
					modifier();
					}
					} 
				}
				setState(659);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			}
			setState(660);
			qualifiedName();
			setState(661);
			id();
			setState(662);
			match(RPAREN);
			setState(663);
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
		enterRule(_localctx, 122, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(665);
			match(FINALLY);
			setState(666);
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
		enterRule(_localctx, 124, RULE_forControl);
		int _la;
		try {
			setState(680);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(668);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(670);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (AT - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(669);
					forInit();
					}
				}

				setState(672);
				match(SEMI);
				setState(674);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(673);
					expression(0);
					}
				}

				setState(676);
				match(SEMI);
				setState(678);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(677);
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
		enterRule(_localctx, 126, RULE_forInit);
		try {
			setState(684);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(682);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(683);
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
		enterRule(_localctx, 128, RULE_enhancedForControl);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(689);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(686);
					modifier();
					}
					} 
				}
				setState(691);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			}
			setState(692);
			typeRef();
			setState(693);
			id();
			setState(694);
			match(COLON);
			setState(695);
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
		enterRule(_localctx, 130, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(697);
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
		enterRule(_localctx, 132, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			match(LPAREN);
			setState(700);
			expression(0);
			setState(701);
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
		enterRule(_localctx, 134, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			expression(0);
			setState(708);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(704);
				match(COMMA);
				setState(705);
				expression(0);
				}
				}
				setState(710);
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
	public static class PrimaryExpressionContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PrimaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Arth1ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(ApexParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(ApexParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(ApexParser.MOD, 0); }
		public Arth1ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class DotExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public DotExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitOrExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BITOR() { return getToken(ApexParser.BITOR, 0); }
		public BitOrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class ArrayExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(ApexParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(ApexParser.RBRACK, 0); }
		public ArrayExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NewExpressionContext extends ExpressionContext {
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class AssignExpressionContext extends ExpressionContext {
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
		public AssignExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class MethodCallExpressionContext extends ExpressionContext {
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public MethodCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitNotExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode CARET() { return getToken(ApexParser.CARET, 0); }
		public BitNotExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class Arth2ExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(ApexParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public Arth2ExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class LogAndExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ApexParser.AND, 0); }
		public LogAndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class CastExpressionContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CastExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitAndExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BITAND() { return getToken(ApexParser.BITAND, 0); }
		public BitAndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class CmpExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LE() { return getToken(ApexParser.LE, 0); }
		public TerminalNode GE() { return getToken(ApexParser.GE, 0); }
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public CmpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class BitExpressionContext extends ExpressionContext {
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
		public BitExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class LogOrExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ApexParser.OR, 0); }
		public LogOrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class CondExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode QUESTION() { return getToken(ApexParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public CondExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class EqualityExpressionContext extends ExpressionContext {
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
		public EqualityExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PostOpExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(ApexParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ApexParser.DEC, 0); }
		public PostOpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class NegExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TILDE() { return getToken(ApexParser.TILDE, 0); }
		public TerminalNode BANG() { return getToken(ApexParser.BANG, 0); }
		public NegExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class PreOpExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ADD() { return getToken(ApexParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public TerminalNode INC() { return getToken(ApexParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ApexParser.DEC, 0); }
		public PreOpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	public static class InstanceOfExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public InstanceOfExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 136;
		enterRecursionRule(_localctx, 136, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(725);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				{
				_localctx = new PrimaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(712);
				primary();
				}
				break;
			case 2:
				{
				_localctx = new MethodCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(713);
				methodCall();
				}
				break;
			case 3:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(714);
				match(NEW);
				setState(715);
				creator();
				}
				break;
			case 4:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(716);
				match(LPAREN);
				setState(717);
				typeRef();
				setState(718);
				match(RPAREN);
				setState(719);
				expression(17);
				}
				break;
			case 5:
				{
				_localctx = new PreOpExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(721);
				_la = _input.LA(1);
				if ( !(((((_la - 87)) & ~0x3f) == 0 && ((1L << (_la - 87)) & ((1L << (INC - 87)) | (1L << (DEC - 87)) | (1L << (ADD - 87)) | (1L << (SUB - 87)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(722);
				expression(15);
				}
				break;
			case 6:
				{
				_localctx = new NegExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(723);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(724);
				expression(14);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(792);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(790);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
					case 1:
						{
						_localctx = new Arth1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(727);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(728);
						_la = _input.LA(1);
						if ( !(((((_la - 91)) & ~0x3f) == 0 && ((1L << (_la - 91)) & ((1L << (MUL - 91)) | (1L << (DIV - 91)) | (1L << (MOD - 91)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(729);
						expression(14);
						}
						break;
					case 2:
						{
						_localctx = new Arth2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(730);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(731);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(732);
						expression(13);
						}
						break;
					case 3:
						{
						_localctx = new BitExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(733);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(741);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
						case 1:
							{
							setState(734);
							match(LT);
							setState(735);
							match(LT);
							}
							break;
						case 2:
							{
							setState(736);
							match(GT);
							setState(737);
							match(GT);
							setState(738);
							match(GT);
							}
							break;
						case 3:
							{
							setState(739);
							match(GT);
							setState(740);
							match(GT);
							}
							break;
						}
						setState(743);
						expression(12);
						}
						break;
					case 4:
						{
						_localctx = new CmpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(744);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(745);
						_la = _input.LA(1);
						if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (LE - 72)) | (1L << (GE - 72)) | (1L << (GT - 72)) | (1L << (LT - 72)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(746);
						expression(11);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(747);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(748);
						_la = _input.LA(1);
						if ( !(((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (EQUAL - 80)) | (1L << (TRIPLEEQUAL - 80)) | (1L << (NOTEQUAL - 80)) | (1L << (LESSANDGREATER - 80)) | (1L << (TRIPLENOTEQUAL - 80)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(749);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new BitAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(750);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(751);
						match(BITAND);
						setState(752);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new BitNotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(753);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(754);
						match(CARET);
						setState(755);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new BitOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(756);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(757);
						match(BITOR);
						setState(758);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new LogAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(759);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(760);
						match(AND);
						setState(761);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new LogOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(762);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(763);
						match(OR);
						setState(764);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new CondExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(765);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(766);
						match(QUESTION);
						setState(767);
						expression(0);
						setState(768);
						match(COLON);
						setState(769);
						expression(2);
						}
						break;
					case 12:
						{
						_localctx = new AssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(771);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(772);
						_la = _input.LA(1);
						if ( !(((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (ASSIGN - 71)) | (1L << (ADD_ASSIGN - 71)) | (1L << (SUB_ASSIGN - 71)) | (1L << (MUL_ASSIGN - 71)) | (1L << (DIV_ASSIGN - 71)) | (1L << (AND_ASSIGN - 71)) | (1L << (OR_ASSIGN - 71)) | (1L << (XOR_ASSIGN - 71)) | (1L << (MOD_ASSIGN - 71)) | (1L << (LSHIFT_ASSIGN - 71)) | (1L << (RSHIFT_ASSIGN - 71)) | (1L << (URSHIFT_ASSIGN - 71)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(773);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new DotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(774);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(775);
						match(DOT);
						setState(778);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
						case 1:
							{
							setState(776);
							id();
							}
							break;
						case 2:
							{
							setState(777);
							methodCall();
							}
							break;
						}
						}
						break;
					case 14:
						{
						_localctx = new ArrayExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(780);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(781);
						match(LBRACK);
						setState(782);
						expression(0);
						setState(783);
						match(RBRACK);
						}
						break;
					case 15:
						{
						_localctx = new PostOpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(785);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(786);
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
					case 16:
						{
						_localctx = new InstanceOfExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(787);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(788);
						match(INSTANCEOF);
						setState(789);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(794);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
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
	public static class TypeRefPrimaryContext extends PrimaryContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public TypeRefPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class IdPrimaryContext extends PrimaryContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public IdPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	public static class LiteralPrimaryContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
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
		enterRule(_localctx, 138, RULE_primary);
		try {
			setState(808);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				_localctx = new SubPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(795);
				match(LPAREN);
				setState(796);
				expression(0);
				setState(797);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ThisPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(799);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new SuperPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(800);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new LiteralPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(801);
				literal();
				}
				break;
			case 5:
				_localctx = new TypeRefPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(802);
				typeRef();
				setState(803);
				match(DOT);
				setState(804);
				match(CLASS);
				}
				break;
			case 6:
				_localctx = new IdPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(806);
				id();
				}
				break;
			case 7:
				_localctx = new SoqlPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(807);
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

	public static class MethodCallContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_methodCall);
		int _la;
		try {
			setState(829);
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
			case FOR:
			case GET:
			case GLOBAL:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTERFACE:
			case MERGE:
			case NEW:
			case NULL:
			case ON:
			case OVERRIDE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case RUNAS:
			case SET:
			case SHARING:
			case SHORT:
			case STATIC:
			case SWITCH:
			case TESTMETHOD:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(810);
				id();
				setState(811);
				match(LPAREN);
				setState(813);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(812);
					expressionList();
					}
				}

				setState(815);
				match(RPAREN);
				}
				break;
			case THIS:
				enterOuterAlt(_localctx, 2);
				{
				setState(817);
				match(THIS);
				setState(818);
				match(LPAREN);
				setState(820);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(819);
					expressionList();
					}
				}

				setState(822);
				match(RPAREN);
				}
				break;
			case SUPER:
				enterOuterAlt(_localctx, 3);
				{
				setState(823);
				match(SUPER);
				setState(824);
				match(LPAREN);
				setState(826);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
					{
					setState(825);
					expressionList();
					}
				}

				setState(828);
				match(RPAREN);
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
		enterRule(_localctx, 142, RULE_creator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(831);
			createdName();
			setState(837);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				{
				setState(832);
				noRest();
				}
				break;
			case 2:
				{
				setState(833);
				classCreatorRest();
				}
				break;
			case 3:
				{
				setState(834);
				arrayCreatorRest();
				}
				break;
			case 4:
				{
				setState(835);
				mapCreatorRest();
				}
				break;
			case 5:
				{
				setState(836);
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
		enterRule(_localctx, 144, RULE_createdName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(839);
			idCreatedNamePair();
			setState(844);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(840);
				match(DOT);
				setState(841);
				idCreatedNamePair();
				}
				}
				setState(846);
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
		enterRule(_localctx, 146, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(847);
			id();
			setState(852);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(848);
				match(LT);
				setState(849);
				typeList();
				setState(850);
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
		enterRule(_localctx, 148, RULE_noRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(854);
			match(LBRACE);
			setState(855);
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
		enterRule(_localctx, 150, RULE_classCreatorRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(857);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ApexParser.RBRACK, 0); }
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
		enterRule(_localctx, 152, RULE_arrayCreatorRest);
		try {
			setState(868);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(859);
				match(LBRACK);
				setState(860);
				expression(0);
				setState(861);
				match(RBRACK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(863);
				match(LBRACK);
				setState(864);
				match(RBRACK);
				setState(866);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
				case 1:
					{
					setState(865);
					arrayInitializer();
					}
					break;
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
		enterRule(_localctx, 154, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(870);
			match(LBRACE);
			setState(871);
			mapCreatorRestPair();
			setState(876);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(872);
				match(COMMA);
				setState(873);
				mapCreatorRestPair();
				}
				}
				setState(878);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(879);
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
		enterRule(_localctx, 156, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(881);
			expression(0);
			setState(882);
			match(MAP);
			setState(883);
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
		enterRule(_localctx, 158, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(885);
			match(LBRACE);
			setState(886);
			expression(0);
			setState(891);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(887);
				match(COMMA);
				{
				setState(888);
				expression(0);
				}
				}
				}
				setState(893);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(894);
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
		enterRule(_localctx, 160, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(896);
			match(LPAREN);
			setState(898);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << IntegerLiteral) | (1L << NumberLiteral) | (1L << BooleanLiteral) | (1L << StringLiteral) | (1L << LPAREN))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (LBRACK - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)))) != 0)) {
				{
				setState(897);
				expressionList();
				}
			}

			setState(900);
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
		enterRule(_localctx, 162, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(902);
			match(LBRACK);
			setState(907);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(905);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
					case 1:
						{
						setState(903);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(904);
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
				setState(909);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,87,_ctx);
			}
			setState(910);
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
		public TerminalNode FOR() { return getToken(ApexParser.FOR, 0); }
		public TerminalNode GET() { return getToken(ApexParser.GET, 0); }
		public TerminalNode GLOBAL() { return getToken(ApexParser.GLOBAL, 0); }
		public TerminalNode IF() { return getToken(ApexParser.IF, 0); }
		public TerminalNode IMPLEMENTS() { return getToken(ApexParser.IMPLEMENTS, 0); }
		public TerminalNode INHERITED() { return getToken(ApexParser.INHERITED, 0); }
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TerminalNode INTERFACE() { return getToken(ApexParser.INTERFACE, 0); }
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public TerminalNode ON() { return getToken(ApexParser.ON, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode RETURN() { return getToken(ApexParser.RETURN, 0); }
		public TerminalNode RUNAS() { return getToken(ApexParser.RUNAS, 0); }
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public TerminalNode SHORT() { return getToken(ApexParser.SHORT, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
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
		enterRule(_localctx, 164, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(912);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) ) {
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
		case 68:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 13);
		case 1:
			return precpred(_ctx, 12);
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 21);
		case 13:
			return precpred(_ctx, 20);
		case 14:
			return precpred(_ctx, 16);
		case 15:
			return precpred(_ctx, 9);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3t\u0395\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\3\2\3\2\3\2\3\3\7\3\u00ad\n\3\f\3\16\3\u00b0\13\3\3\3\3\3\7\3\u00b4\n"+
		"\3\f\3\16\3\u00b7\13\3\3\3\3\3\7\3\u00bb\n\3\f\3\16\3\u00be\13\3\3\3\5"+
		"\3\u00c1\n\3\3\4\3\4\3\4\3\4\5\4\u00c7\n\4\3\4\3\4\5\4\u00cb\n\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\5\5\u00d3\n\5\3\5\3\5\3\6\3\6\3\6\7\6\u00da\n\6\f\6"+
		"\16\6\u00dd\13\6\3\7\3\7\3\7\3\7\5\7\u00e3\n\7\3\7\3\7\3\b\3\b\3\b\7\b"+
		"\u00ea\n\b\f\b\16\b\u00ed\13\b\3\t\3\t\7\t\u00f1\n\t\f\t\16\t\u00f4\13"+
		"\t\3\t\3\t\3\n\3\n\7\n\u00fa\n\n\f\n\16\n\u00fd\13\n\3\n\3\n\3\13\3\13"+
		"\5\13\u0103\n\13\3\13\3\13\7\13\u0107\n\13\f\13\16\13\u010a\13\13\3\13"+
		"\5\13\u010d\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\5\f\u0122\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u012b"+
		"\n\r\3\16\7\16\u012e\n\16\f\16\16\16\u0131\13\16\3\16\3\16\5\16\u0135"+
		"\n\16\3\16\3\16\3\16\3\16\5\16\u013b\n\16\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\7\21\u0149\n\21\f\21\16\21\u014c\13\21"+
		"\3\21\3\21\3\22\7\22\u0151\n\22\f\22\16\22\u0154\13\22\3\22\3\22\5\22"+
		"\u0158\n\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\7\23\u0161\n\23\f\23\16"+
		"\23\u0164\13\23\3\24\3\24\3\24\5\24\u0169\n\24\3\25\3\25\3\25\3\25\7\25"+
		"\u016f\n\25\f\25\16\25\u0172\13\25\3\25\5\25\u0175\n\25\5\25\u0177\n\25"+
		"\3\25\3\25\3\26\3\26\3\26\7\26\u017e\n\26\f\26\16\26\u0181\13\26\3\26"+
		"\3\26\3\27\3\27\7\27\u0187\n\27\f\27\16\27\u018a\13\27\3\30\3\30\5\30"+
		"\u018e\n\30\3\31\3\31\3\31\3\31\3\32\3\32\5\32\u0196\n\32\3\32\3\32\3"+
		"\33\3\33\3\33\7\33\u019d\n\33\f\33\16\33\u01a0\13\33\3\34\7\34\u01a3\n"+
		"\34\f\34\16\34\u01a6\13\34\3\34\3\34\3\34\3\35\3\35\3\35\7\35\u01ae\n"+
		"\35\f\35\16\35\u01b1\13\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\5\37\u01ba"+
		"\n\37\3\37\5\37\u01bd\n\37\3 \3 \5 \u01c1\n \3 \7 \u01c4\n \f \16 \u01c7"+
		"\13 \3!\3!\3!\3!\3\"\3\"\3\"\5\"\u01d0\n\"\3#\3#\3#\3#\7#\u01d6\n#\f#"+
		"\16#\u01d9\13#\5#\u01db\n#\3#\5#\u01de\n#\3#\3#\3$\3$\7$\u01e4\n$\f$\16"+
		"$\u01e7\13$\3$\3$\3%\3%\3%\3&\7&\u01ef\n&\f&\16&\u01f2\13&\3&\3&\3&\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\5\'\u020b\n\'\3(\3(\3(\3(\3(\5(\u0212\n(\3)\3)\3)\3)\3)\6)\u0219"+
		"\n)\r)\16)\u021a\3)\3)\3*\3*\3*\5*\u0222\n*\3*\3*\3+\3+\3+\3+\3+\3+\3"+
		",\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3.\6.\u0239\n.\r.\16.\u023a\3.\5.\u023e"+
		"\n.\3.\5.\u0241\n.\3/\3/\5/\u0245\n/\3/\3/\3\60\3\60\3\60\3\60\3\61\3"+
		"\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\65\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\66\3\67\3\67\3\67\5\67\u0266\n\67\3\67"+
		"\3\67\38\38\38\38\38\39\39\39\59\u0272\n9\39\39\59\u0276\n9\3:\3:\3:\3"+
		";\7;\u027c\n;\f;\16;\u027f\13;\3;\3;\5;\u0283\n;\3<\3<\3<\5<\u0288\n<"+
		"\3=\3=\3=\5=\u028d\n=\3>\3>\3>\7>\u0292\n>\f>\16>\u0295\13>\3>\3>\3>\3"+
		">\3>\3?\3?\3?\3@\3@\5@\u02a1\n@\3@\3@\5@\u02a5\n@\3@\3@\5@\u02a9\n@\5"+
		"@\u02ab\n@\3A\3A\5A\u02af\nA\3B\7B\u02b2\nB\fB\16B\u02b5\13B\3B\3B\3B"+
		"\3B\3B\3C\3C\3D\3D\3D\3D\3E\3E\3E\7E\u02c5\nE\fE\16E\u02c8\13E\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u02d8\nF\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\5F\u02e8\nF\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\5"+
		"F\u030d\nF\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\7F\u0319\nF\fF\16F\u031c\13F"+
		"\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\5G\u032b\nG\3H\3H\3H\5H\u0330"+
		"\nH\3H\3H\3H\3H\3H\5H\u0337\nH\3H\3H\3H\3H\5H\u033d\nH\3H\5H\u0340\nH"+
		"\3I\3I\3I\3I\3I\3I\5I\u0348\nI\3J\3J\3J\7J\u034d\nJ\fJ\16J\u0350\13J\3"+
		"K\3K\3K\3K\3K\5K\u0357\nK\3L\3L\3L\3M\3M\3N\3N\3N\3N\3N\3N\3N\5N\u0365"+
		"\nN\5N\u0367\nN\3O\3O\3O\3O\7O\u036d\nO\fO\16O\u0370\13O\3O\3O\3P\3P\3"+
		"P\3P\3Q\3Q\3Q\3Q\7Q\u037c\nQ\fQ\16Q\u037f\13Q\3Q\3Q\3R\3R\5R\u0385\nR"+
		"\3R\3R\3S\3S\3S\7S\u038c\nS\fS\16S\u038f\13S\3S\3S\3T\3T\3T\3\u038d\3"+
		"\u008aU\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:"+
		"<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a"+
		"\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2"+
		"\u00a4\u00a6\2\r\4\2\36\36;>\3\2Y\\\3\2NO\4\2]^bb\3\2[\\\3\2JM\3\2RV\4"+
		"\2IIdn\3\2YZ\3\2EE\6\2\3)+,.:pp\2\u03de\2\u00a8\3\2\2\2\4\u00c0\3\2\2"+
		"\2\6\u00c2\3\2\2\2\b\u00ce\3\2\2\2\n\u00d6\3\2\2\2\f\u00de\3\2\2\2\16"+
		"\u00e6\3\2\2\2\20\u00ee\3\2\2\2\22\u00f7\3\2\2\2\24\u010c\3\2\2\2\26\u0121"+
		"\3\2\2\2\30\u012a\3\2\2\2\32\u012f\3\2\2\2\34\u013c\3\2\2\2\36\u0140\3"+
		"\2\2\2 \u0144\3\2\2\2\"\u0152\3\2\2\2$\u015d\3\2\2\2&\u0165\3\2\2\2(\u016a"+
		"\3\2\2\2*\u017a\3\2\2\2,\u0188\3\2\2\2.\u018b\3\2\2\2\60\u018f\3\2\2\2"+
		"\62\u0193\3\2\2\2\64\u0199\3\2\2\2\66\u01a4\3\2\2\28\u01aa\3\2\2\2:\u01b2"+
		"\3\2\2\2<\u01b4\3\2\2\2>\u01be\3\2\2\2@\u01c8\3\2\2\2B\u01cf\3\2\2\2D"+
		"\u01d1\3\2\2\2F\u01e1\3\2\2\2H\u01ea\3\2\2\2J\u01f0\3\2\2\2L\u020a\3\2"+
		"\2\2N\u020c\3\2\2\2P\u0213\3\2\2\2R\u021e\3\2\2\2T\u0225\3\2\2\2V\u022b"+
		"\3\2\2\2X\u022f\3\2\2\2Z\u0235\3\2\2\2\\\u0242\3\2\2\2^\u0248\3\2\2\2"+
		"`\u024c\3\2\2\2b\u024f\3\2\2\2d\u0252\3\2\2\2f\u0256\3\2\2\2h\u025a\3"+
		"\2\2\2j\u025e\3\2\2\2l\u0262\3\2\2\2n\u0269\3\2\2\2p\u026e\3\2\2\2r\u0277"+
		"\3\2\2\2t\u027d\3\2\2\2v\u0284\3\2\2\2x\u0289\3\2\2\2z\u028e\3\2\2\2|"+
		"\u029b\3\2\2\2~\u02aa\3\2\2\2\u0080\u02ae\3\2\2\2\u0082\u02b3\3\2\2\2"+
		"\u0084\u02bb\3\2\2\2\u0086\u02bd\3\2\2\2\u0088\u02c1\3\2\2\2\u008a\u02d7"+
		"\3\2\2\2\u008c\u032a\3\2\2\2\u008e\u033f\3\2\2\2\u0090\u0341\3\2\2\2\u0092"+
		"\u0349\3\2\2\2\u0094\u0351\3\2\2\2\u0096\u0358\3\2\2\2\u0098\u035b\3\2"+
		"\2\2\u009a\u0366\3\2\2\2\u009c\u0368\3\2\2\2\u009e\u0373\3\2\2\2\u00a0"+
		"\u0377\3\2\2\2\u00a2\u0382\3\2\2\2\u00a4\u0388\3\2\2\2\u00a6\u0392\3\2"+
		"\2\2\u00a8\u00a9\5\4\3\2\u00a9\u00aa\7\2\2\3\u00aa\3\3\2\2\2\u00ab\u00ad"+
		"\5\26\f\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2"+
		"\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00c1"+
		"\5\6\4\2\u00b2\u00b4\5\26\f\2\u00b3\u00b2\3\2\2\2\u00b4\u00b7\3\2\2\2"+
		"\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8\3\2\2\2\u00b7\u00b5"+
		"\3\2\2\2\u00b8\u00c1\5\b\5\2\u00b9\u00bb\5\26\f\2\u00ba\u00b9\3\2\2\2"+
		"\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bf"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c1\5\f\7\2\u00c0\u00ae\3\2\2\2\u00c0"+
		"\u00b5\3\2\2\2\u00c0\u00bc\3\2\2\2\u00c1\5\3\2\2\2\u00c2\u00c3\7\b\2\2"+
		"\u00c3\u00c6\5\u00a6T\2\u00c4\u00c5\7\20\2\2\u00c5\u00c7\5*\26\2\u00c6"+
		"\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c9\7\27"+
		"\2\2\u00c9\u00cb\5\16\b\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"+
		"\u00cc\3\2\2\2\u00cc\u00cd\5\20\t\2\u00cd\7\3\2\2\2\u00ce\u00cf\7\17\2"+
		"\2\u00cf\u00d0\5\u00a6T\2\u00d0\u00d2\7B\2\2\u00d1\u00d3\5\n\6\2\u00d2"+
		"\u00d1\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\7C"+
		"\2\2\u00d5\t\3\2\2\2\u00d6\u00db\5\u00a6T\2\u00d7\u00d8\7G\2\2\u00d8\u00da"+
		"\5\u00a6T\2\u00d9\u00d7\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2"+
		"\2\u00db\u00dc\3\2\2\2\u00dc\13\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00df"+
		"\7\33\2\2\u00df\u00e2\5\u00a6T\2\u00e0\u00e1\7\20\2\2\u00e1\u00e3\5\16"+
		"\b\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e5\5\22\n\2\u00e5\r\3\2\2\2\u00e6\u00eb\5*\26\2\u00e7\u00e8\7G\2\2"+
		"\u00e8\u00ea\5*\26\2\u00e9\u00e7\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9"+
		"\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\17\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee"+
		"\u00f2\7B\2\2\u00ef\u00f1\5\24\13\2\u00f0\u00ef\3\2\2\2\u00f1\u00f4\3"+
		"\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f5\3\2\2\2\u00f4"+
		"\u00f2\3\2\2\2\u00f5\u00f6\7C\2\2\u00f6\21\3\2\2\2\u00f7\u00fb\7B\2\2"+
		"\u00f8\u00fa\5\"\22\2\u00f9\u00f8\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9"+
		"\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe"+
		"\u00ff\7C\2\2\u00ff\23\3\2\2\2\u0100\u010d\7F\2\2\u0101\u0103\7)\2\2\u0102"+
		"\u0101\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u010d\5F"+
		"$\2\u0105\u0107\5\26\f\2\u0106\u0105\3\2\2\2\u0107\u010a\3\2\2\2\u0108"+
		"\u0106\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b\3\2\2\2\u010a\u0108\3\2"+
		"\2\2\u010b\u010d\5\30\r\2\u010c\u0100\3\2\2\2\u010c\u0102\3\2\2\2\u010c"+
		"\u0108\3\2\2\2\u010d\25\3\2\2\2\u010e\u0122\5<\37\2\u010f\u0122\7\25\2"+
		"\2\u0110\u0122\7#\2\2\u0111\u0122\7\"\2\2\u0112\u0122\7!\2\2\u0113\u0122"+
		"\7/\2\2\u0114\u0122\7)\2\2\u0115\u0122\7\3\2\2\u0116\u0122\7\21\2\2\u0117"+
		"\u0122\7\66\2\2\u0118\u0122\7 \2\2\u0119\u0122\7\64\2\2\u011a\u0122\7"+
		",\2\2\u011b\u011c\79\2\2\u011c\u0122\7\'\2\2\u011d\u011e\7:\2\2\u011e"+
		"\u0122\7\'\2\2\u011f\u0120\7\30\2\2\u0120\u0122\7\'\2\2\u0121\u010e\3"+
		"\2\2\2\u0121\u010f\3\2\2\2\u0121\u0110\3\2\2\2\u0121\u0111\3\2\2\2\u0121"+
		"\u0112\3\2\2\2\u0121\u0113\3\2\2\2\u0121\u0114\3\2\2\2\u0121\u0115\3\2"+
		"\2\2\u0121\u0116\3\2\2\2\u0121\u0117\3\2\2\2\u0121\u0118\3\2\2\2\u0121"+
		"\u0119\3\2\2\2\u0121\u011a\3\2\2\2\u0121\u011b\3\2\2\2\u0121\u011d\3\2"+
		"\2\2\u0121\u011f\3\2\2\2\u0122\27\3\2\2\2\u0123\u012b\5\32\16\2\u0124"+
		"\u012b\5\36\20\2\u0125\u012b\5\34\17\2\u0126\u012b\5\f\7\2\u0127\u012b"+
		"\5\6\4\2\u0128\u012b\5\b\5\2\u0129\u012b\5 \21\2\u012a\u0123\3\2\2\2\u012a"+
		"\u0124\3\2\2\2\u012a\u0125\3\2\2\2\u012a\u0126\3\2\2\2\u012a\u0127\3\2"+
		"\2\2\u012a\u0128\3\2\2\2\u012a\u0129\3\2\2\2\u012b\31\3\2\2\2\u012c\u012e"+
		"\5\26\f\2\u012d\u012c\3\2\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2\2"+
		"\u012f\u0130\3\2\2\2\u0130\u0134\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u0135"+
		"\5*\26\2\u0133\u0135\7\65\2\2\u0134\u0132\3\2\2\2\u0134\u0133\3\2\2\2"+
		"\u0135\u0136\3\2\2\2\u0136\u0137\5\u00a6T\2\u0137\u013a\5\62\32\2\u0138"+
		"\u013b\5F$\2\u0139\u013b\7F\2\2\u013a\u0138\3\2\2\2\u013a\u0139\3\2\2"+
		"\2\u013b\33\3\2\2\2\u013c\u013d\58\35\2\u013d\u013e\5\62\32\2\u013e\u013f"+
		"\5F$\2\u013f\35\3\2\2\2\u0140\u0141\5*\26\2\u0141\u0142\5$\23\2\u0142"+
		"\u0143\7F\2\2\u0143\37\3\2\2\2\u0144\u0145\5*\26\2\u0145\u0146\5\u00a6"+
		"T\2\u0146\u014a\7B\2\2\u0147\u0149\5t;\2\u0148\u0147\3\2\2\2\u0149\u014c"+
		"\3\2\2\2\u014a\u0148\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u014d\3\2\2\2\u014c"+
		"\u014a\3\2\2\2\u014d\u014e\7C\2\2\u014e!\3\2\2\2\u014f\u0151\5\26\f\2"+
		"\u0150\u014f\3\2\2\2\u0151\u0154\3\2\2\2\u0152\u0150\3\2\2\2\u0152\u0153"+
		"\3\2\2\2\u0153\u0157\3\2\2\2\u0154\u0152\3\2\2\2\u0155\u0158\5*\26\2\u0156"+
		"\u0158\7\65\2\2\u0157\u0155\3\2\2\2\u0157\u0156\3\2\2\2\u0158\u0159\3"+
		"\2\2\2\u0159\u015a\5\u00a6T\2\u015a\u015b\5\62\32\2\u015b\u015c\7F\2\2"+
		"\u015c#\3\2\2\2\u015d\u0162\5&\24\2\u015e\u015f\7G\2\2\u015f\u0161\5&"+
		"\24\2\u0160\u015e\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162"+
		"\u0163\3\2\2\2\u0163%\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0168\5\u00a6"+
		"T\2\u0166\u0167\7I\2\2\u0167\u0169\5\u008aF\2\u0168\u0166\3\2\2\2\u0168"+
		"\u0169\3\2\2\2\u0169\'\3\2\2\2\u016a\u0176\7B\2\2\u016b\u0170\5\u008a"+
		"F\2\u016c\u016d\7G\2\2\u016d\u016f\5\u008aF\2\u016e\u016c\3\2\2\2\u016f"+
		"\u0172\3\2\2\2\u0170\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0174\3\2"+
		"\2\2\u0172\u0170\3\2\2\2\u0173\u0175\7G\2\2\u0174\u0173\3\2\2\2\u0174"+
		"\u0175\3\2\2\2\u0175\u0177\3\2\2\2\u0176\u016b\3\2\2\2\u0176\u0177\3\2"+
		"\2\2\u0177\u0178\3\2\2\2\u0178\u0179\7C\2\2\u0179)\3\2\2\2\u017a\u017f"+
		"\5.\30\2\u017b\u017c\7H\2\2\u017c\u017e\5.\30\2\u017d\u017b\3\2\2\2\u017e"+
		"\u0181\3\2\2\2\u017f\u017d\3\2\2\2\u017f\u0180\3\2\2\2\u0180\u0182\3\2"+
		"\2\2\u0181\u017f\3\2\2\2\u0182\u0183\5,\27\2\u0183+\3\2\2\2\u0184\u0185"+
		"\7D\2\2\u0185\u0187\7E\2\2\u0186\u0184\3\2\2\2\u0187\u018a\3\2\2\2\u0188"+
		"\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189-\3\2\2\2\u018a\u0188\3\2\2\2"+
		"\u018b\u018d\5\u00a6T\2\u018c\u018e\5\60\31\2\u018d\u018c\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e/\3\2\2\2\u018f\u0190\7M\2\2\u0190\u0191\5\16\b\2"+
		"\u0191\u0192\7L\2\2\u0192\61\3\2\2\2\u0193\u0195\7@\2\2\u0194\u0196\5"+
		"\64\33\2\u0195\u0194\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0197\3\2\2\2\u0197"+
		"\u0198\7A\2\2\u0198\63\3\2\2\2\u0199\u019e\5\66\34\2\u019a\u019b\7G\2"+
		"\2\u019b\u019d\5\66\34\2\u019c\u019a\3\2\2\2\u019d\u01a0\3\2\2\2\u019e"+
		"\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\65\3\2\2\2\u01a0\u019e\3\2\2"+
		"\2\u01a1\u01a3\5\26\f\2\u01a2\u01a1\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4"+
		"\u01a2\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5\u01a7\3\2\2\2\u01a6\u01a4\3\2"+
		"\2\2\u01a7\u01a8\5*\26\2\u01a8\u01a9\5\u00a6T\2\u01a9\67\3\2\2\2\u01aa"+
		"\u01af\5\u00a6T\2\u01ab\u01ac\7H\2\2\u01ac\u01ae\5\u00a6T\2\u01ad\u01ab"+
		"\3\2\2\2\u01ae\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0"+
		"9\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2\u01b3\t\2\2\2\u01b3;\3\2\2\2\u01b4"+
		"\u01b5\7o\2\2\u01b5\u01bc\58\35\2\u01b6\u01b9\7@\2\2\u01b7\u01ba\5> \2"+
		"\u01b8\u01ba\5B\"\2\u01b9\u01b7\3\2\2\2\u01b9\u01b8\3\2\2\2\u01b9\u01ba"+
		"\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bd\7A\2\2\u01bc\u01b6\3\2\2\2\u01bc"+
		"\u01bd\3\2\2\2\u01bd=\3\2\2\2\u01be\u01c5\5@!\2\u01bf\u01c1\7G\2\2\u01c0"+
		"\u01bf\3\2\2\2\u01c0\u01c1\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2\u01c4\5@"+
		"!\2\u01c3\u01c0\3\2\2\2\u01c4\u01c7\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c5"+
		"\u01c6\3\2\2\2\u01c6?\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c8\u01c9\5\u00a6"+
		"T\2\u01c9\u01ca\7I\2\2\u01ca\u01cb\5B\"\2\u01cbA\3\2\2\2\u01cc\u01d0\5"+
		"\u008aF\2\u01cd\u01d0\5<\37\2\u01ce\u01d0\5D#\2\u01cf\u01cc\3\2\2\2\u01cf"+
		"\u01cd\3\2\2\2\u01cf\u01ce\3\2\2\2\u01d0C\3\2\2\2\u01d1\u01da\7B\2\2\u01d2"+
		"\u01d7\5B\"\2\u01d3\u01d4\7G\2\2\u01d4\u01d6\5B\"\2\u01d5\u01d3\3\2\2"+
		"\2\u01d6\u01d9\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01db"+
		"\3\2\2\2\u01d9\u01d7\3\2\2\2\u01da\u01d2\3\2\2\2\u01da\u01db\3\2\2\2\u01db"+
		"\u01dd\3\2\2\2\u01dc\u01de\7G\2\2\u01dd\u01dc\3\2\2\2\u01dd\u01de\3\2"+
		"\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\7C\2\2\u01e0E\3\2\2\2\u01e1\u01e5"+
		"\7B\2\2\u01e2\u01e4\5L\'\2\u01e3\u01e2\3\2\2\2\u01e4\u01e7\3\2\2\2\u01e5"+
		"\u01e3\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e5\3\2"+
		"\2\2\u01e8\u01e9\7C\2\2\u01e9G\3\2\2\2\u01ea\u01eb\5J&\2\u01eb\u01ec\7"+
		"F\2\2\u01ecI\3\2\2\2\u01ed\u01ef\5\26\f\2\u01ee\u01ed\3\2\2\2\u01ef\u01f2"+
		"\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f3\3\2\2\2\u01f2"+
		"\u01f0\3\2\2\2\u01f3\u01f4\5*\26\2\u01f4\u01f5\5$\23\2\u01f5K\3\2\2\2"+
		"\u01f6\u020b\5F$\2\u01f7\u020b\5N(\2\u01f8\u020b\5P)\2\u01f9\u020b\5T"+
		"+\2\u01fa\u020b\5V,\2\u01fb\u020b\5X-\2\u01fc\u020b\5Z.\2\u01fd\u020b"+
		"\5\\/\2\u01fe\u020b\5^\60\2\u01ff\u020b\5`\61\2\u0200\u020b\5b\62\2\u0201"+
		"\u020b\5d\63\2\u0202\u020b\5f\64\2\u0203\u020b\5h\65\2\u0204\u020b\5j"+
		"\66\2\u0205\u020b\5l\67\2\u0206\u020b\5n8\2\u0207\u020b\5p9\2\u0208\u020b"+
		"\5H%\2\u0209\u020b\5r:\2\u020a\u01f6\3\2\2\2\u020a\u01f7\3\2\2\2\u020a"+
		"\u01f8\3\2\2\2\u020a\u01f9\3\2\2\2\u020a\u01fa\3\2\2\2\u020a\u01fb\3\2"+
		"\2\2\u020a\u01fc\3\2\2\2\u020a\u01fd\3\2\2\2\u020a\u01fe\3\2\2\2\u020a"+
		"\u01ff\3\2\2\2\u020a\u0200\3\2\2\2\u020a\u0201\3\2\2\2\u020a\u0202\3\2"+
		"\2\2\u020a\u0203\3\2\2\2\u020a\u0204\3\2\2\2\u020a\u0205\3\2\2\2\u020a"+
		"\u0206\3\2\2\2\u020a\u0207\3\2\2\2\u020a\u0208\3\2\2\2\u020a\u0209\3\2"+
		"\2\2\u020bM\3\2\2\2\u020c\u020d\7\26\2\2\u020d\u020e\5\u0086D\2\u020e"+
		"\u0211\5L\'\2\u020f\u0210\7\16\2\2\u0210\u0212\5L\'\2\u0211\u020f\3\2"+
		"\2\2\u0211\u0212\3\2\2\2\u0212O\3\2\2\2\u0213\u0214\7+\2\2\u0214\u0215"+
		"\7\37\2\2\u0215\u0216\5\u008aF\2\u0216\u0218\7B\2\2\u0217\u0219\5R*\2"+
		"\u0218\u0217\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u0218\3\2\2\2\u021a\u021b"+
		"\3\2\2\2\u021b\u021c\3\2\2\2\u021c\u021d\7C\2\2\u021dQ\3\2\2\2\u021e\u0221"+
		"\7\67\2\2\u021f\u0222\7\16\2\2\u0220\u0222\5\u0088E\2\u0221\u021f\3\2"+
		"\2\2\u0221\u0220\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0224\5F$\2\u0224S"+
		"\3\2\2\2\u0225\u0226\7\23\2\2\u0226\u0227\7@\2\2\u0227\u0228\5~@\2\u0228"+
		"\u0229\7A\2\2\u0229\u022a\5L\'\2\u022aU\3\2\2\2\u022b\u022c\78\2\2\u022c"+
		"\u022d\5\u0086D\2\u022d\u022e\5L\'\2\u022eW\3\2\2\2\u022f\u0230\7\r\2"+
		"\2\u0230\u0231\5L\'\2\u0231\u0232\78\2\2\u0232\u0233\5\u0086D\2\u0233"+
		"\u0234\7F\2\2\u0234Y\3\2\2\2\u0235\u0236\7\60\2\2\u0236\u0240\5F$\2\u0237"+
		"\u0239\5z>\2\u0238\u0237\3\2\2\2\u0239\u023a\3\2\2\2\u023a\u0238\3\2\2"+
		"\2\u023a\u023b\3\2\2\2\u023b\u023d\3\2\2\2\u023c\u023e\5|?\2\u023d\u023c"+
		"\3\2\2\2\u023d\u023e\3\2\2\2\u023e\u0241\3\2\2\2\u023f\u0241\5|?\2\u0240"+
		"\u0238\3\2\2\2\u0240\u023f\3\2\2\2\u0241[\3\2\2\2\u0242\u0244\7$\2\2\u0243"+
		"\u0245\5\u008aF\2\u0244\u0243\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0246"+
		"\3\2\2\2\u0246\u0247\7F\2\2\u0247]\3\2\2\2\u0248\u0249\7.\2\2\u0249\u024a"+
		"\5\u008aF\2\u024a\u024b\7F\2\2\u024b_\3\2\2\2\u024c\u024d\7\4\2\2\u024d"+
		"\u024e\7F\2\2\u024ea\3\2\2\2\u024f\u0250\7\n\2\2\u0250\u0251\7F\2\2\u0251"+
		"c\3\2\2\2\u0252\u0253\7\31\2\2\u0253\u0254\5\u008aF\2\u0254\u0255\7F\2"+
		"\2\u0255e\3\2\2\2\u0256\u0257\7\62\2\2\u0257\u0258\5\u008aF\2\u0258\u0259"+
		"\7F\2\2\u0259g\3\2\2\2\u025a\u025b\7\f\2\2\u025b\u025c\5\u008aF\2\u025c"+
		"\u025d\7F\2\2\u025di\3\2\2\2\u025e\u025f\7\61\2\2\u025f\u0260\5\u008a"+
		"F\2\u0260\u0261\7F\2\2\u0261k\3\2\2\2\u0262\u0263\7\63\2\2\u0263\u0265"+
		"\5\u008aF\2\u0264\u0266\58\35\2\u0265\u0264\3\2\2\2\u0265\u0266\3\2\2"+
		"\2\u0266\u0267\3\2\2\2\u0267\u0268\7F\2\2\u0268m\3\2\2\2\u0269\u026a\7"+
		"\34\2\2\u026a\u026b\5\u008aF\2\u026b\u026c\5\u008aF\2\u026c\u026d\7F\2"+
		"\2\u026do\3\2\2\2\u026e\u026f\7%\2\2\u026f\u0271\7@\2\2\u0270\u0272\5"+
		"\u0088E\2\u0271\u0270\3\2\2\2\u0271\u0272\3\2\2\2\u0272\u0273\3\2\2\2"+
		"\u0273\u0275\7A\2\2\u0274\u0276\5F$\2\u0275\u0274\3\2\2\2\u0275\u0276"+
		"\3\2\2\2\u0276q\3\2\2\2\u0277\u0278\5\u008aF\2\u0278\u0279\7F\2\2\u0279"+
		"s\3\2\2\2\u027a\u027c\5\26\f\2\u027b\u027a\3\2\2\2\u027c\u027f\3\2\2\2"+
		"\u027d\u027b\3\2\2\2\u027d\u027e\3\2\2\2\u027e\u0282\3\2\2\2\u027f\u027d"+
		"\3\2\2\2\u0280\u0283\5v<\2\u0281\u0283\5x=\2\u0282\u0280\3\2\2\2\u0282"+
		"\u0281\3\2\2\2\u0283u\3\2\2\2\u0284\u0287\7\24\2\2\u0285\u0288\7F\2\2"+
		"\u0286\u0288\5F$\2\u0287\u0285\3\2\2\2\u0287\u0286\3\2\2\2\u0288w\3\2"+
		"\2\2\u0289\u028c\7&\2\2\u028a\u028d\7F\2\2\u028b\u028d\5F$\2\u028c\u028a"+
		"\3\2\2\2\u028c\u028b\3\2\2\2\u028dy\3\2\2\2\u028e\u028f\7\6\2\2\u028f"+
		"\u0293\7@\2\2\u0290\u0292\5\26\f\2\u0291\u0290\3\2\2\2\u0292\u0295\3\2"+
		"\2\2\u0293\u0291\3\2\2\2\u0293\u0294\3\2\2\2\u0294\u0296\3\2\2\2\u0295"+
		"\u0293\3\2\2\2\u0296\u0297\58\35\2\u0297\u0298\5\u00a6T\2\u0298\u0299"+
		"\7A\2\2\u0299\u029a\5F$\2\u029a{\3\2\2\2\u029b\u029c\7\22\2\2\u029c\u029d"+
		"\5F$\2\u029d}\3\2\2\2\u029e\u02ab\5\u0082B\2\u029f\u02a1\5\u0080A\2\u02a0"+
		"\u029f\3\2\2\2\u02a0\u02a1\3\2\2\2\u02a1\u02a2\3\2\2\2\u02a2\u02a4\7F"+
		"\2\2\u02a3\u02a5\5\u008aF\2\u02a4\u02a3\3\2\2\2\u02a4\u02a5\3\2\2\2\u02a5"+
		"\u02a6\3\2\2\2\u02a6\u02a8\7F\2\2\u02a7\u02a9\5\u0084C\2\u02a8\u02a7\3"+
		"\2\2\2\u02a8\u02a9\3\2\2\2\u02a9\u02ab\3\2\2\2\u02aa\u029e\3\2\2\2\u02aa"+
		"\u02a0\3\2\2\2\u02ab\177\3\2\2\2\u02ac\u02af\5J&\2\u02ad\u02af\5\u0088"+
		"E\2\u02ae\u02ac\3\2\2\2\u02ae\u02ad\3\2\2\2\u02af\u0081\3\2\2\2\u02b0"+
		"\u02b2\5\26\f\2\u02b1\u02b0\3\2\2\2\u02b2\u02b5\3\2\2\2\u02b3\u02b1\3"+
		"\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b6\3\2\2\2\u02b5\u02b3\3\2\2\2\u02b6"+
		"\u02b7\5*\26\2\u02b7\u02b8\5\u00a6T\2\u02b8\u02b9\7Q\2\2\u02b9\u02ba\5"+
		"\u008aF\2\u02ba\u0083\3\2\2\2\u02bb\u02bc\5\u0088E\2\u02bc\u0085\3\2\2"+
		"\2\u02bd\u02be\7@\2\2\u02be\u02bf\5\u008aF\2\u02bf\u02c0\7A\2\2\u02c0"+
		"\u0087\3\2\2\2\u02c1\u02c6\5\u008aF\2\u02c2\u02c3\7G\2\2\u02c3\u02c5\5"+
		"\u008aF\2\u02c4\u02c2\3\2\2\2\u02c5\u02c8\3\2\2\2\u02c6\u02c4\3\2\2\2"+
		"\u02c6\u02c7\3\2\2\2\u02c7\u0089\3\2\2\2\u02c8\u02c6\3\2\2\2\u02c9\u02ca"+
		"\bF\1\2\u02ca\u02d8\5\u008cG\2\u02cb\u02d8\5\u008eH\2\u02cc\u02cd\7\35"+
		"\2\2\u02cd\u02d8\5\u0090I\2\u02ce\u02cf\7@\2\2\u02cf\u02d0\5*\26\2\u02d0"+
		"\u02d1\7A\2\2\u02d1\u02d2\5\u008aF\23\u02d2\u02d8\3\2\2\2\u02d3\u02d4"+
		"\t\3\2\2\u02d4\u02d8\5\u008aF\21\u02d5\u02d6\t\4\2\2\u02d6\u02d8\5\u008a"+
		"F\20\u02d7\u02c9\3\2\2\2\u02d7\u02cb\3\2\2\2\u02d7\u02cc\3\2\2\2\u02d7"+
		"\u02ce\3\2\2\2\u02d7\u02d3\3\2\2\2\u02d7\u02d5\3\2\2\2\u02d8\u031a\3\2"+
		"\2\2\u02d9\u02da\f\17\2\2\u02da\u02db\t\5\2\2\u02db\u0319\5\u008aF\20"+
		"\u02dc\u02dd\f\16\2\2\u02dd\u02de\t\6\2\2\u02de\u0319\5\u008aF\17\u02df"+
		"\u02e7\f\r\2\2\u02e0\u02e1\7M\2\2\u02e1\u02e8\7M\2\2\u02e2\u02e3\7L\2"+
		"\2\u02e3\u02e4\7L\2\2\u02e4\u02e8\7L\2\2\u02e5\u02e6\7L\2\2\u02e6\u02e8"+
		"\7L\2\2\u02e7\u02e0\3\2\2\2\u02e7\u02e2\3\2\2\2\u02e7\u02e5\3\2\2\2\u02e8"+
		"\u02e9\3\2\2\2\u02e9\u0319\5\u008aF\16\u02ea\u02eb\f\f\2\2\u02eb\u02ec"+
		"\t\7\2\2\u02ec\u0319\5\u008aF\r\u02ed\u02ee\f\n\2\2\u02ee\u02ef\t\b\2"+
		"\2\u02ef\u0319\5\u008aF\13\u02f0\u02f1\f\t\2\2\u02f1\u02f2\7_\2\2\u02f2"+
		"\u0319\5\u008aF\n\u02f3\u02f4\f\b\2\2\u02f4\u02f5\7a\2\2\u02f5\u0319\5"+
		"\u008aF\t\u02f6\u02f7\f\7\2\2\u02f7\u02f8\7`\2\2\u02f8\u0319\5\u008aF"+
		"\b\u02f9\u02fa\f\6\2\2\u02fa\u02fb\7W\2\2\u02fb\u0319\5\u008aF\7\u02fc"+
		"\u02fd\f\5\2\2\u02fd\u02fe\7X\2\2\u02fe\u0319\5\u008aF\6\u02ff\u0300\f"+
		"\4\2\2\u0300\u0301\7P\2\2\u0301\u0302\5\u008aF\2\u0302\u0303\7Q\2\2\u0303"+
		"\u0304\5\u008aF\4\u0304\u0319\3\2\2\2\u0305\u0306\f\3\2\2\u0306\u0307"+
		"\t\t\2\2\u0307\u0319\5\u008aF\3\u0308\u0309\f\27\2\2\u0309\u030c\7H\2"+
		"\2\u030a\u030d\5\u00a6T\2\u030b\u030d\5\u008eH\2\u030c\u030a\3\2\2\2\u030c"+
		"\u030b\3\2\2\2\u030d\u0319\3\2\2\2\u030e\u030f\f\26\2\2\u030f\u0310\7"+
		"D\2\2\u0310\u0311\5\u008aF\2\u0311\u0312\7E\2\2\u0312\u0319\3\2\2\2\u0313"+
		"\u0314\f\22\2\2\u0314\u0319\t\n\2\2\u0315\u0316\f\13\2\2\u0316\u0317\7"+
		"\32\2\2\u0317\u0319\5*\26\2\u0318\u02d9\3\2\2\2\u0318\u02dc\3\2\2\2\u0318"+
		"\u02df\3\2\2\2\u0318\u02ea\3\2\2\2\u0318\u02ed\3\2\2\2\u0318\u02f0\3\2"+
		"\2\2\u0318\u02f3\3\2\2\2\u0318\u02f6\3\2\2\2\u0318\u02f9\3\2\2\2\u0318"+
		"\u02fc\3\2\2\2\u0318\u02ff\3\2\2\2\u0318\u0305\3\2\2\2\u0318\u0308\3\2"+
		"\2\2\u0318\u030e\3\2\2\2\u0318\u0313\3\2\2\2\u0318\u0315\3\2\2\2\u0319"+
		"\u031c\3\2\2\2\u031a\u0318\3\2\2\2\u031a\u031b\3\2\2\2\u031b\u008b\3\2"+
		"\2\2\u031c\u031a\3\2\2\2\u031d\u031e\7@\2\2\u031e\u031f\5\u008aF\2\u031f"+
		"\u0320\7A\2\2\u0320\u032b\3\2\2\2\u0321\u032b\7-\2\2\u0322\u032b\7*\2"+
		"\2\u0323\u032b\5:\36\2\u0324\u0325\5*\26\2\u0325\u0326\7H\2\2\u0326\u0327"+
		"\7\b\2\2\u0327\u032b\3\2\2\2\u0328\u032b\5\u00a6T\2\u0329\u032b\5\u00a4"+
		"S\2\u032a\u031d\3\2\2\2\u032a\u0321\3\2\2\2\u032a\u0322\3\2\2\2\u032a"+
		"\u0323\3\2\2\2\u032a\u0324\3\2\2\2\u032a\u0328\3\2\2\2\u032a\u0329\3\2"+
		"\2\2\u032b\u008d\3\2\2\2\u032c\u032d\5\u00a6T\2\u032d\u032f\7@\2\2\u032e"+
		"\u0330\5\u0088E\2\u032f\u032e\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u0331"+
		"\3\2\2\2\u0331\u0332\7A\2\2\u0332\u0340\3\2\2\2\u0333\u0334\7-\2\2\u0334"+
		"\u0336\7@\2\2\u0335\u0337\5\u0088E\2\u0336\u0335\3\2\2\2\u0336\u0337\3"+
		"\2\2\2\u0337\u0338\3\2\2\2\u0338\u0340\7A\2\2\u0339\u033a\7*\2\2\u033a"+
		"\u033c\7@\2\2\u033b\u033d\5\u0088E\2\u033c\u033b\3\2\2\2\u033c\u033d\3"+
		"\2\2\2\u033d\u033e\3\2\2\2\u033e\u0340\7A\2\2\u033f\u032c\3\2\2\2\u033f"+
		"\u0333\3\2\2\2\u033f\u0339\3\2\2\2\u0340\u008f\3\2\2\2\u0341\u0347\5\u0092"+
		"J\2\u0342\u0348\5\u0096L\2\u0343\u0348\5\u0098M\2\u0344\u0348\5\u009a"+
		"N\2\u0345\u0348\5\u009cO\2\u0346\u0348\5\u00a0Q\2\u0347\u0342\3\2\2\2"+
		"\u0347\u0343\3\2\2\2\u0347\u0344\3\2\2\2\u0347\u0345\3\2\2\2\u0347\u0346"+
		"\3\2\2\2\u0348\u0091\3\2\2\2\u0349\u034e\5\u0094K\2\u034a\u034b\7H\2\2"+
		"\u034b\u034d\5\u0094K\2\u034c\u034a\3\2\2\2\u034d\u0350\3\2\2\2\u034e"+
		"\u034c\3\2\2\2\u034e\u034f\3\2\2\2\u034f\u0093\3\2\2\2\u0350\u034e\3\2"+
		"\2\2\u0351\u0356\5\u00a6T\2\u0352\u0353\7M\2\2\u0353\u0354\5\16\b\2\u0354"+
		"\u0355\7L\2\2\u0355\u0357\3\2\2\2\u0356\u0352\3\2\2\2\u0356\u0357\3\2"+
		"\2\2\u0357\u0095\3\2\2\2\u0358\u0359\7B\2\2\u0359\u035a\7C\2\2\u035a\u0097"+
		"\3\2\2\2\u035b\u035c\5\u00a2R\2\u035c\u0099\3\2\2\2\u035d\u035e\7D\2\2"+
		"\u035e\u035f\5\u008aF\2\u035f\u0360\7E\2\2\u0360\u0367\3\2\2\2\u0361\u0362"+
		"\7D\2\2\u0362\u0364\7E\2\2\u0363\u0365\5(\25\2\u0364\u0363\3\2\2\2\u0364"+
		"\u0365\3\2\2\2\u0365\u0367\3\2\2\2\u0366\u035d\3\2\2\2\u0366\u0361\3\2"+
		"\2\2\u0367\u009b\3\2\2\2\u0368\u0369\7B\2\2\u0369\u036e\5\u009eP\2\u036a"+
		"\u036b\7G\2\2\u036b\u036d\5\u009eP\2\u036c\u036a\3\2\2\2\u036d\u0370\3"+
		"\2\2\2\u036e\u036c\3\2\2\2\u036e\u036f\3\2\2\2\u036f\u0371\3\2\2\2\u0370"+
		"\u036e\3\2\2\2\u0371\u0372\7C\2\2\u0372\u009d\3\2\2\2\u0373\u0374\5\u008a"+
		"F\2\u0374\u0375\7c\2\2\u0375\u0376\5\u008aF\2\u0376\u009f\3\2\2\2\u0377"+
		"\u0378\7B\2\2\u0378\u037d\5\u008aF\2\u0379\u037a\7G\2\2\u037a\u037c\5"+
		"\u008aF\2\u037b\u0379\3\2\2\2\u037c\u037f\3\2\2\2\u037d\u037b\3\2\2\2"+
		"\u037d\u037e\3\2\2\2\u037e\u0380\3\2\2\2\u037f\u037d\3\2\2\2\u0380\u0381"+
		"\7C\2\2\u0381\u00a1\3\2\2\2\u0382\u0384\7@\2\2\u0383\u0385\5\u0088E\2"+
		"\u0384\u0383\3\2\2\2\u0384\u0385\3\2\2\2\u0385\u0386\3\2\2\2\u0386\u0387"+
		"\7A\2\2\u0387\u00a3\3\2\2\2\u0388\u038d\7D\2\2\u0389\u038c\5\u00a4S\2"+
		"\u038a\u038c\n\13\2\2\u038b\u0389\3\2\2\2\u038b\u038a\3\2\2\2\u038c\u038f"+
		"\3\2\2\2\u038d\u038e\3\2\2\2\u038d\u038b\3\2\2\2\u038e\u0390\3\2\2\2\u038f"+
		"\u038d\3\2\2\2\u0390\u0391\7E\2\2\u0391\u00a5\3\2\2\2\u0392\u0393\t\f"+
		"\2\2\u0393\u00a7\3\2\2\2Z\u00ae\u00b5\u00bc\u00c0\u00c6\u00ca\u00d2\u00db"+
		"\u00e2\u00eb\u00f2\u00fb\u0102\u0108\u010c\u0121\u012a\u012f\u0134\u013a"+
		"\u014a\u0152\u0157\u0162\u0168\u0170\u0174\u0176\u017f\u0188\u018d\u0195"+
		"\u019e\u01a4\u01af\u01b9\u01bc\u01c0\u01c5\u01cf\u01d7\u01da\u01dd\u01e5"+
		"\u01f0\u020a\u0211\u021a\u0221\u023a\u023d\u0240\u0244\u0265\u0271\u0275"+
		"\u027d\u0282\u0287\u028c\u0293\u02a0\u02a4\u02a8\u02aa\u02ae\u02b3\u02c6"+
		"\u02d7\u02e7\u030c\u0318\u031a\u032a\u032f\u0336\u033c\u033f\u0347\u034e"+
		"\u0356\u0364\u0366\u036e\u037d\u0384\u038b\u038d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}