package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectDataStructure extends DatabaseObject {
	@Index
	String dataSetReference;
	@Index
	String descriptionDataData;
	@Index
	String chemConnectObjectLink;
	
	public ChemConnectDataStructure() {
		super();
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
	}
	
	public ChemConnectDataStructure(ChemConnectDataStructure datastructure) {
		super(datastructure.getIdentifier(),datastructure.getAccess(),datastructure.getOwner(),datastructure.getSourceID());
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
	}

	public ChemConnectDataStructure(DatabaseObject obj) {
		super(obj);
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
	}
	
	public ChemConnectDataStructure(DatabaseObject obj, String descriptionDataDataID) {
		super(obj);
		this.descriptionDataData = descriptionDataDataID;
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
	}
	
	public ChemConnectDataStructure(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
	}

	public ChemConnectDataStructure(DatabaseObject obj,
			String descriptionDataData, String dataSetReference, 
			String ChemConnectObjectLink) {
		super(obj);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.chemConnectObjectLink = ChemConnectObjectLink;
	}
	
	public ChemConnectDataStructure(String identifier, String access, String owner, String sourceID,
			String descriptionDataData, String dataSetReference, 
			String ChemConnectObjectLink) {
		super(identifier, access, owner,sourceID);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.chemConnectObjectLink = ChemConnectObjectLink;
	}

	public void fill(ChemConnectDataStructure datastructure) {
		super.fill(datastructure.getIdentifier(), datastructure.getAccess(),
				datastructure.getOwner(), datastructure.getSourceID());
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
	}
	
	
	public String getDataSetReference() {
		return dataSetReference;
	}

	public String getDescriptionDataData() {
		return descriptionDataData;
	}

	public String getChemConnectObjectLink() {
		return chemConnectObjectLink;
	}
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Descr: ");
		builder.append(descriptionDataData);
		builder.append("\n");
		builder.append(prefix);
		builder.append("Reference: ");
		builder.append(dataSetReference);
		builder.append("\n");
		builder.append(prefix + "ObjectLink: ");
		builder.append(chemConnectObjectLink);
		builder.append("\n");
		return builder.toString();
	}
}
