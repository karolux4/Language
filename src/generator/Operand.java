package generator;


/** Abstract supertype of all kinds of operands. */
abstract public class Operand {
	private final Type type;

	/** Constructs an operand of a given type. */
	protected Operand(Type type) {
		this.type = type;
	}

	/** Returns the type of this operand. */
	public Type getType() {
		return this.type;
	}

	/** Enumeration of all available operand types. */
	public static enum Type {
		/** Register-type operand; class {@link Reg}. */
		REG,
		/** Numeric operand; class {@link Num}. */
		NUM;
	}
}
