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
		ABSTRACT=1, BLOB=2, BOOLEAN=3, BREAK=4, BYTE=5, CATCH=6, CHAR=7, CLASS=8, 
		CONST=9, CONTINUE=10, DATE=11, DATETIME=12, DECIMAL=13, DEFAULT=14, DELETE=15, 
		DO=16, DOUBLE=17, ELSE=18, ENUM=19, EXTENDS=20, FINAL=21, FINALLY=22, 
		FLOAT=23, FOR=24, GET=25, GLOBAL=26, GOTO=27, ID=28, IF=29, IMPLEMENTS=30, 
		INHERITED=31, INSERT=32, INSTANCEOF=33, INTEGER=34, INTERFACE=35, LONG=36, 
		MERGE=37, NATIVE=38, NEW=39, NULL=40, OBJECT=41, ON=42, OVERRIDE=43, PACKAGE=44, 
		PRIVATE=45, PROTECTED=46, PUBLIC=47, RETURN=48, RUNAS=49, SELECT=50, SET=51, 
		SHARING=52, SHORT=53, STATIC=54, STRING=55, SUPER=56, SWITCH=57, TESTMETHOD=58, 
		THIS=59, THROW=60, TIME=61, TRANSIENT=62, TRY=63, UNDELETE=64, UPDATE=65, 
		UPSERT=66, VIRTUAL=67, VOID=68, WEBSERVICE=69, WHEN=70, WHILE=71, WITH=72, 
		WITHOUT=73, IntegerLiteral=74, NumberLiteral=75, BooleanLiteral=76, StringLiteral=77, 
		NullLiteral=78, LPAREN=79, RPAREN=80, LBRACE=81, RBRACE=82, LBRACK=83, 
		RBRACK=84, SEMI=85, COMMA=86, DOT=87, ASSIGN=88, LE=89, GE=90, GT=91, 
		LT=92, BANG=93, TILDE=94, QUESTION=95, COLON=96, EQUAL=97, TRIPLEEQUAL=98, 
		NOTEQUAL=99, LESSANDGREATER=100, TRIPLENOTEQUAL=101, AND=102, OR=103, 
		INC=104, DEC=105, ADD=106, SUB=107, MUL=108, DIV=109, BITAND=110, BITOR=111, 
		CARET=112, MOD=113, MAP=114, ADD_ASSIGN=115, SUB_ASSIGN=116, MUL_ASSIGN=117, 
		DIV_ASSIGN=118, AND_ASSIGN=119, OR_ASSIGN=120, XOR_ASSIGN=121, MOD_ASSIGN=122, 
		LSHIFT_ASSIGN=123, RSHIFT_ASSIGN=124, URSHIFT_ASSIGN=125, Identifier=126, 
		AT=127, WS=128, DOC_COMMENT=129, COMMENT=130, LINE_COMMENT=131;
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
		RULE_statement = 46, RULE_ifStatement = 47, RULE_switchStatement = 48, 
		RULE_whenControl = 49, RULE_forStatement = 50, RULE_whileStatement = 51, 
		RULE_doWhileStatement = 52, RULE_tryStatement = 53, RULE_returnStatement = 54, 
		RULE_throwStatement = 55, RULE_breakStatement = 56, RULE_continueStatement = 57, 
		RULE_insertStatement = 58, RULE_updateStatement = 59, RULE_deleteStatement = 60, 
		RULE_undeleteStatement = 61, RULE_upsertStatement = 62, RULE_mergeStatement = 63, 
		RULE_runAsStatement = 64, RULE_emptyStatement = 65, RULE_expressionStatement = 66, 
		RULE_idStatement = 67, RULE_propertyBlock = 68, RULE_getter = 69, RULE_setter = 70, 
		RULE_catchClause = 71, RULE_catchType = 72, RULE_finallyBlock = 73, RULE_forControl = 74, 
		RULE_forInit = 75, RULE_enhancedForControl = 76, RULE_forUpdate = 77, 
		RULE_parExpression = 78, RULE_expressionList = 79, RULE_expression = 80, 
		RULE_primary = 81, RULE_creator = 82, RULE_createdName = 83, RULE_idCreatedNamePair = 84, 
		RULE_innerCreator = 85, RULE_arrayCreatorRest = 86, RULE_mapCreatorRest = 87, 
		RULE_mapCreatorRestPair = 88, RULE_setCreatorRest = 89, RULE_literalOrExpression = 90, 
		RULE_idOrExpression = 91, RULE_classCreatorRest = 92, RULE_explicitGenericInvocation = 93, 
		RULE_nonWildcardTypeArguments = 94, RULE_typeArgumentsOrDiamond = 95, 
		RULE_nonWildcardTypeArgumentsOrDiamond = 96, RULE_superSuffix = 97, RULE_explicitGenericInvocationSuffix = 98, 
		RULE_arguments = 99, RULE_soqlLiteral = 100, RULE_id = 101;
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
			null, "'abstract'", "'blob'", "'boolean'", "'break'", "'byte'", "'catch'", 
			"'char'", "'class'", "'const'", "'continue'", "'date'", "'datetime'", 
			"'decimal'", "'default'", "'delete'", "'do'", "'double'", "'else'", "'enum'", 
			"'extends'", "'final'", "'finally'", "'float'", "'for'", "'get'", "'global'", 
			"'goto'", "'id'", "'if'", "'implements'", "'inherited'", "'insert'", 
			"'instanceof'", "'integer'", "'interface'", "'long'", "'merge'", "'native'", 
			"'new'", "'null'", "'object'", "'on'", "'override'", "'package'", "'private'", 
			"'protected'", "'public'", "'return'", "'system.runas'", "'select'", 
			"'set'", "'sharing'", "'short'", "'static'", "'string'", "'super'", "'switch'", 
			"'testmethod'", "'this'", "'throw'", "'time'", "'transient'", "'try'", 
			"'undelete'", "'update'", "'upsert'", "'virtual'", "'void'", "'webservice'", 
			"'when'", "'while'", "'with'", "'without'", null, null, null, null, null, 
			"'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'", "'='", 
			"'<='", "'>='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", "'==='", 
			"'!='", "'<>'", "'!=='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", 
			"'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'=>'", "'+='", "'-='", "'*='", 
			"'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, 
			"'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BLOB", "BOOLEAN", "BREAK", "BYTE", "CATCH", "CHAR", 
			"CLASS", "CONST", "CONTINUE", "DATE", "DATETIME", "DECIMAL", "DEFAULT", 
			"DELETE", "DO", "DOUBLE", "ELSE", "ENUM", "EXTENDS", "FINAL", "FINALLY", 
			"FLOAT", "FOR", "GET", "GLOBAL", "GOTO", "ID", "IF", "IMPLEMENTS", "INHERITED", 
			"INSERT", "INSTANCEOF", "INTEGER", "INTERFACE", "LONG", "MERGE", "NATIVE", 
			"NEW", "NULL", "OBJECT", "ON", "OVERRIDE", "PACKAGE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "RUNAS", "SELECT", "SET", "SHARING", "SHORT", "STATIC", 
			"STRING", "SUPER", "SWITCH", "TESTMETHOD", "THIS", "THROW", "TIME", "TRANSIENT", 
			"TRY", "UNDELETE", "UPDATE", "UPSERT", "VIRTUAL", "VOID", "WEBSERVICE", 
			"WHEN", "WHILE", "WITH", "WITHOUT", "IntegerLiteral", "NumberLiteral", 
			"BooleanLiteral", "StringLiteral", "NullLiteral", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", 
			"LE", "GE", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", 
			"TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", 
			"MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "WS", "DOC_COMMENT", 
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
			setState(204);
			typeDeclaration();
			setState(205);
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
			setState(228);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (VIRTUAL - 67)) | (1L << (WEBSERVICE - 67)) | (1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (AT - 67)))) != 0)) {
					{
					{
					setState(207);
					modifier();
					}
					}
					setState(212);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(213);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (VIRTUAL - 67)) | (1L << (WEBSERVICE - 67)) | (1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (AT - 67)))) != 0)) {
					{
					{
					setState(214);
					modifier();
					}
					}
					setState(219);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(220);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(224);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (VIRTUAL - 67)) | (1L << (WEBSERVICE - 67)) | (1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (AT - 67)))) != 0)) {
					{
					{
					setState(221);
					modifier();
					}
					}
					setState(226);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(227);
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
			setState(230);
			match(CLASS);
			setState(231);
			id();
			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(232);
				match(EXTENDS);
				setState(233);
				typeRef();
				}
			}

			setState(238);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(236);
				match(IMPLEMENTS);
				setState(237);
				typeList();
				}
			}

			setState(240);
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
			setState(242);
			match(ENUM);
			setState(243);
			id();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(244);
				match(IMPLEMENTS);
				setState(245);
				typeList();
				}
			}

			setState(248);
			match(LBRACE);
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(249);
				enumConstants();
				}
			}

			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(252);
				match(COMMA);
				}
			}

			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(255);
				enumBodyDeclarations();
				}
			}

			setState(258);
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
			setState(260);
			enumConstant();
			setState(265);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(261);
					match(COMMA);
					setState(262);
					enumConstant();
					}
					} 
				}
				setState(267);
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
			setState(271);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(268);
					modifier();
					}
					} 
				}
				setState(273);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(274);
			id();
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(275);
				arguments();
				}
			}

			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(278);
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
			setState(281);
			match(SEMI);
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(282);
				classBodyDeclaration();
				}
				}
				setState(287);
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
			setState(288);
			match(INTERFACE);
			setState(289);
			id();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(290);
				match(EXTENDS);
				setState(291);
				typeList();
				}
			}

			setState(294);
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
			setState(296);
			typeRef();
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(297);
				match(COMMA);
				setState(298);
				typeRef();
				}
				}
				setState(303);
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
			setState(304);
			match(LBRACE);
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(305);
				classBodyDeclaration();
				}
				}
				setState(310);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(311);
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
			setState(313);
			match(LBRACE);
			setState(317);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(314);
				interfaceBodyDeclaration();
				}
				}
				setState(319);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(320);
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
			setState(334);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(324);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(323);
					match(STATIC);
					}
				}

				setState(326);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(330);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(327);
						modifier();
						}
						} 
					}
					setState(332);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
				setState(333);
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
			setState(355);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(336);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(338);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(339);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(340);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(341);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(342);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(343);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(344);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(345);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(346);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(347);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(348);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(349);
				match(WITH);
				setState(350);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(351);
				match(WITHOUT);
				setState(352);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(353);
				match(INHERITED);
				setState(354);
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
			setState(364);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(357);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(358);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(359);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(360);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(361);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(362);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(363);
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
			setState(369);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			setState(374);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(372);
				typeRef();
				}
				break;
			case 2:
				{
				setState(373);
				match(VOID);
				}
				break;
			}
			setState(376);
			id();
			setState(377);
			formalParameters();
			setState(380);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(378);
				block();
				}
				break;
			case SEMI:
				{
				setState(379);
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
			setState(382);
			qualifiedName();
			setState(383);
			formalParameters();
			setState(384);
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
			setState(386);
			typeRef();
			setState(387);
			variableDeclarators();
			setState(388);
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
			setState(390);
			typeRef();
			setState(391);
			variableDeclarators();
			setState(392);
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
			setState(394);
			match(LBRACE);
			setState(395);
			propertyBlock();
			setState(397);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (VIRTUAL - 67)) | (1L << (WEBSERVICE - 67)) | (1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (AT - 67)))) != 0)) {
				{
				setState(396);
				propertyBlock();
				}
			}

			setState(399);
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
			setState(409);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BLOB:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case DEFAULT:
			case DELETE:
			case DO:
			case DOUBLE:
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
			case ID:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTEGER:
			case INTERFACE:
			case LONG:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case OBJECT:
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
			case STRING:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TIME:
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
				setState(404);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(401);
						modifier();
						}
						} 
					}
					setState(406);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				}
				setState(407);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(408);
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
			setState(416);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(411);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(412);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(413);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(414);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(415);
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
			setState(418);
			typeRef();
			setState(419);
			constantDeclarator();
			setState(424);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(420);
				match(COMMA);
				setState(421);
				constantDeclarator();
				}
				}
				setState(426);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(427);
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
			setState(429);
			id();
			setState(434);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(430);
				match(LBRACK);
				setState(431);
				match(RBRACK);
				}
				}
				setState(436);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(437);
			match(ASSIGN);
			setState(438);
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
			setState(442);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(440);
				typeRef();
				}
				break;
			case 2:
				{
				setState(441);
				match(VOID);
				}
				break;
			}
			setState(444);
			id();
			setState(445);
			formalParameters();
			setState(446);
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
			setState(448);
			variableDeclarator();
			setState(453);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(449);
				match(COMMA);
				setState(450);
				variableDeclarator();
				}
				}
				setState(455);
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
			setState(456);
			id();
			setState(459);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(457);
				match(ASSIGN);
				setState(458);
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
			setState(463);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(461);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BLOB:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case DEFAULT:
			case DELETE:
			case DO:
			case DOUBLE:
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
			case ID:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTEGER:
			case INTERFACE:
			case LONG:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case OBJECT:
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
			case STRING:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TIME:
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
				setState(462);
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
			setState(465);
			match(LBRACE);
			setState(477);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(466);
				variableInitializer();
				setState(471);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(467);
						match(COMMA);
						setState(468);
						variableInitializer();
						}
						} 
					}
					setState(473);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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

				}
			}

			setState(479);
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
			setState(487);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(481);
				classOrInterfaceType();
				setState(482);
				arraySubscripts();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(484);
				primitiveType();
				setState(485);
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
			setState(493);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(489);
					match(LBRACK);
					setState(490);
					match(RBRACK);
					}
					} 
				}
				setState(495);
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
			setState(496);
			id();
			setState(498);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(497);
				typeArguments();
				}
				break;
			}
			setState(507);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(500);
					match(DOT);
					setState(501);
					id();
					setState(503);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
					case 1:
						{
						setState(502);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(509);
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
			setState(510);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BLOB) | (1L << BOOLEAN) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DOUBLE) | (1L << ID) | (1L << INTEGER) | (1L << LONG) | (1L << OBJECT) | (1L << STRING) | (1L << TIME))) != 0)) ) {
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
			setState(512);
			match(LT);
			setState(513);
			typeList();
			setState(514);
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
			setState(516);
			match(LPAREN);
			setState(518);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(517);
				formalParameterList();
				}
			}

			setState(520);
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
			setState(522);
			formalParameter();
			setState(527);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(523);
				match(COMMA);
				setState(524);
				formalParameter();
				}
				}
				setState(529);
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
			setState(533);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(530);
					modifier();
					}
					} 
				}
				setState(535);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			setState(536);
			typeRef();
			setState(537);
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
			setState(539);
			id();
			setState(544);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(540);
				match(DOT);
				setState(541);
				id();
				}
				}
				setState(546);
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
			setState(547);
			_la = _input.LA(1);
			if ( !(((((_la - 40)) & ~0x3f) == 0 && ((1L << (_la - 40)) & ((1L << (NULL - 40)) | (1L << (IntegerLiteral - 40)) | (1L << (NumberLiteral - 40)) | (1L << (BooleanLiteral - 40)) | (1L << (StringLiteral - 40)))) != 0)) ) {
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
			setState(549);
			match(AT);
			setState(550);
			qualifiedName();
			setState(557);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(551);
				match(LPAREN);
				setState(554);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
				case 1:
					{
					setState(552);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(553);
					elementValue();
					}
					break;
				}
				setState(556);
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
			setState(559);
			elementValuePair();
			setState(566);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (COMMA - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(561);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(560);
					match(COMMA);
					}
				}

				setState(563);
				elementValuePair();
				}
				}
				setState(568);
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
			setState(569);
			id();
			setState(570);
			match(ASSIGN);
			setState(571);
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
			setState(576);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABSTRACT:
			case BLOB:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case DEFAULT:
			case DELETE:
			case DO:
			case DOUBLE:
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
			case ID:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTEGER:
			case INTERFACE:
			case LONG:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case OBJECT:
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
			case STRING:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TIME:
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
				setState(573);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(574);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(575);
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
			setState(578);
			match(LBRACE);
			setState(587);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(579);
				elementValue();
				setState(584);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(580);
						match(COMMA);
						setState(581);
						elementValue();
						}
						} 
					}
					setState(586);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
				}
				}
			}

			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(589);
				match(COMMA);
				}
			}

			setState(592);
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
			setState(594);
			match(LBRACE);
			setState(598);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (SEMI - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(595);
				statement();
				}
				}
				setState(600);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(601);
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
			setState(603);
			localVariableDeclaration();
			setState(604);
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
			setState(609);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(606);
					modifier();
					}
					} 
				}
				setState(611);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			}
			setState(612);
			typeRef();
			setState(613);
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
		enterRule(_localctx, 92, RULE_statement);
		try {
			setState(637);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(615);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(616);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(617);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(618);
				switchStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(619);
				forStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(620);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(621);
				doWhileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(622);
				tryStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(623);
				returnStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(624);
				throwStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(625);
				breakStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(626);
				continueStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(627);
				insertStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(628);
				updateStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(629);
				deleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(630);
				undeleteStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(631);
				upsertStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(632);
				mergeStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(633);
				runAsStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(634);
				emptyStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(635);
				expressionStatement();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(636);
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
			setState(639);
			match(IF);
			setState(640);
			parExpression();
			setState(641);
			statement();
			setState(644);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(642);
				match(ELSE);
				setState(643);
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
		enterRule(_localctx, 96, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(646);
			match(SWITCH);
			setState(647);
			match(ON);
			setState(648);
			expression(0);
			setState(649);
			match(LBRACE);
			setState(651); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(650);
				whenControl();
				}
				}
				setState(653); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(655);
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
		enterRule(_localctx, 98, RULE_whenControl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(657);
			match(WHEN);
			setState(660);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				{
				setState(658);
				expressionList();
				}
				break;
			case 2:
				{
				setState(659);
				match(ELSE);
				}
				break;
			}
			setState(662);
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
		enterRule(_localctx, 100, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(664);
			match(FOR);
			setState(665);
			match(LPAREN);
			setState(666);
			forControl();
			setState(667);
			match(RPAREN);
			setState(668);
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
		enterRule(_localctx, 102, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(670);
			match(WHILE);
			setState(671);
			parExpression();
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
		enterRule(_localctx, 104, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			match(DO);
			setState(675);
			statement();
			setState(676);
			match(WHILE);
			setState(677);
			parExpression();
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
		enterRule(_localctx, 106, RULE_tryStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(680);
			match(TRY);
			setState(681);
			block();
			setState(691);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(683); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(682);
						catchClause();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(685); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(688);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
				case 1:
					{
					setState(687);
					finallyBlock();
					}
					break;
				}
				}
				break;
			case FINALLY:
				{
				setState(690);
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
		enterRule(_localctx, 108, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(693);
			match(RETURN);
			setState(695);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(694);
				expression(0);
				}
			}

			setState(697);
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
		enterRule(_localctx, 110, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			match(THROW);
			setState(700);
			expression(0);
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
		enterRule(_localctx, 112, RULE_breakStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			match(BREAK);
			setState(705);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(704);
				id();
				}
			}

			setState(707);
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
		enterRule(_localctx, 114, RULE_continueStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(709);
			match(CONTINUE);
			setState(711);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)))) != 0)) {
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
		enterRule(_localctx, 116, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(715);
			match(INSERT);
			setState(716);
			expression(0);
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
		enterRule(_localctx, 118, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(719);
			match(UPDATE);
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
		enterRule(_localctx, 120, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(723);
			match(DELETE);
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
		enterRule(_localctx, 122, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(727);
			match(UNDELETE);
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
		enterRule(_localctx, 124, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(731);
			match(UPSERT);
			setState(732);
			expression(0);
			setState(734);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(733);
				id();
				}
			}

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
		enterRule(_localctx, 126, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(738);
			match(MERGE);
			setState(739);
			expression(0);
			setState(740);
			expression(0);
			setState(741);
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
		enterRule(_localctx, 128, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(743);
			match(RUNAS);
			setState(744);
			match(LPAREN);
			setState(746);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(745);
				expressionList();
				}
			}

			setState(748);
			match(RPAREN);
			setState(750);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				{
				setState(749);
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
		enterRule(_localctx, 130, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(752);
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
		enterRule(_localctx, 132, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(754);
			expression(0);
			setState(755);
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
		enterRule(_localctx, 134, RULE_idStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(757);
			id();
			setState(758);
			match(COLON);
			setState(759);
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
		enterRule(_localctx, 136, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(764);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT))) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (VIRTUAL - 67)) | (1L << (WEBSERVICE - 67)) | (1L << (WITH - 67)) | (1L << (WITHOUT - 67)) | (1L << (AT - 67)))) != 0)) {
				{
				{
				setState(761);
				modifier();
				}
				}
				setState(766);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(769);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(767);
				getter();
				}
				break;
			case SET:
				{
				setState(768);
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
		enterRule(_localctx, 138, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(771);
			match(GET);
			setState(774);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(772);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(773);
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
		enterRule(_localctx, 140, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(776);
			match(SET);
			setState(779);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(777);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(778);
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
		enterRule(_localctx, 142, RULE_catchClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(781);
			match(CATCH);
			setState(782);
			match(LPAREN);
			setState(786);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(783);
					modifier();
					}
					} 
				}
				setState(788);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			}
			setState(789);
			catchType();
			setState(790);
			id();
			setState(791);
			match(RPAREN);
			setState(792);
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
		enterRule(_localctx, 144, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(794);
			qualifiedName();
			setState(799);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(795);
				match(BITOR);
				setState(796);
				qualifiedName();
				}
				}
				setState(801);
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
		enterRule(_localctx, 146, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(802);
			match(FINALLY);
			setState(803);
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
		enterRule(_localctx, 148, RULE_forControl);
		int _la;
		try {
			setState(817);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(805);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(807);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(806);
					forInit();
					}
				}

				setState(809);
				match(SEMI);
				setState(811);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(810);
					expression(0);
					}
				}

				setState(813);
				match(SEMI);
				setState(815);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(814);
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
		enterRule(_localctx, 150, RULE_forInit);
		try {
			setState(821);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(819);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(820);
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
		enterRule(_localctx, 152, RULE_enhancedForControl);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(826);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(823);
					modifier();
					}
					} 
				}
				setState(828);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			}
			setState(829);
			typeRef();
			setState(830);
			id();
			setState(831);
			match(COLON);
			setState(832);
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
		enterRule(_localctx, 154, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(834);
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
		enterRule(_localctx, 156, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(836);
			match(LPAREN);
			setState(837);
			expression(0);
			setState(838);
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
		enterRule(_localctx, 158, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(840);
			expression(0);
			setState(845);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(841);
				match(COMMA);
				setState(842);
				expression(0);
				}
				}
				setState(847);
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
		int _startState = 160;
		enterRecursionRule(_localctx, 160, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(861);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(849);
				match(NEW);
				setState(850);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(851);
				match(LPAREN);
				setState(852);
				typeRef();
				setState(853);
				match(RPAREN);
				setState(854);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(856);
				_la = _input.LA(1);
				if ( !(((((_la - 104)) & ~0x3f) == 0 && ((1L << (_la - 104)) & ((1L << (INC - 104)) | (1L << (DEC - 104)) | (1L << (ADD - 104)) | (1L << (SUB - 104)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(857);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(858);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(859);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(860);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(957);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(955);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(863);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(864);
						_la = _input.LA(1);
						if ( !(((((_la - 108)) & ~0x3f) == 0 && ((1L << (_la - 108)) & ((1L << (MUL - 108)) | (1L << (DIV - 108)) | (1L << (MOD - 108)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(865);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(866);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(867);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(868);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(869);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(877);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
						case 1:
							{
							setState(870);
							match(LT);
							setState(871);
							match(LT);
							}
							break;
						case 2:
							{
							setState(872);
							match(GT);
							setState(873);
							match(GT);
							setState(874);
							match(GT);
							}
							break;
						case 3:
							{
							setState(875);
							match(GT);
							setState(876);
							match(GT);
							}
							break;
						}
						setState(879);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(880);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(889);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
						case 1:
							{
							setState(881);
							match(LT);
							setState(882);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(883);
							match(GT);
							setState(884);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(885);
							match(LE);
							}
							break;
						case 4:
							{
							setState(886);
							match(GE);
							}
							break;
						case 5:
							{
							setState(887);
							match(GT);
							}
							break;
						case 6:
							{
							setState(888);
							match(LT);
							}
							break;
						}
						setState(891);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(892);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(893);
						_la = _input.LA(1);
						if ( !(((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & ((1L << (EQUAL - 97)) | (1L << (TRIPLEEQUAL - 97)) | (1L << (NOTEQUAL - 97)) | (1L << (LESSANDGREATER - 97)) | (1L << (TRIPLENOTEQUAL - 97)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(894);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(895);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(896);
						match(BITAND);
						setState(897);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(898);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(899);
						match(CARET);
						setState(900);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(901);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(902);
						match(BITOR);
						setState(903);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(904);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(905);
						match(AND);
						setState(906);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(907);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(908);
						match(OR);
						setState(909);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(910);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(911);
						match(QUESTION);
						setState(912);
						expression(0);
						setState(913);
						match(COLON);
						setState(914);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(916);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(917);
						_la = _input.LA(1);
						if ( !(((((_la - 88)) & ~0x3f) == 0 && ((1L << (_la - 88)) & ((1L << (ASSIGN - 88)) | (1L << (ADD_ASSIGN - 88)) | (1L << (SUB_ASSIGN - 88)) | (1L << (MUL_ASSIGN - 88)) | (1L << (DIV_ASSIGN - 88)) | (1L << (AND_ASSIGN - 88)) | (1L << (OR_ASSIGN - 88)) | (1L << (XOR_ASSIGN - 88)) | (1L << (MOD_ASSIGN - 88)) | (1L << (LSHIFT_ASSIGN - 88)) | (1L << (RSHIFT_ASSIGN - 88)) | (1L << (URSHIFT_ASSIGN - 88)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(918);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(919);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(920);
						match(DOT);
						setState(921);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(922);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(923);
						match(DOT);
						setState(924);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(925);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(926);
						match(DOT);
						setState(927);
						match(NEW);
						setState(929);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(928);
							nonWildcardTypeArguments();
							}
						}

						setState(931);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(932);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(933);
						match(DOT);
						setState(934);
						match(SUPER);
						setState(935);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(936);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(937);
						match(DOT);
						setState(938);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(939);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(940);
						match(LBRACK);
						setState(941);
						expression(0);
						setState(942);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(944);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(945);
						match(LPAREN);
						setState(947);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(946);
							expressionList();
							}
						}

						setState(949);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(950);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(951);
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
						setState(952);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(953);
						match(INSTANCEOF);
						setState(954);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(959);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
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
		enterRule(_localctx, 162, RULE_primary);
		try {
			setState(982);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,93,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(960);
				match(LPAREN);
				setState(961);
				expression(0);
				setState(962);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(964);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(965);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(966);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(967);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(968);
				typeRef();
				setState(969);
				match(DOT);
				setState(970);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(972);
				match(VOID);
				setState(973);
				match(DOT);
				setState(974);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(975);
				nonWildcardTypeArguments();
				setState(979);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
				case 1:
					{
					setState(976);
					explicitGenericInvocationSuffix();
					}
					break;
				case 2:
					{
					setState(977);
					match(THIS);
					setState(978);
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
				setState(981);
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
		enterRule(_localctx, 164, RULE_creator);
		try {
			setState(995);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(984);
				nonWildcardTypeArguments();
				setState(985);
				createdName();
				setState(986);
				classCreatorRest();
				}
				break;
			case ABSTRACT:
			case BLOB:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case DEFAULT:
			case DELETE:
			case DO:
			case DOUBLE:
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
			case ID:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTEGER:
			case INTERFACE:
			case LONG:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case OBJECT:
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
			case STRING:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TIME:
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
				setState(988);
				createdName();
				setState(993);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
				case 1:
					{
					setState(989);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(990);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(991);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(992);
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
		enterRule(_localctx, 166, RULE_createdName);
		int _la;
		try {
			setState(1006);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(997);
				idCreatedNamePair();
				setState(1002);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(998);
					match(DOT);
					setState(999);
					idCreatedNamePair();
					}
					}
					setState(1004);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1005);
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
		enterRule(_localctx, 168, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1008);
			id();
			setState(1010);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1009);
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
		enterRule(_localctx, 170, RULE_innerCreator);
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
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(1016);
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
		enterRule(_localctx, 172, RULE_arrayCreatorRest);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1018);
			match(LBRACK);
			setState(1036);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(1019);
				match(RBRACK);
				setState(1020);
				arraySubscripts();
				setState(1021);
				arrayInitializer();
				}
				break;
			case ABSTRACT:
			case BLOB:
			case BOOLEAN:
			case BREAK:
			case BYTE:
			case CATCH:
			case CHAR:
			case CLASS:
			case CONST:
			case CONTINUE:
			case DATE:
			case DATETIME:
			case DECIMAL:
			case DEFAULT:
			case DELETE:
			case DO:
			case DOUBLE:
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
			case ID:
			case IF:
			case IMPLEMENTS:
			case INHERITED:
			case INSERT:
			case INSTANCEOF:
			case INTEGER:
			case INTERFACE:
			case LONG:
			case MERGE:
			case NATIVE:
			case NEW:
			case NULL:
			case OBJECT:
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
			case STRING:
			case SUPER:
			case SWITCH:
			case TESTMETHOD:
			case THIS:
			case THROW:
			case TIME:
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
				setState(1023);
				expression(0);
				setState(1024);
				match(RBRACK);
				setState(1031);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,100,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1025);
						match(LBRACK);
						setState(1026);
						expression(0);
						setState(1027);
						match(RBRACK);
						}
						} 
					}
					setState(1033);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,100,_ctx);
				}
				setState(1034);
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
		enterRule(_localctx, 174, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1038);
			match(LBRACE);
			setState(1039);
			mapCreatorRestPair();
			setState(1044);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1040);
				match(COMMA);
				setState(1041);
				mapCreatorRestPair();
				}
				}
				setState(1046);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1047);
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
		enterRule(_localctx, 176, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1049);
			idOrExpression();
			setState(1050);
			match(MAP);
			setState(1051);
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
		enterRule(_localctx, 178, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1053);
			match(LBRACE);
			setState(1054);
			literalOrExpression();
			setState(1059);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1055);
				match(COMMA);
				{
				setState(1056);
				literalOrExpression();
				}
				}
				}
				setState(1061);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1062);
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
		enterRule(_localctx, 180, RULE_literalOrExpression);
		try {
			setState(1066);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,104,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1064);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1065);
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
		enterRule(_localctx, 182, RULE_idOrExpression);
		try {
			setState(1070);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1068);
				id();
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
		enterRule(_localctx, 184, RULE_classCreatorRest);
		int _la;
		try {
			setState(1078);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1072);
				arguments();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1073);
				match(LBRACE);
				setState(1075);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1074);
					expressionList();
					}
				}

				setState(1077);
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
		enterRule(_localctx, 186, RULE_explicitGenericInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1080);
			nonWildcardTypeArguments();
			setState(1081);
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
		enterRule(_localctx, 188, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1083);
			match(LT);
			setState(1084);
			typeList();
			setState(1085);
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
		enterRule(_localctx, 190, RULE_typeArgumentsOrDiamond);
		try {
			setState(1090);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1087);
				match(LT);
				setState(1088);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1089);
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
		enterRule(_localctx, 192, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1095);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1092);
				match(LT);
				setState(1093);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1094);
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
		enterRule(_localctx, 194, RULE_superSuffix);
		try {
			setState(1103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1097);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1098);
				match(DOT);
				setState(1099);
				id();
				setState(1101);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
				case 1:
					{
					setState(1100);
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
		enterRule(_localctx, 196, RULE_explicitGenericInvocationSuffix);
		try {
			setState(1110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1105);
				match(SUPER);
				setState(1106);
				superSuffix();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1107);
				id();
				setState(1108);
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
		enterRule(_localctx, 198, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1112);
			match(LPAREN);
			setState(1114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1113);
				expressionList();
				}
			}

			setState(1116);
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
		enterRule(_localctx, 200, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1118);
			match(LBRACK);
			setState(1123);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1121);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
					case 1:
						{
						setState(1119);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1120);
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
				setState(1125);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
			}
			setState(1126);
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
		public TerminalNode STRING() { return getToken(ApexParser.STRING, 0); }
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
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
		enterRule(_localctx, 202, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1128);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BLOB) | (1L << BOOLEAN) | (1L << BREAK) | (1L << BYTE) | (1L << CATCH) | (1L << CHAR) | (1L << CLASS) | (1L << CONST) | (1L << CONTINUE) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << DEFAULT) | (1L << DELETE) | (1L << DO) | (1L << DOUBLE) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FLOAT) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << GOTO) | (1L << ID) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTEGER) | (1L << INTERFACE) | (1L << LONG) | (1L << MERGE) | (1L << NATIVE) | (1L << NEW) | (1L << NULL) | (1L << OBJECT) | (1L << ON) | (1L << OVERRIDE) | (1L << PACKAGE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << RUNAS) | (1L << SELECT) | (1L << SET) | (1L << SHARING) | (1L << SHORT) | (1L << STATIC) | (1L << STRING) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TIME) | (1L << TRANSIENT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (UNDELETE - 64)) | (1L << (UPDATE - 64)) | (1L << (UPSERT - 64)) | (1L << (VIRTUAL - 64)) | (1L << (VOID - 64)) | (1L << (WEBSERVICE - 64)) | (1L << (WHEN - 64)) | (1L << (WHILE - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (Identifier - 64)))) != 0)) ) {
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
		case 80:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0085\u046d\4\2\t"+
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
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\3\2\3\2\3\2\3\3\7\3\u00d3"+
		"\n\3\f\3\16\3\u00d6\13\3\3\3\3\3\7\3\u00da\n\3\f\3\16\3\u00dd\13\3\3\3"+
		"\3\3\7\3\u00e1\n\3\f\3\16\3\u00e4\13\3\3\3\5\3\u00e7\n\3\3\4\3\4\3\4\3"+
		"\4\5\4\u00ed\n\4\3\4\3\4\5\4\u00f1\n\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\u00f9"+
		"\n\5\3\5\3\5\5\5\u00fd\n\5\3\5\5\5\u0100\n\5\3\5\5\5\u0103\n\5\3\5\3\5"+
		"\3\6\3\6\3\6\7\6\u010a\n\6\f\6\16\6\u010d\13\6\3\7\7\7\u0110\n\7\f\7\16"+
		"\7\u0113\13\7\3\7\3\7\5\7\u0117\n\7\3\7\5\7\u011a\n\7\3\b\3\b\7\b\u011e"+
		"\n\b\f\b\16\b\u0121\13\b\3\t\3\t\3\t\3\t\5\t\u0127\n\t\3\t\3\t\3\n\3\n"+
		"\3\n\7\n\u012e\n\n\f\n\16\n\u0131\13\n\3\13\3\13\7\13\u0135\n\13\f\13"+
		"\16\13\u0138\13\13\3\13\3\13\3\f\3\f\7\f\u013e\n\f\f\f\16\f\u0141\13\f"+
		"\3\f\3\f\3\r\3\r\5\r\u0147\n\r\3\r\3\r\7\r\u014b\n\r\f\r\16\r\u014e\13"+
		"\r\3\r\5\r\u0151\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0166\n\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u016f\n\17\3\20\7\20\u0172\n\20\f\20\16"+
		"\20\u0175\13\20\3\20\3\20\5\20\u0179\n\20\3\20\3\20\3\20\3\20\5\20\u017f"+
		"\n\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\5\24\u0190\n\24\3\24\3\24\3\25\7\25\u0195\n\25\f\25\16\25\u0198"+
		"\13\25\3\25\3\25\5\25\u019c\n\25\3\26\3\26\3\26\3\26\3\26\5\26\u01a3\n"+
		"\26\3\27\3\27\3\27\3\27\7\27\u01a9\n\27\f\27\16\27\u01ac\13\27\3\27\3"+
		"\27\3\30\3\30\3\30\7\30\u01b3\n\30\f\30\16\30\u01b6\13\30\3\30\3\30\3"+
		"\30\3\31\3\31\5\31\u01bd\n\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\7\32"+
		"\u01c6\n\32\f\32\16\32\u01c9\13\32\3\33\3\33\3\33\5\33\u01ce\n\33\3\34"+
		"\3\34\5\34\u01d2\n\34\3\35\3\35\3\35\3\35\7\35\u01d8\n\35\f\35\16\35\u01db"+
		"\13\35\3\35\5\35\u01de\n\35\5\35\u01e0\n\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\36\3\36\3\36\5\36\u01ea\n\36\3\37\3\37\7\37\u01ee\n\37\f\37\16\37\u01f1"+
		"\13\37\3 \3 \5 \u01f5\n \3 \3 \3 \5 \u01fa\n \7 \u01fc\n \f \16 \u01ff"+
		"\13 \3!\3!\3\"\3\"\3\"\3\"\3#\3#\5#\u0209\n#\3#\3#\3$\3$\3$\7$\u0210\n"+
		"$\f$\16$\u0213\13$\3%\7%\u0216\n%\f%\16%\u0219\13%\3%\3%\3%\3&\3&\3&\7"+
		"&\u0221\n&\f&\16&\u0224\13&\3\'\3\'\3(\3(\3(\3(\3(\5(\u022d\n(\3(\5(\u0230"+
		"\n(\3)\3)\5)\u0234\n)\3)\7)\u0237\n)\f)\16)\u023a\13)\3*\3*\3*\3*\3+\3"+
		"+\3+\5+\u0243\n+\3,\3,\3,\3,\7,\u0249\n,\f,\16,\u024c\13,\5,\u024e\n,"+
		"\3,\5,\u0251\n,\3,\3,\3-\3-\7-\u0257\n-\f-\16-\u025a\13-\3-\3-\3.\3.\3"+
		".\3/\7/\u0262\n/\f/\16/\u0265\13/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\5\60\u0280\n\60\3\61\3\61\3\61\3\61\3\61\5\61\u0287\n\61"+
		"\3\62\3\62\3\62\3\62\3\62\6\62\u028e\n\62\r\62\16\62\u028f\3\62\3\62\3"+
		"\63\3\63\3\63\5\63\u0297\n\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\6\67"+
		"\u02ae\n\67\r\67\16\67\u02af\3\67\5\67\u02b3\n\67\3\67\5\67\u02b6\n\67"+
		"\38\38\58\u02ba\n8\38\38\39\39\39\39\3:\3:\5:\u02c4\n:\3:\3:\3;\3;\5;"+
		"\u02ca\n;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3>\3>\3?\3?\3?\3?\3@\3@"+
		"\3@\5@\u02e1\n@\3@\3@\3A\3A\3A\3A\3A\3B\3B\3B\5B\u02ed\nB\3B\3B\5B\u02f1"+
		"\nB\3C\3C\3D\3D\3D\3E\3E\3E\3E\3F\7F\u02fd\nF\fF\16F\u0300\13F\3F\3F\5"+
		"F\u0304\nF\3G\3G\3G\5G\u0309\nG\3H\3H\3H\5H\u030e\nH\3I\3I\3I\7I\u0313"+
		"\nI\fI\16I\u0316\13I\3I\3I\3I\3I\3I\3J\3J\3J\7J\u0320\nJ\fJ\16J\u0323"+
		"\13J\3K\3K\3K\3L\3L\5L\u032a\nL\3L\3L\5L\u032e\nL\3L\3L\5L\u0332\nL\5"+
		"L\u0334\nL\3M\3M\5M\u0338\nM\3N\7N\u033b\nN\fN\16N\u033e\13N\3N\3N\3N"+
		"\3N\3N\3O\3O\3P\3P\3P\3P\3Q\3Q\3Q\7Q\u034e\nQ\fQ\16Q\u0351\13Q\3R\3R\3"+
		"R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u0360\nR\3R\3R\3R\3R\3R\3R\3R\3R\3"+
		"R\3R\3R\3R\3R\3R\5R\u0370\nR\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u037c\n"+
		"R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3"+
		"R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u03a4\nR\3R\3R\3R\3"+
		"R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\3R\5R\u03b6\nR\3R\3R\3R\3R\3R\3R\7"+
		"R\u03be\nR\fR\16R\u03c1\13R\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\3S\3S\3S\3S\5S\u03d6\nS\3S\5S\u03d9\nS\3T\3T\3T\3T\3T\3T\3T\3T\3T"+
		"\5T\u03e4\nT\5T\u03e6\nT\3U\3U\3U\7U\u03eb\nU\fU\16U\u03ee\13U\3U\5U\u03f1"+
		"\nU\3V\3V\5V\u03f5\nV\3W\3W\5W\u03f9\nW\3W\3W\3X\3X\3X\3X\3X\3X\3X\3X"+
		"\3X\3X\3X\7X\u0408\nX\fX\16X\u040b\13X\3X\3X\5X\u040f\nX\3Y\3Y\3Y\3Y\7"+
		"Y\u0415\nY\fY\16Y\u0418\13Y\3Y\3Y\3Z\3Z\3Z\3Z\3[\3[\3[\3[\7[\u0424\n["+
		"\f[\16[\u0427\13[\3[\3[\3\\\3\\\5\\\u042d\n\\\3]\3]\5]\u0431\n]\3^\3^"+
		"\3^\5^\u0436\n^\3^\5^\u0439\n^\3_\3_\3_\3`\3`\3`\3`\3a\3a\3a\5a\u0445"+
		"\na\3b\3b\3b\5b\u044a\nb\3c\3c\3c\3c\5c\u0450\nc\5c\u0452\nc\3d\3d\3d"+
		"\3d\3d\5d\u0459\nd\3e\3e\5e\u045d\ne\3e\3e\3f\3f\3f\7f\u0464\nf\ff\16"+
		"f\u0467\13f\3f\3f\3g\3g\3g\3\u0465\3\u00a2h\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|"+
		"~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096"+
		"\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae"+
		"\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6"+
		"\u00c8\u00ca\u00cc\2\r\13\2\4\5\r\17\23\23\36\36$$&&++99??\4\2**LO\3\2"+
		"jm\3\2_`\4\2noss\3\2lm\3\2cg\4\2ZZu\177\3\2jk\3\2VV\4\2\3K\u0080\u0080"+
		"\2\u04cc\2\u00ce\3\2\2\2\4\u00e6\3\2\2\2\6\u00e8\3\2\2\2\b\u00f4\3\2\2"+
		"\2\n\u0106\3\2\2\2\f\u0111\3\2\2\2\16\u011b\3\2\2\2\20\u0122\3\2\2\2\22"+
		"\u012a\3\2\2\2\24\u0132\3\2\2\2\26\u013b\3\2\2\2\30\u0150\3\2\2\2\32\u0165"+
		"\3\2\2\2\34\u016e\3\2\2\2\36\u0173\3\2\2\2 \u0180\3\2\2\2\"\u0184\3\2"+
		"\2\2$\u0188\3\2\2\2&\u018c\3\2\2\2(\u019b\3\2\2\2*\u01a2\3\2\2\2,\u01a4"+
		"\3\2\2\2.\u01af\3\2\2\2\60\u01bc\3\2\2\2\62\u01c2\3\2\2\2\64\u01ca\3\2"+
		"\2\2\66\u01d1\3\2\2\28\u01d3\3\2\2\2:\u01e9\3\2\2\2<\u01ef\3\2\2\2>\u01f2"+
		"\3\2\2\2@\u0200\3\2\2\2B\u0202\3\2\2\2D\u0206\3\2\2\2F\u020c\3\2\2\2H"+
		"\u0217\3\2\2\2J\u021d\3\2\2\2L\u0225\3\2\2\2N\u0227\3\2\2\2P\u0231\3\2"+
		"\2\2R\u023b\3\2\2\2T\u0242\3\2\2\2V\u0244\3\2\2\2X\u0254\3\2\2\2Z\u025d"+
		"\3\2\2\2\\\u0263\3\2\2\2^\u027f\3\2\2\2`\u0281\3\2\2\2b\u0288\3\2\2\2"+
		"d\u0293\3\2\2\2f\u029a\3\2\2\2h\u02a0\3\2\2\2j\u02a4\3\2\2\2l\u02aa\3"+
		"\2\2\2n\u02b7\3\2\2\2p\u02bd\3\2\2\2r\u02c1\3\2\2\2t\u02c7\3\2\2\2v\u02cd"+
		"\3\2\2\2x\u02d1\3\2\2\2z\u02d5\3\2\2\2|\u02d9\3\2\2\2~\u02dd\3\2\2\2\u0080"+
		"\u02e4\3\2\2\2\u0082\u02e9\3\2\2\2\u0084\u02f2\3\2\2\2\u0086\u02f4\3\2"+
		"\2\2\u0088\u02f7\3\2\2\2\u008a\u02fe\3\2\2\2\u008c\u0305\3\2\2\2\u008e"+
		"\u030a\3\2\2\2\u0090\u030f\3\2\2\2\u0092\u031c\3\2\2\2\u0094\u0324\3\2"+
		"\2\2\u0096\u0333\3\2\2\2\u0098\u0337\3\2\2\2\u009a\u033c\3\2\2\2\u009c"+
		"\u0344\3\2\2\2\u009e\u0346\3\2\2\2\u00a0\u034a\3\2\2\2\u00a2\u035f\3\2"+
		"\2\2\u00a4\u03d8\3\2\2\2\u00a6\u03e5\3\2\2\2\u00a8\u03f0\3\2\2\2\u00aa"+
		"\u03f2\3\2\2\2\u00ac\u03f6\3\2\2\2\u00ae\u03fc\3\2\2\2\u00b0\u0410\3\2"+
		"\2\2\u00b2\u041b\3\2\2\2\u00b4\u041f\3\2\2\2\u00b6\u042c\3\2\2\2\u00b8"+
		"\u0430\3\2\2\2\u00ba\u0438\3\2\2\2\u00bc\u043a\3\2\2\2\u00be\u043d\3\2"+
		"\2\2\u00c0\u0444\3\2\2\2\u00c2\u0449\3\2\2\2\u00c4\u0451\3\2\2\2\u00c6"+
		"\u0458\3\2\2\2\u00c8\u045a\3\2\2\2\u00ca\u0460\3\2\2\2\u00cc\u046a\3\2"+
		"\2\2\u00ce\u00cf\5\4\3\2\u00cf\u00d0\7\2\2\3\u00d0\3\3\2\2\2\u00d1\u00d3"+
		"\5\32\16\2\u00d2\u00d1\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2"+
		"\u00d4\u00d5\3\2\2\2\u00d5\u00d7\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00e7"+
		"\5\6\4\2\u00d8\u00da\5\32\16\2\u00d9\u00d8\3\2\2\2\u00da\u00dd\3\2\2\2"+
		"\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00de\3\2\2\2\u00dd\u00db"+
		"\3\2\2\2\u00de\u00e7\5\b\5\2\u00df\u00e1\5\32\16\2\u00e0\u00df\3\2\2\2"+
		"\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e5"+
		"\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00e7\5\20\t\2\u00e6\u00d4\3\2\2\2"+
		"\u00e6\u00db\3\2\2\2\u00e6\u00e2\3\2\2\2\u00e7\5\3\2\2\2\u00e8\u00e9\7"+
		"\n\2\2\u00e9\u00ec\5\u00ccg\2\u00ea\u00eb\7\26\2\2\u00eb\u00ed\5:\36\2"+
		"\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ef"+
		"\7 \2\2\u00ef\u00f1\5\22\n\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1"+
		"\u00f2\3\2\2\2\u00f2\u00f3\5\24\13\2\u00f3\7\3\2\2\2\u00f4\u00f5\7\25"+
		"\2\2\u00f5\u00f8\5\u00ccg\2\u00f6\u00f7\7 \2\2\u00f7\u00f9\5\22\n\2\u00f8"+
		"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fc\7S"+
		"\2\2\u00fb\u00fd\5\n\6\2\u00fc\u00fb\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd"+
		"\u00ff\3\2\2\2\u00fe\u0100\7X\2\2\u00ff\u00fe\3\2\2\2\u00ff\u0100\3\2"+
		"\2\2\u0100\u0102\3\2\2\2\u0101\u0103\5\16\b\2\u0102\u0101\3\2\2\2\u0102"+
		"\u0103\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\7T\2\2\u0105\t\3\2\2\2"+
		"\u0106\u010b\5\f\7\2\u0107\u0108\7X\2\2\u0108\u010a\5\f\7\2\u0109\u0107"+
		"\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\13\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0110\5\32\16\2\u010f\u010e\3\2"+
		"\2\2\u0110\u0113\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112"+
		"\u0114\3\2\2\2\u0113\u0111\3\2\2\2\u0114\u0116\5\u00ccg\2\u0115\u0117"+
		"\5\u00c8e\2\u0116\u0115\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2\2"+
		"\2\u0118\u011a\5\24\13\2\u0119\u0118\3\2\2\2\u0119\u011a\3\2\2\2\u011a"+
		"\r\3\2\2\2\u011b\u011f\7W\2\2\u011c\u011e\5\30\r\2\u011d\u011c\3\2\2\2"+
		"\u011e\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\17"+
		"\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0123\7%\2\2\u0123\u0126\5\u00ccg\2"+
		"\u0124\u0125\7\26\2\2\u0125\u0127\5\22\n\2\u0126\u0124\3\2\2\2\u0126\u0127"+
		"\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\5\26\f\2\u0129\21\3\2\2\2\u012a"+
		"\u012f\5:\36\2\u012b\u012c\7X\2\2\u012c\u012e\5:\36\2\u012d\u012b\3\2"+
		"\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2\2\2\u0130"+
		"\23\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u0136\7S\2\2\u0133\u0135\5\30\r"+
		"\2\u0134\u0133\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137"+
		"\3\2\2\2\u0137\u0139\3\2\2\2\u0138\u0136\3\2\2\2\u0139\u013a\7T\2\2\u013a"+
		"\25\3\2\2\2\u013b\u013f\7S\2\2\u013c\u013e\5(\25\2\u013d\u013c\3\2\2\2"+
		"\u013e\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0142"+
		"\3\2\2\2\u0141\u013f\3\2\2\2\u0142\u0143\7T\2\2\u0143\27\3\2\2\2\u0144"+
		"\u0151\7W\2\2\u0145\u0147\78\2\2\u0146\u0145\3\2\2\2\u0146\u0147\3\2\2"+
		"\2\u0147\u0148\3\2\2\2\u0148\u0151\5X-\2\u0149\u014b\5\32\16\2\u014a\u0149"+
		"\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d"+
		"\u014f\3\2\2\2\u014e\u014c\3\2\2\2\u014f\u0151\5\34\17\2\u0150\u0144\3"+
		"\2\2\2\u0150\u0146\3\2\2\2\u0150\u014c\3\2\2\2\u0151\31\3\2\2\2\u0152"+
		"\u0166\5N(\2\u0153\u0166\7\34\2\2\u0154\u0166\7\61\2\2\u0155\u0166\7\60"+
		"\2\2\u0156\u0166\7/\2\2\u0157\u0166\7@\2\2\u0158\u0166\78\2\2\u0159\u0166"+
		"\7\3\2\2\u015a\u0166\7\27\2\2\u015b\u0166\7G\2\2\u015c\u0166\7-\2\2\u015d"+
		"\u0166\7E\2\2\u015e\u0166\7<\2\2\u015f\u0160\7J\2\2\u0160\u0166\7\66\2"+
		"\2\u0161\u0162\7K\2\2\u0162\u0166\7\66\2\2\u0163\u0164\7!\2\2\u0164\u0166"+
		"\7\66\2\2\u0165\u0152\3\2\2\2\u0165\u0153\3\2\2\2\u0165\u0154\3\2\2\2"+
		"\u0165\u0155\3\2\2\2\u0165\u0156\3\2\2\2\u0165\u0157\3\2\2\2\u0165\u0158"+
		"\3\2\2\2\u0165\u0159\3\2\2\2\u0165\u015a\3\2\2\2\u0165\u015b\3\2\2\2\u0165"+
		"\u015c\3\2\2\2\u0165\u015d\3\2\2\2\u0165\u015e\3\2\2\2\u0165\u015f\3\2"+
		"\2\2\u0165\u0161\3\2\2\2\u0165\u0163\3\2\2\2\u0166\33\3\2\2\2\u0167\u016f"+
		"\5\36\20\2\u0168\u016f\5\"\22\2\u0169\u016f\5 \21\2\u016a\u016f\5\20\t"+
		"\2\u016b\u016f\5\6\4\2\u016c\u016f\5\b\5\2\u016d\u016f\5$\23\2\u016e\u0167"+
		"\3\2\2\2\u016e\u0168\3\2\2\2\u016e\u0169\3\2\2\2\u016e\u016a\3\2\2\2\u016e"+
		"\u016b\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016d\3\2\2\2\u016f\35\3\2\2"+
		"\2\u0170\u0172\5\32\16\2\u0171\u0170\3\2\2\2\u0172\u0175\3\2\2\2\u0173"+
		"\u0171\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0178\3\2\2\2\u0175\u0173\3\2"+
		"\2\2\u0176\u0179\5:\36\2\u0177\u0179\7F\2\2\u0178\u0176\3\2\2\2\u0178"+
		"\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u017b\5\u00ccg\2\u017b\u017e"+
		"\5D#\2\u017c\u017f\5X-\2\u017d\u017f\7W\2\2\u017e\u017c\3\2\2\2\u017e"+
		"\u017d\3\2\2\2\u017f\37\3\2\2\2\u0180\u0181\5J&\2\u0181\u0182\5D#\2\u0182"+
		"\u0183\5X-\2\u0183!\3\2\2\2\u0184\u0185\5:\36\2\u0185\u0186\5\62\32\2"+
		"\u0186\u0187\7W\2\2\u0187#\3\2\2\2\u0188\u0189\5:\36\2\u0189\u018a\5\62"+
		"\32\2\u018a\u018b\5&\24\2\u018b%\3\2\2\2\u018c\u018d\7S\2\2\u018d\u018f"+
		"\5\u008aF\2\u018e\u0190\5\u008aF\2\u018f\u018e\3\2\2\2\u018f\u0190\3\2"+
		"\2\2\u0190\u0191\3\2\2\2\u0191\u0192\7T\2\2\u0192\'\3\2\2\2\u0193\u0195"+
		"\5\32\16\2\u0194\u0193\3\2\2\2\u0195\u0198\3\2\2\2\u0196\u0194\3\2\2\2"+
		"\u0196\u0197\3\2\2\2\u0197\u0199\3\2\2\2\u0198\u0196\3\2\2\2\u0199\u019c"+
		"\5*\26\2\u019a\u019c\7W\2\2\u019b\u0196\3\2\2\2\u019b\u019a\3\2\2\2\u019c"+
		")\3\2\2\2\u019d\u01a3\5,\27\2\u019e\u01a3\5\60\31\2\u019f\u01a3\5\20\t"+
		"\2\u01a0\u01a3\5\6\4\2\u01a1\u01a3\5\b\5\2\u01a2\u019d\3\2\2\2\u01a2\u019e"+
		"\3\2\2\2\u01a2\u019f\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a2\u01a1\3\2\2\2\u01a3"+
		"+\3\2\2\2\u01a4\u01a5\5:\36\2\u01a5\u01aa\5.\30\2\u01a6\u01a7\7X\2\2\u01a7"+
		"\u01a9\5.\30\2\u01a8\u01a6\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa\u01a8\3\2"+
		"\2\2\u01aa\u01ab\3\2\2\2\u01ab\u01ad\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ad"+
		"\u01ae\7W\2\2\u01ae-\3\2\2\2\u01af\u01b4\5\u00ccg\2\u01b0\u01b1\7U\2\2"+
		"\u01b1\u01b3\7V\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01b6\3\2\2\2\u01b4\u01b2"+
		"\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b7\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7"+
		"\u01b8\7Z\2\2\u01b8\u01b9\5\66\34\2\u01b9/\3\2\2\2\u01ba\u01bd\5:\36\2"+
		"\u01bb\u01bd\7F\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bb\3\2\2\2\u01bd\u01be"+
		"\3\2\2\2\u01be\u01bf\5\u00ccg\2\u01bf\u01c0\5D#\2\u01c0\u01c1\7W\2\2\u01c1"+
		"\61\3\2\2\2\u01c2\u01c7\5\64\33\2\u01c3\u01c4\7X\2\2\u01c4\u01c6\5\64"+
		"\33\2\u01c5\u01c3\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7"+
		"\u01c8\3\2\2\2\u01c8\63\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01cd\5\u00cc"+
		"g\2\u01cb\u01cc\7Z\2\2\u01cc\u01ce\5\66\34\2\u01cd\u01cb\3\2\2\2\u01cd"+
		"\u01ce\3\2\2\2\u01ce\65\3\2\2\2\u01cf\u01d2\58\35\2\u01d0\u01d2\5\u00a2"+
		"R\2\u01d1\u01cf\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2\67\3\2\2\2\u01d3\u01df"+
		"\7S\2\2\u01d4\u01d9\5\66\34\2\u01d5\u01d6\7X\2\2\u01d6\u01d8\5\66\34\2"+
		"\u01d7\u01d5\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da"+
		"\3\2\2\2\u01da\u01dd\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc\u01de\7X\2\2\u01dd"+
		"\u01dc\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01e0\3\2\2\2\u01df\u01d4\3\2"+
		"\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e2\7T\2\2\u01e2"+
		"9\3\2\2\2\u01e3\u01e4\5> \2\u01e4\u01e5\5<\37\2\u01e5\u01ea\3\2\2\2\u01e6"+
		"\u01e7\5@!\2\u01e7\u01e8\5<\37\2\u01e8\u01ea\3\2\2\2\u01e9\u01e3\3\2\2"+
		"\2\u01e9\u01e6\3\2\2\2\u01ea;\3\2\2\2\u01eb\u01ec\7U\2\2\u01ec\u01ee\7"+
		"V\2\2\u01ed\u01eb\3\2\2\2\u01ee\u01f1\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef"+
		"\u01f0\3\2\2\2\u01f0=\3\2\2\2\u01f1\u01ef\3\2\2\2\u01f2\u01f4\5\u00cc"+
		"g\2\u01f3\u01f5\5B\"\2\u01f4\u01f3\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5\u01fd"+
		"\3\2\2\2\u01f6\u01f7\7Y\2\2\u01f7\u01f9\5\u00ccg\2\u01f8\u01fa\5B\"\2"+
		"\u01f9\u01f8\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fc\3\2\2\2\u01fb\u01f6"+
		"\3\2\2\2\u01fc\u01ff\3\2\2\2\u01fd\u01fb\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe"+
		"?\3\2\2\2\u01ff\u01fd\3\2\2\2\u0200\u0201\t\2\2\2\u0201A\3\2\2\2\u0202"+
		"\u0203\7^\2\2\u0203\u0204\5\22\n\2\u0204\u0205\7]\2\2\u0205C\3\2\2\2\u0206"+
		"\u0208\7Q\2\2\u0207\u0209\5F$\2\u0208\u0207\3\2\2\2\u0208\u0209\3\2\2"+
		"\2\u0209\u020a\3\2\2\2\u020a\u020b\7R\2\2\u020bE\3\2\2\2\u020c\u0211\5"+
		"H%\2\u020d\u020e\7X\2\2\u020e\u0210\5H%\2\u020f\u020d\3\2\2\2\u0210\u0213"+
		"\3\2\2\2\u0211\u020f\3\2\2\2\u0211\u0212\3\2\2\2\u0212G\3\2\2\2\u0213"+
		"\u0211\3\2\2\2\u0214\u0216\5\32\16\2\u0215\u0214\3\2\2\2\u0216\u0219\3"+
		"\2\2\2\u0217\u0215\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u021a\3\2\2\2\u0219"+
		"\u0217\3\2\2\2\u021a\u021b\5:\36\2\u021b\u021c\5\u00ccg\2\u021cI\3\2\2"+
		"\2\u021d\u0222\5\u00ccg\2\u021e\u021f\7Y\2\2\u021f\u0221\5\u00ccg\2\u0220"+
		"\u021e\3\2\2\2\u0221\u0224\3\2\2\2\u0222\u0220\3\2\2\2\u0222\u0223\3\2"+
		"\2\2\u0223K\3\2\2\2\u0224\u0222\3\2\2\2\u0225\u0226\t\3\2\2\u0226M\3\2"+
		"\2\2\u0227\u0228\7\u0081\2\2\u0228\u022f\5J&\2\u0229\u022c\7Q\2\2\u022a"+
		"\u022d\5P)\2\u022b\u022d\5T+\2\u022c\u022a\3\2\2\2\u022c\u022b\3\2\2\2"+
		"\u022c\u022d\3\2\2\2\u022d\u022e\3\2\2\2\u022e\u0230\7R\2\2\u022f\u0229"+
		"\3\2\2\2\u022f\u0230\3\2\2\2\u0230O\3\2\2\2\u0231\u0238\5R*\2\u0232\u0234"+
		"\7X\2\2\u0233\u0232\3\2\2\2\u0233\u0234\3\2\2\2\u0234\u0235\3\2\2\2\u0235"+
		"\u0237\5R*\2\u0236\u0233\3\2\2\2\u0237\u023a\3\2\2\2\u0238\u0236\3\2\2"+
		"\2\u0238\u0239\3\2\2\2\u0239Q\3\2\2\2\u023a\u0238\3\2\2\2\u023b\u023c"+
		"\5\u00ccg\2\u023c\u023d\7Z\2\2\u023d\u023e\5T+\2\u023eS\3\2\2\2\u023f"+
		"\u0243\5\u00a2R\2\u0240\u0243\5N(\2\u0241\u0243\5V,\2\u0242\u023f\3\2"+
		"\2\2\u0242\u0240\3\2\2\2\u0242\u0241\3\2\2\2\u0243U\3\2\2\2\u0244\u024d"+
		"\7S\2\2\u0245\u024a\5T+\2\u0246\u0247\7X\2\2\u0247\u0249\5T+\2\u0248\u0246"+
		"\3\2\2\2\u0249\u024c\3\2\2\2\u024a\u0248\3\2\2\2\u024a\u024b\3\2\2\2\u024b"+
		"\u024e\3\2\2\2\u024c\u024a\3\2\2\2\u024d\u0245\3\2\2\2\u024d\u024e\3\2"+
		"\2\2\u024e\u0250\3\2\2\2\u024f\u0251\7X\2\2\u0250\u024f\3\2\2\2\u0250"+
		"\u0251\3\2\2\2\u0251\u0252\3\2\2\2\u0252\u0253\7T\2\2\u0253W\3\2\2\2\u0254"+
		"\u0258\7S\2\2\u0255\u0257\5^\60\2\u0256\u0255\3\2\2\2\u0257\u025a\3\2"+
		"\2\2\u0258\u0256\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u025b\3\2\2\2\u025a"+
		"\u0258\3\2\2\2\u025b\u025c\7T\2\2\u025cY\3\2\2\2\u025d\u025e\5\\/\2\u025e"+
		"\u025f\7W\2\2\u025f[\3\2\2\2\u0260\u0262\5\32\16\2\u0261\u0260\3\2\2\2"+
		"\u0262\u0265\3\2\2\2\u0263\u0261\3\2\2\2\u0263\u0264\3\2\2\2\u0264\u0266"+
		"\3\2\2\2\u0265\u0263\3\2\2\2\u0266\u0267\5:\36\2\u0267\u0268\5\62\32\2"+
		"\u0268]\3\2\2\2\u0269\u0280\5X-\2\u026a\u0280\5Z.\2\u026b\u0280\5`\61"+
		"\2\u026c\u0280\5b\62\2\u026d\u0280\5f\64\2\u026e\u0280\5h\65\2\u026f\u0280"+
		"\5j\66\2\u0270\u0280\5l\67\2\u0271\u0280\5n8\2\u0272\u0280\5p9\2\u0273"+
		"\u0280\5r:\2\u0274\u0280\5t;\2\u0275\u0280\5v<\2\u0276\u0280\5x=\2\u0277"+
		"\u0280\5z>\2\u0278\u0280\5|?\2\u0279\u0280\5~@\2\u027a\u0280\5\u0080A"+
		"\2\u027b\u0280\5\u0082B\2\u027c\u0280\5\u0084C\2\u027d\u0280\5\u0086D"+
		"\2\u027e\u0280\5\u0088E\2\u027f\u0269\3\2\2\2\u027f\u026a\3\2\2\2\u027f"+
		"\u026b\3\2\2\2\u027f\u026c\3\2\2\2\u027f\u026d\3\2\2\2\u027f\u026e\3\2"+
		"\2\2\u027f\u026f\3\2\2\2\u027f\u0270\3\2\2\2\u027f\u0271\3\2\2\2\u027f"+
		"\u0272\3\2\2\2\u027f\u0273\3\2\2\2\u027f\u0274\3\2\2\2\u027f\u0275\3\2"+
		"\2\2\u027f\u0276\3\2\2\2\u027f\u0277\3\2\2\2\u027f\u0278\3\2\2\2\u027f"+
		"\u0279\3\2\2\2\u027f\u027a\3\2\2\2\u027f\u027b\3\2\2\2\u027f\u027c\3\2"+
		"\2\2\u027f\u027d\3\2\2\2\u027f\u027e\3\2\2\2\u0280_\3\2\2\2\u0281\u0282"+
		"\7\37\2\2\u0282\u0283\5\u009eP\2\u0283\u0286\5^\60\2\u0284\u0285\7\24"+
		"\2\2\u0285\u0287\5^\60\2\u0286\u0284\3\2\2\2\u0286\u0287\3\2\2\2\u0287"+
		"a\3\2\2\2\u0288\u0289\7;\2\2\u0289\u028a\7,\2\2\u028a\u028b\5\u00a2R\2"+
		"\u028b\u028d\7S\2\2\u028c\u028e\5d\63\2\u028d\u028c\3\2\2\2\u028e\u028f"+
		"\3\2\2\2\u028f\u028d\3\2\2\2\u028f\u0290\3\2\2\2\u0290\u0291\3\2\2\2\u0291"+
		"\u0292\7T\2\2\u0292c\3\2\2\2\u0293\u0296\7H\2\2\u0294\u0297\5\u00a0Q\2"+
		"\u0295\u0297\7\24\2\2\u0296\u0294\3\2\2\2\u0296\u0295\3\2\2\2\u0297\u0298"+
		"\3\2\2\2\u0298\u0299\5X-\2\u0299e\3\2\2\2\u029a\u029b\7\32\2\2\u029b\u029c"+
		"\7Q\2\2\u029c\u029d\5\u0096L\2\u029d\u029e\7R\2\2\u029e\u029f\5^\60\2"+
		"\u029fg\3\2\2\2\u02a0\u02a1\7I\2\2\u02a1\u02a2\5\u009eP\2\u02a2\u02a3"+
		"\5^\60\2\u02a3i\3\2\2\2\u02a4\u02a5\7\22\2\2\u02a5\u02a6\5^\60\2\u02a6"+
		"\u02a7\7I\2\2\u02a7\u02a8\5\u009eP\2\u02a8\u02a9\7W\2\2\u02a9k\3\2\2\2"+
		"\u02aa\u02ab\7A\2\2\u02ab\u02b5\5X-\2\u02ac\u02ae\5\u0090I\2\u02ad\u02ac"+
		"\3\2\2\2\u02ae\u02af\3\2\2\2\u02af\u02ad\3\2\2\2\u02af\u02b0\3\2\2\2\u02b0"+
		"\u02b2\3\2\2\2\u02b1\u02b3\5\u0094K\2\u02b2\u02b1\3\2\2\2\u02b2\u02b3"+
		"\3\2\2\2\u02b3\u02b6\3\2\2\2\u02b4\u02b6\5\u0094K\2\u02b5\u02ad\3\2\2"+
		"\2\u02b5\u02b4\3\2\2\2\u02b6m\3\2\2\2\u02b7\u02b9\7\62\2\2\u02b8\u02ba"+
		"\5\u00a2R\2\u02b9\u02b8\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bb\3\2\2"+
		"\2\u02bb\u02bc\7W\2\2\u02bco\3\2\2\2\u02bd\u02be\7>\2\2\u02be\u02bf\5"+
		"\u00a2R\2\u02bf\u02c0\7W\2\2\u02c0q\3\2\2\2\u02c1\u02c3\7\6\2\2\u02c2"+
		"\u02c4\5\u00ccg\2\u02c3\u02c2\3\2\2\2\u02c3\u02c4\3\2\2\2\u02c4\u02c5"+
		"\3\2\2\2\u02c5\u02c6\7W\2\2\u02c6s\3\2\2\2\u02c7\u02c9\7\f\2\2\u02c8\u02ca"+
		"\5\u00ccg\2\u02c9\u02c8\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cb\3\2\2"+
		"\2\u02cb\u02cc\7W\2\2\u02ccu\3\2\2\2\u02cd\u02ce\7\"\2\2\u02ce\u02cf\5"+
		"\u00a2R\2\u02cf\u02d0\7W\2\2\u02d0w\3\2\2\2\u02d1\u02d2\7C\2\2\u02d2\u02d3"+
		"\5\u00a2R\2\u02d3\u02d4\7W\2\2\u02d4y\3\2\2\2\u02d5\u02d6\7\21\2\2\u02d6"+
		"\u02d7\5\u00a2R\2\u02d7\u02d8\7W\2\2\u02d8{\3\2\2\2\u02d9\u02da\7B\2\2"+
		"\u02da\u02db\5\u00a2R\2\u02db\u02dc\7W\2\2\u02dc}\3\2\2\2\u02dd\u02de"+
		"\7D\2\2\u02de\u02e0\5\u00a2R\2\u02df\u02e1\5\u00ccg\2\u02e0\u02df\3\2"+
		"\2\2\u02e0\u02e1\3\2\2\2\u02e1\u02e2\3\2\2\2\u02e2\u02e3\7W\2\2\u02e3"+
		"\177\3\2\2\2\u02e4\u02e5\7\'\2\2\u02e5\u02e6\5\u00a2R\2\u02e6\u02e7\5"+
		"\u00a2R\2\u02e7\u02e8\7W\2\2\u02e8\u0081\3\2\2\2\u02e9\u02ea\7\63\2\2"+
		"\u02ea\u02ec\7Q\2\2\u02eb\u02ed\5\u00a0Q\2\u02ec\u02eb\3\2\2\2\u02ec\u02ed"+
		"\3\2\2\2\u02ed\u02ee\3\2\2\2\u02ee\u02f0\7R\2\2\u02ef\u02f1\5X-\2\u02f0"+
		"\u02ef\3\2\2\2\u02f0\u02f1\3\2\2\2\u02f1\u0083\3\2\2\2\u02f2\u02f3\7W"+
		"\2\2\u02f3\u0085\3\2\2\2\u02f4\u02f5\5\u00a2R\2\u02f5\u02f6\7W\2\2\u02f6"+
		"\u0087\3\2\2\2\u02f7\u02f8\5\u00ccg\2\u02f8\u02f9\7b\2\2\u02f9\u02fa\5"+
		"^\60\2\u02fa\u0089\3\2\2\2\u02fb\u02fd\5\32\16\2\u02fc\u02fb\3\2\2\2\u02fd"+
		"\u0300\3\2\2\2\u02fe\u02fc\3\2\2\2\u02fe\u02ff\3\2\2\2\u02ff\u0303\3\2"+
		"\2\2\u0300\u02fe\3\2\2\2\u0301\u0304\5\u008cG\2\u0302\u0304\5\u008eH\2"+
		"\u0303\u0301\3\2\2\2\u0303\u0302\3\2\2\2\u0304\u008b\3\2\2\2\u0305\u0308"+
		"\7\33\2\2\u0306\u0309\7W\2\2\u0307\u0309\5X-\2\u0308\u0306\3\2\2\2\u0308"+
		"\u0307\3\2\2\2\u0309\u008d\3\2\2\2\u030a\u030d\7\65\2\2\u030b\u030e\7"+
		"W\2\2\u030c\u030e\5X-\2\u030d\u030b\3\2\2\2\u030d\u030c\3\2\2\2\u030e"+
		"\u008f\3\2\2\2\u030f\u0310\7\b\2\2\u0310\u0314\7Q\2\2\u0311\u0313\5\32"+
		"\16\2\u0312\u0311\3\2\2\2\u0313\u0316\3\2\2\2\u0314\u0312\3\2\2\2\u0314"+
		"\u0315\3\2\2\2\u0315\u0317\3\2\2\2\u0316\u0314\3\2\2\2\u0317\u0318\5\u0092"+
		"J\2\u0318\u0319\5\u00ccg\2\u0319\u031a\7R\2\2\u031a\u031b\5X-\2\u031b"+
		"\u0091\3\2\2\2\u031c\u0321\5J&\2\u031d\u031e\7q\2\2\u031e\u0320\5J&\2"+
		"\u031f\u031d\3\2\2\2\u0320\u0323\3\2\2\2\u0321\u031f\3\2\2\2\u0321\u0322"+
		"\3\2\2\2\u0322\u0093\3\2\2\2\u0323\u0321\3\2\2\2\u0324\u0325\7\30\2\2"+
		"\u0325\u0326\5X-\2\u0326\u0095\3\2\2\2\u0327\u0334\5\u009aN\2\u0328\u032a"+
		"\5\u0098M\2\u0329\u0328\3\2\2\2\u0329\u032a\3\2\2\2\u032a\u032b\3\2\2"+
		"\2\u032b\u032d\7W\2\2\u032c\u032e\5\u00a2R\2\u032d\u032c\3\2\2\2\u032d"+
		"\u032e\3\2\2\2\u032e\u032f\3\2\2\2\u032f\u0331\7W\2\2\u0330\u0332\5\u009c"+
		"O\2\u0331\u0330\3\2\2\2\u0331\u0332\3\2\2\2\u0332\u0334\3\2\2\2\u0333"+
		"\u0327\3\2\2\2\u0333\u0329\3\2\2\2\u0334\u0097\3\2\2\2\u0335\u0338\5\\"+
		"/\2\u0336\u0338\5\u00a0Q\2\u0337\u0335\3\2\2\2\u0337\u0336\3\2\2\2\u0338"+
		"\u0099\3\2\2\2\u0339\u033b\5\32\16\2\u033a\u0339\3\2\2\2\u033b\u033e\3"+
		"\2\2\2\u033c\u033a\3\2\2\2\u033c\u033d\3\2\2\2\u033d\u033f\3\2\2\2\u033e"+
		"\u033c\3\2\2\2\u033f\u0340\5:\36\2\u0340\u0341\5\u00ccg\2\u0341\u0342"+
		"\7b\2\2\u0342\u0343\5\u00a2R\2\u0343\u009b\3\2\2\2\u0344\u0345\5\u00a0"+
		"Q\2\u0345\u009d\3\2\2\2\u0346\u0347\7Q\2\2\u0347\u0348\5\u00a2R\2\u0348"+
		"\u0349\7R\2\2\u0349\u009f\3\2\2\2\u034a\u034f\5\u00a2R\2\u034b\u034c\7"+
		"X\2\2\u034c\u034e\5\u00a2R\2\u034d\u034b\3\2\2\2\u034e\u0351\3\2\2\2\u034f"+
		"\u034d\3\2\2\2\u034f\u0350\3\2\2\2\u0350\u00a1\3\2\2\2\u0351\u034f\3\2"+
		"\2\2\u0352\u0353\bR\1\2\u0353\u0354\7)\2\2\u0354\u0360\5\u00a6T\2\u0355"+
		"\u0356\7Q\2\2\u0356\u0357\5:\36\2\u0357\u0358\7R\2\2\u0358\u0359\5\u00a2"+
		"R\24\u0359\u0360\3\2\2\2\u035a\u035b\t\4\2\2\u035b\u0360\5\u00a2R\22\u035c"+
		"\u035d\t\5\2\2\u035d\u0360\5\u00a2R\21\u035e\u0360\5\u00a4S\2\u035f\u0352"+
		"\3\2\2\2\u035f\u0355\3\2\2\2\u035f\u035a\3\2\2\2\u035f\u035c\3\2\2\2\u035f"+
		"\u035e\3\2\2\2\u0360\u03bf\3\2\2\2\u0361\u0362\f\20\2\2\u0362\u0363\t"+
		"\6\2\2\u0363\u03be\5\u00a2R\21\u0364\u0365\f\17\2\2\u0365\u0366\t\7\2"+
		"\2\u0366\u03be\5\u00a2R\20\u0367\u036f\f\16\2\2\u0368\u0369\7^\2\2\u0369"+
		"\u0370\7^\2\2\u036a\u036b\7]\2\2\u036b\u036c\7]\2\2\u036c\u0370\7]\2\2"+
		"\u036d\u036e\7]\2\2\u036e\u0370\7]\2\2\u036f\u0368\3\2\2\2\u036f\u036a"+
		"\3\2\2\2\u036f\u036d\3\2\2\2\u0370\u0371\3\2\2\2\u0371\u03be\5\u00a2R"+
		"\17\u0372\u037b\f\r\2\2\u0373\u0374\7^\2\2\u0374\u037c\7Z\2\2\u0375\u0376"+
		"\7]\2\2\u0376\u037c\7Z\2\2\u0377\u037c\7[\2\2\u0378\u037c\7\\\2\2\u0379"+
		"\u037c\7]\2\2\u037a\u037c\7^\2\2\u037b\u0373\3\2\2\2\u037b\u0375\3\2\2"+
		"\2\u037b\u0377\3\2\2\2\u037b\u0378\3\2\2\2\u037b\u0379\3\2\2\2\u037b\u037a"+
		"\3\2\2\2\u037c\u037d\3\2\2\2\u037d\u03be\5\u00a2R\16\u037e\u037f\f\13"+
		"\2\2\u037f\u0380\t\b\2\2\u0380\u03be\5\u00a2R\f\u0381\u0382\f\n\2\2\u0382"+
		"\u0383\7p\2\2\u0383\u03be\5\u00a2R\13\u0384\u0385\f\t\2\2\u0385\u0386"+
		"\7r\2\2\u0386\u03be\5\u00a2R\n\u0387\u0388\f\b\2\2\u0388\u0389\7q\2\2"+
		"\u0389\u03be\5\u00a2R\t\u038a\u038b\f\7\2\2\u038b\u038c\7h\2\2\u038c\u03be"+
		"\5\u00a2R\b\u038d\u038e\f\6\2\2\u038e\u038f\7i\2\2\u038f\u03be\5\u00a2"+
		"R\7\u0390\u0391\f\5\2\2\u0391\u0392\7a\2\2\u0392\u0393\5\u00a2R\2\u0393"+
		"\u0394\7b\2\2\u0394\u0395\5\u00a2R\6\u0395\u03be\3\2\2\2\u0396\u0397\f"+
		"\4\2\2\u0397\u0398\t\t\2\2\u0398\u03be\5\u00a2R\4\u0399\u039a\f\34\2\2"+
		"\u039a\u039b\7Y\2\2\u039b\u03be\5\u00ccg\2\u039c\u039d\f\33\2\2\u039d"+
		"\u039e\7Y\2\2\u039e\u03be\7=\2\2\u039f\u03a0\f\32\2\2\u03a0\u03a1\7Y\2"+
		"\2\u03a1\u03a3\7)\2\2\u03a2\u03a4\5\u00be`\2\u03a3\u03a2\3\2\2\2\u03a3"+
		"\u03a4\3\2\2\2\u03a4\u03a5\3\2\2\2\u03a5\u03be\5\u00acW\2\u03a6\u03a7"+
		"\f\31\2\2\u03a7\u03a8\7Y\2\2\u03a8\u03a9\7:\2\2\u03a9\u03be\5\u00c4c\2"+
		"\u03aa\u03ab\f\30\2\2\u03ab\u03ac\7Y\2\2\u03ac\u03be\5\u00bc_\2\u03ad"+
		"\u03ae\f\27\2\2\u03ae\u03af\7U\2\2\u03af\u03b0\5\u00a2R\2\u03b0\u03b1"+
		"\7V\2\2\u03b1\u03be\3\2\2\2\u03b2\u03b3\f\26\2\2\u03b3\u03b5\7Q\2\2\u03b4"+
		"\u03b6\5\u00a0Q\2\u03b5\u03b4\3\2\2\2\u03b5\u03b6\3\2\2\2\u03b6\u03b7"+
		"\3\2\2\2\u03b7\u03be\7R\2\2\u03b8\u03b9\f\23\2\2\u03b9\u03be\t\n\2\2\u03ba"+
		"\u03bb\f\f\2\2\u03bb\u03bc\7#\2\2\u03bc\u03be\5:\36\2\u03bd\u0361\3\2"+
		"\2\2\u03bd\u0364\3\2\2\2\u03bd\u0367\3\2\2\2\u03bd\u0372\3\2\2\2\u03bd"+
		"\u037e\3\2\2\2\u03bd\u0381\3\2\2\2\u03bd\u0384\3\2\2\2\u03bd\u0387\3\2"+
		"\2\2\u03bd\u038a\3\2\2\2\u03bd\u038d\3\2\2\2\u03bd\u0390\3\2\2\2\u03bd"+
		"\u0396\3\2\2\2\u03bd\u0399\3\2\2\2\u03bd\u039c\3\2\2\2\u03bd\u039f\3\2"+
		"\2\2\u03bd\u03a6\3\2\2\2\u03bd\u03aa\3\2\2\2\u03bd\u03ad\3\2\2\2\u03bd"+
		"\u03b2\3\2\2\2\u03bd\u03b8\3\2\2\2\u03bd\u03ba\3\2\2\2\u03be\u03c1\3\2"+
		"\2\2\u03bf\u03bd\3\2\2\2\u03bf\u03c0\3\2\2\2\u03c0\u00a3\3\2\2\2\u03c1"+
		"\u03bf\3\2\2\2\u03c2\u03c3\7Q\2\2\u03c3\u03c4\5\u00a2R\2\u03c4\u03c5\7"+
		"R\2\2\u03c5\u03d9\3\2\2\2\u03c6\u03d9\7=\2\2\u03c7\u03d9\7:\2\2\u03c8"+
		"\u03d9\5L\'\2\u03c9\u03d9\5\u00ccg\2\u03ca\u03cb\5:\36\2\u03cb\u03cc\7"+
		"Y\2\2\u03cc\u03cd\7\n\2\2\u03cd\u03d9\3\2\2\2\u03ce\u03cf\7F\2\2\u03cf"+
		"\u03d0\7Y\2\2\u03d0\u03d9\7\n\2\2\u03d1\u03d5\5\u00be`\2\u03d2\u03d6\5"+
		"\u00c6d\2\u03d3\u03d4\7=\2\2\u03d4\u03d6\5\u00c8e\2\u03d5\u03d2\3\2\2"+
		"\2\u03d5\u03d3\3\2\2\2\u03d6\u03d9\3\2\2\2\u03d7\u03d9\5\u00caf\2\u03d8"+
		"\u03c2\3\2\2\2\u03d8\u03c6\3\2\2\2\u03d8\u03c7\3\2\2\2\u03d8\u03c8\3\2"+
		"\2\2\u03d8\u03c9\3\2\2\2\u03d8\u03ca\3\2\2\2\u03d8\u03ce\3\2\2\2\u03d8"+
		"\u03d1\3\2\2\2\u03d8\u03d7\3\2\2\2\u03d9\u00a5\3\2\2\2\u03da\u03db\5\u00be"+
		"`\2\u03db\u03dc\5\u00a8U\2\u03dc\u03dd\5\u00ba^\2\u03dd\u03e6\3\2\2\2"+
		"\u03de\u03e3\5\u00a8U\2\u03df\u03e4\5\u00aeX\2\u03e0\u03e4\5\u00ba^\2"+
		"\u03e1\u03e4\5\u00b0Y\2\u03e2\u03e4\5\u00b4[\2\u03e3\u03df\3\2\2\2\u03e3"+
		"\u03e0\3\2\2\2\u03e3\u03e1\3\2\2\2\u03e3\u03e2\3\2\2\2\u03e4\u03e6\3\2"+
		"\2\2\u03e5\u03da\3\2\2\2\u03e5\u03de\3\2\2\2\u03e6\u00a7\3\2\2\2\u03e7"+
		"\u03ec\5\u00aaV\2\u03e8\u03e9\7Y\2\2\u03e9\u03eb\5\u00aaV\2\u03ea\u03e8"+
		"\3\2\2\2\u03eb\u03ee\3\2\2\2\u03ec\u03ea\3\2\2\2\u03ec\u03ed\3\2\2\2\u03ed"+
		"\u03f1\3\2\2\2\u03ee\u03ec\3\2\2\2\u03ef\u03f1\5@!\2\u03f0\u03e7\3\2\2"+
		"\2\u03f0\u03ef\3\2\2\2\u03f1\u00a9\3\2\2\2\u03f2\u03f4\5\u00ccg\2\u03f3"+
		"\u03f5\5\u00c0a\2\u03f4\u03f3\3\2\2\2\u03f4\u03f5\3\2\2\2\u03f5\u00ab"+
		"\3\2\2\2\u03f6\u03f8\5\u00ccg\2\u03f7\u03f9\5\u00c2b\2\u03f8\u03f7\3\2"+
		"\2\2\u03f8\u03f9\3\2\2\2\u03f9\u03fa\3\2\2\2\u03fa\u03fb\5\u00ba^\2\u03fb"+
		"\u00ad\3\2\2\2\u03fc\u040e\7U\2\2\u03fd\u03fe\7V\2\2\u03fe\u03ff\5<\37"+
		"\2\u03ff\u0400\58\35\2\u0400\u040f\3\2\2\2\u0401\u0402\5\u00a2R\2\u0402"+
		"\u0409\7V\2\2\u0403\u0404\7U\2\2\u0404\u0405\5\u00a2R\2\u0405\u0406\7"+
		"V\2\2\u0406\u0408\3\2\2\2\u0407\u0403\3\2\2\2\u0408\u040b\3\2\2\2\u0409"+
		"\u0407\3\2\2\2\u0409\u040a\3\2\2\2\u040a\u040c\3\2\2\2\u040b\u0409\3\2"+
		"\2\2\u040c\u040d\5<\37\2\u040d\u040f\3\2\2\2\u040e\u03fd\3\2\2\2\u040e"+
		"\u0401\3\2\2\2\u040f\u00af\3\2\2\2\u0410\u0411\7S\2\2\u0411\u0416\5\u00b2"+
		"Z\2\u0412\u0413\7X\2\2\u0413\u0415\5\u00b2Z\2\u0414\u0412\3\2\2\2\u0415"+
		"\u0418\3\2\2\2\u0416\u0414\3\2\2\2\u0416\u0417\3\2\2\2\u0417\u0419\3\2"+
		"\2\2\u0418\u0416\3\2\2\2\u0419\u041a\7T\2\2\u041a\u00b1\3\2\2\2\u041b"+
		"\u041c\5\u00b8]\2\u041c\u041d\7t\2\2\u041d\u041e\5\u00b6\\\2\u041e\u00b3"+
		"\3\2\2\2\u041f\u0420\7S\2\2\u0420\u0425\5\u00b6\\\2\u0421\u0422\7X\2\2"+
		"\u0422\u0424\5\u00b6\\\2\u0423\u0421\3\2\2\2\u0424\u0427\3\2\2\2\u0425"+
		"\u0423\3\2\2\2\u0425\u0426\3\2\2\2\u0426\u0428\3\2\2\2\u0427\u0425\3\2"+
		"\2\2\u0428\u0429\7T\2\2\u0429\u00b5\3\2\2\2\u042a\u042d\5L\'\2\u042b\u042d"+
		"\5\u00a2R\2\u042c\u042a\3\2\2\2\u042c\u042b\3\2\2\2\u042d\u00b7\3\2\2"+
		"\2\u042e\u0431\5\u00ccg\2\u042f\u0431\5\u00a2R\2\u0430\u042e\3\2\2\2\u0430"+
		"\u042f\3\2\2\2\u0431\u00b9\3\2\2\2\u0432\u0439\5\u00c8e\2\u0433\u0435"+
		"\7S\2\2\u0434\u0436\5\u00a0Q\2\u0435\u0434\3\2\2\2\u0435\u0436\3\2\2\2"+
		"\u0436\u0437\3\2\2\2\u0437\u0439\7T\2\2\u0438\u0432\3\2\2\2\u0438\u0433"+
		"\3\2\2\2\u0439\u00bb\3\2\2\2\u043a\u043b\5\u00be`\2\u043b\u043c\5\u00c6"+
		"d\2\u043c\u00bd\3\2\2\2\u043d\u043e\7^\2\2\u043e\u043f\5\22\n\2\u043f"+
		"\u0440\7]\2\2\u0440\u00bf\3\2\2\2\u0441\u0442\7^\2\2\u0442\u0445\7]\2"+
		"\2\u0443\u0445\5B\"\2\u0444\u0441\3\2\2\2\u0444\u0443\3\2\2\2\u0445\u00c1"+
		"\3\2\2\2\u0446\u0447\7^\2\2\u0447\u044a\7]\2\2\u0448\u044a\5\u00be`\2"+
		"\u0449\u0446\3\2\2\2\u0449\u0448\3\2\2\2\u044a\u00c3\3\2\2\2\u044b\u0452"+
		"\5\u00c8e\2\u044c\u044d\7Y\2\2\u044d\u044f\5\u00ccg\2\u044e\u0450\5\u00c8"+
		"e\2\u044f\u044e\3\2\2\2\u044f\u0450\3\2\2\2\u0450\u0452\3\2\2\2\u0451"+
		"\u044b\3\2\2\2\u0451\u044c\3\2\2\2\u0452\u00c5\3\2\2\2\u0453\u0454\7:"+
		"\2\2\u0454\u0459\5\u00c4c\2\u0455\u0456\5\u00ccg\2\u0456\u0457\5\u00c8"+
		"e\2\u0457\u0459\3\2\2\2\u0458\u0453\3\2\2\2\u0458\u0455\3\2\2\2\u0459"+
		"\u00c7\3\2\2\2\u045a\u045c\7Q\2\2\u045b\u045d\5\u00a0Q\2\u045c\u045b\3"+
		"\2\2\2\u045c\u045d\3\2\2\2\u045d\u045e\3\2\2\2\u045e\u045f\7R\2\2\u045f"+
		"\u00c9\3\2\2\2\u0460\u0465\7U\2\2\u0461\u0464\5\u00caf\2\u0462\u0464\n"+
		"\13\2\2\u0463\u0461\3\2\2\2\u0463\u0462\3\2\2\2\u0464\u0467\3\2\2\2\u0465"+
		"\u0466\3\2\2\2\u0465\u0463\3\2\2\2\u0466\u0468\3\2\2\2\u0467\u0465\3\2"+
		"\2\2\u0468\u0469\7V\2\2\u0469\u00cb\3\2\2\2\u046a\u046b\t\f\2\2\u046b"+
		"\u00cd\3\2\2\2v\u00d4\u00db\u00e2\u00e6\u00ec\u00f0\u00f8\u00fc\u00ff"+
		"\u0102\u010b\u0111\u0116\u0119\u011f\u0126\u012f\u0136\u013f\u0146\u014c"+
		"\u0150\u0165\u016e\u0173\u0178\u017e\u018f\u0196\u019b\u01a2\u01aa\u01b4"+
		"\u01bc\u01c7\u01cd\u01d1\u01d9\u01dd\u01df\u01e9\u01ef\u01f4\u01f9\u01fd"+
		"\u0208\u0211\u0217\u0222\u022c\u022f\u0233\u0238\u0242\u024a\u024d\u0250"+
		"\u0258\u0263\u027f\u0286\u028f\u0296\u02af\u02b2\u02b5\u02b9\u02c3\u02c9"+
		"\u02e0\u02ec\u02f0\u02fe\u0303\u0308\u030d\u0314\u0321\u0329\u032d\u0331"+
		"\u0333\u0337\u033c\u034f\u035f\u036f\u037b\u03a3\u03b5\u03bd\u03bf\u03d5"+
		"\u03d8\u03e3\u03e5\u03ec\u03f0\u03f4\u03f8\u0409\u040e\u0416\u0425\u042c"+
		"\u0430\u0435\u0438\u0444\u0449\u044f\u0451\u0458\u045c\u0463\u0465";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}