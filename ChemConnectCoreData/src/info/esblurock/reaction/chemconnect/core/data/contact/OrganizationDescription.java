package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
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
	
	public OrganizationDescription(String identifier, String access, String owner,
			String organizationUnit, String organizationClassification, 
			String organizationName, String subOrganizationOf) {
		super(identifier,access,owner);
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
