package info.esblurock.reaction.chemconnect.core.data.observations;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public class ObservationsFromSpreadSheet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	ArrayList<SpreadSheetRow> matrix;
	ArrayList<SpreadSheetBlockInformation> blocks;
	SpreadSheetInputInformation input;
	DatabaseObject obj = new DatabaseObject();

	public ObservationsFromSpreadSheet() {
		this.matrix = new ArrayList<SpreadSheetRow>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
		this.obj = new DatabaseObject();
	}
	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input) {
		this.input = input;
		this.matrix = new ArrayList<SpreadSheetRow>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
		this.obj = new DatabaseObject(input);
	}

	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input, 
			ArrayList<SpreadSheetRow> matrix) {
		this.input = input;
		this.matrix = matrix;
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
		this.obj = new DatabaseObject(input);
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
	
	public DatabaseObject getBase() {
		return obj;
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(input.toString(prefix + "ObservationsFromSpreadSheet: "));
		build.append("\n");
		int count = 0;
		for(SpreadSheetRow row : matrix) {
			build.append(row.toString());
			build.append(prefix + count++ + ": ");
			for(String cell : row.getRow()) {
				build.append("'" + cell + "' \t");
			}
			build.append("\n");
		}

		for(SpreadSheetBlockInformation block : blocks) {
			build.append(block.toString(prefix));
			build.append("\n");
		}
		return build.toString();
	}

}
