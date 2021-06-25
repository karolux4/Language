package tests.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Test;

import compiler.ErrorListener;
import grammar.PickleCannonLexer;
import grammar.PickleCannonParser;

public class SyntaxTest {

	@Test
	public void testBaseTypes() {
		// Accept-reject tests
		accepts("cannon { int a; }");
		accepts("CaNnon { InT B; }");
		accepts("cannon { a = b45; }");
		accepts("cannon { int a3V4 = 15; }");
		accepts("cannon { bool a; }");
		accepts("cannon { bool a43 = true; }");
		accepts("cannon { bool be = false; }");
		accepts("cannon { bool f1 = f2; }");
		rejects("cannon { int 3aV4 = 15; }"); // identifier names should start with a letter
		rejects("cannon { int a5* = 15; }"); // identifier names contain only letters and digits
		rejects("{ int a; }"); // program main body starts with 'cannon' keyword
		rejects("cannon int a;"); // program main body should be enclosed by braces
		rejects("cannon { int a }"); // each statement (that does not open a new scope) should end with ';'
		rejects("cannon { bool bool = false; }"); // bool is reserver keyword and cannot be used as identifier
		rejects("cannon { integer a1 = 2; }"); // integer is non-existing type keyword (must use 'int')
		rejects("cannon { boolean a1 = true; }"); // boolean is non-existing type keyword (must use 'bool')
		// ParseTree tests
		ParseTree tree = accepts("cannon { int a5=3;}");
		Assert.assertEquals("{inta5=3;}", tree.getChild(1).getText()); // check if body is the same (whitespaces are
																		// removed)
		Assert.assertEquals("int", tree.getChild(1).getChild(1).getChild(0).getText());
		Assert.assertEquals("a5", tree.getChild(1).getChild(1).getChild(1).getText());
		Assert.assertEquals("3", tree.getChild(1).getChild(1).getChild(3).getText());
	}

	@Test
	public void testExpressions() {
		// Accept-reject tests
		accepts("cannon {int a = 2+9;}");
		accepts("cannon {int a = 10-5;}");
		accepts("cannon {int a = 2*3;}");
		accepts("cannon {int a = 10/3;}");
		accepts("cannon {int a = 2*3+(3+3)*4;}");
		accepts("cannon {int a = -2;}");
		accepts("cannon {int a = --3---4;}");
		accepts("cannon {int a = a*b-3;}");
		accepts("cannon {bool a = true&&false;}");
		accepts("cannon {bool a = true||true;}");
		accepts("cannon {bool a = b==c;}");
		accepts("cannon {bool a = !true;}");
		rejects("cannon {int a = 2**3;}"); // duplicate multiplication symbol
		rejects("cannon {int a = +3;}"); // only '-' and '!' are allowed prefix operators
		rejects("cannon {bool a = true&true;}"); // AND operations uses two '&' symbols
		rejects("cannon {int a = (2+3;}"); // missing ')'
		rejects("cannon {int a == 1;}"); // assignment should use only one '=' operator
		// ParseTree tests
		ParseTree tree = accepts("cannon { int a=2+(3+3*2)*8--3;}");
		Assert.assertEquals("{inta=2+(3+3*2)*8--3;}", tree.getChild(1).getText()); // check if body is the same
		Assert.assertEquals("2+(3+3*2)*8--3", tree.getChild(1).getChild(1).getChild(3).getText());
		Assert.assertEquals("2+(3+3*2)*8", tree.getChild(1).getChild(1).getChild(3).getChild(0).getText());
		Assert.assertEquals("(3+3*2)*8", tree.getChild(1).getChild(1).getChild(3).getChild(0).getChild(2).getText());
		Assert.assertEquals("(3+3*2)",
				tree.getChild(1).getChild(1).getChild(3).getChild(0).getChild(2).getChild(0).getText());
		Assert.assertEquals("3*2", tree.getChild(1).getChild(1).getChild(3).getChild(0).getChild(2).getChild(0)
				.getChild(1).getChild(2).getText());
	}

	@Test
	public void testIfWhileScopes() {
		// Accept-reject tests
		accepts("cannon { while (a>0) { }}");
		accepts("cannon { while (true) { int a = 3;}}");
		accepts("cannon { while (a>b[1]) { int b = 4;}}");
		accepts("cannon { while (!false) { }}");
		accepts("cannon { if (10 > 3){ }}");
		accepts("cannon { if (a>0) { }}");
		accepts("cannon { if (a>0) { } else { }}");
		accepts("cannon { if (true||false) { int a=2; } else {int a=3;} }");
		accepts("cannon { int a = 2; {int a=3;}}");
		accepts("cannon { int a = 2; {int a=3; { a=5;}}}");
		accepts("cannon { int a = 2; {int a=3;} {int a=4;}}");
		rejects("cannon { while (a>0) int a=3;}"); // while body needs braces
		rejects("cannon { while a>0 {int a=3;}}"); // while condition needs parenthesis
		rejects("cannon { if (true) int a=3;}"); // if body needs braces
		rejects("cannon { if true {int a=3;}}"); // if condition needs parenthesis
		rejects("cannon { if (true) {int a=3;} else int a=2;}"); // else body needs braces
		rejects("cannon { if (true) {int a=3;} else (false) {int a=2;}}"); // else does not have a condition
		rejects("cannon { { int a; }"); // missing '}' to close the nested scope
		// ParseTree tests
		ParseTree tree = accepts("cannon {if (true) { a = 2;} else { a=3; }}");
		Assert.assertEquals("{if(true){a=2;}else{a=3;}}", tree.getChild(1).getText()); // check if body is the same
		Assert.assertEquals("if", tree.getChild(1).getChild(1).getChild(0).getText());
		Assert.assertEquals("true", tree.getChild(1).getChild(1).getChild(2).getText());
		Assert.assertEquals("{a=2;}", tree.getChild(1).getChild(1).getChild(4).getText());
		Assert.assertEquals("else", tree.getChild(1).getChild(1).getChild(5).getText());
		Assert.assertEquals("{a=3;}", tree.getChild(1).getChild(1).getChild(6).getText());
	}
	
	@Test
	public void testThreads() {
		// Accept-reject tests
		accepts("cannon { int a; fork { int a=3;}}");
		accepts("cannon { int a; fork { {int a=3;} }}");
		accepts("cannon { int a; fork { int a=5; fork { int a=4;}}}");
		accepts("cannon { int a; fork { int a=3;} join; if(a>0) { print(a); }}");
		accepts("cannon { int a; fork { fork {int a=3;} fork{ int a=4; } join; } }");
		accepts("cannon { int shared a; fork { sync {a=3;} } sync {a=5;} }");
		rejects("cannon { int a; fork { int a=3;};}"); // ';' is not needed after fork body
		rejects("cannon { int a; fork { int a=3;} join }"); // join statement needs ';'
		rejects("cannon { int shared a; fork { a=3;} sync a=2; }"); // sync statement needs braces
		// ParseTree tests
		ParseTree tree = accepts("cannon { int shared a; fork { sync {a=3;} } }");
		Assert.assertEquals("{intshareda;fork{sync{a=3;}}}", tree.getChild(1).getText()); // check if body is the same
		Assert.assertEquals("intshareda;", tree.getChild(1).getChild(1).getText());
		Assert.assertEquals("shared", tree.getChild(1).getChild(1).getChild(1).getText());
		Assert.assertEquals("fork{sync{a=3;}}", tree.getChild(1).getChild(2).getText());
		Assert.assertEquals("fork", tree.getChild(1).getChild(2).getChild(0).getText());
		Assert.assertEquals("{sync{a=3;}}", tree.getChild(1).getChild(2).getChild(1).getText());
		Assert.assertEquals("sync", tree.getChild(1).getChild(2).getChild(1).getChild(1).getChild(0).getText());
	}
	
	@Test
	public void testArrays() {
		// Accept-reject tests
		accepts("cannon { int a[3];}");
		accepts("cannon { bool a[2] = [true,false];}");
		accepts("cannon { a[3] = 5*8-10;}");
		accepts("cannon { int a[3] = b;}");
		accepts("cannon { int a = b[4]*10;}");
		accepts("cannon { print(a[4]);}");
		rejects("cannon { int a[] = [3,2];}"); // array size must be specified
		rejects("cannon { int [3]a;}"); // array size is specified after the identifier
		rejects("cannon { int a[2 = [1,4];}"); // missing ']' 
		rejects("cannon { int a[3][3];}"); // only one-dimension arrays are supported
		// ParseTree tests
		ParseTree tree = accepts("cannon { bool a[2] = [true,false];}");
		Assert.assertEquals("{boola[2]=[true,false];}", tree.getChild(1).getText()); // check if body is the same
		Assert.assertEquals("bool", tree.getChild(1).getChild(1).getChild(0).getText());
		Assert.assertEquals("a", tree.getChild(1).getChild(1).getChild(1).getText());
		Assert.assertEquals("2", tree.getChild(1).getChild(1).getChild(3).getText());
		Assert.assertEquals("[true,false]", tree.getChild(1).getChild(1).getChild(6).getText());
	}
	
	@Test
	public void testProcedures() {
		// Accept-reject tests
		accepts("pickle p1() { } cannon {int a=2;}");
		accepts("pickle p1(int a) { print(a); } cannon { p1(3);}");
		accepts("pickle p1() { } pickle p2(int a){ }  cannon {p1();}");
		accepts("pickle p1() { } pickle p2(int a, bool b[4]){ p1();}  cannon {p2(4, [false,true,false,false]);}");
		rejects("p1() { } cannon {int a=2;}"); // each procedure needs to start with 'pickle' keyword
		rejects("pickle 1p() { } cannon {int a=2;}"); // each procedure name needs to start with a letter
		rejects("pickle p1(int a; int b;) { } cannon {int a=2;}"); // parameters need to be separated by ','
		rejects("cannon {int a=2;} pickle p1() { } "); // procedures are declared before main body
		rejects("pickle p1 { } cannon {int a=2;}"); // each procedure needs to have parenthesis
		rejects("pickle p1(int a[]) { } cannon {int a=2;}"); // size of array need to be specified in procedure parameters
		rejects("pickle p1() { return 5;} cannon {int a=2;}"); // return functionality is not-supported
		rejects("pickle p1() { } cannon { p1;}"); // procedure calls need parenthesis
		// ParseTree tests
		ParseTree tree = accepts("pickle p1(int a) { print(a); } cannon { p1(3);}");
		Assert.assertEquals("picklep1(inta){print(a);}", tree.getChild(0).getText());
		Assert.assertEquals("{p1(3);}", tree.getChild(2).getText());
		Assert.assertEquals("pickle", tree.getChild(0).getChild(0).getText());
		Assert.assertEquals("p1", tree.getChild(0).getChild(1).getText());
		Assert.assertEquals("inta", tree.getChild(0).getChild(3).getText());
		Assert.assertEquals("{print(a);}", tree.getChild(0).getChild(5).getText());
		Assert.assertEquals("p1", tree.getChild(2).getChild(1).getChild(0).getText());
		Assert.assertEquals("(3)", tree.getChild(2).getChild(1).getChild(1).getText());
	}

	@Test
	public void testFiles() {
		accepts("src/sample/concurrency/fork.pickle", true);
		accepts("src/sample/procedures/proc.pickle", true);
	}

	public ParseTree accepts(String input) {
		return accepts(input, false);
	}

	public ParseTree accepts(String input, boolean isFilePath) {
		try {
			ParseTree tree = parse(input, isFilePath);
			// Pass the test
			return tree;
		} catch (Exception e) {
			Assert.fail("Should have passed, but failed (Input: " + input + ")");
			return null;
		}
	}

	public void rejects(String input) {
		rejects(input, false);
	}

	public void rejects(String input, boolean isFilePath) {
		try {
			parse(input, isFilePath);
			Assert.fail("Should have failed, but passed (Input: " + input + ")");
		} catch (Exception e) {
			// Pass the test
		}
	}

	public ParseTree parse(String input, boolean isFilePath) throws Exception {
		Lexer lexer;
		if (isFilePath) {
			lexer = new PickleCannonLexer(CharStreams.fromFileName(input));
		} else {
			lexer = new PickleCannonLexer(CharStreams.fromString(input));
		}
		lexer.removeErrorListeners();
		lexer.addErrorListener(new ErrorListener());
		PickleCannonParser parser = new PickleCannonParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(new ErrorListener());
		ParseTree tree = parser.program();
		if (((ErrorListener) (lexer.getErrorListeners().get(0))).getErrors().size() == 0
				&& ((ErrorListener) (parser.getErrorListeners().get(0))).getErrors().size() == 0) {
		} else {
			((ErrorListener) (lexer.getErrorListeners().get(0))).throwException();
			((ErrorListener) (parser.getErrorListeners().get(0))).throwException();
		}
		return tree;
	}
}
