// Generated from PickleCannon.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PickleCannonParser}.
 */
public interface PickleCannonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PickleCannonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PickleCannonParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#proc}.
	 * @param ctx the parse tree
	 */
	void enterProc(PickleCannonParser.ProcContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#proc}.
	 * @param ctx the parse tree
	 */
	void exitProc(PickleCannonParser.ProcContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varPar}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 */
	void enterVarPar(PickleCannonParser.VarParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varPar}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 */
	void exitVarPar(PickleCannonParser.VarParContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayPar}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 */
	void enterArrayPar(PickleCannonParser.ArrayParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayPar}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 */
	void exitArrayPar(PickleCannonParser.ArrayParContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(PickleCannonParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(PickleCannonParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleVarStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterSimpleVarStat(PickleCannonParser.SimpleVarStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleVarStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitSimpleVarStat(PickleCannonParser.SimpleVarStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayVarStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterArrayVarStat(PickleCannonParser.ArrayVarStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayVarStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitArrayVarStat(PickleCannonParser.ArrayVarStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignStat(PickleCannonParser.AssignStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignStat(PickleCannonParser.AssignStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(PickleCannonParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(PickleCannonParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(PickleCannonParser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(PickleCannonParser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forkStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterForkStat(PickleCannonParser.ForkStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forkStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitForkStat(PickleCannonParser.ForkStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterJoinStat(PickleCannonParser.JoinStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitJoinStat(PickleCannonParser.JoinStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterSyncStat(PickleCannonParser.SyncStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitSyncStat(PickleCannonParser.SyncStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlockStat(PickleCannonParser.BlockStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlockStat(PickleCannonParser.BlockStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintStat(PickleCannonParser.PrintStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintStat(PickleCannonParser.PrintStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterCallStat(PickleCannonParser.CallStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitCallStat(PickleCannonParser.CallStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 */
	void enterIdTarget(PickleCannonParser.IdTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 */
	void exitIdTarget(PickleCannonParser.IdTargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 */
	void enterArrayTarget(PickleCannonParser.ArrayTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 */
	void exitArrayTarget(PickleCannonParser.ArrayTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(PickleCannonParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(PickleCannonParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(PickleCannonParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(PickleCannonParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(PickleCannonParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(PickleCannonParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(PickleCannonParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(PickleCannonParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(PickleCannonParser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(PickleCannonParser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompExpr(PickleCannonParser.CompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompExpr(PickleCannonParser.CompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrfExpr(PickleCannonParser.PrfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrfExpr(PickleCannonParser.PrfExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(PickleCannonParser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(PickleCannonParser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(PickleCannonParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(PickleCannonParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(PickleCannonParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(PickleCannonParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumExpr(PickleCannonParser.NumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumExpr(PickleCannonParser.NumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpr(PickleCannonParser.PlusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpr(PickleCannonParser.PlusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(PickleCannonParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(PickleCannonParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void enterPrfOp(PickleCannonParser.PrfOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void exitPrfOp(PickleCannonParser.PrfOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#multOp}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(PickleCannonParser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#multOp}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(PickleCannonParser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void enterPlusOp(PickleCannonParser.PlusOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void exitPlusOp(PickleCannonParser.PlusOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void enterBoolOp(PickleCannonParser.BoolOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void exitBoolOp(PickleCannonParser.BoolOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link PickleCannonParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(PickleCannonParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link PickleCannonParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(PickleCannonParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(PickleCannonParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(PickleCannonParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(PickleCannonParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(PickleCannonParser.BoolTypeContext ctx);
}