package info.esblurock.reaction.chemconnect.core.client.administration;

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
import com.github.gwtd3.api.layout.Force.ForceEventType;
import com.github.gwtd3.api.layout.Force.Link;
import com.github.gwtd3.api.layout.Force.Node;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

import info.esblurock.reaction.chemconnect.core.client.graph.BaseForceGraph;
import info.esblurock.reaction.chemconnect.core.client.graph.BaseGraphLink;

/**
 * This is a GWT implementation of Mike Bostock's 'Mobile Patent Suit' D3.js infographic.
 * @see <a href="https://gist.github.com/mbostock/1153292">Mobile Patent Suits</a>.
 * 
 * This code example demonstrates a possible use of the Java wrapper API
 * to create a force-directed graph layout. 
 */
public class GraphNodesWithForces extends FlowPanel implements DemoCase {
	
	BaseForceGraph basegraph;
	
	public static final Resources R = GWT.create(Resources.class);
	
	public interface Resources extends ClientBundle {
		

		interface Style extends CssResource {
		
			String link();
			
			String licensing();

			String resolved();
			
		}
		
		@Source("MobilePatentSuits.css")
		public Style style();

	}

	
	
	private  Force<String> force;
	
	private Selection path;
	
	private Selection circle;
	
	private Selection text;
	
	ChemConnectAdministrationImpl top;
	ArrayList<BaseGraphLink> suits;
    public GraphNodesWithForces(ChemConnectAdministrationImpl top, ArrayList<BaseGraphLink> suits) {
    	this.suits = suits;
    	this.top = top;
    	R.style().ensureInjected();
    }
    
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

	@Override
	public void start() {
		//? Enumerate all unique companies.
		Set<String> companySet = new HashSet<>();
		for (BaseGraphLink suit: suits) {
			companySet.add(suit.getSource());
			companySet.add(suit.getTarget());
		}
		
		LinkedHashMap<String, Node<String>> map = new LinkedHashMap<>();
		for (BaseGraphLink suit: suits) {
			if (!map.containsKey(suit.getSource())) {
				map.put(suit.getSource(), newNode(suit.getSource()));
			}
			if (!map.containsKey(suit.getTarget())) {
				map.put(suit.getTarget(), newNode(suit.getTarget()));
			}
		}		
		Array<Force.Node<String>> nodes = Array.fromIterable(map.values());
		
		/**
		 * For convenience, a link’s source and target properties may be initialized
		 * using numeric or string identifiers rather than object references.
		 */
		Array<Force.Link<String>> links = Array.create();
		for (BaseGraphLink suit: suits) {
			Node<String> source = map.get(suit.getSource());
			Node<String> target = map.get(suit.getTarget());
			Force.Link<String> link = newLinkIndex(nodes.indexOf(source), nodes.indexOf(target), suit);
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
		.charge(-300f)
		.on(ForceEventType.TICK, new DatumFunction<Void>() {
			@Override
			public Void apply(Element context, Value d, int index) {
			    path.attr("d", new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Link<?> link = d.as(Link.class);
						double dx = link.target().x() - link.source().x();
						double dy = link.target().y() - link.source().y();
						double dr = Math.sqrt(dx * dx + dy * dy);
						
						double x1 = link.source().x();
						double y1 = link.source().y();
						double x2 = link.target().x();
						double y2 = link.target().y();
						return "M" + x1 + "," + y1 + "A" + dr + "," + dr + " 0 0,1 " + x2 + "," + y2;
					}});
				circle.attr("transform", new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Node<?> node = d.as(Node.class);
						double x = node.x();
						double y = node.y();
						return "translate(" + x + "," + y + ")";
					}
				});
				text.attr("transform",  new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Node<?> node = d.as(Node.class);
						double x = node.x();
						double y = node.y();
						return "translate(" + x + "," + y + ")";
					}
				});				
				return null;
			}
		});
		
		force.start();

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
		    .attr("refX", 15)
		    .attr("refY", -1.5)
			.attr("markerWidth", 15)
			.attr("markerHeight", 15)
			.attr("orient", "auto")
			.append("path")
			.attr("d", "M0,-5L10,0L0,5");
	
		path = svg
			.append("g")
			.selectAll("path")
			.data(force.links())
			.enter().append("path")
		    .attr("class", new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					Link<?> link = d.as(Link.class);
					BaseGraphLink suit = getDatum(link);
					String style = R.style().link();
					switch (suit.getType()) {
						case "licensing":
							style += ' ' + R.style().licensing();
							break;
						case "resolved":
							style += ' ' + R.style().resolved();
							break;
						default:
					}
					return style;
				}
		    })
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
		    .call(force.drag())
		    	.on("mouseover",new DatumFunction<Void>() {
					@Override
					public Void apply(Element context, Value d, int index) {
						Node<String> node = d.as();
						top.nodeMouseOver(node.datum().toString());
						return null;
					}
			    	
			    })
		    	.on("click",new DatumFunction<Void>() {
					@Override
					public Void apply(Element context, Value d, int index) {
						Node<String> node = d.as();
						top.nodeClicked(node.datum().toString());					
						return null;
					}
			    	
			    })
;
		/*
		circle = svg
				.append("g")
				.selectAll("rect")
			    .data(force.nodes())
			    .enter()
			    .append("rect")
			    .attr("height", 10)
			    .attr("width", 10)
			    .call(force.drag())
			    .on("mouseover",new DatumFunction<Void>() {

					@Override
					public Void apply(Element context, Value d, int index) {
						Node<String> node = d.as();
						top.nodeClicked(node.datum().toString());
						return null;
					}
			    	
			    });
		*/
		text = svg
			.append("g")
			.selectAll("text")
		    .data(force.nodes())
		    .enter().append("text")
		    .attr("x", 6)
		    .attr("y", ".31em")
		    .text(new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					Node<String> node = d.as();
					return node.datum();
				}
		    });
		
	}
	
	@Override
	public void stop() {
		if (force != null) {
			force.stop();
		}
	}
	

}