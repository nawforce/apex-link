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
		COMMENT=1, PI_START=2, OPEN=3, OPEN_SCRIPT=4, CDATA_START=5, EL_START=6, 
		CHARDATA_REF=7, WS_NL=8, TEXT=9, CLOSE_SCRIPT=10, CLOSE_OPEN_SCRIPT=11, 
		ScriptName=12, SCRIPT_ATTRS_START=13, SCRIPT_ATTRD_START=14, SCRIPT_WS=15, 
		END_SCRIPT=16, SCRIPT_EL_START=17, SCRIPT_CHARDATA_REF=18, SCRIPT_WS_NL=19, 
		SCRIPT_TEXT=20, PI_END=21, CLOSE=22, SLASH_CLOSE=23, SLASH=24, EQUALS=25, 
		STRING=26, ATTRS_START=27, ATTRD_START=28, WS=29, Name=30, EL_END=31, 
		EL_BODY=32, CDATA_END=33, CDATA_EL=34, CDATA_TEXT=35, ATTRS_END=36, ATTRS_EL_START=37, 
		ATTRS_TEXT=38, ATTRD_END=39, ATTRD_EL_START=40, ATTRD_TEXT=41;
	public static final int
		RULE_vfUnit = 0, RULE_element = 1, RULE_attribute = 2, RULE_attributeName = 3, 
		RULE_attributeValues = 4, RULE_content = 5, RULE_chardata = 6, RULE_processingInstruction = 7, 
		RULE_scriptChardata = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"vfUnit", "element", "attribute", "attributeName", "attributeValues", 
			"content", "chardata", "processingInstruction", "scriptChardata"
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
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << PI_START) | (1L << WS_NL))) != 0)) {
				{
				setState(21);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case COMMENT:
					{
					setState(18);
					match(COMMENT);
					}
					break;
				case WS_NL:
					{
					setState(19);
					match(WS_NL);
					}
					break;
				case PI_START:
					{
					setState(20);
					processingInstruction();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
			element();
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT || _la==WS_NL) {
				{
				{
				setState(27);
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
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(33);
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
		public TerminalNode OPEN_SCRIPT() { return getToken(VFParser.OPEN_SCRIPT, 0); }
		public TerminalNode CLOSE_OPEN_SCRIPT() { return getToken(VFParser.CLOSE_OPEN_SCRIPT, 0); }
		public ScriptChardataContext scriptChardata() {
			return getRuleContext(ScriptChardataContext.class,0);
		}
		public TerminalNode END_SCRIPT() { return getToken(VFParser.END_SCRIPT, 0); }
		public TerminalNode CLOSE_SCRIPT() { return getToken(VFParser.CLOSE_SCRIPT, 0); }
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
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				match(OPEN);
				setState(36);
				match(Name);
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ScriptName || _la==Name) {
					{
					{
					setState(37);
					attribute();
					}
					}
					setState(42);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(43);
				match(CLOSE);
				setState(44);
				content();
				setState(45);
				match(OPEN);
				setState(46);
				match(SLASH);
				setState(47);
				match(Name);
				setState(48);
				match(CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				match(OPEN);
				setState(51);
				match(Name);
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ScriptName || _la==Name) {
					{
					{
					setState(52);
					attribute();
					}
					}
					setState(57);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(58);
				match(SLASH_CLOSE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(59);
				match(OPEN_SCRIPT);
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ScriptName || _la==Name) {
					{
					{
					setState(60);
					attribute();
					}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(66);
				match(CLOSE_OPEN_SCRIPT);
				setState(67);
				scriptChardata();
				setState(68);
				match(END_SCRIPT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(70);
				match(OPEN_SCRIPT);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ScriptName || _la==Name) {
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
				match(CLOSE_SCRIPT);
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
		public AttributeNameContext attributeName() {
			return getRuleContext(AttributeNameContext.class,0);
		}
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
		public TerminalNode SCRIPT_ATTRD_START() { return getToken(VFParser.SCRIPT_ATTRD_START, 0); }
		public TerminalNode SCRIPT_ATTRS_START() { return getToken(VFParser.SCRIPT_ATTRS_START, 0); }
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
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				attributeName();
				setState(81);
				match(ATTRD_START);
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(82);
					attributeValues();
					}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(88);
				match(ATTRD_END);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				attributeName();
				setState(91);
				match(ATTRS_START);
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_TEXT))) != 0)) {
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
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(100);
				attributeName();
				setState(101);
				match(SCRIPT_ATTRD_START);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(102);
					attributeValues();
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(108);
				match(ATTRD_END);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(110);
				attributeName();
				setState(111);
				match(SCRIPT_ATTRS_START);
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ATTRS_EL_START) | (1L << ATTRS_TEXT) | (1L << ATTRD_EL_START) | (1L << ATTRD_TEXT))) != 0)) {
					{
					{
					setState(112);
					attributeValues();
					}
					}
					setState(117);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(118);
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

	public static class AttributeNameContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(VFParser.Name, 0); }
		public TerminalNode ScriptName() { return getToken(VFParser.ScriptName, 0); }
		public AttributeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterAttributeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitAttributeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitAttributeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeNameContext attributeName() throws RecognitionException {
		AttributeNameContext _localctx = new AttributeNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attributeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_la = _input.LA(1);
			if ( !(_la==ScriptName || _la==Name) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class AttributeValuesContext extends ParserRuleContext {
		public TerminalNode ATTRD_TEXT() { return getToken(VFParser.ATTRD_TEXT, 0); }
		public TerminalNode ATTRS_TEXT() { return getToken(VFParser.ATTRS_TEXT, 0); }
		public TerminalNode ATTRD_EL_START() { return getToken(VFParser.ATTRD_EL_START, 0); }
		public TerminalNode EL_END() { return getToken(VFParser.EL_END, 0); }
		public TerminalNode EL_BODY() { return getToken(VFParser.EL_BODY, 0); }
		public TerminalNode ATTRS_EL_START() { return getToken(VFParser.ATTRS_EL_START, 0); }
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
		enterRule(_localctx, 8, RULE_attributeValues);
		int _la;
		try {
			setState(136);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATTRD_TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(ATTRD_TEXT);
				}
				break;
			case ATTRS_TEXT:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(ATTRS_TEXT);
				}
				break;
			case ATTRD_EL_START:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				match(ATTRD_EL_START);
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(127);
					match(EL_BODY);
					}
				}

				setState(130);
				match(EL_END);
				}
				break;
			case ATTRS_EL_START:
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				match(ATTRS_EL_START);
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EL_BODY) {
					{
					setState(132);
					match(EL_BODY);
					}
				}

				setState(135);
				match(EL_END);
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
		enterRule(_localctx, 10, RULE_content);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			chardata();
			setState(147);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(142);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case COMMENT:
						{
						setState(139);
						match(COMMENT);
						}
						break;
					case PI_START:
						{
						setState(140);
						processingInstruction();
						}
						break;
					case OPEN:
					case OPEN_SCRIPT:
						{
						setState(141);
						element();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(144);
					chardata();
					}
					} 
				}
				setState(149);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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
		public List<TerminalNode> WS_NL() { return getTokens(VFParser.WS_NL); }
		public TerminalNode WS_NL(int i) {
			return getToken(VFParser.WS_NL, i);
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
		public List<TerminalNode> CDATA_START() { return getTokens(VFParser.CDATA_START); }
		public TerminalNode CDATA_START(int i) {
			return getToken(VFParser.CDATA_START, i);
		}
		public List<TerminalNode> CDATA_END() { return getTokens(VFParser.CDATA_END); }
		public TerminalNode CDATA_END(int i) {
			return getToken(VFParser.CDATA_END, i);
		}
		public List<TerminalNode> EL_BODY() { return getTokens(VFParser.EL_BODY); }
		public TerminalNode EL_BODY(int i) {
			return getToken(VFParser.EL_BODY, i);
		}
		public List<TerminalNode> CDATA_TEXT() { return getTokens(VFParser.CDATA_TEXT); }
		public TerminalNode CDATA_TEXT(int i) {
			return getToken(VFParser.CDATA_TEXT, i);
		}
		public List<TerminalNode> CDATA_EL() { return getTokens(VFParser.CDATA_EL); }
		public TerminalNode CDATA_EL(int i) {
			return getToken(VFParser.CDATA_EL, i);
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
		enterRule(_localctx, 12, RULE_chardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CDATA_START) | (1L << EL_START) | (1L << CHARDATA_REF) | (1L << WS_NL) | (1L << TEXT))) != 0)) {
				{
				setState(171);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(150);
					match(TEXT);
					}
					break;
				case WS_NL:
					{
					setState(151);
					match(WS_NL);
					}
					break;
				case CHARDATA_REF:
					{
					setState(152);
					match(CHARDATA_REF);
					}
					break;
				case EL_START:
					{
					setState(153);
					match(EL_START);
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==EL_BODY) {
						{
						setState(154);
						match(EL_BODY);
						}
					}

					setState(157);
					match(EL_END);
					}
					break;
				case CDATA_START:
					{
					setState(158);
					match(CDATA_START);
					setState(167);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==CDATA_EL || _la==CDATA_TEXT) {
						{
						setState(165);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
						case CDATA_TEXT:
							{
							setState(159);
							match(CDATA_TEXT);
							}
							break;
						case CDATA_EL:
							{
							setState(160);
							match(CDATA_EL);
							setState(162);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la==EL_BODY) {
								{
								setState(161);
								match(EL_BODY);
								}
							}

							setState(164);
							match(EL_END);
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						}
						setState(169);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(170);
					match(CDATA_END);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(175);
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
		enterRule(_localctx, 14, RULE_processingInstruction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(PI_START);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ScriptName || _la==Name) {
				{
				{
				setState(177);
				attribute();
				}
				}
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(183);
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

	public static class ScriptChardataContext extends ParserRuleContext {
		public List<TerminalNode> SCRIPT_TEXT() { return getTokens(VFParser.SCRIPT_TEXT); }
		public TerminalNode SCRIPT_TEXT(int i) {
			return getToken(VFParser.SCRIPT_TEXT, i);
		}
		public List<TerminalNode> SCRIPT_WS_NL() { return getTokens(VFParser.SCRIPT_WS_NL); }
		public TerminalNode SCRIPT_WS_NL(int i) {
			return getToken(VFParser.SCRIPT_WS_NL, i);
		}
		public List<TerminalNode> SCRIPT_CHARDATA_REF() { return getTokens(VFParser.SCRIPT_CHARDATA_REF); }
		public TerminalNode SCRIPT_CHARDATA_REF(int i) {
			return getToken(VFParser.SCRIPT_CHARDATA_REF, i);
		}
		public List<TerminalNode> SCRIPT_EL_START() { return getTokens(VFParser.SCRIPT_EL_START); }
		public TerminalNode SCRIPT_EL_START(int i) {
			return getToken(VFParser.SCRIPT_EL_START, i);
		}
		public List<TerminalNode> EL_END() { return getTokens(VFParser.EL_END); }
		public TerminalNode EL_END(int i) {
			return getToken(VFParser.EL_END, i);
		}
		public List<TerminalNode> EL_BODY() { return getTokens(VFParser.EL_BODY); }
		public TerminalNode EL_BODY(int i) {
			return getToken(VFParser.EL_BODY, i);
		}
		public ScriptChardataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptChardata; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).enterScriptChardata(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VFParserListener ) ((VFParserListener)listener).exitScriptChardata(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VFParserVisitor ) return ((VFParserVisitor<? extends T>)visitor).visitScriptChardata(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptChardataContext scriptChardata() throws RecognitionException {
		ScriptChardataContext _localctx = new ScriptChardataContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_scriptChardata);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SCRIPT_EL_START) | (1L << SCRIPT_CHARDATA_REF) | (1L << SCRIPT_WS_NL) | (1L << SCRIPT_TEXT))) != 0)) {
				{
				setState(193);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SCRIPT_TEXT:
					{
					setState(185);
					match(SCRIPT_TEXT);
					}
					break;
				case SCRIPT_WS_NL:
					{
					setState(186);
					match(SCRIPT_WS_NL);
					}
					break;
				case SCRIPT_CHARDATA_REF:
					{
					setState(187);
					match(SCRIPT_CHARDATA_REF);
					}
					break;
				case SCRIPT_EL_START:
					{
					setState(188);
					match(SCRIPT_EL_START);
					setState(190);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==EL_BODY) {
						{
						setState(189);
						match(EL_BODY);
						}
					}

					setState(192);
					match(EL_END);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(197);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3+\u00c9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\3\2\7\2\37\n\2\f\2\16\2\"\13\2\3"+
		"\2\3\2\3\3\3\3\3\3\7\3)\n\3\f\3\16\3,\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\7\38\n\3\f\3\16\3;\13\3\3\3\3\3\3\3\7\3@\n\3\f\3\16\3C\13"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3K\n\3\f\3\16\3N\13\3\3\3\5\3Q\n\3\3\4\3"+
		"\4\3\4\7\4V\n\4\f\4\16\4Y\13\4\3\4\3\4\3\4\3\4\3\4\7\4`\n\4\f\4\16\4c"+
		"\13\4\3\4\3\4\3\4\3\4\3\4\7\4j\n\4\f\4\16\4m\13\4\3\4\3\4\3\4\3\4\3\4"+
		"\7\4t\n\4\f\4\16\4w\13\4\3\4\3\4\5\4{\n\4\3\5\3\5\3\6\3\6\3\6\3\6\5\6"+
		"\u0083\n\6\3\6\3\6\3\6\5\6\u0088\n\6\3\6\5\6\u008b\n\6\3\7\3\7\3\7\3\7"+
		"\5\7\u0091\n\7\3\7\7\7\u0094\n\7\f\7\16\7\u0097\13\7\3\b\3\b\3\b\3\b\3"+
		"\b\5\b\u009e\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u00a5\n\b\3\b\7\b\u00a8\n\b\f"+
		"\b\16\b\u00ab\13\b\3\b\7\b\u00ae\n\b\f\b\16\b\u00b1\13\b\3\t\3\t\7\t\u00b5"+
		"\n\t\f\t\16\t\u00b8\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\5\n\u00c1\n\n\3\n"+
		"\7\n\u00c4\n\n\f\n\16\n\u00c7\13\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\4"+
		"\4\2\3\3\n\n\4\2\16\16  \2\u00e8\2\31\3\2\2\2\4P\3\2\2\2\6z\3\2\2\2\b"+
		"|\3\2\2\2\n\u008a\3\2\2\2\f\u008c\3\2\2\2\16\u00af\3\2\2\2\20\u00b2\3"+
		"\2\2\2\22\u00c5\3\2\2\2\24\30\7\3\2\2\25\30\7\n\2\2\26\30\5\20\t\2\27"+
		"\24\3\2\2\2\27\25\3\2\2\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27\3\2\2\2\31"+
		"\32\3\2\2\2\32\34\3\2\2\2\33\31\3\2\2\2\34 \5\4\3\2\35\37\t\2\2\2\36\35"+
		"\3\2\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!#\3\2\2\2\" \3\2\2\2#$\7\2"+
		"\2\3$\3\3\2\2\2%&\7\5\2\2&*\7 \2\2\')\5\6\4\2(\'\3\2\2\2),\3\2\2\2*(\3"+
		"\2\2\2*+\3\2\2\2+-\3\2\2\2,*\3\2\2\2-.\7\30\2\2./\5\f\7\2/\60\7\5\2\2"+
		"\60\61\7\32\2\2\61\62\7 \2\2\62\63\7\30\2\2\63Q\3\2\2\2\64\65\7\5\2\2"+
		"\659\7 \2\2\668\5\6\4\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2"+
		":<\3\2\2\2;9\3\2\2\2<Q\7\31\2\2=A\7\6\2\2>@\5\6\4\2?>\3\2\2\2@C\3\2\2"+
		"\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\r\2\2EF\5\22\n\2FG\7\22"+
		"\2\2GQ\3\2\2\2HL\7\6\2\2IK\5\6\4\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2"+
		"\2\2MO\3\2\2\2NL\3\2\2\2OQ\7\f\2\2P%\3\2\2\2P\64\3\2\2\2P=\3\2\2\2PH\3"+
		"\2\2\2Q\5\3\2\2\2RS\5\b\5\2SW\7\36\2\2TV\5\n\6\2UT\3\2\2\2VY\3\2\2\2W"+
		"U\3\2\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z[\7)\2\2[{\3\2\2\2\\]\5\b\5\2"+
		"]a\7\35\2\2^`\5\n\6\2_^\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2"+
		"\2ca\3\2\2\2de\7&\2\2e{\3\2\2\2fg\5\b\5\2gk\7\20\2\2hj\5\n\6\2ih\3\2\2"+
		"\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2mk\3\2\2\2no\7)\2\2o{\3\2\2"+
		"\2pq\5\b\5\2qu\7\17\2\2rt\5\n\6\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2"+
		"\2\2vx\3\2\2\2wu\3\2\2\2xy\7&\2\2y{\3\2\2\2zR\3\2\2\2z\\\3\2\2\2zf\3\2"+
		"\2\2zp\3\2\2\2{\7\3\2\2\2|}\t\3\2\2}\t\3\2\2\2~\u008b\7+\2\2\177\u008b"+
		"\7(\2\2\u0080\u0082\7*\2\2\u0081\u0083\7\"\2\2\u0082\u0081\3\2\2\2\u0082"+
		"\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u008b\7!\2\2\u0085\u0087\7\'"+
		"\2\2\u0086\u0088\7\"\2\2\u0087\u0086\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u0089\3\2\2\2\u0089\u008b\7!\2\2\u008a~\3\2\2\2\u008a\177\3\2\2\2\u008a"+
		"\u0080\3\2\2\2\u008a\u0085\3\2\2\2\u008b\13\3\2\2\2\u008c\u0095\5\16\b"+
		"\2\u008d\u0091\7\3\2\2\u008e\u0091\5\20\t\2\u008f\u0091\5\4\3\2\u0090"+
		"\u008d\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2"+
		"\2\2\u0092\u0094\5\16\b\2\u0093\u0090\3\2\2\2\u0094\u0097\3\2\2\2\u0095"+
		"\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\r\3\2\2\2\u0097\u0095\3\2\2\2"+
		"\u0098\u00ae\7\13\2\2\u0099\u00ae\7\n\2\2\u009a\u00ae\7\t\2\2\u009b\u009d"+
		"\7\b\2\2\u009c\u009e\7\"\2\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u009f\3\2\2\2\u009f\u00ae\7!\2\2\u00a0\u00a9\7\7\2\2\u00a1\u00a8\7%\2"+
		"\2\u00a2\u00a4\7$\2\2\u00a3\u00a5\7\"\2\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5"+
		"\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a8\7!\2\2\u00a7\u00a1\3\2\2\2\u00a7"+
		"\u00a2\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2"+
		"\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00ae\7#\2\2\u00ad"+
		"\u0098\3\2\2\2\u00ad\u0099\3\2\2\2\u00ad\u009a\3\2\2\2\u00ad\u009b\3\2"+
		"\2\2\u00ad\u00a0\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\17\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b6\7\4\2"+
		"\2\u00b3\u00b5\5\6\4\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4"+
		"\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9"+
		"\u00ba\7\27\2\2\u00ba\21\3\2\2\2\u00bb\u00c4\7\26\2\2\u00bc\u00c4\7\25"+
		"\2\2\u00bd\u00c4\7\24\2\2\u00be\u00c0\7\23\2\2\u00bf\u00c1\7\"\2\2\u00c0"+
		"\u00bf\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\7!"+
		"\2\2\u00c3\u00bb\3\2\2\2\u00c3\u00bc\3\2\2\2\u00c3\u00bd\3\2\2\2\u00c3"+
		"\u00be\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2"+
		"\2\2\u00c6\23\3\2\2\2\u00c7\u00c5\3\2\2\2\36\27\31 *9ALPWakuz\u0082\u0087"+
		"\u008a\u0090\u0095\u009d\u00a4\u00a7\u00a9\u00ad\u00af\u00b6\u00c0\u00c3"+
		"\u00c5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}