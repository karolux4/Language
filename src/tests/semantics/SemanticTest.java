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
	public void generatorTest() {
		//compile("src/sample/fork.pickle", "fork");
		//System.out.println("-----------------------");
		//compile("src/sample/arrayCorrect.pickle", "arrayCorrect");
		//System.out.println("-----------------------");
		//compile("src/sample/procCorrect.pickle", "procCorrect");
		/*
		 * compile("src/sample/simple/simple.pickle", "simple");
		 * compile("src/sample/concurrency/simpleFork.pickle", "simpleFork");
		 * compile("src/sample/concurrency/bankExample.pickle", "bankExample");
		 * compile("src/sample/concurrency/petersonsExample.pickle",
		 * "petersonsExample"); compile("src/sample/division/division.pickle",
		 * "divisionExample"); compile("src/sample/simple/expressions.pickle",
		 * "expressionsExample"); compile("src/sample/concurrency/fork.pickle", "fork");
		 * compile("src/sample/concurrency/manyForks.pickle", "manyForks");
		 * compile("src/sample/arrays/simpleArray.pickle", "simpleArray");
		 * compile("src/sample/procedures/simpleProc.pickle", "simpleProc");
		 * compile("src/sample/procedures/fibonacci.pickle", "fibonacci");
		 */
		check("src/sample/concurrency/bankExample.pickle", true, "Sprockell 0 says 11000");
		check("cannon { print(10); }", "Sprockell 0 says 10");
		check("cannon { print(false); }", "Sprockell 0 says 0");

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
