package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;

public class ClassificationPrimitiveDataStructure extends PrimitiveDataStructureBase {
	ArrayList<String> classificationList;
	
	ClassificationPrimitiveDataStructure() {
		super();
		MaterialPanel panel = this.getPanel();
		MaterialTextBox box = new MaterialTextBox();
		panel.add(box);
	}
		ClassificationPrimitiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialPanel panel = this.getPanel();
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
		panel.add(dropdown);
	}
}
