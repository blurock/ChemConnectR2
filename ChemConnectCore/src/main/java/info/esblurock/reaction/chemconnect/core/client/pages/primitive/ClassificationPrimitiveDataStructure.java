package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import java.util.ArrayList;
import java.util.Iterator;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class ClassificationPrimitiveDataStructure extends PrimitiveDataStructureBase {
	ArrayList<String> classificationList;
	
	ClassificationPrimitiveDataStructure() {
		super();
	}
	
	ClassificationPrimitiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialDropDown dropdown = new MaterialDropDown();
		classificationList = new ArrayList<String>();
		classificationList.add(primitiveinfo.getValue());
		Iterator<String> classification = classificationList.iterator();
		while(classification.hasNext()) {
			MaterialLink link = new MaterialLink();
			link.setText(classification.next());
			link.setTextColor(Color.BLACK);
			dropdown.add(link);
		}
	}
}
