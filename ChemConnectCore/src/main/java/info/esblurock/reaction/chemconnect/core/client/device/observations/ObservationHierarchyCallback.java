package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class ObservationHierarchyCallback implements AsyncCallback<SetOfObservationsTransfer> {

	ObservationHierarchyInterface top;
	
	public ObservationHierarchyCallback(ObservationHierarchyInterface top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(SetOfObservationsTransfer transfer) {
		top.addSetOfObservations(transfer);
	}

}
