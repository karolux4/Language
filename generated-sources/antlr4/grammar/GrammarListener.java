// Generated from Grammar.g4 by ANTLR 4.4
package grammar;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlockStat(@NotNull GrammarParser.BlockStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlockStat(@NotNull GrammarParser.BlockStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintStat(@NotNull GrammarParser.PrintStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintStat(@NotNull GrammarParser.PrintStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTrueExpr(@NotNull GrammarParser.TrueExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTrueExpr(@NotNull GrammarParser.TrueExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterSyncStat(@NotNull GrammarParser.SyncStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitSyncStat(@NotNull GrammarParser.SyncStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varArg}
	 * labeled alternative in {@link GrammarParser#pars}.
	 * @param ctx the parse tree
	 */
	void enterVarArg(@NotNull GrammarParser.VarArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varArg}
	 * labeled alternative in {@link GrammarParser#pars}.
	 * @param ctx the parse tree
	 */
	void exitVarArg(@NotNull GrammarParser.VarArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull GrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull GrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(@NotNull GrammarParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(@NotNull GrammarParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(@NotNull GrammarParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(@NotNull GrammarParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayArg}
	 * labeled alternative in {@link GrammarParser#pars}.
	 * @param ctx the parse tree
	 */
	void enterArrayArg(@NotNull GrammarParser.ArrayArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayArg}
	 * labeled alternative in {@link GrammarParser#pars}.
	 * @param ctx the parse tree
	 */
	void exitArrayArg(@NotNull GrammarParser.ArrayArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#multOp}.
	 * @param ctx the parse tree
	 */
	void enterMultOp(@NotNull GrammarParser.MultOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#multOp}.
	 * @param ctx the parse tree
	 */
	void exitMultOp(@NotNull GrammarParser.MultOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayTarget}
	 * labeled alternative in {@link GrammarParser#target}.
	 * @param ctx the parse tree
	 */
	void enterArrayTarget(@NotNull GrammarParser.ArrayTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayTarget}
	 * labeled alternative in {@link GrammarParser#target}.
	 * @param ctx the parse tree
	 */
	void exitArrayTarget(@NotNull GrammarParser.ArrayTargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompExpr(@NotNull GrammarParser.CompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompExpr(@NotNull GrammarParser.CompExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull GrammarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull GrammarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFalseExpr(@NotNull GrammarParser.FalseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFalseExpr(@NotNull GrammarParser.FalseExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void enterPlusOp(@NotNull GrammarParser.PlusOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#plusOp}.
	 * @param ctx the parse tree
	 */
	void exitPlusOp(@NotNull GrammarParser.PlusOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileStat(@NotNull GrammarParser.WhileStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileStat(@NotNull GrammarParser.WhileStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStat(@NotNull GrammarParser.IfStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStat(@NotNull GrammarParser.IfStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void enterBoolOp(@NotNull GrammarParser.BoolOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void exitBoolOp(@NotNull GrammarParser.BoolOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link GrammarParser#target}.
	 * @param ctx the parse tree
	 */
	void enterIdTarget(@NotNull GrammarParser.IdTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link GrammarParser#target}.
	 * @param ctx the parse tree
	 */
	void exitIdTarget(@NotNull GrammarParser.IdTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#proc}.
	 * @param ctx the parse tree
	 */
	void enterProc(@NotNull GrammarParser.ProcContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#proc}.
	 * @param ctx the parse tree
	 */
	void exitProc(@NotNull GrammarParser.ProcContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(@NotNull GrammarParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(@NotNull GrammarParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterCallStat(@NotNull GrammarParser.CallStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitCallStat(@NotNull GrammarParser.CallStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(@NotNull GrammarParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(@NotNull GrammarParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forkStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterForkStat(@NotNull GrammarParser.ForkStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forkStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitForkStat(@NotNull GrammarParser.ForkStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code joinStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterJoinStat(@NotNull GrammarParser.JoinStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code joinStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitJoinStat(@NotNull GrammarParser.JoinStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(@NotNull GrammarParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(@NotNull GrammarParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumExpr(@NotNull GrammarParser.NumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumExpr(@NotNull GrammarParser.NumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpr(@NotNull GrammarParser.PlusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpr(@NotNull GrammarParser.PlusExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(@NotNull GrammarParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(@NotNull GrammarParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(@NotNull GrammarParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(@NotNull GrammarParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpr(@NotNull GrammarParser.IndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpr(@NotNull GrammarParser.IndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrfExpr(@NotNull GrammarParser.PrfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrfExpr(@NotNull GrammarParser.PrfExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void enterPrfOp(@NotNull GrammarParser.PrfOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#prfOp}.
	 * @param ctx the parse tree
	 */
	void exitPrfOp(@NotNull GrammarParser.PrfOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignStat(@NotNull GrammarParser.AssignStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignStat(@NotNull GrammarParser.AssignStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(@NotNull GrammarParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(@NotNull GrammarParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDecl}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(@NotNull GrammarParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDecl}
	 * labeled alternative in {@link GrammarParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(@NotNull GrammarParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(@NotNull GrammarParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(@NotNull GrammarParser.IdExprContext ctx);
}