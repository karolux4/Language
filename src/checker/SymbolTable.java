package checker;

import java.util.Stack;

public class SymbolTable {
	/** Stack of name-to-type maps. */
	private final Stack<Scope> scopes;
	/**Scope of name-to-type for shared variables*/
	private final Scope sharedScope;

	/** Constructs a fresh, initially empty symbol table. */
	public SymbolTable() {
		System.out.println("Initial scope");
		this.scopes = new Stack<>();
		this.sharedScope = new Scope();
		openScope();
	}
	/** Adds a next deeper scope level. */
	public void openScope() {
		System.out.println("Open new scope");
		this.scopes.push(new Scope());
	}

	/** Removes the deepest scope level.
	 * @throws RuntimeException if the table only contains the outer scope.
	 */
	public void closeScope() {
		System.out.println("Close scope");
		if (this.scopes.size() == 1) {
			throw new IllegalStateException("Can't close outer scope");
		}
		this.scopes.pop();
	}
	
	/** Adds a new nested level in a scope. */
	public void openNestedLevel() {
		System.out.println("Open new nested level");
		this.scopes.peek().openNestedLevel();
	}

	/** Closes a nested level in a scope.
	 * @throws RuntimeException if the scope only contains the outer level.
	 */
	public void closeNestedLevel() {
		System.out.println("Close new nested level");
		this.scopes.peek().closeNestedLevel();
	}

	/** Tries to declare a given identifier in the deepest scope level.
	 * @return <code>true</code> if the identifier was added,
	 * <code>false</code> if it was already declared in this scope.
	 */
	public boolean put(String id, Type record) {
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
		for (int i = this.scopes.size() - 1; result == null && i >= 0; i--) {
			result = this.scopes.get(i).offset(id);
		}
		return result == null ? -1 : result;
	}
	
	
	/** Tries to declare a given identifier in the the shared scope.
	 * @return <code>true</code> if the identifier was added,
	 * <code>false</code> if it was already declared in this scope.
	 */
	public boolean putShared(String id, Type record) {
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
	 * Returns current scope depth
	 * @return Current scope depth
	 */
	public int scopeDepth() {
		return this.scopes.peek().getScopeDepth();
	}
	
}
