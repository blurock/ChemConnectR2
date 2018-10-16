package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public interface SpreadSheetInformationExtractionInterface {

	public void setCorrespondences(ArrayList<SpreadSheetTitleRowCorrespondence> correspondences);

	public void setIsolatedMatrix(ArrayList<ObservationValueRow> matrix);

	public void setMatrixInterpretation(SpreadSheetBlockIsolation interpretation);

}
