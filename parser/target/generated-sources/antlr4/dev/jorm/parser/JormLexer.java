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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		WHITESPACE=10, LINE_COMMENT=11, BLOCK_COMMENT=12, CONFIG=13, MODEL=14, 
		ENUM=15, ID=16, STRING_LIT=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"WHITESPACE", "LINE_COMMENT", "BLOCK_COMMENT", "CONFIG", "MODEL", "ENUM", 
			"ID", "STRING_LIT"
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
		"\u0004\u0000\u0011x\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0004\t8\b\t\u000b"+
		"\t\f\t9\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0005\nB\b\n\n"+
		"\n\f\nE\t\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0005\u000bM\b\u000b\n\u000b\f\u000bP\t\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0005\u000fk\b\u000f\n\u000f\f\u000fn\t\u000f\u0001\u0010"+
		"\u0001\u0010\u0005\u0010r\b\u0010\n\u0010\f\u0010u\t\u0010\u0001\u0010"+
		"\u0001\u0010\u0001N\u0000\u0011\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011\u0001\u0000"+
		"\u0005\u0003\u0000\t\n\r\r  \u0002\u0000\n\n\r\r\u0003\u0000AZ__az\u0004"+
		"\u000009AZ__az\u0001\u0000\"\"|\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0001#\u0001\u0000\u0000"+
		"\u0000\u0003%\u0001\u0000\u0000\u0000\u0005\'\u0001\u0000\u0000\u0000"+
		"\u0007)\u0001\u0000\u0000\u0000\t+\u0001\u0000\u0000\u0000\u000b.\u0001"+
		"\u0000\u0000\u0000\r0\u0001\u0000\u0000\u0000\u000f2\u0001\u0000\u0000"+
		"\u0000\u00114\u0001\u0000\u0000\u0000\u00137\u0001\u0000\u0000\u0000\u0015"+
		"=\u0001\u0000\u0000\u0000\u0017H\u0001\u0000\u0000\u0000\u0019V\u0001"+
		"\u0000\u0000\u0000\u001b]\u0001\u0000\u0000\u0000\u001dc\u0001\u0000\u0000"+
		"\u0000\u001fh\u0001\u0000\u0000\u0000!o\u0001\u0000\u0000\u0000#$\u0005"+
		"{\u0000\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005}\u0000\u0000&\u0004"+
		"\u0001\u0000\u0000\u0000\'(\u0005=\u0000\u0000(\u0006\u0001\u0000\u0000"+
		"\u0000)*\u0005.\u0000\u0000*\b\u0001\u0000\u0000\u0000+,\u0005[\u0000"+
		"\u0000,-\u0005]\u0000\u0000-\n\u0001\u0000\u0000\u0000./\u0005?\u0000"+
		"\u0000/\f\u0001\u0000\u0000\u000001\u0005@\u0000\u00001\u000e\u0001\u0000"+
		"\u0000\u000023\u0005(\u0000\u00003\u0010\u0001\u0000\u0000\u000045\u0005"+
		")\u0000\u00005\u0012\u0001\u0000\u0000\u000068\u0007\u0000\u0000\u0000"+
		"76\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u000097\u0001\u0000\u0000"+
		"\u00009:\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;<\u0006\t\u0000"+
		"\u0000<\u0014\u0001\u0000\u0000\u0000=>\u0005/\u0000\u0000>?\u0005/\u0000"+
		"\u0000?C\u0001\u0000\u0000\u0000@B\b\u0001\u0000\u0000A@\u0001\u0000\u0000"+
		"\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000DF\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000FG\u0006"+
		"\n\u0000\u0000G\u0016\u0001\u0000\u0000\u0000HI\u0005/\u0000\u0000IJ\u0005"+
		"*\u0000\u0000JN\u0001\u0000\u0000\u0000KM\t\u0000\u0000\u0000LK\u0001"+
		"\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000"+
		"NL\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000"+
		"\u0000QR\u0005*\u0000\u0000RS\u0005/\u0000\u0000ST\u0001\u0000\u0000\u0000"+
		"TU\u0006\u000b\u0000\u0000U\u0018\u0001\u0000\u0000\u0000VW\u0005c\u0000"+
		"\u0000WX\u0005o\u0000\u0000XY\u0005n\u0000\u0000YZ\u0005f\u0000\u0000"+
		"Z[\u0005i\u0000\u0000[\\\u0005g\u0000\u0000\\\u001a\u0001\u0000\u0000"+
		"\u0000]^\u0005m\u0000\u0000^_\u0005o\u0000\u0000_`\u0005d\u0000\u0000"+
		"`a\u0005e\u0000\u0000ab\u0005l\u0000\u0000b\u001c\u0001\u0000\u0000\u0000"+
		"cd\u0005e\u0000\u0000de\u0005n\u0000\u0000ef\u0005u\u0000\u0000fg\u0005"+
		"m\u0000\u0000g\u001e\u0001\u0000\u0000\u0000hl\u0007\u0002\u0000\u0000"+
		"ik\u0007\u0003\u0000\u0000ji\u0001\u0000\u0000\u0000kn\u0001\u0000\u0000"+
		"\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000m \u0001\u0000"+
		"\u0000\u0000nl\u0001\u0000\u0000\u0000os\u0005\"\u0000\u0000pr\b\u0004"+
		"\u0000\u0000qp\u0001\u0000\u0000\u0000ru\u0001\u0000\u0000\u0000sq\u0001"+
		"\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tv\u0001\u0000\u0000\u0000"+
		"us\u0001\u0000\u0000\u0000vw\u0005\"\u0000\u0000w\"\u0001\u0000\u0000"+
		"\u0000\u0006\u00009CNls\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}