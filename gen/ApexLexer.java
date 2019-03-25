// Generated from /Users/kevin/Projects/ApexLink/src/main/antlr4/com/nawforce/parsers/ApexLexer.g4 by ANTLR 4.7.2

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
			"PUBLIC", "RETURN", "SHORT", "STATIC", "VIRTUAL", "SUPER", "THIS", "THROW", 
			"TRANSIENT", "TRY", "VOID", "WHILE", "GLOBAL", "WEBSERVICE", "SELECT", 
			"INSERT", "UPSERT", "UPDATE", "DELETE", "UNDELETE", "MERGE", "TESTMETHOD", 
			"OVERRIDE", "GET", "SET", "BLOB", "DATE", "DATETIME", "DECIMAL", "ID", 
			"INTEGER", "OBJECT", "STRING", "TIME", "RUNAS", "WITH", "WITHOUT", "SHARING", 
			"INHERITED", "IntegerLiteral", "NumberLiteral", "Digit", "BooleanLiteral", 
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
		case 127:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 128:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\u0082\u03f0\b\1\4"+
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
		"\4\u0085\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3"+
		"\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3"+
		" \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3"+
		"#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3"+
		"&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)"+
		"\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-"+
		"\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67"+
		"\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\39\3:\3:\3:\3:\3:\3;\3;\3;\3"+
		";\3;\3<\3<\3<\3<\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3=\3>\3>\3>\3?\3"+
		"?\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3B\3B\3"+
		"B\3B\3B\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\3D\3D\3E\3E\3"+
		"E\3E\3E\3E\3E\3E\3F\3F\3F\3F\3F\3F\3F\3F\3G\3G\3G\3G\3G\3G\3G\3G\3G\3"+
		"G\3H\3H\7H\u02f3\nH\fH\16H\u02f6\13H\3H\5H\u02f9\nH\3I\7I\u02fc\nI\fI"+
		"\16I\u02ff\13I\3I\3I\3I\7I\u0304\nI\fI\16I\u0307\13I\3I\5I\u030a\nI\3"+
		"J\3J\3K\3K\3K\3K\3K\3K\3K\3K\3K\5K\u0317\nK\3L\3L\5L\u031b\nL\3L\3L\3"+
		"M\6M\u0320\nM\rM\16M\u0321\3N\3N\5N\u0326\nN\3O\3O\3O\3P\3P\3Q\3Q\3R\3"+
		"R\3S\3S\3T\3T\3U\3U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3[\3\\\3\\\3\\"+
		"\3]\3]\3^\3^\3_\3_\3`\3`\3a\3a\3b\3b\3c\3c\3c\3d\3d\3d\3d\3e\3e\3e\3f"+
		"\3f\3f\3g\3g\3g\3g\3h\3h\3h\3i\3i\3i\3j\3j\3j\3k\3k\3k\3l\3l\3m\3m\3n"+
		"\3n\3o\3o\3p\3p\3q\3q\3r\3r\3s\3s\3t\3t\3t\3u\3u\3u\3v\3v\3v\3w\3w\3w"+
		"\3x\3x\3x\3y\3y\3y\3z\3z\3z\3{\3{\3{\3|\3|\3|\3}\3}\3}\3}\3~\3~\3~\3~"+
		"\3\177\3\177\3\177\3\177\3\177\3\u0080\3\u0080\7\u0080\u03aa\n\u0080\f"+
		"\u0080\16\u0080\u03ad\13\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\5\u0081\u03b5\n\u0081\3\u0082\3\u0082\3\u0082\3\u0082\3\u0082"+
		"\3\u0082\5\u0082\u03bd\n\u0082\3\u0083\3\u0083\3\u0084\6\u0084\u03c2\n"+
		"\u0084\r\u0084\16\u0084\u03c3\3\u0084\3\u0084\3\u0085\3\u0085\3\u0085"+
		"\3\u0085\3\u0085\3\u0085\7\u0085\u03ce\n\u0085\f\u0085\16\u0085\u03d1"+
		"\13\u0085\3\u0085\3\u0085\3\u0085\3\u0085\3\u0085\3\u0086\3\u0086\3\u0086"+
		"\3\u0086\7\u0086\u03dc\n\u0086\f\u0086\16\u0086\u03df\13\u0086\3\u0086"+
		"\3\u0086\3\u0086\3\u0086\3\u0086\3\u0087\3\u0087\3\u0087\3\u0087\7\u0087"+
		"\u03ea\n\u0087\f\u0087\16\u0087\u03ed\13\u0087\3\u0087\3\u0087\4\u03cf"+
		"\u03dd\2\u0088\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g"+
		"\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F"+
		"\u008bG\u008dH\u008fI\u0091J\u0093\2\u0095K\u0097L\u0099\2\u009b\2\u009d"+
		"\2\u009fM\u00a1N\u00a3O\u00a5P\u00a7Q\u00a9R\u00abS\u00adT\u00afU\u00b1"+
		"V\u00b3W\u00b5X\u00b7Y\u00b9Z\u00bb[\u00bd\\\u00bf]\u00c1^\u00c3_\u00c5"+
		"`\u00c7a\u00c9b\u00cbc\u00cdd\u00cfe\u00d1f\u00d3g\u00d5h\u00d7i\u00d9"+
		"j\u00dbk\u00ddl\u00dfm\u00e1n\u00e3o\u00e5p\u00e7q\u00e9r\u00ebs\u00ed"+
		"t\u00efu\u00f1v\u00f3w\u00f5x\u00f7y\u00f9z\u00fb{\u00fd|\u00ff}\u0101"+
		"\2\u0103\2\u0105~\u0107\177\u0109\u0080\u010b\u0081\u010d\u0082\3\2\16"+
		"\4\2NNnn\4\2FFff\3\2\62;\4\2))^^\n\2$$))^^ddhhppttvv\6\2&&C\\aac|\4\2"+
		"\2\u0101\ud802\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7\2&&\62;C\\aac|"+
		"\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u03fb\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M"+
		"\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2"+
		"\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2"+
		"\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s"+
		"\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177"+
		"\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2"+
		"\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091"+
		"\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2"+
		"\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2\2\2\u00ab"+
		"\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\2\u00b3\3\2\2"+
		"\2\2\u00b5\3\2\2\2\2\u00b7\3\2\2\2\2\u00b9\3\2\2\2\2\u00bb\3\2\2\2\2\u00bd"+
		"\3\2\2\2\2\u00bf\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3\3\2\2\2\2\u00c5\3\2\2"+
		"\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\2\u00cb\3\2\2\2\2\u00cd\3\2\2\2\2\u00cf"+
		"\3\2\2\2\2\u00d1\3\2\2\2\2\u00d3\3\2\2\2\2\u00d5\3\2\2\2\2\u00d7\3\2\2"+
		"\2\2\u00d9\3\2\2\2\2\u00db\3\2\2\2\2\u00dd\3\2\2\2\2\u00df\3\2\2\2\2\u00e1"+
		"\3\2\2\2\2\u00e3\3\2\2\2\2\u00e5\3\2\2\2\2\u00e7\3\2\2\2\2\u00e9\3\2\2"+
		"\2\2\u00eb\3\2\2\2\2\u00ed\3\2\2\2\2\u00ef\3\2\2\2\2\u00f1\3\2\2\2\2\u00f3"+
		"\3\2\2\2\2\u00f5\3\2\2\2\2\u00f7\3\2\2\2\2\u00f9\3\2\2\2\2\u00fb\3\2\2"+
		"\2\2\u00fd\3\2\2\2\2\u00ff\3\2\2\2\2\u0105\3\2\2\2\2\u0107\3\2\2\2\2\u0109"+
		"\3\2\2\2\2\u010b\3\2\2\2\2\u010d\3\2\2\2\3\u010f\3\2\2\2\5\u0118\3\2\2"+
		"\2\7\u0120\3\2\2\2\t\u0126\3\2\2\2\13\u012b\3\2\2\2\r\u0131\3\2\2\2\17"+
		"\u0136\3\2\2\2\21\u013c\3\2\2\2\23\u0142\3\2\2\2\25\u014b\3\2\2\2\27\u0153"+
		"\3\2\2\2\31\u0156\3\2\2\2\33\u015d\3\2\2\2\35\u0162\3\2\2\2\37\u0167\3"+
		"\2\2\2!\u016f\3\2\2\2#\u0175\3\2\2\2%\u017d\3\2\2\2\'\u0183\3\2\2\2)\u0187"+
		"\3\2\2\2+\u018a\3\2\2\2-\u018f\3\2\2\2/\u019a\3\2\2\2\61\u01a5\3\2\2\2"+
		"\63\u01af\3\2\2\2\65\u01b4\3\2\2\2\67\u01bb\3\2\2\29\u01bf\3\2\2\2;\u01c4"+
		"\3\2\2\2=\u01cc\3\2\2\2?\u01d4\3\2\2\2A\u01de\3\2\2\2C\u01e5\3\2\2\2E"+
		"\u01ec\3\2\2\2G\u01f2\3\2\2\2I\u01f9\3\2\2\2K\u0201\3\2\2\2M\u0207\3\2"+
		"\2\2O\u020c\3\2\2\2Q\u0212\3\2\2\2S\u021c\3\2\2\2U\u0220\3\2\2\2W\u0225"+
		"\3\2\2\2Y\u022b\3\2\2\2[\u0232\3\2\2\2]\u023d\3\2\2\2_\u0244\3\2\2\2a"+
		"\u024b\3\2\2\2c\u0252\3\2\2\2e\u0259\3\2\2\2g\u0260\3\2\2\2i\u0269\3\2"+
		"\2\2k\u026f\3\2\2\2m\u027a\3\2\2\2o\u0283\3\2\2\2q\u0287\3\2\2\2s\u028b"+
		"\3\2\2\2u\u0290\3\2\2\2w\u0295\3\2\2\2y\u029e\3\2\2\2{\u02a6\3\2\2\2}"+
		"\u02a9\3\2\2\2\177\u02b1\3\2\2\2\u0081\u02b8\3\2\2\2\u0083\u02bf\3\2\2"+
		"\2\u0085\u02c4\3\2\2\2\u0087\u02d1\3\2\2\2\u0089\u02d6\3\2\2\2\u008b\u02de"+
		"\3\2\2\2\u008d\u02e6\3\2\2\2\u008f\u02f0\3\2\2\2\u0091\u02fd\3\2\2\2\u0093"+
		"\u030b\3\2\2\2\u0095\u0316\3\2\2\2\u0097\u0318\3\2\2\2\u0099\u031f\3\2"+
		"\2\2\u009b\u0325\3\2\2\2\u009d\u0327\3\2\2\2\u009f\u032a\3\2\2\2\u00a1"+
		"\u032c\3\2\2\2\u00a3\u032e\3\2\2\2\u00a5\u0330\3\2\2\2\u00a7\u0332\3\2"+
		"\2\2\u00a9\u0334\3\2\2\2\u00ab\u0336\3\2\2\2\u00ad\u0338\3\2\2\2\u00af"+
		"\u033a\3\2\2\2\u00b1\u033c\3\2\2\2\u00b3\u033e\3\2\2\2\u00b5\u0340\3\2"+
		"\2\2\u00b7\u0343\3\2\2\2\u00b9\u0346\3\2\2\2\u00bb\u0348\3\2\2\2\u00bd"+
		"\u034a\3\2\2\2\u00bf\u034c\3\2\2\2\u00c1\u034e\3\2\2\2\u00c3\u0350\3\2"+
		"\2\2\u00c5\u0352\3\2\2\2\u00c7\u0355\3\2\2\2\u00c9\u0359\3\2\2\2\u00cb"+
		"\u035c\3\2\2\2\u00cd\u035f\3\2\2\2\u00cf\u0363\3\2\2\2\u00d1\u0366\3\2"+
		"\2\2\u00d3\u0369\3\2\2\2\u00d5\u036c\3\2\2\2\u00d7\u036f\3\2\2\2\u00d9"+
		"\u0371\3\2\2\2\u00db\u0373\3\2\2\2\u00dd\u0375\3\2\2\2\u00df\u0377\3\2"+
		"\2\2\u00e1\u0379\3\2\2\2\u00e3\u037b\3\2\2\2\u00e5\u037d\3\2\2\2\u00e7"+
		"\u037f\3\2\2\2\u00e9\u0382\3\2\2\2\u00eb\u0385\3\2\2\2\u00ed\u0388\3\2"+
		"\2\2\u00ef\u038b\3\2\2\2\u00f1\u038e\3\2\2\2\u00f3\u0391\3\2\2\2\u00f5"+
		"\u0394\3\2\2\2\u00f7\u0397\3\2\2\2\u00f9\u039a\3\2\2\2\u00fb\u039e\3\2"+
		"\2\2\u00fd\u03a2\3\2\2\2\u00ff\u03a7\3\2\2\2\u0101\u03b4\3\2\2\2\u0103"+
		"\u03bc\3\2\2\2\u0105\u03be\3\2\2\2\u0107\u03c1\3\2\2\2\u0109\u03c7\3\2"+
		"\2\2\u010b\u03d7\3\2\2\2\u010d\u03e5\3\2\2\2\u010f\u0110\7c\2\2\u0110"+
		"\u0111\7d\2\2\u0111\u0112\7u\2\2\u0112\u0113\7v\2\2\u0113\u0114\7t\2\2"+
		"\u0114\u0115\7c\2\2\u0115\u0116\7e\2\2\u0116\u0117\7v\2\2\u0117\4\3\2"+
		"\2\2\u0118\u0119\7d\2\2\u0119\u011a\7q\2\2\u011a\u011b\7q\2\2\u011b\u011c"+
		"\7n\2\2\u011c\u011d\7g\2\2\u011d\u011e\7c\2\2\u011e\u011f\7p\2\2\u011f"+
		"\6\3\2\2\2\u0120\u0121\7d\2\2\u0121\u0122\7t\2\2\u0122\u0123\7g\2\2\u0123"+
		"\u0124\7c\2\2\u0124\u0125\7m\2\2\u0125\b\3\2\2\2\u0126\u0127\7d\2\2\u0127"+
		"\u0128\7{\2\2\u0128\u0129\7v\2\2\u0129\u012a\7g\2\2\u012a\n\3\2\2\2\u012b"+
		"\u012c\7e\2\2\u012c\u012d\7c\2\2\u012d\u012e\7v\2\2\u012e\u012f\7e\2\2"+
		"\u012f\u0130\7j\2\2\u0130\f\3\2\2\2\u0131\u0132\7e\2\2\u0132\u0133\7j"+
		"\2\2\u0133\u0134\7c\2\2\u0134\u0135\7t\2\2\u0135\16\3\2\2\2\u0136\u0137"+
		"\7e\2\2\u0137\u0138\7n\2\2\u0138\u0139\7c\2\2\u0139\u013a\7u\2\2\u013a"+
		"\u013b\7u\2\2\u013b\20\3\2\2\2\u013c\u013d\7e\2\2\u013d\u013e\7q\2\2\u013e"+
		"\u013f\7p\2\2\u013f\u0140\7u\2\2\u0140\u0141\7v\2\2\u0141\22\3\2\2\2\u0142"+
		"\u0143\7e\2\2\u0143\u0144\7q\2\2\u0144\u0145\7p\2\2\u0145\u0146\7v\2\2"+
		"\u0146\u0147\7k\2\2\u0147\u0148\7p\2\2\u0148\u0149\7w\2\2\u0149\u014a"+
		"\7g\2\2\u014a\24\3\2\2\2\u014b\u014c\7f\2\2\u014c\u014d\7g\2\2\u014d\u014e"+
		"\7h\2\2\u014e\u014f\7c\2\2\u014f\u0150\7w\2\2\u0150\u0151\7n\2\2\u0151"+
		"\u0152\7v\2\2\u0152\26\3\2\2\2\u0153\u0154\7f\2\2\u0154\u0155\7q\2\2\u0155"+
		"\30\3\2\2\2\u0156\u0157\7f\2\2\u0157\u0158\7q\2\2\u0158\u0159\7w\2\2\u0159"+
		"\u015a\7d\2\2\u015a\u015b\7n\2\2\u015b\u015c\7g\2\2\u015c\32\3\2\2\2\u015d"+
		"\u015e\7g\2\2\u015e\u015f\7n\2\2\u015f\u0160\7u\2\2\u0160\u0161\7g\2\2"+
		"\u0161\34\3\2\2\2\u0162\u0163\7g\2\2\u0163\u0164\7p\2\2\u0164\u0165\7"+
		"w\2\2\u0165\u0166\7o\2\2\u0166\36\3\2\2\2\u0167\u0168\7g\2\2\u0168\u0169"+
		"\7z\2\2\u0169\u016a\7v\2\2\u016a\u016b\7g\2\2\u016b\u016c\7p\2\2\u016c"+
		"\u016d\7f\2\2\u016d\u016e\7u\2\2\u016e \3\2\2\2\u016f\u0170\7h\2\2\u0170"+
		"\u0171\7k\2\2\u0171\u0172\7p\2\2\u0172\u0173\7c\2\2\u0173\u0174\7n\2\2"+
		"\u0174\"\3\2\2\2\u0175\u0176\7h\2\2\u0176\u0177\7k\2\2\u0177\u0178\7p"+
		"\2\2\u0178\u0179\7c\2\2\u0179\u017a\7n\2\2\u017a\u017b\7n\2\2\u017b\u017c"+
		"\7{\2\2\u017c$\3\2\2\2\u017d\u017e\7h\2\2\u017e\u017f\7n\2\2\u017f\u0180"+
		"\7q\2\2\u0180\u0181\7c\2\2\u0181\u0182\7v\2\2\u0182&\3\2\2\2\u0183\u0184"+
		"\7h\2\2\u0184\u0185\7q\2\2\u0185\u0186\7t\2\2\u0186(\3\2\2\2\u0187\u0188"+
		"\7k\2\2\u0188\u0189\7h\2\2\u0189*\3\2\2\2\u018a\u018b\7i\2\2\u018b\u018c"+
		"\7q\2\2\u018c\u018d\7v\2\2\u018d\u018e\7q\2\2\u018e,\3\2\2\2\u018f\u0190"+
		"\7k\2\2\u0190\u0191\7o\2\2\u0191\u0192\7r\2\2\u0192\u0193\7n\2\2\u0193"+
		"\u0194\7g\2\2\u0194\u0195\7o\2\2\u0195\u0196\7g\2\2\u0196\u0197\7p\2\2"+
		"\u0197\u0198\7v\2\2\u0198\u0199\7u\2\2\u0199.\3\2\2\2\u019a\u019b\7k\2"+
		"\2\u019b\u019c\7p\2\2\u019c\u019d\7u\2\2\u019d\u019e\7v\2\2\u019e\u019f"+
		"\7c\2\2\u019f\u01a0\7p\2\2\u01a0\u01a1\7e\2\2\u01a1\u01a2\7g\2\2\u01a2"+
		"\u01a3\7q\2\2\u01a3\u01a4\7h\2\2\u01a4\60\3\2\2\2\u01a5\u01a6\7k\2\2\u01a6"+
		"\u01a7\7p\2\2\u01a7\u01a8\7v\2\2\u01a8\u01a9\7g\2\2\u01a9\u01aa\7t\2\2"+
		"\u01aa\u01ab\7h\2\2\u01ab\u01ac\7c\2\2\u01ac\u01ad\7e\2\2\u01ad\u01ae"+
		"\7g\2\2\u01ae\62\3\2\2\2\u01af\u01b0\7n\2\2\u01b0\u01b1\7q\2\2\u01b1\u01b2"+
		"\7p\2\2\u01b2\u01b3\7i\2\2\u01b3\64\3\2\2\2\u01b4\u01b5\7p\2\2\u01b5\u01b6"+
		"\7c\2\2\u01b6\u01b7\7v\2\2\u01b7\u01b8\7k\2\2\u01b8\u01b9\7x\2\2\u01b9"+
		"\u01ba\7g\2\2\u01ba\66\3\2\2\2\u01bb\u01bc\7p\2\2\u01bc\u01bd\7g\2\2\u01bd"+
		"\u01be\7y\2\2\u01be8\3\2\2\2\u01bf\u01c0\7p\2\2\u01c0\u01c1\7w\2\2\u01c1"+
		"\u01c2\7n\2\2\u01c2\u01c3\7n\2\2\u01c3:\3\2\2\2\u01c4\u01c5\7r\2\2\u01c5"+
		"\u01c6\7c\2\2\u01c6\u01c7\7e\2\2\u01c7\u01c8\7m\2\2\u01c8\u01c9\7c\2\2"+
		"\u01c9\u01ca\7i\2\2\u01ca\u01cb\7g\2\2\u01cb<\3\2\2\2\u01cc\u01cd\7r\2"+
		"\2\u01cd\u01ce\7t\2\2\u01ce\u01cf\7k\2\2\u01cf\u01d0\7x\2\2\u01d0\u01d1"+
		"\7c\2\2\u01d1\u01d2\7v\2\2\u01d2\u01d3\7g\2\2\u01d3>\3\2\2\2\u01d4\u01d5"+
		"\7r\2\2\u01d5\u01d6\7t\2\2\u01d6\u01d7\7q\2\2\u01d7\u01d8\7v\2\2\u01d8"+
		"\u01d9\7g\2\2\u01d9\u01da\7e\2\2\u01da\u01db\7v\2\2\u01db\u01dc\7g\2\2"+
		"\u01dc\u01dd\7f\2\2\u01dd@\3\2\2\2\u01de\u01df\7r\2\2\u01df\u01e0\7w\2"+
		"\2\u01e0\u01e1\7d\2\2\u01e1\u01e2\7n\2\2\u01e2\u01e3\7k\2\2\u01e3\u01e4"+
		"\7e\2\2\u01e4B\3\2\2\2\u01e5\u01e6\7t\2\2\u01e6\u01e7\7g\2\2\u01e7\u01e8"+
		"\7v\2\2\u01e8\u01e9\7w\2\2\u01e9\u01ea\7t\2\2\u01ea\u01eb\7p\2\2\u01eb"+
		"D\3\2\2\2\u01ec\u01ed\7u\2\2\u01ed\u01ee\7j\2\2\u01ee\u01ef\7q\2\2\u01ef"+
		"\u01f0\7t\2\2\u01f0\u01f1\7v\2\2\u01f1F\3\2\2\2\u01f2\u01f3\7u\2\2\u01f3"+
		"\u01f4\7v\2\2\u01f4\u01f5\7c\2\2\u01f5\u01f6\7v\2\2\u01f6\u01f7\7k\2\2"+
		"\u01f7\u01f8\7e\2\2\u01f8H\3\2\2\2\u01f9\u01fa\7x\2\2\u01fa\u01fb\7k\2"+
		"\2\u01fb\u01fc\7t\2\2\u01fc\u01fd\7v\2\2\u01fd\u01fe\7w\2\2\u01fe\u01ff"+
		"\7c\2\2\u01ff\u0200\7n\2\2\u0200J\3\2\2\2\u0201\u0202\7u\2\2\u0202\u0203"+
		"\7w\2\2\u0203\u0204\7r\2\2\u0204\u0205\7g\2\2\u0205\u0206\7t\2\2\u0206"+
		"L\3\2\2\2\u0207\u0208\7v\2\2\u0208\u0209\7j\2\2\u0209\u020a\7k\2\2\u020a"+
		"\u020b\7u\2\2\u020bN\3\2\2\2\u020c\u020d\7v\2\2\u020d\u020e\7j\2\2\u020e"+
		"\u020f\7t\2\2\u020f\u0210\7q\2\2\u0210\u0211\7y\2\2\u0211P\3\2\2\2\u0212"+
		"\u0213\7v\2\2\u0213\u0214\7t\2\2\u0214\u0215\7c\2\2\u0215\u0216\7p\2\2"+
		"\u0216\u0217\7u\2\2\u0217\u0218\7k\2\2\u0218\u0219\7g\2\2\u0219\u021a"+
		"\7p\2\2\u021a\u021b\7v\2\2\u021bR\3\2\2\2\u021c\u021d\7v\2\2\u021d\u021e"+
		"\7t\2\2\u021e\u021f\7{\2\2\u021fT\3\2\2\2\u0220\u0221\7x\2\2\u0221\u0222"+
		"\7q\2\2\u0222\u0223\7k\2\2\u0223\u0224\7f\2\2\u0224V\3\2\2\2\u0225\u0226"+
		"\7y\2\2\u0226\u0227\7j\2\2\u0227\u0228\7k\2\2\u0228\u0229\7n\2\2\u0229"+
		"\u022a\7g\2\2\u022aX\3\2\2\2\u022b\u022c\7i\2\2\u022c\u022d\7n\2\2\u022d"+
		"\u022e\7q\2\2\u022e\u022f\7d\2\2\u022f\u0230\7c\2\2\u0230\u0231\7n\2\2"+
		"\u0231Z\3\2\2\2\u0232\u0233\7y\2\2\u0233\u0234\7g\2\2\u0234\u0235\7d\2"+
		"\2\u0235\u0236\7u\2\2\u0236\u0237\7g\2\2\u0237\u0238\7t\2\2\u0238\u0239"+
		"\7x\2\2\u0239\u023a\7k\2\2\u023a\u023b\7e\2\2\u023b\u023c\7g\2\2\u023c"+
		"\\\3\2\2\2\u023d\u023e\7u\2\2\u023e\u023f\7g\2\2\u023f\u0240\7n\2\2\u0240"+
		"\u0241\7g\2\2\u0241\u0242\7e\2\2\u0242\u0243\7v\2\2\u0243^\3\2\2\2\u0244"+
		"\u0245\7k\2\2\u0245\u0246\7p\2\2\u0246\u0247\7u\2\2\u0247\u0248\7g\2\2"+
		"\u0248\u0249\7t\2\2\u0249\u024a\7v\2\2\u024a`\3\2\2\2\u024b\u024c\7w\2"+
		"\2\u024c\u024d\7r\2\2\u024d\u024e\7u\2\2\u024e\u024f\7g\2\2\u024f\u0250"+
		"\7t\2\2\u0250\u0251\7v\2\2\u0251b\3\2\2\2\u0252\u0253\7w\2\2\u0253\u0254"+
		"\7r\2\2\u0254\u0255\7f\2\2\u0255\u0256\7c\2\2\u0256\u0257\7v\2\2\u0257"+
		"\u0258\7g\2\2\u0258d\3\2\2\2\u0259\u025a\7f\2\2\u025a\u025b\7g\2\2\u025b"+
		"\u025c\7n\2\2\u025c\u025d\7g\2\2\u025d\u025e\7v\2\2\u025e\u025f\7g\2\2"+
		"\u025ff\3\2\2\2\u0260\u0261\7w\2\2\u0261\u0262\7p\2\2\u0262\u0263\7f\2"+
		"\2\u0263\u0264\7g\2\2\u0264\u0265\7n\2\2\u0265\u0266\7g\2\2\u0266\u0267"+
		"\7v\2\2\u0267\u0268\7g\2\2\u0268h\3\2\2\2\u0269\u026a\7o\2\2\u026a\u026b"+
		"\7g\2\2\u026b\u026c\7t\2\2\u026c\u026d\7i\2\2\u026d\u026e\7g\2\2\u026e"+
		"j\3\2\2\2\u026f\u0270\7v\2\2\u0270\u0271\7g\2\2\u0271\u0272\7u\2\2\u0272"+
		"\u0273\7v\2\2\u0273\u0274\7o\2\2\u0274\u0275\7g\2\2\u0275\u0276\7v\2\2"+
		"\u0276\u0277\7j\2\2\u0277\u0278\7q\2\2\u0278\u0279\7f\2\2\u0279l\3\2\2"+
		"\2\u027a\u027b\7q\2\2\u027b\u027c\7x\2\2\u027c\u027d\7g\2\2\u027d\u027e"+
		"\7t\2\2\u027e\u027f\7t\2\2\u027f\u0280\7k\2\2\u0280\u0281\7f\2\2\u0281"+
		"\u0282\7g\2\2\u0282n\3\2\2\2\u0283\u0284\7i\2\2\u0284\u0285\7g\2\2\u0285"+
		"\u0286\7v\2\2\u0286p\3\2\2\2\u0287\u0288\7u\2\2\u0288\u0289\7g\2\2\u0289"+
		"\u028a\7v\2\2\u028ar\3\2\2\2\u028b\u028c\7d\2\2\u028c\u028d\7n\2\2\u028d"+
		"\u028e\7q\2\2\u028e\u028f\7d\2\2\u028ft\3\2\2\2\u0290\u0291\7f\2\2\u0291"+
		"\u0292\7c\2\2\u0292\u0293\7v\2\2\u0293\u0294\7g\2\2\u0294v\3\2\2\2\u0295"+
		"\u0296\7f\2\2\u0296\u0297\7c\2\2\u0297\u0298\7v\2\2\u0298\u0299\7g\2\2"+
		"\u0299\u029a\7v\2\2\u029a\u029b\7k\2\2\u029b\u029c\7o\2\2\u029c\u029d"+
		"\7g\2\2\u029dx\3\2\2\2\u029e\u029f\7f\2\2\u029f\u02a0\7g\2\2\u02a0\u02a1"+
		"\7e\2\2\u02a1\u02a2\7k\2\2\u02a2\u02a3\7o\2\2\u02a3\u02a4\7c\2\2\u02a4"+
		"\u02a5\7n\2\2\u02a5z\3\2\2\2\u02a6\u02a7\7k\2\2\u02a7\u02a8\7f\2\2\u02a8"+
		"|\3\2\2\2\u02a9\u02aa\7k\2\2\u02aa\u02ab\7p\2\2\u02ab\u02ac\7v\2\2\u02ac"+
		"\u02ad\7g\2\2\u02ad\u02ae\7i\2\2\u02ae\u02af\7g\2\2\u02af\u02b0\7t\2\2"+
		"\u02b0~\3\2\2\2\u02b1\u02b2\7q\2\2\u02b2\u02b3\7d\2\2\u02b3\u02b4\7l\2"+
		"\2\u02b4\u02b5\7g\2\2\u02b5\u02b6\7e\2\2\u02b6\u02b7\7v\2\2\u02b7\u0080"+
		"\3\2\2\2\u02b8\u02b9\7u\2\2\u02b9\u02ba\7v\2\2\u02ba\u02bb\7t\2\2\u02bb"+
		"\u02bc\7k\2\2\u02bc\u02bd\7p\2\2\u02bd\u02be\7i\2\2\u02be\u0082\3\2\2"+
		"\2\u02bf\u02c0\7v\2\2\u02c0\u02c1\7k\2\2\u02c1\u02c2\7o\2\2\u02c2\u02c3"+
		"\7g\2\2\u02c3\u0084\3\2\2\2\u02c4\u02c5\7u\2\2\u02c5\u02c6\7{\2\2\u02c6"+
		"\u02c7\7u\2\2\u02c7\u02c8\7v\2\2\u02c8\u02c9\7g\2\2\u02c9\u02ca\7o\2\2"+
		"\u02ca\u02cb\7\60\2\2\u02cb\u02cc\7t\2\2\u02cc\u02cd\7w\2\2\u02cd\u02ce"+
		"\7p\2\2\u02ce\u02cf\7c\2\2\u02cf\u02d0\7u\2\2\u02d0\u0086\3\2\2\2\u02d1"+
		"\u02d2\7y\2\2\u02d2\u02d3\7k\2\2\u02d3\u02d4\7v\2\2\u02d4\u02d5\7j\2\2"+
		"\u02d5\u0088\3\2\2\2\u02d6\u02d7\7y\2\2\u02d7\u02d8\7k\2\2\u02d8\u02d9"+
		"\7v\2\2\u02d9\u02da\7j\2\2\u02da\u02db\7q\2\2\u02db\u02dc\7w\2\2\u02dc"+
		"\u02dd\7v\2\2\u02dd\u008a\3\2\2\2\u02de\u02df\7u\2\2\u02df\u02e0\7j\2"+
		"\2\u02e0\u02e1\7c\2\2\u02e1\u02e2\7t\2\2\u02e2\u02e3\7k\2\2\u02e3\u02e4"+
		"\7p\2\2\u02e4\u02e5\7i\2\2\u02e5\u008c\3\2\2\2\u02e6\u02e7\7k\2\2\u02e7"+
		"\u02e8\7p\2\2\u02e8\u02e9\7j\2\2\u02e9\u02ea\7g\2\2\u02ea\u02eb\7t\2\2"+
		"\u02eb\u02ec\7k\2\2\u02ec\u02ed\7v\2\2\u02ed\u02ee\7g\2\2\u02ee\u02ef"+
		"\7f\2\2\u02ef\u008e\3\2\2\2\u02f0\u02f4\5\u0093J\2\u02f1\u02f3\5\u0093"+
		"J\2\u02f2\u02f1\3\2\2\2\u02f3\u02f6\3\2\2\2\u02f4\u02f2\3\2\2\2\u02f4"+
		"\u02f5\3\2\2\2\u02f5\u02f8\3\2\2\2\u02f6\u02f4\3\2\2\2\u02f7\u02f9\t\2"+
		"\2\2\u02f8\u02f7\3\2\2\2\u02f8\u02f9\3\2\2\2\u02f9\u0090\3\2\2\2\u02fa"+
		"\u02fc\5\u0093J\2\u02fb\u02fa\3\2\2\2\u02fc\u02ff\3\2\2\2\u02fd\u02fb"+
		"\3\2\2\2\u02fd\u02fe\3\2\2\2\u02fe\u0300\3\2\2\2\u02ff\u02fd\3\2\2\2\u0300"+
		"\u0301\7\60\2\2\u0301\u0305\5\u0093J\2\u0302\u0304\5\u0093J\2\u0303\u0302"+
		"\3\2\2\2\u0304\u0307\3\2\2\2\u0305\u0303\3\2\2\2\u0305\u0306\3\2\2\2\u0306"+
		"\u0309\3\2\2\2\u0307\u0305\3\2\2\2\u0308\u030a\t\3\2\2\u0309\u0308\3\2"+
		"\2\2\u0309\u030a\3\2\2\2\u030a\u0092\3\2\2\2\u030b\u030c\t\4\2\2\u030c"+
		"\u0094\3\2\2\2\u030d\u030e\7v\2\2\u030e\u030f\7t\2\2\u030f\u0310\7w\2"+
		"\2\u0310\u0317\7g\2\2\u0311\u0312\7h\2\2\u0312\u0313\7c\2\2\u0313\u0314"+
		"\7n\2\2\u0314\u0315\7u\2\2\u0315\u0317\7g\2\2\u0316\u030d\3\2\2\2\u0316"+
		"\u0311\3\2\2\2\u0317\u0096\3\2\2\2\u0318\u031a\7)\2\2\u0319\u031b\5\u0099"+
		"M\2\u031a\u0319\3\2\2\2\u031a\u031b\3\2\2\2\u031b\u031c\3\2\2\2\u031c"+
		"\u031d\7)\2\2\u031d\u0098\3\2\2\2\u031e\u0320\5\u009bN\2\u031f\u031e\3"+
		"\2\2\2\u0320\u0321\3\2\2\2\u0321\u031f\3\2\2\2\u0321\u0322\3\2\2\2\u0322"+
		"\u009a\3\2\2\2\u0323\u0326\n\5\2\2\u0324\u0326\5\u009dO\2\u0325\u0323"+
		"\3\2\2\2\u0325\u0324\3\2\2\2\u0326\u009c\3\2\2\2\u0327\u0328\7^\2\2\u0328"+
		"\u0329\t\6\2\2\u0329\u009e\3\2\2\2\u032a\u032b\59\35\2\u032b\u00a0\3\2"+
		"\2\2\u032c\u032d\7*\2\2\u032d\u00a2\3\2\2\2\u032e\u032f\7+\2\2\u032f\u00a4"+
		"\3\2\2\2\u0330\u0331\7}\2\2\u0331\u00a6\3\2\2\2\u0332\u0333\7\177\2\2"+
		"\u0333\u00a8\3\2\2\2\u0334\u0335\7]\2\2\u0335\u00aa\3\2\2\2\u0336\u0337"+
		"\7_\2\2\u0337\u00ac\3\2\2\2\u0338\u0339\7=\2\2\u0339\u00ae\3\2\2\2\u033a"+
		"\u033b\7.\2\2\u033b\u00b0\3\2\2\2\u033c\u033d\7\60\2\2\u033d\u00b2\3\2"+
		"\2\2\u033e\u033f\7?\2\2\u033f\u00b4\3\2\2\2\u0340\u0341\7>\2\2\u0341\u0342"+
		"\7?\2\2\u0342\u00b6\3\2\2\2\u0343\u0344\7@\2\2\u0344\u0345\7?\2\2\u0345"+
		"\u00b8\3\2\2\2\u0346\u0347\7@\2\2\u0347\u00ba\3\2\2\2\u0348\u0349\7>\2"+
		"\2\u0349\u00bc\3\2\2\2\u034a\u034b\7#\2\2\u034b\u00be\3\2\2\2\u034c\u034d"+
		"\7\u0080\2\2\u034d\u00c0\3\2\2\2\u034e\u034f\7A\2\2\u034f\u00c2\3\2\2"+
		"\2\u0350\u0351\7<\2\2\u0351\u00c4\3\2\2\2\u0352\u0353\7?\2\2\u0353\u0354"+
		"\7?\2\2\u0354\u00c6\3\2\2\2\u0355\u0356\7?\2\2\u0356\u0357\7?\2\2\u0357"+
		"\u0358\7?\2\2\u0358\u00c8\3\2\2\2\u0359\u035a\7#\2\2\u035a\u035b\7?\2"+
		"\2\u035b\u00ca\3\2\2\2\u035c\u035d\7>\2\2\u035d\u035e\7@\2\2\u035e\u00cc"+
		"\3\2\2\2\u035f\u0360\7#\2\2\u0360\u0361\7?\2\2\u0361\u0362\7?\2\2\u0362"+
		"\u00ce\3\2\2\2\u0363\u0364\7(\2\2\u0364\u0365\7(\2\2\u0365\u00d0\3\2\2"+
		"\2\u0366\u0367\7~\2\2\u0367\u0368\7~\2\2\u0368\u00d2\3\2\2\2\u0369\u036a"+
		"\7-\2\2\u036a\u036b\7-\2\2\u036b\u00d4\3\2\2\2\u036c\u036d\7/\2\2\u036d"+
		"\u036e\7/\2\2\u036e\u00d6\3\2\2\2\u036f\u0370\7-\2\2\u0370\u00d8\3\2\2"+
		"\2\u0371\u0372\7/\2\2\u0372\u00da\3\2\2\2\u0373\u0374\7,\2\2\u0374\u00dc"+
		"\3\2\2\2\u0375\u0376\7\61\2\2\u0376\u00de\3\2\2\2\u0377\u0378\7(\2\2\u0378"+
		"\u00e0\3\2\2\2\u0379\u037a\7~\2\2\u037a\u00e2\3\2\2\2\u037b\u037c\7`\2"+
		"\2\u037c\u00e4\3\2\2\2\u037d\u037e\7\'\2\2\u037e\u00e6\3\2\2\2\u037f\u0380"+
		"\7?\2\2\u0380\u0381\7@\2\2\u0381\u00e8\3\2\2\2\u0382\u0383\7-\2\2\u0383"+
		"\u0384\7?\2\2\u0384\u00ea\3\2\2\2\u0385\u0386\7/\2\2\u0386\u0387\7?\2"+
		"\2\u0387\u00ec\3\2\2\2\u0388\u0389\7,\2\2\u0389\u038a\7?\2\2\u038a\u00ee"+
		"\3\2\2\2\u038b\u038c\7\61\2\2\u038c\u038d\7?\2\2\u038d\u00f0\3\2\2\2\u038e"+
		"\u038f\7(\2\2\u038f\u0390\7?\2\2\u0390\u00f2\3\2\2\2\u0391\u0392\7~\2"+
		"\2\u0392\u0393\7?\2\2\u0393\u00f4\3\2\2\2\u0394\u0395\7`\2\2\u0395\u0396"+
		"\7?\2\2\u0396\u00f6\3\2\2\2\u0397\u0398\7\'\2\2\u0398\u0399\7?\2\2\u0399"+
		"\u00f8\3\2\2\2\u039a\u039b\7>\2\2\u039b\u039c\7>\2\2\u039c\u039d\7?\2"+
		"\2\u039d\u00fa\3\2\2\2\u039e\u039f\7@\2\2\u039f\u03a0\7@\2\2\u03a0\u03a1"+
		"\7?\2\2\u03a1\u00fc\3\2\2\2\u03a2\u03a3\7@\2\2\u03a3\u03a4\7@\2\2\u03a4"+
		"\u03a5\7@\2\2\u03a5\u03a6\7?\2\2\u03a6\u00fe\3\2\2\2\u03a7\u03ab\5\u0101"+
		"\u0081\2\u03a8\u03aa\5\u0103\u0082\2\u03a9\u03a8\3\2\2\2\u03aa\u03ad\3"+
		"\2\2\2\u03ab\u03a9\3\2\2\2\u03ab\u03ac\3\2\2\2\u03ac\u0100\3\2\2\2\u03ad"+
		"\u03ab\3\2\2\2\u03ae\u03b5\t\7\2\2\u03af\u03b0\n\b\2\2\u03b0\u03b5\6\u0081"+
		"\2\2\u03b1\u03b2\t\t\2\2\u03b2\u03b3\t\n\2\2\u03b3\u03b5\6\u0081\3\2\u03b4"+
		"\u03ae\3\2\2\2\u03b4\u03af\3\2\2\2\u03b4\u03b1\3\2\2\2\u03b5\u0102\3\2"+
		"\2\2\u03b6\u03bd\t\13\2\2\u03b7\u03b8\n\b\2\2\u03b8\u03bd\6\u0082\4\2"+
		"\u03b9\u03ba\t\t\2\2\u03ba\u03bb\t\n\2\2\u03bb\u03bd\6\u0082\5\2\u03bc"+
		"\u03b6\3\2\2\2\u03bc\u03b7\3\2\2\2\u03bc\u03b9\3\2\2\2\u03bd\u0104\3\2"+
		"\2\2\u03be\u03bf\7B\2\2\u03bf\u0106\3\2\2\2\u03c0\u03c2\t\f\2\2\u03c1"+
		"\u03c0\3\2\2\2\u03c2\u03c3\3\2\2\2\u03c3\u03c1\3\2\2\2\u03c3\u03c4\3\2"+
		"\2\2\u03c4\u03c5\3\2\2\2\u03c5\u03c6\b\u0084\2\2\u03c6\u0108\3\2\2\2\u03c7"+
		"\u03c8\7\61\2\2\u03c8\u03c9\7,\2\2\u03c9\u03ca\7,\2\2\u03ca\u03cb\3\2"+
		"\2\2\u03cb\u03cf\t\r\2\2\u03cc\u03ce\13\2\2\2\u03cd\u03cc\3\2\2\2\u03ce"+
		"\u03d1\3\2\2\2\u03cf\u03d0\3\2\2\2\u03cf\u03cd\3\2\2\2\u03d0\u03d2\3\2"+
		"\2\2\u03d1\u03cf\3\2\2\2\u03d2\u03d3\7,\2\2\u03d3\u03d4\7\61\2\2\u03d4"+
		"\u03d5\3\2\2\2\u03d5\u03d6\b\u0085\3\2\u03d6\u010a\3\2\2\2\u03d7\u03d8"+
		"\7\61\2\2\u03d8\u03d9\7,\2\2\u03d9\u03dd\3\2\2\2\u03da\u03dc\13\2\2\2"+
		"\u03db\u03da\3\2\2\2\u03dc\u03df\3\2\2\2\u03dd\u03de\3\2\2\2\u03dd\u03db"+
		"\3\2\2\2\u03de\u03e0\3\2\2\2\u03df\u03dd\3\2\2\2\u03e0\u03e1\7,\2\2\u03e1"+
		"\u03e2\7\61\2\2\u03e2\u03e3\3\2\2\2\u03e3\u03e4\b\u0086\3\2\u03e4\u010c"+
		"\3\2\2\2\u03e5\u03e6\7\61\2\2\u03e6\u03e7\7\61\2\2\u03e7\u03eb\3\2\2\2"+
		"\u03e8\u03ea\n\r\2\2\u03e9\u03e8\3\2\2\2\u03ea\u03ed\3\2\2\2\u03eb\u03e9"+
		"\3\2\2\2\u03eb\u03ec\3\2\2\2\u03ec\u03ee\3\2\2\2\u03ed\u03eb\3\2\2\2\u03ee"+
		"\u03ef\b\u0087\3\2\u03ef\u010e\3\2\2\2\23\2\u02f4\u02f8\u02fd\u0305\u0309"+
		"\u0316\u031a\u0321\u0325\u03ab\u03b4\u03bc\u03c3\u03cf\u03dd\u03eb\4\2"+
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