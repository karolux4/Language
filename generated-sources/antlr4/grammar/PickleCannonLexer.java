// Generated from PickleCannon.g4 by ANTLR 4.9.2
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
public class PickleCannonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, INT=2, ELSE=3, FALSE=4, FORK=5, IF=6, JOIN=7, PRINT=8, PICKLE=9, 
		CANNON=10, SYNC=11, TRUE=12, WHILE=13, AND=14, OR=15, ASSIGN=16, COMMA=17, 
		EQ=18, GE=19, GT=20, LE=21, LBRACE=22, LPAR=23, LSQ=24, LT=25, MINUS=26, 
		NE=27, NOT=28, PLUS=29, RBRACE=30, RPAR=31, RSQ=32, SEMI=33, SLASH=34, 
		STAR=35, ID=36, NUM=37, WS=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BOOL", "INT", "ELSE", "FALSE", "FORK", "IF", "JOIN", "PRINT", "PICKLE", 
			"CANNON", "SYNC", "TRUE", "WHILE", "AND", "OR", "ASSIGN", "COMMA", "EQ", 
			"GE", "GT", "LE", "LBRACE", "LPAR", "LSQ", "LT", "MINUS", "NE", "NOT", 
			"PLUS", "RBRACE", "RPAR", "RSQ", "SEMI", "SLASH", "STAR", "ID", "NUM", 
			"LETTER", "DIGIT", "WS", "A", "B", "C", "D", "E", "F", "G", "H", "I", 
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", 
			"X", "Y", "Z"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "'&&'", "'||'", "'='", "','", "'=='", "'>='", "'>'", "'<='", 
			"'{'", "'('", "'['", "'<'", "'-'", "'!='", "'!'", "'+'", "'}'", "')'", 
			"']'", "';'", "'/'", "'*'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BOOL", "INT", "ELSE", "FALSE", "FORK", "IF", "JOIN", "PRINT", 
			"PICKLE", "CANNON", "SYNC", "TRUE", "WHILE", "AND", "OR", "ASSIGN", "COMMA", 
			"EQ", "GE", "GT", "LE", "LBRACE", "LPAR", "LSQ", "LT", "MINUS", "NE", 
			"NOT", "PLUS", "RBRACE", "RPAR", "RSQ", "SEMI", "SLASH", "STAR", "ID", 
			"NUM", "WS"
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


	public PickleCannonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PickleCannon.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u0149\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3"+
		"%\3%\3%\7%\u0102\n%\f%\16%\u0105\13%\3&\3&\7&\u0109\n&\f&\16&\u010c\13"+
		"&\3\'\3\'\3(\3(\3)\3)\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3"+
		"\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3"+
		"\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3A\3B\3B"+
		"\3C\3C\2\2D\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$G%I&K\'M\2O\2Q(S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\2"+
		"i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2\u0085\2\3\2\37"+
		"\4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4"+
		"\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOo"+
		"o\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2"+
		"XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\2\u012f\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2"+
		"\2Q\3\2\2\2\3\u0087\3\2\2\2\5\u008c\3\2\2\2\7\u0090\3\2\2\2\t\u0095\3"+
		"\2\2\2\13\u009b\3\2\2\2\r\u00a0\3\2\2\2\17\u00a3\3\2\2\2\21\u00a8\3\2"+
		"\2\2\23\u00ae\3\2\2\2\25\u00b5\3\2\2\2\27\u00bc\3\2\2\2\31\u00c1\3\2\2"+
		"\2\33\u00c6\3\2\2\2\35\u00cc\3\2\2\2\37\u00cf\3\2\2\2!\u00d2\3\2\2\2#"+
		"\u00d4\3\2\2\2%\u00d6\3\2\2\2\'\u00d9\3\2\2\2)\u00dc\3\2\2\2+\u00de\3"+
		"\2\2\2-\u00e1\3\2\2\2/\u00e3\3\2\2\2\61\u00e5\3\2\2\2\63\u00e7\3\2\2\2"+
		"\65\u00e9\3\2\2\2\67\u00eb\3\2\2\29\u00ee\3\2\2\2;\u00f0\3\2\2\2=\u00f2"+
		"\3\2\2\2?\u00f4\3\2\2\2A\u00f6\3\2\2\2C\u00f8\3\2\2\2E\u00fa\3\2\2\2G"+
		"\u00fc\3\2\2\2I\u00fe\3\2\2\2K\u0106\3\2\2\2M\u010d\3\2\2\2O\u010f\3\2"+
		"\2\2Q\u0111\3\2\2\2S\u0115\3\2\2\2U\u0117\3\2\2\2W\u0119\3\2\2\2Y\u011b"+
		"\3\2\2\2[\u011d\3\2\2\2]\u011f\3\2\2\2_\u0121\3\2\2\2a\u0123\3\2\2\2c"+
		"\u0125\3\2\2\2e\u0127\3\2\2\2g\u0129\3\2\2\2i\u012b\3\2\2\2k\u012d\3\2"+
		"\2\2m\u012f\3\2\2\2o\u0131\3\2\2\2q\u0133\3\2\2\2s\u0135\3\2\2\2u\u0137"+
		"\3\2\2\2w\u0139\3\2\2\2y\u013b\3\2\2\2{\u013d\3\2\2\2}\u013f\3\2\2\2\177"+
		"\u0141\3\2\2\2\u0081\u0143\3\2\2\2\u0083\u0145\3\2\2\2\u0085\u0147\3\2"+
		"\2\2\u0087\u0088\5U+\2\u0088\u0089\5o8\2\u0089\u008a\5o8\2\u008a\u008b"+
		"\5i\65\2\u008b\4\3\2\2\2\u008c\u008d\5c\62\2\u008d\u008e\5m\67\2\u008e"+
		"\u008f\5y=\2\u008f\6\3\2\2\2\u0090\u0091\5[.\2\u0091\u0092\5i\65\2\u0092"+
		"\u0093\5w<\2\u0093\u0094\5[.\2\u0094\b\3\2\2\2\u0095\u0096\5]/\2\u0096"+
		"\u0097\5S*\2\u0097\u0098\5i\65\2\u0098\u0099\5w<\2\u0099\u009a\5[.\2\u009a"+
		"\n\3\2\2\2\u009b\u009c\5]/\2\u009c\u009d\5o8\2\u009d\u009e\5u;\2\u009e"+
		"\u009f\5g\64\2\u009f\f\3\2\2\2\u00a0\u00a1\5c\62\2\u00a1\u00a2\5]/\2\u00a2"+
		"\16\3\2\2\2\u00a3\u00a4\5e\63\2\u00a4\u00a5\5o8\2\u00a5\u00a6\5c\62\2"+
		"\u00a6\u00a7\5m\67\2\u00a7\20\3\2\2\2\u00a8\u00a9\5q9\2\u00a9\u00aa\5"+
		"u;\2\u00aa\u00ab\5c\62\2\u00ab\u00ac\5m\67\2\u00ac\u00ad\5y=\2\u00ad\22"+
		"\3\2\2\2\u00ae\u00af\5q9\2\u00af\u00b0\5c\62\2\u00b0\u00b1\5W,\2\u00b1"+
		"\u00b2\5g\64\2\u00b2\u00b3\5i\65\2\u00b3\u00b4\5[.\2\u00b4\24\3\2\2\2"+
		"\u00b5\u00b6\5W,\2\u00b6\u00b7\5S*\2\u00b7\u00b8\5m\67\2\u00b8\u00b9\5"+
		"m\67\2\u00b9\u00ba\5o8\2\u00ba\u00bb\5m\67\2\u00bb\26\3\2\2\2\u00bc\u00bd"+
		"\5w<\2\u00bd\u00be\5\u0083B\2\u00be\u00bf\5m\67\2\u00bf\u00c0\5W,\2\u00c0"+
		"\30\3\2\2\2\u00c1\u00c2\5y=\2\u00c2\u00c3\5u;\2\u00c3\u00c4\5{>\2\u00c4"+
		"\u00c5\5[.\2\u00c5\32\3\2\2\2\u00c6\u00c7\5\177@\2\u00c7\u00c8\5a\61\2"+
		"\u00c8\u00c9\5c\62\2\u00c9\u00ca\5i\65\2\u00ca\u00cb\5[.\2\u00cb\34\3"+
		"\2\2\2\u00cc\u00cd\7(\2\2\u00cd\u00ce\7(\2\2\u00ce\36\3\2\2\2\u00cf\u00d0"+
		"\7~\2\2\u00d0\u00d1\7~\2\2\u00d1 \3\2\2\2\u00d2\u00d3\7?\2\2\u00d3\"\3"+
		"\2\2\2\u00d4\u00d5\7.\2\2\u00d5$\3\2\2\2\u00d6\u00d7\7?\2\2\u00d7\u00d8"+
		"\7?\2\2\u00d8&\3\2\2\2\u00d9\u00da\7@\2\2\u00da\u00db\7?\2\2\u00db(\3"+
		"\2\2\2\u00dc\u00dd\7@\2\2\u00dd*\3\2\2\2\u00de\u00df\7>\2\2\u00df\u00e0"+
		"\7?\2\2\u00e0,\3\2\2\2\u00e1\u00e2\7}\2\2\u00e2.\3\2\2\2\u00e3\u00e4\7"+
		"*\2\2\u00e4\60\3\2\2\2\u00e5\u00e6\7]\2\2\u00e6\62\3\2\2\2\u00e7\u00e8"+
		"\7>\2\2\u00e8\64\3\2\2\2\u00e9\u00ea\7/\2\2\u00ea\66\3\2\2\2\u00eb\u00ec"+
		"\7#\2\2\u00ec\u00ed\7?\2\2\u00ed8\3\2\2\2\u00ee\u00ef\7#\2\2\u00ef:\3"+
		"\2\2\2\u00f0\u00f1\7-\2\2\u00f1<\3\2\2\2\u00f2\u00f3\7\177\2\2\u00f3>"+
		"\3\2\2\2\u00f4\u00f5\7+\2\2\u00f5@\3\2\2\2\u00f6\u00f7\7_\2\2\u00f7B\3"+
		"\2\2\2\u00f8\u00f9\7=\2\2\u00f9D\3\2\2\2\u00fa\u00fb\7\61\2\2\u00fbF\3"+
		"\2\2\2\u00fc\u00fd\7,\2\2\u00fdH\3\2\2\2\u00fe\u0103\5M\'\2\u00ff\u0102"+
		"\5M\'\2\u0100\u0102\5O(\2\u0101\u00ff\3\2\2\2\u0101\u0100\3\2\2\2\u0102"+
		"\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104J\3\2\2\2"+
		"\u0105\u0103\3\2\2\2\u0106\u010a\5O(\2\u0107\u0109\5O(\2\u0108\u0107\3"+
		"\2\2\2\u0109\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b"+
		"L\3\2\2\2\u010c\u010a\3\2\2\2\u010d\u010e\t\2\2\2\u010eN\3\2\2\2\u010f"+
		"\u0110\t\3\2\2\u0110P\3\2\2\2\u0111\u0112\t\4\2\2\u0112\u0113\3\2\2\2"+
		"\u0113\u0114\b)\2\2\u0114R\3\2\2\2\u0115\u0116\t\5\2\2\u0116T\3\2\2\2"+
		"\u0117\u0118\t\6\2\2\u0118V\3\2\2\2\u0119\u011a\t\7\2\2\u011aX\3\2\2\2"+
		"\u011b\u011c\t\b\2\2\u011cZ\3\2\2\2\u011d\u011e\t\t\2\2\u011e\\\3\2\2"+
		"\2\u011f\u0120\t\n\2\2\u0120^\3\2\2\2\u0121\u0122\t\13\2\2\u0122`\3\2"+
		"\2\2\u0123\u0124\t\f\2\2\u0124b\3\2\2\2\u0125\u0126\t\r\2\2\u0126d\3\2"+
		"\2\2\u0127\u0128\t\16\2\2\u0128f\3\2\2\2\u0129\u012a\t\17\2\2\u012ah\3"+
		"\2\2\2\u012b\u012c\t\20\2\2\u012cj\3\2\2\2\u012d\u012e\t\21\2\2\u012e"+
		"l\3\2\2\2\u012f\u0130\t\22\2\2\u0130n\3\2\2\2\u0131\u0132\t\23\2\2\u0132"+
		"p\3\2\2\2\u0133\u0134\t\24\2\2\u0134r\3\2\2\2\u0135\u0136\t\25\2\2\u0136"+
		"t\3\2\2\2\u0137\u0138\t\26\2\2\u0138v\3\2\2\2\u0139\u013a\t\27\2\2\u013a"+
		"x\3\2\2\2\u013b\u013c\t\30\2\2\u013cz\3\2\2\2\u013d\u013e\t\31\2\2\u013e"+
		"|\3\2\2\2\u013f\u0140\t\32\2\2\u0140~\3\2\2\2\u0141\u0142\t\33\2\2\u0142"+
		"\u0080\3\2\2\2\u0143\u0144\t\34\2\2\u0144\u0082\3\2\2\2\u0145\u0146\t"+
		"\35\2\2\u0146\u0084\3\2\2\2\u0147\u0148\t\36\2\2\u0148\u0086\3\2\2\2\6"+
		"\2\u0101\u0103\u010a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}