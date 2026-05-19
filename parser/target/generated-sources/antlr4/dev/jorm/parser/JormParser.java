// Generated from dev/jorm/parser/Jorm.g4 by ANTLR 4.13.1
package dev.jorm.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class JormParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		WHITESPACE=10, LINE_COMMENT=11, BLOCK_COMMENT=12, CONFIG=13, MODEL=14, 
		ENUM=15, ID=16, STRING_LIT=17;
	public static final int
		RULE_schema = 0, RULE_block = 1, RULE_configBlock = 2, RULE_configEntry = 3, 
		RULE_configKey = 4, RULE_modelBlock = 5, RULE_field = 6, RULE_fieldType = 7, 
		RULE_attribute = 8, RULE_attributeArg = 9, RULE_enumBlock = 10, RULE_enumValue = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"schema", "block", "configBlock", "configEntry", "configKey", "modelBlock", 
			"field", "fieldType", "attribute", "attributeArg", "enumBlock", "enumValue"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'='", "'.'", "'[]'", "'?'", "'@'", "'('", "')'", 
			null, null, null, "'config'", "'model'", "'enum'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "WHITESPACE", 
			"LINE_COMMENT", "BLOCK_COMMENT", "CONFIG", "MODEL", "ENUM", "ID", "STRING_LIT"
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
	public String getGrammarFileName() { return "Jorm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JormParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SchemaContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JormParser.EOF, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public SchemaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_schema; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterSchema(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitSchema(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitSchema(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SchemaContext schema() throws RecognitionException {
		SchemaContext _localctx = new SchemaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_schema);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 57344L) != 0)) {
				{
				{
				setState(24);
				block();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
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

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public ConfigBlockContext configBlock() {
			return getRuleContext(ConfigBlockContext.class,0);
		}
		public ModelBlockContext modelBlock() {
			return getRuleContext(ModelBlockContext.class,0);
		}
		public EnumBlockContext enumBlock() {
			return getRuleContext(EnumBlockContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		try {
			setState(35);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONFIG:
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				configBlock();
				}
				break;
			case MODEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				modelBlock();
				}
				break;
			case ENUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(34);
				enumBlock();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConfigBlockContext extends ParserRuleContext {
		public TerminalNode CONFIG() { return getToken(JormParser.CONFIG, 0); }
		public List<ConfigEntryContext> configEntry() {
			return getRuleContexts(ConfigEntryContext.class);
		}
		public ConfigEntryContext configEntry(int i) {
			return getRuleContext(ConfigEntryContext.class,i);
		}
		public ConfigBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterConfigBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitConfigBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitConfigBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfigBlockContext configBlock() throws RecognitionException {
		ConfigBlockContext _localctx = new ConfigBlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_configBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(CONFIG);
			setState(38);
			match(T__0);
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(39);
				configEntry();
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
			match(T__1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConfigEntryContext extends ParserRuleContext {
		public ConfigKeyContext configKey() {
			return getRuleContext(ConfigKeyContext.class,0);
		}
		public TerminalNode STRING_LIT() { return getToken(JormParser.STRING_LIT, 0); }
		public ConfigEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterConfigEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitConfigEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitConfigEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfigEntryContext configEntry() throws RecognitionException {
		ConfigEntryContext _localctx = new ConfigEntryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_configEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			configKey();
			setState(48);
			match(T__2);
			setState(49);
			match(STRING_LIT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConfigKeyContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(JormParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JormParser.ID, i);
		}
		public ConfigKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_configKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterConfigKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitConfigKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitConfigKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfigKeyContext configKey() throws RecognitionException {
		ConfigKeyContext _localctx = new ConfigKeyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_configKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(ID);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(52);
				match(T__3);
				setState(53);
				match(ID);
				}
				}
				setState(58);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ModelBlockContext extends ParserRuleContext {
		public TerminalNode MODEL() { return getToken(JormParser.MODEL, 0); }
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public ModelBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterModelBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitModelBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitModelBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelBlockContext modelBlock() throws RecognitionException {
		ModelBlockContext _localctx = new ModelBlockContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_modelBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(MODEL);
			setState(60);
			match(ID);
			setState(61);
			match(T__0);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(62);
				field();
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(T__1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FieldContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public FieldTypeContext fieldType() {
			return getRuleContext(FieldTypeContext.class,0);
		}
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(ID);
			setState(71);
			fieldType();
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class FieldTypeContext extends ParserRuleContext {
		public Token isArray;
		public Token isOptional;
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public FieldTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterFieldType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitFieldType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitFieldType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldTypeContext fieldType() throws RecognitionException {
		FieldTypeContext _localctx = new FieldTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fieldType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(ID);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(79);
				((FieldTypeContext)_localctx).isArray = match(T__4);
				}
			}

			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(82);
				((FieldTypeContext)_localctx).isOptional = match(T__5);
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public AttributeArgContext attributeArg() {
			return getRuleContext(AttributeArgContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(T__6);
			setState(86);
			match(ID);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(87);
				match(T__7);
				setState(88);
				attributeArg();
				setState(89);
				match(T__8);
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeArgContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public TerminalNode STRING_LIT() { return getToken(JormParser.STRING_LIT, 0); }
		public AttributeArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterAttributeArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitAttributeArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitAttributeArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeArgContext attributeArg() throws RecognitionException {
		AttributeArgContext _localctx = new AttributeArgContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_attributeArg);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==STRING_LIT) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class EnumBlockContext extends ParserRuleContext {
		public TerminalNode ENUM() { return getToken(JormParser.ENUM, 0); }
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public List<EnumValueContext> enumValue() {
			return getRuleContexts(EnumValueContext.class);
		}
		public EnumValueContext enumValue(int i) {
			return getRuleContext(EnumValueContext.class,i);
		}
		public EnumBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterEnumBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitEnumBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitEnumBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBlockContext enumBlock() throws RecognitionException {
		EnumBlockContext _localctx = new EnumBlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_enumBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(ENUM);
			setState(96);
			match(ID);
			setState(97);
			match(T__0);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(98);
				enumValue();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			match(T__1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class EnumValueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(JormParser.ID, 0); }
		public EnumValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).enterEnumValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JormListener ) ((JormListener)listener).exitEnumValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JormVisitor ) return ((JormVisitor<? extends T>)visitor).visitEnumValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumValueContext enumValue() throws RecognitionException {
		EnumValueContext _localctx = new EnumValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_enumValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(ID);
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
		"\u0004\u0001\u0011m\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001$\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002)\b\u0002\n\u0002\f\u0002"+
		",\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u00047\b\u0004"+
		"\n\u0004\f\u0004:\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005@\b\u0005\n\u0005\f\u0005C\t\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006J\b\u0006\n\u0006\f\u0006"+
		"M\t\u0006\u0001\u0007\u0001\u0007\u0003\u0007Q\b\u0007\u0001\u0007\u0003"+
		"\u0007T\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003"+
		"\b\\\b\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0005\nd\b\n"+
		"\n\n\f\ng\t\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0000"+
		"\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000"+
		"\u0001\u0001\u0000\u0010\u0011k\u0000\u001b\u0001\u0000\u0000\u0000\u0002"+
		"#\u0001\u0000\u0000\u0000\u0004%\u0001\u0000\u0000\u0000\u0006/\u0001"+
		"\u0000\u0000\u0000\b3\u0001\u0000\u0000\u0000\n;\u0001\u0000\u0000\u0000"+
		"\fF\u0001\u0000\u0000\u0000\u000eN\u0001\u0000\u0000\u0000\u0010U\u0001"+
		"\u0000\u0000\u0000\u0012]\u0001\u0000\u0000\u0000\u0014_\u0001\u0000\u0000"+
		"\u0000\u0016j\u0001\u0000\u0000\u0000\u0018\u001a\u0003\u0002\u0001\u0000"+
		"\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000"+
		"\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000"+
		"\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000"+
		"\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000\u0000"+
		" $\u0003\u0004\u0002\u0000!$\u0003\n\u0005\u0000\"$\u0003\u0014\n\u0000"+
		"# \u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000#\"\u0001\u0000\u0000"+
		"\u0000$\u0003\u0001\u0000\u0000\u0000%&\u0005\r\u0000\u0000&*\u0005\u0001"+
		"\u0000\u0000\')\u0003\u0006\u0003\u0000(\'\u0001\u0000\u0000\u0000),\u0001"+
		"\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000"+
		"+-\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000-.\u0005\u0002\u0000"+
		"\u0000.\u0005\u0001\u0000\u0000\u0000/0\u0003\b\u0004\u000001\u0005\u0003"+
		"\u0000\u000012\u0005\u0011\u0000\u00002\u0007\u0001\u0000\u0000\u0000"+
		"38\u0005\u0010\u0000\u000045\u0005\u0004\u0000\u000057\u0005\u0010\u0000"+
		"\u000064\u0001\u0000\u0000\u00007:\u0001\u0000\u0000\u000086\u0001\u0000"+
		"\u0000\u000089\u0001\u0000\u0000\u00009\t\u0001\u0000\u0000\u0000:8\u0001"+
		"\u0000\u0000\u0000;<\u0005\u000e\u0000\u0000<=\u0005\u0010\u0000\u0000"+
		"=A\u0005\u0001\u0000\u0000>@\u0003\f\u0006\u0000?>\u0001\u0000\u0000\u0000"+
		"@C\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000"+
		"\u0000BD\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000DE\u0005\u0002"+
		"\u0000\u0000E\u000b\u0001\u0000\u0000\u0000FG\u0005\u0010\u0000\u0000"+
		"GK\u0003\u000e\u0007\u0000HJ\u0003\u0010\b\u0000IH\u0001\u0000\u0000\u0000"+
		"JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000"+
		"\u0000L\r\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000NP\u0005\u0010"+
		"\u0000\u0000OQ\u0005\u0005\u0000\u0000PO\u0001\u0000\u0000\u0000PQ\u0001"+
		"\u0000\u0000\u0000QS\u0001\u0000\u0000\u0000RT\u0005\u0006\u0000\u0000"+
		"SR\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000T\u000f\u0001\u0000"+
		"\u0000\u0000UV\u0005\u0007\u0000\u0000V[\u0005\u0010\u0000\u0000WX\u0005"+
		"\b\u0000\u0000XY\u0003\u0012\t\u0000YZ\u0005\t\u0000\u0000Z\\\u0001\u0000"+
		"\u0000\u0000[W\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u0011"+
		"\u0001\u0000\u0000\u0000]^\u0007\u0000\u0000\u0000^\u0013\u0001\u0000"+
		"\u0000\u0000_`\u0005\u000f\u0000\u0000`a\u0005\u0010\u0000\u0000ae\u0005"+
		"\u0001\u0000\u0000bd\u0003\u0016\u000b\u0000cb\u0001\u0000\u0000\u0000"+
		"dg\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000"+
		"\u0000fh\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000\u0000hi\u0005\u0002"+
		"\u0000\u0000i\u0015\u0001\u0000\u0000\u0000jk\u0005\u0010\u0000\u0000"+
		"k\u0017\u0001\u0000\u0000\u0000\n\u001b#*8AKPS[e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}