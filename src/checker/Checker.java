package checker;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import checker.Type.Proc;
import compiler.ParseException;
import grammar.PickleCannonBaseListener;
import grammar.PickleCannonParser.ArrayExprContext;
import grammar.PickleCannonParser.ArrayParContext;
import grammar.PickleCannonParser.ArrayTargetContext;
import grammar.PickleCannonParser.ArrayVarStatContext;
import grammar.PickleCannonParser.AssignStatContext;
import grammar.PickleCannonParser.BlockContext;
import grammar.PickleCannonParser.BlockStatContext;
import grammar.PickleCannonParser.BoolExprContext;
import grammar.PickleCannonParser.BoolTypeContext;
import grammar.PickleCannonParser.CallStatContext;
import grammar.PickleCannonParser.CompExprContext;
import grammar.PickleCannonParser.ExprContext;
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
import grammar.PickleCannonParser.ParsContext;
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

public class Checker extends PickleCannonBaseListener {
	/** Result of the latest call of {@link #check}. */
	private Result result;
	/** Symbol table for the latest call of {@link #check}. */
	private SymbolTable table;
	/** List of errors collected in the latest call of {@link #check}. */
	private List<String> errors;
	/** Boolean used to indicate whether procedure has already opened the scope */
	private boolean scopeOpenedByFunction;
	/**
	 * Boolean used to indicate whether listener now is in main scope - cannon, not
	 * in procedures
	 */
	private boolean isInMainScope;
	/**
	 * Integer used to indicate whether listener now is inside a while cycle(s) -
	 * cannot fork in while cycles
	 */
	private int insideWhile;
	/** Integer used to indicate whether listener is inside forked thread(s) */
	private int insideFork;
	
	/** Integer used to check if one sync is already open to prevent a deadlock
	 *  from nested sync statements*/
	private int insideSync;

	private List<FunctionCall> calls;

	/**
	 * Runs this checker on a given parse tree, and returns the checker result.
	 * 
	 * @throws ParseException if an error was found during checking.
	 */
	public Result check(ParseTree tree) throws ParseException {
		this.table = new SymbolTable();
		this.result = new Result();
		this.errors = new ArrayList<>();
		this.isInMainScope = false;
		this.insideWhile = 0;
		this.insideFork = 0;
		this.insideSync=0;
		this.calls = new ArrayList<>();
		new ParseTreeWalker().walk(this, tree);
		checkCalls(this.calls);
		if (hasErrors()) {
			throw new ParseException(getErrors());
		}
		return this.result;
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void enterProc(ProcContext ctx) {
		this.table.openScope();
		this.table.openNestedLevel();
		this.scopeOpenedByFunction = true;
	}

	@Override
	public void exitProc(ProcContext ctx) {
		this.table.closeScope();
		List<Type> parameters = new ArrayList<>();
		for (ParsContext p : ctx.pars()) {
			parameters.add(getType(p));
		}
		Type type = new Type.Proc(parameters);
		boolean isAdded = this.table.put(ctx.ID().getText(), type);
		if (!isAdded) {
			addError(ctx, "Function '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			setType(ctx, type);
			setOffset(ctx, this.table.offset(ctx.ID().getText()));
		}
	}

	@Override
	public void exitVarPar(VarParContext ctx) {
		boolean isAdded = this.table.put(ctx.ID().getText(), getType(ctx.type()));
		if (!isAdded) {
			addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			setType(ctx, getType(ctx.type()));
			setOffset(ctx, this.table.offset(ctx.ID().getText()));
		}
	}

	@Override
	public void exitArrayPar(ArrayParContext ctx) {
		Type type = new Type.Array(1, getType(ctx.type()));
		boolean isAdded = this.table.put(ctx.ID().getText(), type);
		if (!isAdded) {
			addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			setType(ctx, type);
			setOffset(ctx, this.table.offset(ctx.ID().getText()));
		}
	}

	@Override
	public void enterBlock(BlockContext ctx) {
		if (!this.scopeOpenedByFunction) {
			this.table.openNestedLevel();
			if (ctx.parent instanceof ProgramContext) {
				this.isInMainScope = true;
			}
		} else {
			this.scopeOpenedByFunction = false;
		}
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		this.table.closeNestedLevel();
		if (ctx.stat().size() > 0) {
			setEntry(ctx, entry(ctx.stat(0)));
		} else {
			setEntry(ctx, ctx);
		}
	}

	@Override
	public void exitSimpleVarStat(SimpleVarStatContext ctx) {
		if (this.table.isUsed(ctx.ID().getText())) {
			addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			if (ctx.expr() != null) {
				checkType(ctx.type(), getType(ctx.expr()));
			}
			if (ctx.SHARED() != null) {
				if (!this.isInMainScope || this.table.scopeDepth() > 2) {
					addError(ctx, "Variable '%s' can be declared shared only in cannon outer scope",
							ctx.ID().getText());
				} else {
					boolean isAdded = this.table.putShared(ctx.ID().getText(), getType(ctx.type()));
					if (!isAdded) {
						addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
					} else {
						setOffset(ctx.ID(), this.table.offsetShared(ctx.ID().getText()));
						setEntry(ctx, ctx.type());
						setIsShared(ctx, true);
					}
				}
			} else {
				boolean isAdded = this.table.put(ctx.ID().getText(), getType(ctx.type()));
				if (!isAdded) {
					addError(ctx, "Variable '%s' is already declared in this scope %s", ctx.ID().getText(),
							this.table.scopeDepth());
				} else {
					setOffset(ctx.ID(), this.table.offset(ctx.ID().getText()));
					setEntry(ctx, ctx.type());
					setIsShared(ctx, false);
				}
			}
		}
	}

	@Override
	public void exitArrayVarStat(ArrayVarStatContext ctx) {
		if (this.table.isUsed(ctx.ID().getText())) {
			addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			if (Integer.parseInt(ctx.NUM().getText()) <= 0) {
				addError(ctx, "Array '%s' size must be greater than 0", ctx.ID().getText());
			} else {
				Type array = new Type.Array(Integer.parseInt(ctx.NUM().getText()), getType(ctx.type()));
				if (ctx.SHARED() != null) {
					if (!this.isInMainScope || this.table.scopeDepth() > 2) {
						addError(ctx, "Variable '%s' can be declared shared only in cannon outer scope",
								ctx.ID().getText());
					}
					boolean isAdded = this.table.putShared(ctx.ID().getText(), array);
					if (!isAdded) {
						addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
					} else {
						setOffset(ctx.ID(), this.table.offsetShared(ctx.ID().getText()));
						setEntry(ctx, ctx.type());
						setIsShared(ctx, true);
						if (ctx.expr() != null) {
							checkType(ctx.expr(), array);
						}
					}
				} else {
					boolean isAdded = this.table.put(ctx.ID().getText(), array);
					if (!isAdded) {
						addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
					} else {
						setOffset(ctx.ID(), this.table.offset(ctx.ID().getText()));
						setEntry(ctx, ctx.type());
						setIsShared(ctx, false);
						if (ctx.expr() != null) {
							checkType(ctx.expr(), array);
						}
					}
				}

			}
		}
	}

	@Override
	public void exitAssignStat(AssignStatContext ctx) {
		checkType(ctx.target(), getType(ctx.expr()));
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitIfStat(IfStatContext ctx) {
		checkType(ctx.expr(), Type.BOOL);
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void enterWhileStat(WhileStatContext ctx) {
		this.insideWhile++;
	}

	@Override
	public void exitWhileStat(WhileStatContext ctx) {
		this.insideWhile--;
		checkType(ctx.expr(), Type.BOOL);
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void enterForkStat(ForkStatContext ctx) {
		if (!this.isInMainScope || this.insideWhile > 0) {
			addError(ctx, "Cannot fork a thread inside a function or a while loop");
		}
		this.insideFork++;
	}

	@Override
	public void exitForkStat(ForkStatContext ctx) {
		this.insideFork--;
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void exitJoinStat(JoinStatContext ctx) {
		setEntry(ctx, ctx);
	}

	@Override
	public void enterSyncStat(SyncStatContext ctx) {
		if(this.insideSync>0) {
			addError(ctx, "Cannot nest sync statements as it can lead to the deadlock");
		}
		this.insideSync++;
	}
	
	@Override
	public void exitSyncStat(SyncStatContext ctx) {
		this.insideSync--;
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void exitBlockStat(BlockStatContext ctx) {
		setEntry(ctx, entry(ctx.block()));
	}

	@Override
	public void exitPrintStat(PrintStatContext ctx) {
		setEntry(ctx, entry(ctx.expr()));
	}

	@Override
	public void exitCallStat(CallStatContext ctx) {
		List<Type> args = new ArrayList<>();
		for (ExprContext arg : ctx.args().expr()) {
			args.add(getType(arg));
		}
		FunctionCall call = new FunctionCall(ctx, ctx.ID().getText());
		calls.add(call);
		Type type = new Type.Proc(args);
		setType(ctx, type);
	}

	@Override
	public void exitIdTarget(IdTargetContext ctx) {
		String id = ctx.ID().getText();
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth != 0 && depth != -1) {
				addError(ctx, "Variable '%s' must be declared shared in the cannon outer scope", id);
			} else if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}
				setType(ctx, type);
				setOffset(ctx, offset);
				setEntry(ctx, ctx);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				setType(ctx, type);
				setOffset(ctx, offset);
				setEntry(ctx, ctx);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	@Override
	public void exitArrayTarget(ArrayTargetContext ctx) {
		String id = ctx.ID().getText();

		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth != 0 && depth != -1) {
				addError(ctx, "Variable '%s' must be declared shared in the cannon outer scope", id);
			} else if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}

				if (!(type instanceof Type.Array)) {
					addError(ctx, "Variable '%s' is not an array", id);
				} else {
					checkType(ctx.expr(), Type.INT);
					setType(ctx, ((Type.Array) type).getElemType());
					setOffset(ctx, offset);
					setEntry(ctx, ctx.expr());
					setIsShared(ctx, isShared);
					this.table.putUsed(id, type);
				}
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Array '%s' not declared in this scope", id);
			} else if (!(type instanceof Type.Array)) {
				addError(ctx, "Variable '%s' is not an array", id);
			} else {
				checkType(ctx.expr(), Type.INT);
				setType(ctx, ((Type.Array) type).getElemType());
				setOffset(ctx, offset);
				setEntry(ctx, ctx.expr());
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
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
			if (getType(ctx.expr(0)).getKind() == TypeKind.PROC) {
				addError(ctx, "Procedure type cannot be compared");
			}
			checkTypeWithoutSize(ctx.expr(1), getType(ctx.expr(0)));
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
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth != 0 && depth != -1) {
				addError(ctx, "Variable '%s' must be declared shared in the cannon outer scope", id);
			} else if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}
				setType(ctx, type);
				setOffset(ctx, offset);
				setEntry(ctx, ctx);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				setType(ctx, type);
				setOffset(ctx, offset);
				setEntry(ctx, ctx);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
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
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth != 0 && depth != -1) {
				addError(ctx, "Variable '%s' must be declared shared in the cannon outer scope", id);
			} else if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}

				if (!(type instanceof Type.Array)) {
					addError(ctx, "Variable '%s' is not an array", id);
				} else {
					checkType(ctx.expr(), Type.INT);
					setType(ctx, ((Type.Array) type).getElemType());
					setOffset(ctx, offset);
					setEntry(ctx, ctx.expr());
					setIsShared(ctx,isShared);
					this.table.putUsed(id, type);
				}
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Array '%s' not declared in this scope", id);
			} else if (!(type instanceof Type.Array)) {
				addError(ctx, "Variable '%s' is not an array", id);
			} else {
				checkType(ctx.expr(), Type.INT);
				setType(ctx, ((Type.Array) type).getElemType());
				setOffset(ctx, offset);
				setEntry(ctx, ctx.expr());
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	@Override
	public void exitArrayExpr(ArrayExprContext ctx) {
		Type type = getType(ctx.expr(0));
		for (int i = 1; i < ctx.expr().size(); i++) {
			checkType(ctx.expr(i), type);
		}
		Type array = new Type.Array(ctx.expr().size(), type);
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
			// throw new IllegalArgumentException("Missing inferred type of " +
			// node.getText());
		} else if (actual instanceof Type.Array) {
			if (!((Type.Array) actual).equalsWithSize(expected)) {
				if (expected instanceof Type.Array) {
					addError(node, "Expected type '%s' but found '%s'", ((Type.Array) expected).toStringWithSize(),
							((Type.Array) actual).toStringWithSize());
				} else {
					addError(node, "Expected type '%s' but found '%s'", expected,
							((Type.Array) actual).toStringWithSize());
				}
			}
		} else if (!actual.equals(expected)) {
			addError(node, "Expected type '%s' but found '%s'", expected, actual);
		}
	}

	/**
	 * Checks the inferred type of a given parse tree, and adds an error if it does
	 * not correspond to the expected type. (Used for arrays when size does not have
	 * to match)
	 */
	private void checkTypeWithoutSize(ParserRuleContext node, Type expected) {
		Type actual = getType(node);
		if (actual == null || expected == null) {
			// throw new IllegalArgumentException("Missing inferred type of " +
			// node.getText());
		} else if (!actual.equals(expected)) {
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
			// throw new IllegalArgumentException("Null flow graph entry");
		} else {
			this.result.setEntry(node, entry);
		}
	}

	/** Returns the flow graph entry of a given expression or statement. */
	private ParserRuleContext entry(ParseTree node) {
		return this.result.getEntry(node);
	}

	/** Convenience method to set shared property in the result */
	private void setIsShared(ParseTree node, Boolean bool) {
		this.result.setIsShared(node, bool);
	}

	/**
	 * Class to store Function call parameters for function call correctness check
	 * 
	 * @author Karolis Butkus
	 *
	 */
	private class FunctionCall {
		private ParserRuleContext node;
		private String function;

		private FunctionCall(ParserRuleContext node, String function) {
			this.node = node;
			this.function = function;
		}

		public String getID() {
			return this.function;
		}

		public ParserRuleContext getNode() {
			return this.node;
		}
	}

	private void checkCalls(List<FunctionCall> calls) {
		for (FunctionCall call : calls) {
			Type type = this.table.type(call.getID());
			if (type == null) {
				addError(call.getNode(), "Pickle '%s' is not declared", call.getID());
			} else {
				checkType(call.node, type);
			}
		}
	}

}
