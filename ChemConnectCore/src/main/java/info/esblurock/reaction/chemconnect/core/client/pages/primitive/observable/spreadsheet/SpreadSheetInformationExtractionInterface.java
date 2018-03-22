package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInterpretation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;

public interface SpreadSheetInformationExtractionInterface {

	public void setCorrespondences(ArrayList<SpreadSheetTitleRowCorrespondence> correspondences);

	public void setIsolatedMatrix(ArrayList<SpreadSheetRow> matrix);

	public void setMatrixInterpretation(SpreadSheetInterpretation interpretation);

}
