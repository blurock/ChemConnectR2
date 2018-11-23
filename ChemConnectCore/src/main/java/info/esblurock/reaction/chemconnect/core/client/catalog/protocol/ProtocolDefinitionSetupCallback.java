package info.esblurock.reaction.chemconnect.core.client.catalog.protocol;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;

public class ProtocolDefinitionSetupCallback  implements AsyncCallback<ProtocolSetupTransfer> {

	StandardDatasetProtocolHeader protocol;
	
	public ProtocolDefinitionSetupCallback(StandardDatasetProtocolHeader protocol) {
		this.protocol = protocol;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("Error is setting up protocol:\n" + arg0.toString());
	}

	@Override
	public void onSuccess(ProtocolSetupTransfer transfer) {
		protocol.protocolSetup(transfer);
		
	}

}
