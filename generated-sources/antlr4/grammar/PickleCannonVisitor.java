// Generated from PickleCannon.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PickleCannonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PickleCannonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(PickleCannonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#proc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProc(PickleCannonParser.ProcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varArg}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarArg(PickleCannonParser.VarArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayArg}
	 * labeled alternative in {@link PickleCannonParser#pars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayArg(PickleCannonParser.ArrayArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(PickleCannonParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDecl}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(PickleCannonParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStat(PickleCannonParser.AssignStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStat(PickleCannonParser.IfStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStat(PickleCannonParser.WhileStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forkStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForkStat(PickleCannonParser.ForkStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code joinStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinStat(PickleCannonParser.JoinStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code syncStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSyncStat(PickleCannonParser.SyncStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStat(PickleCannonParser.BlockStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStat(PickleCannonParser.CallStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printStat}
	 * labeled alternative in {@link PickleCannonParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStat(PickleCannonParser.PrintStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdTarget(PickleCannonParser.IdTargetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayTarget}
	 * labeled alternative in {@link PickleCannonParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayTarget(PickleCannonParser.ArrayTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(PickleCannonParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExpr(PickleCannonParser.IndexExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(PickleCannonParser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(PickleCannonParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueExpr(PickleCannonParser.TrueExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompExpr(PickleCannonParser.CompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prfExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfExpr(PickleCannonParser.PrfExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseExpr(PickleCannonParser.FalseExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(PickleCannonParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(PickleCannonParser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumExpr(PickleCannonParser.NumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plusExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusExpr(PickleCannonParser.PlusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link PickleCannonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(PickleCannonParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#prfOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrfOp(PickleCannonParser.PrfOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#multOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultOp(PickleCannonParser.MultOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#plusOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusOp(PickleCannonParser.PlusOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#boolOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOp(PickleCannonParser.BoolOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link PickleCannonParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(PickleCannonParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(PickleCannonParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link PickleCannonParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(PickleCannonParser.BoolTypeContext ctx);
}