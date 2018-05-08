package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultiple extends DatabaseObject {
	@Index 
	ArrayList<String> ids;
	
	public ChemConnectCompoundMultiple() {
		super();
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj) {
		super(obj);
	}
	public ChemConnectCompoundMultiple(DatabaseObject obj,ArrayList<String> ids) {
		super(obj);
		this.ids = ids;
	}

	public void addID(String id) {
		ids.add(id);
	}
	public ArrayList<String> getIds() {
		return ids;
	}
	
}
