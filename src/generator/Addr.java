package generator;

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
		return "("+this.type.toString()+" "+this.value+")";
	}
}
