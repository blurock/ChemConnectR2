package info.esblurock.reaction.chemconnect.core.client.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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


import info.esblurock.reaction.chemconnect.core.client.administration.DemoCase;

public class GraphWithEdgeAndNodeLabels  extends FlowPanel implements DemoCase {

	private  Force<String> force;

    /**
     * Convenience function to overcome API limitation. 
     */
    static final native <T> Node<T> newNode(T userDatum)/*-{
		return {
			datum : userDatum
		};
    }-*/;
    
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

    ArrayList<BaseGraphLink> datalinks;
    
    
    
	
	public GraphWithEdgeAndNodeLabels(ArrayList<BaseGraphLink> datalinks) {
		super();
		this.datalinks = datalinks;
	}

	
	@Override
	public void start() {
		LinkedHashMap<String, Node<String>> map = new LinkedHashMap<>();
		for (BaseGraphLink lnk: datalinks) {
			if (!map.containsKey(lnk.getSource())) {
				map.put(lnk.getSource(), newNode(lnk.getSource()));
			}
			if (!map.containsKey(lnk.getTarget())) {
				map.put(lnk.getTarget(), newNode(lnk.getTarget()));
			}
		}		
		Array<Force.Node<String>> nodes = Array.fromIterable(map.values());
		
		/**
		 * For convenience, a link’s source and target properties may be initialized
		 * using numeric or string identifiers rather than object references.
		 */
		Array<Force.Link<String>> links = Array.create();
		for (BaseGraphLink lnk: datalinks) {
			Node<String> source = map.get(lnk.getSource());
			Node<String> target = map.get(lnk.getTarget());
			Force.Link<String> link = newLinkIndex(nodes.indexOf(source), nodes.indexOf(target), lnk);
			links.push(link);
		}

		double width = 900;
		double height = 500;
		
		force = D3.layout().force().cast();
		force
		.nodes(nodes)
		.links(links)
		.size(width, height)
		.linkDistance(50f)
		.charge(-300f);
		

		
		Selection svg = D3
				.select(this)
				.append("svg")
				.attr("width", width)
				.attr("height", height);
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
	    .attr("refX", 13)
	    .attr("refY", 0)
		.attr("markerWidth", 13)
		.attr("markerHeight", 13)
		.attr("xoverflow","visible")
		.attr("orient", "auto")
		.append("path")
		.attr("d", "M0,-5L10,0L0,5")
		.attr("fill", "#999")
        .style("stroke","none");
		
	    Selection link = svg.selectAll(".link")
	            .data(links)
	            .enter()
	            .append("line")
	            .attr("class", "link")
	            .attr("marker-end","url(#arrowhead)");
	    
	         link.append("title")
	            .text(new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						return d.typeof();
						Link<?> link = d.as(Link.class);
						BaseGraphLink suit = getDatum(link);
						return "url(#" + suit.getType() + ")";
					}
				}
	            		
	            		function (d) {return d.type;});

		
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
