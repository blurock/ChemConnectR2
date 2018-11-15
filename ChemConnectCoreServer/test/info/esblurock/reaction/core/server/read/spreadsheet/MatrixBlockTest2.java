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

public class MatrixBlockTest2 {

	@Test
	public void test() {
		
		DatabaseObject obj = new DatabaseObject("id", "Public", "Administration", "1");		
		DatabaseObjectHierarchy hierarchy = InterpretData.SpreadSheetInputInformation.createEmptyObject(obj);
		SpreadSheetInputInformation info = (SpreadSheetInputInformation) hierarchy.getObject();
		info.setSourceType(SpreadSheetInputInformation.STRINGSOURCE);
		String source = "Initial Temperature (K),Initial Pressure (bar),Compression Time (ms),Compressed Temperature (K),1000/Tc (1/K),Compressed Pressure (bar),Ignition Delay (msec),Ignition Delay Unc (msec)\n" + 
				"413,0.8043,28.7,816,1.2262,14.95,112.55,5.35\n" + 
				"413,0.7689,30.4,824,1.2129,15.05,73.52,3.51\n" + 
				"413,0.7240,31.25,835,1.1974,14.97,49.79,3.07\n" + 
				"413,0.6810,32.7,846,1.1816,14.98,34.60,1.21\n" + 
				"413,0.6427,33.9,855,1.1692,14.95,24.49,1.53\n" + 
				",,,,,,,\n" + 
				"phi = 1.0,O2:N2 = 1:3.76 (Air),Mole Fractions:,Fuel,0.0338,,,\n" + 
				",,,O2,0.2030,,,\n" + 
				",,,N2,0.7632,,,\n" + 
				",,,,,,,";
		info.setSource(source);
		info.setType(SpreadSheetInputInformation.CSV);
		
		DatabaseObjectHierarchy blockhier = InterpretData.SpreadSheetBlockIsolation.createEmptyObject(obj);
		SpreadSheetBlockIsolation isolate = (SpreadSheetBlockIsolation) blockhier.getObject();
		isolate.setStartColumnType(StandardDatasetMetaData.matrixBlockColumnBeginAtPosition);
		isolate.setStartColumnInfo("3");
		isolate.setEndColumnType(StandardDatasetMetaData.matrixBlockColumnEndNumberOfColumns);
		isolate.setEndColumnInfo("2");
		isolate.setStartRowType(StandardDatasetMetaData.beginMatrixAtStartsWithSpecifiedIdentifier);
		isolate.setStartRowInfo("phi =");
		isolate.setEndRowType(StandardDatasetMetaData.matrixBlockEndAtBlankLine);
		isolate.setTitleIncluded(Boolean.FALSE.toString());
		System.out.println("Block test isolate:\n" + isolate.toString());
		DatabaseObjectHierarchy catidhier = InterpretData.DataCatalogID.createEmptyObject(obj);
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setDataCatalog("CSV");
		catid.setCatalogBaseName("NewMatrix");
		System.out.println("DataCatalogID:\n" + catid.toString());
		System.out.println("SpreadSheetInputInformation:\n" + info.toString());
		System.out.println("-------------------------------------------------------");
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
