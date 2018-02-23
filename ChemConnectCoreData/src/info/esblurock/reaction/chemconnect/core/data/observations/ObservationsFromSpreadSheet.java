package info.esblurock.reaction.chemconnect.core.data.observations;

import java.io.Serializable;
import java.util.ArrayList;

public class ObservationsFromSpreadSheet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	ArrayList<ArrayList<String>> matrix;
	ArrayList<SpreadSheetBlockInformation> blocks;
	SpreadSheetInputInformation input;
	
	public ObservationsFromSpreadSheet() {
		this.matrix = new ArrayList<ArrayList<String>>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}
	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input) {
		this.input = input;
		this.matrix = new ArrayList<ArrayList<String>>();
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}

	public ObservationsFromSpreadSheet(SpreadSheetInputInformation input, 
			ArrayList<ArrayList<String>> matrix) {
		this.input = input;
		this.matrix = matrix;
		this.blocks = new ArrayList<SpreadSheetBlockInformation>();
	}
	
	public void addBlock(SpreadSheetBlockInformation block) {
		blocks.add(block);
	}
	
	public void addRow(ArrayList<String> row) {
		matrix.add(row);
	}

	public ArrayList<ArrayList<String>> getMatrix() {
		return matrix;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(input.toString(prefix + "ObservationsFromSpreadSheet: "));
		build.append("\n");
		int count = 0;
		for(ArrayList<String> row : matrix) {
			build.append(prefix + count++ + ": ");
			for(String cell : row) {
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
