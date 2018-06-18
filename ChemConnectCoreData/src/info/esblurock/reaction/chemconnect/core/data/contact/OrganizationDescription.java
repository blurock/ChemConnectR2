package info.esblurock.reaction.chemconnect.core.data.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class OrganizationDescription extends ChemConnectCompoundDataStructure {
	
	@Index
	String OrganizationUnit;
	@Index
	String OrganizationClassification;
	@Index
	String OrganizationName;
	@Index
	String SubOrganizationOf;

	public OrganizationDescription() {
		this.OrganizationUnit = "";
		this.OrganizationClassification = "";
		this.OrganizationName = "";
		this.SubOrganizationOf = "";
	}
	
	public OrganizationDescription(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.OrganizationUnit = "";
		this.OrganizationClassification = "";
		this.OrganizationName = "";
		this.SubOrganizationOf = "";
	}
	
	public OrganizationDescription(ChemConnectCompoundDataStructure compound,
			String organizationUnit, String organizationClassification, 
			String organizationName, String subOrganizationOf) {
		super(compound);
		this.OrganizationUnit = organizationUnit;
		this.OrganizationClassification = organizationClassification;
		this.OrganizationName = organizationName;
		this.SubOrganizationOf = subOrganizationOf;
	}
	
	
	public OrganizationDescription(String identifier, String access, String owner, String sourceID,
					String organizationUnit, String organizationClassification, 
					String organizationName, String subOrganizationOf) {
		this.fill(identifier,access,owner,sourceID, organizationUnit, organizationClassification, 
						organizationName, subOrganizationOf);
	}
	public void fill(String identifier, String access, String owner, String sourceID,
			String organizationUnit, String organizationClassification, 
			String organizationName, String subOrganizationOf) {
		super.fill(identifier,access,owner,sourceID);
		this.OrganizationUnit = organizationUnit;
		this.OrganizationClassification = organizationClassification;
		this.OrganizationName = organizationName;
		this.SubOrganizationOf = subOrganizationOf;
}
	public void fill(DatabaseObject object) {
		super.fill(object);
		OrganizationDescription descr = (OrganizationDescription) object;
		this.OrganizationUnit = descr.getOrganizationUnit();
		this.OrganizationClassification = descr.getOrganizationClassification();
		this.OrganizationName = descr.getOrganizationName();
		this.SubOrganizationOf = descr.getSubOrganizationOf();
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
	
	
	
	public void setOrganizationUnit(String organizationUnit) {
		OrganizationUnit = organizationUnit;
	}

	public void setOrganizationClassification(String organizationClassification) {
		OrganizationClassification = organizationClassification;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public void setSubOrganizationOf(String subOrganizationOf) {
		SubOrganizationOf = subOrganizationOf;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Name      : " + OrganizationName + "\n");
		builder.append(prefix + "Unit      : " + OrganizationUnit + "\n");
		builder.append(prefix + "Belongs to: " + SubOrganizationOf + "\n");
		builder.append(prefix + "Type      : " + OrganizationClassification + "\n");
		return builder.toString();
	}	
}
