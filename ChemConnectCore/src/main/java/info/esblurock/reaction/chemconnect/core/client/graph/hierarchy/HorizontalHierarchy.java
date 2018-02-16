package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Cluster;
import com.github.gwtd3.api.layout.Cluster.Node;
import com.github.gwtd3.api.layout.HierarchicalLayout.Link;
import com.github.gwtd3.api.svg.Diagonal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

import info.esblurock.reaction.chemconnect.core.client.graph.FlareNode;

public class HorizontalHierarchy extends FlowPanel {

	private final MyResources css;
	private Cluster<FlareNode> cluster;
	private Diagonal diagonal;
	private Selection svg;

	JSONObject jsonroot;
	int width = 500;
	int height = 500;

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);
		@Source("ClusterDendogram.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String link();

		String node();
	}

	public HorizontalHierarchy(HorizontalHierarchyInformation info) {
		this.jsonroot = info.getRoot();
		width = info.getWidth();
		height = info.getHeight();
		css = Bundle.INSTANCE.css();
		css.ensureInjected();
	}

	public void start() {

		cluster = D3.layout().cluster();
		cluster.size(height, width - 160).children(new DatumFunction<List<FlareNode>>() {
			@Override
			public List<FlareNode> apply(final Element context, final Value d, final int index) {
				FlareNode node = d.as(FlareNode.class);
				return node.isLeaf() ? new ArrayList<FlareNode>() : node.children().asList();
			}
		});

		diagonal = D3.svg().diagonal().projection(new DatumFunction<Array<Double>>() {
			@Override
			public Array<Double> apply(final Element context, final Value value, final int index) {
				return Array.fromDoubles(value.asCoords().y(), value.asCoords().x());
			}
		});

		svg = D3.select(this).append("svg").attr("width", width).attr("height", height).append("g").attr("transform",
				"translate(40,0)");
		FlareNode root = JsonUtils.safeEval(jsonroot.toString());
		Array<Node<FlareNode>> nodes = cluster.nodes(root);
		Array<Link<FlareNode>> links = cluster.links(nodes);

		@SuppressWarnings("unused")
		Selection link = svg.selectAll("." + css.link()).data(links).enter().append("path").attr("class", css.link())
				.attr("d", diagonal);

		Selection node = svg.selectAll("." + css.node()).data(nodes).enter().append("g").attr("class", css.node())
				.attr("transform", new DatumFunction<String>() {
					@Override
					public String apply(final Element context, final Value value, final int index) {
						return "translate(" + value.asCoords().y() + "," + value.asCoords().x() + ")";
					}
				});

		node.append("circle").attr("r", 4.5);

		node.append("text").attr("dx", new DatumFunction<Integer>() {
			@Override
			public Integer apply(final Element context, final Value d, final int index) {
				return d.<Node<FlareNode>>as().children() != null ? -8 : 8;
			}
		}).attr("dy", 3).style("text-anchor", new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value d, final int index) {
				return d.<Node<FlareNode>>as().children() != null ? "end" : "start";
			}
		}).text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value d, final int index) {
				return d.<Node<FlareNode>>as().datum().name();
			}
		});

		D3.select(HorizontalHierarchy.this).select("svg").style("height", height + "px");

	}

}
