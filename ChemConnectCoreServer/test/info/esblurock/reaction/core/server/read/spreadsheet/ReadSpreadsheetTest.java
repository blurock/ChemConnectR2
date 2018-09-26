package info.esblurock.reaction.core.server.read.spreadsheet;

//import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.core.server.read.InterpretSpreadSheet;

public class ReadSpreadsheetTest {

	@Test
	public void test() {
		String fileS = "X_ValueDistance	TC [K]     	 Polyfit coeff	Gas v [cm/s]	Plenum T [K]	  Pressure [hPa]	Phi	Area [cm^2]	Calculated setpoints [L/min]	 Setpoints in MFC [L/min]	MFC_max flow [L/min]	Ch disabled    	Ch number     	Ch disabled     1	Ch number      1	Ch disabled     2	Ch number      2	Comment\n" + 
				"	0.000000	131.449505	130.552889	27.000000	100.000000	1019.000000	0.700000	12.803000	0.000000	0.000000	6.116208	1.000000	6.000000	0.000000	0.000000	0.000000	0.000000\n" + 
				"	2.100000	131.128392	0.000000						14.223289	13.826250	30.670652						\n" + 
				"	4.200000	129.434691	-0.003209						0.000000	0.000000	15.000000						\n" + 
				"	6.300000	130.790576							1.045412	1.055875	4.022382						\n" + 
				"	8.400000	129.879119							0.000000	0.000000	24.883666						\n" + 
				"	10.500000	129.857962															\n" + 
				"	12.600000	-58223.577922															\n" + 
				//"	14.700000	129.554834															\n" + 
				//"	16.800000	129.257301															\n" + 
				//"	18.200000	129.860821															\n" + 
				//"	19.600000	129.642354															\n" + 
				"";

		DatabaseObject obj = new DatabaseObject("id", "Public", "Administration", "1");
		
		InputStream stream = new ByteArrayInputStream(fileS.getBytes(StandardCharsets.UTF_8));
		ArrayList<ObservationValueRow> rowset = new ArrayList<ObservationValueRow>();
		try {
			InterpretSpreadSheet.readDelimitedFile(stream, "\t", obj, rowset);
			System.out.println("==================================================");
			
			for(ObservationValueRow row : rowset) {
				System.out.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
