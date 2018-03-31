package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.gcs.UploadedElementCollapsible;

public interface UploadedFilesInterface {

	void addCollapsible(UploadedElementCollapsible coll); 
	MaterialPanel getModalPanel();
}
