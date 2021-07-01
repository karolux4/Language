package generator;

import static generator.Operand.Type.REG;
import static generator.Operand.Type.OPERATOR;
import static generator.Operand.Type.ADDR;
import static generator.Operand.Type.TARGET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The enumerator to store all possible Sprockell instructions used in code
 * generation
 * 
 * @author Karolis Butkus
 *
 */
public enum OpCode {

	/** No operation */
	Nop(0),

	/** Add operation */
	Compute(3, OPERATOR, REG, REG, REG),

	/** Jump operation */
	Jump(1, TARGET),

	/** Branch operation */
	Branch(2, REG, TARGET),

	/** Load operation */
	Load(1, ADDR, REG),

	/** Store value from register to specified memory address */
	Store(1, REG, ADDR),

	/** Push register value to stack */
	Push(1, REG),

	/** Pop value from stack */
	Pop(0, REG),

	/** Send read request to shared memory address */
	ReadInstr(1, ADDR),

	/** Wait for reply and save it in the register */
	Receive(0, REG),

	/** Write register value to shared memory address */
	WriteInstr(2, REG, ADDR),

	/**
	 * Request a test on address for 0 and sets it to 1 if it is. Reply will contain
	 * 1 on success, and 0 on failure. This is an atomic operation; it might
	 * therefore be used to implement locks or synchronisation.
	 */
	TestAndSet(1, ADDR),

	/** Program end operation */
	EndProg(0)

	;

	/** The source operand types. */
	private final List<Operand.Type> sourceSig;

	/** The target operand types. */
	private final List<Operand.Type> targetSig;

	/** The operand types. */
	private final List<Operand.Type> sig;

	/** Constructs OpCode object from the given argument count and Operands*/
	private OpCode(int sourceCount, Operand.Type... sig) {
		this.sourceSig = new ArrayList<>(sourceCount);
		for (int i = 0; i < sourceCount; i++) {
			this.sourceSig.add(sig[i]);
		}
		this.targetSig = new ArrayList<>(sig.length - sourceCount);
		for (int i = sourceCount; i < sig.length; i++) {
			this.targetSig.add(sig[i]);
		}
		this.sig = new ArrayList<>(Arrays.asList(sig));
	}

	/** Returns the number of operands. */
	public int getSigSize() {
		return getSourceCount() + getTargetCount();
	}

	/** Returns the list of expected operand types. */
	public List<Operand.Type> getSig() {
		return this.sig;
	}

	/** Returns the number of source operands. */
	public int getSourceCount() {
		return getSourceSig().size();
	}

	/** Returns the list of expected source operand types. */
	public List<Operand.Type> getSourceSig() {
		return this.sourceSig;
	}

	/** Returns the number of target operands. */
	public int getTargetCount() {
		return getTargetSig().size();
	}

	/** Returns the list of expected target operand types. */
	public List<Operand.Type> getTargetSig() {
		return this.targetSig;
	}
}
