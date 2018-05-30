package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class DatabaseObjectHierarchy implements Serializable {

	private static final long serialVersionUID = 1L;
	DatabaseObject object;
	HashMap<String, DatabaseObjectHierarchy> subobjects;
	
	public DatabaseObjectHierarchy() {
		object = null;
		init();
	}
	public DatabaseObjectHierarchy(DatabaseObject object) {
		this.object = object;
		init();
	}
	private void init() {
		subobjects = new HashMap<String,DatabaseObjectHierarchy>();
	}
	public DatabaseObject getObject() {
		return object;
	}
	
	public void setObject(DatabaseObject obj) {
		this.object = obj;
	}
	
	public DatabaseObjectHierarchy getSubObject(String name) {
		return subobjects.get(name);
	}
	
	public void transferSubObjects(DatabaseObjectHierarchy hierarchy) {
		ArrayList<DatabaseObjectHierarchy> subs = hierarchy.getSubobjects();
		for(DatabaseObjectHierarchy sub : subs) {
			this.addSubobject(sub);
		}
	}
	
	public Set<String> getSubObjectKeys() {
		return subobjects.keySet();
	}
	
	public ArrayList<DatabaseObjectHierarchy> getSubobjects() {
		ArrayList<DatabaseObjectHierarchy> array = new ArrayList<DatabaseObjectHierarchy>();
		Set<String> names = subobjects.keySet();
		for(String name : names) {
			array.add(subobjects.get(name));
		}
		return array;
	}
	public void addSubobject(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject obj = objecthierarchy.getObject();
		subobjects.put(obj.getIdentifier(),objecthierarchy);
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append("---------- DatabaseObjectHierarchy ---------- " 
		+ subobjects.size() + "\n");
		String objprefix = prefix + " Obj: ";
		if(object != null) {
			builder.append(object.toString(objprefix));
		} else {
			builder.append(objprefix + " No object defined");
		}
		builder.append("\n");
		String newprefix = prefix + "\t:  ";
		Set<String> names = subobjects.keySet();
		int count = 0;
		for(String name : names) {
			String subprefix = newprefix + count++ + ": ";
			DatabaseObjectHierarchy hierarchy = subobjects.get(name);
			builder.append(hierarchy.toString(subprefix));
		}
		return builder.toString();
	}
}
