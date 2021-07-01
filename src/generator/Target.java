package generator;

import generator.Reg.RegName;

/** Target operand class used in Sprockell code generation
 * 
 * @author Karolis Butkus
 *
 */
public class Target extends Operand{
	
	/** The possible target types*/
	public enum TargetType{
		Abs,
		Rel,
		Ind
	}
	
	/** The type of the Target object*/
	private final TargetType type;
	/** The value of the Target object*/
	private final int value;
	
	/** Constructs the Target object from the given Target type and value*/
	public Target(TargetType type, int value) {
		super(Type.TARGET);
		this.type=type;
		this.value=value;
	}
	
	/** Returns the Target type */
	public TargetType getTargetType() {
		return this.type;
	}
	
	/** Returns the value of the Target object*/
	public int getValue() {
		return this.value;
	}
	
	/** Prints the Target in Sprockell instruction argument style*/
	@Override
	public String toString() {
		if(this.type==TargetType.Ind) {
			return "("+this.type.toString()+" "+RegName.values()[this.value]+")";
		}
		return "("+this.type.toString()+" ("+this.value+"))";
	}

}
