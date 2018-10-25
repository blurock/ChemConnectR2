package info.esblurock.reaction.core.server.read.spreadsheet;

//import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.spreadsheet.block.IsolateBlockFromMatrix;
import info.esblurock.reaction.core.server.read.InterpretSpreadSheet;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class MatrixBlockTest {

	@Test
	public void test() {
		//String fileS = "";
		//InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileS);
		
		DatabaseObject obj = new DatabaseObject("id", "Public", "Administration", "1");		
		DatabaseObjectHierarchy hierarchy = InterpretData.SpreadSheetInputInformation.createEmptyObject(obj);
		SpreadSheetInputInformation info = (SpreadSheetInputInformation) hierarchy.getObject();
		info.setSourceType(SpreadSheetInputInformation.URL);
		info.setSource("https://www.googleapis.com/download/storage/v1/b/chemconnect/o/upload%2FAdministration%2FIgnition%20Delays.csv?generation=1539951597894015&alt=media");
		info.setType(SpreadSheetInputInformation.CSV);
		
		DatabaseObjectHierarchy blockhier = InterpretData.SpreadSheetBlockIsolation.createEmptyObject(obj);
		SpreadSheetBlockIsolation isolate = (SpreadSheetBlockIsolation) blockhier.getObject();
		isolate.setStartColumnType(StandardDatasetMetaData.matrixBlockColumnBeginLeft);
		isolate.setEndColumnType(StandardDatasetMetaData.matrixBlockColumnEndMaximum);
		isolate.setStartRowType(StandardDatasetMetaData.beginMatrixTopOfSpreadSheet);
		isolate.setEndRowType(StandardDatasetMetaData.matrixBlockEndAtBlankLine);
		isolate.setTitleIncluded(Boolean.TRUE.toString());
		System.out.println("Block test isolate:" + isolate.toString());
		DatabaseObjectHierarchy catidhier = InterpretData.DataCatalogID.createEmptyObject(obj);
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setDataCatalog("CSV");
		catid.setCatalogBaseName("NewMatrix");
		try {
			DatabaseObjectHierarchy spreadhier = InterpretSpreadSheet.readSpreadSheet(info, catid);
			System.out.println(spreadhier.toString());
			
			System.out.println("Full until blank line======================================================");
			catid.setSimpleCatalogName("UntilBlankLine");
			DatabaseObjectHierarchy newhier1 = IsolateBlockFromMatrix.isolateFromMatrix(catid, spreadhier, isolate);
			System.out.println(newhier1.toString("UntilBlankLine:  "));
			/*
			System.out.println("From 2 to 'phi = 1.0' (inclusive)======================================================");
			catid.setSimpleCatalogName("From2");
			isolate.setStartRowType(StandardDatasetMetaData.beginMatrixAtSpecified);
			isolate.setStartRowInfo("2");
			isolate.setEndRowType(StandardDatasetMetaData.matrixBlockEndAtIdentifierInclusive);
			isolate.setEndRowInfo("phi = 1.0");
			catid.setSimpleCatalogName("From 2:  ");
			DatabaseObjectHierarchy newhier2 = IsolateBlockFromMatrix.isolateFromMatrix(catid, spreadhier, isolate);
			System.out.println(newhier2.toString("From 2:  "));
			*/
			
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		
		
	}

}
