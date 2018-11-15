package info.esblurock.reaction.core.server.db.spreadsheet;

import java.util.ArrayList;
import java.util.Scanner;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object.InterpretInputDataMatrix;
import info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object.InterpretInputDataObject;
import info.esblurock.reaction.chemconnect.core.data.base.spreadsheet.object.InterpretInputDataString;

@SuppressWarnings("serial")
@Entity
public class ConvertToMatrixOfObjects extends ConvertInputDataBase {

	public static String xslS  = "XSL";
	public static String textS = "TEXT";
	
	public static String commaS = "COMMA";
	public static String tabS = "TAB";
	public static String spaceS = "SPACE";
	
	@Index
	String fileType;
	@Index
	String delimitor;
	
	public ConvertToMatrixOfObjects(DatabaseObject object, String fileType, String delimitor) {
		super(object,InterpretInputDataString.class.getSimpleName(),
				InterpretInputDataMatrix.class.getSimpleName());
		this.fileType = fileType;
		this.delimitor = delimitor;
	}
	
	public String getFileType() {
		return fileType;
	}
	public String getDelimitor() {
		return delimitor;
	}

	@Override
	public InterpretInputDataObject process(DatabaseObject object, InterpretInputDataObject input) {
		InterpretInputDataString stringinput = (InterpretInputDataString) input;
		Scanner scan = new Scanner(stringinput.getStringObject());
		ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			line = line.replaceAll(",", " , ");
			Scanner linescan = new Scanner(line);
			linescan.useDelimiter(delimitor);
			ArrayList<String> vector = new ArrayList<String>();
			while(linescan.hasNext()) {
				String element = linescan.next();
				vector.add(element.trim());
			}
			matrix.add(vector);
			linescan.close();
		}
		scan.close();
		InterpretInputDataMatrix output = new InterpretInputDataMatrix(object,matrix);
		return output;
	}

	@Override
	public boolean applicable(InterpretInputDataObject input) {
		return input.getType().compareTo(InterpretInputDataString.class.getSimpleName()) == 0;
	}
}
