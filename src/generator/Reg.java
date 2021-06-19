package generator;

public class Reg extends Operand{

	private final int id;
	
	public enum RegName {
		reg0,
		regSprID,
		regA,
		regB,
		regC,
		regD,
		regE,
		regF
	}
	
	public Reg(int id) {
		super(Type.REG);
		assert id >= 0 && id < 8 : "Register id must be between 0 and 7";
		this.id=id;
	}
	
	/** Returns the id of this register. */
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return ""+RegName.values()[id];
	}
}
