package tests.context;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import compiler.Compiler;
import compiler.ParseException;

public class ContextTest {

	private Compiler compiler = Compiler.instance();

	@Test
	public void testBasicTypes() {
		accepts("cannon { int a;}");
		accepts("cannon { int shared a=2;}");
		accepts("cannon { int a=-12;}");
		accepts("cannon { bool shared b;}");
		accepts("cannon { bool a = true;}");
		rejects("cannon { int a = true; }"); // int can only be a number
		rejects("cannon { bool a=1;}"); // bool can be only true or false
	}

	@Test
	public void testDeclarations() {
		accepts("cannon { int a; { int a;}}");
		accepts("cannon { int a=2; if(a>2) { bool a = false;} }");
		accepts("cannon { int a=2; if(a>2) { bool a = false;} else { int a[3];} }");
		rejects("cannon { int a; b=3;}"); // Variable b has not been declared
		rejects("cannon { int a; int a=3;}"); // Variable a has already been declared
		rejects("cannon { int a; {int shared b=3;}}"); // Shared variables can be declared only in the outer global
														// scope
		rejects("cannon { a=3; int a=2;}"); // Variable a has not been declared
		rejects("cannon { int a=3; {a=2; bool a = true;}}"); // Variable a has already been declared (because of use)
		rejects("cannon { int a=3; {int b=2;} b=3;}"); // Variable b has not been declared in the scope
	}

	@Test
	public void testExpressions() {
		accepts("cannon { int a=2+3-9;}");
		accepts("cannon { int a=2+2*3+1/-2;}");
		accepts("cannon { int a=2+(8+3)*(1+3)/2;}");
		accepts("cannon { bool a=true&&false;}");
		accepts("cannon { bool a = 2==2;}");
		accepts("cannon { bool a=true||false;}");
		accepts("cannon { bool a=true&&(false||true);}");
		rejects("cannon { int a=2+true;}"); // addition must use int type
		rejects("cannon { bool a = 1&&1;}"); // AND must use bool type
		rejects("cannon { bool a = 2==true;}"); // mismatched types
		rejects("cannon { bool a = 3>=true;}"); // mismatched types
	}

	@Test
	public void testIfWhile() {
		accepts("cannon { if(2>3) {print (3);}}");
		accepts("cannon { int a; if(a>0) { print (a); } else { print(0);}}");
		accepts("cannon { if(true) {print(false);} else {print(true);}}");
		accepts("cannon { int a=5; while(a>0) {a=a-1;} print(a);}");
		accepts("cannon { while(false) {int a=3;} print(2);}");
		rejects("cannon { if (2) {print (3);}}"); // if condition must be a bool
		rejects("cannon { if (true) {int a=3;} print(a);}"); // variable 'a' has not been declared in the outer scope
		rejects("cannon { if (2+3*9/3) {print (3);}}"); // if condition must be a bool
		rejects("cannon { while(0) {print (1);}}"); // while condition must be a bool
		rejects("cannon { while(false||true) {int a=3;} print(a); }"); // 'a' has not been declared in the outer scope
	}
	
	@Test
	public void testThreads() {
		accepts("cannon { fork {print (2);} print(1);}");
		accepts("cannon { fork { fork { print(3);} print(2); } print(1);}");
		accepts("cannon { int shared a=2; fork { sync { a=3;}} a=1;}");
		accepts("cannon { int shared a=2; fork { sync { a=a+2;}} join; a=a+1;}");
		accepts("cannon { int shared a=2; fork { fork { sync {a=a+3;} } join; } print(a);}");
		rejects("cannon { int a=3; fork {a=2;} }"); // 'a' must be declared shared
		rejects("cannon { while(true) { fork {print (3);} } }"); // cannot fork inside a while loop
		rejects("cannon { fork { int shared a=3; }}"); // shared variables can only be declared in outer global scope
		rejects("cannon { fork { int a=3; } print(a); }"); // 'a' has not been declared in outer scope
	}
	
	@Test
	public void testArrays() {
		accepts("cannon { int a[3];}");
		accepts("cannon { bool a[2]=[true,false];}");
		accepts("cannon { int a[3]; a[0+1]=3*8;}");
		accepts("cannon { int a[3]=[2,3,1]; bool c = a==[3,2];}");
		accepts("cannon { int a[3]=[1,2,3]; int b[3]=a;}");
		accepts("cannon { int shared a[3];}");
		rejects("cannon { int a[0]; }"); // array size must be greater than 0
		rejects("cannon { int a[3] = [1,2,false];}"); // all array elements must be of the declared type
		rejects("cannon { int a[3]=[3,2,1]; int b[2]=a;}"); //during assignment array sizes must match
		rejects("cannon { int a[3]; bool b[3]; bool c = a==b;}"); //mismatched array types
		rejects("cannon { int a[2+1];}"); //array size must be declared as a number, not expression
		// NOTE: there is no detection for trying to access elements out of array bounds
	}
	
	@Test
	public void testProcedures(){
		accepts("pickle p1() { p1(); } cannon { p1(); }");
		accepts("pickle p1(int a) { print(a);} cannon { p1(3); }");
		accepts("pickle p1() { p2();} pickle p2() { print(0); } cannon { p1(); }");
		accepts("pickle p1(int a[4]) { print(a);} cannon { p1([1,2,3,4]); }");
		accepts("pickle p1(int a) { print(a);} cannon { fork { p1(3); } }");
		rejects("pickle p1(int a) {} cannon { p1(); }"); // missing argument in procedure call
		rejects("pickle p1(int a) {} cannon { p1(false); }"); // mismatched argument type in procedure call
		rejects("pickle p1(int a[3]) {} cannon { p1([2,3]); }"); // mismatched argument type in procedure call
		rejects("pickle p1(int a) {} pickle p1() {} cannon { p1(false); }"); // duplicate procedure names
		rejects("pickle p1(bool a) { int a=3;} cannon { p1(false); }"); // 'a' already declared in the scope
		rejects("pickle p1(bool a) { int shared b=3;} cannon { p1(false); }"); // shared variables cannot be declared in procedure
		rejects("pickle p1(bool a) { fork { print(3); } } cannon { p1(false); }"); // cannot fork in the procedure
	}

	@Test
	public void testFiles() {
		accepts("src/tests/context/testSources/fork.pickle", true);
		accepts("src/tests/context/testSources/arrayCorrect.pickle", true);
		accepts("src/tests/context/testSources/procCorrect.pickle", true);
		rejects("src/tests/context/testSources/arrayError.pickle", true);
		rejects("src/tests/context/testSources/procError.pickle", true);
		rejects("src/tests/context/testSources/sharedError.pickle", true);
		rejects("src/tests/context/testSources/forkError.pickle", true);
		rejects("src/tests/context/testSources/forkSharedError.pickle", true);
		rejects("src/tests/context/testSources/error1.pickle", true);
		rejects("src/tests/context/testSources/error2.pickle", true);
		rejects("src/tests/context/testSources/error3.pickle", true);

	}

	public void accepts(String input) {
		accepts(input, false);
	}

	public void accepts(String input, boolean isFilePath) {
		try {
			if (isFilePath) {
				compiler.check(new File(input));
			} else {
				compiler.check(input);
			}
			// Pass the test
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown parse exception");
		}
	}

	public void rejects(String input) {
		rejects(input, false);
	}

	public void rejects(String input, boolean isFilePath) {
		try {
			if (isFilePath) {
				compiler.check(new File(input));
			} else {
				compiler.check(input);
			}
			Assert.fail("Should have thrown an exception");
		} catch (ParseException e) {
			// Pass the test
			System.out.println("Caught expected errors (Input '"+input+"'): ");
			e.print();
			System.out.println();
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("File does not exist");
		}
	}
}
