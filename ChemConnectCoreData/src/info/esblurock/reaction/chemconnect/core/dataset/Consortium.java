package info.esblurock.reaction.chemconnect.core.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class Consortium extends DatabaseObject {
	
	@Unindex
	HashSet<String> DatabaseUserIDReadAccess;
	@Unindex
	HashSet<String> DatabaseUserIDWriteAccess;
	@Unindex
	HashSet<String> DataSetCatalogID;
	@Unindex
	HashSet<String> OrganizationID;
	@Index
	String  DescriptionID;
	
	public Consortium() {
		super();
		DatabaseUserIDReadAccess = new HashSet<String>();
		DatabaseUserIDWriteAccess = new HashSet<String>();
		DataSetCatalogID = new HashSet<String>();
		OrganizationID = new HashSet<String>();
		DescriptionID = "";
	}
	
	public Consortium(String identifier, String sourceID) {
		super(identifier, sourceID);
		DatabaseUserIDReadAccess = new HashSet<String>();
		DatabaseUserIDWriteAccess = new HashSet<String>();
		DataSetCatalogID = new HashSet<String>();
		OrganizationID = new HashSet<String>();
		DescriptionID = "";
	}
	
	public Consortium(String identifier, String access, String owner, String sourceID,
			HashSet<String> databaseUserIDReadAccess, 
			HashSet<String> databaseUserIDWriteAccess,
			HashSet<String> dataSetCatalogID, 
			HashSet<String>  organizationID, String descriptionID) {
		super(identifier, access, owner,sourceID);
		DatabaseUserIDReadAccess = databaseUserIDReadAccess;
		DatabaseUserIDWriteAccess = databaseUserIDWriteAccess;
		DataSetCatalogID = dataSetCatalogID;
		OrganizationID = organizationID;
		DescriptionID = descriptionID;
	}
	public HashSet<String> getDatabaseUserIDReadAccess() {
		return DatabaseUserIDReadAccess;
	}
	public void setDatabaseUserIDReadAccess(HashSet<String> databaseUserIDReadAccess) {
		DatabaseUserIDReadAccess = databaseUserIDReadAccess;
	}
	public HashSet<String> getDatabaseUserIDWriteAccess() {
		return DatabaseUserIDWriteAccess;
	}
	public void setDatabaseUserIDWriteAccess(HashSet<String> databaseUserIDWriteAccess) {
		DatabaseUserIDWriteAccess = databaseUserIDWriteAccess;
	}
	public HashSet<String> getDataSetCatalogID() {
		return DataSetCatalogID;
	}
	public void setDataSetCatalogID(HashSet<String> dataSetCatalogID) {
		DataSetCatalogID = dataSetCatalogID;
	}
	public HashSet<String> getOrganizationID() {
		return OrganizationID;
	}
	
	public void setOrganizationID(HashSet<String> organizationID) {
		OrganizationID = organizationID;
	}
	public String getDescriptionID() {
		return DescriptionID;
	}
	

}
