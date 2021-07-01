package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Program is class that used to describe Sprockell program objects
 * 
 * @author Karolis Butkus
 *
 */
public class Program {

	/** The list with the instructions of the program in order */
	private final List<Instr> instrList;

	/** The spawned thread count (excluding the main thread) */
	private int threadCount = 0;

	/** Constructs a Program object from the given thread count */
	public Program(int threadCount) {
		this.instrList = new ArrayList<>();
		this.threadCount = threadCount;
	}

	/** Adds instruction to a Program instruction list */
	public void addInstr(Instr instr) {
		this.instrList.add(instr);
	}

	/** Returns the specified instruction in the Program instrucion list */
	public Instr getInstr(int i) {
		return this.instrList.get(i);
	}

	/** Updates the specified instruction to the new one */
	public void updateInstr(int i, Instr ins) {
		this.instrList.set(i, ins);
	}

	/**
	 * Writes the Program as a Sprockell program in the {@link #OUTPUT_PATH}
	 * directory with the specified name
	 */
	public void writeToFile(String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(OUTPUT_PATH + fileName + ".hs");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("import Sprockell");
		printWriter.println();
		printWriter.println("prog :: [Instruction]");
		printWriter.println("prog = [ ");
		boolean first = true;
		for (Instr i : instrList) {
			if (first) {
				printWriter.println("   " + i.toString());
				first = false;
			} else {
				printWriter.println(" , " + i.toString());
			}
		}
		printWriter.println(" ]");
		printWriter.println();
		printWriter.print("main = run [prog");
		for (int i = 0; i < threadCount; i++) {
			printWriter.print(",prog");
		}
		printWriter.print("]");
		printWriter.close();
	}

	/** Path to the the output directory */
	private final String OUTPUT_PATH = "src/output/";
}
