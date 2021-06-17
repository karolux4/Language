package checker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class Scope {
	/** Current size of this scope (in bytes). 
	 * Used to calculate offsets of newly declared variables. */
	private int size;
	/** Stack for each nested level to store map from declared variables to their types. */
	private final Stack<Map<String, Type>> types;
	/** Stack for each nested level to store map from declared variables to their offset within the allocation
	 * record of this scope. */
	private final Stack<Map<String, Integer>> offsets;
	/** Stack for each nested level to store used variables in that scope, so that re-declaration after use would be prevented*/
	private final Stack<Map<String, Type>> used;
	

	/** Constructs a fresh, initially empty scope. */
	public Scope() {
		this.types = new Stack<>();
		this.offsets = new Stack<>();
		this.used = new Stack<>();
		this.types.push(new LinkedHashMap<>());
		this.offsets.push(new LinkedHashMap<>());
		this.used.push(new LinkedHashMap<>());
	}
	
	/** Opens new nested level of the scope*/
	public void openNestedLevel() {
		types.push(new LinkedHashMap<>());
		offsets.push(new LinkedHashMap<>());
		used.push(new LinkedHashMap<>());
	}
	
	/** Closes top nested level of the scope*/
	public void closeNestedLevel() {
		if (this.types.size() == 1) {
			throw new IllegalStateException("Can't close outer level");
		}
		types.pop();
		offsets.pop();
		used.pop();
	}
	
	/**
	 * Return current scope depth
	 * @return current scope depth
	 */
	public int getScopeDepth() {
		return this.types.size();
	}

	/** Tests if a given identifier is declared in this scope. */
	public boolean contains(String id) {
		return this.types.peek().containsKey(id);
	}

	/** Declares an identifier with a given type, if the identifier
	 * is not yet in this scope.
	 * @return <code>true</code> if the identifier was added;
	 * <code>false</code> if it was already declared.
	 */
	public boolean put(String id, Type type) {
		boolean result = !this.types.peek().containsKey(id);
		if (result) {
			this.types.peek().put(id, type);
			this.offsets.peek().put(id, this.size);
			this.size += type.size();
		}
		return result;
	}

	/** Returns the type of a given (presumably declared) identifier.
	 */
	public Type type(String id) {
		for(int i=types.size()-1;i>=0;i--) {
			if(this.types.get(i).get(id)!=null) {
				return this.types.get(i).get(id);
			}
		}
		return null;
	}

	/** Returns the offset of a given (presumably declared) identifier. 
	  * with respect to the beginning of this scope's activation record.
	  * Offsets are assigned in order of declaration. 
	  */
	public Integer offset(String id) {
		for(int i=offsets.size()-1;i>=0;i--) {
			if(this.offsets.get(i).get(id)!=null) {
				return this.offsets.get(i).get(id);
			}
		}
		return null;
	}
	
	/**
	 * Returns the relative depth of variable in the nested levels
	 * @param id
	 * @return {@code 0} if variable resides in the top nested level,
	 * {@code -1} if variable is not declared
	 */
	public int variableDepth(String id) {
		int result = -1;
		for (int i = this.types.size() - 1; result < 0 && i >= 0; i--) {
			if (this.types.get(i).containsKey(id)) {
				result = this.types.size() - 1 - i;
			}
		}
		return result;
	}
	
	
	/**
	 * Puts variable in used nested level so that program knows that this variable has already been used
	 * @param id
	 * @param type
	 */
	public void putUsed(String id, Type type) {
		this.used.peek().put(id, type);
	}
	
	/**
	 * Returns if the variable has been used in the inner-most nested level
	 * @param id
	 * @return
	 */
	public boolean isUsed(String id) {
		return this.used.peek().containsKey(id);
	}
	
}
