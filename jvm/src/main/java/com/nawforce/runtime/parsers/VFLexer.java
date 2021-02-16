// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/VFLexer.g4 by ANTLR 4.8
package com.nawforce.runtime.parsers;
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
		COMMENT=1, PI_START=2, OPEN=3, EL_START=4, CHARDATA_REF=5, WS_NL=6, TEXT=7, 
		PI_END=8, CLOSE=9, SLASH_CLOSE=10, SLASH=11, EQUALS=12, STRING=13, ATTRS_START=14, 
		ATTRD_START=15, WS=16, Name=17, EL_END=18, EL_BODY=19, ATTRS_END=20, ATTRS_EL_START=21, 
		ATTRS_REF=22, ATTRS_TEXT=23, ATTRD_END=24, ATTRD_EL_START=25, ATTRD_REF=26, 
		ATTRD_TEXT=27;
	public static final int
		INTAG=1, EL=2, ATTRS=3, ATTRD=4;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "INTAG", "EL", "ATTRS", "ATTRD"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMMENT", "PI_START", "OPEN", "EL_START", "CHARDATA_REF", "WS_NL", "TEXT", 
			"PI_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", "STRING", "ATTRS_START", 
			"ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", "ATTRS_END", "ATTRS_EL_START", 
			"ATTRS_REF", "ATTRS_TEXT", "ATTRD_END", "ATTRD_EL_START", "ATTRD_REF", 
			"ATTRD_TEXT", "NameChar", "NameStartChar", "Ref"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'<'", null, null, null, null, "'?>'", "'>'", "'/>'", 
			"'/'", "'='", null, null, null, null, null, "'}'", null, "'''", null, 
			null, null, "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMMENT", "PI_START", "OPEN", "EL_START", "CHARDATA_REF", "WS_NL", 
			"TEXT", "PI_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", "STRING", 
			"ATTRS_START", "ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", "ATTRS_END", 
			"ATTRS_EL_START", "ATTRS_REF", "ATTRS_TEXT", "ATTRD_END", "ATTRD_EL_START", 
			"ATTRD_REF", "ATTRD_TEXT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u011a\b\1\b\1"+
		"\b\1\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27"+
		"\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36"+
		"\4\37\t\37\3\2\3\2\3\2\3\2\3\2\3\2\7\2J\n\2\f\2\16\2M\13\2\3\2\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\5\3X\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\5\7i\n\7\3\7\6\7l\n\7\r\7\16\7m\3\b\6\bq\n\b"+
		"\r\b\16\br\3\b\3\b\3\b\7\bx\n\b\f\b\16\b{\13\b\3\b\5\b~\n\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3"+
		"\16\3\16\7\16\u0094\n\16\f\16\16\16\u0097\13\16\3\16\3\16\3\17\3\17\7"+
		"\17\u009d\n\17\f\17\16\17\u00a0\13\17\3\17\3\17\3\17\3\17\3\20\3\20\7"+
		"\20\u00a8\n\20\f\20\16\20\u00ab\13\20\3\20\3\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\21\3\22\3\22\7\22\u00b7\n\22\f\22\16\22\u00ba\13\22\3\23\3\23\3"+
		"\23\3\23\3\24\6\24\u00c1\n\24\r\24\16\24\u00c2\3\25\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\26\3\27\3\27\3\30\6\30\u00d1\n\30\r\30\16\30\u00d2\3"+
		"\30\3\30\3\30\7\30\u00d8\n\30\f\30\16\30\u00db\13\30\3\30\5\30\u00de\n"+
		"\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\34\6\34\u00ec"+
		"\n\34\r\34\16\34\u00ed\3\34\3\34\3\34\7\34\u00f3\n\34\f\34\16\34\u00f6"+
		"\13\34\3\34\5\34\u00f9\n\34\3\35\3\35\5\35\u00fd\n\35\3\36\5\36\u0100"+
		"\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\6\37\u010a\n\37\r\37\16"+
		"\37\u010b\3\37\3\37\3\37\3\37\3\37\3\37\6\37\u0114\n\37\r\37\16\37\u0115"+
		"\3\37\5\37\u0119\n\37\3K\2 \7\3\t\4\13\5\r\6\17\7\21\b\23\t\25\n\27\13"+
		"\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\63\31\65"+
		"\32\67\339\34;\35=\2?\2A\2\7\2\3\4\5\6\20\4\2\13\13\"\"\5\2((>>}}\6\2"+
		"##((>>}}\4\2$$>>\5\2\13\f\17\17\"\"\3\2\177\177\4\2()}}\5\2##()}}\5\2"+
		"$$((}}\5\2#$((}}\b\2/\60\62;aa\u00b9\u00b9\u0302\u0371\u2041\u2042\n\2"+
		"<<C\\c|\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\3"+
		"\2\62;\5\2\62;CHch\2\u012d\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3"+
		"\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\3\25\3\2\2\2\3\27\3\2\2"+
		"\2\3\31\3\2\2\2\3\33\3\2\2\2\3\35\3\2\2\2\3\37\3\2\2\2\3!\3\2\2\2\3#\3"+
		"\2\2\2\3%\3\2\2\2\3\'\3\2\2\2\4)\3\2\2\2\4+\3\2\2\2\5-\3\2\2\2\5/\3\2"+
		"\2\2\5\61\3\2\2\2\5\63\3\2\2\2\6\65\3\2\2\2\6\67\3\2\2\2\69\3\2\2\2\6"+
		";\3\2\2\2\7C\3\2\2\2\tR\3\2\2\2\13[\3\2\2\2\r_\3\2\2\2\17d\3\2\2\2\21"+
		"k\3\2\2\2\23}\3\2\2\2\25\177\3\2\2\2\27\u0084\3\2\2\2\31\u0088\3\2\2\2"+
		"\33\u008d\3\2\2\2\35\u008f\3\2\2\2\37\u0091\3\2\2\2!\u009a\3\2\2\2#\u00a5"+
		"\3\2\2\2%\u00b0\3\2\2\2\'\u00b4\3\2\2\2)\u00bb\3\2\2\2+\u00c0\3\2\2\2"+
		"-\u00c4\3\2\2\2/\u00c8\3\2\2\2\61\u00cd\3\2\2\2\63\u00dd\3\2\2\2\65\u00df"+
		"\3\2\2\2\67\u00e3\3\2\2\29\u00e8\3\2\2\2;\u00f8\3\2\2\2=\u00fc\3\2\2\2"+
		"?\u00ff\3\2\2\2A\u0118\3\2\2\2CD\7>\2\2DE\7#\2\2EF\7/\2\2FG\7/\2\2GK\3"+
		"\2\2\2HJ\13\2\2\2IH\3\2\2\2JM\3\2\2\2KL\3\2\2\2KI\3\2\2\2LN\3\2\2\2MK"+
		"\3\2\2\2NO\7/\2\2OP\7/\2\2PQ\7@\2\2Q\b\3\2\2\2RS\7>\2\2ST\7A\2\2TU\3\2"+
		"\2\2UW\5\'\22\2VX\5%\21\2WV\3\2\2\2WX\3\2\2\2XY\3\2\2\2YZ\b\3\2\2Z\n\3"+
		"\2\2\2[\\\7>\2\2\\]\3\2\2\2]^\b\4\2\2^\f\3\2\2\2_`\7}\2\2`a\7#\2\2ab\3"+
		"\2\2\2bc\b\5\3\2c\16\3\2\2\2de\5A\37\2e\20\3\2\2\2fl\t\2\2\2gi\7\17\2"+
		"\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2jl\7\f\2\2kf\3\2\2\2kh\3\2\2\2lm\3\2\2"+
		"\2mk\3\2\2\2mn\3\2\2\2n\22\3\2\2\2oq\n\3\2\2po\3\2\2\2qr\3\2\2\2rp\3\2"+
		"\2\2rs\3\2\2\2s~\3\2\2\2tu\7}\2\2uy\n\4\2\2vx\n\3\2\2wv\3\2\2\2x{\3\2"+
		"\2\2yw\3\2\2\2yz\3\2\2\2z~\3\2\2\2{y\3\2\2\2|~\7}\2\2}p\3\2\2\2}t\3\2"+
		"\2\2}|\3\2\2\2~\24\3\2\2\2\177\u0080\7A\2\2\u0080\u0081\7@\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\u0083\b\t\4\2\u0083\26\3\2\2\2\u0084\u0085\7@\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0087\b\n\4\2\u0087\30\3\2\2\2\u0088\u0089\7\61\2"+
		"\2\u0089\u008a\7@\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\13\4\2\u008c\32"+
		"\3\2\2\2\u008d\u008e\7\61\2\2\u008e\34\3\2\2\2\u008f\u0090\7?\2\2\u0090"+
		"\36\3\2\2\2\u0091\u0095\7$\2\2\u0092\u0094\n\5\2\2\u0093\u0092\3\2\2\2"+
		"\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098"+
		"\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u0099\7$\2\2\u0099 \3\2\2\2\u009a\u009e"+
		"\7?\2\2\u009b\u009d\t\2\2\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e"+
		"\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2"+
		"\2\2\u00a1\u00a2\7)\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\b\17\5\2\u00a4"+
		"\"\3\2\2\2\u00a5\u00a9\7?\2\2\u00a6\u00a8\t\2\2\2\u00a7\u00a6\3\2\2\2"+
		"\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac"+
		"\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00ad\7$\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\u00af\b\20\6\2\u00af$\3\2\2\2\u00b0\u00b1\t\6\2\2\u00b1\u00b2\3\2\2\2"+
		"\u00b2\u00b3\b\21\7\2\u00b3&\3\2\2\2\u00b4\u00b8\5?\36\2\u00b5\u00b7\5"+
		"=\35\2\u00b6\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9(\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\7\177\2"+
		"\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\b\23\4\2\u00be*\3\2\2\2\u00bf\u00c1"+
		"\n\7\2\2\u00c0\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2"+
		"\u00c3\3\2\2\2\u00c3,\3\2\2\2\u00c4\u00c5\7)\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c7\b\25\4\2\u00c7.\3\2\2\2\u00c8\u00c9\7}\2\2\u00c9\u00ca\7#\2\2\u00ca"+
		"\u00cb\3\2\2\2\u00cb\u00cc\b\26\3\2\u00cc\60\3\2\2\2\u00cd\u00ce\5A\37"+
		"\2\u00ce\62\3\2\2\2\u00cf\u00d1\n\b\2\2\u00d0\u00cf\3\2\2\2\u00d1\u00d2"+
		"\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00de\3\2\2\2\u00d4"+
		"\u00d5\7}\2\2\u00d5\u00d9\n\t\2\2\u00d6\u00d8\n\b\2\2\u00d7\u00d6\3\2"+
		"\2\2\u00d8\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00de\3\2\2\2\u00db\u00d9\3\2\2\2\u00dc\u00de\7}\2\2\u00dd\u00d0\3\2"+
		"\2\2\u00dd\u00d4\3\2\2\2\u00dd\u00dc\3\2\2\2\u00de\64\3\2\2\2\u00df\u00e0"+
		"\7$\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\b\31\4\2\u00e2\66\3\2\2\2\u00e3"+
		"\u00e4\7}\2\2\u00e4\u00e5\7#\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\b\32"+
		"\3\2\u00e78\3\2\2\2\u00e8\u00e9\5A\37\2\u00e9:\3\2\2\2\u00ea\u00ec\n\n"+
		"\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed"+
		"\u00ee\3\2\2\2\u00ee\u00f9\3\2\2\2\u00ef\u00f0\7}\2\2\u00f0\u00f4\n\13"+
		"\2\2\u00f1\u00f3\n\n\2\2\u00f2\u00f1\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4"+
		"\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f9\3\2\2\2\u00f6\u00f4\3\2"+
		"\2\2\u00f7\u00f9\7}\2\2\u00f8\u00eb\3\2\2\2\u00f8\u00ef\3\2\2\2\u00f8"+
		"\u00f7\3\2\2\2\u00f9<\3\2\2\2\u00fa\u00fd\5?\36\2\u00fb\u00fd\t\f\2\2"+
		"\u00fc\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd>\3\2\2\2\u00fe\u0100\t"+
		"\r\2\2\u00ff\u00fe\3\2\2\2\u0100@\3\2\2\2\u0101\u0102\7(\2\2\u0102\u0103"+
		"\5\'\22\2\u0103\u0104\7=\2\2\u0104\u0119\3\2\2\2\u0105\u0106\7(\2\2\u0106"+
		"\u0107\7%\2\2\u0107\u0109\3\2\2\2\u0108\u010a\t\16\2\2\u0109\u0108\3\2"+
		"\2\2\u010a\u010b\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d\u0119\7=\2\2\u010e\u010f\7(\2\2\u010f\u0110\7%\2"+
		"\2\u0110\u0111\7z\2\2\u0111\u0113\3\2\2\2\u0112\u0114\t\17\2\2\u0113\u0112"+
		"\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116"+
		"\u0117\3\2\2\2\u0117\u0119\7=\2\2\u0118\u0101\3\2\2\2\u0118\u0105\3\2"+
		"\2\2\u0118\u010e\3\2\2\2\u0119B\3\2\2\2\37\2\3\4\5\6KWhkmry}\u0095\u009e"+
		"\u00a9\u00b8\u00c2\u00d2\u00d9\u00dd\u00ed\u00f4\u00f8\u00fc\u00ff\u010b"+
		"\u0115\u0118\b\7\3\2\7\4\2\6\2\2\7\5\2\7\6\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}