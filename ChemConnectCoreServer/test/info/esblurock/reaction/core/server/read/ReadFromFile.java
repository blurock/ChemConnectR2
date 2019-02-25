package info.esblurock.reaction.core.server.read;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class ReadFromFile {

	@Test
	public void test() {
		/*
		URL oracle;
		try {
			oracle = new URL("http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls");
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(oracle.openStream()));

	                String inputLine;
	                while ((inputLine = in.readLine()) != null)
	                    System.out.println(inputLine);
	                in.close();
	               
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		try {
			String url = "https://storage.googleapis.com/chemconnect/upload/Blurock/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
			//String url = "http://cms.heatfluxburner.org/wp-content/uploads/Bosschaart_CH4_Air_1atm_Tu_295K_thesis.xls";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
	public void readXLSFile(String xlsS) throws IOException
	{
		URL oracle = new URL(xlsS);
        InputStream is = oracle.openStream();
		//InputStream ExcelFileToRead = new FileInputStream(oracle);
		HSSFWorkbook wb = new HSSFWorkbook(is);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;
		int rowcount = 0;
		Iterator<Row> rows = sheet.rowIterator();
		ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			ArrayList<String> rowarray = new ArrayList<String>();
			while (cells.hasNext()) {
				cell=(HSSFCell) cells.next();
				if(cell.getCellTypeEnum() == CellType.STRING){
					String element = cell.getStringCellValue();
					rowarray.add(element);
				}
				else if(cell.getCellTypeEnum() == CellType.NUMERIC) {
					double dbl = cell.getNumericCellValue();
					Double elementD = new Double(dbl);
					String elementS = elementD.toString();
					rowarray.add(elementS);
				} else {
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println(rowcount++ + ":  " + rowarray.size());
			System.out.println(rowarray);
			matrix.add(rowarray);
		}
		System.out.println(matrix);
		wb.close();
	}

}
