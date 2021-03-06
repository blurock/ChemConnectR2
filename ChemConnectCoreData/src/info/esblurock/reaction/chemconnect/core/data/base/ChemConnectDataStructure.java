package info.esblurock.reaction.chemconnect.core.data.base;

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
	@Index
	String catalogDataID;
	@Index
	String contactHasSite;
	
	public ChemConnectDataStructure() {
		super();
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.catalogDataID = "";
		this.contactHasSite = "";
	}
	
	public ChemConnectDataStructure(ChemConnectDataStructure datastructure) {
		super(datastructure.getIdentifier(),datastructure.getAccess(),datastructure.getOwner(),datastructure.getSourceID());
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
		this.catalogDataID = datastructure.getCatalogDataID();
		this.contactHasSite = datastructure.getContactHasSite();
	}

	public ChemConnectDataStructure(DatabaseObject obj) {
		super(obj);
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.catalogDataID = "";
		this.contactHasSite = "";
	}
	
	public ChemConnectDataStructure(DatabaseObject obj, String descriptionDataDataID) {
		super(obj);
		this.descriptionDataData = descriptionDataDataID;
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.catalogDataID = "";
		this.contactHasSite = "";
	}
	
	public ChemConnectDataStructure(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.catalogDataID = "";
		this.contactHasSite = "";
	}

	public ChemConnectDataStructure(DatabaseObject obj,
			String descriptionDataData, String dataSetReference, 
			String ChemConnectObjectLink,String catalogDataID,
			String contactHasSite) {
		super(obj);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.chemConnectObjectLink = ChemConnectObjectLink;
		this.catalogDataID = catalogDataID;
		this.contactHasSite = contactHasSite;
	}
	
	public ChemConnectDataStructure(String identifier, String access, String owner, String sourceID,
			String descriptionDataData, String dataSetReference, 
			String ChemConnectObjectLink,String catalogDataID,
			String contactHasSite) {
		super(identifier, access, owner,sourceID);
		this.descriptionDataData = descriptionDataData;
		this.dataSetReference = dataSetReference;
		this.chemConnectObjectLink = ChemConnectObjectLink;
		this.catalogDataID = catalogDataID;
		this.contactHasSite = contactHasSite;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ChemConnectDataStructure datastructure = (ChemConnectDataStructure) object;
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
		this.catalogDataID = datastructure.getCatalogDataID();
		this.contactHasSite = datastructure.getContactHasSite();
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
	
	public String getCatalogDataID() {
		return catalogDataID;
	}
	
	public String getContactHasSite() {
		return contactHasSite;
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
		builder.append(prefix + "Catalog ID: ");
		builder.append(catalogDataID);
		builder.append("\n");
		return builder.toString();
	}
}
