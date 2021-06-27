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

public class SemanticTest {

	private Compiler compiler = Compiler.instance();
	
	@Test
	public void testTypesAndAssignments() {
		check("cannon { int a = 203; print(a); }", "Sprockell 0 says 203");
		check("cannon { bool a = true; print(a); }", "Sprockell 0 says 1");
		check("cannon { int a = 1; { bool a = true; print(a); } }", "Sprockell 0 says 1");
		check("cannon { int a[3] = [10,5,100]; print(a); }", 
				"{\nSprockell 0 says 10\nSprockell 0 says 5\nSprockell 0 says 100\n}");
		check("cannon { int a[3] = [10,5,100]; int b[3]=a; b[1]=3; print(a[1]); }", "Sprockell 0 says 5");
	}
	
	@Test
	public void testExpressions() {
		check("cannon { int a = 2+3*4; print(a); }", "Sprockell 0 says 14");
		check("cannon { int a = (7+8)*2-9+3*2; print(a); }", "Sprockell 0 says 27");
		check("cannon { int a = (3-8)/2; print(a); }", "Sprockell 0 says -2");
		check("cannon { int a[3] = [10,4,9]; int b[3] = [10,3,9]; print(a==b); }", "Sprockell 0 says 0");
		check("cannon { print(!true==false&&[3,2,1]==[3,2,1]); }", "Sprockell 0 says 1");
	}
	
	@Test
	public void testWhileIf() {
		check("cannon { int a = 10; int sum = 0; while (a>0) { if(a>5) { sum = sum + a;} a=a-1; } print(sum);}", "Sprockell 0 says 40");
		check("cannon { if(false!=false) { print (10); } else { print(100); } }", "Sprockell 0 says 100");
		check("cannon {  while (false) { print(2); } print(1);}", "Sprockell 0 says 1");
	}
	
	@Test
	public void testConcurrency() {
		check("src/tests/semantics/testSources/bankExample.pickle", true, "Sprockell 0 says 11000");
		check("src/tests/semantics/testSources/petersonsExample.pickle", true, "Sprockell 0 says 30");
		check("src/tests/semantics/testSources/nestedThreadsExample.pickle", true, "Sprockell 2 says 3\nSprockell 1 says 2\nSprockell 0 says 1");
	}
	
	@Test
	public void testGeneralAlgorithms() {
		check("src/tests/semantics/testSources/februaryDaysExample.pickle", true, 
				"Sprockell 0 says 29\nSprockell 0 says 28\nSprockell 0 says 29\nSprockell 0 says 28\nSprockell 0 says 29");
		check("src/tests/semantics/testSources/isPrimeExample.pickle", true, 
				"Sprockell 0 says 1\nSprockell 0 says 1\nSprockell 0 says 0\nSprockell 0 says 0");
		check("src/tests/semantics/testSources/arrayConcurrentSumExample.pickle", true, "Sprockell 0 says 5");
		check("src/tests/semantics/testSources/fibonacciExample.pickle", true, 
				"Sprockell 0 says 1\nSprockell 0 says 2\nSprockell 0 says 3\nSprockell 0 says 5\nSprockell 0 says 165580141");
		check("src/tests/semantics/testSources/arrayMaxExample.pickle", true, 
				"Sprockell 0 says 100\nSprockell 0 says -3\nSprockell 0 says 1000000");
		check("src/tests/semantics/testSources/boolArrayExample.pickle", true, 
				"Sprockell 0 says 1\nSprockell 0 says 0\nSprockell 0 says 1");
	}

	public void check(String input, String expected) {
		check(input, false, expected);
	}
	
	public void check(String input, boolean isFilePath, String expected) {
			try {
				Program p;
				if(isFilePath) {
					p= compiler.compile(new File(input));
				}
				else {
					p= compiler.compile(input);
				}
				if(p!=null) {
					p.writeToFile("program_out");
					String output = runFile("program_out");
					Assert.assertEquals(expected, output);
				}
				else {
					Assert.fail("Program has not been generated");
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				Assert.fail("Should not have thrown parse exception");
			}
	}
	
	public String runFile(String name) throws IOException {
		String currentDir = System.getProperty("user.dir");
		String sep = System.getProperty("file.separator");
		String outputDir = currentDir+sep+"src"+sep+"output";
		String OS = System.getProperty("os.name").toLowerCase();
	    String output = "";
	    ProcessBuilder builder;
		if(OS.contains("win")) {
			//Windows
			builder = new ProcessBuilder("cmd.exe", "/c", "cd "+outputDir +"&& runhaskell", name+".hs");
		}
		else {
			//Linux
			builder = new ProcessBuilder("sh", "-c", "cd "+outputDir +"&& runhaskell", name+".hs");
		}
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line;
	    while (true) {
	        line = r.readLine();
	        if (line == null) {
	        	break;
	        }
	        else {
	        	if(output!="") {
		        	output+="\n"+line;
	        	}
	        	else {
	        		output=line;
	        	}
	        }
	    }
		return output;
	}
	
}
