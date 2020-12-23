// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/ApexParser.g4 by ANTLR 4.8
package com.nawforce.runtime.parsers;
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
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABSTRACT=1, AFTER=2, BEFORE=3, BREAK=4, CATCH=5, CLASS=6, CONTINUE=7, 
		DELETE=8, DO=9, ELSE=10, ENUM=11, EXTENDS=12, FINAL=13, FINALLY=14, FOR=15, 
		GET=16, GLOBAL=17, IF=18, IMPLEMENTS=19, INHERITED=20, INSERT=21, INSTANCEOF=22, 
		INTERFACE=23, MERGE=24, NEW=25, NULL=26, ON=27, OVERRIDE=28, PRIVATE=29, 
		PROTECTED=30, PUBLIC=31, RETURN=32, SYSTEMRUNAS=33, SET=34, SHARING=35, 
		STATIC=36, SUPER=37, SWITCH=38, TESTMETHOD=39, THIS=40, THROW=41, TRANSIENT=42, 
		TRIGGER=43, TRY=44, UNDELETE=45, UPDATE=46, UPSERT=47, VIRTUAL=48, VOID=49, 
		WEBSERVICE=50, WHEN=51, WHILE=52, WITH=53, WITHOUT=54, LIST=55, MAP=56, 
		SELECT=57, COUNT=58, FROM=59, AS=60, USING=61, SCOPE=62, WHERE=63, ORDER=64, 
		BY=65, LIMIT=66, SOQLAND=67, SOQLOF=68, NOT=69, AVG=70, COUNT_DISTINCT=71, 
		MIN=72, MAX=73, SUM=74, TYPEOF=75, END=76, THEN=77, IntegerLiteral=78, 
		NumberLiteral=79, BooleanLiteral=80, StringLiteral=81, NullLiteral=82, 
		LPAREN=83, RPAREN=84, LBRACE=85, RBRACE=86, LBRACK=87, RBRACK=88, SEMI=89, 
		COMMA=90, DOT=91, ASSIGN=92, GT=93, LT=94, BANG=95, TILDE=96, QUESTIONDOT=97, 
		QUESTION=98, COLON=99, EQUAL=100, TRIPLEEQUAL=101, NOTEQUAL=102, LESSANDGREATER=103, 
		TRIPLENOTEQUAL=104, AND=105, OR=106, INC=107, DEC=108, ADD=109, SUB=110, 
		MUL=111, DIV=112, BITAND=113, BITOR=114, CARET=115, MOD=116, MAPTO=117, 
		ADD_ASSIGN=118, SUB_ASSIGN=119, MUL_ASSIGN=120, DIV_ASSIGN=121, AND_ASSIGN=122, 
		OR_ASSIGN=123, XOR_ASSIGN=124, MOD_ASSIGN=125, LSHIFT_ASSIGN=126, RSHIFT_ASSIGN=127, 
		URSHIFT_ASSIGN=128, AT=129, Identifier=130, WS=131, DOC_COMMENT=132, COMMENT=133, 
		LINE_COMMENT=134;
	public static final int
		RULE_triggerUnit = 0, RULE_triggerCase = 1, RULE_compilationUnit = 2, 
		RULE_typeDeclaration = 3, RULE_classDeclaration = 4, RULE_enumDeclaration = 5, 
		RULE_enumConstants = 6, RULE_interfaceDeclaration = 7, RULE_typeList = 8, 
		RULE_classBody = 9, RULE_interfaceBody = 10, RULE_classBodyDeclaration = 11, 
		RULE_modifier = 12, RULE_memberDeclaration = 13, RULE_methodDeclaration = 14, 
		RULE_constructorDeclaration = 15, RULE_fieldDeclaration = 16, RULE_propertyDeclaration = 17, 
		RULE_interfaceMethodDeclaration = 18, RULE_variableDeclarators = 19, RULE_variableDeclarator = 20, 
		RULE_arrayInitializer = 21, RULE_typeRef = 22, RULE_arraySubscripts = 23, 
		RULE_typeName = 24, RULE_typeArguments = 25, RULE_formalParameters = 26, 
		RULE_formalParameterList = 27, RULE_formalParameter = 28, RULE_qualifiedName = 29, 
		RULE_literal = 30, RULE_annotation = 31, RULE_elementValuePairs = 32, 
		RULE_elementValuePair = 33, RULE_elementValue = 34, RULE_elementValueArrayInitializer = 35, 
		RULE_block = 36, RULE_localVariableDeclarationStatement = 37, RULE_localVariableDeclaration = 38, 
		RULE_statement = 39, RULE_ifStatement = 40, RULE_switchStatement = 41, 
		RULE_whenControl = 42, RULE_whenValue = 43, RULE_whenLiteral = 44, RULE_forStatement = 45, 
		RULE_whileStatement = 46, RULE_doWhileStatement = 47, RULE_tryStatement = 48, 
		RULE_returnStatement = 49, RULE_throwStatement = 50, RULE_breakStatement = 51, 
		RULE_continueStatement = 52, RULE_insertStatement = 53, RULE_updateStatement = 54, 
		RULE_deleteStatement = 55, RULE_undeleteStatement = 56, RULE_upsertStatement = 57, 
		RULE_mergeStatement = 58, RULE_runAsStatement = 59, RULE_expressionStatement = 60, 
		RULE_propertyBlock = 61, RULE_getter = 62, RULE_setter = 63, RULE_catchClause = 64, 
		RULE_finallyBlock = 65, RULE_forControl = 66, RULE_forInit = 67, RULE_enhancedForControl = 68, 
		RULE_forUpdate = 69, RULE_parExpression = 70, RULE_expressionList = 71, 
		RULE_expression = 72, RULE_primary = 73, RULE_methodCall = 74, RULE_dotMethodCall = 75, 
		RULE_creator = 76, RULE_createdName = 77, RULE_idCreatedNamePair = 78, 
		RULE_noRest = 79, RULE_classCreatorRest = 80, RULE_arrayCreatorRest = 81, 
		RULE_mapCreatorRest = 82, RULE_mapCreatorRestPair = 83, RULE_setCreatorRest = 84, 
		RULE_arguments = 85, RULE_soqlLiteral = 86, RULE_query = 87, RULE_subQuery = 88, 
		RULE_fieldList = 89, RULE_fieldEntry = 90, RULE_nameList = 91, RULE_subFieldList = 92, 
		RULE_subFieldEntry = 93, RULE_aggregateFunction = 94, RULE_typeOf = 95, 
		RULE_whenClause = 96, RULE_elseClause = 97, RULE_simpleFieldList = 98, 
		RULE_usingScope = 99, RULE_soqlId = 100, RULE_id = 101, RULE_anyId = 102;
	private static String[] makeRuleNames() {
		return new String[] {
			"triggerUnit", "triggerCase", "compilationUnit", "typeDeclaration", "classDeclaration", 
			"enumDeclaration", "enumConstants", "interfaceDeclaration", "typeList", 
			"classBody", "interfaceBody", "classBodyDeclaration", "modifier", "memberDeclaration", 
			"methodDeclaration", "constructorDeclaration", "fieldDeclaration", "propertyDeclaration", 
			"interfaceMethodDeclaration", "variableDeclarators", "variableDeclarator", 
			"arrayInitializer", "typeRef", "arraySubscripts", "typeName", "typeArguments", 
			"formalParameters", "formalParameterList", "formalParameter", "qualifiedName", 
			"literal", "annotation", "elementValuePairs", "elementValuePair", "elementValue", 
			"elementValueArrayInitializer", "block", "localVariableDeclarationStatement", 
			"localVariableDeclaration", "statement", "ifStatement", "switchStatement", 
			"whenControl", "whenValue", "whenLiteral", "forStatement", "whileStatement", 
			"doWhileStatement", "tryStatement", "returnStatement", "throwStatement", 
			"breakStatement", "continueStatement", "insertStatement", "updateStatement", 
			"deleteStatement", "undeleteStatement", "upsertStatement", "mergeStatement", 
			"runAsStatement", "expressionStatement", "propertyBlock", "getter", "setter", 
			"catchClause", "finallyBlock", "forControl", "forInit", "enhancedForControl", 
			"forUpdate", "parExpression", "expressionList", "expression", "primary", 
			"methodCall", "dotMethodCall", "creator", "createdName", "idCreatedNamePair", 
			"noRest", "classCreatorRest", "arrayCreatorRest", "mapCreatorRest", "mapCreatorRestPair", 
			"setCreatorRest", "arguments", "soqlLiteral", "query", "subQuery", "fieldList", 
			"fieldEntry", "nameList", "subFieldList", "subFieldEntry", "aggregateFunction", 
			"typeOf", "whenClause", "elseClause", "simpleFieldList", "usingScope", 
			"soqlId", "id", "anyId"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'abstract'", "'after'", "'before'", "'break'", "'catch'", "'class'", 
			"'continue'", "'delete'", "'do'", "'else'", "'enum'", "'extends'", "'final'", 
			"'finally'", "'for'", "'get'", "'global'", "'if'", "'implements'", "'inherited'", 
			"'insert'", "'instanceof'", "'interface'", "'merge'", "'new'", "'null'", 
			"'on'", "'override'", "'private'", "'protected'", "'public'", "'return'", 
			"'system.runas'", "'set'", "'sharing'", "'static'", "'super'", "'switch'", 
			"'testmethod'", "'this'", "'throw'", "'transient'", "'trigger'", "'try'", 
			"'undelete'", "'update'", "'upsert'", "'virtual'", "'void'", "'webservice'", 
			"'when'", "'while'", "'with'", "'without'", "'list'", "'map'", "'select'", 
			"'count'", "'from'", "'as'", "'using'", "'scope'", "'where'", "'order'", 
			"'by'", "'limit'", "'and'", "'or'", "'not'", "'avg'", "'count_distinct'", 
			"'min'", "'max'", "'sum'", "'typeof'", "'end'", "'then'", null, null, 
			null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "','", 
			"'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?.'", "'?'", "':'", "'=='", 
			"'==='", "'!='", "'<>'", "'!=='", "'&&'", "'||'", "'++'", "'--'", "'+'", 
			"'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'=>'", "'+='", "'-='", 
			"'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", 
			"'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "AFTER", "BEFORE", "BREAK", "CATCH", "CLASS", "CONTINUE", 
			"DELETE", "DO", "ELSE", "ENUM", "EXTENDS", "FINAL", "FINALLY", "FOR", 
			"GET", "GLOBAL", "IF", "IMPLEMENTS", "INHERITED", "INSERT", "INSTANCEOF", 
			"INTERFACE", "MERGE", "NEW", "NULL", "ON", "OVERRIDE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "SYSTEMRUNAS", "SET", "SHARING", "STATIC", "SUPER", 
			"SWITCH", "TESTMETHOD", "THIS", "THROW", "TRANSIENT", "TRIGGER", "TRY", 
			"UNDELETE", "UPDATE", "UPSERT", "VIRTUAL", "VOID", "WEBSERVICE", "WHEN", 
			"WHILE", "WITH", "WITHOUT", "LIST", "MAP", "SELECT", "COUNT", "FROM", 
			"AS", "USING", "SCOPE", "WHERE", "ORDER", "BY", "LIMIT", "SOQLAND", "SOQLOF", 
			"NOT", "AVG", "COUNT_DISTINCT", "MIN", "MAX", "SUM", "TYPEOF", "END", 
			"THEN", "IntegerLiteral", "NumberLiteral", "BooleanLiteral", "StringLiteral", 
			"NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTIONDOT", 
			"QUESTION", "COLON", "EQUAL", "TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", 
			"TRIPLENOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "MAPTO", "ADD_ASSIGN", "SUB_ASSIGN", 
			"MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
			"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "AT", 
			"Identifier", "WS", "DOC_COMMENT", "COMMENT", "LINE_COMMENT"
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


	    public void clearCache() {
	        _interp.clearDFA();
	    }

	public ApexParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TriggerUnitContext extends ParserRuleContext {
		public TerminalNode TRIGGER() { return getToken(ApexParser.TRIGGER, 0); }
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public TerminalNode ON() { return getToken(ApexParser.ON, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public List<TriggerCaseContext> triggerCase() {
			return getRuleContexts(TriggerCaseContext.class);
		}
		public TriggerCaseContext triggerCase(int i) {
			return getRuleContext(TriggerCaseContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ApexParser.EOF, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public TriggerUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTriggerUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTriggerUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTriggerUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggerUnitContext triggerUnit() throws RecognitionException {
		TriggerUnitContext _localctx = new TriggerUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_triggerUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(TRIGGER);
			setState(207);
			id();
			setState(208);
			match(ON);
			setState(209);
			id();
			setState(210);
			match(LPAREN);
			setState(211);
			triggerCase();
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(212);
				match(COMMA);
				setState(213);
				triggerCase();
				}
				}
				setState(218);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(219);
			match(RPAREN);
			setState(220);
			block();
			setState(221);
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

	public static class TriggerCaseContext extends ParserRuleContext {
		public TerminalNode BEFORE() { return getToken(ApexParser.BEFORE, 0); }
		public TerminalNode AFTER() { return getToken(ApexParser.AFTER, 0); }
		public TerminalNode INSERT() { return getToken(ApexParser.INSERT, 0); }
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public TerminalNode DELETE() { return getToken(ApexParser.DELETE, 0); }
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public TriggerCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTriggerCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTriggerCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTriggerCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggerCaseContext triggerCase() throws RecognitionException {
		TriggerCaseContext _localctx = new TriggerCaseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_triggerCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_la = _input.LA(1);
			if ( !(_la==AFTER || _la==BEFORE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(224);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DELETE) | (1L << INSERT) | (1L << UNDELETE) | (1L << UPDATE))) != 0)) ) {
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

	public static class CompilationUnitContext extends ParserRuleContext {
		public TypeDeclarationContext typeDeclaration() {
			return getRuleContext(TypeDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ApexParser.EOF, 0); }
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCompilationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_compilationUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			typeDeclaration();
			setState(227);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeDeclaration);
		int _la;
		try {
			setState(250);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(229);
					modifier();
					}
					}
					setState(234);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(235);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(236);
					modifier();
					}
					}
					setState(241);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(242);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
					{
					{
					setState(243);
					modifier();
					}
					}
					setState(248);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(249);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(CLASS);
			setState(253);
			id();
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(254);
				match(EXTENDS);
				setState(255);
				typeRef();
				}
			}

			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(258);
				match(IMPLEMENTS);
				setState(259);
				typeList();
				}
			}

			setState(262);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterEnumDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitEnumDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(ENUM);
			setState(265);
			id();
			setState(266);
			match(LBRACE);
			setState(268);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(267);
				enumConstants();
				}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterEnumConstants(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitEnumConstants(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitEnumConstants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumConstantsContext enumConstants() throws RecognitionException {
		EnumConstantsContext _localctx = new EnumConstantsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enumConstants);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			id();
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(273);
				match(COMMA);
				setState(274);
				id();
				}
				}
				setState(279);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterInterfaceDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitInterfaceDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitInterfaceDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDeclarationContext interfaceDeclaration() throws RecognitionException {
		InterfaceDeclarationContext _localctx = new InterfaceDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(INTERFACE);
			setState(281);
			id();
			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(282);
				match(EXTENDS);
				setState(283);
				typeList();
				}
			}

			setState(286);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeListContext typeList() throws RecognitionException {
		TypeListContext _localctx = new TypeListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			typeRef();
			setState(293);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(289);
				match(COMMA);
				setState(290);
				typeRef();
				}
				}
				setState(295);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(LBRACE);
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 85)) & ~0x3f) == 0 && ((1L << (_la - 85)) & ((1L << (LBRACE - 85)) | (1L << (SEMI - 85)) | (1L << (AT - 85)) | (1L << (Identifier - 85)))) != 0)) {
				{
				{
				setState(297);
				classBodyDeclaration();
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(303);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterInterfaceBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitInterfaceBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitInterfaceBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceBodyContext interfaceBody() throws RecognitionException {
		InterfaceBodyContext _localctx = new InterfaceBodyContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			match(LBRACE);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || _la==AT || _la==Identifier) {
				{
				{
				setState(306);
				interfaceMethodDeclaration();
				}
				}
				setState(311);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(312);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterClassBodyDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitClassBodyDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitClassBodyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyDeclarationContext classBodyDeclaration() throws RecognitionException {
		ClassBodyDeclarationContext _localctx = new ClassBodyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(326);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(314);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(315);
					match(STATIC);
					}
				}

				setState(318);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(322);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(319);
						modifier();
						}
						} 
					}
					setState(324);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				setState(325);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_modifier);
		try {
			setState(347);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(329);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(330);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(331);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(332);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(333);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(334);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(335);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(336);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(337);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(338);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(339);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(340);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(341);
				match(WITH);
				setState(342);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(343);
				match(WITHOUT);
				setState(344);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(345);
				match(INHERITED);
				setState(346);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_memberDeclaration);
		try {
			setState(356);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(349);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(350);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(351);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(352);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(353);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(354);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(355);
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
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_methodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case LIST:
			case MAP:
			case Identifier:
				{
				setState(358);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(359);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(362);
			id();
			setState(363);
			formalParameters();
			setState(366);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(364);
				block();
				}
				break;
			case SEMI:
				{
				setState(365);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterConstructorDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitConstructorDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(368);
			qualifiedName();
			setState(369);
			formalParameters();
			setState(370);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			typeRef();
			setState(373);
			variableDeclarators();
			setState(374);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterPropertyDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitPropertyDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitPropertyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyDeclarationContext propertyDeclaration() throws RecognitionException {
		PropertyDeclarationContext _localctx = new PropertyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_propertyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			typeRef();
			setState(377);
			id();
			setState(378);
			match(LBRACE);
			setState(382);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(379);
				propertyBlock();
				}
				}
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(385);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterInterfaceMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitInterfaceMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitInterfaceMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceMethodDeclarationContext interfaceMethodDeclaration() throws RecognitionException {
		InterfaceMethodDeclarationContext _localctx = new InterfaceMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_interfaceMethodDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(387);
					modifier();
					}
					} 
				}
				setState(392);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			setState(395);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case LIST:
			case MAP:
			case Identifier:
				{
				setState(393);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(394);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(397);
			id();
			setState(398);
			formalParameters();
			setState(399);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterVariableDeclarators(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitVariableDeclarators(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitVariableDeclarators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorsContext variableDeclarators() throws RecognitionException {
		VariableDeclaratorsContext _localctx = new VariableDeclaratorsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			variableDeclarator();
			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(402);
				match(COMMA);
				setState(403);
				variableDeclarator();
				}
				}
				setState(408);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterVariableDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitVariableDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			id();
			setState(412);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(410);
				match(ASSIGN);
				setState(411);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayInitializerContext arrayInitializer() throws RecognitionException {
		ArrayInitializerContext _localctx = new ArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			match(LBRACE);
			setState(426);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(415);
				expression(0);
				setState(420);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(416);
						match(COMMA);
						setState(417);
						expression(0);
						}
						} 
					}
					setState(422);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(424);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(423);
					match(COMMA);
					}
				}

				}
			}

			setState(428);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeRefContext typeRef() throws RecognitionException {
		TypeRefContext _localctx = new TypeRefContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_typeRef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			typeName();
			setState(435);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(431);
					match(DOT);
					setState(432);
					typeName();
					}
					} 
				}
				setState(437);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			setState(438);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArraySubscripts(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArraySubscripts(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArraySubscripts(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArraySubscriptsContext arraySubscripts() throws RecognitionException {
		ArraySubscriptsContext _localctx = new ArraySubscriptsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(440);
					match(LBRACK);
					setState(441);
					match(RBRACK);
					}
					} 
				}
				setState(446);
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
		public TerminalNode LIST() { return getToken(ApexParser.LIST, 0); }
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode MAP() { return getToken(ApexParser.MAP, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_typeName);
		try {
			setState(463);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(447);
				match(LIST);
				setState(449);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
				case 1:
					{
					setState(448);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(451);
				match(SET);
				setState(453);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(452);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(455);
				match(MAP);
				setState(457);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
				case 1:
					{
					setState(456);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(459);
				id();
				setState(461);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(460);
					typeArguments();
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			match(LT);
			setState(466);
			typeList();
			setState(467);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFormalParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFormalParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFormalParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParametersContext formalParameters() throws RecognitionException {
		FormalParametersContext _localctx = new FormalParametersContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(469);
			match(LPAREN);
			setState(471);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || _la==AT || _la==Identifier) {
				{
				setState(470);
				formalParameterList();
				}
			}

			setState(473);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFormalParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFormalParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFormalParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterListContext formalParameterList() throws RecognitionException {
		FormalParameterListContext _localctx = new FormalParameterListContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			formalParameter();
			setState(480);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(476);
				match(COMMA);
				setState(477);
				formalParameter();
				}
				}
				setState(482);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFormalParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFormalParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFormalParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParameterContext formalParameter() throws RecognitionException {
		FormalParameterContext _localctx = new FormalParameterContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_formalParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(486);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(483);
					modifier();
					}
					} 
				}
				setState(488);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			setState(489);
			typeRef();
			setState(490);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			id();
			setState(497);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(493);
				match(DOT);
				setState(494);
				id();
				}
				}
				setState(499);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(500);
			_la = _input.LA(1);
			if ( !(((((_la - 26)) & ~0x3f) == 0 && ((1L << (_la - 26)) & ((1L << (NULL - 26)) | (1L << (IntegerLiteral - 26)) | (1L << (NumberLiteral - 26)) | (1L << (BooleanLiteral - 26)) | (1L << (StringLiteral - 26)))) != 0)) ) {
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(502);
			match(AT);
			setState(503);
			qualifiedName();
			setState(510);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(504);
				match(LPAREN);
				setState(507);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(505);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(506);
					elementValue();
					}
					break;
				}
				setState(509);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterElementValuePairs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitElementValuePairs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitElementValuePairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairsContext elementValuePairs() throws RecognitionException {
		ElementValuePairsContext _localctx = new ElementValuePairsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(512);
			elementValuePair();
			setState(519);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==COMMA || _la==Identifier) {
				{
				{
				setState(514);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(513);
					match(COMMA);
					}
				}

				setState(516);
				elementValuePair();
				}
				}
				setState(521);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterElementValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitElementValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitElementValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
			id();
			setState(523);
			match(ASSIGN);
			setState(524);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterElementValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitElementValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitElementValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_elementValue);
		try {
			setState(529);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case NEW:
			case NULL:
			case SET:
			case SHARING:
			case SUPER:
			case SWITCH:
			case THIS:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case LIST:
			case MAP:
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
				setState(526);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(527);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(528);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterElementValueArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitElementValueArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitElementValueArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			match(LBRACE);
			setState(540);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACE - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (AT - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(532);
				elementValue();
				setState(537);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(533);
						match(COMMA);
						setState(534);
						elementValue();
						}
						} 
					}
					setState(539);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				}
				}
			}

			setState(543);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(542);
				match(COMMA);
				}
			}

			setState(545);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(LBRACE);
			setState(551);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << BREAK) | (1L << CONTINUE) | (1L << DELETE) | (1L << DO) | (1L << FINAL) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SYSTEMRUNAS) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACE - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (AT - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				{
				setState(548);
				statement();
				}
				}
				setState(553);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(554);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLocalVariableDeclarationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLocalVariableDeclarationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLocalVariableDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableDeclarationStatementContext localVariableDeclarationStatement() throws RecognitionException {
		LocalVariableDeclarationStatementContext _localctx = new LocalVariableDeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			localVariableDeclaration();
			setState(557);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLocalVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLocalVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLocalVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalVariableDeclarationContext localVariableDeclaration() throws RecognitionException {
		LocalVariableDeclarationContext _localctx = new LocalVariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_localVariableDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(559);
					modifier();
					}
					} 
				}
				setState(564);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			setState(565);
			typeRef();
			setState(566);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_statement);
		try {
			setState(588);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(568);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(569);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(570);
				switchStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(571);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(572);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(573);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(574);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(575);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(576);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(577);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(578);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(579);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(580);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(581);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(582);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(583);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(584);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(585);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(586);
				localVariableDeclarationStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(587);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(590);
			match(IF);
			setState(591);
			parExpression();
			setState(592);
			statement();
			setState(595);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(593);
				match(ELSE);
				setState(594);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597);
			match(SWITCH);
			setState(598);
			match(ON);
			setState(599);
			expression(0);
			setState(600);
			match(LBRACE);
			setState(602); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(601);
				whenControl();
				}
				}
				setState(604); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(606);
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
		public WhenValueContext whenValue() {
			return getRuleContext(WhenValueContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhenControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenControl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhenControl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhenControl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhenControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenControlContext whenControl() throws RecognitionException {
		WhenControlContext _localctx = new WhenControlContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_whenControl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(608);
			match(WHEN);
			setState(609);
			whenValue();
			setState(610);
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

	public static class WhenValueContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public List<WhenLiteralContext> whenLiteral() {
			return getRuleContexts(WhenLiteralContext.class);
		}
		public WhenLiteralContext whenLiteral(int i) {
			return getRuleContext(WhenLiteralContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public WhenValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhenValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhenValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhenValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenValueContext whenValue() throws RecognitionException {
		WhenValueContext _localctx = new WhenValueContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_whenValue);
		int _la;
		try {
			setState(624);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(612);
				match(ELSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(613);
				whenLiteral();
				setState(618);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(614);
					match(COMMA);
					setState(615);
					whenLiteral();
					}
					}
					setState(620);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(621);
				id();
				setState(622);
				id();
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

	public static class WhenLiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public TerminalNode StringLiteral() { return getToken(ApexParser.StringLiteral, 0); }
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public WhenLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhenLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhenLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhenLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenLiteralContext whenLiteral() throws RecognitionException {
		WhenLiteralContext _localctx = new WhenLiteralContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_whenLiteral);
		int _la;
		try {
			setState(633);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case SUB:
				enterOuterAlt(_localctx, 1);
				{
				setState(627);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SUB) {
					{
					setState(626);
					match(SUB);
					}
				}

				setState(629);
				match(IntegerLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(630);
				match(StringLiteral);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(631);
				match(NULL);
				}
				break;
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(632);
				id();
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(635);
			match(FOR);
			setState(636);
			match(LPAREN);
			setState(637);
			forControl();
			setState(638);
			match(RPAREN);
			setState(639);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(641);
			match(WHILE);
			setState(642);
			parExpression();
			setState(643);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDoWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDoWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileStatementContext doWhileStatement() throws RecognitionException {
		DoWhileStatementContext _localctx = new DoWhileStatementContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(645);
			match(DO);
			setState(646);
			statement();
			setState(647);
			match(WHILE);
			setState(648);
			parExpression();
			setState(649);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(651);
			match(TRY);
			setState(652);
			block();
			setState(662);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(654); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(653);
					catchClause();
					}
					}
					setState(656); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CATCH );
				setState(659);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(658);
					finallyBlock();
					}
				}

				}
				break;
			case FINALLY:
				{
				setState(661);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(664);
			match(RETURN);
			setState(666);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(665);
				expression(0);
				}
			}

			setState(668);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterThrowStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitThrowStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitThrowStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowStatementContext throwStatement() throws RecognitionException {
		ThrowStatementContext _localctx = new ThrowStatementContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(670);
			match(THROW);
			setState(671);
			expression(0);
			setState(672);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(674);
			match(BREAK);
			setState(675);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(677);
			match(CONTINUE);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterInsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitInsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(680);
			match(INSERT);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterUpdateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitUpdateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitUpdateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateStatementContext updateStatement() throws RecognitionException {
		UpdateStatementContext _localctx = new UpdateStatementContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(684);
			match(UPDATE);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDeleteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDeleteStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteStatementContext deleteStatement() throws RecognitionException {
		DeleteStatementContext _localctx = new DeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(688);
			match(DELETE);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterUndeleteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitUndeleteStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitUndeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UndeleteStatementContext undeleteStatement() throws RecognitionException {
		UndeleteStatementContext _localctx = new UndeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(692);
			match(UNDELETE);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterUpsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitUpsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitUpsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpsertStatementContext upsertStatement() throws RecognitionException {
		UpsertStatementContext _localctx = new UpsertStatementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(696);
			match(UPSERT);
			setState(697);
			expression(0);
			setState(699);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) {
				{
				setState(698);
				qualifiedName();
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMergeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMergeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMergeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MergeStatementContext mergeStatement() throws RecognitionException {
		MergeStatementContext _localctx = new MergeStatementContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703);
			match(MERGE);
			setState(704);
			expression(0);
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

	public static class RunAsStatementContext extends ParserRuleContext {
		public TerminalNode SYSTEMRUNAS() { return getToken(ApexParser.SYSTEMRUNAS, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public RunAsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_runAsStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterRunAsStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitRunAsStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitRunAsStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RunAsStatementContext runAsStatement() throws RecognitionException {
		RunAsStatementContext _localctx = new RunAsStatementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(708);
			match(SYSTEMRUNAS);
			setState(709);
			match(LPAREN);
			setState(711);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(710);
				expressionList();
				}
			}

			setState(713);
			match(RPAREN);
			setState(714);
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

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApexParser.SEMI, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterPropertyBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitPropertyBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitPropertyBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyBlockContext propertyBlock() throws RecognitionException {
		PropertyBlockContext _localctx = new PropertyBlockContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(722);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==AT) {
				{
				{
				setState(719);
				modifier();
				}
				}
				setState(724);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(727);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(725);
				getter();
				}
				break;
			case SET:
				{
				setState(726);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterGetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitGetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitGetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetterContext getter() throws RecognitionException {
		GetterContext _localctx = new GetterContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(729);
			match(GET);
			setState(732);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(730);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(731);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetterContext setter() throws RecognitionException {
		SetterContext _localctx = new SetterContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(734);
			match(SET);
			setState(737);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(735);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(736);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCatchClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCatchClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchClauseContext catchClause() throws RecognitionException {
		CatchClauseContext _localctx = new CatchClauseContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_catchClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(739);
			match(CATCH);
			setState(740);
			match(LPAREN);
			setState(744);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(741);
					modifier();
					}
					} 
				}
				setState(746);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			}
			setState(747);
			qualifiedName();
			setState(748);
			id();
			setState(749);
			match(RPAREN);
			setState(750);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFinallyBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFinallyBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFinallyBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyBlockContext finallyBlock() throws RecognitionException {
		FinallyBlockContext _localctx = new FinallyBlockContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(752);
			match(FINALLY);
			setState(753);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterForControl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitForControl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_forControl);
		int _la;
		try {
			setState(767);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(755);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(757);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (AT - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(756);
					forInit();
					}
				}

				setState(759);
				match(SEMI);
				setState(761);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(760);
					expression(0);
					}
				}

				setState(763);
				match(SEMI);
				setState(765);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(764);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_forInit);
		try {
			setState(771);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(769);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(770);
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
		public EnhancedForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enhancedForControl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterEnhancedForControl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitEnhancedForControl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitEnhancedForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnhancedForControlContext enhancedForControl() throws RecognitionException {
		EnhancedForControlContext _localctx = new EnhancedForControlContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_enhancedForControl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(773);
			typeRef();
			setState(774);
			id();
			setState(775);
			match(COLON);
			setState(776);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterForUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitForUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitForUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForUpdateContext forUpdate() throws RecognitionException {
		ForUpdateContext _localctx = new ForUpdateContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(778);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterParExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitParExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitParExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParExpressionContext parExpression() throws RecognitionException {
		ParExpressionContext _localctx = new ParExpressionContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780);
			match(LPAREN);
			setState(781);
			expression(0);
			setState(782);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(784);
			expression(0);
			setState(789);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(785);
				match(COMMA);
				setState(786);
				expression(0);
				}
				}
				setState(791);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArth1Expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArth1Expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArth1Expression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DotExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode QUESTIONDOT() { return getToken(ApexParser.QUESTIONDOT, 0); }
		public DotMethodCallContext dotMethodCall() {
			return getRuleContext(DotMethodCallContext.class,0);
		}
		public AnyIdContext anyId() {
			return getRuleContext(AnyIdContext.class,0);
		}
		public DotExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDotExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBitOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBitOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBitOrExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArrayExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArrayExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArrayExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewExpressionContext extends ExpressionContext {
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitNewExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterAssignExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitAssignExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitAssignExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodCallExpressionContext extends ExpressionContext {
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public MethodCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMethodCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMethodCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMethodCallExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBitNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBitNotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBitNotExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArth2Expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArth2Expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArth2Expression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLogAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLogAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLogAndExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCastExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCastExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBitAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CmpExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public CmpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCmpExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCmpExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCmpExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBitExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBitExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLogOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLogOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLogOrExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCondExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCondExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCondExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PostOpExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode INC() { return getToken(ApexParser.INC, 0); }
		public TerminalNode DEC() { return getToken(ApexParser.DEC, 0); }
		public PostOpExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterPostOpExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitPostOpExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitPostOpExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegExpressionContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TILDE() { return getToken(ApexParser.TILDE, 0); }
		public TerminalNode BANG() { return getToken(ApexParser.BANG, 0); }
		public NegExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterNegExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitNegExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitNegExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterPreOpExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitPreOpExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitPreOpExpression(this);
			else return visitor.visitChildren(this);
		}
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterInstanceOfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitInstanceOfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitInstanceOfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 144;
		enterRecursionRule(_localctx, 144, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(806);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				{
				_localctx = new PrimaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(793);
				primary();
				}
				break;
			case 2:
				{
				_localctx = new MethodCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(794);
				methodCall();
				}
				break;
			case 3:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(795);
				match(NEW);
				setState(796);
				creator();
				}
				break;
			case 4:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(797);
				match(LPAREN);
				setState(798);
				typeRef();
				setState(799);
				match(RPAREN);
				setState(800);
				expression(17);
				}
				break;
			case 5:
				{
				_localctx = new PreOpExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(802);
				_la = _input.LA(1);
				if ( !(((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & ((1L << (INC - 107)) | (1L << (DEC - 107)) | (1L << (ADD - 107)) | (1L << (SUB - 107)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(803);
				expression(15);
				}
				break;
			case 6:
				{
				_localctx = new NegExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(804);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(805);
				expression(14);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(876);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,78,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(874);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
					case 1:
						{
						_localctx = new Arth1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(808);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(809);
						_la = _input.LA(1);
						if ( !(((((_la - 111)) & ~0x3f) == 0 && ((1L << (_la - 111)) & ((1L << (MUL - 111)) | (1L << (DIV - 111)) | (1L << (MOD - 111)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(810);
						expression(14);
						}
						break;
					case 2:
						{
						_localctx = new Arth2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(811);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(812);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(813);
						expression(13);
						}
						break;
					case 3:
						{
						_localctx = new BitExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(814);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(822);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
						case 1:
							{
							setState(815);
							match(LT);
							setState(816);
							match(LT);
							}
							break;
						case 2:
							{
							setState(817);
							match(GT);
							setState(818);
							match(GT);
							setState(819);
							match(GT);
							}
							break;
						case 3:
							{
							setState(820);
							match(GT);
							setState(821);
							match(GT);
							}
							break;
						}
						setState(824);
						expression(12);
						}
						break;
					case 4:
						{
						_localctx = new CmpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(825);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(826);
						_la = _input.LA(1);
						if ( !(_la==GT || _la==LT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(828);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==ASSIGN) {
							{
							setState(827);
							match(ASSIGN);
							}
						}

						setState(830);
						expression(11);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(831);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(832);
						_la = _input.LA(1);
						if ( !(((((_la - 100)) & ~0x3f) == 0 && ((1L << (_la - 100)) & ((1L << (EQUAL - 100)) | (1L << (TRIPLEEQUAL - 100)) | (1L << (NOTEQUAL - 100)) | (1L << (LESSANDGREATER - 100)) | (1L << (TRIPLENOTEQUAL - 100)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(833);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new BitAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(834);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(835);
						match(BITAND);
						setState(836);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new BitNotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(837);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(838);
						match(CARET);
						setState(839);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new BitOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(840);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(841);
						match(BITOR);
						setState(842);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new LogAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(843);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(844);
						match(AND);
						setState(845);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new LogOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(846);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(847);
						match(OR);
						setState(848);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new CondExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(849);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(850);
						match(QUESTION);
						setState(851);
						expression(0);
						setState(852);
						match(COLON);
						setState(853);
						expression(2);
						}
						break;
					case 12:
						{
						_localctx = new AssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(855);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(856);
						_la = _input.LA(1);
						if ( !(((((_la - 92)) & ~0x3f) == 0 && ((1L << (_la - 92)) & ((1L << (ASSIGN - 92)) | (1L << (ADD_ASSIGN - 92)) | (1L << (SUB_ASSIGN - 92)) | (1L << (MUL_ASSIGN - 92)) | (1L << (DIV_ASSIGN - 92)) | (1L << (AND_ASSIGN - 92)) | (1L << (OR_ASSIGN - 92)) | (1L << (XOR_ASSIGN - 92)) | (1L << (MOD_ASSIGN - 92)) | (1L << (LSHIFT_ASSIGN - 92)) | (1L << (RSHIFT_ASSIGN - 92)) | (1L << (URSHIFT_ASSIGN - 92)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(857);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new DotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(858);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(859);
						_la = _input.LA(1);
						if ( !(_la==DOT || _la==QUESTIONDOT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(862);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
						case 1:
							{
							setState(860);
							dotMethodCall();
							}
							break;
						case 2:
							{
							setState(861);
							anyId();
							}
							break;
						}
						}
						break;
					case 14:
						{
						_localctx = new ArrayExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(864);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(865);
						match(LBRACK);
						setState(866);
						expression(0);
						setState(867);
						match(RBRACK);
						}
						break;
					case 15:
						{
						_localctx = new PostOpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(869);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(870);
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
						setState(871);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(872);
						match(INSTANCEOF);
						setState(873);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(878);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,78,_ctx);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterThisPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitThisPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitThisPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SoqlPrimaryContext extends PrimaryContext {
		public SoqlLiteralContext soqlLiteral() {
			return getRuleContext(SoqlLiteralContext.class,0);
		}
		public SoqlPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSoqlPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSoqlPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSoqlPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuperPrimaryContext extends PrimaryContext {
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public SuperPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSuperPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSuperPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSuperPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeRefPrimaryContext extends PrimaryContext {
		public TypeRefContext typeRef() {
			return getRuleContext(TypeRefContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ApexParser.DOT, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public TypeRefPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeRefPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeRefPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeRefPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdPrimaryContext extends PrimaryContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public IdPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterIdPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitIdPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitIdPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralPrimaryContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLiteralPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLiteralPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLiteralPrimary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubPrimaryContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public SubPrimaryContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSubPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSubPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSubPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_primary);
		try {
			setState(892);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				_localctx = new SubPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(879);
				match(LPAREN);
				setState(880);
				expression(0);
				setState(881);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ThisPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(883);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new SuperPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(884);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new LiteralPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(885);
				literal();
				}
				break;
			case 5:
				_localctx = new TypeRefPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(886);
				typeRef();
				setState(887);
				match(DOT);
				setState(888);
				match(CLASS);
				}
				break;
			case 6:
				_localctx = new IdPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(890);
				id();
				}
				break;
			case 7:
				_localctx = new SoqlPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(891);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_methodCall);
		int _la;
		try {
			setState(913);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(894);
				id();
				setState(895);
				match(LPAREN);
				setState(897);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(896);
					expressionList();
					}
				}

				setState(899);
				match(RPAREN);
				}
				break;
			case THIS:
				enterOuterAlt(_localctx, 2);
				{
				setState(901);
				match(THIS);
				setState(902);
				match(LPAREN);
				setState(904);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(903);
					expressionList();
					}
				}

				setState(906);
				match(RPAREN);
				}
				break;
			case SUPER:
				enterOuterAlt(_localctx, 3);
				{
				setState(907);
				match(SUPER);
				setState(908);
				match(LPAREN);
				setState(910);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
					{
					setState(909);
					expressionList();
					}
				}

				setState(912);
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

	public static class DotMethodCallContext extends ParserRuleContext {
		public AnyIdContext anyId() {
			return getRuleContext(AnyIdContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public DotMethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dotMethodCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDotMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDotMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDotMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DotMethodCallContext dotMethodCall() throws RecognitionException {
		DotMethodCallContext _localctx = new DotMethodCallContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_dotMethodCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(915);
			anyId();
			setState(916);
			match(LPAREN);
			setState(918);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(917);
				expressionList();
				}
			}

			setState(920);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_creator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			createdName();
			setState(928);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				{
				setState(923);
				noRest();
				}
				break;
			case 2:
				{
				setState(924);
				classCreatorRest();
				}
				break;
			case 3:
				{
				setState(925);
				arrayCreatorRest();
				}
				break;
			case 4:
				{
				setState(926);
				mapCreatorRest();
				}
				break;
			case 5:
				{
				setState(927);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCreatedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCreatedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCreatedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatedNameContext createdName() throws RecognitionException {
		CreatedNameContext _localctx = new CreatedNameContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_createdName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(930);
			idCreatedNamePair();
			setState(935);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(931);
				match(DOT);
				setState(932);
				idCreatedNamePair();
				}
				}
				setState(937);
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
		public AnyIdContext anyId() {
			return getRuleContext(AnyIdContext.class,0);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterIdCreatedNamePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitIdCreatedNamePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitIdCreatedNamePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdCreatedNamePairContext idCreatedNamePair() throws RecognitionException {
		IdCreatedNamePairContext _localctx = new IdCreatedNamePairContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(938);
			anyId();
			setState(943);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(939);
				match(LT);
				setState(940);
				typeList();
				setState(941);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterNoRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitNoRest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitNoRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NoRestContext noRest() throws RecognitionException {
		NoRestContext _localctx = new NoRestContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_noRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(945);
			match(LBRACE);
			setState(946);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterClassCreatorRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitClassCreatorRest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitClassCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassCreatorRestContext classCreatorRest() throws RecognitionException {
		ClassCreatorRestContext _localctx = new ClassCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_classCreatorRest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(948);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArrayCreatorRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArrayCreatorRest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArrayCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayCreatorRestContext arrayCreatorRest() throws RecognitionException {
		ArrayCreatorRestContext _localctx = new ArrayCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_arrayCreatorRest);
		try {
			setState(959);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(950);
				match(LBRACK);
				setState(951);
				expression(0);
				setState(952);
				match(RBRACK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(954);
				match(LBRACK);
				setState(955);
				match(RBRACK);
				setState(957);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
				case 1:
					{
					setState(956);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMapCreatorRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMapCreatorRest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMapCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapCreatorRestContext mapCreatorRest() throws RecognitionException {
		MapCreatorRestContext _localctx = new MapCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(961);
			match(LBRACE);
			setState(962);
			mapCreatorRestPair();
			setState(967);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(963);
				match(COMMA);
				setState(964);
				mapCreatorRestPair();
				}
				}
				setState(969);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(970);
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
		public TerminalNode MAPTO() { return getToken(ApexParser.MAPTO, 0); }
		public MapCreatorRestPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapCreatorRestPair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterMapCreatorRestPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitMapCreatorRestPair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitMapCreatorRestPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapCreatorRestPairContext mapCreatorRestPair() throws RecognitionException {
		MapCreatorRestPairContext _localctx = new MapCreatorRestPairContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(972);
			expression(0);
			setState(973);
			match(MAPTO);
			setState(974);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSetCreatorRest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSetCreatorRest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSetCreatorRest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetCreatorRestContext setCreatorRest() throws RecognitionException {
		SetCreatorRestContext _localctx = new SetCreatorRestContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(976);
			match(LBRACE);
			setState(977);
			expression(0);
			setState(982);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(978);
				match(COMMA);
				{
				setState(979);
				expression(0);
				}
				}
				}
				setState(984);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(985);
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
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(987);
			match(LPAREN);
			setState(989);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || ((((_la - 78)) & ~0x3f) == 0 && ((1L << (_la - 78)) & ((1L << (IntegerLiteral - 78)) | (1L << (NumberLiteral - 78)) | (1L << (BooleanLiteral - 78)) | (1L << (StringLiteral - 78)) | (1L << (LPAREN - 78)) | (1L << (LBRACK - 78)) | (1L << (BANG - 78)) | (1L << (TILDE - 78)) | (1L << (INC - 78)) | (1L << (DEC - 78)) | (1L << (ADD - 78)) | (1L << (SUB - 78)) | (1L << (Identifier - 78)))) != 0)) {
				{
				setState(988);
				expressionList();
				}
			}

			setState(991);
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
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ApexParser.RBRACK, 0); }
		public SoqlLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_soqlLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSoqlLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSoqlLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSoqlLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SoqlLiteralContext soqlLiteral() throws RecognitionException {
		SoqlLiteralContext _localctx = new SoqlLiteralContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_soqlLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(993);
			match(LBRACK);
			setState(994);
			query();
			setState(995);
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

	public static class QueryContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(ApexParser.FROM, 0); }
		public NameListContext nameList() {
			return getRuleContext(NameListContext.class,0);
		}
		public UsingScopeContext usingScope() {
			return getRuleContext(UsingScopeContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(997);
			match(SELECT);
			setState(998);
			fieldList();
			setState(999);
			match(FROM);
			setState(1000);
			nameList();
			setState(1002);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==USING) {
				{
				setState(1001);
				usingScope();
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

	public static class SubQueryContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public SubFieldListContext subFieldList() {
			return getRuleContext(SubFieldListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(ApexParser.FROM, 0); }
		public NameListContext nameList() {
			return getRuleContext(NameListContext.class,0);
		}
		public SubQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSubQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSubQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSubQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubQueryContext subQuery() throws RecognitionException {
		SubQueryContext _localctx = new SubQueryContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_subQuery);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1004);
			match(SELECT);
			setState(1005);
			subFieldList();
			setState(1006);
			match(FROM);
			setState(1007);
			nameList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldListContext extends ParserRuleContext {
		public List<FieldEntryContext> fieldEntry() {
			return getRuleContexts(FieldEntryContext.class);
		}
		public FieldEntryContext fieldEntry(int i) {
			return getRuleContext(FieldEntryContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public FieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldListContext fieldList() throws RecognitionException {
		FieldListContext _localctx = new FieldListContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_fieldList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1009);
			fieldEntry();
			setState(1014);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1010);
				match(COMMA);
				setState(1011);
				fieldEntry();
				}
				}
				setState(1016);
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

	public static class FieldEntryContext extends ParserRuleContext {
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public AggregateFunctionContext aggregateFunction() {
			return getRuleContext(AggregateFunctionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public SubQueryContext subQuery() {
			return getRuleContext(SubQueryContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public TypeOfContext typeOf() {
			return getRuleContext(TypeOfContext.class,0);
		}
		public FieldEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldEntryContext fieldEntry() throws RecognitionException {
		FieldEntryContext _localctx = new FieldEntryContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_fieldEntry);
		try {
			setState(1024);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1017);
				soqlId();
				}
				break;
			case COUNT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(1018);
				aggregateFunction();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(1019);
				match(LPAREN);
				setState(1020);
				subQuery();
				setState(1021);
				match(RPAREN);
				}
				break;
			case TYPEOF:
				enterOuterAlt(_localctx, 4);
				{
				setState(1023);
				typeOf();
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

	public static class NameListContext extends ParserRuleContext {
		public List<SoqlIdContext> soqlId() {
			return getRuleContexts(SoqlIdContext.class);
		}
		public SoqlIdContext soqlId(int i) {
			return getRuleContext(SoqlIdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public NameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitNameList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameListContext nameList() throws RecognitionException {
		NameListContext _localctx = new NameListContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_nameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1026);
			soqlId();
			setState(1031);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1027);
				match(COMMA);
				setState(1028);
				soqlId();
				}
				}
				setState(1033);
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

	public static class SubFieldListContext extends ParserRuleContext {
		public List<SubFieldEntryContext> subFieldEntry() {
			return getRuleContexts(SubFieldEntryContext.class);
		}
		public SubFieldEntryContext subFieldEntry(int i) {
			return getRuleContext(SubFieldEntryContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public SubFieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subFieldList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSubFieldList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSubFieldList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSubFieldList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubFieldListContext subFieldList() throws RecognitionException {
		SubFieldListContext _localctx = new SubFieldListContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_subFieldList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1034);
			subFieldEntry();
			setState(1039);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1035);
				match(COMMA);
				setState(1036);
				subFieldEntry();
				}
				}
				setState(1041);
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

	public static class SubFieldEntryContext extends ParserRuleContext {
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public AggregateFunctionContext aggregateFunction() {
			return getRuleContext(AggregateFunctionContext.class,0);
		}
		public SubFieldEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subFieldEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSubFieldEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSubFieldEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSubFieldEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubFieldEntryContext subFieldEntry() throws RecognitionException {
		SubFieldEntryContext _localctx = new SubFieldEntryContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_subFieldEntry);
		try {
			setState(1044);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AFTER:
			case BEFORE:
			case GET:
			case INHERITED:
			case INSTANCEOF:
			case SET:
			case SHARING:
			case SWITCH:
			case TRANSIENT:
			case TRIGGER:
			case WHEN:
			case WITH:
			case WITHOUT:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1042);
				soqlId();
				}
				break;
			case COUNT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(1043);
				aggregateFunction();
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

	public static class AggregateFunctionContext extends ParserRuleContext {
		public TerminalNode AVG() { return getToken(ApexParser.AVG, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public TerminalNode COUNT() { return getToken(ApexParser.COUNT, 0); }
		public TerminalNode COUNT_DISTINCT() { return getToken(ApexParser.COUNT_DISTINCT, 0); }
		public TerminalNode MIN() { return getToken(ApexParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(ApexParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(ApexParser.SUM, 0); }
		public AggregateFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregateFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterAggregateFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitAggregateFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitAggregateFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AggregateFunctionContext aggregateFunction() throws RecognitionException {
		AggregateFunctionContext _localctx = new AggregateFunctionContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_aggregateFunction);
		try {
			setState(1079);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1046);
				match(AVG);
				setState(1047);
				match(LPAREN);
				setState(1048);
				soqlId();
				setState(1049);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1051);
				match(COUNT);
				setState(1052);
				match(LPAREN);
				setState(1053);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1054);
				match(COUNT);
				setState(1055);
				match(LPAREN);
				setState(1056);
				soqlId();
				setState(1057);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1059);
				match(COUNT_DISTINCT);
				setState(1060);
				match(LPAREN);
				setState(1061);
				soqlId();
				setState(1062);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1064);
				match(MIN);
				setState(1065);
				match(LPAREN);
				setState(1066);
				soqlId();
				setState(1067);
				match(RPAREN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1069);
				match(MAX);
				setState(1070);
				match(LPAREN);
				setState(1071);
				soqlId();
				setState(1072);
				match(RPAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1074);
				match(SUM);
				setState(1075);
				match(LPAREN);
				setState(1076);
				soqlId();
				setState(1077);
				match(RPAREN);
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

	public static class TypeOfContext extends ParserRuleContext {
		public TerminalNode TYPEOF() { return getToken(ApexParser.TYPEOF, 0); }
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public TerminalNode END() { return getToken(ApexParser.END, 0); }
		public List<WhenClauseContext> whenClause() {
			return getRuleContexts(WhenClauseContext.class);
		}
		public WhenClauseContext whenClause(int i) {
			return getRuleContext(WhenClauseContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public TypeOfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeOf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterTypeOf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitTypeOf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitTypeOf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeOfContext typeOf() throws RecognitionException {
		TypeOfContext _localctx = new TypeOfContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_typeOf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1081);
			match(TYPEOF);
			setState(1082);
			soqlId();
			setState(1084); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1083);
				whenClause();
				}
				}
				setState(1086); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(1089);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(1088);
				elseClause();
				}
			}

			setState(1091);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhenClauseContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(ApexParser.WHEN, 0); }
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public TerminalNode THEN() { return getToken(ApexParser.THEN, 0); }
		public SimpleFieldListContext simpleFieldList() {
			return getRuleContext(SimpleFieldListContext.class,0);
		}
		public WhenClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhenClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhenClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhenClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenClauseContext whenClause() throws RecognitionException {
		WhenClauseContext _localctx = new WhenClauseContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_whenClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1093);
			match(WHEN);
			setState(1094);
			soqlId();
			setState(1095);
			match(THEN);
			setState(1096);
			simpleFieldList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseClauseContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(ApexParser.ELSE, 0); }
		public SimpleFieldListContext simpleFieldList() {
			return getRuleContext(SimpleFieldListContext.class,0);
		}
		public ElseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterElseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitElseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitElseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseClauseContext elseClause() throws RecognitionException {
		ElseClauseContext _localctx = new ElseClauseContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_elseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1098);
			match(ELSE);
			setState(1099);
			simpleFieldList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleFieldListContext extends ParserRuleContext {
		public List<SoqlIdContext> soqlId() {
			return getRuleContexts(SoqlIdContext.class);
		}
		public SoqlIdContext soqlId(int i) {
			return getRuleContext(SoqlIdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public SimpleFieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleFieldList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSimpleFieldList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSimpleFieldList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSimpleFieldList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleFieldListContext simpleFieldList() throws RecognitionException {
		SimpleFieldListContext _localctx = new SimpleFieldListContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_simpleFieldList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1101);
			soqlId();
			setState(1106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1102);
				match(COMMA);
				setState(1103);
				soqlId();
				}
				}
				setState(1108);
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

	public static class UsingScopeContext extends ParserRuleContext {
		public TerminalNode USING() { return getToken(ApexParser.USING, 0); }
		public TerminalNode SCOPE() { return getToken(ApexParser.SCOPE, 0); }
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public UsingScopeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usingScope; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterUsingScope(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitUsingScope(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitUsingScope(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UsingScopeContext usingScope() throws RecognitionException {
		UsingScopeContext _localctx = new UsingScopeContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_usingScope);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1109);
			match(USING);
			setState(1110);
			match(SCOPE);
			setState(1111);
			soqlId();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SoqlIdContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public SoqlIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_soqlId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSoqlId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSoqlId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSoqlId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SoqlIdContext soqlId() throws RecognitionException {
		SoqlIdContext _localctx = new SoqlIdContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_soqlId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1113);
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ApexParser.Identifier, 0); }
		public TerminalNode AFTER() { return getToken(ApexParser.AFTER, 0); }
		public TerminalNode BEFORE() { return getToken(ApexParser.BEFORE, 0); }
		public TerminalNode GET() { return getToken(ApexParser.GET, 0); }
		public TerminalNode INHERITED() { return getToken(ApexParser.INHERITED, 0); }
		public TerminalNode INSTANCEOF() { return getToken(ApexParser.INSTANCEOF, 0); }
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public TerminalNode TRIGGER() { return getToken(ApexParser.TRIGGER, 0); }
		public TerminalNode WHEN() { return getToken(ApexParser.WHEN, 0); }
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode WITHOUT() { return getToken(ApexParser.WITHOUT, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1115);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==Identifier) ) {
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

	public static class AnyIdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ApexParser.Identifier, 0); }
		public TerminalNode ABSTRACT() { return getToken(ApexParser.ABSTRACT, 0); }
		public TerminalNode AFTER() { return getToken(ApexParser.AFTER, 0); }
		public TerminalNode BEFORE() { return getToken(ApexParser.BEFORE, 0); }
		public TerminalNode BREAK() { return getToken(ApexParser.BREAK, 0); }
		public TerminalNode CATCH() { return getToken(ApexParser.CATCH, 0); }
		public TerminalNode CLASS() { return getToken(ApexParser.CLASS, 0); }
		public TerminalNode CONTINUE() { return getToken(ApexParser.CONTINUE, 0); }
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
		public TerminalNode LIST() { return getToken(ApexParser.LIST, 0); }
		public TerminalNode MAP() { return getToken(ApexParser.MAP, 0); }
		public TerminalNode MERGE() { return getToken(ApexParser.MERGE, 0); }
		public TerminalNode NEW() { return getToken(ApexParser.NEW, 0); }
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public TerminalNode ON() { return getToken(ApexParser.ON, 0); }
		public TerminalNode OVERRIDE() { return getToken(ApexParser.OVERRIDE, 0); }
		public TerminalNode PRIVATE() { return getToken(ApexParser.PRIVATE, 0); }
		public TerminalNode PROTECTED() { return getToken(ApexParser.PROTECTED, 0); }
		public TerminalNode PUBLIC() { return getToken(ApexParser.PUBLIC, 0); }
		public TerminalNode RETURN() { return getToken(ApexParser.RETURN, 0); }
		public TerminalNode SET() { return getToken(ApexParser.SET, 0); }
		public TerminalNode SHARING() { return getToken(ApexParser.SHARING, 0); }
		public TerminalNode STATIC() { return getToken(ApexParser.STATIC, 0); }
		public TerminalNode SUPER() { return getToken(ApexParser.SUPER, 0); }
		public TerminalNode SWITCH() { return getToken(ApexParser.SWITCH, 0); }
		public TerminalNode TESTMETHOD() { return getToken(ApexParser.TESTMETHOD, 0); }
		public TerminalNode THIS() { return getToken(ApexParser.THIS, 0); }
		public TerminalNode THROW() { return getToken(ApexParser.THROW, 0); }
		public TerminalNode TRANSIENT() { return getToken(ApexParser.TRANSIENT, 0); }
		public TerminalNode TRIGGER() { return getToken(ApexParser.TRIGGER, 0); }
		public TerminalNode TRY() { return getToken(ApexParser.TRY, 0); }
		public TerminalNode UNDELETE() { return getToken(ApexParser.UNDELETE, 0); }
		public TerminalNode UPDATE() { return getToken(ApexParser.UPDATE, 0); }
		public TerminalNode UPSERT() { return getToken(ApexParser.UPSERT, 0); }
		public TerminalNode VIRTUAL() { return getToken(ApexParser.VIRTUAL, 0); }
		public TerminalNode WEBSERVICE() { return getToken(ApexParser.WEBSERVICE, 0); }
		public TerminalNode WHEN() { return getToken(ApexParser.WHEN, 0); }
		public TerminalNode WHILE() { return getToken(ApexParser.WHILE, 0); }
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode WITHOUT() { return getToken(ApexParser.WITHOUT, 0); }
		public AnyIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterAnyId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitAnyId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitAnyId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnyIdContext anyId() throws RecognitionException {
		AnyIdContext _localctx = new AnyIdContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_anyId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1117);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << BREAK) | (1L << CATCH) | (1L << CLASS) | (1L << CONTINUE) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP))) != 0) || _la==Identifier) ) {
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
		case 72:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0088\u0462\4\2\t"+
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
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\7\2\u00d9\n\2\f\2\16\2\u00dc\13\2\3\2\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\5\7\5\u00e9\n\5\f\5\16\5\u00ec\13\5\3\5\3\5\7\5\u00f0"+
		"\n\5\f\5\16\5\u00f3\13\5\3\5\3\5\7\5\u00f7\n\5\f\5\16\5\u00fa\13\5\3\5"+
		"\5\5\u00fd\n\5\3\6\3\6\3\6\3\6\5\6\u0103\n\6\3\6\3\6\5\6\u0107\n\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\5\7\u010f\n\7\3\7\3\7\3\b\3\b\3\b\7\b\u0116\n\b\f"+
		"\b\16\b\u0119\13\b\3\t\3\t\3\t\3\t\5\t\u011f\n\t\3\t\3\t\3\n\3\n\3\n\7"+
		"\n\u0126\n\n\f\n\16\n\u0129\13\n\3\13\3\13\7\13\u012d\n\13\f\13\16\13"+
		"\u0130\13\13\3\13\3\13\3\f\3\f\7\f\u0136\n\f\f\f\16\f\u0139\13\f\3\f\3"+
		"\f\3\r\3\r\5\r\u013f\n\r\3\r\3\r\7\r\u0143\n\r\f\r\16\r\u0146\13\r\3\r"+
		"\5\r\u0149\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u015e\n\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\5\17\u0167\n\17\3\20\3\20\5\20\u016b\n\20\3\20\3"+
		"\20\3\20\3\20\5\20\u0171\n\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\7\23\u017f\n\23\f\23\16\23\u0182\13\23\3\23\3\23"+
		"\3\24\7\24\u0187\n\24\f\24\16\24\u018a\13\24\3\24\3\24\5\24\u018e\n\24"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\7\25\u0197\n\25\f\25\16\25\u019a\13"+
		"\25\3\26\3\26\3\26\5\26\u019f\n\26\3\27\3\27\3\27\3\27\7\27\u01a5\n\27"+
		"\f\27\16\27\u01a8\13\27\3\27\5\27\u01ab\n\27\5\27\u01ad\n\27\3\27\3\27"+
		"\3\30\3\30\3\30\7\30\u01b4\n\30\f\30\16\30\u01b7\13\30\3\30\3\30\3\31"+
		"\3\31\7\31\u01bd\n\31\f\31\16\31\u01c0\13\31\3\32\3\32\5\32\u01c4\n\32"+
		"\3\32\3\32\5\32\u01c8\n\32\3\32\3\32\5\32\u01cc\n\32\3\32\3\32\5\32\u01d0"+
		"\n\32\5\32\u01d2\n\32\3\33\3\33\3\33\3\33\3\34\3\34\5\34\u01da\n\34\3"+
		"\34\3\34\3\35\3\35\3\35\7\35\u01e1\n\35\f\35\16\35\u01e4\13\35\3\36\7"+
		"\36\u01e7\n\36\f\36\16\36\u01ea\13\36\3\36\3\36\3\36\3\37\3\37\3\37\7"+
		"\37\u01f2\n\37\f\37\16\37\u01f5\13\37\3 \3 \3!\3!\3!\3!\3!\5!\u01fe\n"+
		"!\3!\5!\u0201\n!\3\"\3\"\5\"\u0205\n\"\3\"\7\"\u0208\n\"\f\"\16\"\u020b"+
		"\13\"\3#\3#\3#\3#\3$\3$\3$\5$\u0214\n$\3%\3%\3%\3%\7%\u021a\n%\f%\16%"+
		"\u021d\13%\5%\u021f\n%\3%\5%\u0222\n%\3%\3%\3&\3&\7&\u0228\n&\f&\16&\u022b"+
		"\13&\3&\3&\3\'\3\'\3\'\3(\7(\u0233\n(\f(\16(\u0236\13(\3(\3(\3(\3)\3)"+
		"\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u024f\n)\3*"+
		"\3*\3*\3*\3*\5*\u0256\n*\3+\3+\3+\3+\3+\6+\u025d\n+\r+\16+\u025e\3+\3"+
		"+\3,\3,\3,\3,\3-\3-\3-\3-\7-\u026b\n-\f-\16-\u026e\13-\3-\3-\3-\5-\u0273"+
		"\n-\3.\5.\u0276\n.\3.\3.\3.\3.\5.\u027c\n.\3/\3/\3/\3/\3/\3/\3\60\3\60"+
		"\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\6\62\u0291\n\62"+
		"\r\62\16\62\u0292\3\62\5\62\u0296\n\62\3\62\5\62\u0299\n\62\3\63\3\63"+
		"\5\63\u029d\n\63\3\63\3\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66"+
		"\3\66\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\39\3:\3:\3:\3:\3;\3;\3"+
		";\5;\u02be\n;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\5=\u02ca\n=\3=\3=\3=\3>\3"+
		">\3>\3?\7?\u02d3\n?\f?\16?\u02d6\13?\3?\3?\5?\u02da\n?\3@\3@\3@\5@\u02df"+
		"\n@\3A\3A\3A\5A\u02e4\nA\3B\3B\3B\7B\u02e9\nB\fB\16B\u02ec\13B\3B\3B\3"+
		"B\3B\3B\3C\3C\3C\3D\3D\5D\u02f8\nD\3D\3D\5D\u02fc\nD\3D\3D\5D\u0300\n"+
		"D\5D\u0302\nD\3E\3E\5E\u0306\nE\3F\3F\3F\3F\3F\3G\3G\3H\3H\3H\3H\3I\3"+
		"I\3I\7I\u0316\nI\fI\16I\u0319\13I\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J"+
		"\3J\3J\5J\u0329\nJ\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5J\u0339"+
		"\nJ\3J\3J\3J\3J\5J\u033f\nJ\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J"+
		"\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5J\u0361\nJ\3J"+
		"\3J\3J\3J\3J\3J\3J\3J\3J\3J\7J\u036d\nJ\fJ\16J\u0370\13J\3K\3K\3K\3K\3"+
		"K\3K\3K\3K\3K\3K\3K\3K\3K\5K\u037f\nK\3L\3L\3L\5L\u0384\nL\3L\3L\3L\3"+
		"L\3L\5L\u038b\nL\3L\3L\3L\3L\5L\u0391\nL\3L\5L\u0394\nL\3M\3M\3M\5M\u0399"+
		"\nM\3M\3M\3N\3N\3N\3N\3N\3N\5N\u03a3\nN\3O\3O\3O\7O\u03a8\nO\fO\16O\u03ab"+
		"\13O\3P\3P\3P\3P\3P\5P\u03b2\nP\3Q\3Q\3Q\3R\3R\3S\3S\3S\3S\3S\3S\3S\5"+
		"S\u03c0\nS\5S\u03c2\nS\3T\3T\3T\3T\7T\u03c8\nT\fT\16T\u03cb\13T\3T\3T"+
		"\3U\3U\3U\3U\3V\3V\3V\3V\7V\u03d7\nV\fV\16V\u03da\13V\3V\3V\3W\3W\5W\u03e0"+
		"\nW\3W\3W\3X\3X\3X\3X\3Y\3Y\3Y\3Y\3Y\5Y\u03ed\nY\3Z\3Z\3Z\3Z\3Z\3[\3["+
		"\3[\7[\u03f7\n[\f[\16[\u03fa\13[\3\\\3\\\3\\\3\\\3\\\3\\\3\\\5\\\u0403"+
		"\n\\\3]\3]\3]\7]\u0408\n]\f]\16]\u040b\13]\3^\3^\3^\7^\u0410\n^\f^\16"+
		"^\u0413\13^\3_\3_\5_\u0417\n_\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3"+
		"`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\3`\5`\u043a\n"+
		"`\3a\3a\3a\6a\u043f\na\ra\16a\u0440\3a\5a\u0444\na\3a\3a\3b\3b\3b\3b\3"+
		"b\3c\3c\3c\3d\3d\3d\7d\u0453\nd\fd\16d\u0456\13d\3e\3e\3e\3e\3f\3f\3g"+
		"\3g\3h\3h\3h\2\3\u0092i\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c"+
		"\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4"+
		"\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc"+
		"\u00ce\2\20\3\2\4\5\5\2\n\n\27\27/\60\4\2\34\34PS\3\2mp\3\2ab\4\2qrvv"+
		"\3\2op\3\2_`\3\2fj\4\2^^x\u0082\4\2]]cc\3\2mn\f\2\4\5\22\22\26\26\30\30"+
		"$%((,-\65\65\678\u0084\u0084\6\2\3\"$\62\64:\u0084\u0084\2\u04b2\2\u00d0"+
		"\3\2\2\2\4\u00e1\3\2\2\2\6\u00e4\3\2\2\2\b\u00fc\3\2\2\2\n\u00fe\3\2\2"+
		"\2\f\u010a\3\2\2\2\16\u0112\3\2\2\2\20\u011a\3\2\2\2\22\u0122\3\2\2\2"+
		"\24\u012a\3\2\2\2\26\u0133\3\2\2\2\30\u0148\3\2\2\2\32\u015d\3\2\2\2\34"+
		"\u0166\3\2\2\2\36\u016a\3\2\2\2 \u0172\3\2\2\2\"\u0176\3\2\2\2$\u017a"+
		"\3\2\2\2&\u0188\3\2\2\2(\u0193\3\2\2\2*\u019b\3\2\2\2,\u01a0\3\2\2\2."+
		"\u01b0\3\2\2\2\60\u01be\3\2\2\2\62\u01d1\3\2\2\2\64\u01d3\3\2\2\2\66\u01d7"+
		"\3\2\2\28\u01dd\3\2\2\2:\u01e8\3\2\2\2<\u01ee\3\2\2\2>\u01f6\3\2\2\2@"+
		"\u01f8\3\2\2\2B\u0202\3\2\2\2D\u020c\3\2\2\2F\u0213\3\2\2\2H\u0215\3\2"+
		"\2\2J\u0225\3\2\2\2L\u022e\3\2\2\2N\u0234\3\2\2\2P\u024e\3\2\2\2R\u0250"+
		"\3\2\2\2T\u0257\3\2\2\2V\u0262\3\2\2\2X\u0272\3\2\2\2Z\u027b\3\2\2\2\\"+
		"\u027d\3\2\2\2^\u0283\3\2\2\2`\u0287\3\2\2\2b\u028d\3\2\2\2d\u029a\3\2"+
		"\2\2f\u02a0\3\2\2\2h\u02a4\3\2\2\2j\u02a7\3\2\2\2l\u02aa\3\2\2\2n\u02ae"+
		"\3\2\2\2p\u02b2\3\2\2\2r\u02b6\3\2\2\2t\u02ba\3\2\2\2v\u02c1\3\2\2\2x"+
		"\u02c6\3\2\2\2z\u02ce\3\2\2\2|\u02d4\3\2\2\2~\u02db\3\2\2\2\u0080\u02e0"+
		"\3\2\2\2\u0082\u02e5\3\2\2\2\u0084\u02f2\3\2\2\2\u0086\u0301\3\2\2\2\u0088"+
		"\u0305\3\2\2\2\u008a\u0307\3\2\2\2\u008c\u030c\3\2\2\2\u008e\u030e\3\2"+
		"\2\2\u0090\u0312\3\2\2\2\u0092\u0328\3\2\2\2\u0094\u037e\3\2\2\2\u0096"+
		"\u0393\3\2\2\2\u0098\u0395\3\2\2\2\u009a\u039c\3\2\2\2\u009c\u03a4\3\2"+
		"\2\2\u009e\u03ac\3\2\2\2\u00a0\u03b3\3\2\2\2\u00a2\u03b6\3\2\2\2\u00a4"+
		"\u03c1\3\2\2\2\u00a6\u03c3\3\2\2\2\u00a8\u03ce\3\2\2\2\u00aa\u03d2\3\2"+
		"\2\2\u00ac\u03dd\3\2\2\2\u00ae\u03e3\3\2\2\2\u00b0\u03e7\3\2\2\2\u00b2"+
		"\u03ee\3\2\2\2\u00b4\u03f3\3\2\2\2\u00b6\u0402\3\2\2\2\u00b8\u0404\3\2"+
		"\2\2\u00ba\u040c\3\2\2\2\u00bc\u0416\3\2\2\2\u00be\u0439\3\2\2\2\u00c0"+
		"\u043b\3\2\2\2\u00c2\u0447\3\2\2\2\u00c4\u044c\3\2\2\2\u00c6\u044f\3\2"+
		"\2\2\u00c8\u0457\3\2\2\2\u00ca\u045b\3\2\2\2\u00cc\u045d\3\2\2\2\u00ce"+
		"\u045f\3\2\2\2\u00d0\u00d1\7-\2\2\u00d1\u00d2\5\u00ccg\2\u00d2\u00d3\7"+
		"\35\2\2\u00d3\u00d4\5\u00ccg\2\u00d4\u00d5\7U\2\2\u00d5\u00da\5\4\3\2"+
		"\u00d6\u00d7\7\\\2\2\u00d7\u00d9\5\4\3\2\u00d8\u00d6\3\2\2\2\u00d9\u00dc"+
		"\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dd\3\2\2\2\u00dc"+
		"\u00da\3\2\2\2\u00dd\u00de\7V\2\2\u00de\u00df\5J&\2\u00df\u00e0\7\2\2"+
		"\3\u00e0\3\3\2\2\2\u00e1\u00e2\t\2\2\2\u00e2\u00e3\t\3\2\2\u00e3\5\3\2"+
		"\2\2\u00e4\u00e5\5\b\5\2\u00e5\u00e6\7\2\2\3\u00e6\7\3\2\2\2\u00e7\u00e9"+
		"\5\32\16\2\u00e8\u00e7\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2"+
		"\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed\u00fd"+
		"\5\n\6\2\u00ee\u00f0\5\32\16\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2\2\2"+
		"\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1"+
		"\3\2\2\2\u00f4\u00fd\5\f\7\2\u00f5\u00f7\5\32\16\2\u00f6\u00f5\3\2\2\2"+
		"\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb"+
		"\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fd\5\20\t\2\u00fc\u00ea\3\2\2\2"+
		"\u00fc\u00f1\3\2\2\2\u00fc\u00f8\3\2\2\2\u00fd\t\3\2\2\2\u00fe\u00ff\7"+
		"\b\2\2\u00ff\u0102\5\u00ccg\2\u0100\u0101\7\16\2\2\u0101\u0103\5.\30\2"+
		"\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0106\3\2\2\2\u0104\u0105"+
		"\7\25\2\2\u0105\u0107\5\22\n\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2"+
		"\u0107\u0108\3\2\2\2\u0108\u0109\5\24\13\2\u0109\13\3\2\2\2\u010a\u010b"+
		"\7\r\2\2\u010b\u010c\5\u00ccg\2\u010c\u010e\7W\2\2\u010d\u010f\5\16\b"+
		"\2\u010e\u010d\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111"+
		"\7X\2\2\u0111\r\3\2\2\2\u0112\u0117\5\u00ccg\2\u0113\u0114\7\\\2\2\u0114"+
		"\u0116\5\u00ccg\2\u0115\u0113\3\2\2\2\u0116\u0119\3\2\2\2\u0117\u0115"+
		"\3\2\2\2\u0117\u0118\3\2\2\2\u0118\17\3\2\2\2\u0119\u0117\3\2\2\2\u011a"+
		"\u011b\7\31\2\2\u011b\u011e\5\u00ccg\2\u011c\u011d\7\16\2\2\u011d\u011f"+
		"\5\22\n\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120\3\2\2\2"+
		"\u0120\u0121\5\26\f\2\u0121\21\3\2\2\2\u0122\u0127\5.\30\2\u0123\u0124"+
		"\7\\\2\2\u0124\u0126\5.\30\2\u0125\u0123\3\2\2\2\u0126\u0129\3\2\2\2\u0127"+
		"\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128\23\3\2\2\2\u0129\u0127\3\2\2"+
		"\2\u012a\u012e\7W\2\2\u012b\u012d\5\30\r\2\u012c\u012b\3\2\2\2\u012d\u0130"+
		"\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0131\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0131\u0132\7X\2\2\u0132\25\3\2\2\2\u0133\u0137\7W\2\2"+
		"\u0134\u0136\5&\24\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0135"+
		"\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u013a\3\2\2\2\u0139\u0137\3\2\2\2\u013a"+
		"\u013b\7X\2\2\u013b\27\3\2\2\2\u013c\u0149\7[\2\2\u013d\u013f\7&\2\2\u013e"+
		"\u013d\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0149\5J"+
		"&\2\u0141\u0143\5\32\16\2\u0142\u0141\3\2\2\2\u0143\u0146\3\2\2\2\u0144"+
		"\u0142\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0147\3\2\2\2\u0146\u0144\3\2"+
		"\2\2\u0147\u0149\5\34\17\2\u0148\u013c\3\2\2\2\u0148\u013e\3\2\2\2\u0148"+
		"\u0144\3\2\2\2\u0149\31\3\2\2\2\u014a\u015e\5@!\2\u014b\u015e\7\23\2\2"+
		"\u014c\u015e\7!\2\2\u014d\u015e\7 \2\2\u014e\u015e\7\37\2\2\u014f\u015e"+
		"\7,\2\2\u0150\u015e\7&\2\2\u0151\u015e\7\3\2\2\u0152\u015e\7\17\2\2\u0153"+
		"\u015e\7\64\2\2\u0154\u015e\7\36\2\2\u0155\u015e\7\62\2\2\u0156\u015e"+
		"\7)\2\2\u0157\u0158\7\67\2\2\u0158\u015e\7%\2\2\u0159\u015a\78\2\2\u015a"+
		"\u015e\7%\2\2\u015b\u015c\7\26\2\2\u015c\u015e\7%\2\2\u015d\u014a\3\2"+
		"\2\2\u015d\u014b\3\2\2\2\u015d\u014c\3\2\2\2\u015d\u014d\3\2\2\2\u015d"+
		"\u014e\3\2\2\2\u015d\u014f\3\2\2\2\u015d\u0150\3\2\2\2\u015d\u0151\3\2"+
		"\2\2\u015d\u0152\3\2\2\2\u015d\u0153\3\2\2\2\u015d\u0154\3\2\2\2\u015d"+
		"\u0155\3\2\2\2\u015d\u0156\3\2\2\2\u015d\u0157\3\2\2\2\u015d\u0159\3\2"+
		"\2\2\u015d\u015b\3\2\2\2\u015e\33\3\2\2\2\u015f\u0167\5\36\20\2\u0160"+
		"\u0167\5\"\22\2\u0161\u0167\5 \21\2\u0162\u0167\5\20\t\2\u0163\u0167\5"+
		"\n\6\2\u0164\u0167\5\f\7\2\u0165\u0167\5$\23\2\u0166\u015f\3\2\2\2\u0166"+
		"\u0160\3\2\2\2\u0166\u0161\3\2\2\2\u0166\u0162\3\2\2\2\u0166\u0163\3\2"+
		"\2\2\u0166\u0164\3\2\2\2\u0166\u0165\3\2\2\2\u0167\35\3\2\2\2\u0168\u016b"+
		"\5.\30\2\u0169\u016b\7\63\2\2\u016a\u0168\3\2\2\2\u016a\u0169\3\2\2\2"+
		"\u016b\u016c\3\2\2\2\u016c\u016d\5\u00ccg\2\u016d\u0170\5\66\34\2\u016e"+
		"\u0171\5J&\2\u016f\u0171\7[\2\2\u0170\u016e\3\2\2\2\u0170\u016f\3\2\2"+
		"\2\u0171\37\3\2\2\2\u0172\u0173\5<\37\2\u0173\u0174\5\66\34\2\u0174\u0175"+
		"\5J&\2\u0175!\3\2\2\2\u0176\u0177\5.\30\2\u0177\u0178\5(\25\2\u0178\u0179"+
		"\7[\2\2\u0179#\3\2\2\2\u017a\u017b\5.\30\2\u017b\u017c\5\u00ccg\2\u017c"+
		"\u0180\7W\2\2\u017d\u017f\5|?\2\u017e\u017d\3\2\2\2\u017f\u0182\3\2\2"+
		"\2\u0180\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181\u0183\3\2\2\2\u0182\u0180"+
		"\3\2\2\2\u0183\u0184\7X\2\2\u0184%\3\2\2\2\u0185\u0187\5\32\16\2\u0186"+
		"\u0185\3\2\2\2\u0187\u018a\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0189\3\2"+
		"\2\2\u0189\u018d\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018e\5.\30\2\u018c"+
		"\u018e\7\63\2\2\u018d\u018b\3\2\2\2\u018d\u018c\3\2\2\2\u018e\u018f\3"+
		"\2\2\2\u018f\u0190\5\u00ccg\2\u0190\u0191\5\66\34\2\u0191\u0192\7[\2\2"+
		"\u0192\'\3\2\2\2\u0193\u0198\5*\26\2\u0194\u0195\7\\\2\2\u0195\u0197\5"+
		"*\26\2\u0196\u0194\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0196\3\2\2\2\u0198"+
		"\u0199\3\2\2\2\u0199)\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u019e\5\u00cc"+
		"g\2\u019c\u019d\7^\2\2\u019d\u019f\5\u0092J\2\u019e\u019c\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f+\3\2\2\2\u01a0\u01ac\7W\2\2\u01a1\u01a6\5\u0092J"+
		"\2\u01a2\u01a3\7\\\2\2\u01a3\u01a5\5\u0092J\2\u01a4\u01a2\3\2\2\2\u01a5"+
		"\u01a8\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7\u01aa\3\2"+
		"\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01ab\7\\\2\2\u01aa\u01a9\3\2\2\2\u01aa"+
		"\u01ab\3\2\2\2\u01ab\u01ad\3\2\2\2\u01ac\u01a1\3\2\2\2\u01ac\u01ad\3\2"+
		"\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\7X\2\2\u01af-\3\2\2\2\u01b0\u01b5"+
		"\5\62\32\2\u01b1\u01b2\7]\2\2\u01b2\u01b4\5\62\32\2\u01b3\u01b1\3\2\2"+
		"\2\u01b4\u01b7\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b8"+
		"\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b8\u01b9\5\60\31\2\u01b9/\3\2\2\2\u01ba"+
		"\u01bb\7Y\2\2\u01bb\u01bd\7Z\2\2\u01bc\u01ba\3\2\2\2\u01bd\u01c0\3\2\2"+
		"\2\u01be\u01bc\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\61\3\2\2\2\u01c0\u01be"+
		"\3\2\2\2\u01c1\u01c3\79\2\2\u01c2\u01c4\5\64\33\2\u01c3\u01c2\3\2\2\2"+
		"\u01c3\u01c4\3\2\2\2\u01c4\u01d2\3\2\2\2\u01c5\u01c7\7$\2\2\u01c6\u01c8"+
		"\5\64\33\2\u01c7\u01c6\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01d2\3\2\2\2"+
		"\u01c9\u01cb\7:\2\2\u01ca\u01cc\5\64\33\2\u01cb\u01ca\3\2\2\2\u01cb\u01cc"+
		"\3\2\2\2\u01cc\u01d2\3\2\2\2\u01cd\u01cf\5\u00ccg\2\u01ce\u01d0\5\64\33"+
		"\2\u01cf\u01ce\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01d2\3\2\2\2\u01d1\u01c1"+
		"\3\2\2\2\u01d1\u01c5\3\2\2\2\u01d1\u01c9\3\2\2\2\u01d1\u01cd\3\2\2\2\u01d2"+
		"\63\3\2\2\2\u01d3\u01d4\7`\2\2\u01d4\u01d5\5\22\n\2\u01d5\u01d6\7_\2\2"+
		"\u01d6\65\3\2\2\2\u01d7\u01d9\7U\2\2\u01d8\u01da\58\35\2\u01d9\u01d8\3"+
		"\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01dc\7V\2\2\u01dc"+
		"\67\3\2\2\2\u01dd\u01e2\5:\36\2\u01de\u01df\7\\\2\2\u01df\u01e1\5:\36"+
		"\2\u01e0\u01de\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e2\u01e3"+
		"\3\2\2\2\u01e39\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5\u01e7\5\32\16\2\u01e6"+
		"\u01e5\3\2\2\2\u01e7\u01ea\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2"+
		"\2\2\u01e9\u01eb\3\2\2\2\u01ea\u01e8\3\2\2\2\u01eb\u01ec\5.\30\2\u01ec"+
		"\u01ed\5\u00ccg\2\u01ed;\3\2\2\2\u01ee\u01f3\5\u00ccg\2\u01ef\u01f0\7"+
		"]\2\2\u01f0\u01f2\5\u00ccg\2\u01f1\u01ef\3\2\2\2\u01f2\u01f5\3\2\2\2\u01f3"+
		"\u01f1\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4=\3\2\2\2\u01f5\u01f3\3\2\2\2"+
		"\u01f6\u01f7\t\4\2\2\u01f7?\3\2\2\2\u01f8\u01f9\7\u0083\2\2\u01f9\u0200"+
		"\5<\37\2\u01fa\u01fd\7U\2\2\u01fb\u01fe\5B\"\2\u01fc\u01fe\5F$\2\u01fd"+
		"\u01fb\3\2\2\2\u01fd\u01fc\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe\u01ff\3\2"+
		"\2\2\u01ff\u0201\7V\2\2\u0200\u01fa\3\2\2\2\u0200\u0201\3\2\2\2\u0201"+
		"A\3\2\2\2\u0202\u0209\5D#\2\u0203\u0205\7\\\2\2\u0204\u0203\3\2\2\2\u0204"+
		"\u0205\3\2\2\2\u0205\u0206\3\2\2\2\u0206\u0208\5D#\2\u0207\u0204\3\2\2"+
		"\2\u0208\u020b\3\2\2\2\u0209\u0207\3\2\2\2\u0209\u020a\3\2\2\2\u020aC"+
		"\3\2\2\2\u020b\u0209\3\2\2\2\u020c\u020d\5\u00ccg\2\u020d\u020e\7^\2\2"+
		"\u020e\u020f\5F$\2\u020fE\3\2\2\2\u0210\u0214\5\u0092J\2\u0211\u0214\5"+
		"@!\2\u0212\u0214\5H%\2\u0213\u0210\3\2\2\2\u0213\u0211\3\2\2\2\u0213\u0212"+
		"\3\2\2\2\u0214G\3\2\2\2\u0215\u021e\7W\2\2\u0216\u021b\5F$\2\u0217\u0218"+
		"\7\\\2\2\u0218\u021a\5F$\2\u0219\u0217\3\2\2\2\u021a\u021d\3\2\2\2\u021b"+
		"\u0219\3\2\2\2\u021b\u021c\3\2\2\2\u021c\u021f\3\2\2\2\u021d\u021b\3\2"+
		"\2\2\u021e\u0216\3\2\2\2\u021e\u021f\3\2\2\2\u021f\u0221\3\2\2\2\u0220"+
		"\u0222\7\\\2\2\u0221\u0220\3\2\2\2\u0221\u0222\3\2\2\2\u0222\u0223\3\2"+
		"\2\2\u0223\u0224\7X\2\2\u0224I\3\2\2\2\u0225\u0229\7W\2\2\u0226\u0228"+
		"\5P)\2\u0227\u0226\3\2\2\2\u0228\u022b\3\2\2\2\u0229\u0227\3\2\2\2\u0229"+
		"\u022a\3\2\2\2\u022a\u022c\3\2\2\2\u022b\u0229\3\2\2\2\u022c\u022d\7X"+
		"\2\2\u022dK\3\2\2\2\u022e\u022f\5N(\2\u022f\u0230\7[\2\2\u0230M\3\2\2"+
		"\2\u0231\u0233\5\32\16\2\u0232\u0231\3\2\2\2\u0233\u0236\3\2\2\2\u0234"+
		"\u0232\3\2\2\2\u0234\u0235\3\2\2\2\u0235\u0237\3\2\2\2\u0236\u0234\3\2"+
		"\2\2\u0237\u0238\5.\30\2\u0238\u0239\5(\25\2\u0239O\3\2\2\2\u023a\u024f"+
		"\5J&\2\u023b\u024f\5R*\2\u023c\u024f\5T+\2\u023d\u024f\5\\/\2\u023e\u024f"+
		"\5^\60\2\u023f\u024f\5`\61\2\u0240\u024f\5b\62\2\u0241\u024f\5d\63\2\u0242"+
		"\u024f\5f\64\2\u0243\u024f\5h\65\2\u0244\u024f\5j\66\2\u0245\u024f\5l"+
		"\67\2\u0246\u024f\5n8\2\u0247\u024f\5p9\2\u0248\u024f\5r:\2\u0249\u024f"+
		"\5t;\2\u024a\u024f\5v<\2\u024b\u024f\5x=\2\u024c\u024f\5L\'\2\u024d\u024f"+
		"\5z>\2\u024e\u023a\3\2\2\2\u024e\u023b\3\2\2\2\u024e\u023c\3\2\2\2\u024e"+
		"\u023d\3\2\2\2\u024e\u023e\3\2\2\2\u024e\u023f\3\2\2\2\u024e\u0240\3\2"+
		"\2\2\u024e\u0241\3\2\2\2\u024e\u0242\3\2\2\2\u024e\u0243\3\2\2\2\u024e"+
		"\u0244\3\2\2\2\u024e\u0245\3\2\2\2\u024e\u0246\3\2\2\2\u024e\u0247\3\2"+
		"\2\2\u024e\u0248\3\2\2\2\u024e\u0249\3\2\2\2\u024e\u024a\3\2\2\2\u024e"+
		"\u024b\3\2\2\2\u024e\u024c\3\2\2\2\u024e\u024d\3\2\2\2\u024fQ\3\2\2\2"+
		"\u0250\u0251\7\24\2\2\u0251\u0252\5\u008eH\2\u0252\u0255\5P)\2\u0253\u0254"+
		"\7\f\2\2\u0254\u0256\5P)\2\u0255\u0253\3\2\2\2\u0255\u0256\3\2\2\2\u0256"+
		"S\3\2\2\2\u0257\u0258\7(\2\2\u0258\u0259\7\35\2\2\u0259\u025a\5\u0092"+
		"J\2\u025a\u025c\7W\2\2\u025b\u025d\5V,\2\u025c\u025b\3\2\2\2\u025d\u025e"+
		"\3\2\2\2\u025e\u025c\3\2\2\2\u025e\u025f\3\2\2\2\u025f\u0260\3\2\2\2\u0260"+
		"\u0261\7X\2\2\u0261U\3\2\2\2\u0262\u0263\7\65\2\2\u0263\u0264\5X-\2\u0264"+
		"\u0265\5J&\2\u0265W\3\2\2\2\u0266\u0273\7\f\2\2\u0267\u026c\5Z.\2\u0268"+
		"\u0269\7\\\2\2\u0269\u026b\5Z.\2\u026a\u0268\3\2\2\2\u026b\u026e\3\2\2"+
		"\2\u026c\u026a\3\2\2\2\u026c\u026d\3\2\2\2\u026d\u0273\3\2\2\2\u026e\u026c"+
		"\3\2\2\2\u026f\u0270\5\u00ccg\2\u0270\u0271\5\u00ccg\2\u0271\u0273\3\2"+
		"\2\2\u0272\u0266\3\2\2\2\u0272\u0267\3\2\2\2\u0272\u026f\3\2\2\2\u0273"+
		"Y\3\2\2\2\u0274\u0276\7p\2\2\u0275\u0274\3\2\2\2\u0275\u0276\3\2\2\2\u0276"+
		"\u0277\3\2\2\2\u0277\u027c\7P\2\2\u0278\u027c\7S\2\2\u0279\u027c\7\34"+
		"\2\2\u027a\u027c\5\u00ccg\2\u027b\u0275\3\2\2\2\u027b\u0278\3\2\2\2\u027b"+
		"\u0279\3\2\2\2\u027b\u027a\3\2\2\2\u027c[\3\2\2\2\u027d\u027e\7\21\2\2"+
		"\u027e\u027f\7U\2\2\u027f\u0280\5\u0086D\2\u0280\u0281\7V\2\2\u0281\u0282"+
		"\5P)\2\u0282]\3\2\2\2\u0283\u0284\7\66\2\2\u0284\u0285\5\u008eH\2\u0285"+
		"\u0286\5P)\2\u0286_\3\2\2\2\u0287\u0288\7\13\2\2\u0288\u0289\5P)\2\u0289"+
		"\u028a\7\66\2\2\u028a\u028b\5\u008eH\2\u028b\u028c\7[\2\2\u028ca\3\2\2"+
		"\2\u028d\u028e\7.\2\2\u028e\u0298\5J&\2\u028f\u0291\5\u0082B\2\u0290\u028f"+
		"\3\2\2\2\u0291\u0292\3\2\2\2\u0292\u0290\3\2\2\2\u0292\u0293\3\2\2\2\u0293"+
		"\u0295\3\2\2\2\u0294\u0296\5\u0084C\2\u0295\u0294\3\2\2\2\u0295\u0296"+
		"\3\2\2\2\u0296\u0299\3\2\2\2\u0297\u0299\5\u0084C\2\u0298\u0290\3\2\2"+
		"\2\u0298\u0297\3\2\2\2\u0299c\3\2\2\2\u029a\u029c\7\"\2\2\u029b\u029d"+
		"\5\u0092J\2\u029c\u029b\3\2\2\2\u029c\u029d\3\2\2\2\u029d\u029e\3\2\2"+
		"\2\u029e\u029f\7[\2\2\u029fe\3\2\2\2\u02a0\u02a1\7+\2\2\u02a1\u02a2\5"+
		"\u0092J\2\u02a2\u02a3\7[\2\2\u02a3g\3\2\2\2\u02a4\u02a5\7\6\2\2\u02a5"+
		"\u02a6\7[\2\2\u02a6i\3\2\2\2\u02a7\u02a8\7\t\2\2\u02a8\u02a9\7[\2\2\u02a9"+
		"k\3\2\2\2\u02aa\u02ab\7\27\2\2\u02ab\u02ac\5\u0092J\2\u02ac\u02ad\7[\2"+
		"\2\u02adm\3\2\2\2\u02ae\u02af\7\60\2\2\u02af\u02b0\5\u0092J\2\u02b0\u02b1"+
		"\7[\2\2\u02b1o\3\2\2\2\u02b2\u02b3\7\n\2\2\u02b3\u02b4\5\u0092J\2\u02b4"+
		"\u02b5\7[\2\2\u02b5q\3\2\2\2\u02b6\u02b7\7/\2\2\u02b7\u02b8\5\u0092J\2"+
		"\u02b8\u02b9\7[\2\2\u02b9s\3\2\2\2\u02ba\u02bb\7\61\2\2\u02bb\u02bd\5"+
		"\u0092J\2\u02bc\u02be\5<\37\2\u02bd\u02bc\3\2\2\2\u02bd\u02be\3\2\2\2"+
		"\u02be\u02bf\3\2\2\2\u02bf\u02c0\7[\2\2\u02c0u\3\2\2\2\u02c1\u02c2\7\32"+
		"\2\2\u02c2\u02c3\5\u0092J\2\u02c3\u02c4\5\u0092J\2\u02c4\u02c5\7[\2\2"+
		"\u02c5w\3\2\2\2\u02c6\u02c7\7#\2\2\u02c7\u02c9\7U\2\2\u02c8\u02ca\5\u0090"+
		"I\2\u02c9\u02c8\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cb\3\2\2\2\u02cb"+
		"\u02cc\7V\2\2\u02cc\u02cd\5J&\2\u02cdy\3\2\2\2\u02ce\u02cf\5\u0092J\2"+
		"\u02cf\u02d0\7[\2\2\u02d0{\3\2\2\2\u02d1\u02d3\5\32\16\2\u02d2\u02d1\3"+
		"\2\2\2\u02d3\u02d6\3\2\2\2\u02d4\u02d2\3\2\2\2\u02d4\u02d5\3\2\2\2\u02d5"+
		"\u02d9\3\2\2\2\u02d6\u02d4\3\2\2\2\u02d7\u02da\5~@\2\u02d8\u02da\5\u0080"+
		"A\2\u02d9\u02d7\3\2\2\2\u02d9\u02d8\3\2\2\2\u02da}\3\2\2\2\u02db\u02de"+
		"\7\22\2\2\u02dc\u02df\7[\2\2\u02dd\u02df\5J&\2\u02de\u02dc\3\2\2\2\u02de"+
		"\u02dd\3\2\2\2\u02df\177\3\2\2\2\u02e0\u02e3\7$\2\2\u02e1\u02e4\7[\2\2"+
		"\u02e2\u02e4\5J&\2\u02e3\u02e1\3\2\2\2\u02e3\u02e2\3\2\2\2\u02e4\u0081"+
		"\3\2\2\2\u02e5\u02e6\7\7\2\2\u02e6\u02ea\7U\2\2\u02e7\u02e9\5\32\16\2"+
		"\u02e8\u02e7\3\2\2\2\u02e9\u02ec\3\2\2\2\u02ea\u02e8\3\2\2\2\u02ea\u02eb"+
		"\3\2\2\2\u02eb\u02ed\3\2\2\2\u02ec\u02ea\3\2\2\2\u02ed\u02ee\5<\37\2\u02ee"+
		"\u02ef\5\u00ccg\2\u02ef\u02f0\7V\2\2\u02f0\u02f1\5J&\2\u02f1\u0083\3\2"+
		"\2\2\u02f2\u02f3\7\20\2\2\u02f3\u02f4\5J&\2\u02f4\u0085\3\2\2\2\u02f5"+
		"\u0302\5\u008aF\2\u02f6\u02f8\5\u0088E\2\u02f7\u02f6\3\2\2\2\u02f7\u02f8"+
		"\3\2\2\2\u02f8\u02f9\3\2\2\2\u02f9\u02fb\7[\2\2\u02fa\u02fc\5\u0092J\2"+
		"\u02fb\u02fa\3\2\2\2\u02fb\u02fc\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd\u02ff"+
		"\7[\2\2\u02fe\u0300\5\u008cG\2\u02ff\u02fe\3\2\2\2\u02ff\u0300\3\2\2\2"+
		"\u0300\u0302\3\2\2\2\u0301\u02f5\3\2\2\2\u0301\u02f7\3\2\2\2\u0302\u0087"+
		"\3\2\2\2\u0303\u0306\5N(\2\u0304\u0306\5\u0090I\2\u0305\u0303\3\2\2\2"+
		"\u0305\u0304\3\2\2\2\u0306\u0089\3\2\2\2\u0307\u0308\5.\30\2\u0308\u0309"+
		"\5\u00ccg\2\u0309\u030a\7e\2\2\u030a\u030b\5\u0092J\2\u030b\u008b\3\2"+
		"\2\2\u030c\u030d\5\u0090I\2\u030d\u008d\3\2\2\2\u030e\u030f\7U\2\2\u030f"+
		"\u0310\5\u0092J\2\u0310\u0311\7V\2\2\u0311\u008f\3\2\2\2\u0312\u0317\5"+
		"\u0092J\2\u0313\u0314\7\\\2\2\u0314\u0316\5\u0092J\2\u0315\u0313\3\2\2"+
		"\2\u0316\u0319\3\2\2\2\u0317\u0315\3\2\2\2\u0317\u0318\3\2\2\2\u0318\u0091"+
		"\3\2\2\2\u0319\u0317\3\2\2\2\u031a\u031b\bJ\1\2\u031b\u0329\5\u0094K\2"+
		"\u031c\u0329\5\u0096L\2\u031d\u031e\7\33\2\2\u031e\u0329\5\u009aN\2\u031f"+
		"\u0320\7U\2\2\u0320\u0321\5.\30\2\u0321\u0322\7V\2\2\u0322\u0323\5\u0092"+
		"J\23\u0323\u0329\3\2\2\2\u0324\u0325\t\5\2\2\u0325\u0329\5\u0092J\21\u0326"+
		"\u0327\t\6\2\2\u0327\u0329\5\u0092J\20\u0328\u031a\3\2\2\2\u0328\u031c"+
		"\3\2\2\2\u0328\u031d\3\2\2\2\u0328\u031f\3\2\2\2\u0328\u0324\3\2\2\2\u0328"+
		"\u0326\3\2\2\2\u0329\u036e\3\2\2\2\u032a\u032b\f\17\2\2\u032b\u032c\t"+
		"\7\2\2\u032c\u036d\5\u0092J\20\u032d\u032e\f\16\2\2\u032e\u032f\t\b\2"+
		"\2\u032f\u036d\5\u0092J\17\u0330\u0338\f\r\2\2\u0331\u0332\7`\2\2\u0332"+
		"\u0339\7`\2\2\u0333\u0334\7_\2\2\u0334\u0335\7_\2\2\u0335\u0339\7_\2\2"+
		"\u0336\u0337\7_\2\2\u0337\u0339\7_\2\2\u0338\u0331\3\2\2\2\u0338\u0333"+
		"\3\2\2\2\u0338\u0336\3\2\2\2\u0339\u033a\3\2\2\2\u033a\u036d\5\u0092J"+
		"\16\u033b\u033c\f\f\2\2\u033c\u033e\t\t\2\2\u033d\u033f\7^\2\2\u033e\u033d"+
		"\3\2\2\2\u033e\u033f\3\2\2\2\u033f\u0340\3\2\2\2\u0340\u036d\5\u0092J"+
		"\r\u0341\u0342\f\n\2\2\u0342\u0343\t\n\2\2\u0343\u036d\5\u0092J\13\u0344"+
		"\u0345\f\t\2\2\u0345\u0346\7s\2\2\u0346\u036d\5\u0092J\n\u0347\u0348\f"+
		"\b\2\2\u0348\u0349\7u\2\2\u0349\u036d\5\u0092J\t\u034a\u034b\f\7\2\2\u034b"+
		"\u034c\7t\2\2\u034c\u036d\5\u0092J\b\u034d\u034e\f\6\2\2\u034e\u034f\7"+
		"k\2\2\u034f\u036d\5\u0092J\7\u0350\u0351\f\5\2\2\u0351\u0352\7l\2\2\u0352"+
		"\u036d\5\u0092J\6\u0353\u0354\f\4\2\2\u0354\u0355\7d\2\2\u0355\u0356\5"+
		"\u0092J\2\u0356\u0357\7e\2\2\u0357\u0358\5\u0092J\4\u0358\u036d\3\2\2"+
		"\2\u0359\u035a\f\3\2\2\u035a\u035b\t\13\2\2\u035b\u036d\5\u0092J\3\u035c"+
		"\u035d\f\27\2\2\u035d\u0360\t\f\2\2\u035e\u0361\5\u0098M\2\u035f\u0361"+
		"\5\u00ceh\2\u0360\u035e\3\2\2\2\u0360\u035f\3\2\2\2\u0361\u036d\3\2\2"+
		"\2\u0362\u0363\f\26\2\2\u0363\u0364\7Y\2\2\u0364\u0365\5\u0092J\2\u0365"+
		"\u0366\7Z\2\2\u0366\u036d\3\2\2\2\u0367\u0368\f\22\2\2\u0368\u036d\t\r"+
		"\2\2\u0369\u036a\f\13\2\2\u036a\u036b\7\30\2\2\u036b\u036d\5.\30\2\u036c"+
		"\u032a\3\2\2\2\u036c\u032d\3\2\2\2\u036c\u0330\3\2\2\2\u036c\u033b\3\2"+
		"\2\2\u036c\u0341\3\2\2\2\u036c\u0344\3\2\2\2\u036c\u0347\3\2\2\2\u036c"+
		"\u034a\3\2\2\2\u036c\u034d\3\2\2\2\u036c\u0350\3\2\2\2\u036c\u0353\3\2"+
		"\2\2\u036c\u0359\3\2\2\2\u036c\u035c\3\2\2\2\u036c\u0362\3\2\2\2\u036c"+
		"\u0367\3\2\2\2\u036c\u0369\3\2\2\2\u036d\u0370\3\2\2\2\u036e\u036c\3\2"+
		"\2\2\u036e\u036f\3\2\2\2\u036f\u0093\3\2\2\2\u0370\u036e\3\2\2\2\u0371"+
		"\u0372\7U\2\2\u0372\u0373\5\u0092J\2\u0373\u0374\7V\2\2\u0374\u037f\3"+
		"\2\2\2\u0375\u037f\7*\2\2\u0376\u037f\7\'\2\2\u0377\u037f\5> \2\u0378"+
		"\u0379\5.\30\2\u0379\u037a\7]\2\2\u037a\u037b\7\b\2\2\u037b\u037f\3\2"+
		"\2\2\u037c\u037f\5\u00ccg\2\u037d\u037f\5\u00aeX\2\u037e\u0371\3\2\2\2"+
		"\u037e\u0375\3\2\2\2\u037e\u0376\3\2\2\2\u037e\u0377\3\2\2\2\u037e\u0378"+
		"\3\2\2\2\u037e\u037c\3\2\2\2\u037e\u037d\3\2\2\2\u037f\u0095\3\2\2\2\u0380"+
		"\u0381\5\u00ccg\2\u0381\u0383\7U\2\2\u0382\u0384\5\u0090I\2\u0383\u0382"+
		"\3\2\2\2\u0383\u0384\3\2\2\2\u0384\u0385\3\2\2\2\u0385\u0386\7V\2\2\u0386"+
		"\u0394\3\2\2\2\u0387\u0388\7*\2\2\u0388\u038a\7U\2\2\u0389\u038b\5\u0090"+
		"I\2\u038a\u0389\3\2\2\2\u038a\u038b\3\2\2\2\u038b\u038c\3\2\2\2\u038c"+
		"\u0394\7V\2\2\u038d\u038e\7\'\2\2\u038e\u0390\7U\2\2\u038f\u0391\5\u0090"+
		"I\2\u0390\u038f\3\2\2\2\u0390\u0391\3\2\2\2\u0391\u0392\3\2\2\2\u0392"+
		"\u0394\7V\2\2\u0393\u0380\3\2\2\2\u0393\u0387\3\2\2\2\u0393\u038d\3\2"+
		"\2\2\u0394\u0097\3\2\2\2\u0395\u0396\5\u00ceh\2\u0396\u0398\7U\2\2\u0397"+
		"\u0399\5\u0090I\2\u0398\u0397\3\2\2\2\u0398\u0399\3\2\2\2\u0399\u039a"+
		"\3\2\2\2\u039a\u039b\7V\2\2\u039b\u0099\3\2\2\2\u039c\u03a2\5\u009cO\2"+
		"\u039d\u03a3\5\u00a0Q\2\u039e\u03a3\5\u00a2R\2\u039f\u03a3\5\u00a4S\2"+
		"\u03a0\u03a3\5\u00a6T\2\u03a1\u03a3\5\u00aaV\2\u03a2\u039d\3\2\2\2\u03a2"+
		"\u039e\3\2\2\2\u03a2\u039f\3\2\2\2\u03a2\u03a0\3\2\2\2\u03a2\u03a1\3\2"+
		"\2\2\u03a3\u009b\3\2\2\2\u03a4\u03a9\5\u009eP\2\u03a5\u03a6\7]\2\2\u03a6"+
		"\u03a8\5\u009eP\2\u03a7\u03a5\3\2\2\2\u03a8\u03ab\3\2\2\2\u03a9\u03a7"+
		"\3\2\2\2\u03a9\u03aa\3\2\2\2\u03aa\u009d\3\2\2\2\u03ab\u03a9\3\2\2\2\u03ac"+
		"\u03b1\5\u00ceh\2\u03ad\u03ae\7`\2\2\u03ae\u03af\5\22\n\2\u03af\u03b0"+
		"\7_\2\2\u03b0\u03b2\3\2\2\2\u03b1\u03ad\3\2\2\2\u03b1\u03b2\3\2\2\2\u03b2"+
		"\u009f\3\2\2\2\u03b3\u03b4\7W\2\2\u03b4\u03b5\7X\2\2\u03b5\u00a1\3\2\2"+
		"\2\u03b6\u03b7\5\u00acW\2\u03b7\u00a3\3\2\2\2\u03b8\u03b9\7Y\2\2\u03b9"+
		"\u03ba\5\u0092J\2\u03ba\u03bb\7Z\2\2\u03bb\u03c2\3\2\2\2\u03bc\u03bd\7"+
		"Y\2\2\u03bd\u03bf\7Z\2\2\u03be\u03c0\5,\27\2\u03bf\u03be\3\2\2\2\u03bf"+
		"\u03c0\3\2\2\2\u03c0\u03c2\3\2\2\2\u03c1\u03b8\3\2\2\2\u03c1\u03bc\3\2"+
		"\2\2\u03c2\u00a5\3\2\2\2\u03c3\u03c4\7W\2\2\u03c4\u03c9\5\u00a8U\2\u03c5"+
		"\u03c6\7\\\2\2\u03c6\u03c8\5\u00a8U\2\u03c7\u03c5\3\2\2\2\u03c8\u03cb"+
		"\3\2\2\2\u03c9\u03c7\3\2\2\2\u03c9\u03ca\3\2\2\2\u03ca\u03cc\3\2\2\2\u03cb"+
		"\u03c9\3\2\2\2\u03cc\u03cd\7X\2\2\u03cd\u00a7\3\2\2\2\u03ce\u03cf\5\u0092"+
		"J\2\u03cf\u03d0\7w\2\2\u03d0\u03d1\5\u0092J\2\u03d1\u00a9\3\2\2\2\u03d2"+
		"\u03d3\7W\2\2\u03d3\u03d8\5\u0092J\2\u03d4\u03d5\7\\\2\2\u03d5\u03d7\5"+
		"\u0092J\2\u03d6\u03d4\3\2\2\2\u03d7\u03da\3\2\2\2\u03d8\u03d6\3\2\2\2"+
		"\u03d8\u03d9\3\2\2\2\u03d9\u03db\3\2\2\2\u03da\u03d8\3\2\2\2\u03db\u03dc"+
		"\7X\2\2\u03dc\u00ab\3\2\2\2\u03dd\u03df\7U\2\2\u03de\u03e0\5\u0090I\2"+
		"\u03df\u03de\3\2\2\2\u03df\u03e0\3\2\2\2\u03e0\u03e1\3\2\2\2\u03e1\u03e2"+
		"\7V\2\2\u03e2\u00ad\3\2\2\2\u03e3\u03e4\7Y\2\2\u03e4\u03e5\5\u00b0Y\2"+
		"\u03e5\u03e6\7Z\2\2\u03e6\u00af\3\2\2\2\u03e7\u03e8\7;\2\2\u03e8\u03e9"+
		"\5\u00b4[\2\u03e9\u03ea\7=\2\2\u03ea\u03ec\5\u00b8]\2\u03eb\u03ed\5\u00c8"+
		"e\2\u03ec\u03eb\3\2\2\2\u03ec\u03ed\3\2\2\2\u03ed\u00b1\3\2\2\2\u03ee"+
		"\u03ef\7;\2\2\u03ef\u03f0\5\u00ba^\2\u03f0\u03f1\7=\2\2\u03f1\u03f2\5"+
		"\u00b8]\2\u03f2\u00b3\3\2\2\2\u03f3\u03f8\5\u00b6\\\2\u03f4\u03f5\7\\"+
		"\2\2\u03f5\u03f7\5\u00b6\\\2\u03f6\u03f4\3\2\2\2\u03f7\u03fa\3\2\2\2\u03f8"+
		"\u03f6\3\2\2\2\u03f8\u03f9\3\2\2\2\u03f9\u00b5\3\2\2\2\u03fa\u03f8\3\2"+
		"\2\2\u03fb\u0403\5\u00caf\2\u03fc\u0403\5\u00be`\2\u03fd\u03fe\7U\2\2"+
		"\u03fe\u03ff\5\u00b2Z\2\u03ff\u0400\7V\2\2\u0400\u0403\3\2\2\2\u0401\u0403"+
		"\5\u00c0a\2\u0402\u03fb\3\2\2\2\u0402\u03fc\3\2\2\2\u0402\u03fd\3\2\2"+
		"\2\u0402\u0401\3\2\2\2\u0403\u00b7\3\2\2\2\u0404\u0409\5\u00caf\2\u0405"+
		"\u0406\7\\\2\2\u0406\u0408\5\u00caf\2\u0407\u0405\3\2\2\2\u0408\u040b"+
		"\3\2\2\2\u0409\u0407\3\2\2\2\u0409\u040a\3\2\2\2\u040a\u00b9\3\2\2\2\u040b"+
		"\u0409\3\2\2\2\u040c\u0411\5\u00bc_\2\u040d\u040e\7\\\2\2\u040e\u0410"+
		"\5\u00bc_\2\u040f\u040d\3\2\2\2\u0410\u0413\3\2\2\2\u0411\u040f\3\2\2"+
		"\2\u0411\u0412\3\2\2\2\u0412\u00bb\3\2\2\2\u0413\u0411\3\2\2\2\u0414\u0417"+
		"\5\u00caf\2\u0415\u0417\5\u00be`\2\u0416\u0414\3\2\2\2\u0416\u0415\3\2"+
		"\2\2\u0417\u00bd\3\2\2\2\u0418\u0419\7H\2\2\u0419\u041a\7U\2\2\u041a\u041b"+
		"\5\u00caf\2\u041b\u041c\7V\2\2\u041c\u043a\3\2\2\2\u041d\u041e\7<\2\2"+
		"\u041e\u041f\7U\2\2\u041f\u043a\7V\2\2\u0420\u0421\7<\2\2\u0421\u0422"+
		"\7U\2\2\u0422\u0423\5\u00caf\2\u0423\u0424\7V\2\2\u0424\u043a\3\2\2\2"+
		"\u0425\u0426\7I\2\2\u0426\u0427\7U\2\2\u0427\u0428\5\u00caf\2\u0428\u0429"+
		"\7V\2\2\u0429\u043a\3\2\2\2\u042a\u042b\7J\2\2\u042b\u042c\7U\2\2\u042c"+
		"\u042d\5\u00caf\2\u042d\u042e\7V\2\2\u042e\u043a\3\2\2\2\u042f\u0430\7"+
		"K\2\2\u0430\u0431\7U\2\2\u0431\u0432\5\u00caf\2\u0432\u0433\7V\2\2\u0433"+
		"\u043a\3\2\2\2\u0434\u0435\7L\2\2\u0435\u0436\7U\2\2\u0436\u0437\5\u00ca"+
		"f\2\u0437\u0438\7V\2\2\u0438\u043a\3\2\2\2\u0439\u0418\3\2\2\2\u0439\u041d"+
		"\3\2\2\2\u0439\u0420\3\2\2\2\u0439\u0425\3\2\2\2\u0439\u042a\3\2\2\2\u0439"+
		"\u042f\3\2\2\2\u0439\u0434\3\2\2\2\u043a\u00bf\3\2\2\2\u043b\u043c\7M"+
		"\2\2\u043c\u043e\5\u00caf\2\u043d\u043f\5\u00c2b\2\u043e\u043d\3\2\2\2"+
		"\u043f\u0440\3\2\2\2\u0440\u043e\3\2\2\2\u0440\u0441\3\2\2\2\u0441\u0443"+
		"\3\2\2\2\u0442\u0444\5\u00c4c\2\u0443\u0442\3\2\2\2\u0443\u0444\3\2\2"+
		"\2\u0444\u0445\3\2\2\2\u0445\u0446\7N\2\2\u0446\u00c1\3\2\2\2\u0447\u0448"+
		"\7\65\2\2\u0448\u0449\5\u00caf\2\u0449\u044a\7O\2\2\u044a\u044b\5\u00c6"+
		"d\2\u044b\u00c3\3\2\2\2\u044c\u044d\7\f\2\2\u044d\u044e\5\u00c6d\2\u044e"+
		"\u00c5\3\2\2\2\u044f\u0454\5\u00caf\2\u0450\u0451\7\\\2\2\u0451\u0453"+
		"\5\u00caf\2\u0452\u0450\3\2\2\2\u0453\u0456\3\2\2\2\u0454\u0452\3\2\2"+
		"\2\u0454\u0455\3\2\2\2\u0455\u00c7\3\2\2\2\u0456\u0454\3\2\2\2\u0457\u0458"+
		"\7?\2\2\u0458\u0459\7@\2\2\u0459\u045a\5\u00caf\2\u045a\u00c9\3\2\2\2"+
		"\u045b\u045c\5\u00ccg\2\u045c\u00cb\3\2\2\2\u045d\u045e\t\16\2\2\u045e"+
		"\u00cd\3\2\2\2\u045f\u0460\t\17\2\2\u0460\u00cf\3\2\2\2i\u00da\u00ea\u00f1"+
		"\u00f8\u00fc\u0102\u0106\u010e\u0117\u011e\u0127\u012e\u0137\u013e\u0144"+
		"\u0148\u015d\u0166\u016a\u0170\u0180\u0188\u018d\u0198\u019e\u01a6\u01aa"+
		"\u01ac\u01b5\u01be\u01c3\u01c7\u01cb\u01cf\u01d1\u01d9\u01e2\u01e8\u01f3"+
		"\u01fd\u0200\u0204\u0209\u0213\u021b\u021e\u0221\u0229\u0234\u024e\u0255"+
		"\u025e\u026c\u0272\u0275\u027b\u0292\u0295\u0298\u029c\u02bd\u02c9\u02d4"+
		"\u02d9\u02de\u02e3\u02ea\u02f7\u02fb\u02ff\u0301\u0305\u0317\u0328\u0338"+
		"\u033e\u0360\u036c\u036e\u037e\u0383\u038a\u0390\u0393\u0398\u03a2\u03a9"+
		"\u03b1\u03bf\u03c1\u03c9\u03d8\u03df\u03ec\u03f8\u0402\u0409\u0411\u0416"+
		"\u0439\u0440\u0443\u0454";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}