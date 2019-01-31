package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class ObservationStructureCallback implements AsyncCallback<ChemConnectDataStructure> {

	SetOfObservationsDefinition top;
	
	public ObservationStructureCallback(SetOfObservationsDefinition top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("ERROR: Observation\n" + arg0.toString());
		MaterialLoader.loading(false);
		}

	@Override
	public void onSuccess(ChemConnectDataStructure structure) {
		MaterialLoader.loading(false);
		//top.addChemConnectDataStructure(structure);
	}

}
