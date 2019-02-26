package info.esblurock.reaction.core.server.read;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class ReadXSLXFile {

	@Test
	public void test() {
		try {
			//String url = "https://storage.googleapis.com/chemconnect/upload/Blurock/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			//String url = "http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			String resourceS = "/info/esblurock/reaction/core/server/read/IgnitionDelays.xlsx";
			InputStream is = ClassLoader.class.getResourceAsStream(resourceS);
			System.out.println("Test: " +  is);
			ArrayList<ObservationValueRow> set = new ArrayList<ObservationValueRow>();
			DatabaseObject obj = new DatabaseObject();
			int numberOfColumns = InterpretSpreadSheet.readXLSXFile(is, obj, set);
			System.out.println("Number of Columns: " + numberOfColumns);
			for(ObservationValueRow row : set) {
				System.out.println(row.toString("Result: "));
			}
			System.out.println("Number of Columns: " + numberOfColumns);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

}
