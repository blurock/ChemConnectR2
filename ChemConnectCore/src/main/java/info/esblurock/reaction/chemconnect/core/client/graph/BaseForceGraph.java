package info.esblurock.reaction.chemconnect.core.client.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Force;
import com.github.gwtd3.api.layout.Force.Link;
import com.github.gwtd3.api.layout.Force.Node;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

import info.esblurock.reaction.chemconnect.core.client.administration.GraphNodesWithForces.Resources;

public class BaseForceGraph extends FlowPanel implements Serializable {
	
	
	
	String arrowShape = "M 0,-5 L 10 ,0 L 0,5";

	private static final long serialVersionUID = 1L;
	
	ArrayList<BaseGraphLink> datalinks;
	Set<String> nodeset;
	
	LinkedHashMap<String, Node<String>> map;
	Array<Force.Node<String>> nodes;
	Array<Force.Link<String>> links;
	
	double width = 960;
	double height = 500;

	private  Force<String> force;
	
	
	public BaseForceGraph() {
		datalinks = null;
		nodes = null;
	}
	
	public BaseForceGraph(ArrayList<BaseGraphLink> datalinks) {
		initLinksAndNodes(datalinks);
		setWindow();
	}

	public void start() {
		force = D3.layout().force().cast();
		
	}
	
	public Set<String> extractNodeSet(ArrayList<BaseGraphLink> datalinks) {
		nodeset = new HashSet<String>();
		for(BaseGraphLink link : datalinks) {
			nodeset.add(link.getSource());
			nodeset.add(link.getTarget());
		}
		return nodeset;
	}
	
	public LinkedHashMap<String, Node<String>> extractLinkedHashMap(ArrayList<BaseGraphLink> datalinks) {
		map = new LinkedHashMap<String, Node<String>>();
		for (BaseGraphLink link: datalinks) {
			if (!map.containsKey(link.getSource())) {
				map.put(link.getSource(), newNode(link.getSource()));
			}
			if (!map.containsKey(link.getTarget())) {
				map.put(link.getTarget(), newNode(link.getTarget()));
			}
		}		
		return map;
	}
	
	public Array<Force.Link<String>> extractForceLinks(ArrayList<BaseGraphLink> datalinks,Array<Force.Node<String>> nodes) {
		links = Array.create();
		for (BaseGraphLink link: datalinks) {
			Node<String> source = map.get(link.getSource());
			Node<String> target = map.get(link.getTarget());
			Force.Link<String> forcelink = newLinkIndex(nodes.indexOf(source), nodes.indexOf(target), link);
			links.push(forcelink);
		}
		return links;
	}
	private void initLinksAndNodes(ArrayList<BaseGraphLink> datalinks) {
		this.datalinks = datalinks;
		nodeset = extractNodeSet(datalinks);
		map = new LinkedHashMap<>();
		nodes = Array.fromIterable(map.values());

		/**
		 * For convenience, a link’s source and target properties may be initialized
		 * using numeric or string identifiers rather than object references.
		 */
		links = extractForceLinks(datalinks,nodes);
	}
	
	public void setWindow() {
		width = 960;
		height = 500;
	}
	public Selection svg() {
		Selection svg = D3
				.select(this)
				.append("svg")
				.attr("width", width)
				.attr("height", height);
		return svg;
	}
	
	public void startGraph(Resources R, Force<String> force, Selection svg, Selection path, Selection circle, Selection text) {
		svg
		.append("defs")
		.selectAll("marker")
		.data(new Object[] { "suit", "licensing", "resolved" })
		.enter()
		.append("marker")
		.attr("id", new DatumFunction<String>() {
			@Override
			public String apply(Element context, Value d, int index) {
				return d.asString();
			}
		 })
		.attr("viewBox", "0 -5 10 10")
		.attr("refX", 15)
		.attr("refY", -1.5)
		.attr("markerWidth", 6)
		.attr("markerHeight", 6)
		.attr("orient", "auto")
		.append("path")
		.attr("d", "M0,-5L10,0L0,5");
	
	path = svg
		.append("g")
		.selectAll("path")
		.data(force.links())
		.enter().append("path")
	    .attr("class", new ClassStyleFunction(R))
		.attr("marker-end", new DatumFunction<String>() {
			@Override
			public String apply(Element context, Value d, int index) {
				Link<?> link = d.as(Link.class);
				BaseGraphLink suit = getDatum(link);
				return "url(#" + suit.getType() + ")";
			}
		});

	circle = svg
		.append("g")
		.selectAll("circle")
	    .data(force.nodes())
	    .enter()
	    .append("circle")
	    .attr("r", 6)
	    .call(force.drag());
	
	text = svg
		.append("g")
		.selectAll("text")
	    .data(force.nodes())
	    .enter().append("text")
	    .attr("x", 8)
	    .attr("y", ".31em")
	    .text(new DatumFunction<String>() {
			@Override
			public String apply(Element context, Value d, int index) {
				Node<String> node = d.as();
				return node.datum();
			}
	    });
		
	}
	
	
	
	public void setWindow(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	   /**
     * Convenience function to overcome API limitation. 
     */    
    static final native <T> Link<T> newLinkIndex(int source, int target, BaseGraphLink userDatum) /*-{
		return {
			source : source,
			target : target,
			datum: userDatum
		};
    }-*/;

    /**
     * Convenience function to overcome API limitation. 
     */
    static final native BaseGraphLink getDatum(JavaScriptObject object) /*-{
    	return object.datum;
    }-*/;
	
    /**
     * Convenience function to overcome API limitation. 
     */
    static final native <T> Node<T> newNode(T userDatum)/*-{
		return {
			datum : userDatum
		};
    }-*/;
 
}
