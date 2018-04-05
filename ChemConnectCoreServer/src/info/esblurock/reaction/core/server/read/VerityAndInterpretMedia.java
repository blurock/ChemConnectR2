package info.esblurock.reaction.core.server.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public enum VerityAndInterpretMedia {

	SpreadSheet {
		@Override
		public ArrayList<DatabaseObjectHierarchy> extractDataObject(InputStream inputstream, DatabaseObject interpretation)  throws IOException {
			SpreadSheetInputInformation info = (SpreadSheetInputInformation) interpretation;
			ObservationsFromSpreadSheet observations = InterpretSpreadSheet.streamReadSpreadSheet(inputstream, info,true);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(info);
			ArrayList<DatabaseObjectHierarchy> lst = new ArrayList<DatabaseObjectHierarchy>();
			lst.add(hierarchy);
			return lst;
		}
		
	};
	public abstract ArrayList<DatabaseObjectHierarchy> extractDataObject(InputStream inputstream,DatabaseObject interpretation) throws IOException;
}
