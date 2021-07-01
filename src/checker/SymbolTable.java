package checker;

import java.util.Stack;

/**
 * Symbol table class is a class that stores information about all program
 * scopes. It contains information about opened scopes ({@link Scope}) and their
 * data, it also has a separate scope for shared data. Class also provides
 * methods to manipulate scopes' data.
 * 
 * @author Karolis Butkus
 *
 */
public class SymbolTable {
	/** Stack of opened scopes. */
	private final Stack<Scope> scopes;
	/** Scope for shared variables data */
	private final Scope sharedScope;

	/** Constructs a fresh, initially empty symbol table. */
	public SymbolTable() {
		this.scopes = new Stack<>();
		this.sharedScope = new Scope();
		openScope();
	}

	/** Adds a next deeper scope level. */
	public void openScope() {
		this.scopes.push(new Scope());
	}

	/**
	 * Removes the deepest scope level.
	 * 
	 * @throws RuntimeException if the table only contains the outer scope.
	 */
	public void closeScope() {
		if (this.scopes.size() == 1) {
			throw new IllegalStateException("Can't close outer scope");
		}
		this.scopes.pop();
	}

	/** Adds a new nested level in a current scope. */
	public void openNestedLevel() {
		this.scopes.peek().openNestedLevel();
	}

	/**
	 * Closes a nested level in a current scope.
	 * 
	 * @throws RuntimeException if the scope only contains the outer level.
	 */
	public void closeNestedLevel() {
		this.scopes.peek().closeNestedLevel();
	}

	/**
	 * Tries to declare a given identifier in the deepest scope level.
	 * 
	 * @return <code>true</code> if the identifier was added, <code>false</code> if
	 *         it was already declared in this scope.
	 */
	public boolean put(String id, Type record) {
		/*
		 * This if-statement is triggered only for outer most scopes of procedures or
		 * main block. It can return false only when main block already contains shared
		 * variable with the same name as the one that is being tried to add.
		 */
		if (this.scopes.size() == 1 && this.scopes.peek().getScopeDepth() == 2) {
			if (this.sharedScope.contains(id)) {
				return false;
			}
		}
		return this.scopes.peek().put(id, record);
	}

	/**
	 * Looks up a given identifier and returns the associated type.
	 * 
	 * @return the type associated with the inner (deepest) declaration of the
	 *         identifier; {@code null} if there is none.
	 */
	public Type type(String id) {
		Type result = null;
		for (int i = this.scopes.size() - 1; result == null && i >= 0; i--) {
			result = this.scopes.get(i).type(id);
		}
		return result;
	}

	/**
	 * Returns the (relative) scope depth at which an identifier was declared.
	 * 
	 * @return The depth at which {@code id} was declared ({@code 0} being the
	 *         current level), or {@code -1} if {@code id} was not declared.
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

	/**
	 * Returns the offset of an identifier within its own scope. The offset is the
	 * sum of the sizes of all types declared before the identifier.
	 * 
	 * @return The offset at which {@code id} was declared, or {@code -1} if it was
	 *         not declared.
	 */
	public int offset(String id) {
		Integer result = null;
		result = this.scopes.peek().offset(id);
		return result == null ? -1 : result;
	}

	/**
	 * Tries to declare a given identifier in the the shared scope.
	 * 
	 * @return <code>true</code> if the identifier was added, <code>false</code> if
	 *         it was already declared in this scope.
	 */
	public boolean putShared(String id, Type record) {
		if (this.scopes.peek().contains(id)) {
			return false;
		}
		return this.sharedScope.put(id, record);
	}

	/**
	 * Looks up a given identifier and returns the associated type.
	 * 
	 * @return the type associated with the inner (deepest) declaration of the
	 *         identifier; {@code null} if there is none.
	 */
	public Type typeShared(String id) {
		Type result = null;
		result = this.sharedScope.type(id);
		return result;
	}

	/**
	 * Returns the offset of an identifier within shared scope. The offset is the
	 * sum of the sizes of all types declared before the identifier.
	 * 
	 * @return The offset at which {@code id} was declared, or {@code -1} if it was
	 *         not declared.
	 */
	public int offsetShared(String id) {
		Integer result = null;
		result = this.sharedScope.offset(id);
		return result == null ? -1 : result;
	}

	/**
	 * Returns current deepest scope nested levels count
	 * 
	 * @return nested levels count
	 */
	public int scopeDepth() {
		return this.scopes.peek().getScopeDepth();
	}

	/**
	 * Returns the variable depth in the deepest scope
	 * 
	 * @param id - the name of the variable
	 * @return {@code 0} if variable is in the most inner nested level, {@code -1}
	 *         if variable is not declared.
	 */
	public int variableDepth(String id) {
		return this.scopes.peek().variableDepth(id);
	}

	/**
	 * Puts variable in the used structure of deepest scope, so that program knows
	 * that variable has been already used in this nested level
	 * 
	 * @param id   - the name of the variable
	 * @param type - the type of the variable
	 */
	public void putUsed(String id, Type type) {
		this.scopes.peek().putUsed(id, type);
	}

	/**
	 * Returns if the variable has been used in the inner-most level of the deepest
	 * scope
	 * 
	 * @param id - the name of the variable
	 * @return {@code true} if variable was used, {@code false} if variable was not
	 */
	public boolean isUsed(String id) {
		return this.scopes.peek().isUsed(id);
	}

	/**
	 * Return the number of currently opened scopes
	 * 
	 * @return scope count
	 */
	public int scopeCount() {
		return this.scopes.size();
	}

	/**
	 * Return the local data area size of the deepest scope
	 * 
	 * @return the size of the deepest scope
	 */
	public int scopeSize() {
		return this.scopes.peek().size();
	}

}
