package generator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Program {

	private final List<Instr> instrList;
	
	//private int threadCount
	
	public Program() {
		this.instrList = new ArrayList<>();
	}
	
	public void addInstr(Instr instr) {
		this.instrList.add(instr);
	}
	
	public Instr getInstr(int i) {
		return this.instrList.get(i);
	}
	
	public void updateInstr(int i, Instr ins) {
		this.instrList.set(i, ins);
	}
	
	public void writeToFile(String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(OUTPUT_PATH+fileName+".hs");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("import Sprockell");
		printWriter.println();
		printWriter.println("prog :: [Instruction]");
		printWriter.println("prog = [ ");
		boolean first=true;
		for(Instr i : instrList) {
			if(first) {
				printWriter.println("   "+i.toString());
				first=false;
			}
			else {
				printWriter.println(" , "+i.toString());
			}
		}
		printWriter.println(" ]");
		printWriter.println();
		printWriter.println("main = run [prog]");
		printWriter.close();
	}
	
	private final String OUTPUT_PATH = "src/output/";
}
