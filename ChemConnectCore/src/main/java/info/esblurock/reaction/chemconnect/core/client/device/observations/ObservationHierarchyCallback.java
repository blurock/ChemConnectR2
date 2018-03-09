package info.esblurock.reaction.chemconnect.core.client.device.observations;

import static org.junit.Assert.assertNotNull;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.device.SetOfObservationsDefinition;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;

public class ObservationHierarchyCallback implements AsyncCallback<SetOfObservationsTransfer> {

	SetOfObservationsDefinition top;
	
	public ObservationHierarchyCallback(SetOfObservationsDefinition top) {
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
