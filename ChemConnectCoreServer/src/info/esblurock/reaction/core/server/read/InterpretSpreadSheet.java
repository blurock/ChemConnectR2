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
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.db.image.UserImageServiceImpl;

public class InterpretSpreadSheet {

	public static DatabaseObjectHierarchy readSpreadSheetFromGCS(GCSBlobFileInformation gcsinfo,
			SpreadSheetInputInformation input, boolean writeObjects) throws IOException {
		InputStream stream = UserImageServiceImpl.getInputStream(gcsinfo);
		return streamReadSpreadSheet(stream, input, writeObjects);
	}

	public static DatabaseObjectHierarchy readSpreadSheet(boolean writeObjects, SpreadSheetInputInformation input)
			throws IOException {
		InputStream is = null;
		System.out.println(input.getSource());

		if (input.isSourceType(SpreadSheetInputInformation.URL)) {
			URL oracle;
			oracle = new URL(input.getSource());
			is = oracle.openStream();
		} else if (input.isSourceType(SpreadSheetInputInformation.STRINGSOURCE)) {
			String source = input.getSource();
			System.out.println(input.getSource());
			is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		} else if (input.isSourceType(SpreadSheetInputInformation.BLOBSOURCE)) {
		}
		if (is == null) {
			throw new IOException("Source Error: couldn't open source");
		}
		return streamReadSpreadSheet(is, input, writeObjects);
	}

	public static DatabaseObjectHierarchy streamReadSpreadSheet(InputStream is, 
			SpreadSheetInputInformation input,
			boolean writeObjects) throws IOException {
		System.out.println(input.toString());
		ArrayList<ObservationValueRow> set = new ArrayList<ObservationValueRow>();
		DatabaseObject obj = new DatabaseObject(input);
		obj.nullKey();
		
		int numberOfColumns = 0;
		if (input.isType(SpreadSheetInputInformation.XLS)) {
			numberOfColumns = readXLSFile(is, obj, set);
		} else if (input.isType(SpreadSheetInputInformation.CSV)) {
			System.out.println("streamReadSpreadSheet: CSV");
			numberOfColumns = readDelimitedFile(is, ",", obj, set);
		} else if (input.isType(SpreadSheetInputInformation.Delimited)) {
			numberOfColumns = readDelimitedFile(is, input.getDelimitor(), obj, set);
		} else if (input.isType(SpreadSheetInputInformation.SpaceDelimited)) {
			System.out.println("streamReadSpreadSheet: SpaceDelimited");
			numberOfColumns = readDelimitedFile(is, " ", obj, set);
		} else if (input.isType(SpreadSheetInputInformation.TabDelimited)) {
			System.out.println("streamReadSpreadSheet: TabDelimited");
			numberOfColumns = readDelimitedFile(is, "\t", obj, set);
		}
		
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,obj.getIdentifier());		
		for(ObservationValueRow row: set) {
			
		}
		
		
		ObservationsFromSpreadSheet obssheet;
		
		
		
		
		writeSpreadSheetRows(writeObjects, input, set);
		return obs;
	}

	public static void writeSpreadSheetRows(boolean writeObjects, SpreadSheetInputInformation input,
			ArrayList<DatabaseObject> set) {
		if (writeObjects) {
			DatabaseWriteBase.writeListOfDatabaseObjects(set);
			DatabaseWriteBase.writeObjectWithTransaction(input);
		}

	}

	private static ObservationValueRow createSpreadSheetRow(DatabaseObject obj, int count, ArrayList<String> lst) {
		DatabaseObject rowobj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-R" + count;
		rowobj.setIdentifier(id);
		
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(rowobj,obj.getIdentifier());
		String countS = String.valueOf(count);
		ObservationValueRow row = new ObservationValueRow(structure, countS, lst);
		return row;
	}

	public static int readDelimitedFile(InputStream is, String delimiter, DatabaseObject obj,
			ArrayList<ObservationValueRow> rowset) throws IOException {
		BufferedInputStream reader = new BufferedInputStream(is);
		BufferedReader r = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));
		boolean notdone = true;
		int count = 0;
		int numberOfColumns = 0;
		while (notdone) {
			String line = r.readLine();
			ArrayList<String> lst = new ArrayList<String>();
			if (line != null) {
				StringTokenizer tok = new StringTokenizer(line, delimiter,true);
				while (tok.hasMoreTokens()) {
					String cell = tok.nextToken();
					if(cell.compareTo("\t") == 0) {
						lst.add(" ");
					} else {
						lst.add(cell);
						if(tok.hasMoreTokens()) {
							tok.nextToken();
						}
					}
				}
				if (lst.size() > numberOfColumns) {
					numberOfColumns = lst.size();
				}
				ObservationValueRow row = createSpreadSheetRow(obj, count++, lst);
				rowset.add(row);
			} else {
				notdone = false;
			}
		}
		return numberOfColumns;
	}

	public static int readXLSFile(InputStream is, DatabaseObject obj, ArrayList<ObservationValueRow> rowset)
			throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(is);

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		int numberOfColumns = 0;
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
			if (array.size() > numberOfColumns) {
				numberOfColumns = array.size();
			}
			ObservationValueRow arrayrow = createSpreadSheetRow(obj, count++, array);
			rowset.add(arrayrow);
		}
		wb.close();
		return numberOfColumns;
	}

	public static SpreadSheetBlockInformation findBlocks(ObservationsFromSpreadSheet obs, ArrayList<ObservationValueRow> rows) {
		Iterator<ObservationValueRow> iter = rows.iterator();
		int linecount = 0;
		boolean morerows = iter.hasNext();
		ObservationValueRow row = iter.next();
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

	public static ObservationValueRow isolateTitleAndComments(ObservationValueRow row, Iterator<ObservationValueRow> iter,
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

	public static ObservationValueRow isolateBlockElements(ObservationValueRow row, Iterator<ObservationValueRow> iter,
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
			row = nextRow(iter, block);
			block.incrementLineCount();
		}
		block.decrementLineCount();
		return row;
	}

	public static ObservationValueRow nextRow(Iterator<ObservationValueRow> iter, SpreadSheetBlockInformation block) {
		ObservationValueRow row = iter.next();
		block.incrementTotalLineCount();
		return row;
	}

}
