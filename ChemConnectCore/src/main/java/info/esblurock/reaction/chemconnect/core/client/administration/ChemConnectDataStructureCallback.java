package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class ChemConnectDataStructureCallback implements AsyncCallback<ChemConnectDataStructure> {


	MaterialCollapsible content;
	MaterialPanel modal;
	ChemConnectDataStructureInterface top;
	DatabaseObject topobj;
	
	public ChemConnectDataStructureCallback(DatabaseObject topobj, ChemConnectDataStructureInterface top) {
		this.top = top;
		this.topobj = topobj;
		modal = top.getModalPanel();
		content = top.getInfoContentCollapisble();
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("ERROR: Data structure retrieve\n" + arg0.toString());
		MaterialLoader.loading(false);
	}

	@Override
	public void onSuccess(ChemConnectDataStructure structure) {
		addHierarchialModal(topobj,structure);
		top.setIdentifer(topobj);
		MaterialLoader.loading(false);
	}
	/*
	 * ChemConnectDataStructure has the information structure, but the elements are not filled in
	 * The objects are filled in with MainDataStructureCollapsible
	 * 
	 * 1. This loops through each record:
	 * 1.1 Using the suffix, determine the subelement name
	 * 1.2 Create a new subobj information (subobj) -- inheirits all from top object
	 * 1.3 If the type is listed in the mapping, then create a MainDataStructureCollapsible
	 * 1.4 Add content
	 */
	public void addHierarchialModal(DatabaseObject topobj,ChemConnectDataStructure infoStructure) {
		Map<String, DatabaseObject> objectmap = infoStructure.getObjectMap();
		DatabaseObject topcatalogobject = objectmap.get(topobj.getIdentifier());
		if(topcatalogobject != null ) {
			topobj = topcatalogobject;
		}
		for(DataElementInformation element : infoStructure.getRecords()) {
			String subid = topobj.getIdentifier() + "-" + element.getSuffix();
			DatabaseObject subobj = new DatabaseObject(topobj);
			subobj.setIdentifier(subid);
			String type = element.getDataElementName();
			if(infoStructure.getMapping().getStructure(type) != null) {
					MainDataStructureCollapsible main = new MainDataStructureCollapsible(subobj,element,infoStructure,modal);
					content.add(main);
			} else {
				Window.alert("Compound element not found: " + type);
			}	
		}
	}

}
