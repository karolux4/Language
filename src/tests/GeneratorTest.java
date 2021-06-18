package tests;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import compiler.Compiler;
import compiler.ParseException;

public class GeneratorTest {

	private Compiler compiler = Compiler.instance();
	
	@Test
	public void generatorTest() {
		compile("src/sample/fork.pickle");
		System.out.println("-----------------------");
		compile("src/sample/arrayCorrect.pickle");
		System.out.println("-----------------------");
		compile("src/sample/procCorrect.pickle");

	}
	
	public void compile(String file) {
		try {
			compiler.compile(new File(file));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown parse exception");
		}
	}
	
}
