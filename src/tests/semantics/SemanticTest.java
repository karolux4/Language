package tests.semantics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import compiler.Compiler;
import compiler.ParseException;
import generator.Program;

/**
 * Semantic test class is used to perform semantic testing
 * 
 * @author Karolis Butkus
 *
 */
public class SemanticTest {

	/** Compiler instances */
	private Compiler compiler = Compiler.instance();

	@Test
	public void testTypesAndAssignments() {
		check("cannon { int a = 203; print(a); }", "typesAndAssignments1", "Sprockell 0 says 203");
		check("cannon { bool a = true; print(a); }", "typesAndAssignments2", "Sprockell 0 says 1");
		check("cannon { int a = 1; { bool a = true; print(a); } }", "typesAndAssignments3", "Sprockell 0 says 1");
		check("cannon { int a[3] = [10,5,100]; print(a); }", "typesAndAssignments4",
				"{\nSprockell 0 says 10\nSprockell 0 says 5\nSprockell 0 says 100\n}");
		check("cannon { int a[3] = [10,5,100]; int b[3]=a; b[1]=3; print(a[1]); }", "typesAndAssignments5",
				"Sprockell 0 says 5");
	}

	@Test
	public void testExpressions() {
		check("cannon { int a = 2+3*4; print(a); }", "expr1", "Sprockell 0 says 14");
		check("cannon { int a = (7+8)*2-9+3*2; print(a); }", "expr2", "Sprockell 0 says 27");
		check("cannon { int a = (3-8)/2; print(a); }", "expr3", "Sprockell 0 says -2");
		check("cannon { int a[3] = [10,4,9]; int b[3] = [10,3,9]; print(a==b); }", "expr4", "Sprockell 0 says 0");
		check("cannon { print(!true==false&&[3,2,1]==[3,2,1]); }", "expr5", "Sprockell 0 says 1");
	}

	@Test
	public void testWhileIf() {
		check("cannon { int a = 10; int sum = 0; while (a>0) { if(a>5) { sum = sum + a;} a=a-1; } print(sum);}",
				"whileIf1", "Sprockell 0 says 40");
		check("cannon { if(false!=false) { print (10); } else { print(100); } }", "whileIf2", "Sprockell 0 says 100");
		check("cannon {  while (false) { print(2); } print(1);}", "whileIf3", "Sprockell 0 says 1");
	}

	@Test
	public void testConcurrency() {
		check("src/tests/semantics/testSources/bankExample.pickle", true, "bankExample", "Sprockell 0 says 11000");
		check("src/tests/semantics/testSources/petersonsExample.pickle", true, "petersonsExample",
				"Sprockell 0 says 30");
		check("src/tests/semantics/testSources/nestedThreadsExample.pickle", true, "nestedThreadsExample",
				"Sprockell 2 says 3\nSprockell 1 says 2\nSprockell 0 says 1");
	}

	@Test
	public void testGeneralAlgorithms() {
		check("src/tests/semantics/testSources/februaryDaysExample.pickle", true, "februaryDaysExample",
				"Sprockell 0 says 29\nSprockell 0 says 28\nSprockell 0 says 29\nSprockell 0 says 28\nSprockell 0 says 29");
		check("src/tests/semantics/testSources/isPrimeExample.pickle", true, "isPrimeExample",
				"Sprockell 0 says 1\nSprockell 0 says 1\nSprockell 0 says 0\nSprockell 0 says 0");
		check("src/tests/semantics/testSources/arrayConcurrentSumExample.pickle", true, "arrayConcurrentSumExample",
				"Sprockell 0 says 5");
		check("src/tests/semantics/testSources/fibonacciExample.pickle", true, "fibonacciExample",
				"Sprockell 0 says 1\nSprockell 0 says 2\nSprockell 0 says 3\nSprockell 0 says 5\nSprockell 0 says 165580141");
		check("src/tests/semantics/testSources/arrayMaxExample.pickle", true, "arrayMaxExample",
				"Sprockell 0 says 100\nSprockell 0 says -3\nSprockell 0 says 1000000");
		check("src/tests/semantics/testSources/boolArrayExample.pickle", true, "boolArrayExample",
				"Sprockell 0 says 1\nSprockell 0 says 0\nSprockell 0 says 1");
		check("src/tests/semantics/testSources/extendedProgram.pickle", true, "extendedProgram",
				"Sprockell 0 says 4\nSprockell 0 says 1\nSprockell 0 says 10\nSprockell 0 says 9\nSprockell 0 says 24");
	}

	/*
	 * IMPORTANT! Do not forget to kill ghc process manually as its execution is not
	 * stopped due to infinite cycle
	 */
	@Test
	public void testInfiniteLoop() throws InterruptedException {
		Thread thread = new Thread() {
			@Override
			public void run() {
				check("cannon { while (10>3) { int a =3;}}", "infiniteLoop", "");
			}
		};

		thread.start();

		// Let the current thread sleep (not the created thread!)
		Thread.sleep(5000);

		Assert.assertTrue(thread.isAlive());
	}

	/*
	 * IMPORTANT! Do not forget to kill ghc process manually as its execution is not
	 * stopped due to infinite cycle
	 */
	@Test
	public void testDivisionByZero() throws InterruptedException {
		Thread thread = new Thread() {
			@Override
			public void run() {
				check("cannon { int a = 3/0;}", "divisionByZero", "");
			}
		};

		thread.start();

		// Let the current thread sleep (not the created thread!)
		Thread.sleep(5000);

		Assert.assertTrue(thread.isAlive());
	}

	/** The same as check(input,false,output,expected) */
	public void check(String input, String output, String expected) {
		check(input, false, output, expected);
	}

	/**
	 * Checks if input program after compilation and execution produce the correct
	 * results
	 */
	public void check(String input, boolean isFilePath, String output, String expected) {
		try {
			Program p;
			if (isFilePath) {
				p = compiler.compile(new File(input));
			} else {
				p = compiler.compile(input);
			}
			if (p != null) {
				p.writeToFile(output);
				String out = runFile(output);
				Assert.assertEquals(expected, out);
			} else {
				Assert.fail("Program has not been generated");
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown parse exception");
		}
	}

	/** Runs the specified program in ghc */
	public String runFile(String name) throws IOException {
		String currentDir = System.getProperty("user.dir");
		String sep = System.getProperty("file.separator");
		String outputDir = currentDir + sep + "src" + sep + "output";
		String OS = System.getProperty("os.name").toLowerCase();
		String output = "";
		ProcessBuilder builder;
		if (OS.contains("win")) {
			// Windows
			builder = new ProcessBuilder("cmd.exe", "/c", "cd " + outputDir + " && runhaskell", name + ".hs");
		} else {
			// Linux
			builder = new ProcessBuilder("/bin/bash", "-c", "cd " + outputDir + " && runhaskell " + name + ".hs");
		}
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			} else {
				if (output != "") {
					output += "\n" + line;
				} else {
					output = line;
				}
			}
		}
		return output;
	}

}
