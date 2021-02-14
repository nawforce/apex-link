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
		COMMENT=1, DOCTYPE=2, DECL_START=3, OPEN=4, EL_START=5, CHARDATA_REF=6, 
		WS_NL=7, TEXT=8, DECL_END=9, CLOSE=10, SLASH_CLOSE=11, SLASH=12, EQUALS=13, 
		STRING=14, ATTRS_START=15, ATTRD_START=16, WS=17, Name=18, EL_END=19, 
		EL_BODY=20, ATTRS_END=21, ATTRS_EL_START=22, ATTRS_REF=23, ATTRS_TEXT=24, 
		ATTRD_END=25, ATTRD_EL_START=26, ATTRD_REF=27, ATTRD_TEXT=28;
	public static final int
		RULE_vfUnit = 0, RULE_prolog = 1, RULE_declaration = 2, RULE_element = 3, 
		RULE_attribute = 4, RULE_attributeValues = 5, RULE_content = 6, RULE_chardata = 7, 
		RULE_misc = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"vfUnit", "prolog", "declaration", "element", "attribute", "attributeValues", 
			"content", "chardata", "misc"
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
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public TerminalNode EOF() { return getToken(VFParser.EOF, 0); }
		public PrologContext prolog() {
			return getRuleContext(PrologContext.class,0);
		}
		public List<MiscContext> misc() {
			return getRuleContexts(MiscContext.class);
		}
		public MiscContext misc(int i) {
			return getRuleContext(MiscContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(18);
				prolog();
				}
				break;
			}
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(21);
				misc();
				}
				}
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(27);
			element();
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(28);
				misc();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
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
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT || _la==WS_NL) {
				{
				{
				setState(36);
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
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECL_START:
				{
				setState(42);
				declaration();
				}
				break;
			case DOCTYPE:
				{
				setState(43);
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
			setState(46);
			match(DECL_START);
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(47);
				attribute();
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53);
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
		enterRule(_localctx, 6, RULE_element);
		int _la;
		try {
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				match(OPEN);
				setState(56);
				match(Name);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(57);
					attribute();
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(63);
				match(CLOSE);
				setState(64);
				content();
				setState(65);
				match(OPEN);
				setState(66);
				match(SLASH);
				setState(67);
				match(Name);
				setState(68);
				match(CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				match(OPEN);
				setState(71);
				match(Name);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(72);
					attribute();
					}
					}
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(78);
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(VFParser.Name, 0); }
		public TerminalNode ATTRD_START() { return getToken(VFParser.ATTRD_START, 0); }
		public TerminalNode ATTRD_END() { return getToken(VFParser.ATTRD_END, 0); }
		public List<AttributeValuesContext> attributeValues() {
			return getRuleContexts(AttributeValuesContext.class);
		}
		public AttributeValuesContext attributeValues(int i) {
			return getRuleContext(AttributeValuesContext.class,i);
		}
		public TerminalNode ATTRS_START() { return getToken(VFParser.ATTRS_START, 0); }
		public TerminalNode ATTRS_END() { return getToken(VFParser.ATTRS_END, 0); }
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
		enterRule(_localctx, 8, RULE_attribute);
		int _la;
		try {
			setState(99);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				match(Name);
				setState(82);
				match(ATTRD_START);
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_REF) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_REF) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(83);
					attributeValues();
					}
					}
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(89);
				match(ATTRD_END);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				match(Name);
				setState(91);
				match(ATTRS_START);
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_REF) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_REF) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(92);
					attributeValues();
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(98);
				match(ATTRS_END);
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

	public static class AttributeValuesContext extends ParserRuleContext {
		public TerminalNode ATTRD_TEXT() { return getToken(VFParser.ATTRD_TEXT, 0); }
		public TerminalNode ATTRS_TEXT() { return getToken(VFParser.ATTRS_TEXT, 0); }
		public TerminalNode ATTRD_EL_START() { return getToken(VFParser.ATTRD_EL_START, 0); }
		public TerminalNode EL_END() { return getToken(VFParser.EL_END, 0); }
		public TerminalNode EL_BODY() { return getToken(VFParser.EL_BODY, 0); }
		public TerminalNode ATTRS_EL_START() { return getToken(VFParser.ATTRS_EL_START, 0); }
		public TerminalNode ATTRD_REF() { return getToken(VFParser.ATTRD_REF, 0); }
		public TerminalNode ATTRS_REF() { return getToken(VFParser.ATTRS_REF, 0); }
		public AttributeValuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeValues; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterAttributeValues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitAttributeValues(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitAttributeValues(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeValuesContext attributeValues() throws RecognitionException {
		AttributeValuesContext _localctx = new AttributeValuesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_attributeValues);
		int _la;
		try {
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATTRD_TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				match(ATTRD_TEXT);
				}
				break;
			case ATTRS_TEXT:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(ATTRS_TEXT);
				}
				break;
			case ATTRD_EL_START:
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				match(ATTRD_EL_START);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(104);
					match(EL_BODY);
					}
				}

				setState(107);
				match(EL_END);
				}
				break;
			case ATTRS_EL_START:
				enterOuterAlt(_localctx, 4);
				{
				setState(108);
				match(ATTRS_EL_START);
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(109);
					match(EL_BODY);
					}
				}

				setState(112);
				match(EL_END);
				}
				break;
			case ATTRD_REF:
				enterOuterAlt(_localctx, 5);
				{
				setState(113);
				match(ATTRD_REF);
				}
				break;
			case ATTRS_REF:
				enterOuterAlt(_localctx, 6);
				{
				setState(114);
				match(ATTRS_REF);
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

	public static class ContentContext extends ParserRuleContext {
		public List<ChardataContext> chardata() {
			return getRuleContexts(ChardataContext.class);
		}
		public ChardataContext chardata(int i) {
			return getRuleContext(ChardataContext.class,i);
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
		enterRule(_localctx, 12, RULE_content);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			chardata();
			setState(125);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(120);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case COMMENT:
						{
						setState(118);
						match(COMMENT);
						}
						break;
					case OPEN:
						{
						setState(119);
						element();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(122);
					chardata();
					}
					} 
				}
				setState(127);
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

	public static class ChardataContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(VFParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(VFParser.TEXT, i);
		}
		public List<TerminalNode> CHARDATA_REF() { return getTokens(VFParser.CHARDATA_REF); }
		public TerminalNode CHARDATA_REF(int i) {
			return getToken(VFParser.CHARDATA_REF, i);
		}
		public List<TerminalNode> EL_START() { return getTokens(VFParser.EL_START); }
		public TerminalNode EL_START(int i) {
			return getToken(VFParser.EL_START, i);
		}
		public List<TerminalNode> EL_END() { return getTokens(VFParser.EL_END); }
		public TerminalNode EL_END(int i) {
			return getToken(VFParser.EL_END, i);
		}
		public List<TerminalNode> EL_BODY() { return getTokens(VFParser.EL_BODY); }
		public TerminalNode EL_BODY(int i) {
			return getToken(VFParser.EL_BODY, i);
		}
		public ChardataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chardata; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterChardata(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitChardata(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitChardata(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChardataContext chardata() throws RecognitionException {
		ChardataContext _localctx = new ChardataContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EL_START) | (1L << CHARDATA_REF) | (1L << TEXT))) != 0)) {
				{
				setState(135);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(128);
					match(TEXT);
					}
					break;
				case CHARDATA_REF:
					{
					setState(129);
					match(CHARDATA_REF);
					}
					break;
				case EL_START:
					{
					setState(130);
					match(EL_START);
					setState(132);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==EL_BODY) {
						{
						setState(131);
						match(EL_BODY);
						}
					}

					setState(134);
					match(EL_END);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(139);
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

	public static class MiscContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(VFParser.COMMENT, 0); }
		public MiscContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_misc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterMisc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitMisc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitMisc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MiscContext misc() throws RecognitionException {
		MiscContext _localctx = new MiscContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_misc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(COMMENT);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36\u0091\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\5"+
		"\2\26\n\2\3\2\7\2\31\n\2\f\2\16\2\34\13\2\3\2\3\2\7\2 \n\2\f\2\16\2#\13"+
		"\2\3\2\3\2\3\3\7\3(\n\3\f\3\16\3+\13\3\3\3\3\3\5\3/\n\3\3\4\3\4\7\4\63"+
		"\n\4\f\4\16\4\66\13\4\3\4\3\4\3\5\3\5\3\5\7\5=\n\5\f\5\16\5@\13\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5L\n\5\f\5\16\5O\13\5\3\5\5\5R"+
		"\n\5\3\6\3\6\3\6\7\6W\n\6\f\6\16\6Z\13\6\3\6\3\6\3\6\3\6\7\6`\n\6\f\6"+
		"\16\6c\13\6\3\6\5\6f\n\6\3\7\3\7\3\7\3\7\5\7l\n\7\3\7\3\7\3\7\5\7q\n\7"+
		"\3\7\3\7\3\7\5\7v\n\7\3\b\3\b\3\b\5\b{\n\b\3\b\7\b~\n\b\f\b\16\b\u0081"+
		"\13\b\3\t\3\t\3\t\3\t\5\t\u0087\n\t\3\t\7\t\u008a\n\t\f\t\16\t\u008d\13"+
		"\t\3\n\3\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\3\4\2\3\3\t\t\2\u00a0\2\25"+
		"\3\2\2\2\4)\3\2\2\2\6\60\3\2\2\2\bQ\3\2\2\2\ne\3\2\2\2\fu\3\2\2\2\16w"+
		"\3\2\2\2\20\u008b\3\2\2\2\22\u008e\3\2\2\2\24\26\5\4\3\2\25\24\3\2\2\2"+
		"\25\26\3\2\2\2\26\32\3\2\2\2\27\31\5\22\n\2\30\27\3\2\2\2\31\34\3\2\2"+
		"\2\32\30\3\2\2\2\32\33\3\2\2\2\33\35\3\2\2\2\34\32\3\2\2\2\35!\5\b\5\2"+
		"\36 \5\22\n\2\37\36\3\2\2\2 #\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"$\3\2\2"+
		"\2#!\3\2\2\2$%\7\2\2\3%\3\3\2\2\2&(\t\2\2\2\'&\3\2\2\2(+\3\2\2\2)\'\3"+
		"\2\2\2)*\3\2\2\2*.\3\2\2\2+)\3\2\2\2,/\5\6\4\2-/\7\4\2\2.,\3\2\2\2.-\3"+
		"\2\2\2/\5\3\2\2\2\60\64\7\5\2\2\61\63\5\n\6\2\62\61\3\2\2\2\63\66\3\2"+
		"\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\7\13"+
		"\2\28\7\3\2\2\29:\7\6\2\2:>\7\24\2\2;=\5\n\6\2<;\3\2\2\2=@\3\2\2\2><\3"+
		"\2\2\2>?\3\2\2\2?A\3\2\2\2@>\3\2\2\2AB\7\f\2\2BC\5\16\b\2CD\7\6\2\2DE"+
		"\7\16\2\2EF\7\24\2\2FG\7\f\2\2GR\3\2\2\2HI\7\6\2\2IM\7\24\2\2JL\5\n\6"+
		"\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PR\7\r\2"+
		"\2Q9\3\2\2\2QH\3\2\2\2R\t\3\2\2\2ST\7\24\2\2TX\7\22\2\2UW\5\f\7\2VU\3"+
		"\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y[\3\2\2\2ZX\3\2\2\2[f\7\33\2\2\\"+
		"]\7\24\2\2]a\7\21\2\2^`\5\f\7\2_^\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2"+
		"\2bd\3\2\2\2ca\3\2\2\2df\7\27\2\2eS\3\2\2\2e\\\3\2\2\2f\13\3\2\2\2gv\7"+
		"\36\2\2hv\7\32\2\2ik\7\34\2\2jl\7\26\2\2kj\3\2\2\2kl\3\2\2\2lm\3\2\2\2"+
		"mv\7\25\2\2np\7\30\2\2oq\7\26\2\2po\3\2\2\2pq\3\2\2\2qr\3\2\2\2rv\7\25"+
		"\2\2sv\7\35\2\2tv\7\31\2\2ug\3\2\2\2uh\3\2\2\2ui\3\2\2\2un\3\2\2\2us\3"+
		"\2\2\2ut\3\2\2\2v\r\3\2\2\2w\177\5\20\t\2x{\7\3\2\2y{\5\b\5\2zx\3\2\2"+
		"\2zy\3\2\2\2{|\3\2\2\2|~\5\20\t\2}z\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2"+
		"\2\177\u0080\3\2\2\2\u0080\17\3\2\2\2\u0081\177\3\2\2\2\u0082\u008a\7"+
		"\n\2\2\u0083\u008a\7\b\2\2\u0084\u0086\7\7\2\2\u0085\u0087\7\26\2\2\u0086"+
		"\u0085\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a\7\25"+
		"\2\2\u0089\u0082\3\2\2\2\u0089\u0083\3\2\2\2\u0089\u0084\3\2\2\2\u008a"+
		"\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\21\3\2\2"+
		"\2\u008d\u008b\3\2\2\2\u008e\u008f\7\3\2\2\u008f\23\3\2\2\2\26\25\32!"+
		").\64>MQXaekpuz\177\u0086\u0089\u008b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}