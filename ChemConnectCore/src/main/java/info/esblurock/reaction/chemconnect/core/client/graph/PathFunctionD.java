package info.esblurock.reaction.chemconnect.core.client.graph;

import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Force.Link;
import com.google.gwt.dom.client.Element;

public class PathFunctionD implements DatumFunction<String> {

	public PathFunctionD() {
	}
	@Override
	public String apply(Element context, Value d, int index) {
		Link<?> link = d.as(Link.class);
		double dx = link.target().x() - link.source().x();
		double dy = link.target().y() - link.source().y();
		double dr = Math.sqrt(dx * dx + dy * dy);						
		return "M" + link.source().x() + "," + link.source().y() + "A" + dr + "," + dr + " 0 0,1 " + link.target().x() + "," + link.target().y();
	}

}
