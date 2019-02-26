package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class ReadFromFile {

	@Test
	public void test() {
		try {
			//String url = "https://storage.googleapis.com/chemconnect/upload/Blurock/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			String url = "http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			ArrayList<ObservationValueRow> set = new ArrayList<ObservationValueRow>();
			DatabaseObject obj = new DatabaseObject();
			URL oracle;
			oracle = new URL(url);
			InputStream is = oracle.openStream();
			InterpretSpreadSheet.readXLSFile(is, obj, set);
			for(ObservationValueRow row : set) {
				System.out.println(row.toString("Result: "));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

}
