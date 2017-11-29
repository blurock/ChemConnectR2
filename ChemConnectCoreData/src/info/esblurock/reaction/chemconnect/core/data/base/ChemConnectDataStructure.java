package info.esblurock.reaction.chemconnect.core.data.base;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectDataStructure extends DatabaseObject {
	@Index
	HashSet<String> dataSetReferenceID;
	@Index
	String descriptionDataDataID;
	@Index
	HashSet<String> consortiumID;
	
	public ChemConnectDataStructure() {
		super();
		this.dataSetReferenceID = new HashSet<String>();
		this.descriptionDataDataID = "";
		this.consortiumID = new HashSet<String>();
	}
	public ChemConnectDataStructure(ChemConnectDataStructure datastructure) {
		super(datastructure.getIdentifier(),datastructure.getAccess(),datastructure.getOwner(),datastructure.getSourceID());
		this.dataSetReferenceID = datastructure.dataSetReferenceID;
		this.descriptionDataDataID = datastructure.getDescriptionDataDataID();
		this.consortiumID = datastructure.getConsortiumID();
	}

	public ChemConnectDataStructure(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.dataSetReferenceID = new HashSet<String>();
		this.descriptionDataDataID = "";
		this.consortiumID = new HashSet<String>();
	}

	public ChemConnectDataStructure(DatabaseObject obj,
			HashSet<String> dataSetReferenceID, String descriptionDataDataID, HashSet<String> consortiumID) {
		super(obj);
		this.dataSetReferenceID = dataSetReferenceID;
		this.descriptionDataDataID = descriptionDataDataID;
		this.consortiumID = consortiumID;
	}
	
	public ChemConnectDataStructure(String identifier, String access, String owner, String sourceID,
			HashSet<String> dataSetReference, String descriptionDataData, HashSet<String> consortium) {
		super(identifier, access, owner,sourceID);
		this.dataSetReferenceID = dataSetReference;
		this.descriptionDataDataID = descriptionDataData;
		this.consortiumID = consortium;
	}

	public void fill(ChemConnectDataStructure datastructure) {
		super.fill(datastructure.getIdentifier(), datastructure.getAccess(),
				datastructure.getOwner(), datastructure.getSourceID());
		this.dataSetReferenceID = datastructure.getDataSetReferenceID();
		this.descriptionDataDataID = datastructure.getDescriptionDataDataID();
		this.consortiumID = datastructure.getConsortiumID();
	}
	public HashSet<String> getDataSetReferenceID() {
		return dataSetReferenceID;
	}

	public String getDescriptionDataDataID() {
		return descriptionDataDataID;
	}

	public HashSet<String> getConsortiumID() {
		return consortiumID;
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
		builder.append("Reference: ");
		builder.append(dataSetReferenceID);
		builder.append(", Descr: ");
		builder.append(descriptionDataDataID);
		builder.append(", Consortium: ");
		builder.append(consortiumID);
		builder.append("\n");
		return builder.toString();
	}
}
