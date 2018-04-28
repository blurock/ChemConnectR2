package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
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
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ChemConnectDataStructure structure) {
		addHierarchialModal(topobj,structure);
		top.setIdentifer(topobj);
	}
	public void addHierarchialModal(DatabaseObject topobj,ChemConnectDataStructure infoStructure) {
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
