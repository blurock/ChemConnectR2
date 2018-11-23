package info.esblurock.reaction.core.server.db.spreadsheet.block;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationMatrixValues;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.core.server.db.spreadsheet.CompareObservationValueRowHierarchy;
import info.esblurock.reaction.core.server.initialization.CreateDefaultObjectsFactory;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;

public class IsolateBlockFromMatrix {

	public static DatabaseObjectHierarchy isolateFromMatrix(DataCatalogID catid, DatabaseObjectHierarchy matrixhier,
			SpreadSheetBlockIsolation blockisolate) throws IOException {
		ObservationsFromSpreadSheetFull matrix = (ObservationsFromSpreadSheetFull) matrixhier.getObject();
		DatabaseObjectHierarchy obsmathier = matrixhier.getSubObject(matrix.getObservationMatrixValues());
		ObservationMatrixValues obsvalues = (ObservationMatrixValues) obsmathier.getObject();
		DatabaseObjectHierarchy valueshier = obsmathier.getSubObject(obsvalues.getObservationRowValue());
		int beginrow = determineBeginRow(valueshier, blockisolate);
		int endrow = determineEndRow(beginrow, valueshier, blockisolate);
		DatabaseObjectHierarchy rowhier = valueshier.getSubobjects().get(beginrow);
		ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
		String titleS = blockisolate.getTitleIncluded();
		boolean title = titleS.compareTo(StandardDatasetMetaData.matrixBlockTitleFirstLine) == 0;
		boolean firstcolumn = titleS.compareTo(StandardDatasetMetaData.matrixBlockTitlesFirstColumnTranspose) == 0;
		int begincolumn = determineBeginColumn(row, blockisolate);
		int endcolumn = determineEndColumn(begincolumn, row, blockisolate);
		int numberOfColumns = endcolumn - begincolumn + 1;
		int numberOfRows = endrow - beginrow + 1;
		if (title) {
			numberOfRows--;
		}
		if (firstcolumn) {
			numberOfColumns -= 1;
		}
		/*
		 System.out.println("blockisolate:\n " + blockisolate.toString());
		 System.out.println("begincolumn: " + begincolumn);
		 System.out.println("endcolumn:   " + endcolumn);
		 System.out.println("beginrow:    " + beginrow);
		 System.out.println("endrow:      " + endrow);
		 System.out.println("title:       " + title);
		 System.out.println("firstcolumn: " + firstcolumn);
		 */
		DatabaseObjectHierarchy infohier = InterpretData.SpreadSheetInputInformation.createEmptyObject(catid);
		SpreadSheetInputInformation newinput = (SpreadSheetInputInformation) infohier.getObject();
		newinput.setSourceType(StandardDatasetMetaData.chemConnectDataObject);
		newinput.setSource(matrix.getIdentifier());
		newinput.setType(StandardDatasetMetaData.matrixBlockIsolated);
		newinput.setDelimitor("none");

		DatabaseObjectHierarchy newmatrixhier = null;
		if(firstcolumn) {
			newmatrixhier = CreateDefaultObjectsFactory.fillObservationsFromSpreadSheet(catid,
					catid, newinput, numberOfRows, numberOfColumns);			
		} else {
			newmatrixhier = CreateDefaultObjectsFactory.fillObservationsFromSpreadSheet(catid,
				catid, newinput, numberOfColumns, numberOfRows);
		}
		ObservationsFromSpreadSheet newmatrix = (ObservationsFromSpreadSheet) newmatrixhier.getObject();
		DatabaseObjectHierarchy newobsmathier = newmatrixhier.getSubObject(newmatrix.getObservationMatrixValues());
		ObservationMatrixValues newobsvalues = (ObservationMatrixValues) newobsmathier.getObject();
		DatabaseObjectHierarchy newvalueshier = newobsmathier.getSubObject(newobsvalues.getObservationRowValue());
		ArrayList<DatabaseObjectHierarchy> values = valueshier.getSubobjects();
		ArrayList<DatabaseObjectHierarchy> newvalues = newvalueshier.getSubobjects();
		values.sort(new CompareObservationValueRowHierarchy());
		newvalues.sort(new CompareObservationValueRowHierarchy());
		DatabaseObjectHierarchy titleshier = newmatrixhier.getSubObject(newmatrix.getObservationValueRowTitle());
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titleshier.getObject();
		if (title) {
			DatabaseObjectHierarchy rowelementhier = values.get(beginrow);
			//System.out.println(rowelementhier.toString("TitleRow: "));
			ObservationValueRow rowelement = (ObservationValueRow) rowelementhier.getObject();
			ArrayList<String> rowvalues = new ArrayList<String>(numberOfColumns);
			ArrayList<String> originalvalues = rowelement.getRow();
			for (int i = begincolumn; i <= endcolumn; i++) {
				rowvalues.add(originalvalues.get(i));
			}
			titles.setParameterLabel(rowvalues);
			beginrow++;
		}
		if (firstcolumn) {
			ArrayList<String> rowvalues = new ArrayList<String>();
			for (int i = beginrow; i <= endrow; i++) {
				DatabaseObjectHierarchy valuehier = values.get(i);
				ObservationValueRow therow = (ObservationValueRow) valuehier.getObject();
				ArrayList<String> rowvalueset = therow.getRow();
				String firstcol = rowvalueset.get(begincolumn);
				rowvalues.add(firstcol);
			}
			titles.setParameterLabel(rowvalues);
			begincolumn += 1;
		} else {
			if(!title) {
				ArrayList<String> rowvalues = new ArrayList<String>(numberOfColumns);
				for (int i = 0; i < numberOfColumns; i++) {
					String coltitle = "col: " + i;
					rowvalues.add(coltitle);
				}
				titles.setParameterLabel(rowvalues);
			}
		}
		if (firstcolumn) {
			fillNewMatrixTranspose(beginrow, endrow, begincolumn, endcolumn, values, newvalues);
		} else {
			fillNewMatrix(beginrow, endrow, begincolumn, endcolumn, values, newvalues);
		} 
		return newmatrixhier;
	}

	private static void fillNewMatrixTranspose(int beginrow, int endrow, int begincolumn, int endcolumn,
			ArrayList<DatabaseObjectHierarchy> values, 
			ArrayList<DatabaseObjectHierarchy> newvalues) {
		int coli = begincolumn;
		for (DatabaseObjectHierarchy newhier : newvalues) {
			ObservationValueRow newrow = (ObservationValueRow) newhier.getObject();
			ArrayList<String> newrowvalueset = new ArrayList<String>();
			for (int rowcount = beginrow; rowcount <= endrow; rowcount++) {
				DatabaseObjectHierarchy rowhier = values.get(rowcount);
				ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
				ArrayList<String> rowvalueset = row.getRow();
				newrowvalueset.add(rowvalueset.get(coli));
			}
			newrow.setRow(newrowvalueset);
			coli++;
		}
	}

	private static void fillNewMatrix(int beginrow, int endrow, int begincolumn, int endcolumn,
			ArrayList<DatabaseObjectHierarchy> values, ArrayList<DatabaseObjectHierarchy> newvalues) {
		int newrowcount = 0;
		for (int rowcount = beginrow; rowcount <= endrow; rowcount++) {
			DatabaseObjectHierarchy rowhier = values.get(rowcount);
			ObservationValueRow row = (ObservationValueRow) rowhier.getObject();
			ArrayList<String> rowvalues = row.getRow();
			DatabaseObjectHierarchy newrowhier = newvalues.get(newrowcount);
			ObservationValueRow newrow = (ObservationValueRow) newrowhier.getObject();
			ArrayList<String> newrowvalues = newrow.getRow();
			int newcolcount = 0;
			for (int colcount = begincolumn; colcount <= endcolumn; colcount++) {
				if (colcount < rowvalues.size()) {
					newrowvalues.set(newcolcount, rowvalues.get(colcount));
				} else {
					newrowvalues.set(newcolcount, ",");
				}
				newcolcount++;
			}
			newrowcount++;
		}
	}

	private static int determineEndColumn(int begincolumn, ObservationValueRow row,
			SpreadSheetBlockIsolation blockisolate) throws IOException {
		int endcolumn = row.getRow().size() - 1;
		String endtype = blockisolate.getEndColumnType();
		String endinfo = blockisolate.getEndColumnInfo();
		if (endtype.compareTo(StandardDatasetMetaData.matrixBlockColumnEndMaximum) == 0) {
			endcolumn = row.getRow().size() - 1;
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockColumnEndAtPosition) == 0) {
			try {
				endcolumn = Integer.valueOf(endinfo).intValue();
			} catch (NumberFormatException ex) {
				throw new IOException("Column begin position not a number: '" + endinfo + "'");
			}
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockColumnEndNumberOfColumns) == 0) {
			try {
				int count = Integer.valueOf(endinfo).intValue();
				endcolumn = begincolumn + count - 1;
				if (endcolumn > row.getRow().size() - 1) {
					endcolumn = row.getRow().size() - 1;
				}
			} catch (NumberFormatException ex) {
				throw new IOException("Column begin position not a number: '" + endinfo + "'");
			}
		}
		return endcolumn;
	}

	private static int determineBeginColumn(ObservationValueRow row, SpreadSheetBlockIsolation blockisolate)
			throws IOException {
		int begincolumn = 0;
		String begintype = blockisolate.getStartColumnType();
		String begininfo = blockisolate.getStartColumnInfo();
		if (begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnBeginLeft) == 0) {
			begincolumn = 0;
		} else if (begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnBeginAtPosition) == 0) {
			try {
				begincolumn = Integer.valueOf(begininfo).intValue();
			} catch (NumberFormatException ex) {
				throw new IOException("Column begin position not a number: '" + begininfo + "'");
			}
		} else if (begintype.compareTo(StandardDatasetMetaData.matrixBlockColumnBeginAtIdentifier) == 0) {
			if (begininfo == null) {
				throw new IOException("Column begin identifier not specified");
			}
			if (begininfo.length() == 0) {
				throw new IOException("Column begin identifier not specified");
			}
			ArrayList<String> elements = row.getRow();
			int count = 0;
			boolean notfound = true;
			while (notfound && count < elements.size()) {
				String element = elements.get(count);
				if (element.compareTo(begininfo) == 0) {
					notfound = false;
				} else {
					count++;
				}
			}
			if (count >= elements.size()) {
				throw new IOException("Column begin position not found: '" + begininfo + "'");
			}
		}
		return begincolumn;
	}

	private static int determineEndRow(int start, DatabaseObjectHierarchy multhier,
			SpreadSheetBlockIsolation blockisolate) throws IOException {
		String endtype = blockisolate.getEndRowType();
		String endinfo = blockisolate.getEndRowInfo();
		if (endinfo == null) {
			endinfo = String.valueOf(start);
		}
		int endrow = start;
		if (endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtBlankLine) == 0) {
			try {
				endrow = searchForBlankLine(start, multhier.getSubobjects());
			} catch (IOException ex) {
				throw new IOException("Blank Line Expected for rows");
			}
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtFileEnd) == 0) {
			endrow = multhier.getSubobjects().size() - 1;
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtFileEndOrBlankLine) == 0) {
			try {
				endrow = searchForBlankLine(start, multhier.getSubobjects());
			} catch (IOException ex) {
				endrow = multhier.getSubobjects().size() - 1;
			}
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtIdentifierExclusive) == 0) {
			endrow = findIdentifierInRows(0, endinfo, multhier, false) - 1;
		} else if (endtype.compareTo(StandardDatasetMetaData.matrixBlockEndAtIdentifierInclusive) == 0) {
			endrow = findIdentifierInRows(0, endinfo, multhier, false);
		}
		return endrow;
	}

	private static int determineBeginRow(DatabaseObjectHierarchy multhier, SpreadSheetBlockIsolation blockisolate)
			throws IOException {
		String begintype = blockisolate.getStartRowType();
		String begininfo = blockisolate.getStartRowInfo();
		if (begininfo == null) {
			begininfo = "0";
		}
		int beginrow = 0;
		if (begintype.compareTo(StandardDatasetMetaData.beginMatrixAfterSpecifiedIdentifier) == 0) {
			beginrow = findIdentifierInRows(0, begininfo, multhier, false);
		} else if (begintype.compareTo(StandardDatasetMetaData.beginMatrixAtSpecified) == 0) {
			beginrow = Integer.valueOf(begininfo).intValue();
		} else if (begintype.compareTo(StandardDatasetMetaData.beginMatrixTopOfSpreadSheet) == 0) {
			beginrow = 0;
		} else if (begintype.compareTo(StandardDatasetMetaData.beginMatrixAtStartsWithSpecifiedIdentifier) == 0) {
			beginrow = findIdentifierInRows(0, begininfo, multhier, true);
		}
		return beginrow;
	}

	private static int searchForBlankLine(int startrow, ArrayList<DatabaseObjectHierarchy> subs) throws IOException {
		int endrow = startrow;
		boolean notfound = true;

		subs.sort(new CompareObservationValueRowHierarchy());
		while (notfound && endrow < subs.size()) {

			DatabaseObjectHierarchy hierarchy = subs.get(endrow);
			ObservationValueRow row = (ObservationValueRow) hierarchy.getObject();
			if (rowIsABlankLine(row)) {
				notfound = false;
			} else {
				endrow++;
			}
		}
		if (endrow >= subs.size()) {
			throw new IOException("End of file reached");
		}
		return --endrow;
	}

	private static boolean rowIsABlankLine(ObservationValueRow row) {
		boolean blank = true;
		int count = 0;
		ArrayList<String> labels = row.getRow();
		while (count < labels.size() && blank) {
			if (labels.get(count).length() > 0) {
				blank = false;
			}
			count++;
		}
		return blank;
	}

	private static int findIdentifierInRows(int start, String identifier, DatabaseObjectHierarchy multhier,
			boolean startsWith) throws IOException {
		boolean notfound = true;
		int endrow = start;
		ArrayList<DatabaseObjectHierarchy> lst = multhier.getSubobjects();
		lst.sort(new CompareObservationValueRowHierarchy());
		while (endrow < lst.size() && notfound) {
			DatabaseObjectHierarchy hier = lst.get(endrow);
			ObservationValueRow row = (ObservationValueRow) hier.getObject();
			ArrayList<String> rowvalues = row.getRow();
			if (rowvalues.size() > 0) {
				String rowS = rowvalues.get(0);
				if (startsWith) {
					if (rowS.startsWith(identifier)) {
						notfound = false;
					} else {
						endrow++;
					}
				} else {
					if (rowS.compareTo(identifier) == 0) {
						notfound = false;
					} else {
						endrow++;
					}
				}
			} else {
				endrow++;
			}
		}
		if (endrow >= lst.size()) {
			throw new IOException("Identifier for end of block not found: " + identifier);
		}
		return endrow;
	}

}
