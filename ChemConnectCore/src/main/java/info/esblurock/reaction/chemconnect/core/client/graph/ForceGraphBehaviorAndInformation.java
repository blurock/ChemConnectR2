package info.esblurock.reaction.chemconnect.core.client.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class ForceGraphBehaviorAndInformation {

	
	ArrayList<BaseGraphLink> links;
	Set<String> nodes;
	Set<String> linktype;
	
	public ForceGraphBehaviorAndInformation() {
		this.links = null;
		this.nodes = null;
		this.linktype = null;
		init();
	}
	public ForceGraphBehaviorAndInformation(ArrayList<BaseGraphLink> links) {
		init(links);
	}
	
	public ForceGraphBehaviorAndInformation(BaseGraphLink[] lnkarray) {
		ArrayList<BaseGraphLink> links = new ArrayList<BaseGraphLink>();
		for(BaseGraphLink lnk : lnkarray) {
			links.add(lnk);
		}
		init(links);
	}
	
	public void init(ArrayList<BaseGraphLink> links) {
		this.links = links;
		this.nodes = determineNodes(links);
		this.linktype = determineLinks(links);
		init();
	}
	
	private void init() {
	}
	
	private Set<String> determineNodes(ArrayList<BaseGraphLink> links) {
		Set<String> nodes = new HashSet<>();
		for (BaseGraphLink link: links) {
			nodes.add(link.getSource());
			nodes.add(link.getTarget());
		}
		return nodes;
	}
	
	private Set<String> determineLinks(ArrayList<BaseGraphLink> links) {
		Set<String> linktype = new HashSet<>();
		for (BaseGraphLink link: links) {
			linktype.add(link.getType());
		}
		return linktype;
	}
	
	public abstract void mouseOver(String nodeName);
	public abstract void mouseClick(String nodeName);

	public ArrayList<BaseGraphLink> getLinks() {
		return links;
	}

	public Set<String> getNodes() {
		return nodes;
	}
	
	
}
