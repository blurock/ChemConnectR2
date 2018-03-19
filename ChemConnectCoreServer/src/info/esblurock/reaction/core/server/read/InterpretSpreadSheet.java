package info.esblurock.reaction.core.server.read;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetRow;

public class InterpretSpreadSheet {

	public static ObservationsFromSpreadSheet readSpreadSheet(SpreadSheetInputInformation input) throws IOException {
		ObservationsFromSpreadSheet obs = new ObservationsFromSpreadSheet(input);
		InputStream is = null;
		System.out.println(input.getSource());

		System.out.println(SpreadSheetInputInformation.STRINGSOURCE);
		System.out.println(input.getSourceType());
		System.out.println(input.isSourceType(SpreadSheetInputInformation.STRINGSOURCE));
		try {
			if (input.isSourceType(SpreadSheetInputInformation.URL)) {
				URL oracle;
				oracle = new URL(input.getSource());
				is = oracle.openStream();
			} else if (input.isSourceType(SpreadSheetInputInformation.STRINGSOURCE)) {
				String source = input.getSource();
				System.out.println(input.getSource());
				is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
			}

			if (is != null) {
				if (input.isType(SpreadSheetInputInformation.XLS)) {
					readXLSFile(is, obs);
				} else if (input.isType(SpreadSheetInputInformation.CVS)) {
					readDelimitedFile(is, ",", obs);
				} else if (input.isType(SpreadSheetInputInformation.Delimited)) {
					readDelimitedFile(is, input.getDelimitor(), obs);
				}
			} else {
				throw new IOException("Source Error: couldn't open source");
			}
		} catch (IOException e) {
			throw new IOException("Source Error: couldn't open source");
		}

		return obs;
	}

	public static void readDelimitedFile(InputStream is, String delimiter, ObservationsFromSpreadSheet obs)
			throws IOException {
		String parent = "";
		BufferedInputStream reader = new BufferedInputStream(is);
		BufferedReader r = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));
		boolean notdone = true;
		int count = 0;
		ArrayList<String> lst = new ArrayList<String>();
		while (notdone) {
			String line = r.readLine();
			if (line != null) {
				StringTokenizer tok = new StringTokenizer(line, delimiter);
				while (tok.hasMoreTokens()) {
					String cell = tok.nextToken();
					lst.add(cell);
				}
				SpreadSheetRow row = new SpreadSheetRow(obs.getBase(),count++,parent,lst);
				obs.addRow(row);
			} else {
				notdone = false;
			}
		}
	}

	public static void readXLSFile(InputStream is, ObservationsFromSpreadSheet obs) throws IOException {
		String parent = "";
		
		HSSFWorkbook wb = new HSSFWorkbook(is);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator<Row> rows = sheet.rowIterator();
		int count = 0;
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			ArrayList<String> array = new ArrayList<String>();
			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String element = cell.getStringCellValue();
					array.add(element);
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					double dbl = cell.getNumericCellValue();
					Double elementD = new Double(dbl);
					String elementS = elementD.toString();
					array.add(elementS);
				} else {
					// U Can Handel Boolean, Formula, Errors
				}
			}
			SpreadSheetRow arrayrow = new SpreadSheetRow(obs.getBase(),count++,parent,array);
			obs.addRow(arrayrow);
		}
		wb.close();
	}

	public static void findBlocks(ObservationsFromSpreadSheet obs) {
		ArrayList<SpreadSheetRow> rows = obs.getMatrix();
		Iterator<SpreadSheetRow> iter = rows.iterator();
		int linecount = 0;
		boolean morerows = iter.hasNext();
		SpreadSheetRow row = iter.next();
		while (morerows) {
			SpreadSheetBlockInformation block = new SpreadSheetBlockInformation(linecount);
			block.setFirstLine(linecount);
			if (row.size() == 1) {
				row = isolateTitleAndComments(row, iter, block);
			} else if (row.size() > 1) {
				row = isolateBlockElements(row, iter, block);
			}
			obs.addBlock(block);
			if (row != null) {
				if (row.size() == 0) {
					boolean notdone = iter.hasNext();
					while (notdone) {
						row = nextRow(iter, block);
						notdone = iter.hasNext();
						if (row.size() > 0) {
							notdone = false;
						}
					}
				}
			}
			block.finalize();
			morerows = iter.hasNext();
			linecount = block.getTotalLineCount();
		}
	}

	public static SpreadSheetRow isolateTitleAndComments(SpreadSheetRow row, Iterator<SpreadSheetRow> iter,
			SpreadSheetBlockInformation block) {
		if (row.size() == 1) {
			block.setTitle(row.get(0));
			block.setMaxNumberOfColumns(1);
			block.setMinNumberOfColumns(1);
			block.setNumberOfLines(1);
			block.setNumberOfColumnsEqual(true);
			row = nextRow(iter, block);
			if (row.size() == 1) {
				block.incrementLineCount();
				while (row.size() == 1) {
					String comment = row.get(0);
					block.addComment(comment);
					row = nextRow(iter, block);
					block.incrementLineCount();
				}
				if (row.size() > 0) {
					isolateBlockElements(row, iter, block);
					block.setTitlePlusBlock(true);
				}
			} else {
				block.setJustTitle(true);
			}
		}
		return row;
	}

	public static SpreadSheetRow isolateBlockElements(SpreadSheetRow row, Iterator<SpreadSheetRow> iter,
			SpreadSheetBlockInformation block) {
		block.setMinNumberOfColumns(1000000);
		while (row.size() > 1) {
			if (block.getMaxNumberOfColumns() < row.size()) {
				block.setMaxNumberOfColumns(row.size());
			}
			if (block.getMinNumberOfColumns() > row.size()) {
				block.setMinNumberOfColumns(row.size());
			}
			block.addRow(row);
			row = nextRow(iter,block);
			block.incrementLineCount();
		}
		block.decrementLineCount();
		return row;
	}

	public static SpreadSheetRow nextRow(Iterator<SpreadSheetRow> iter, SpreadSheetBlockInformation block) {
		SpreadSheetRow row = iter.next();
		block.incrementTotalLineCount();
		return row;
	}

}
