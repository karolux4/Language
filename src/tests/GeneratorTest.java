package tests;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import compiler.Compiler;
import compiler.ParseException;
import generator.Program;

public class GeneratorTest {

	private Compiler compiler = Compiler.instance();
	
	@Test
	public void generatorTest() {
		//compile("src/sample/fork.pickle", "fork");
		//System.out.println("-----------------------");
		//compile("src/sample/arrayCorrect.pickle", "arrayCorrect");
		//System.out.println("-----------------------");
		//compile("src/sample/procCorrect.pickle", "procCorrect");
		compile("src/sample/simple/simple.pickle", "simple");
		compile("src/sample/concurrency/simpleFork.pickle", "simpleFork");
		compile("src/sample/concurrency/bankExample.pickle", "bankExample");
		compile("src/sample/concurrency/petersonsExample.pickle", "petersonsExample");
		compile("src/sample/division/division.pickle", "divisionExample");
		compile("src/sample/simple/expressions.pickle", "expressionsExample");
		compile("src/sample/concurrency/fork.pickle", "fork");
		compile("src/sample/concurrency/manyForks.pickle", "manyForks");
		compile("src/sample/arrays/simpleArray.pickle", "simpleArray");
		compile("src/sample/procedures/simpleProc.pickle", "simpleProc");
		compile("src/sample/procedures/fibonacci.pickle", "fibonacci");

	}
	
	public void compile(String file, String out) {
		try {
			Program p= compiler.compile(new File(file));
			if(p!=null) {
				p.writeToFile(out);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown parse exception");
		}
	}
	
}
