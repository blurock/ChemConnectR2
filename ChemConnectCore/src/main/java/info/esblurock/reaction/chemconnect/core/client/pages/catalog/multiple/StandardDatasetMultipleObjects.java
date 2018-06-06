package info.esblurock.reaction.chemconnect.core.client.pages.catalog.multiple;

import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetMultipleObjects extends StandardDatasetObjectHierarchyItem {

	public StandardDatasetMultipleObjects() {
		super();
	}

	public StandardDatasetMultipleObjects(DatabaseObjectHierarchy object, MaterialPanel modalpanel) {
		super(object, modalpanel);
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) object.getObject();
		ChemConnectCompoundMultipleHeader header = new ChemConnectCompoundMultipleHeader(multiple.getType());
		this.addHeader(header);
	}
	

}
