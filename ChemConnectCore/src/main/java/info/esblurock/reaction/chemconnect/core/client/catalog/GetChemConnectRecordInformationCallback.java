package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.ChemConnectRecordInformation;

public class GetChemConnectRecordInformationCallback implements AsyncCallback<ChemConnectRecordInformation> {

	StandardDatasetRecord record;
	
	public GetChemConnectRecordInformationCallback(StandardDatasetRecord record) {
		this.record = record;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Record information\n" + arg0.toString());
	}

	@Override
	public void onSuccess(ChemConnectRecordInformation info) {
		MaterialLoader.loading(false);
		record.insertRecords(info);
	}

}
