package info.esblurock.reaction.chemconnect.core.data.query;

import java.io.Serializable;
import java.util.ArrayList;

public class SetOfQueryPropertyValues extends ArrayList<QueryPropertyValue> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public SetOfQueryPropertyValues() {
	}
	
	public void add(String property, String value) {
		QueryPropertyValue pv = new QueryPropertyValue(property,value);
		this.add(pv);
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + " QueryPropertyValues[");
		boolean notfirst = false;
		for(QueryPropertyValue pv: this) {
			if(notfirst) {
				build.append(", ");
			} else {
				notfirst = true;
			}
			build.append(pv.toString());
		}
		build.append("]");
		return build.toString();
	}

}
