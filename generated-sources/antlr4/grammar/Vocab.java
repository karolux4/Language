// Generated from Vocab.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Vocab extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, INT=2, ELSE=3, FALSE=4, FORK=5, IF=6, JOIN=7, PRINT=8, PICKLE=9, 
		CANNON=10, SHARED=11, SYNC=12, TRUE=13, WHILE=14, AND=15, OR=16, ASSIGN=17, 
		COMMA=18, EQ=19, GE=20, GT=21, LE=22, LBRACE=23, LPAR=24, LSQ=25, LT=26, 
		MINUS=27, NE=28, NOT=29, PLUS=30, RBRACE=31, RPAR=32, RSQ=33, SEMI=34, 
		SLASH=35, STAR=36, ID=37, NUM=38, WS=39;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BOOL", "INT", "ELSE", "FALSE", "FORK", "IF", "JOIN", "PRINT", "PICKLE", 
			"CANNON", "SHARED", "SYNC", "TRUE", "WHILE", "AND", "OR", "ASSIGN", "COMMA", 
			"EQ", "GE", "GT", "LE", "LBRACE", "LPAR", "LSQ", "LT", "MINUS", "NE", 
			"NOT", "PLUS", "RBRACE", "RPAR", "RSQ", "SEMI", "SLASH", "STAR", "ID", 
			"NUM", "LETTER", "DIGIT", "WS", "A", "B", "C", "D", "E", "F", "G", "H", 
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", 
			"W", "X", "Y", "Z"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "'&&'", "'||'", "'='", "','", "'=='", "'>='", "'>'", 
			"'<='", "'{'", "'('", "'['", "'<'", "'-'", "'!='", "'!'", "'+'", "'}'", 
			"')'", "']'", "';'", "'/'", "'*'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BOOL", "INT", "ELSE", "FALSE", "FORK", "IF", "JOIN", "PRINT", 
			"PICKLE", "CANNON", "SHARED", "SYNC", "TRUE", "WHILE", "AND", "OR", "ASSIGN", 
			"COMMA", "EQ", "GE", "GT", "LE", "LBRACE", "LPAR", "LSQ", "LT", "MINUS", 
			"NE", "NOT", "PLUS", "RBRACE", "RPAR", "RSQ", "SEMI", "SLASH", "STAR", 
			"ID", "NUM", "WS"
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


	public Vocab(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Vocab.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2)\u0152\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\3\2\3\2\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3"+
		" \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3&\7&\u010b\n&\f&\16&\u010e\13"+
		"&\3\'\3\'\7\'\u0112\n\'\f\'\16\'\u0115\13\'\3(\3(\3)\3)\3*\3*\3*\3*\3"+
		"+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3"+
		"\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3"+
		"=\3=\3>\3>\3?\3?\3@\3@\3A\3A\3B\3B\3C\3C\3D\3D\2\2E\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'"+
		"M(O\2Q\2S)U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2"+
		"}\2\177\2\u0081\2\u0083\2\u0085\2\u0087\2\3\2\37\4\2C\\c|\3\2\62;\5\2"+
		"\13\f\17\17\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2II"+
		"ii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2"+
		"RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4"+
		"\2[[{{\4\2\\\\||\2\u0138\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2S\3\2\2\2\3"+
		"\u0089\3\2\2\2\5\u008e\3\2\2\2\7\u0092\3\2\2\2\t\u0097\3\2\2\2\13\u009d"+
		"\3\2\2\2\r\u00a2\3\2\2\2\17\u00a5\3\2\2\2\21\u00aa\3\2\2\2\23\u00b0\3"+
		"\2\2\2\25\u00b7\3\2\2\2\27\u00be\3\2\2\2\31\u00c5\3\2\2\2\33\u00ca\3\2"+
		"\2\2\35\u00cf\3\2\2\2\37\u00d5\3\2\2\2!\u00d8\3\2\2\2#\u00db\3\2\2\2%"+
		"\u00dd\3\2\2\2\'\u00df\3\2\2\2)\u00e2\3\2\2\2+\u00e5\3\2\2\2-\u00e7\3"+
		"\2\2\2/\u00ea\3\2\2\2\61\u00ec\3\2\2\2\63\u00ee\3\2\2\2\65\u00f0\3\2\2"+
		"\2\67\u00f2\3\2\2\29\u00f4\3\2\2\2;\u00f7\3\2\2\2=\u00f9\3\2\2\2?\u00fb"+
		"\3\2\2\2A\u00fd\3\2\2\2C\u00ff\3\2\2\2E\u0101\3\2\2\2G\u0103\3\2\2\2I"+
		"\u0105\3\2\2\2K\u0107\3\2\2\2M\u010f\3\2\2\2O\u0116\3\2\2\2Q\u0118\3\2"+
		"\2\2S\u011a\3\2\2\2U\u011e\3\2\2\2W\u0120\3\2\2\2Y\u0122\3\2\2\2[\u0124"+
		"\3\2\2\2]\u0126\3\2\2\2_\u0128\3\2\2\2a\u012a\3\2\2\2c\u012c\3\2\2\2e"+
		"\u012e\3\2\2\2g\u0130\3\2\2\2i\u0132\3\2\2\2k\u0134\3\2\2\2m\u0136\3\2"+
		"\2\2o\u0138\3\2\2\2q\u013a\3\2\2\2s\u013c\3\2\2\2u\u013e\3\2\2\2w\u0140"+
		"\3\2\2\2y\u0142\3\2\2\2{\u0144\3\2\2\2}\u0146\3\2\2\2\177\u0148\3\2\2"+
		"\2\u0081\u014a\3\2\2\2\u0083\u014c\3\2\2\2\u0085\u014e\3\2\2\2\u0087\u0150"+
		"\3\2\2\2\u0089\u008a\5W,\2\u008a\u008b\5q9\2\u008b\u008c\5q9\2\u008c\u008d"+
		"\5k\66\2\u008d\4\3\2\2\2\u008e\u008f\5e\63\2\u008f\u0090\5o8\2\u0090\u0091"+
		"\5{>\2\u0091\6\3\2\2\2\u0092\u0093\5]/\2\u0093\u0094\5k\66\2\u0094\u0095"+
		"\5y=\2\u0095\u0096\5]/\2\u0096\b\3\2\2\2\u0097\u0098\5_\60\2\u0098\u0099"+
		"\5U+\2\u0099\u009a\5k\66\2\u009a\u009b\5y=\2\u009b\u009c\5]/\2\u009c\n"+
		"\3\2\2\2\u009d\u009e\5_\60\2\u009e\u009f\5q9\2\u009f\u00a0\5w<\2\u00a0"+
		"\u00a1\5i\65\2\u00a1\f\3\2\2\2\u00a2\u00a3\5e\63\2\u00a3\u00a4\5_\60\2"+
		"\u00a4\16\3\2\2\2\u00a5\u00a6\5g\64\2\u00a6\u00a7\5q9\2\u00a7\u00a8\5"+
		"e\63\2\u00a8\u00a9\5o8\2\u00a9\20\3\2\2\2\u00aa\u00ab\5s:\2\u00ab\u00ac"+
		"\5w<\2\u00ac\u00ad\5e\63\2\u00ad\u00ae\5o8\2\u00ae\u00af\5{>\2\u00af\22"+
		"\3\2\2\2\u00b0\u00b1\5s:\2\u00b1\u00b2\5e\63\2\u00b2\u00b3\5Y-\2\u00b3"+
		"\u00b4\5i\65\2\u00b4\u00b5\5k\66\2\u00b5\u00b6\5]/\2\u00b6\24\3\2\2\2"+
		"\u00b7\u00b8\5Y-\2\u00b8\u00b9\5U+\2\u00b9\u00ba\5o8\2\u00ba\u00bb\5o"+
		"8\2\u00bb\u00bc\5q9\2\u00bc\u00bd\5o8\2\u00bd\26\3\2\2\2\u00be\u00bf\5"+
		"y=\2\u00bf\u00c0\5c\62\2\u00c0\u00c1\5U+\2\u00c1\u00c2\5w<\2\u00c2\u00c3"+
		"\5]/\2\u00c3\u00c4\5[.\2\u00c4\30\3\2\2\2\u00c5\u00c6\5y=\2\u00c6\u00c7"+
		"\5\u0085C\2\u00c7\u00c8\5o8\2\u00c8\u00c9\5Y-\2\u00c9\32\3\2\2\2\u00ca"+
		"\u00cb\5{>\2\u00cb\u00cc\5w<\2\u00cc\u00cd\5}?\2\u00cd\u00ce\5]/\2\u00ce"+
		"\34\3\2\2\2\u00cf\u00d0\5\u0081A\2\u00d0\u00d1\5c\62\2\u00d1\u00d2\5e"+
		"\63\2\u00d2\u00d3\5k\66\2\u00d3\u00d4\5]/\2\u00d4\36\3\2\2\2\u00d5\u00d6"+
		"\7(\2\2\u00d6\u00d7\7(\2\2\u00d7 \3\2\2\2\u00d8\u00d9\7~\2\2\u00d9\u00da"+
		"\7~\2\2\u00da\"\3\2\2\2\u00db\u00dc\7?\2\2\u00dc$\3\2\2\2\u00dd\u00de"+
		"\7.\2\2\u00de&\3\2\2\2\u00df\u00e0\7?\2\2\u00e0\u00e1\7?\2\2\u00e1(\3"+
		"\2\2\2\u00e2\u00e3\7@\2\2\u00e3\u00e4\7?\2\2\u00e4*\3\2\2\2\u00e5\u00e6"+
		"\7@\2\2\u00e6,\3\2\2\2\u00e7\u00e8\7>\2\2\u00e8\u00e9\7?\2\2\u00e9.\3"+
		"\2\2\2\u00ea\u00eb\7}\2\2\u00eb\60\3\2\2\2\u00ec\u00ed\7*\2\2\u00ed\62"+
		"\3\2\2\2\u00ee\u00ef\7]\2\2\u00ef\64\3\2\2\2\u00f0\u00f1\7>\2\2\u00f1"+
		"\66\3\2\2\2\u00f2\u00f3\7/\2\2\u00f38\3\2\2\2\u00f4\u00f5\7#\2\2\u00f5"+
		"\u00f6\7?\2\2\u00f6:\3\2\2\2\u00f7\u00f8\7#\2\2\u00f8<\3\2\2\2\u00f9\u00fa"+
		"\7-\2\2\u00fa>\3\2\2\2\u00fb\u00fc\7\177\2\2\u00fc@\3\2\2\2\u00fd\u00fe"+
		"\7+\2\2\u00feB\3\2\2\2\u00ff\u0100\7_\2\2\u0100D\3\2\2\2\u0101\u0102\7"+
		"=\2\2\u0102F\3\2\2\2\u0103\u0104\7\61\2\2\u0104H\3\2\2\2\u0105\u0106\7"+
		",\2\2\u0106J\3\2\2\2\u0107\u010c\5O(\2\u0108\u010b\5O(\2\u0109\u010b\5"+
		"Q)\2\u010a\u0108\3\2\2\2\u010a\u0109\3\2\2\2\u010b\u010e\3\2\2\2\u010c"+
		"\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010dL\3\2\2\2\u010e\u010c\3\2\2\2"+
		"\u010f\u0113\5Q)\2\u0110\u0112\5Q)\2\u0111\u0110\3\2\2\2\u0112\u0115\3"+
		"\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114N\3\2\2\2\u0115\u0113"+
		"\3\2\2\2\u0116\u0117\t\2\2\2\u0117P\3\2\2\2\u0118\u0119\t\3\2\2\u0119"+
		"R\3\2\2\2\u011a\u011b\t\4\2\2\u011b\u011c\3\2\2\2\u011c\u011d\b*\2\2\u011d"+
		"T\3\2\2\2\u011e\u011f\t\5\2\2\u011fV\3\2\2\2\u0120\u0121\t\6\2\2\u0121"+
		"X\3\2\2\2\u0122\u0123\t\7\2\2\u0123Z\3\2\2\2\u0124\u0125\t\b\2\2\u0125"+
		"\\\3\2\2\2\u0126\u0127\t\t\2\2\u0127^\3\2\2\2\u0128\u0129\t\n\2\2\u0129"+
		"`\3\2\2\2\u012a\u012b\t\13\2\2\u012bb\3\2\2\2\u012c\u012d\t\f\2\2\u012d"+
		"d\3\2\2\2\u012e\u012f\t\r\2\2\u012ff\3\2\2\2\u0130\u0131\t\16\2\2\u0131"+
		"h\3\2\2\2\u0132\u0133\t\17\2\2\u0133j\3\2\2\2\u0134\u0135\t\20\2\2\u0135"+
		"l\3\2\2\2\u0136\u0137\t\21\2\2\u0137n\3\2\2\2\u0138\u0139\t\22\2\2\u0139"+
		"p\3\2\2\2\u013a\u013b\t\23\2\2\u013br\3\2\2\2\u013c\u013d\t\24\2\2\u013d"+
		"t\3\2\2\2\u013e\u013f\t\25\2\2\u013fv\3\2\2\2\u0140\u0141\t\26\2\2\u0141"+
		"x\3\2\2\2\u0142\u0143\t\27\2\2\u0143z\3\2\2\2\u0144\u0145\t\30\2\2\u0145"+
		"|\3\2\2\2\u0146\u0147\t\31\2\2\u0147~\3\2\2\2\u0148\u0149\t\32\2\2\u0149"+
		"\u0080\3\2\2\2\u014a\u014b\t\33\2\2\u014b\u0082\3\2\2\2\u014c\u014d\t"+
		"\34\2\2\u014d\u0084\3\2\2\2\u014e\u014f\t\35\2\2\u014f\u0086\3\2\2\2\u0150"+
		"\u0151\t\36\2\2\u0151\u0088\3\2\2\2\6\2\u010a\u010c\u0113\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}