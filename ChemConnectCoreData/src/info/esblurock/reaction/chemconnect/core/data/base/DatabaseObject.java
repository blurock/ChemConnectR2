package info.esblurock.reaction.chemconnect.core.data.base;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

/** This is the base class for all data objects in the database.
 * ALL classes are derived from this one.
 * 
 * In searching, the access is used to determine whether the data is accessible to the user.
 * 
 * 'owner' represents who inputted the data and who can give access rights. 
 * It can be used as a cleanup measure
 * @author edwardblurock
 */
@Entity
public class DatabaseObject  implements Serializable {
	 private static final long serialVersionUID = 1L;
	
	@Id  Long key;
	@Index  String identifier;
	@Index  String access;
	@Index  String sourceID;
	@Index  Date creationDate;
	@Index String owner;
	
	/** Empty constructor
	 *  fills with current date and public access and owner
	 */
	public DatabaseObject() {
		fill(null,MetaDataKeywords.publicAccess,MetaDataKeywords.publicOwner,"");
	}
	
	/**
	 * @param id The identifier of the data object
	 */
	public DatabaseObject(String id,String sourceID) {
		fill(id,MetaDataKeywords.publicAccess,MetaDataKeywords.publicOwner,sourceID);
	}

	/** Database object with access and owner
	 * @param access Access keyword (username, group or 'Public')
	 * @param owner The owner username who created the data
	 * 
	 * Current date (and null for key)
	 */
	public DatabaseObject(String id, String access, String owner,String sourceID) {
		fill(id,access,owner,sourceID);
	}
	
	public DatabaseObject(DatabaseObject obj) {
		fill(obj.getIdentifier(),obj.getAccess(),obj.getOwner(),obj.getSourceID());
	}
	public void fill(DatabaseObject obj) {
		fill(obj.getIdentifier(),obj.getAccess(),obj.getOwner(),obj.getSourceID());
	}
	
	public void fill(String id, String access, String owner,String sourceID) {
		this.sourceID = sourceID;
		this.identifier = id;
		this.access = access;
		this.owner = owner;
		creationDate = new Date();
		key = null;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getKey() {
		return key;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getIdentifier() {
		return identifier;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	@Override
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(this.getClass().getSimpleName() + ": " + identifier + "\n");
		builder.append(prefix + "(");
		builder.append(access);
		builder.append(", ");
		builder.append(sourceID);
		builder.append(", ");
		builder.append(owner);
		builder.append(", ");
		builder.append(creationDate);
		builder.append(")\n");
		
		return builder.toString();
	}
	
}
