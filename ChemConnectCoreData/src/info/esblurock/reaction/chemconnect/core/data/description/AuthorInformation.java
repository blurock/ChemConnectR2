package info.esblurock.reaction.chemconnect.core.data.description;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

@SuppressWarnings("serial")
@Entity
public class AuthorInformation extends NameOfPerson {
	@Index
	String linkToContact;

	public AuthorInformation() {
	}

	public AuthorInformation(NameOfPerson person, String linkToContact) {
		super(person);
		this.linkToContact = linkToContact;
	}
	public String getLinkToContact() {
		return linkToContact;
	}

	public void setLinkToContact(String linkToContact) {
		this.linkToContact = linkToContact;
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
		build.append("Link: " + linkToContact);
		build.append("\n");
		return build.toString();
	}
	
	
}
