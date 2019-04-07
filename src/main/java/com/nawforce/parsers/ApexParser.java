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
		INSTANCEOF=23, INTERFACE=24, LONG=25, NATIVE=26, NEW=27, NULL=28, PACKAGE=29, 
		PRIVATE=30, PROTECTED=31, PUBLIC=32, RETURN=33, SHORT=34, STATIC=35, VIRTUAL=36, 
		SUPER=37, THIS=38, THROW=39, TRANSIENT=40, TRY=41, VOID=42, WHILE=43, 
		GLOBAL=44, WEBSERVICE=45, SELECT=46, INSERT=47, UPSERT=48, UPDATE=49, 
		DELETE=50, UNDELETE=51, MERGE=52, TESTMETHOD=53, OVERRIDE=54, GET=55, 
		SET=56, BLOB=57, DATE=58, DATETIME=59, DECIMAL=60, ID=61, INTEGER=62, 
		OBJECT=63, STRING=64, TIME=65, RUNAS=66, WITH=67, WITHOUT=68, SHARING=69, 
		INHERITED=70, IntegerLiteral=71, NumberLiteral=72, BooleanLiteral=73, 
		StringLiteral=74, NullLiteral=75, LPAREN=76, RPAREN=77, LBRACE=78, RBRACE=79, 
		LBRACK=80, RBRACK=81, SEMI=82, COMMA=83, DOT=84, ASSIGN=85, LE=86, GE=87, 
		GT=88, LT=89, BANG=90, TILDE=91, QUESTION=92, COLON=93, EQUAL=94, TRIPLEEQUAL=95, 
		NOTEQUAL=96, LESSANDGREATER=97, TRIPLENOTEQUAL=98, AND=99, OR=100, INC=101, 
		DEC=102, ADD=103, SUB=104, MUL=105, DIV=106, BITAND=107, BITOR=108, CARET=109, 
		MOD=110, MAP=111, ADD_ASSIGN=112, SUB_ASSIGN=113, MUL_ASSIGN=114, DIV_ASSIGN=115, 
		AND_ASSIGN=116, OR_ASSIGN=117, XOR_ASSIGN=118, MOD_ASSIGN=119, LSHIFT_ASSIGN=120, 
		RSHIFT_ASSIGN=121, URSHIFT_ASSIGN=122, Identifier=123, AT=124, WS=125, 
		DOC_COMMENT=126, COMMENT=127, LINE_COMMENT=128;
	public static final int
		RULE_compilationUnit = 0, RULE_typeDeclaration = 1, RULE_classDeclaration = 2, 
		RULE_enumDeclaration = 3, RULE_enumConstants = 4, RULE_enumConstant = 5, 
		RULE_enumBodyDeclarations = 6, RULE_interfaceDeclaration = 7, RULE_typeList = 8, 
		RULE_classBody = 9, RULE_interfaceBody = 10, RULE_classBodyDeclaration = 11, 
		RULE_modifier = 12, RULE_memberDeclaration = 13, RULE_methodDeclaration = 14, 
		RULE_constructorDeclaration = 15, RULE_fieldDeclaration = 16, RULE_propertyDeclaration = 17, 
		RULE_propertyBodyDeclaration = 18, RULE_interfaceBodyDeclaration = 19, 
		RULE_interfaceMemberDeclaration = 20, RULE_constDeclaration = 21, RULE_constantDeclarator = 22, 
		RULE_interfaceMethodDeclaration = 23, RULE_variableDeclarators = 24, RULE_variableDeclarator = 25, 
		RULE_variableInitializer = 26, RULE_arrayInitializer = 27, RULE_typeRef = 28, 
		RULE_arraySubscripts = 29, RULE_classOrInterfaceType = 30, RULE_primitiveType = 31, 
		RULE_typeArguments = 32, RULE_formalParameters = 33, RULE_formalParameterList = 34, 
		RULE_formalParameter = 35, RULE_qualifiedName = 36, RULE_literal = 37, 
		RULE_annotation = 38, RULE_elementValuePairs = 39, RULE_elementValuePair = 40, 
		RULE_elementValue = 41, RULE_elementValueArrayInitializer = 42, RULE_block = 43, 
		RULE_localVariableDeclarationStatement = 44, RULE_localVariableDeclaration = 45, 
		RULE_statement = 46, RULE_ifStatement = 47, RULE_forStatement = 48, RULE_whileStatement = 49, 
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
			"enumConstants", "enumConstant", "enumBodyDeclarations", "interfaceDeclaration", 
			"typeList", "classBody", "interfaceBody", "classBodyDeclaration", "modifier", 
			"memberDeclaration", "methodDeclaration", "constructorDeclaration", "fieldDeclaration", 
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
			"explicitGenericInvocationSuffix", "arguments", "soqlLiteral", "id"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'abstract'", "'boolean'", "'break'", "'byte'", "'catch'", "'char'", 
			"'class'", "'const'", "'continue'", "'default'", "'do'", "'double'", 
			"'else'", "'enum'", "'extends'", "'final'", "'finally'", "'float'", "'for'", 
			"'if'", "'goto'", "'implements'", "'instanceof'", "'interface'", "'long'", 
			"'native'", "'new'", "'null'", "'package'", "'private'", "'protected'", 
			"'public'", "'return'", "'short'", "'static'", "'virtual'", "'super'", 
			"'this'", "'throw'", "'transient'", "'try'", "'void'", "'while'", "'global'", 
			"'webservice'", "'select'", "'insert'", "'upsert'", "'update'", "'delete'", 
			"'undelete'", "'merge'", "'testmethod'", "'override'", "'get'", "'set'", 
			"'blob'", "'date'", "'datetime'", "'decimal'", "'id'", "'integer'", "'object'", 
			"'string'", "'time'", "'system.runas'", "'with'", "'without'", "'sharing'", 
			"'inherited'", null, null, null, null, null, "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "';'", "','", "'.'", "'='", "'<='", "'>='", "'>'", "'<'", 
			"'!'", "'~'", "'?'", "':'", "'=='", "'==='", "'!='", "'<>'", "'!=='", 
			"'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", 
			"'^'", "'%'", "'=>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", 
			"'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BOOLEAN", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", 
			"CONST", "CONTINUE", "DEFAULT", "DO", "DOUBLE", "ELSE", "ENUM", "EXTENDS", 
			"FINAL", "FINALLY", "FLOAT", "FOR", "IF", "GOTO", "IMPLEMENTS", "INSTANCEOF", 
			"INTERFACE", "LONG", "NATIVE", "NEW", "NULL", "PACKAGE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "SHORT", "STATIC", "VIRTUAL", "SUPER", "THIS", "THROW", 
			"TRANSIENT", "TRY", "VOID", "WHILE", "GLOBAL", "WEBSERVICE", "SELECT", 
			"INSERT", "UPSERT", "UPDATE", "DELETE", "UNDELETE", "MERGE", "TESTMETHOD", 
			"OVERRIDE", "GET", "SET", "BLOB", "DATE", "DATETIME", "DECIMAL", "ID", 
			"INTEGER", "OBJECT", "STRING", "TIME", "RUNAS", "WITH", "WITHOUT", "SHARING", 
			"INHERITED", "IntegerLiteral", "NumberLiteral", "BooleanLiteral", "StringLiteral", 
			"NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "LE", "GE", "GT", "LT", "BANG", "TILDE", 
			"QUESTION", "COLON", "EQUAL", "TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", 
			"TRIPLENOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", 
			"MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
			"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", 
			"AT", "WS", "DOC_COMMENT", "COMMENT", "LINE_COMMENT"
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
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (INHERITED - 67)) | (1L << (AT - 67)))) != 0)) {
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
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (INHERITED - 67)) | (1L << (AT - 67)))) != 0)) {
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
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (INHERITED - 67)) | (1L << (AT - 67)))) != 0)) {
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
		enterRule(_localctx, 6, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(ENUM);
			setState(239);
			id();
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(240);
				match(IMPLEMENTS);
				setState(241);
				typeList();
				}
			}

			setState(244);
			match(LBRACE);
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(245);
				enumConstants();
				}
			}

			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(248);
				match(COMMA);
				}
			}

			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(251);
				enumBodyDeclarations();
				}
			}

			setState(254);
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			enumConstant();
			setState(261);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(257);
					match(COMMA);
					setState(258);
					enumConstant();
					}
					} 
				}
				setState(263);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
			setState(267);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(264);
					modifier();
					}
					} 
				}
				setState(269);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(270);
			id();
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(271);
				arguments();
				}
			}

			setState(275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(274);
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
		enterRule(_localctx, 12, RULE_enumBodyDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(SEMI);
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(278);
				classBodyDeclaration();
				}
				}
				setState(283);
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
		enterRule(_localctx, 14, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(INTERFACE);
			setState(285);
			id();
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(286);
				match(EXTENDS);
				setState(287);
				typeList();
				}
			}

			setState(290);
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
		enterRule(_localctx, 16, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			typeRef();
			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(293);
				match(COMMA);
				setState(294);
				typeRef();
				}
				}
				setState(299);
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
		enterRule(_localctx, 18, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			match(LBRACE);
			setState(304);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(301);
				classBodyDeclaration();
				}
				}
				setState(306);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(307);
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
		enterRule(_localctx, 20, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			match(LBRACE);
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(310);
				interfaceBodyDeclaration();
				}
				}
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(316);
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
		enterRule(_localctx, 22, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(330);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(318);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(320);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(319);
					match(STATIC);
					}
				}

				setState(322);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(323);
						modifier();
						}
						} 
					}
					setState(328);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				setState(329);
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
		enterRule(_localctx, 24, RULE_modifier);
		try {
			setState(351);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(332);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(333);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(334);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(335);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(336);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(337);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(338);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(339);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(340);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(341);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(342);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(343);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(344);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(345);
				match(WITH);
				setState(346);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(347);
				match(WITHOUT);
				setState(348);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(349);
				match(INHERITED);
				setState(350);
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
		enterRule(_localctx, 26, RULE_memberDeclaration);
		try {
			setState(360);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(353);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(355);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(356);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(357);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(358);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(359);
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
		enterRule(_localctx, 28, RULE_methodDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(362);
					modifier();
					}
					} 
				}
				setState(367);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			setState(370);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(368);
				typeRef();
				}
				break;
			case 2:
				{
				setState(369);
				match(VOID);
				}
				break;
			}
			setState(372);
			id();
			setState(373);
			formalParameters();
			setState(376);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(374);
				block();
				}
				break;
			case SEMI:
				{
				setState(375);
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
		enterRule(_localctx, 30, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			qualifiedName();
			setState(379);
			formalParameters();
			setState(380);
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
		enterRule(_localctx, 32, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			typeRef();
			setState(383);
			variableDeclarators();
			setState(384);
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
		enterRule(_localctx, 34, RULE_propertyDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
			typeRef();
			setState(387);
			variableDeclarators();
			setState(388);
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
		enterRule(_localctx, 36, RULE_propertyBodyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(LBRACE);
			setState(391);
			propertyBlock();
			setState(393);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (INHERITED - 67)) | (1L << (AT - 67)))) != 0)) {
				{
				setState(392);
				propertyBlock();
				}
			}

			setState(395);
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
		enterRule(_localctx, 38, RULE_interfaceBodyDeclaration);
		try {
			int _alt;
			setState(405);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DO:
			case DOUBLE:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case IF:
			case GOTO:
			case IMPLEMENTS:
			case INSTANCEOF:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case NULL:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case SHORT:
			case STATIC:
			case VIRTUAL:
			case SUPER:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case VOID:
			case WHILE:
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
			case RUNAS:
			case WITH:
			case WITHOUT:
			case SHARING:
			case INHERITED:
			case Identifier:
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(400);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(397);
						modifier();
						}
						} 
					}
					setState(402);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				}
				setState(403);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(404);
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
		enterRule(_localctx, 40, RULE_interfaceMemberDeclaration);
		try {
			setState(412);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(407);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(408);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(409);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(410);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(411);
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
		enterRule(_localctx, 42, RULE_constDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			typeRef();
			setState(415);
			constantDeclarator();
			setState(420);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(416);
				match(COMMA);
				setState(417);
				constantDeclarator();
				}
				}
				setState(422);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(423);
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
		enterRule(_localctx, 44, RULE_constantDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(425);
			id();
			setState(430);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(426);
				match(LBRACK);
				setState(427);
				match(RBRACK);
				}
				}
				setState(432);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(433);
			match(ASSIGN);
			setState(434);
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
		enterRule(_localctx, 46, RULE_interfaceMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(436);
				typeRef();
				}
				break;
			case 2:
				{
				setState(437);
				match(VOID);
				}
				break;
			}
			setState(440);
			id();
			setState(441);
			formalParameters();
			setState(442);
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
		enterRule(_localctx, 48, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			variableDeclarator();
			setState(449);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(445);
				match(COMMA);
				setState(446);
				variableDeclarator();
				}
				}
				setState(451);
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
		enterRule(_localctx, 50, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(452);
			id();
			setState(455);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(453);
				match(ASSIGN);
				setState(454);
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
		enterRule(_localctx, 52, RULE_variableInitializer);
		try {
			setState(459);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(457);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DO:
			case DOUBLE:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case IF:
			case GOTO:
			case IMPLEMENTS:
			case INSTANCEOF:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case NULL:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case SHORT:
			case STATIC:
			case VIRTUAL:
			case SUPER:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case VOID:
			case WHILE:
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
			case RUNAS:
			case WITH:
			case WITHOUT:
			case SHARING:
			case INHERITED:
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
				setState(458);
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
		enterRule(_localctx, 54, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(461);
			match(LBRACE);
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(462);
				variableInitializer();
				setState(467);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(463);
						match(COMMA);
						setState(464);
						variableInitializer();
						}
						} 
					}
					setState(469);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
				}
				setState(471);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(470);
					match(COMMA);
					}
				}

				}
			}

			setState(475);
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
		enterRule(_localctx, 56, RULE_typeRef);
		try {
			setState(483);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				classOrInterfaceType();
				setState(478);
				arraySubscripts();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(480);
				primitiveType();
				setState(481);
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
		enterRule(_localctx, 58, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(489);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(485);
					match(LBRACK);
					setState(486);
					match(RBRACK);
					}
					} 
				}
				setState(491);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
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
		enterRule(_localctx, 60, RULE_classOrInterfaceType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			id();
			setState(494);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(493);
				typeArguments();
				}
				break;
			}
			setState(503);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(496);
					match(DOT);
					setState(497);
					id();
					setState(499);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
					case 1:
						{
						setState(498);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(505);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
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
		enterRule(_localctx, 62, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(506);
			_la = _input.LA(1);
			if ( !(((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (BOOLEAN - 2)) | (1L << (DOUBLE - 2)) | (1L << (LONG - 2)) | (1L << (BLOB - 2)) | (1L << (DATE - 2)) | (1L << (DATETIME - 2)) | (1L << (DECIMAL - 2)) | (1L << (ID - 2)) | (1L << (INTEGER - 2)) | (1L << (OBJECT - 2)) | (1L << (STRING - 2)) | (1L << (TIME - 2)))) != 0)) ) {
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
		enterRule(_localctx, 64, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(508);
			match(LT);
			setState(509);
			typeList();
			setState(510);
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
		enterRule(_localctx, 66, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(512);
			match(LPAREN);
			setState(514);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(513);
				formalParameterList();
				}
			}

			setState(516);
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
		enterRule(_localctx, 68, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(518);
			formalParameter();
			setState(523);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(519);
				match(COMMA);
				setState(520);
				formalParameter();
				}
				}
				setState(525);
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
		enterRule(_localctx, 70, RULE_formalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(526);
					modifier();
					}
					} 
				}
				setState(531);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			setState(532);
			typeRef();
			setState(533);
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
		enterRule(_localctx, 72, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(535);
			id();
			setState(540);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(536);
				match(DOT);
				setState(537);
				id();
				}
				}
				setState(542);
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
		enterRule(_localctx, 74, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(543);
			_la = _input.LA(1);
			if ( !(((((_la - 28)) & ~0x3f) == 0 && ((1L << (_la - 28)) & ((1L << (NULL - 28)) | (1L << (IntegerLiteral - 28)) | (1L << (NumberLiteral - 28)) | (1L << (BooleanLiteral - 28)) | (1L << (StringLiteral - 28)))) != 0)) ) {
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
		enterRule(_localctx, 76, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(AT);
			setState(546);
			qualifiedName();
			setState(553);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(547);
				match(LPAREN);
				setState(550);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
				case 1:
					{
					setState(548);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(549);
					elementValue();
					}
					break;
				}
				setState(552);
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
		enterRule(_localctx, 78, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			elementValuePair();
			setState(562);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (COMMA - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(557);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(556);
					match(COMMA);
					}
				}

				setState(559);
				elementValuePair();
				}
				}
				setState(564);
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
		enterRule(_localctx, 80, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(565);
			id();
			setState(566);
			match(ASSIGN);
			setState(567);
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
		enterRule(_localctx, 82, RULE_elementValue);
		try {
			setState(572);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DO:
			case DOUBLE:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case IF:
			case GOTO:
			case IMPLEMENTS:
			case INSTANCEOF:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case NULL:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case SHORT:
			case STATIC:
			case VIRTUAL:
			case SUPER:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case VOID:
			case WHILE:
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
			case RUNAS:
			case WITH:
			case WITHOUT:
			case SHARING:
			case INHERITED:
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
				setState(569);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(570);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(571);
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
		enterRule(_localctx, 84, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(574);
			match(LBRACE);
			setState(583);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(575);
				elementValue();
				setState(580);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(576);
						match(COMMA);
						setState(577);
						elementValue();
						}
						} 
					}
					setState(582);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
				}
				}
			}

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
		enterRule(_localctx, 86, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(590);
			match(LBRACE);
			setState(594);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (SEMI - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(591);
				statement();
				}
				}
				setState(596);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(597);
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
		enterRule(_localctx, 88, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(599);
			localVariableDeclaration();
			setState(600);
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
		enterRule(_localctx, 90, RULE_localVariableDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(605);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(602);
					modifier();
					}
					} 
				}
				setState(607);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			}
			setState(608);
			typeRef();
			setState(609);
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
		enterRule(_localctx, 92, RULE_statement);
		try {
			setState(632);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(611);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(612);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(613);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(614);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(615);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(616);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(617);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(618);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(619);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(620);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(621);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(622);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(623);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(624);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(625);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(626);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(627);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(628);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(629);
				emptyStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(630);
				expressionStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(631);
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
		enterRule(_localctx, 94, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(634);
			match(IF);
			setState(635);
			parExpression();
			setState(636);
			statement();
			setState(639);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(637);
				match(ELSE);
				setState(638);
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
		enterRule(_localctx, 96, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(641);
			match(FOR);
			setState(642);
			match(LPAREN);
			setState(643);
			forControl();
			setState(644);
			match(RPAREN);
			setState(645);
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
			setState(647);
			match(WHILE);
			setState(648);
			parExpression();
			setState(649);
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
			setState(651);
			match(DO);
			setState(652);
			statement();
			setState(653);
			match(WHILE);
			setState(654);
			parExpression();
			setState(655);
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
			setState(657);
			match(TRY);
			setState(658);
			block();
			setState(668);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(660); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(659);
						catchClause();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(662); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(665);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
				case 1:
					{
					setState(664);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case FINALLY:
				{
				setState(667);
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
			setState(670);
			match(RETURN);
			setState(672);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(671);
				expression(0);
				}
			}

			setState(674);
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
			setState(676);
			match(THROW);
			setState(677);
			expression(0);
			setState(678);
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
			setState(680);
			match(BREAK);
			setState(682);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(681);
				id();
				}
			}

			setState(684);
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
			setState(686);
			match(CONTINUE);
			setState(688);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(687);
				id();
				}
			}

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
			setState(692);
			match(INSERT);
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
			setState(696);
			match(UPDATE);
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
			setState(700);
			match(DELETE);
			setState(701);
			expression(0);
			setState(702);
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
			setState(704);
			match(UNDELETE);
			setState(705);
			expression(0);
			setState(706);
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
		enterRule(_localctx, 120, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(708);
			match(UPSERT);
			setState(709);
			expression(0);
			setState(711);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(710);
				id();
				}
			}

			setState(713);
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
			setState(715);
			match(MERGE);
			setState(716);
			expression(0);
			setState(717);
			expression(0);
			setState(718);
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
			setState(720);
			match(RUNAS);
			setState(721);
			match(LPAREN);
			setState(723);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(722);
				expressionList();
				}
			}

			setState(725);
			match(RPAREN);
			setState(727);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				{
				setState(726);
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
			setState(734);
			id();
			setState(735);
			match(COLON);
			setState(736);
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
			setState(741);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (INHERITED - 67)) | (1L << (AT - 67)))) != 0)) {
				{
				{
				setState(738);
				modifier();
				}
				}
				setState(743);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(746);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(744);
				getter();
				}
				break;
			case SET:
				{
				setState(745);
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
			setState(748);
			match(GET);
			setState(751);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(749);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(750);
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
			setState(753);
			match(SET);
			setState(756);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(754);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(755);
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
			setState(758);
			match(CATCH);
			setState(759);
			match(LPAREN);
			setState(763);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,74,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(760);
					modifier();
					}
					} 
				}
				setState(765);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,74,_ctx);
			}
			setState(766);
			catchType();
			setState(767);
			id();
			setState(768);
			match(RPAREN);
			setState(769);
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
			setState(771);
			qualifiedName();
			setState(776);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(772);
				match(BITOR);
				setState(773);
				qualifiedName();
				}
				}
				setState(778);
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
			setState(779);
			match(FINALLY);
			setState(780);
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
			setState(794);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(782);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(784);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(783);
					forInit();
					}
				}

				setState(786);
				match(SEMI);
				setState(788);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(787);
					expression(0);
					}
				}

				setState(790);
				match(SEMI);
				setState(792);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(791);
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
			setState(798);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,80,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(796);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(797);
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
			setState(803);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(800);
					modifier();
					}
					} 
				}
				setState(805);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
			}
			setState(806);
			typeRef();
			setState(807);
			id();
			setState(808);
			match(COLON);
			setState(809);
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
			setState(811);
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
			setState(813);
			match(LPAREN);
			setState(814);
			expression(0);
			setState(815);
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
			setState(817);
			expression(0);
			setState(822);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(818);
				match(COMMA);
				setState(819);
				expression(0);
				}
				}
				setState(824);
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
			setState(838);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(826);
				match(NEW);
				setState(827);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(828);
				match(LPAREN);
				setState(829);
				typeRef();
				setState(830);
				match(RPAREN);
				setState(831);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(833);
				_la = _input.LA(1);
				if ( !(((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (INC - 101)) | (1L << (DEC - 101)) | (1L << (ADD - 101)) | (1L << (SUB - 101)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(834);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(835);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(836);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(837);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(934);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(932);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(840);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(841);
						_la = _input.LA(1);
						if ( !(((((_la - 105)) & ~0x3f) == 0 && ((1L << (_la - 105)) & ((1L << (MUL - 105)) | (1L << (DIV - 105)) | (1L << (MOD - 105)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(842);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(843);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(844);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(845);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(846);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(854);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
						case 1:
							{
							setState(847);
							match(LT);
							setState(848);
							match(LT);
							}
							break;
						case 2:
							{
							setState(849);
							match(GT);
							setState(850);
							match(GT);
							setState(851);
							match(GT);
							}
							break;
						case 3:
							{
							setState(852);
							match(GT);
							setState(853);
							match(GT);
							}
							break;
						}
						setState(856);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(857);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(866);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
						case 1:
							{
							setState(858);
							match(LT);
							setState(859);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(860);
							match(GT);
							setState(861);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(862);
							match(LE);
							}
							break;
						case 4:
							{
							setState(863);
							match(GE);
							}
							break;
						case 5:
							{
							setState(864);
							match(GT);
							}
							break;
						case 6:
							{
							setState(865);
							match(LT);
							}
							break;
						}
						setState(868);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(869);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(870);
						_la = _input.LA(1);
						if ( !(((((_la - 94)) & ~0x3f) == 0 && ((1L << (_la - 94)) & ((1L << (EQUAL - 94)) | (1L << (TRIPLEEQUAL - 94)) | (1L << (NOTEQUAL - 94)) | (1L << (LESSANDGREATER - 94)) | (1L << (TRIPLENOTEQUAL - 94)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(871);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(872);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(873);
						match(BITAND);
						setState(874);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(875);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(876);
						match(CARET);
						setState(877);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(878);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(879);
						match(BITOR);
						setState(880);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(881);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(882);
						match(AND);
						setState(883);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(884);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(885);
						match(OR);
						setState(886);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(887);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(888);
						match(QUESTION);
						setState(889);
						expression(0);
						setState(890);
						match(COLON);
						setState(891);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(893);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(894);
						_la = _input.LA(1);
						if ( !(((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (ASSIGN - 85)) | (1L << (ADD_ASSIGN - 85)) | (1L << (SUB_ASSIGN - 85)) | (1L << (MUL_ASSIGN - 85)) | (1L << (DIV_ASSIGN - 85)) | (1L << (AND_ASSIGN - 85)) | (1L << (OR_ASSIGN - 85)) | (1L << (XOR_ASSIGN - 85)) | (1L << (MOD_ASSIGN - 85)) | (1L << (LSHIFT_ASSIGN - 85)) | (1L << (RSHIFT_ASSIGN - 85)) | (1L << (URSHIFT_ASSIGN - 85)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(895);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(896);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(897);
						match(DOT);
						setState(898);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(899);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(900);
						match(DOT);
						setState(901);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(902);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(903);
						match(DOT);
						setState(904);
						match(NEW);
						setState(906);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(905);
							nonWildcardTypeArguments();
							}
						}

						setState(908);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(909);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(910);
						match(DOT);
						setState(911);
						match(SUPER);
						setState(912);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(913);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(914);
						match(DOT);
						setState(915);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(916);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(917);
						match(LBRACK);
						setState(918);
						expression(0);
						setState(919);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(921);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(922);
						match(LPAREN);
						setState(924);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(923);
							expressionList();
							}
						}

						setState(926);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(927);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(928);
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
						setState(929);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(930);
						match(INSTANCEOF);
						setState(931);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(936);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
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
			setState(959);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,91,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(937);
				match(LPAREN);
				setState(938);
				expression(0);
				setState(939);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(941);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(942);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(943);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(944);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(945);
				typeRef();
				setState(946);
				match(DOT);
				setState(947);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(949);
				match(VOID);
				setState(950);
				match(DOT);
				setState(951);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(952);
				nonWildcardTypeArguments();
				setState(956);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
				case 1:
					{
					setState(953);
					explicitGenericInvocationSuffix();
					}
					break;
				case 2:
					{
					setState(954);
					match(THIS);
					setState(955);
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
				setState(958);
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
			setState(972);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(961);
				nonWildcardTypeArguments();
				setState(962);
				createdName();
				setState(963);
				classCreatorRest();
				}
				break;
			case ABSTRACT:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DO:
			case DOUBLE:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case IF:
			case GOTO:
			case IMPLEMENTS:
			case INSTANCEOF:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case NULL:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case SHORT:
			case STATIC:
			case VIRTUAL:
			case SUPER:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case VOID:
			case WHILE:
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
			case RUNAS:
			case WITH:
			case WITHOUT:
			case SHARING:
			case INHERITED:
			case Identifier:
				_localctx = new Alt2CreatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(965);
				createdName();
				setState(970);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
				case 1:
					{
					setState(966);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(967);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(968);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(969);
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
		enterRule(_localctx, 162, RULE_createdName);
		int _la;
		try {
			setState(983);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(974);
				idCreatedNamePair();
				setState(979);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(975);
					match(DOT);
					setState(976);
					idCreatedNamePair();
					}
					}
					setState(981);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(982);
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
		enterRule(_localctx, 164, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(985);
			id();
			setState(987);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(986);
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
			setState(989);
			id();
			setState(991);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(990);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(993);
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
			setState(995);
			match(LBRACK);
			setState(1013);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(996);
				match(RBRACK);
				setState(997);
				arraySubscripts();
				setState(998);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DEFAULT:
			case DO:
			case DOUBLE:
			case ELSE:
			case ENUM:
			case EXTENDS:
			case FINAL:
			case FINALLY:
			case FLOAT:
			case FOR:
			case IF:
			case GOTO:
			case IMPLEMENTS:
			case INSTANCEOF:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case NEW:
			case NULL:
			case PACKAGE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case RETURN:
			case SHORT:
			case STATIC:
			case VIRTUAL:
			case SUPER:
			case THIS:
			case THROW:
			case TRANSIENT:
			case TRY:
			case VOID:
			case WHILE:
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
			case RUNAS:
			case WITH:
			case WITHOUT:
			case SHARING:
			case INHERITED:
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
				setState(1000);
				expression(0);
				setState(1001);
				match(RBRACK);
				setState(1008);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,98,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1002);
						match(LBRACK);
						setState(1003);
						expression(0);
						setState(1004);
						match(RBRACK);
						}
						} 
					}
					setState(1010);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,98,_ctx);
				}
				setState(1011);
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
			setState(1015);
			match(LBRACE);
			setState(1016);
			mapCreatorRestPair();
			setState(1021);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1017);
				match(COMMA);
				setState(1018);
				mapCreatorRestPair();
				}
				}
				setState(1023);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1024);
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
			setState(1026);
			idOrExpression();
			setState(1027);
			match(MAP);
			setState(1028);
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
			setState(1030);
			match(LBRACE);
			setState(1031);
			literalOrExpression();
			setState(1036);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1032);
				match(COMMA);
				{
				setState(1033);
				literalOrExpression();
				}
				}
				}
				setState(1038);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1039);
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
			setState(1043);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1041);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1042);
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
			setState(1047);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1045);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1046);
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
			setState(1055);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1049);
				arguments();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1050);
				match(LBRACE);
				setState(1052);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1051);
					expressionList();
					}
				}

				setState(1054);
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
			setState(1057);
			nonWildcardTypeArguments();
			setState(1058);
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
			setState(1060);
			match(LT);
			setState(1061);
			typeList();
			setState(1062);
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
			setState(1067);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1064);
				match(LT);
				setState(1065);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1066);
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
			setState(1072);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1069);
				match(LT);
				setState(1070);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1071);
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
			setState(1080);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1074);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1075);
				match(DOT);
				setState(1076);
				id();
				setState(1078);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
				case 1:
					{
					setState(1077);
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
			setState(1087);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1082);
				match(SUPER);
				setState(1083);
				superSuffix();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1084);
				id();
				setState(1085);
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
			setState(1089);
			match(LPAREN);
			setState(1091);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1090);
				expressionList();
				}
			}

			setState(1093);
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
			setState(1095);
			match(LBRACK);
			setState(1100);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,113,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1098);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
					case 1:
						{
						setState(1096);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1097);
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
				setState(1102);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,113,_ctx);
			}
			setState(1103);
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
		public TerminalNode BLOB() { return getToken(ApexParser.BLOB, 0); }
		public TerminalNode BOOLEAN() { return getToken(ApexParser.BOOLEAN, 0); }
		public TerminalNode BREAK() { return getToken(ApexParser.BREAK, 0); }
		public TerminalNode BYTE() { return getToken(ApexParser.BYTE, 0); }
		public TerminalNode CATCH() { return getToken(ApexParser.CATCH, 0); }
		public TerminalNode CHAR() { return getToken(ApexParser.CHAR, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public TerminalNode CONST() { return getToken(ApexParser.CONST, 0); }
		public TerminalNode CONTINUE() { return getToken(ApexParser.CONTINUE, 0); }
		public TerminalNode DATE() { return getToken(ApexParser.DATE, 0); }
		public TerminalNode DATETIME() { return getToken(ApexParser.DATETIME, 0); }
		public TerminalNode DECIMAL() { return getToken(ApexParser.DECIMAL, 0); }
		public TerminalNode DEFAULT() { return getToken(ApexParser.DEFAULT, 0); }
		public TerminalNode DELETE() { return getToken(ApexParser.DELETE, 0); }
		public TerminalNode DO() { return getToken(ApexParser.DO, 0); }
		public TerminalNode DOUBLE() { return getToken(ApexParser.DOUBLE, 0); }
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
		public TerminalNode ID() { return getToken(ApexParser.ID, 0); }
		public TerminalNode IF() { return getToken(ApexParser.IF, 0); }
		public TerminalNode IMPLEMENTS() { return getToken(ApexParser.IMPLEMENTS, 0); }
		public TerminalNode INHERITED() { return getToken(ApexParser.INHERITED, 0); }
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TerminalNode INTEGER() { return getToken(ApexParser.INTEGER, 0); }
		public TerminalNode INTERFACE() { return getToken(ApexParser.INTERFACE, 0); }
		public TerminalNode LONG() { return getToken(ApexParser.LONG, 0); }
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public TerminalNode NATIVE() { return getToken(ApexParser.NATIVE, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public TerminalNode OBJECT() { return getToken(ApexParser.OBJECT, 0); }
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
		public TerminalNode STRING() { return getToken(ApexParser.STRING, 0); }
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public TerminalNode THROW() { return getToken(ApexParser.THROW, 0); }
		public TerminalNode TIME() { return getToken(ApexParser.TIME, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public TerminalNode TRY() { return getToken(ApexParser.TRY, 0); }
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public TerminalNode UPSERT() { return getToken(ApexParser.UPSERT, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode VOID() { return getToken(ApexParser.VOID, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
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
			setState(1105);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DEFAULT) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << IF) | (1L << GOTO) | (1L << IMPLEMENTS) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SHORT) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SUPER) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRY) | (1L << VOID) | (1L << WHILE) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER) | (1L << OBJECT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (RUNAS - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SHARING - 64)) | (1L << (INHERITED - 64)) | (1L << (Identifier - 64)))) != 0)) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0082\u0456\4\2\t"+
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
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\3\2\3\2\3\2\3\3\7\3\u00cf\n\3\f\3\16"+
		"\3\u00d2\13\3\3\3\3\3\7\3\u00d6\n\3\f\3\16\3\u00d9\13\3\3\3\3\3\7\3\u00dd"+
		"\n\3\f\3\16\3\u00e0\13\3\3\3\5\3\u00e3\n\3\3\4\3\4\3\4\3\4\5\4\u00e9\n"+
		"\4\3\4\3\4\5\4\u00ed\n\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\u00f5\n\5\3\5\3\5"+
		"\5\5\u00f9\n\5\3\5\5\5\u00fc\n\5\3\5\5\5\u00ff\n\5\3\5\3\5\3\6\3\6\3\6"+
		"\7\6\u0106\n\6\f\6\16\6\u0109\13\6\3\7\7\7\u010c\n\7\f\7\16\7\u010f\13"+
		"\7\3\7\3\7\5\7\u0113\n\7\3\7\5\7\u0116\n\7\3\b\3\b\7\b\u011a\n\b\f\b\16"+
		"\b\u011d\13\b\3\t\3\t\3\t\3\t\5\t\u0123\n\t\3\t\3\t\3\n\3\n\3\n\7\n\u012a"+
		"\n\n\f\n\16\n\u012d\13\n\3\13\3\13\7\13\u0131\n\13\f\13\16\13\u0134\13"+
		"\13\3\13\3\13\3\f\3\f\7\f\u013a\n\f\f\f\16\f\u013d\13\f\3\f\3\f\3\r\3"+
		"\r\5\r\u0143\n\r\3\r\3\r\7\r\u0147\n\r\f\r\16\r\u014a\13\r\3\r\5\r\u014d"+
		"\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0162\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\5\17\u016b\n\17\3\20\7\20\u016e\n\20\f\20\16\20\u0171\13\20"+
		"\3\20\3\20\5\20\u0175\n\20\3\20\3\20\3\20\3\20\5\20\u017b\n\20\3\21\3"+
		"\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\5"+
		"\24\u018c\n\24\3\24\3\24\3\25\7\25\u0191\n\25\f\25\16\25\u0194\13\25\3"+
		"\25\3\25\5\25\u0198\n\25\3\26\3\26\3\26\3\26\3\26\5\26\u019f\n\26\3\27"+
		"\3\27\3\27\3\27\7\27\u01a5\n\27\f\27\16\27\u01a8\13\27\3\27\3\27\3\30"+
		"\3\30\3\30\7\30\u01af\n\30\f\30\16\30\u01b2\13\30\3\30\3\30\3\30\3\31"+
		"\3\31\5\31\u01b9\n\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\7\32\u01c2\n"+
		"\32\f\32\16\32\u01c5\13\32\3\33\3\33\3\33\5\33\u01ca\n\33\3\34\3\34\5"+
		"\34\u01ce\n\34\3\35\3\35\3\35\3\35\7\35\u01d4\n\35\f\35\16\35\u01d7\13"+
		"\35\3\35\5\35\u01da\n\35\5\35\u01dc\n\35\3\35\3\35\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\5\36\u01e6\n\36\3\37\3\37\7\37\u01ea\n\37\f\37\16\37\u01ed"+
		"\13\37\3 \3 \5 \u01f1\n \3 \3 \3 \5 \u01f6\n \7 \u01f8\n \f \16 \u01fb"+
		"\13 \3!\3!\3\"\3\"\3\"\3\"\3#\3#\5#\u0205\n#\3#\3#\3$\3$\3$\7$\u020c\n"+
		"$\f$\16$\u020f\13$\3%\7%\u0212\n%\f%\16%\u0215\13%\3%\3%\3%\3&\3&\3&\7"+
		"&\u021d\n&\f&\16&\u0220\13&\3\'\3\'\3(\3(\3(\3(\3(\5(\u0229\n(\3(\5(\u022c"+
		"\n(\3)\3)\5)\u0230\n)\3)\7)\u0233\n)\f)\16)\u0236\13)\3*\3*\3*\3*\3+\3"+
		"+\3+\5+\u023f\n+\3,\3,\3,\3,\7,\u0245\n,\f,\16,\u0248\13,\5,\u024a\n,"+
		"\3,\5,\u024d\n,\3,\3,\3-\3-\7-\u0253\n-\f-\16-\u0256\13-\3-\3-\3.\3.\3"+
		".\3/\7/\u025e\n/\f/\16/\u0261\13/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\5\60\u027b\n\60\3\61\3\61\3\61\3\61\3\61\5\61\u0282\n\61\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\65\3\65\3\65\6\65\u0297\n\65\r\65\16\65\u0298\3\65\5\65\u029c"+
		"\n\65\3\65\5\65\u029f\n\65\3\66\3\66\5\66\u02a3\n\66\3\66\3\66\3\67\3"+
		"\67\3\67\3\67\38\38\58\u02ad\n8\38\38\39\39\59\u02b3\n9\39\39\3:\3:\3"+
		":\3:\3;\3;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3>\5>\u02ca\n>\3>\3>\3"+
		"?\3?\3?\3?\3?\3@\3@\3@\5@\u02d6\n@\3@\3@\5@\u02da\n@\3A\3A\3B\3B\3B\3"+
		"C\3C\3C\3C\3D\7D\u02e6\nD\fD\16D\u02e9\13D\3D\3D\5D\u02ed\nD\3E\3E\3E"+
		"\5E\u02f2\nE\3F\3F\3F\5F\u02f7\nF\3G\3G\3G\7G\u02fc\nG\fG\16G\u02ff\13"+
		"G\3G\3G\3G\3G\3G\3H\3H\3H\7H\u0309\nH\fH\16H\u030c\13H\3I\3I\3I\3J\3J"+
		"\5J\u0313\nJ\3J\3J\5J\u0317\nJ\3J\3J\5J\u031b\nJ\5J\u031d\nJ\3K\3K\5K"+
		"\u0321\nK\3L\7L\u0324\nL\fL\16L\u0327\13L\3L\3L\3L\3L\3L\3M\3M\3N\3N\3"+
		"N\3N\3O\3O\3O\7O\u0337\nO\fO\16O\u033a\13O\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\5P\u0349\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\5P"+
		"\u0359\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\5P\u0365\nP\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\3P\3P\3P\5P\u038d\nP\3P\3P\3P\3P\3P\3P\3P\3P\3P\3P"+
		"\3P\3P\3P\3P\3P\3P\5P\u039f\nP\3P\3P\3P\3P\3P\3P\7P\u03a7\nP\fP\16P\u03aa"+
		"\13P\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\5Q\u03bf"+
		"\nQ\3Q\5Q\u03c2\nQ\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u03cd\nR\5R\u03cf\nR"+
		"\3S\3S\3S\7S\u03d4\nS\fS\16S\u03d7\13S\3S\5S\u03da\nS\3T\3T\5T\u03de\n"+
		"T\3U\3U\5U\u03e2\nU\3U\3U\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\7V\u03f1\n"+
		"V\fV\16V\u03f4\13V\3V\3V\5V\u03f8\nV\3W\3W\3W\3W\7W\u03fe\nW\fW\16W\u0401"+
		"\13W\3W\3W\3X\3X\3X\3X\3Y\3Y\3Y\3Y\7Y\u040d\nY\fY\16Y\u0410\13Y\3Y\3Y"+
		"\3Z\3Z\5Z\u0416\nZ\3[\3[\5[\u041a\n[\3\\\3\\\3\\\5\\\u041f\n\\\3\\\5\\"+
		"\u0422\n\\\3]\3]\3]\3^\3^\3^\3^\3_\3_\3_\5_\u042e\n_\3`\3`\3`\5`\u0433"+
		"\n`\3a\3a\3a\3a\5a\u0439\na\5a\u043b\na\3b\3b\3b\3b\3b\5b\u0442\nb\3c"+
		"\3c\5c\u0446\nc\3c\3c\3d\3d\3d\7d\u044d\nd\fd\16d\u0450\13d\3d\3d\3e\3"+
		"e\3e\3\u044e\3\u009ef\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,."+
		"\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086"+
		"\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e"+
		"\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6"+
		"\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\2\r\6\2\4\4\16"+
		"\16\33\33;C\4\2\36\36IL\3\2gj\3\2\\]\4\2klpp\3\2ij\3\2`d\4\2WWr|\3\2g"+
		"h\3\2SS\4\2\3H}}\2\u04b4\2\u00ca\3\2\2\2\4\u00e2\3\2\2\2\6\u00e4\3\2\2"+
		"\2\b\u00f0\3\2\2\2\n\u0102\3\2\2\2\f\u010d\3\2\2\2\16\u0117\3\2\2\2\20"+
		"\u011e\3\2\2\2\22\u0126\3\2\2\2\24\u012e\3\2\2\2\26\u0137\3\2\2\2\30\u014c"+
		"\3\2\2\2\32\u0161\3\2\2\2\34\u016a\3\2\2\2\36\u016f\3\2\2\2 \u017c\3\2"+
		"\2\2\"\u0180\3\2\2\2$\u0184\3\2\2\2&\u0188\3\2\2\2(\u0197\3\2\2\2*\u019e"+
		"\3\2\2\2,\u01a0\3\2\2\2.\u01ab\3\2\2\2\60\u01b8\3\2\2\2\62\u01be\3\2\2"+
		"\2\64\u01c6\3\2\2\2\66\u01cd\3\2\2\28\u01cf\3\2\2\2:\u01e5\3\2\2\2<\u01eb"+
		"\3\2\2\2>\u01ee\3\2\2\2@\u01fc\3\2\2\2B\u01fe\3\2\2\2D\u0202\3\2\2\2F"+
		"\u0208\3\2\2\2H\u0213\3\2\2\2J\u0219\3\2\2\2L\u0221\3\2\2\2N\u0223\3\2"+
		"\2\2P\u022d\3\2\2\2R\u0237\3\2\2\2T\u023e\3\2\2\2V\u0240\3\2\2\2X\u0250"+
		"\3\2\2\2Z\u0259\3\2\2\2\\\u025f\3\2\2\2^\u027a\3\2\2\2`\u027c\3\2\2\2"+
		"b\u0283\3\2\2\2d\u0289\3\2\2\2f\u028d\3\2\2\2h\u0293\3\2\2\2j\u02a0\3"+
		"\2\2\2l\u02a6\3\2\2\2n\u02aa\3\2\2\2p\u02b0\3\2\2\2r\u02b6\3\2\2\2t\u02ba"+
		"\3\2\2\2v\u02be\3\2\2\2x\u02c2\3\2\2\2z\u02c6\3\2\2\2|\u02cd\3\2\2\2~"+
		"\u02d2\3\2\2\2\u0080\u02db\3\2\2\2\u0082\u02dd\3\2\2\2\u0084\u02e0\3\2"+
		"\2\2\u0086\u02e7\3\2\2\2\u0088\u02ee\3\2\2\2\u008a\u02f3\3\2\2\2\u008c"+
		"\u02f8\3\2\2\2\u008e\u0305\3\2\2\2\u0090\u030d\3\2\2\2\u0092\u031c\3\2"+
		"\2\2\u0094\u0320\3\2\2\2\u0096\u0325\3\2\2\2\u0098\u032d\3\2\2\2\u009a"+
		"\u032f\3\2\2\2\u009c\u0333\3\2\2\2\u009e\u0348\3\2\2\2\u00a0\u03c1\3\2"+
		"\2\2\u00a2\u03ce\3\2\2\2\u00a4\u03d9\3\2\2\2\u00a6\u03db\3\2\2\2\u00a8"+
		"\u03df\3\2\2\2\u00aa\u03e5\3\2\2\2\u00ac\u03f9\3\2\2\2\u00ae\u0404\3\2"+
		"\2\2\u00b0\u0408\3\2\2\2\u00b2\u0415\3\2\2\2\u00b4\u0419\3\2\2\2\u00b6"+
		"\u0421\3\2\2\2\u00b8\u0423\3\2\2\2\u00ba\u0426\3\2\2\2\u00bc\u042d\3\2"+
		"\2\2\u00be\u0432\3\2\2\2\u00c0\u043a\3\2\2\2\u00c2\u0441\3\2\2\2\u00c4"+
		"\u0443\3\2\2\2\u00c6\u0449\3\2\2\2\u00c8\u0453\3\2\2\2\u00ca\u00cb\5\4"+
		"\3\2\u00cb\u00cc\7\2\2\3\u00cc\3\3\2\2\2\u00cd\u00cf\5\32\16\2\u00ce\u00cd"+
		"\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3\u00e3\5\6\4\2\u00d4\u00d6\5\32"+
		"\16\2\u00d5\u00d4\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00da\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00e3\5\b"+
		"\5\2\u00db\u00dd\5\32\16\2\u00dc\u00db\3\2\2\2\u00dd\u00e0\3\2\2\2\u00de"+
		"\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e1\3\2\2\2\u00e0\u00de\3\2"+
		"\2\2\u00e1\u00e3\5\20\t\2\u00e2\u00d0\3\2\2\2\u00e2\u00d7\3\2\2\2\u00e2"+
		"\u00de\3\2\2\2\u00e3\5\3\2\2\2\u00e4\u00e5\7\t\2\2\u00e5\u00e8\5\u00c8"+
		"e\2\u00e6\u00e7\7\21\2\2\u00e7\u00e9\5:\36\2\u00e8\u00e6\3\2\2\2\u00e8"+
		"\u00e9\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00eb\7\30\2\2\u00eb\u00ed\5"+
		"\22\n\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee"+
		"\u00ef\5\24\13\2\u00ef\7\3\2\2\2\u00f0\u00f1\7\20\2\2\u00f1\u00f4\5\u00c8"+
		"e\2\u00f2\u00f3\7\30\2\2\u00f3\u00f5\5\22\n\2\u00f4\u00f2\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8\7P\2\2\u00f7\u00f9\5\n"+
		"\6\2\u00f8\u00f7\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2\2\2\u00fa"+
		"\u00fc\7U\2\2\u00fb\u00fa\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2"+
		"\2\2\u00fd\u00ff\5\16\b\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff"+
		"\u0100\3\2\2\2\u0100\u0101\7Q\2\2\u0101\t\3\2\2\2\u0102\u0107\5\f\7\2"+
		"\u0103\u0104\7U\2\2\u0104\u0106\5\f\7\2\u0105\u0103\3\2\2\2\u0106\u0109"+
		"\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\13\3\2\2\2\u0109"+
		"\u0107\3\2\2\2\u010a\u010c\5\32\16\2\u010b\u010a\3\2\2\2\u010c\u010f\3"+
		"\2\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0110\3\2\2\2\u010f"+
		"\u010d\3\2\2\2\u0110\u0112\5\u00c8e\2\u0111\u0113\5\u00c4c\2\u0112\u0111"+
		"\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0115\3\2\2\2\u0114\u0116\5\24\13\2"+
		"\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116\r\3\2\2\2\u0117\u011b\7"+
		"T\2\2\u0118\u011a\5\30\r\2\u0119\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c\17\3\2\2\2\u011d\u011b\3\2\2"+
		"\2\u011e\u011f\7\32\2\2\u011f\u0122\5\u00c8e\2\u0120\u0121\7\21\2\2\u0121"+
		"\u0123\5\22\n\2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0124\3"+
		"\2\2\2\u0124\u0125\5\26\f\2\u0125\21\3\2\2\2\u0126\u012b\5:\36\2\u0127"+
		"\u0128\7U\2\2\u0128\u012a\5:\36\2\u0129\u0127\3\2\2\2\u012a\u012d\3\2"+
		"\2\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\23\3\2\2\2\u012d\u012b"+
		"\3\2\2\2\u012e\u0132\7P\2\2\u012f\u0131\5\30\r\2\u0130\u012f\3\2\2\2\u0131"+
		"\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133\u0135\3\2"+
		"\2\2\u0134\u0132\3\2\2\2\u0135\u0136\7Q\2\2\u0136\25\3\2\2\2\u0137\u013b"+
		"\7P\2\2\u0138\u013a\5(\25\2\u0139\u0138\3\2\2\2\u013a\u013d\3\2\2\2\u013b"+
		"\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013e\3\2\2\2\u013d\u013b\3\2"+
		"\2\2\u013e\u013f\7Q\2\2\u013f\27\3\2\2\2\u0140\u014d\7T\2\2\u0141\u0143"+
		"\7%\2\2\u0142\u0141\3\2\2\2\u0142\u0143\3\2\2\2\u0143\u0144\3\2\2\2\u0144"+
		"\u014d\5X-\2\u0145\u0147\5\32\16\2\u0146\u0145\3\2\2\2\u0147\u014a\3\2"+
		"\2\2\u0148\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014b\3\2\2\2\u014a"+
		"\u0148\3\2\2\2\u014b\u014d\5\34\17\2\u014c\u0140\3\2\2\2\u014c\u0142\3"+
		"\2\2\2\u014c\u0148\3\2\2\2\u014d\31\3\2\2\2\u014e\u0162\5N(\2\u014f\u0162"+
		"\7.\2\2\u0150\u0162\7\"\2\2\u0151\u0162\7!\2\2\u0152\u0162\7 \2\2\u0153"+
		"\u0162\7*\2\2\u0154\u0162\7%\2\2\u0155\u0162\7\3\2\2\u0156\u0162\7\22"+
		"\2\2\u0157\u0162\7/\2\2\u0158\u0162\78\2\2\u0159\u0162\7&\2\2\u015a\u0162"+
		"\7\67\2\2\u015b\u015c\7E\2\2\u015c\u0162\7G\2\2\u015d\u015e\7F\2\2\u015e"+
		"\u0162\7G\2\2\u015f\u0160\7H\2\2\u0160\u0162\7G\2\2\u0161\u014e\3\2\2"+
		"\2\u0161\u014f\3\2\2\2\u0161\u0150\3\2\2\2\u0161\u0151\3\2\2\2\u0161\u0152"+
		"\3\2\2\2\u0161\u0153\3\2\2\2\u0161\u0154\3\2\2\2\u0161\u0155\3\2\2\2\u0161"+
		"\u0156\3\2\2\2\u0161\u0157\3\2\2\2\u0161\u0158\3\2\2\2\u0161\u0159\3\2"+
		"\2\2\u0161\u015a\3\2\2\2\u0161\u015b\3\2\2\2\u0161\u015d\3\2\2\2\u0161"+
		"\u015f\3\2\2\2\u0162\33\3\2\2\2\u0163\u016b\5\36\20\2\u0164\u016b\5\""+
		"\22\2\u0165\u016b\5 \21\2\u0166\u016b\5\20\t\2\u0167\u016b\5\6\4\2\u0168"+
		"\u016b\5\b\5\2\u0169\u016b\5$\23\2\u016a\u0163\3\2\2\2\u016a\u0164\3\2"+
		"\2\2\u016a\u0165\3\2\2\2\u016a\u0166\3\2\2\2\u016a\u0167\3\2\2\2\u016a"+
		"\u0168\3\2\2\2\u016a\u0169\3\2\2\2\u016b\35\3\2\2\2\u016c\u016e\5\32\16"+
		"\2\u016d\u016c\3\2\2\2\u016e\u0171\3\2\2\2\u016f\u016d\3\2\2\2\u016f\u0170"+
		"\3\2\2\2\u0170\u0174\3\2\2\2\u0171\u016f\3\2\2\2\u0172\u0175\5:\36\2\u0173"+
		"\u0175\7,\2\2\u0174\u0172\3\2\2\2\u0174\u0173\3\2\2\2\u0175\u0176\3\2"+
		"\2\2\u0176\u0177\5\u00c8e\2\u0177\u017a\5D#\2\u0178\u017b\5X-\2\u0179"+
		"\u017b\7T\2\2\u017a\u0178\3\2\2\2\u017a\u0179\3\2\2\2\u017b\37\3\2\2\2"+
		"\u017c\u017d\5J&\2\u017d\u017e\5D#\2\u017e\u017f\5X-\2\u017f!\3\2\2\2"+
		"\u0180\u0181\5:\36\2\u0181\u0182\5\62\32\2\u0182\u0183\7T\2\2\u0183#\3"+
		"\2\2\2\u0184\u0185\5:\36\2\u0185\u0186\5\62\32\2\u0186\u0187\5&\24\2\u0187"+
		"%\3\2\2\2\u0188\u0189\7P\2\2\u0189\u018b\5\u0086D\2\u018a\u018c\5\u0086"+
		"D\2\u018b\u018a\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018d\3\2\2\2\u018d"+
		"\u018e\7Q\2\2\u018e\'\3\2\2\2\u018f\u0191\5\32\16\2\u0190\u018f\3\2\2"+
		"\2\u0191\u0194\3\2\2\2\u0192\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u0195"+
		"\3\2\2\2\u0194\u0192\3\2\2\2\u0195\u0198\5*\26\2\u0196\u0198\7T\2\2\u0197"+
		"\u0192\3\2\2\2\u0197\u0196\3\2\2\2\u0198)\3\2\2\2\u0199\u019f\5,\27\2"+
		"\u019a\u019f\5\60\31\2\u019b\u019f\5\20\t\2\u019c\u019f\5\6\4\2\u019d"+
		"\u019f\5\b\5\2\u019e\u0199\3\2\2\2\u019e\u019a\3\2\2\2\u019e\u019b\3\2"+
		"\2\2\u019e\u019c\3\2\2\2\u019e\u019d\3\2\2\2\u019f+\3\2\2\2\u01a0\u01a1"+
		"\5:\36\2\u01a1\u01a6\5.\30\2\u01a2\u01a3\7U\2\2\u01a3\u01a5\5.\30\2\u01a4"+
		"\u01a2\3\2\2\2\u01a5\u01a8\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a7\3\2"+
		"\2\2\u01a7\u01a9\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01aa\7T\2\2\u01aa"+
		"-\3\2\2\2\u01ab\u01b0\5\u00c8e\2\u01ac\u01ad\7R\2\2\u01ad\u01af\7S\2\2"+
		"\u01ae\u01ac\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b0\u01b1"+
		"\3\2\2\2\u01b1\u01b3\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01b4\7W\2\2\u01b4"+
		"\u01b5\5\66\34\2\u01b5/\3\2\2\2\u01b6\u01b9\5:\36\2\u01b7\u01b9\7,\2\2"+
		"\u01b8\u01b6\3\2\2\2\u01b8\u01b7\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb"+
		"\5\u00c8e\2\u01bb\u01bc\5D#\2\u01bc\u01bd\7T\2\2\u01bd\61\3\2\2\2\u01be"+
		"\u01c3\5\64\33\2\u01bf\u01c0\7U\2\2\u01c0\u01c2\5\64\33\2\u01c1\u01bf"+
		"\3\2\2\2\u01c2\u01c5\3\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4"+
		"\63\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c6\u01c9\5\u00c8e\2\u01c7\u01c8\7W"+
		"\2\2\u01c8\u01ca\5\66\34\2\u01c9\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca"+
		"\65\3\2\2\2\u01cb\u01ce\58\35\2\u01cc\u01ce\5\u009eP\2\u01cd\u01cb\3\2"+
		"\2\2\u01cd\u01cc\3\2\2\2\u01ce\67\3\2\2\2\u01cf\u01db\7P\2\2\u01d0\u01d5"+
		"\5\66\34\2\u01d1\u01d2\7U\2\2\u01d2\u01d4\5\66\34\2\u01d3\u01d1\3\2\2"+
		"\2\u01d4\u01d7\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d9"+
		"\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d8\u01da\7U\2\2\u01d9\u01d8\3\2\2\2\u01d9"+
		"\u01da\3\2\2\2\u01da\u01dc\3\2\2\2\u01db\u01d0\3\2\2\2\u01db\u01dc\3\2"+
		"\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\7Q\2\2\u01de9\3\2\2\2\u01df\u01e0"+
		"\5> \2\u01e0\u01e1\5<\37\2\u01e1\u01e6\3\2\2\2\u01e2\u01e3\5@!\2\u01e3"+
		"\u01e4\5<\37\2\u01e4\u01e6\3\2\2\2\u01e5\u01df\3\2\2\2\u01e5\u01e2\3\2"+
		"\2\2\u01e6;\3\2\2\2\u01e7\u01e8\7R\2\2\u01e8\u01ea\7S\2\2\u01e9\u01e7"+
		"\3\2\2\2\u01ea\u01ed\3\2\2\2\u01eb\u01e9\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec"+
		"=\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ee\u01f0\5\u00c8e\2\u01ef\u01f1\5B\""+
		"\2\u01f0\u01ef\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f9\3\2\2\2\u01f2\u01f3"+
		"\7V\2\2\u01f3\u01f5\5\u00c8e\2\u01f4\u01f6\5B\"\2\u01f5\u01f4\3\2\2\2"+
		"\u01f5\u01f6\3\2\2\2\u01f6\u01f8\3\2\2\2\u01f7\u01f2\3\2\2\2\u01f8\u01fb"+
		"\3\2\2\2\u01f9\u01f7\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa?\3\2\2\2\u01fb"+
		"\u01f9\3\2\2\2\u01fc\u01fd\t\2\2\2\u01fdA\3\2\2\2\u01fe\u01ff\7[\2\2\u01ff"+
		"\u0200\5\22\n\2\u0200\u0201\7Z\2\2\u0201C\3\2\2\2\u0202\u0204\7N\2\2\u0203"+
		"\u0205\5F$\2\u0204\u0203\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0206\3\2\2"+
		"\2\u0206\u0207\7O\2\2\u0207E\3\2\2\2\u0208\u020d\5H%\2\u0209\u020a\7U"+
		"\2\2\u020a\u020c\5H%\2\u020b\u0209\3\2\2\2\u020c\u020f\3\2\2\2\u020d\u020b"+
		"\3\2\2\2\u020d\u020e\3\2\2\2\u020eG\3\2\2\2\u020f\u020d\3\2\2\2\u0210"+
		"\u0212\5\32\16\2\u0211\u0210\3\2\2\2\u0212\u0215\3\2\2\2\u0213\u0211\3"+
		"\2\2\2\u0213\u0214\3\2\2\2\u0214\u0216\3\2\2\2\u0215\u0213\3\2\2\2\u0216"+
		"\u0217\5:\36\2\u0217\u0218\5\u00c8e\2\u0218I\3\2\2\2\u0219\u021e\5\u00c8"+
		"e\2\u021a\u021b\7V\2\2\u021b\u021d\5\u00c8e\2\u021c\u021a\3\2\2\2\u021d"+
		"\u0220\3\2\2\2\u021e\u021c\3\2\2\2\u021e\u021f\3\2\2\2\u021fK\3\2\2\2"+
		"\u0220\u021e\3\2\2\2\u0221\u0222\t\3\2\2\u0222M\3\2\2\2\u0223\u0224\7"+
		"~\2\2\u0224\u022b\5J&\2\u0225\u0228\7N\2\2\u0226\u0229\5P)\2\u0227\u0229"+
		"\5T+\2\u0228\u0226\3\2\2\2\u0228\u0227\3\2\2\2\u0228\u0229\3\2\2\2\u0229"+
		"\u022a\3\2\2\2\u022a\u022c\7O\2\2\u022b\u0225\3\2\2\2\u022b\u022c\3\2"+
		"\2\2\u022cO\3\2\2\2\u022d\u0234\5R*\2\u022e\u0230\7U\2\2\u022f\u022e\3"+
		"\2\2\2\u022f\u0230\3\2\2\2\u0230\u0231\3\2\2\2\u0231\u0233\5R*\2\u0232"+
		"\u022f\3\2\2\2\u0233\u0236\3\2\2\2\u0234\u0232\3\2\2\2\u0234\u0235\3\2"+
		"\2\2\u0235Q\3\2\2\2\u0236\u0234\3\2\2\2\u0237\u0238\5\u00c8e\2\u0238\u0239"+
		"\7W\2\2\u0239\u023a\5T+\2\u023aS\3\2\2\2\u023b\u023f\5\u009eP\2\u023c"+
		"\u023f\5N(\2\u023d\u023f\5V,\2\u023e\u023b\3\2\2\2\u023e\u023c\3\2\2\2"+
		"\u023e\u023d\3\2\2\2\u023fU\3\2\2\2\u0240\u0249\7P\2\2\u0241\u0246\5T"+
		"+\2\u0242\u0243\7U\2\2\u0243\u0245\5T+\2\u0244\u0242\3\2\2\2\u0245\u0248"+
		"\3\2\2\2\u0246\u0244\3\2\2\2\u0246\u0247\3\2\2\2\u0247\u024a\3\2\2\2\u0248"+
		"\u0246\3\2\2\2\u0249\u0241\3\2\2\2\u0249\u024a\3\2\2\2\u024a\u024c\3\2"+
		"\2\2\u024b\u024d\7U\2\2\u024c\u024b\3\2\2\2\u024c\u024d\3\2\2\2\u024d"+
		"\u024e\3\2\2\2\u024e\u024f\7Q\2\2\u024fW\3\2\2\2\u0250\u0254\7P\2\2\u0251"+
		"\u0253\5^\60\2\u0252\u0251\3\2\2\2\u0253\u0256\3\2\2\2\u0254\u0252\3\2"+
		"\2\2\u0254\u0255\3\2\2\2\u0255\u0257\3\2\2\2\u0256\u0254\3\2\2\2\u0257"+
		"\u0258\7Q\2\2\u0258Y\3\2\2\2\u0259\u025a\5\\/\2\u025a\u025b\7T\2\2\u025b"+
		"[\3\2\2\2\u025c\u025e\5\32\16\2\u025d\u025c\3\2\2\2\u025e\u0261\3\2\2"+
		"\2\u025f\u025d\3\2\2\2\u025f\u0260\3\2\2\2\u0260\u0262\3\2\2\2\u0261\u025f"+
		"\3\2\2\2\u0262\u0263\5:\36\2\u0263\u0264\5\62\32\2\u0264]\3\2\2\2\u0265"+
		"\u027b\5X-\2\u0266\u027b\5Z.\2\u0267\u027b\5`\61\2\u0268\u027b\5b\62\2"+
		"\u0269\u027b\5d\63\2\u026a\u027b\5f\64\2\u026b\u027b\5h\65\2\u026c\u027b"+
		"\5j\66\2\u026d\u027b\5l\67\2\u026e\u027b\5n8\2\u026f\u027b\5p9\2\u0270"+
		"\u027b\5r:\2\u0271\u027b\5t;\2\u0272\u027b\5v<\2\u0273\u027b\5x=\2\u0274"+
		"\u027b\5z>\2\u0275\u027b\5|?\2\u0276\u027b\5~@\2\u0277\u027b\5\u0080A"+
		"\2\u0278\u027b\5\u0082B\2\u0279\u027b\5\u0084C\2\u027a\u0265\3\2\2\2\u027a"+
		"\u0266\3\2\2\2\u027a\u0267\3\2\2\2\u027a\u0268\3\2\2\2\u027a\u0269\3\2"+
		"\2\2\u027a\u026a\3\2\2\2\u027a\u026b\3\2\2\2\u027a\u026c\3\2\2\2\u027a"+
		"\u026d\3\2\2\2\u027a\u026e\3\2\2\2\u027a\u026f\3\2\2\2\u027a\u0270\3\2"+
		"\2\2\u027a\u0271\3\2\2\2\u027a\u0272\3\2\2\2\u027a\u0273\3\2\2\2\u027a"+
		"\u0274\3\2\2\2\u027a\u0275\3\2\2\2\u027a\u0276\3\2\2\2\u027a\u0277\3\2"+
		"\2\2\u027a\u0278\3\2\2\2\u027a\u0279\3\2\2\2\u027b_\3\2\2\2\u027c\u027d"+
		"\7\26\2\2\u027d\u027e\5\u009aN\2\u027e\u0281\5^\60\2\u027f\u0280\7\17"+
		"\2\2\u0280\u0282\5^\60\2\u0281\u027f\3\2\2\2\u0281\u0282\3\2\2\2\u0282"+
		"a\3\2\2\2\u0283\u0284\7\25\2\2\u0284\u0285\7N\2\2\u0285\u0286\5\u0092"+
		"J\2\u0286\u0287\7O\2\2\u0287\u0288\5^\60\2\u0288c\3\2\2\2\u0289\u028a"+
		"\7-\2\2\u028a\u028b\5\u009aN\2\u028b\u028c\5^\60\2\u028ce\3\2\2\2\u028d"+
		"\u028e\7\r\2\2\u028e\u028f\5^\60\2\u028f\u0290\7-\2\2\u0290\u0291\5\u009a"+
		"N\2\u0291\u0292\7T\2\2\u0292g\3\2\2\2\u0293\u0294\7+\2\2\u0294\u029e\5"+
		"X-\2\u0295\u0297\5\u008cG\2\u0296\u0295\3\2\2\2\u0297\u0298\3\2\2\2\u0298"+
		"\u0296\3\2\2\2\u0298\u0299\3\2\2\2\u0299\u029b\3\2\2\2\u029a\u029c\5\u0090"+
		"I\2\u029b\u029a\3\2\2\2\u029b\u029c\3\2\2\2\u029c\u029f\3\2\2\2\u029d"+
		"\u029f\5\u0090I\2\u029e\u0296\3\2\2\2\u029e\u029d\3\2\2\2\u029fi\3\2\2"+
		"\2\u02a0\u02a2\7#\2\2\u02a1\u02a3\5\u009eP\2\u02a2\u02a1\3\2\2\2\u02a2"+
		"\u02a3\3\2\2\2\u02a3\u02a4\3\2\2\2\u02a4\u02a5\7T\2\2\u02a5k\3\2\2\2\u02a6"+
		"\u02a7\7)\2\2\u02a7\u02a8\5\u009eP\2\u02a8\u02a9\7T\2\2\u02a9m\3\2\2\2"+
		"\u02aa\u02ac\7\5\2\2\u02ab\u02ad\5\u00c8e\2\u02ac\u02ab\3\2\2\2\u02ac"+
		"\u02ad\3\2\2\2\u02ad\u02ae\3\2\2\2\u02ae\u02af\7T\2\2\u02afo\3\2\2\2\u02b0"+
		"\u02b2\7\13\2\2\u02b1\u02b3\5\u00c8e\2\u02b2\u02b1\3\2\2\2\u02b2\u02b3"+
		"\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b5\7T\2\2\u02b5q\3\2\2\2\u02b6\u02b7"+
		"\7\61\2\2\u02b7\u02b8\5\u009eP\2\u02b8\u02b9\7T\2\2\u02b9s\3\2\2\2\u02ba"+
		"\u02bb\7\63\2\2\u02bb\u02bc\5\u009eP\2\u02bc\u02bd\7T\2\2\u02bdu\3\2\2"+
		"\2\u02be\u02bf\7\64\2\2\u02bf\u02c0\5\u009eP\2\u02c0\u02c1\7T\2\2\u02c1"+
		"w\3\2\2\2\u02c2\u02c3\7\65\2\2\u02c3\u02c4\5\u009eP\2\u02c4\u02c5\7T\2"+
		"\2\u02c5y\3\2\2\2\u02c6\u02c7\7\62\2\2\u02c7\u02c9\5\u009eP\2\u02c8\u02ca"+
		"\5\u00c8e\2\u02c9\u02c8\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cb\3\2\2"+
		"\2\u02cb\u02cc\7T\2\2\u02cc{\3\2\2\2\u02cd\u02ce\7\66\2\2\u02ce\u02cf"+
		"\5\u009eP\2\u02cf\u02d0\5\u009eP\2\u02d0\u02d1\7T\2\2\u02d1}\3\2\2\2\u02d2"+
		"\u02d3\7D\2\2\u02d3\u02d5\7N\2\2\u02d4\u02d6\5\u009cO\2\u02d5\u02d4\3"+
		"\2\2\2\u02d5\u02d6\3\2\2\2\u02d6\u02d7\3\2\2\2\u02d7\u02d9\7O\2\2\u02d8"+
		"\u02da\5X-\2\u02d9\u02d8\3\2\2\2\u02d9\u02da\3\2\2\2\u02da\177\3\2\2\2"+
		"\u02db\u02dc\7T\2\2\u02dc\u0081\3\2\2\2\u02dd\u02de\5\u009eP\2\u02de\u02df"+
		"\7T\2\2\u02df\u0083\3\2\2\2\u02e0\u02e1\5\u00c8e\2\u02e1\u02e2\7_\2\2"+
		"\u02e2\u02e3\5^\60\2\u02e3\u0085\3\2\2\2\u02e4\u02e6\5\32\16\2\u02e5\u02e4"+
		"\3\2\2\2\u02e6\u02e9\3\2\2\2\u02e7\u02e5\3\2\2\2\u02e7\u02e8\3\2\2\2\u02e8"+
		"\u02ec\3\2\2\2\u02e9\u02e7\3\2\2\2\u02ea\u02ed\5\u0088E\2\u02eb\u02ed"+
		"\5\u008aF\2\u02ec\u02ea\3\2\2\2\u02ec\u02eb\3\2\2\2\u02ed\u0087\3\2\2"+
		"\2\u02ee\u02f1\79\2\2\u02ef\u02f2\7T\2\2\u02f0\u02f2\5X-\2\u02f1\u02ef"+
		"\3\2\2\2\u02f1\u02f0\3\2\2\2\u02f2\u0089\3\2\2\2\u02f3\u02f6\7:\2\2\u02f4"+
		"\u02f7\7T\2\2\u02f5\u02f7\5X-\2\u02f6\u02f4\3\2\2\2\u02f6\u02f5\3\2\2"+
		"\2\u02f7\u008b\3\2\2\2\u02f8\u02f9\7\7\2\2\u02f9\u02fd\7N\2\2\u02fa\u02fc"+
		"\5\32\16\2\u02fb\u02fa\3\2\2\2\u02fc\u02ff\3\2\2\2\u02fd\u02fb\3\2\2\2"+
		"\u02fd\u02fe\3\2\2\2\u02fe\u0300\3\2\2\2\u02ff\u02fd\3\2\2\2\u0300\u0301"+
		"\5\u008eH\2\u0301\u0302\5\u00c8e\2\u0302\u0303\7O\2\2\u0303\u0304\5X-"+
		"\2\u0304\u008d\3\2\2\2\u0305\u030a\5J&\2\u0306\u0307\7n\2\2\u0307\u0309"+
		"\5J&\2\u0308\u0306\3\2\2\2\u0309\u030c\3\2\2\2\u030a\u0308\3\2\2\2\u030a"+
		"\u030b\3\2\2\2\u030b\u008f\3\2\2\2\u030c\u030a\3\2\2\2\u030d\u030e\7\23"+
		"\2\2\u030e\u030f\5X-\2\u030f\u0091\3\2\2\2\u0310\u031d\5\u0096L\2\u0311"+
		"\u0313\5\u0094K\2\u0312\u0311\3\2\2\2\u0312\u0313\3\2\2\2\u0313\u0314"+
		"\3\2\2\2\u0314\u0316\7T\2\2\u0315\u0317\5\u009eP\2\u0316\u0315\3\2\2\2"+
		"\u0316\u0317\3\2\2\2\u0317\u0318\3\2\2\2\u0318\u031a\7T\2\2\u0319\u031b"+
		"\5\u0098M\2\u031a\u0319\3\2\2\2\u031a\u031b\3\2\2\2\u031b\u031d\3\2\2"+
		"\2\u031c\u0310\3\2\2\2\u031c\u0312\3\2\2\2\u031d\u0093\3\2\2\2\u031e\u0321"+
		"\5\\/\2\u031f\u0321\5\u009cO\2\u0320\u031e\3\2\2\2\u0320\u031f\3\2\2\2"+
		"\u0321\u0095\3\2\2\2\u0322\u0324\5\32\16\2\u0323\u0322\3\2\2\2\u0324\u0327"+
		"\3\2\2\2\u0325\u0323\3\2\2\2\u0325\u0326\3\2\2\2\u0326\u0328\3\2\2\2\u0327"+
		"\u0325\3\2\2\2\u0328\u0329\5:\36\2\u0329\u032a\5\u00c8e\2\u032a\u032b"+
		"\7_\2\2\u032b\u032c\5\u009eP\2\u032c\u0097\3\2\2\2\u032d\u032e\5\u009c"+
		"O\2\u032e\u0099\3\2\2\2\u032f\u0330\7N\2\2\u0330\u0331\5\u009eP\2\u0331"+
		"\u0332\7O\2\2\u0332\u009b\3\2\2\2\u0333\u0338\5\u009eP\2\u0334\u0335\7"+
		"U\2\2\u0335\u0337\5\u009eP\2\u0336\u0334\3\2\2\2\u0337\u033a\3\2\2\2\u0338"+
		"\u0336\3\2\2\2\u0338\u0339\3\2\2\2\u0339\u009d\3\2\2\2\u033a\u0338\3\2"+
		"\2\2\u033b\u033c\bP\1\2\u033c\u033d\7\35\2\2\u033d\u0349\5\u00a2R\2\u033e"+
		"\u033f\7N\2\2\u033f\u0340\5:\36\2\u0340\u0341\7O\2\2\u0341\u0342\5\u009e"+
		"P\24\u0342\u0349\3\2\2\2\u0343\u0344\t\4\2\2\u0344\u0349\5\u009eP\22\u0345"+
		"\u0346\t\5\2\2\u0346\u0349\5\u009eP\21\u0347\u0349\5\u00a0Q\2\u0348\u033b"+
		"\3\2\2\2\u0348\u033e\3\2\2\2\u0348\u0343\3\2\2\2\u0348\u0345\3\2\2\2\u0348"+
		"\u0347\3\2\2\2\u0349\u03a8\3\2\2\2\u034a\u034b\f\20\2\2\u034b\u034c\t"+
		"\6\2\2\u034c\u03a7\5\u009eP\21\u034d\u034e\f\17\2\2\u034e\u034f\t\7\2"+
		"\2\u034f\u03a7\5\u009eP\20\u0350\u0358\f\16\2\2\u0351\u0352\7[\2\2\u0352"+
		"\u0359\7[\2\2\u0353\u0354\7Z\2\2\u0354\u0355\7Z\2\2\u0355\u0359\7Z\2\2"+
		"\u0356\u0357\7Z\2\2\u0357\u0359\7Z\2\2\u0358\u0351\3\2\2\2\u0358\u0353"+
		"\3\2\2\2\u0358\u0356\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u03a7\5\u009eP"+
		"\17\u035b\u0364\f\r\2\2\u035c\u035d\7[\2\2\u035d\u0365\7W\2\2\u035e\u035f"+
		"\7Z\2\2\u035f\u0365\7W\2\2\u0360\u0365\7X\2\2\u0361\u0365\7Y\2\2\u0362"+
		"\u0365\7Z\2\2\u0363\u0365\7[\2\2\u0364\u035c\3\2\2\2\u0364\u035e\3\2\2"+
		"\2\u0364\u0360\3\2\2\2\u0364\u0361\3\2\2\2\u0364\u0362\3\2\2\2\u0364\u0363"+
		"\3\2\2\2\u0365\u0366\3\2\2\2\u0366\u03a7\5\u009eP\16\u0367\u0368\f\13"+
		"\2\2\u0368\u0369\t\b\2\2\u0369\u03a7\5\u009eP\f\u036a\u036b\f\n\2\2\u036b"+
		"\u036c\7m\2\2\u036c\u03a7\5\u009eP\13\u036d\u036e\f\t\2\2\u036e\u036f"+
		"\7o\2\2\u036f\u03a7\5\u009eP\n\u0370\u0371\f\b\2\2\u0371\u0372\7n\2\2"+
		"\u0372\u03a7\5\u009eP\t\u0373\u0374\f\7\2\2\u0374\u0375\7e\2\2\u0375\u03a7"+
		"\5\u009eP\b\u0376\u0377\f\6\2\2\u0377\u0378\7f\2\2\u0378\u03a7\5\u009e"+
		"P\7\u0379\u037a\f\5\2\2\u037a\u037b\7^\2\2\u037b\u037c\5\u009eP\2\u037c"+
		"\u037d\7_\2\2\u037d\u037e\5\u009eP\6\u037e\u03a7\3\2\2\2\u037f\u0380\f"+
		"\4\2\2\u0380\u0381\t\t\2\2\u0381\u03a7\5\u009eP\4\u0382\u0383\f\34\2\2"+
		"\u0383\u0384\7V\2\2\u0384\u03a7\5\u00c8e\2\u0385\u0386\f\33\2\2\u0386"+
		"\u0387\7V\2\2\u0387\u03a7\7(\2\2\u0388\u0389\f\32\2\2\u0389\u038a\7V\2"+
		"\2\u038a\u038c\7\35\2\2\u038b\u038d\5\u00ba^\2\u038c\u038b\3\2\2\2\u038c"+
		"\u038d\3\2\2\2\u038d\u038e\3\2\2\2\u038e\u03a7\5\u00a8U\2\u038f\u0390"+
		"\f\31\2\2\u0390\u0391\7V\2\2\u0391\u0392\7\'\2\2\u0392\u03a7\5\u00c0a"+
		"\2\u0393\u0394\f\30\2\2\u0394\u0395\7V\2\2\u0395\u03a7\5\u00b8]\2\u0396"+
		"\u0397\f\27\2\2\u0397\u0398\7R\2\2\u0398\u0399\5\u009eP\2\u0399\u039a"+
		"\7S\2\2\u039a\u03a7\3\2\2\2\u039b\u039c\f\26\2\2\u039c\u039e\7N\2\2\u039d"+
		"\u039f\5\u009cO\2\u039e\u039d\3\2\2\2\u039e\u039f\3\2\2\2\u039f\u03a0"+
		"\3\2\2\2\u03a0\u03a7\7O\2\2\u03a1\u03a2\f\23\2\2\u03a2\u03a7\t\n\2\2\u03a3"+
		"\u03a4\f\f\2\2\u03a4\u03a5\7\31\2\2\u03a5\u03a7\5:\36\2\u03a6\u034a\3"+
		"\2\2\2\u03a6\u034d\3\2\2\2\u03a6\u0350\3\2\2\2\u03a6\u035b\3\2\2\2\u03a6"+
		"\u0367\3\2\2\2\u03a6\u036a\3\2\2\2\u03a6\u036d\3\2\2\2\u03a6\u0370\3\2"+
		"\2\2\u03a6\u0373\3\2\2\2\u03a6\u0376\3\2\2\2\u03a6\u0379\3\2\2\2\u03a6"+
		"\u037f\3\2\2\2\u03a6\u0382\3\2\2\2\u03a6\u0385\3\2\2\2\u03a6\u0388\3\2"+
		"\2\2\u03a6\u038f\3\2\2\2\u03a6\u0393\3\2\2\2\u03a6\u0396\3\2\2\2\u03a6"+
		"\u039b\3\2\2\2\u03a6\u03a1\3\2\2\2\u03a6\u03a3\3\2\2\2\u03a7\u03aa\3\2"+
		"\2\2\u03a8\u03a6\3\2\2\2\u03a8\u03a9\3\2\2\2\u03a9\u009f\3\2\2\2\u03aa"+
		"\u03a8\3\2\2\2\u03ab\u03ac\7N\2\2\u03ac\u03ad\5\u009eP\2\u03ad\u03ae\7"+
		"O\2\2\u03ae\u03c2\3\2\2\2\u03af\u03c2\7(\2\2\u03b0\u03c2\7\'\2\2\u03b1"+
		"\u03c2\5L\'\2\u03b2\u03c2\5\u00c8e\2\u03b3\u03b4\5:\36\2\u03b4\u03b5\7"+
		"V\2\2\u03b5\u03b6\7\t\2\2\u03b6\u03c2\3\2\2\2\u03b7\u03b8\7,\2\2\u03b8"+
		"\u03b9\7V\2\2\u03b9\u03c2\7\t\2\2\u03ba\u03be\5\u00ba^\2\u03bb\u03bf\5"+
		"\u00c2b\2\u03bc\u03bd\7(\2\2\u03bd\u03bf\5\u00c4c\2\u03be\u03bb\3\2\2"+
		"\2\u03be\u03bc\3\2\2\2\u03bf\u03c2\3\2\2\2\u03c0\u03c2\5\u00c6d\2\u03c1"+
		"\u03ab\3\2\2\2\u03c1\u03af\3\2\2\2\u03c1\u03b0\3\2\2\2\u03c1\u03b1\3\2"+
		"\2\2\u03c1\u03b2\3\2\2\2\u03c1\u03b3\3\2\2\2\u03c1\u03b7\3\2\2\2\u03c1"+
		"\u03ba\3\2\2\2\u03c1\u03c0\3\2\2\2\u03c2\u00a1\3\2\2\2\u03c3\u03c4\5\u00ba"+
		"^\2\u03c4\u03c5\5\u00a4S\2\u03c5\u03c6\5\u00b6\\\2\u03c6\u03cf\3\2\2\2"+
		"\u03c7\u03cc\5\u00a4S\2\u03c8\u03cd\5\u00aaV\2\u03c9\u03cd\5\u00b6\\\2"+
		"\u03ca\u03cd\5\u00acW\2\u03cb\u03cd\5\u00b0Y\2\u03cc\u03c8\3\2\2\2\u03cc"+
		"\u03c9\3\2\2\2\u03cc\u03ca\3\2\2\2\u03cc\u03cb\3\2\2\2\u03cd\u03cf\3\2"+
		"\2\2\u03ce\u03c3\3\2\2\2\u03ce\u03c7\3\2\2\2\u03cf\u00a3\3\2\2\2\u03d0"+
		"\u03d5\5\u00a6T\2\u03d1\u03d2\7V\2\2\u03d2\u03d4\5\u00a6T\2\u03d3\u03d1"+
		"\3\2\2\2\u03d4\u03d7\3\2\2\2\u03d5\u03d3\3\2\2\2\u03d5\u03d6\3\2\2\2\u03d6"+
		"\u03da\3\2\2\2\u03d7\u03d5\3\2\2\2\u03d8\u03da\5@!\2\u03d9\u03d0\3\2\2"+
		"\2\u03d9\u03d8\3\2\2\2\u03da\u00a5\3\2\2\2\u03db\u03dd\5\u00c8e\2\u03dc"+
		"\u03de\5\u00bc_\2\u03dd\u03dc\3\2\2\2\u03dd\u03de\3\2\2\2\u03de\u00a7"+
		"\3\2\2\2\u03df\u03e1\5\u00c8e\2\u03e0\u03e2\5\u00be`\2\u03e1\u03e0\3\2"+
		"\2\2\u03e1\u03e2\3\2\2\2\u03e2\u03e3\3\2\2\2\u03e3\u03e4\5\u00b6\\\2\u03e4"+
		"\u00a9\3\2\2\2\u03e5\u03f7\7R\2\2\u03e6\u03e7\7S\2\2\u03e7\u03e8\5<\37"+
		"\2\u03e8\u03e9\58\35\2\u03e9\u03f8\3\2\2\2\u03ea\u03eb\5\u009eP\2\u03eb"+
		"\u03f2\7S\2\2\u03ec\u03ed\7R\2\2\u03ed\u03ee\5\u009eP\2\u03ee\u03ef\7"+
		"S\2\2\u03ef\u03f1\3\2\2\2\u03f0\u03ec\3\2\2\2\u03f1\u03f4\3\2\2\2\u03f2"+
		"\u03f0\3\2\2\2\u03f2\u03f3\3\2\2\2\u03f3\u03f5\3\2\2\2\u03f4\u03f2\3\2"+
		"\2\2\u03f5\u03f6\5<\37\2\u03f6\u03f8\3\2\2\2\u03f7\u03e6\3\2\2\2\u03f7"+
		"\u03ea\3\2\2\2\u03f8\u00ab\3\2\2\2\u03f9\u03fa\7P\2\2\u03fa\u03ff\5\u00ae"+
		"X\2\u03fb\u03fc\7U\2\2\u03fc\u03fe\5\u00aeX\2\u03fd\u03fb\3\2\2\2\u03fe"+
		"\u0401\3\2\2\2\u03ff\u03fd\3\2\2\2\u03ff\u0400\3\2\2\2\u0400\u0402\3\2"+
		"\2\2\u0401\u03ff\3\2\2\2\u0402\u0403\7Q\2\2\u0403\u00ad\3\2\2\2\u0404"+
		"\u0405\5\u00b4[\2\u0405\u0406\7q\2\2\u0406\u0407\5\u00b2Z\2\u0407\u00af"+
		"\3\2\2\2\u0408\u0409\7P\2\2\u0409\u040e\5\u00b2Z\2\u040a\u040b\7U\2\2"+
		"\u040b\u040d\5\u00b2Z\2\u040c\u040a\3\2\2\2\u040d\u0410\3\2\2\2\u040e"+
		"\u040c\3\2\2\2\u040e\u040f\3\2\2\2\u040f\u0411\3\2\2\2\u0410\u040e\3\2"+
		"\2\2\u0411\u0412\7Q\2\2\u0412\u00b1\3\2\2\2\u0413\u0416\5L\'\2\u0414\u0416"+
		"\5\u009eP\2\u0415\u0413\3\2\2\2\u0415\u0414\3\2\2\2\u0416\u00b3\3\2\2"+
		"\2\u0417\u041a\5\u00c8e\2\u0418\u041a\5\u009eP\2\u0419\u0417\3\2\2\2\u0419"+
		"\u0418\3\2\2\2\u041a\u00b5\3\2\2\2\u041b\u0422\5\u00c4c\2\u041c\u041e"+
		"\7P\2\2\u041d\u041f\5\u009cO\2\u041e\u041d\3\2\2\2\u041e\u041f\3\2\2\2"+
		"\u041f\u0420\3\2\2\2\u0420\u0422\7Q\2\2\u0421\u041b\3\2\2\2\u0421\u041c"+
		"\3\2\2\2\u0422\u00b7\3\2\2\2\u0423\u0424\5\u00ba^\2\u0424\u0425\5\u00c2"+
		"b\2\u0425\u00b9\3\2\2\2\u0426\u0427\7[\2\2\u0427\u0428\5\22\n\2\u0428"+
		"\u0429\7Z\2\2\u0429\u00bb\3\2\2\2\u042a\u042b\7[\2\2\u042b\u042e\7Z\2"+
		"\2\u042c\u042e\5B\"\2\u042d\u042a\3\2\2\2\u042d\u042c\3\2\2\2\u042e\u00bd"+
		"\3\2\2\2\u042f\u0430\7[\2\2\u0430\u0433\7Z\2\2\u0431\u0433\5\u00ba^\2"+
		"\u0432\u042f\3\2\2\2\u0432\u0431\3\2\2\2\u0433\u00bf\3\2\2\2\u0434\u043b"+
		"\5\u00c4c\2\u0435\u0436\7V\2\2\u0436\u0438\5\u00c8e\2\u0437\u0439\5\u00c4"+
		"c\2\u0438\u0437\3\2\2\2\u0438\u0439\3\2\2\2\u0439\u043b\3\2\2\2\u043a"+
		"\u0434\3\2\2\2\u043a\u0435\3\2\2\2\u043b\u00c1\3\2\2\2\u043c\u043d\7\'"+
		"\2\2\u043d\u0442\5\u00c0a\2\u043e\u043f\5\u00c8e\2\u043f\u0440\5\u00c4"+
		"c\2\u0440\u0442\3\2\2\2\u0441\u043c\3\2\2\2\u0441\u043e\3\2\2\2\u0442"+
		"\u00c3\3\2\2\2\u0443\u0445\7N\2\2\u0444\u0446\5\u009cO\2\u0445\u0444\3"+
		"\2\2\2\u0445\u0446\3\2\2\2\u0446\u0447\3\2\2\2\u0447\u0448\7O\2\2\u0448"+
		"\u00c5\3\2\2\2\u0449\u044e\7R\2\2\u044a\u044d\5\u00c6d\2\u044b\u044d\n"+
		"\13\2\2\u044c\u044a\3\2\2\2\u044c\u044b\3\2\2\2\u044d\u0450\3\2\2\2\u044e"+
		"\u044f\3\2\2\2\u044e\u044c\3\2\2\2\u044f\u0451\3\2\2\2\u0450\u044e\3\2"+
		"\2\2\u0451\u0452\7S\2\2\u0452\u00c7\3\2\2\2\u0453\u0454\t\f\2\2\u0454"+
		"\u00c9\3\2\2\2t\u00d0\u00d7\u00de\u00e2\u00e8\u00ec\u00f4\u00f8\u00fb"+
		"\u00fe\u0107\u010d\u0112\u0115\u011b\u0122\u012b\u0132\u013b\u0142\u0148"+
		"\u014c\u0161\u016a\u016f\u0174\u017a\u018b\u0192\u0197\u019e\u01a6\u01b0"+
		"\u01b8\u01c3\u01c9\u01cd\u01d5\u01d9\u01db\u01e5\u01eb\u01f0\u01f5\u01f9"+
		"\u0204\u020d\u0213\u021e\u0228\u022b\u022f\u0234\u023e\u0246\u0249\u024c"+
		"\u0254\u025f\u027a\u0281\u0298\u029b\u029e\u02a2\u02ac\u02b2\u02c9\u02d5"+
		"\u02d9\u02e7\u02ec\u02f1\u02f6\u02fd\u030a\u0312\u0316\u031a\u031c\u0320"+
		"\u0325\u0338\u0348\u0358\u0364\u038c\u039e\u03a6\u03a8\u03be\u03c1\u03cc"+
		"\u03ce\u03d5\u03d9\u03dd\u03e1\u03f2\u03f7\u03ff\u040e\u0415\u0419\u041e"+
		"\u0421\u042d\u0432\u0438\u043a\u0441\u0445\u044c\u044e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}