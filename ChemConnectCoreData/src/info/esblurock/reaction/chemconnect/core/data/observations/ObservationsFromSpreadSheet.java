package info.esblurock.reaction.chemconnect.core.data.observations;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class ObservationsFromSpreadSheet extends VisualizeObservationBase {

	private static final long serialVersionUID = 1L;
	
	
	ArrayList<SpreadSheetRow> matrix;
	ArrayList<SpreadSheetBlockInformation> blocks;
	SpreadSheetInputInformation input;

	public ObservationsFromSpreadSheet() {
		super();
		this.matrix = new ArrayList<SpreadSheetRow>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}
	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input) {
		super(input);
		this.input = input;
		this.matrix = new ArrayList<SpreadSheetRow>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}

	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input, 
			ArrayList<SpreadSheetRow> matrix) {
		super(input);
		this.input = input;
		this.matrix = matrix;
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}
	
	public ArrayList<SpreadSheetBlockInformation> getBlocks() {
		return blocks;
	}
	public void addBlock(SpreadSheetBlockInformation block) {
		blocks.add(block);
	}
	
	public void addRow(SpreadSheetRow row) {
		matrix.add(row);
	}

	public ArrayList<SpreadSheetRow> getMatrix() {
		return matrix;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(input.toString(prefix + "ObservationsFromSpreadSheet: "));
		build.append("\n");
		//int count = 0;
		for(SpreadSheetRow row : matrix) {
			build.append(row.toString());
			/*
			build.append(prefix + count++ + ": ");
			for(String cell : row.getRow()) {
				build.append("'" + cell + "' \t");
			}
			build.append("\n");
			*/
		}

		for(SpreadSheetBlockInformation block : blocks) {
			build.append(block.toString(prefix));
			build.append("\n");
		}
		return build.toString();
	}

}
