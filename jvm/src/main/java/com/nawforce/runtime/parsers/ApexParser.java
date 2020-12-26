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
		BY=65, LIMIT=66, SOQLAND=67, SOQLOR=68, NOT=69, AVG=70, COUNT_DISTINCT=71, 
		MIN=72, MAX=73, SUM=74, TYPEOF=75, END=76, THEN=77, LIKE=78, IN=79, INCLUDES=80, 
		EXCLUDES=81, ASC=82, DESC=83, NULLS=84, FIRST=85, LAST=86, GROUP=87, ALL=88, 
		ROWS=89, VIEW=90, HAVING=91, ROLLUP=92, TOLABEL=93, OFFSET=94, DATA=95, 
		CATEGORY=96, AT=97, ABOVE=98, BELOW=99, ABOVE_OR_BELOW=100, SECURITY_ENFORCED=101, 
		REFERENCE=102, CUBE=103, YESTERDAY=104, TODAY=105, TOMORROW=106, LAST_WEEK=107, 
		THIS_WEEK=108, NEXT_WEEK=109, LAST_MONTH=110, THIS_MONTH=111, NEXT_MONTH=112, 
		LAST_90_DAYS=113, NEXT_90_DAYS=114, LAST_N_DAYS_N=115, NEXT_N_DAYS_N=116, 
		NEXT_N_WEEKS_N=117, LAST_N_WEEKS_N=118, NEXT_N_MONTHS_N=119, LAST_N_MONTHS_N=120, 
		THIS_QUARTER=121, LAST_QUARTER=122, NEXT_QUARTER=123, NEXT_N_QUARTERS_N=124, 
		LAST_N_QUARTERS_N=125, THIS_YEAR=126, LAST_YEAR=127, NEXT_YEAR=128, NEXT_N_YEARS_N=129, 
		LAST_N_YEARS_N=130, THIS_FISCAL_QUARTER=131, LAST_FISCAL_QUARTER=132, 
		NEXT_FISCAL_QUARTER=133, NEXT_N_FISCAL_QUARTERS_N=134, LAST_N_FISCAL_QUARTERS_N=135, 
		THIS_FISCAL_YEAR=136, LAST_FISCAL_YEAR=137, NEXT_FISCAL_YEAR=138, NEXT_N_FISCAL_YEARS_N=139, 
		LAST_N_FISCAL_YEARS_N=140, DateLiteral=141, DateTimeLiteral=142, IntegerLiteral=143, 
		LongLiteral=144, NumberLiteral=145, BooleanLiteral=146, StringLiteral=147, 
		NullLiteral=148, LPAREN=149, RPAREN=150, LBRACE=151, RBRACE=152, LBRACK=153, 
		RBRACK=154, SEMI=155, COMMA=156, DOT=157, ASSIGN=158, GT=159, LT=160, 
		BANG=161, TILDE=162, QUESTIONDOT=163, QUESTION=164, COLON=165, EQUAL=166, 
		TRIPLEEQUAL=167, NOTEQUAL=168, LESSANDGREATER=169, TRIPLENOTEQUAL=170, 
		AND=171, OR=172, INC=173, DEC=174, ADD=175, SUB=176, MUL=177, DIV=178, 
		BITAND=179, BITOR=180, CARET=181, MOD=182, MAPTO=183, ADD_ASSIGN=184, 
		SUB_ASSIGN=185, MUL_ASSIGN=186, DIV_ASSIGN=187, AND_ASSIGN=188, OR_ASSIGN=189, 
		XOR_ASSIGN=190, MOD_ASSIGN=191, LSHIFT_ASSIGN=192, RSHIFT_ASSIGN=193, 
		URSHIFT_ASSIGN=194, ATSIGN=195, Identifier=196, WS=197, DOC_COMMENT=198, 
		COMMENT=199, LINE_COMMENT=200;
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
		RULE_selectList = 89, RULE_selectEntry = 90, RULE_fieldName = 91, RULE_fromNameList = 92, 
		RULE_subFieldList = 93, RULE_subFieldEntry = 94, RULE_aggregateFunction = 95, 
		RULE_typeOf = 96, RULE_whenClause = 97, RULE_elseClause = 98, RULE_fieldNameList = 99, 
		RULE_usingScope = 100, RULE_whereClause = 101, RULE_logicalExpression = 102, 
		RULE_conditionalExpression = 103, RULE_fieldExpression = 104, RULE_comparisonOperator = 105, 
		RULE_value = 106, RULE_valueList = 107, RULE_currencyValue = 108, RULE_withClause = 109, 
		RULE_filteringExpression = 110, RULE_dataCategorySelection = 111, RULE_dataCategoryName = 112, 
		RULE_filteringSelector = 113, RULE_groupByClause = 114, RULE_orderByClause = 115, 
		RULE_fieldOrderList = 116, RULE_fieldOrder = 117, RULE_limitClause = 118, 
		RULE_offsetClause = 119, RULE_allRowsClause = 120, RULE_forClauses = 121, 
		RULE_boundExpression = 122, RULE_dateFormula = 123, RULE_signedInteger = 124, 
		RULE_soqlId = 125, RULE_id = 126, RULE_anyId = 127;
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
			"setCreatorRest", "arguments", "soqlLiteral", "query", "subQuery", "selectList", 
			"selectEntry", "fieldName", "fromNameList", "subFieldList", "subFieldEntry", 
			"aggregateFunction", "typeOf", "whenClause", "elseClause", "fieldNameList", 
			"usingScope", "whereClause", "logicalExpression", "conditionalExpression", 
			"fieldExpression", "comparisonOperator", "value", "valueList", "currencyValue", 
			"withClause", "filteringExpression", "dataCategorySelection", "dataCategoryName", 
			"filteringSelector", "groupByClause", "orderByClause", "fieldOrderList", 
			"fieldOrder", "limitClause", "offsetClause", "allRowsClause", "forClauses", 
			"boundExpression", "dateFormula", "signedInteger", "soqlId", "id", "anyId"
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
			"'min'", "'max'", "'sum'", "'typeof'", "'end'", "'then'", "'like'", "'in'", 
			"'includes'", "'excludes'", "'asc'", "'desc'", "'nulls'", "'first'", 
			"'last'", "'group'", "'all'", "'rows'", "'view'", "'having'", "'rollup'", 
			"'tolabel'", "'offset'", "'data'", "'category'", "'at'", "'above'", "'below'", 
			"'above_or_below'", "'security_enforced'", "'reference'", "'cube'", "'yesterday'", 
			"'today'", "'tomorrow'", "'last_week'", "'this_week'", "'next_week'", 
			"'last_month'", "'this_month'", "'next_month'", "'last_90_days'", "'next_90_days'", 
			"'last_n_days'", "'next_n_days'", "'next_n_weeks'", "'last_n_weeks'", 
			"'next_n_months'", "'last_n_months'", "'this_quarter'", "'last_quarted'", 
			"'next_quarter'", "'next_n_quarters'", "'last_n_quarters'", "'this_year'", 
			"'last_year'", "'next_year'", "'next_n_years'", "'last_n_years'", "'this_fiscal_quarter'", 
			"'last_fiscal_quarter'", "'next_fiscal_quarter'", "'next_n_fiscal_quarters'", 
			"'last_n_fiscal_quarters'", "'this_fiscal_year'", "'last_fiscal_year'", 
			"'next_fiscal_year'", "'next_n_fiscal_years'", "'last_n_fiscal_years'", 
			null, null, null, null, null, null, null, null, "'('", "')'", "'{'", 
			"'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'>'", "'<'", "'!'", 
			"'~'", "'?.'", "'?'", "':'", "'=='", "'==='", "'!='", "'<>'", "'!=='", 
			"'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", 
			"'^'", "'%'", "'=>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", 
			"'^='", "'%='", "'<<='", "'>>='", "'>>>='", "'@'"
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
			"AS", "USING", "SCOPE", "WHERE", "ORDER", "BY", "LIMIT", "SOQLAND", "SOQLOR", 
			"NOT", "AVG", "COUNT_DISTINCT", "MIN", "MAX", "SUM", "TYPEOF", "END", 
			"THEN", "LIKE", "IN", "INCLUDES", "EXCLUDES", "ASC", "DESC", "NULLS", 
			"FIRST", "LAST", "GROUP", "ALL", "ROWS", "VIEW", "HAVING", "ROLLUP", 
			"TOLABEL", "OFFSET", "DATA", "CATEGORY", "AT", "ABOVE", "BELOW", "ABOVE_OR_BELOW", 
			"SECURITY_ENFORCED", "REFERENCE", "CUBE", "YESTERDAY", "TODAY", "TOMORROW", 
			"LAST_WEEK", "THIS_WEEK", "NEXT_WEEK", "LAST_MONTH", "THIS_MONTH", "NEXT_MONTH", 
			"LAST_90_DAYS", "NEXT_90_DAYS", "LAST_N_DAYS_N", "NEXT_N_DAYS_N", "NEXT_N_WEEKS_N", 
			"LAST_N_WEEKS_N", "NEXT_N_MONTHS_N", "LAST_N_MONTHS_N", "THIS_QUARTER", 
			"LAST_QUARTER", "NEXT_QUARTER", "NEXT_N_QUARTERS_N", "LAST_N_QUARTERS_N", 
			"THIS_YEAR", "LAST_YEAR", "NEXT_YEAR", "NEXT_N_YEARS_N", "LAST_N_YEARS_N", 
			"THIS_FISCAL_QUARTER", "LAST_FISCAL_QUARTER", "NEXT_FISCAL_QUARTER", 
			"NEXT_N_FISCAL_QUARTERS_N", "LAST_N_FISCAL_QUARTERS_N", "THIS_FISCAL_YEAR", 
			"LAST_FISCAL_YEAR", "NEXT_FISCAL_YEAR", "NEXT_N_FISCAL_YEARS_N", "LAST_N_FISCAL_YEARS_N", 
			"DateLiteral", "DateTimeLiteral", "IntegerLiteral", "LongLiteral", "NumberLiteral", 
			"BooleanLiteral", "StringLiteral", "NullLiteral", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", 
			"GT", "LT", "BANG", "TILDE", "QUESTIONDOT", "QUESTION", "COLON", "EQUAL", 
			"TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", 
			"MOD", "MAPTO", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "ATSIGN", "Identifier", "WS", "DOC_COMMENT", 
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
			setState(256);
			match(TRIGGER);
			setState(257);
			id();
			setState(258);
			match(ON);
			setState(259);
			id();
			setState(260);
			match(LPAREN);
			setState(261);
			triggerCase();
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(262);
				match(COMMA);
				setState(263);
				triggerCase();
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(269);
			match(RPAREN);
			setState(270);
			block();
			setState(271);
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
			setState(273);
			_la = _input.LA(1);
			if ( !(_la==AFTER || _la==BEFORE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(274);
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
			setState(276);
			typeDeclaration();
			setState(277);
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
			setState(300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==ATSIGN) {
					{
					{
					setState(279);
					modifier();
					}
					}
					setState(284);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(285);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==ATSIGN) {
					{
					{
					setState(286);
					modifier();
					}
					}
					setState(291);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(292);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==ATSIGN) {
					{
					{
					setState(293);
					modifier();
					}
					}
					setState(298);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(299);
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
			setState(302);
			match(CLASS);
			setState(303);
			id();
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(304);
				match(EXTENDS);
				setState(305);
				typeRef();
				}
			}

			setState(310);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(308);
				match(IMPLEMENTS);
				setState(309);
				typeList();
				}
			}

			setState(312);
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
			setState(314);
			match(ENUM);
			setState(315);
			id();
			setState(316);
			match(LBRACE);
			setState(318);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==Identifier) {
				{
				setState(317);
				enumConstants();
				}
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
			setState(322);
			id();
			setState(327);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(323);
				match(COMMA);
				setState(324);
				id();
				}
				}
				setState(329);
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
			setState(330);
			match(INTERFACE);
			setState(331);
			id();
			setState(334);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(332);
				match(EXTENDS);
				setState(333);
				typeList();
				}
			}

			setState(336);
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
			setState(338);
			typeRef();
			setState(343);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(339);
				match(COMMA);
				setState(340);
				typeRef();
				}
				}
				setState(345);
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
			setState(346);
			match(LBRACE);
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (LBRACE - 128)) | (1L << (SEMI - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
				{
				{
				setState(347);
				classBodyDeclaration();
				}
				}
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(353);
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
			setState(355);
			match(LBRACE);
			setState(359);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << VOID) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
				{
				{
				setState(356);
				interfaceMethodDeclaration();
				}
				}
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(362);
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
			setState(376);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(364);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(365);
					match(STATIC);
					}
				}

				setState(368);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(369);
						modifier();
						}
						} 
					}
					setState(374);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				setState(375);
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
			setState(397);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATSIGN:
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				annotation();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				match(GLOBAL);
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 3);
				{
				setState(380);
				match(PUBLIC);
				}
				break;
			case PROTECTED:
				enterOuterAlt(_localctx, 4);
				{
				setState(381);
				match(PROTECTED);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(382);
				match(PRIVATE);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 6);
				{
				setState(383);
				match(TRANSIENT);
				}
				break;
			case STATIC:
				enterOuterAlt(_localctx, 7);
				{
				setState(384);
				match(STATIC);
				}
				break;
			case ABSTRACT:
				enterOuterAlt(_localctx, 8);
				{
				setState(385);
				match(ABSTRACT);
				}
				break;
			case FINAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(386);
				match(FINAL);
				}
				break;
			case WEBSERVICE:
				enterOuterAlt(_localctx, 10);
				{
				setState(387);
				match(WEBSERVICE);
				}
				break;
			case OVERRIDE:
				enterOuterAlt(_localctx, 11);
				{
				setState(388);
				match(OVERRIDE);
				}
				break;
			case VIRTUAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(389);
				match(VIRTUAL);
				}
				break;
			case TESTMETHOD:
				enterOuterAlt(_localctx, 13);
				{
				setState(390);
				match(TESTMETHOD);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 14);
				{
				setState(391);
				match(WITH);
				setState(392);
				match(SHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 15);
				{
				setState(393);
				match(WITHOUT);
				setState(394);
				match(SHARING);
				}
				break;
			case INHERITED:
				enterOuterAlt(_localctx, 16);
				{
				setState(395);
				match(INHERITED);
				setState(396);
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
			setState(406);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(399);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(400);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(401);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(402);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(403);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(404);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(405);
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
			setState(410);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				{
				setState(408);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(409);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(412);
			id();
			setState(413);
			formalParameters();
			setState(416);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(414);
				block();
				}
				break;
			case SEMI:
				{
				setState(415);
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
			setState(418);
			qualifiedName();
			setState(419);
			formalParameters();
			setState(420);
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
			setState(422);
			typeRef();
			setState(423);
			variableDeclarators();
			setState(424);
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
			setState(426);
			typeRef();
			setState(427);
			id();
			setState(428);
			match(LBRACE);
			setState(432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==ATSIGN) {
				{
				{
				setState(429);
				propertyBlock();
				}
				}
				setState(434);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(435);
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
			setState(440);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(437);
					modifier();
					}
					} 
				}
				setState(442);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			setState(445);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				{
				setState(443);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(444);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(447);
			id();
			setState(448);
			formalParameters();
			setState(449);
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
			setState(451);
			variableDeclarator();
			setState(456);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(452);
				match(COMMA);
				setState(453);
				variableDeclarator();
				}
				}
				setState(458);
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
			setState(459);
			id();
			setState(462);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(460);
				match(ASSIGN);
				setState(461);
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
			setState(464);
			match(LBRACE);
			setState(476);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
				{
				setState(465);
				expression(0);
				setState(470);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(466);
						match(COMMA);
						setState(467);
						expression(0);
						}
						} 
					}
					setState(472);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(474);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(473);
					match(COMMA);
					}
				}

				}
			}

			setState(478);
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
			setState(480);
			typeName();
			setState(485);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(481);
					match(DOT);
					setState(482);
					typeName();
					}
					} 
				}
				setState(487);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			setState(488);
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
			setState(494);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(490);
					match(LBRACK);
					setState(491);
					match(RBRACK);
					}
					} 
				}
				setState(496);
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
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(497);
				match(LIST);
				setState(499);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
				case 1:
					{
					setState(498);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(501);
				match(SET);
				setState(503);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(502);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(505);
				match(MAP);
				setState(507);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
				case 1:
					{
					setState(506);
					typeArguments();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(509);
				id();
				setState(511);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(510);
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
			setState(515);
			match(LT);
			setState(516);
			typeList();
			setState(517);
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
			setState(519);
			match(LPAREN);
			setState(521);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
				{
				setState(520);
				formalParameterList();
				}
			}

			setState(523);
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
			setState(525);
			formalParameter();
			setState(530);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(526);
				match(COMMA);
				setState(527);
				formalParameter();
				}
				}
				setState(532);
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
			setState(536);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(533);
					modifier();
					}
					} 
				}
				setState(538);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			setState(539);
			typeRef();
			setState(540);
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
			setState(542);
			id();
			setState(547);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(543);
				match(DOT);
				setState(544);
				id();
				}
				}
				setState(549);
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
		public TerminalNode LongLiteral() { return getToken(ApexParser.LongLiteral, 0); }
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
			setState(550);
			_la = _input.LA(1);
			if ( !(_la==NULL || ((((_la - 143)) & ~0x3f) == 0 && ((1L << (_la - 143)) & ((1L << (IntegerLiteral - 143)) | (1L << (LongLiteral - 143)) | (1L << (NumberLiteral - 143)) | (1L << (BooleanLiteral - 143)) | (1L << (StringLiteral - 143)))) != 0)) ) {
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
		public TerminalNode ATSIGN() { return getToken(ApexParser.ATSIGN, 0); }
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
			setState(552);
			match(ATSIGN);
			setState(553);
			qualifiedName();
			setState(560);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(554);
				match(LPAREN);
				setState(557);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(555);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(556);
					elementValue();
					}
					break;
				}
				setState(559);
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
			setState(562);
			elementValuePair();
			setState(569);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (COMMA - 128)))) != 0) || _la==Identifier) {
				{
				{
				setState(564);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(563);
					match(COMMA);
					}
				}

				setState(566);
				elementValuePair();
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
			setState(572);
			id();
			setState(573);
			match(ASSIGN);
			setState(574);
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
			setState(579);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case IntegerLiteral:
			case LongLiteral:
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
				setState(576);
				expression(0);
				}
				break;
			case ATSIGN:
				enterOuterAlt(_localctx, 2);
				{
				setState(577);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(578);
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
			setState(581);
			match(LBRACE);
			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACE - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
				{
				setState(582);
				elementValue();
				setState(587);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(583);
						match(COMMA);
						setState(584);
						elementValue();
						}
						} 
					}
					setState(589);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
				}
				}
			}

			setState(593);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(592);
				match(COMMA);
				}
			}

			setState(595);
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
			setState(597);
			match(LBRACE);
			setState(601);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << BREAK) | (1L << CONTINUE) | (1L << DELETE) | (1L << DO) | (1L << FINAL) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SYSTEMRUNAS) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACE - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
				{
				{
				setState(598);
				statement();
				}
				}
				setState(603);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(604);
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
			setState(606);
			localVariableDeclaration();
			setState(607);
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
			setState(612);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(609);
					modifier();
					}
					} 
				}
				setState(614);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			setState(615);
			typeRef();
			setState(616);
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
			setState(638);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(618);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(619);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(620);
				switchStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(621);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(622);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(623);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(624);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(625);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(626);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(627);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(628);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(629);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(630);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(631);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(632);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(633);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(634);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(635);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(636);
				localVariableDeclarationStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(637);
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
			setState(640);
			match(IF);
			setState(641);
			parExpression();
			setState(642);
			statement();
			setState(645);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(643);
				match(ELSE);
				setState(644);
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
			setState(647);
			match(SWITCH);
			setState(648);
			match(ON);
			setState(649);
			expression(0);
			setState(650);
			match(LBRACE);
			setState(652); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(651);
				whenControl();
				}
				}
				setState(654); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(656);
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
			setState(658);
			match(WHEN);
			setState(659);
			whenValue();
			setState(660);
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
			setState(674);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(662);
				match(ELSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(663);
				whenLiteral();
				setState(668);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(664);
					match(COMMA);
					setState(665);
					whenLiteral();
					}
					}
					setState(670);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(671);
				id();
				setState(672);
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
		public TerminalNode LongLiteral() { return getToken(ApexParser.LongLiteral, 0); }
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
			setState(684);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case SUB:
				enterOuterAlt(_localctx, 1);
				{
				setState(677);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SUB) {
					{
					setState(676);
					match(SUB);
					}
				}

				setState(679);
				match(IntegerLiteral);
				}
				break;
			case LongLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(680);
				match(LongLiteral);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(681);
				match(StringLiteral);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 4);
				{
				setState(682);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(683);
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
			setState(686);
			match(FOR);
			setState(687);
			match(LPAREN);
			setState(688);
			forControl();
			setState(689);
			match(RPAREN);
			setState(690);
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
			setState(692);
			match(WHILE);
			setState(693);
			parExpression();
			setState(694);
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
			setState(696);
			match(DO);
			setState(697);
			statement();
			setState(698);
			match(WHILE);
			setState(699);
			parExpression();
			setState(700);
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
			setState(702);
			match(TRY);
			setState(703);
			block();
			setState(713);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(705); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(704);
					catchClause();
					}
					}
					setState(707); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CATCH );
				setState(710);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(709);
					finallyBlock();
					}
				}

				}
				break;
			case FINALLY:
				{
				setState(712);
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
			setState(715);
			match(RETURN);
			setState(717);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
				{
				setState(716);
				expression(0);
				}
			}

			setState(719);
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
			setState(721);
			match(THROW);
			setState(722);
			expression(0);
			setState(723);
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
			setState(725);
			match(BREAK);
			setState(726);
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
			setState(728);
			match(CONTINUE);
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
			setState(731);
			match(INSERT);
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
			setState(735);
			match(UPDATE);
			setState(736);
			expression(0);
			setState(737);
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
			setState(739);
			match(DELETE);
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
			setState(743);
			match(UNDELETE);
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
			setState(747);
			match(UPSERT);
			setState(748);
			expression(0);
			setState(750);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==Identifier) {
				{
				setState(749);
				qualifiedName();
				}
			}

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
			setState(754);
			match(MERGE);
			setState(755);
			expression(0);
			setState(756);
			expression(0);
			setState(757);
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
			setState(759);
			match(SYSTEMRUNAS);
			setState(760);
			match(LPAREN);
			setState(762);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
				{
				setState(761);
				expressionList();
				}
			}

			setState(764);
			match(RPAREN);
			setState(765);
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
			setState(767);
			expression(0);
			setState(768);
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
			setState(773);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << GLOBAL) | (1L << INHERITED) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << TESTMETHOD) | (1L << TRANSIENT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WITH) | (1L << WITHOUT))) != 0) || _la==ATSIGN) {
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
			setState(790);
			match(CATCH);
			setState(791);
			match(LPAREN);
			setState(795);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
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
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			}
			setState(798);
			qualifiedName();
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
			setState(803);
			match(FINALLY);
			setState(804);
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
			setState(818);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(806);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(808);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << FINAL) | (1L << GET) | (1L << GLOBAL) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==ATSIGN || _la==Identifier) {
					{
					setState(807);
					forInit();
					}
				}

				setState(810);
				match(SEMI);
				setState(812);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
					{
					setState(811);
					expression(0);
					}
				}

				setState(814);
				match(SEMI);
				setState(816);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
					{
					setState(815);
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
			setState(822);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(820);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(821);
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
			setState(824);
			typeRef();
			setState(825);
			id();
			setState(826);
			match(COLON);
			setState(827);
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
			setState(829);
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
			setState(831);
			match(LPAREN);
			setState(832);
			expression(0);
			setState(833);
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
			setState(835);
			expression(0);
			setState(840);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(836);
				match(COMMA);
				setState(837);
				expression(0);
				}
				}
				setState(842);
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
			setState(857);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				{
				_localctx = new PrimaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(844);
				primary();
				}
				break;
			case 2:
				{
				_localctx = new MethodCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(845);
				methodCall();
				}
				break;
			case 3:
				{
				_localctx = new NewExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(846);
				match(NEW);
				setState(847);
				creator();
				}
				break;
			case 4:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(848);
				match(LPAREN);
				setState(849);
				typeRef();
				setState(850);
				match(RPAREN);
				setState(851);
				expression(17);
				}
				break;
			case 5:
				{
				_localctx = new PreOpExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(853);
				_la = _input.LA(1);
				if ( !(((((_la - 173)) & ~0x3f) == 0 && ((1L << (_la - 173)) & ((1L << (INC - 173)) | (1L << (DEC - 173)) | (1L << (ADD - 173)) | (1L << (SUB - 173)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(854);
				expression(15);
				}
				break;
			case 6:
				{
				_localctx = new NegExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(855);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(856);
				expression(14);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(927);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,78,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(925);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
					case 1:
						{
						_localctx = new Arth1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(859);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(860);
						_la = _input.LA(1);
						if ( !(((((_la - 177)) & ~0x3f) == 0 && ((1L << (_la - 177)) & ((1L << (MUL - 177)) | (1L << (DIV - 177)) | (1L << (MOD - 177)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(861);
						expression(14);
						}
						break;
					case 2:
						{
						_localctx = new Arth2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(862);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(863);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(864);
						expression(13);
						}
						break;
					case 3:
						{
						_localctx = new BitExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(865);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(873);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
						case 1:
							{
							setState(866);
							match(LT);
							setState(867);
							match(LT);
							}
							break;
						case 2:
							{
							setState(868);
							match(GT);
							setState(869);
							match(GT);
							setState(870);
							match(GT);
							}
							break;
						case 3:
							{
							setState(871);
							match(GT);
							setState(872);
							match(GT);
							}
							break;
						}
						setState(875);
						expression(12);
						}
						break;
					case 4:
						{
						_localctx = new CmpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(876);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(877);
						_la = _input.LA(1);
						if ( !(_la==GT || _la==LT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(879);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==ASSIGN) {
							{
							setState(878);
							match(ASSIGN);
							}
						}

						setState(881);
						expression(11);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(882);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(883);
						_la = _input.LA(1);
						if ( !(((((_la - 166)) & ~0x3f) == 0 && ((1L << (_la - 166)) & ((1L << (EQUAL - 166)) | (1L << (TRIPLEEQUAL - 166)) | (1L << (NOTEQUAL - 166)) | (1L << (LESSANDGREATER - 166)) | (1L << (TRIPLENOTEQUAL - 166)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(884);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new BitAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(885);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(886);
						match(BITAND);
						setState(887);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new BitNotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(888);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(889);
						match(CARET);
						setState(890);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new BitOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(891);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(892);
						match(BITOR);
						setState(893);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new LogAndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(894);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(895);
						match(AND);
						setState(896);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new LogOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(897);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(898);
						match(OR);
						setState(899);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new CondExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(900);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(901);
						match(QUESTION);
						setState(902);
						expression(0);
						setState(903);
						match(COLON);
						setState(904);
						expression(2);
						}
						break;
					case 12:
						{
						_localctx = new AssignExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(906);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(907);
						_la = _input.LA(1);
						if ( !(((((_la - 158)) & ~0x3f) == 0 && ((1L << (_la - 158)) & ((1L << (ASSIGN - 158)) | (1L << (ADD_ASSIGN - 158)) | (1L << (SUB_ASSIGN - 158)) | (1L << (MUL_ASSIGN - 158)) | (1L << (DIV_ASSIGN - 158)) | (1L << (AND_ASSIGN - 158)) | (1L << (OR_ASSIGN - 158)) | (1L << (XOR_ASSIGN - 158)) | (1L << (MOD_ASSIGN - 158)) | (1L << (LSHIFT_ASSIGN - 158)) | (1L << (RSHIFT_ASSIGN - 158)) | (1L << (URSHIFT_ASSIGN - 158)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(908);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new DotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(909);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(910);
						_la = _input.LA(1);
						if ( !(_la==DOT || _la==QUESTIONDOT) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(913);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
						case 1:
							{
							setState(911);
							dotMethodCall();
							}
							break;
						case 2:
							{
							setState(912);
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
						setState(915);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(916);
						match(LBRACK);
						setState(917);
						expression(0);
						setState(918);
						match(RBRACK);
						}
						break;
					case 15:
						{
						_localctx = new PostOpExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(920);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(921);
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
						setState(922);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(923);
						match(INSTANCEOF);
						setState(924);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(929);
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
			setState(943);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				_localctx = new SubPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(930);
				match(LPAREN);
				setState(931);
				expression(0);
				setState(932);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ThisPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(934);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new SuperPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(935);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new LiteralPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(936);
				literal();
				}
				break;
			case 5:
				_localctx = new TypeRefPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(937);
				typeRef();
				setState(938);
				match(DOT);
				setState(939);
				match(CLASS);
				}
				break;
			case 6:
				_localctx = new IdPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(941);
				id();
				}
				break;
			case 7:
				_localctx = new SoqlPrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(942);
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
			setState(964);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(945);
				id();
				setState(946);
				match(LPAREN);
				setState(948);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
					{
					setState(947);
					expressionList();
					}
				}

				setState(950);
				match(RPAREN);
				}
				break;
			case THIS:
				enterOuterAlt(_localctx, 2);
				{
				setState(952);
				match(THIS);
				setState(953);
				match(LPAREN);
				setState(955);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
					{
					setState(954);
					expressionList();
					}
				}

				setState(957);
				match(RPAREN);
				}
				break;
			case SUPER:
				enterOuterAlt(_localctx, 3);
				{
				setState(958);
				match(SUPER);
				setState(959);
				match(LPAREN);
				setState(961);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
					{
					setState(960);
					expressionList();
					}
				}

				setState(963);
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
			setState(966);
			anyId();
			setState(967);
			match(LPAREN);
			setState(969);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
				{
				setState(968);
				expressionList();
				}
			}

			setState(971);
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
			setState(973);
			createdName();
			setState(979);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				{
				setState(974);
				noRest();
				}
				break;
			case 2:
				{
				setState(975);
				classCreatorRest();
				}
				break;
			case 3:
				{
				setState(976);
				arrayCreatorRest();
				}
				break;
			case 4:
				{
				setState(977);
				mapCreatorRest();
				}
				break;
			case 5:
				{
				setState(978);
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
			setState(981);
			idCreatedNamePair();
			setState(986);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(982);
				match(DOT);
				setState(983);
				idCreatedNamePair();
				}
				}
				setState(988);
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
			setState(989);
			anyId();
			setState(994);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(990);
				match(LT);
				setState(991);
				typeList();
				setState(992);
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
			setState(996);
			match(LBRACE);
			setState(997);
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
			setState(999);
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
			setState(1010);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,89,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1001);
				match(LBRACK);
				setState(1002);
				expression(0);
				setState(1003);
				match(RBRACK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1005);
				match(LBRACK);
				setState(1006);
				match(RBRACK);
				setState(1008);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
				case 1:
					{
					setState(1007);
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
			setState(1012);
			match(LBRACE);
			setState(1013);
			mapCreatorRestPair();
			setState(1018);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1014);
				match(COMMA);
				setState(1015);
				mapCreatorRestPair();
				}
				}
				setState(1020);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1021);
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
			setState(1023);
			expression(0);
			setState(1024);
			match(MAPTO);
			setState(1025);
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
			setState(1027);
			match(LBRACE);
			setState(1028);
			expression(0);
			setState(1033);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1029);
				match(COMMA);
				{
				setState(1030);
				expression(0);
				}
				}
				}
				setState(1035);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1036);
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
			setState(1038);
			match(LPAREN);
			setState(1040);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << NEW) | (1L << NULL) | (1L << SET) | (1L << SHARING) | (1L << SUPER) | (1L << SWITCH) | (1L << THIS) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)) | (1L << (IntegerLiteral - 128)) | (1L << (LongLiteral - 128)) | (1L << (NumberLiteral - 128)) | (1L << (BooleanLiteral - 128)) | (1L << (StringLiteral - 128)) | (1L << (LPAREN - 128)) | (1L << (LBRACK - 128)) | (1L << (BANG - 128)) | (1L << (TILDE - 128)) | (1L << (INC - 128)) | (1L << (DEC - 128)) | (1L << (ADD - 128)) | (1L << (SUB - 128)))) != 0) || _la==Identifier) {
				{
				setState(1039);
				expressionList();
				}
			}

			setState(1042);
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
			setState(1044);
			match(LBRACK);
			setState(1045);
			query();
			setState(1046);
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
		public SelectListContext selectList() {
			return getRuleContext(SelectListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(ApexParser.FROM, 0); }
		public FromNameListContext fromNameList() {
			return getRuleContext(FromNameListContext.class,0);
		}
		public ForClausesContext forClauses() {
			return getRuleContext(ForClausesContext.class,0);
		}
		public UsingScopeContext usingScope() {
			return getRuleContext(UsingScopeContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public WithClauseContext withClause() {
			return getRuleContext(WithClauseContext.class,0);
		}
		public GroupByClauseContext groupByClause() {
			return getRuleContext(GroupByClauseContext.class,0);
		}
		public OrderByClauseContext orderByClause() {
			return getRuleContext(OrderByClauseContext.class,0);
		}
		public LimitClauseContext limitClause() {
			return getRuleContext(LimitClauseContext.class,0);
		}
		public OffsetClauseContext offsetClause() {
			return getRuleContext(OffsetClauseContext.class,0);
		}
		public AllRowsClauseContext allRowsClause() {
			return getRuleContext(AllRowsClauseContext.class,0);
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
			setState(1048);
			match(SELECT);
			setState(1049);
			selectList();
			setState(1050);
			match(FROM);
			setState(1051);
			fromNameList();
			setState(1053);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==USING) {
				{
				setState(1052);
				usingScope();
				}
			}

			setState(1056);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(1055);
				whereClause();
				}
			}

			setState(1059);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(1058);
				withClause();
				}
			}

			setState(1062);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GROUP) {
				{
				setState(1061);
				groupByClause();
				}
			}

			setState(1065);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(1064);
				orderByClause();
				}
			}

			setState(1068);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(1067);
				limitClause();
				}
			}

			setState(1071);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OFFSET) {
				{
				setState(1070);
				offsetClause();
				}
			}

			setState(1074);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALL) {
				{
				setState(1073);
				allRowsClause();
				}
			}

			setState(1076);
			forClauses();
			}
		}
		catch (RecognitionException re) {
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
		public FromNameListContext fromNameList() {
			return getRuleContext(FromNameListContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public OrderByClauseContext orderByClause() {
			return getRuleContext(OrderByClauseContext.class,0);
		}
		public LimitClauseContext limitClause() {
			return getRuleContext(LimitClauseContext.class,0);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1078);
			match(SELECT);
			setState(1079);
			subFieldList();
			setState(1080);
			match(FROM);
			setState(1081);
			fromNameList();
			setState(1083);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(1082);
				whereClause();
				}
			}

			setState(1086);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(1085);
				orderByClause();
				}
			}

			setState(1089);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(1088);
				limitClause();
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

	public static class SelectListContext extends ParserRuleContext {
		public List<SelectEntryContext> selectEntry() {
			return getRuleContexts(SelectEntryContext.class);
		}
		public SelectEntryContext selectEntry(int i) {
			return getRuleContext(SelectEntryContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public SelectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSelectList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSelectList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSelectList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectListContext selectList() throws RecognitionException {
		SelectListContext _localctx = new SelectListContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_selectList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1091);
			selectEntry();
			setState(1096);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1092);
				match(COMMA);
				setState(1093);
				selectEntry();
				}
				}
				setState(1098);
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

	public static class SelectEntryContext extends ParserRuleContext {
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
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
		public SelectEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSelectEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSelectEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSelectEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectEntryContext selectEntry() throws RecognitionException {
		SelectEntryContext _localctx = new SelectEntryContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_selectEntry);
		try {
			setState(1112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1099);
				fieldName();
				setState(1101);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
				case 1:
					{
					setState(1100);
					soqlId();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1103);
				aggregateFunction();
				setState(1105);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
				case 1:
					{
					setState(1104);
					soqlId();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1107);
				match(LPAREN);
				setState(1108);
				subQuery();
				setState(1109);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1111);
				typeOf();
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

	public static class FieldNameContext extends ParserRuleContext {
		public List<SoqlIdContext> soqlId() {
			return getRuleContexts(SoqlIdContext.class);
		}
		public SoqlIdContext soqlId(int i) {
			return getRuleContext(SoqlIdContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(ApexParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(ApexParser.DOT, i);
		}
		public FieldNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldNameContext fieldName() throws RecognitionException {
		FieldNameContext _localctx = new FieldNameContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_fieldName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1114);
			soqlId();
			setState(1119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(1115);
				match(DOT);
				setState(1116);
				soqlId();
				}
				}
				setState(1121);
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

	public static class FromNameListContext extends ParserRuleContext {
		public List<FieldNameContext> fieldName() {
			return getRuleContexts(FieldNameContext.class);
		}
		public FieldNameContext fieldName(int i) {
			return getRuleContext(FieldNameContext.class,i);
		}
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
		public FromNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fromNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFromNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFromNameList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFromNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FromNameListContext fromNameList() throws RecognitionException {
		FromNameListContext _localctx = new FromNameListContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_fromNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1122);
			fieldName();
			setState(1124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				{
				setState(1123);
				soqlId();
				}
				break;
			}
			setState(1133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1126);
				match(COMMA);
				setState(1127);
				fieldName();
				setState(1129);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
				case 1:
					{
					setState(1128);
					soqlId();
					}
					break;
				}
				}
				}
				setState(1135);
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
		enterRule(_localctx, 186, RULE_subFieldList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1136);
			subFieldEntry();
			setState(1141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1137);
				match(COMMA);
				setState(1138);
				subFieldEntry();
				}
				}
				setState(1143);
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
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
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
		enterRule(_localctx, 188, RULE_subFieldEntry);
		try {
			setState(1152);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1144);
				fieldName();
				setState(1146);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
				case 1:
					{
					setState(1145);
					soqlId();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1148);
				aggregateFunction();
				setState(1150);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
				case 1:
					{
					setState(1149);
					soqlId();
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

	public static class AggregateFunctionContext extends ParserRuleContext {
		public TerminalNode AVG() { return getToken(ApexParser.AVG, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public TerminalNode COUNT() { return getToken(ApexParser.COUNT, 0); }
		public TerminalNode COUNT_DISTINCT() { return getToken(ApexParser.COUNT_DISTINCT, 0); }
		public TerminalNode MIN() { return getToken(ApexParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(ApexParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(ApexParser.SUM, 0); }
		public TerminalNode TOLABEL() { return getToken(ApexParser.TOLABEL, 0); }
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
		enterRule(_localctx, 190, RULE_aggregateFunction);
		try {
			setState(1192);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,116,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1154);
				match(AVG);
				setState(1155);
				match(LPAREN);
				setState(1156);
				fieldName();
				setState(1157);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1159);
				match(COUNT);
				setState(1160);
				match(LPAREN);
				setState(1161);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1162);
				match(COUNT);
				setState(1163);
				match(LPAREN);
				setState(1164);
				fieldName();
				setState(1165);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1167);
				match(COUNT_DISTINCT);
				setState(1168);
				match(LPAREN);
				setState(1169);
				fieldName();
				setState(1170);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1172);
				match(MIN);
				setState(1173);
				match(LPAREN);
				setState(1174);
				fieldName();
				setState(1175);
				match(RPAREN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1177);
				match(MAX);
				setState(1178);
				match(LPAREN);
				setState(1179);
				fieldName();
				setState(1180);
				match(RPAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1182);
				match(SUM);
				setState(1183);
				match(LPAREN);
				setState(1184);
				fieldName();
				setState(1185);
				match(RPAREN);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1187);
				match(TOLABEL);
				setState(1188);
				match(LPAREN);
				setState(1189);
				fieldName();
				setState(1190);
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
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
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
		enterRule(_localctx, 192, RULE_typeOf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1194);
			match(TYPEOF);
			setState(1195);
			fieldName();
			setState(1197); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(1196);
				whenClause();
				}
				}
				setState(1199); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WHEN );
			setState(1202);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(1201);
				elseClause();
				}
			}

			setState(1204);
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
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
		public TerminalNode THEN() { return getToken(ApexParser.THEN, 0); }
		public FieldNameListContext fieldNameList() {
			return getRuleContext(FieldNameListContext.class,0);
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
		enterRule(_localctx, 194, RULE_whenClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1206);
			match(WHEN);
			setState(1207);
			fieldName();
			setState(1208);
			match(THEN);
			setState(1209);
			fieldNameList();
			}
		}
		catch (RecognitionException re) {
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
		public FieldNameListContext fieldNameList() {
			return getRuleContext(FieldNameListContext.class,0);
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
		enterRule(_localctx, 196, RULE_elseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1211);
			match(ELSE);
			setState(1212);
			fieldNameList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldNameListContext extends ParserRuleContext {
		public List<FieldNameContext> fieldName() {
			return getRuleContexts(FieldNameContext.class);
		}
		public FieldNameContext fieldName(int i) {
			return getRuleContext(FieldNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public FieldNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldNameList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldNameListContext fieldNameList() throws RecognitionException {
		FieldNameListContext _localctx = new FieldNameListContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_fieldNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1214);
			fieldName();
			setState(1219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1215);
				match(COMMA);
				setState(1216);
				fieldName();
				}
				}
				setState(1221);
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
		enterRule(_localctx, 200, RULE_usingScope);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1222);
			match(USING);
			setState(1223);
			match(SCOPE);
			setState(1224);
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

	public static class WhereClauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(ApexParser.WHERE, 0); }
		public LogicalExpressionContext logicalExpression() {
			return getRuleContext(LogicalExpressionContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1226);
			match(WHERE);
			setState(1227);
			logicalExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalExpressionContext extends ParserRuleContext {
		public List<ConditionalExpressionContext> conditionalExpression() {
			return getRuleContexts(ConditionalExpressionContext.class);
		}
		public ConditionalExpressionContext conditionalExpression(int i) {
			return getRuleContext(ConditionalExpressionContext.class,i);
		}
		public List<TerminalNode> SOQLAND() { return getTokens(ApexParser.SOQLAND); }
		public TerminalNode SOQLAND(int i) {
			return getToken(ApexParser.SOQLAND, i);
		}
		public List<TerminalNode> SOQLOR() { return getTokens(ApexParser.SOQLOR); }
		public TerminalNode SOQLOR(int i) {
			return getToken(ApexParser.SOQLOR, i);
		}
		public TerminalNode NOT() { return getToken(ApexParser.NOT, 0); }
		public LogicalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLogicalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLogicalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLogicalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalExpressionContext logicalExpression() throws RecognitionException {
		LogicalExpressionContext _localctx = new LogicalExpressionContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_logicalExpression);
		int _la;
		try {
			setState(1247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1229);
				conditionalExpression();
				setState(1234);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SOQLAND) {
					{
					{
					setState(1230);
					match(SOQLAND);
					setState(1231);
					conditionalExpression();
					}
					}
					setState(1236);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1237);
				conditionalExpression();
				setState(1242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SOQLOR) {
					{
					{
					setState(1238);
					match(SOQLOR);
					setState(1239);
					conditionalExpression();
					}
					}
					setState(1244);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1245);
				match(NOT);
				setState(1246);
				conditionalExpression();
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

	public static class ConditionalExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public LogicalExpressionContext logicalExpression() {
			return getRuleContext(LogicalExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public FieldExpressionContext fieldExpression() {
			return getRuleContext(FieldExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_conditionalExpression);
		try {
			setState(1254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1249);
				match(LPAREN);
				setState(1250);
				logicalExpression();
				setState(1251);
				match(RPAREN);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(1253);
				fieldExpression();
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

	public static class FieldExpressionContext extends ParserRuleContext {
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public AggregateFunctionContext aggregateFunction() {
			return getRuleContext(AggregateFunctionContext.class,0);
		}
		public FieldExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldExpressionContext fieldExpression() throws RecognitionException {
		FieldExpressionContext _localctx = new FieldExpressionContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_fieldExpression);
		try {
			setState(1264);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,124,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1256);
				fieldName();
				setState(1257);
				comparisonOperator();
				setState(1258);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1260);
				aggregateFunction();
				setState(1261);
				comparisonOperator();
				setState(1262);
				value();
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

	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(ApexParser.ASSIGN, 0); }
		public TerminalNode NOTEQUAL() { return getToken(ApexParser.NOTEQUAL, 0); }
		public TerminalNode LT() { return getToken(ApexParser.LT, 0); }
		public TerminalNode GT() { return getToken(ApexParser.GT, 0); }
		public TerminalNode LESSANDGREATER() { return getToken(ApexParser.LESSANDGREATER, 0); }
		public TerminalNode LIKE() { return getToken(ApexParser.LIKE, 0); }
		public TerminalNode IN() { return getToken(ApexParser.IN, 0); }
		public TerminalNode NOT() { return getToken(ApexParser.NOT, 0); }
		public TerminalNode INCLUDES() { return getToken(ApexParser.INCLUDES, 0); }
		public TerminalNode EXCLUDES() { return getToken(ApexParser.EXCLUDES, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_comparisonOperator);
		try {
			setState(1281);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,125,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1266);
				match(ASSIGN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1267);
				match(NOTEQUAL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1268);
				match(LT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1269);
				match(GT);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1270);
				match(LT);
				setState(1271);
				match(ASSIGN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1272);
				match(GT);
				setState(1273);
				match(ASSIGN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1274);
				match(LESSANDGREATER);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1275);
				match(LIKE);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1276);
				match(IN);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1277);
				match(NOT);
				setState(1278);
				match(IN);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1279);
				match(INCLUDES);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1280);
				match(EXCLUDES);
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(ApexParser.NULL, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ApexParser.BooleanLiteral, 0); }
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public TerminalNode LongLiteral() { return getToken(ApexParser.LongLiteral, 0); }
		public TerminalNode NumberLiteral() { return getToken(ApexParser.NumberLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(ApexParser.StringLiteral, 0); }
		public TerminalNode DateLiteral() { return getToken(ApexParser.DateLiteral, 0); }
		public TerminalNode DateTimeLiteral() { return getToken(ApexParser.DateTimeLiteral, 0); }
		public DateFormulaContext dateFormula() {
			return getRuleContext(DateFormulaContext.class,0);
		}
		public CurrencyValueContext currencyValue() {
			return getRuleContext(CurrencyValueContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public SubQueryContext subQuery() {
			return getRuleContext(SubQueryContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public BoundExpressionContext boundExpression() {
			return getRuleContext(BoundExpressionContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_value);
		try {
			setState(1299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,126,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1283);
				match(NULL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1284);
				match(BooleanLiteral);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1285);
				match(IntegerLiteral);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1286);
				match(LongLiteral);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1287);
				match(NumberLiteral);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1288);
				match(StringLiteral);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1289);
				match(DateLiteral);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1290);
				match(DateTimeLiteral);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1291);
				dateFormula();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1292);
				currencyValue();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1293);
				match(LPAREN);
				setState(1294);
				subQuery();
				setState(1295);
				match(RPAREN);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1297);
				valueList();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1298);
				boundExpression();
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

	public static class ValueListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1301);
			match(LPAREN);
			setState(1302);
			value();
			setState(1307);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1303);
				match(COMMA);
				setState(1304);
				value();
				}
				}
				setState(1309);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1310);
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

	public static class CurrencyValueContext extends ParserRuleContext {
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public SignedIntegerContext signedInteger() {
			return getRuleContext(SignedIntegerContext.class,0);
		}
		public CurrencyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_currencyValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterCurrencyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitCurrencyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitCurrencyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CurrencyValueContext currencyValue() throws RecognitionException {
		CurrencyValueContext _localctx = new CurrencyValueContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_currencyValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1312);
			soqlId();
			setState(1313);
			signedInteger();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithClauseContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(ApexParser.WITH, 0); }
		public TerminalNode DATA() { return getToken(ApexParser.DATA, 0); }
		public TerminalNode CATEGORY() { return getToken(ApexParser.CATEGORY, 0); }
		public FilteringExpressionContext filteringExpression() {
			return getRuleContext(FilteringExpressionContext.class,0);
		}
		public TerminalNode SECURITY_ENFORCED() { return getToken(ApexParser.SECURITY_ENFORCED, 0); }
		public LogicalExpressionContext logicalExpression() {
			return getRuleContext(LogicalExpressionContext.class,0);
		}
		public WithClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterWithClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitWithClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitWithClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithClauseContext withClause() throws RecognitionException {
		WithClauseContext _localctx = new WithClauseContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_withClause);
		try {
			setState(1323);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1315);
				match(WITH);
				setState(1316);
				match(DATA);
				setState(1317);
				match(CATEGORY);
				setState(1318);
				filteringExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1319);
				match(WITH);
				setState(1320);
				match(SECURITY_ENFORCED);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1321);
				match(WITH);
				setState(1322);
				logicalExpression();
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

	public static class FilteringExpressionContext extends ParserRuleContext {
		public List<DataCategorySelectionContext> dataCategorySelection() {
			return getRuleContexts(DataCategorySelectionContext.class);
		}
		public DataCategorySelectionContext dataCategorySelection(int i) {
			return getRuleContext(DataCategorySelectionContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(ApexParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(ApexParser.AND, i);
		}
		public FilteringExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filteringExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFilteringExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFilteringExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFilteringExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilteringExpressionContext filteringExpression() throws RecognitionException {
		FilteringExpressionContext _localctx = new FilteringExpressionContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_filteringExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1325);
			dataCategorySelection();
			setState(1330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(1326);
				match(AND);
				setState(1327);
				dataCategorySelection();
				}
				}
				setState(1332);
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

	public static class DataCategorySelectionContext extends ParserRuleContext {
		public SoqlIdContext soqlId() {
			return getRuleContext(SoqlIdContext.class,0);
		}
		public FilteringSelectorContext filteringSelector() {
			return getRuleContext(FilteringSelectorContext.class,0);
		}
		public DataCategoryNameContext dataCategoryName() {
			return getRuleContext(DataCategoryNameContext.class,0);
		}
		public DataCategorySelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataCategorySelection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDataCategorySelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDataCategorySelection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDataCategorySelection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataCategorySelectionContext dataCategorySelection() throws RecognitionException {
		DataCategorySelectionContext _localctx = new DataCategorySelectionContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_dataCategorySelection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1333);
			soqlId();
			setState(1334);
			filteringSelector();
			setState(1335);
			dataCategoryName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataCategoryNameContext extends ParserRuleContext {
		public List<SoqlIdContext> soqlId() {
			return getRuleContexts(SoqlIdContext.class);
		}
		public SoqlIdContext soqlId(int i) {
			return getRuleContext(SoqlIdContext.class,i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(ApexParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(ApexParser.LPAREN, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public DataCategoryNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataCategoryName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDataCategoryName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDataCategoryName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDataCategoryName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataCategoryNameContext dataCategoryName() throws RecognitionException {
		DataCategoryNameContext _localctx = new DataCategoryNameContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_dataCategoryName);
		int _la;
		try {
			setState(1349);
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
			case SELECT:
			case COUNT:
			case FROM:
			case AS:
			case USING:
			case SCOPE:
			case WHERE:
			case ORDER:
			case BY:
			case LIMIT:
			case SOQLAND:
			case SOQLOR:
			case NOT:
			case AVG:
			case COUNT_DISTINCT:
			case MIN:
			case MAX:
			case SUM:
			case TYPEOF:
			case END:
			case THEN:
			case LIKE:
			case IN:
			case INCLUDES:
			case EXCLUDES:
			case ASC:
			case DESC:
			case NULLS:
			case FIRST:
			case LAST:
			case GROUP:
			case ALL:
			case ROWS:
			case VIEW:
			case HAVING:
			case ROLLUP:
			case TOLABEL:
			case OFFSET:
			case DATA:
			case CATEGORY:
			case AT:
			case ABOVE:
			case BELOW:
			case ABOVE_OR_BELOW:
			case SECURITY_ENFORCED:
			case REFERENCE:
			case CUBE:
			case YESTERDAY:
			case TODAY:
			case TOMORROW:
			case LAST_WEEK:
			case THIS_WEEK:
			case NEXT_WEEK:
			case LAST_MONTH:
			case THIS_MONTH:
			case NEXT_MONTH:
			case LAST_90_DAYS:
			case NEXT_90_DAYS:
			case LAST_N_DAYS_N:
			case NEXT_N_DAYS_N:
			case NEXT_N_WEEKS_N:
			case LAST_N_WEEKS_N:
			case NEXT_N_MONTHS_N:
			case LAST_N_MONTHS_N:
			case THIS_QUARTER:
			case LAST_QUARTER:
			case NEXT_QUARTER:
			case NEXT_N_QUARTERS_N:
			case LAST_N_QUARTERS_N:
			case THIS_YEAR:
			case LAST_YEAR:
			case NEXT_YEAR:
			case NEXT_N_YEARS_N:
			case LAST_N_YEARS_N:
			case THIS_FISCAL_QUARTER:
			case LAST_FISCAL_QUARTER:
			case NEXT_FISCAL_QUARTER:
			case NEXT_N_FISCAL_QUARTERS_N:
			case LAST_N_FISCAL_QUARTERS_N:
			case THIS_FISCAL_YEAR:
			case LAST_FISCAL_YEAR:
			case NEXT_FISCAL_YEAR:
			case NEXT_N_FISCAL_YEARS_N:
			case LAST_N_FISCAL_YEARS_N:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1337);
				soqlId();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(1338);
				match(LPAREN);
				setState(1339);
				soqlId();
				setState(1344);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1340);
					match(COMMA);
					setState(1341);
					soqlId();
					}
					}
					setState(1346);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1347);
				match(LPAREN);
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

	public static class FilteringSelectorContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(ApexParser.AT, 0); }
		public TerminalNode ABOVE() { return getToken(ApexParser.ABOVE, 0); }
		public TerminalNode BELOW() { return getToken(ApexParser.BELOW, 0); }
		public TerminalNode ABOVE_OR_BELOW() { return getToken(ApexParser.ABOVE_OR_BELOW, 0); }
		public FilteringSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filteringSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFilteringSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFilteringSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFilteringSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilteringSelectorContext filteringSelector() throws RecognitionException {
		FilteringSelectorContext _localctx = new FilteringSelectorContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_filteringSelector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1351);
			_la = _input.LA(1);
			if ( !(((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & ((1L << (AT - 97)) | (1L << (ABOVE - 97)) | (1L << (BELOW - 97)) | (1L << (ABOVE_OR_BELOW - 97)))) != 0)) ) {
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

	public static class GroupByClauseContext extends ParserRuleContext {
		public TerminalNode GROUP() { return getToken(ApexParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(ApexParser.BY, 0); }
		public SelectListContext selectList() {
			return getRuleContext(SelectListContext.class,0);
		}
		public TerminalNode HAVING() { return getToken(ApexParser.HAVING, 0); }
		public LogicalExpressionContext logicalExpression() {
			return getRuleContext(LogicalExpressionContext.class,0);
		}
		public TerminalNode ROLLUP() { return getToken(ApexParser.ROLLUP, 0); }
		public TerminalNode LPAREN() { return getToken(ApexParser.LPAREN, 0); }
		public List<FieldNameContext> fieldName() {
			return getRuleContexts(FieldNameContext.class);
		}
		public FieldNameContext fieldName(int i) {
			return getRuleContext(FieldNameContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(ApexParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public TerminalNode CUBE() { return getToken(ApexParser.CUBE, 0); }
		public GroupByClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupByClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterGroupByClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitGroupByClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitGroupByClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupByClauseContext groupByClause() throws RecognitionException {
		GroupByClauseContext _localctx = new GroupByClauseContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_groupByClause);
		int _la;
		try {
			setState(1388);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,135,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1353);
				match(GROUP);
				setState(1354);
				match(BY);
				setState(1355);
				selectList();
				setState(1358);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==HAVING) {
					{
					setState(1356);
					match(HAVING);
					setState(1357);
					logicalExpression();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1360);
				match(GROUP);
				setState(1361);
				match(BY);
				setState(1362);
				match(ROLLUP);
				setState(1363);
				match(LPAREN);
				setState(1364);
				fieldName();
				setState(1369);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1365);
					match(COMMA);
					setState(1366);
					fieldName();
					}
					}
					setState(1371);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1372);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1374);
				match(GROUP);
				setState(1375);
				match(BY);
				setState(1376);
				match(CUBE);
				setState(1377);
				match(LPAREN);
				setState(1378);
				fieldName();
				setState(1383);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1379);
					match(COMMA);
					setState(1380);
					fieldName();
					}
					}
					setState(1385);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1386);
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

	public static class OrderByClauseContext extends ParserRuleContext {
		public TerminalNode ORDER() { return getToken(ApexParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(ApexParser.BY, 0); }
		public FieldOrderListContext fieldOrderList() {
			return getRuleContext(FieldOrderListContext.class,0);
		}
		public OrderByClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderByClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterOrderByClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitOrderByClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitOrderByClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrderByClauseContext orderByClause() throws RecognitionException {
		OrderByClauseContext _localctx = new OrderByClauseContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_orderByClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1390);
			match(ORDER);
			setState(1391);
			match(BY);
			setState(1392);
			fieldOrderList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldOrderListContext extends ParserRuleContext {
		public List<FieldOrderContext> fieldOrder() {
			return getRuleContexts(FieldOrderContext.class);
		}
		public FieldOrderContext fieldOrder(int i) {
			return getRuleContext(FieldOrderContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public FieldOrderListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldOrderList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldOrderList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldOrderList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldOrderList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldOrderListContext fieldOrderList() throws RecognitionException {
		FieldOrderListContext _localctx = new FieldOrderListContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_fieldOrderList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1394);
			fieldOrder();
			setState(1399);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1395);
				match(COMMA);
				setState(1396);
				fieldOrder();
				}
				}
				setState(1401);
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

	public static class FieldOrderContext extends ParserRuleContext {
		public FieldNameContext fieldName() {
			return getRuleContext(FieldNameContext.class,0);
		}
		public TerminalNode NULLS() { return getToken(ApexParser.NULLS, 0); }
		public TerminalNode ASC() { return getToken(ApexParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(ApexParser.DESC, 0); }
		public TerminalNode FIRST() { return getToken(ApexParser.FIRST, 0); }
		public TerminalNode LAST() { return getToken(ApexParser.LAST, 0); }
		public AggregateFunctionContext aggregateFunction() {
			return getRuleContext(AggregateFunctionContext.class,0);
		}
		public FieldOrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldOrder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterFieldOrder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitFieldOrder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitFieldOrder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldOrderContext fieldOrder() throws RecognitionException {
		FieldOrderContext _localctx = new FieldOrderContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_fieldOrder);
		int _la;
		try {
			setState(1411);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,139,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1402);
				fieldName();
				setState(1404);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASC || _la==DESC) {
					{
					setState(1403);
					_la = _input.LA(1);
					if ( !(_la==ASC || _la==DESC) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(1408);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NULLS) {
					{
					setState(1406);
					match(NULLS);
					setState(1407);
					_la = _input.LA(1);
					if ( !(_la==FIRST || _la==LAST) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1410);
				aggregateFunction();
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

	public static class LimitClauseContext extends ParserRuleContext {
		public TerminalNode LIMIT() { return getToken(ApexParser.LIMIT, 0); }
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public BoundExpressionContext boundExpression() {
			return getRuleContext(BoundExpressionContext.class,0);
		}
		public LimitClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterLimitClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitLimitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitLimitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LimitClauseContext limitClause() throws RecognitionException {
		LimitClauseContext _localctx = new LimitClauseContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_limitClause);
		try {
			setState(1417);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,140,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1413);
				match(LIMIT);
				setState(1414);
				match(IntegerLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1415);
				match(LIMIT);
				setState(1416);
				boundExpression();
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

	public static class OffsetClauseContext extends ParserRuleContext {
		public TerminalNode OFFSET() { return getToken(ApexParser.OFFSET, 0); }
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public BoundExpressionContext boundExpression() {
			return getRuleContext(BoundExpressionContext.class,0);
		}
		public OffsetClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_offsetClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterOffsetClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitOffsetClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitOffsetClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OffsetClauseContext offsetClause() throws RecognitionException {
		OffsetClauseContext _localctx = new OffsetClauseContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_offsetClause);
		try {
			setState(1423);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,141,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1419);
				match(OFFSET);
				setState(1420);
				match(IntegerLiteral);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1421);
				match(OFFSET);
				setState(1422);
				boundExpression();
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

	public static class AllRowsClauseContext extends ParserRuleContext {
		public TerminalNode ALL() { return getToken(ApexParser.ALL, 0); }
		public TerminalNode ROWS() { return getToken(ApexParser.ROWS, 0); }
		public AllRowsClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_allRowsClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterAllRowsClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitAllRowsClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitAllRowsClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AllRowsClauseContext allRowsClause() throws RecognitionException {
		AllRowsClauseContext _localctx = new AllRowsClauseContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_allRowsClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1425);
			match(ALL);
			setState(1426);
			match(ROWS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForClausesContext extends ParserRuleContext {
		public List<TerminalNode> FOR() { return getTokens(ApexParser.FOR); }
		public TerminalNode FOR(int i) {
			return getToken(ApexParser.FOR, i);
		}
		public List<TerminalNode> VIEW() { return getTokens(ApexParser.VIEW); }
		public TerminalNode VIEW(int i) {
			return getToken(ApexParser.VIEW, i);
		}
		public List<TerminalNode> UPDATE() { return getTokens(ApexParser.UPDATE); }
		public TerminalNode UPDATE(int i) {
			return getToken(ApexParser.UPDATE, i);
		}
		public List<TerminalNode> REFERENCE() { return getTokens(ApexParser.REFERENCE); }
		public TerminalNode REFERENCE(int i) {
			return getToken(ApexParser.REFERENCE, i);
		}
		public ForClausesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forClauses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterForClauses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitForClauses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitForClauses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForClausesContext forClauses() throws RecognitionException {
		ForClausesContext _localctx = new ForClausesContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_forClauses);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1432);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FOR) {
				{
				{
				setState(1428);
				match(FOR);
				setState(1429);
				_la = _input.LA(1);
				if ( !(((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (UPDATE - 46)) | (1L << (VIEW - 46)) | (1L << (REFERENCE - 46)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(1434);
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

	public static class BoundExpressionContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BoundExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boundExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterBoundExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitBoundExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitBoundExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoundExpressionContext boundExpression() throws RecognitionException {
		BoundExpressionContext _localctx = new BoundExpressionContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_boundExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1435);
			match(COLON);
			setState(1436);
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

	public static class DateFormulaContext extends ParserRuleContext {
		public TerminalNode YESTERDAY() { return getToken(ApexParser.YESTERDAY, 0); }
		public TerminalNode TODAY() { return getToken(ApexParser.TODAY, 0); }
		public TerminalNode TOMORROW() { return getToken(ApexParser.TOMORROW, 0); }
		public TerminalNode LAST_WEEK() { return getToken(ApexParser.LAST_WEEK, 0); }
		public TerminalNode THIS_WEEK() { return getToken(ApexParser.THIS_WEEK, 0); }
		public TerminalNode NEXT_WEEK() { return getToken(ApexParser.NEXT_WEEK, 0); }
		public TerminalNode LAST_MONTH() { return getToken(ApexParser.LAST_MONTH, 0); }
		public TerminalNode THIS_MONTH() { return getToken(ApexParser.THIS_MONTH, 0); }
		public TerminalNode NEXT_MONTH() { return getToken(ApexParser.NEXT_MONTH, 0); }
		public TerminalNode LAST_90_DAYS() { return getToken(ApexParser.LAST_90_DAYS, 0); }
		public TerminalNode NEXT_90_DAYS() { return getToken(ApexParser.NEXT_90_DAYS, 0); }
		public TerminalNode LAST_N_DAYS_N() { return getToken(ApexParser.LAST_N_DAYS_N, 0); }
		public TerminalNode COLON() { return getToken(ApexParser.COLON, 0); }
		public SignedIntegerContext signedInteger() {
			return getRuleContext(SignedIntegerContext.class,0);
		}
		public TerminalNode NEXT_N_DAYS_N() { return getToken(ApexParser.NEXT_N_DAYS_N, 0); }
		public TerminalNode NEXT_N_WEEKS_N() { return getToken(ApexParser.NEXT_N_WEEKS_N, 0); }
		public TerminalNode LAST_N_WEEKS_N() { return getToken(ApexParser.LAST_N_WEEKS_N, 0); }
		public TerminalNode NEXT_N_MONTHS_N() { return getToken(ApexParser.NEXT_N_MONTHS_N, 0); }
		public TerminalNode LAST_N_MONTHS_N() { return getToken(ApexParser.LAST_N_MONTHS_N, 0); }
		public TerminalNode THIS_QUARTER() { return getToken(ApexParser.THIS_QUARTER, 0); }
		public TerminalNode LAST_QUARTER() { return getToken(ApexParser.LAST_QUARTER, 0); }
		public TerminalNode NEXT_QUARTER() { return getToken(ApexParser.NEXT_QUARTER, 0); }
		public TerminalNode NEXT_N_QUARTERS_N() { return getToken(ApexParser.NEXT_N_QUARTERS_N, 0); }
		public TerminalNode LAST_N_QUARTERS_N() { return getToken(ApexParser.LAST_N_QUARTERS_N, 0); }
		public TerminalNode THIS_YEAR() { return getToken(ApexParser.THIS_YEAR, 0); }
		public TerminalNode LAST_YEAR() { return getToken(ApexParser.LAST_YEAR, 0); }
		public TerminalNode NEXT_YEAR() { return getToken(ApexParser.NEXT_YEAR, 0); }
		public TerminalNode NEXT_N_YEARS_N() { return getToken(ApexParser.NEXT_N_YEARS_N, 0); }
		public TerminalNode LAST_N_YEARS_N() { return getToken(ApexParser.LAST_N_YEARS_N, 0); }
		public TerminalNode THIS_FISCAL_QUARTER() { return getToken(ApexParser.THIS_FISCAL_QUARTER, 0); }
		public TerminalNode LAST_FISCAL_QUARTER() { return getToken(ApexParser.LAST_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_FISCAL_QUARTER() { return getToken(ApexParser.NEXT_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.NEXT_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode LAST_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.LAST_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode THIS_FISCAL_YEAR() { return getToken(ApexParser.THIS_FISCAL_YEAR, 0); }
		public TerminalNode LAST_FISCAL_YEAR() { return getToken(ApexParser.LAST_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_FISCAL_YEAR() { return getToken(ApexParser.NEXT_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_N_FISCAL_YEARS_N() { return getToken(ApexParser.NEXT_N_FISCAL_YEARS_N, 0); }
		public TerminalNode LAST_N_FISCAL_YEARS_N() { return getToken(ApexParser.LAST_N_FISCAL_YEARS_N, 0); }
		public DateFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateFormula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterDateFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitDateFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitDateFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateFormulaContext dateFormula() throws RecognitionException {
		DateFormulaContext _localctx = new DateFormulaContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_dateFormula);
		try {
			setState(1503);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case YESTERDAY:
				enterOuterAlt(_localctx, 1);
				{
				setState(1438);
				match(YESTERDAY);
				}
				break;
			case TODAY:
				enterOuterAlt(_localctx, 2);
				{
				setState(1439);
				match(TODAY);
				}
				break;
			case TOMORROW:
				enterOuterAlt(_localctx, 3);
				{
				setState(1440);
				match(TOMORROW);
				}
				break;
			case LAST_WEEK:
				enterOuterAlt(_localctx, 4);
				{
				setState(1441);
				match(LAST_WEEK);
				}
				break;
			case THIS_WEEK:
				enterOuterAlt(_localctx, 5);
				{
				setState(1442);
				match(THIS_WEEK);
				}
				break;
			case NEXT_WEEK:
				enterOuterAlt(_localctx, 6);
				{
				setState(1443);
				match(NEXT_WEEK);
				}
				break;
			case LAST_MONTH:
				enterOuterAlt(_localctx, 7);
				{
				setState(1444);
				match(LAST_MONTH);
				}
				break;
			case THIS_MONTH:
				enterOuterAlt(_localctx, 8);
				{
				setState(1445);
				match(THIS_MONTH);
				}
				break;
			case NEXT_MONTH:
				enterOuterAlt(_localctx, 9);
				{
				setState(1446);
				match(NEXT_MONTH);
				}
				break;
			case LAST_90_DAYS:
				enterOuterAlt(_localctx, 10);
				{
				setState(1447);
				match(LAST_90_DAYS);
				}
				break;
			case NEXT_90_DAYS:
				enterOuterAlt(_localctx, 11);
				{
				setState(1448);
				match(NEXT_90_DAYS);
				}
				break;
			case LAST_N_DAYS_N:
				enterOuterAlt(_localctx, 12);
				{
				setState(1449);
				match(LAST_N_DAYS_N);
				setState(1450);
				match(COLON);
				setState(1451);
				signedInteger();
				}
				break;
			case NEXT_N_DAYS_N:
				enterOuterAlt(_localctx, 13);
				{
				setState(1452);
				match(NEXT_N_DAYS_N);
				setState(1453);
				match(COLON);
				setState(1454);
				signedInteger();
				}
				break;
			case NEXT_N_WEEKS_N:
				enterOuterAlt(_localctx, 14);
				{
				setState(1455);
				match(NEXT_N_WEEKS_N);
				setState(1456);
				match(COLON);
				setState(1457);
				signedInteger();
				}
				break;
			case LAST_N_WEEKS_N:
				enterOuterAlt(_localctx, 15);
				{
				setState(1458);
				match(LAST_N_WEEKS_N);
				setState(1459);
				match(COLON);
				setState(1460);
				signedInteger();
				}
				break;
			case NEXT_N_MONTHS_N:
				enterOuterAlt(_localctx, 16);
				{
				setState(1461);
				match(NEXT_N_MONTHS_N);
				setState(1462);
				match(COLON);
				setState(1463);
				signedInteger();
				}
				break;
			case LAST_N_MONTHS_N:
				enterOuterAlt(_localctx, 17);
				{
				setState(1464);
				match(LAST_N_MONTHS_N);
				setState(1465);
				match(COLON);
				setState(1466);
				signedInteger();
				}
				break;
			case THIS_QUARTER:
				enterOuterAlt(_localctx, 18);
				{
				setState(1467);
				match(THIS_QUARTER);
				}
				break;
			case LAST_QUARTER:
				enterOuterAlt(_localctx, 19);
				{
				setState(1468);
				match(LAST_QUARTER);
				}
				break;
			case NEXT_QUARTER:
				enterOuterAlt(_localctx, 20);
				{
				setState(1469);
				match(NEXT_QUARTER);
				}
				break;
			case NEXT_N_QUARTERS_N:
				enterOuterAlt(_localctx, 21);
				{
				setState(1470);
				match(NEXT_N_QUARTERS_N);
				setState(1471);
				match(COLON);
				setState(1472);
				signedInteger();
				}
				break;
			case LAST_N_QUARTERS_N:
				enterOuterAlt(_localctx, 22);
				{
				setState(1473);
				match(LAST_N_QUARTERS_N);
				setState(1474);
				match(COLON);
				setState(1475);
				signedInteger();
				}
				break;
			case THIS_YEAR:
				enterOuterAlt(_localctx, 23);
				{
				setState(1476);
				match(THIS_YEAR);
				}
				break;
			case LAST_YEAR:
				enterOuterAlt(_localctx, 24);
				{
				setState(1477);
				match(LAST_YEAR);
				}
				break;
			case NEXT_YEAR:
				enterOuterAlt(_localctx, 25);
				{
				setState(1478);
				match(NEXT_YEAR);
				}
				break;
			case NEXT_N_YEARS_N:
				enterOuterAlt(_localctx, 26);
				{
				setState(1479);
				match(NEXT_N_YEARS_N);
				setState(1480);
				match(COLON);
				setState(1481);
				signedInteger();
				}
				break;
			case LAST_N_YEARS_N:
				enterOuterAlt(_localctx, 27);
				{
				setState(1482);
				match(LAST_N_YEARS_N);
				setState(1483);
				match(COLON);
				setState(1484);
				signedInteger();
				}
				break;
			case THIS_FISCAL_QUARTER:
				enterOuterAlt(_localctx, 28);
				{
				setState(1485);
				match(THIS_FISCAL_QUARTER);
				}
				break;
			case LAST_FISCAL_QUARTER:
				enterOuterAlt(_localctx, 29);
				{
				setState(1486);
				match(LAST_FISCAL_QUARTER);
				}
				break;
			case NEXT_FISCAL_QUARTER:
				enterOuterAlt(_localctx, 30);
				{
				setState(1487);
				match(NEXT_FISCAL_QUARTER);
				}
				break;
			case NEXT_N_FISCAL_QUARTERS_N:
				enterOuterAlt(_localctx, 31);
				{
				setState(1488);
				match(NEXT_N_FISCAL_QUARTERS_N);
				setState(1489);
				match(COLON);
				setState(1490);
				signedInteger();
				}
				break;
			case LAST_N_FISCAL_QUARTERS_N:
				enterOuterAlt(_localctx, 32);
				{
				setState(1491);
				match(LAST_N_FISCAL_QUARTERS_N);
				setState(1492);
				match(COLON);
				setState(1493);
				signedInteger();
				}
				break;
			case THIS_FISCAL_YEAR:
				enterOuterAlt(_localctx, 33);
				{
				setState(1494);
				match(THIS_FISCAL_YEAR);
				}
				break;
			case LAST_FISCAL_YEAR:
				enterOuterAlt(_localctx, 34);
				{
				setState(1495);
				match(LAST_FISCAL_YEAR);
				}
				break;
			case NEXT_FISCAL_YEAR:
				enterOuterAlt(_localctx, 35);
				{
				setState(1496);
				match(NEXT_FISCAL_YEAR);
				}
				break;
			case NEXT_N_FISCAL_YEARS_N:
				enterOuterAlt(_localctx, 36);
				{
				setState(1497);
				match(NEXT_N_FISCAL_YEARS_N);
				setState(1498);
				match(COLON);
				setState(1499);
				signedInteger();
				}
				break;
			case LAST_N_FISCAL_YEARS_N:
				enterOuterAlt(_localctx, 37);
				{
				setState(1500);
				match(LAST_N_FISCAL_YEARS_N);
				setState(1501);
				match(COLON);
				setState(1502);
				signedInteger();
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

	public static class SignedIntegerContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(ApexParser.IntegerLiteral, 0); }
		public TerminalNode ADD() { return getToken(ApexParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(ApexParser.SUB, 0); }
		public SignedIntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signedInteger; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).enterSignedInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApexParserListener ) ((ApexParserListener)listener).exitSignedInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApexParserVisitor ) return ((ApexParserVisitor<? extends T>)visitor).visitSignedInteger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignedIntegerContext signedInteger() throws RecognitionException {
		SignedIntegerContext _localctx = new SignedIntegerContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_signedInteger);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1506);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ADD || _la==SUB) {
				{
				setState(1505);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1508);
			match(IntegerLiteral);
			}
		}
		catch (RecognitionException re) {
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
		enterRule(_localctx, 250, RULE_soqlId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1510);
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
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public TerminalNode COUNT() { return getToken(ApexParser.COUNT, 0); }
		public TerminalNode FROM() { return getToken(ApexParser.FROM, 0); }
		public TerminalNode AS() { return getToken(ApexParser.AS, 0); }
		public TerminalNode USING() { return getToken(ApexParser.USING, 0); }
		public TerminalNode SCOPE() { return getToken(ApexParser.SCOPE, 0); }
		public TerminalNode WHERE() { return getToken(ApexParser.WHERE, 0); }
		public TerminalNode ORDER() { return getToken(ApexParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(ApexParser.BY, 0); }
		public TerminalNode LIMIT() { return getToken(ApexParser.LIMIT, 0); }
		public TerminalNode SOQLAND() { return getToken(ApexParser.SOQLAND, 0); }
		public TerminalNode SOQLOR() { return getToken(ApexParser.SOQLOR, 0); }
		public TerminalNode NOT() { return getToken(ApexParser.NOT, 0); }
		public TerminalNode AVG() { return getToken(ApexParser.AVG, 0); }
		public TerminalNode COUNT_DISTINCT() { return getToken(ApexParser.COUNT_DISTINCT, 0); }
		public TerminalNode MIN() { return getToken(ApexParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(ApexParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(ApexParser.SUM, 0); }
		public TerminalNode TYPEOF() { return getToken(ApexParser.TYPEOF, 0); }
		public TerminalNode END() { return getToken(ApexParser.END, 0); }
		public TerminalNode THEN() { return getToken(ApexParser.THEN, 0); }
		public TerminalNode LIKE() { return getToken(ApexParser.LIKE, 0); }
		public TerminalNode IN() { return getToken(ApexParser.IN, 0); }
		public TerminalNode INCLUDES() { return getToken(ApexParser.INCLUDES, 0); }
		public TerminalNode EXCLUDES() { return getToken(ApexParser.EXCLUDES, 0); }
		public TerminalNode ASC() { return getToken(ApexParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(ApexParser.DESC, 0); }
		public TerminalNode NULLS() { return getToken(ApexParser.NULLS, 0); }
		public TerminalNode FIRST() { return getToken(ApexParser.FIRST, 0); }
		public TerminalNode LAST() { return getToken(ApexParser.LAST, 0); }
		public TerminalNode GROUP() { return getToken(ApexParser.GROUP, 0); }
		public TerminalNode ALL() { return getToken(ApexParser.ALL, 0); }
		public TerminalNode ROWS() { return getToken(ApexParser.ROWS, 0); }
		public TerminalNode VIEW() { return getToken(ApexParser.VIEW, 0); }
		public TerminalNode HAVING() { return getToken(ApexParser.HAVING, 0); }
		public TerminalNode ROLLUP() { return getToken(ApexParser.ROLLUP, 0); }
		public TerminalNode TOLABEL() { return getToken(ApexParser.TOLABEL, 0); }
		public TerminalNode OFFSET() { return getToken(ApexParser.OFFSET, 0); }
		public TerminalNode DATA() { return getToken(ApexParser.DATA, 0); }
		public TerminalNode CATEGORY() { return getToken(ApexParser.CATEGORY, 0); }
		public TerminalNode AT() { return getToken(ApexParser.AT, 0); }
		public TerminalNode ABOVE() { return getToken(ApexParser.ABOVE, 0); }
		public TerminalNode BELOW() { return getToken(ApexParser.BELOW, 0); }
		public TerminalNode ABOVE_OR_BELOW() { return getToken(ApexParser.ABOVE_OR_BELOW, 0); }
		public TerminalNode SECURITY_ENFORCED() { return getToken(ApexParser.SECURITY_ENFORCED, 0); }
		public TerminalNode REFERENCE() { return getToken(ApexParser.REFERENCE, 0); }
		public TerminalNode CUBE() { return getToken(ApexParser.CUBE, 0); }
		public TerminalNode YESTERDAY() { return getToken(ApexParser.YESTERDAY, 0); }
		public TerminalNode TODAY() { return getToken(ApexParser.TODAY, 0); }
		public TerminalNode TOMORROW() { return getToken(ApexParser.TOMORROW, 0); }
		public TerminalNode LAST_WEEK() { return getToken(ApexParser.LAST_WEEK, 0); }
		public TerminalNode THIS_WEEK() { return getToken(ApexParser.THIS_WEEK, 0); }
		public TerminalNode NEXT_WEEK() { return getToken(ApexParser.NEXT_WEEK, 0); }
		public TerminalNode LAST_MONTH() { return getToken(ApexParser.LAST_MONTH, 0); }
		public TerminalNode THIS_MONTH() { return getToken(ApexParser.THIS_MONTH, 0); }
		public TerminalNode NEXT_MONTH() { return getToken(ApexParser.NEXT_MONTH, 0); }
		public TerminalNode LAST_90_DAYS() { return getToken(ApexParser.LAST_90_DAYS, 0); }
		public TerminalNode NEXT_90_DAYS() { return getToken(ApexParser.NEXT_90_DAYS, 0); }
		public TerminalNode LAST_N_DAYS_N() { return getToken(ApexParser.LAST_N_DAYS_N, 0); }
		public TerminalNode NEXT_N_DAYS_N() { return getToken(ApexParser.NEXT_N_DAYS_N, 0); }
		public TerminalNode NEXT_N_WEEKS_N() { return getToken(ApexParser.NEXT_N_WEEKS_N, 0); }
		public TerminalNode LAST_N_WEEKS_N() { return getToken(ApexParser.LAST_N_WEEKS_N, 0); }
		public TerminalNode NEXT_N_MONTHS_N() { return getToken(ApexParser.NEXT_N_MONTHS_N, 0); }
		public TerminalNode LAST_N_MONTHS_N() { return getToken(ApexParser.LAST_N_MONTHS_N, 0); }
		public TerminalNode THIS_QUARTER() { return getToken(ApexParser.THIS_QUARTER, 0); }
		public TerminalNode LAST_QUARTER() { return getToken(ApexParser.LAST_QUARTER, 0); }
		public TerminalNode NEXT_QUARTER() { return getToken(ApexParser.NEXT_QUARTER, 0); }
		public TerminalNode NEXT_N_QUARTERS_N() { return getToken(ApexParser.NEXT_N_QUARTERS_N, 0); }
		public TerminalNode LAST_N_QUARTERS_N() { return getToken(ApexParser.LAST_N_QUARTERS_N, 0); }
		public TerminalNode THIS_YEAR() { return getToken(ApexParser.THIS_YEAR, 0); }
		public TerminalNode LAST_YEAR() { return getToken(ApexParser.LAST_YEAR, 0); }
		public TerminalNode NEXT_YEAR() { return getToken(ApexParser.NEXT_YEAR, 0); }
		public TerminalNode NEXT_N_YEARS_N() { return getToken(ApexParser.NEXT_N_YEARS_N, 0); }
		public TerminalNode LAST_N_YEARS_N() { return getToken(ApexParser.LAST_N_YEARS_N, 0); }
		public TerminalNode THIS_FISCAL_QUARTER() { return getToken(ApexParser.THIS_FISCAL_QUARTER, 0); }
		public TerminalNode LAST_FISCAL_QUARTER() { return getToken(ApexParser.LAST_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_FISCAL_QUARTER() { return getToken(ApexParser.NEXT_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.NEXT_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode LAST_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.LAST_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode THIS_FISCAL_YEAR() { return getToken(ApexParser.THIS_FISCAL_YEAR, 0); }
		public TerminalNode LAST_FISCAL_YEAR() { return getToken(ApexParser.LAST_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_FISCAL_YEAR() { return getToken(ApexParser.NEXT_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_N_FISCAL_YEARS_N() { return getToken(ApexParser.NEXT_N_FISCAL_YEARS_N, 0); }
		public TerminalNode LAST_N_FISCAL_YEARS_N() { return getToken(ApexParser.LAST_N_FISCAL_YEARS_N, 0); }
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
		enterRule(_localctx, 252, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1512);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AFTER) | (1L << BEFORE) | (1L << GET) | (1L << INHERITED) | (1L << INSTANCEOF) | (1L << SET) | (1L << SHARING) | (1L << SWITCH) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << WHEN) | (1L << WITH) | (1L << WITHOUT) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==Identifier) ) {
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
		public TerminalNode SELECT() { return getToken(ApexParser.SELECT, 0); }
		public TerminalNode COUNT() { return getToken(ApexParser.COUNT, 0); }
		public TerminalNode FROM() { return getToken(ApexParser.FROM, 0); }
		public TerminalNode AS() { return getToken(ApexParser.AS, 0); }
		public TerminalNode USING() { return getToken(ApexParser.USING, 0); }
		public TerminalNode SCOPE() { return getToken(ApexParser.SCOPE, 0); }
		public TerminalNode WHERE() { return getToken(ApexParser.WHERE, 0); }
		public TerminalNode ORDER() { return getToken(ApexParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(ApexParser.BY, 0); }
		public TerminalNode LIMIT() { return getToken(ApexParser.LIMIT, 0); }
		public TerminalNode SOQLAND() { return getToken(ApexParser.SOQLAND, 0); }
		public TerminalNode SOQLOR() { return getToken(ApexParser.SOQLOR, 0); }
		public TerminalNode NOT() { return getToken(ApexParser.NOT, 0); }
		public TerminalNode AVG() { return getToken(ApexParser.AVG, 0); }
		public TerminalNode COUNT_DISTINCT() { return getToken(ApexParser.COUNT_DISTINCT, 0); }
		public TerminalNode MIN() { return getToken(ApexParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(ApexParser.MAX, 0); }
		public TerminalNode SUM() { return getToken(ApexParser.SUM, 0); }
		public TerminalNode TYPEOF() { return getToken(ApexParser.TYPEOF, 0); }
		public TerminalNode END() { return getToken(ApexParser.END, 0); }
		public TerminalNode THEN() { return getToken(ApexParser.THEN, 0); }
		public TerminalNode LIKE() { return getToken(ApexParser.LIKE, 0); }
		public TerminalNode IN() { return getToken(ApexParser.IN, 0); }
		public TerminalNode INCLUDES() { return getToken(ApexParser.INCLUDES, 0); }
		public TerminalNode EXCLUDES() { return getToken(ApexParser.EXCLUDES, 0); }
		public TerminalNode ASC() { return getToken(ApexParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(ApexParser.DESC, 0); }
		public TerminalNode NULLS() { return getToken(ApexParser.NULLS, 0); }
		public TerminalNode FIRST() { return getToken(ApexParser.FIRST, 0); }
		public TerminalNode LAST() { return getToken(ApexParser.LAST, 0); }
		public TerminalNode GROUP() { return getToken(ApexParser.GROUP, 0); }
		public TerminalNode ALL() { return getToken(ApexParser.ALL, 0); }
		public TerminalNode ROWS() { return getToken(ApexParser.ROWS, 0); }
		public TerminalNode VIEW() { return getToken(ApexParser.VIEW, 0); }
		public TerminalNode HAVING() { return getToken(ApexParser.HAVING, 0); }
		public TerminalNode ROLLUP() { return getToken(ApexParser.ROLLUP, 0); }
		public TerminalNode TOLABEL() { return getToken(ApexParser.TOLABEL, 0); }
		public TerminalNode OFFSET() { return getToken(ApexParser.OFFSET, 0); }
		public TerminalNode DATA() { return getToken(ApexParser.DATA, 0); }
		public TerminalNode CATEGORY() { return getToken(ApexParser.CATEGORY, 0); }
		public TerminalNode AT() { return getToken(ApexParser.AT, 0); }
		public TerminalNode ABOVE() { return getToken(ApexParser.ABOVE, 0); }
		public TerminalNode BELOW() { return getToken(ApexParser.BELOW, 0); }
		public TerminalNode ABOVE_OR_BELOW() { return getToken(ApexParser.ABOVE_OR_BELOW, 0); }
		public TerminalNode SECURITY_ENFORCED() { return getToken(ApexParser.SECURITY_ENFORCED, 0); }
		public TerminalNode REFERENCE() { return getToken(ApexParser.REFERENCE, 0); }
		public TerminalNode CUBE() { return getToken(ApexParser.CUBE, 0); }
		public TerminalNode YESTERDAY() { return getToken(ApexParser.YESTERDAY, 0); }
		public TerminalNode TODAY() { return getToken(ApexParser.TODAY, 0); }
		public TerminalNode TOMORROW() { return getToken(ApexParser.TOMORROW, 0); }
		public TerminalNode LAST_WEEK() { return getToken(ApexParser.LAST_WEEK, 0); }
		public TerminalNode THIS_WEEK() { return getToken(ApexParser.THIS_WEEK, 0); }
		public TerminalNode NEXT_WEEK() { return getToken(ApexParser.NEXT_WEEK, 0); }
		public TerminalNode LAST_MONTH() { return getToken(ApexParser.LAST_MONTH, 0); }
		public TerminalNode THIS_MONTH() { return getToken(ApexParser.THIS_MONTH, 0); }
		public TerminalNode NEXT_MONTH() { return getToken(ApexParser.NEXT_MONTH, 0); }
		public TerminalNode LAST_90_DAYS() { return getToken(ApexParser.LAST_90_DAYS, 0); }
		public TerminalNode NEXT_90_DAYS() { return getToken(ApexParser.NEXT_90_DAYS, 0); }
		public TerminalNode LAST_N_DAYS_N() { return getToken(ApexParser.LAST_N_DAYS_N, 0); }
		public TerminalNode NEXT_N_DAYS_N() { return getToken(ApexParser.NEXT_N_DAYS_N, 0); }
		public TerminalNode NEXT_N_WEEKS_N() { return getToken(ApexParser.NEXT_N_WEEKS_N, 0); }
		public TerminalNode LAST_N_WEEKS_N() { return getToken(ApexParser.LAST_N_WEEKS_N, 0); }
		public TerminalNode NEXT_N_MONTHS_N() { return getToken(ApexParser.NEXT_N_MONTHS_N, 0); }
		public TerminalNode LAST_N_MONTHS_N() { return getToken(ApexParser.LAST_N_MONTHS_N, 0); }
		public TerminalNode THIS_QUARTER() { return getToken(ApexParser.THIS_QUARTER, 0); }
		public TerminalNode LAST_QUARTER() { return getToken(ApexParser.LAST_QUARTER, 0); }
		public TerminalNode NEXT_QUARTER() { return getToken(ApexParser.NEXT_QUARTER, 0); }
		public TerminalNode NEXT_N_QUARTERS_N() { return getToken(ApexParser.NEXT_N_QUARTERS_N, 0); }
		public TerminalNode LAST_N_QUARTERS_N() { return getToken(ApexParser.LAST_N_QUARTERS_N, 0); }
		public TerminalNode THIS_YEAR() { return getToken(ApexParser.THIS_YEAR, 0); }
		public TerminalNode LAST_YEAR() { return getToken(ApexParser.LAST_YEAR, 0); }
		public TerminalNode NEXT_YEAR() { return getToken(ApexParser.NEXT_YEAR, 0); }
		public TerminalNode NEXT_N_YEARS_N() { return getToken(ApexParser.NEXT_N_YEARS_N, 0); }
		public TerminalNode LAST_N_YEARS_N() { return getToken(ApexParser.LAST_N_YEARS_N, 0); }
		public TerminalNode THIS_FISCAL_QUARTER() { return getToken(ApexParser.THIS_FISCAL_QUARTER, 0); }
		public TerminalNode LAST_FISCAL_QUARTER() { return getToken(ApexParser.LAST_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_FISCAL_QUARTER() { return getToken(ApexParser.NEXT_FISCAL_QUARTER, 0); }
		public TerminalNode NEXT_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.NEXT_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode LAST_N_FISCAL_QUARTERS_N() { return getToken(ApexParser.LAST_N_FISCAL_QUARTERS_N, 0); }
		public TerminalNode THIS_FISCAL_YEAR() { return getToken(ApexParser.THIS_FISCAL_YEAR, 0); }
		public TerminalNode LAST_FISCAL_YEAR() { return getToken(ApexParser.LAST_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_FISCAL_YEAR() { return getToken(ApexParser.NEXT_FISCAL_YEAR, 0); }
		public TerminalNode NEXT_N_FISCAL_YEARS_N() { return getToken(ApexParser.NEXT_N_FISCAL_YEARS_N, 0); }
		public TerminalNode LAST_N_FISCAL_YEARS_N() { return getToken(ApexParser.LAST_N_FISCAL_YEARS_N, 0); }
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
		enterRule(_localctx, 254, RULE_anyId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1514);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << AFTER) | (1L << BEFORE) | (1L << BREAK) | (1L << CATCH) | (1L << CLASS) | (1L << CONTINUE) | (1L << DELETE) | (1L << DO) | (1L << ELSE) | (1L << ENUM) | (1L << EXTENDS) | (1L << FINAL) | (1L << FINALLY) | (1L << FOR) | (1L << GET) | (1L << GLOBAL) | (1L << IF) | (1L << IMPLEMENTS) | (1L << INHERITED) | (1L << INSERT) | (1L << INSTANCEOF) | (1L << INTERFACE) | (1L << MERGE) | (1L << NEW) | (1L << NULL) | (1L << ON) | (1L << OVERRIDE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << RETURN) | (1L << SET) | (1L << SHARING) | (1L << STATIC) | (1L << SUPER) | (1L << SWITCH) | (1L << TESTMETHOD) | (1L << THIS) | (1L << THROW) | (1L << TRANSIENT) | (1L << TRIGGER) | (1L << TRY) | (1L << UNDELETE) | (1L << UPDATE) | (1L << UPSERT) | (1L << VIRTUAL) | (1L << WEBSERVICE) | (1L << WHEN) | (1L << WHILE) | (1L << WITH) | (1L << WITHOUT) | (1L << LIST) | (1L << MAP) | (1L << SELECT) | (1L << COUNT) | (1L << FROM) | (1L << AS) | (1L << USING) | (1L << SCOPE) | (1L << WHERE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ORDER - 64)) | (1L << (BY - 64)) | (1L << (LIMIT - 64)) | (1L << (SOQLAND - 64)) | (1L << (SOQLOR - 64)) | (1L << (NOT - 64)) | (1L << (AVG - 64)) | (1L << (COUNT_DISTINCT - 64)) | (1L << (MIN - 64)) | (1L << (MAX - 64)) | (1L << (SUM - 64)) | (1L << (TYPEOF - 64)) | (1L << (END - 64)) | (1L << (THEN - 64)) | (1L << (LIKE - 64)) | (1L << (IN - 64)) | (1L << (INCLUDES - 64)) | (1L << (EXCLUDES - 64)) | (1L << (ASC - 64)) | (1L << (DESC - 64)) | (1L << (NULLS - 64)) | (1L << (FIRST - 64)) | (1L << (LAST - 64)) | (1L << (GROUP - 64)) | (1L << (ALL - 64)) | (1L << (ROWS - 64)) | (1L << (VIEW - 64)) | (1L << (HAVING - 64)) | (1L << (ROLLUP - 64)) | (1L << (TOLABEL - 64)) | (1L << (OFFSET - 64)) | (1L << (DATA - 64)) | (1L << (CATEGORY - 64)) | (1L << (AT - 64)) | (1L << (ABOVE - 64)) | (1L << (BELOW - 64)) | (1L << (ABOVE_OR_BELOW - 64)) | (1L << (SECURITY_ENFORCED - 64)) | (1L << (REFERENCE - 64)) | (1L << (CUBE - 64)) | (1L << (YESTERDAY - 64)) | (1L << (TODAY - 64)) | (1L << (TOMORROW - 64)) | (1L << (LAST_WEEK - 64)) | (1L << (THIS_WEEK - 64)) | (1L << (NEXT_WEEK - 64)) | (1L << (LAST_MONTH - 64)) | (1L << (THIS_MONTH - 64)) | (1L << (NEXT_MONTH - 64)) | (1L << (LAST_90_DAYS - 64)) | (1L << (NEXT_90_DAYS - 64)) | (1L << (LAST_N_DAYS_N - 64)) | (1L << (NEXT_N_DAYS_N - 64)) | (1L << (NEXT_N_WEEKS_N - 64)) | (1L << (LAST_N_WEEKS_N - 64)) | (1L << (NEXT_N_MONTHS_N - 64)) | (1L << (LAST_N_MONTHS_N - 64)) | (1L << (THIS_QUARTER - 64)) | (1L << (LAST_QUARTER - 64)) | (1L << (NEXT_QUARTER - 64)) | (1L << (NEXT_N_QUARTERS_N - 64)) | (1L << (LAST_N_QUARTERS_N - 64)) | (1L << (THIS_YEAR - 64)) | (1L << (LAST_YEAR - 64)))) != 0) || ((((_la - 128)) & ~0x3f) == 0 && ((1L << (_la - 128)) & ((1L << (NEXT_YEAR - 128)) | (1L << (NEXT_N_YEARS_N - 128)) | (1L << (LAST_N_YEARS_N - 128)) | (1L << (THIS_FISCAL_QUARTER - 128)) | (1L << (LAST_FISCAL_QUARTER - 128)) | (1L << (NEXT_FISCAL_QUARTER - 128)) | (1L << (NEXT_N_FISCAL_QUARTERS_N - 128)) | (1L << (LAST_N_FISCAL_QUARTERS_N - 128)) | (1L << (THIS_FISCAL_YEAR - 128)) | (1L << (LAST_FISCAL_YEAR - 128)) | (1L << (NEXT_FISCAL_YEAR - 128)) | (1L << (NEXT_N_FISCAL_YEARS_N - 128)) | (1L << (LAST_N_FISCAL_YEARS_N - 128)))) != 0) || _la==Identifier) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u00ca\u05ef\4\2\t"+
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
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2\u010b\n\2\f\2\16"+
		"\2\u010e\13\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\7\5\u011b\n"+
		"\5\f\5\16\5\u011e\13\5\3\5\3\5\7\5\u0122\n\5\f\5\16\5\u0125\13\5\3\5\3"+
		"\5\7\5\u0129\n\5\f\5\16\5\u012c\13\5\3\5\5\5\u012f\n\5\3\6\3\6\3\6\3\6"+
		"\5\6\u0135\n\6\3\6\3\6\5\6\u0139\n\6\3\6\3\6\3\7\3\7\3\7\3\7\5\7\u0141"+
		"\n\7\3\7\3\7\3\b\3\b\3\b\7\b\u0148\n\b\f\b\16\b\u014b\13\b\3\t\3\t\3\t"+
		"\3\t\5\t\u0151\n\t\3\t\3\t\3\n\3\n\3\n\7\n\u0158\n\n\f\n\16\n\u015b\13"+
		"\n\3\13\3\13\7\13\u015f\n\13\f\13\16\13\u0162\13\13\3\13\3\13\3\f\3\f"+
		"\7\f\u0168\n\f\f\f\16\f\u016b\13\f\3\f\3\f\3\r\3\r\5\r\u0171\n\r\3\r\3"+
		"\r\7\r\u0175\n\r\f\r\16\r\u0178\13\r\3\r\5\r\u017b\n\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\5\16\u0190\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u0199"+
		"\n\17\3\20\3\20\5\20\u019d\n\20\3\20\3\20\3\20\3\20\5\20\u01a3\n\20\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\7\23\u01b1"+
		"\n\23\f\23\16\23\u01b4\13\23\3\23\3\23\3\24\7\24\u01b9\n\24\f\24\16\24"+
		"\u01bc\13\24\3\24\3\24\5\24\u01c0\n\24\3\24\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\7\25\u01c9\n\25\f\25\16\25\u01cc\13\25\3\26\3\26\3\26\5\26\u01d1\n"+
		"\26\3\27\3\27\3\27\3\27\7\27\u01d7\n\27\f\27\16\27\u01da\13\27\3\27\5"+
		"\27\u01dd\n\27\5\27\u01df\n\27\3\27\3\27\3\30\3\30\3\30\7\30\u01e6\n\30"+
		"\f\30\16\30\u01e9\13\30\3\30\3\30\3\31\3\31\7\31\u01ef\n\31\f\31\16\31"+
		"\u01f2\13\31\3\32\3\32\5\32\u01f6\n\32\3\32\3\32\5\32\u01fa\n\32\3\32"+
		"\3\32\5\32\u01fe\n\32\3\32\3\32\5\32\u0202\n\32\5\32\u0204\n\32\3\33\3"+
		"\33\3\33\3\33\3\34\3\34\5\34\u020c\n\34\3\34\3\34\3\35\3\35\3\35\7\35"+
		"\u0213\n\35\f\35\16\35\u0216\13\35\3\36\7\36\u0219\n\36\f\36\16\36\u021c"+
		"\13\36\3\36\3\36\3\36\3\37\3\37\3\37\7\37\u0224\n\37\f\37\16\37\u0227"+
		"\13\37\3 \3 \3!\3!\3!\3!\3!\5!\u0230\n!\3!\5!\u0233\n!\3\"\3\"\5\"\u0237"+
		"\n\"\3\"\7\"\u023a\n\"\f\"\16\"\u023d\13\"\3#\3#\3#\3#\3$\3$\3$\5$\u0246"+
		"\n$\3%\3%\3%\3%\7%\u024c\n%\f%\16%\u024f\13%\5%\u0251\n%\3%\5%\u0254\n"+
		"%\3%\3%\3&\3&\7&\u025a\n&\f&\16&\u025d\13&\3&\3&\3\'\3\'\3\'\3(\7(\u0265"+
		"\n(\f(\16(\u0268\13(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3"+
		")\3)\3)\3)\3)\3)\3)\5)\u0281\n)\3*\3*\3*\3*\3*\5*\u0288\n*\3+\3+\3+\3"+
		"+\3+\6+\u028f\n+\r+\16+\u0290\3+\3+\3,\3,\3,\3,\3-\3-\3-\3-\7-\u029d\n"+
		"-\f-\16-\u02a0\13-\3-\3-\3-\5-\u02a5\n-\3.\5.\u02a8\n.\3.\3.\3.\3.\3."+
		"\5.\u02af\n.\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61"+
		"\3\61\3\61\3\62\3\62\3\62\6\62\u02c4\n\62\r\62\16\62\u02c5\3\62\5\62\u02c9"+
		"\n\62\3\62\5\62\u02cc\n\62\3\63\3\63\5\63\u02d0\n\63\3\63\3\63\3\64\3"+
		"\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\3\67\38\38"+
		"\38\38\39\39\39\39\3:\3:\3:\3:\3;\3;\3;\5;\u02f1\n;\3;\3;\3<\3<\3<\3<"+
		"\3<\3=\3=\3=\5=\u02fd\n=\3=\3=\3=\3>\3>\3>\3?\7?\u0306\n?\f?\16?\u0309"+
		"\13?\3?\3?\5?\u030d\n?\3@\3@\3@\5@\u0312\n@\3A\3A\3A\5A\u0317\nA\3B\3"+
		"B\3B\7B\u031c\nB\fB\16B\u031f\13B\3B\3B\3B\3B\3B\3C\3C\3C\3D\3D\5D\u032b"+
		"\nD\3D\3D\5D\u032f\nD\3D\3D\5D\u0333\nD\5D\u0335\nD\3E\3E\5E\u0339\nE"+
		"\3F\3F\3F\3F\3F\3G\3G\3H\3H\3H\3H\3I\3I\3I\7I\u0349\nI\fI\16I\u034c\13"+
		"I\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5J\u035c\nJ\3J\3J\3J\3J\3"+
		"J\3J\3J\3J\3J\3J\3J\3J\3J\3J\5J\u036c\nJ\3J\3J\3J\3J\5J\u0372\nJ\3J\3"+
		"J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3"+
		"J\3J\3J\3J\3J\3J\3J\3J\5J\u0394\nJ\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\7J\u03a0"+
		"\nJ\fJ\16J\u03a3\13J\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\5K\u03b2\n"+
		"K\3L\3L\3L\5L\u03b7\nL\3L\3L\3L\3L\3L\5L\u03be\nL\3L\3L\3L\3L\5L\u03c4"+
		"\nL\3L\5L\u03c7\nL\3M\3M\3M\5M\u03cc\nM\3M\3M\3N\3N\3N\3N\3N\3N\5N\u03d6"+
		"\nN\3O\3O\3O\7O\u03db\nO\fO\16O\u03de\13O\3P\3P\3P\3P\3P\5P\u03e5\nP\3"+
		"Q\3Q\3Q\3R\3R\3S\3S\3S\3S\3S\3S\3S\5S\u03f3\nS\5S\u03f5\nS\3T\3T\3T\3"+
		"T\7T\u03fb\nT\fT\16T\u03fe\13T\3T\3T\3U\3U\3U\3U\3V\3V\3V\3V\7V\u040a"+
		"\nV\fV\16V\u040d\13V\3V\3V\3W\3W\5W\u0413\nW\3W\3W\3X\3X\3X\3X\3Y\3Y\3"+
		"Y\3Y\3Y\5Y\u0420\nY\3Y\5Y\u0423\nY\3Y\5Y\u0426\nY\3Y\5Y\u0429\nY\3Y\5"+
		"Y\u042c\nY\3Y\5Y\u042f\nY\3Y\5Y\u0432\nY\3Y\5Y\u0435\nY\3Y\3Y\3Z\3Z\3"+
		"Z\3Z\3Z\5Z\u043e\nZ\3Z\5Z\u0441\nZ\3Z\5Z\u0444\nZ\3[\3[\3[\7[\u0449\n"+
		"[\f[\16[\u044c\13[\3\\\3\\\5\\\u0450\n\\\3\\\3\\\5\\\u0454\n\\\3\\\3\\"+
		"\3\\\3\\\3\\\5\\\u045b\n\\\3]\3]\3]\7]\u0460\n]\f]\16]\u0463\13]\3^\3"+
		"^\5^\u0467\n^\3^\3^\3^\5^\u046c\n^\7^\u046e\n^\f^\16^\u0471\13^\3_\3_"+
		"\3_\7_\u0476\n_\f_\16_\u0479\13_\3`\3`\5`\u047d\n`\3`\3`\5`\u0481\n`\5"+
		"`\u0483\n`\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3"+
		"a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\3a\5a\u04ab\na\3"+
		"b\3b\3b\6b\u04b0\nb\rb\16b\u04b1\3b\5b\u04b5\nb\3b\3b\3c\3c\3c\3c\3c\3"+
		"d\3d\3d\3e\3e\3e\7e\u04c4\ne\fe\16e\u04c7\13e\3f\3f\3f\3f\3g\3g\3g\3h"+
		"\3h\3h\7h\u04d3\nh\fh\16h\u04d6\13h\3h\3h\3h\7h\u04db\nh\fh\16h\u04de"+
		"\13h\3h\3h\5h\u04e2\nh\3i\3i\3i\3i\3i\5i\u04e9\ni\3j\3j\3j\3j\3j\3j\3"+
		"j\3j\5j\u04f3\nj\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\3k\5k\u0504"+
		"\nk\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\3l\5l\u0516\nl\3m\3m"+
		"\3m\3m\7m\u051c\nm\fm\16m\u051f\13m\3m\3m\3n\3n\3n\3o\3o\3o\3o\3o\3o\3"+
		"o\3o\5o\u052e\no\3p\3p\3p\7p\u0533\np\fp\16p\u0536\13p\3q\3q\3q\3q\3r"+
		"\3r\3r\3r\3r\7r\u0541\nr\fr\16r\u0544\13r\3r\3r\5r\u0548\nr\3s\3s\3t\3"+
		"t\3t\3t\3t\5t\u0551\nt\3t\3t\3t\3t\3t\3t\3t\7t\u055a\nt\ft\16t\u055d\13"+
		"t\3t\3t\3t\3t\3t\3t\3t\3t\3t\7t\u0568\nt\ft\16t\u056b\13t\3t\3t\5t\u056f"+
		"\nt\3u\3u\3u\3u\3v\3v\3v\7v\u0578\nv\fv\16v\u057b\13v\3w\3w\5w\u057f\n"+
		"w\3w\3w\5w\u0583\nw\3w\5w\u0586\nw\3x\3x\3x\3x\5x\u058c\nx\3y\3y\3y\3"+
		"y\5y\u0592\ny\3z\3z\3z\3{\3{\7{\u0599\n{\f{\16{\u059c\13{\3|\3|\3|\3}"+
		"\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}"+
		"\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}"+
		"\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\3}\5}\u05e2\n}\3~"+
		"\5~\u05e5\n~\3~\3~\3\177\3\177\3\u0080\3\u0080\3\u0081\3\u0081\3\u0081"+
		"\2\3\u0092\u0082\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088"+
		"\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0"+
		"\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8"+
		"\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0"+
		"\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8"+
		"\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00fe\u0100"+
		"\2\24\3\2\4\5\5\2\n\n\27\27/\60\4\2\34\34\u0091\u0095\3\2\u00af\u00b2"+
		"\3\2\u00a3\u00a4\4\2\u00b3\u00b4\u00b8\u00b8\3\2\u00b1\u00b2\3\2\u00a1"+
		"\u00a2\3\2\u00a8\u00ac\4\2\u00a0\u00a0\u00ba\u00c4\4\2\u009f\u009f\u00a5"+
		"\u00a5\3\2\u00af\u00b0\3\2cf\3\2TU\3\2WX\5\2\60\60\\\\hh\r\2\4\5\22\22"+
		"\26\26\30\30$%((,-\65\65\678;\u008e\u00c6\u00c6\6\2\3\"$\62\64\u008e\u00c6"+
		"\u00c6\2\u068d\2\u0102\3\2\2\2\4\u0113\3\2\2\2\6\u0116\3\2\2\2\b\u012e"+
		"\3\2\2\2\n\u0130\3\2\2\2\f\u013c\3\2\2\2\16\u0144\3\2\2\2\20\u014c\3\2"+
		"\2\2\22\u0154\3\2\2\2\24\u015c\3\2\2\2\26\u0165\3\2\2\2\30\u017a\3\2\2"+
		"\2\32\u018f\3\2\2\2\34\u0198\3\2\2\2\36\u019c\3\2\2\2 \u01a4\3\2\2\2\""+
		"\u01a8\3\2\2\2$\u01ac\3\2\2\2&\u01ba\3\2\2\2(\u01c5\3\2\2\2*\u01cd\3\2"+
		"\2\2,\u01d2\3\2\2\2.\u01e2\3\2\2\2\60\u01f0\3\2\2\2\62\u0203\3\2\2\2\64"+
		"\u0205\3\2\2\2\66\u0209\3\2\2\28\u020f\3\2\2\2:\u021a\3\2\2\2<\u0220\3"+
		"\2\2\2>\u0228\3\2\2\2@\u022a\3\2\2\2B\u0234\3\2\2\2D\u023e\3\2\2\2F\u0245"+
		"\3\2\2\2H\u0247\3\2\2\2J\u0257\3\2\2\2L\u0260\3\2\2\2N\u0266\3\2\2\2P"+
		"\u0280\3\2\2\2R\u0282\3\2\2\2T\u0289\3\2\2\2V\u0294\3\2\2\2X\u02a4\3\2"+
		"\2\2Z\u02ae\3\2\2\2\\\u02b0\3\2\2\2^\u02b6\3\2\2\2`\u02ba\3\2\2\2b\u02c0"+
		"\3\2\2\2d\u02cd\3\2\2\2f\u02d3\3\2\2\2h\u02d7\3\2\2\2j\u02da\3\2\2\2l"+
		"\u02dd\3\2\2\2n\u02e1\3\2\2\2p\u02e5\3\2\2\2r\u02e9\3\2\2\2t\u02ed\3\2"+
		"\2\2v\u02f4\3\2\2\2x\u02f9\3\2\2\2z\u0301\3\2\2\2|\u0307\3\2\2\2~\u030e"+
		"\3\2\2\2\u0080\u0313\3\2\2\2\u0082\u0318\3\2\2\2\u0084\u0325\3\2\2\2\u0086"+
		"\u0334\3\2\2\2\u0088\u0338\3\2\2\2\u008a\u033a\3\2\2\2\u008c\u033f\3\2"+
		"\2\2\u008e\u0341\3\2\2\2\u0090\u0345\3\2\2\2\u0092\u035b\3\2\2\2\u0094"+
		"\u03b1\3\2\2\2\u0096\u03c6\3\2\2\2\u0098\u03c8\3\2\2\2\u009a\u03cf\3\2"+
		"\2\2\u009c\u03d7\3\2\2\2\u009e\u03df\3\2\2\2\u00a0\u03e6\3\2\2\2\u00a2"+
		"\u03e9\3\2\2\2\u00a4\u03f4\3\2\2\2\u00a6\u03f6\3\2\2\2\u00a8\u0401\3\2"+
		"\2\2\u00aa\u0405\3\2\2\2\u00ac\u0410\3\2\2\2\u00ae\u0416\3\2\2\2\u00b0"+
		"\u041a\3\2\2\2\u00b2\u0438\3\2\2\2\u00b4\u0445\3\2\2\2\u00b6\u045a\3\2"+
		"\2\2\u00b8\u045c\3\2\2\2\u00ba\u0464\3\2\2\2\u00bc\u0472\3\2\2\2\u00be"+
		"\u0482\3\2\2\2\u00c0\u04aa\3\2\2\2\u00c2\u04ac\3\2\2\2\u00c4\u04b8\3\2"+
		"\2\2\u00c6\u04bd\3\2\2\2\u00c8\u04c0\3\2\2\2\u00ca\u04c8\3\2\2\2\u00cc"+
		"\u04cc\3\2\2\2\u00ce\u04e1\3\2\2\2\u00d0\u04e8\3\2\2\2\u00d2\u04f2\3\2"+
		"\2\2\u00d4\u0503\3\2\2\2\u00d6\u0515\3\2\2\2\u00d8\u0517\3\2\2\2\u00da"+
		"\u0522\3\2\2\2\u00dc\u052d\3\2\2\2\u00de\u052f\3\2\2\2\u00e0\u0537\3\2"+
		"\2\2\u00e2\u0547\3\2\2\2\u00e4\u0549\3\2\2\2\u00e6\u056e\3\2\2\2\u00e8"+
		"\u0570\3\2\2\2\u00ea\u0574\3\2\2\2\u00ec\u0585\3\2\2\2\u00ee\u058b\3\2"+
		"\2\2\u00f0\u0591\3\2\2\2\u00f2\u0593\3\2\2\2\u00f4\u059a\3\2\2\2\u00f6"+
		"\u059d\3\2\2\2\u00f8\u05e1\3\2\2\2\u00fa\u05e4\3\2\2\2\u00fc\u05e8\3\2"+
		"\2\2\u00fe\u05ea\3\2\2\2\u0100\u05ec\3\2\2\2\u0102\u0103\7-\2\2\u0103"+
		"\u0104\5\u00fe\u0080\2\u0104\u0105\7\35\2\2\u0105\u0106\5\u00fe\u0080"+
		"\2\u0106\u0107\7\u0097\2\2\u0107\u010c\5\4\3\2\u0108\u0109\7\u009e\2\2"+
		"\u0109\u010b\5\4\3\2\u010a\u0108\3\2\2\2\u010b\u010e\3\2\2\2\u010c\u010a"+
		"\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\3\2\2\2\u010e\u010c\3\2\2\2\u010f"+
		"\u0110\7\u0098\2\2\u0110\u0111\5J&\2\u0111\u0112\7\2\2\3\u0112\3\3\2\2"+
		"\2\u0113\u0114\t\2\2\2\u0114\u0115\t\3\2\2\u0115\5\3\2\2\2\u0116\u0117"+
		"\5\b\5\2\u0117\u0118\7\2\2\3\u0118\7\3\2\2\2\u0119\u011b\5\32\16\2\u011a"+
		"\u0119\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011a\3\2\2\2\u011c\u011d\3\2"+
		"\2\2\u011d\u011f\3\2\2\2\u011e\u011c\3\2\2\2\u011f\u012f\5\n\6\2\u0120"+
		"\u0122\5\32\16\2\u0121\u0120\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121\3"+
		"\2\2\2\u0123\u0124\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u0123\3\2\2\2\u0126"+
		"\u012f\5\f\7\2\u0127\u0129\5\32\16\2\u0128\u0127\3\2\2\2\u0129\u012c\3"+
		"\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012d\3\2\2\2\u012c"+
		"\u012a\3\2\2\2\u012d\u012f\5\20\t\2\u012e\u011c\3\2\2\2\u012e\u0123\3"+
		"\2\2\2\u012e\u012a\3\2\2\2\u012f\t\3\2\2\2\u0130\u0131\7\b\2\2\u0131\u0134"+
		"\5\u00fe\u0080\2\u0132\u0133\7\16\2\2\u0133\u0135\5.\30\2\u0134\u0132"+
		"\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0137\7\25\2\2"+
		"\u0137\u0139\5\22\n\2\u0138\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013a"+
		"\3\2\2\2\u013a\u013b\5\24\13\2\u013b\13\3\2\2\2\u013c\u013d\7\r\2\2\u013d"+
		"\u013e\5\u00fe\u0080\2\u013e\u0140\7\u0099\2\2\u013f\u0141\5\16\b\2\u0140"+
		"\u013f\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0143\7\u009a"+
		"\2\2\u0143\r\3\2\2\2\u0144\u0149\5\u00fe\u0080\2\u0145\u0146\7\u009e\2"+
		"\2\u0146\u0148\5\u00fe\u0080\2\u0147\u0145\3\2\2\2\u0148\u014b\3\2\2\2"+
		"\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\17\3\2\2\2\u014b\u0149"+
		"\3\2\2\2\u014c\u014d\7\31\2\2\u014d\u0150\5\u00fe\u0080\2\u014e\u014f"+
		"\7\16\2\2\u014f\u0151\5\22\n\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2"+
		"\u0151\u0152\3\2\2\2\u0152\u0153\5\26\f\2\u0153\21\3\2\2\2\u0154\u0159"+
		"\5.\30\2\u0155\u0156\7\u009e\2\2\u0156\u0158\5.\30\2\u0157\u0155\3\2\2"+
		"\2\u0158\u015b\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a\23"+
		"\3\2\2\2\u015b\u0159\3\2\2\2\u015c\u0160\7\u0099\2\2\u015d\u015f\5\30"+
		"\r\2\u015e\u015d\3\2\2\2\u015f\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160"+
		"\u0161\3\2\2\2\u0161\u0163\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0164\7\u009a"+
		"\2\2\u0164\25\3\2\2\2\u0165\u0169\7\u0099\2\2\u0166\u0168\5&\24\2\u0167"+
		"\u0166\3\2\2\2\u0168\u016b\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2"+
		"\2\2\u016a\u016c\3\2\2\2\u016b\u0169\3\2\2\2\u016c\u016d\7\u009a\2\2\u016d"+
		"\27\3\2\2\2\u016e\u017b\7\u009d\2\2\u016f\u0171\7&\2\2\u0170\u016f\3\2"+
		"\2\2\u0170\u0171\3\2\2\2\u0171\u0172\3\2\2\2\u0172\u017b\5J&\2\u0173\u0175"+
		"\5\32\16\2\u0174\u0173\3\2\2\2\u0175\u0178\3\2\2\2\u0176\u0174\3\2\2\2"+
		"\u0176\u0177\3\2\2\2\u0177\u0179\3\2\2\2\u0178\u0176\3\2\2\2\u0179\u017b"+
		"\5\34\17\2\u017a\u016e\3\2\2\2\u017a\u0170\3\2\2\2\u017a\u0176\3\2\2\2"+
		"\u017b\31\3\2\2\2\u017c\u0190\5@!\2\u017d\u0190\7\23\2\2\u017e\u0190\7"+
		"!\2\2\u017f\u0190\7 \2\2\u0180\u0190\7\37\2\2\u0181\u0190\7,\2\2\u0182"+
		"\u0190\7&\2\2\u0183\u0190\7\3\2\2\u0184\u0190\7\17\2\2\u0185\u0190\7\64"+
		"\2\2\u0186\u0190\7\36\2\2\u0187\u0190\7\62\2\2\u0188\u0190\7)\2\2\u0189"+
		"\u018a\7\67\2\2\u018a\u0190\7%\2\2\u018b\u018c\78\2\2\u018c\u0190\7%\2"+
		"\2\u018d\u018e\7\26\2\2\u018e\u0190\7%\2\2\u018f\u017c\3\2\2\2\u018f\u017d"+
		"\3\2\2\2\u018f\u017e\3\2\2\2\u018f\u017f\3\2\2\2\u018f\u0180\3\2\2\2\u018f"+
		"\u0181\3\2\2\2\u018f\u0182\3\2\2\2\u018f\u0183\3\2\2\2\u018f\u0184\3\2"+
		"\2\2\u018f\u0185\3\2\2\2\u018f\u0186\3\2\2\2\u018f\u0187\3\2\2\2\u018f"+
		"\u0188\3\2\2\2\u018f\u0189\3\2\2\2\u018f\u018b\3\2\2\2\u018f\u018d\3\2"+
		"\2\2\u0190\33\3\2\2\2\u0191\u0199\5\36\20\2\u0192\u0199\5\"\22\2\u0193"+
		"\u0199\5 \21\2\u0194\u0199\5\20\t\2\u0195\u0199\5\n\6\2\u0196\u0199\5"+
		"\f\7\2\u0197\u0199\5$\23\2\u0198\u0191\3\2\2\2\u0198\u0192\3\2\2\2\u0198"+
		"\u0193\3\2\2\2\u0198\u0194\3\2\2\2\u0198\u0195\3\2\2\2\u0198\u0196\3\2"+
		"\2\2\u0198\u0197\3\2\2\2\u0199\35\3\2\2\2\u019a\u019d\5.\30\2\u019b\u019d"+
		"\7\63\2\2\u019c\u019a\3\2\2\2\u019c\u019b\3\2\2\2\u019d\u019e\3\2\2\2"+
		"\u019e\u019f\5\u00fe\u0080\2\u019f\u01a2\5\66\34\2\u01a0\u01a3\5J&\2\u01a1"+
		"\u01a3\7\u009d\2\2\u01a2\u01a0\3\2\2\2\u01a2\u01a1\3\2\2\2\u01a3\37\3"+
		"\2\2\2\u01a4\u01a5\5<\37\2\u01a5\u01a6\5\66\34\2\u01a6\u01a7\5J&\2\u01a7"+
		"!\3\2\2\2\u01a8\u01a9\5.\30\2\u01a9\u01aa\5(\25\2\u01aa\u01ab\7\u009d"+
		"\2\2\u01ab#\3\2\2\2\u01ac\u01ad\5.\30\2\u01ad\u01ae\5\u00fe\u0080\2\u01ae"+
		"\u01b2\7\u0099\2\2\u01af\u01b1\5|?\2\u01b0\u01af\3\2\2\2\u01b1\u01b4\3"+
		"\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b5\3\2\2\2\u01b4"+
		"\u01b2\3\2\2\2\u01b5\u01b6\7\u009a\2\2\u01b6%\3\2\2\2\u01b7\u01b9\5\32"+
		"\16\2\u01b8\u01b7\3\2\2\2\u01b9\u01bc\3\2\2\2\u01ba\u01b8\3\2\2\2\u01ba"+
		"\u01bb\3\2\2\2\u01bb\u01bf\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bd\u01c0\5."+
		"\30\2\u01be\u01c0\7\63\2\2\u01bf\u01bd\3\2\2\2\u01bf\u01be\3\2\2\2\u01c0"+
		"\u01c1\3\2\2\2\u01c1\u01c2\5\u00fe\u0080\2\u01c2\u01c3\5\66\34\2\u01c3"+
		"\u01c4\7\u009d\2\2\u01c4\'\3\2\2\2\u01c5\u01ca\5*\26\2\u01c6\u01c7\7\u009e"+
		"\2\2\u01c7\u01c9\5*\26\2\u01c8\u01c6\3\2\2\2\u01c9\u01cc\3\2\2\2\u01ca"+
		"\u01c8\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb)\3\2\2\2\u01cc\u01ca\3\2\2\2"+
		"\u01cd\u01d0\5\u00fe\u0080\2\u01ce\u01cf\7\u00a0\2\2\u01cf\u01d1\5\u0092"+
		"J\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1+\3\2\2\2\u01d2\u01de"+
		"\7\u0099\2\2\u01d3\u01d8\5\u0092J\2\u01d4\u01d5\7\u009e\2\2\u01d5\u01d7"+
		"\5\u0092J\2\u01d6\u01d4\3\2\2\2\u01d7\u01da\3\2\2\2\u01d8\u01d6\3\2\2"+
		"\2\u01d8\u01d9\3\2\2\2\u01d9\u01dc\3\2\2\2\u01da\u01d8\3\2\2\2\u01db\u01dd"+
		"\7\u009e\2\2\u01dc\u01db\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01df\3\2\2"+
		"\2\u01de\u01d3\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1"+
		"\7\u009a\2\2\u01e1-\3\2\2\2\u01e2\u01e7\5\62\32\2\u01e3\u01e4\7\u009f"+
		"\2\2\u01e4\u01e6\5\62\32\2\u01e5\u01e3\3\2\2\2\u01e6\u01e9\3\2\2\2\u01e7"+
		"\u01e5\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01ea\3\2\2\2\u01e9\u01e7\3\2"+
		"\2\2\u01ea\u01eb\5\60\31\2\u01eb/\3\2\2\2\u01ec\u01ed\7\u009b\2\2\u01ed"+
		"\u01ef\7\u009c\2\2\u01ee\u01ec\3\2\2\2\u01ef\u01f2\3\2\2\2\u01f0\u01ee"+
		"\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\61\3\2\2\2\u01f2\u01f0\3\2\2\2\u01f3"+
		"\u01f5\79\2\2\u01f4\u01f6\5\64\33\2\u01f5\u01f4\3\2\2\2\u01f5\u01f6\3"+
		"\2\2\2\u01f6\u0204\3\2\2\2\u01f7\u01f9\7$\2\2\u01f8\u01fa\5\64\33\2\u01f9"+
		"\u01f8\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u0204\3\2\2\2\u01fb\u01fd\7:"+
		"\2\2\u01fc\u01fe\5\64\33\2\u01fd\u01fc\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe"+
		"\u0204\3\2\2\2\u01ff\u0201\5\u00fe\u0080\2\u0200\u0202\5\64\33\2\u0201"+
		"\u0200\3\2\2\2\u0201\u0202\3\2\2\2\u0202\u0204\3\2\2\2\u0203\u01f3\3\2"+
		"\2\2\u0203\u01f7\3\2\2\2\u0203\u01fb\3\2\2\2\u0203\u01ff\3\2\2\2\u0204"+
		"\63\3\2\2\2\u0205\u0206\7\u00a2\2\2\u0206\u0207\5\22\n\2\u0207\u0208\7"+
		"\u00a1\2\2\u0208\65\3\2\2\2\u0209\u020b\7\u0097\2\2\u020a\u020c\58\35"+
		"\2\u020b\u020a\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020d\3\2\2\2\u020d\u020e"+
		"\7\u0098\2\2\u020e\67\3\2\2\2\u020f\u0214\5:\36\2\u0210\u0211\7\u009e"+
		"\2\2\u0211\u0213\5:\36\2\u0212\u0210\3\2\2\2\u0213\u0216\3\2\2\2\u0214"+
		"\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u02159\3\2\2\2\u0216\u0214\3\2\2\2"+
		"\u0217\u0219\5\32\16\2\u0218\u0217\3\2\2\2\u0219\u021c\3\2\2\2\u021a\u0218"+
		"\3\2\2\2\u021a\u021b\3\2\2\2\u021b\u021d\3\2\2\2\u021c\u021a\3\2\2\2\u021d"+
		"\u021e\5.\30\2\u021e\u021f\5\u00fe\u0080\2\u021f;\3\2\2\2\u0220\u0225"+
		"\5\u00fe\u0080\2\u0221\u0222\7\u009f\2\2\u0222\u0224\5\u00fe\u0080\2\u0223"+
		"\u0221\3\2\2\2\u0224\u0227\3\2\2\2\u0225\u0223\3\2\2\2\u0225\u0226\3\2"+
		"\2\2\u0226=\3\2\2\2\u0227\u0225\3\2\2\2\u0228\u0229\t\4\2\2\u0229?\3\2"+
		"\2\2\u022a\u022b\7\u00c5\2\2\u022b\u0232\5<\37\2\u022c\u022f\7\u0097\2"+
		"\2\u022d\u0230\5B\"\2\u022e\u0230\5F$\2\u022f\u022d\3\2\2\2\u022f\u022e"+
		"\3\2\2\2\u022f\u0230\3\2\2\2\u0230\u0231\3\2\2\2\u0231\u0233\7\u0098\2"+
		"\2\u0232\u022c\3\2\2\2\u0232\u0233\3\2\2\2\u0233A\3\2\2\2\u0234\u023b"+
		"\5D#\2\u0235\u0237\7\u009e\2\2\u0236\u0235\3\2\2\2\u0236\u0237\3\2\2\2"+
		"\u0237\u0238\3\2\2\2\u0238\u023a\5D#\2\u0239\u0236\3\2\2\2\u023a\u023d"+
		"\3\2\2\2\u023b\u0239\3\2\2\2\u023b\u023c\3\2\2\2\u023cC\3\2\2\2\u023d"+
		"\u023b\3\2\2\2\u023e\u023f\5\u00fe\u0080\2\u023f\u0240\7\u00a0\2\2\u0240"+
		"\u0241\5F$\2\u0241E\3\2\2\2\u0242\u0246\5\u0092J\2\u0243\u0246\5@!\2\u0244"+
		"\u0246\5H%\2\u0245\u0242\3\2\2\2\u0245\u0243\3\2\2\2\u0245\u0244\3\2\2"+
		"\2\u0246G\3\2\2\2\u0247\u0250\7\u0099\2\2\u0248\u024d\5F$\2\u0249\u024a"+
		"\7\u009e\2\2\u024a\u024c\5F$\2\u024b\u0249\3\2\2\2\u024c\u024f\3\2\2\2"+
		"\u024d\u024b\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u0251\3\2\2\2\u024f\u024d"+
		"\3\2\2\2\u0250\u0248\3\2\2\2\u0250\u0251\3\2\2\2\u0251\u0253\3\2\2\2\u0252"+
		"\u0254\7\u009e\2\2\u0253\u0252\3\2\2\2\u0253\u0254\3\2\2\2\u0254\u0255"+
		"\3\2\2\2\u0255\u0256\7\u009a\2\2\u0256I\3\2\2\2\u0257\u025b\7\u0099\2"+
		"\2\u0258\u025a\5P)\2\u0259\u0258\3\2\2\2\u025a\u025d\3\2\2\2\u025b\u0259"+
		"\3\2\2\2\u025b\u025c\3\2\2\2\u025c\u025e\3\2\2\2\u025d\u025b\3\2\2\2\u025e"+
		"\u025f\7\u009a\2\2\u025fK\3\2\2\2\u0260\u0261\5N(\2\u0261\u0262\7\u009d"+
		"\2\2\u0262M\3\2\2\2\u0263\u0265\5\32\16\2\u0264\u0263\3\2\2\2\u0265\u0268"+
		"\3\2\2\2\u0266\u0264\3\2\2\2\u0266\u0267\3\2\2\2\u0267\u0269\3\2\2\2\u0268"+
		"\u0266\3\2\2\2\u0269\u026a\5.\30\2\u026a\u026b\5(\25\2\u026bO\3\2\2\2"+
		"\u026c\u0281\5J&\2\u026d\u0281\5R*\2\u026e\u0281\5T+\2\u026f\u0281\5\\"+
		"/\2\u0270\u0281\5^\60\2\u0271\u0281\5`\61\2\u0272\u0281\5b\62\2\u0273"+
		"\u0281\5d\63\2\u0274\u0281\5f\64\2\u0275\u0281\5h\65\2\u0276\u0281\5j"+
		"\66\2\u0277\u0281\5l\67\2\u0278\u0281\5n8\2\u0279\u0281\5p9\2\u027a\u0281"+
		"\5r:\2\u027b\u0281\5t;\2\u027c\u0281\5v<\2\u027d\u0281\5x=\2\u027e\u0281"+
		"\5L\'\2\u027f\u0281\5z>\2\u0280\u026c\3\2\2\2\u0280\u026d\3\2\2\2\u0280"+
		"\u026e\3\2\2\2\u0280\u026f\3\2\2\2\u0280\u0270\3\2\2\2\u0280\u0271\3\2"+
		"\2\2\u0280\u0272\3\2\2\2\u0280\u0273\3\2\2\2\u0280\u0274\3\2\2\2\u0280"+
		"\u0275\3\2\2\2\u0280\u0276\3\2\2\2\u0280\u0277\3\2\2\2\u0280\u0278\3\2"+
		"\2\2\u0280\u0279\3\2\2\2\u0280\u027a\3\2\2\2\u0280\u027b\3\2\2\2\u0280"+
		"\u027c\3\2\2\2\u0280\u027d\3\2\2\2\u0280\u027e\3\2\2\2\u0280\u027f\3\2"+
		"\2\2\u0281Q\3\2\2\2\u0282\u0283\7\24\2\2\u0283\u0284\5\u008eH\2\u0284"+
		"\u0287\5P)\2\u0285\u0286\7\f\2\2\u0286\u0288\5P)\2\u0287\u0285\3\2\2\2"+
		"\u0287\u0288\3\2\2\2\u0288S\3\2\2\2\u0289\u028a\7(\2\2\u028a\u028b\7\35"+
		"\2\2\u028b\u028c\5\u0092J\2\u028c\u028e\7\u0099\2\2\u028d\u028f\5V,\2"+
		"\u028e\u028d\3\2\2\2\u028f\u0290\3\2\2\2\u0290\u028e\3\2\2\2\u0290\u0291"+
		"\3\2\2\2\u0291\u0292\3\2\2\2\u0292\u0293\7\u009a\2\2\u0293U\3\2\2\2\u0294"+
		"\u0295\7\65\2\2\u0295\u0296\5X-\2\u0296\u0297\5J&\2\u0297W\3\2\2\2\u0298"+
		"\u02a5\7\f\2\2\u0299\u029e\5Z.\2\u029a\u029b\7\u009e\2\2\u029b\u029d\5"+
		"Z.\2\u029c\u029a\3\2\2\2\u029d\u02a0\3\2\2\2\u029e\u029c\3\2\2\2\u029e"+
		"\u029f\3\2\2\2\u029f\u02a5\3\2\2\2\u02a0\u029e\3\2\2\2\u02a1\u02a2\5\u00fe"+
		"\u0080\2\u02a2\u02a3\5\u00fe\u0080\2\u02a3\u02a5\3\2\2\2\u02a4\u0298\3"+
		"\2\2\2\u02a4\u0299\3\2\2\2\u02a4\u02a1\3\2\2\2\u02a5Y\3\2\2\2\u02a6\u02a8"+
		"\7\u00b2\2\2\u02a7\u02a6\3\2\2\2\u02a7\u02a8\3\2\2\2\u02a8\u02a9\3\2\2"+
		"\2\u02a9\u02af\7\u0091\2\2\u02aa\u02af\7\u0092\2\2\u02ab\u02af\7\u0095"+
		"\2\2\u02ac\u02af\7\34\2\2\u02ad\u02af\5\u00fe\u0080\2\u02ae\u02a7\3\2"+
		"\2\2\u02ae\u02aa\3\2\2\2\u02ae\u02ab\3\2\2\2\u02ae\u02ac\3\2\2\2\u02ae"+
		"\u02ad\3\2\2\2\u02af[\3\2\2\2\u02b0\u02b1\7\21\2\2\u02b1\u02b2\7\u0097"+
		"\2\2\u02b2\u02b3\5\u0086D\2\u02b3\u02b4\7\u0098\2\2\u02b4\u02b5\5P)\2"+
		"\u02b5]\3\2\2\2\u02b6\u02b7\7\66\2\2\u02b7\u02b8\5\u008eH\2\u02b8\u02b9"+
		"\5P)\2\u02b9_\3\2\2\2\u02ba\u02bb\7\13\2\2\u02bb\u02bc\5P)\2\u02bc\u02bd"+
		"\7\66\2\2\u02bd\u02be\5\u008eH\2\u02be\u02bf\7\u009d\2\2\u02bfa\3\2\2"+
		"\2\u02c0\u02c1\7.\2\2\u02c1\u02cb\5J&\2\u02c2\u02c4\5\u0082B\2\u02c3\u02c2"+
		"\3\2\2\2\u02c4\u02c5\3\2\2\2\u02c5\u02c3\3\2\2\2\u02c5\u02c6\3\2\2\2\u02c6"+
		"\u02c8\3\2\2\2\u02c7\u02c9\5\u0084C\2\u02c8\u02c7\3\2\2\2\u02c8\u02c9"+
		"\3\2\2\2\u02c9\u02cc\3\2\2\2\u02ca\u02cc\5\u0084C\2\u02cb\u02c3\3\2\2"+
		"\2\u02cb\u02ca\3\2\2\2\u02ccc\3\2\2\2\u02cd\u02cf\7\"\2\2\u02ce\u02d0"+
		"\5\u0092J\2\u02cf\u02ce\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0\u02d1\3\2\2"+
		"\2\u02d1\u02d2\7\u009d\2\2\u02d2e\3\2\2\2\u02d3\u02d4\7+\2\2\u02d4\u02d5"+
		"\5\u0092J\2\u02d5\u02d6\7\u009d\2\2\u02d6g\3\2\2\2\u02d7\u02d8\7\6\2\2"+
		"\u02d8\u02d9\7\u009d\2\2\u02d9i\3\2\2\2\u02da\u02db\7\t\2\2\u02db\u02dc"+
		"\7\u009d\2\2\u02dck\3\2\2\2\u02dd\u02de\7\27\2\2\u02de\u02df\5\u0092J"+
		"\2\u02df\u02e0\7\u009d\2\2\u02e0m\3\2\2\2\u02e1\u02e2\7\60\2\2\u02e2\u02e3"+
		"\5\u0092J\2\u02e3\u02e4\7\u009d\2\2\u02e4o\3\2\2\2\u02e5\u02e6\7\n\2\2"+
		"\u02e6\u02e7\5\u0092J\2\u02e7\u02e8\7\u009d\2\2\u02e8q\3\2\2\2\u02e9\u02ea"+
		"\7/\2\2\u02ea\u02eb\5\u0092J\2\u02eb\u02ec\7\u009d\2\2\u02ecs\3\2\2\2"+
		"\u02ed\u02ee\7\61\2\2\u02ee\u02f0\5\u0092J\2\u02ef\u02f1\5<\37\2\u02f0"+
		"\u02ef\3\2\2\2\u02f0\u02f1\3\2\2\2\u02f1\u02f2\3\2\2\2\u02f2\u02f3\7\u009d"+
		"\2\2\u02f3u\3\2\2\2\u02f4\u02f5\7\32\2\2\u02f5\u02f6\5\u0092J\2\u02f6"+
		"\u02f7\5\u0092J\2\u02f7\u02f8\7\u009d\2\2\u02f8w\3\2\2\2\u02f9\u02fa\7"+
		"#\2\2\u02fa\u02fc\7\u0097\2\2\u02fb\u02fd\5\u0090I\2\u02fc\u02fb\3\2\2"+
		"\2\u02fc\u02fd\3\2\2\2\u02fd\u02fe\3\2\2\2\u02fe\u02ff\7\u0098\2\2\u02ff"+
		"\u0300\5J&\2\u0300y\3\2\2\2\u0301\u0302\5\u0092J\2\u0302\u0303\7\u009d"+
		"\2\2\u0303{\3\2\2\2\u0304\u0306\5\32\16\2\u0305\u0304\3\2\2\2\u0306\u0309"+
		"\3\2\2\2\u0307\u0305\3\2\2\2\u0307\u0308\3\2\2\2\u0308\u030c\3\2\2\2\u0309"+
		"\u0307\3\2\2\2\u030a\u030d\5~@\2\u030b\u030d\5\u0080A\2\u030c\u030a\3"+
		"\2\2\2\u030c\u030b\3\2\2\2\u030d}\3\2\2\2\u030e\u0311\7\22\2\2\u030f\u0312"+
		"\7\u009d\2\2\u0310\u0312\5J&\2\u0311\u030f\3\2\2\2\u0311\u0310\3\2\2\2"+
		"\u0312\177\3\2\2\2\u0313\u0316\7$\2\2\u0314\u0317\7\u009d\2\2\u0315\u0317"+
		"\5J&\2\u0316\u0314\3\2\2\2\u0316\u0315\3\2\2\2\u0317\u0081\3\2\2\2\u0318"+
		"\u0319\7\7\2\2\u0319\u031d\7\u0097\2\2\u031a\u031c\5\32\16\2\u031b\u031a"+
		"\3\2\2\2\u031c\u031f\3\2\2\2\u031d\u031b\3\2\2\2\u031d\u031e\3\2\2\2\u031e"+
		"\u0320\3\2\2\2\u031f\u031d\3\2\2\2\u0320\u0321\5<\37\2\u0321\u0322\5\u00fe"+
		"\u0080\2\u0322\u0323\7\u0098\2\2\u0323\u0324\5J&\2\u0324\u0083\3\2\2\2"+
		"\u0325\u0326\7\20\2\2\u0326\u0327\5J&\2\u0327\u0085\3\2\2\2\u0328\u0335"+
		"\5\u008aF\2\u0329\u032b\5\u0088E\2\u032a\u0329\3\2\2\2\u032a\u032b\3\2"+
		"\2\2\u032b\u032c\3\2\2\2\u032c\u032e\7\u009d\2\2\u032d\u032f\5\u0092J"+
		"\2\u032e\u032d\3\2\2\2\u032e\u032f\3\2\2\2\u032f\u0330\3\2\2\2\u0330\u0332"+
		"\7\u009d\2\2\u0331\u0333\5\u008cG\2\u0332\u0331\3\2\2\2\u0332\u0333\3"+
		"\2\2\2\u0333\u0335\3\2\2\2\u0334\u0328\3\2\2\2\u0334\u032a\3\2\2\2\u0335"+
		"\u0087\3\2\2\2\u0336\u0339\5N(\2\u0337\u0339\5\u0090I\2\u0338\u0336\3"+
		"\2\2\2\u0338\u0337\3\2\2\2\u0339\u0089\3\2\2\2\u033a\u033b\5.\30\2\u033b"+
		"\u033c\5\u00fe\u0080\2\u033c\u033d\7\u00a7\2\2\u033d\u033e\5\u0092J\2"+
		"\u033e\u008b\3\2\2\2\u033f\u0340\5\u0090I\2\u0340\u008d\3\2\2\2\u0341"+
		"\u0342\7\u0097\2\2\u0342\u0343\5\u0092J\2\u0343\u0344\7\u0098\2\2\u0344"+
		"\u008f\3\2\2\2\u0345\u034a\5\u0092J\2\u0346\u0347\7\u009e\2\2\u0347\u0349"+
		"\5\u0092J\2\u0348\u0346\3\2\2\2\u0349\u034c\3\2\2\2\u034a\u0348\3\2\2"+
		"\2\u034a\u034b\3\2\2\2\u034b\u0091\3\2\2\2\u034c\u034a\3\2\2\2\u034d\u034e"+
		"\bJ\1\2\u034e\u035c\5\u0094K\2\u034f\u035c\5\u0096L\2\u0350\u0351\7\33"+
		"\2\2\u0351\u035c\5\u009aN\2\u0352\u0353\7\u0097\2\2\u0353\u0354\5.\30"+
		"\2\u0354\u0355\7\u0098\2\2\u0355\u0356\5\u0092J\23\u0356\u035c\3\2\2\2"+
		"\u0357\u0358\t\5\2\2\u0358\u035c\5\u0092J\21\u0359\u035a\t\6\2\2\u035a"+
		"\u035c\5\u0092J\20\u035b\u034d\3\2\2\2\u035b\u034f\3\2\2\2\u035b\u0350"+
		"\3\2\2\2\u035b\u0352\3\2\2\2\u035b\u0357\3\2\2\2\u035b\u0359\3\2\2\2\u035c"+
		"\u03a1\3\2\2\2\u035d\u035e\f\17\2\2\u035e\u035f\t\7\2\2\u035f\u03a0\5"+
		"\u0092J\20\u0360\u0361\f\16\2\2\u0361\u0362\t\b\2\2\u0362\u03a0\5\u0092"+
		"J\17\u0363\u036b\f\r\2\2\u0364\u0365\7\u00a2\2\2\u0365\u036c\7\u00a2\2"+
		"\2\u0366\u0367\7\u00a1\2\2\u0367\u0368\7\u00a1\2\2\u0368\u036c\7\u00a1"+
		"\2\2\u0369\u036a\7\u00a1\2\2\u036a\u036c\7\u00a1\2\2\u036b\u0364\3\2\2"+
		"\2\u036b\u0366\3\2\2\2\u036b\u0369\3\2\2\2\u036c\u036d\3\2\2\2\u036d\u03a0"+
		"\5\u0092J\16\u036e\u036f\f\f\2\2\u036f\u0371\t\t\2\2\u0370\u0372\7\u00a0"+
		"\2\2\u0371\u0370\3\2\2\2\u0371\u0372\3\2\2\2\u0372\u0373\3\2\2\2\u0373"+
		"\u03a0\5\u0092J\r\u0374\u0375\f\n\2\2\u0375\u0376\t\n\2\2\u0376\u03a0"+
		"\5\u0092J\13\u0377\u0378\f\t\2\2\u0378\u0379\7\u00b5\2\2\u0379\u03a0\5"+
		"\u0092J\n\u037a\u037b\f\b\2\2\u037b\u037c\7\u00b7\2\2\u037c\u03a0\5\u0092"+
		"J\t\u037d\u037e\f\7\2\2\u037e\u037f\7\u00b6\2\2\u037f\u03a0\5\u0092J\b"+
		"\u0380\u0381\f\6\2\2\u0381\u0382\7\u00ad\2\2\u0382\u03a0\5\u0092J\7\u0383"+
		"\u0384\f\5\2\2\u0384\u0385\7\u00ae\2\2\u0385\u03a0\5\u0092J\6\u0386\u0387"+
		"\f\4\2\2\u0387\u0388\7\u00a6\2\2\u0388\u0389\5\u0092J\2\u0389\u038a\7"+
		"\u00a7\2\2\u038a\u038b\5\u0092J\4\u038b\u03a0\3\2\2\2\u038c\u038d\f\3"+
		"\2\2\u038d\u038e\t\13\2\2\u038e\u03a0\5\u0092J\3\u038f\u0390\f\27\2\2"+
		"\u0390\u0393\t\f\2\2\u0391\u0394\5\u0098M\2\u0392\u0394\5\u0100\u0081"+
		"\2\u0393\u0391\3\2\2\2\u0393\u0392\3\2\2\2\u0394\u03a0\3\2\2\2\u0395\u0396"+
		"\f\26\2\2\u0396\u0397\7\u009b\2\2\u0397\u0398\5\u0092J\2\u0398\u0399\7"+
		"\u009c\2\2\u0399\u03a0\3\2\2\2\u039a\u039b\f\22\2\2\u039b\u03a0\t\r\2"+
		"\2\u039c\u039d\f\13\2\2\u039d\u039e\7\30\2\2\u039e\u03a0\5.\30\2\u039f"+
		"\u035d\3\2\2\2\u039f\u0360\3\2\2\2\u039f\u0363\3\2\2\2\u039f\u036e\3\2"+
		"\2\2\u039f\u0374\3\2\2\2\u039f\u0377\3\2\2\2\u039f\u037a\3\2\2\2\u039f"+
		"\u037d\3\2\2\2\u039f\u0380\3\2\2\2\u039f\u0383\3\2\2\2\u039f\u0386\3\2"+
		"\2\2\u039f\u038c\3\2\2\2\u039f\u038f\3\2\2\2\u039f\u0395\3\2\2\2\u039f"+
		"\u039a\3\2\2\2\u039f\u039c\3\2\2\2\u03a0\u03a3\3\2\2\2\u03a1\u039f\3\2"+
		"\2\2\u03a1\u03a2\3\2\2\2\u03a2\u0093\3\2\2\2\u03a3\u03a1\3\2\2\2\u03a4"+
		"\u03a5\7\u0097\2\2\u03a5\u03a6\5\u0092J\2\u03a6\u03a7\7\u0098\2\2\u03a7"+
		"\u03b2\3\2\2\2\u03a8\u03b2\7*\2\2\u03a9\u03b2\7\'\2\2\u03aa\u03b2\5> "+
		"\2\u03ab\u03ac\5.\30\2\u03ac\u03ad\7\u009f\2\2\u03ad\u03ae\7\b\2\2\u03ae"+
		"\u03b2\3\2\2\2\u03af\u03b2\5\u00fe\u0080\2\u03b0\u03b2\5\u00aeX\2\u03b1"+
		"\u03a4\3\2\2\2\u03b1\u03a8\3\2\2\2\u03b1\u03a9\3\2\2\2\u03b1\u03aa\3\2"+
		"\2\2\u03b1\u03ab\3\2\2\2\u03b1\u03af\3\2\2\2\u03b1\u03b0\3\2\2\2\u03b2"+
		"\u0095\3\2\2\2\u03b3\u03b4\5\u00fe\u0080\2\u03b4\u03b6\7\u0097\2\2\u03b5"+
		"\u03b7\5\u0090I\2\u03b6\u03b5\3\2\2\2\u03b6\u03b7\3\2\2\2\u03b7\u03b8"+
		"\3\2\2\2\u03b8\u03b9\7\u0098\2\2\u03b9\u03c7\3\2\2\2\u03ba\u03bb\7*\2"+
		"\2\u03bb\u03bd\7\u0097\2\2\u03bc\u03be\5\u0090I\2\u03bd\u03bc\3\2\2\2"+
		"\u03bd\u03be\3\2\2\2\u03be\u03bf\3\2\2\2\u03bf\u03c7\7\u0098\2\2\u03c0"+
		"\u03c1\7\'\2\2\u03c1\u03c3\7\u0097\2\2\u03c2\u03c4\5\u0090I\2\u03c3\u03c2"+
		"\3\2\2\2\u03c3\u03c4\3\2\2\2\u03c4\u03c5\3\2\2\2\u03c5\u03c7\7\u0098\2"+
		"\2\u03c6\u03b3\3\2\2\2\u03c6\u03ba\3\2\2\2\u03c6\u03c0\3\2\2\2\u03c7\u0097"+
		"\3\2\2\2\u03c8\u03c9\5\u0100\u0081\2\u03c9\u03cb\7\u0097\2\2\u03ca\u03cc"+
		"\5\u0090I\2\u03cb\u03ca\3\2\2\2\u03cb\u03cc\3\2\2\2\u03cc\u03cd\3\2\2"+
		"\2\u03cd\u03ce\7\u0098\2\2\u03ce\u0099\3\2\2\2\u03cf\u03d5\5\u009cO\2"+
		"\u03d0\u03d6\5\u00a0Q\2\u03d1\u03d6\5\u00a2R\2\u03d2\u03d6\5\u00a4S\2"+
		"\u03d3\u03d6\5\u00a6T\2\u03d4\u03d6\5\u00aaV\2\u03d5\u03d0\3\2\2\2\u03d5"+
		"\u03d1\3\2\2\2\u03d5\u03d2\3\2\2\2\u03d5\u03d3\3\2\2\2\u03d5\u03d4\3\2"+
		"\2\2\u03d6\u009b\3\2\2\2\u03d7\u03dc\5\u009eP\2\u03d8\u03d9\7\u009f\2"+
		"\2\u03d9\u03db\5\u009eP\2\u03da\u03d8\3\2\2\2\u03db\u03de\3\2\2\2\u03dc"+
		"\u03da\3\2\2\2\u03dc\u03dd\3\2\2\2\u03dd\u009d\3\2\2\2\u03de\u03dc\3\2"+
		"\2\2\u03df\u03e4\5\u0100\u0081\2\u03e0\u03e1\7\u00a2\2\2\u03e1\u03e2\5"+
		"\22\n\2\u03e2\u03e3\7\u00a1\2\2\u03e3\u03e5\3\2\2\2\u03e4\u03e0\3\2\2"+
		"\2\u03e4\u03e5\3\2\2\2\u03e5\u009f\3\2\2\2\u03e6\u03e7\7\u0099\2\2\u03e7"+
		"\u03e8\7\u009a\2\2\u03e8\u00a1\3\2\2\2\u03e9\u03ea\5\u00acW\2\u03ea\u00a3"+
		"\3\2\2\2\u03eb\u03ec\7\u009b\2\2\u03ec\u03ed\5\u0092J\2\u03ed\u03ee\7"+
		"\u009c\2\2\u03ee\u03f5\3\2\2\2\u03ef\u03f0\7\u009b\2\2\u03f0\u03f2\7\u009c"+
		"\2\2\u03f1\u03f3\5,\27\2\u03f2\u03f1\3\2\2\2\u03f2\u03f3\3\2\2\2\u03f3"+
		"\u03f5\3\2\2\2\u03f4\u03eb\3\2\2\2\u03f4\u03ef\3\2\2\2\u03f5\u00a5\3\2"+
		"\2\2\u03f6\u03f7\7\u0099\2\2\u03f7\u03fc\5\u00a8U\2\u03f8\u03f9\7\u009e"+
		"\2\2\u03f9\u03fb\5\u00a8U\2\u03fa\u03f8\3\2\2\2\u03fb\u03fe\3\2\2\2\u03fc"+
		"\u03fa\3\2\2\2\u03fc\u03fd\3\2\2\2\u03fd\u03ff\3\2\2\2\u03fe\u03fc\3\2"+
		"\2\2\u03ff\u0400\7\u009a\2\2\u0400\u00a7\3\2\2\2\u0401\u0402\5\u0092J"+
		"\2\u0402\u0403\7\u00b9\2\2\u0403\u0404\5\u0092J\2\u0404\u00a9\3\2\2\2"+
		"\u0405\u0406\7\u0099\2\2\u0406\u040b\5\u0092J\2\u0407\u0408\7\u009e\2"+
		"\2\u0408\u040a\5\u0092J\2\u0409\u0407\3\2\2\2\u040a\u040d\3\2\2\2\u040b"+
		"\u0409\3\2\2\2\u040b\u040c\3\2\2\2\u040c\u040e\3\2\2\2\u040d\u040b\3\2"+
		"\2\2\u040e\u040f\7\u009a\2\2\u040f\u00ab\3\2\2\2\u0410\u0412\7\u0097\2"+
		"\2\u0411\u0413\5\u0090I\2\u0412\u0411\3\2\2\2\u0412\u0413\3\2\2\2\u0413"+
		"\u0414\3\2\2\2\u0414\u0415\7\u0098\2\2\u0415\u00ad\3\2\2\2\u0416\u0417"+
		"\7\u009b\2\2\u0417\u0418\5\u00b0Y\2\u0418\u0419\7\u009c\2\2\u0419\u00af"+
		"\3\2\2\2\u041a\u041b\7;\2\2\u041b\u041c\5\u00b4[\2\u041c\u041d\7=\2\2"+
		"\u041d\u041f\5\u00ba^\2\u041e\u0420\5\u00caf\2\u041f\u041e\3\2\2\2\u041f"+
		"\u0420\3\2\2\2\u0420\u0422\3\2\2\2\u0421\u0423\5\u00ccg\2\u0422\u0421"+
		"\3\2\2\2\u0422\u0423\3\2\2\2\u0423\u0425\3\2\2\2\u0424\u0426\5\u00dco"+
		"\2\u0425\u0424\3\2\2\2\u0425\u0426\3\2\2\2\u0426\u0428\3\2\2\2\u0427\u0429"+
		"\5\u00e6t\2\u0428\u0427\3\2\2\2\u0428\u0429\3\2\2\2\u0429\u042b\3\2\2"+
		"\2\u042a\u042c\5\u00e8u\2\u042b\u042a\3\2\2\2\u042b\u042c\3\2\2\2\u042c"+
		"\u042e\3\2\2\2\u042d\u042f\5\u00eex\2\u042e\u042d\3\2\2\2\u042e\u042f"+
		"\3\2\2\2\u042f\u0431\3\2\2\2\u0430\u0432\5\u00f0y\2\u0431\u0430\3\2\2"+
		"\2\u0431\u0432\3\2\2\2\u0432\u0434\3\2\2\2\u0433\u0435\5\u00f2z\2\u0434"+
		"\u0433\3\2\2\2\u0434\u0435\3\2\2\2\u0435\u0436\3\2\2\2\u0436\u0437\5\u00f4"+
		"{\2\u0437\u00b1\3\2\2\2\u0438\u0439\7;\2\2\u0439\u043a\5\u00bc_\2\u043a"+
		"\u043b\7=\2\2\u043b\u043d\5\u00ba^\2\u043c\u043e\5\u00ccg\2\u043d\u043c"+
		"\3\2\2\2\u043d\u043e\3\2\2\2\u043e\u0440\3\2\2\2\u043f\u0441\5\u00e8u"+
		"\2\u0440\u043f\3\2\2\2\u0440\u0441\3\2\2\2\u0441\u0443\3\2\2\2\u0442\u0444"+
		"\5\u00eex\2\u0443\u0442\3\2\2\2\u0443\u0444\3\2\2\2\u0444\u00b3\3\2\2"+
		"\2\u0445\u044a\5\u00b6\\\2\u0446\u0447\7\u009e\2\2\u0447\u0449\5\u00b6"+
		"\\\2\u0448\u0446\3\2\2\2\u0449\u044c\3\2\2\2\u044a\u0448\3\2\2\2\u044a"+
		"\u044b\3\2\2\2\u044b\u00b5\3\2\2\2\u044c\u044a\3\2\2\2\u044d\u044f\5\u00b8"+
		"]\2\u044e\u0450\5\u00fc\177\2\u044f\u044e\3\2\2\2\u044f\u0450\3\2\2\2"+
		"\u0450\u045b\3\2\2\2\u0451\u0453\5\u00c0a\2\u0452\u0454\5\u00fc\177\2"+
		"\u0453\u0452\3\2\2\2\u0453\u0454\3\2\2\2\u0454\u045b\3\2\2\2\u0455\u0456"+
		"\7\u0097\2\2\u0456\u0457\5\u00b2Z\2\u0457\u0458\7\u0098\2\2\u0458\u045b"+
		"\3\2\2\2\u0459\u045b\5\u00c2b\2\u045a\u044d\3\2\2\2\u045a\u0451\3\2\2"+
		"\2\u045a\u0455\3\2\2\2\u045a\u0459\3\2\2\2\u045b\u00b7\3\2\2\2\u045c\u0461"+
		"\5\u00fc\177\2\u045d\u045e\7\u009f\2\2\u045e\u0460\5\u00fc\177\2\u045f"+
		"\u045d\3\2\2\2\u0460\u0463\3\2\2\2\u0461\u045f\3\2\2\2\u0461\u0462\3\2"+
		"\2\2\u0462\u00b9\3\2\2\2\u0463\u0461\3\2\2\2\u0464\u0466\5\u00b8]\2\u0465"+
		"\u0467\5\u00fc\177\2\u0466\u0465\3\2\2\2\u0466\u0467\3\2\2\2\u0467\u046f"+
		"\3\2\2\2\u0468\u0469\7\u009e\2\2\u0469\u046b\5\u00b8]\2\u046a\u046c\5"+
		"\u00fc\177\2\u046b\u046a\3\2\2\2\u046b\u046c\3\2\2\2\u046c\u046e\3\2\2"+
		"\2\u046d\u0468\3\2\2\2\u046e\u0471\3\2\2\2\u046f\u046d\3\2\2\2\u046f\u0470"+
		"\3\2\2\2\u0470\u00bb\3\2\2\2\u0471\u046f\3\2\2\2\u0472\u0477\5\u00be`"+
		"\2\u0473\u0474\7\u009e\2\2\u0474\u0476\5\u00be`\2\u0475\u0473\3\2\2\2"+
		"\u0476\u0479\3\2\2\2\u0477\u0475\3\2\2\2\u0477\u0478\3\2\2\2\u0478\u00bd"+
		"\3\2\2\2\u0479\u0477\3\2\2\2\u047a\u047c\5\u00b8]\2\u047b\u047d\5\u00fc"+
		"\177\2\u047c\u047b\3\2\2\2\u047c\u047d\3\2\2\2\u047d\u0483\3\2\2\2\u047e"+
		"\u0480\5\u00c0a\2\u047f\u0481\5\u00fc\177\2\u0480\u047f\3\2\2\2\u0480"+
		"\u0481\3\2\2\2\u0481\u0483\3\2\2\2\u0482\u047a\3\2\2\2\u0482\u047e\3\2"+
		"\2\2\u0483\u00bf\3\2\2\2\u0484\u0485\7H\2\2\u0485\u0486\7\u0097\2\2\u0486"+
		"\u0487\5\u00b8]\2\u0487\u0488\7\u0098\2\2\u0488\u04ab\3\2\2\2\u0489\u048a"+
		"\7<\2\2\u048a\u048b\7\u0097\2\2\u048b\u04ab\7\u0098\2\2\u048c\u048d\7"+
		"<\2\2\u048d\u048e\7\u0097\2\2\u048e\u048f\5\u00b8]\2\u048f\u0490\7\u0098"+
		"\2\2\u0490\u04ab\3\2\2\2\u0491\u0492\7I\2\2\u0492\u0493\7\u0097\2\2\u0493"+
		"\u0494\5\u00b8]\2\u0494\u0495\7\u0098\2\2\u0495\u04ab\3\2\2\2\u0496\u0497"+
		"\7J\2\2\u0497\u0498\7\u0097\2\2\u0498\u0499\5\u00b8]\2\u0499\u049a\7\u0098"+
		"\2\2\u049a\u04ab\3\2\2\2\u049b\u049c\7K\2\2\u049c\u049d\7\u0097\2\2\u049d"+
		"\u049e\5\u00b8]\2\u049e\u049f\7\u0098\2\2\u049f\u04ab\3\2\2\2\u04a0\u04a1"+
		"\7L\2\2\u04a1\u04a2\7\u0097\2\2\u04a2\u04a3\5\u00b8]\2\u04a3\u04a4\7\u0098"+
		"\2\2\u04a4\u04ab\3\2\2\2\u04a5\u04a6\7_\2\2\u04a6\u04a7\7\u0097\2\2\u04a7"+
		"\u04a8\5\u00b8]\2\u04a8\u04a9\7\u0098\2\2\u04a9\u04ab\3\2\2\2\u04aa\u0484"+
		"\3\2\2\2\u04aa\u0489\3\2\2\2\u04aa\u048c\3\2\2\2\u04aa\u0491\3\2\2\2\u04aa"+
		"\u0496\3\2\2\2\u04aa\u049b\3\2\2\2\u04aa\u04a0\3\2\2\2\u04aa\u04a5\3\2"+
		"\2\2\u04ab\u00c1\3\2\2\2\u04ac\u04ad\7M\2\2\u04ad\u04af\5\u00b8]\2\u04ae"+
		"\u04b0\5\u00c4c\2\u04af\u04ae\3\2\2\2\u04b0\u04b1\3\2\2\2\u04b1\u04af"+
		"\3\2\2\2\u04b1\u04b2\3\2\2\2\u04b2\u04b4\3\2\2\2\u04b3\u04b5\5\u00c6d"+
		"\2\u04b4\u04b3\3\2\2\2\u04b4\u04b5\3\2\2\2\u04b5\u04b6\3\2\2\2\u04b6\u04b7"+
		"\7N\2\2\u04b7\u00c3\3\2\2\2\u04b8\u04b9\7\65\2\2\u04b9\u04ba\5\u00b8]"+
		"\2\u04ba\u04bb\7O\2\2\u04bb\u04bc\5\u00c8e\2\u04bc\u00c5\3\2\2\2\u04bd"+
		"\u04be\7\f\2\2\u04be\u04bf\5\u00c8e\2\u04bf\u00c7\3\2\2\2\u04c0\u04c5"+
		"\5\u00b8]\2\u04c1\u04c2\7\u009e\2\2\u04c2\u04c4\5\u00b8]\2\u04c3\u04c1"+
		"\3\2\2\2\u04c4\u04c7\3\2\2\2\u04c5\u04c3\3\2\2\2\u04c5\u04c6\3\2\2\2\u04c6"+
		"\u00c9\3\2\2\2\u04c7\u04c5\3\2\2\2\u04c8\u04c9\7?\2\2\u04c9\u04ca\7@\2"+
		"\2\u04ca\u04cb\5\u00fc\177\2\u04cb\u00cb\3\2\2\2\u04cc\u04cd\7A\2\2\u04cd"+
		"\u04ce\5\u00ceh\2\u04ce\u00cd\3\2\2\2\u04cf\u04d4\5\u00d0i\2\u04d0\u04d1"+
		"\7E\2\2\u04d1\u04d3\5\u00d0i\2\u04d2\u04d0\3\2\2\2\u04d3\u04d6\3\2\2\2"+
		"\u04d4\u04d2\3\2\2\2\u04d4\u04d5\3\2\2\2\u04d5\u04e2\3\2\2\2\u04d6\u04d4"+
		"\3\2\2\2\u04d7\u04dc\5\u00d0i\2\u04d8\u04d9\7F\2\2\u04d9\u04db\5\u00d0"+
		"i\2\u04da\u04d8\3\2\2\2\u04db\u04de\3\2\2\2\u04dc\u04da\3\2\2\2\u04dc"+
		"\u04dd\3\2\2\2\u04dd\u04e2\3\2\2\2\u04de\u04dc\3\2\2\2\u04df\u04e0\7G"+
		"\2\2\u04e0\u04e2\5\u00d0i\2\u04e1\u04cf\3\2\2\2\u04e1\u04d7\3\2\2\2\u04e1"+
		"\u04df\3\2\2\2\u04e2\u00cf\3\2\2\2\u04e3\u04e4\7\u0097\2\2\u04e4\u04e5"+
		"\5\u00ceh\2\u04e5\u04e6\7\u0098\2\2\u04e6\u04e9\3\2\2\2\u04e7\u04e9\5"+
		"\u00d2j\2\u04e8\u04e3\3\2\2\2\u04e8\u04e7\3\2\2\2\u04e9\u00d1\3\2\2\2"+
		"\u04ea\u04eb\5\u00b8]\2\u04eb\u04ec\5\u00d4k\2\u04ec\u04ed\5\u00d6l\2"+
		"\u04ed\u04f3\3\2\2\2\u04ee\u04ef\5\u00c0a\2\u04ef\u04f0\5\u00d4k\2\u04f0"+
		"\u04f1\5\u00d6l\2\u04f1\u04f3\3\2\2\2\u04f2\u04ea\3\2\2\2\u04f2\u04ee"+
		"\3\2\2\2\u04f3\u00d3\3\2\2\2\u04f4\u0504\7\u00a0\2\2\u04f5\u0504\7\u00aa"+
		"\2\2\u04f6\u0504\7\u00a2\2\2\u04f7\u0504\7\u00a1\2\2\u04f8\u04f9\7\u00a2"+
		"\2\2\u04f9\u0504\7\u00a0\2\2\u04fa\u04fb\7\u00a1\2\2\u04fb\u0504\7\u00a0"+
		"\2\2\u04fc\u0504\7\u00ab\2\2\u04fd\u0504\7P\2\2\u04fe\u0504\7Q\2\2\u04ff"+
		"\u0500\7G\2\2\u0500\u0504\7Q\2\2\u0501\u0504\7R\2\2\u0502\u0504\7S\2\2"+
		"\u0503\u04f4\3\2\2\2\u0503\u04f5\3\2\2\2\u0503\u04f6\3\2\2\2\u0503\u04f7"+
		"\3\2\2\2\u0503\u04f8\3\2\2\2\u0503\u04fa\3\2\2\2\u0503\u04fc\3\2\2\2\u0503"+
		"\u04fd\3\2\2\2\u0503\u04fe\3\2\2\2\u0503\u04ff\3\2\2\2\u0503\u0501\3\2"+
		"\2\2\u0503\u0502\3\2\2\2\u0504\u00d5\3\2\2\2\u0505\u0516\7\34\2\2\u0506"+
		"\u0516\7\u0094\2\2\u0507\u0516\7\u0091\2\2\u0508\u0516\7\u0092\2\2\u0509"+
		"\u0516\7\u0093\2\2\u050a\u0516\7\u0095\2\2\u050b\u0516\7\u008f\2\2\u050c"+
		"\u0516\7\u0090\2\2\u050d\u0516\5\u00f8}\2\u050e\u0516\5\u00dan\2\u050f"+
		"\u0510\7\u0097\2\2\u0510\u0511\5\u00b2Z\2\u0511\u0512\7\u0098\2\2\u0512"+
		"\u0516\3\2\2\2\u0513\u0516\5\u00d8m\2\u0514\u0516\5\u00f6|\2\u0515\u0505"+
		"\3\2\2\2\u0515\u0506\3\2\2\2\u0515\u0507\3\2\2\2\u0515\u0508\3\2\2\2\u0515"+
		"\u0509\3\2\2\2\u0515\u050a\3\2\2\2\u0515\u050b\3\2\2\2\u0515\u050c\3\2"+
		"\2\2\u0515\u050d\3\2\2\2\u0515\u050e\3\2\2\2\u0515\u050f\3\2\2\2\u0515"+
		"\u0513\3\2\2\2\u0515\u0514\3\2\2\2\u0516\u00d7\3\2\2\2\u0517\u0518\7\u0097"+
		"\2\2\u0518\u051d\5\u00d6l\2\u0519\u051a\7\u009e\2\2\u051a\u051c\5\u00d6"+
		"l\2\u051b\u0519\3\2\2\2\u051c\u051f\3\2\2\2\u051d\u051b\3\2\2\2\u051d"+
		"\u051e\3\2\2\2\u051e\u0520\3\2\2\2\u051f\u051d\3\2\2\2\u0520\u0521\7\u0098"+
		"\2\2\u0521\u00d9\3\2\2\2\u0522\u0523\5\u00fc\177\2\u0523\u0524\5\u00fa"+
		"~\2\u0524\u00db\3\2\2\2\u0525\u0526\7\67\2\2\u0526\u0527\7a\2\2\u0527"+
		"\u0528\7b\2\2\u0528\u052e\5\u00dep\2\u0529\u052a\7\67\2\2\u052a\u052e"+
		"\7g\2\2\u052b\u052c\7\67\2\2\u052c\u052e\5\u00ceh\2\u052d\u0525\3\2\2"+
		"\2\u052d\u0529\3\2\2\2\u052d\u052b\3\2\2\2\u052e\u00dd\3\2\2\2\u052f\u0534"+
		"\5\u00e0q\2\u0530\u0531\7\u00ad\2\2\u0531\u0533\5\u00e0q\2\u0532\u0530"+
		"\3\2\2\2\u0533\u0536\3\2\2\2\u0534\u0532\3\2\2\2\u0534\u0535\3\2\2\2\u0535"+
		"\u00df\3\2\2\2\u0536\u0534\3\2\2\2\u0537\u0538\5\u00fc\177\2\u0538\u0539"+
		"\5\u00e4s\2\u0539\u053a\5\u00e2r\2\u053a\u00e1\3\2\2\2\u053b\u0548\5\u00fc"+
		"\177\2\u053c\u053d\7\u0097\2\2\u053d\u0542\5\u00fc\177\2\u053e\u053f\7"+
		"\u009e\2\2\u053f\u0541\5\u00fc\177\2\u0540\u053e\3\2\2\2\u0541\u0544\3"+
		"\2\2\2\u0542\u0540\3\2\2\2\u0542\u0543\3\2\2\2\u0543\u0545\3\2\2\2\u0544"+
		"\u0542\3\2\2\2\u0545\u0546\7\u0097\2\2\u0546\u0548\3\2\2\2\u0547\u053b"+
		"\3\2\2\2\u0547\u053c\3\2\2\2\u0548\u00e3\3\2\2\2\u0549\u054a\t\16\2\2"+
		"\u054a\u00e5\3\2\2\2\u054b\u054c\7Y\2\2\u054c\u054d\7C\2\2\u054d\u0550"+
		"\5\u00b4[\2\u054e\u054f\7]\2\2\u054f\u0551\5\u00ceh\2\u0550\u054e\3\2"+
		"\2\2\u0550\u0551\3\2\2\2\u0551\u056f\3\2\2\2\u0552\u0553\7Y\2\2\u0553"+
		"\u0554\7C\2\2\u0554\u0555\7^\2\2\u0555\u0556\7\u0097\2\2\u0556\u055b\5"+
		"\u00b8]\2\u0557\u0558\7\u009e\2\2\u0558\u055a\5\u00b8]\2\u0559\u0557\3"+
		"\2\2\2\u055a\u055d\3\2\2\2\u055b\u0559\3\2\2\2\u055b\u055c\3\2\2\2\u055c"+
		"\u055e\3\2\2\2\u055d\u055b\3\2\2\2\u055e\u055f\7\u0098\2\2\u055f\u056f"+
		"\3\2\2\2\u0560\u0561\7Y\2\2\u0561\u0562\7C\2\2\u0562\u0563\7i\2\2\u0563"+
		"\u0564\7\u0097\2\2\u0564\u0569\5\u00b8]\2\u0565\u0566\7\u009e\2\2\u0566"+
		"\u0568\5\u00b8]\2\u0567\u0565\3\2\2\2\u0568\u056b\3\2\2\2\u0569\u0567"+
		"\3\2\2\2\u0569\u056a\3\2\2\2\u056a\u056c\3\2\2\2\u056b\u0569\3\2\2\2\u056c"+
		"\u056d\7\u0098\2\2\u056d\u056f\3\2\2\2\u056e\u054b\3\2\2\2\u056e\u0552"+
		"\3\2\2\2\u056e\u0560\3\2\2\2\u056f\u00e7\3\2\2\2\u0570\u0571\7B\2\2\u0571"+
		"\u0572\7C\2\2\u0572\u0573\5\u00eav\2\u0573\u00e9\3\2\2\2\u0574\u0579\5"+
		"\u00ecw\2\u0575\u0576\7\u009e\2\2\u0576\u0578\5\u00ecw\2\u0577\u0575\3"+
		"\2\2\2\u0578\u057b\3\2\2\2\u0579\u0577\3\2\2\2\u0579\u057a\3\2\2\2\u057a"+
		"\u00eb\3\2\2\2\u057b\u0579\3\2\2\2\u057c\u057e\5\u00b8]\2\u057d\u057f"+
		"\t\17\2\2\u057e\u057d\3\2\2\2\u057e\u057f\3\2\2\2\u057f\u0582\3\2\2\2"+
		"\u0580\u0581\7V\2\2\u0581\u0583\t\20\2\2\u0582\u0580\3\2\2\2\u0582\u0583"+
		"\3\2\2\2\u0583\u0586\3\2\2\2\u0584\u0586\5\u00c0a\2\u0585\u057c\3\2\2"+
		"\2\u0585\u0584\3\2\2\2\u0586\u00ed\3\2\2\2\u0587\u0588\7D\2\2\u0588\u058c"+
		"\7\u0091\2\2\u0589\u058a\7D\2\2\u058a\u058c\5\u00f6|\2\u058b\u0587\3\2"+
		"\2\2\u058b\u0589\3\2\2\2\u058c\u00ef\3\2\2\2\u058d\u058e\7`\2\2\u058e"+
		"\u0592\7\u0091\2\2\u058f\u0590\7`\2\2\u0590\u0592\5\u00f6|\2\u0591\u058d"+
		"\3\2\2\2\u0591\u058f\3\2\2\2\u0592\u00f1\3\2\2\2\u0593\u0594\7Z\2\2\u0594"+
		"\u0595\7[\2\2\u0595\u00f3\3\2\2\2\u0596\u0597\7\21\2\2\u0597\u0599\t\21"+
		"\2\2\u0598\u0596\3\2\2\2\u0599\u059c\3\2\2\2\u059a\u0598\3\2\2\2\u059a"+
		"\u059b\3\2\2\2\u059b\u00f5\3\2\2\2\u059c\u059a\3\2\2\2\u059d\u059e\7\u00a7"+
		"\2\2\u059e\u059f\5\u0092J\2\u059f\u00f7\3\2\2\2\u05a0\u05e2\7j\2\2\u05a1"+
		"\u05e2\7k\2\2\u05a2\u05e2\7l\2\2\u05a3\u05e2\7m\2\2\u05a4\u05e2\7n\2\2"+
		"\u05a5\u05e2\7o\2\2\u05a6\u05e2\7p\2\2\u05a7\u05e2\7q\2\2\u05a8\u05e2"+
		"\7r\2\2\u05a9\u05e2\7s\2\2\u05aa\u05e2\7t\2\2\u05ab\u05ac\7u\2\2\u05ac"+
		"\u05ad\7\u00a7\2\2\u05ad\u05e2\5\u00fa~\2\u05ae\u05af\7v\2\2\u05af\u05b0"+
		"\7\u00a7\2\2\u05b0\u05e2\5\u00fa~\2\u05b1\u05b2\7w\2\2\u05b2\u05b3\7\u00a7"+
		"\2\2\u05b3\u05e2\5\u00fa~\2\u05b4\u05b5\7x\2\2\u05b5\u05b6\7\u00a7\2\2"+
		"\u05b6\u05e2\5\u00fa~\2\u05b7\u05b8\7y\2\2\u05b8\u05b9\7\u00a7\2\2\u05b9"+
		"\u05e2\5\u00fa~\2\u05ba\u05bb\7z\2\2\u05bb\u05bc\7\u00a7\2\2\u05bc\u05e2"+
		"\5\u00fa~\2\u05bd\u05e2\7{\2\2\u05be\u05e2\7|\2\2\u05bf\u05e2\7}\2\2\u05c0"+
		"\u05c1\7~\2\2\u05c1\u05c2\7\u00a7\2\2\u05c2\u05e2\5\u00fa~\2\u05c3\u05c4"+
		"\7\177\2\2\u05c4\u05c5\7\u00a7\2\2\u05c5\u05e2\5\u00fa~\2\u05c6\u05e2"+
		"\7\u0080\2\2\u05c7\u05e2\7\u0081\2\2\u05c8\u05e2\7\u0082\2\2\u05c9\u05ca"+
		"\7\u0083\2\2\u05ca\u05cb\7\u00a7\2\2\u05cb\u05e2\5\u00fa~\2\u05cc\u05cd"+
		"\7\u0084\2\2\u05cd\u05ce\7\u00a7\2\2\u05ce\u05e2\5\u00fa~\2\u05cf\u05e2"+
		"\7\u0085\2\2\u05d0\u05e2\7\u0086\2\2\u05d1\u05e2\7\u0087\2\2\u05d2\u05d3"+
		"\7\u0088\2\2\u05d3\u05d4\7\u00a7\2\2\u05d4\u05e2\5\u00fa~\2\u05d5\u05d6"+
		"\7\u0089\2\2\u05d6\u05d7\7\u00a7\2\2\u05d7\u05e2\5\u00fa~\2\u05d8\u05e2"+
		"\7\u008a\2\2\u05d9\u05e2\7\u008b\2\2\u05da\u05e2\7\u008c\2\2\u05db\u05dc"+
		"\7\u008d\2\2\u05dc\u05dd\7\u00a7\2\2\u05dd\u05e2\5\u00fa~\2\u05de\u05df"+
		"\7\u008e\2\2\u05df\u05e0\7\u00a7\2\2\u05e0\u05e2\5\u00fa~\2\u05e1\u05a0"+
		"\3\2\2\2\u05e1\u05a1\3\2\2\2\u05e1\u05a2\3\2\2\2\u05e1\u05a3\3\2\2\2\u05e1"+
		"\u05a4\3\2\2\2\u05e1\u05a5\3\2\2\2\u05e1\u05a6\3\2\2\2\u05e1\u05a7\3\2"+
		"\2\2\u05e1\u05a8\3\2\2\2\u05e1\u05a9\3\2\2\2\u05e1\u05aa\3\2\2\2\u05e1"+
		"\u05ab\3\2\2\2\u05e1\u05ae\3\2\2\2\u05e1\u05b1\3\2\2\2\u05e1\u05b4\3\2"+
		"\2\2\u05e1\u05b7\3\2\2\2\u05e1\u05ba\3\2\2\2\u05e1\u05bd\3\2\2\2\u05e1"+
		"\u05be\3\2\2\2\u05e1\u05bf\3\2\2\2\u05e1\u05c0\3\2\2\2\u05e1\u05c3\3\2"+
		"\2\2\u05e1\u05c6\3\2\2\2\u05e1\u05c7\3\2\2\2\u05e1\u05c8\3\2\2\2\u05e1"+
		"\u05c9\3\2\2\2\u05e1\u05cc\3\2\2\2\u05e1\u05cf\3\2\2\2\u05e1\u05d0\3\2"+
		"\2\2\u05e1\u05d1\3\2\2\2\u05e1\u05d2\3\2\2\2\u05e1\u05d5\3\2\2\2\u05e1"+
		"\u05d8\3\2\2\2\u05e1\u05d9\3\2\2\2\u05e1\u05da\3\2\2\2\u05e1\u05db\3\2"+
		"\2\2\u05e1\u05de\3\2\2\2\u05e2\u00f9\3\2\2\2\u05e3\u05e5\t\b\2\2\u05e4"+
		"\u05e3\3\2\2\2\u05e4\u05e5\3\2\2\2\u05e5\u05e6\3\2\2\2\u05e6\u05e7\7\u0091"+
		"\2\2\u05e7\u00fb\3\2\2\2\u05e8\u05e9\5\u00fe\u0080\2\u05e9\u00fd\3\2\2"+
		"\2\u05ea\u05eb\t\22\2\2\u05eb\u00ff\3\2\2\2\u05ec\u05ed\t\23\2\2\u05ed"+
		"\u0101\3\2\2\2\u0093\u010c\u011c\u0123\u012a\u012e\u0134\u0138\u0140\u0149"+
		"\u0150\u0159\u0160\u0169\u0170\u0176\u017a\u018f\u0198\u019c\u01a2\u01b2"+
		"\u01ba\u01bf\u01ca\u01d0\u01d8\u01dc\u01de\u01e7\u01f0\u01f5\u01f9\u01fd"+
		"\u0201\u0203\u020b\u0214\u021a\u0225\u022f\u0232\u0236\u023b\u0245\u024d"+
		"\u0250\u0253\u025b\u0266\u0280\u0287\u0290\u029e\u02a4\u02a7\u02ae\u02c5"+
		"\u02c8\u02cb\u02cf\u02f0\u02fc\u0307\u030c\u0311\u0316\u031d\u032a\u032e"+
		"\u0332\u0334\u0338\u034a\u035b\u036b\u0371\u0393\u039f\u03a1\u03b1\u03b6"+
		"\u03bd\u03c3\u03c6\u03cb\u03d5\u03dc\u03e4\u03f2\u03f4\u03fc\u040b\u0412"+
		"\u041f\u0422\u0425\u0428\u042b\u042e\u0431\u0434\u043d\u0440\u0443\u044a"+
		"\u044f\u0453\u045a\u0461\u0466\u046b\u046f\u0477\u047c\u0480\u0482\u04aa"+
		"\u04b1\u04b4\u04c5\u04d4\u04dc\u04e1\u04e8\u04f2\u0503\u0515\u051d\u052d"+
		"\u0534\u0542\u0547\u0550\u055b\u0569\u056e\u0579\u057e\u0582\u0585\u058b"+
		"\u0591\u059a\u05e1\u05e4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}