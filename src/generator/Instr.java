package generator;

import java.util.Arrays;
import java.util.List;

import generator.Operand.Type;

public class Instr {

	/** The operation code. */
	private final OpCode opCode;
	/** The list of arguments of this operation. */
	private final List<Operand> args;

	/** Constructs an instruction with a given opcode and arguments. */
	public Instr(OpCode opCode, Operand... args) throws IllegalArgumentException {
		this.opCode = opCode;
		int argsCount = opCode.getSigSize();
		if (args.length != argsCount) {
			throw new IllegalArgumentException(
					String.format("Operation '%s' expects %d arguments but has %d", opCode, argsCount, args.length));
		}
		for (int i = 0; i < argsCount; i++) {
			Operand arg = args[i];
			Type expected = opCode.getSig().get(i);
			if (arg.getType() != expected) {
				throw new IllegalArgumentException(
						String.format("Operation '%s' argument %d should be '%s' but is '%s'", this.opCode, i, expected,
								arg.getType()));
			}
		}
		this.args = Arrays.asList(args);

	}

	/** Returns the opcode of this operation. */
	public OpCode getOpCode() {
		return this.opCode;
	}

	/** Returns the list of all (source + target) arguments. */
	public List<Operand> getArgs() {
		return this.args;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getOpCode());
		for(Operand o : getArgs()) {
			result.append(' ');
			result.append(o.toString());
		}
		return result.toString();
	}

}
