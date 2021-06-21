package tests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import generator.Addr;
import generator.Addr.AddrImmDI;
import generator.Instr;
import generator.OpCode;
import generator.Operator;
import generator.Operator.Oper;
import generator.Program;
import generator.Reg;

public class WriterTest {
	
	@Test
	public void writeTest() {
		Program p = new Program(0);
		p.addInstr(new Instr(OpCode.Compute, new Operator(Oper.Add), new Reg(1), new Reg(2), new Reg(3)));
		p.addInstr(new Instr(OpCode.Load, new Addr(AddrImmDI.DirAddr, 3), new Reg(3)));
		p.addInstr(new Instr(OpCode.EndProg));
		try {
			p.writeToFile("test1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
}
