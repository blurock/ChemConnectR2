package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;

public class ReadDelimitedFile {

	@Test
	public void test() {
		String matrix = "a,b,c\n"
				+ "c,d,e\n"
				+ "1.4, 4.5, 3.0";
		try {
			SpreadSheetInputInformation input = new SpreadSheetInputInformation(SpreadSheetInputInformation.CVS,
					SpreadSheetInputInformation.STRINGSOURCE,matrix);
			System.out.println(input.toString());
			ObservationsFromSpreadSheet result = InterpretSpreadSheet.readSpreadSheet(input);
			System.out.println(result);
			
			
			String url = "http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			input = new SpreadSheetInputInformation(SpreadSheetInputInformation.XLS,
					SpreadSheetInputInformation.URL,url);
			System.out.println(input.toString());
			result = InterpretSpreadSheet.readSpreadSheet(input);
			System.out.println(result);
			InterpretSpreadSheet.findBlocks(result);
			System.out.println(result);
			
			ArrayList<SpreadSheetBlockInformation> blocks = result.getBlocks();
			for(SpreadSheetBlockInformation block : blocks) {
				System.out.println("Block");
			for(ArrayList<String> row : block.getRows()) {
				System.out.println(row);
			}
			}
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
