package tests.combined;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.context.ContextTest;
import tests.semantics.SemanticTest;
import tests.syntax.SyntaxTest;

/**
 * Test suite class used to run all tests automatically
 * 
 * @author Karolis Butkus
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ SyntaxTest.class, ContextTest.class, SemanticTest.class })
public class TestSuite {

}
