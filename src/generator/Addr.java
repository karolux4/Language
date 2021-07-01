package generator;

import generator.Reg.RegName;

/**
 * Addr operand class that is used in Sprockell code generation
 * 
 * @author Karolis Butkus
 *
 */
public class Addr extends Operand {

	/** Possible Addr types */
	public enum AddrImmDI {
		ImmValue, DirAddr, IndAddr
	}

	/** The address object for number output */
	public static Addr NUMBER_IO = new Addr(AddrImmDI.DirAddr, -1);
	/** The address object for character output */
	public static Addr CHAR_IO = new Addr(AddrImmDI.DirAddr, -2);

	/** Addr object type */
	private final AddrImmDI type;
	/** Addr object value */
	private final int value;

	/** Constructs Addr object from the given type and value */
	public Addr(AddrImmDI type, int value) {
		super(Type.ADDR);
		this.type = type;
		if (type == AddrImmDI.IndAddr) {
			assert value >= 0 && value <= 8 : "Register id must be between 0 and 8";
		}
		this.value = value;
	}

	/** Return the type of Addr object */
	public AddrImmDI getAddrType() {
		return this.type;
	}

	/** Return the value of Addr object */
	public int getValue() {
		return this.value;
	}

	/** Prints the object in Sprockell instruction argument style */
	@Override
	public String toString() {
		if (this.value == -1 && this.type == AddrImmDI.DirAddr) {
			return "numberIO"; // numberIO address
		}
		if (this.value == -2 && this.type == AddrImmDI.DirAddr) {
			return "charIO"; // charIO address
		}
		if (this.type == AddrImmDI.IndAddr) {
			return "(" + this.type.toString() + " " + RegName.values()[this.value] + ")";
		}
		return "(" + this.type.toString() + " (" + this.value + "))";
	}
}
