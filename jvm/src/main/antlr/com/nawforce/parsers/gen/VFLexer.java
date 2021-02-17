// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/VFLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VFLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, PI_START=2, OPEN=3, OPEN_SCRIPT=4, CDATA_START=5, EL_START=6, 
		CHARDATA_REF=7, WS_NL=8, TEXT=9, CLOSE_SCRIPT=10, CLOSE_OPEN_SCRIPT=11, 
		ScriptName=12, SCRIPT_ATTRS_START=13, SCRIPT_ATTRD_START=14, SCRIPT_WS=15, 
		END_SCRIPT=16, SCRIPT_EL_START=17, SCRIPT_CHARDATA_REF=18, SCRIPT_WS_NL=19, 
		SCRIPT_TEXT=20, PI_END=21, CLOSE=22, SLASH_CLOSE=23, SLASH=24, EQUALS=25, 
		STRING=26, ATTRS_START=27, ATTRD_START=28, WS=29, Name=30, EL_END=31, 
		EL_BODY=32, CDATA_END=33, CDATA_EL=34, CDATA_TEXT=35, ATTRS_END=36, ATTRS_EL_START=37, 
		ATTRS_TEXT=38, ATTRD_END=39, ATTRD_EL_START=40, ATTRD_TEXT=41;
	public static final int
		IN_SCRIPT_TAG=1, IN_SCRIPT=2, INTAG=3, EL=4, CDATA=5, ATTRS=6, ATTRD=7;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "IN_SCRIPT_TAG", "IN_SCRIPT", "INTAG", "EL", "CDATA", 
		"ATTRS", "ATTRD"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMMENT", "PI_START", "OPEN", "OPEN_SCRIPT", "CDATA_START", "EL_START", 
			"CHARDATA_REF", "WS_NL", "TEXT", "CLOSE_SCRIPT", "CLOSE_OPEN_SCRIPT", 
			"ScriptName", "SCRIPT_ATTRS_START", "SCRIPT_ATTRD_START", "SCRIPT_WS", 
			"END_SCRIPT", "SCRIPT_EL_START", "SCRIPT_CHARDATA_REF", "SCRIPT_WS_NL", 
			"SCRIPT_TEXT", "PI_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", "STRING", 
			"ATTRS_START", "ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", "CDATA_END", 
			"CDATA_EL", "CDATA_TEXT", "ATTRS_END", "ATTRS_EL_START", "ATTRS_TEXT", 
			"ATTRD_END", "ATTRD_EL_START", "ATTRD_TEXT", "NameChar", "NameStartChar", 
			"Ref"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'<'", "'<script'", "'<![CDATA['", null, null, null, 
			null, null, null, null, null, null, null, "'</script>'", null, null, 
			null, null, "'?>'", null, null, "'/'", "'='", null, null, null, null, 
			null, "'}'", null, "']]>'", null, null, "'''", null, null, "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMMENT", "PI_START", "OPEN", "OPEN_SCRIPT", "CDATA_START", "EL_START", 
			"CHARDATA_REF", "WS_NL", "TEXT", "CLOSE_SCRIPT", "CLOSE_OPEN_SCRIPT", 
			"ScriptName", "SCRIPT_ATTRS_START", "SCRIPT_ATTRD_START", "SCRIPT_WS", 
			"END_SCRIPT", "SCRIPT_EL_START", "SCRIPT_CHARDATA_REF", "SCRIPT_WS_NL", 
			"SCRIPT_TEXT", "PI_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", "STRING", 
			"ATTRS_START", "ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", "CDATA_END", 
			"CDATA_EL", "CDATA_TEXT", "ATTRS_END", "ATTRS_EL_START", "ATTRS_TEXT", 
			"ATTRD_END", "ATTRD_EL_START", "ATTRD_TEXT"
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


	public VFLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VFLexer.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u01c4\b\1\b\1\b"+
		"\1\b\1\b\1\b\1\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7"+
		"\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17"+
		"\4\20\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26"+
		"\4\27\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35"+
		"\4\36\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t"+
		"\'\4(\t(\4)\t)\4*\t*\4+\t+\4,\t,\4-\t-\3\2\3\2\3\2\3\2\3\2\3\2\7\2i\n"+
		"\2\f\2\16\2l\13\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3w\n\3\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\5\t\u009e\n\t\3\t\6\t\u00a1\n\t\r\t\16\t\u00a2\3\n\6\n\u00a6\n\n\r\n"+
		"\16\n\u00a7\3\n\3\n\3\n\7\n\u00ad\n\n\f\n\16\n\u00b0\13\n\3\n\5\n\u00b3"+
		"\n\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\7\r\u00c0\n\r\f"+
		"\r\16\r\u00c3\13\r\3\16\3\16\7\16\u00c7\n\16\f\16\16\16\u00ca\13\16\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\7\17\u00d2\n\17\f\17\16\17\u00d5\13\17\3"+
		"\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3"+
		"\24\3\24\5\24\u00f5\n\24\3\24\6\24\u00f8\n\24\r\24\16\24\u00f9\3\25\6"+
		"\25\u00fd\n\25\r\25\16\25\u00fe\3\25\3\25\3\25\7\25\u0104\n\25\f\25\16"+
		"\25\u0107\13\25\3\25\3\25\3\25\7\25\u010c\n\25\f\25\16\25\u010f\13\25"+
		"\5\25\u0111\n\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\7\33\u0127\n\33\f\33\16"+
		"\33\u012a\13\33\3\33\3\33\3\34\3\34\7\34\u0130\n\34\f\34\16\34\u0133\13"+
		"\34\3\34\3\34\3\34\3\34\3\35\3\35\7\35\u013b\n\35\f\35\16\35\u013e\13"+
		"\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37\7\37\u014a\n\37"+
		"\f\37\16\37\u014d\13\37\3 \3 \3 \3 \3!\6!\u0154\n!\r!\16!\u0155\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\6$\u0164\n$\r$\16$\u0165\3$\3$\3"+
		"$\7$\u016b\n$\f$\16$\u016e\13$\3$\5$\u0171\n$\3%\3%\3%\3%\3&\3&\3&\3&"+
		"\3&\3\'\6\'\u017d\n\'\r\'\16\'\u017e\3\'\3\'\3\'\7\'\u0184\n\'\f\'\16"+
		"\'\u0187\13\'\3\'\5\'\u018a\n\'\3(\3(\3(\3(\3)\3)\3)\3)\3)\3*\6*\u0196"+
		"\n*\r*\16*\u0197\3*\3*\3*\7*\u019d\n*\f*\16*\u01a0\13*\3*\5*\u01a3\n*"+
		"\3+\3+\5+\u01a7\n+\3,\5,\u01aa\n,\3-\3-\3-\3-\3-\3-\3-\3-\6-\u01b4\n-"+
		"\r-\16-\u01b5\3-\3-\3-\3-\3-\3-\6-\u01be\n-\r-\16-\u01bf\3-\5-\u01c3\n"+
		"-\3j\2.\n\3\f\4\16\5\20\6\22\7\24\b\26\t\30\n\32\13\34\f\36\r \16\"\17"+
		"$\20&\21(\22*\23,\24.\25\60\26\62\27\64\30\66\318\32:\33<\34>\35@\36B"+
		"\37D F!H\"J#L$N%P&R\'T(V)X*Z+\\\2^\2`\2\n\2\3\4\5\6\7\b\t\24\4\2\13\13"+
		"\"\"\5\2((>>}}\6\2##((>>}}\5\2\13\f\17\17\"\"\4\2>>}}\5\2##>>}}\4\2\61"+
		"\61>>\4\2$$>>\3\2\177\177\4\2__}}\4\2))}}\5\2##))}}\4\2$$}}\4\2#$}}\b"+
		"\2/\60\62;aa\u00b9\u00b9\u0302\u0371\u2041\u2042\n\2<<C\\c|\u2072\u2191"+
		"\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\3\2\62;\5\2\62;CHch\2"+
		"\u01e4\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2"+
		"\2\24\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\3\34\3\2\2\2\3\36"+
		"\3\2\2\2\3 \3\2\2\2\3\"\3\2\2\2\3$\3\2\2\2\3&\3\2\2\2\4(\3\2\2\2\4*\3"+
		"\2\2\2\4,\3\2\2\2\4.\3\2\2\2\4\60\3\2\2\2\5\62\3\2\2\2\5\64\3\2\2\2\5"+
		"\66\3\2\2\2\58\3\2\2\2\5:\3\2\2\2\5<\3\2\2\2\5>\3\2\2\2\5@\3\2\2\2\5B"+
		"\3\2\2\2\5D\3\2\2\2\6F\3\2\2\2\6H\3\2\2\2\7J\3\2\2\2\7L\3\2\2\2\7N\3\2"+
		"\2\2\bP\3\2\2\2\bR\3\2\2\2\bT\3\2\2\2\tV\3\2\2\2\tX\3\2\2\2\tZ\3\2\2\2"+
		"\nb\3\2\2\2\fq\3\2\2\2\16z\3\2\2\2\20~\3\2\2\2\22\u0088\3\2\2\2\24\u0094"+
		"\3\2\2\2\26\u0099\3\2\2\2\30\u00a0\3\2\2\2\32\u00b2\3\2\2\2\34\u00b4\3"+
		"\2\2\2\36\u00b9\3\2\2\2 \u00bd\3\2\2\2\"\u00c4\3\2\2\2$\u00cf\3\2\2\2"+
		"&\u00da\3\2\2\2(\u00de\3\2\2\2*\u00eb\3\2\2\2,\u00f0\3\2\2\2.\u00f7\3"+
		"\2\2\2\60\u0110\3\2\2\2\62\u0112\3\2\2\2\64\u0117\3\2\2\2\66\u011b\3\2"+
		"\2\28\u0120\3\2\2\2:\u0122\3\2\2\2<\u0124\3\2\2\2>\u012d\3\2\2\2@\u0138"+
		"\3\2\2\2B\u0143\3\2\2\2D\u0147\3\2\2\2F\u014e\3\2\2\2H\u0153\3\2\2\2J"+
		"\u0157\3\2\2\2L\u015d\3\2\2\2N\u0170\3\2\2\2P\u0172\3\2\2\2R\u0176\3\2"+
		"\2\2T\u0189\3\2\2\2V\u018b\3\2\2\2X\u018f\3\2\2\2Z\u01a2\3\2\2\2\\\u01a6"+
		"\3\2\2\2^\u01a9\3\2\2\2`\u01c2\3\2\2\2bc\7>\2\2cd\7#\2\2de\7/\2\2ef\7"+
		"/\2\2fj\3\2\2\2gi\13\2\2\2hg\3\2\2\2il\3\2\2\2jk\3\2\2\2jh\3\2\2\2km\3"+
		"\2\2\2lj\3\2\2\2mn\7/\2\2no\7/\2\2op\7@\2\2p\13\3\2\2\2qr\7>\2\2rs\7A"+
		"\2\2st\3\2\2\2tv\5D\37\2uw\5B\36\2vu\3\2\2\2vw\3\2\2\2wx\3\2\2\2xy\b\3"+
		"\2\2y\r\3\2\2\2z{\7>\2\2{|\3\2\2\2|}\b\4\2\2}\17\3\2\2\2~\177\7>\2\2\177"+
		"\u0080\7u\2\2\u0080\u0081\7e\2\2\u0081\u0082\7t\2\2\u0082\u0083\7k\2\2"+
		"\u0083\u0084\7r\2\2\u0084\u0085\7v\2\2\u0085\u0086\3\2\2\2\u0086\u0087"+
		"\b\5\3\2\u0087\21\3\2\2\2\u0088\u0089\7>\2\2\u0089\u008a\7#\2\2\u008a"+
		"\u008b\7]\2\2\u008b\u008c\7E\2\2\u008c\u008d\7F\2\2\u008d\u008e\7C\2\2"+
		"\u008e\u008f\7V\2\2\u008f\u0090\7C\2\2\u0090\u0091\7]\2\2\u0091\u0092"+
		"\3\2\2\2\u0092\u0093\b\6\4\2\u0093\23\3\2\2\2\u0094\u0095\7}\2\2\u0095"+
		"\u0096\7#\2\2\u0096\u0097\3\2\2\2\u0097\u0098\b\7\5\2\u0098\25\3\2\2\2"+
		"\u0099\u009a\5`-\2\u009a\27\3\2\2\2\u009b\u00a1\t\2\2\2\u009c\u009e\7"+
		"\17\2\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f"+
		"\u00a1\7\f\2\2\u00a0\u009b\3\2\2\2\u00a0\u009d\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\31\3\2\2\2\u00a4\u00a6"+
		"\n\3\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00b3\3\2\2\2\u00a9\u00aa\7}\2\2\u00aa\u00ae\n\4"+
		"\2\2\u00ab\u00ad\n\3\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae"+
		"\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b3\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b1\u00b3\7}\2\2\u00b2\u00a5\3\2\2\2\u00b2\u00a9\3\2\2\2\u00b2"+
		"\u00b1\3\2\2\2\u00b3\33\3\2\2\2\u00b4\u00b5\7\61\2\2\u00b5\u00b6\7@\2"+
		"\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\b\13\6\2\u00b8\35\3\2\2\2\u00b9\u00ba"+
		"\7@\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\b\f\7\2\u00bc\37\3\2\2\2\u00bd"+
		"\u00c1\5^,\2\u00be\u00c0\5\\+\2\u00bf\u00be\3\2\2\2\u00c0\u00c3\3\2\2"+
		"\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2!\3\2\2\2\u00c3\u00c1"+
		"\3\2\2\2\u00c4\u00c8\7?\2\2\u00c5\u00c7\t\2\2\2\u00c6\u00c5\3\2\2\2\u00c7"+
		"\u00ca\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cb\3\2"+
		"\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00cc\7)\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\u00ce\b\16\b\2\u00ce#\3\2\2\2\u00cf\u00d3\7?\2\2\u00d0\u00d2\t\2\2\2"+
		"\u00d1\u00d0\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4"+
		"\3\2\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6\u00d7\7$\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00d9\b\17\t\2\u00d9%\3\2\2\2\u00da\u00db\t\5\2\2"+
		"\u00db\u00dc\3\2\2\2\u00dc\u00dd\b\20\n\2\u00dd\'\3\2\2\2\u00de\u00df"+
		"\7>\2\2\u00df\u00e0\7\61\2\2\u00e0\u00e1\7u\2\2\u00e1\u00e2\7e\2\2\u00e2"+
		"\u00e3\7t\2\2\u00e3\u00e4\7k\2\2\u00e4\u00e5\7r\2\2\u00e5\u00e6\7v\2\2"+
		"\u00e6\u00e7\7@\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\b\21\6\2\u00e9\u00ea"+
		"\b\21\6\2\u00ea)\3\2\2\2\u00eb\u00ec\7}\2\2\u00ec\u00ed\7#\2\2\u00ed\u00ee"+
		"\3\2\2\2\u00ee\u00ef\b\22\5\2\u00ef+\3\2\2\2\u00f0\u00f1\5`-\2\u00f1-"+
		"\3\2\2\2\u00f2\u00f8\t\2\2\2\u00f3\u00f5\7\17\2\2\u00f4\u00f3\3\2\2\2"+
		"\u00f4\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8\7\f\2\2\u00f7\u00f2"+
		"\3\2\2\2\u00f7\u00f4\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa/\3\2\2\2\u00fb\u00fd\n\6\2\2\u00fc\u00fb\3\2\2\2"+
		"\u00fd\u00fe\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0111"+
		"\3\2\2\2\u0100\u0101\7}\2\2\u0101\u0105\n\7\2\2\u0102\u0104\n\6\2\2\u0103"+
		"\u0102\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2"+
		"\2\2\u0106\u0111\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u0111\7}\2\2\u0109"+
		"\u010d\7>\2\2\u010a\u010c\n\b\2\2\u010b\u010a\3\2\2\2\u010c\u010f\3\2"+
		"\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0111\3\2\2\2\u010f"+
		"\u010d\3\2\2\2\u0110\u00fc\3\2\2\2\u0110\u0100\3\2\2\2\u0110\u0108\3\2"+
		"\2\2\u0110\u0109\3\2\2\2\u0111\61\3\2\2\2\u0112\u0113\7A\2\2\u0113\u0114"+
		"\7@\2\2\u0114\u0115\3\2\2\2\u0115\u0116\b\26\6\2\u0116\63\3\2\2\2\u0117"+
		"\u0118\7@\2\2\u0118\u0119\3\2\2\2\u0119\u011a\b\27\6\2\u011a\65\3\2\2"+
		"\2\u011b\u011c\7\61\2\2\u011c\u011d\7@\2\2\u011d\u011e\3\2\2\2\u011e\u011f"+
		"\b\30\6\2\u011f\67\3\2\2\2\u0120\u0121\7\61\2\2\u01219\3\2\2\2\u0122\u0123"+
		"\7?\2\2\u0123;\3\2\2\2\u0124\u0128\7$\2\2\u0125\u0127\n\t\2\2\u0126\u0125"+
		"\3\2\2\2\u0127\u012a\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129"+
		"\u012b\3\2\2\2\u012a\u0128\3\2\2\2\u012b\u012c\7$\2\2\u012c=\3\2\2\2\u012d"+
		"\u0131\7?\2\2\u012e\u0130\t\2\2\2\u012f\u012e\3\2\2\2\u0130\u0133\3\2"+
		"\2\2\u0131\u012f\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0134\3\2\2\2\u0133"+
		"\u0131\3\2\2\2\u0134\u0135\7)\2\2\u0135\u0136\3\2\2\2\u0136\u0137\b\34"+
		"\b\2\u0137?\3\2\2\2\u0138\u013c\7?\2\2\u0139\u013b\t\2\2\2\u013a\u0139"+
		"\3\2\2\2\u013b\u013e\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d"+
		"\u013f\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0140\7$\2\2\u0140\u0141\3\2"+
		"\2\2\u0141\u0142\b\35\t\2\u0142A\3\2\2\2\u0143\u0144\t\5\2\2\u0144\u0145"+
		"\3\2\2\2\u0145\u0146\b\36\n\2\u0146C\3\2\2\2\u0147\u014b\5^,\2\u0148\u014a"+
		"\5\\+\2\u0149\u0148\3\2\2\2\u014a\u014d\3\2\2\2\u014b\u0149\3\2\2\2\u014b"+
		"\u014c\3\2\2\2\u014cE\3\2\2\2\u014d\u014b\3\2\2\2\u014e\u014f\7\177\2"+
		"\2\u014f\u0150\3\2\2\2\u0150\u0151\b \6\2\u0151G\3\2\2\2\u0152\u0154\n"+
		"\n\2\2\u0153\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0153\3\2\2\2\u0155"+
		"\u0156\3\2\2\2\u0156I\3\2\2\2\u0157\u0158\7_\2\2\u0158\u0159\7_\2\2\u0159"+
		"\u015a\7@\2\2\u015a\u015b\3\2\2\2\u015b\u015c\b\"\6\2\u015cK\3\2\2\2\u015d"+
		"\u015e\7}\2\2\u015e\u015f\7#\2\2\u015f\u0160\3\2\2\2\u0160\u0161\b#\5"+
		"\2\u0161M\3\2\2\2\u0162\u0164\n\13\2\2\u0163\u0162\3\2\2\2\u0164\u0165"+
		"\3\2\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0171\3\2\2\2\u0167"+
		"\u0168\7_\2\2\u0168\u016c\n\13\2\2\u0169\u016b\n\13\2\2\u016a\u0169\3"+
		"\2\2\2\u016b\u016e\3\2\2\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d"+
		"\u0171\3\2\2\2\u016e\u016c\3\2\2\2\u016f\u0171\t\13\2\2\u0170\u0163\3"+
		"\2\2\2\u0170\u0167\3\2\2\2\u0170\u016f\3\2\2\2\u0171O\3\2\2\2\u0172\u0173"+
		"\7)\2\2\u0173\u0174\3\2\2\2\u0174\u0175\b%\6\2\u0175Q\3\2\2\2\u0176\u0177"+
		"\7}\2\2\u0177\u0178\7#\2\2\u0178\u0179\3\2\2\2\u0179\u017a\b&\5\2\u017a"+
		"S\3\2\2\2\u017b\u017d\n\f\2\2\u017c\u017b\3\2\2\2\u017d\u017e\3\2\2\2"+
		"\u017e\u017c\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u018a\3\2\2\2\u0180\u0181"+
		"\7}\2\2\u0181\u0185\n\r\2\2\u0182\u0184\n\f\2\2\u0183\u0182\3\2\2\2\u0184"+
		"\u0187\3\2\2\2\u0185\u0183\3\2\2\2\u0185\u0186\3\2\2\2\u0186\u018a\3\2"+
		"\2\2\u0187\u0185\3\2\2\2\u0188\u018a\7}\2\2\u0189\u017c\3\2\2\2\u0189"+
		"\u0180\3\2\2\2\u0189\u0188\3\2\2\2\u018aU\3\2\2\2\u018b\u018c\7$\2\2\u018c"+
		"\u018d\3\2\2\2\u018d\u018e\b(\6\2\u018eW\3\2\2\2\u018f\u0190\7}\2\2\u0190"+
		"\u0191\7#\2\2\u0191\u0192\3\2\2\2\u0192\u0193\b)\5\2\u0193Y\3\2\2\2\u0194"+
		"\u0196\n\16\2\2\u0195\u0194\3\2\2\2\u0196\u0197\3\2\2\2\u0197\u0195\3"+
		"\2\2\2\u0197\u0198\3\2\2\2\u0198\u01a3\3\2\2\2\u0199\u019a\7}\2\2\u019a"+
		"\u019e\n\17\2\2\u019b\u019d\n\16\2\2\u019c\u019b\3\2\2\2\u019d\u01a0\3"+
		"\2\2\2\u019e\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a3\3\2\2\2\u01a0"+
		"\u019e\3\2\2\2\u01a1\u01a3\7}\2\2\u01a2\u0195\3\2\2\2\u01a2\u0199\3\2"+
		"\2\2\u01a2\u01a1\3\2\2\2\u01a3[\3\2\2\2\u01a4\u01a7\5^,\2\u01a5\u01a7"+
		"\t\20\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a5\3\2\2\2\u01a7]\3\2\2\2\u01a8"+
		"\u01aa\t\21\2\2\u01a9\u01a8\3\2\2\2\u01aa_\3\2\2\2\u01ab\u01ac\7(\2\2"+
		"\u01ac\u01ad\5D\37\2\u01ad\u01ae\7=\2\2\u01ae\u01c3\3\2\2\2\u01af\u01b0"+
		"\7(\2\2\u01b0\u01b1\7%\2\2\u01b1\u01b3\3\2\2\2\u01b2\u01b4\t\22\2\2\u01b3"+
		"\u01b2\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2"+
		"\2\2\u01b6\u01b7\3\2\2\2\u01b7\u01c3\7=\2\2\u01b8\u01b9\7(\2\2\u01b9\u01ba"+
		"\7%\2\2\u01ba\u01bb\7z\2\2\u01bb\u01bd\3\2\2\2\u01bc\u01be\t\23\2\2\u01bd"+
		"\u01bc\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01bd\3\2\2\2\u01bf\u01c0\3\2"+
		"\2\2\u01c0\u01c1\3\2\2\2\u01c1\u01c3\7=\2\2\u01c2\u01ab\3\2\2\2\u01c2"+
		"\u01af\3\2\2\2\u01c2\u01b8\3\2\2\2\u01c3a\3\2\2\2/\2\3\4\5\6\7\b\tjv\u009d"+
		"\u00a0\u00a2\u00a7\u00ae\u00b2\u00c1\u00c8\u00d3\u00f4\u00f7\u00f9\u00fe"+
		"\u0105\u010d\u0110\u0128\u0131\u013c\u014b\u0155\u0165\u016c\u0170\u017e"+
		"\u0185\u0189\u0197\u019e\u01a2\u01a6\u01a9\u01b5\u01bf\u01c2\13\7\5\2"+
		"\7\3\2\7\7\2\7\6\2\6\2\2\7\4\2\7\b\2\7\t\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}