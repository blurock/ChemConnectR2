package info.esblurock.reaction.core.server.read.spreadsheet;


//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.junit.Test;

public class SpreadsheetScannerTest {

	@Test
	public void test() {
		String input = "Initial Temperature (K),Initial Pressure (bar),Compression Time (ms),Compressed Temperature (K),1000/Tc (1/K),Compressed Pressure (bar),Ignition Delay (msec),Ignition Delay Unc (msec)\n" + 
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

		String delimitor = ",";
		Scanner scan = new Scanner(input);
		
		System.out.println(input);
		
		
		ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if(line.startsWith(",")) {
				line = " " + line;
			}
			Scanner linescan = new Scanner(line);
			linescan.useDelimiter(delimitor);
			ArrayList<String> vector = new ArrayList<String>();
			System.out.println("Scanner: '" + line + "'");
			while(linescan.hasNext()) {
				String element = linescan.next();
				System.out.println("Element: '" + element + "'");
				vector.add(element);
			}
			matrix.add(vector);
			linescan.close();
		}
		scan.close();
		for(ArrayList<String> v : matrix) {
			System.out.println(v.toString());
		}
	scan = new Scanner(input);
	matrix = new ArrayList<ArrayList<String>>();
	while(scan.hasNextLine()) {
		String line = scan.nextLine();
		line = line.replaceAll(",", " , ");
		StringTokenizer linescan = new StringTokenizer(line,",");
		ArrayList<String> vector = new ArrayList<String>();
		System.out.println("Scanner: '" + line + "'");
		while(linescan.hasMoreTokens()) {
			String element = linescan.nextToken();
			System.out.println("Element: '" + element + "'");
			vector.add(element);
		}
		matrix.add(vector);
	}
	scan.close();
	for(ArrayList<String> v : matrix) {
		System.out.println(v.toString());
	}
}

}
