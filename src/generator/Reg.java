package generator;

/**
 * Reg operand class that is used in Sprockell code generation
 * @author Karolis Butkus
 *
 */
public class Reg extends Operand{

	/** The id of the register*/
	private final int id;
	
	/** The register names enumerator in order (starting from 0)*/
	public enum RegName {
		reg0,
		regSprID,
		regA,
		regB,
		regC,
		regD,
		regE,
		regF,
		regSP
	}
	
	/** Constructs Reg object from a given register id*/
	public Reg(int id) {
		super(Type.REG);
		assert id >= 0 && id < 9 : "Register id must be between 0 and 8";
		this.id=id;
	}
	
	/** Returns the id of this register. */
	public int getId() {
		return this.id;
	}
	
	/** Prints the register name according to the id*/
	@Override
	public String toString() {
		return ""+RegName.values()[id];
	}
}
