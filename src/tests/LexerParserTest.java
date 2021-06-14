package tests;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import compiler.ErrorListener;
import grammar.PickleCannonLexer;
import grammar.PickleCannonParser;

public class LexerParserTest {
	
	@Test
	public void testLexerParser() {
		try {
			parse("src/sample/fork.pickle");
			parse("src/sample/proc.pickle");
		} catch (IOException e) {
			Assert.fail("Throws IOException");
		}
	}
	
	public void parse(String path) throws IOException {
		Lexer lexer = new PickleCannonLexer(CharStreams.fromFileName(path));
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ErrorListener());
		PickleCannonParser parser = new PickleCannonParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ErrorListener());
		ParseTree tree = parser.program();
		if (((ErrorListener) (lexer.getErrorListeners().get(0))).getErrors().size()==0
				&& ((ErrorListener) (parser.getErrorListeners().get(0))).getErrors().size()==0) {
			System.out.println("No errors");
		}
		else {
			System.out.println("Lexer errors: "+((ErrorListener) (lexer.getErrorListeners().get(0))).getErrors());
			System.out.println("Parser errors: "+((ErrorListener) (parser.getErrorListeners().get(0))).getErrors());
		}
	}
}
