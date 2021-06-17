package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static generator.Operand.Type.NUM;
import static generator.Operand.Type.REG;

public enum OpCode {

	/** No operation */
	nop(0),

	/** Add operation */
	add(2, REG, REG, REG),

	/** Subtract operation */
	sub(2, REG, REG, REG),

	/** Multiplication operation */
	mult(2, REG, REG, REG),

	/** Equal operation */
	eq(2, REG, REG, REG),

	/** Not equal operation */
	neq(2, REG, REG, REG),

	/** Greater than operation */
	gt(2, REG, REG, REG),

	/** Greater than or equal operation */
	gte(2, REG, REG, REG),

	/** Lower than operation */
	lt(2, REG, REG, REG),

	/** Lower than or equal operation */
	lte(2, REG, REG, REG),

	/** And operation */
	and(2, REG, REG, REG),

	/** Or operation */
	or(2, REG, REG, REG),

	/** Load number operation */
	loadNum(1, NUM, REG),

	/** Load value from memory address */
	loadAddr(1, NUM, REG),

	/** Load value from memory at address stored in register */
	loadReg(1, REG, REG),

	/** Store value from register to specified memory address */
	storeAddr(1, REG, NUM),

	/** Store value from register to memory at address stored in register */
	storeReg(1, REG, REG),

	/** Push register value to stack */
	push(1, REG),

	/** Pop value from stack */
	pop(0, REG),

	/** Send read request to shared memory address */
	readInstrAddr(1, NUM),

	/** Send read request to shared memory address stored at register */
	readInstrReg(1, REG),

	/** Wait for reply and save it in the register */
	receiveInstr(0, REG),

	/** Write register value to shared memory address */
	writeInstrAddr(2, REG, NUM),

	/** Write register value to shared memory address stored at register */
	writeInstrReg(2, REG, REG),

	/**
	 * Request a test on address for 0 and sets it to 1 if it is. Reply will contain
	 * 1 on success, and 0 on failure. This is an atomic operation; it might
	 * therefore be used to implement locks or synchronisation.
	 */
	testAndSetAddr(1, NUM),

	/**
	 * Request a test on address for 0 and sets it to 1 if it is. Reply will contain
	 * 1 on success, and 0 on failure. This is an atomic operation; it might
	 * therefore be used to implement locks or synchronisation.
	 */
	testAndSetReg(1, REG),

	/** Program end operation */
	endProg(0)

	;

	/** The source operand types. */
	private final List<Operand.Type> sourceSig;

	/** The target operand types. */
	private final List<Operand.Type> targetSig;

	/** The operand types. */
	private final List<Operand.Type> sig;

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
}
