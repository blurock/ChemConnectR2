package info.esblurock.reaction.chemconnect.core.data.contact;

import java.util.HashSet;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class UserDescription extends DatabaseObject {

	@Index
	String userRole;

	@Index
	String title;
	
	@Index
	String givenName;
	
	@Index
	String familyName;
	
	@Unindex
	HashSet<String> groups;

	public UserDescription() {
		super();
		this.userRole = null;
		this.title = null;
		this.givenName = null;
		this.familyName = null;
		this.groups = null;
	}
	
	/**
	 * @param id The username of the contact
	 * @param userRole The access level of the user
	 * @param title The title of the user (Dr., Mr. ,...)
	 * @param givenName The first name. Can have initials
	 * @param familyName The last name
	 * @param groups The set of groups this user 
	 */
	public UserDescription(String id, String userRole, String title, String givenName, String familyName,
			HashSet<String> groups) {
		super(id);
		this.userRole = userRole;
		this.title = title;
		this.givenName = givenName;
		this.familyName = familyName;
		this.groups = groups;
	}

	public String getUserRole() {
		return userRole;
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

	public HashSet<String> getGroups() {
		return groups;
	}
	
	
}
