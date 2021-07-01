package checker;

import java.util.ArrayList;
import java.util.List;

public abstract class Type {

	/** The singleton instance of the {@link Bool} type. */
	public static final Type BOOL = new Bool();
	/** The singleton instance of the {@link Int} type. */
	public static final Type INT = new Int();
	/** The kind of the type */
	private final TypeKind kind;
	
	/** Constructor for subclasses. */
	protected Type(TypeKind kind) {
		this.kind = kind;
	}
	
	/** Returns the kind of this type. */
	public TypeKind getKind() {
		return this.kind;
	}
	
	
	/** Returns the size (in integer units - one unit 4 bytes) of a value of this type. */
	abstract public int size();
	
	/** Representation of the boolean type. */
	static public class Bool extends Type {
		private Bool() {
			super(TypeKind.BOOL);
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public String toString() {
			return "bool";
		}
	}
	
	/** Representation of the integer type. */
	static public class Int extends Type {
		private Int() {
			super(TypeKind.INT);
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public String toString() {
			return "int";
		}
	}
	
	/** Representation of Array types. */
	static public class Array extends Type {
		private final int size;
		private final Type elemType;

		/** Constructs a new array type*/
		public Array(int size, Type elemType) {
			super(TypeKind.ARRAY);
			assert size > 0;
			this.size = size;
			this.elemType = elemType;
		}

		/** Returns the size of this array type. */
		public int getSize() {
			return this.size;
		}

		/** Returns the type of elements stored in array. */
		public Type getElemType() {
			return this.elemType;
		}

		@Override
		public int size() {
			return this.size * this.elemType.size();
		}

		@Override
		public String toString() {
			return this.elemType + "[" + this.size + "]";
		}
		
		/** Returns the string representation of array without a size*/
		public String toStringWithoutSize() {
			return this.elemType + "[]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.elemType.hashCode();
			result = prime * result + this.size;
			return result;
		}

		@Override
		/** Returns if types are equal by type, class and size*/
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Array)) {
				return false;
			}
			Array other = (Array) obj;
			if (!this.elemType.equals(other.elemType)) {
				return false;
			}
			if(this.size!=other.size) {
				return false;
			}
			return true;
		}
		
		/** Returns if types are equal by type and class (without size)*/
		public boolean equalsWithoutSize(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Array)) {
				return false;
			}
			Array other = (Array) obj;
			if (!this.elemType.equals(other.elemType)) {
				return false;
			}
			return true;
		}

	}
	
	/** Representation of procedure types. */
	static public class Proc extends Type {
		/** List of parameter types. */
		private final List<Type> paramTypes;

		/** Constructs a new procedure type. */
		public Proc(List<Type> paramTypes) {
			super(TypeKind.PROC);
			this.paramTypes = new ArrayList<>(paramTypes);
		}

		/** Returns the list of parameter types of this function type. */
		public List<Type> getParamTypes() {
			return this.paramTypes;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public String toString() {
			return "Proc " + this.paramTypes;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.paramTypes.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Proc)) {
				return false;
			}
			Proc other = (Proc) obj;
			if (!this.paramTypes.equals(other.paramTypes)) {
				return false;
			}
			return true;
		}
		
		
	}
	
}
