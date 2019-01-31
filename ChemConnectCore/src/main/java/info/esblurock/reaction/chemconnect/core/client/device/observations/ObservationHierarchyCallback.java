package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class ObservationHierarchyCallback implements AsyncCallback<SetOfObservationsTransfer> {

	ObservationHierarchyInterface top;
	
	public ObservationHierarchyCallback(ObservationHierarchyInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Observation\n" + arg0.toString());
	}

	@Override
	public void onSuccess(SetOfObservationsTransfer transfer) {
		MaterialLoader.loading(false);
		top.addSetOfObservations(transfer);
	}

}
