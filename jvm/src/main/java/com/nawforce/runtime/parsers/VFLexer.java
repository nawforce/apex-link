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
		COMMENT=1, DOCTYPE=2, DECL_START=3, OPEN=4, EL_START=5, CHARDATA_REF=6, 
		WS_NL=7, TEXT=8, DECL_END=9, CLOSE=10, SLASH_CLOSE=11, SLASH=12, EQUALS=13, 
		STRING=14, ATTRS_START=15, ATTRD_START=16, WS=17, Name=18, EL_END=19, 
		EL_BODY=20, ATTRS_END=21, ATTRS_EL_START=22, ATTRS_REF=23, ATTRS_TEXT=24, 
		ATTRD_END=25, ATTRD_EL_START=26, ATTRD_REF=27, ATTRD_TEXT=28;
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
			"COMMENT", "DOCTYPE", "DECL_START", "OPEN", "EL_START", "CHARDATA_REF", 
			"WS_NL", "TEXT", "DECL_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", 
			"STRING", "ATTRS_START", "ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", 
			"ATTRS_END", "ATTRS_EL_START", "ATTRS_REF", "ATTRS_TEXT", "ATTRD_END", 
			"ATTRD_EL_START", "ATTRD_REF", "ATTRD_TEXT", "NameChar", "NameStartChar", 
			"Ref"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'<'", null, null, null, null, "'?>'", "'>'", 
			"'/>'", "'/'", "'='", null, null, null, null, null, "'}'", null, "'''", 
			null, null, null, "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMMENT", "DOCTYPE", "DECL_START", "OPEN", "EL_START", "CHARDATA_REF", 
			"WS_NL", "TEXT", "DECL_END", "CLOSE", "SLASH_CLOSE", "SLASH", "EQUALS", 
			"STRING", "ATTRS_START", "ATTRD_START", "WS", "Name", "EL_END", "EL_BODY", 
			"ATTRS_END", "ATTRS_EL_START", "ATTRS_REF", "ATTRS_TEXT", "ATTRD_END", 
			"ATTRD_EL_START", "ATTRD_REF", "ATTRD_TEXT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u012e\b\1\b\1"+
		"\b\1\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27"+
		"\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36"+
		"\4\37\t\37\4 \t \3\2\3\2\3\2\3\2\3\2\3\2\7\2L\n\2\f\2\16\2O\13\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3`\n\3\f\3\16"+
		"\3c\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4l\n\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\5\b}\n\b\3\b\6\b\u0080\n\b\r\b\16"+
		"\b\u0081\3\t\6\t\u0085\n\t\r\t\16\t\u0086\3\t\3\t\3\t\7\t\u008c\n\t\f"+
		"\t\16\t\u008f\13\t\3\t\5\t\u0092\n\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\7\17\u00a8\n\17"+
		"\f\17\16\17\u00ab\13\17\3\17\3\17\3\20\3\20\7\20\u00b1\n\20\f\20\16\20"+
		"\u00b4\13\20\3\20\3\20\3\20\3\20\3\21\3\21\7\21\u00bc\n\21\f\21\16\21"+
		"\u00bf\13\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\7\23\u00cb"+
		"\n\23\f\23\16\23\u00ce\13\23\3\24\3\24\3\24\3\24\3\25\6\25\u00d5\n\25"+
		"\r\25\16\25\u00d6\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3"+
		"\30\3\31\6\31\u00e5\n\31\r\31\16\31\u00e6\3\31\3\31\3\31\7\31\u00ec\n"+
		"\31\f\31\16\31\u00ef\13\31\3\31\5\31\u00f2\n\31\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\6\35\u0100\n\35\r\35\16\35\u0101"+
		"\3\35\3\35\3\35\7\35\u0107\n\35\f\35\16\35\u010a\13\35\3\35\5\35\u010d"+
		"\n\35\3\36\3\36\5\36\u0111\n\36\3\37\5\37\u0114\n\37\3 \3 \3 \3 \3 \3"+
		" \3 \3 \6 \u011e\n \r \16 \u011f\3 \3 \3 \3 \3 \3 \6 \u0128\n \r \16 "+
		"\u0129\3 \5 \u012d\n \4Ma\2!\7\3\t\4\13\5\r\6\17\7\21\b\23\t\25\n\27\13"+
		"\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\63\31\65"+
		"\32\67\339\34;\35=\36?\2A\2C\2\7\2\3\4\5\6\20\4\2\13\13\"\"\5\2((>>}}"+
		"\6\2##((>>}}\4\2$$>>\5\2\13\f\17\17\"\"\3\2\177\177\4\2()}}\5\2##()}}"+
		"\5\2$$((}}\5\2#$((}}\b\2/\60\62;aa\u00b9\u00b9\u0302\u0371\u2041\u2042"+
		"\n\2<<C\\c|\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff"+
		"\3\2\62;\5\2\62;CHch\2\u0142\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r"+
		"\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2"+
		"\2\2\3\31\3\2\2\2\3\33\3\2\2\2\3\35\3\2\2\2\3\37\3\2\2\2\3!\3\2\2\2\3"+
		"#\3\2\2\2\3%\3\2\2\2\3\'\3\2\2\2\3)\3\2\2\2\4+\3\2\2\2\4-\3\2\2\2\5/\3"+
		"\2\2\2\5\61\3\2\2\2\5\63\3\2\2\2\5\65\3\2\2\2\6\67\3\2\2\2\69\3\2\2\2"+
		"\6;\3\2\2\2\6=\3\2\2\2\7E\3\2\2\2\tT\3\2\2\2\13f\3\2\2\2\ro\3\2\2\2\17"+
		"s\3\2\2\2\21x\3\2\2\2\23\177\3\2\2\2\25\u0091\3\2\2\2\27\u0093\3\2\2\2"+
		"\31\u0098\3\2\2\2\33\u009c\3\2\2\2\35\u00a1\3\2\2\2\37\u00a3\3\2\2\2!"+
		"\u00a5\3\2\2\2#\u00ae\3\2\2\2%\u00b9\3\2\2\2\'\u00c4\3\2\2\2)\u00c8\3"+
		"\2\2\2+\u00cf\3\2\2\2-\u00d4\3\2\2\2/\u00d8\3\2\2\2\61\u00dc\3\2\2\2\63"+
		"\u00e1\3\2\2\2\65\u00f1\3\2\2\2\67\u00f3\3\2\2\29\u00f7\3\2\2\2;\u00fc"+
		"\3\2\2\2=\u010c\3\2\2\2?\u0110\3\2\2\2A\u0113\3\2\2\2C\u012c\3\2\2\2E"+
		"F\7>\2\2FG\7#\2\2GH\7/\2\2HI\7/\2\2IM\3\2\2\2JL\13\2\2\2KJ\3\2\2\2LO\3"+
		"\2\2\2MN\3\2\2\2MK\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7/\2\2QR\7/\2\2RS\7@"+
		"\2\2S\b\3\2\2\2TU\7>\2\2UV\7#\2\2VW\7F\2\2WX\7Q\2\2XY\7E\2\2YZ\7V\2\2"+
		"Z[\7[\2\2[\\\7R\2\2\\]\7G\2\2]a\3\2\2\2^`\13\2\2\2_^\3\2\2\2`c\3\2\2\2"+
		"ab\3\2\2\2a_\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\7@\2\2e\n\3\2\2\2fg\7>\2\2"+
		"gh\7A\2\2hi\3\2\2\2ik\5)\23\2jl\5\'\22\2kj\3\2\2\2kl\3\2\2\2lm\3\2\2\2"+
		"mn\b\4\2\2n\f\3\2\2\2op\7>\2\2pq\3\2\2\2qr\b\5\2\2r\16\3\2\2\2st\7}\2"+
		"\2tu\7#\2\2uv\3\2\2\2vw\b\6\3\2w\20\3\2\2\2xy\5C \2y\22\3\2\2\2z\u0080"+
		"\t\2\2\2{}\7\17\2\2|{\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\u0080\7\f\2\2\177z"+
		"\3\2\2\2\177|\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\24\3\2\2\2\u0083\u0085\n\3\2\2\u0084\u0083\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0092\3\2"+
		"\2\2\u0088\u0089\7}\2\2\u0089\u008d\n\4\2\2\u008a\u008c\n\3\2\2\u008b"+
		"\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2"+
		"\2\2\u008e\u0092\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0092\7}\2\2\u0091"+
		"\u0084\3\2\2\2\u0091\u0088\3\2\2\2\u0091\u0090\3\2\2\2\u0092\26\3\2\2"+
		"\2\u0093\u0094\7A\2\2\u0094\u0095\7@\2\2\u0095\u0096\3\2\2\2\u0096\u0097"+
		"\b\n\4\2\u0097\30\3\2\2\2\u0098\u0099\7@\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\u009b\b\13\4\2\u009b\32\3\2\2\2\u009c\u009d\7\61\2\2\u009d\u009e\7@\2"+
		"\2\u009e\u009f\3\2\2\2\u009f\u00a0\b\f\4\2\u00a0\34\3\2\2\2\u00a1\u00a2"+
		"\7\61\2\2\u00a2\36\3\2\2\2\u00a3\u00a4\7?\2\2\u00a4 \3\2\2\2\u00a5\u00a9"+
		"\7$\2\2\u00a6\u00a8\n\5\2\2\u00a7\u00a6\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ac\u00ad\7$\2\2\u00ad\"\3\2\2\2\u00ae\u00b2\7?\2\2\u00af\u00b1"+
		"\t\2\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\7)"+
		"\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\b\20\5\2\u00b8$\3\2\2\2\u00b9\u00bd"+
		"\7?\2\2\u00ba\u00bc\t\2\2\2\u00bb\u00ba\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd"+
		"\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00c0\3\2\2\2\u00bf\u00bd\3\2"+
		"\2\2\u00c0\u00c1\7$\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\b\21\6\2\u00c3"+
		"&\3\2\2\2\u00c4\u00c5\t\6\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\b\22\7\2"+
		"\u00c7(\3\2\2\2\u00c8\u00cc\5A\37\2\u00c9\u00cb\5?\36\2\u00ca\u00c9\3"+
		"\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"*\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d0\7\177\2\2\u00d0\u00d1\3\2\2"+
		"\2\u00d1\u00d2\b\24\4\2\u00d2,\3\2\2\2\u00d3\u00d5\n\7\2\2\u00d4\u00d3"+
		"\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		".\3\2\2\2\u00d8\u00d9\7)\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\b\26\4\2"+
		"\u00db\60\3\2\2\2\u00dc\u00dd\7}\2\2\u00dd\u00de\7#\2\2\u00de\u00df\3"+
		"\2\2\2\u00df\u00e0\b\27\3\2\u00e0\62\3\2\2\2\u00e1\u00e2\5C \2\u00e2\64"+
		"\3\2\2\2\u00e3\u00e5\n\b\2\2\u00e4\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00f2\3\2\2\2\u00e8\u00e9\7}"+
		"\2\2\u00e9\u00ed\n\t\2\2\u00ea\u00ec\n\b\2\2\u00eb\u00ea\3\2\2\2\u00ec"+
		"\u00ef\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00f2\3\2"+
		"\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f2\7}\2\2\u00f1\u00e4\3\2\2\2\u00f1"+
		"\u00e8\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2\66\3\2\2\2\u00f3\u00f4\7$\2\2"+
		"\u00f4\u00f5\3\2\2\2\u00f5\u00f6\b\32\4\2\u00f68\3\2\2\2\u00f7\u00f8\7"+
		"}\2\2\u00f8\u00f9\7#\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\b\33\3\2\u00fb"+
		":\3\2\2\2\u00fc\u00fd\5C \2\u00fd<\3\2\2\2\u00fe\u0100\n\n\2\2\u00ff\u00fe"+
		"\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3\2\2\2\u0101\u0102\3\2\2\2\u0102"+
		"\u010d\3\2\2\2\u0103\u0104\7}\2\2\u0104\u0108\n\13\2\2\u0105\u0107\n\n"+
		"\2\2\u0106\u0105\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0108"+
		"\u0109\3\2\2\2\u0109\u010d\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010d\7}"+
		"\2\2\u010c\u00ff\3\2\2\2\u010c\u0103\3\2\2\2\u010c\u010b\3\2\2\2\u010d"+
		">\3\2\2\2\u010e\u0111\5A\37\2\u010f\u0111\t\f\2\2\u0110\u010e\3\2\2\2"+
		"\u0110\u010f\3\2\2\2\u0111@\3\2\2\2\u0112\u0114\t\r\2\2\u0113\u0112\3"+
		"\2\2\2\u0114B\3\2\2\2\u0115\u0116\7(\2\2\u0116\u0117\5)\23\2\u0117\u0118"+
		"\7=\2\2\u0118\u012d\3\2\2\2\u0119\u011a\7(\2\2\u011a\u011b\7%\2\2\u011b"+
		"\u011d\3\2\2\2\u011c\u011e\t\16\2\2\u011d\u011c\3\2\2\2\u011e\u011f\3"+
		"\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u012d\7=\2\2\u0122\u0123\7(\2\2\u0123\u0124\7%\2\2\u0124\u0125\7z\2\2"+
		"\u0125\u0127\3\2\2\2\u0126\u0128\t\17\2\2\u0127\u0126\3\2\2\2\u0128\u0129"+
		"\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012d\7=\2\2\u012c\u0115\3\2\2\2\u012c\u0119\3\2\2\2\u012c\u0122\3\2"+
		"\2\2\u012dD\3\2\2\2 \2\3\4\5\6Mak|\177\u0081\u0086\u008d\u0091\u00a9\u00b2"+
		"\u00bd\u00cc\u00d6\u00e6\u00ed\u00f1\u0101\u0108\u010c\u0110\u0113\u011f"+
		"\u0129\u012c\b\7\3\2\7\4\2\6\2\2\7\5\2\7\6\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}