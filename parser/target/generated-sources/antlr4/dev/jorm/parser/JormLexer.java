// Generated from dev/jorm/parser/Jorm.g4 by ANTLR 4.13.1
package dev.jorm.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JormLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, WHITESPACE=9, 
		LINE_COMMENT=10, BLOCK_COMMENT=11, CONFIG=12, MODEL=13, ENUM=14, ID=15, 
		STRING_LIT=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "WHITESPACE", 
			"LINE_COMMENT", "BLOCK_COMMENT", "CONFIG", "MODEL", "ENUM", "ID", "STRING_LIT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'='", "'[]'", "'?'", "'@'", "'('", "')'", null, 
			null, null, "'config'", "'model'", "'enum'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "WHITESPACE", "LINE_COMMENT", 
			"BLOCK_COMMENT", "CONFIG", "MODEL", "ENUM", "ID", "STRING_LIT"
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


	public JormLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Jorm.g4"; }

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
		"\u0004\u0000\u0010t\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0004\b4\b\b\u000b\b\f\b5\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t>\b\t\n\t\f\tA\t\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\nI\b\n\n\n\f\nL\t\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0005"+
		"\u000eg\b\u000e\n\u000e\f\u000ej\t\u000e\u0001\u000f\u0001\u000f\u0005"+
		"\u000fn\b\u000f\n\u000f\f\u000fq\t\u000f\u0001\u000f\u0001\u000f\u0001"+
		"J\u0000\u0010\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005"+
		"\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019"+
		"\r\u001b\u000e\u001d\u000f\u001f\u0010\u0001\u0000\u0005\u0003\u0000\t"+
		"\n\r\r  \u0002\u0000\n\n\r\r\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001"+
		"\u0000\"\"x\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0001!\u0001\u0000\u0000\u0000\u0003#\u0001\u0000\u0000\u0000\u0005%"+
		"\u0001\u0000\u0000\u0000\u0007\'\u0001\u0000\u0000\u0000\t*\u0001\u0000"+
		"\u0000\u0000\u000b,\u0001\u0000\u0000\u0000\r.\u0001\u0000\u0000\u0000"+
		"\u000f0\u0001\u0000\u0000\u0000\u00113\u0001\u0000\u0000\u0000\u00139"+
		"\u0001\u0000\u0000\u0000\u0015D\u0001\u0000\u0000\u0000\u0017R\u0001\u0000"+
		"\u0000\u0000\u0019Y\u0001\u0000\u0000\u0000\u001b_\u0001\u0000\u0000\u0000"+
		"\u001dd\u0001\u0000\u0000\u0000\u001fk\u0001\u0000\u0000\u0000!\"\u0005"+
		"{\u0000\u0000\"\u0002\u0001\u0000\u0000\u0000#$\u0005}\u0000\u0000$\u0004"+
		"\u0001\u0000\u0000\u0000%&\u0005=\u0000\u0000&\u0006\u0001\u0000\u0000"+
		"\u0000\'(\u0005[\u0000\u0000()\u0005]\u0000\u0000)\b\u0001\u0000\u0000"+
		"\u0000*+\u0005?\u0000\u0000+\n\u0001\u0000\u0000\u0000,-\u0005@\u0000"+
		"\u0000-\f\u0001\u0000\u0000\u0000./\u0005(\u0000\u0000/\u000e\u0001\u0000"+
		"\u0000\u000001\u0005)\u0000\u00001\u0010\u0001\u0000\u0000\u000024\u0007"+
		"\u0000\u0000\u000032\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"53\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u000067\u0001\u0000\u0000"+
		"\u000078\u0006\b\u0000\u00008\u0012\u0001\u0000\u0000\u00009:\u0005/\u0000"+
		"\u0000:;\u0005/\u0000\u0000;?\u0001\u0000\u0000\u0000<>\b\u0001\u0000"+
		"\u0000=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000"+
		"\u0000\u0000?@\u0001\u0000\u0000\u0000@B\u0001\u0000\u0000\u0000A?\u0001"+
		"\u0000\u0000\u0000BC\u0006\t\u0000\u0000C\u0014\u0001\u0000\u0000\u0000"+
		"DE\u0005/\u0000\u0000EF\u0005*\u0000\u0000FJ\u0001\u0000\u0000\u0000G"+
		"I\t\u0000\u0000\u0000HG\u0001\u0000\u0000\u0000IL\u0001\u0000\u0000\u0000"+
		"JK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KM\u0001\u0000\u0000"+
		"\u0000LJ\u0001\u0000\u0000\u0000MN\u0005*\u0000\u0000NO\u0005/\u0000\u0000"+
		"OP\u0001\u0000\u0000\u0000PQ\u0006\n\u0000\u0000Q\u0016\u0001\u0000\u0000"+
		"\u0000RS\u0005c\u0000\u0000ST\u0005o\u0000\u0000TU\u0005n\u0000\u0000"+
		"UV\u0005f\u0000\u0000VW\u0005i\u0000\u0000WX\u0005g\u0000\u0000X\u0018"+
		"\u0001\u0000\u0000\u0000YZ\u0005m\u0000\u0000Z[\u0005o\u0000\u0000[\\"+
		"\u0005d\u0000\u0000\\]\u0005e\u0000\u0000]^\u0005l\u0000\u0000^\u001a"+
		"\u0001\u0000\u0000\u0000_`\u0005e\u0000\u0000`a\u0005n\u0000\u0000ab\u0005"+
		"u\u0000\u0000bc\u0005m\u0000\u0000c\u001c\u0001\u0000\u0000\u0000dh\u0007"+
		"\u0002\u0000\u0000eg\u0007\u0003\u0000\u0000fe\u0001\u0000\u0000\u0000"+
		"gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000"+
		"\u0000i\u001e\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000ko\u0005"+
		"\"\u0000\u0000ln\b\u0004\u0000\u0000ml\u0001\u0000\u0000\u0000nq\u0001"+
		"\u0000\u0000\u0000om\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000"+
		"pr\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000rs\u0005\"\u0000\u0000"+
		"s \u0001\u0000\u0000\u0000\u0006\u00005?Jho\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}