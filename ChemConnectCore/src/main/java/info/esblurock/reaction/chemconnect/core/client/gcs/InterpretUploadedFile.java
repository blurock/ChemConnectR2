package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialCollapsible;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
public enum InterpretUploadedFile {

	DataFileMatrixStructure {

		@Override
		public void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
				GCSBlobFileInformation info, UploadedElementCollapsible uploaded) {
			VisualizeMedia visual = VisualizeMedia.valueOf(ChemConnectCompoundDataStructure.removeNamespace(visualType));

			String sourceType = SpreadSheetInputInformation.BLOBSOURCE;
			String source = info.getGSFilename();
			obj.setIdentifier(catid.getFullName());
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(catid,catid.getIdentifier());
			SpreadSheetInputInformation spread = new SpreadSheetInputInformation(structure," ",sourceType,source);
			if(visual != null) {
				visual.getInterpretedBlob(info, spread, catid,true,uploaded);
			}
		}
	}, DataFileImageStructure {
		@Override
		public void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
				GCSBlobFileInformation info, UploadedElementCollapsible uploaded) {
			String source = info.getGSFilename();
			Window.alert("InterpretUploadedFile: DataFileImageStructure source: " + source);
			MaterialCollapsible collapsible = new MaterialCollapsible();
			uploaded.getObjectPanel().add(collapsible);
			SetUpDatabaseObjectHierarchyCallback createcallback = 
					new SetUpDatabaseObjectHierarchyCallback(collapsible, uploaded.getModalPanel());
			UserImageServiceAsync createasync = UserImageService.Util.getInstance();
			createasync.createDatasetImage(obj,catid,visualType, info,createcallback);			
		}
		
	};
	public abstract void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
			GCSBlobFileInformation info, UploadedElementCollapsible uploaded);
}
