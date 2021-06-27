package compiler;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import checker.Checker;
import checker.Result;
import generator.Generator;
import generator.Program;
import grammar.PickleCannonLexer;
import grammar.PickleCannonParser;

public class Compiler {
	/** The singleton instance of this class. */
	private final static Compiler instance = new Compiler();
	/** Debug flag. */
	private final static boolean SHOW = true;

	/** Returns the singleton instance of this class. */
	public static Compiler instance() {
		return instance;
	}

	/** Compiles and runs the program named in the argument. */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: filename");
			return;
		}
		try {
			System.out.println("--- Running " + args[0]);
			Program p = instance.compile(new File(args[0]));
			p.writeToFile("sizeTest");
			System.out.println("--- Done with " + args[0]);
		} catch (ParseException exc) {
			exc.print();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	/** The fixed checker of this compiler. */
	private final Checker checker;
	/** The fixed generator of this compiler. */
	private final Generator generator;

	private Compiler() {
		this.checker = new Checker();
		this.generator = new Generator();
	}

	/** Typechecks a given Simple Pascal string. */

	public Result check(String text) throws ParseException {
		return check(parse(text));
	}

	/** Typechecks a given Simple Pascal file. */

	public Result check(File file) throws ParseException, IOException {
		return check(parse(file));
	}

	/** Typechecks a given Simple Pascal parse tree. */

	public Result check(ParseTree tree) throws ParseException {
		return this.checker.check(tree);
	}

	/** Compiles a given Simple Pascal string into an SPRIL program. */

	public Program compile(String text) throws ParseException {
		return compile(parse(text));
	}

	/** Compiles a given Simple Pascal file into an SPRIL program. */

	public Program compile(File file) throws ParseException, IOException {
		return compile(parse(file));
	}

	/** Compiles a given Simple Pascal parse tree into an SPRIL program. */

	public Program compile(ParseTree tree) throws ParseException {
		Result checkResult = this.checker.check(tree);
		return this.generator.generate(tree, checkResult);
	}

	/** Compiles a given Simple Pascal string into a parse tree. */
	public ParseTree parse(String text) throws ParseException {
		return parse(CharStreams.fromString(text));
	}

	/** Compiles a given Simple Pascal string into a parse tree. */
	public ParseTree parse(File file) throws ParseException, IOException {
		return parse(CharStreams.fromPath(file.toPath()));
	}

	private ParseTree parse(CharStream chars) throws ParseException {
		ErrorListener listener = new ErrorListener();
		Lexer lexer = new PickleCannonLexer(chars);
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		TokenStream tokens = new CommonTokenStream(lexer);
		PickleCannonParser parser = new PickleCannonParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		ParseTree result = parser.program();
		listener.throwException();
		return result;
	}
}
