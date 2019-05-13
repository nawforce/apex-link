// Generated from /Users/Kevin/ApexLink/src/main/antlr4/com/nawforce/parsers/ApexLexer.g4 by ANTLR 4.7.2
package com.nawforce.parsers;

import java.util.*; 

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ApexLexer extends Lexer {
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
		WHITESPACE_CHANNEL=2, COMMENT_CHANNEL=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN", "WHITESPACE_CHANNEL", "COMMENT_CHANNEL"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ABSTRACT", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", "CONST", "CONTINUE", 
			"DEFAULT", "DELETE", "DO", "ELSE", "ENUM", "EXTENDS", "FINAL", "FINALLY", 
			"FLOAT", "FOR", "GET", "GLOBAL", "GOTO", "IF", "IMPLEMENTS", "INHERITED", 
			"INSERT", "INSTANCEOF", "INTERFACE", "MERGE", "NATIVE", "NEW", "NULL", 
			"ON", "OVERRIDE", "PACKAGE", "PRIVATE", "PROTECTED", "PUBLIC", "RETURN", 
			"RUNAS", "SELECT", "SET", "SHARING", "SHORT", "STATIC", "SUPER", "SWITCH", 
			"TESTMETHOD", "THIS", "THROW", "TRANSIENT", "TRY", "UNDELETE", "UPDATE", 
			"UPSERT", "VIRTUAL", "VOID", "WEBSERVICE", "WHEN", "WHILE", "WITH", "WITHOUT", 
			"IntegerLiteral", "NumberLiteral", "HexCharacter", "Digit", "BooleanLiteral", 
			"StringLiteral", "StringCharacters", "StringCharacter", "EscapeSequence", 
			"NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "LE", "GE", "GT", "LT", "BANG", "TILDE", 
			"QUESTION", "COLON", "EQUAL", "TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", 
			"TRIPLENOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", 
			"MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
			"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", 
			"JavaLetter", "JavaLetterOrDigit", "AT", "WS", "DOC_COMMENT", "COMMENT", 
			"LINE_COMMENT"
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


	    public void clearCache() {
	        _interp.clearDFA();
	    }


	public ApexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ApexLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 119:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 120:
			return JavaLetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean JavaLetter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean JavaLetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2y\u03af\b\1\4\2\t"+
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
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3"+
		" \3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3"+
		"#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3&\3"+
		"&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3"+
		"+\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3"+
		"/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\61"+
		"\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\38\38\39\39\39\39\39\3:\3"+
		":\3:\3:\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3<\3=\3=\3"+
		"=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3>\3?\3?\7?\u02a5\n?\f?\16?\u02a8\13?\3?"+
		"\5?\u02ab\n?\3@\7@\u02ae\n@\f@\16@\u02b1\13@\3@\3@\3@\7@\u02b6\n@\f@\16"+
		"@\u02b9\13@\3@\5@\u02bc\n@\3A\3A\5A\u02c0\nA\3B\3B\3C\3C\3C\3C\3C\3C\3"+
		"C\3C\3C\5C\u02cd\nC\3D\3D\5D\u02d1\nD\3D\3D\3E\6E\u02d6\nE\rE\16E\u02d7"+
		"\3F\3F\5F\u02dc\nF\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\5G\u02e8\nG\3H\3H\3I"+
		"\3I\3J\3J\3K\3K\3L\3L\3M\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3R\3S\3S\3S\3T"+
		"\3T\3T\3U\3U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3[\3\\\3\\\3\\\3\\\3"+
		"]\3]\3]\3^\3^\3^\3_\3_\3_\3_\3`\3`\3`\3a\3a\3a\3b\3b\3b\3c\3c\3c\3d\3"+
		"d\3e\3e\3f\3f\3g\3g\3h\3h\3i\3i\3j\3j\3k\3k\3l\3l\3l\3m\3m\3m\3n\3n\3"+
		"n\3o\3o\3o\3p\3p\3p\3q\3q\3q\3r\3r\3r\3s\3s\3s\3t\3t\3t\3u\3u\3u\3u\3"+
		"v\3v\3v\3v\3w\3w\3w\3w\3w\3x\3x\7x\u0369\nx\fx\16x\u036c\13x\3y\3y\3y"+
		"\3y\3y\3y\5y\u0374\ny\3z\3z\3z\3z\3z\3z\5z\u037c\nz\3{\3{\3|\6|\u0381"+
		"\n|\r|\16|\u0382\3|\3|\3}\3}\3}\3}\3}\3}\7}\u038d\n}\f}\16}\u0390\13}"+
		"\3}\3}\3}\3}\3}\3~\3~\3~\3~\7~\u039b\n~\f~\16~\u039e\13~\3~\3~\3~\3~\3"+
		"~\3\177\3\177\3\177\3\177\7\177\u03a9\n\177\f\177\16\177\u03ac\13\177"+
		"\3\177\3\177\4\u038e\u039c\2\u0080\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.["+
		"/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081\2\u0083"+
		"\2\u0085B\u0087C\u0089\2\u008b\2\u008d\2\u008fD\u0091E\u0093F\u0095G\u0097"+
		"H\u0099I\u009bJ\u009dK\u009fL\u00a1M\u00a3N\u00a5O\u00a7P\u00a9Q\u00ab"+
		"R\u00adS\u00afT\u00b1U\u00b3V\u00b5W\u00b7X\u00b9Y\u00bbZ\u00bd[\u00bf"+
		"\\\u00c1]\u00c3^\u00c5_\u00c7`\u00c9a\u00cbb\u00cdc\u00cfd\u00d1e\u00d3"+
		"f\u00d5g\u00d7h\u00d9i\u00dbj\u00ddk\u00dfl\u00e1m\u00e3n\u00e5o\u00e7"+
		"p\u00e9q\u00ebr\u00eds\u00eft\u00f1\2\u00f3\2\u00f5u\u00f7v\u00f9w\u00fb"+
		"x\u00fdy\3\2\16\4\2NNnn\4\2FFff\3\2\62;\4\2))^^\n\2$$))^^ddhhppttvv\6"+
		"\2&&C\\aac|\4\2\2\u0101\ud802\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7"+
		"\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u03bb\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2"+
		"q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3"+
		"\2\2\2\2\177\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u008f\3\2\2\2\2"+
		"\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099"+
		"\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2"+
		"\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2\2\2\u00ab"+
		"\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\2\u00b3\3\2\2"+
		"\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2\2\2\u00bb\3\2\2\2\2\u00bd"+
		"\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3\3\2\2\2\2\u00c5\3\2\2"+
		"\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2\2\2\u00cd\3\2\2\2\2\u00cf"+
		"\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5\3\2\2\2\2\u00d7\3\2\2"+
		"\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2\2\2\u00df\3\2\2\2\2\u00e1"+
		"\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7\3\2\2\2\2\u00e9\3\2\2"+
		"\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7"+
		"\3\2\2\2\2\u00f9\3\2\2\2\2\u00fb\3\2\2\2\2\u00fd\3\2\2\2\3\u00ff\3\2\2"+
		"\2\5\u0108\3\2\2\2\7\u010e\3\2\2\2\t\u0113\3\2\2\2\13\u0119\3\2\2\2\r"+
		"\u011e\3\2\2\2\17\u0124\3\2\2\2\21\u012a\3\2\2\2\23\u0133\3\2\2\2\25\u013b"+
		"\3\2\2\2\27\u0142\3\2\2\2\31\u0145\3\2\2\2\33\u014a\3\2\2\2\35\u014f\3"+
		"\2\2\2\37\u0157\3\2\2\2!\u015d\3\2\2\2#\u0165\3\2\2\2%\u016b\3\2\2\2\'"+
		"\u016f\3\2\2\2)\u0173\3\2\2\2+\u017a\3\2\2\2-\u017f\3\2\2\2/\u0182\3\2"+
		"\2\2\61\u018d\3\2\2\2\63\u0197\3\2\2\2\65\u019e\3\2\2\2\67\u01a9\3\2\2"+
		"\29\u01b3\3\2\2\2;\u01b9\3\2\2\2=\u01c0\3\2\2\2?\u01c4\3\2\2\2A\u01c9"+
		"\3\2\2\2C\u01cc\3\2\2\2E\u01d5\3\2\2\2G\u01dd\3\2\2\2I\u01e5\3\2\2\2K"+
		"\u01ef\3\2\2\2M\u01f6\3\2\2\2O\u01fd\3\2\2\2Q\u020a\3\2\2\2S\u0211\3\2"+
		"\2\2U\u0215\3\2\2\2W\u021d\3\2\2\2Y\u0223\3\2\2\2[\u022a\3\2\2\2]\u0230"+
		"\3\2\2\2_\u0237\3\2\2\2a\u0242\3\2\2\2c\u0247\3\2\2\2e\u024d\3\2\2\2g"+
		"\u0257\3\2\2\2i\u025b\3\2\2\2k\u0264\3\2\2\2m\u026b\3\2\2\2o\u0272\3\2"+
		"\2\2q\u027a\3\2\2\2s\u027f\3\2\2\2u\u028a\3\2\2\2w\u028f\3\2\2\2y\u0295"+
		"\3\2\2\2{\u029a\3\2\2\2}\u02a2\3\2\2\2\177\u02af\3\2\2\2\u0081\u02bf\3"+
		"\2\2\2\u0083\u02c1\3\2\2\2\u0085\u02cc\3\2\2\2\u0087\u02ce\3\2\2\2\u0089"+
		"\u02d5\3\2\2\2\u008b\u02db\3\2\2\2\u008d\u02e7\3\2\2\2\u008f\u02e9\3\2"+
		"\2\2\u0091\u02eb\3\2\2\2\u0093\u02ed\3\2\2\2\u0095\u02ef\3\2\2\2\u0097"+
		"\u02f1\3\2\2\2\u0099\u02f3\3\2\2\2\u009b\u02f5\3\2\2\2\u009d\u02f7\3\2"+
		"\2\2\u009f\u02f9\3\2\2\2\u00a1\u02fb\3\2\2\2\u00a3\u02fd\3\2\2\2\u00a5"+
		"\u02ff\3\2\2\2\u00a7\u0302\3\2\2\2\u00a9\u0305\3\2\2\2\u00ab\u0307\3\2"+
		"\2\2\u00ad\u0309\3\2\2\2\u00af\u030b\3\2\2\2\u00b1\u030d\3\2\2\2\u00b3"+
		"\u030f\3\2\2\2\u00b5\u0311\3\2\2\2\u00b7\u0314\3\2\2\2\u00b9\u0318\3\2"+
		"\2\2\u00bb\u031b\3\2\2\2\u00bd\u031e\3\2\2\2\u00bf\u0322\3\2\2\2\u00c1"+
		"\u0325\3\2\2\2\u00c3\u0328\3\2\2\2\u00c5\u032b\3\2\2\2\u00c7\u032e\3\2"+
		"\2\2\u00c9\u0330\3\2\2\2\u00cb\u0332\3\2\2\2\u00cd\u0334\3\2\2\2\u00cf"+
		"\u0336\3\2\2\2\u00d1\u0338\3\2\2\2\u00d3\u033a\3\2\2\2\u00d5\u033c\3\2"+
		"\2\2\u00d7\u033e\3\2\2\2\u00d9\u0341\3\2\2\2\u00db\u0344\3\2\2\2\u00dd"+
		"\u0347\3\2\2\2\u00df\u034a\3\2\2\2\u00e1\u034d\3\2\2\2\u00e3\u0350\3\2"+
		"\2\2\u00e5\u0353\3\2\2\2\u00e7\u0356\3\2\2\2\u00e9\u0359\3\2\2\2\u00eb"+
		"\u035d\3\2\2\2\u00ed\u0361\3\2\2\2\u00ef\u0366\3\2\2\2\u00f1\u0373\3\2"+
		"\2\2\u00f3\u037b\3\2\2\2\u00f5\u037d\3\2\2\2\u00f7\u0380\3\2\2\2\u00f9"+
		"\u0386\3\2\2\2\u00fb\u0396\3\2\2\2\u00fd\u03a4\3\2\2\2\u00ff\u0100\7c"+
		"\2\2\u0100\u0101\7d\2\2\u0101\u0102\7u\2\2\u0102\u0103\7v\2\2\u0103\u0104"+
		"\7t\2\2\u0104\u0105\7c\2\2\u0105\u0106\7e\2\2\u0106\u0107\7v\2\2\u0107"+
		"\4\3\2\2\2\u0108\u0109\7d\2\2\u0109\u010a\7t\2\2\u010a\u010b\7g\2\2\u010b"+
		"\u010c\7c\2\2\u010c\u010d\7m\2\2\u010d\6\3\2\2\2\u010e\u010f\7d\2\2\u010f"+
		"\u0110\7{\2\2\u0110\u0111\7v\2\2\u0111\u0112\7g\2\2\u0112\b\3\2\2\2\u0113"+
		"\u0114\7e\2\2\u0114\u0115\7c\2\2\u0115\u0116\7v\2\2\u0116\u0117\7e\2\2"+
		"\u0117\u0118\7j\2\2\u0118\n\3\2\2\2\u0119\u011a\7e\2\2\u011a\u011b\7j"+
		"\2\2\u011b\u011c\7c\2\2\u011c\u011d\7t\2\2\u011d\f\3\2\2\2\u011e\u011f"+
		"\7e\2\2\u011f\u0120\7n\2\2\u0120\u0121\7c\2\2\u0121\u0122\7u\2\2\u0122"+
		"\u0123\7u\2\2\u0123\16\3\2\2\2\u0124\u0125\7e\2\2\u0125\u0126\7q\2\2\u0126"+
		"\u0127\7p\2\2\u0127\u0128\7u\2\2\u0128\u0129\7v\2\2\u0129\20\3\2\2\2\u012a"+
		"\u012b\7e\2\2\u012b\u012c\7q\2\2\u012c\u012d\7p\2\2\u012d\u012e\7v\2\2"+
		"\u012e\u012f\7k\2\2\u012f\u0130\7p\2\2\u0130\u0131\7w\2\2\u0131\u0132"+
		"\7g\2\2\u0132\22\3\2\2\2\u0133\u0134\7f\2\2\u0134\u0135\7g\2\2\u0135\u0136"+
		"\7h\2\2\u0136\u0137\7c\2\2\u0137\u0138\7w\2\2\u0138\u0139\7n\2\2\u0139"+
		"\u013a\7v\2\2\u013a\24\3\2\2\2\u013b\u013c\7f\2\2\u013c\u013d\7g\2\2\u013d"+
		"\u013e\7n\2\2\u013e\u013f\7g\2\2\u013f\u0140\7v\2\2\u0140\u0141\7g\2\2"+
		"\u0141\26\3\2\2\2\u0142\u0143\7f\2\2\u0143\u0144\7q\2\2\u0144\30\3\2\2"+
		"\2\u0145\u0146\7g\2\2\u0146\u0147\7n\2\2\u0147\u0148\7u\2\2\u0148\u0149"+
		"\7g\2\2\u0149\32\3\2\2\2\u014a\u014b\7g\2\2\u014b\u014c\7p\2\2\u014c\u014d"+
		"\7w\2\2\u014d\u014e\7o\2\2\u014e\34\3\2\2\2\u014f\u0150\7g\2\2\u0150\u0151"+
		"\7z\2\2\u0151\u0152\7v\2\2\u0152\u0153\7g\2\2\u0153\u0154\7p\2\2\u0154"+
		"\u0155\7f\2\2\u0155\u0156\7u\2\2\u0156\36\3\2\2\2\u0157\u0158\7h\2\2\u0158"+
		"\u0159\7k\2\2\u0159\u015a\7p\2\2\u015a\u015b\7c\2\2\u015b\u015c\7n\2\2"+
		"\u015c \3\2\2\2\u015d\u015e\7h\2\2\u015e\u015f\7k\2\2\u015f\u0160\7p\2"+
		"\2\u0160\u0161\7c\2\2\u0161\u0162\7n\2\2\u0162\u0163\7n\2\2\u0163\u0164"+
		"\7{\2\2\u0164\"\3\2\2\2\u0165\u0166\7h\2\2\u0166\u0167\7n\2\2\u0167\u0168"+
		"\7q\2\2\u0168\u0169\7c\2\2\u0169\u016a\7v\2\2\u016a$\3\2\2\2\u016b\u016c"+
		"\7h\2\2\u016c\u016d\7q\2\2\u016d\u016e\7t\2\2\u016e&\3\2\2\2\u016f\u0170"+
		"\7i\2\2\u0170\u0171\7g\2\2\u0171\u0172\7v\2\2\u0172(\3\2\2\2\u0173\u0174"+
		"\7i\2\2\u0174\u0175\7n\2\2\u0175\u0176\7q\2\2\u0176\u0177\7d\2\2\u0177"+
		"\u0178\7c\2\2\u0178\u0179\7n\2\2\u0179*\3\2\2\2\u017a\u017b\7i\2\2\u017b"+
		"\u017c\7q\2\2\u017c\u017d\7v\2\2\u017d\u017e\7q\2\2\u017e,\3\2\2\2\u017f"+
		"\u0180\7k\2\2\u0180\u0181\7h\2\2\u0181.\3\2\2\2\u0182\u0183\7k\2\2\u0183"+
		"\u0184\7o\2\2\u0184\u0185\7r\2\2\u0185\u0186\7n\2\2\u0186\u0187\7g\2\2"+
		"\u0187\u0188\7o\2\2\u0188\u0189\7g\2\2\u0189\u018a\7p\2\2\u018a\u018b"+
		"\7v\2\2\u018b\u018c\7u\2\2\u018c\60\3\2\2\2\u018d\u018e\7k\2\2\u018e\u018f"+
		"\7p\2\2\u018f\u0190\7j\2\2\u0190\u0191\7g\2\2\u0191\u0192\7t\2\2\u0192"+
		"\u0193\7k\2\2\u0193\u0194\7v\2\2\u0194\u0195\7g\2\2\u0195\u0196\7f\2\2"+
		"\u0196\62\3\2\2\2\u0197\u0198\7k\2\2\u0198\u0199\7p\2\2\u0199\u019a\7"+
		"u\2\2\u019a\u019b\7g\2\2\u019b\u019c\7t\2\2\u019c\u019d\7v\2\2\u019d\64"+
		"\3\2\2\2\u019e\u019f\7k\2\2\u019f\u01a0\7p\2\2\u01a0\u01a1\7u\2\2\u01a1"+
		"\u01a2\7v\2\2\u01a2\u01a3\7c\2\2\u01a3\u01a4\7p\2\2\u01a4\u01a5\7e\2\2"+
		"\u01a5\u01a6\7g\2\2\u01a6\u01a7\7q\2\2\u01a7\u01a8\7h\2\2\u01a8\66\3\2"+
		"\2\2\u01a9\u01aa\7k\2\2\u01aa\u01ab\7p\2\2\u01ab\u01ac\7v\2\2\u01ac\u01ad"+
		"\7g\2\2\u01ad\u01ae\7t\2\2\u01ae\u01af\7h\2\2\u01af\u01b0\7c\2\2\u01b0"+
		"\u01b1\7e\2\2\u01b1\u01b2\7g\2\2\u01b28\3\2\2\2\u01b3\u01b4\7o\2\2\u01b4"+
		"\u01b5\7g\2\2\u01b5\u01b6\7t\2\2\u01b6\u01b7\7i\2\2\u01b7\u01b8\7g\2\2"+
		"\u01b8:\3\2\2\2\u01b9\u01ba\7p\2\2\u01ba\u01bb\7c\2\2\u01bb\u01bc\7v\2"+
		"\2\u01bc\u01bd\7k\2\2\u01bd\u01be\7x\2\2\u01be\u01bf\7g\2\2\u01bf<\3\2"+
		"\2\2\u01c0\u01c1\7p\2\2\u01c1\u01c2\7g\2\2\u01c2\u01c3\7y\2\2\u01c3>\3"+
		"\2\2\2\u01c4\u01c5\7p\2\2\u01c5\u01c6\7w\2\2\u01c6\u01c7\7n\2\2\u01c7"+
		"\u01c8\7n\2\2\u01c8@\3\2\2\2\u01c9\u01ca\7q\2\2\u01ca\u01cb\7p\2\2\u01cb"+
		"B\3\2\2\2\u01cc\u01cd\7q\2\2\u01cd\u01ce\7x\2\2\u01ce\u01cf\7g\2\2\u01cf"+
		"\u01d0\7t\2\2\u01d0\u01d1\7t\2\2\u01d1\u01d2\7k\2\2\u01d2\u01d3\7f\2\2"+
		"\u01d3\u01d4\7g\2\2\u01d4D\3\2\2\2\u01d5\u01d6\7r\2\2\u01d6\u01d7\7c\2"+
		"\2\u01d7\u01d8\7e\2\2\u01d8\u01d9\7m\2\2\u01d9\u01da\7c\2\2\u01da\u01db"+
		"\7i\2\2\u01db\u01dc\7g\2\2\u01dcF\3\2\2\2\u01dd\u01de\7r\2\2\u01de\u01df"+
		"\7t\2\2\u01df\u01e0\7k\2\2\u01e0\u01e1\7x\2\2\u01e1\u01e2\7c\2\2\u01e2"+
		"\u01e3\7v\2\2\u01e3\u01e4\7g\2\2\u01e4H\3\2\2\2\u01e5\u01e6\7r\2\2\u01e6"+
		"\u01e7\7t\2\2\u01e7\u01e8\7q\2\2\u01e8\u01e9\7v\2\2\u01e9\u01ea\7g\2\2"+
		"\u01ea\u01eb\7e\2\2\u01eb\u01ec\7v\2\2\u01ec\u01ed\7g\2\2\u01ed\u01ee"+
		"\7f\2\2\u01eeJ\3\2\2\2\u01ef\u01f0\7r\2\2\u01f0\u01f1\7w\2\2\u01f1\u01f2"+
		"\7d\2\2\u01f2\u01f3\7n\2\2\u01f3\u01f4\7k\2\2\u01f4\u01f5\7e\2\2\u01f5"+
		"L\3\2\2\2\u01f6\u01f7\7t\2\2\u01f7\u01f8\7g\2\2\u01f8\u01f9\7v\2\2\u01f9"+
		"\u01fa\7w\2\2\u01fa\u01fb\7t\2\2\u01fb\u01fc\7p\2\2\u01fcN\3\2\2\2\u01fd"+
		"\u01fe\7u\2\2\u01fe\u01ff\7{\2\2\u01ff\u0200\7u\2\2\u0200\u0201\7v\2\2"+
		"\u0201\u0202\7g\2\2\u0202\u0203\7o\2\2\u0203\u0204\7\60\2\2\u0204\u0205"+
		"\7t\2\2\u0205\u0206\7w\2\2\u0206\u0207\7p\2\2\u0207\u0208\7c\2\2\u0208"+
		"\u0209\7u\2\2\u0209P\3\2\2\2\u020a\u020b\7u\2\2\u020b\u020c\7g\2\2\u020c"+
		"\u020d\7n\2\2\u020d\u020e\7g\2\2\u020e\u020f\7e\2\2\u020f\u0210\7v\2\2"+
		"\u0210R\3\2\2\2\u0211\u0212\7u\2\2\u0212\u0213\7g\2\2\u0213\u0214\7v\2"+
		"\2\u0214T\3\2\2\2\u0215\u0216\7u\2\2\u0216\u0217\7j\2\2\u0217\u0218\7"+
		"c\2\2\u0218\u0219\7t\2\2\u0219\u021a\7k\2\2\u021a\u021b\7p\2\2\u021b\u021c"+
		"\7i\2\2\u021cV\3\2\2\2\u021d\u021e\7u\2\2\u021e\u021f\7j\2\2\u021f\u0220"+
		"\7q\2\2\u0220\u0221\7t\2\2\u0221\u0222\7v\2\2\u0222X\3\2\2\2\u0223\u0224"+
		"\7u\2\2\u0224\u0225\7v\2\2\u0225\u0226\7c\2\2\u0226\u0227\7v\2\2\u0227"+
		"\u0228\7k\2\2\u0228\u0229\7e\2\2\u0229Z\3\2\2\2\u022a\u022b\7u\2\2\u022b"+
		"\u022c\7w\2\2\u022c\u022d\7r\2\2\u022d\u022e\7g\2\2\u022e\u022f\7t\2\2"+
		"\u022f\\\3\2\2\2\u0230\u0231\7u\2\2\u0231\u0232\7y\2\2\u0232\u0233\7k"+
		"\2\2\u0233\u0234\7v\2\2\u0234\u0235\7e\2\2\u0235\u0236\7j\2\2\u0236^\3"+
		"\2\2\2\u0237\u0238\7v\2\2\u0238\u0239\7g\2\2\u0239\u023a\7u\2\2\u023a"+
		"\u023b\7v\2\2\u023b\u023c\7o\2\2\u023c\u023d\7g\2\2\u023d\u023e\7v\2\2"+
		"\u023e\u023f\7j\2\2\u023f\u0240\7q\2\2\u0240\u0241\7f\2\2\u0241`\3\2\2"+
		"\2\u0242\u0243\7v\2\2\u0243\u0244\7j\2\2\u0244\u0245\7k\2\2\u0245\u0246"+
		"\7u\2\2\u0246b\3\2\2\2\u0247\u0248\7v\2\2\u0248\u0249\7j\2\2\u0249\u024a"+
		"\7t\2\2\u024a\u024b\7q\2\2\u024b\u024c\7y\2\2\u024cd\3\2\2\2\u024d\u024e"+
		"\7v\2\2\u024e\u024f\7t\2\2\u024f\u0250\7c\2\2\u0250\u0251\7p\2\2\u0251"+
		"\u0252\7u\2\2\u0252\u0253\7k\2\2\u0253\u0254\7g\2\2\u0254\u0255\7p\2\2"+
		"\u0255\u0256\7v\2\2\u0256f\3\2\2\2\u0257\u0258\7v\2\2\u0258\u0259\7t\2"+
		"\2\u0259\u025a\7{\2\2\u025ah\3\2\2\2\u025b\u025c\7w\2\2\u025c\u025d\7"+
		"p\2\2\u025d\u025e\7f\2\2\u025e\u025f\7g\2\2\u025f\u0260\7n\2\2\u0260\u0261"+
		"\7g\2\2\u0261\u0262\7v\2\2\u0262\u0263\7g\2\2\u0263j\3\2\2\2\u0264\u0265"+
		"\7w\2\2\u0265\u0266\7r\2\2\u0266\u0267\7f\2\2\u0267\u0268\7c\2\2\u0268"+
		"\u0269\7v\2\2\u0269\u026a\7g\2\2\u026al\3\2\2\2\u026b\u026c\7w\2\2\u026c"+
		"\u026d\7r\2\2\u026d\u026e\7u\2\2\u026e\u026f\7g\2\2\u026f\u0270\7t\2\2"+
		"\u0270\u0271\7v\2\2\u0271n\3\2\2\2\u0272\u0273\7x\2\2\u0273\u0274\7k\2"+
		"\2\u0274\u0275\7t\2\2\u0275\u0276\7v\2\2\u0276\u0277\7w\2\2\u0277\u0278"+
		"\7c\2\2\u0278\u0279\7n\2\2\u0279p\3\2\2\2\u027a\u027b\7x\2\2\u027b\u027c"+
		"\7q\2\2\u027c\u027d\7k\2\2\u027d\u027e\7f\2\2\u027er\3\2\2\2\u027f\u0280"+
		"\7y\2\2\u0280\u0281\7g\2\2\u0281\u0282\7d\2\2\u0282\u0283\7u\2\2\u0283"+
		"\u0284\7g\2\2\u0284\u0285\7t\2\2\u0285\u0286\7x\2\2\u0286\u0287\7k\2\2"+
		"\u0287\u0288\7e\2\2\u0288\u0289\7g\2\2\u0289t\3\2\2\2\u028a\u028b\7y\2"+
		"\2\u028b\u028c\7j\2\2\u028c\u028d\7g\2\2\u028d\u028e\7p\2\2\u028ev\3\2"+
		"\2\2\u028f\u0290\7y\2\2\u0290\u0291\7j\2\2\u0291\u0292\7k\2\2\u0292\u0293"+
		"\7n\2\2\u0293\u0294\7g\2\2\u0294x\3\2\2\2\u0295\u0296\7y\2\2\u0296\u0297"+
		"\7k\2\2\u0297\u0298\7v\2\2\u0298\u0299\7j\2\2\u0299z\3\2\2\2\u029a\u029b"+
		"\7y\2\2\u029b\u029c\7k\2\2\u029c\u029d\7v\2\2\u029d\u029e\7j\2\2\u029e"+
		"\u029f\7q\2\2\u029f\u02a0\7w\2\2\u02a0\u02a1\7v\2\2\u02a1|\3\2\2\2\u02a2"+
		"\u02a6\5\u0083B\2\u02a3\u02a5\5\u0083B\2\u02a4\u02a3\3\2\2\2\u02a5\u02a8"+
		"\3\2\2\2\u02a6\u02a4\3\2\2\2\u02a6\u02a7\3\2\2\2\u02a7\u02aa\3\2\2\2\u02a8"+
		"\u02a6\3\2\2\2\u02a9\u02ab\t\2\2\2\u02aa\u02a9\3\2\2\2\u02aa\u02ab\3\2"+
		"\2\2\u02ab~\3\2\2\2\u02ac\u02ae\5\u0083B\2\u02ad\u02ac\3\2\2\2\u02ae\u02b1"+
		"\3\2\2\2\u02af\u02ad\3\2\2\2\u02af\u02b0\3\2\2\2\u02b0\u02b2\3\2\2\2\u02b1"+
		"\u02af\3\2\2\2\u02b2\u02b3\7\60\2\2\u02b3\u02b7\5\u0083B\2\u02b4\u02b6"+
		"\5\u0083B\2\u02b5\u02b4\3\2\2\2\u02b6\u02b9\3\2\2\2\u02b7\u02b5\3\2\2"+
		"\2\u02b7\u02b8\3\2\2\2\u02b8\u02bb\3\2\2\2\u02b9\u02b7\3\2\2\2\u02ba\u02bc"+
		"\t\3\2\2\u02bb\u02ba\3\2\2\2\u02bb\u02bc\3\2\2\2\u02bc\u0080\3\2\2\2\u02bd"+
		"\u02c0\5\u0083B\2\u02be\u02c0\4ch\2\u02bf\u02bd\3\2\2\2\u02bf\u02be\3"+
		"\2\2\2\u02c0\u0082\3\2\2\2\u02c1\u02c2\t\4\2\2\u02c2\u0084\3\2\2\2\u02c3"+
		"\u02c4\7v\2\2\u02c4\u02c5\7t\2\2\u02c5\u02c6\7w\2\2\u02c6\u02cd\7g\2\2"+
		"\u02c7\u02c8\7h\2\2\u02c8\u02c9\7c\2\2\u02c9\u02ca\7n\2\2\u02ca\u02cb"+
		"\7u\2\2\u02cb\u02cd\7g\2\2\u02cc\u02c3\3\2\2\2\u02cc\u02c7\3\2\2\2\u02cd"+
		"\u0086\3\2\2\2\u02ce\u02d0\7)\2\2\u02cf\u02d1\5\u0089E\2\u02d0\u02cf\3"+
		"\2\2\2\u02d0\u02d1\3\2\2\2\u02d1\u02d2\3\2\2\2\u02d2\u02d3\7)\2\2\u02d3"+
		"\u0088\3\2\2\2\u02d4\u02d6\5\u008bF\2\u02d5\u02d4\3\2\2\2\u02d6\u02d7"+
		"\3\2\2\2\u02d7\u02d5\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u008a\3\2\2\2\u02d9"+
		"\u02dc\n\5\2\2\u02da\u02dc\5\u008dG\2\u02db\u02d9\3\2\2\2\u02db\u02da"+
		"\3\2\2\2\u02dc\u008c\3\2\2\2\u02dd\u02de\7^\2\2\u02de\u02e8\t\6\2\2\u02df"+
		"\u02e0\7^\2\2\u02e0\u02e1\7w\2\2\u02e1\u02e2\3\2\2\2\u02e2\u02e3\5\u0081"+
		"A\2\u02e3\u02e4\5\u0081A\2\u02e4\u02e5\5\u0081A\2\u02e5\u02e6\5\u0081"+
		"A\2\u02e6\u02e8\3\2\2\2\u02e7\u02dd\3\2\2\2\u02e7\u02df\3\2\2\2\u02e8"+
		"\u008e\3\2\2\2\u02e9\u02ea\5? \2\u02ea\u0090\3\2\2\2\u02eb\u02ec\7*\2"+
		"\2\u02ec\u0092\3\2\2\2\u02ed\u02ee\7+\2\2\u02ee\u0094\3\2\2\2\u02ef\u02f0"+
		"\7}\2\2\u02f0\u0096\3\2\2\2\u02f1\u02f2\7\177\2\2\u02f2\u0098\3\2\2\2"+
		"\u02f3\u02f4\7]\2\2\u02f4\u009a\3\2\2\2\u02f5\u02f6\7_\2\2\u02f6\u009c"+
		"\3\2\2\2\u02f7\u02f8\7=\2\2\u02f8\u009e\3\2\2\2\u02f9\u02fa\7.\2\2\u02fa"+
		"\u00a0\3\2\2\2\u02fb\u02fc\7\60\2\2\u02fc\u00a2\3\2\2\2\u02fd\u02fe\7"+
		"?\2\2\u02fe\u00a4\3\2\2\2\u02ff\u0300\7>\2\2\u0300\u0301\7?\2\2\u0301"+
		"\u00a6\3\2\2\2\u0302\u0303\7@\2\2\u0303\u0304\7?\2\2\u0304\u00a8\3\2\2"+
		"\2\u0305\u0306\7@\2\2\u0306\u00aa\3\2\2\2\u0307\u0308\7>\2\2\u0308\u00ac"+
		"\3\2\2\2\u0309\u030a\7#\2\2\u030a\u00ae\3\2\2\2\u030b\u030c\7\u0080\2"+
		"\2\u030c\u00b0\3\2\2\2\u030d\u030e\7A\2\2\u030e\u00b2\3\2\2\2\u030f\u0310"+
		"\7<\2\2\u0310\u00b4\3\2\2\2\u0311\u0312\7?\2\2\u0312\u0313\7?\2\2\u0313"+
		"\u00b6\3\2\2\2\u0314\u0315\7?\2\2\u0315\u0316\7?\2\2\u0316\u0317\7?\2"+
		"\2\u0317\u00b8\3\2\2\2\u0318\u0319\7#\2\2\u0319\u031a\7?\2\2\u031a\u00ba"+
		"\3\2\2\2\u031b\u031c\7>\2\2\u031c\u031d\7@\2\2\u031d\u00bc\3\2\2\2\u031e"+
		"\u031f\7#\2\2\u031f\u0320\7?\2\2\u0320\u0321\7?\2\2\u0321\u00be\3\2\2"+
		"\2\u0322\u0323\7(\2\2\u0323\u0324\7(\2\2\u0324\u00c0\3\2\2\2\u0325\u0326"+
		"\7~\2\2\u0326\u0327\7~\2\2\u0327\u00c2\3\2\2\2\u0328\u0329\7-\2\2\u0329"+
		"\u032a\7-\2\2\u032a\u00c4\3\2\2\2\u032b\u032c\7/\2\2\u032c\u032d\7/\2"+
		"\2\u032d\u00c6\3\2\2\2\u032e\u032f\7-\2\2\u032f\u00c8\3\2\2\2\u0330\u0331"+
		"\7/\2\2\u0331\u00ca\3\2\2\2\u0332\u0333\7,\2\2\u0333\u00cc\3\2\2\2\u0334"+
		"\u0335\7\61\2\2\u0335\u00ce\3\2\2\2\u0336\u0337\7(\2\2\u0337\u00d0\3\2"+
		"\2\2\u0338\u0339\7~\2\2\u0339\u00d2\3\2\2\2\u033a\u033b\7`\2\2\u033b\u00d4"+
		"\3\2\2\2\u033c\u033d\7\'\2\2\u033d\u00d6\3\2\2\2\u033e\u033f\7?\2\2\u033f"+
		"\u0340\7@\2\2\u0340\u00d8\3\2\2\2\u0341\u0342\7-\2\2\u0342\u0343\7?\2"+
		"\2\u0343\u00da\3\2\2\2\u0344\u0345\7/\2\2\u0345\u0346\7?\2\2\u0346\u00dc"+
		"\3\2\2\2\u0347\u0348\7,\2\2\u0348\u0349\7?\2\2\u0349\u00de\3\2\2\2\u034a"+
		"\u034b\7\61\2\2\u034b\u034c\7?\2\2\u034c\u00e0\3\2\2\2\u034d\u034e\7("+
		"\2\2\u034e\u034f\7?\2\2\u034f\u00e2\3\2\2\2\u0350\u0351\7~\2\2\u0351\u0352"+
		"\7?\2\2\u0352\u00e4\3\2\2\2\u0353\u0354\7`\2\2\u0354\u0355\7?\2\2\u0355"+
		"\u00e6\3\2\2\2\u0356\u0357\7\'\2\2\u0357\u0358\7?\2\2\u0358\u00e8\3\2"+
		"\2\2\u0359\u035a\7>\2\2\u035a\u035b\7>\2\2\u035b\u035c\7?\2\2\u035c\u00ea"+
		"\3\2\2\2\u035d\u035e\7@\2\2\u035e\u035f\7@\2\2\u035f\u0360\7?\2\2\u0360"+
		"\u00ec\3\2\2\2\u0361\u0362\7@\2\2\u0362\u0363\7@\2\2\u0363\u0364\7@\2"+
		"\2\u0364\u0365\7?\2\2\u0365\u00ee\3\2\2\2\u0366\u036a\5\u00f1y\2\u0367"+
		"\u0369\5\u00f3z\2\u0368\u0367\3\2\2\2\u0369\u036c\3\2\2\2\u036a\u0368"+
		"\3\2\2\2\u036a\u036b\3\2\2\2\u036b\u00f0\3\2\2\2\u036c\u036a\3\2\2\2\u036d"+
		"\u0374\t\7\2\2\u036e\u036f\n\b\2\2\u036f\u0374\6y\2\2\u0370\u0371\t\t"+
		"\2\2\u0371\u0372\t\n\2\2\u0372\u0374\6y\3\2\u0373\u036d\3\2\2\2\u0373"+
		"\u036e\3\2\2\2\u0373\u0370\3\2\2\2\u0374\u00f2\3\2\2\2\u0375\u037c\t\13"+
		"\2\2\u0376\u0377\n\b\2\2\u0377\u037c\6z\4\2\u0378\u0379\t\t\2\2\u0379"+
		"\u037a\t\n\2\2\u037a\u037c\6z\5\2\u037b\u0375\3\2\2\2\u037b\u0376\3\2"+
		"\2\2\u037b\u0378\3\2\2\2\u037c\u00f4\3\2\2\2\u037d\u037e\7B\2\2\u037e"+
		"\u00f6\3\2\2\2\u037f\u0381\t\f\2\2\u0380\u037f\3\2\2\2\u0381\u0382\3\2"+
		"\2\2\u0382\u0380\3\2\2\2\u0382\u0383\3\2\2\2\u0383\u0384\3\2\2\2\u0384"+
		"\u0385\b|\2\2\u0385\u00f8\3\2\2\2\u0386\u0387\7\61\2\2\u0387\u0388\7,"+
		"\2\2\u0388\u0389\7,\2\2\u0389\u038a\3\2\2\2\u038a\u038e\t\r\2\2\u038b"+
		"\u038d\13\2\2\2\u038c\u038b\3\2\2\2\u038d\u0390\3\2\2\2\u038e\u038f\3"+
		"\2\2\2\u038e\u038c\3\2\2\2\u038f\u0391\3\2\2\2\u0390\u038e\3\2\2\2\u0391"+
		"\u0392\7,\2\2\u0392\u0393\7\61\2\2\u0393\u0394\3\2\2\2\u0394\u0395\b}"+
		"\3\2\u0395\u00fa\3\2\2\2\u0396\u0397\7\61\2\2\u0397\u0398\7,\2\2\u0398"+
		"\u039c\3\2\2\2\u0399\u039b\13\2\2\2\u039a\u0399\3\2\2\2\u039b\u039e\3"+
		"\2\2\2\u039c\u039d\3\2\2\2\u039c\u039a\3\2\2\2\u039d\u039f\3\2\2\2\u039e"+
		"\u039c\3\2\2\2\u039f\u03a0\7,\2\2\u03a0\u03a1\7\61\2\2\u03a1\u03a2\3\2"+
		"\2\2\u03a2\u03a3\b~\3\2\u03a3\u00fc\3\2\2\2\u03a4\u03a5\7\61\2\2\u03a5"+
		"\u03a6\7\61\2\2\u03a6\u03aa\3\2\2\2\u03a7\u03a9\n\r\2\2\u03a8\u03a7\3"+
		"\2\2\2\u03a9\u03ac\3\2\2\2\u03aa\u03a8\3\2\2\2\u03aa\u03ab\3\2\2\2\u03ab"+
		"\u03ad\3\2\2\2\u03ac\u03aa\3\2\2\2\u03ad\u03ae\b\177\3\2\u03ae\u00fe\3"+
		"\2\2\2\25\2\u02a6\u02aa\u02af\u02b7\u02bb\u02bf\u02cc\u02d0\u02d7\u02db"+
		"\u02e7\u036a\u0373\u037b\u0382\u038e\u039c\u03aa\4\2\4\2\2\5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}