package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultiple extends DatabaseObject {
	@Index 
	String type;
	@Index 
	HashSet<String> ids;
	
	public ChemConnectCompoundMultiple() {
		super();
		ids = new HashSet<String>();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj, String type) {
		super(obj);
		this.type = type;
		ids = new HashSet<String>();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj,String type, HashSet<String> ids) {
		super(obj);
		this.ids = ids;
		this.type = type;
	}

	public void addID(String id) {
		ids.add(id);
	}
	
	
	public String getType() {
		return type;
	}
	public HashSet<String> getIds() {
		return ids;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Type: " + type + "\n");
		build.append(prefix + ids.toString());
		return build.toString();
	}
}
