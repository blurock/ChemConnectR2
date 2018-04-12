package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectDataStructure extends DatabaseObject {
	@Index
	HashSet<String> dataSetReference;
	@Index
	String descriptionDataData;
	@Index
	HashSet<String> ChemConnectObjectLink;
	
	public ChemConnectDataStructure() {
		super();
		this.descriptionDataData = "";
		this.dataSetReference = new HashSet<String>();
		this.ChemConnectObjectLink = new HashSet<String>();
	}
	
	public ChemConnectDataStructure(ChemConnectDataStructure datastructure) {
		super(datastructure.getIdentifier(),datastructure.getAccess(),datastructure.getOwner(),datastructure.getSourceID());
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.ChemConnectObjectLink = datastructure.getChemConnectObjectLink();
	}

	public ChemConnectDataStructure(DatabaseObject obj) {
		super(obj);
		this.descriptionDataData = "";
		this.dataSetReference = new HashSet<String>();
		this.ChemConnectObjectLink = new HashSet<String>();
	}
	
	public ChemConnectDataStructure(DatabaseObject obj, String descriptionDataDataID) {
		super(obj);
		this.descriptionDataData = descriptionDataDataID;
		this.dataSetReference = new HashSet<String>();
		this.ChemConnectObjectLink = new HashSet<String>();
	}
	
	public ChemConnectDataStructure(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.descriptionDataData = "";
		this.dataSetReference = new HashSet<String>();
		this.ChemConnectObjectLink = new HashSet<String>();
	}

	public ChemConnectDataStructure(DatabaseObject obj,
			String descriptionDataData, HashSet<String> dataSetReference, 
			HashSet<String> ChemConnectObjectLink) {
		super(obj);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.ChemConnectObjectLink = ChemConnectObjectLink;
	}
	
	public ChemConnectDataStructure(String identifier, String access, String owner, String sourceID,
			String descriptionDataData, HashSet<String> dataSetReference, 
			HashSet<String> ChemConnectObjectLink) {
		super(identifier, access, owner,sourceID);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.ChemConnectObjectLink = ChemConnectObjectLink;
	}

	public void fill(ChemConnectDataStructure datastructure) {
		super.fill(datastructure.getIdentifier(), datastructure.getAccess(),
				datastructure.getOwner(), datastructure.getSourceID());
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.ChemConnectObjectLink = datastructure.getChemConnectObjectLink();
	}
	public HashSet<String> getDataSetReference() {
		return dataSetReference;
	}

	public String getDescriptionDataData() {
		return descriptionDataData;
	}

	public HashSet<String> getChemConnectObjectLink() {
		return ChemConnectObjectLink;
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
		builder.append(ChemConnectObjectLink);
		builder.append("\n");
		return builder.toString();
	}
}
