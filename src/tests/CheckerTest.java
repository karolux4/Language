package tests;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import compiler.Compiler;
import compiler.ParseException;

public class CheckerTest {

	private Compiler compiler = Compiler.instance();
	
	@Test
	public void checkerTest() {
		checkSuccess("src/sample/fork.pickle");
		checkSuccess("src/sample/arrayCorrect.pickle");
		checkSuccess("src/sample/procCorrect.pickle");
		checkFail("src/sample/arrayError.pickle");
		checkFail("src/sample/error1.pickle");
		checkFail("src/sample/error2.pickle");
		checkFail("src/sample/error3.pickle");
	}
	
	public void checkSuccess(String file) {
		try {
			compiler.check(new File(file));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			Assert.fail("Should not have thrown parse exception");
		}
	}
	
	public void checkFail(String file) {
		try {
			compiler.check(new File(file));
			Assert.fail("Should have thrown an exception");
		} catch (ParseException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
