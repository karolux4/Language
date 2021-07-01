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

/**
 * Checker class is a class that is responsible for the elaboration phase of
 * compiler. Class extends {@link PickleCannonBaseListener} and by overriding
 * listener methods performs the elaboration. The result of elaboration is the
 * object of {@link Result} class.
 * 
 * @author Karolis Butkus
 *
 */
public class Checker extends PickleCannonBaseListener {
	/** Result of the latest call of {@link #check}. */
	private Result result;
	/** Symbol table for the latest call of {@link #check}. */
	private SymbolTable table;
	/** List of errors collected in the latest call of {@link #check}. */
	private List<String> errors;
	/** Boolean used to indicate whether procedure has opened the scope */
	private boolean scopeOpenedByFunction;
	/**
	 * Boolean used to indicate whether listener now is in main scope - cannon, not
	 * in procedures
	 */
	private boolean isInMainScope;
	/**
	 * Integer used to indicate whether listener now is inside a while cycle(s) or
	 * if/else body - cannot fork in while cycles or if constructs
	 */
	private int insideWhileIf;
	/** Integer used to indicate whether listener is inside forked thread(s) */
	private int insideFork;
	/**
	 * Integer used to check if one sync is already opened, to prevent a deadlock
	 * from nested sync statements
	 */
	private int insideSync;
	/**
	 * Integer to keep the count of concurrently working threads (excluding the main
	 * thread)
	 */
	private int concurrentThreads = 0;

	/**
	 * List to store all procedure calls made in the program, so that they later can
	 * be typechecked. List uses inner-class {@link FunctionCall} to store call
	 * information.
	 */
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
		this.insideWhileIf = 0;
		this.insideFork = 0;
		this.insideSync = 0;
		this.calls = new ArrayList<>();
		new ParseTreeWalker().walk(this, tree);
		checkCalls(this.calls);
		if (hasErrors()) {
			throw new ParseException(getErrors());
		}
		return this.result;
	}

	/**
	 * Listener method that is executed at the end of program node visit. Method
	 * retrieves the size of symbol table scope data area and associates it with the
	 * main body
	 */
	@Override
	public void exitProgram(ProgramContext ctx) {
		int size = this.table.scopeSize();
		this.result.setProcedureSize(ctx, size); // set local data size of main body
	}

	/**
	 * Listener method that is executed at the start of procedure node visit. Method
	 * opens the scope in symbol table for the parameters.
	 */
	@Override
	public void enterProc(ProcContext ctx) {
		this.table.openScope();
		this.table.openNestedLevel();
		this.scopeOpenedByFunction = true;
	}

	/**
	 * Listener method that is executed at the end of procedure node visit. Method
	 * retrieves the size of symbol table scope data area and associates it with
	 * procedure. Also method checks if procedure was not declared before.
	 */
	@Override
	public void exitProc(ProcContext ctx) {
		int size = this.table.scopeSize(); // retrieve procedure local data size
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
			this.result.setProcedureSize(ctx, size);
		}
	}

	/**
	 * Listener method that is executed at the end of simple variable parameter node
	 * visit. Method typechecks the parameter and adds it to the symbol table.
	 */
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

	/**
	 * Listener method that is executed at the end of array parameter node visit.
	 * Method typechecks the parameter and adds it to the symbol table.
	 */
	@Override
	public void exitArrayPar(ArrayParContext ctx) {
		if (Integer.parseInt(ctx.NUM().getText()) <= 0) {
			addError(ctx, "Array '%s' size must be greater than 0", ctx.ID().getText());
		} else {
			Type type = new Type.Array(Integer.parseInt(ctx.NUM().getText()), getType(ctx.type()));
			boolean isAdded = this.table.put(ctx.ID().getText(), type);
			if (!isAdded) {
				addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
			} else {
				setType(ctx, type);
				setOffset(ctx, this.table.offset(ctx.ID().getText()));
			}
		}
	}

	/**
	 * Listener method that is executed at the start of block node visit. Method
	 * opens up new nested level if it was not previously opened by the procedure
	 * node.
	 */
	@Override
	public void enterBlock(BlockContext ctx) {
		if (!this.scopeOpenedByFunction) { 
			this.table.openNestedLevel();
			if (ctx.parent instanceof ProgramContext) {
				this.isInMainScope = true;
			}
		} else {
			// do not open another nested level if function parameters already opened it
			this.scopeOpenedByFunction = false;
		}
	}

	/**
	 * Listener method that is executed at the end of block node visit. Method
	 * closes the nested level.
	 */
	@Override
	public void exitBlock(BlockContext ctx) {
		this.table.closeNestedLevel();
	}

	/**
	 * Listener method that is executed at the end of simple variable node
	 * declaration visit. Method checks for declaration, also typechecks the
	 * variable if initial value is provided and puts it into the symbol table.
	 * Variable is added to shared memory region if variable is shared and to local
	 * data region if it is not.
	 */
	@Override
	public void exitSimpleVarStat(SimpleVarStatContext ctx) {
		if (this.table.isUsed(ctx.ID().getText())) {
			addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
		} else {
			if (ctx.expr() != null) {
				checkType(ctx.type(), getType(ctx.expr()));
			}
			if (ctx.SHARED() != null) {
				if (!this.isInMainScope || this.table.scopeCount() != 1 || this.table.scopeDepth() > 2) {
					addError(ctx, "Variable '%s' can be declared shared only in cannon outer scope",
							ctx.ID().getText());
				} else {
					boolean isAdded = this.table.putShared(ctx.ID().getText(), getType(ctx.type()));
					if (!isAdded) {
						addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
					} else {
						setOffset(ctx.ID(), this.table.offsetShared(ctx.ID().getText()));
						setIsShared(ctx, true);
					}
				}
			} else {
				boolean isAdded = this.table.put(ctx.ID().getText(), getType(ctx.type()));
				if (!isAdded) {
					addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
				} else {
					setOffset(ctx.ID(), this.table.offset(ctx.ID().getText()));
					setIsShared(ctx, false);
				}
			}
		}
	}

	/**
	 * Listener method that is executed at the end of array variable declaration
	 * node visit. Method checks for declaration, also typechecks the variable if
	 * initial value is provided and puts it into the symbol table. Variable is
	 * added to shared memory region if variable is shared and to local data region
	 * if it is not.
	 */
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
					if (!this.isInMainScope || this.table.scopeCount() != 1 || this.table.scopeDepth() > 2) {
						addError(ctx, "Variable '%s' can be declared shared only in cannon outer scope",
								ctx.ID().getText());
					}
					boolean isAdded = this.table.putShared(ctx.ID().getText(), array);
					if (!isAdded) {
						addError(ctx, "Variable '%s' is already declared in this scope", ctx.ID().getText());
					} else {
						setOffset(ctx.ID(), this.table.offsetShared(ctx.ID().getText()));
						setType(ctx.ID(), array);
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
						setType(ctx.ID(), array);
						setIsShared(ctx, false);
						if (ctx.expr() != null) {
							checkType(ctx.expr(), array);
						}
					}
				}

			}
		}
	}

	/**
	 * Listener method that is executed at the end of assign node visit. Method
	 * typechecks if target and expression types match.
	 */
	@Override
	public void exitAssignStat(AssignStatContext ctx) {
		checkType(ctx.target(), getType(ctx.expr()));
	}

	/**
	 * Listener method that is executed at the start of if statement. Method
	 * increases {@link #insideWhileIf} variable by 1 to indicate that listener is
	 * inside if statement.
	 */
	@Override
	public void enterIfStat(IfStatContext ctx) {
		this.insideWhileIf++;
	}

	/**
	 * Listener method that is executed at the end of if statement. Method decreases
	 * {@link #insideWhileIf} variable by 1 to indicate that listener has left if
	 * statement. Method also typechecks if if-condition is a boolean.
	 */
	@Override
	public void exitIfStat(IfStatContext ctx) {
		this.insideWhileIf--;
		checkType(ctx.expr(), Type.BOOL);
	}

	/**
	 * Listener method that is executed at the start of while statement. Method
	 * increases {@link #insideWhileIf} variable by 1 to indicate that listener is
	 * inside while statement.
	 */
	@Override
	public void enterWhileStat(WhileStatContext ctx) {
		this.insideWhileIf++;
	}

	/**
	 * Listener method that is executed at the end of while statement. Method
	 * decreases {@link #insideWhileIf} variable by 1 to indicate that listener has
	 * left while statement. Method also typechecks if while-condition is a boolean.
	 */
	@Override
	public void exitWhileStat(WhileStatContext ctx) {
		this.insideWhileIf--;
		checkType(ctx.expr(), Type.BOOL);
	}

	/**
	 * Listener method that is executed at the start of fork statement. Method
	 * increases {@link #insideFork} variable by 1 to indicate that listener is
	 * inside fork statement. Also it add thread call to the result of program and
	 * opens new scope
	 */
	@Override
	public void enterForkStat(ForkStatContext ctx) {
		if (!this.isInMainScope || this.insideWhileIf > 0) {
			addError(ctx, "Cannot fork a thread inside a function, a while loop or if-else statement");
		}
		this.insideFork++;
		this.result.addThread(concurrentThreads);
		this.concurrentThreads++;
		this.table.openScope();
	}

	/**
	 * Listener method that is executed at the end of fork statement. Method
	 * decreases {@link #insideFork} variable by 1 to indicate that listener left
	 * fork statement. Also it closes the scope
	 */
	@Override
	public void exitForkStat(ForkStatContext ctx) {
		this.insideFork--;
		this.result.setProcedureSize(ctx, this.table.scopeSize());
		this.table.closeScope();
	}

	/**
	 * Listener method that is executed at the end of join statement. Method updates
	 * {@link #concurrentThreads} variable to indicate concurrently working thread
	 * count after the join
	 */
	@Override
	public void exitJoinStat(JoinStatContext ctx) {
		// after join all spawned nested threads from this thread unite into one
		this.concurrentThreads = this.insideFork;
	}

	/**
	 * Listener method that is executed at the start of sync statement. Method
	 * updates {@link #insideSync} variable to indicate that listener enter sync
	 * statement. Also it checks if sync statement was not opened inside another
	 * sync statement which would cause the deadlock.
	 */
	@Override
	public void enterSyncStat(SyncStatContext ctx) {
		if (this.insideSync > 0) {
			addError(ctx, "Cannot nest sync statements as it can lead to the deadlock");
		}
		this.insideSync++;
	}

	/**
	 * Listener method that is executed at the end of sync statement. Method updates
	 * {@link #insideSync} variable to indicate that listener left sync statement.
	 */
	@Override
	public void exitSyncStat(SyncStatContext ctx) {
		this.insideSync--;
	}

	/**
	 * Listener method that is executed at the end of procedure call statement.
	 * Method creates a {@link FunctionCall} object that is added to the list of
	 * function calls to typecheck them at the end of elaboration.
	 */
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

	/**
	 * Listener method that is executed at the end of identifier target node. Method
	 * checks identifier for the declaration and associates the node with the type
	 * and offset retrieved from the symbol table.
	 */
	@Override
	public void exitIdTarget(IdTargetContext ctx) {
		String id = ctx.ID().getText();
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope or is not shared", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					// variable may have been declared in shared memory
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}
				setType(ctx, type);
				setOffset(ctx, offset);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				// variable may have been declared in shared memory
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				setType(ctx, type);
				setOffset(ctx, offset);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	/**
	 * Listener method that is executed at the end of array target node. Method
	 * checks array for the declaration, and typechecks the index. After that method
	 * associates the node with the type and offset retrieved from the symbol table.
	 */
	@Override
	public void exitArrayTarget(ArrayTargetContext ctx) {
		String id = ctx.ID().getText();

		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope or is not shared", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					// variable may have been declared in shared memory
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
					setIsShared(ctx, isShared);
					this.table.putUsed(id, type);
				}
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				// variable may have been declared in shared memory
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
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	/**
	 * Listener method that is executed at the end of prefix expression node. Method
	 * typechecks the expressions.
	 */
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
	}

	/**
	 * Listener method that is executed at the end of multiplication/division
	 * expression node. Method typechecks the expressions.
	 */
	@Override
	public void exitMultExpr(MultExprContext ctx) {
		checkType(ctx.expr(0), Type.INT);
		checkType(ctx.expr(1), Type.INT);
		setType(ctx, Type.INT);
	}

	/**
	 * Listener method that is executed at the end of plus/minus expression node.
	 * Method typechecks the expressions.
	 */
	@Override
	public void exitPlusExpr(PlusExprContext ctx) {
		checkType(ctx.expr(0), Type.INT);
		checkType(ctx.expr(1), Type.INT);
		setType(ctx, Type.INT);
	}

	/**
	 * Listener method that is executed at the end of comparison expression node.
	 * Method typechecks the expressions.
	 */
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
	}

	/**
	 * Listener method that is executed at the end of boolean expression node.
	 * Method typechecks the expressions.
	 */
	@Override
	public void exitBoolExpr(BoolExprContext ctx) {
		checkType(ctx.expr(0), Type.BOOL);
		checkType(ctx.expr(1), Type.BOOL);
		setType(ctx, Type.BOOL);
	}

	/**
	 * Listener method that is executed at the end of parenthesis expression node.
	 * Method typechecks the expressions.
	 */
	@Override
	public void exitParExpr(ParExprContext ctx) {
		setType(ctx, getType(ctx.expr()));
	}

	/**
	 * Listener method that is executed at the end of identifier expression node.
	 * Method checks for declaration and retrieves the type and offset from the
	 * symbol table.
	 */
	@Override
	public void exitIdExpr(IdExprContext ctx) {
		String id = ctx.ID().getText();
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope or is not shared", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					// variable may have been declared in shared memory
					type = this.table.typeShared(id);
					offset = this.table.offsetShared(id);
					isShared = true;
				}
				setType(ctx, type);
				setOffset(ctx, offset);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				// variable may have been declared in shared memory
				type = this.table.typeShared(id);
				offset = this.table.offsetShared(id);
				isShared = true;
			}
			if (type == null) {
				addError(ctx, "Variable '%s' not declared in this scope", id);
			} else {
				setType(ctx, type);
				setOffset(ctx, offset);
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	/**
	 * Listener method that is executed at the end of number expression node.
	 */
	@Override
	public void exitNumExpr(NumExprContext ctx) {
		setType(ctx, Type.INT);
	}

	/**
	 * Listener method that is executed at the end of true expression node.
	 */
	@Override
	public void exitTrueExpr(TrueExprContext ctx) {
		setType(ctx, Type.BOOL);
	}

	/**
	 * Listener method that is executed at the end of false expression node.
	 */
	@Override
	public void exitFalseExpr(FalseExprContext ctx) {
		setType(ctx, Type.BOOL);
	}

	/**
	 * Listener method that is executed at the end of array index expression node.
	 * Method typechecks the index and checks for identifier declaration. Also,
	 * method retrieves the type and offset of the identifier.
	 */
	@Override
	public void exitIndexExpr(IndexExprContext ctx) {
		String id = ctx.ID().getText();
		if (this.insideFork > 0) {
			int depth = this.table.variableDepth(id);
			if (depth == -1 && this.table.offsetShared(id) == -1) {
				addError(ctx, "Variable '%s' not declared in this scope or is not shared", id);
			} else {
				Type type = this.table.type(id);
				int offset = this.table.offset(id);
				boolean isShared = false;
				if (type == null) {
					// variable may have been declared in shared memory
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
					setIsShared(ctx, isShared);
					this.table.putUsed(id, type);
				}
			}
		} else {
			Type type = this.table.type(id);
			int offset = this.table.offset(id);
			boolean isShared = false;
			if (type == null) {
				// variable may have been declared in shared memory
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
				setIsShared(ctx, isShared);
				this.table.putUsed(id, type);
			}
		}
	}

	/**
	 * Listener method that is executed at the end of array expression node.
	 * Method typechecks all array expressions.
	 */
	@Override
	public void exitArrayExpr(ArrayExprContext ctx) {
		Type type = getType(ctx.expr(0));
		if (type != Type.INT && type != Type.BOOL) {
			addError(ctx, "Multi-dimensional arrays are not supported");
		}
		for (int i = 1; i < ctx.expr().size(); i++) {
			checkType(ctx.expr(i), type);
		}
		Type array = new Type.Array(ctx.expr().size(), type);
		setType(ctx, array);

	}

	/**
	 * Listener method that is executed at the end of int type node.
	 */
	@Override
	public void exitIntType(IntTypeContext ctx) {
		setType(ctx, Type.INT);
	}

	/**
	 * Listener method that is executed at the end of bool type node.
	 */
	@Override
	public void exitBoolType(BoolTypeContext ctx) {
		setType(ctx, Type.BOOL);
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
			
		} else if (actual instanceof Type.Array) {
			if (!((Type.Array) actual).equalsWithoutSize(expected)) {
				if (expected instanceof Type.Array) {
					addError(node, "Expected type '%s' but found '%s'", ((Type.Array) expected).toStringWithoutSize(),
							((Type.Array) actual).toStringWithoutSize());
				} else {
					addError(node, "Expected type '%s' but found '%s'", expected,
							((Type.Array) actual).toStringWithoutSize());
				}
			}
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

	/** Convenience method to set shared property in the result */
	private void setIsShared(ParseTree node, Boolean bool) {
		this.result.setIsShared(node, bool);
	}

	/**
	 * Class to store Function call parameters for procedure call correctness check.
	 * 
	 * @author Karolis Butkus
	 *
	 */
	private class FunctionCall {
		/** Procedure call node*/
		private ParserRuleContext node;
		/** Name of the called function*/
		private String function;

		/** Constructs the FunctionCall object from the call node and function name*/
		private FunctionCall(ParserRuleContext node, String function) {
			this.node = node;
			this.function = function;
		}
		/** 
		 * Return the name of the called function
		 * @return function name
		 */
		public String getID() {
			return this.function;
		}

		/**
		 * Return the node at which call occurred
		 * @return parse tree node
		 */
		public ParserRuleContext getNode() {
			return this.node;
		}
	}

	/**
	 * Checks function calls for procedure declaration and parameter types.
	 * @param calls - the list of all procedure calls
	 */
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
