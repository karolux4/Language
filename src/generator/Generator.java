package generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * Generator class is responsible for Sprockell code generation after the
 * elaboration phase has been completed. Generator is a tree visitor that
 * extends {@link PickleCannonBaseVisitor} class. Generated code is returned in
 * {@link Program} type object which can be later be written to the specified
 * file.
 * 
 * @author Karolis Butkus
 *
 */
public class Generator extends PickleCannonBaseVisitor<Instr> {

	/** The outcome of the checker phase. */
	private Result checkResult;

	/** The program being built. */
	private Program prog;

	/** Total register number */
	private final static int REGISTER_COUNT = 8;

	/** The list of arrays for each thread indicating if the register is taken */
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

	/** List of all procedure calls */
	private List<FunctionCall> calls;

	/** Mapping from procedure name to instruction number */
	private Map<String, Integer> functionAddress;

	/**
	 * Generates a Sprockell code from the given parse tree and analysis made during
	 * elaboration phase.
	 * 
	 * @param tree        - input program parse tree
	 * @param checkResult - the result of elaboration phase
	 * @return generated Sprockell program
	 */
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
		this.calls = new ArrayList<>();
		this.functionAddress = new HashMap<>();
		tree.accept(this);
		resolveFunctionCalls();
		return prog;
	}

	/**
	 * Visitor method that is executed when program is visited. At the start method
	 * adds an instruction to jump to the main body instructions and skip procedures
	 * body. Then procedures are visited. After that at the start of main body ARP
	 * value is set and thread jumping/waiting instructions are generated. Lastly,
	 * local data is allocated and main body instructions are visited.
	 */
	@Override
	public Instr visitProgram(ProgramContext ctx) {
		emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		for (ProcContext proc : ctx.proc()) {
			visit(proc);
		}
		this.prog.updateInstr(0, new Instr(OpCode.Jump, new Target(TargetType.Abs, this.instructionCount)));
		emit(OpCode.Push, new Reg(8)); // load SP
		emit(OpCode.Pop, new Reg(2)); // save SP to ARP
		// decrement value by 1 to point to first data element and not out of bounds
		emit(OpCode.Compute, new Operator(Oper.Decr), new Reg(2), new Reg(0), new Reg(2));
		if (this.checkResult.getThreadCount() > 0) {
			// Instructions dedicated for thread jumping with main thread skipping them
			generateThreadJumping(true);
		}
		// Load local data size
		emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getProcedureSize(ctx)), new Reg(3));
		// Move SP to allocate local data area
		emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(8), new Reg(3), new Reg(8));
		visit(ctx.block());
		emit(OpCode.EndProg);
		return null;
	}

	/**
	 * Visitor method that is executed when procedure is visited. At the start
	 * method generates the prologue to allocate local data area and then visits the
	 * procedure body. At the end epilogue is generated to return to the caller's
	 * address and deallocate the memory.
	 */
	@Override
	public Instr visitProc(ProcContext ctx) {
		this.functionAddress.put(ctx.ID().getText(), this.instructionCount);
		// Prologue
		// Load local data size
		emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getProcedureSize(ctx) - 1), new Reg(3));
		// Move SP to allocate local data area
		emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), new Reg(3), new Reg(8));
		visit(ctx.block());
		// Epilogue
		// Increase ARP by 1 to get callers ARP address store location
		emit(OpCode.Compute, new Operator(Oper.Incr), new Reg(2), new Reg(0), new Reg(3));
		// Increase ARP by another 1 to get return address store location
		emit(OpCode.Compute, new Operator(Oper.Incr), new Reg(3), new Reg(0), new Reg(4));
		// Move SP to point to return address
		emit(OpCode.Compute, new Operator(Oper.Add), new Reg(4), new Reg(0), new Reg(8));
		// Increment SP by 1 to point to the top of the last local data area
		emit(OpCode.Compute, new Operator(Oper.Incr), new Reg(8), new Reg(0), new Reg(8));
		// Load caller's ARP to ARP
		emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, 3), new Reg(2));
		// Load return address to regC
		emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, 4), new Reg(4));
		// Jump to return address
		emit(OpCode.Jump, new Target(TargetType.Ind, 4));
		return null;
	}

	/**
	 * Visitor method that is executed when block is visited. Method visits all the
	 * statements declared in the block.
	 */
	@Override
	public Instr visitBlock(BlockContext ctx) {
		for (StatContext stat : ctx.stat()) {
			visit(stat);
		}
		return null;
	}

	/**
	 * Visitor method that is executed when simple variable declaration is visited.
	 * Depending whether variable is shared and is the initial value provided,
	 * instructions are generated. If initial value is not given, variable is
	 * initialized with 0 (for integers 0 is 0, for booleans 0 is false).
	 */
	@Override
	public Instr visitSimpleVarStat(SimpleVarStatContext ctx) {
		if (ctx.expr() != null) {
			visit(ctx.expr());
			if (isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()), offset(ctx.ID(), true));
			} else {
				// load the offset
				Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx.ID())),
						reg(ctx));
				// compute the address in activation record with the offset
				Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
				// store the value
				Instr i3 = emit(OpCode.Store, reg(ctx.expr()), new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
				freeReg(ctx);
			}
			freeReg(ctx.expr());
		} else {
			if (isShared(ctx)) {
				Instr i = emit(OpCode.WriteInstr, new Reg(0), offset(ctx.ID(), true));
			} else {
				// load the offset
				Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx.ID())),
						reg(ctx));
				// compute the address in activation with the offset
				Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
				// store 0 in the variable memory location
				Instr i3 = emit(OpCode.Store, new Reg(0), new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
				freeReg(ctx);
			}
		}
		return null;
	}

	/**
	 * Visitor method that is executed when array variable declaration is visited.
	 * Depending whether variable is shared and is the initial value provided,
	 * instructions are generated. If initial value is not given, all array elements
	 * are initialized with 0 (for integers 0 is 0, for booleans 0 is false). For
	 * array declaration instructions the cycle is created that retrieves the values
	 * from the stack if the initial values were provided, or just stores 0 from
	 * reg0 in the correct memory areas.
	 */
	@Override
	public Instr visitArrayVarStat(ArrayVarStatContext ctx) {
		if (ctx.expr() != null) {
			visit(ctx.expr());
		}
		int arraySize = ((Type.Array) getType(ctx.ID())).getSize();
		int extraRegIndex = getFreeRegister();
		lockRegister(extraRegIndex);
		Reg extraReg = new Reg(extraRegIndex);
		Reg valueReg;
		int jump;
		if (ctx.expr() != null) {
			// if initial value is provided register to store popped values from stack will
			// be used
			valueReg = reg(ctx);
			jump = 6;
		} else {
			// register with default 0 value will be used
			valueReg = new Reg(0);
			jump = 5;
		}

		if (isShared(ctx)) {
			// load the starting address of array in shared memory
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue,
					this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx.ID())), reg(ctx.ID()));
			// load array size
			Instr i2 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
			// add array size to starting address to point to the last array element because
			// pushed values are in reverse order
			Instr i3 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx.ID()), extraReg, reg(ctx.ID()));
			// condition used in cycle to check if all array elements have been added
			Instr i4 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
			// jump over instructions if all array values have been added
			Instr i5 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, jump));
			if (ctx.expr() != null) {
				// pop the value from the stack
				Instr i6 = emit(OpCode.Pop, reg(ctx));
			}
			// write the array value to shared memory
			Instr i7 = emit(OpCode.WriteInstr, valueReg, new Addr(AddrImmDI.IndAddr, reg(ctx.ID()).getId()));
			// decrease array pointer
			Instr i8 = emit(OpCode.Compute, new Operator(Oper.Decr), reg(ctx.ID()), new Reg(0), reg(ctx.ID()));
			// decrease the counter
			Instr i9 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
			// jump back to cycle condition
			Instr i10 = emit(OpCode.Jump, new Target(TargetType.Rel, -jump));
		} else {
			// load local data offset for array
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx.ID())),
					reg(ctx.ID()));
			// compute the address of array start in the stack
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx.ID()), reg(ctx.ID()));
			// load array size
			Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
			// make array pointer point to the end of array
			Instr i4 = emit(OpCode.Compute, new Operator(Oper.Sub), reg(ctx.ID()), extraReg, reg(ctx.ID()));
			// condition used in cycle to if all array elements have been added
			Instr i5 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
			// jump over next instructions if all array elements have been added
			Instr i6 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, jump));
			if (ctx.expr() != null) {
				// pop array value from the stack
				Instr i7 = emit(OpCode.Pop, reg(ctx));
			}
			// store the array value in the local data area pointed by array pointer
			Instr i8 = emit(OpCode.Store, valueReg, new Addr(AddrImmDI.IndAddr, reg(ctx.ID()).getId()));
			// move array pointer to the next element
			Instr i9 = emit(OpCode.Compute, new Operator(Oper.Incr), reg(ctx.ID()), new Reg(0), reg(ctx.ID()));
			// decrease the counter
			Instr i10 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
			// jump back to the cycle condition
			Instr i11 = emit(OpCode.Jump, new Target(TargetType.Rel, -jump));
		}
		freeReg(ctx.ID());
		freeReg(ctx);
		freeUpRegister(extraRegIndex);

		return null;
	}

	/**
	 * Visitor method that is executed when assignment is visited. Depending whether
	 * variable is simple variable or array also is shared or not, instructions are
	 * generated. Firstly, expression and target nodes are visited to retrieve the
	 * values from them. Then if variable is primitive simple one instruction is
	 * added depending whether variable is shared or not. If variable is an array, a
	 * cycle is generated that pops the values from the stack and puts them in
	 * correct memory areas (in shared memory or local data depending on variable).
	 */
	@Override
	public Instr visitAssignStat(AssignStatContext ctx) {
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
			if (isShared(ctx.target())) {
				// load array size
				Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
				// add array size to make array pointer point to the last element
				Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx.target()), extraReg, reg(ctx.target()));
				// condition used in cycle to check if all array elements have been added
				Instr i3 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
				// jump over next instructions if all array elements have been added
				Instr i4 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 6));
				// pop the value from the stack
				Instr i5 = emit(OpCode.Pop, reg(ctx));
				// write array value to the shared memory region
				Instr i6 = emit(OpCode.WriteInstr, reg(ctx), new Addr(AddrImmDI.IndAddr, reg(ctx.target()).getId()));
				// decrease array pointer
				Instr i7 = emit(OpCode.Compute, new Operator(Oper.Decr), reg(ctx.target()), new Reg(0),
						reg(ctx.target()));
				// decrease counter
				Instr i8 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
				// jump back to cycle condition
				Instr i9 = emit(OpCode.Jump, new Target(TargetType.Rel, -6));
			} else {
				// load array size
				Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
				// compute array element position in the activation record
				Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), reg(ctx.target()), extraReg, reg(ctx.target()));
				// condition used in the cycle to check if all array elements have been added
				Instr i3 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
				// jump over next instructions if all array elements have already been added
				Instr i4 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 6));
				// pop the value from the stack
				Instr i5 = emit(OpCode.Pop, reg(ctx));
				// store the value in the local data area pointed by array pointer
				Instr i6 = emit(OpCode.Store, reg(ctx), new Addr(AddrImmDI.IndAddr, reg(ctx.target()).getId()));
				// move array pointer to the next element
				Instr i7 = emit(OpCode.Compute, new Operator(Oper.Incr), reg(ctx.target()), new Reg(0),
						reg(ctx.target()));
				// decrease counter
				Instr i8 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
				// jump back to cycle condition
				Instr i9 = emit(OpCode.Jump, new Target(TargetType.Rel, -6));
			}
			freeReg(ctx);
			freeUpRegister(extraRegIndex);
		}
		freeReg(ctx.target());
		return null;
	}

	/**
	 * Visitor method that is executed when if statement is visited. Firstly, if
	 * condition is visited. Then the conditional branch and jump instructions are
	 * added to make code execution dependent on if condition. After that if
	 * statement body is visited, also if else body was declared it is also visited
	 * with an added jump between them, so that program would never execute both
	 * bodies.
	 */
	@Override
	public Instr visitIfStat(IfStatContext ctx) {
		// Condition
		visit(ctx.expr());
		Reg r = reg(ctx);
		// check if if-condition is false
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), reg(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		// jump over the if-statement body if condition is false
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		// Then
		visit(ctx.block(0));
		Integer jumpID = this.instructionCount;
		// jump over else body
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		// Else
		Integer elseStart = this.instructionCount;
		if (ctx.block().size() > 1) {
			visit(ctx.block(1));
		}
		Integer endStart = this.instructionCount;
		Instr i4 = emit(OpCode.Nop);
		// resolve jump instruction numbers
		this.prog.updateInstr(branchID, new Instr(OpCode.Branch, r, new Target(TargetType.Abs, elseStart)));
		this.prog.updateInstr(jumpID, new Instr(OpCode.Jump, new Target(TargetType.Abs, endStart)));
		return null;
	}

	/**
	 * Visitor method that is executed when while statement is visited. Firstly,
	 * while condition is visited. Then the conditional branch and jump instructions
	 * are added to make code execution dependent on while condition. After that
	 * while body is visited and at the end of the body an instruction to jump back
	 * to the while condition is added.
	 */
	@Override
	public Instr visitWhileStat(WhileStatContext ctx) {
		// Condition
		Integer start = this.instructionCount;
		visit(ctx.expr());
		Reg r = reg(ctx);
		// check if while condition is false
		Instr i1 = emit(OpCode.Compute, new Operator(Oper.Equal), reg(ctx.expr()), new Reg(0), r);
		freeReg(ctx.expr());
		Integer branchID = this.instructionCount;
		// jump over while body if condition is false
		Instr i2 = emit(OpCode.Branch, r, new Target(TargetType.Abs, -1));
		freeReg(ctx);
		// Body
		visit(ctx.block());
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, start));
		Instr i4 = emit(OpCode.Nop);
		// resolve jump instruction number
		this.prog.updateInstr(branchID, new Instr(OpCode.Branch, r, new Target(TargetType.Abs, instructionCount - 1)));
		return null;
	}

	/**
	 * Visitor method that is executed when fork statement is visited. Firstly,
	 * instructions to update special memory unit for certain thread are added.
	 * These instructions load the address of the program that newly spawned will
	 * start executing. After that the jump instruction is added so that father
	 * thread jump over the spawned threads body. Then threads body is visited.
	 * After that threads deallocates its local data area and sets its
	 * synchronization value back to 0. If thread will be spawned again it enters
	 * wait loop similar to the starting one, if not end program instruction is
	 * added and threads ends its execution.
	 * 
	 */
	@Override
	public Instr visitForkStat(ForkStatContext ctx) {
		// load and write starting instruction number for the spawned thread
		Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.instructionCount + 3), reg(ctx));
		Instr i2 = emit(OpCode.WriteInstr, reg(ctx), new Addr(AddrImmDI.DirAddr, concurrentThreads + 1));
		freeReg(ctx);
		int jumpID = this.instructionCount;
		// jump over new thread body
		Instr i3 = emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		// decrease this thread call count
		int callCount = this.checkResult.decreaseThreadCallCount(concurrentThreads);
		// add new concurent thread to the thread stack
		this.currentThreads.push(concurrentThreads + 1);
		this.concurrentThreads++;
		// Load local data size for the new thread
		emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getProcedureSize(ctx)), new Reg(3));
		// Move SP to allocate local data area for the new thread
		emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(8), new Reg(3), new Reg(8));
		visit(ctx.block());
		// after thread end pop the thread from thread stack
		this.currentThreads.pop();
		if (callCount == 1) {
			// if the thread with the same concurrent thread number won't be spawned again
			// write zero to its synchronization area in memory and end program
			Instr i4 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.IndAddr, 1));
			Instr i5 = emit(OpCode.EndProg);
		} else {
			// if the thread with the same concurrent thread number will be spawned again
			// deallocate AR, write 0 to synchronization area and generating waiting cycle
			Instr i4 = emit(OpCode.Push, new Reg(2));
			Instr i5 = emit(OpCode.Pop, new Reg(8));
			Instr i6 = emit(OpCode.Compute, new Operator(Oper.Incr), new Reg(8), new Reg(0), new Reg(8)); // restore SP
																											// to the
																											// start
			Instr i7 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.IndAddr, 1));
			generateThreadJumping(false);
		}
		this.prog.updateInstr(jumpID, new Instr(OpCode.Jump, new Target(TargetType.Abs, this.instructionCount)));
		return null;
	}

	/**
	 * Visitor method that is executed when join statement is visited. This thread
	 * generates instruction that makes thread wait while all its children and their
	 * descendants will end their execution and set their synchronization units back
	 * to 0.
	 * 
	 */
	@Override
	public Instr visitJoinStat(JoinStatContext ctx) {
		generateThreadJoin(this.currentThreads.peek(), this.concurrentThreads);
		this.concurrentThreads = this.currentThreads.peek();
		return null;
	}

	/**
	 * Visitor method that is executed when sync statement is visited. This visitor
	 * generates a loop that makes thread try to set global lock from 0 to 1. After
	 * that sync statement body is visited. At the end instruction to set sync lock
	 * back to 0 is added.
	 * 
	 */
	@Override
	public Instr visitSyncStat(SyncStatContext ctx) {
		Instr i1 = emit(OpCode.TestAndSet, new Addr(AddrImmDI.DirAddr, this.checkResult.getLockAddress()));
		Instr i2 = emit(OpCode.Receive, reg(ctx));
		Instr i3 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 2));
		Instr i4 = emit(OpCode.Jump, new Target(TargetType.Rel, -3));
		freeReg(ctx);
		visit(ctx.block());
		Instr i5 = emit(OpCode.WriteInstr, new Reg(0), new Addr(AddrImmDI.DirAddr, this.checkResult.getLockAddress()));
		return null;
	}

	/**
	 * Visitor method that is executed when block statement is visited. Method
	 * visits the block.
	 * 
	 */
	@Override
	public Instr visitBlockStat(BlockStatContext ctx) {
		visit(ctx.block());
		return null;
	}

	/**
	 * Visitor method that is executed when print statement is visited. If
	 * expression is of primitive type, then single instruction to print the
	 * expression value is added (print the number for integers, print 0 for false
	 * and print 1 for true). If expression is of array type, then the cycle is
	 * generated. Firstly character '{' is printed out, then all array values are
	 * popped from the stack and print and at the end the '}' is printed.
	 * 
	 */
	@Override
	public Instr visitPrintStat(PrintStatContext ctx) {
		visit(ctx.expr());
		if (getType(ctx.expr()) == Type.INT || getType(ctx.expr()) == Type.BOOL) {
			Instr i = emit(OpCode.WriteInstr, reg(ctx.expr()), Addr.NUMBER_IO);
			freeReg(ctx.expr());
		} else {
			int arraySize = ((Type.Array) getType(ctx.expr())).getSize();
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 123), reg(ctx)); // load '{' symbol
			Instr i2 = emit(OpCode.WriteInstr, reg(ctx), Addr.CHAR_IO); // write '{' symbol
			Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 10), reg(ctx)); // load '\n' symbol
			Instr i4 = emit(OpCode.WriteInstr, reg(ctx), Addr.CHAR_IO); // write '\n' symbol
			// generate array cycle that retrieves values from the stack and prints them
			Instr i5 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize - 1), extraReg);
			Instr i6 = emit(OpCode.Compute, new Operator(Oper.Lt), extraReg, new Reg(0), reg(ctx));
			Instr i7 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 6));
			Instr i8 = emit(OpCode.Compute, new Operator(Oper.Add), new Reg(8), extraReg, reg(ctx));
			Instr i9 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
			Instr i10 = emit(OpCode.WriteInstr, reg(ctx), Addr.NUMBER_IO);
			Instr i11 = emit(OpCode.Compute, new Operator(Oper.Decr), extraReg, new Reg(0), extraReg);
			Instr i12 = emit(OpCode.Jump, new Target(TargetType.Rel, -6));
			Instr i13 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 125), reg(ctx)); // load '}' symbol
			Instr i14 = emit(OpCode.WriteInstr, reg(ctx), Addr.CHAR_IO); // write '}' symbol
			Instr i15 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 10), reg(ctx)); // load '\n' symbol
			Instr i16 = emit(OpCode.WriteInstr, reg(ctx), Addr.CHAR_IO); // write '\n' symbol
			Instr i17 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize), extraReg);
			Instr i18 = emit(OpCode.Compute, new Operator(Oper.Add), new Reg(8), extraReg, new Reg(8)); // restore SP
																										// pointer
			freeReg(ctx);
			freeUpRegister(extraRegIndex);
		}
		return null;
	}

	/**
	 * Visitor method that is executed when procedure call statement is visited.
	 * Firstly, method generates a precall instructions that push the return address
	 * and caller's ARP for newly created activation record. Then parameter values
	 * are pushed. After that ARP is moved to point to the start of parameters area
	 * and the jump to the function is added. At the time function call is visited
	 * it is impossible to determine function jump address so it is added to the
	 * {@link #calls} list which at the of code generation updates the jump address.
	 * 
	 */
	@Override
	public Instr visitCallStat(CallStatContext ctx) {
		int returnID = this.instructionCount;
		Reg register = reg(ctx);
		// Precall
		emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 0), register); // temporary value for return address
		emit(OpCode.Push, register); // push return address
		emit(OpCode.Push, new Reg(2)); // push caller's ARP
		emit(OpCode.Compute, new Operator(Oper.Decr), new Reg(8), new Reg(0), register); // save ARP position for
																							// function
		visit(ctx.args());
		emit(OpCode.Compute, new Operator(Oper.Add), register, new Reg(0), new Reg(2)); // move ARP
		freeReg(ctx);
		// jump to procedure (temporary uses -1 because it can only be set after all
		// functions are generated)
		emit(OpCode.Jump, new Target(TargetType.Abs, -1));
		calls.add(new FunctionCall(this.instructionCount - 1, ctx.ID().getText()));
		this.prog.updateInstr(returnID,
				new Instr(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.instructionCount), register));
		return null;
	}

	/**
	 * Visitor method that is executed when identifier target is visited. Visitor
	 * generates instructions that load the address of the identifier into the
	 * register.
	 * 
	 */
	@Override
	public Instr visitIdTarget(IdTargetContext ctx) {
		if (isShared(ctx)) {
			Instr i = emit(OpCode.Load,
					new Addr(AddrImmDI.ImmValue, this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)),
					reg(ctx));
		} else {
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
		}
		return null;
	}

	/**
	 * Visitor method that is executed when array target is visited. Firstly,
	 * expression is visited to retrieve the index. Then visitor generates
	 * instructions that load the address of the array variable at the index
	 * position into the register.
	 * 
	 */
	@Override
	public Instr visitArrayTarget(ArrayTargetContext ctx) {
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
			Instr i3 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
			freeReg(ctx.expr());
		}
		return null;
	}

	/**
	 * Visitor method that is executed when procedure call arguments are visited.
	 * Visitor visits each argument expression separately and push them onto the
	 * stack.
	 * 
	 */
	@Override
	public Instr visitArgs(ArgsContext ctx) {
		for (ExprContext expr : ctx.expr()) {
			visit(expr);
			if (getType(expr) == Type.INT || getType(expr) == Type.BOOL) {
				Instr i1 = emit(OpCode.Push, reg(expr));
				freeReg(expr);
			} else {
				// array elements are already pushed on stack
			}
		}
		return null;
	}

	/**
	 * Visitor method that is executed when prefix expression is visited. Visitor
	 * visits expression and generates instructions to apply prefix operation.
	 * 
	 */
	@Override
	public Instr visitPrfExpr(PrfExprContext ctx) {
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

	/**
	 * Visitor method that is executed when multiplication/division expression is
	 * visited. Visitor firstly visits both expressions. For multiplication,
	 * multiplication instruction is added. For division, a cycle is created that
	 * decrease first expression by the second instruction until first becomes
	 * smaller than second. Because division expressions can be negative firstly the
	 * instructions to determine the end sign are added and only then the cycle with
	 * positive values are started. The end result is stored into the register.
	 * 
	 */
	@Override
	public Instr visitMultExpr(MultExprContext ctx) {
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

	/**
	 * Visitor method that is executed when addition/subtraction expression is
	 * visited. Visitor firstly visits both expressions. Then addition/subtraction
	 * is added and result is stored into the register.
	 * 
	 */
	@Override
	public Instr visitPlusExpr(PlusExprContext ctx) {
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

	/**
	 * Visitor method that is executed when comparison expression is visited.
	 * Visitor firstly visits both expressions. Then comparison operation is added
	 * and result is stored into the register. For the array comparison the cycle is
	 * generated so that values would be compared value-to-value. If array sizes
	 * differ which can be obtained from elaboration results, then simply false is
	 * loaded for equality operation and true for inequality operation.
	 * 
	 */
	@Override
	public Instr visitCompExpr(CompExprContext ctx) {
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

	/**
	 * Visitor method that is executed when boolean expression is visited. Visitor
	 * firstly visits both expressions. Then AND/OR operation is added and result is
	 * stored into the register.
	 * 
	 */
	@Override
	public Instr visitBoolExpr(BoolExprContext ctx) {
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

	/**
	 * Visitor method that is executed when parenthesis expression is visited.
	 * Visitor visits the expression and assigns its register to himself.
	 * 
	 */
	@Override
	public Instr visitParExpr(ParExprContext ctx) {
		visit(ctx.expr());
		setReg(ctx, regs.get(ctx.expr()));
		return null;
	}

	/**
	 * Visitor method that is executed when id expression is visited. For primitive
	 * variables simple load instructions are added (load from shared or local data
	 * memory depending on the variable). If id expression points to the array, the
	 * cycle is created that push all array value from index 0 to end of array to
	 * the stack.
	 * 
	 */
	@Override
	public Instr visitIdExpr(IdExprContext ctx) {
		if (getType(ctx) == Type.INT || getType(ctx) == Type.BOOL) {
			if (isShared(ctx)) {
				Instr i1 = emit(OpCode.ReadInstr, offset(ctx, true));
				Instr i2 = emit(OpCode.Receive, reg(ctx));
			} else {
				Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
				Instr i2 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
				Instr i3 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
			}
		} else {
			int arraySize = ((Type.Array) getType(ctx)).getSize();
			int extraRegIndex = getFreeRegister();
			lockRegister(extraRegIndex);
			Reg extraReg = new Reg(extraRegIndex);
			// load starting array index
			Instr i1 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 0), extraReg);
			// load array size
			Instr i2 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, arraySize), reg(ctx));
			// condition used in cycle to check if all array values have been pushed
			Instr i3 = emit(OpCode.Compute, new Operator(Oper.GtE), extraReg, reg(ctx), reg(ctx));
			// jump over next instructions if all array values have been pushed
			Instr i4 = emit(OpCode.Branch, reg(ctx), new Target(TargetType.Rel, 8));
			if (isShared(ctx)) {
				// load array starting address in shared memory
				Instr i5 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue,
						this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)), reg(ctx));
				// add current array element index
				Instr i6 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), extraReg, reg(ctx));
				// retrieve array element
				Instr i7 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
				Instr i8 = emit(OpCode.Receive, reg(ctx));
			} else {
				// load array starting address in local data memory
				Instr i5 = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
				// add current array element index
				Instr i6 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), extraReg, reg(ctx));
				// get the address in the stack using ARP
				Instr i7 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
				// load array element value
				Instr i8 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
			}
			// push array element value on the stack
			Instr i9 = emit(OpCode.Push, reg(ctx));
			// increase the index
			Instr i10 = emit(OpCode.Compute, new Operator(Oper.Incr), extraReg, new Reg(0), extraReg);
			// jump back to the cycle contition
			Instr i11 = emit(OpCode.Jump, new Target(TargetType.Rel, -9));
			freeUpRegister(extraRegIndex);
			freeReg(ctx);

		}
		return null;
	}

	/**
	 * Visitor method that is executed when number expression is visited. Visitor
	 * adds an instruction to load a number into the register.
	 * 
	 */
	@Override
	public Instr visitNumExpr(NumExprContext ctx) {
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, Integer.parseInt(ctx.NUM().getText())), reg(ctx));
		return null;
	}

	/**
	 * Visitor method that is executed when true expression is visited. Visitor adds
	 * an instruction to load a true into the register.
	 * 
	 */
	@Override
	public Instr visitTrueExpr(TrueExprContext ctx) {
		Instr i = emit(OpCode.Load, new Addr(AddrImmDI.ImmValue, 1), reg(ctx));
		return null;
	}

	/**
	 * Visitor method that is executed when false expression is visited. Visitor
	 * assigns reg0 to this node as it already contains false value.
	 * 
	 */
	@Override
	public Instr visitFalseExpr(FalseExprContext ctx) {
		setReg(ctx, new Reg(0));
		return null;
	}

	/**
	 * Visitor method that is executed when array index expression is visited.
	 * Visitor firstly visits index expression and then generates the instructions
	 * that load the value from the specified memory region (shared or local data).
	 * 
	 */
	@Override
	public Instr visitIndexExpr(IndexExprContext ctx) {
		visit(ctx.expr());
		if (isShared(ctx)) {
			// load array starting address in shared memory
			Instr i1 = emit(OpCode.Load, new Addr(Addr.AddrImmDI.ImmValue,
					this.checkResult.getBaseOffset() + this.checkResult.getOffset(ctx)), reg(ctx));
			// compute the address of array element at the specified index
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			freeReg(ctx.expr());
			// retrieve array element value
			Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()));
			Instr i4 = emit(OpCode.Receive, reg(ctx));
		} else {
			// load array starting address in local data memory
			Instr i1 = emit(OpCode.Load, new Addr(Addr.AddrImmDI.ImmValue, this.checkResult.getOffset(ctx)), reg(ctx));
			// compute the address of array element at the specified index
			Instr i2 = emit(OpCode.Compute, new Operator(Oper.Add), reg(ctx), reg(ctx.expr()), reg(ctx));
			// get the address in the stack using ARP
			Instr i3 = emit(OpCode.Compute, new Operator(Oper.Sub), new Reg(2), reg(ctx), reg(ctx));
			freeReg(ctx.expr());
			// retrieve array element value
			Instr i4 = emit(OpCode.Load, new Addr(AddrImmDI.IndAddr, reg(ctx).getId()), reg(ctx));
		}
		return null;
	}

	/**
	 * Visitor method that is executed when array expression is visited. Visitor
	 * adds instructions that visits every expression push their values on the
	 * stack.
	 * 
	 */
	@Override
	public Instr visitArrayExpr(ArrayExprContext ctx) {
		for (int i = 0; i < ctx.expr().size(); i++) {
			visit(ctx.expr(i));
			Instr i1 = emit(OpCode.Push, reg(ctx.expr(i)));
			freeReg(ctx.expr(i));
		}
		return null;
	}

	/**
	 * Adds the specified instruction to the program
	 * 
	 * @param opCode - instruction code
	 * @param args   - instruction arguments
	 * @return - the added instruction
	 */
	private Instr emit(OpCode opCode, Operand... args) {
		Instr result = new Instr(opCode, args);
		this.prog.addInstr(result);
		this.instructionCount++;
		return result;
	}

	/** Convenience method that returns free register id for current thread */
	private int getFreeRegister() {
		for (int i = 3; i < this.isRegisterTaken.get(currentThreads.peek()).length; i++) {
			if (!this.isRegisterTaken.get(currentThreads.peek())[i]) {
				return i;
			}
		}
		return -1;
	}

	/** Convenience method that free specified register */
	private void freeUpRegister(int i) {
		this.isRegisterTaken.get(currentThreads.peek())[i] = false;
	}

	/** Convenience method that locks specified register */
	private void lockRegister(int i) {
		if (this.isRegisterTaken.get(currentThreads.peek())[i]) {
			throw new RuntimeException("Register is already locked");
		}
		this.isRegisterTaken.get(currentThreads.peek())[i] = true;
	}

	/**
	 * Convenience method that returns Reg object associated with a node, or creates
	 * a new one if node is not associated with any
	 */
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

	/** Convenience method that frees the register from the specified node */
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
	 * Retrieves the offset with an added modifier of a variable node from the
	 * checker result, wrapped in a {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, int modifier) {
		return new Addr(AddrImmDI.DirAddr, modifier + this.checkResult.getOffset(node));
	}

	/**
	 * Retrieves the offset of a shared variable node from the checker result,
	 * wrapped in a {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, boolean isShared) {
		if (isShared) {
			return new Addr(AddrImmDI.DirAddr, this.checkResult.getBaseOffset() + this.checkResult.getOffset(node));
		}
		return new Addr(AddrImmDI.DirAddr, this.checkResult.getOffset(node));
	}

	/**
	 * Retrieves the offset with an added modifier of a shared variable node from
	 * the checker result, wrapped in a {@link Addr} operand.
	 */
	private Addr offset(ParseTree node, boolean isShared, int modifier) {
		if (isShared) {
			return new Addr(AddrImmDI.DirAddr,
					this.checkResult.getBaseOffset() + this.checkResult.getOffset(node) + modifier);
		}
		return new Addr(AddrImmDI.DirAddr, this.checkResult.getOffset(node) + modifier);
	}

	/**
	 * Returns <code>true</code> if node is stored in shared memory, otherwise
	 * <code>false</code>
	 */
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
		// non-main threads cycle until the instruction number is written in regSprID
		// address of shared memory
		Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.IndAddr, 1));
		Instr i4 = emit(OpCode.Receive, new Reg(3));
		Instr i5 = emit(OpCode.Compute, new Operator(Oper.Equal), new Reg(3), new Reg(0), new Reg(4));
		Instr i6 = emit(OpCode.Branch, new Reg(4), new Target(TargetType.Rel, -3));
		Instr i7 = emit(OpCode.Jump, new Target(TargetType.Ind, 3));
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

	/** Convenience method to generate instruction for thread joining */
	private void generateThreadJoin(int from, int to) {
		if (from < 0 || to < 0 || from > this.checkResult.getThreadCount() || to > this.checkResult.getThreadCount()) {
			throw new RuntimeException("Thread ids are out of bounds");
		}
		// instructions to read joining thread synchronization values stored in shared
		// memory until all threads state that they have ended (contains 0)
		if (from < to) {
			Instr i1 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.DirAddr, from + 1));
			Instr i2 = emit(OpCode.Receive, new Reg(3)); // store in regB
			for (int i = from + 2; i <= to; i++) {
				Instr i3 = emit(OpCode.ReadInstr, new Addr(AddrImmDI.DirAddr, i));
				Instr i4 = emit(OpCode.Receive, new Reg(4)); // store in regC
				Instr i5 = emit(OpCode.Compute, new Operator(Oper.Or), new Reg(3), new Reg(4), new Reg(3)); // store in
																											// regB
			}
			Instr i6 = emit(OpCode.Branch, new Reg(3), new Target(TargetType.Rel, -2 - (3 * (to - from - 1))));
		}
	}

	/**
	 * Private class to store procedure call instructions so that they can be
	 * resolved after all procedures have been generated
	 * 
	 * @author Karolis Butkus
	 *
	 */
	private class FunctionCall {
		/** Instruction number of the procedure call */
		private int instructionNumber;
		/** Procedure name */
		private String functionID;

		/** Constructs procedure call from the instruction number and procedure name */
		public FunctionCall(int instructionNumber, String functionID) {
			this.instructionNumber = instructionNumber;
			this.functionID = functionID;
		}
	}

	/**
	 * Updates all procedure call jump instructions to point to correct procedure
	 * starting addresses
	 */
	private void resolveFunctionCalls() {
		for (FunctionCall call : calls) {
			int start = this.functionAddress.get(call.functionID);
			this.prog.updateInstr(call.instructionNumber, new Instr(OpCode.Jump, new Target(TargetType.Abs, start)));
		}
	}

}
