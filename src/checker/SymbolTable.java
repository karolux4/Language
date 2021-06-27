package checker;

import java.util.Stack;

public class SymbolTable {
	/** Stack of name-to-type maps. */
	private final Stack<Scope> scopes;
	/**Scope of name-to-type for shared variables*/
	private final Scope sharedScope;

	/** Constructs a fresh, initially empty symbol table. */
	public SymbolTable() {
		//System.out.println("Initial scope");
		this.scopes = new Stack<>();
		this.sharedScope = new Scope();
		openScope();
	}
	/** Adds a next deeper scope level. */
	public void openScope() {
		//System.out.println("Open new scope");
		this.scopes.push(new Scope());
	}

	/** Removes the deepest scope level.
	 * @throws RuntimeException if the table only contains the outer scope.
	 */
	public void closeScope() {
		//System.out.println("Close scope");
		if (this.scopes.size() == 1) {
			throw new IllegalStateException("Can't close outer scope");
		}
		this.scopes.pop();
	}
	
	/** Adds a new nested level in a scope. */
	public void openNestedLevel() {
		//System.out.println("Open new nested level");
		this.scopes.peek().openNestedLevel();
	}

	/** Closes a nested level in a scope.
	 * @throws RuntimeException if the scope only contains the outer level.
	 */
	public void closeNestedLevel() {
		//System.out.println("Close new nested level");
		this.scopes.peek().closeNestedLevel();
	}

	/** Tries to declare a given identifier in the deepest scope level.
	 * @return <code>true</code> if the identifier was added,
	 * <code>false</code> if it was already declared in this scope.
	 */
	public boolean put(String id, Type record) {
		// This is if-statement is triggered only for outer most scopes of procedures or main block
		// and can return false only from main block as shared variables can be declared only there
		if(this.scopes.size()==1&& this.scopes.peek().getScopeDepth()==2) {
			if(this.sharedScope.contains(id)) {
				return false;
			}
		}
		return this.scopes.peek().put(id, record);
	}

	/** Looks up a given identifier and returns the associated type.
	 * @return the record associated with the inner (deepest) declaration
	 * of the identifier; {@code null} if there is none.
	 */
	public Type type(String id) {
		Type result = null;
		for (int i = this.scopes.size() - 1; result == null && i >= 0; i--) {
			result = this.scopes.get(i).type(id);
		}
		return result;
	}

	/** Returns the (relative) depth at which an identifier was declared.
	 * @return The depth at which {@code id} was declared ({@code 0} being
	 * the current level), or {@code -1} if {@code id} was not declared.
	 */
	public int depth(String id) {
		int result = -1;
		for (int i = this.scopes.size() - 1; result < 0 && i >= 0; i--) {
			if (this.scopes.get(i).contains(id)) {
				result = this.scopes.size() - 1 - i;
			}
		}
		return result;
	}

	/** Returns the offset of an identifier within its own scope. The
	 * offset is the sum of the sizes of all types declared before the 
	 * identifier.
	 * @return The offset at which {@code id} was declared, or {@code -1}
	 * if it was not declared.
	 */
	public int offset(String id) {
		Integer result = null;
		result = this.scopes.peek().offset(id);
		return result == null ? -1 : result;
	}
	
	
	/** Tries to declare a given identifier in the the shared scope.
	 * @return <code>true</code> if the identifier was added,
	 * <code>false</code> if it was already declared in this scope.
	 */
	public boolean putShared(String id, Type record) {
		if(this.scopes.peek().contains(id)) {
			return false;
		}
		return this.sharedScope.put(id, record);
	}

	/** Looks up a given identifier and returns the associated type.
	 * @return the record associated with the inner (deepest) declaration
	 * of the identifier; {@code null} if there is none.
	 */
	public Type typeShared(String id) {
		Type result = null;
		result = this.sharedScope.type(id);
		return result;
	}
	
	/** Returns the offset of an identifier within shared scope. The
	 * offset is the sum of the sizes of all types declared before the 
	 * identifier.
	 * @return The offset at which {@code id} was declared, or {@code -1}
	 * if it was not declared.
	 */
	public int offsetShared(String id) {
		Integer result = null;
		result = this.sharedScope.offset(id);
		return result == null ? -1 : result;
	}
	
	/**
	 * Returns current scope nested levels count
	 * @return Current scope nested levels count
	 */
	public int scopeDepth() {
		return this.scopes.peek().getScopeDepth();
	}
	
	/**
	 * Returns the variable depth in the top scope
	 * @param id
	 * @return {@code 0} if variable is in the most inner nested level,
	 * {@code -1} if variable is not declared.
	 */
	public int variableDepth(String id) {
		return this.scopes.peek().variableDepth(id);
	}
	
	/**
	 * Puts variable in the used structure, so that program knows that variable
	 * has been already used in this nested level
	 * @param id
	 * @param type
	 */
	public void putUsed(String id, Type type) {
		this.scopes.peek().putUsed(id, type);
	}
	
	/**
	 * Returns if the variable has been used in the inner-most level
	 * @param id
	 * @return {@code true} if variable was used, {@code false} if variable was not
	 */
	public boolean isUsed(String id) {
		return this.scopes.peek().isUsed(id);
	}
	
	/**
	 * Returns scopes count
	 * @return scope count
	 */
	public int scopeCount() {
		return this.scopes.size();
	}
	
	/**
	 * Return the local data area size of the scope
	 * @return the size of the scope
	 */
	public int scopeSize() {
		return this.scopes.peek().size();
	}
	
}
