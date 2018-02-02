package info.esblurock.reaction.chemconnect.core.data.transfer.graph;

import java.io.Serializable;

public class SubSystemConceptLink implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String targetConcept;
	String linkConcept;
	public SubSystemConceptLink() {
		
	}
	public SubSystemConceptLink(String linkConcept, String targetConcept) {
		this.targetConcept = targetConcept;
		this.linkConcept = linkConcept;
	}
	public String getTargetConcept() {
		return targetConcept;
	}
	public String getLinkConcept() {
		return linkConcept;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
			StringBuilder build = new StringBuilder();
			build.append(prefix);
			build.append("(" + linkConcept);
			build.append(" --> " + targetConcept);
			build.append(")");
			return build.toString();
	}
}
