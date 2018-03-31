package info.esblurock.reaction.chemconnect.core.data.observations;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class VisualizeObservationBase implements Serializable {
	private static final long serialVersionUID = 1L;

	DatabaseObject obj;
	
	VisualizeObservationBase() {
		obj = new DatabaseObject();
	}

	VisualizeObservationBase(DatabaseObject obj) {
		this.obj = obj;
	}


	public DatabaseObject getBase() {
		return obj;
	}

}
