package checker;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class Result {
	/** Mapping from expressions to types. */
	private final ParseTreeProperty<Type> types = new ParseTreeProperty<>();
	/** Mapping from variables to coordinates. */
	private final ParseTreeProperty<Integer> offsets = new ParseTreeProperty<>();
	/** Property stating if node is stored in a shared memory*/
	private final ParseTreeProperty<Boolean> isShared = new ParseTreeProperty<>();
	/** Property that holds the local data (including parameters) size of the procedure*/
	private final ParseTreeProperty<Integer> procedureDataSize = new ParseTreeProperty<>();
	/** List to store how many times the certain thread has been called*/
	private List<Integer> threadCalls = new ArrayList<>();

	/** Adds an association from a parse tree node containing a 
	 * variable reference to the offset
	 * of that variable. */
	public void setOffset(ParseTree node, int offset) {
		this.offsets.put(node, offset);
	}

	/** Returns the declaration offset of the variable 
	 * accessed in a given parse tree node. */
	public int getOffset(ParseTree node) {
		return this.offsets.get(node);
	}

	/** Adds an association from a parse tree expression, type,
	 * or assignment target node to the corresponding (inferred) type. */
	public void setType(ParseTree node, Type type) {
		this.types.put(node, type);
	}

	/** Returns the type associated with a given parse tree node. */
	public Type getType(ParseTree node) {
		return this.types.get(node);
	}
	
	/** Adds an association to indicate if node is stored in shared memory*/
	public void setIsShared(ParseTree node, Boolean bool) {
		this.isShared.put(node, bool);
	}
	
	/** Returns a boolean is node stored in shared memory*/
	public Boolean getIsShared(ParseTree node) {
		return this.isShared.get(node);
	}
	
	/** Return thread count */
	public int getThreadCount() {
		return this.threadCalls.size();
	}
	
	/** Return the address for synchronization lock*/
	public int getLockAddress() {
		return 0;
	}
	
	/** Return base offset for shared memory (the start is dedicated for thread synchronization and lock)*/
	public int getBaseOffset() {
		return this.threadCalls.size()+1;
	}
	
	/** Add a thread call to the list*/
	public void addThread(int i) {
		if(i>=threadCalls.size()) {
			threadCalls.add(1);
		}
		else {
			threadCalls.set(i, threadCalls.get(i)+1);
		}
	}
	
	/** Get a thread call count from the list*/
	public int getThreadCallCount(int i) {
		return this.threadCalls.get(i);
	}
	
	
	/** Decreases a thread call count in the list*/
	public int decreaseThreadCallCount(int i) {
		return this.threadCalls.set(i, threadCalls.get(i)-1);
	}
	
	public void setProcedureSize(ParseTree node, int size) {
		this.procedureDataSize.put(node, size);
	}
	
	public int getProcedureSize(ParseTree node) {
		return this.procedureDataSize.get(node);
	}
	
}
