package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class DatabaseObjectHierarchy implements Serializable{

	private static final long serialVersionUID = 1L;
	DatabaseObject object;
	ArrayList<DatabaseObjectHierarchy> subobjects;
	
	public DatabaseObjectHierarchy() {
		
	}
		public DatabaseObjectHierarchy(DatabaseObject object) {
		this.object = object;
		init();
	}
	private void init() {
		subobjects = new ArrayList<DatabaseObjectHierarchy>();
	}
	
	public void addSubobject(DatabaseObjectHierarchy objecthierarchy) {
		subobjects.add(objecthierarchy);
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(prefix);
		builder.append("DatabaseObjectHierarchy: ");
		builder.append(object.getIdentifier());
		builder.append("\n");
		String newprefix = prefix + "\t";
		for(DatabaseObjectHierarchy hierarchy : subobjects) {
			builder.append(hierarchy.toString(newprefix));
		}
		
		
		return builder.toString();
	}
}
