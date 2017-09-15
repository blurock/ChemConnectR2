package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class OrganizationDescription extends DatabaseObject {
	
	@Index
	String OrganizationUnit;
	@Index
	String OrganizationClassification;
	@Index
	String OrganizationName;
	@Index
	String SubOrganizationOf;

	public OrganizationDescription() {
	}
	
	public OrganizationDescription(String identifier, String access, String owner, String sourceID,
			String organizationUnit, String organizationClassification, 
			String organizationName, String subOrganizationOf) {
		super(identifier,access,owner,sourceID);
		OrganizationUnit = organizationUnit;
		OrganizationClassification = organizationClassification;
		OrganizationName = organizationName;
		SubOrganizationOf = subOrganizationOf;
	}

	public String getOrganizationUnit() {
		return OrganizationUnit;
	}

	public String getOrganizationClassification() {
		return OrganizationClassification;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public String getSubOrganizationOf() {
		return SubOrganizationOf;
	}
	
}
