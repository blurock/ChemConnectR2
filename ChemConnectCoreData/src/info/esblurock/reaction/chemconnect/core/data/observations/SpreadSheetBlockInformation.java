package info.esblurock.reaction.chemconnect.core.data.observations;

import java.io.Serializable;
import java.util.ArrayList;

public class SpreadSheetBlockInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	int numberOfLines;
	int firstLine;
	int lastLine;
	int maxNumberOfColumns;
	int minNumberOfColumns;
	boolean numberOfColumnsEqual;
	boolean titlePlusBlock;
	boolean justTitle;
	
	int currentLineCount;
	
	String title;
	ArrayList<String> comments;
	ArrayList<ArrayList<String>> rows;
	
	public SpreadSheetBlockInformation() {
		
	}
		public SpreadSheetBlockInformation(int linecount) {
		numberOfLines = 0;
		firstLine = 0;
		lastLine = 0;
		maxNumberOfColumns = 0;
		minNumberOfColumns = 0;
		numberOfColumnsEqual = false;
		titlePlusBlock = false;
		justTitle = false;
		title = "";
		comments = new ArrayList<String>();
		rows = new ArrayList<ArrayList<String>>();
		currentLineCount = linecount;
	}
	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public int getFirstLine() {
		return firstLine;
	}

	public int getLastLine() {
		return lastLine;
	}

	public int getMaxNumberOfColumns() {
		return maxNumberOfColumns;
	}

	public void setMaxNumberOfColumns(int maxNumberOfColumns) {
		this.maxNumberOfColumns = maxNumberOfColumns;
	}

	public int getMinNumberOfColumns() {
		return minNumberOfColumns;
	}

	public void setMinNumberOfColumns(int minNumberOfColumns) {
		this.minNumberOfColumns = minNumberOfColumns;
	}

	public boolean isNumberOfColumnsEqual() {
		return numberOfColumnsEqual;
	}

	public void setNumberOfColumnsEqual(boolean numberOfColumnsEqual) {
		this.numberOfColumnsEqual = numberOfColumnsEqual;
	}

	public boolean isTitlePlusBlock() {
		return titlePlusBlock;
	}

	public void setTitlePlusBlock(boolean titlePlusBlock) {
		this.titlePlusBlock = titlePlusBlock;
	}

	public boolean isJustTitle() {
		return justTitle;
	}

	public void setJustTitle(boolean justTitle) {
		this.justTitle = justTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
	
	public ArrayList<String> getComments() {
		return comments;
	}
	public ArrayList<ArrayList<String>> getRows() {
		return rows;
	}
	public void addComment(String comment) {
		comments.add(comment);
	}
	public void addRow(ArrayList<String> row) {
		rows.add(row);
	}
	public void incrementLineCount() {
		numberOfLines++;
		lastLine++;	
	}
	public void decrementLineCount() {
		lastLine--;	
	}

	public void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
		lastLine = firstLine;
		numberOfLines = 0;
	}
	public int getTotalLineCount() {
		return currentLineCount;
	}

	public void finalize() {
		numberOfColumnsEqual = (maxNumberOfColumns == minNumberOfColumns);
	}
	
	public int incrementTotalLineCount() {
		currentLineCount++;
		return currentLineCount;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		if(numberOfLines > 1) {
			build.append("Block Lines: " + firstLine + "-" + lastLine + "(" + numberOfLines + ")");
		} else {
			build.append("Line: " + firstLine);
		}
		if(numberOfColumnsEqual) {
			if(minNumberOfColumns > 1) {
				build.append(" Columns: " + minNumberOfColumns);
			}
		} else {
			build.append(" Columns: " + minNumberOfColumns + "-" + maxNumberOfColumns);			
		}
		if(justTitle) {
			build.append(prefix + "  Block Just Title: ");
		} else {
			build.append(prefix);			
		}
		if(title.length() > 0) {
			build.append("'" + title + "'");
		}
		return build.toString();
	}

	
}
