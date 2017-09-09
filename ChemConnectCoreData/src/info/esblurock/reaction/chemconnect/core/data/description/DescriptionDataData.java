package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.Date;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class DescriptionDataData extends DatabaseObject {
	@Unindex
	String onlinedescription;

	@Unindex
	String descriptionAbstract;

	@Index
	String sourcekey;
	
	@Index
	Date sourceDate;

	@Index
	String dataType;
	
	@Unindex
	HashSet<String> keywords;

	/**
	 * @param identifier:  The keyword name of the object
	 * @param owner:  The keyword name of the owner
	 * @param access:  The keyword name of the accessibility of the object.
	 * @param onlinedescription A one line description of the object
	 * @param fulldescription A longer description of the object
	 * @param sourcekey The data set (organization and catalog) associated with the data object
	 * @param sourceDate The source data associated with the object 
	 * @param dataType The data type of the object
	 * @param keywords The set of keywords associated with the 
	 */
	public DescriptionDataData(String identifier, String owner, String access, 
			String onlinedescription, String fulldescription, String sourcekey,
			Date sourceDate, String dataType, HashSet<String> keywords) {
		super(identifier,owner,access);
		this.onlinedescription = onlinedescription;
		this.descriptionAbstract = fulldescription;
		this.sourcekey = sourcekey;
		this.sourceDate = sourceDate;
		this.dataType = dataType;
		this.keywords = keywords;
	}

	public String getOnlinedescription() {
		return onlinedescription;
	}

	public String getFulldescription() {
		return descriptionAbstract;
	}

	public String getSourcekey() {
		return sourcekey;
	}

	public Date getSourceDate() {
		return sourceDate;
	}

	public String getDataType() {
		return dataType;
	}

	public HashSet<String> getKeywords() {
		return keywords;
	}
	

}
