package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultiple extends DatabaseObject {
	@Index 
	HashSet<String> ids;
	
	public ChemConnectCompoundMultiple() {
		super();
		ids = new HashSet<String>();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj) {
		super(obj);
		ids = new HashSet<String>();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj,HashSet<String> ids) {
		super(obj);
		this.ids = ids;
	}

	public void addID(String id) {
		ids.add(id);
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
		build.append(prefix + ids.toString());
		return build.toString();
	}
}
