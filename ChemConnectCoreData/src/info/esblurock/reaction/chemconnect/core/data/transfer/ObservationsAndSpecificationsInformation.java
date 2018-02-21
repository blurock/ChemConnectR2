package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ObservationsAndSpecificationsInformation extends SetOfObservationsInformation {

	String source;
	String inputText;
	String singleObservation;
	ArrayList<String> vectorOfObservations;
	ArrayList<ArrayList<String>> matrixOfObervations;

	public ObservationsAndSpecificationsInformation() {
		
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	public String getSingleObservation() {
		return singleObservation;
	}
	public void setSingleObservation(String singleObservation) {
		this.singleObservation = singleObservation;
	}
	public ArrayList<String> getVectorOfObservations() {
		return vectorOfObservations;
	}
	public void setVectorOfObservations(ArrayList<String> vectorOfObservations) {
		this.vectorOfObservations = vectorOfObservations;
	}
	public ArrayList<ArrayList<String>> getMatrixOfObervations() {
		return matrixOfObervations;
	}
	public void setMatrixOfObervations(ArrayList<ArrayList<String>> matrixOfObervations) {
		this.matrixOfObervations = matrixOfObervations;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + source + "\n");
		builder.append(prefix + inputText + "\n");
		return builder.toString();
		
	}
}
