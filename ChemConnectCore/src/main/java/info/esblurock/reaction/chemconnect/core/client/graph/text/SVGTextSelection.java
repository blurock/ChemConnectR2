package info.esblurock.reaction.chemconnect.core.client.graph.text;

import com.github.gwtd3.api.core.Selection;

public class SVGTextSelection {
	
	public static Selection setPosition(Selection selection, int x, int y) {
		selection.attr("x",x)
		.attr("y",y);
		return selection;
	}
	public static Selection setPosition(Selection selection, String x, String y) {
		selection.attr("x",x)
		.attr("y",y);
		return selection;
	}

}
