// Generated from /Users/kevin/Projects/ApexLink/src/main/antlr4/com/nawforce/parsers/ApexLexer.g4 by ANTLR 4.7.2
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
		ABSTRACT=1, BOOLEAN=2, BREAK=3, BYTE=4, CATCH=5, CHAR=6, CLASS=7, CONST=8, 
		CONTINUE=9, DEFAULT=10, DO=11, DOUBLE=12, ELSE=13, ENUM=14, EXTENDS=15, 
		FINAL=16, FINALLY=17, FLOAT=18, FOR=19, IF=20, GOTO=21, IMPLEMENTS=22, 
		INSTANCEOF=23, INTERFACE=24, LONG=25, NATIVE=26, NEW=27, NULL=28, PACKAGE=29, 
		PRIVATE=30, PROTECTED=31, PUBLIC=32, RETURN=33, SHORT=34, STATIC=35, VIRTUAL=36, 
		SUPER=37, SYNCHRONIZED=38, THIS=39, THROW=40, TRANSIENT=41, TRY=42, VOID=43, 
		VOLATILE=44, WHILE=45, GLOBAL=46, WEBSERVICE=47, SELECT=48, INSERT=49, 
		UPSERT=50, UPDATE=51, DELETE=52, UNDELETE=53, MERGE=54, TESTMETHOD=55, 
		OVERRIDE=56, GET=57, SET=58, BLOB=59, DATE=60, DATETIME=61, DECIMAL=62, 
		ID=63, INTEGER=64, OBJECT=65, STRING=66, TIME=67, RUNAS=68, WITH=69, WITHOUT=70, 
		SHARING=71, INHERITED=72, IntegerLiteral=73, NumberLiteral=74, BooleanLiteral=75, 
		StringLiteral=76, NullLiteral=77, LPAREN=78, RPAREN=79, LBRACE=80, RBRACE=81, 
		LBRACK=82, RBRACK=83, SEMI=84, COMMA=85, DOT=86, ASSIGN=87, LE=88, GE=89, 
		GT=90, LT=91, BANG=92, TILDE=93, QUESTION=94, COLON=95, EQUAL=96, TRIPLEEQUAL=97, 
		NOTEQUAL=98, LESSANDGREATER=99, TRIPLENOTEQUAL=100, AND=101, OR=102, INC=103, 
		DEC=104, ADD=105, SUB=106, MUL=107, DIV=108, BITAND=109, BITOR=110, CARET=111, 
		MOD=112, MAP=113, ADD_ASSIGN=114, SUB_ASSIGN=115, MUL_ASSIGN=116, DIV_ASSIGN=117, 
		AND_ASSIGN=118, OR_ASSIGN=119, XOR_ASSIGN=120, MOD_ASSIGN=121, LSHIFT_ASSIGN=122, 
		RSHIFT_ASSIGN=123, URSHIFT_ASSIGN=124, Identifier=125, AT=126, WS=127, 
		DOC_COMMENT=128, COMMENT=129, LINE_COMMENT=130;
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
			"ABSTRACT", "BOOLEAN", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", "CONST", 
			"CONTINUE", "DEFAULT", "DO", "DOUBLE", "ELSE", "ENUM", "EXTENDS", "FINAL", 
			"FINALLY", "FLOAT", "FOR", "IF", "GOTO", "IMPLEMENTS", "INSTANCEOF", 
			"INTERFACE", "LONG", "NATIVE", "NEW", "NULL", "PACKAGE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "SHORT", "STATIC", "VIRTUAL", "SUPER", "SYNCHRONIZED", 
			"THIS", "THROW", "TRANSIENT", "TRY", "VOID", "VOLATILE", "WHILE", "GLOBAL", 
			"WEBSERVICE", "SELECT", "INSERT", "UPSERT", "UPDATE", "DELETE", "UNDELETE", 
			"MERGE", "TESTMETHOD", "OVERRIDE", "GET", "SET", "BLOB", "DATE", "DATETIME", 
			"DECIMAL", "ID", "INTEGER", "OBJECT", "STRING", "TIME", "RUNAS", "WITH", 
			"WITHOUT", "SHARING", "INHERITED", "IntegerLiteral", "NumberLiteral", 
			"Digit", "BooleanLiteral", "StringLiteral", "StringCharacters", "StringCharacter", 
			"EscapeSequence", "NullLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", "LE", "GE", "GT", 
			"LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", "TRIPLEEQUAL", "NOTEQUAL", 
			"LESSANDGREATER", "TRIPLENOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", 
			"SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", "MOD", "MAP", "ADD_ASSIGN", 
			"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", 
			"XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", 
			"Identifier", "JavaLetter", "JavaLetterOrDigit", "AT", "WS", "DOC_COMMENT", 
			"COMMENT", "LINE_COMMENT"
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
			"'synchronized'", "'this'", "'throw'", "'transient'", "'try'", "'void'", 
			"'volatile'", "'while'", "'global'", "'webservice'", "'select'", "'insert'", 
			"'upsert'", "'update'", "'delete'", "'undelete'", "'merge'", "'testmethod'", 
			"'override'", "'get'", "'set'", "'blob'", "'date'", "'datetime'", "'decimal'", 
			"'id'", "'integer'", "'object'", "'string'", "'time'", "'system.runas'", 
			"'with'", "'without'", "'sharing'", "'inherited'", null, null, null, 
			null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'", 
			"'='", "'<='", "'>='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", 
			"'==='", "'!='", "'<>'", "'!=='", "'&&'", "'||'", "'++'", "'--'", "'+'", 
			"'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'=>'", "'+='", "'-='", 
			"'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", 
			null, "'@'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABSTRACT", "BOOLEAN", "BREAK", "BYTE", "CATCH", "CHAR", "CLASS", 
			"CONST", "CONTINUE", "DEFAULT", "DO", "DOUBLE", "ELSE", "ENUM", "EXTENDS", 
			"FINAL", "FINALLY", "FLOAT", "FOR", "IF", "GOTO", "IMPLEMENTS", "INSTANCEOF", 
			"INTERFACE", "LONG", "NATIVE", "NEW", "NULL", "PACKAGE", "PRIVATE", "PROTECTED", 
			"PUBLIC", "RETURN", "SHORT", "STATIC", "VIRTUAL", "SUPER", "SYNCHRONIZED", 
			"THIS", "THROW", "TRANSIENT", "TRY", "VOID", "VOLATILE", "WHILE", "GLOBAL", 
			"WEBSERVICE", "SELECT", "INSERT", "UPSERT", "UPDATE", "DELETE", "UNDELETE", 
			"MERGE", "TESTMETHOD", "OVERRIDE", "GET", "SET", "BLOB", "DATE", "DATETIME", 
			"DECIMAL", "ID", "INTEGER", "OBJECT", "STRING", "TIME", "RUNAS", "WITH", 
			"WITHOUT", "SHARING", "INHERITED", "IntegerLiteral", "NumberLiteral", 
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
		case 129:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 130:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\u0084\u040a\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4"+
		"I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\t"+
		"T\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_"+
		"\4`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k"+
		"\tk\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv"+
		"\4w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t"+
		"\u0080\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084"+
		"\4\u0085\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089"+
		"\t\u0089\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3"+
		"\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3"+
		"\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'"+
		"\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3.\3"+
		".\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38"+
		"\38\38\38\38\38\38\38\38\39\39\39\39\39\39\39\39\39\3:\3:\3:\3:\3;\3;"+
		"\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3>\3>\3?\3?"+
		"\3?\3?\3?\3?\3?\3?\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3A\3B\3B\3B\3B\3B\3B"+
		"\3B\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\3D\3D\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3F\3F\3F\3F\3F\3G\3G\3G\3G\3G\3G\3G\3G\3H\3H\3H\3H\3H\3H\3H"+
		"\3H\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3J\3J\7J\u030d\nJ\fJ\16J\u0310\13J\3"+
		"J\5J\u0313\nJ\3K\7K\u0316\nK\fK\16K\u0319\13K\3K\3K\3K\7K\u031e\nK\fK"+
		"\16K\u0321\13K\3K\5K\u0324\nK\3L\3L\3M\3M\3M\3M\3M\3M\3M\3M\3M\5M\u0331"+
		"\nM\3N\3N\5N\u0335\nN\3N\3N\3O\6O\u033a\nO\rO\16O\u033b\3P\3P\5P\u0340"+
		"\nP\3Q\3Q\3Q\3R\3R\3S\3S\3T\3T\3U\3U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3["+
		"\3[\3\\\3\\\3]\3]\3]\3^\3^\3^\3_\3_\3`\3`\3a\3a\3b\3b\3c\3c\3d\3d\3e\3"+
		"e\3e\3f\3f\3f\3f\3g\3g\3g\3h\3h\3h\3i\3i\3i\3i\3j\3j\3j\3k\3k\3k\3l\3"+
		"l\3l\3m\3m\3m\3n\3n\3o\3o\3p\3p\3q\3q\3r\3r\3s\3s\3t\3t\3u\3u\3v\3v\3"+
		"v\3w\3w\3w\3x\3x\3x\3y\3y\3y\3z\3z\3z\3{\3{\3{\3|\3|\3|\3}\3}\3}\3~\3"+
		"~\3~\3\177\3\177\3\177\3\177\3\u0080\3\u0080\3\u0080\3\u0080\3\u0081\3"+
		"\u0081\3\u0081\3\u0081\3\u0081\3\u0082\3\u0082\7\u0082\u03c4\n\u0082\f"+
		"\u0082\16\u0082\u03c7\13\u0082\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083"+
		"\3\u0083\5\u0083\u03cf\n\u0083\3\u0084\3\u0084\3\u0084\3\u0084\3\u0084"+
		"\3\u0084\5\u0084\u03d7\n\u0084\3\u0085\3\u0085\3\u0086\6\u0086\u03dc\n"+
		"\u0086\r\u0086\16\u0086\u03dd\3\u0086\3\u0086\3\u0087\3\u0087\3\u0087"+
		"\3\u0087\3\u0087\3\u0087\7\u0087\u03e8\n\u0087\f\u0087\16\u0087\u03eb"+
		"\13\u0087\3\u0087\3\u0087\3\u0087\3\u0087\3\u0087\3\u0088\3\u0088\3\u0088"+
		"\3\u0088\7\u0088\u03f6\n\u0088\f\u0088\16\u0088\u03f9\13\u0088\3\u0088"+
		"\3\u0088\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089\3\u0089\7\u0089"+
		"\u0404\n\u0089\f\u0089\16\u0089\u0407\13\u0089\3\u0089\3\u0089\4\u03e9"+
		"\u03f7\2\u008a\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g"+
		"\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F"+
		"\u008bG\u008dH\u008fI\u0091J\u0093K\u0095L\u0097\2\u0099M\u009bN\u009d"+
		"\2\u009f\2\u00a1\2\u00a3O\u00a5P\u00a7Q\u00a9R\u00abS\u00adT\u00afU\u00b1"+
		"V\u00b3W\u00b5X\u00b7Y\u00b9Z\u00bb[\u00bd\\\u00bf]\u00c1^\u00c3_\u00c5"+
		"`\u00c7a\u00c9b\u00cbc\u00cdd\u00cfe\u00d1f\u00d3g\u00d5h\u00d7i\u00d9"+
		"j\u00dbk\u00ddl\u00dfm\u00e1n\u00e3o\u00e5p\u00e7q\u00e9r\u00ebs\u00ed"+
		"t\u00efu\u00f1v\u00f3w\u00f5x\u00f7y\u00f9z\u00fb{\u00fd|\u00ff}\u0101"+
		"~\u0103\177\u0105\2\u0107\2\u0109\u0080\u010b\u0081\u010d\u0082\u010f"+
		"\u0083\u0111\u0084\3\2\16\4\2NNnn\4\2FFff\3\2\62;\4\2))^^\n\2$$))^^dd"+
		"hhppttvv\6\2&&C\\aac|\4\2\2\u0101\ud802\udc01\3\2\ud802\udc01\3\2\udc02"+
		"\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u0415\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2"+
		"\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I"+
		"\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2"+
		"\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2"+
		"\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o"+
		"\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2"+
		"\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085"+
		"\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0099"+
		"\3\2\2\2\2\u009b\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2"+
		"\2\2\u00a9\3\2\2\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1"+
		"\3\2\2\2\2\u00b3\3\2\2\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2"+
		"\2\2\u00bb\3\2\2\2\2\u00bd\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3"+
		"\3\2\2\2\2\u00c5\3\2\2\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2"+
		"\2\2\u00cd\3\2\2\2\2\u00cf\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5"+
		"\3\2\2\2\2\u00d7\3\2\2\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2"+
		"\2\2\u00df\3\2\2\2\2\u00e1\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7"+
		"\3\2\2\2\2\u00e9\3\2\2\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2"+
		"\2\2\u00f1\3\2\2\2\2\u00f3\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7\3\2\2\2\2\u00f9"+
		"\3\2\2\2\2\u00fb\3\2\2\2\2\u00fd\3\2\2\2\2\u00ff\3\2\2\2\2\u0101\3\2\2"+
		"\2\2\u0103\3\2\2\2\2\u0109\3\2\2\2\2\u010b\3\2\2\2\2\u010d\3\2\2\2\2\u010f"+
		"\3\2\2\2\2\u0111\3\2\2\2\3\u0113\3\2\2\2\5\u011c\3\2\2\2\7\u0124\3\2\2"+
		"\2\t\u012a\3\2\2\2\13\u012f\3\2\2\2\r\u0135\3\2\2\2\17\u013a\3\2\2\2\21"+
		"\u0140\3\2\2\2\23\u0146\3\2\2\2\25\u014f\3\2\2\2\27\u0157\3\2\2\2\31\u015a"+
		"\3\2\2\2\33\u0161\3\2\2\2\35\u0166\3\2\2\2\37\u016b\3\2\2\2!\u0173\3\2"+
		"\2\2#\u0179\3\2\2\2%\u0181\3\2\2\2\'\u0187\3\2\2\2)\u018b\3\2\2\2+\u018e"+
		"\3\2\2\2-\u0193\3\2\2\2/\u019e\3\2\2\2\61\u01a9\3\2\2\2\63\u01b3\3\2\2"+
		"\2\65\u01b8\3\2\2\2\67\u01bf\3\2\2\29\u01c3\3\2\2\2;\u01c8\3\2\2\2=\u01d0"+
		"\3\2\2\2?\u01d8\3\2\2\2A\u01e2\3\2\2\2C\u01e9\3\2\2\2E\u01f0\3\2\2\2G"+
		"\u01f6\3\2\2\2I\u01fd\3\2\2\2K\u0205\3\2\2\2M\u020b\3\2\2\2O\u0218\3\2"+
		"\2\2Q\u021d\3\2\2\2S\u0223\3\2\2\2U\u022d\3\2\2\2W\u0231\3\2\2\2Y\u0236"+
		"\3\2\2\2[\u023f\3\2\2\2]\u0245\3\2\2\2_\u024c\3\2\2\2a\u0257\3\2\2\2c"+
		"\u025e\3\2\2\2e\u0265\3\2\2\2g\u026c\3\2\2\2i\u0273\3\2\2\2k\u027a\3\2"+
		"\2\2m\u0283\3\2\2\2o\u0289\3\2\2\2q\u0294\3\2\2\2s\u029d\3\2\2\2u\u02a1"+
		"\3\2\2\2w\u02a5\3\2\2\2y\u02aa\3\2\2\2{\u02af\3\2\2\2}\u02b8\3\2\2\2\177"+
		"\u02c0\3\2\2\2\u0081\u02c3\3\2\2\2\u0083\u02cb\3\2\2\2\u0085\u02d2\3\2"+
		"\2\2\u0087\u02d9\3\2\2\2\u0089\u02de\3\2\2\2\u008b\u02eb\3\2\2\2\u008d"+
		"\u02f0\3\2\2\2\u008f\u02f8\3\2\2\2\u0091\u0300\3\2\2\2\u0093\u030a\3\2"+
		"\2\2\u0095\u0317\3\2\2\2\u0097\u0325\3\2\2\2\u0099\u0330\3\2\2\2\u009b"+
		"\u0332\3\2\2\2\u009d\u0339\3\2\2\2\u009f\u033f\3\2\2\2\u00a1\u0341\3\2"+
		"\2\2\u00a3\u0344\3\2\2\2\u00a5\u0346\3\2\2\2\u00a7\u0348\3\2\2\2\u00a9"+
		"\u034a\3\2\2\2\u00ab\u034c\3\2\2\2\u00ad\u034e\3\2\2\2\u00af\u0350\3\2"+
		"\2\2\u00b1\u0352\3\2\2\2\u00b3\u0354\3\2\2\2\u00b5\u0356\3\2\2\2\u00b7"+
		"\u0358\3\2\2\2\u00b9\u035a\3\2\2\2\u00bb\u035d\3\2\2\2\u00bd\u0360\3\2"+
		"\2\2\u00bf\u0362\3\2\2\2\u00c1\u0364\3\2\2\2\u00c3\u0366\3\2\2\2\u00c5"+
		"\u0368\3\2\2\2\u00c7\u036a\3\2\2\2\u00c9\u036c\3\2\2\2\u00cb\u036f\3\2"+
		"\2\2\u00cd\u0373\3\2\2\2\u00cf\u0376\3\2\2\2\u00d1\u0379\3\2\2\2\u00d3"+
		"\u037d\3\2\2\2\u00d5\u0380\3\2\2\2\u00d7\u0383\3\2\2\2\u00d9\u0386\3\2"+
		"\2\2\u00db\u0389\3\2\2\2\u00dd\u038b\3\2\2\2\u00df\u038d\3\2\2\2\u00e1"+
		"\u038f\3\2\2\2\u00e3\u0391\3\2\2\2\u00e5\u0393\3\2\2\2\u00e7\u0395\3\2"+
		"\2\2\u00e9\u0397\3\2\2\2\u00eb\u0399\3\2\2\2\u00ed\u039c\3\2\2\2\u00ef"+
		"\u039f\3\2\2\2\u00f1\u03a2\3\2\2\2\u00f3\u03a5\3\2\2\2\u00f5\u03a8\3\2"+
		"\2\2\u00f7\u03ab\3\2\2\2\u00f9\u03ae\3\2\2\2\u00fb\u03b1\3\2\2\2\u00fd"+
		"\u03b4\3\2\2\2\u00ff\u03b8\3\2\2\2\u0101\u03bc\3\2\2\2\u0103\u03c1\3\2"+
		"\2\2\u0105\u03ce\3\2\2\2\u0107\u03d6\3\2\2\2\u0109\u03d8\3\2\2\2\u010b"+
		"\u03db\3\2\2\2\u010d\u03e1\3\2\2\2\u010f\u03f1\3\2\2\2\u0111\u03ff\3\2"+
		"\2\2\u0113\u0114\7c\2\2\u0114\u0115\7d\2\2\u0115\u0116\7u\2\2\u0116\u0117"+
		"\7v\2\2\u0117\u0118\7t\2\2\u0118\u0119\7c\2\2\u0119\u011a\7e\2\2\u011a"+
		"\u011b\7v\2\2\u011b\4\3\2\2\2\u011c\u011d\7d\2\2\u011d\u011e\7q\2\2\u011e"+
		"\u011f\7q\2\2\u011f\u0120\7n\2\2\u0120\u0121\7g\2\2\u0121\u0122\7c\2\2"+
		"\u0122\u0123\7p\2\2\u0123\6\3\2\2\2\u0124\u0125\7d\2\2\u0125\u0126\7t"+
		"\2\2\u0126\u0127\7g\2\2\u0127\u0128\7c\2\2\u0128\u0129\7m\2\2\u0129\b"+
		"\3\2\2\2\u012a\u012b\7d\2\2\u012b\u012c\7{\2\2\u012c\u012d\7v\2\2\u012d"+
		"\u012e\7g\2\2\u012e\n\3\2\2\2\u012f\u0130\7e\2\2\u0130\u0131\7c\2\2\u0131"+
		"\u0132\7v\2\2\u0132\u0133\7e\2\2\u0133\u0134\7j\2\2\u0134\f\3\2\2\2\u0135"+
		"\u0136\7e\2\2\u0136\u0137\7j\2\2\u0137\u0138\7c\2\2\u0138\u0139\7t\2\2"+
		"\u0139\16\3\2\2\2\u013a\u013b\7e\2\2\u013b\u013c\7n\2\2\u013c\u013d\7"+
		"c\2\2\u013d\u013e\7u\2\2\u013e\u013f\7u\2\2\u013f\20\3\2\2\2\u0140\u0141"+
		"\7e\2\2\u0141\u0142\7q\2\2\u0142\u0143\7p\2\2\u0143\u0144\7u\2\2\u0144"+
		"\u0145\7v\2\2\u0145\22\3\2\2\2\u0146\u0147\7e\2\2\u0147\u0148\7q\2\2\u0148"+
		"\u0149\7p\2\2\u0149\u014a\7v\2\2\u014a\u014b\7k\2\2\u014b\u014c\7p\2\2"+
		"\u014c\u014d\7w\2\2\u014d\u014e\7g\2\2\u014e\24\3\2\2\2\u014f\u0150\7"+
		"f\2\2\u0150\u0151\7g\2\2\u0151\u0152\7h\2\2\u0152\u0153\7c\2\2\u0153\u0154"+
		"\7w\2\2\u0154\u0155\7n\2\2\u0155\u0156\7v\2\2\u0156\26\3\2\2\2\u0157\u0158"+
		"\7f\2\2\u0158\u0159\7q\2\2\u0159\30\3\2\2\2\u015a\u015b\7f\2\2\u015b\u015c"+
		"\7q\2\2\u015c\u015d\7w\2\2\u015d\u015e\7d\2\2\u015e\u015f\7n\2\2\u015f"+
		"\u0160\7g\2\2\u0160\32\3\2\2\2\u0161\u0162\7g\2\2\u0162\u0163\7n\2\2\u0163"+
		"\u0164\7u\2\2\u0164\u0165\7g\2\2\u0165\34\3\2\2\2\u0166\u0167\7g\2\2\u0167"+
		"\u0168\7p\2\2\u0168\u0169\7w\2\2\u0169\u016a\7o\2\2\u016a\36\3\2\2\2\u016b"+
		"\u016c\7g\2\2\u016c\u016d\7z\2\2\u016d\u016e\7v\2\2\u016e\u016f\7g\2\2"+
		"\u016f\u0170\7p\2\2\u0170\u0171\7f\2\2\u0171\u0172\7u\2\2\u0172 \3\2\2"+
		"\2\u0173\u0174\7h\2\2\u0174\u0175\7k\2\2\u0175\u0176\7p\2\2\u0176\u0177"+
		"\7c\2\2\u0177\u0178\7n\2\2\u0178\"\3\2\2\2\u0179\u017a\7h\2\2\u017a\u017b"+
		"\7k\2\2\u017b\u017c\7p\2\2\u017c\u017d\7c\2\2\u017d\u017e\7n\2\2\u017e"+
		"\u017f\7n\2\2\u017f\u0180\7{\2\2\u0180$\3\2\2\2\u0181\u0182\7h\2\2\u0182"+
		"\u0183\7n\2\2\u0183\u0184\7q\2\2\u0184\u0185\7c\2\2\u0185\u0186\7v\2\2"+
		"\u0186&\3\2\2\2\u0187\u0188\7h\2\2\u0188\u0189\7q\2\2\u0189\u018a\7t\2"+
		"\2\u018a(\3\2\2\2\u018b\u018c\7k\2\2\u018c\u018d\7h\2\2\u018d*\3\2\2\2"+
		"\u018e\u018f\7i\2\2\u018f\u0190\7q\2\2\u0190\u0191\7v\2\2\u0191\u0192"+
		"\7q\2\2\u0192,\3\2\2\2\u0193\u0194\7k\2\2\u0194\u0195\7o\2\2\u0195\u0196"+
		"\7r\2\2\u0196\u0197\7n\2\2\u0197\u0198\7g\2\2\u0198\u0199\7o\2\2\u0199"+
		"\u019a\7g\2\2\u019a\u019b\7p\2\2\u019b\u019c\7v\2\2\u019c\u019d\7u\2\2"+
		"\u019d.\3\2\2\2\u019e\u019f\7k\2\2\u019f\u01a0\7p\2\2\u01a0\u01a1\7u\2"+
		"\2\u01a1\u01a2\7v\2\2\u01a2\u01a3\7c\2\2\u01a3\u01a4\7p\2\2\u01a4\u01a5"+
		"\7e\2\2\u01a5\u01a6\7g\2\2\u01a6\u01a7\7q\2\2\u01a7\u01a8\7h\2\2\u01a8"+
		"\60\3\2\2\2\u01a9\u01aa\7k\2\2\u01aa\u01ab\7p\2\2\u01ab\u01ac\7v\2\2\u01ac"+
		"\u01ad\7g\2\2\u01ad\u01ae\7t\2\2\u01ae\u01af\7h\2\2\u01af\u01b0\7c\2\2"+
		"\u01b0\u01b1\7e\2\2\u01b1\u01b2\7g\2\2\u01b2\62\3\2\2\2\u01b3\u01b4\7"+
		"n\2\2\u01b4\u01b5\7q\2\2\u01b5\u01b6\7p\2\2\u01b6\u01b7\7i\2\2\u01b7\64"+
		"\3\2\2\2\u01b8\u01b9\7p\2\2\u01b9\u01ba\7c\2\2\u01ba\u01bb\7v\2\2\u01bb"+
		"\u01bc\7k\2\2\u01bc\u01bd\7x\2\2\u01bd\u01be\7g\2\2\u01be\66\3\2\2\2\u01bf"+
		"\u01c0\7p\2\2\u01c0\u01c1\7g\2\2\u01c1\u01c2\7y\2\2\u01c28\3\2\2\2\u01c3"+
		"\u01c4\7p\2\2\u01c4\u01c5\7w\2\2\u01c5\u01c6\7n\2\2\u01c6\u01c7\7n\2\2"+
		"\u01c7:\3\2\2\2\u01c8\u01c9\7r\2\2\u01c9\u01ca\7c\2\2\u01ca\u01cb\7e\2"+
		"\2\u01cb\u01cc\7m\2\2\u01cc\u01cd\7c\2\2\u01cd\u01ce\7i\2\2\u01ce\u01cf"+
		"\7g\2\2\u01cf<\3\2\2\2\u01d0\u01d1\7r\2\2\u01d1\u01d2\7t\2\2\u01d2\u01d3"+
		"\7k\2\2\u01d3\u01d4\7x\2\2\u01d4\u01d5\7c\2\2\u01d5\u01d6\7v\2\2\u01d6"+
		"\u01d7\7g\2\2\u01d7>\3\2\2\2\u01d8\u01d9\7r\2\2\u01d9\u01da\7t\2\2\u01da"+
		"\u01db\7q\2\2\u01db\u01dc\7v\2\2\u01dc\u01dd\7g\2\2\u01dd\u01de\7e\2\2"+
		"\u01de\u01df\7v\2\2\u01df\u01e0\7g\2\2\u01e0\u01e1\7f\2\2\u01e1@\3\2\2"+
		"\2\u01e2\u01e3\7r\2\2\u01e3\u01e4\7w\2\2\u01e4\u01e5\7d\2\2\u01e5\u01e6"+
		"\7n\2\2\u01e6\u01e7\7k\2\2\u01e7\u01e8\7e\2\2\u01e8B\3\2\2\2\u01e9\u01ea"+
		"\7t\2\2\u01ea\u01eb\7g\2\2\u01eb\u01ec\7v\2\2\u01ec\u01ed\7w\2\2\u01ed"+
		"\u01ee\7t\2\2\u01ee\u01ef\7p\2\2\u01efD\3\2\2\2\u01f0\u01f1\7u\2\2\u01f1"+
		"\u01f2\7j\2\2\u01f2\u01f3\7q\2\2\u01f3\u01f4\7t\2\2\u01f4\u01f5\7v\2\2"+
		"\u01f5F\3\2\2\2\u01f6\u01f7\7u\2\2\u01f7\u01f8\7v\2\2\u01f8\u01f9\7c\2"+
		"\2\u01f9\u01fa\7v\2\2\u01fa\u01fb\7k\2\2\u01fb\u01fc\7e\2\2\u01fcH\3\2"+
		"\2\2\u01fd\u01fe\7x\2\2\u01fe\u01ff\7k\2\2\u01ff\u0200\7t\2\2\u0200\u0201"+
		"\7v\2\2\u0201\u0202\7w\2\2\u0202\u0203\7c\2\2\u0203\u0204\7n\2\2\u0204"+
		"J\3\2\2\2\u0205\u0206\7u\2\2\u0206\u0207\7w\2\2\u0207\u0208\7r\2\2\u0208"+
		"\u0209\7g\2\2\u0209\u020a\7t\2\2\u020aL\3\2\2\2\u020b\u020c\7u\2\2\u020c"+
		"\u020d\7{\2\2\u020d\u020e\7p\2\2\u020e\u020f\7e\2\2\u020f\u0210\7j\2\2"+
		"\u0210\u0211\7t\2\2\u0211\u0212\7q\2\2\u0212\u0213\7p\2\2\u0213\u0214"+
		"\7k\2\2\u0214\u0215\7|\2\2\u0215\u0216\7g\2\2\u0216\u0217\7f\2\2\u0217"+
		"N\3\2\2\2\u0218\u0219\7v\2\2\u0219\u021a\7j\2\2\u021a\u021b\7k\2\2\u021b"+
		"\u021c\7u\2\2\u021cP\3\2\2\2\u021d\u021e\7v\2\2\u021e\u021f\7j\2\2\u021f"+
		"\u0220\7t\2\2\u0220\u0221\7q\2\2\u0221\u0222\7y\2\2\u0222R\3\2\2\2\u0223"+
		"\u0224\7v\2\2\u0224\u0225\7t\2\2\u0225\u0226\7c\2\2\u0226\u0227\7p\2\2"+
		"\u0227\u0228\7u\2\2\u0228\u0229\7k\2\2\u0229\u022a\7g\2\2\u022a\u022b"+
		"\7p\2\2\u022b\u022c\7v\2\2\u022cT\3\2\2\2\u022d\u022e\7v\2\2\u022e\u022f"+
		"\7t\2\2\u022f\u0230\7{\2\2\u0230V\3\2\2\2\u0231\u0232\7x\2\2\u0232\u0233"+
		"\7q\2\2\u0233\u0234\7k\2\2\u0234\u0235\7f\2\2\u0235X\3\2\2\2\u0236\u0237"+
		"\7x\2\2\u0237\u0238\7q\2\2\u0238\u0239\7n\2\2\u0239\u023a\7c\2\2\u023a"+
		"\u023b\7v\2\2\u023b\u023c\7k\2\2\u023c\u023d\7n\2\2\u023d\u023e\7g\2\2"+
		"\u023eZ\3\2\2\2\u023f\u0240\7y\2\2\u0240\u0241\7j\2\2\u0241\u0242\7k\2"+
		"\2\u0242\u0243\7n\2\2\u0243\u0244\7g\2\2\u0244\\\3\2\2\2\u0245\u0246\7"+
		"i\2\2\u0246\u0247\7n\2\2\u0247\u0248\7q\2\2\u0248\u0249\7d\2\2\u0249\u024a"+
		"\7c\2\2\u024a\u024b\7n\2\2\u024b^\3\2\2\2\u024c\u024d\7y\2\2\u024d\u024e"+
		"\7g\2\2\u024e\u024f\7d\2\2\u024f\u0250\7u\2\2\u0250\u0251\7g\2\2\u0251"+
		"\u0252\7t\2\2\u0252\u0253\7x\2\2\u0253\u0254\7k\2\2\u0254\u0255\7e\2\2"+
		"\u0255\u0256\7g\2\2\u0256`\3\2\2\2\u0257\u0258\7u\2\2\u0258\u0259\7g\2"+
		"\2\u0259\u025a\7n\2\2\u025a\u025b\7g\2\2\u025b\u025c\7e\2\2\u025c\u025d"+
		"\7v\2\2\u025db\3\2\2\2\u025e\u025f\7k\2\2\u025f\u0260\7p\2\2\u0260\u0261"+
		"\7u\2\2\u0261\u0262\7g\2\2\u0262\u0263\7t\2\2\u0263\u0264\7v\2\2\u0264"+
		"d\3\2\2\2\u0265\u0266\7w\2\2\u0266\u0267\7r\2\2\u0267\u0268\7u\2\2\u0268"+
		"\u0269\7g\2\2\u0269\u026a\7t\2\2\u026a\u026b\7v\2\2\u026bf\3\2\2\2\u026c"+
		"\u026d\7w\2\2\u026d\u026e\7r\2\2\u026e\u026f\7f\2\2\u026f\u0270\7c\2\2"+
		"\u0270\u0271\7v\2\2\u0271\u0272\7g\2\2\u0272h\3\2\2\2\u0273\u0274\7f\2"+
		"\2\u0274\u0275\7g\2\2\u0275\u0276\7n\2\2\u0276\u0277\7g\2\2\u0277\u0278"+
		"\7v\2\2\u0278\u0279\7g\2\2\u0279j\3\2\2\2\u027a\u027b\7w\2\2\u027b\u027c"+
		"\7p\2\2\u027c\u027d\7f\2\2\u027d\u027e\7g\2\2\u027e\u027f\7n\2\2\u027f"+
		"\u0280\7g\2\2\u0280\u0281\7v\2\2\u0281\u0282\7g\2\2\u0282l\3\2\2\2\u0283"+
		"\u0284\7o\2\2\u0284\u0285\7g\2\2\u0285\u0286\7t\2\2\u0286\u0287\7i\2\2"+
		"\u0287\u0288\7g\2\2\u0288n\3\2\2\2\u0289\u028a\7v\2\2\u028a\u028b\7g\2"+
		"\2\u028b\u028c\7u\2\2\u028c\u028d\7v\2\2\u028d\u028e\7o\2\2\u028e\u028f"+
		"\7g\2\2\u028f\u0290\7v\2\2\u0290\u0291\7j\2\2\u0291\u0292\7q\2\2\u0292"+
		"\u0293\7f\2\2\u0293p\3\2\2\2\u0294\u0295\7q\2\2\u0295\u0296\7x\2\2\u0296"+
		"\u0297\7g\2\2\u0297\u0298\7t\2\2\u0298\u0299\7t\2\2\u0299\u029a\7k\2\2"+
		"\u029a\u029b\7f\2\2\u029b\u029c\7g\2\2\u029cr\3\2\2\2\u029d\u029e\7i\2"+
		"\2\u029e\u029f\7g\2\2\u029f\u02a0\7v\2\2\u02a0t\3\2\2\2\u02a1\u02a2\7"+
		"u\2\2\u02a2\u02a3\7g\2\2\u02a3\u02a4\7v\2\2\u02a4v\3\2\2\2\u02a5\u02a6"+
		"\7d\2\2\u02a6\u02a7\7n\2\2\u02a7\u02a8\7q\2\2\u02a8\u02a9\7d\2\2\u02a9"+
		"x\3\2\2\2\u02aa\u02ab\7f\2\2\u02ab\u02ac\7c\2\2\u02ac\u02ad\7v\2\2\u02ad"+
		"\u02ae\7g\2\2\u02aez\3\2\2\2\u02af\u02b0\7f\2\2\u02b0\u02b1\7c\2\2\u02b1"+
		"\u02b2\7v\2\2\u02b2\u02b3\7g\2\2\u02b3\u02b4\7v\2\2\u02b4\u02b5\7k\2\2"+
		"\u02b5\u02b6\7o\2\2\u02b6\u02b7\7g\2\2\u02b7|\3\2\2\2\u02b8\u02b9\7f\2"+
		"\2\u02b9\u02ba\7g\2\2\u02ba\u02bb\7e\2\2\u02bb\u02bc\7k\2\2\u02bc\u02bd"+
		"\7o\2\2\u02bd\u02be\7c\2\2\u02be\u02bf\7n\2\2\u02bf~\3\2\2\2\u02c0\u02c1"+
		"\7k\2\2\u02c1\u02c2\7f\2\2\u02c2\u0080\3\2\2\2\u02c3\u02c4\7k\2\2\u02c4"+
		"\u02c5\7p\2\2\u02c5\u02c6\7v\2\2\u02c6\u02c7\7g\2\2\u02c7\u02c8\7i\2\2"+
		"\u02c8\u02c9\7g\2\2\u02c9\u02ca\7t\2\2\u02ca\u0082\3\2\2\2\u02cb\u02cc"+
		"\7q\2\2\u02cc\u02cd\7d\2\2\u02cd\u02ce\7l\2\2\u02ce\u02cf\7g\2\2\u02cf"+
		"\u02d0\7e\2\2\u02d0\u02d1\7v\2\2\u02d1\u0084\3\2\2\2\u02d2\u02d3\7u\2"+
		"\2\u02d3\u02d4\7v\2\2\u02d4\u02d5\7t\2\2\u02d5\u02d6\7k\2\2\u02d6\u02d7"+
		"\7p\2\2\u02d7\u02d8\7i\2\2\u02d8\u0086\3\2\2\2\u02d9\u02da\7v\2\2\u02da"+
		"\u02db\7k\2\2\u02db\u02dc\7o\2\2\u02dc\u02dd\7g\2\2\u02dd\u0088\3\2\2"+
		"\2\u02de\u02df\7u\2\2\u02df\u02e0\7{\2\2\u02e0\u02e1\7u\2\2\u02e1\u02e2"+
		"\7v\2\2\u02e2\u02e3\7g\2\2\u02e3\u02e4\7o\2\2\u02e4\u02e5\7\60\2\2\u02e5"+
		"\u02e6\7t\2\2\u02e6\u02e7\7w\2\2\u02e7\u02e8\7p\2\2\u02e8\u02e9\7c\2\2"+
		"\u02e9\u02ea\7u\2\2\u02ea\u008a\3\2\2\2\u02eb\u02ec\7y\2\2\u02ec\u02ed"+
		"\7k\2\2\u02ed\u02ee\7v\2\2\u02ee\u02ef\7j\2\2\u02ef\u008c\3\2\2\2\u02f0"+
		"\u02f1\7y\2\2\u02f1\u02f2\7k\2\2\u02f2\u02f3\7v\2\2\u02f3\u02f4\7j\2\2"+
		"\u02f4\u02f5\7q\2\2\u02f5\u02f6\7w\2\2\u02f6\u02f7\7v\2\2\u02f7\u008e"+
		"\3\2\2\2\u02f8\u02f9\7u\2\2\u02f9\u02fa\7j\2\2\u02fa\u02fb\7c\2\2\u02fb"+
		"\u02fc\7t\2\2\u02fc\u02fd\7k\2\2\u02fd\u02fe\7p\2\2\u02fe\u02ff\7i\2\2"+
		"\u02ff\u0090\3\2\2\2\u0300\u0301\7k\2\2\u0301\u0302\7p\2\2\u0302\u0303"+
		"\7j\2\2\u0303\u0304\7g\2\2\u0304\u0305\7t\2\2\u0305\u0306\7k\2\2\u0306"+
		"\u0307\7v\2\2\u0307\u0308\7g\2\2\u0308\u0309\7f\2\2\u0309\u0092\3\2\2"+
		"\2\u030a\u030e\5\u0097L\2\u030b\u030d\5\u0097L\2\u030c\u030b\3\2\2\2\u030d"+
		"\u0310\3\2\2\2\u030e\u030c\3\2\2\2\u030e\u030f\3\2\2\2\u030f\u0312\3\2"+
		"\2\2\u0310\u030e\3\2\2\2\u0311\u0313\t\2\2\2\u0312\u0311\3\2\2\2\u0312"+
		"\u0313\3\2\2\2\u0313\u0094\3\2\2\2\u0314\u0316\5\u0097L\2\u0315\u0314"+
		"\3\2\2\2\u0316\u0319\3\2\2\2\u0317\u0315\3\2\2\2\u0317\u0318\3\2\2\2\u0318"+
		"\u031a\3\2\2\2\u0319\u0317\3\2\2\2\u031a\u031b\7\60\2\2\u031b\u031f\5"+
		"\u0097L\2\u031c\u031e\5\u0097L\2\u031d\u031c\3\2\2\2\u031e\u0321\3\2\2"+
		"\2\u031f\u031d\3\2\2\2\u031f\u0320\3\2\2\2\u0320\u0323\3\2\2\2\u0321\u031f"+
		"\3\2\2\2\u0322\u0324\t\3\2\2\u0323\u0322\3\2\2\2\u0323\u0324\3\2\2\2\u0324"+
		"\u0096\3\2\2\2\u0325\u0326\t\4\2\2\u0326\u0098\3\2\2\2\u0327\u0328\7v"+
		"\2\2\u0328\u0329\7t\2\2\u0329\u032a\7w\2\2\u032a\u0331\7g\2\2\u032b\u032c"+
		"\7h\2\2\u032c\u032d\7c\2\2\u032d\u032e\7n\2\2\u032e\u032f\7u\2\2\u032f"+
		"\u0331\7g\2\2\u0330\u0327\3\2\2\2\u0330\u032b\3\2\2\2\u0331\u009a\3\2"+
		"\2\2\u0332\u0334\7)\2\2\u0333\u0335\5\u009dO\2\u0334\u0333\3\2\2\2\u0334"+
		"\u0335\3\2\2\2\u0335\u0336\3\2\2\2\u0336\u0337\7)\2\2\u0337\u009c\3\2"+
		"\2\2\u0338\u033a\5\u009fP\2\u0339\u0338\3\2\2\2\u033a\u033b\3\2\2\2\u033b"+
		"\u0339\3\2\2\2\u033b\u033c\3\2\2\2\u033c\u009e\3\2\2\2\u033d\u0340\n\5"+
		"\2\2\u033e\u0340\5\u00a1Q\2\u033f\u033d\3\2\2\2\u033f\u033e\3\2\2\2\u0340"+
		"\u00a0\3\2\2\2\u0341\u0342\7^\2\2\u0342\u0343\t\6\2\2\u0343\u00a2\3\2"+
		"\2\2\u0344\u0345\59\35\2\u0345\u00a4\3\2\2\2\u0346\u0347\7*\2\2\u0347"+
		"\u00a6\3\2\2\2\u0348\u0349\7+\2\2\u0349\u00a8\3\2\2\2\u034a\u034b\7}\2"+
		"\2\u034b\u00aa\3\2\2\2\u034c\u034d\7\177\2\2\u034d\u00ac\3\2\2\2\u034e"+
		"\u034f\7]\2\2\u034f\u00ae\3\2\2\2\u0350\u0351\7_\2\2\u0351\u00b0\3\2\2"+
		"\2\u0352\u0353\7=\2\2\u0353\u00b2\3\2\2\2\u0354\u0355\7.\2\2\u0355\u00b4"+
		"\3\2\2\2\u0356\u0357\7\60\2\2\u0357\u00b6\3\2\2\2\u0358\u0359\7?\2\2\u0359"+
		"\u00b8\3\2\2\2\u035a\u035b\7>\2\2\u035b\u035c\7?\2\2\u035c\u00ba\3\2\2"+
		"\2\u035d\u035e\7@\2\2\u035e\u035f\7?\2\2\u035f\u00bc\3\2\2\2\u0360\u0361"+
		"\7@\2\2\u0361\u00be\3\2\2\2\u0362\u0363\7>\2\2\u0363\u00c0\3\2\2\2\u0364"+
		"\u0365\7#\2\2\u0365\u00c2\3\2\2\2\u0366\u0367\7\u0080\2\2\u0367\u00c4"+
		"\3\2\2\2\u0368\u0369\7A\2\2\u0369\u00c6\3\2\2\2\u036a\u036b\7<\2\2\u036b"+
		"\u00c8\3\2\2\2\u036c\u036d\7?\2\2\u036d\u036e\7?\2\2\u036e\u00ca\3\2\2"+
		"\2\u036f\u0370\7?\2\2\u0370\u0371\7?\2\2\u0371\u0372\7?\2\2\u0372\u00cc"+
		"\3\2\2\2\u0373\u0374\7#\2\2\u0374\u0375\7?\2\2\u0375\u00ce\3\2\2\2\u0376"+
		"\u0377\7>\2\2\u0377\u0378\7@\2\2\u0378\u00d0\3\2\2\2\u0379\u037a\7#\2"+
		"\2\u037a\u037b\7?\2\2\u037b\u037c\7?\2\2\u037c\u00d2\3\2\2\2\u037d\u037e"+
		"\7(\2\2\u037e\u037f\7(\2\2\u037f\u00d4\3\2\2\2\u0380\u0381\7~\2\2\u0381"+
		"\u0382\7~\2\2\u0382\u00d6\3\2\2\2\u0383\u0384\7-\2\2\u0384\u0385\7-\2"+
		"\2\u0385\u00d8\3\2\2\2\u0386\u0387\7/\2\2\u0387\u0388\7/\2\2\u0388\u00da"+
		"\3\2\2\2\u0389\u038a\7-\2\2\u038a\u00dc\3\2\2\2\u038b\u038c\7/\2\2\u038c"+
		"\u00de\3\2\2\2\u038d\u038e\7,\2\2\u038e\u00e0\3\2\2\2\u038f\u0390\7\61"+
		"\2\2\u0390\u00e2\3\2\2\2\u0391\u0392\7(\2\2\u0392\u00e4\3\2\2\2\u0393"+
		"\u0394\7~\2\2\u0394\u00e6\3\2\2\2\u0395\u0396\7`\2\2\u0396\u00e8\3\2\2"+
		"\2\u0397\u0398\7\'\2\2\u0398\u00ea\3\2\2\2\u0399\u039a\7?\2\2\u039a\u039b"+
		"\7@\2\2\u039b\u00ec\3\2\2\2\u039c\u039d\7-\2\2\u039d\u039e\7?\2\2\u039e"+
		"\u00ee\3\2\2\2\u039f\u03a0\7/\2\2\u03a0\u03a1\7?\2\2\u03a1\u00f0\3\2\2"+
		"\2\u03a2\u03a3\7,\2\2\u03a3\u03a4\7?\2\2\u03a4\u00f2\3\2\2\2\u03a5\u03a6"+
		"\7\61\2\2\u03a6\u03a7\7?\2\2\u03a7\u00f4\3\2\2\2\u03a8\u03a9\7(\2\2\u03a9"+
		"\u03aa\7?\2\2\u03aa\u00f6\3\2\2\2\u03ab\u03ac\7~\2\2\u03ac\u03ad\7?\2"+
		"\2\u03ad\u00f8\3\2\2\2\u03ae\u03af\7`\2\2\u03af\u03b0\7?\2\2\u03b0\u00fa"+
		"\3\2\2\2\u03b1\u03b2\7\'\2\2\u03b2\u03b3\7?\2\2\u03b3\u00fc\3\2\2\2\u03b4"+
		"\u03b5\7>\2\2\u03b5\u03b6\7>\2\2\u03b6\u03b7\7?\2\2\u03b7\u00fe\3\2\2"+
		"\2\u03b8\u03b9\7@\2\2\u03b9\u03ba\7@\2\2\u03ba\u03bb\7?\2\2\u03bb\u0100"+
		"\3\2\2\2\u03bc\u03bd\7@\2\2\u03bd\u03be\7@\2\2\u03be\u03bf\7@\2\2\u03bf"+
		"\u03c0\7?\2\2\u03c0\u0102\3\2\2\2\u03c1\u03c5\5\u0105\u0083\2\u03c2\u03c4"+
		"\5\u0107\u0084\2\u03c3\u03c2\3\2\2\2\u03c4\u03c7\3\2\2\2\u03c5\u03c3\3"+
		"\2\2\2\u03c5\u03c6\3\2\2\2\u03c6\u0104\3\2\2\2\u03c7\u03c5\3\2\2\2\u03c8"+
		"\u03cf\t\7\2\2\u03c9\u03ca\n\b\2\2\u03ca\u03cf\6\u0083\2\2\u03cb\u03cc"+
		"\t\t\2\2\u03cc\u03cd\t\n\2\2\u03cd\u03cf\6\u0083\3\2\u03ce\u03c8\3\2\2"+
		"\2\u03ce\u03c9\3\2\2\2\u03ce\u03cb\3\2\2\2\u03cf\u0106\3\2\2\2\u03d0\u03d7"+
		"\t\13\2\2\u03d1\u03d2\n\b\2\2\u03d2\u03d7\6\u0084\4\2\u03d3\u03d4\t\t"+
		"\2\2\u03d4\u03d5\t\n\2\2\u03d5\u03d7\6\u0084\5\2\u03d6\u03d0\3\2\2\2\u03d6"+
		"\u03d1\3\2\2\2\u03d6\u03d3\3\2\2\2\u03d7\u0108\3\2\2\2\u03d8\u03d9\7B"+
		"\2\2\u03d9\u010a\3\2\2\2\u03da\u03dc\t\f\2\2\u03db\u03da\3\2\2\2\u03dc"+
		"\u03dd\3\2\2\2\u03dd\u03db\3\2\2\2\u03dd\u03de\3\2\2\2\u03de\u03df\3\2"+
		"\2\2\u03df\u03e0\b\u0086\2\2\u03e0\u010c\3\2\2\2\u03e1\u03e2\7\61\2\2"+
		"\u03e2\u03e3\7,\2\2\u03e3\u03e4\7,\2\2\u03e4\u03e5\3\2\2\2\u03e5\u03e9"+
		"\t\r\2\2\u03e6\u03e8\13\2\2\2\u03e7\u03e6\3\2\2\2\u03e8\u03eb\3\2\2\2"+
		"\u03e9\u03ea\3\2\2\2\u03e9\u03e7\3\2\2\2\u03ea\u03ec\3\2\2\2\u03eb\u03e9"+
		"\3\2\2\2\u03ec\u03ed\7,\2\2\u03ed\u03ee\7\61\2\2\u03ee\u03ef\3\2\2\2\u03ef"+
		"\u03f0\b\u0087\3\2\u03f0\u010e\3\2\2\2\u03f1\u03f2\7\61\2\2\u03f2\u03f3"+
		"\7,\2\2\u03f3\u03f7\3\2\2\2\u03f4\u03f6\13\2\2\2\u03f5\u03f4\3\2\2\2\u03f6"+
		"\u03f9\3\2\2\2\u03f7\u03f8\3\2\2\2\u03f7\u03f5\3\2\2\2\u03f8\u03fa\3\2"+
		"\2\2\u03f9\u03f7\3\2\2\2\u03fa\u03fb\7,\2\2\u03fb\u03fc\7\61\2\2\u03fc"+
		"\u03fd\3\2\2\2\u03fd\u03fe\b\u0088\3\2\u03fe\u0110\3\2\2\2\u03ff\u0400"+
		"\7\61\2\2\u0400\u0401\7\61\2\2\u0401\u0405\3\2\2\2\u0402\u0404\n\r\2\2"+
		"\u0403\u0402\3\2\2\2\u0404\u0407\3\2\2\2\u0405\u0403\3\2\2\2\u0405\u0406"+
		"\3\2\2\2\u0406\u0408\3\2\2\2\u0407\u0405\3\2\2\2\u0408\u0409\b\u0089\3"+
		"\2\u0409\u0112\3\2\2\2\23\2\u030e\u0312\u0317\u031f\u0323\u0330\u0334"+
		"\u033b\u033f\u03c5\u03ce\u03d6\u03dd\u03e9\u03f7\u0405\4\2\4\2\2\5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}