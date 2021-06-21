package generator;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import checker.Result;
import checker.Type;
import generator.Addr.AddrImmDI;
import generator.Operator.Oper;
import generator.Target.TargetType;
import grammar.PickleCannonBaseVisitor;
import grammar.PickleCannonParser.ArgsContext;
import grammar.PickleCannonParser.ArrayExprContext;
import grammar.PickleCannonParser.ArrayTargetContext;
import grammar.PickleCannonParser.ArrayVarStatContext;
import grammar.PickleCannonParser.AssignStatContext;
import grammar.PickleCannonParser.BlockContext;
import grammar.PickleCannonParser.BlockStatContext;
import grammar.PickleCannonParser.BoolExprContext;
import grammar.PickleCannonParser.CallStatContext;
import grammar.PickleCannonParser.CompExprContext;
import grammar.PickleCannonParser.ExprContext;
import grammar.PickleCannonParser.FalseExprContext;
import grammar.PickleCannonParser.ForkStatContext;
import grammar.PickleCannonParser.IdExprContext;
import grammar.PickleCannonParser.IdTargetContext;
import grammar.PickleCannonParser.IfStatContext;
import grammar.PickleCannonParser.IndexExprContext;
import grammar.PickleCannonParser.JoinStatContext;
import grammar.PickleCannonParser.MultExprContext;
import grammar.PickleCannonParser.NumExprContext;
import grammar.PickleCannonParser.ParExprContext;
import grammar.PickleCannonParser.PlusExprContext;
import grammar.PickleCannonParser.PrfExprContext;
import grammar.PickleCannonParser.PrintStatContext;
import grammar.PickleCannonParser.ProcContext;
import grammar.PickleCannonParser.ProgramContext;
import grammar.PickleCannonParser.SimpleVarStatContext;
import grammar.PickleCannonParser.StatContext;
import grammar.PickleCannonParser.SyncStatContext;
import grammar.PickleCannonParser.TrueExprContext;
import grammar.PickleCannonParser.WhileStatContext;

public class Generator extends PickleCannonBaseVisitor<Instr>{

	/** The outcome of the checker phase. */
	private Result checkResult;
	
	/** The program being built. */
	private Program prog;
	
	/** The array indicating if register is taken*/
	private boolean isRegisterTaken[];
	
	/** The number of inserted instructions*/
	private int instructionCount;
	
	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;
	
	/** Association of the first instruction number in the node with a node*/
	private ParseTreeProperty<Integer> instrID;
	
	
	
	public Program generate(ParseTree tree, Result checkResult) {
		this.prog = new Program();
		this.checkResult = checkResult;
		this.isRegisterTaken = new boolean[8];
		// reg0 is taken
		this.isRegisterTaken[0]=true;
		// regSprID is taken
		this.isRegisterTaken[1]=true;
		// regA is allocated for ARP
		this.isRegisterTaken[2]=true;
		this.instructionCount=0;
		this.regs = new ParseTreeProperty<>();
		this.instrID = new ParseTreeProperty<>();
		tree.accept(this);
		return prog;
	}
	
	// Override the visitor methods
	@Override
	public Instr visitProgram(ProgramContext ctx) {
		System.out.println("Visit program");
		for(ProcContext proc : ctx.proc()) {
			visit(proc);
		}
		visit(ctx.block());
		emit(OpCode.EndProg);
		return null;
	}
	
	@Override
	public Instr visitProc(ProcContext ctx) {
		System.out.println("Visit procedure");
		visit(ctx.block());
		return null;
	}
	
	@Override
	public Instr visitBlock(BlockContext ctx) {
		System.out.println("Visit block");
		for(StatContext stat : ctx.stat()) {
			visit(stat);
		}
		if(ctx.stat().size()>0) {
			this.instrID.put(ctx, this.instrID.get(ctx.stat(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitSimpleVarStat(SimpleVarStatContext ctx) {
		System.out.println("Visit simpleVarStat");
		//!!! IMPORTANT !!! does not work for shared
		if(ctx.expr()!=null) {
			visit(ctx.expr());
			if(isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr,regs.get(ctx.expr()), offset(ctx.ID()));
			}
			else {
				Instr i = emit(OpCode.Store,regs.get(ctx.expr()), offset(ctx.ID()));
			}
			freeReg(ctx.expr());
		}
		else {
			if(isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr,regs.get(ctx.expr()), offset(ctx.ID()));
			}
			else {
				Instr i = emit(OpCode.Store,new Reg(0), offset(ctx.ID()));
			}
		}
		return null;
	}
	
	@Override
	public Instr visitArrayVarStat(ArrayVarStatContext ctx) {
		System.out.println("Visit arrayVarStat");
		if(ctx.expr()!=null) {
			visit(ctx.expr());
		}
		return null;
	}
	
	@Override
	public Instr visitAssignStat(AssignStatContext ctx) {
		System.out.println("Visit assignStat");
		visit(ctx.expr());
		visit(ctx.target());
		if(isShared(ctx.target())) {
			Instr i = emit(OpCode.WriteInstr,regs.get(ctx.expr()), new Addr(AddrImmDI.IndAddr, regs.get(ctx.target()).getId()));
		}
		else {
			Instr i = emit(OpCode.Store,regs.get(ctx.expr()), new Addr(AddrImmDI.IndAddr, regs.get(ctx.target()).getId()));
		}
		freeReg(ctx.target());
		freeReg(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitIfStat(IfStatContext ctx) {
		System.out.println("Visit ifStat");
		//Condition
		visit(ctx.expr());
		Reg r = reg(ctx);
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), this.regs.get(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		//Then
		visit(ctx.block(0));
		Integer jumpID = this.instructionCount;
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		//Else
		Integer elseStart = this.instructionCount;
		if(ctx.block().size()>1) {
			visit(ctx.block(1));
		}
		Integer endStart = this.instructionCount;
		Instr i4 = emit(OpCode.Nop);
		this.prog.updateInstr(branchID, new Instr(OpCode.Branch, r, new Target(TargetType.Abs, elseStart)));
		this.prog.updateInstr(jumpID, new Instr(OpCode.Jump, new Target(TargetType.Abs, endStart)));
		return null;
	}
	
	@Override
	public Instr visitWhileStat(WhileStatContext ctx) {
		System.out.println("Visit whileStat");
		//Condition
		Integer start = this.instructionCount;
		visit(ctx.expr());
		Reg r = reg(ctx);
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), this.regs.get(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		//Body
		visit(ctx.block());
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, start));
		Instr i4 = emit(OpCode.Nop);
		this.prog.updateInstr(branchID, new Instr(OpCode.Branch, r, new Target(TargetType.Abs, instructionCount-1)));
		return null;
	}
	
	@Override
	public Instr visitForkStat(ForkStatContext ctx) {
		System.out.println("Visit forkStat");
		visit(ctx.block());
		this.instrID.put(ctx, this.instrID.get(ctx.block()));
		return null;
	}
	
	@Override
	public Instr visitJoinStat(JoinStatContext ctx) {
		System.out.println("Visit joinStat");
		return null;
	}
	
	@Override
	public Instr visitSyncStat(SyncStatContext ctx) {
		System.out.println("Visit syncStat");
		visit(ctx.block());
		this.instrID.put(ctx, this.instrID.get(ctx.block()));
		return null;
	}
	
	@Override
	public Instr visitBlockStat(BlockStatContext ctx) {
		System.out.println("Visit blockStat");
		visit(ctx.block());
		this.instrID.put(ctx, this.instrID.get(ctx.block()));
		return null;
	}
	
	@Override
	public Instr visitPrintStat(PrintStatContext ctx) {
		System.out.println("Visit printStat");
		visit(ctx.expr());
		this.instrID.put(ctx, this.instrID.get(ctx.expr()));
		Instr i = emit(OpCode.WriteInstr, this.regs.get(ctx.expr()), Addr.NUMBER_IO);
		freeReg(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitCallStat(CallStatContext ctx) {
		System.out.println("Visit callStat");
		visit(ctx.args());
		this.instrID.put(ctx, this.instrID.get(ctx.args()));
		return null;
	}
	
	@Override
	public Instr visitIdTarget(IdTargetContext ctx) {
		System.out.println("Visit idTarget");
		this.instrID.put(ctx, instructionCount);
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
		return null;
	}
	
	@Override
	public Instr visitArrayTarget(ArrayTargetContext ctx) {
		System.out.println("Visit arrayTarget");
		visit(ctx.expr());
		this.instrID.put(ctx, this.instrID.get(ctx.expr()));
		return null;
	}
	
	@Override
	public Instr visitArgs(ArgsContext ctx) {
		System.out.println("Visit args");
		for(ExprContext expr : ctx.expr()) {
			visit(expr);
		}
		if(ctx.expr().size()>0) {
			this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitPrfExpr(PrfExprContext ctx) {
		System.out.println("Visit prfExpr");
		visit(ctx.expr());
		this.instrID.put(ctx, this.instrID.get(ctx.expr()));
		return null;
	}
	
	@Override
	public Instr visitMultExpr(MultExprContext ctx) {
		System.out.println("Visit multExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		if(ctx.multOp().STAR()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Mul),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitPlusExpr(PlusExprContext ctx) {
		System.out.println("Visit plusExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		if(ctx.plusOp().PLUS()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Add),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		else if(ctx.plusOp().MINUS()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Sub),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitCompExpr(CompExprContext ctx) {
		System.out.println("Visit compExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		if(ctx.compOp().EQ()!=null) {
			if(this.checkResult.getType(ctx.expr(0))==Type.INT||
					this.checkResult.getType(ctx.expr(0))==Type.BOOL) {
				Instr i = emit(OpCode.Compute, new Operator(Oper.Equal),
						this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
				freeReg(ctx.expr(1));
				setReg(ctx, this.regs.get(ctx.expr(0)));
			}
		}
		else if(ctx.compOp().NE()!=null) {
			if(this.checkResult.getType(ctx.expr(0))==Type.INT||
					this.checkResult.getType(ctx.expr(0))==Type.BOOL) {
				Instr i = emit(OpCode.Compute, new Operator(Oper.NEq),
						this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
				freeReg(ctx.expr(1));
				setReg(ctx, this.regs.get(ctx.expr(0)));
			}
		}
		else if(ctx.compOp().LE()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.LtE),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		else if(ctx.compOp().LT()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Lt),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		else if(ctx.compOp().GE()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.GtE),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		else if(ctx.compOp().GT()!=null){
			Instr i = emit(OpCode.Compute, new Operator(Oper.Gt),
					this.regs.get(ctx.expr(0)), this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitBoolExpr(BoolExprContext ctx) {
		System.out.println("Visit boolExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		if(ctx.boolOp().AND()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.And), this.regs.get(ctx.expr(0)),
					this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		else if(ctx.boolOp().OR()!=null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Or), this.regs.get(ctx.expr(0)),
					this.regs.get(ctx.expr(1)), this.regs.get(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, this.regs.get(ctx.expr(0)));
		}
		return null;
	}
	
	@Override
	public Instr visitParExpr(ParExprContext ctx) {
		System.out.println("Visit parExpr");
		visit(ctx.expr());
		this.instrID.put(ctx, this.instrID.get(ctx.expr()));
		setReg(ctx, regs.get(ctx.expr()));
		return null;
	}
	
	@Override
	public Instr visitIdExpr(IdExprContext ctx) {
		System.out.println("Visit idExpr");
		if(isShared(ctx)) {
			Instr i1 = emit(OpCode.ReadInstr, offset(ctx));
			Instr i2 = emit(OpCode.Receive, reg(ctx));
		}
		else {
			Instr i = emit(OpCode.Load, offset(ctx), reg(ctx));
		}
		return null;
	}
	
	@Override
	public Instr visitNumExpr(NumExprContext ctx) {
		System.out.println("Visit numExpr");
		this.instrID.put(ctx, instructionCount);
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, Integer.parseInt(ctx.NUM().getText())), reg(ctx));
		return null;
	}
	
	@Override
	public Instr visitTrueExpr(TrueExprContext ctx) {
		System.out.println("Visit trueExpr");
		this.instrID.put(ctx, instructionCount);
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 1), reg(ctx));
		return null;
	}
	
	@Override
	public Instr visitFalseExpr(FalseExprContext ctx) {
		System.out.println("Visit falseExpr");
		setReg(ctx, new Reg(0));
		return null;
	}
	
	@Override
	public Instr visitIndexExpr(IndexExprContext ctx) {
		System.out.println("Visit indexExpr");
		visit(ctx.expr());
		freeReg(ctx.expr());
		this.instrID.put(ctx, this.instrID.get(ctx.expr()));
		return null;
	}
	
	@Override
	public Instr visitArrayExpr(ArrayExprContext ctx) {
		System.out.println("Visit arrayExpr");
		for(ExprContext expr : ctx.expr()) {
			visit(expr);
			freeReg(expr);
		}
		if(ctx.expr().size()>0) {
			this.instrID.put(ctx, this.instrID.get(ctx.expr(0)));
		}
		return null;
	}
	
	private Instr emit(OpCode opCode, Operand... args) {
		Instr result = new Instr(opCode, args);
		this.prog.addInstr(result);
		this.instructionCount++;
		return result;
	}
	
	private boolean isRegisterTaken(int i) {
		return this.isRegisterTaken[i];
	}
	
	private int getFreeRegister() {
		for(int i=3;i<this.isRegisterTaken.length;i++) {
			if(!this.isRegisterTaken[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private void freeUpRegister(int i) {
		this.isRegisterTaken[i]=false;
	}
	
	private void lockRegister(int i) {
		if(this.isRegisterTaken[i]) {
			throw new RuntimeException("Register is already locked");
		}
		this.isRegisterTaken[i]=true;
	}
	
	private Reg reg(ParseTree node) {
		Reg result = this.regs.get(node);
		if (result == null) {
			int regId = getFreeRegister();
			if(regId==-1) {
				throw new RuntimeException("All registers are taken");
			}
			result = new Reg(regId);
			lockRegister(regId);
			this.regs.put(node, result);
		}
		return result;
	}
	
	private void freeReg(ParseTree node) {
		Reg result = this.regs.get(node);
		if(result == null) {
			throw new RuntimeException("This node does not have a register");
		}
		else {
			this.regs.removeFrom(node);
			this.freeUpRegister(result.getId());
		}
	}
	
	/** Assigns a register to a given parse tree node. */
	private void setReg(ParseTree node, Reg reg) {
		this.regs.put(node, reg);
	}
	
	/** Retrieves the offset of a variable node from the checker result,
	 * wrapped in a {@link Num} operand. */
	private Addr offset(ParseTree node) {
		return new Addr(AddrImmDI.DirAddr,this.checkResult.getOffset(node));
	}
	
	/** Returns a boolean is node stored in shared memory*/
	private boolean isShared(ParseTree node) {
		Boolean result = this.checkResult.getIsShared(node);
		if(result == null) {
			throw new RuntimeException("Node does not have declared is shared property");
		}
		return result;
	}
	
}
