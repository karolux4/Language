package tests.combined;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.context.ContextTest;
import tests.semantics.SemanticTest;
import tests.syntax.SyntaxTest;

@RunWith(Suite.class)
@SuiteClasses({
	SyntaxTest.class,
	ContextTest.class,
	SemanticTest.class})
public class TestSuite {

}
