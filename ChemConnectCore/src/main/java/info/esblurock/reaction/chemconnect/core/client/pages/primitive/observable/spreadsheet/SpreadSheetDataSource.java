package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import info.esblurock.reaction.chemconnect.core.client.device.observations.SpreadSheetRowsCallback;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.client.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

public class SpreadSheetDataSource implements DataSource<SpreadSheetRow> {

	SpreadSheetServicesAsync async;
	String parent;
	int total;
	
	public SpreadSheetDataSource(String parent, int total, SpreadSheetServicesAsync async) {
		this.parent = parent;
		this.total = total;
		this.async = async;
	}
	
	@Override
	public void load(LoadConfig<SpreadSheetRow> loadConfig, LoadCallback<SpreadSheetRow> callback) {
		Window.alert("SpreadSheetDataSource:  load");
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		SpreadSheetRowsCallback asyncallback = new SpreadSheetRowsCallback(callback,loadConfig.getOffset(),total);
		async.getSpreadSheetRows(parent,loadConfig.getOffset(), loadConfig.getLimit(),
				asyncallback);
	}

	@Override
	public boolean useRemoteSort() {
		return false;
	}

}
