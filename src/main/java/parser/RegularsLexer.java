// Generated from /home/mateusz/Dropbox/semVI/TK/tk-projekt/src/main/antlr4/Regulars.g4 by ANTLR 4.2.2
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RegularsLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__15=1, T__14=2, T__13=3, T__12=4, T__11=5, T__10=6, T__9=7, T__8=8, 
		T__7=9, T__6=10, T__5=11, T__4=12, T__3=13, T__2=14, T__1=15, T__0=16, 
		NUMBER=17, ANONMETACHARACTER=18, METACHARACTER=19, WS=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "'.'", "')'", "','", "'+'", "'['", "'*'", "'-'", "'('", "'[^'", 
		"'?'", "'\\'", "'{'", "'}'", "'|'", "'$'", "NUMBER", "ANONMETACHARACTER", 
		"METACHARACTER", "WS"
	};
	public static final String[] ruleNames = {
		"T__15", "T__14", "T__13", "T__12", "T__11", "T__10", "T__9", "T__8", 
		"T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "NUMBER", 
		"ANONMETACHARACTER", "METACHARACTER", "WS"
	};


	public RegularsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Regulars.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26T\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3"+
		"\25\2\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26\3\2\6\3\2\62;\5\2\62;C\\c|\7\2*-"+
		"AA]]__~~\4\2\f\f\"\"S\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2"+
		"\2\5-\3\2\2\2\7/\3\2\2\2\t\61\3\2\2\2\13\63\3\2\2\2\r\65\3\2\2\2\17\67"+
		"\3\2\2\2\219\3\2\2\2\23;\3\2\2\2\25=\3\2\2\2\27@\3\2\2\2\31B\3\2\2\2\33"+
		"D\3\2\2\2\35F\3\2\2\2\37H\3\2\2\2!J\3\2\2\2#L\3\2\2\2%N\3\2\2\2\'P\3\2"+
		"\2\2)R\3\2\2\2+,\7_\2\2,\4\3\2\2\2-.\7\60\2\2.\6\3\2\2\2/\60\7+\2\2\60"+
		"\b\3\2\2\2\61\62\7.\2\2\62\n\3\2\2\2\63\64\7-\2\2\64\f\3\2\2\2\65\66\7"+
		"]\2\2\66\16\3\2\2\2\678\7,\2\28\20\3\2\2\29:\7/\2\2:\22\3\2\2\2;<\7*\2"+
		"\2<\24\3\2\2\2=>\7]\2\2>?\7`\2\2?\26\3\2\2\2@A\7A\2\2A\30\3\2\2\2BC\7"+
		"^\2\2C\32\3\2\2\2DE\7}\2\2E\34\3\2\2\2FG\7\177\2\2G\36\3\2\2\2HI\7~\2"+
		"\2I \3\2\2\2JK\7&\2\2K\"\3\2\2\2LM\t\2\2\2M$\3\2\2\2NO\t\3\2\2O&\3\2\2"+
		"\2PQ\t\4\2\2Q(\3\2\2\2RS\t\5\2\2S*\3\2\2\2\3\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}