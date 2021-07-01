package generator;

/**
 * Operator operand class used in Sprockell code generation
 * @author Karolis Butkus
 *
 */
public class Operator extends Operand{

	/** The possible types of the operator*/
	public enum Oper{
		Add,
		Sub,
		Mul,
		Equal,
		NEq,
		Gt,
		Lt,
		GtE,
		LtE,
		And,
		Or,
		Xor,
		Incr,
		Decr
	}
	
	/** The type of Operator object*/
	private final Oper operator;
	
	/** Constructs the Operator object from the given type*/
	public Operator(Oper o) {
		super(Type.OPERATOR);
		this.operator=o;
	}
	
	/** Returns the operator. */
	public Oper getOperator() {
		return this.operator;
	}
	
	/** Prints the operator name*/
	@Override
	public String toString() {
		return this.operator.toString();
	}
}
