package info.esblurock.reaction.chemconnect.core.data.observations;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRow;

public class ObservationsFromSpreadSheet extends VisualizeObservationBase {

	private static final long serialVersionUID = 1L;
	
	
	ArrayList<ObservationValueRow> matrix;
	ArrayList<SpreadSheetBlockInformation> blocks;
	SpreadSheetInputInformation input;
	int sizeOfMatrix;
	int numberOfColumns;

	public ObservationsFromSpreadSheet() {
		super();
		//this.matrix = new ArrayList<SpreadSheetRow>();
		//this.blocks = new ArrayList<SpreadSheetBlockInformation>();
		this.matrix = null;
		this.blocks = null;
	}
	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input) {
		super(input);
		this.input = input;
		//this.matrix = new ArrayList<SpreadSheetRow>();
		//this.blocks = new ArrayList<SpreadSheetBlockInformation>();
		this.matrix = null;
		this.blocks = null;
	}

	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input, 
			ArrayList<ObservationValueRow> matrix) {
		super(input);
		this.input = input;
		this.matrix = matrix;
		this.blocks = null;
		//this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}
	
	public ArrayList<SpreadSheetBlockInformation> getBlocks() {
		return blocks;
	}
	
	public void addBlock(SpreadSheetBlockInformation block) {
		blocks.add(block);
	}
	/*	
	public void addRow(SpreadSheetRow row) {
		matrix.add(row);
	}

	public ArrayList<SpreadSheetRow> getMatrix() {
		return matrix;
	}
	*/
	public String toString() {
		return toString("");
	}
	
	public int getSizeOfMatrix() {
		return sizeOfMatrix;
	}
	
	public void setSizeOfMatrix(int sizeOfMatrix) {
		this.sizeOfMatrix = sizeOfMatrix;
	}
	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(input.toString(prefix + "ObservationsFromSpreadSheet: "));
		build.append("\n");
		//int count = 0;
		if(matrix != null) {
		for(ObservationValueRow row : matrix) {
			build.append(row.toString());
		}
		} else {
			build.append("Matrix size: " + sizeOfMatrix);
		}
		if(blocks != null) {
		for(SpreadSheetBlockInformation block : blocks) {
			build.append(block.toString(prefix));
			build.append("\n");
		}
		}
		return build.toString();
	}

}
