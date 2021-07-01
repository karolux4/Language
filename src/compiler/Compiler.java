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

/**
 * The main class that is used to compile Pickle Cannon language code
 * 
 * @author Karolis Butkus
 *
 */
public class Compiler {
	/** The singleton instance of this class. */
	private final static Compiler instance = new Compiler();

	/** Returns the singleton instance of this class. */
	public static Compiler instance() {
		return instance;
	}

	/**
	 * Compiles the program specified in the argument and writes it in the
	 * src/output directory. If argument was not specified the
	 * src/sample/main.pickle program is compiled
	 * 
	 * @param args - the path to the compiled program
	 */
	public static void main(String[] args) {
		File file;
		if (args.length == 0) {
			file = new File("src/sample/main.pickle");
		} else if (args.length == 1) {
			file = new File(args[0]);
		} else {
			System.err.println("Usage: filename");
			return;
		}
		try {
			System.out.println("--- Running " + file.toString());
			Program p = instance.compile(file);
			String nameWithoutExt = file.getName().replaceFirst("[.][^.]+$", "");
			p.writeToFile(nameWithoutExt);
			System.out.println("--- Done with " + file.toString());
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

	/** Performs elaboration on Pickle Cannon language string. */

	public Result check(String text) throws ParseException {
		return check(parse(text));
	}

	/** Performs elaboration on Pickle Cannon language file. */

	public Result check(File file) throws ParseException, IOException {
		return check(parse(file));
	}

	/** Performs elaboration on Pickle Cannon language parse tree. */

	public Result check(ParseTree tree) throws ParseException {
		return this.checker.check(tree);
	}

	/** Compiles a given Pickle Cannon language string into an Sprockell program. */

	public Program compile(String text) throws ParseException {
		return compile(parse(text));
	}

	/** Compiles a given Pickle Cannon language file into an Sprockell program. */

	public Program compile(File file) throws ParseException, IOException {
		return compile(parse(file));
	}

	/**
	 * Compiles a given Pickle Cannon language parse tree into an Sprockell program.
	 */

	public Program compile(ParseTree tree) throws ParseException {
		Result checkResult = this.checker.check(tree);
		return this.generator.generate(tree, checkResult);
	}

	/** Parses a given Pickle Cannon language string into a parse tree. */
	public ParseTree parse(String text) throws ParseException {
		return parse(CharStreams.fromString(text));
	}

	/** Parses a given Pickle Cannon language string into a parse tree. */
	public ParseTree parse(File file) throws ParseException, IOException {
		return parse(CharStreams.fromPath(file.toPath()));
	}

	/** Parses given Pickle Cannon language character stream into a parse tree */
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
