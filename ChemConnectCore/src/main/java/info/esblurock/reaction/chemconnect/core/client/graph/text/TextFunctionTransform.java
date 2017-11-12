package info.esblurock.reaction.chemconnect.core.client.graph.text;

import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Force.Node;
import com.google.gwt.dom.client.Element;

public class TextFunctionTransform implements DatumFunction<String> {

	@Override
	public String apply(Element context, Value d, int index) {
		Node<?> node = d.as(Node.class);
		return "translate(" + node.x() + "," + node.y() + ")";
	}

}
