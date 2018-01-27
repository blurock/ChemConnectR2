package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SubSystemParameters implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Type of parameter
	 */
	String type;
	/*
	 * These are the parameters that are not inherited from subsystems or components or another inherited device
	 */
	Set<String> direct;
	/*
	 * These are inherited parameters, but are listed as important in the device, subsystem or component
	 */
	Set<String> listedInherited;
	/*
	 * These are the rest of the inherited parameters (not listed above)
	 */
	Set<String> restInherited;
	
	public SubSystemParameters() {
		this.type = "Attributes";
		this.direct = new HashSet<String>();
		this.listedInherited = new HashSet<String>();
		this.restInherited = new HashSet<String>();
	}

	public SubSystemParameters(String type, Set<String> direct, Set<String> listedInherited, Set<String> restInherited) {
		this.type = type;
		this.direct = direct;
		this.listedInherited = listedInherited;
		this.restInherited = restInherited;
	}

	public void addDirect(String name) {
		direct.add(name);
	}
	public void addListedInherited(String name) {
		listedInherited.add(name);
	}
	public void addRestInherited(String name) {
		restInherited.add(name);
	}
	
	public Set<String> getDirect() {
		return direct;
	}

	public Set<String> getListedInhierated() {
		return listedInherited;
	}

	public Set<String> getRestInhierated() {
		return restInherited;
	}
	
	public String getType() {
		return type;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + ": Direct            " + type + "  " + direct + "\n");
		build.append(prefix + ": Listed Inherited  " + type + "  " + listedInherited + "\n");
		build.append(prefix + ": Rest Inherited    " + type + "  " + restInherited + "\n");
		return build.toString();
	}
	

}
