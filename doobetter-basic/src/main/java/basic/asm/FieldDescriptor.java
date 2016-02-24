package basic.asm;

public class FieldDescriptor {
	private String name;
	// fully. eg. java.lang.String
	private String type;
	private boolean affinityKey;
	private boolean indexField;
	private boolean queryField;

	public FieldDescriptor() {
	}

	public FieldDescriptor(String name, String type, boolean queryField, boolean indexField, boolean affinityKey) {
		this.name = name;
		this.type = type;
		this.affinityKey = affinityKey;
		this.indexField = indexField;
		this.queryField = queryField;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAffinityKey() {
		return affinityKey;
	}

	public void setAffinityKey(boolean affinityKey) {
		this.affinityKey = affinityKey;
	}

	public boolean isIndexField() {
		return indexField;
	}

	public void setIndexField(boolean indexField) {
		this.indexField = indexField;
	}

	public boolean isQueryField() {
		return queryField;
	}

	public void setQueryField(boolean queryField) {
		this.queryField = queryField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDescriptor other = (FieldDescriptor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}