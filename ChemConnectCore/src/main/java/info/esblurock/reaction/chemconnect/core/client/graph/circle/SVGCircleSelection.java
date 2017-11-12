package info.esblurock.reaction.chemconnect.core.client.graph.circle;

import com.github.gwtd3.api.core.Selection;

public class SVGCircleSelection {
	
	
	public static Selection circle(Selection select, int x, int y, int radius, String color) {
		
		select.attr("cx", x)
			.attr("cy",y)
			.attr("r", radius)
			.attr("fill",color);
		
		return select;
	}
	public static Selection circle(Selection select, int radius, String color) {
		select.attr("r", radius)
			.attr("fill",color);
		return select;
	}
	public static Selection circle(Selection select, int radius) {
		select.attr("r", radius);
		return select;
	}

}
