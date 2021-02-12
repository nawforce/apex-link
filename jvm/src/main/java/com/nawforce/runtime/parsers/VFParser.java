// Generated from /Users/kevin/Projects/pkgforce/jvm/src/main/antlr/com/nawforce/parsers/VFParser.g4 by ANTLR 4.8
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
public class VFParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, DOCTYPE=2, DECL_START=3, OPEN=4, EL_START=5, EL_END=6, EntityRef=7, 
		CharRef=8, WS_NL=9, TEXT=10, DECL_END=11, CLOSE=12, SLASH_CLOSE=13, SLASH=14, 
		EQUALS=15, STRING=16, WS=17, Name=18;
	public static final int
		RULE_vfUnit = 0, RULE_prolog = 1, RULE_declaration = 2, RULE_attribute = 3, 
		RULE_content = 4, RULE_element = 5, RULE_elExpression = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"vfUnit", "prolog", "declaration", "attribute", "content", "element", 
			"elExpression"
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

	@Override
	public String getGrammarFileName() { return "VFParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    public void clearCache() {
	        _interp.clearDFA();
	    }

	public VFParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class VfUnitContext extends ParserRuleContext {
		public ContentContext content() {
			return getRuleContext(ContentContext.class,0);
		}
		public TerminalNode EOF() { return getToken(VFParser.EOF, 0); }
		public PrologContext prolog() {
			return getRuleContext(PrologContext.class,0);
		}
		public VfUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vfUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterVfUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitVfUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitVfUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VfUnitContext vfUnit() throws RecognitionException {
		VfUnitContext _localctx = new VfUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_vfUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(14);
				prolog();
				}
				break;
			}
			setState(17);
			content();
			setState(18);
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

	public static class PrologContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TerminalNode DOCTYPE() { return getToken(VFParser.DOCTYPE, 0); }
		public List<TerminalNode> COMMENT() { return getTokens(VFParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(VFParser.COMMENT, i);
		}
		public List<TerminalNode> WS_NL() { return getTokens(VFParser.WS_NL); }
		public TerminalNode WS_NL(int i) {
			return getToken(VFParser.WS_NL, i);
		}
		public PrologContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prolog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterProlog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitProlog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitProlog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrologContext prolog() throws RecognitionException {
		PrologContext _localctx = new PrologContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prolog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT || _la==WS_NL) {
				{
				{
				setState(20);
				_la = _input.LA(1);
				if ( !(_la==COMMENT || _la==WS_NL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECL_START:
				{
				setState(26);
				declaration();
				}
				break;
			case DOCTYPE:
				{
				setState(27);
				match(DOCTYPE);
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

	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode DECL_START() { return getToken(VFParser.DECL_START, 0); }
		public TerminalNode DECL_END() { return getToken(VFParser.DECL_END, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(DECL_START);
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(31);
				attribute();
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			match(DECL_END);
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(VFParser.Name, 0); }
		public TerminalNode EQUALS() { return getToken(VFParser.EQUALS, 0); }
		public TerminalNode STRING() { return getToken(VFParser.STRING, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(Name);
			setState(40);
			match(EQUALS);
			setState(41);
			match(STRING);
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

	public static class ContentContext extends ParserRuleContext {
		public List<ElExpressionContext> elExpression() {
			return getRuleContexts(ElExpressionContext.class);
		}
		public ElExpressionContext elExpression(int i) {
			return getRuleContext(ElExpressionContext.class,i);
		}
		public List<TerminalNode> TEXT() { return getTokens(VFParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(VFParser.TEXT, i);
		}
		public List<TerminalNode> EntityRef() { return getTokens(VFParser.EntityRef); }
		public TerminalNode EntityRef(int i) {
			return getToken(VFParser.EntityRef, i);
		}
		public List<TerminalNode> CharRef() { return getTokens(VFParser.CharRef); }
		public TerminalNode CharRef(int i) {
			return getToken(VFParser.CharRef, i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(VFParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(VFParser.COMMENT, i);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_content);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(49);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case EL_START:
						{
						setState(43);
						elExpression();
						}
						break;
					case TEXT:
						{
						setState(44);
						match(TEXT);
						}
						break;
					case EntityRef:
						{
						setState(45);
						match(EntityRef);
						}
						break;
					case CharRef:
						{
						setState(46);
						match(CharRef);
						}
						break;
					case COMMENT:
						{
						setState(47);
						match(COMMENT);
						}
						break;
					case OPEN:
						{
						setState(48);
						element();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(53);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
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

	public static class ElementContext extends ParserRuleContext {
		public List<TerminalNode> OPEN() { return getTokens(VFParser.OPEN); }
		public TerminalNode OPEN(int i) {
			return getToken(VFParser.OPEN, i);
		}
		public List<TerminalNode> Name() { return getTokens(VFParser.Name); }
		public TerminalNode Name(int i) {
			return getToken(VFParser.Name, i);
		}
		public List<TerminalNode> CLOSE() { return getTokens(VFParser.CLOSE); }
		public TerminalNode CLOSE(int i) {
			return getToken(VFParser.CLOSE, i);
		}
		public ContentContext content() {
			return getRuleContext(ContentContext.class,0);
		}
		public TerminalNode SLASH() { return getToken(VFParser.SLASH, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public TerminalNode SLASH_CLOSE() { return getToken(VFParser.SLASH_CLOSE, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_element);
		int _la;
		try {
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				match(OPEN);
				setState(55);
				match(Name);
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(56);
					attribute();
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(62);
				match(CLOSE);
				setState(63);
				content();
				setState(64);
				match(OPEN);
				setState(65);
				match(SLASH);
				setState(66);
				match(Name);
				setState(67);
				match(CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				match(OPEN);
				setState(70);
				match(Name);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(71);
					attribute();
					}
					}
					setState(76);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(77);
				match(SLASH_CLOSE);
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

	public static class ElExpressionContext extends ParserRuleContext {
		public TerminalNode EL_START() { return getToken(VFParser.EL_START, 0); }
		public TerminalNode EL_END() { return getToken(VFParser.EL_END, 0); }
		public ElExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterElExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitElExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitElExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElExpressionContext elExpression() throws RecognitionException {
		ElExpressionContext _localctx = new ElExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_elExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(EL_START);
			setState(81);
			match(EL_END);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24V\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\5\2\22\n\2\3\2\3\2\3\2"+
		"\3\3\7\3\30\n\3\f\3\16\3\33\13\3\3\3\3\3\5\3\37\n\3\3\4\3\4\7\4#\n\4\f"+
		"\4\16\4&\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\7\6\64\n"+
		"\6\f\6\16\6\67\13\6\3\7\3\7\3\7\7\7<\n\7\f\7\16\7?\13\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7K\n\7\f\7\16\7N\13\7\3\7\5\7Q\n\7\3\b\3"+
		"\b\3\b\3\b\2\2\t\2\4\6\b\n\f\16\2\3\4\2\3\3\13\13\2[\2\21\3\2\2\2\4\31"+
		"\3\2\2\2\6 \3\2\2\2\b)\3\2\2\2\n\65\3\2\2\2\fP\3\2\2\2\16R\3\2\2\2\20"+
		"\22\5\4\3\2\21\20\3\2\2\2\21\22\3\2\2\2\22\23\3\2\2\2\23\24\5\n\6\2\24"+
		"\25\7\2\2\3\25\3\3\2\2\2\26\30\t\2\2\2\27\26\3\2\2\2\30\33\3\2\2\2\31"+
		"\27\3\2\2\2\31\32\3\2\2\2\32\36\3\2\2\2\33\31\3\2\2\2\34\37\5\6\4\2\35"+
		"\37\7\4\2\2\36\34\3\2\2\2\36\35\3\2\2\2\37\5\3\2\2\2 $\7\5\2\2!#\5\b\5"+
		"\2\"!\3\2\2\2#&\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%\'\3\2\2\2&$\3\2\2\2\'(\7"+
		"\r\2\2(\7\3\2\2\2)*\7\24\2\2*+\7\21\2\2+,\7\22\2\2,\t\3\2\2\2-\64\5\16"+
		"\b\2.\64\7\f\2\2/\64\7\t\2\2\60\64\7\n\2\2\61\64\7\3\2\2\62\64\5\f\7\2"+
		"\63-\3\2\2\2\63.\3\2\2\2\63/\3\2\2\2\63\60\3\2\2\2\63\61\3\2\2\2\63\62"+
		"\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66\13\3\2\2\2\67\65"+
		"\3\2\2\289\7\6\2\29=\7\24\2\2:<\5\b\5\2;:\3\2\2\2<?\3\2\2\2=;\3\2\2\2"+
		"=>\3\2\2\2>@\3\2\2\2?=\3\2\2\2@A\7\16\2\2AB\5\n\6\2BC\7\6\2\2CD\7\20\2"+
		"\2DE\7\24\2\2EF\7\16\2\2FQ\3\2\2\2GH\7\6\2\2HL\7\24\2\2IK\5\b\5\2JI\3"+
		"\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NL\3\2\2\2OQ\7\17\2\2P8"+
		"\3\2\2\2PG\3\2\2\2Q\r\3\2\2\2RS\7\7\2\2ST\7\b\2\2T\17\3\2\2\2\13\21\31"+
		"\36$\63\65=LP";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}