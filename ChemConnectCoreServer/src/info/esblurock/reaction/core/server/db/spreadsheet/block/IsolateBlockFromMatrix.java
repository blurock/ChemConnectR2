package info.esblurock.reaction.core.server.db.spreadsheet.block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class IsolateBlockFromMatrix {
	
	public static DatabaseObjectHierarchy isolateFromMatrix(DataCatalogID catid,  
			DatabaseObjectHierarchy matrixhier, 
			DatabaseObjectHierarchy blockdefinitionhier) throws IOException {
		ObservationsFromSpreadSheet matrix = (ObservationsFromSpreadSheet) matrixhier.getObject();
		ObservationBlockFromSpreadSheet blockdefinition = (ObservationBlockFromSpreadSheet) blockdefinitionhier.getObject();
	
		DatabaseObjectHierarchy valueshier = matrixhier.getSubObject(matrix.getObservationMatrixValues());
		DatabaseObjectHierarchy blockisolatehier = blockdefinitionhier.getSubObject(blockdefinition.getSpreadBlockIsolation());
		SpreadSheetBlockIsolation blockisolate = (SpreadSheetBlockIsolation) blockisolatehier.getObject();
		int beginrow = determineBeginRow(valueshier, blockisolate);
		int endrow = determineEndRow(beginrow,valueshier,blockisolate);
		DatabaseObjectHierarchy rowhier = valueshier.getSubobjects().get(beginrow);
		ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
		int begincolumn = determineBeginColumn(row,blockisolate);
		int endcolumn = determineEndColumn(begincolumn, row,blockisolate);
		int numberOfColumns = endcolumn - begincolumn+1;
		int numberOfRows = endrow - beginrow+1;
		DatabaseObjectHierarchy newmatrixhier = CreateDefaultObjectsFactory.fillObservationsFromSpreadSheet(catid, 
				catid, numberOfColumns, numberOfRows);
		ObservationsFromSpreadSheet newmatrix = (ObservationsFromSpreadSheet) newmatrixhier.getObject();
		DatabaseObjectHierarchy newvalueshier = matrixhier.getSubObject(newmatrix.getObservationMatrixValues());
		fillNewMatrix(beginrow, endrow, begincolumn, endcolumn,valueshier,newvalueshier);
		return newmatrixhier;
	}
	
	private static void fillNewMatrix(int beginrow, int endrow, int begincolumn,int endcolumn,
			DatabaseObjectHierarchy valueshier, DatabaseObjectHierarchy newvalueshier) {
		ArrayList<DatabaseObjectHierarchy> values = valueshier.getSubobjects();
		ArrayList<DatabaseObjectHierarchy> newvalues = valueshier.getSubobjects();
		int newrowcount = 0;
		for(int rowcount=beginrow; rowcount <= endrow;rowcount++) {
			DatabaseObjectHierarchy rowhier = values.get(rowcount);
			ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
			ArrayList<String> rowvalues = row.getRow();
			DatabaseObjectHierarchy newrowhier = newvalues.get(newrowcount);
			ObservationValueRow newrow = (ObservationValueRow) newrowhier.getObject();
			ArrayList<String> newrowvalues = newrow.getRow();
			int newcolcount = 0;
			for(int colcount=begincolumn; colcount <= endcolumn;colcount++) {
				if(colcount < rowvalues.size()) {
					newrowvalues.set(newcolcount, rowvalues.get(colcount));
				} else {
					newrowvalues.set(newcolcount, ",");
				}
				newcolcount++;
			}
		}
		
		
	}

	private static int determineEndColumn(int begincolumn, ObservationValueRow row, SpreadSheetBlockIsolation blockisolate) throws IOException {
		int endcolumn = row.getRow().size()-1;
		String endtype = blockisolate.getEndColumnType();
		String endinfo = blockisolate.getEndColumnInfo();
		if(endtype.compareTo(StandardDatasetMetaData.matrixBlockColumnEndMaximum) == 0) {
			endcolumn = row.getRow().size()-1;
		} else if(endtype.compareTo(StandardDatasetMetaData.matrixBlockColumnEndAtPosition) == 0) {
			try {
				begincolumn = Integer.valueOf(endinfo).intValue();
			} catch(NumberFormatException ex) {
				throw new IOException("Column begin position not a number: '" + endinfo + "'");
			}			
		}
		return endcolumn;
	}
	
	private static int determineBeginColumn(ObservationValueRow row, SpreadSheetBlockIsolation blockisolate) throws IOException {
		int begincolumn = 0;
		String begintype = blockisolate.getStartColumnType();
		String begininfo = blockisolate.getStartColumnInfo();
		if(begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnBeginLeft) == 0) {
			begincolumn = 0;
		} else if(begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnAtPosition) == 0) {
			try {
				begincolumn = Integer.valueOf(begininfo).intValue();
			} catch(NumberFormatException ex) {
				throw new IOException("Column begin position not a number: '" + begininfo + "'");
			}
		} else if(begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnBeginAtIdentifier) == 0) {
			if(begininfo == null) {
				throw new IOException("Column begin identifier not specified");
			}
			if(begininfo.length() == 0) {
				throw new IOException("Column begin identifier not specified");
			}
			ArrayList<String> elements = row.getRow();
			int count = 0;
			boolean notfound = true;
			while(notfound && count < elements.size()) {
				String element = elements.get(count);
				if(element.compareTo(begininfo) == 0) {
					notfound = false;
				} else {
					count++;
				}
			}
			if(count >= elements.size()) {
				throw new IOException("Column begin position not found: '" + begininfo + "'");
			}
		}
		return begincolumn;
	}
	
	private static int determineEndRow(int start, DatabaseObjectHierarchy valueshier, SpreadSheetBlockIsolation blockisolate) throws IOException {
		String endtype = blockisolate.getStartRowType();
		String endinfo = blockisolate.getStartRowInfo();
		if(endinfo == null) {
			endinfo = String.valueOf(start);
		}
		int endrow = start;
		ObservationMatrixValues values = (ObservationMatrixValues) valueshier.getObject();
		DatabaseObjectHierarchy multhier = valueshier.getSubObject(values.getObservationRowValue());
		
		if(endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtBlankLine) == 0) {
			try {
				endrow = searchForBlankLine(start,multhier.getSubobjects());
			} catch(IOException ex) {
				throw new IOException("Blank Line Expected for rows");
			}
		} else if(endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtFileEnd) == 0) {
			endrow = multhier.getSubobjects().size() -1;
		} else if(endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtFileEndOrBlankLine) == 0) {
			try {
				endrow = searchForBlankLine(start,multhier.getSubobjects());
			} catch(IOException ex) {
				endrow = multhier.getSubobjects().size()-1;
			}
		} else if(endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtIdentifierExclusive) == 0) {
			endrow = findIdentifierInRows(0,endinfo,multhier)-1;
		} else if(endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtIdentifierInclusive) == 0) {
			endrow = findIdentifierInRows(0,endinfo,multhier);
		}
		return endrow;
	}
	private static int determineBeginRow(DatabaseObjectHierarchy valueshier, SpreadSheetBlockIsolation blockisolate) throws IOException {
		String begintype = blockisolate.getStartRowType();
		String begininfo = blockisolate.getStartRowInfo();
		if(begininfo == null) {
			begininfo = "0";
		}
		int beginrow = 0;
		ObservationMatrixValues values = (ObservationMatrixValues) valueshier.getObject();
		DatabaseObjectHierarchy multhier = valueshier.getSubObject(values.getObservationRowValue());
		
		if(begintype.compareTo(StandardDatasetMetaData.beginMatrixAfterSpecifiedIdentifier) == 0) {
			beginrow = findIdentifierInRows(0,begininfo,multhier);
		} else if(begintype.compareTo(StandardDatasetMetaData.beginMatrixAtSpecified) == 0) {
			beginrow = Integer.valueOf(begininfo).intValue();
		} else if(begintype.compareTo(StandardDatasetMetaData.beginMatrixTopOfSpreadSheet) == 0) {
			beginrow = 0;
		}
		return beginrow;
	}
	
	private static int searchForBlankLine(int startrow, ArrayList<DatabaseObjectHierarchy> subs) throws IOException {
		int endrow = startrow;
		boolean notfound = true;
		while(notfound && endrow < subs.size() ) {
			DatabaseObjectHierarchy hierarchy = subs.get(endrow);
			ObservationValueRow row = (ObservationValueRow) hierarchy.getObject();
			if(rowIsABlankLine(row)) {
				notfound = false;
			} else {
				endrow++;
			}
		}
		if(endrow >= subs.size()) {
			throw new IOException("End of file reached");
		}
		return --endrow;
	}
	private static boolean rowIsABlankLine(ObservationValueRow row) {
		boolean blank = true;
		int count = 0;
		while(count < row.size() && blank) {
			if(row.get(count).compareTo(",") == 0) {
				count++;
			}
		}
		return blank;
	}
	private static int findIdentifierInRows(int start, String identifier, DatabaseObjectHierarchy multhier) throws IOException {
		int beginrow = 0;
		Iterator<DatabaseObjectHierarchy> iter = multhier.getSubobjects().iterator();
		while(iter.hasNext() && beginrow < start) {
			iter.next();
			beginrow++;
		}
		boolean notfound = true;
		while(iter.hasNext() && notfound) {
			ObservationValueRow row = (ObservationValueRow) iter.next().getObject();
			ArrayList<String> rowvalues = row.getRow();
			if(rowvalues.size() > 0) {
				String rowS = rowvalues.get(0);
				if(rowS.compareTo(identifier) == 0) {
					notfound = false;
				} else {
					beginrow++;
				}
			} else {
				beginrow++;
			}
			iter.next();
		}
		if(!iter.hasNext()) {
			throw new IOException("Identifier for end of block not found: " + identifier);
		}
		return beginrow;
	}

}
