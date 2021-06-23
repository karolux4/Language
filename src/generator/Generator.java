package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

public class Generator extends PickleCannonBaseVisitor<Instr> {

	/** The outcome of the checker phase. */
	private Result checkResult;

	/** The program being built. */
	private Program prog;

	/** Total register number */
	private final static int REGISTER_COUNT = 8;

	/** The array indicating if register is taken */
	private List<Boolean[]> isRegisterTaken;

	/** The number of inserted instructions */
	private int instructionCount;

	/** The ids of currently open threads */
	private Stack<Integer> currentThreads;

	/**
	 * The integer indicating how many concurrent threads are now executing
	 * (excluding main thread)
	 */
	private int concurrentThreads;

	/** Association of expression and target nodes to registers. */
	private ParseTreeProperty<Reg> regs;

	/** Association of the first instruction number in the node with a node */
	private ParseTreeProperty<Integer> instrID;

	public Program generate(ParseTree tree, Result checkResult) {
		this.checkResult = checkResult;
		this.prog = new Program(this.checkResult.getThreadCount());
		this.isRegisterTaken = new ArrayList<>();
		initializeRegisters(this.checkResult.getThreadCount() + 1);
		this.currentThreads = new Stack<>();
		this.currentThreads.push(0);
		this.concurrentThreads = 0;
		this.instructionCount = 0;
		this.regs = new ParseTreeProperty<>();
		this.instrID = new ParseTreeProperty<>();
		tree.accept(this);
		return prog;
	}

	// Override the visitor methods
	@Override
	public Instr visitProgram(ProgramContext ctx) {
		System.out.println("Visit program");
		emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		for (ProcContext proc : ctx.proc()) {
			visit(proc);
		}
		this.prog.updateInstr(0, new Instr(OpCode.Jump, new Target(TargetType.Abs, this.instructionCount)));
		if (this.checkResult.getThreadCount() > 0) {
			// Instructions dedicated for thread jumping with main thread skipping them
			generateThreadJumping(true);
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
		for (StatContext stat : ctx.stat()) {
			visit(stat);
		}
		return null;
	}

	@Override
	public Instr visitSimpleVarStat(SimpleVarStatContext ctx) {
		System.out.println("Visit simpleVarStat");
		if (ctx.expr() != null) {
			visit(ctx.expr());
			if (isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()), offset(ctx.ID(), true));
			} else {
				Instr i = emit(OpCode.Store, reg(ctx.expr()), offset(ctx.ID()));
			}
			freeReg(ctx.expr());
		} else {
			if (isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()), offset(ctx.ID(), true));
			} else {
				Instr i = emit(OpCode.Store, new Reg(0), offset(ctx.ID()));
			}
		}
		return null;
	}

	@Override
	public Instr visitArrayVarStat(ArrayVarStatContext ctx) {
		System.out.println("Visit arrayVarStat");
		if (ctx.expr() != null) {
			visit(ctx.expr());
			int arraySize = ((Type.Array) getType(ctx.ID())).getSize();
			if (isShared(ctx)) {
				for (int i = 0; i < arraySize; i++) {
					Instr i1 = emit(OpCode.Pop, reg(ctx));
					Instr i2 = emit(OpCode.WriteInstr, reg(ctx), offset(ctx.ID(), true, i));
				}
				freeReg(ctx);
			} else {
				for (int i = 0; i < arraySize; i++) {
					Instr i1 = emit(OpCode.Pop, reg(ctx));
					Instr i2 = emit(OpCode.Store, reg(ctx), offset(ctx.ID(), i));
				}
				freeReg(ctx);
			}
		} else {
			if (isShared(ctx)) {
				int arraySize = ((Type.Array) (getType(ctx.ID()))).getSize();
				for (int i = 0; i < arraySize; i++) {
					Instr i1 = emit(OpCode.WriteInstr, new Reg(0), offset(ctx.ID(), true, i));
				}
			} else {
				int arraySize = ((Type.Array) (getType(ctx.ID()))).getSize();
				for (int i = 0; i < arraySize; i++) {
					Instr i1 = emit(OpCode.Store, new Reg(0), offset(ctx.ID(), i));
				}
			}
		}
		return null;
	}

	@Override
	public Instr visitAssignStat(AssignStatContext ctx) {
		System.out.println("Visit assignStat");
		visit(ctx.expr());
		visit(ctx.target());
		if (getType(ctx.target()) == Type.INT || getType(ctx.target()) == Type.BOOL) {
			if (isShared(ctx.target())) {
				Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()),
						new Addr(AddrImmDI.IndAddr, reg(ctx.target()).getId()));
			} else {
				Instr i = emit(OpCode.Store, reg(ctx.expr()), new Addr(AddrImmDI.IndAddr, reg(ctx.target()).getId()));
			}
			freeReg(ctx.expr());
		} else {
			int arraySize = ((Type.Array) getType(ctx.target())).getSize();
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			OpCode code;
			if(isShared(ctx.target())) {
				code = OpCode.WriteInstr;
			}
			else {
				code = OpCode.Store;
			}
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
			Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 6));
			Instr i4 = emit(OpCode.Pop, reg(ctx));
			Instr i5 = emit(code, reg(ctx), new Addr(AddrImmDI.IndAddr, reg(ctx.target()).getId()));
			Instr i6 = emit(OpCode.Compute, new Operator(Oper.Incr), reg(ctx.target()), new Reg(0), reg(ctx.target()));
			Instr i7 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
			Instr i8 = emit(OpCode.Jump, new Target(TargetType.Rel, -6));
			freeReg(ctx);
			freeUpRegister(extraRegIndex);
		}
		freeReg(ctx.target());
		return null;
	}

	@Override
	public Instr visitIfStat(IfStatContext ctx) {
		System.out.println("Visit ifStat");
		// Condition
		visit(ctx.expr());
		Reg r = reg(ctx);
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), reg(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		// Then
		visit(ctx.block(0));
		Integer jumpID = this.instructionCount;
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		// Else
		Integer elseStart = this.instructionCount;
		if (ctx.block().size() > 1) {
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
		// Condition
		Integer start = this.instructionCount;
		visit(ctx.expr());
		Reg r = reg(ctx);
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), reg(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		// Body
		visit(ctx.block());
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, start));
		Instr i4 = emit(OpCode.Nop);
		this.prog.updateInstr(branchID, new Instr(OpCode.Branch, r, new Target(TargetType.Abs, instructionCount - 1)));
		return null;
	}

	@Override
	public Instr visitForkStat(ForkStatContext ctx) {
		System.out.println("Visit forkStat");
		Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.instructionCount + 3), reg(ctx));
		Instr i2 = emit(OpCode.WriteInstr, reg(ctx), new Addr(AddrImmDI.DirAddr, concurrentThreads + 1));
		freeReg(ctx);
		int jumpID = this.instructionCount;
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		int callCount = this.checkResult.decreaseThreadCallCount(concurrentThreads);
		this.currentThreads.push(concurrentThreads + 1);
		this.concurrentThreads++;
		visit(ctx.block());
		this.currentThreads.pop();
		if (callCount == 1) {
			Instr i4 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.IndAddr, 1));
			Instr i5 = emit(OpCode.EndProg);
		} else {
			Instr i4 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.IndAddr, 1));
			generateThreadJumping(false);
		}
		this.prog.updateInstr(jumpID, new Instr(OpCode.Jump, new Target(TargetType.Abs, this.instructionCount)));
		return null;
	}

	@Override
	public Instr visitJoinStat(JoinStatContext ctx) {
		System.out.println("Visit joinStat");
		generateThreadJoin(this.currentThreads.peek(), this.concurrentThreads);
		this.concurrentThreads = this.currentThreads.peek();
		return null;
	}

	@Override
	public Instr visitSyncStat(SyncStatContext ctx) {
		System.out.println("Visit syncStat");
		Instr i1 = emit(OpCode.TestAndSet, new Addr(AddrImmDI.DirAddr, this.checkResult.getLockAddress()));
		Instr i2 = emit(OpCode.Receive, reg(ctx));
		Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 2));
		Instr i4 = emit(OpCode.Jump, new Target(TargetType.Rel, -3));
		freeReg(ctx);
		visit(ctx.block());
		Instr i5 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.DirAddr, this.checkResult.getLockAddress()));
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
		if (getType(ctx.expr()) == Type.INT || getType(ctx.expr()) == Type.BOOL) {
			Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()), Addr.NUMBER_IO);
			freeReg(ctx.expr());
		} else {
			int arraySize = ((Type.Array) getType(ctx.expr())).getSize();
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize), extraReg);
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.LtE), extraReg, new Reg(0), reg(ctx));
			Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 5));
			Instr i4 = emit(OpCode.Pop, reg(ctx));
			Instr i5 = emit(OpCode.WriteInstr, reg(ctx), Addr.NUMBER_IO);
			Instr i6 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
			Instr i7 = emit(OpCode.Jump, new Target(TargetType.Rel, -5));
			freeReg(ctx);
			freeUpRegister(extraRegIndex);
		}
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
		if (isShared(ctx)) {
			Instr i = emit(OpCode.Load,
					new Addr(AddrImmDI.ImmValue, this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)),
					reg(ctx));
		} else {
			Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
		}
		return null;
	}

	@Override
	public Instr visitArrayTarget(ArrayTargetContext ctx) {
		System.out.println("Visit arrayTarget");
		visit(ctx.expr());
		if (isShared(ctx)) {
			Instr i1 = emit(OpCode.Load,
					new Addr(AddrImmDI.ImmValue, this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)),
					reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
		} else {
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
		}
		return null;
	}

	@Override
	public Instr visitArgs(ArgsContext ctx) {
		System.out.println("Visit args");
		for (ExprContext expr : ctx.expr()) {
			visit(expr);
		}
		return null;
	}

	@Override
	public Instr visitPrfExpr(PrfExprContext ctx) {
		System.out.println("Visit prfExpr");
		visit(ctx.expr());
		if (getType(ctx) == Type.INT) {
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, -1), reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
		} else {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Equal), reg(ctx.expr()), new Reg(0), reg(ctx.expr()));
			setReg(ctx, reg(ctx.expr()));
		}
		return null;
	}

	@Override
	public Instr visitMultExpr(MultExprContext ctx) {
		System.out.println("Visit multExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		if (ctx.multOp().STAR() != null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx.expr(0)), reg(ctx.expr(1)),
					reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		} else if (ctx.multOp().SLASH() != null) {
			reg(ctx); // take up register for ctx node;
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			// if numbers are negative, multiply them by -1 so that positive division would
			// be possible
			Instr i1 = emit(OpCode.Compute, new Operator(Oper.GtE), reg(ctx.expr(0)), new Reg(0), reg(ctx));
			Instr i2 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 3));
			Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, -1), reg(ctx));
			Instr i4 = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx.expr(0)), reg(ctx), reg(ctx.expr(0)));
			Instr i5 = emit(OpCode.Compute, new Operator(Oper.GtE), reg(ctx.expr(1)), new Reg(0), extraReg);
			Instr i6 = emit(OpCode.Branch, extraReg, new Target(TargetType.Rel, 3));
			Instr i7 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, -1), extraReg);
			Instr i8 = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx.expr(1)), extraReg, reg(ctx.expr(1)));
			// determine if the ending result positive or negative
			Instr i9 = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx), extraReg, reg(ctx));
			// push result sign to stack
			Instr i10 = emit(OpCode.Push, reg(ctx));
			// positive number division instructions (subtract one register value from
			// another and increase the counter)
			Instr i11 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, -1), reg(ctx));
			Instr i12 = emit(OpCode.Compute, new Operator(Oper.Incr), reg(ctx), new Reg(0), reg(ctx));
			Instr i13 = emit(OpCode.Compute, new Operator(Oper.GtE), reg(ctx.expr(0)), reg(ctx.expr(1)), extraReg);
			Instr i14 = emit(OpCode.Compute, new Operator(Oper.Sub), reg(ctx.expr(0)), reg(ctx.expr(1)),
					reg(ctx.expr(0)));
			Instr i15 = emit(OpCode.Branch, extraReg, new Target(TargetType.Rel, -3));
			// retrieve the expression sign from the stack
			Instr i16 = emit(OpCode.Pop, extraReg);
			// multiply the positive division result by the sign
			Instr i17 = emit(OpCode.Compute, new Operator(Oper.Mul), reg(ctx), extraReg, reg(ctx));
			freeUpRegister(extraRegIndex);
			freeReg(ctx.expr(0));
			freeReg(ctx.expr(1));
		}
		return null;
	}

	@Override
	public Instr visitPlusExpr(PlusExprContext ctx) {
		System.out.println("Visit plusExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		if (ctx.plusOp().PLUS() != null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx.expr(0)), reg(ctx.expr(1)),
					reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		} else if (ctx.plusOp().MINUS() != null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Sub), reg(ctx.expr(0)), reg(ctx.expr(1)),
					reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		}
		return null;
	}

	@Override
	public Instr visitCompExpr(CompExprContext ctx) {
		System.out.println("Visit compExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		if (ctx.compOp().EQ() != null || ctx.compOp().NE() != null) {
			if (this.checkResult.getType(ctx.expr(0)) == Type.INT
					|| this.checkResult.getType(ctx.expr(0)) == Type.BOOL) {
				Operator oper;
				if (ctx.compOp().EQ() != null) {
					oper = new Operator(Oper.Equal);
				} else {
					oper = new Operator(Oper.NEq);
				}
				Instr i = emit(OpCode.Compute, oper, reg(ctx.expr(0)), reg(ctx.expr(1)), reg(ctx.expr(0)));
				freeReg(ctx.expr(1));
				setReg(ctx, reg(ctx.expr(0)));
			} else {
				int size1 = ((Type.Array) getType(ctx.expr(0))).getSize();
				int size2 = ((Type.Array) getType(ctx.expr(1))).getSize();
				if (size1 != size2) {
					Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, size1 + size2), reg(ctx));
					Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(8), reg(ctx), new Reg(8));
					if (ctx.compOp().EQ() != null) {
						Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 0), reg(ctx));
					} else {
						Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 1), reg(ctx));
					}
				} else {
					Operator comparisonOper;
					Operator joinOper;
					if (ctx.compOp().EQ() != null) {
						comparisonOper = new Operator(Oper.Equal);
						joinOper = new Operator(Oper.And);
						// Load true at the start of the evaluation
						Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 1), reg(ctx));
					} else {
						comparisonOper = new Operator(Oper.NEq);
						joinOper = new Operator(Oper.Or);
						// Load true at the start of the evaluation
						Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 0), reg(ctx));
					}

					int extraRegIndex = getFreeRegister();
					lockRegister(extraRegIndex);
					Reg extraReg = new Reg(extraRegIndex);
					// Load the iteration count
					Instr i2 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, size1), extraReg);
					// Check if iteration ended
					Instr i3 = emit(OpCode.Compute, new Operator(Oper.LtE), extraReg, new Reg(0), reg(ctx.expr(0)));
					// Skip instructions if all iterations ended
					Instr i4 = emit(OpCode.Branch, reg(ctx.expr(0)), new Target(TargetType.Rel, 9));
					// Retrieve the current iteration array value
					Instr i5 = emit(OpCode.Pop, reg(ctx.expr(0)));
					// Load the the offset to another array current iteration
					Instr i6 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, size1 - 1), reg(ctx.expr(1)));
					// Compute the address of another array current iteration
					Instr i7 = emit(OpCode.Compute, new Operator(Oper.Add), new Reg(8), reg(ctx.expr(1)),
							reg(ctx.expr(1)));
					// Load another array current iteration value
					Instr i8 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx.expr(1)).getId()),
							reg(ctx.expr(1)));
					// Compare values
					Instr i9 = emit(OpCode.Compute, comparisonOper, reg(ctx.expr(0)), reg(ctx.expr(1)),
							reg(ctx.expr(0)));
					// Update the cumulative boolean value
					Instr i10 = emit(OpCode.Compute, joinOper, reg(ctx), reg(ctx.expr(0)), reg(ctx));
					// Update iteration number
					Instr i11 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
					// Jump back to branch
					Instr i12 = emit(OpCode.Jump, new Target(TargetType.Rel, -9));
					// Load the size of remaining array in size
					Instr i13 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, size2), extraReg);
					// Move stack pointer to skip the remaining array
					Instr i14 = emit(OpCode.Compute, new Operator(Oper.Add), new Reg(8), extraReg, new Reg(8));
					freeUpRegister(extraRegIndex);
					freeReg(ctx.expr(0));
					freeReg(ctx.expr(1));
				}
			}
		} else {
			Operator oper;
			if (ctx.compOp().LE() != null) {
				oper = new Operator(Oper.LtE);
			} else if (ctx.compOp().LT() != null) {
				oper = new Operator(Oper.Lt);
			} else if (ctx.compOp().GE() != null) {
				oper = new Operator(Oper.GtE);
			} else {
				oper = new Operator(Oper.Gt);
			}
			Instr i = emit(OpCode.Compute, oper, reg(ctx.expr(0)), reg(ctx.expr(1)), reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		}
		return null;
	}

	@Override
	public Instr visitBoolExpr(BoolExprContext ctx) {
		System.out.println("Visit boolExpr");
		visit(ctx.expr(0));
		visit(ctx.expr(1));
		if (ctx.boolOp().AND() != null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.And), reg(ctx.expr(0)), reg(ctx.expr(1)),
					reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		} else if (ctx.boolOp().OR() != null) {
			Instr i = emit(OpCode.Compute, new Operator(Oper.Or), reg(ctx.expr(0)), reg(ctx.expr(1)), reg(ctx.expr(0)));
			freeReg(ctx.expr(1));
			setReg(ctx, reg(ctx.expr(0)));
		}
		return null;
	}

	@Override
	public Instr visitParExpr(ParExprContext ctx) {
		System.out.println("Visit parExpr");
		visit(ctx.expr());
		setReg(ctx, regs.get(ctx.expr()));
		return null;
	}

	@Override
	public Instr visitIdExpr(IdExprContext ctx) {
		System.out.println("Visit idExpr");
		if (getType(ctx) == Type.INT || getType(ctx) == Type.BOOL) {
			if (isShared(ctx)) {
				Instr i1 = emit(OpCode.ReadInstr, offset(ctx, true));
				Instr i2 = emit(OpCode.Receive, reg(ctx));
			} else {
				Instr i = emit(OpCode.Load, offset(ctx), reg(ctx));
			}
		} else {
			int arraySize = ((Type.Array) getType(ctx)).getSize();
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
			if (isShared(ctx)) {
				Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 8));
				Instr i4 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue,
						this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)), reg(ctx));
				Instr i5 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), extraReg, reg(ctx));
				Instr i6 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
				Instr i7 = emit(OpCode.Receive, reg(ctx));
				Instr i8 = emit(OpCode.Push, reg(ctx));
				Instr i9 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
				Instr i10 = emit(OpCode.Jump, new Target(TargetType.Rel, -8));
			} else {
				Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 7));
				Instr i4 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
				Instr i5 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), extraReg, reg(ctx));
				Instr i6 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
				Instr i7 = emit(OpCode.Push, reg(ctx));
				Instr i8 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
				Instr i9 = emit(OpCode.Jump, new Target(TargetType.Rel, -7));
			}
			freeUpRegister(extraRegIndex);
			freeReg(ctx);

		}
		return null;
	}

	@Override
	public Instr visitNumExpr(NumExprContext ctx) {
		System.out.println("Visit numExpr");
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, Integer.parseInt(ctx.NUM().getText())), reg(ctx));
		return null;
	}

	@Override
	public Instr visitTrueExpr(TrueExprContext ctx) {
		System.out.println("Visit trueExpr");
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
		if (isShared(ctx)) {
			Instr i1 = emit(OpCode.Load, new Addr(Addr.AddrImmDI.ImmValue,
					this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)), reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
			Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
			Instr i4 = emit(OpCode.Receive, reg(ctx));
		} else {
			Instr i1 = emit(OpCode.Load, new Addr(Addr.AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
			Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
		}
		return null;
	}

	@Override
	public Instr visitArrayExpr(ArrayExprContext ctx) {
		System.out.println("Visit arrayExpr");
		for (int i = ctx.expr().size() - 1; i >= 0; i--) {
			visit(ctx.expr(i));
			Instr i1 = emit(OpCode.Push, reg(ctx.expr(i)));
			freeReg(ctx.expr(i));
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
		return this.isRegisterTaken.get(currentThreads.peek())[i];
	}

	private int getFreeRegister() {
		for (int i = 3; i < this.isRegisterTaken.get(currentThreads.peek()).length; i++) {
			if (!this.isRegisterTaken.get(currentThreads.peek())[i]) {
				return i;
			}
		}
		return -1;
	}

	private void freeUpRegister(int i) {
		this.isRegisterTaken.get(currentThreads.peek())[i] = false;
	}

	private void lockRegister(int i) {
		if (this.isRegisterTaken.get(currentThreads.peek())[i]) {
			throw new RuntimeException("Register is already locked");
		}
		this.isRegisterTaken.get(currentThreads.peek())[i] = true;
	}

	private Reg reg(ParseTree node) {
		Reg result = this.regs.get(node);
		if (result == null) {
			int regId = getFreeRegister();
			if (regId == -1) {
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
		if (result == null) {
			throw new RuntimeException("This node does not have a register");
		} else {
			this.regs.removeFrom(node);
			this.freeUpRegister(result.getId());
		}
	}

	/** Assigns a register to a given parse tree node. */
	private void setReg(ParseTree node, Reg reg) {
		this.regs.put(node, reg);
	}

	/**
	 * Retrieves the offset of a variable node from the checker result, wrapped in a
	 * {@link Addr} operand.
	 */
	private Addr offset(ParseTree node) {
		return new Addr(AddrImmDI.DirAddr, this.checkResult.getOffset(node));
	}

	/**
	 * Retrieves the offset of a variable node from the checker result, wrapped in a
	 * {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, int modifier) {
		return new Addr(AddrImmDI.DirAddr, modifier + this.checkResult.getOffset(node));
	}

	/**
	 * Retrieves the offset of a variable node from the checker result, wrapped in a
	 * {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, boolean isShared) {
		if (isShared) {
			return new Addr(AddrImmDI.DirAddr, this.checkResult.getBaseOffset() + this.checkResult.getOffset(node));
		}
		return new Addr(AddrImmDI.DirAddr, this.checkResult.getOffset(node));
	}

	/**
	 * Retrieves the offset of a variable node from the checker result, wrapped in a
	 * {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, boolean isShared, int modifier) {
		if (isShared) {
			return new Addr(AddrImmDI.DirAddr,
					this.checkResult.getBaseOffset() + this.checkResult.getOffset(node) + modifier);
		}
		return new Addr(AddrImmDI.DirAddr, this.checkResult.getOffset(node) + modifier);
	}

	/** Returns a boolean is node stored in shared memory */
	private boolean isShared(ParseTree node) {
		Boolean result = this.checkResult.getIsShared(node);
		if (result == null) {
			throw new RuntimeException("Node does not have declared is shared property");
		}
		return result;
	}

	/** Return the type of the expression */
	private Type getType(ParseTree node) {
		return this.checkResult.getType(node);
	}

	/** Convenience method to create instructions for thread jumping */
	private void generateThreadJumping(boolean withMainThread) {
		if (withMainThread) {
			Instr i1 = emit(OpCode.Branch, new Reg(1), new Target(TargetType.Rel, 2));
			Instr i2 = emit(OpCode.Jump, new Target(TargetType.Rel, 6));
		}
		Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, 1));
		Instr i4 = emit(OpCode.Receive, new Reg(2));
		Instr i5 = emit(OpCode.Compute, new Operator(Oper.Equal), new Reg(2), new Reg(0), new Reg(3));
		Instr i6 = emit(OpCode.Branch, new Reg(3), new Target(TargetType.Rel, -3));
		Instr i7 = emit(OpCode.Jump, new Target(TargetType.Ind, 2));
	}

	/** Method to initialize register arrays for each thread */
	private void initializeRegisters(int count) {
		for (int i = 0; i < count; i++) {
			Boolean isTaken[] = new Boolean[REGISTER_COUNT];
			// reg0 is taken
			isTaken[0] = true;
			// regSprID is taken
			isTaken[1] = true;
			// regA is allocated for ARP
			isTaken[2] = true;
			for (int j = 3; j < REGISTER_COUNT; j++) {
				isTaken[j] = false;
			}

			this.isRegisterTaken.add(isTaken);
		}

	}

	private void generateThreadJoin(int from, int to) {
		if (from < 0 || to < 0 || from > this.checkResult.getThreadCount() || to > this.checkResult.getThreadCount()) {
			throw new RuntimeException("Thread ids are out of bounds");
		}
		if (from < to) {
			Instr i1 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.DirAddr, from + 1));
			Instr i2 = emit(OpCode.Receive, new Reg(2)); // store in regB
			for (int i = from + 2; i <= to; i++) {
				Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.DirAddr, i));
				Instr i4 = emit(OpCode.Receive, new Reg(3)); // store in regC
				Instr i5 = emit(OpCode.Compute, new Operator(Oper.Or), new Reg(2), new Reg(3), new Reg(2)); // store in
																											// regB
			}
			Instr i6 = emit(OpCode.Branch, new Reg(2), new Target(TargetType.Rel, -2 - (3 * (to - from - 1))));
		}
	}

}
