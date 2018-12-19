package info.esblurock.reaction.chemconnect.core.data.contact;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class NameOfPerson extends ChemConnectCompoundDataStructure {

	@Index
	String title;
	@Index
	String givenName;
	@Index
	String familyName;
	
	public NameOfPerson() {
		this.title = "";
		this.givenName = "";
		this.familyName = "";
	}
	
	public NameOfPerson(NameOfPerson person) {
		super(person);
		this.title = person.getTitle();
		this.givenName = person.getGivenName();
		this.familyName = person.getFamilyName();
	}
	
	public NameOfPerson(String identifier, String sourceID) {
		super(identifier,sourceID);
		this.title = "";
		this.givenName = "";
		this.familyName = "";		
	}
	
	public NameOfPerson(ChemConnectCompoundDataStructure structure,
			String title, String givenName, String familyName) {
		this.fill(structure,title,givenName,familyName);
	}

	public void fill(ChemConnectCompoundDataStructure structure,
			String title, String givenName, String familyName) {
		super.fill(structure);
		this.title = title;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		NameOfPerson name = (NameOfPerson) object;
		this.title = name.getTitle();
		this.givenName = name.getGivenName();
		this.familyName = name.getFamilyName();
	}
	
	public String getTitle() {
		return title;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String nameAsString() {
		StringBuilder build = new StringBuilder();
		build.append(fillText(title,"Prof.,Dr.,Mr.,Ms...",""));
		build.append(fillText(givenName+" ","Name ",""));
		build.append(fillText(familyName,"Input Family Name ","FamilyName"));
		return build.toString();
		
	}
	private String fillText(String element, String nullstring, String defaultString) {
		String text = element;
		if(element == null) {
			text = nullstring;
		} else {
			if(element.trim().length() == 0) {
				text = defaultString;
			}
		}
		return text;
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);	
		build.append(title);
		build.append(" ");
		build.append(givenName);
		build.append(" ");
		build.append(familyName);
		build.append("\n");
		return build.toString();
	}
	
	
}
