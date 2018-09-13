package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ReadDelimitedFile {

	@Test
	public void test() {
		String matrix = "Column1,Column2,Column3\n"
				+ "a,b,c\n"
				+ "c,d,e\n"
				+ "1.4, 4.5, 3.0";
		try {
			String id = "Catalog-Administration-SpreadSheet-Example";
			String access = "Public";
			String owner = "Administration";
			String sourceID = "0";
			DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
			SpreadSheetInputInformation input = new SpreadSheetInputInformation(structure,SpreadSheetInputInformation.CSV,
					SpreadSheetInputInformation.STRINGSOURCE,matrix,true);
			System.out.println(input.toString());
			String catalogBaseName = "Catalog-Administration";
			String dataCatalog = "dataset:SpreadSheet";
			String simpleCatalogName = "Example";
			ArrayList<String> path = new ArrayList<String>();
			path.add("Catalog");
			path.add("Administration");
			DataCatalogID catid = new DataCatalogID(structure, catalogBaseName, dataCatalog, simpleCatalogName, path);
			System.out.println("DataCatalogID:  " + catid.toString());
			System.out.println("----------------------------------------------------------");
			DatabaseObjectHierarchy hierarchy = InterpretSpreadSheet.readSpreadSheet(input,catid);
			System.out.println("----------------------------------------------------------");
			System.out.println(hierarchy);
			System.out.println("----------------------------------------------------------");
			
			/*
			String url = "http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			input = new SpreadSheetInputInformation(obj,SpreadSheetInputInformation.XLS,
					SpreadSheetInputInformation.URL,url);
			System.out.println(input.toString());
			result = InterpretSpreadSheet.readSpreadSheet(input);
			System.out.println(result);
			InterpretSpreadSheet.findBlocks(result);
			System.out.println(result);
			
			ArrayList<SpreadSheetBlockInformation> blocks = result.getBlocks();
			for(SpreadSheetBlockInformation block : blocks) {
				System.out.println("Block");
			for(SpreadSheetRow row : block.getRows()) {
				System.out.println(row);
			}
			}
			*/
			/*
			url = "http://cms.heatfluxburner.org/wp-content/uploads/Goswami_CH4_Air_1atm_Tu_298K.xls";
			input = new SpreadSheetInputInformation(SpreadSheetInputInformation.XLS,
					SpreadSheetInputInformation.URL,url);
			System.out.println(input.toString());
			result = InterpretSpreadSheet.readSpreadSheet(input);
			System.out.println(result);
			*/
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
