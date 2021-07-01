package compiler;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

/**
 * ErrorListener class that is used to collect errors detected
 * during scanning, parsing and elaboration.
 * @author Karolis Butkus
 *
 */
public class ErrorListener  extends BaseErrorListener {
	/** Errors collected by the listener. */
	private final List<String> errors = new ArrayList<>();

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
			Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		this.errors.add(String.format("Line %d:%d - %s", line,
				charPositionInLine, msg));
	}

	/** Adds an error message during the tree visit stage. */
	public void visitError(Token token, String msg, Object... args) {
		int line = token.getLine();
		int charPositionInLine = token.getCharPositionInLine();
		msg = String.format(msg, args);
		msg = String.format("Line %d:%d - %s", line, charPositionInLine, msg);
		this.errors.add(msg);
	}

	/** Indicates if the listener has collected any errors. */
	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

	/** Returns the (possibly empty) list of errors collected by the listener. */
	public List<String> getErrors() {
		return this.errors;
	}
	
	/** Throws an exception if any errors were reported;
	 * does nothing otherwise.
	 * @throws ParseException an exception containing all errors found 
	 * during listening
	 */
	public void throwException() throws ParseException {
		if (hasErrors()) {
			throw new ParseException(getErrors());
		}
	}
}
