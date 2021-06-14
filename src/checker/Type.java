package checker;

public abstract class Type {

	/** The singleton instance of the {@link Bool} type. */
	public static final Type BOOL = new Bool();
	/** The singleton instance of the {@link Int} type. */
	public static final Type INT = new Int();
	private final TypeKind kind;
	
	/** Constructor for subclasses. */
	protected Type(TypeKind kind) {
		this.kind = kind;
	}
	
	/** Returns the kind of this type. */
	public TypeKind getKind() {
		return this.kind;
	}
	
	/** returns the size (in bytes) of a value of this type. */
	abstract public int size();
	
	/** Representation of the boolean type. */
	static public class Bool extends Type {
		private Bool() {
			super(TypeKind.BOOL);
		}

		@Override
		public int size() {
			return 4;
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
			return 4;
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

		private Array(int size, Type elemType) {
			super(TypeKind.ARRAY);
			assert size > 0;
			this.size = size;
			this.elemType = elemType;
		}

		/** Returns the size of this array type. */
		public int getSize() {
			return this.size;
		}

		/** Returns the element bound of this array type. */
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.elemType.hashCode();
			result = prime * result + this.size;
			return result;
		}

		@Override
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
			if (this.size != other.size) {
				return false;
			}
			return true;
		}

	}
	
}
