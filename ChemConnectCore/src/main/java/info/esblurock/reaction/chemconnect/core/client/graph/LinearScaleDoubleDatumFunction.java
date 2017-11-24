package info.esblurock.reaction.chemconnect.core.client.graph;

import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.google.gwt.dom.client.Element;

public abstract class LinearScaleDoubleDatumFunction implements DatumFunction<Double> {

	LinearScale linear;
	
	LinearScaleDoubleDatumFunction(LinearScale linear) {
		this.linear = linear;
	}
	
	
	@Override
	public Double apply(Element context, Value d, int index) {
		double dta = getDataValue();
		Value v = linear.apply(dta);
		Double dbl = v.asDouble();
    return dbl;
	}
	
	public abstract double getDataValue();
	

}
