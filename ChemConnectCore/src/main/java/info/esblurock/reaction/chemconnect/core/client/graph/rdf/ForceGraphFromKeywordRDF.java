package info.esblurock.reaction.chemconnect.core.client.graph.rdf;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.client.graph.BaseGraphLink;
import info.esblurock.reaction.chemconnect.core.client.graph.ForceGraphBehaviorAndInformation;
import info.esblurock.reaction.chemconnect.core.data.rdf.KeywordRDF;
import info.esblurock.reaction.chemconnect.core.data.rdf.SetOfKeywordRDF;

public class ForceGraphFromKeywordRDF extends ForceGraphBehaviorAndInformation {

	SetOfKeywordRDF rdfs;
	ForceGraphPanel top;
	
	public ForceGraphFromKeywordRDF(SetOfKeywordRDF rdfs, ForceGraphPanel top) {
		this.rdfs = rdfs;
		this.top = top;
		ArrayList<BaseGraphLink> links = new ArrayList<BaseGraphLink>();
		for(KeywordRDF rdf : rdfs) {
			String subject = removeNameSpace(rdf.getIdentifier());
			String predicate = removeNameSpace(rdf.getPredicate());
			String object = removeNameSpace(rdf.getObject());
			BaseGraphLink link = new BaseGraphLink(subject, object, predicate);
			links.add(link);
		}
		this.init(links);
	}
	
	public String removeNameSpace(String name) {
		int pos = name.indexOf(":");
		return name.substring(pos+1);
	}
	
	@Override
	public void mouseOver(String nodeName) {
		top.mouseOver(nodeName);
	}

	@Override
	public void mouseClick(String nodeName) {
		top.mouseClick(nodeName);
	}

}
