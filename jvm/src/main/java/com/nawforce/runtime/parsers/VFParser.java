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
		COMMENT=1, PI_START=2, OPEN=3, EL_START=4, CHARDATA_REF=5, WS_NL=6, TEXT=7, 
		PI_END=8, CLOSE=9, SLASH_CLOSE=10, SLASH=11, EQUALS=12, STRING=13, ATTRS_START=14, 
		ATTRD_START=15, WS=16, Name=17, EL_END=18, EL_BODY=19, ATTRS_END=20, ATTRS_EL_START=21, 
		ATTRS_REF=22, ATTRS_TEXT=23, ATTRD_END=24, ATTRD_EL_START=25, ATTRD_REF=26, 
		ATTRD_TEXT=27;
	public static final int
		RULE_vfUnit = 0, RULE_element = 1, RULE_attribute = 2, RULE_attributeValues = 3, 
		RULE_content = 4, RULE_chardata = 5, RULE_processingInstruction = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"vfUnit", "element", "attribute", "attributeValues", "content", "chardata", 
			"processingInstruction"
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
		public List<TerminalNode> COMMENT() { return getTokens(VFParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(VFParser.COMMENT, i);
		}
		public List<TerminalNode> WS_NL() { return getTokens(VFParser.WS_NL); }
		public TerminalNode WS_NL(int i) {
			return getToken(VFParser.WS_NL, i);
		}
		public List<ProcessingInstructionContext> processingInstruction() {
			return getRuleContexts(ProcessingInstructionContext.class);
		}
		public ProcessingInstructionContext processingInstruction(int i) {
			return getRuleContext(ProcessingInstructionContext.class,i);
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
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << PI_START) | (1L << WS_NL))) != 0)) {
				{
				setState(17);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case COMMENT:
					{
					setState(14);
					match(COMMENT);
					}
					break;
				case WS_NL:
					{
					setState(15);
					match(WS_NL);
					}
					break;
				case PI_START:
					{
					setState(16);
					processingInstruction();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			element();
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT || _la==WS_NL) {
				{
				{
				setState(23);
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
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(29);
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
		enterRule(_localctx, 2, RULE_element);
		int _la;
		try {
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				match(OPEN);
				setState(32);
				match(Name);
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(33);
					attribute();
					}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(39);
				match(CLOSE);
				setState(40);
				content();
				setState(41);
				match(OPEN);
				setState(42);
				match(SLASH);
				setState(43);
				match(Name);
				setState(44);
				match(CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				match(OPEN);
				setState(47);
				match(Name);
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Name) {
					{
					{
					setState(48);
					attribute();
					}
					}
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(54);
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
		enterRule(_localctx, 4, RULE_attribute);
		int _la;
		try {
			setState(75);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				match(Name);
				setState(58);
				match(ATTRD_START);
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_REF) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_REF) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(59);
					attributeValues();
					}
					}
					setState(64);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(65);
				match(ATTRD_END);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
				match(Name);
				setState(67);
				match(ATTRS_START);
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_REF) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_REF) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(68);
					attributeValues();
					}
					}
					setState(73);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(74);
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
		enterRule(_localctx, 6, RULE_attributeValues);
		int _la;
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATTRD_TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(ATTRD_TEXT);
				}
				break;
			case ATTRS_TEXT:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				match(ATTRS_TEXT);
				}
				break;
			case ATTRD_EL_START:
				enterOuterAlt(_localctx, 3);
				{
				setState(79);
				match(ATTRD_EL_START);
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(80);
					match(EL_BODY);
					}
				}

				setState(83);
				match(EL_END);
				}
				break;
			case ATTRS_EL_START:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				match(ATTRS_EL_START);
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(85);
					match(EL_BODY);
					}
				}

				setState(88);
				match(EL_END);
				}
				break;
			case ATTRD_REF:
				enterOuterAlt(_localctx, 5);
				{
				setState(89);
				match(ATTRD_REF);
				}
				break;
			case ATTRS_REF:
				enterOuterAlt(_localctx, 6);
				{
				setState(90);
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
		public List<ProcessingInstructionContext> processingInstruction() {
			return getRuleContexts(ProcessingInstructionContext.class);
		}
		public ProcessingInstructionContext processingInstruction(int i) {
			return getRuleContext(ProcessingInstructionContext.class,i);
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
			setState(93);
			chardata();
			setState(102);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(97);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case COMMENT:
						{
						setState(94);
						match(COMMENT);
						}
						break;
					case PI_START:
						{
						setState(95);
						processingInstruction();
						}
						break;
					case OPEN:
						{
						setState(96);
						element();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(99);
					chardata();
					}
					} 
				}
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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
		enterRule(_localctx, 10, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EL_START) | (1L << CHARDATA_REF) | (1L << TEXT))) != 0)) {
				{
				setState(112);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(105);
					match(TEXT);
					}
					break;
				case CHARDATA_REF:
					{
					setState(106);
					match(CHARDATA_REF);
					}
					break;
				case EL_START:
					{
					setState(107);
					match(EL_START);
					setState(109);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==EL_BODY) {
						{
						setState(108);
						match(EL_BODY);
						}
					}

					setState(111);
					match(EL_END);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(116);
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

	public static class ProcessingInstructionContext extends ParserRuleContext {
		public TerminalNode PI_START() { return getToken(VFParser.PI_START, 0); }
		public TerminalNode PI_END() { return getToken(VFParser.PI_END, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ProcessingInstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processingInstruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterProcessingInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitProcessingInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitProcessingInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessingInstructionContext processingInstruction() throws RecognitionException {
		ProcessingInstructionContext _localctx = new ProcessingInstructionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_processingInstruction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(PI_START);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Name) {
				{
				{
				setState(118);
				attribute();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(124);
			match(PI_END);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u0081\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\7\2\24\n"+
		"\2\f\2\16\2\27\13\2\3\2\3\2\7\2\33\n\2\f\2\16\2\36\13\2\3\2\3\2\3\3\3"+
		"\3\3\3\7\3%\n\3\f\3\16\3(\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\7\3\64\n\3\f\3\16\3\67\13\3\3\3\5\3:\n\3\3\4\3\4\3\4\7\4?\n\4\f\4\16"+
		"\4B\13\4\3\4\3\4\3\4\3\4\7\4H\n\4\f\4\16\4K\13\4\3\4\5\4N\n\4\3\5\3\5"+
		"\3\5\3\5\5\5T\n\5\3\5\3\5\3\5\5\5Y\n\5\3\5\3\5\3\5\5\5^\n\5\3\6\3\6\3"+
		"\6\3\6\5\6d\n\6\3\6\7\6g\n\6\f\6\16\6j\13\6\3\7\3\7\3\7\3\7\5\7p\n\7\3"+
		"\7\7\7s\n\7\f\7\16\7v\13\7\3\b\3\b\7\bz\n\b\f\b\16\b}\13\b\3\b\3\b\3\b"+
		"\2\2\t\2\4\6\b\n\f\16\2\3\4\2\3\3\b\b\2\u0092\2\25\3\2\2\2\49\3\2\2\2"+
		"\6M\3\2\2\2\b]\3\2\2\2\n_\3\2\2\2\ft\3\2\2\2\16w\3\2\2\2\20\24\7\3\2\2"+
		"\21\24\7\b\2\2\22\24\5\16\b\2\23\20\3\2\2\2\23\21\3\2\2\2\23\22\3\2\2"+
		"\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2"+
		"\2\30\34\5\4\3\2\31\33\t\2\2\2\32\31\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2"+
		"\2\34\35\3\2\2\2\35\37\3\2\2\2\36\34\3\2\2\2\37 \7\2\2\3 \3\3\2\2\2!\""+
		"\7\5\2\2\"&\7\23\2\2#%\5\6\4\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2"+
		"\2\')\3\2\2\2(&\3\2\2\2)*\7\13\2\2*+\5\n\6\2+,\7\5\2\2,-\7\r\2\2-.\7\23"+
		"\2\2./\7\13\2\2/:\3\2\2\2\60\61\7\5\2\2\61\65\7\23\2\2\62\64\5\6\4\2\63"+
		"\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\668\3\2\2\2\67\65"+
		"\3\2\2\28:\7\f\2\29!\3\2\2\29\60\3\2\2\2:\5\3\2\2\2;<\7\23\2\2<@\7\21"+
		"\2\2=?\5\b\5\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B@\3\2"+
		"\2\2CN\7\32\2\2DE\7\23\2\2EI\7\20\2\2FH\5\b\5\2GF\3\2\2\2HK\3\2\2\2IG"+
		"\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2\2\2LN\7\26\2\2M;\3\2\2\2MD\3\2\2\2"+
		"N\7\3\2\2\2O^\7\35\2\2P^\7\31\2\2QS\7\33\2\2RT\7\25\2\2SR\3\2\2\2ST\3"+
		"\2\2\2TU\3\2\2\2U^\7\24\2\2VX\7\27\2\2WY\7\25\2\2XW\3\2\2\2XY\3\2\2\2"+
		"YZ\3\2\2\2Z^\7\24\2\2[^\7\34\2\2\\^\7\30\2\2]O\3\2\2\2]P\3\2\2\2]Q\3\2"+
		"\2\2]V\3\2\2\2][\3\2\2\2]\\\3\2\2\2^\t\3\2\2\2_h\5\f\7\2`d\7\3\2\2ad\5"+
		"\16\b\2bd\5\4\3\2c`\3\2\2\2ca\3\2\2\2cb\3\2\2\2de\3\2\2\2eg\5\f\7\2fc"+
		"\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2i\13\3\2\2\2jh\3\2\2\2ks\7\t\2\2"+
		"ls\7\7\2\2mo\7\6\2\2np\7\25\2\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qs\7\24\2"+
		"\2rk\3\2\2\2rl\3\2\2\2rm\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2u\r\3\2"+
		"\2\2vt\3\2\2\2w{\7\4\2\2xz\5\6\4\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2"+
		"\2\2|~\3\2\2\2}{\3\2\2\2~\177\7\n\2\2\177\17\3\2\2\2\24\23\25\34&\659"+
		"@IMSX]chort{";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}