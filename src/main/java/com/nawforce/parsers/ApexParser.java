// Generated from /Users/kevin/Projects/ApexLink/src/main/antlr4/io/github/nawforce/apexlink/antlr/ApexParser.g4 by ANTLR 4.7.2
package com.nawforce.parsers;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

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
		RULE_typeBound = 6, RULE_enumDeclaration = 7, RULE_enumConstants = 8, 
		RULE_enumConstant = 9, RULE_enumBodyDeclarations = 10, RULE_interfaceDeclaration = 11, 
		RULE_typeList = 12, RULE_classBody = 13, RULE_interfaceBody = 14, RULE_classBodyDeclaration = 15, 
		RULE_memberDeclaration = 16, RULE_methodModifier = 17, RULE_methodDeclaration = 18, 
		RULE_constructorDeclaration = 19, RULE_fieldDeclaration = 20, RULE_propertyDeclaration = 21, 
		RULE_propertyBodyDeclaration = 22, RULE_interfaceBodyDeclaration = 23, 
		RULE_interfaceMemberDeclaration = 24, RULE_constDeclaration = 25, RULE_constantDeclarator = 26, 
		RULE_interfaceMethodDeclaration = 27, RULE_variableDeclarators = 28, RULE_variableDeclarator = 29, 
		RULE_variableInitializer = 30, RULE_arrayInitializer = 31, RULE_typeRef = 32, 
		RULE_arraySubscripts = 33, RULE_classOrInterfaceType = 34, RULE_primitiveType = 35, 
		RULE_typeArguments = 36, RULE_qualifiedNameList = 37, RULE_formalParameters = 38, 
		RULE_formalParameterList = 39, RULE_formalParameter = 40, RULE_qualifiedName = 41, 
		RULE_literal = 42, RULE_annotation = 43, RULE_elementValuePairs = 44, 
		RULE_elementValuePair = 45, RULE_elementValue = 46, RULE_elementValueArrayInitializer = 47, 
		RULE_block = 48, RULE_localVariableDeclarationStatement = 49, RULE_localVariableDeclaration = 50, 
		RULE_statement = 51, RULE_ifStatement = 52, RULE_forStatement = 53, RULE_whileStatement = 54, 
		RULE_doWhileStatement = 55, RULE_tryStatement = 56, RULE_returnStatement = 57, 
		RULE_throwStatement = 58, RULE_breakStatement = 59, RULE_continueStatement = 60, 
		RULE_insertStatement = 61, RULE_updateStatement = 62, RULE_deleteStatement = 63, 
		RULE_undeleteStatement = 64, RULE_upsertStatement = 65, RULE_mergeStatement = 66, 
		RULE_runAsStatement = 67, RULE_emptyStatement = 68, RULE_expressionStatement = 69, 
		RULE_idStatement = 70, RULE_bangStatement = 71, RULE_propertyBlock = 72, 
		RULE_getter = 73, RULE_setter = 74, RULE_catchClause = 75, RULE_catchType = 76, 
		RULE_finallyBlock = 77, RULE_forControl = 78, RULE_forInit = 79, RULE_enhancedForControl = 80, 
		RULE_forUpdate = 81, RULE_parExpression = 82, RULE_expressionList = 83, 
		RULE_expression = 84, RULE_primary = 85, RULE_creator = 86, RULE_createdName = 87, 
		RULE_idCreatedNamePair = 88, RULE_innerCreator = 89, RULE_arrayCreatorRest = 90, 
		RULE_mapCreatorRest = 91, RULE_mapCreatorRestPair = 92, RULE_setCreatorRest = 93, 
		RULE_literalOrExpression = 94, RULE_idOrExpression = 95, RULE_classCreatorRest = 96, 
		RULE_explicitGenericInvocation = 97, RULE_nonWildcardTypeArguments = 98, 
		RULE_typeArgumentsOrDiamond = 99, RULE_nonWildcardTypeArgumentsOrDiamond = 100, 
		RULE_superSuffix = 101, RULE_explicitGenericInvocationSuffix = 102, RULE_arguments = 103, 
		RULE_soqlLiteral = 104, RULE_withSharing = 105, RULE_withoutSharing = 106, 
		RULE_id = 107;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "typeDeclaration", "modifier", "classOrInterfaceModifier", 
			"variableModifier", "classDeclaration", "typeBound", "enumDeclaration", 
			"enumConstants", "enumConstant", "enumBodyDeclarations", "interfaceDeclaration", 
			"typeList", "classBody", "interfaceBody", "classBodyDeclaration", "memberDeclaration", 
			"methodModifier", "methodDeclaration", "constructorDeclaration", "fieldDeclaration", 
			"propertyDeclaration", "propertyBodyDeclaration", "interfaceBodyDeclaration", 
			"interfaceMemberDeclaration", "constDeclaration", "constantDeclarator", 
			"interfaceMethodDeclaration", "variableDeclarators", "variableDeclarator", 
			"variableInitializer", "arrayInitializer", "typeRef", "arraySubscripts", 
			"classOrInterfaceType", "primitiveType", "typeArguments", "qualifiedNameList", 
			"formalParameters", "formalParameterList", "formalParameter", "qualifiedName", 
			"literal", "annotation", "elementValuePairs", "elementValuePair", "elementValue", 
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
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << CLASS) | (1L << ENUM) | (1L << FINAL) | (1L << INTERFACE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (SEMI - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(216);
				typeDeclaration();
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(222);
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
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(224);
					classOrInterfaceModifier();
					}
					}
					setState(229);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(230);
				classDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(234);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(231);
					classOrInterfaceModifier();
					}
					}
					setState(236);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(237);
				enumDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
					{
					{
					setState(238);
					classOrInterfaceModifier();
					}
					}
					setState(243);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(244);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(245);
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
			setState(250);
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
				setState(248);
				classOrInterfaceModifier();
				}
				break;
			case NATIVE:
			case SYNCHRONIZED:
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(249);
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
			setState(268);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(252);
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
				setState(266);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PUBLIC:
					{
					setState(253);
					match(PUBLIC);
					}
					break;
				case PROTECTED:
					{
					setState(254);
					match(PROTECTED);
					}
					break;
				case PRIVATE:
					{
					setState(255);
					match(PRIVATE);
					}
					break;
				case STATIC:
					{
					setState(256);
					match(STATIC);
					}
					break;
				case ABSTRACT:
					{
					setState(257);
					match(ABSTRACT);
					}
					break;
				case FINAL:
					{
					setState(258);
					match(FINAL);
					}
					break;
				case GLOBAL:
					{
					setState(259);
					match(GLOBAL);
					}
					break;
				case WEBSERVICE:
					{
					setState(260);
					match(WEBSERVICE);
					}
					break;
				case OVERRIDE:
					{
					setState(261);
					match(OVERRIDE);
					}
					break;
				case VIRTUAL:
					{
					setState(262);
					match(VIRTUAL);
					}
					break;
				case TESTMETHOD:
					{
					setState(263);
					match(TESTMETHOD);
					}
					break;
				case WITH:
				case WITHSHARING:
					{
					setState(264);
					withSharing();
					}
					break;
				case WITHOUT:
				case WITHOUTSHARING:
					{
					setState(265);
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
			setState(273);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				match(FINAL);
				}
				break;
			case TRANSIENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(271);
				match(TRANSIENT);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 3);
				{
				setState(272);
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
			setState(275);
			match(CLASS);
			setState(276);
			id();
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(277);
				match(EXTENDS);
				setState(278);
				typeRef();
				}
			}

			setState(283);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(281);
				match(IMPLEMENTS);
				setState(282);
				typeList();
				}
			}

			setState(285);
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

	public static class TypeBoundContext extends ParserRuleContext {
		public List<TypeRefContext> typeRef() {
			return getRuleContexts(TypeRefContext.class);
		}
		public TypeRefContext typeRef(int i) {
			return getRuleContext(TypeRefContext.class,i);
		}
		public List<TerminalNode> BITAND() { return getTokens(ApexParser.BITAND); }
		public TerminalNode BITAND(int i) {
			return getToken(ApexParser.BITAND, i);
		}
		public TypeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeBound; }
	}

	public final TypeBoundContext typeBound() throws RecognitionException {
		TypeBoundContext _localctx = new TypeBoundContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeBound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			typeRef();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITAND) {
				{
				{
				setState(288);
				match(BITAND);
				setState(289);
				typeRef();
				}
				}
				setState(294);
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
		enterRule(_localctx, 14, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			match(ENUM);
			setState(296);
			id();
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPLEMENTS) {
				{
				setState(297);
				match(IMPLEMENTS);
				setState(298);
				typeList();
				}
			}

			setState(301);
			match(LBRACE);
			setState(303);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(302);
				enumConstants();
				}
			}

			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(305);
				match(COMMA);
				}
			}

			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(308);
				enumBodyDeclarations();
				}
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
		enterRule(_localctx, 16, RULE_enumConstants);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			enumConstant();
			setState(318);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(314);
					match(COMMA);
					setState(315);
					enumConstant();
					}
					} 
				}
				setState(320);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
		enterRule(_localctx, 18, RULE_enumConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(321);
				annotation();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(327);
			id();
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(328);
				arguments();
				}
			}

			setState(332);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(331);
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
		enterRule(_localctx, 20, RULE_enumBodyDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			match(SEMI);
			setState(338);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(335);
				classBodyDeclaration();
				}
				}
				setState(340);
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
		enterRule(_localctx, 22, RULE_interfaceDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(INTERFACE);
			setState(342);
			id();
			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(343);
				match(EXTENDS);
				setState(344);
				typeList();
				}
			}

			setState(347);
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
		enterRule(_localctx, 24, RULE_typeList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			typeRef();
			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(350);
				match(COMMA);
				setState(351);
				typeRef();
				}
				}
				setState(356);
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
		enterRule(_localctx, 26, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			match(LBRACE);
			setState(361);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (LBRACE - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(358);
				classBodyDeclaration();
				}
				}
				setState(363);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(364);
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
		enterRule(_localctx, 28, RULE_interfaceBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(LBRACE);
			setState(370);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << BOOLEAN) | (1L << CLASS) | (1L << DOUBLE) | (1L << ENUM) | (1L << FINAL) | (1L << FOR) | (1L << INTERFACE) | (1L << LONG) | (1L << NATIVE) | (1L << NEW) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << VOID) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITH - 64)) | (1L << (WITHOUT - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (SEMI - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				{
				setState(367);
				interfaceBodyDeclaration();
				}
				}
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(373);
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
		enterRule(_localctx, 30, RULE_classBodyDeclaration);
		int _la;
		try {
			int _alt;
			setState(387);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(375);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(377);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(376);
					match(STATIC);
					}
				}

				setState(379);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(383);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
					_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
				}
				setState(386);
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
		enterRule(_localctx, 32, RULE_memberDeclaration);
		try {
			setState(396);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(389);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(390);
				fieldDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(391);
				constructorDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(392);
				interfaceDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(393);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(394);
				enumDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(395);
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
		enterRule(_localctx, 34, RULE_methodModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
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
		enterRule(_localctx, 36, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AT) {
				{
				setState(400);
				annotation();
				}
			}

			setState(406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0)) {
				{
				{
				setState(403);
				methodModifier();
				}
				}
				setState(408);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(411);
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
				setState(409);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(410);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(413);
			id();
			setState(414);
			formalParameters();
			setState(417);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				setState(415);
				block();
				}
				break;
			case SEMI:
				{
				setState(416);
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
		enterRule(_localctx, 38, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			qualifiedName();
			setState(420);
			formalParameters();
			setState(421);
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
		enterRule(_localctx, 40, RULE_fieldDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			typeRef();
			setState(424);
			variableDeclarators();
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
		enterRule(_localctx, 42, RULE_propertyDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(427);
			typeRef();
			setState(428);
			variableDeclarators();
			setState(429);
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
		enterRule(_localctx, 44, RULE_propertyBodyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
			match(LBRACE);
			setState(432);
			propertyBlock();
			setState(434);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE) | (1L << GET) | (1L << SET))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				setState(433);
				propertyBlock();
				}
			}

			setState(436);
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
		enterRule(_localctx, 46, RULE_interfaceBodyDeclaration);
		try {
			int _alt;
			setState(446);
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
				setState(441);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(438);
						modifier();
						}
						} 
					}
					setState(443);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				}
				setState(444);
				interfaceMemberDeclaration();
				}
				break;
			case SEMI:
				enterOuterAlt(_localctx, 2);
				{
				setState(445);
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
		enterRule(_localctx, 48, RULE_interfaceMemberDeclaration);
		try {
			setState(453);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(448);
				constDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(449);
				interfaceMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(450);
				interfaceDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(451);
				classDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(452);
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
		enterRule(_localctx, 50, RULE_constDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			typeRef();
			setState(456);
			constantDeclarator();
			setState(461);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(457);
				match(COMMA);
				setState(458);
				constantDeclarator();
				}
				}
				setState(463);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(464);
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
		enterRule(_localctx, 52, RULE_constantDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(466);
			id();
			setState(471);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACK) {
				{
				{
				setState(467);
				match(LBRACK);
				setState(468);
				match(RBRACK);
				}
				}
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(474);
			match(ASSIGN);
			setState(475);
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
		enterRule(_localctx, 54, RULE_interfaceMethodDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(479);
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
				setState(477);
				typeRef();
				}
				break;
			case VOID:
				{
				setState(478);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(481);
			id();
			setState(482);
			formalParameters();
			setState(483);
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
		enterRule(_localctx, 56, RULE_variableDeclarators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			variableDeclarator();
			setState(490);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(486);
				match(COMMA);
				setState(487);
				variableDeclarator();
				}
				}
				setState(492);
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
		enterRule(_localctx, 58, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493);
			id();
			setState(496);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(494);
				match(ASSIGN);
				setState(495);
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
		enterRule(_localctx, 60, RULE_variableInitializer);
		try {
			setState(500);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(498);
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
				setState(499);
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
		enterRule(_localctx, 62, RULE_arrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(502);
			match(LBRACE);
			setState(514);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(503);
				variableInitializer();
				setState(508);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(504);
						match(COMMA);
						setState(505);
						variableInitializer();
						}
						} 
					}
					setState(510);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				setState(512);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(511);
					match(COMMA);
					}
				}

				}
			}

			setState(516);
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
		enterRule(_localctx, 64, RULE_typeRef);
		try {
			setState(524);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				classOrInterfaceType();
				setState(519);
				arraySubscripts();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(521);
				primitiveType();
				setState(522);
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
		enterRule(_localctx, 66, RULE_arraySubscripts);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(530);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(526);
					match(LBRACK);
					setState(527);
					match(RBRACK);
					}
					} 
				}
				setState(532);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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
		enterRule(_localctx, 68, RULE_classOrInterfaceType);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
			id();
			setState(535);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(534);
				typeArguments();
				}
				break;
			}
			setState(544);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(537);
					match(DOT);
					setState(538);
					id();
					setState(540);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
					case 1:
						{
						setState(539);
						typeArguments();
						}
						break;
					}
					}
					} 
				}
				setState(546);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
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
		enterRule(_localctx, 70, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
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
		enterRule(_localctx, 72, RULE_typeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(549);
			match(LT);
			setState(550);
			typeList();
			setState(551);
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

	public static class QualifiedNameListContext extends ParserRuleContext {
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApexParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApexParser.COMMA, i);
		}
		public QualifiedNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedNameList; }
	}

	public final QualifiedNameListContext qualifiedNameList() throws RecognitionException {
		QualifiedNameListContext _localctx = new QualifiedNameListContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_qualifiedNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(553);
			qualifiedName();
			setState(558);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(554);
				match(COMMA);
				setState(555);
				qualifiedName();
				}
				}
				setState(560);
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
		enterRule(_localctx, 76, RULE_formalParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(561);
			match(LPAREN);
			setState(563);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << TRANSIENT) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(562);
				formalParameterList();
				}
			}

			setState(565);
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
		enterRule(_localctx, 78, RULE_formalParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(567);
			formalParameter();
			setState(572);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(568);
				match(COMMA);
				setState(569);
				formalParameter();
				}
				}
				setState(574);
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
		enterRule(_localctx, 80, RULE_formalParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(578);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(575);
				variableModifier();
				}
				}
				setState(580);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(581);
			typeRef();
			setState(582);
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
		enterRule(_localctx, 82, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584);
			id();
			setState(589);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(585);
				match(DOT);
				setState(586);
				id();
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
		enterRule(_localctx, 84, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(592);
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
		enterRule(_localctx, 86, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			match(AT);
			setState(595);
			qualifiedName();
			setState(602);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(596);
				match(LPAREN);
				setState(599);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
				case 1:
					{
					setState(597);
					elementValuePairs();
					}
					break;
				case 2:
					{
					setState(598);
					elementValue();
					}
					break;
				}
				setState(601);
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
		enterRule(_localctx, 88, RULE_elementValuePairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			elementValuePair();
			setState(611);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (COMMA - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(606);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(605);
					match(COMMA);
					}
				}

				setState(608);
				elementValuePair();
				}
				}
				setState(613);
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
		enterRule(_localctx, 90, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			id();
			setState(615);
			match(ASSIGN);
			setState(616);
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
		enterRule(_localctx, 92, RULE_elementValue);
		try {
			setState(621);
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
				setState(618);
				expression(0);
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(619);
				annotation();
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(620);
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
		enterRule(_localctx, 94, RULE_elementValueArrayInitializer);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(623);
			match(LBRACE);
			setState(632);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACE - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
				{
				setState(624);
				elementValue();
				setState(629);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(625);
						match(COMMA);
						setState(626);
						elementValue();
						}
						} 
					}
					setState(631);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
				}
				}
			}

			setState(635);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(634);
				match(COMMA);
				}
			}

			setState(637);
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
		enterRule(_localctx, 96, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(639);
			match(LBRACE);
			setState(643);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 2)) & ~0x3f) == 0 && ((1L << (_la - 2)) & ((1L << (BOOLEAN - 2)) | (1L << (BREAK - 2)) | (1L << (CONTINUE - 2)) | (1L << (DO - 2)) | (1L << (DOUBLE - 2)) | (1L << (FINAL - 2)) | (1L << (FOR - 2)) | (1L << (IF - 2)) | (1L << (LONG - 2)) | (1L << (NEW - 2)) | (1L << (RETURN - 2)) | (1L << (SUPER - 2)) | (1L << (THIS - 2)) | (1L << (THROW - 2)) | (1L << (TRANSIENT - 2)) | (1L << (TRY - 2)) | (1L << (VOID - 2)) | (1L << (WHILE - 2)) | (1L << (SELECT - 2)) | (1L << (INSERT - 2)) | (1L << (UPSERT - 2)) | (1L << (UPDATE - 2)) | (1L << (DELETE - 2)) | (1L << (UNDELETE - 2)) | (1L << (MERGE - 2)) | (1L << (GET - 2)) | (1L << (SET - 2)) | (1L << (BLOB - 2)) | (1L << (DATE - 2)) | (1L << (DATETIME - 2)) | (1L << (DECIMAL - 2)) | (1L << (ID - 2)) | (1L << (INTEGER - 2)) | (1L << (OBJECT - 2)) | (1L << (STRING - 2)))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (TIME - 66)) | (1L << (RUNAS - 66)) | (1L << (WITHSHARING - 66)) | (1L << (WITHOUTSHARING - 66)) | (1L << (IntegerLiteral - 66)) | (1L << (NumberLiteral - 66)) | (1L << (BooleanLiteral - 66)) | (1L << (StringLiteral - 66)) | (1L << (NullLiteral - 66)) | (1L << (LPAREN - 66)) | (1L << (LBRACE - 66)) | (1L << (LBRACK - 66)) | (1L << (SEMI - 66)) | (1L << (LT - 66)) | (1L << (BANG - 66)) | (1L << (TILDE - 66)) | (1L << (INC - 66)) | (1L << (DEC - 66)) | (1L << (ADD - 66)) | (1L << (SUB - 66)) | (1L << (Identifier - 66)) | (1L << (AT - 66)) | (1L << (BANG_STATEMENT - 66)))) != 0)) {
				{
				{
				setState(640);
				statement();
				}
				}
				setState(645);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(646);
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
		enterRule(_localctx, 98, RULE_localVariableDeclarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(648);
			localVariableDeclaration();
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
		enterRule(_localctx, 100, RULE_localVariableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(654);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(651);
				variableModifier();
				}
				}
				setState(656);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(657);
			typeRef();
			setState(658);
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
		enterRule(_localctx, 102, RULE_statement);
		try {
			setState(682);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(660);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(661);
				localVariableDeclarationStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(662);
				ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(663);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(664);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(665);
				doWhileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(666);
				tryStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(667);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(668);
				throwStatement();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(669);
				breakStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(670);
				continueStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(671);
				insertStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(672);
				updateStatement();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(673);
				deleteStatement();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(674);
				undeleteStatement();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(675);
				upsertStatement();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(676);
				mergeStatement();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(677);
				runAsStatement();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(678);
				emptyStatement();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(679);
				expressionStatement();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(680);
				idStatement();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(681);
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
		enterRule(_localctx, 104, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(684);
			match(IF);
			setState(685);
			parExpression();
			setState(686);
			statement();
			setState(689);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				{
				setState(687);
				match(ELSE);
				setState(688);
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
		enterRule(_localctx, 106, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(691);
			match(FOR);
			setState(692);
			match(LPAREN);
			setState(693);
			forControl();
			setState(694);
			match(RPAREN);
			setState(695);
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
		enterRule(_localctx, 108, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(697);
			match(WHILE);
			setState(698);
			parExpression();
			setState(699);
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
		enterRule(_localctx, 110, RULE_doWhileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(701);
			match(DO);
			setState(702);
			statement();
			setState(703);
			match(WHILE);
			setState(704);
			parExpression();
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
		enterRule(_localctx, 112, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			match(TRY);
			setState(708);
			block();
			setState(718);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CATCH:
				{
				setState(710); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(709);
					catchClause();
					}
					}
					setState(712); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CATCH );
				setState(715);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FINALLY) {
					{
					setState(714);
					finallyBlock();
					}
				}

				}
				break;
			case FINALLY:
				{
				setState(717);
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
		enterRule(_localctx, 114, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(720);
			match(RETURN);
			setState(722);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(721);
				expression(0);
				}
			}

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
		enterRule(_localctx, 116, RULE_throwStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(726);
			match(THROW);
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
		enterRule(_localctx, 118, RULE_breakStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			match(BREAK);
			setState(732);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(731);
				id();
				}
			}

			setState(734);
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
		enterRule(_localctx, 120, RULE_continueStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(736);
			match(CONTINUE);
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
		enterRule(_localctx, 122, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(742);
			match(INSERT);
			setState(743);
			expression(0);
			setState(744);
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
		enterRule(_localctx, 124, RULE_updateStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(746);
			match(UPDATE);
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
		enterRule(_localctx, 126, RULE_deleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(750);
			match(DELETE);
			setState(751);
			expression(0);
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
		enterRule(_localctx, 128, RULE_undeleteStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(754);
			match(UNDELETE);
			setState(755);
			expression(0);
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
		enterRule(_localctx, 130, RULE_upsertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(758);
			match(UPSERT);
			setState(759);
			expression(0);
			setState(761);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(760);
				id();
				}
			}

			setState(763);
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
		enterRule(_localctx, 132, RULE_mergeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(765);
			match(MERGE);
			setState(766);
			expression(0);
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
		enterRule(_localctx, 134, RULE_runAsStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(770);
			match(RUNAS);
			setState(771);
			match(LPAREN);
			setState(773);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(772);
				expressionList();
				}
			}

			setState(775);
			match(RPAREN);
			setState(777);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
			case 1:
				{
				setState(776);
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
		enterRule(_localctx, 136, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(779);
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
		enterRule(_localctx, 138, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(781);
			expression(0);
			setState(782);
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
		enterRule(_localctx, 140, RULE_idStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(784);
			id();
			setState(785);
			match(COLON);
			setState(786);
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
		enterRule(_localctx, 142, RULE_bangStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(788);
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
		enterRule(_localctx, 144, RULE_propertyBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(793);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FINAL) | (1L << NATIVE) | (1L << PRIVATE) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << STATIC) | (1L << VIRTUAL) | (1L << SYNCHRONIZED) | (1L << TRANSIENT) | (1L << GLOBAL) | (1L << WEBSERVICE) | (1L << TESTMETHOD) | (1L << OVERRIDE))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (WITH - 68)) | (1L << (WITHOUT - 68)) | (1L << (WITHSHARING - 68)) | (1L << (WITHOUTSHARING - 68)) | (1L << (AT - 68)))) != 0)) {
				{
				{
				setState(790);
				modifier();
				}
				}
				setState(795);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(798);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GET:
				{
				setState(796);
				getter();
				}
				break;
			case SET:
				{
				setState(797);
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
		enterRule(_localctx, 146, RULE_getter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(800);
			match(GET);
			setState(803);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(801);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(802);
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
		enterRule(_localctx, 148, RULE_setter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(805);
			match(SET);
			setState(808);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(806);
				match(SEMI);
				}
				break;
			case LBRACE:
				{
				setState(807);
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
		enterRule(_localctx, 150, RULE_catchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(810);
			match(CATCH);
			setState(811);
			match(LPAREN);
			setState(815);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(812);
				variableModifier();
				}
				}
				setState(817);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(818);
			catchType();
			setState(819);
			id();
			setState(820);
			match(RPAREN);
			setState(821);
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
		enterRule(_localctx, 152, RULE_catchType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(823);
			qualifiedName();
			setState(828);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BITOR) {
				{
				{
				setState(824);
				match(BITOR);
				setState(825);
				qualifiedName();
				}
				}
				setState(830);
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
		enterRule(_localctx, 154, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(831);
			match(FINALLY);
			setState(832);
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
		enterRule(_localctx, 156, RULE_forControl);
		int _la;
		try {
			setState(846);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(834);
				enhancedForControl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(836);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FINAL) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << TRANSIENT) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)) | (1L << (AT - 64)))) != 0)) {
					{
					setState(835);
					forInit();
					}
				}

				setState(838);
				match(SEMI);
				setState(840);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(839);
					expression(0);
					}
				}

				setState(842);
				match(SEMI);
				setState(844);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(843);
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
		enterRule(_localctx, 158, RULE_forInit);
		try {
			setState(850);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(848);
				localVariableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(849);
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
		enterRule(_localctx, 160, RULE_enhancedForControl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(855);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FINAL || _la==TRANSIENT || _la==AT) {
				{
				{
				setState(852);
				variableModifier();
				}
				}
				setState(857);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(858);
			typeRef();
			setState(859);
			id();
			setState(860);
			match(COLON);
			setState(861);
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
		enterRule(_localctx, 162, RULE_forUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(863);
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
		enterRule(_localctx, 164, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(865);
			match(LPAREN);
			setState(866);
			expression(0);
			setState(867);
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
		enterRule(_localctx, 166, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(869);
			expression(0);
			setState(874);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(870);
				match(COMMA);
				setState(871);
				expression(0);
				}
				}
				setState(876);
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
		int _startState = 168;
		enterRecursionRule(_localctx, 168, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(890);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
			case 1:
				{
				_localctx = new Alt8ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(878);
				match(NEW);
				setState(879);
				creator();
				}
				break;
			case 2:
				{
				_localctx = new Alt9ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(880);
				match(LPAREN);
				setState(881);
				typeRef();
				setState(882);
				match(RPAREN);
				setState(883);
				expression(18);
				}
				break;
			case 3:
				{
				_localctx = new Alt11ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(885);
				_la = _input.LA(1);
				if ( !(((((_la - 103)) & ~0x3f) == 0 && ((1L << (_la - 103)) & ((1L << (INC - 103)) | (1L << (DEC - 103)) | (1L << (ADD - 103)) | (1L << (SUB - 103)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(886);
				expression(16);
				}
				break;
			case 4:
				{
				_localctx = new Alt12ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(887);
				_la = _input.LA(1);
				if ( !(_la==BANG || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(888);
				expression(15);
				}
				break;
			case 5:
				{
				_localctx = new Alt26ExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(889);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(986);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,96,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(984);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
					case 1:
						{
						_localctx = new Alt13ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(892);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(893);
						_la = _input.LA(1);
						if ( !(((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & ((1L << (MUL - 107)) | (1L << (DIV - 107)) | (1L << (MOD - 107)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(894);
						expression(15);
						}
						break;
					case 2:
						{
						_localctx = new Alt14ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(895);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(896);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(897);
						expression(14);
						}
						break;
					case 3:
						{
						_localctx = new Alt15ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(898);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(906);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,91,_ctx) ) {
						case 1:
							{
							setState(899);
							match(LT);
							setState(900);
							match(LT);
							}
							break;
						case 2:
							{
							setState(901);
							match(GT);
							setState(902);
							match(GT);
							setState(903);
							match(GT);
							}
							break;
						case 3:
							{
							setState(904);
							match(GT);
							setState(905);
							match(GT);
							}
							break;
						}
						setState(908);
						expression(13);
						}
						break;
					case 4:
						{
						_localctx = new Alt16ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(909);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(918);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
						case 1:
							{
							setState(910);
							match(LT);
							setState(911);
							match(ASSIGN);
							}
							break;
						case 2:
							{
							setState(912);
							match(GT);
							setState(913);
							match(ASSIGN);
							}
							break;
						case 3:
							{
							setState(914);
							match(LE);
							}
							break;
						case 4:
							{
							setState(915);
							match(GE);
							}
							break;
						case 5:
							{
							setState(916);
							match(GT);
							}
							break;
						case 6:
							{
							setState(917);
							match(LT);
							}
							break;
						}
						setState(920);
						expression(12);
						}
						break;
					case 5:
						{
						_localctx = new Alt18ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(921);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(922);
						_la = _input.LA(1);
						if ( !(((((_la - 96)) & ~0x3f) == 0 && ((1L << (_la - 96)) & ((1L << (EQUAL - 96)) | (1L << (TRIPLEEQUAL - 96)) | (1L << (NOTEQUAL - 96)) | (1L << (LESSANDGREATER - 96)) | (1L << (TRIPLENOTEQUAL - 96)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(923);
						expression(10);
						}
						break;
					case 6:
						{
						_localctx = new Alt19ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(924);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(925);
						match(BITAND);
						setState(926);
						expression(9);
						}
						break;
					case 7:
						{
						_localctx = new Alt20ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(927);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(928);
						match(CARET);
						setState(929);
						expression(8);
						}
						break;
					case 8:
						{
						_localctx = new Alt21ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(930);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(931);
						match(BITOR);
						setState(932);
						expression(7);
						}
						break;
					case 9:
						{
						_localctx = new Alt22ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(933);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(934);
						match(AND);
						setState(935);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new Alt23ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(936);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(937);
						match(OR);
						setState(938);
						expression(5);
						}
						break;
					case 11:
						{
						_localctx = new Alt24ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(939);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(940);
						match(QUESTION);
						setState(941);
						expression(0);
						setState(942);
						match(COLON);
						setState(943);
						expression(4);
						}
						break;
					case 12:
						{
						_localctx = new Alt25ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(945);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(946);
						_la = _input.LA(1);
						if ( !(((((_la - 87)) & ~0x3f) == 0 && ((1L << (_la - 87)) & ((1L << (ASSIGN - 87)) | (1L << (ADD_ASSIGN - 87)) | (1L << (SUB_ASSIGN - 87)) | (1L << (MUL_ASSIGN - 87)) | (1L << (DIV_ASSIGN - 87)) | (1L << (AND_ASSIGN - 87)) | (1L << (OR_ASSIGN - 87)) | (1L << (XOR_ASSIGN - 87)) | (1L << (MOD_ASSIGN - 87)) | (1L << (LSHIFT_ASSIGN - 87)) | (1L << (RSHIFT_ASSIGN - 87)) | (1L << (URSHIFT_ASSIGN - 87)))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(947);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Alt1ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(948);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(949);
						match(DOT);
						setState(950);
						id();
						}
						break;
					case 14:
						{
						_localctx = new Alt2ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(951);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(952);
						match(DOT);
						setState(953);
						match(THIS);
						}
						break;
					case 15:
						{
						_localctx = new Alt3ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(954);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(955);
						match(DOT);
						setState(956);
						match(NEW);
						setState(958);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==LT) {
							{
							setState(957);
							nonWildcardTypeArguments();
							}
						}

						setState(960);
						innerCreator();
						}
						break;
					case 16:
						{
						_localctx = new Alt4ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(961);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(962);
						match(DOT);
						setState(963);
						match(SUPER);
						setState(964);
						superSuffix();
						}
						break;
					case 17:
						{
						_localctx = new Alt5ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(965);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(966);
						match(DOT);
						setState(967);
						explicitGenericInvocation();
						}
						break;
					case 18:
						{
						_localctx = new Alt6ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(968);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(969);
						match(LBRACK);
						setState(970);
						expression(0);
						setState(971);
						match(RBRACK);
						}
						break;
					case 19:
						{
						_localctx = new FunctionCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(973);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(974);
						match(LPAREN);
						setState(976);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
							{
							setState(975);
							expressionList();
							}
						}

						setState(978);
						match(RPAREN);
						}
						break;
					case 20:
						{
						_localctx = new Alt10ExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(979);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(980);
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
						setState(981);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(982);
						match(INSTANCEOF);
						setState(983);
						typeRef();
						}
						break;
					}
					} 
				}
				setState(988);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,96,_ctx);
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
		enterRule(_localctx, 170, RULE_primary);
		try {
			setState(1011);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
			case 1:
				_localctx = new Alt1PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(989);
				match(LPAREN);
				setState(990);
				expression(0);
				setState(991);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new Alt2PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(993);
				match(THIS);
				}
				break;
			case 3:
				_localctx = new Alt3PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(994);
				match(SUPER);
				}
				break;
			case 4:
				_localctx = new Alt4PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(995);
				literal();
				}
				break;
			case 5:
				_localctx = new Alt5PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(996);
				id();
				}
				break;
			case 6:
				_localctx = new Alt6PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(997);
				typeRef();
				setState(998);
				match(DOT);
				setState(999);
				match(CLASS);
				}
				break;
			case 7:
				_localctx = new Alt7PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1001);
				match(VOID);
				setState(1002);
				match(DOT);
				setState(1003);
				match(CLASS);
				}
				break;
			case 8:
				_localctx = new Alt8PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1004);
				nonWildcardTypeArguments();
				setState(1008);
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
					setState(1005);
					explicitGenericInvocationSuffix();
					}
					break;
				case THIS:
					{
					setState(1006);
					match(THIS);
					setState(1007);
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
				setState(1010);
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
		enterRule(_localctx, 172, RULE_creator);
		try {
			setState(1024);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new Alt1CreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1013);
				nonWildcardTypeArguments();
				setState(1014);
				createdName();
				setState(1015);
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
				setState(1017);
				createdName();
				setState(1022);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
				case 1:
					{
					setState(1018);
					arrayCreatorRest();
					}
					break;
				case 2:
					{
					setState(1019);
					classCreatorRest();
					}
					break;
				case 3:
					{
					setState(1020);
					mapCreatorRest();
					}
					break;
				case 4:
					{
					setState(1021);
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
		enterRule(_localctx, 174, RULE_createdName);
		int _la;
		try {
			setState(1035);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1026);
				idCreatedNamePair();
				setState(1031);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(1027);
					match(DOT);
					setState(1028);
					idCreatedNamePair();
					}
					}
					setState(1033);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1034);
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
		enterRule(_localctx, 176, RULE_idCreatedNamePair);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1037);
			id();
			setState(1039);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1038);
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
		enterRule(_localctx, 178, RULE_innerCreator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1041);
			id();
			setState(1043);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(1042);
				nonWildcardTypeArgumentsOrDiamond();
				}
			}

			setState(1045);
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
		enterRule(_localctx, 180, RULE_arrayCreatorRest);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1047);
			match(LBRACK);
			setState(1065);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RBRACK:
				{
				setState(1048);
				match(RBRACK);
				setState(1049);
				arraySubscripts();
				setState(1050);
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
				setState(1052);
				expression(0);
				setState(1053);
				match(RBRACK);
				setState(1060);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1054);
						match(LBRACK);
						setState(1055);
						expression(0);
						setState(1056);
						match(RBRACK);
						}
						} 
					}
					setState(1062);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
				}
				setState(1063);
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
		enterRule(_localctx, 182, RULE_mapCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1067);
			match(LBRACE);
			setState(1068);
			mapCreatorRestPair();
			setState(1073);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1069);
				match(COMMA);
				setState(1070);
				mapCreatorRestPair();
				}
				}
				setState(1075);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1076);
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
		enterRule(_localctx, 184, RULE_mapCreatorRestPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1078);
			idOrExpression();
			setState(1079);
			match(MAP);
			setState(1080);
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
		enterRule(_localctx, 186, RULE_setCreatorRest);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1082);
			match(LBRACE);
			setState(1083);
			literalOrExpression();
			setState(1088);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1084);
				match(COMMA);
				{
				setState(1085);
				literalOrExpression();
				}
				}
				}
				setState(1090);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1091);
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
		enterRule(_localctx, 188, RULE_literalOrExpression);
		try {
			setState(1095);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1093);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1094);
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
		enterRule(_localctx, 190, RULE_idOrExpression);
		try {
			setState(1099);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,110,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1097);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1098);
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
		enterRule(_localctx, 192, RULE_classCreatorRest);
		int _la;
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
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1102);
				match(LBRACE);
				setState(1104);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(1103);
					expressionList();
					}
				}

				setState(1106);
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
		enterRule(_localctx, 194, RULE_explicitGenericInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1109);
			nonWildcardTypeArguments();
			setState(1110);
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
		enterRule(_localctx, 196, RULE_nonWildcardTypeArguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1112);
			match(LT);
			setState(1113);
			typeList();
			setState(1114);
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
		enterRule(_localctx, 198, RULE_typeArgumentsOrDiamond);
		try {
			setState(1119);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1116);
				match(LT);
				setState(1117);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1118);
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
		enterRule(_localctx, 200, RULE_nonWildcardTypeArgumentsOrDiamond);
		try {
			setState(1124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,114,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1121);
				match(LT);
				setState(1122);
				match(GT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1123);
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
		enterRule(_localctx, 202, RULE_superSuffix);
		try {
			setState(1132);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1126);
				arguments();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1127);
				match(DOT);
				setState(1128);
				id();
				setState(1130);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
				case 1:
					{
					setState(1129);
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
		enterRule(_localctx, 204, RULE_explicitGenericInvocationSuffix);
		try {
			setState(1139);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUPER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1134);
				match(SUPER);
				setState(1135);
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
				setState(1136);
				id();
				setState(1137);
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
		enterRule(_localctx, 206, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1141);
			match(LPAREN);
			setState(1143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << DOUBLE) | (1L << FOR) | (1L << LONG) | (1L << NEW) | (1L << SUPER) | (1L << THIS) | (1L << VOID) | (1L << SELECT) | (1L << INSERT) | (1L << UPSERT) | (1L << UPDATE) | (1L << DELETE) | (1L << UNDELETE) | (1L << MERGE) | (1L << GET) | (1L << SET) | (1L << BLOB) | (1L << DATE) | (1L << DATETIME) | (1L << DECIMAL) | (1L << ID) | (1L << INTEGER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (OBJECT - 64)) | (1L << (STRING - 64)) | (1L << (TIME - 64)) | (1L << (WITHSHARING - 64)) | (1L << (WITHOUTSHARING - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (NumberLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (StringLiteral - 64)) | (1L << (NullLiteral - 64)) | (1L << (LPAREN - 64)) | (1L << (LBRACK - 64)) | (1L << (LT - 64)) | (1L << (BANG - 64)) | (1L << (TILDE - 64)) | (1L << (INC - 64)) | (1L << (DEC - 64)) | (1L << (ADD - 64)) | (1L << (SUB - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1142);
				expressionList();
				}
			}

			setState(1145);
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
		enterRule(_localctx, 208, RULE_soqlLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1147);
			match(LBRACK);
			setState(1152);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,120,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(1150);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,119,_ctx) ) {
					case 1:
						{
						setState(1148);
						soqlLiteral();
						}
						break;
					case 2:
						{
						setState(1149);
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
				setState(1154);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,120,_ctx);
			}
			setState(1155);
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
		enterRule(_localctx, 210, RULE_withSharing);
		try {
			setState(1160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1157);
				match(WITHSHARING);
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 2);
				{
				setState(1158);
				match(WITH);
				setState(1159);
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
		enterRule(_localctx, 212, RULE_withoutSharing);
		try {
			setState(1165);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITHOUTSHARING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1162);
				match(WITHOUTSHARING);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1163);
				match(WITHOUT);
				setState(1164);
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
		enterRule(_localctx, 214, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1167);
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
		case 84:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u0086\u0494\4\2\t"+
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
		"k\4l\tl\4m\tm\3\2\7\2\u00dc\n\2\f\2\16\2\u00df\13\2\3\2\3\2\3\3\7\3\u00e4"+
		"\n\3\f\3\16\3\u00e7\13\3\3\3\3\3\7\3\u00eb\n\3\f\3\16\3\u00ee\13\3\3\3"+
		"\3\3\7\3\u00f2\n\3\f\3\16\3\u00f5\13\3\3\3\3\3\5\3\u00f9\n\3\3\4\3\4\5"+
		"\4\u00fd\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5"+
		"\5\u010d\n\5\5\5\u010f\n\5\3\6\3\6\3\6\5\6\u0114\n\6\3\7\3\7\3\7\3\7\5"+
		"\7\u011a\n\7\3\7\3\7\5\7\u011e\n\7\3\7\3\7\3\b\3\b\3\b\7\b\u0125\n\b\f"+
		"\b\16\b\u0128\13\b\3\t\3\t\3\t\3\t\5\t\u012e\n\t\3\t\3\t\5\t\u0132\n\t"+
		"\3\t\5\t\u0135\n\t\3\t\5\t\u0138\n\t\3\t\3\t\3\n\3\n\3\n\7\n\u013f\n\n"+
		"\f\n\16\n\u0142\13\n\3\13\7\13\u0145\n\13\f\13\16\13\u0148\13\13\3\13"+
		"\3\13\5\13\u014c\n\13\3\13\5\13\u014f\n\13\3\f\3\f\7\f\u0153\n\f\f\f\16"+
		"\f\u0156\13\f\3\r\3\r\3\r\3\r\5\r\u015c\n\r\3\r\3\r\3\16\3\16\3\16\7\16"+
		"\u0163\n\16\f\16\16\16\u0166\13\16\3\17\3\17\7\17\u016a\n\17\f\17\16\17"+
		"\u016d\13\17\3\17\3\17\3\20\3\20\7\20\u0173\n\20\f\20\16\20\u0176\13\20"+
		"\3\20\3\20\3\21\3\21\5\21\u017c\n\21\3\21\3\21\7\21\u0180\n\21\f\21\16"+
		"\21\u0183\13\21\3\21\5\21\u0186\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u018f\n\22\3\23\3\23\3\24\5\24\u0194\n\24\3\24\7\24\u0197\n\24\f"+
		"\24\16\24\u019a\13\24\3\24\3\24\5\24\u019e\n\24\3\24\3\24\3\24\3\24\5"+
		"\24\u01a4\n\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\5\30\u01b5\n\30\3\30\3\30\3\31\7\31\u01ba\n\31\f"+
		"\31\16\31\u01bd\13\31\3\31\3\31\5\31\u01c1\n\31\3\32\3\32\3\32\3\32\3"+
		"\32\5\32\u01c8\n\32\3\33\3\33\3\33\3\33\7\33\u01ce\n\33\f\33\16\33\u01d1"+
		"\13\33\3\33\3\33\3\34\3\34\3\34\7\34\u01d8\n\34\f\34\16\34\u01db\13\34"+
		"\3\34\3\34\3\34\3\35\3\35\5\35\u01e2\n\35\3\35\3\35\3\35\3\35\3\36\3\36"+
		"\3\36\7\36\u01eb\n\36\f\36\16\36\u01ee\13\36\3\37\3\37\3\37\5\37\u01f3"+
		"\n\37\3 \3 \5 \u01f7\n \3!\3!\3!\3!\7!\u01fd\n!\f!\16!\u0200\13!\3!\5"+
		"!\u0203\n!\5!\u0205\n!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u020f\n\"\3#"+
		"\3#\7#\u0213\n#\f#\16#\u0216\13#\3$\3$\5$\u021a\n$\3$\3$\3$\5$\u021f\n"+
		"$\7$\u0221\n$\f$\16$\u0224\13$\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\7\'\u022f"+
		"\n\'\f\'\16\'\u0232\13\'\3(\3(\5(\u0236\n(\3(\3(\3)\3)\3)\7)\u023d\n)"+
		"\f)\16)\u0240\13)\3*\7*\u0243\n*\f*\16*\u0246\13*\3*\3*\3*\3+\3+\3+\7"+
		"+\u024e\n+\f+\16+\u0251\13+\3,\3,\3-\3-\3-\3-\3-\5-\u025a\n-\3-\5-\u025d"+
		"\n-\3.\3.\5.\u0261\n.\3.\7.\u0264\n.\f.\16.\u0267\13.\3/\3/\3/\3/\3\60"+
		"\3\60\3\60\5\60\u0270\n\60\3\61\3\61\3\61\3\61\7\61\u0276\n\61\f\61\16"+
		"\61\u0279\13\61\5\61\u027b\n\61\3\61\5\61\u027e\n\61\3\61\3\61\3\62\3"+
		"\62\7\62\u0284\n\62\f\62\16\62\u0287\13\62\3\62\3\62\3\63\3\63\3\63\3"+
		"\64\7\64\u028f\n\64\f\64\16\64\u0292\13\64\3\64\3\64\3\64\3\65\3\65\3"+
		"\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3"+
		"\65\3\65\3\65\3\65\3\65\3\65\5\65\u02ad\n\65\3\66\3\66\3\66\3\66\3\66"+
		"\5\66\u02b4\n\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\3"+
		"9\39\39\3:\3:\3:\6:\u02c9\n:\r:\16:\u02ca\3:\5:\u02ce\n:\3:\5:\u02d1\n"+
		":\3;\3;\5;\u02d5\n;\3;\3;\3<\3<\3<\3<\3=\3=\5=\u02df\n=\3=\3=\3>\3>\5"+
		">\u02e5\n>\3>\3>\3?\3?\3?\3?\3@\3@\3@\3@\3A\3A\3A\3A\3B\3B\3B\3B\3C\3"+
		"C\3C\5C\u02fc\nC\3C\3C\3D\3D\3D\3D\3D\3E\3E\3E\5E\u0308\nE\3E\3E\5E\u030c"+
		"\nE\3F\3F\3G\3G\3G\3H\3H\3H\3H\3I\3I\3J\7J\u031a\nJ\fJ\16J\u031d\13J\3"+
		"J\3J\5J\u0321\nJ\3K\3K\3K\5K\u0326\nK\3L\3L\3L\5L\u032b\nL\3M\3M\3M\7"+
		"M\u0330\nM\fM\16M\u0333\13M\3M\3M\3M\3M\3M\3N\3N\3N\7N\u033d\nN\fN\16"+
		"N\u0340\13N\3O\3O\3O\3P\3P\5P\u0347\nP\3P\3P\5P\u034b\nP\3P\3P\5P\u034f"+
		"\nP\5P\u0351\nP\3Q\3Q\5Q\u0355\nQ\3R\7R\u0358\nR\fR\16R\u035b\13R\3R\3"+
		"R\3R\3R\3R\3S\3S\3T\3T\3T\3T\3U\3U\3U\7U\u036b\nU\fU\16U\u036e\13U\3V"+
		"\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\5V\u037d\nV\3V\3V\3V\3V\3V\3V\3V"+
		"\3V\3V\3V\3V\3V\3V\3V\5V\u038d\nV\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\5V\u0399"+
		"\nV\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V"+
		"\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\5V\u03c1\nV\3V\3V\3V"+
		"\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\5V\u03d3\nV\3V\3V\3V\3V\3V\3V"+
		"\7V\u03db\nV\fV\16V\u03de\13V\3W\3W\3W\3W\3W\3W\3W\3W\3W\3W\3W\3W\3W\3"+
		"W\3W\3W\3W\3W\3W\5W\u03f3\nW\3W\5W\u03f6\nW\3X\3X\3X\3X\3X\3X\3X\3X\3"+
		"X\5X\u0401\nX\5X\u0403\nX\3Y\3Y\3Y\7Y\u0408\nY\fY\16Y\u040b\13Y\3Y\5Y"+
		"\u040e\nY\3Z\3Z\5Z\u0412\nZ\3[\3[\5[\u0416\n[\3[\3[\3\\\3\\\3\\\3\\\3"+
		"\\\3\\\3\\\3\\\3\\\3\\\3\\\7\\\u0425\n\\\f\\\16\\\u0428\13\\\3\\\3\\\5"+
		"\\\u042c\n\\\3]\3]\3]\3]\7]\u0432\n]\f]\16]\u0435\13]\3]\3]\3^\3^\3^\3"+
		"^\3_\3_\3_\3_\7_\u0441\n_\f_\16_\u0444\13_\3_\3_\3`\3`\5`\u044a\n`\3a"+
		"\3a\5a\u044e\na\3b\3b\3b\5b\u0453\nb\3b\5b\u0456\nb\3c\3c\3c\3d\3d\3d"+
		"\3d\3e\3e\3e\5e\u0462\ne\3f\3f\3f\5f\u0467\nf\3g\3g\3g\3g\5g\u046d\ng"+
		"\5g\u046f\ng\3h\3h\3h\3h\3h\5h\u0476\nh\3i\3i\5i\u047a\ni\3i\3i\3j\3j"+
		"\3j\7j\u0481\nj\fj\16j\u0484\13j\3j\3j\3k\3k\3k\5k\u048b\nk\3l\3l\3l\5"+
		"l\u0490\nl\3m\3m\3m\3\u0482\3\u00aan\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080"+
		"\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098"+
		"\u009a\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0"+
		"\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8"+
		"\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\2\17\5\2\34\34\'\'**"+
		"\6\2\37!$%/\6089\6\2\4\4\16\16\33\33<D\3\2KO\3\2il\3\2^_\4\2mnrr\3\2k"+
		"l\3\2bf\4\2YYt~\3\2ij\3\2UU\13\2\4\4\16\16\25\25\33\33\35\35\61\67:DI"+
		"J\177\177\2\u04f3\2\u00dd\3\2\2\2\4\u00f8\3\2\2\2\6\u00fc\3\2\2\2\b\u010e"+
		"\3\2\2\2\n\u0113\3\2\2\2\f\u0115\3\2\2\2\16\u0121\3\2\2\2\20\u0129\3\2"+
		"\2\2\22\u013b\3\2\2\2\24\u0146\3\2\2\2\26\u0150\3\2\2\2\30\u0157\3\2\2"+
		"\2\32\u015f\3\2\2\2\34\u0167\3\2\2\2\36\u0170\3\2\2\2 \u0185\3\2\2\2\""+
		"\u018e\3\2\2\2$\u0190\3\2\2\2&\u0193\3\2\2\2(\u01a5\3\2\2\2*\u01a9\3\2"+
		"\2\2,\u01ad\3\2\2\2.\u01b1\3\2\2\2\60\u01c0\3\2\2\2\62\u01c7\3\2\2\2\64"+
		"\u01c9\3\2\2\2\66\u01d4\3\2\2\28\u01e1\3\2\2\2:\u01e7\3\2\2\2<\u01ef\3"+
		"\2\2\2>\u01f6\3\2\2\2@\u01f8\3\2\2\2B\u020e\3\2\2\2D\u0214\3\2\2\2F\u0217"+
		"\3\2\2\2H\u0225\3\2\2\2J\u0227\3\2\2\2L\u022b\3\2\2\2N\u0233\3\2\2\2P"+
		"\u0239\3\2\2\2R\u0244\3\2\2\2T\u024a\3\2\2\2V\u0252\3\2\2\2X\u0254\3\2"+
		"\2\2Z\u025e\3\2\2\2\\\u0268\3\2\2\2^\u026f\3\2\2\2`\u0271\3\2\2\2b\u0281"+
		"\3\2\2\2d\u028a\3\2\2\2f\u0290\3\2\2\2h\u02ac\3\2\2\2j\u02ae\3\2\2\2l"+
		"\u02b5\3\2\2\2n\u02bb\3\2\2\2p\u02bf\3\2\2\2r\u02c5\3\2\2\2t\u02d2\3\2"+
		"\2\2v\u02d8\3\2\2\2x\u02dc\3\2\2\2z\u02e2\3\2\2\2|\u02e8\3\2\2\2~\u02ec"+
		"\3\2\2\2\u0080\u02f0\3\2\2\2\u0082\u02f4\3\2\2\2\u0084\u02f8\3\2\2\2\u0086"+
		"\u02ff\3\2\2\2\u0088\u0304\3\2\2\2\u008a\u030d\3\2\2\2\u008c\u030f\3\2"+
		"\2\2\u008e\u0312\3\2\2\2\u0090\u0316\3\2\2\2\u0092\u031b\3\2\2\2\u0094"+
		"\u0322\3\2\2\2\u0096\u0327\3\2\2\2\u0098\u032c\3\2\2\2\u009a\u0339\3\2"+
		"\2\2\u009c\u0341\3\2\2\2\u009e\u0350\3\2\2\2\u00a0\u0354\3\2\2\2\u00a2"+
		"\u0359\3\2\2\2\u00a4\u0361\3\2\2\2\u00a6\u0363\3\2\2\2\u00a8\u0367\3\2"+
		"\2\2\u00aa\u037c\3\2\2\2\u00ac\u03f5\3\2\2\2\u00ae\u0402\3\2\2\2\u00b0"+
		"\u040d\3\2\2\2\u00b2\u040f\3\2\2\2\u00b4\u0413\3\2\2\2\u00b6\u0419\3\2"+
		"\2\2\u00b8\u042d\3\2\2\2\u00ba\u0438\3\2\2\2\u00bc\u043c\3\2\2\2\u00be"+
		"\u0449\3\2\2\2\u00c0\u044d\3\2\2\2\u00c2\u0455\3\2\2\2\u00c4\u0457\3\2"+
		"\2\2\u00c6\u045a\3\2\2\2\u00c8\u0461\3\2\2\2\u00ca\u0466\3\2\2\2\u00cc"+
		"\u046e\3\2\2\2\u00ce\u0475\3\2\2\2\u00d0\u0477\3\2\2\2\u00d2\u047d\3\2"+
		"\2\2\u00d4\u048a\3\2\2\2\u00d6\u048f\3\2\2\2\u00d8\u0491\3\2\2\2\u00da"+
		"\u00dc\5\4\3\2\u00db\u00da\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3\2"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0"+
		"\u00e1\7\2\2\3\u00e1\3\3\2\2\2\u00e2\u00e4\5\b\5\2\u00e3\u00e2\3\2\2\2"+
		"\u00e4\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8"+
		"\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00f9\5\f\7\2\u00e9\u00eb\5\b\5\2\u00ea"+
		"\u00e9\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2"+
		"\2\2\u00ed\u00ef\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef\u00f9\5\20\t\2\u00f0"+
		"\u00f2\5\b\5\2\u00f1\u00f0\3\2\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f1\3\2"+
		"\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6"+
		"\u00f9\5\30\r\2\u00f7\u00f9\7V\2\2\u00f8\u00e5\3\2\2\2\u00f8\u00ec\3\2"+
		"\2\2\u00f8\u00f3\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9\5\3\2\2\2\u00fa\u00fd"+
		"\5\b\5\2\u00fb\u00fd\t\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd"+
		"\7\3\2\2\2\u00fe\u010f\5X-\2\u00ff\u010d\7!\2\2\u0100\u010d\7 \2\2\u0101"+
		"\u010d\7\37\2\2\u0102\u010d\7$\2\2\u0103\u010d\7\3\2\2\u0104\u010d\7\22"+
		"\2\2\u0105\u010d\7/\2\2\u0106\u010d\7\60\2\2\u0107\u010d\79\2\2\u0108"+
		"\u010d\7%\2\2\u0109\u010d\78\2\2\u010a\u010d\5\u00d4k\2\u010b\u010d\5"+
		"\u00d6l\2\u010c\u00ff\3\2\2\2\u010c\u0100\3\2\2\2\u010c\u0101\3\2\2\2"+
		"\u010c\u0102\3\2\2\2\u010c\u0103\3\2\2\2\u010c\u0104\3\2\2\2\u010c\u0105"+
		"\3\2\2\2\u010c\u0106\3\2\2\2\u010c\u0107\3\2\2\2\u010c\u0108\3\2\2\2\u010c"+
		"\u0109\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d\u010f\3\2"+
		"\2\2\u010e\u00fe\3\2\2\2\u010e\u010c\3\2\2\2\u010f\t\3\2\2\2\u0110\u0114"+
		"\7\22\2\2\u0111\u0114\7*\2\2\u0112\u0114\5X-\2\u0113\u0110\3\2\2\2\u0113"+
		"\u0111\3\2\2\2\u0113\u0112\3\2\2\2\u0114\13\3\2\2\2\u0115\u0116\7\t\2"+
		"\2\u0116\u0119\5\u00d8m\2\u0117\u0118\7\21\2\2\u0118\u011a\5B\"\2\u0119"+
		"\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u011c\7\30"+
		"\2\2\u011c\u011e\5\32\16\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e"+
		"\u011f\3\2\2\2\u011f\u0120\5\34\17\2\u0120\r\3\2\2\2\u0121\u0126\5B\""+
		"\2\u0122\u0123\7o\2\2\u0123\u0125\5B\"\2\u0124\u0122\3\2\2\2\u0125\u0128"+
		"\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\17\3\2\2\2\u0128"+
		"\u0126\3\2\2\2\u0129\u012a\7\20\2\2\u012a\u012d\5\u00d8m\2\u012b\u012c"+
		"\7\30\2\2\u012c\u012e\5\32\16\2\u012d\u012b\3\2\2\2\u012d\u012e\3\2\2"+
		"\2\u012e\u012f\3\2\2\2\u012f\u0131\7R\2\2\u0130\u0132\5\22\n\2\u0131\u0130"+
		"\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0134\3\2\2\2\u0133\u0135\7W\2\2\u0134"+
		"\u0133\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0137\3\2\2\2\u0136\u0138\5\26"+
		"\f\2\u0137\u0136\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\3\2\2\2\u0139"+
		"\u013a\7S\2\2\u013a\21\3\2\2\2\u013b\u0140\5\24\13\2\u013c\u013d\7W\2"+
		"\2\u013d\u013f\5\24\13\2\u013e\u013c\3\2\2\2\u013f\u0142\3\2\2\2\u0140"+
		"\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\23\3\2\2\2\u0142\u0140\3\2\2"+
		"\2\u0143\u0145\5X-\2\u0144\u0143\3\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144"+
		"\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0149\3\2\2\2\u0148\u0146\3\2\2\2\u0149"+
		"\u014b\5\u00d8m\2\u014a\u014c\5\u00d0i\2\u014b\u014a\3\2\2\2\u014b\u014c"+
		"\3\2\2\2\u014c\u014e\3\2\2\2\u014d\u014f\5\34\17\2\u014e\u014d\3\2\2\2"+
		"\u014e\u014f\3\2\2\2\u014f\25\3\2\2\2\u0150\u0154\7V\2\2\u0151\u0153\5"+
		" \21\2\u0152\u0151\3\2\2\2\u0153\u0156\3\2\2\2\u0154\u0152\3\2\2\2\u0154"+
		"\u0155\3\2\2\2\u0155\27\3\2\2\2\u0156\u0154\3\2\2\2\u0157\u0158\7\32\2"+
		"\2\u0158\u015b\5\u00d8m\2\u0159\u015a\7\21\2\2\u015a\u015c\5\32\16\2\u015b"+
		"\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u015e\5\36"+
		"\20\2\u015e\31\3\2\2\2\u015f\u0164\5B\"\2\u0160\u0161\7W\2\2\u0161\u0163"+
		"\5B\"\2\u0162\u0160\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2\2\2\u0164"+
		"\u0165\3\2\2\2\u0165\33\3\2\2\2\u0166\u0164\3\2\2\2\u0167\u016b\7R\2\2"+
		"\u0168\u016a\5 \21\2\u0169\u0168\3\2\2\2\u016a\u016d\3\2\2\2\u016b\u0169"+
		"\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016e\3\2\2\2\u016d\u016b\3\2\2\2\u016e"+
		"\u016f\7S\2\2\u016f\35\3\2\2\2\u0170\u0174\7R\2\2\u0171\u0173\5\60\31"+
		"\2\u0172\u0171\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2\u0174\u0175"+
		"\3\2\2\2\u0175\u0177\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u0178\7S\2\2\u0178"+
		"\37\3\2\2\2\u0179\u0186\7V\2\2\u017a\u017c\7$\2\2\u017b\u017a\3\2\2\2"+
		"\u017b\u017c\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u0186\5b\62\2\u017e\u0180"+
		"\5\6\4\2\u017f\u017e\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181"+
		"\u0182\3\2\2\2\u0182\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0186\5\""+
		"\22\2\u0185\u0179\3\2\2\2\u0185\u017b\3\2\2\2\u0185\u0181\3\2\2\2\u0186"+
		"!\3\2\2\2\u0187\u018f\5&\24\2\u0188\u018f\5*\26\2\u0189\u018f\5(\25\2"+
		"\u018a\u018f\5\30\r\2\u018b\u018f\5\f\7\2\u018c\u018f\5\20\t\2\u018d\u018f"+
		"\5,\27\2\u018e\u0187\3\2\2\2\u018e\u0188\3\2\2\2\u018e\u0189\3\2\2\2\u018e"+
		"\u018a\3\2\2\2\u018e\u018b\3\2\2\2\u018e\u018c\3\2\2\2\u018e\u018d\3\2"+
		"\2\2\u018f#\3\2\2\2\u0190\u0191\t\3\2\2\u0191%\3\2\2\2\u0192\u0194\5X"+
		"-\2\u0193\u0192\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0198\3\2\2\2\u0195"+
		"\u0197\5$\23\2\u0196\u0195\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0196\3\2"+
		"\2\2\u0198\u0199\3\2\2\2\u0199\u019d\3\2\2\2\u019a\u0198\3\2\2\2\u019b"+
		"\u019e\5B\"\2\u019c\u019e\7,\2\2\u019d\u019b\3\2\2\2\u019d\u019c\3\2\2"+
		"\2\u019e\u019f\3\2\2\2\u019f\u01a0\5\u00d8m\2\u01a0\u01a3\5N(\2\u01a1"+
		"\u01a4\5b\62\2\u01a2\u01a4\7V\2\2\u01a3\u01a1\3\2\2\2\u01a3\u01a2\3\2"+
		"\2\2\u01a4\'\3\2\2\2\u01a5\u01a6\5T+\2\u01a6\u01a7\5N(\2\u01a7\u01a8\5"+
		"b\62\2\u01a8)\3\2\2\2\u01a9\u01aa\5B\"\2\u01aa\u01ab\5:\36\2\u01ab\u01ac"+
		"\7V\2\2\u01ac+\3\2\2\2\u01ad\u01ae\5B\"\2\u01ae\u01af\5:\36\2\u01af\u01b0"+
		"\5.\30\2\u01b0-\3\2\2\2\u01b1\u01b2\7R\2\2\u01b2\u01b4\5\u0092J\2\u01b3"+
		"\u01b5\5\u0092J\2\u01b4\u01b3\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b6"+
		"\3\2\2\2\u01b6\u01b7\7S\2\2\u01b7/\3\2\2\2\u01b8\u01ba\5\6\4\2\u01b9\u01b8"+
		"\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc"+
		"\u01be\3\2\2\2\u01bd\u01bb\3\2\2\2\u01be\u01c1\5\62\32\2\u01bf\u01c1\7"+
		"V\2\2\u01c0\u01bb\3\2\2\2\u01c0\u01bf\3\2\2\2\u01c1\61\3\2\2\2\u01c2\u01c8"+
		"\5\64\33\2\u01c3\u01c8\58\35\2\u01c4\u01c8\5\30\r\2\u01c5\u01c8\5\f\7"+
		"\2\u01c6\u01c8\5\20\t\2\u01c7\u01c2\3\2\2\2\u01c7\u01c3\3\2\2\2\u01c7"+
		"\u01c4\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7\u01c6\3\2\2\2\u01c8\63\3\2\2"+
		"\2\u01c9\u01ca\5B\"\2\u01ca\u01cf\5\66\34\2\u01cb\u01cc\7W\2\2\u01cc\u01ce"+
		"\5\66\34\2\u01cd\u01cb\3\2\2\2\u01ce\u01d1\3\2\2\2\u01cf\u01cd\3\2\2\2"+
		"\u01cf\u01d0\3\2\2\2\u01d0\u01d2\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d2\u01d3"+
		"\7V\2\2\u01d3\65\3\2\2\2\u01d4\u01d9\5\u00d8m\2\u01d5\u01d6\7T\2\2\u01d6"+
		"\u01d8\7U\2\2\u01d7\u01d5\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7\3\2"+
		"\2\2\u01d9\u01da\3\2\2\2\u01da\u01dc\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc"+
		"\u01dd\7Y\2\2\u01dd\u01de\5> \2\u01de\67\3\2\2\2\u01df\u01e2\5B\"\2\u01e0"+
		"\u01e2\7,\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e0\3\2\2\2\u01e2\u01e3\3\2"+
		"\2\2\u01e3\u01e4\5\u00d8m\2\u01e4\u01e5\5N(\2\u01e5\u01e6\7V\2\2\u01e6"+
		"9\3\2\2\2\u01e7\u01ec\5<\37\2\u01e8\u01e9\7W\2\2\u01e9\u01eb\5<\37\2\u01ea"+
		"\u01e8\3\2\2\2\u01eb\u01ee\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ec\u01ed\3\2"+
		"\2\2\u01ed;\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ef\u01f2\5\u00d8m\2\u01f0\u01f1"+
		"\7Y\2\2\u01f1\u01f3\5> \2\u01f2\u01f0\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3"+
		"=\3\2\2\2\u01f4\u01f7\5@!\2\u01f5\u01f7\5\u00aaV\2\u01f6\u01f4\3\2\2\2"+
		"\u01f6\u01f5\3\2\2\2\u01f7?\3\2\2\2\u01f8\u0204\7R\2\2\u01f9\u01fe\5>"+
		" \2\u01fa\u01fb\7W\2\2\u01fb\u01fd\5> \2\u01fc\u01fa\3\2\2\2\u01fd\u0200"+
		"\3\2\2\2\u01fe\u01fc\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0202\3\2\2\2\u0200"+
		"\u01fe\3\2\2\2\u0201\u0203\7W\2\2\u0202\u0201\3\2\2\2\u0202\u0203\3\2"+
		"\2\2\u0203\u0205\3\2\2\2\u0204\u01f9\3\2\2\2\u0204\u0205\3\2\2\2\u0205"+
		"\u0206\3\2\2\2\u0206\u0207\7S\2\2\u0207A\3\2\2\2\u0208\u0209\5F$\2\u0209"+
		"\u020a\5D#\2\u020a\u020f\3\2\2\2\u020b\u020c\5H%\2\u020c\u020d\5D#\2\u020d"+
		"\u020f\3\2\2\2\u020e\u0208\3\2\2\2\u020e\u020b\3\2\2\2\u020fC\3\2\2\2"+
		"\u0210\u0211\7T\2\2\u0211\u0213\7U\2\2\u0212\u0210\3\2\2\2\u0213\u0216"+
		"\3\2\2\2\u0214\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u0215E\3\2\2\2\u0216"+
		"\u0214\3\2\2\2\u0217\u0219\5\u00d8m\2\u0218\u021a\5J&\2\u0219\u0218\3"+
		"\2\2\2\u0219\u021a\3\2\2\2\u021a\u0222\3\2\2\2\u021b\u021c\7X\2\2\u021c"+
		"\u021e\5\u00d8m\2\u021d\u021f\5J&\2\u021e\u021d\3\2\2\2\u021e\u021f\3"+
		"\2\2\2\u021f\u0221\3\2\2\2\u0220\u021b\3\2\2\2\u0221\u0224\3\2\2\2\u0222"+
		"\u0220\3\2\2\2\u0222\u0223\3\2\2\2\u0223G\3\2\2\2\u0224\u0222\3\2\2\2"+
		"\u0225\u0226\t\4\2\2\u0226I\3\2\2\2\u0227\u0228\7]\2\2\u0228\u0229\5\32"+
		"\16\2\u0229\u022a\7\\\2\2\u022aK\3\2\2\2\u022b\u0230\5T+\2\u022c\u022d"+
		"\7W\2\2\u022d\u022f\5T+\2\u022e\u022c\3\2\2\2\u022f\u0232\3\2\2\2\u0230"+
		"\u022e\3\2\2\2\u0230\u0231\3\2\2\2\u0231M\3\2\2\2\u0232\u0230\3\2\2\2"+
		"\u0233\u0235\7P\2\2\u0234\u0236\5P)\2\u0235\u0234\3\2\2\2\u0235\u0236"+
		"\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0238\7Q\2\2\u0238O\3\2\2\2\u0239\u023e"+
		"\5R*\2\u023a\u023b\7W\2\2\u023b\u023d\5R*\2\u023c\u023a\3\2\2\2\u023d"+
		"\u0240\3\2\2\2\u023e\u023c\3\2\2\2\u023e\u023f\3\2\2\2\u023fQ\3\2\2\2"+
		"\u0240\u023e\3\2\2\2\u0241\u0243\5\n\6\2\u0242\u0241\3\2\2\2\u0243\u0246"+
		"\3\2\2\2\u0244\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0247\3\2\2\2\u0246"+
		"\u0244\3\2\2\2\u0247\u0248\5B\"\2\u0248\u0249\5\u00d8m\2\u0249S\3\2\2"+
		"\2\u024a\u024f\5\u00d8m\2\u024b\u024c\7X\2\2\u024c\u024e\5\u00d8m\2\u024d"+
		"\u024b\3\2\2\2\u024e\u0251\3\2\2\2\u024f\u024d\3\2\2\2\u024f\u0250\3\2"+
		"\2\2\u0250U\3\2\2\2\u0251\u024f\3\2\2\2\u0252\u0253\t\5\2\2\u0253W\3\2"+
		"\2\2\u0254\u0255\7\u0080\2\2\u0255\u025c\5T+\2\u0256\u0259\7P\2\2\u0257"+
		"\u025a\5Z.\2\u0258\u025a\5^\60\2\u0259\u0257\3\2\2\2\u0259\u0258\3\2\2"+
		"\2\u0259\u025a\3\2\2\2\u025a\u025b\3\2\2\2\u025b\u025d\7Q\2\2\u025c\u0256"+
		"\3\2\2\2\u025c\u025d\3\2\2\2\u025dY\3\2\2\2\u025e\u0265\5\\/\2\u025f\u0261"+
		"\7W\2\2\u0260\u025f\3\2\2\2\u0260\u0261\3\2\2\2\u0261\u0262\3\2\2\2\u0262"+
		"\u0264\5\\/\2\u0263\u0260\3\2\2\2\u0264\u0267\3\2\2\2\u0265\u0263\3\2"+
		"\2\2\u0265\u0266\3\2\2\2\u0266[\3\2\2\2\u0267\u0265\3\2\2\2\u0268\u0269"+
		"\5\u00d8m\2\u0269\u026a\7Y\2\2\u026a\u026b\5^\60\2\u026b]\3\2\2\2\u026c"+
		"\u0270\5\u00aaV\2\u026d\u0270\5X-\2\u026e\u0270\5`\61\2\u026f\u026c\3"+
		"\2\2\2\u026f\u026d\3\2\2\2\u026f\u026e\3\2\2\2\u0270_\3\2\2\2\u0271\u027a"+
		"\7R\2\2\u0272\u0277\5^\60\2\u0273\u0274\7W\2\2\u0274\u0276\5^\60\2\u0275"+
		"\u0273\3\2\2\2\u0276\u0279\3\2\2\2\u0277\u0275\3\2\2\2\u0277\u0278\3\2"+
		"\2\2\u0278\u027b\3\2\2\2\u0279\u0277\3\2\2\2\u027a\u0272\3\2\2\2\u027a"+
		"\u027b\3\2\2\2\u027b\u027d\3\2\2\2\u027c\u027e\7W\2\2\u027d\u027c\3\2"+
		"\2\2\u027d\u027e\3\2\2\2\u027e\u027f\3\2\2\2\u027f\u0280\7S\2\2\u0280"+
		"a\3\2\2\2\u0281\u0285\7R\2\2\u0282\u0284\5h\65\2\u0283\u0282\3\2\2\2\u0284"+
		"\u0287\3\2\2\2\u0285\u0283\3\2\2\2\u0285\u0286\3\2\2\2\u0286\u0288\3\2"+
		"\2\2\u0287\u0285\3\2\2\2\u0288\u0289\7S\2\2\u0289c\3\2\2\2\u028a\u028b"+
		"\5f\64\2\u028b\u028c\7V\2\2\u028ce\3\2\2\2\u028d\u028f\5\n\6\2\u028e\u028d"+
		"\3\2\2\2\u028f\u0292\3\2\2\2\u0290\u028e\3\2\2\2\u0290\u0291\3\2\2\2\u0291"+
		"\u0293\3\2\2\2\u0292\u0290\3\2\2\2\u0293\u0294\5B\"\2\u0294\u0295\5:\36"+
		"\2\u0295g\3\2\2\2\u0296\u02ad\5b\62\2\u0297\u02ad\5d\63\2\u0298\u02ad"+
		"\5j\66\2\u0299\u02ad\5l\67\2\u029a\u02ad\5n8\2\u029b\u02ad\5p9\2\u029c"+
		"\u02ad\5r:\2\u029d\u02ad\5t;\2\u029e\u02ad\5v<\2\u029f\u02ad\5x=\2\u02a0"+
		"\u02ad\5z>\2\u02a1\u02ad\5|?\2\u02a2\u02ad\5~@\2\u02a3\u02ad\5\u0080A"+
		"\2\u02a4\u02ad\5\u0082B\2\u02a5\u02ad\5\u0084C\2\u02a6\u02ad\5\u0086D"+
		"\2\u02a7\u02ad\5\u0088E\2\u02a8\u02ad\5\u008aF\2\u02a9\u02ad\5\u008cG"+
		"\2\u02aa\u02ad\5\u008eH\2\u02ab\u02ad\5\u0090I\2\u02ac\u0296\3\2\2\2\u02ac"+
		"\u0297\3\2\2\2\u02ac\u0298\3\2\2\2\u02ac\u0299\3\2\2\2\u02ac\u029a\3\2"+
		"\2\2\u02ac\u029b\3\2\2\2\u02ac\u029c\3\2\2\2\u02ac\u029d\3\2\2\2\u02ac"+
		"\u029e\3\2\2\2\u02ac\u029f\3\2\2\2\u02ac\u02a0\3\2\2\2\u02ac\u02a1\3\2"+
		"\2\2\u02ac\u02a2\3\2\2\2\u02ac\u02a3\3\2\2\2\u02ac\u02a4\3\2\2\2\u02ac"+
		"\u02a5\3\2\2\2\u02ac\u02a6\3\2\2\2\u02ac\u02a7\3\2\2\2\u02ac\u02a8\3\2"+
		"\2\2\u02ac\u02a9\3\2\2\2\u02ac\u02aa\3\2\2\2\u02ac\u02ab\3\2\2\2\u02ad"+
		"i\3\2\2\2\u02ae\u02af\7\26\2\2\u02af\u02b0\5\u00a6T\2\u02b0\u02b3\5h\65"+
		"\2\u02b1\u02b2\7\17\2\2\u02b2\u02b4\5h\65\2\u02b3\u02b1\3\2\2\2\u02b3"+
		"\u02b4\3\2\2\2\u02b4k\3\2\2\2\u02b5\u02b6\7\25\2\2\u02b6\u02b7\7P\2\2"+
		"\u02b7\u02b8\5\u009eP\2\u02b8\u02b9\7Q\2\2\u02b9\u02ba\5h\65\2\u02bam"+
		"\3\2\2\2\u02bb\u02bc\7.\2\2\u02bc\u02bd\5\u00a6T\2\u02bd\u02be\5h\65\2"+
		"\u02beo\3\2\2\2\u02bf\u02c0\7\r\2\2\u02c0\u02c1\5h\65\2\u02c1\u02c2\7"+
		".\2\2\u02c2\u02c3\5\u00a6T\2\u02c3\u02c4\7V\2\2\u02c4q\3\2\2\2\u02c5\u02c6"+
		"\7+\2\2\u02c6\u02d0\5b\62\2\u02c7\u02c9\5\u0098M\2\u02c8\u02c7\3\2\2\2"+
		"\u02c9\u02ca\3\2\2\2\u02ca\u02c8\3\2\2\2\u02ca\u02cb\3\2\2\2\u02cb\u02cd"+
		"\3\2\2\2\u02cc\u02ce\5\u009cO\2\u02cd\u02cc\3\2\2\2\u02cd\u02ce\3\2\2"+
		"\2\u02ce\u02d1\3\2\2\2\u02cf\u02d1\5\u009cO\2\u02d0\u02c8\3\2\2\2\u02d0"+
		"\u02cf\3\2\2\2\u02d1s\3\2\2\2\u02d2\u02d4\7\"\2\2\u02d3\u02d5\5\u00aa"+
		"V\2\u02d4\u02d3\3\2\2\2\u02d4\u02d5\3\2\2\2\u02d5\u02d6\3\2\2\2\u02d6"+
		"\u02d7\7V\2\2\u02d7u\3\2\2\2\u02d8\u02d9\7)\2\2\u02d9\u02da\5\u00aaV\2"+
		"\u02da\u02db\7V\2\2\u02dbw\3\2\2\2\u02dc\u02de\7\5\2\2\u02dd\u02df\5\u00d8"+
		"m\2\u02de\u02dd\3\2\2\2\u02de\u02df\3\2\2\2\u02df\u02e0\3\2\2\2\u02e0"+
		"\u02e1\7V\2\2\u02e1y\3\2\2\2\u02e2\u02e4\7\13\2\2\u02e3\u02e5\5\u00d8"+
		"m\2\u02e4\u02e3\3\2\2\2\u02e4\u02e5\3\2\2\2\u02e5\u02e6\3\2\2\2\u02e6"+
		"\u02e7\7V\2\2\u02e7{\3\2\2\2\u02e8\u02e9\7\62\2\2\u02e9\u02ea\5\u00aa"+
		"V\2\u02ea\u02eb\7V\2\2\u02eb}\3\2\2\2\u02ec\u02ed\7\64\2\2\u02ed\u02ee"+
		"\5\u00aaV\2\u02ee\u02ef\7V\2\2\u02ef\177\3\2\2\2\u02f0\u02f1\7\65\2\2"+
		"\u02f1\u02f2\5\u00aaV\2\u02f2\u02f3\7V\2\2\u02f3\u0081\3\2\2\2\u02f4\u02f5"+
		"\7\66\2\2\u02f5\u02f6\5\u00aaV\2\u02f6\u02f7\7V\2\2\u02f7\u0083\3\2\2"+
		"\2\u02f8\u02f9\7\63\2\2\u02f9\u02fb\5\u00aaV\2\u02fa\u02fc\5\u00d8m\2"+
		"\u02fb\u02fa\3\2\2\2\u02fb\u02fc\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd\u02fe"+
		"\7V\2\2\u02fe\u0085\3\2\2\2\u02ff\u0300\7\67\2\2\u0300\u0301\5\u00aaV"+
		"\2\u0301\u0302\5\u00aaV\2\u0302\u0303\7V\2\2\u0303\u0087\3\2\2\2\u0304"+
		"\u0305\7E\2\2\u0305\u0307\7P\2\2\u0306\u0308\5\u00a8U\2\u0307\u0306\3"+
		"\2\2\2\u0307\u0308\3\2\2\2\u0308\u0309\3\2\2\2\u0309\u030b\7Q\2\2\u030a"+
		"\u030c\5b\62\2\u030b\u030a\3\2\2\2\u030b\u030c\3\2\2\2\u030c\u0089\3\2"+
		"\2\2\u030d\u030e\7V\2\2\u030e\u008b\3\2\2\2\u030f\u0310\5\u00aaV\2\u0310"+
		"\u0311\7V\2\2\u0311\u008d\3\2\2\2\u0312\u0313\5\u00d8m\2\u0313\u0314\7"+
		"a\2\2\u0314\u0315\5h\65\2\u0315\u008f\3\2\2\2\u0316\u0317\7\u0083\2\2"+
		"\u0317\u0091\3\2\2\2\u0318\u031a\5\6\4\2\u0319\u0318\3\2\2\2\u031a\u031d"+
		"\3\2\2\2\u031b\u0319\3\2\2\2\u031b\u031c\3\2\2\2\u031c\u0320\3\2\2\2\u031d"+
		"\u031b\3\2\2\2\u031e\u0321\5\u0094K\2\u031f\u0321\5\u0096L\2\u0320\u031e"+
		"\3\2\2\2\u0320\u031f\3\2\2\2\u0321\u0093\3\2\2\2\u0322\u0325\7:\2\2\u0323"+
		"\u0326\7V\2\2\u0324\u0326\5b\62\2\u0325\u0323\3\2\2\2\u0325\u0324\3\2"+
		"\2\2\u0326\u0095\3\2\2\2\u0327\u032a\7;\2\2\u0328\u032b\7V\2\2\u0329\u032b"+
		"\5b\62\2\u032a\u0328\3\2\2\2\u032a\u0329\3\2\2\2\u032b\u0097\3\2\2\2\u032c"+
		"\u032d\7\7\2\2\u032d\u0331\7P\2\2\u032e\u0330\5\n\6\2\u032f\u032e\3\2"+
		"\2\2\u0330\u0333\3\2\2\2\u0331\u032f\3\2\2\2\u0331\u0332\3\2\2\2\u0332"+
		"\u0334\3\2\2\2\u0333\u0331\3\2\2\2\u0334\u0335\5\u009aN\2\u0335\u0336"+
		"\5\u00d8m\2\u0336\u0337\7Q\2\2\u0337\u0338\5b\62\2\u0338\u0099\3\2\2\2"+
		"\u0339\u033e\5T+\2\u033a\u033b\7p\2\2\u033b\u033d\5T+\2\u033c\u033a\3"+
		"\2\2\2\u033d\u0340\3\2\2\2\u033e\u033c\3\2\2\2\u033e\u033f\3\2\2\2\u033f"+
		"\u009b\3\2\2\2\u0340\u033e\3\2\2\2\u0341\u0342\7\23\2\2\u0342\u0343\5"+
		"b\62\2\u0343\u009d\3\2\2\2\u0344\u0351\5\u00a2R\2\u0345\u0347\5\u00a0"+
		"Q\2\u0346\u0345\3\2\2\2\u0346\u0347\3\2\2\2\u0347\u0348\3\2\2\2\u0348"+
		"\u034a\7V\2\2\u0349\u034b\5\u00aaV\2\u034a\u0349\3\2\2\2\u034a\u034b\3"+
		"\2\2\2\u034b\u034c\3\2\2\2\u034c\u034e\7V\2\2\u034d\u034f\5\u00a4S\2\u034e"+
		"\u034d\3\2\2\2\u034e\u034f\3\2\2\2\u034f\u0351\3\2\2\2\u0350\u0344\3\2"+
		"\2\2\u0350\u0346\3\2\2\2\u0351\u009f\3\2\2\2\u0352\u0355\5f\64\2\u0353"+
		"\u0355\5\u00a8U\2\u0354\u0352\3\2\2\2\u0354\u0353\3\2\2\2\u0355\u00a1"+
		"\3\2\2\2\u0356\u0358\5\n\6\2\u0357\u0356\3\2\2\2\u0358\u035b\3\2\2\2\u0359"+
		"\u0357\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u035c\3\2\2\2\u035b\u0359\3\2"+
		"\2\2\u035c\u035d\5B\"\2\u035d\u035e\5\u00d8m\2\u035e\u035f\7a\2\2\u035f"+
		"\u0360\5\u00aaV\2\u0360\u00a3\3\2\2\2\u0361\u0362\5\u00a8U\2\u0362\u00a5"+
		"\3\2\2\2\u0363\u0364\7P\2\2\u0364\u0365\5\u00aaV\2\u0365\u0366\7Q\2\2"+
		"\u0366\u00a7\3\2\2\2\u0367\u036c\5\u00aaV\2\u0368\u0369\7W\2\2\u0369\u036b"+
		"\5\u00aaV\2\u036a\u0368\3\2\2\2\u036b\u036e\3\2\2\2\u036c\u036a\3\2\2"+
		"\2\u036c\u036d\3\2\2\2\u036d\u00a9\3\2\2\2\u036e\u036c\3\2\2\2\u036f\u0370"+
		"\bV\1\2\u0370\u0371\7\35\2\2\u0371\u037d\5\u00aeX\2\u0372\u0373\7P\2\2"+
		"\u0373\u0374\5B\"\2\u0374\u0375\7Q\2\2\u0375\u0376\5\u00aaV\24\u0376\u037d"+
		"\3\2\2\2\u0377\u0378\t\6\2\2\u0378\u037d\5\u00aaV\22\u0379\u037a\t\7\2"+
		"\2\u037a\u037d\5\u00aaV\21\u037b\u037d\5\u00acW\2\u037c\u036f\3\2\2\2"+
		"\u037c\u0372\3\2\2\2\u037c\u0377\3\2\2\2\u037c\u0379\3\2\2\2\u037c\u037b"+
		"\3\2\2\2\u037d\u03dc\3\2\2\2\u037e\u037f\f\20\2\2\u037f\u0380\t\b\2\2"+
		"\u0380\u03db\5\u00aaV\21\u0381\u0382\f\17\2\2\u0382\u0383\t\t\2\2\u0383"+
		"\u03db\5\u00aaV\20\u0384\u038c\f\16\2\2\u0385\u0386\7]\2\2\u0386\u038d"+
		"\7]\2\2\u0387\u0388\7\\\2\2\u0388\u0389\7\\\2\2\u0389\u038d\7\\\2\2\u038a"+
		"\u038b\7\\\2\2\u038b\u038d\7\\\2\2\u038c\u0385\3\2\2\2\u038c\u0387\3\2"+
		"\2\2\u038c\u038a\3\2\2\2\u038d\u038e\3\2\2\2\u038e\u03db\5\u00aaV\17\u038f"+
		"\u0398\f\r\2\2\u0390\u0391\7]\2\2\u0391\u0399\7Y\2\2\u0392\u0393\7\\\2"+
		"\2\u0393\u0399\7Y\2\2\u0394\u0399\7Z\2\2\u0395\u0399\7[\2\2\u0396\u0399"+
		"\7\\\2\2\u0397\u0399\7]\2\2\u0398\u0390\3\2\2\2\u0398\u0392\3\2\2\2\u0398"+
		"\u0394\3\2\2\2\u0398\u0395\3\2\2\2\u0398\u0396\3\2\2\2\u0398\u0397\3\2"+
		"\2\2\u0399\u039a\3\2\2\2\u039a\u03db\5\u00aaV\16\u039b\u039c\f\13\2\2"+
		"\u039c\u039d\t\n\2\2\u039d\u03db\5\u00aaV\f\u039e\u039f\f\n\2\2\u039f"+
		"\u03a0\7o\2\2\u03a0\u03db\5\u00aaV\13\u03a1\u03a2\f\t\2\2\u03a2\u03a3"+
		"\7q\2\2\u03a3\u03db\5\u00aaV\n\u03a4\u03a5\f\b\2\2\u03a5\u03a6\7p\2\2"+
		"\u03a6\u03db\5\u00aaV\t\u03a7\u03a8\f\7\2\2\u03a8\u03a9\7g\2\2\u03a9\u03db"+
		"\5\u00aaV\b\u03aa\u03ab\f\6\2\2\u03ab\u03ac\7h\2\2\u03ac\u03db\5\u00aa"+
		"V\7\u03ad\u03ae\f\5\2\2\u03ae\u03af\7`\2\2\u03af\u03b0\5\u00aaV\2\u03b0"+
		"\u03b1\7a\2\2\u03b1\u03b2\5\u00aaV\6\u03b2\u03db\3\2\2\2\u03b3\u03b4\f"+
		"\4\2\2\u03b4\u03b5\t\13\2\2\u03b5\u03db\5\u00aaV\4\u03b6\u03b7\f\34\2"+
		"\2\u03b7\u03b8\7X\2\2\u03b8\u03db\5\u00d8m\2\u03b9\u03ba\f\33\2\2\u03ba"+
		"\u03bb\7X\2\2\u03bb\u03db\7(\2\2\u03bc\u03bd\f\32\2\2\u03bd\u03be\7X\2"+
		"\2\u03be\u03c0\7\35\2\2\u03bf\u03c1\5\u00c6d\2\u03c0\u03bf\3\2\2\2\u03c0"+
		"\u03c1\3\2\2\2\u03c1\u03c2\3\2\2\2\u03c2\u03db\5\u00b4[\2\u03c3\u03c4"+
		"\f\31\2\2\u03c4\u03c5\7X\2\2\u03c5\u03c6\7&\2\2\u03c6\u03db\5\u00ccg\2"+
		"\u03c7\u03c8\f\30\2\2\u03c8\u03c9\7X\2\2\u03c9\u03db\5\u00c4c\2\u03ca"+
		"\u03cb\f\27\2\2\u03cb\u03cc\7T\2\2\u03cc\u03cd\5\u00aaV\2\u03cd\u03ce"+
		"\7U\2\2\u03ce\u03db\3\2\2\2\u03cf\u03d0\f\26\2\2\u03d0\u03d2\7P\2\2\u03d1"+
		"\u03d3\5\u00a8U\2\u03d2\u03d1\3\2\2\2\u03d2\u03d3\3\2\2\2\u03d3\u03d4"+
		"\3\2\2\2\u03d4\u03db\7Q\2\2\u03d5\u03d6\f\23\2\2\u03d6\u03db\t\f\2\2\u03d7"+
		"\u03d8\f\f\2\2\u03d8\u03d9\7\31\2\2\u03d9\u03db\5B\"\2\u03da\u037e\3\2"+
		"\2\2\u03da\u0381\3\2\2\2\u03da\u0384\3\2\2\2\u03da\u038f\3\2\2\2\u03da"+
		"\u039b\3\2\2\2\u03da\u039e\3\2\2\2\u03da\u03a1\3\2\2\2\u03da\u03a4\3\2"+
		"\2\2\u03da\u03a7\3\2\2\2\u03da\u03aa\3\2\2\2\u03da\u03ad\3\2\2\2\u03da"+
		"\u03b3\3\2\2\2\u03da\u03b6\3\2\2\2\u03da\u03b9\3\2\2\2\u03da\u03bc\3\2"+
		"\2\2\u03da\u03c3\3\2\2\2\u03da\u03c7\3\2\2\2\u03da\u03ca\3\2\2\2\u03da"+
		"\u03cf\3\2\2\2\u03da\u03d5\3\2\2\2\u03da\u03d7\3\2\2\2\u03db\u03de\3\2"+
		"\2\2\u03dc\u03da\3\2\2\2\u03dc\u03dd\3\2\2\2\u03dd\u00ab\3\2\2\2\u03de"+
		"\u03dc\3\2\2\2\u03df\u03e0\7P\2\2\u03e0\u03e1\5\u00aaV\2\u03e1\u03e2\7"+
		"Q\2\2\u03e2\u03f6\3\2\2\2\u03e3\u03f6\7(\2\2\u03e4\u03f6\7&\2\2\u03e5"+
		"\u03f6\5V,\2\u03e6\u03f6\5\u00d8m\2\u03e7\u03e8\5B\"\2\u03e8\u03e9\7X"+
		"\2\2\u03e9\u03ea\7\t\2\2\u03ea\u03f6\3\2\2\2\u03eb\u03ec\7,\2\2\u03ec"+
		"\u03ed\7X\2\2\u03ed\u03f6\7\t\2\2\u03ee\u03f2\5\u00c6d\2\u03ef\u03f3\5"+
		"\u00ceh\2\u03f0\u03f1\7(\2\2\u03f1\u03f3\5\u00d0i\2\u03f2\u03ef\3\2\2"+
		"\2\u03f2\u03f0\3\2\2\2\u03f3\u03f6\3\2\2\2\u03f4\u03f6\5\u00d2j\2\u03f5"+
		"\u03df\3\2\2\2\u03f5\u03e3\3\2\2\2\u03f5\u03e4\3\2\2\2\u03f5\u03e5\3\2"+
		"\2\2\u03f5\u03e6\3\2\2\2\u03f5\u03e7\3\2\2\2\u03f5\u03eb\3\2\2\2\u03f5"+
		"\u03ee\3\2\2\2\u03f5\u03f4\3\2\2\2\u03f6\u00ad\3\2\2\2\u03f7\u03f8\5\u00c6"+
		"d\2\u03f8\u03f9\5\u00b0Y\2\u03f9\u03fa\5\u00c2b\2\u03fa\u0403\3\2\2\2"+
		"\u03fb\u0400\5\u00b0Y\2\u03fc\u0401\5\u00b6\\\2\u03fd\u0401\5\u00c2b\2"+
		"\u03fe\u0401\5\u00b8]\2\u03ff\u0401\5\u00bc_\2\u0400\u03fc\3\2\2\2\u0400"+
		"\u03fd\3\2\2\2\u0400\u03fe\3\2\2\2\u0400\u03ff\3\2\2\2\u0401\u0403\3\2"+
		"\2\2\u0402\u03f7\3\2\2\2\u0402\u03fb\3\2\2\2\u0403\u00af\3\2\2\2\u0404"+
		"\u0409\5\u00b2Z\2\u0405\u0406\7X\2\2\u0406\u0408\5\u00b2Z\2\u0407\u0405"+
		"\3\2\2\2\u0408\u040b\3\2\2\2\u0409\u0407\3\2\2\2\u0409\u040a\3\2\2\2\u040a"+
		"\u040e\3\2\2\2\u040b\u0409\3\2\2\2\u040c\u040e\5H%\2\u040d\u0404\3\2\2"+
		"\2\u040d\u040c\3\2\2\2\u040e\u00b1\3\2\2\2\u040f\u0411\5\u00d8m\2\u0410"+
		"\u0412\5\u00c8e\2\u0411\u0410\3\2\2\2\u0411\u0412\3\2\2\2\u0412\u00b3"+
		"\3\2\2\2\u0413\u0415\5\u00d8m\2\u0414\u0416\5\u00caf\2\u0415\u0414\3\2"+
		"\2\2\u0415\u0416\3\2\2\2\u0416\u0417\3\2\2\2\u0417\u0418\5\u00c2b\2\u0418"+
		"\u00b5\3\2\2\2\u0419\u042b\7T\2\2\u041a\u041b\7U\2\2\u041b\u041c\5D#\2"+
		"\u041c\u041d\5@!\2\u041d\u042c\3\2\2\2\u041e\u041f\5\u00aaV\2\u041f\u0426"+
		"\7U\2\2\u0420\u0421\7T\2\2\u0421\u0422\5\u00aaV\2\u0422\u0423\7U\2\2\u0423"+
		"\u0425\3\2\2\2\u0424\u0420\3\2\2\2\u0425\u0428\3\2\2\2\u0426\u0424\3\2"+
		"\2\2\u0426\u0427\3\2\2\2\u0427\u0429\3\2\2\2\u0428\u0426\3\2\2\2\u0429"+
		"\u042a\5D#\2\u042a\u042c\3\2\2\2\u042b\u041a\3\2\2\2\u042b\u041e\3\2\2"+
		"\2\u042c\u00b7\3\2\2\2\u042d\u042e\7R\2\2\u042e\u0433\5\u00ba^\2\u042f"+
		"\u0430\7W\2\2\u0430\u0432\5\u00ba^\2\u0431\u042f\3\2\2\2\u0432\u0435\3"+
		"\2\2\2\u0433\u0431\3\2\2\2\u0433\u0434\3\2\2\2\u0434\u0436\3\2\2\2\u0435"+
		"\u0433\3\2\2\2\u0436\u0437\7S\2\2\u0437\u00b9\3\2\2\2\u0438\u0439\5\u00c0"+
		"a\2\u0439\u043a\7s\2\2\u043a\u043b\5\u00be`\2\u043b\u00bb\3\2\2\2\u043c"+
		"\u043d\7R\2\2\u043d\u0442\5\u00be`\2\u043e\u043f\7W\2\2\u043f\u0441\5"+
		"\u00be`\2\u0440\u043e\3\2\2\2\u0441\u0444\3\2\2\2\u0442\u0440\3\2\2\2"+
		"\u0442\u0443\3\2\2\2\u0443\u0445\3\2\2\2\u0444\u0442\3\2\2\2\u0445\u0446"+
		"\7S\2\2\u0446\u00bd\3\2\2\2\u0447\u044a\5V,\2\u0448\u044a\5\u00aaV\2\u0449"+
		"\u0447\3\2\2\2\u0449\u0448\3\2\2\2\u044a\u00bf\3\2\2\2\u044b\u044e\5\u00d8"+
		"m\2\u044c\u044e\5\u00aaV\2\u044d\u044b\3\2\2\2\u044d\u044c\3\2\2\2\u044e"+
		"\u00c1\3\2\2\2\u044f\u0456\5\u00d0i\2\u0450\u0452\7R\2\2\u0451\u0453\5"+
		"\u00a8U\2\u0452\u0451\3\2\2\2\u0452\u0453\3\2\2\2\u0453\u0454\3\2\2\2"+
		"\u0454\u0456\7S\2\2\u0455\u044f\3\2\2\2\u0455\u0450\3\2\2\2\u0456\u00c3"+
		"\3\2\2\2\u0457\u0458\5\u00c6d\2\u0458\u0459\5\u00ceh\2\u0459\u00c5\3\2"+
		"\2\2\u045a\u045b\7]\2\2\u045b\u045c\5\32\16\2\u045c\u045d\7\\\2\2\u045d"+
		"\u00c7\3\2\2\2\u045e\u045f\7]\2\2\u045f\u0462\7\\\2\2\u0460\u0462\5J&"+
		"\2\u0461\u045e\3\2\2\2\u0461\u0460\3\2\2\2\u0462\u00c9\3\2\2\2\u0463\u0464"+
		"\7]\2\2\u0464\u0467\7\\\2\2\u0465\u0467\5\u00c6d\2\u0466\u0463\3\2\2\2"+
		"\u0466\u0465\3\2\2\2\u0467\u00cb\3\2\2\2\u0468\u046f\5\u00d0i\2\u0469"+
		"\u046a\7X\2\2\u046a\u046c\5\u00d8m\2\u046b\u046d\5\u00d0i\2\u046c\u046b"+
		"\3\2\2\2\u046c\u046d\3\2\2\2\u046d\u046f\3\2\2\2\u046e\u0468\3\2\2\2\u046e"+
		"\u0469\3\2\2\2\u046f\u00cd\3\2\2\2\u0470\u0471\7&\2\2\u0471\u0476\5\u00cc"+
		"g\2\u0472\u0473\5\u00d8m\2\u0473\u0474\5\u00d0i\2\u0474\u0476\3\2\2\2"+
		"\u0475\u0470\3\2\2\2\u0475\u0472\3\2\2\2\u0476\u00cf\3\2\2\2\u0477\u0479"+
		"\7P\2\2\u0478\u047a\5\u00a8U\2\u0479\u0478\3\2\2\2\u0479\u047a\3\2\2\2"+
		"\u047a\u047b\3\2\2\2\u047b\u047c\7Q\2\2\u047c\u00d1\3\2\2\2\u047d\u0482"+
		"\7T\2\2\u047e\u0481\5\u00d2j\2\u047f\u0481\n\r\2\2\u0480\u047e\3\2\2\2"+
		"\u0480\u047f\3\2\2\2\u0481\u0484\3\2\2\2\u0482\u0483\3\2\2\2\u0482\u0480"+
		"\3\2\2\2\u0483\u0485\3\2\2\2\u0484\u0482\3\2\2\2\u0485\u0486\7U\2\2\u0486"+
		"\u00d3\3\2\2\2\u0487\u048b\7I\2\2\u0488\u0489\7F\2\2\u0489\u048b\7H\2"+
		"\2\u048a\u0487\3\2\2\2\u048a\u0488\3\2\2\2\u048b\u00d5\3\2\2\2\u048c\u0490"+
		"\7J\2\2\u048d\u048e\7G\2\2\u048e\u0490\7H\2\2\u048f\u048c\3\2\2\2\u048f"+
		"\u048d\3\2\2\2\u0490\u00d7\3\2\2\2\u0491\u0492\t\16\2\2\u0492\u00d9\3"+
		"\2\2\2}\u00dd\u00e5\u00ec\u00f3\u00f8\u00fc\u010c\u010e\u0113\u0119\u011d"+
		"\u0126\u012d\u0131\u0134\u0137\u0140\u0146\u014b\u014e\u0154\u015b\u0164"+
		"\u016b\u0174\u017b\u0181\u0185\u018e\u0193\u0198\u019d\u01a3\u01b4\u01bb"+
		"\u01c0\u01c7\u01cf\u01d9\u01e1\u01ec\u01f2\u01f6\u01fe\u0202\u0204\u020e"+
		"\u0214\u0219\u021e\u0222\u0230\u0235\u023e\u0244\u024f\u0259\u025c\u0260"+
		"\u0265\u026f\u0277\u027a\u027d\u0285\u0290\u02ac\u02b3\u02ca\u02cd\u02d0"+
		"\u02d4\u02de\u02e4\u02fb\u0307\u030b\u031b\u0320\u0325\u032a\u0331\u033e"+
		"\u0346\u034a\u034e\u0350\u0354\u0359\u036c\u037c\u038c\u0398\u03c0\u03d2"+
		"\u03da\u03dc\u03f2\u03f5\u0400\u0402\u0409\u040d\u0411\u0415\u0426\u042b"+
		"\u0433\u0442\u0449\u044d\u0452\u0455\u0461\u0466\u046c\u046e\u0475\u0479"+
		"\u0480\u0482\u048a\u048f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}