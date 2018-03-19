package info.esblurock.reaction.chemconnect.core.data.observations;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class SingleSpreadSheetBlock extends DatabaseObject {

	@Index
	String parent;

	public SingleSpreadSheetBlock(DatabaseObject obj, String parent) {
		super(obj);
		this.parent = parent;
	}
	
	
	
}
