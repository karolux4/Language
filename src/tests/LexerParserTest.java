package tests;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import pp.block3.cc.antlr.MyErrorListener;
import pp.block3.cc.antlr.TabularLexer;
import pp.block3.cc.antlr.TabularParser;

public class LexerParserTest {
	
	@Test
	public void testLexerParser() {
		
		
	}
	
	public void parse(String path) throws IOException {
		Lexer lexer = new PickleCannonLexer(CharStreams.fromFileName(path));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new MyErrorListener());
		TabularParser parser = new TabularParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new MyErrorListener());
		ParseTree tree = parser.table();
		if (((MyErrorListener) (lexer.getErrorListeners().get(0))).getErrors() == null
				&& ((MyErrorListener) (parser.getErrorListeners().get(0))).getErrors() == null) {
			htmlConversion(path, tree);
		}
	}
}
