package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class Consortium extends ChemConnectDataStructure {
	
	@Unindex
	HashSet<String> DatabaseUserIDReadAccess;
	@Unindex
	HashSet<String> DatabaseUserIDWriteAccess;
	@Unindex
	HashSet<String> DataSetCatalogID;
	@Unindex
	HashSet<String> OrganizationID;
	
	public Consortium() {
		super();
		DatabaseUserIDReadAccess = new HashSet<String>();
		DatabaseUserIDWriteAccess = new HashSet<String>();
		DataSetCatalogID = new HashSet<String>();
		OrganizationID = new HashSet<String>();
	}
	
	public Consortium(String identifier, String sourceID) {
		super(identifier, sourceID);
		DatabaseUserIDReadAccess = new HashSet<String>();
		DatabaseUserIDWriteAccess = new HashSet<String>();
		DataSetCatalogID = new HashSet<String>();
		OrganizationID = new HashSet<String>();
	}
	
	public Consortium(ChemConnectDataStructure datastructure,
			HashSet<String> databaseUserIDReadAccess, 
			HashSet<String> databaseUserIDWriteAccess,
			HashSet<String> dataSetCatalogID, 
			HashSet<String>  organizationID) {
		super(datastructure);
		DatabaseUserIDReadAccess = databaseUserIDReadAccess;
		DatabaseUserIDWriteAccess = databaseUserIDWriteAccess;
		DataSetCatalogID = dataSetCatalogID;
		OrganizationID = organizationID;
	}

	public void fill(ChemConnectDataStructure datastructure,
			HashSet<String> databaseUserIDReadAccess, 
			HashSet<String> databaseUserIDWriteAccess,
			HashSet<String> dataSetCatalogID, 
			HashSet<String>  organizationID) {
		super.fill(datastructure);
		DatabaseUserIDReadAccess = databaseUserIDReadAccess;
		DatabaseUserIDWriteAccess = databaseUserIDWriteAccess;
		DataSetCatalogID = dataSetCatalogID;
		OrganizationID = organizationID;
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
}
