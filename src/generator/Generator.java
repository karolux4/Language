package generator;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import checker.Result;
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
	
	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;
	
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
		this.regs = new ParseTreeProperty<>();
		tree.accept(this);
		return null;
	}
	
	// Override the visitor methods
	@Override
	public Instr visitProgram(ProgramContext ctx) {
		System.out.println("Visit program");
		for(ProcContext proc : ctx.proc()) {
			visit(proc);
		}
		visit(ctx.block());
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
		return null;
	}
	
	@Override
	public Instr visitSimpleVarStat(SimpleVarStatContext ctx) {
		System.out.println("Visit simpleVarStat");
		if(ctx.expr()!=null) {
			visit(ctx.expr());
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
		visit(ctx.target());
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitIfStat(IfStatContext ctx) {
		System.out.println("Visit ifStat");
		//Condition
		visit(ctx.expr());
		//Then
		visit(ctx.block(0));
		//Else
		if(ctx.block().size()>1) {
			visit(ctx.block(1));
		}
		return null;
	}
	
	@Override
	public Instr visitWhileStat(WhileStatContext ctx) {
		System.out.println("Visit whileStat");
		//Condition
		visit(ctx.expr());
		//Body
		visit(ctx.block());
		return null;
	}
	
	@Override
	public Instr visitForkStat(ForkStatContext ctx) {
		System.out.println("Visit forkStat");
		visit(ctx.block());
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
		return null;
	}
	
	@Override
	public Instr visitBlockStat(BlockStatContext ctx) {
		System.out.println("Visit blockStat");
		visit(ctx.block());
		return null;
	}
	
	@Override
	public Instr visitPrintStat(PrintStatContext ctx) {
		System.out.println("Visit printStat");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitCallStat(CallStatContext ctx) {
		System.out.println("Visit callStat");
		visit(ctx.args());
		return null;
	}
	
	@Override
	public Instr visitIdTarget(IdTargetContext ctx) {
		System.out.println("Visit idTarget");
		return null;
	}
	
	@Override
	public Instr visitArrayTarget(ArrayTargetContext ctx) {
		System.out.println("Visit arrayTarget");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitArgs(ArgsContext ctx) {
		System.out.println("Visit args");
		for(ExprContext expr : ctx.expr()) {
			visit(expr);
		}
		return null;
	}
	
	@Override
	public Instr visitPrfExpr(PrfExprContext ctx) {
		System.out.println("Visit prfExpr");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitMultExpr(MultExprContext ctx) {
		System.out.println("Visit multExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		return null;
	}
	
	@Override
	public Instr visitPlusExpr(PlusExprContext ctx) {
		System.out.println("Visit plusExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		return null;
	}
	
	@Override
	public Instr visitCompExpr(CompExprContext ctx) {
		System.out.println("Visit compExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		return null;
	}
	
	@Override
	public Instr visitBoolExpr(BoolExprContext ctx) {
		System.out.println("Visit boolExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		return null;
	}
	
	@Override
	public Instr visitParExpr(ParExprContext ctx) {
		System.out.println("Visit parExpr");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitIdExpr(IdExprContext ctx) {
		System.out.println("Visit idExpr");
		return null;
	}
	
	@Override
	public Instr visitNumExpr(NumExprContext ctx) {
		System.out.println("Visit numExpr");
		return null;
	}
	
	@Override
	public Instr visitTrueExpr(TrueExprContext ctx) {
		System.out.println("Visit trueExpr");
		return null;
	}
	
	@Override
	public Instr visitFalseExpr(FalseExprContext ctx) {
		System.out.println("Visit falseExpr");
		return null;
	}
	
	@Override
	public Instr visitIndexExpr(IndexExprContext ctx) {
		System.out.println("Visit indexExpr");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Instr visitArrayExpr(ArrayExprContext ctx) {
		System.out.println("Visit arrayExpr");
		for(ExprContext expr : ctx.expr()) {
			visit(expr);
		}
		return null;
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
	
}
