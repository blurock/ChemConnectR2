package info.esblurock.reaction.core.server.db.spreadsheet;

import java.util.Comparator;

import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class CompareObservationValueRowHierarchy implements Comparator<DatabaseObjectHierarchy> {
	
	public CompareObservationValueRowHierarchy() {
	}

	@Override
	public int compare(DatabaseObjectHierarchy o1, DatabaseObjectHierarchy o2) {
		ObservationValueRow row1 = (ObservationValueRow) o1.getObject();
		ObservationValueRow row2 = (ObservationValueRow) o2.getObject();
		return row1.compareTo(row2);
	}

}
