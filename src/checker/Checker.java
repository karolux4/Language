package checker;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import checker.Type.Array;
import compiler.ParseException;
import grammar.PickleCannonBaseListener;
import grammar.PickleCannonParser.ArrayExprContext;
import grammar.PickleCannonParser.ArrayTargetContext;
import grammar.PickleCannonParser.ArrayVarStatContext;
import grammar.PickleCannonParser.AssignStatContext;
import grammar.PickleCannonParser.BlockContext;
import grammar.PickleCannonParser.BlockStatContext;
import grammar.PickleCannonParser.BoolExprContext;
import grammar.PickleCannonParser.BoolTypeContext;
import grammar.PickleCannonParser.CompExprContext;
import grammar.PickleCannonParser.FalseExprContext;
import grammar.PickleCannonParser.ForkStatContext;
import grammar.PickleCannonParser.IdExprContext;
import grammar.PickleCannonParser.IdTargetContext;
import grammar.PickleCannonParser.IfStatContext;
import grammar.PickleCannonParser.IndexExprContext;
import grammar.PickleCannonParser.IntTypeContext;
import grammar.PickleCannonParser.JoinStatContext;
import grammar.PickleCannonParser.MultExprContext;
import grammar.PickleCannonParser.NumExprContext;
import grammar.PickleCannonParser.ParExprContext;
import grammar.PickleCannonParser.PlusExprContext;
import grammar.PickleCannonParser.PrfExprContext;
import grammar.PickleCannonParser.PrintStatContext;
import grammar.PickleCannonParser.ProgramContext;
import grammar.PickleCannonParser.SimpleVarStatContext;
import grammar.PickleCannonParser.SyncStatContext;
import grammar.PickleCannonParser.TrueExprContext;
import grammar.PickleCannonParser.WhileStatContext;

public class Checker extends PickleCannonBaseListener {
	/** Result of the latest call of {@link #check}. */
	private Result result;
	/** Symbol table for the latest call of {@link #check}. */
	private SymbolTable table;
	/** List of errors collected in the latest call of {@link #check}. */
	private List<String> errors;

	/**
	 * Runs this checker on a given parse tree, and returns the checker result.
	 * 
	 * @throws ParseException if an error was found during checking.
	 */
	public Result check(ParseTree tree) throws ParseException {
		this.table = new SymbolTable();
		this.result = new Result();
		this.errors = new ArrayList<>();
		new ParseTreeWalker().walk(this, tree);
		if (hasErrors()) {
			throw new ParseException(getErrors());
		}
		return this.result;
	}
	
	@Override
	public void exitProgram(ProgramContext ctx) {
		setEntry(ctx,entry(ctx.block()));
	}
	
	@Override
	public void enterBlock(BlockContext ctx) {
		this.table.openNestedLevel();;
	}
	
	@Override
	public void exitBlock(BlockContext ctx) {
		this.table.closeNestedLevel();
		if(ctx.stat().size()>0) {
			setEntry(ctx,entry(ctx.stat(0)));
		}
	}

	@Override
	public void exitSimpleVarStat(SimpleVarStatContext ctx) {
		if(ctx.expr()!=null) {
			checkType(ctx.type(),getType(ctx.expr()));
		}
		boolean isAdded = this.table.put(ctx.ID().getText(), getType(ctx.type()));
		if(!isAdded) {
			addError(ctx, "Variable '%s' is already decleared in this scope", ctx.ID().getText());
		}
		else {
			setOffset(ctx.ID(),this.table.offset(ctx.ID().getText()));
			setEntry(ctx, ctx.type());
		}
	}
	
	@Override
	public void exitArrayVarStat(ArrayVarStatContext ctx) {
		//!!!NEEDS IMPLEMENTATION
		if(Integer.parseInt(ctx.NUM().getText())<=0) {
			addError(ctx, "Array '%s' size must be greater than 0", ctx.ID().getText());
		}
		else {
			Type array = new Type.Array(Integer.parseInt(ctx.NUM().getText()),getType(ctx.type()));
			boolean isAdded = this.table.put(ctx.ID().getText(), array);
			if(!isAdded) {
				addError(ctx, "Variable '%s' is already decleared in this scope", ctx.ID().getText());
			}
			else {
				setOffset(ctx.ID(),this.table.offset(ctx.ID().getText()));
				setEntry(ctx,ctx.type());
				if(ctx.expr()!=null) {
					checkType(ctx.expr(), array);
				}
			}
		}
	}
	
	@Override
	public void exitAssignStat(AssignStatContext ctx) {
		checkType(ctx.target(), getType(ctx.expr()));
		setEntry(ctx,entry(ctx.expr()));
	}
	
	@Override
	public void exitIfStat(IfStatContext ctx) {
		checkType(ctx.expr(), Type.BOOL);
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitWhileStat(WhileStatContext ctx) {
		checkType(ctx.expr(), Type.BOOL);
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitForkStat(ForkStatContext ctx) {
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void exitJoinStat(JoinStatContext ctx) {
		setEntry(ctx, ctx);
	}

	@Override
	public void exitSyncStat(SyncStatContext ctx) {
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void enterBlockStat(BlockStatContext ctx) {
		this.table.openScope();
	}
	
	@Override
	public void exitBlockStat(BlockStatContext ctx) {
		this.table.closeScope();
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void exitPrintStat(PrintStatContext ctx) {
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitIdTarget(IdTargetContext ctx) {
		String id = ctx.ID().getText();
		Type type = this.table.type(id);
		if (type == null) {
			addError(ctx, "Variable '%s' not declared in this scope", id);
		} else {
			setType(ctx, type);
			setOffset(ctx, this.table.offset(id));
			setEntry(ctx, ctx);
		}
	}

	@Override
	public void exitArrayTarget(ArrayTargetContext ctx) {
		String id = ctx.ID().getText();
		Type type = this.table.type(id);
		if (type == null) {
			addError(ctx, "Array '%s' not declared in this scope", id);
		}
		else if(!(type instanceof Type.Array)){
			addError(ctx, "Variable '%s' is not an array", id);
		}
		else {
			checkType(ctx.expr(), Type.INT);
			setType(ctx, ((Type.Array) type).getElemType());
			setOffset(ctx, this.table.offset(id));
			setEntry(ctx, ctx.expr());
		}
	}

	@Override
	public void exitPrfExpr(PrfExprContext ctx) {
		Type type;
		if (ctx.prfOp().MINUS() != null) {
			type = Type.INT;
		} else {
			assert ctx.prfOp().NOT() != null;
			type = Type.BOOL;
		}
		checkType(ctx.expr(), type);
		setType(ctx, type);
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitMultExpr(MultExprContext ctx) {
		checkType(ctx.expr(0), Type.INT);
		checkType(ctx.expr(1), Type.INT);
		setType(ctx, Type.INT);
		setEntry(ctx, entry(ctx.expr(0)));
	}

	@Override
	public void exitPlusExpr(PlusExprContext ctx) {
		checkType(ctx.expr(0), Type.INT);
		checkType(ctx.expr(1), Type.INT);
		setType(ctx, Type.INT);
		setEntry(ctx, entry(ctx.expr(0)));
	}

	@Override
	public void exitCompExpr(CompExprContext ctx) {
		if (ctx.compOp().EQ() != null || ctx.compOp().NE() != null) {
			checkType(ctx.expr(1), getType(ctx.expr(0)));
		} else {
			checkType(ctx.expr(0), Type.INT);
			checkType(ctx.expr(1), Type.INT);
		}
		setType(ctx, Type.BOOL);
		setEntry(ctx, entry(ctx.expr(0)));
	}

	@Override
	public void exitBoolExpr(BoolExprContext ctx) {
		checkType(ctx.expr(0), Type.BOOL);
		checkType(ctx.expr(1), Type.BOOL);
		setType(ctx, Type.BOOL);
		setEntry(ctx, entry(ctx.expr(0)));
	}

	@Override
	public void exitParExpr(ParExprContext ctx) {
		setType(ctx, getType(ctx.expr()));
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitIdExpr(IdExprContext ctx) {
		String id = ctx.ID().getText();
		Type type = this.table.type(id);
		if (type == null) {
			addError(ctx, "Variable '%s' not declared in this scope", id);
		} else {
			setType(ctx, type);
			setOffset(ctx, this.table.offset(id));
			setEntry(ctx, ctx);
		}
	}

	@Override
	public void exitNumExpr(NumExprContext ctx) {
		setType(ctx, Type.INT);
		setEntry(ctx, ctx);
	}

	@Override
	public void exitTrueExpr(TrueExprContext ctx) {
		setType(ctx, Type.BOOL);
		setEntry(ctx, ctx);
	}

	@Override
	public void exitFalseExpr(FalseExprContext ctx) {
		setType(ctx, Type.BOOL);
		setEntry(ctx, ctx);
	}

	@Override
	public void exitIndexExpr(IndexExprContext ctx) {
		String id = ctx.ID().getText();
		Type type = this.table.type(id);
		if (type == null) {
			addError(ctx, "Array '%s' not declared in this scope", id);
		}
		else if(!(type instanceof Type.Array)){
			addError(ctx, "Variable '%s' is not an array", id);
		}
		else {
			checkType(ctx.expr(), Type.INT);
			setType(ctx, ((Type.Array) type).getElemType());
			setOffset(ctx, this.table.offset(id));
			setEntry(ctx, ctx.expr());
		}
	}

	@Override
	public void exitArrayExpr(ArrayExprContext ctx) {
		Type type = getType(ctx.expr(0));
		for(int i=1;i<ctx.expr().size();i++) {
			checkType(ctx.expr(i),type);
		}
		Type array = new Type.Array(ctx.expr().size(),type);
		setType(ctx, array);
		setEntry(ctx, ctx.expr(0));
		
	}

	@Override
	public void exitIntType(IntTypeContext ctx) {
		setType(ctx, Type.INT);
		setEntry(ctx, ctx);
	}

	@Override
	public void exitBoolType(BoolTypeContext ctx) {
		setType(ctx, Type.BOOL);
		setEntry(ctx, ctx);
	}

	/** Indicates if any errors were encountered in this tree listener. */
	public boolean hasErrors() {
		return !getErrors().isEmpty();
	}

	/** Returns the list of errors collected in this tree listener. */
	public List<String> getErrors() {
		return this.errors;
	}

	/**
	 * Checks the inferred type of a given parse tree, and adds an error if it does
	 * not correspond to the expected type.
	 */
	private void checkType(ParserRuleContext node, Type expected) {
		Type actual = getType(node);
		if (actual == null || expected == null) {
			//throw new IllegalArgumentException("Missing inferred type of " + node.getText());
		}
		else if (!actual.equals(expected)) {
			addError(node, "Expected type '%s' but found '%s'", expected, actual);
		}
	}

	/**
	 * Records an error at a given parse tree node.
	 * 
	 * @param ctx     the parse tree node at which the error occurred
	 * @param message the error message
	 * @param args    arguments for the message, see {@link String#format}
	 */
	private void addError(ParserRuleContext node, String message, Object... args) {
		addError(node.getStart(), message, args);
	}

	/**
	 * Records an error at a given token.
	 * 
	 * @param token   the token at which the error occurred
	 * @param message the error message
	 * @param args    arguments for the message, see {@link String#format}
	 */
	private void addError(Token token, String message, Object... args) {
		int line = token.getLine();
		int column = token.getCharPositionInLine();
		message = String.format(message, args);
		message = String.format("Line %d:%d - %s", line, column, message);
		this.errors.add(message);
	}

	/** Convenience method to add an offset to the result. */
	private void setOffset(ParseTree node, Integer offset) {
		this.result.setOffset(node, offset);
	}

	/** Convenience method to add a type to the result. */
	private void setType(ParseTree node, Type type) {
		this.result.setType(node, type);
	}

	/** Returns the type of a given expression or type node. */
	private Type getType(ParseTree node) {
		return this.result.getType(node);
	}

	/** Convenience method to add a flow graph entry to the result. */
	private void setEntry(ParseTree node, ParserRuleContext entry) {
		if (entry == null) {
			//throw new IllegalArgumentException("Null flow graph entry");
		}
		else {
			this.result.setEntry(node, entry);
		}
	}

	/** Returns the flow graph entry of a given expression or statement. */
	private ParserRuleContext entry(ParseTree node) {
		return this.result.getEntry(node);
	}

}
