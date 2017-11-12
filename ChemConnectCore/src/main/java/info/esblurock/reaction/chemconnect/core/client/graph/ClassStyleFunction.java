package info.esblurock.reaction.chemconnect.core.client.graph;

import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Force.Link;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

import info.esblurock.reaction.chemconnect.core.client.administration.GraphNodesWithForces.Resources;

public class ClassStyleFunction implements DatumFunction<String>{

	Resources R;
	
	public ClassStyleFunction() {
		
	}

	public ClassStyleFunction(Resources R) {
		this.R = R;
	}
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
    /**
     * Convenience function to overcome API limitation. 
     */
    static final native BaseGraphLink getDatum(JavaScriptObject object) /*-{
    	return object.datum;
    }-*/;
 
}
