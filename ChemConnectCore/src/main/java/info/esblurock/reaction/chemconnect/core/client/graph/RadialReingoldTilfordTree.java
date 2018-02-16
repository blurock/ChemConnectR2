package info.esblurock.reaction.chemconnect.core.client.graph;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Cluster.Node;
import com.github.gwtd3.api.layout.HierarchicalLayout.Link;
import com.github.gwtd3.api.layout.SeparationFunction;
import com.github.gwtd3.api.layout.Tree;
import com.github.gwtd3.api.svg.Diagonal;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Radial Reingold–Tilford Tree
 * Example inspired from original Javascript example available at http://bl.ocks.org/mbostock/4063550
 *
 * @author Eric Citaire
 */
public class RadialReingoldTilfordTree extends FlowPanel{
    private static final String JSON_URL = "flare.json";
    private final MyResources css;

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("RadialReingoldTilfordTree.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String link();

        String node();
    }

    public RadialReingoldTilfordTree() {
        css = Bundle.INSTANCE.css();
        css.ensureInjected();
    }

    public void start() {
        double diameter = 960;

        final Tree<FlareNode> tree = D3.layout().tree();
        tree.children(new DatumFunction<List<FlareNode>>() {
            @Override
            public List<FlareNode> apply(final Element context, final Value d, final int index) {
                FlareNode node = d.as(FlareNode.class);
                return node.isLeaf() ? new ArrayList<FlareNode>() : node.children().asList();
            }
        })
                .size(360, diameter / 2 - 120)
                .separation(new SeparationFunction<Tree.Node<FlareNode>>() {
                    @Override
                    public double separation(final Tree.Node<FlareNode> a,
                            final Tree.Node<FlareNode> b) {
                        return (double) (a.parent() == b.parent() ? 1 : 2) / a.depth();
                    }
                });

        final Diagonal diagonal = D3.svg().radialDiagonal()
                .projection(new DatumFunction<Array<Double>>() {
                    @Override
                    public Array<Double> apply(final Element context, final Value value, final int index) {
                        return Array.fromDoubles(value.asCoords().y(), value.asCoords().x() / 180 * Math.PI);
                    }
                });

        final Selection svg = D3.select(this).append("svg")
                .attr("width", diameter)
                .attr("height", diameter - 150)
                .append("g")
                .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JSON_URL);

        try {
        	
            @SuppressWarnings("unused")
			Request request = builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onError(final Request request, final Throwable exception) {
                    Window.alert("Couldn't retrieve JSON");
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    if (200 == response.getStatusCode()) {
                        FlareNode root = JsonUtils.safeEval(response.getText());
                        Array<Tree.Node<FlareNode>> nodes = tree.nodes(root);
                        Array<Link<FlareNode>> links = tree.links(nodes);

                        Selection link = svg.selectAll("." + css.link())
                                .data(links)
                                .enter().append("path")
                                .attr("class", css.link())
                                .attr("d", diagonal);

                        DatumFunction<String> transform = new DatumFunction<String>() {
                            @Override
                            public String apply(final Element context, final Value d, final int index) {
                                Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                double x = node.x();
                                double y = node.y();
                                return "rotate(" + (x - 90) + ")translate(" + y + ")";
                            }
                        };

                        Selection node = svg.selectAll("." + css.node())
                                .data(nodes)
                                .enter().append("g")
                                .attr("class", css.node())
                                .attr("transform", transform);

                        node.append("circle")
                                .attr("r", 4.5);

                        node.append("text")
                                .attr("dy", ".31em")
                                .attr("text-anchor", new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                        return node.x() < 180 ? "start" : "end";
                                    }
                                })
                                .attr("transform", new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                        return node.x() < 180 ? "translate(8)" : "rotate(180)translate(-8)";
                                    }
                                })
                                .text(new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Node<FlareNode> node = d.<Node<FlareNode>> as();
                                        return node.datum().name();
                                    }
                                });

                    } else {
                        Window.alert("Couldn't retrieve JSON (" + response.getStatusText() + ")");
                    }
                }
            });

            D3.select(RadialReingoldTilfordTree.this).style("height", diameter - 150 + "px");
        } catch (RequestException e) {
            Window.alert("Couldn't retrieve JSON");
        }

    }

    public void stop() {

    }

}