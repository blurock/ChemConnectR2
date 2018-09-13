package info.esblurock.reaction.core.server.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public enum VerityAndInterpretMedia {

	SpreadSheet {
		@Override
		public ArrayList<DatabaseObjectHierarchy> extractDataObject(InputStream inputstream, DatabaseObject interpretation, DataCatalogID catid)  throws IOException {
			SpreadSheetInputInformation info = (SpreadSheetInputInformation) interpretation;
			DatabaseObjectHierarchy hierarchy = InterpretSpreadSheet.streamReadSpreadSheet(inputstream, info,catid);
			ArrayList<DatabaseObjectHierarchy> lst = new ArrayList<DatabaseObjectHierarchy>();
			lst.add(hierarchy);
			return lst;
		}
		
	};
	public abstract ArrayList<DatabaseObjectHierarchy> extractDataObject(InputStream inputstream,DatabaseObject interpretation, DataCatalogID catid) throws IOException;
}
