package info.esblurock.reaction.chemconnect.core.client.pages.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.ChemConnectRecordInformation;

public class GetChemConnectRecordInformationCallback implements AsyncCallback<ChemConnectRecordInformation> {

	StandardDatasetRecord record;
	
	public GetChemConnectRecordInformationCallback(StandardDatasetRecord record) {
		this.record = record;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ChemConnectRecordInformation info) {
		record.insertRecords(info);
	}

}
