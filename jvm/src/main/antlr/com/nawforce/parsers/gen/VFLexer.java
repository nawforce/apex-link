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
		COMMENT=1, DOCTYPE=2, DECL_START=3, OPEN=4, EL_START=5, EL_END=6, EntityRef=7, 
		CharRef=8, WS_NL=9, TEXT=10, DECL_END=11, CLOSE=12, SLASH_CLOSE=13, SLASH=14, 
		EQUALS=15, STRING=16, WS=17, Name=18;
	public static final int
		WHITESPACE_CHANNEL=2, COMMENT_CHANNEL=3;
	public static final int
		INSIDE=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN", "WHITESPACE_CHANNEL", "COMMENT_CHANNEL"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "INSIDE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMMENT", "DOCTYPE", "DECL_START", "OPEN", "EL_START", "EL_END", "EntityRef", 
			"CharRef", "WS_NL", "TEXT", "DECL_END", "CLOSE", "SLASH_CLOSE", "SLASH", 
			"EQUALS", "STRING", "WS", "Name", "NameChar", "NameStartChar"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'<'", "'{!'", "'}'", null, null, null, null, 
			"'?>'", "'>'", "'/>'", "'/'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMMENT", "DOCTYPE", "DECL_START", "OPEN", "EL_START", "EL_END", 
			"EntityRef", "CharRef", "WS_NL", "TEXT", "DECL_END", "CLOSE", "SLASH_CLOSE", 
			"SLASH", "EQUALS", "STRING", "WS", "Name"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24\u00bc\b\1\b\1"+
		"\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t"+
		"\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4"+
		"\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\2\3\2\7\2\63"+
		"\n\2\f\2\16\2\66\13\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\7\3G\n\3\f\3\16\3J\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4S"+
		"\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\t\6\th\n\t\r\t\16\ti\3\t\3\t\3\t\3\t\3\t\3\t\6\tr\n\t\r\t\16"+
		"\ts\3\t\5\tw\n\t\3\n\3\n\5\n{\n\n\3\n\6\n~\n\n\r\n\16\n\177\3\13\6\13"+
		"\u0083\n\13\r\13\16\13\u0084\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\7\21\u009b\n\21\f\21"+
		"\16\21\u009e\13\21\3\21\3\21\3\21\7\21\u00a3\n\21\f\21\16\21\u00a6\13"+
		"\21\3\21\5\21\u00a9\n\21\3\22\3\22\3\22\3\22\3\23\3\23\7\23\u00b1\n\23"+
		"\f\23\16\23\u00b4\13\23\3\24\3\24\5\24\u00b8\n\24\3\25\5\25\u00bb\n\25"+
		"\4\64H\2\26\4\3\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f\30\r\32\16\34"+
		"\17\36\20 \21\"\22$\23&\24(\2*\2\4\2\3\13\3\2\62;\5\2\62;CHch\4\2\13\13"+
		"\"\"\4\2((>>\4\2$$>>\4\2))>>\5\2\13\f\17\17\"\"\b\2/\60\62;aa\u00b9\u00b9"+
		"\u0302\u0371\u2041\u2042\n\2<<C\\c|\u2072\u2191\u2c02\u2ff1\u3003\ud801"+
		"\uf902\ufdd1\ufdf2\uffff\2\u00c7\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2"+
		"\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2"+
		"\2\2\2\26\3\2\2\2\3\30\3\2\2\2\3\32\3\2\2\2\3\34\3\2\2\2\3\36\3\2\2\2"+
		"\3 \3\2\2\2\3\"\3\2\2\2\3$\3\2\2\2\3&\3\2\2\2\4,\3\2\2\2\6;\3\2\2\2\b"+
		"M\3\2\2\2\nV\3\2\2\2\fZ\3\2\2\2\16]\3\2\2\2\20_\3\2\2\2\22v\3\2\2\2\24"+
		"}\3\2\2\2\26\u0082\3\2\2\2\30\u0086\3\2\2\2\32\u008b\3\2\2\2\34\u008f"+
		"\3\2\2\2\36\u0094\3\2\2\2 \u0096\3\2\2\2\"\u00a8\3\2\2\2$\u00aa\3\2\2"+
		"\2&\u00ae\3\2\2\2(\u00b7\3\2\2\2*\u00ba\3\2\2\2,-\7>\2\2-.\7#\2\2./\7"+
		"/\2\2/\60\7/\2\2\60\64\3\2\2\2\61\63\13\2\2\2\62\61\3\2\2\2\63\66\3\2"+
		"\2\2\64\65\3\2\2\2\64\62\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\7/\2"+
		"\289\7/\2\29:\7@\2\2:\5\3\2\2\2;<\7>\2\2<=\7#\2\2=>\7F\2\2>?\7Q\2\2?@"+
		"\7E\2\2@A\7V\2\2AB\7[\2\2BC\7R\2\2CD\7G\2\2DH\3\2\2\2EG\13\2\2\2FE\3\2"+
		"\2\2GJ\3\2\2\2HI\3\2\2\2HF\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\7@\2\2L\7\3\2"+
		"\2\2MN\7>\2\2NO\7A\2\2OP\3\2\2\2PR\5&\23\2QS\5$\22\2RQ\3\2\2\2RS\3\2\2"+
		"\2ST\3\2\2\2TU\b\4\2\2U\t\3\2\2\2VW\7>\2\2WX\3\2\2\2XY\b\5\2\2Y\13\3\2"+
		"\2\2Z[\7}\2\2[\\\7#\2\2\\\r\3\2\2\2]^\7\177\2\2^\17\3\2\2\2_`\7(\2\2`"+
		"a\5&\23\2ab\7=\2\2b\21\3\2\2\2cd\7(\2\2de\7%\2\2eg\3\2\2\2fh\t\2\2\2g"+
		"f\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2jk\3\2\2\2kw\7=\2\2lm\7(\2\2mn"+
		"\7%\2\2no\7z\2\2oq\3\2\2\2pr\t\3\2\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3"+
		"\2\2\2tu\3\2\2\2uw\7=\2\2vc\3\2\2\2vl\3\2\2\2w\23\3\2\2\2x~\t\4\2\2y{"+
		"\7\17\2\2zy\3\2\2\2z{\3\2\2\2{|\3\2\2\2|~\7\f\2\2}x\3\2\2\2}z\3\2\2\2"+
		"~\177\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\25\3\2\2\2\u0081\u0083"+
		"\n\5\2\2\u0082\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0082\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0085\27\3\2\2\2\u0086\u0087\7A\2\2\u0087\u0088\7@\2\2"+
		"\u0088\u0089\3\2\2\2\u0089\u008a\b\f\3\2\u008a\31\3\2\2\2\u008b\u008c"+
		"\7@\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\r\3\2\u008e\33\3\2\2\2\u008f"+
		"\u0090\7\61\2\2\u0090\u0091\7@\2\2\u0091\u0092\3\2\2\2\u0092\u0093\b\16"+
		"\3\2\u0093\35\3\2\2\2\u0094\u0095\7\61\2\2\u0095\37\3\2\2\2\u0096\u0097"+
		"\7?\2\2\u0097!\3\2\2\2\u0098\u009c\7$\2\2\u0099\u009b\n\6\2\2\u009a\u0099"+
		"\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d"+
		"\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a9\7$\2\2\u00a0\u00a4\7)\2"+
		"\2\u00a1\u00a3\n\7\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2"+
		"\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7"+
		"\u00a9\7)\2\2\u00a8\u0098\3\2\2\2\u00a8\u00a0\3\2\2\2\u00a9#\3\2\2\2\u00aa"+
		"\u00ab\t\b\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\b\22\4\2\u00ad%\3\2\2\2"+
		"\u00ae\u00b2\5*\25\2\u00af\u00b1\5(\24\2\u00b0\u00af\3\2\2\2\u00b1\u00b4"+
		"\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\'\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b5\u00b8\5*\25\2\u00b6\u00b8\t\t\2\2\u00b7\u00b5\3\2"+
		"\2\2\u00b7\u00b6\3\2\2\2\u00b8)\3\2\2\2\u00b9\u00bb\t\n\2\2\u00ba\u00b9"+
		"\3\2\2\2\u00bb+\3\2\2\2\24\2\3\64HRisvz}\177\u0084\u009c\u00a4\u00a8\u00b2"+
		"\u00b7\u00ba\5\7\3\2\6\2\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}