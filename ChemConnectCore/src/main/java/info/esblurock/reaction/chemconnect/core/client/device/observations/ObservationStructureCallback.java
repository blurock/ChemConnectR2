package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.administration.ChemConnectDataStructureInterface;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;

public class ObservationStructureCallback implements AsyncCallback<ChemConnectDataStructure> {

	ChemConnectDataStructureInterface top;
	
	public ObservationStructureCallback(ChemConnectDataStructureInterface top) {
		this.top = top;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		System.out.println(arg0.toString());
	}

	@Override
	public void onSuccess(ChemConnectDataStructure structure) {
		top.addChemConnectDataStructure(structure);
		
	}

}
