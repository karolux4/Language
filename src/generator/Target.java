package generator;

public class Target extends Operand{
	
	public enum TargetType{
		Abs,
		Rel,
		Ind
	}
	
	private final TargetType type;
	private final int value;
	
	public Target(TargetType type, int value) {
		super(Type.TARGET);
		this.type=type;
		this.value=value;
	}
	
	public TargetType getTargetType() {
		return this.type;
	}
	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "("+this.type.toString()+" "+this.value+")";
	}

}
