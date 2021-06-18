package generator;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import checker.Result;
import grammar.PickleCannonBaseVisitor;
import grammar.PickleCannonParser.ArgsContext;
import grammar.PickleCannonParser.ArrayExprContext;
import grammar.PickleCannonParser.ArrayParContext;
import grammar.PickleCannonParser.ArrayTargetContext;
import grammar.PickleCannonParser.ArrayVarStatContext;
import grammar.PickleCannonParser.AssignStatContext;
import grammar.PickleCannonParser.BlockContext;
import grammar.PickleCannonParser.BlockStatContext;
import grammar.PickleCannonParser.BoolExprContext;
import grammar.PickleCannonParser.CallStatContext;
import grammar.PickleCannonParser.CompExprContext;
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
import grammar.PickleCannonParser.SyncStatContext;
import grammar.PickleCannonParser.TrueExprContext;
import grammar.PickleCannonParser.VarParContext;
import grammar.PickleCannonParser.WhileStatContext;

public class Generator extends PickleCannonBaseVisitor<Instr>{

	/** The outcome of the checker phase. */
	private Result checkResult;
	
	/** The program being built. */
	private Program prog;
	
	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;
	
	public Program generate(ParseTree tree, Result checkResult) {
		this.prog = new Program();
		this.checkResult = checkResult;
		this.regs = new ParseTreeProperty<>();
		tree.accept(this);
		return null;
	}
	
	// Override the visitor methods
	@Override
	public Instr visitProgram(ProgramContext ctx) {
		System.out.println("Visit program");
		return null;
	}
	
	@Override
	public Instr visitProc(ProcContext ctx) {
		System.out.println("Visit procedure");
		return null;
	}
	
	@Override
	public Instr visitVarPar(VarParContext ctx) {
		System.out.println("Visit varPar");
		return null;
	}
	
	@Override
	public Instr visitArrayPar(ArrayParContext ctx) {
		System.out.println("Visit arrayPar");
		return null;
	}
	
	@Override
	public Instr visitBlock(BlockContext ctx) {
		System.out.println("Visit block");
		return null;
	}
	
	@Override
	public Instr visitSimpleVarStat(SimpleVarStatContext ctx) {
		System.out.println("Visit simpleVarStat");
		return null;
	}
	
	@Override
	public Instr visitArrayVarStat(ArrayVarStatContext ctx) {
		System.out.println("Visit arrayVarStat");
		return null;
	}
	
	@Override
	public Instr visitAssignStat(AssignStatContext ctx) {
		System.out.println("Visit assignStat");
		return null;
	}
	
	@Override
	public Instr visitIfStat(IfStatContext ctx) {
		System.out.println("Visit ifStat");
		return null;
	}
	
	@Override
	public Instr visitWhileStat(WhileStatContext ctx) {
		System.out.println("Visit whileStat");
		return null;
	}
	
	@Override
	public Instr visitForkStat(ForkStatContext ctx) {
		System.out.println("Visit forkStat");
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
		return null;
	}
	
	@Override
	public Instr visitBlockStat(BlockStatContext ctx) {
		System.out.println("Visit blockStat");
		return null;
	}
	
	@Override
	public Instr visitPrintStat(PrintStatContext ctx) {
		System.out.println("Visit printStat");
		return null;
	}
	
	@Override
	public Instr visitCallStat(CallStatContext ctx) {
		System.out.println("Visit callStat");
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
		return null;
	}
	
	@Override
	public Instr visitArgs(ArgsContext ctx) {
		System.out.println("Visit args");
		return null;
	}
	
	@Override
	public Instr visitPrfExpr(PrfExprContext ctx) {
		System.out.println("Visit prfExpr");
		return null;
	}
	
	@Override
	public Instr visitMultExpr(MultExprContext ctx) {
		System.out.println("Visit multExpr");
		return null;
	}
	
	@Override
	public Instr visitPlusExpr(PlusExprContext ctx) {
		System.out.println("Visit plusExpr");
		return null;
	}
	
	@Override
	public Instr visitCompExpr(CompExprContext ctx) {
		System.out.println("Visit compExpr");
		return null;
	}
	
	@Override
	public Instr visitBoolExpr(BoolExprContext ctx) {
		System.out.println("Visit boolExpr");
		return null;
	}
	
	@Override
	public Instr visitParExpr(ParExprContext ctx) {
		System.out.println("Visit parExpr");
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
		return null;
	}
	
	@Override
	public Instr visitArrayExpr(ArrayExprContext ctx) {
		System.out.println("Visit arrayExpr");
		return null;
	}
	
	
}
