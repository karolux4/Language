package checker;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Result class is a class that stores information gathered during the
 * elaboration phase. Such information is the types and offsets of the
 * variables. Information whether variables are shared or not, procedures local
 * and parameter data sizes, the number of thread calls.
 * 
 * @author Karolis Butkus
 *
 */
public class Result {
	/** Mapping from variables to types. */
	private final ParseTreeProperty<Type> types = new ParseTreeProperty<>();
	/** Mapping from variables to offsets in activation record. */
	private final ParseTreeProperty<Integer> offsets = new ParseTreeProperty<>();
	/** Property stating if parse tree variable node is stored in a shared memory */
	private final ParseTreeProperty<Boolean> isShared = new ParseTreeProperty<>();
	/** Property that holds the local+parameters data size of the procedure/thread */
	private final ParseTreeProperty<Integer> procedureDataSize = new ParseTreeProperty<>();
	/**
	 * List to store how many times the certain thread has been spawned (excluding
	 * main thread)
	 */
	private List<Integer> threadCalls = new ArrayList<>();

	/**
	 * Adds an association from a parse tree node containing a variable reference to
	 * the offset of that variable.
	 */
	public void setOffset(ParseTree node, int offset) {
		this.offsets.put(node, offset);
	}

	/**
	 * Returns the declaration offset of the variable accessed in a given parse tree
	 * node.
	 * 
	 * @param node - the parse tree node
	 * @return the offset in activation record for that node reference
	 */
	public int getOffset(ParseTree node) {
		return this.offsets.get(node);
	}

	/**
	 * Adds an association from a parse tree expression, type, or assignment target
	 * node to the corresponding (inferred) type.
	 */
	public void setType(ParseTree node, Type type) {
		this.types.put(node, type);
	}

	/**
	 * Returns the type associated with a given parse tree node.
	 * 
	 * @param node - the parse tree node
	 * @return the type associated with a node
	 */
	public Type getType(ParseTree node) {
		return this.types.get(node);
	}

	/**
	 * Adds an association to indicate if parse tree node referencing a variable is
	 * stored in shared memory
	 */
	public void setIsShared(ParseTree node, Boolean bool) {
		this.isShared.put(node, bool);
	}

	/**
	 * Returns a boolean about whether variable associated with a node is stored in
	 * shared memory
	 * 
	 * @param node - the parse tree node
	 * @return <code>true</code> if variable is stored in shared memory;
	 *         <code>false</code> if variable is not stored in shared memory
	 */
	public Boolean getIsShared(ParseTree node) {
		return this.isShared.get(node);
	}

	/**
	 * Return maximum concurrently working thread count
	 * 
	 * @return maximum thread count
	 */
	public int getThreadCount() {
		return this.threadCalls.size();
	}

	/**
	 * Return the address for synchronization lock
	 * 
	 * @return the address in shared memory of global lock
	 */
	public int getLockAddress() {
		return 0;
	}

	/**
	 * Return base offset for shared memory (the start is dedicated for thread
	 * synchronization and lock)
	 * 
	 * @return the offset to free shared memory region
	 */
	public int getBaseOffset() {
		return this.threadCalls.size() + 1;
	}

	/**
	 * Add a thread call to the list
	 * 
	 * @param i - thread id
	 */
	public void addThread(int i) {
		if (i >= threadCalls.size()) {
			threadCalls.add(1);
		} else {
			threadCalls.set(i, threadCalls.get(i) + 1);
		}
	}

	/**
	 * Get a thread call count from the list
	 * 
	 * @param i - thread id
	 * @return - the number of times this thread has been spawned
	 */
	public int getThreadCallCount(int i) {
		return this.threadCalls.get(i);
	}

	/**
	 * Decreases a thread call count in the list
	 * 
	 * @param i - thread id
	 * @return - the thread call count before a decrease
	 */
	public int decreaseThreadCallCount(int i) {
		return this.threadCalls.set(i, threadCalls.get(i) - 1);
	}

	/**
	 * Set the size of local+parameters data area for the procedure/threads
	 * 
	 * @param node - parse tree node associated with the procedure/thread
	 * @param size - the size of local+parameters data area
	 */
	public void setProcedureSize(ParseTree node, int size) {
		this.procedureDataSize.put(node, size);
	}

	/**
	 * Get the size of local+parameters data area for the procedure/threads
	 * 
	 * @param node - parse tree node associated with the procedure/thread
	 * @return the size of procedure data area
	 */
	public int getProcedureSize(ParseTree node) {
		return this.procedureDataSize.get(node);
	}

}
