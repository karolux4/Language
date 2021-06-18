package generator;

public class Reg extends Operand{

	private final int id;
	
	public Reg(int id) {
		super(Type.REG);
		assert id > 0 && id < 7 : "Register id must be between 0 and 7";
		this.id=id;
	}
	
	/** Returns the id of this register. */
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return ""+this.id;
	}
}
