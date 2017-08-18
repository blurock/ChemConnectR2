package info.esblurock.reaction.chemconnect.core.data.rdf;

import java.io.Serializable;
import java.util.HashSet;

public class SetOfKeywordRDF extends HashSet<KeywordRDF> implements Serializable{
	private static final long serialVersionUID = 1363420428188231616L;

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("# results: " + this.size() + "\n");
		for(KeywordRDF rdf : this) {
			build.append(rdf.toString());
			build.append("\n");
		}
		return build.toString();
	}
}
