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
		SHARING=71, WITHSHARING=72, WITHOUTSHARING=73, IntegerLiteral=74, NumberLiteral=75, 
		BooleanLiteral=76, StringLiteral=77, NullLiteral=78, LPAREN=79, RPAREN=80, 
		LBRACE=81, RBRACE=82, LBRACK=83, RBRACK=84, SEMI=85, COMMA=86, DOT=87, 
		ASSIGN=88, LE=89, GE=90, GT=91, LT=92, BANG=93, TILDE=94, QUESTION=95, 
		COLON=96, EQUAL=97, TRIPLEEQUAL=98, NOTEQUAL=99, LESSANDGREATER=100, TRIPLENOTEQUAL=101, 
		AND=102, OR=103, INC=104, DEC=105, ADD=106, SUB=107, MUL=108, DIV=109, 
		BITAND=110, BITOR=111, CARET=112, MOD=113, MAP=114, ADD_ASSIGN=115, SUB_ASSIGN=116, 
		MUL_ASSIGN=117, DIV_ASSIGN=118, AND_ASSIGN=119, OR_ASSIGN=120, XOR_ASSIGN=121, 
		MOD_ASSIGN=122, LSHIFT_ASSIGN=123, RSHIFT_ASSIGN=124, URSHIFT_ASSIGN=125, 
		Identifier=126, AT=127, WS=128, DOC_COMMENT=129, COMMENT=130, LINE_COMMENT=131;
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
			"WITHOUT", "SHARING", "WITHSHARING", "WITHOUTSHARING", "IntegerLiteral", 
			"NumberLiteral", "Digit", "BooleanLiteral", "StringLiteral", "StringCharacters", 
			"StringCharacter", "EscapeSequence", "NullLiteral", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", 
			"LE", "GE", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", 
			"TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", "AND", 
			"OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", "CARET", 
			"MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
			"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
			"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "JavaLetter", "JavaLetterOrDigit", 
			"AT", "WS", "DOC_COMMENT", "COMMENT", "LINE_COMMENT"
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
			"'with'", "'without'", "'sharing'", "'withsharing'", "'withoutsharing'", 
			null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"';'", "','", "'.'", "'='", "'<='", "'>='", "'>'", "'<'", "'!'", "'~'", 
			"'?'", "':'", "'=='", "'==='", "'!='", "'<>'", "'!=='", "'&&'", "'||'", 
			"'++'", "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'", 
			"'=>'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", 
			"'<<='", "'>>='", "'>>>='", null, "'@'"
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
			"WITHOUT", "SHARING", "WITHSHARING", "WITHOUTSHARING", "IntegerLiteral", 
			"NumberLiteral", "BooleanLiteral", "StringLiteral", "NullLiteral", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", 
			"ASSIGN", "LE", "GE", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", 
			"EQUAL", "TRIPLEEQUAL", "NOTEQUAL", "LESSANDGREATER", "TRIPLENOTEQUAL", 
			"AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "BITOR", 
			"CARET", "MOD", "MAP", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
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
		case 130:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 131:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\u0085\u041d\b\1\4"+
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
		"\t\u0089\4\u008a\t\u008a\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35"+
		"\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!"+
		"\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3"+
		"$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3"+
		"\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3"+
		"\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3"+
		"\67\3\67\38\38\38\38\38\38\38\38\38\38\38\39\39\39\39\39\39\39\39\39\3"+
		":\3:\3:\3:\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3"+
		">\3>\3>\3>\3?\3?\3?\3?\3?\3?\3?\3?\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3A\3"+
		"B\3B\3B\3B\3B\3B\3B\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\3D\3D\3E\3E\3E\3E\3"+
		"E\3E\3E\3E\3E\3E\3E\3E\3E\3F\3F\3F\3F\3F\3G\3G\3G\3G\3G\3G\3G\3G\3H\3"+
		"H\3H\3H\3H\3H\3H\3H\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3I\3J\3J\3J\3J\3"+
		"J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3J\3K\3K\7K\u0320\nK\fK\16K\u0323\13K\3K"+
		"\5K\u0326\nK\3L\7L\u0329\nL\fL\16L\u032c\13L\3L\3L\3L\7L\u0331\nL\fL\16"+
		"L\u0334\13L\3L\5L\u0337\nL\3M\3M\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N\u0344\n"+
		"N\3O\3O\5O\u0348\nO\3O\3O\3P\6P\u034d\nP\rP\16P\u034e\3Q\3Q\5Q\u0353\n"+
		"Q\3R\3R\3R\3S\3S\3T\3T\3U\3U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3\\\3"+
		"\\\3]\3]\3^\3^\3^\3_\3_\3_\3`\3`\3a\3a\3b\3b\3c\3c\3d\3d\3e\3e\3f\3f\3"+
		"f\3g\3g\3g\3g\3h\3h\3h\3i\3i\3i\3j\3j\3j\3j\3k\3k\3k\3l\3l\3l\3m\3m\3"+
		"m\3n\3n\3n\3o\3o\3p\3p\3q\3q\3r\3r\3s\3s\3t\3t\3u\3u\3v\3v\3w\3w\3w\3"+
		"x\3x\3x\3y\3y\3y\3z\3z\3z\3{\3{\3{\3|\3|\3|\3}\3}\3}\3~\3~\3~\3\177\3"+
		"\177\3\177\3\u0080\3\u0080\3\u0080\3\u0080\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082\3\u0083\3\u0083\7\u0083\u03d7"+
		"\n\u0083\f\u0083\16\u0083\u03da\13\u0083\3\u0084\3\u0084\3\u0084\3\u0084"+
		"\3\u0084\3\u0084\5\u0084\u03e2\n\u0084\3\u0085\3\u0085\3\u0085\3\u0085"+
		"\3\u0085\3\u0085\5\u0085\u03ea\n\u0085\3\u0086\3\u0086\3\u0087\6\u0087"+
		"\u03ef\n\u0087\r\u0087\16\u0087\u03f0\3\u0087\3\u0087\3\u0088\3\u0088"+
		"\3\u0088\3\u0088\3\u0088\3\u0088\7\u0088\u03fb\n\u0088\f\u0088\16\u0088"+
		"\u03fe\13\u0088\3\u0088\3\u0088\3\u0088\3\u0088\3\u0088\3\u0089\3\u0089"+
		"\3\u0089\3\u0089\7\u0089\u0409\n\u0089\f\u0089\16\u0089\u040c\13\u0089"+
		"\3\u0089\3\u0089\3\u0089\3\u0089\3\u0089\3\u008a\3\u008a\3\u008a\3\u008a"+
		"\7\u008a\u0417\n\u008a\f\u008a\16\u008a\u041a\13\u008a\3\u008a\3\u008a"+
		"\4\u03fc\u040a\2\u008b\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089"+
		"F\u008bG\u008dH\u008fI\u0091J\u0093K\u0095L\u0097M\u0099\2\u009bN\u009d"+
		"O\u009f\2\u00a1\2\u00a3\2\u00a5P\u00a7Q\u00a9R\u00abS\u00adT\u00afU\u00b1"+
		"V\u00b3W\u00b5X\u00b7Y\u00b9Z\u00bb[\u00bd\\\u00bf]\u00c1^\u00c3_\u00c5"+
		"`\u00c7a\u00c9b\u00cbc\u00cdd\u00cfe\u00d1f\u00d3g\u00d5h\u00d7i\u00d9"+
		"j\u00dbk\u00ddl\u00dfm\u00e1n\u00e3o\u00e5p\u00e7q\u00e9r\u00ebs\u00ed"+
		"t\u00efu\u00f1v\u00f3w\u00f5x\u00f7y\u00f9z\u00fb{\u00fd|\u00ff}\u0101"+
		"~\u0103\177\u0105\u0080\u0107\2\u0109\2\u010b\u0081\u010d\u0082\u010f"+
		"\u0083\u0111\u0084\u0113\u0085\3\2\16\4\2NNnn\4\2FFff\3\2\62;\4\2))^^"+
		"\n\2$$))^^ddhhppttvv\6\2&&C\\aac|\4\2\2\u0101\ud802\udc01\3\2\ud802\udc01"+
		"\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u0428"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2"+
		"{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085"+
		"\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097"+
		"\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2"+
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
		"\2\2\u0103\3\2\2\2\2\u0105\3\2\2\2\2\u010b\3\2\2\2\2\u010d\3\2\2\2\2\u010f"+
		"\3\2\2\2\2\u0111\3\2\2\2\2\u0113\3\2\2\2\3\u0115\3\2\2\2\5\u011e\3\2\2"+
		"\2\7\u0126\3\2\2\2\t\u012c\3\2\2\2\13\u0131\3\2\2\2\r\u0137\3\2\2\2\17"+
		"\u013c\3\2\2\2\21\u0142\3\2\2\2\23\u0148\3\2\2\2\25\u0151\3\2\2\2\27\u0159"+
		"\3\2\2\2\31\u015c\3\2\2\2\33\u0163\3\2\2\2\35\u0168\3\2\2\2\37\u016d\3"+
		"\2\2\2!\u0175\3\2\2\2#\u017b\3\2\2\2%\u0183\3\2\2\2\'\u0189\3\2\2\2)\u018d"+
		"\3\2\2\2+\u0190\3\2\2\2-\u0195\3\2\2\2/\u01a0\3\2\2\2\61\u01ab\3\2\2\2"+
		"\63\u01b5\3\2\2\2\65\u01ba\3\2\2\2\67\u01c1\3\2\2\29\u01c5\3\2\2\2;\u01ca"+
		"\3\2\2\2=\u01d2\3\2\2\2?\u01da\3\2\2\2A\u01e4\3\2\2\2C\u01eb\3\2\2\2E"+
		"\u01f2\3\2\2\2G\u01f8\3\2\2\2I\u01ff\3\2\2\2K\u0207\3\2\2\2M\u020d\3\2"+
		"\2\2O\u021a\3\2\2\2Q\u021f\3\2\2\2S\u0225\3\2\2\2U\u022f\3\2\2\2W\u0233"+
		"\3\2\2\2Y\u0238\3\2\2\2[\u0241\3\2\2\2]\u0247\3\2\2\2_\u024e\3\2\2\2a"+
		"\u0259\3\2\2\2c\u0260\3\2\2\2e\u0267\3\2\2\2g\u026e\3\2\2\2i\u0275\3\2"+
		"\2\2k\u027c\3\2\2\2m\u0285\3\2\2\2o\u028b\3\2\2\2q\u0296\3\2\2\2s\u029f"+
		"\3\2\2\2u\u02a3\3\2\2\2w\u02a7\3\2\2\2y\u02ac\3\2\2\2{\u02b1\3\2\2\2}"+
		"\u02ba\3\2\2\2\177\u02c2\3\2\2\2\u0081\u02c5\3\2\2\2\u0083\u02cd\3\2\2"+
		"\2\u0085\u02d4\3\2\2\2\u0087\u02db\3\2\2\2\u0089\u02e0\3\2\2\2\u008b\u02ed"+
		"\3\2\2\2\u008d\u02f2\3\2\2\2\u008f\u02fa\3\2\2\2\u0091\u0302\3\2\2\2\u0093"+
		"\u030e\3\2\2\2\u0095\u031d\3\2\2\2\u0097\u032a\3\2\2\2\u0099\u0338\3\2"+
		"\2\2\u009b\u0343\3\2\2\2\u009d\u0345\3\2\2\2\u009f\u034c\3\2\2\2\u00a1"+
		"\u0352\3\2\2\2\u00a3\u0354\3\2\2\2\u00a5\u0357\3\2\2\2\u00a7\u0359\3\2"+
		"\2\2\u00a9\u035b\3\2\2\2\u00ab\u035d\3\2\2\2\u00ad\u035f\3\2\2\2\u00af"+
		"\u0361\3\2\2\2\u00b1\u0363\3\2\2\2\u00b3\u0365\3\2\2\2\u00b5\u0367\3\2"+
		"\2\2\u00b7\u0369\3\2\2\2\u00b9\u036b\3\2\2\2\u00bb\u036d\3\2\2\2\u00bd"+
		"\u0370\3\2\2\2\u00bf\u0373\3\2\2\2\u00c1\u0375\3\2\2\2\u00c3\u0377\3\2"+
		"\2\2\u00c5\u0379\3\2\2\2\u00c7\u037b\3\2\2\2\u00c9\u037d\3\2\2\2\u00cb"+
		"\u037f\3\2\2\2\u00cd\u0382\3\2\2\2\u00cf\u0386\3\2\2\2\u00d1\u0389\3\2"+
		"\2\2\u00d3\u038c\3\2\2\2\u00d5\u0390\3\2\2\2\u00d7\u0393\3\2\2\2\u00d9"+
		"\u0396\3\2\2\2\u00db\u0399\3\2\2\2\u00dd\u039c\3\2\2\2\u00df\u039e\3\2"+
		"\2\2\u00e1\u03a0\3\2\2\2\u00e3\u03a2\3\2\2\2\u00e5\u03a4\3\2\2\2\u00e7"+
		"\u03a6\3\2\2\2\u00e9\u03a8\3\2\2\2\u00eb\u03aa\3\2\2\2\u00ed\u03ac\3\2"+
		"\2\2\u00ef\u03af\3\2\2\2\u00f1\u03b2\3\2\2\2\u00f3\u03b5\3\2\2\2\u00f5"+
		"\u03b8\3\2\2\2\u00f7\u03bb\3\2\2\2\u00f9\u03be\3\2\2\2\u00fb\u03c1\3\2"+
		"\2\2\u00fd\u03c4\3\2\2\2\u00ff\u03c7\3\2\2\2\u0101\u03cb\3\2\2\2\u0103"+
		"\u03cf\3\2\2\2\u0105\u03d4\3\2\2\2\u0107\u03e1\3\2\2\2\u0109\u03e9\3\2"+
		"\2\2\u010b\u03eb\3\2\2\2\u010d\u03ee\3\2\2\2\u010f\u03f4\3\2\2\2\u0111"+
		"\u0404\3\2\2\2\u0113\u0412\3\2\2\2\u0115\u0116\7c\2\2\u0116\u0117\7d\2"+
		"\2\u0117\u0118\7u\2\2\u0118\u0119\7v\2\2\u0119\u011a\7t\2\2\u011a\u011b"+
		"\7c\2\2\u011b\u011c\7e\2\2\u011c\u011d\7v\2\2\u011d\4\3\2\2\2\u011e\u011f"+
		"\7d\2\2\u011f\u0120\7q\2\2\u0120\u0121\7q\2\2\u0121\u0122\7n\2\2\u0122"+
		"\u0123\7g\2\2\u0123\u0124\7c\2\2\u0124\u0125\7p\2\2\u0125\6\3\2\2\2\u0126"+
		"\u0127\7d\2\2\u0127\u0128\7t\2\2\u0128\u0129\7g\2\2\u0129\u012a\7c\2\2"+
		"\u012a\u012b\7m\2\2\u012b\b\3\2\2\2\u012c\u012d\7d\2\2\u012d\u012e\7{"+
		"\2\2\u012e\u012f\7v\2\2\u012f\u0130\7g\2\2\u0130\n\3\2\2\2\u0131\u0132"+
		"\7e\2\2\u0132\u0133\7c\2\2\u0133\u0134\7v\2\2\u0134\u0135\7e\2\2\u0135"+
		"\u0136\7j\2\2\u0136\f\3\2\2\2\u0137\u0138\7e\2\2\u0138\u0139\7j\2\2\u0139"+
		"\u013a\7c\2\2\u013a\u013b\7t\2\2\u013b\16\3\2\2\2\u013c\u013d\7e\2\2\u013d"+
		"\u013e\7n\2\2\u013e\u013f\7c\2\2\u013f\u0140\7u\2\2\u0140\u0141\7u\2\2"+
		"\u0141\20\3\2\2\2\u0142\u0143\7e\2\2\u0143\u0144\7q\2\2\u0144\u0145\7"+
		"p\2\2\u0145\u0146\7u\2\2\u0146\u0147\7v\2\2\u0147\22\3\2\2\2\u0148\u0149"+
		"\7e\2\2\u0149\u014a\7q\2\2\u014a\u014b\7p\2\2\u014b\u014c\7v\2\2\u014c"+
		"\u014d\7k\2\2\u014d\u014e\7p\2\2\u014e\u014f\7w\2\2\u014f\u0150\7g\2\2"+
		"\u0150\24\3\2\2\2\u0151\u0152\7f\2\2\u0152\u0153\7g\2\2\u0153\u0154\7"+
		"h\2\2\u0154\u0155\7c\2\2\u0155\u0156\7w\2\2\u0156\u0157\7n\2\2\u0157\u0158"+
		"\7v\2\2\u0158\26\3\2\2\2\u0159\u015a\7f\2\2\u015a\u015b\7q\2\2\u015b\30"+
		"\3\2\2\2\u015c\u015d\7f\2\2\u015d\u015e\7q\2\2\u015e\u015f\7w\2\2\u015f"+
		"\u0160\7d\2\2\u0160\u0161\7n\2\2\u0161\u0162\7g\2\2\u0162\32\3\2\2\2\u0163"+
		"\u0164\7g\2\2\u0164\u0165\7n\2\2\u0165\u0166\7u\2\2\u0166\u0167\7g\2\2"+
		"\u0167\34\3\2\2\2\u0168\u0169\7g\2\2\u0169\u016a\7p\2\2\u016a\u016b\7"+
		"w\2\2\u016b\u016c\7o\2\2\u016c\36\3\2\2\2\u016d\u016e\7g\2\2\u016e\u016f"+
		"\7z\2\2\u016f\u0170\7v\2\2\u0170\u0171\7g\2\2\u0171\u0172\7p\2\2\u0172"+
		"\u0173\7f\2\2\u0173\u0174\7u\2\2\u0174 \3\2\2\2\u0175\u0176\7h\2\2\u0176"+
		"\u0177\7k\2\2\u0177\u0178\7p\2\2\u0178\u0179\7c\2\2\u0179\u017a\7n\2\2"+
		"\u017a\"\3\2\2\2\u017b\u017c\7h\2\2\u017c\u017d\7k\2\2\u017d\u017e\7p"+
		"\2\2\u017e\u017f\7c\2\2\u017f\u0180\7n\2\2\u0180\u0181\7n\2\2\u0181\u0182"+
		"\7{\2\2\u0182$\3\2\2\2\u0183\u0184\7h\2\2\u0184\u0185\7n\2\2\u0185\u0186"+
		"\7q\2\2\u0186\u0187\7c\2\2\u0187\u0188\7v\2\2\u0188&\3\2\2\2\u0189\u018a"+
		"\7h\2\2\u018a\u018b\7q\2\2\u018b\u018c\7t\2\2\u018c(\3\2\2\2\u018d\u018e"+
		"\7k\2\2\u018e\u018f\7h\2\2\u018f*\3\2\2\2\u0190\u0191\7i\2\2\u0191\u0192"+
		"\7q\2\2\u0192\u0193\7v\2\2\u0193\u0194\7q\2\2\u0194,\3\2\2\2\u0195\u0196"+
		"\7k\2\2\u0196\u0197\7o\2\2\u0197\u0198\7r\2\2\u0198\u0199\7n\2\2\u0199"+
		"\u019a\7g\2\2\u019a\u019b\7o\2\2\u019b\u019c\7g\2\2\u019c\u019d\7p\2\2"+
		"\u019d\u019e\7v\2\2\u019e\u019f\7u\2\2\u019f.\3\2\2\2\u01a0\u01a1\7k\2"+
		"\2\u01a1\u01a2\7p\2\2\u01a2\u01a3\7u\2\2\u01a3\u01a4\7v\2\2\u01a4\u01a5"+
		"\7c\2\2\u01a5\u01a6\7p\2\2\u01a6\u01a7\7e\2\2\u01a7\u01a8\7g\2\2\u01a8"+
		"\u01a9\7q\2\2\u01a9\u01aa\7h\2\2\u01aa\60\3\2\2\2\u01ab\u01ac\7k\2\2\u01ac"+
		"\u01ad\7p\2\2\u01ad\u01ae\7v\2\2\u01ae\u01af\7g\2\2\u01af\u01b0\7t\2\2"+
		"\u01b0\u01b1\7h\2\2\u01b1\u01b2\7c\2\2\u01b2\u01b3\7e\2\2\u01b3\u01b4"+
		"\7g\2\2\u01b4\62\3\2\2\2\u01b5\u01b6\7n\2\2\u01b6\u01b7\7q\2\2\u01b7\u01b8"+
		"\7p\2\2\u01b8\u01b9\7i\2\2\u01b9\64\3\2\2\2\u01ba\u01bb\7p\2\2\u01bb\u01bc"+
		"\7c\2\2\u01bc\u01bd\7v\2\2\u01bd\u01be\7k\2\2\u01be\u01bf\7x\2\2\u01bf"+
		"\u01c0\7g\2\2\u01c0\66\3\2\2\2\u01c1\u01c2\7p\2\2\u01c2\u01c3\7g\2\2\u01c3"+
		"\u01c4\7y\2\2\u01c48\3\2\2\2\u01c5\u01c6\7p\2\2\u01c6\u01c7\7w\2\2\u01c7"+
		"\u01c8\7n\2\2\u01c8\u01c9\7n\2\2\u01c9:\3\2\2\2\u01ca\u01cb\7r\2\2\u01cb"+
		"\u01cc\7c\2\2\u01cc\u01cd\7e\2\2\u01cd\u01ce\7m\2\2\u01ce\u01cf\7c\2\2"+
		"\u01cf\u01d0\7i\2\2\u01d0\u01d1\7g\2\2\u01d1<\3\2\2\2\u01d2\u01d3\7r\2"+
		"\2\u01d3\u01d4\7t\2\2\u01d4\u01d5\7k\2\2\u01d5\u01d6\7x\2\2\u01d6\u01d7"+
		"\7c\2\2\u01d7\u01d8\7v\2\2\u01d8\u01d9\7g\2\2\u01d9>\3\2\2\2\u01da\u01db"+
		"\7r\2\2\u01db\u01dc\7t\2\2\u01dc\u01dd\7q\2\2\u01dd\u01de\7v\2\2\u01de"+
		"\u01df\7g\2\2\u01df\u01e0\7e\2\2\u01e0\u01e1\7v\2\2\u01e1\u01e2\7g\2\2"+
		"\u01e2\u01e3\7f\2\2\u01e3@\3\2\2\2\u01e4\u01e5\7r\2\2\u01e5\u01e6\7w\2"+
		"\2\u01e6\u01e7\7d\2\2\u01e7\u01e8\7n\2\2\u01e8\u01e9\7k\2\2\u01e9\u01ea"+
		"\7e\2\2\u01eaB\3\2\2\2\u01eb\u01ec\7t\2\2\u01ec\u01ed\7g\2\2\u01ed\u01ee"+
		"\7v\2\2\u01ee\u01ef\7w\2\2\u01ef\u01f0\7t\2\2\u01f0\u01f1\7p\2\2\u01f1"+
		"D\3\2\2\2\u01f2\u01f3\7u\2\2\u01f3\u01f4\7j\2\2\u01f4\u01f5\7q\2\2\u01f5"+
		"\u01f6\7t\2\2\u01f6\u01f7\7v\2\2\u01f7F\3\2\2\2\u01f8\u01f9\7u\2\2\u01f9"+
		"\u01fa\7v\2\2\u01fa\u01fb\7c\2\2\u01fb\u01fc\7v\2\2\u01fc\u01fd\7k\2\2"+
		"\u01fd\u01fe\7e\2\2\u01feH\3\2\2\2\u01ff\u0200\7x\2\2\u0200\u0201\7k\2"+
		"\2\u0201\u0202\7t\2\2\u0202\u0203\7v\2\2\u0203\u0204\7w\2\2\u0204\u0205"+
		"\7c\2\2\u0205\u0206\7n\2\2\u0206J\3\2\2\2\u0207\u0208\7u\2\2\u0208\u0209"+
		"\7w\2\2\u0209\u020a\7r\2\2\u020a\u020b\7g\2\2\u020b\u020c\7t\2\2\u020c"+
		"L\3\2\2\2\u020d\u020e\7u\2\2\u020e\u020f\7{\2\2\u020f\u0210\7p\2\2\u0210"+
		"\u0211\7e\2\2\u0211\u0212\7j\2\2\u0212\u0213\7t\2\2\u0213\u0214\7q\2\2"+
		"\u0214\u0215\7p\2\2\u0215\u0216\7k\2\2\u0216\u0217\7|\2\2\u0217\u0218"+
		"\7g\2\2\u0218\u0219\7f\2\2\u0219N\3\2\2\2\u021a\u021b\7v\2\2\u021b\u021c"+
		"\7j\2\2\u021c\u021d\7k\2\2\u021d\u021e\7u\2\2\u021eP\3\2\2\2\u021f\u0220"+
		"\7v\2\2\u0220\u0221\7j\2\2\u0221\u0222\7t\2\2\u0222\u0223\7q\2\2\u0223"+
		"\u0224\7y\2\2\u0224R\3\2\2\2\u0225\u0226\7v\2\2\u0226\u0227\7t\2\2\u0227"+
		"\u0228\7c\2\2\u0228\u0229\7p\2\2\u0229\u022a\7u\2\2\u022a\u022b\7k\2\2"+
		"\u022b\u022c\7g\2\2\u022c\u022d\7p\2\2\u022d\u022e\7v\2\2\u022eT\3\2\2"+
		"\2\u022f\u0230\7v\2\2\u0230\u0231\7t\2\2\u0231\u0232\7{\2\2\u0232V\3\2"+
		"\2\2\u0233\u0234\7x\2\2\u0234\u0235\7q\2\2\u0235\u0236\7k\2\2\u0236\u0237"+
		"\7f\2\2\u0237X\3\2\2\2\u0238\u0239\7x\2\2\u0239\u023a\7q\2\2\u023a\u023b"+
		"\7n\2\2\u023b\u023c\7c\2\2\u023c\u023d\7v\2\2\u023d\u023e\7k\2\2\u023e"+
		"\u023f\7n\2\2\u023f\u0240\7g\2\2\u0240Z\3\2\2\2\u0241\u0242\7y\2\2\u0242"+
		"\u0243\7j\2\2\u0243\u0244\7k\2\2\u0244\u0245\7n\2\2\u0245\u0246\7g\2\2"+
		"\u0246\\\3\2\2\2\u0247\u0248\7i\2\2\u0248\u0249\7n\2\2\u0249\u024a\7q"+
		"\2\2\u024a\u024b\7d\2\2\u024b\u024c\7c\2\2\u024c\u024d\7n\2\2\u024d^\3"+
		"\2\2\2\u024e\u024f\7y\2\2\u024f\u0250\7g\2\2\u0250\u0251\7d\2\2\u0251"+
		"\u0252\7u\2\2\u0252\u0253\7g\2\2\u0253\u0254\7t\2\2\u0254\u0255\7x\2\2"+
		"\u0255\u0256\7k\2\2\u0256\u0257\7e\2\2\u0257\u0258\7g\2\2\u0258`\3\2\2"+
		"\2\u0259\u025a\7u\2\2\u025a\u025b\7g\2\2\u025b\u025c\7n\2\2\u025c\u025d"+
		"\7g\2\2\u025d\u025e\7e\2\2\u025e\u025f\7v\2\2\u025fb\3\2\2\2\u0260\u0261"+
		"\7k\2\2\u0261\u0262\7p\2\2\u0262\u0263\7u\2\2\u0263\u0264\7g\2\2\u0264"+
		"\u0265\7t\2\2\u0265\u0266\7v\2\2\u0266d\3\2\2\2\u0267\u0268\7w\2\2\u0268"+
		"\u0269\7r\2\2\u0269\u026a\7u\2\2\u026a\u026b\7g\2\2\u026b\u026c\7t\2\2"+
		"\u026c\u026d\7v\2\2\u026df\3\2\2\2\u026e\u026f\7w\2\2\u026f\u0270\7r\2"+
		"\2\u0270\u0271\7f\2\2\u0271\u0272\7c\2\2\u0272\u0273\7v\2\2\u0273\u0274"+
		"\7g\2\2\u0274h\3\2\2\2\u0275\u0276\7f\2\2\u0276\u0277\7g\2\2\u0277\u0278"+
		"\7n\2\2\u0278\u0279\7g\2\2\u0279\u027a\7v\2\2\u027a\u027b\7g\2\2\u027b"+
		"j\3\2\2\2\u027c\u027d\7w\2\2\u027d\u027e\7p\2\2\u027e\u027f\7f\2\2\u027f"+
		"\u0280\7g\2\2\u0280\u0281\7n\2\2\u0281\u0282\7g\2\2\u0282\u0283\7v\2\2"+
		"\u0283\u0284\7g\2\2\u0284l\3\2\2\2\u0285\u0286\7o\2\2\u0286\u0287\7g\2"+
		"\2\u0287\u0288\7t\2\2\u0288\u0289\7i\2\2\u0289\u028a\7g\2\2\u028an\3\2"+
		"\2\2\u028b\u028c\7v\2\2\u028c\u028d\7g\2\2\u028d\u028e\7u\2\2\u028e\u028f"+
		"\7v\2\2\u028f\u0290\7o\2\2\u0290\u0291\7g\2\2\u0291\u0292\7v\2\2\u0292"+
		"\u0293\7j\2\2\u0293\u0294\7q\2\2\u0294\u0295\7f\2\2\u0295p\3\2\2\2\u0296"+
		"\u0297\7q\2\2\u0297\u0298\7x\2\2\u0298\u0299\7g\2\2\u0299\u029a\7t\2\2"+
		"\u029a\u029b\7t\2\2\u029b\u029c\7k\2\2\u029c\u029d\7f\2\2\u029d\u029e"+
		"\7g\2\2\u029er\3\2\2\2\u029f\u02a0\7i\2\2\u02a0\u02a1\7g\2\2\u02a1\u02a2"+
		"\7v\2\2\u02a2t\3\2\2\2\u02a3\u02a4\7u\2\2\u02a4\u02a5\7g\2\2\u02a5\u02a6"+
		"\7v\2\2\u02a6v\3\2\2\2\u02a7\u02a8\7d\2\2\u02a8\u02a9\7n\2\2\u02a9\u02aa"+
		"\7q\2\2\u02aa\u02ab\7d\2\2\u02abx\3\2\2\2\u02ac\u02ad\7f\2\2\u02ad\u02ae"+
		"\7c\2\2\u02ae\u02af\7v\2\2\u02af\u02b0\7g\2\2\u02b0z\3\2\2\2\u02b1\u02b2"+
		"\7f\2\2\u02b2\u02b3\7c\2\2\u02b3\u02b4\7v\2\2\u02b4\u02b5\7g\2\2\u02b5"+
		"\u02b6\7v\2\2\u02b6\u02b7\7k\2\2\u02b7\u02b8\7o\2\2\u02b8\u02b9\7g\2\2"+
		"\u02b9|\3\2\2\2\u02ba\u02bb\7f\2\2\u02bb\u02bc\7g\2\2\u02bc\u02bd\7e\2"+
		"\2\u02bd\u02be\7k\2\2\u02be\u02bf\7o\2\2\u02bf\u02c0\7c\2\2\u02c0\u02c1"+
		"\7n\2\2\u02c1~\3\2\2\2\u02c2\u02c3\7k\2\2\u02c3\u02c4\7f\2\2\u02c4\u0080"+
		"\3\2\2\2\u02c5\u02c6\7k\2\2\u02c6\u02c7\7p\2\2\u02c7\u02c8\7v\2\2\u02c8"+
		"\u02c9\7g\2\2\u02c9\u02ca\7i\2\2\u02ca\u02cb\7g\2\2\u02cb\u02cc\7t\2\2"+
		"\u02cc\u0082\3\2\2\2\u02cd\u02ce\7q\2\2\u02ce\u02cf\7d\2\2\u02cf\u02d0"+
		"\7l\2\2\u02d0\u02d1\7g\2\2\u02d1\u02d2\7e\2\2\u02d2\u02d3\7v\2\2\u02d3"+
		"\u0084\3\2\2\2\u02d4\u02d5\7u\2\2\u02d5\u02d6\7v\2\2\u02d6\u02d7\7t\2"+
		"\2\u02d7\u02d8\7k\2\2\u02d8\u02d9\7p\2\2\u02d9\u02da\7i\2\2\u02da\u0086"+
		"\3\2\2\2\u02db\u02dc\7v\2\2\u02dc\u02dd\7k\2\2\u02dd\u02de\7o\2\2\u02de"+
		"\u02df\7g\2\2\u02df\u0088\3\2\2\2\u02e0\u02e1\7u\2\2\u02e1\u02e2\7{\2"+
		"\2\u02e2\u02e3\7u\2\2\u02e3\u02e4\7v\2\2\u02e4\u02e5\7g\2\2\u02e5\u02e6"+
		"\7o\2\2\u02e6\u02e7\7\60\2\2\u02e7\u02e8\7t\2\2\u02e8\u02e9\7w\2\2\u02e9"+
		"\u02ea\7p\2\2\u02ea\u02eb\7c\2\2\u02eb\u02ec\7u\2\2\u02ec\u008a\3\2\2"+
		"\2\u02ed\u02ee\7y\2\2\u02ee\u02ef\7k\2\2\u02ef\u02f0\7v\2\2\u02f0\u02f1"+
		"\7j\2\2\u02f1\u008c\3\2\2\2\u02f2\u02f3\7y\2\2\u02f3\u02f4\7k\2\2\u02f4"+
		"\u02f5\7v\2\2\u02f5\u02f6\7j\2\2\u02f6\u02f7\7q\2\2\u02f7\u02f8\7w\2\2"+
		"\u02f8\u02f9\7v\2\2\u02f9\u008e\3\2\2\2\u02fa\u02fb\7u\2\2\u02fb\u02fc"+
		"\7j\2\2\u02fc\u02fd\7c\2\2\u02fd\u02fe\7t\2\2\u02fe\u02ff\7k\2\2\u02ff"+
		"\u0300\7p\2\2\u0300\u0301\7i\2\2\u0301\u0090\3\2\2\2\u0302\u0303\7y\2"+
		"\2\u0303\u0304\7k\2\2\u0304\u0305\7v\2\2\u0305\u0306\7j\2\2\u0306\u0307"+
		"\7u\2\2\u0307\u0308\7j\2\2\u0308\u0309\7c\2\2\u0309\u030a\7t\2\2\u030a"+
		"\u030b\7k\2\2\u030b\u030c\7p\2\2\u030c\u030d\7i\2\2\u030d\u0092\3\2\2"+
		"\2\u030e\u030f\7y\2\2\u030f\u0310\7k\2\2\u0310\u0311\7v\2\2\u0311\u0312"+
		"\7j\2\2\u0312\u0313\7q\2\2\u0313\u0314\7w\2\2\u0314\u0315\7v\2\2\u0315"+
		"\u0316\7u\2\2\u0316\u0317\7j\2\2\u0317\u0318\7c\2\2\u0318\u0319\7t\2\2"+
		"\u0319\u031a\7k\2\2\u031a\u031b\7p\2\2\u031b\u031c\7i\2\2\u031c\u0094"+
		"\3\2\2\2\u031d\u0321\5\u0099M\2\u031e\u0320\5\u0099M\2\u031f\u031e\3\2"+
		"\2\2\u0320\u0323\3\2\2\2\u0321\u031f\3\2\2\2\u0321\u0322\3\2\2\2\u0322"+
		"\u0325\3\2\2\2\u0323\u0321\3\2\2\2\u0324\u0326\t\2\2\2\u0325\u0324\3\2"+
		"\2\2\u0325\u0326\3\2\2\2\u0326\u0096\3\2\2\2\u0327\u0329\5\u0099M\2\u0328"+
		"\u0327\3\2\2\2\u0329\u032c\3\2\2\2\u032a\u0328\3\2\2\2\u032a\u032b\3\2"+
		"\2\2\u032b\u032d\3\2\2\2\u032c\u032a\3\2\2\2\u032d\u032e\7\60\2\2\u032e"+
		"\u0332\5\u0099M\2\u032f\u0331\5\u0099M\2\u0330\u032f\3\2\2\2\u0331\u0334"+
		"\3\2\2\2\u0332\u0330\3\2\2\2\u0332\u0333\3\2\2\2\u0333\u0336\3\2\2\2\u0334"+
		"\u0332\3\2\2\2\u0335\u0337\t\3\2\2\u0336\u0335\3\2\2\2\u0336\u0337\3\2"+
		"\2\2\u0337\u0098\3\2\2\2\u0338\u0339\t\4\2\2\u0339\u009a\3\2\2\2\u033a"+
		"\u033b\7v\2\2\u033b\u033c\7t\2\2\u033c\u033d\7w\2\2\u033d\u0344\7g\2\2"+
		"\u033e\u033f\7h\2\2\u033f\u0340\7c\2\2\u0340\u0341\7n\2\2\u0341\u0342"+
		"\7u\2\2\u0342\u0344\7g\2\2\u0343\u033a\3\2\2\2\u0343\u033e\3\2\2\2\u0344"+
		"\u009c\3\2\2\2\u0345\u0347\7)\2\2\u0346\u0348\5\u009fP\2\u0347\u0346\3"+
		"\2\2\2\u0347\u0348\3\2\2\2\u0348\u0349\3\2\2\2\u0349\u034a\7)\2\2\u034a"+
		"\u009e\3\2\2\2\u034b\u034d\5\u00a1Q\2\u034c\u034b\3\2\2\2\u034d\u034e"+
		"\3\2\2\2\u034e\u034c\3\2\2\2\u034e\u034f\3\2\2\2\u034f\u00a0\3\2\2\2\u0350"+
		"\u0353\n\5\2\2\u0351\u0353\5\u00a3R\2\u0352\u0350\3\2\2\2\u0352\u0351"+
		"\3\2\2\2\u0353\u00a2\3\2\2\2\u0354\u0355\7^\2\2\u0355\u0356\t\6\2\2\u0356"+
		"\u00a4\3\2\2\2\u0357\u0358\59\35\2\u0358\u00a6\3\2\2\2\u0359\u035a\7*"+
		"\2\2\u035a\u00a8\3\2\2\2\u035b\u035c\7+\2\2\u035c\u00aa\3\2\2\2\u035d"+
		"\u035e\7}\2\2\u035e\u00ac\3\2\2\2\u035f\u0360\7\177\2\2\u0360\u00ae\3"+
		"\2\2\2\u0361\u0362\7]\2\2\u0362\u00b0\3\2\2\2\u0363\u0364\7_\2\2\u0364"+
		"\u00b2\3\2\2\2\u0365\u0366\7=\2\2\u0366\u00b4\3\2\2\2\u0367\u0368\7.\2"+
		"\2\u0368\u00b6\3\2\2\2\u0369\u036a\7\60\2\2\u036a\u00b8\3\2\2\2\u036b"+
		"\u036c\7?\2\2\u036c\u00ba\3\2\2\2\u036d\u036e\7>\2\2\u036e\u036f\7?\2"+
		"\2\u036f\u00bc\3\2\2\2\u0370\u0371\7@\2\2\u0371\u0372\7?\2\2\u0372\u00be"+
		"\3\2\2\2\u0373\u0374\7@\2\2\u0374\u00c0\3\2\2\2\u0375\u0376\7>\2\2\u0376"+
		"\u00c2\3\2\2\2\u0377\u0378\7#\2\2\u0378\u00c4\3\2\2\2\u0379\u037a\7\u0080"+
		"\2\2\u037a\u00c6\3\2\2\2\u037b\u037c\7A\2\2\u037c\u00c8\3\2\2\2\u037d"+
		"\u037e\7<\2\2\u037e\u00ca\3\2\2\2\u037f\u0380\7?\2\2\u0380\u0381\7?\2"+
		"\2\u0381\u00cc\3\2\2\2\u0382\u0383\7?\2\2\u0383\u0384\7?\2\2\u0384\u0385"+
		"\7?\2\2\u0385\u00ce\3\2\2\2\u0386\u0387\7#\2\2\u0387\u0388\7?\2\2\u0388"+
		"\u00d0\3\2\2\2\u0389\u038a\7>\2\2\u038a\u038b\7@\2\2\u038b\u00d2\3\2\2"+
		"\2\u038c\u038d\7#\2\2\u038d\u038e\7?\2\2\u038e\u038f\7?\2\2\u038f\u00d4"+
		"\3\2\2\2\u0390\u0391\7(\2\2\u0391\u0392\7(\2\2\u0392\u00d6\3\2\2\2\u0393"+
		"\u0394\7~\2\2\u0394\u0395\7~\2\2\u0395\u00d8\3\2\2\2\u0396\u0397\7-\2"+
		"\2\u0397\u0398\7-\2\2\u0398\u00da\3\2\2\2\u0399\u039a\7/\2\2\u039a\u039b"+
		"\7/\2\2\u039b\u00dc\3\2\2\2\u039c\u039d\7-\2\2\u039d\u00de\3\2\2\2\u039e"+
		"\u039f\7/\2\2\u039f\u00e0\3\2\2\2\u03a0\u03a1\7,\2\2\u03a1\u00e2\3\2\2"+
		"\2\u03a2\u03a3\7\61\2\2\u03a3\u00e4\3\2\2\2\u03a4\u03a5\7(\2\2\u03a5\u00e6"+
		"\3\2\2\2\u03a6\u03a7\7~\2\2\u03a7\u00e8\3\2\2\2\u03a8\u03a9\7`\2\2\u03a9"+
		"\u00ea\3\2\2\2\u03aa\u03ab\7\'\2\2\u03ab\u00ec\3\2\2\2\u03ac\u03ad\7?"+
		"\2\2\u03ad\u03ae\7@\2\2\u03ae\u00ee\3\2\2\2\u03af\u03b0\7-\2\2\u03b0\u03b1"+
		"\7?\2\2\u03b1\u00f0\3\2\2\2\u03b2\u03b3\7/\2\2\u03b3\u03b4\7?\2\2\u03b4"+
		"\u00f2\3\2\2\2\u03b5\u03b6\7,\2\2\u03b6\u03b7\7?\2\2\u03b7\u00f4\3\2\2"+
		"\2\u03b8\u03b9\7\61\2\2\u03b9\u03ba\7?\2\2\u03ba\u00f6\3\2\2\2\u03bb\u03bc"+
		"\7(\2\2\u03bc\u03bd\7?\2\2\u03bd\u00f8\3\2\2\2\u03be\u03bf\7~\2\2\u03bf"+
		"\u03c0\7?\2\2\u03c0\u00fa\3\2\2\2\u03c1\u03c2\7`\2\2\u03c2\u03c3\7?\2"+
		"\2\u03c3\u00fc\3\2\2\2\u03c4\u03c5\7\'\2\2\u03c5\u03c6\7?\2\2\u03c6\u00fe"+
		"\3\2\2\2\u03c7\u03c8\7>\2\2\u03c8\u03c9\7>\2\2\u03c9\u03ca\7?\2\2\u03ca"+
		"\u0100\3\2\2\2\u03cb\u03cc\7@\2\2\u03cc\u03cd\7@\2\2\u03cd\u03ce\7?\2"+
		"\2\u03ce\u0102\3\2\2\2\u03cf\u03d0\7@\2\2\u03d0\u03d1\7@\2\2\u03d1\u03d2"+
		"\7@\2\2\u03d2\u03d3\7?\2\2\u03d3\u0104\3\2\2\2\u03d4\u03d8\5\u0107\u0084"+
		"\2\u03d5\u03d7\5\u0109\u0085\2\u03d6\u03d5\3\2\2\2\u03d7\u03da\3\2\2\2"+
		"\u03d8\u03d6\3\2\2\2\u03d8\u03d9\3\2\2\2\u03d9\u0106\3\2\2\2\u03da\u03d8"+
		"\3\2\2\2\u03db\u03e2\t\7\2\2\u03dc\u03dd\n\b\2\2\u03dd\u03e2\6\u0084\2"+
		"\2\u03de\u03df\t\t\2\2\u03df\u03e0\t\n\2\2\u03e0\u03e2\6\u0084\3\2\u03e1"+
		"\u03db\3\2\2\2\u03e1\u03dc\3\2\2\2\u03e1\u03de\3\2\2\2\u03e2\u0108\3\2"+
		"\2\2\u03e3\u03ea\t\13\2\2\u03e4\u03e5\n\b\2\2\u03e5\u03ea\6\u0085\4\2"+
		"\u03e6\u03e7\t\t\2\2\u03e7\u03e8\t\n\2\2\u03e8\u03ea\6\u0085\5\2\u03e9"+
		"\u03e3\3\2\2\2\u03e9\u03e4\3\2\2\2\u03e9\u03e6\3\2\2\2\u03ea\u010a\3\2"+
		"\2\2\u03eb\u03ec\7B\2\2\u03ec\u010c\3\2\2\2\u03ed\u03ef\t\f\2\2\u03ee"+
		"\u03ed\3\2\2\2\u03ef\u03f0\3\2\2\2\u03f0\u03ee\3\2\2\2\u03f0\u03f1\3\2"+
		"\2\2\u03f1\u03f2\3\2\2\2\u03f2\u03f3\b\u0087\2\2\u03f3\u010e\3\2\2\2\u03f4"+
		"\u03f5\7\61\2\2\u03f5\u03f6\7,\2\2\u03f6\u03f7\7,\2\2\u03f7\u03f8\3\2"+
		"\2\2\u03f8\u03fc\t\r\2\2\u03f9\u03fb\13\2\2\2\u03fa\u03f9\3\2\2\2\u03fb"+
		"\u03fe\3\2\2\2\u03fc\u03fd\3\2\2\2\u03fc\u03fa\3\2\2\2\u03fd\u03ff\3\2"+
		"\2\2\u03fe\u03fc\3\2\2\2\u03ff\u0400\7,\2\2\u0400\u0401\7\61\2\2\u0401"+
		"\u0402\3\2\2\2\u0402\u0403\b\u0088\3\2\u0403\u0110\3\2\2\2\u0404\u0405"+
		"\7\61\2\2\u0405\u0406\7,\2\2\u0406\u040a\3\2\2\2\u0407\u0409\13\2\2\2"+
		"\u0408\u0407\3\2\2\2\u0409\u040c\3\2\2\2\u040a\u040b\3\2\2\2\u040a\u0408"+
		"\3\2\2\2\u040b\u040d\3\2\2\2\u040c\u040a\3\2\2\2\u040d\u040e\7,\2\2\u040e"+
		"\u040f\7\61\2\2\u040f\u0410\3\2\2\2\u0410\u0411\b\u0089\3\2\u0411\u0112"+
		"\3\2\2\2\u0412\u0413\7\61\2\2\u0413\u0414\7\61\2\2\u0414\u0418\3\2\2\2"+
		"\u0415\u0417\n\r\2\2\u0416\u0415\3\2\2\2\u0417\u041a\3\2\2\2\u0418\u0416"+
		"\3\2\2\2\u0418\u0419\3\2\2\2\u0419\u041b\3\2\2\2\u041a\u0418\3\2\2\2\u041b"+
		"\u041c\b\u008a\3\2\u041c\u0114\3\2\2\2\23\2\u0321\u0325\u032a\u0332\u0336"+
		"\u0343\u0347\u034e\u0352\u03d8\u03e1\u03e9\u03f0\u03fc\u040a\u0418\4\2"+
		"\4\2\2\5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}