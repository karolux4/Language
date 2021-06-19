package generator;

import generator.Reg.RegName;

public class Addr extends Operand{

	public enum AddrImmDI{
		ImmValue,
		DirAddr,
		IndAddr
	}
	
	private final AddrImmDI type;
	private final int value;
	
	public Addr(AddrImmDI type, int value) {
		super(Type.ADDR);
		this.type=type;
		if(type==AddrImmDI.IndAddr) {
			assert value >=0 && value < 8 : "Register id must be between 0 and 7";
		}
		this.value=value;
	}
	
	public AddrImmDI getAddrType() {
		return this.type;
	}
	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		if(this.type==AddrImmDI.IndAddr) {
			return "("+this.type.toString()+" "+RegName.values()[this.value]+")";
		}
		return "("+this.type.toString()+" "+this.value+")";
	}
}
