package generator;

public class Operator extends Operand{

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
	
	private final Oper operator;
	
	public Operator(Oper o) {
		super(Type.OPERATOR);
		this.operator=o;
	}
	
	/** Returns the operator. */
	public Oper getOperator() {
		return this.operator;
	}
	
	@Override
	public String toString() {
		return this.operator.toString();
	}
}
