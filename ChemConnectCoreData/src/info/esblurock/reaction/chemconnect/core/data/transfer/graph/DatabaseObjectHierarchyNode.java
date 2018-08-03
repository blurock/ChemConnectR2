package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class DatabaseObjectHierarchyNode extends HierarchyNode {
	
	DatabaseObject object;
	
	public DatabaseObjectHierarchyNode(DatabaseObject object) {
		super(object.getIdentifier());
		this.object = object;
	}
	public DatabaseObjectHierarchyNode(DatabaseObject object, String identifier) {
		super(identifier);
		this.object = object;
	}
	public DatabaseObject getObject() {
		return object;
	}
	public void setObject(DatabaseObject object) {
		this.object = object;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		if(object != null) {
			String newprefix = prefix + "NodeObject: ";
			build.append(object.toString(newprefix));
		} else {
			build.append(prefix + "no object listed\n");
		}
		String subprefix = prefix + "Tree      : ";
		build.append(super.toString(subprefix));
		return build.toString();
	}
	
}
