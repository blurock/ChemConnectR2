package info.esblurock.reaction.chemconnect.core.data.transfer;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
public class PrimitiveInterpretedInformation extends PrimitiveDataStructureInformation {

	DatabaseObject obj;
	
	public PrimitiveInterpretedInformation() {
		this.obj = null;
	}
		public PrimitiveInterpretedInformation(PrimitiveDataStructureInformation info, DatabaseObject obj) {
		super(info);
		this.obj = obj;
	}

	public DatabaseObject getObj() {
		return obj;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + " PrimitiveInterpretedInformation\n");
		build.append(super.toString(prefix));
		build.append(obj.toString(prefix));
		return build.toString();
	}
	
}
