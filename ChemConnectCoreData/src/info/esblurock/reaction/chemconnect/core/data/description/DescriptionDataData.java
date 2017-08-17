package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.Date;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

@SuppressWarnings("serial")
@Subclass(index=true)
public class DescriptionDataData extends DatabaseObject {
	@Unindex
	String onlinedescription;

	@Unindex
	String fulldescription;

	@Index
	String sourcekey;
	
	@Index
	Date sourceDate;

	@Index
	String inputkey;

	@Index
	String dataType;
	
	@Unindex
	HashSet<String> keywords;

	/**
	 * @param keyword:  The keyword name of the object
	 * @param onlinedescription A one line description of the object
	 * @param fulldescription A longer description of the object
	 * @param sourcekey The data set (organization and catalog) associated with the data object
	 * @param sourceDate The source data associated with the object 
	 * @param inputkey The key of who inputted the data
	 * @param dataType The data type of the object
	 * @param keywords The set of keywords associated with the 
	 */
	public DescriptionDataData(String keyword, String onlinedescription, String fulldescription, String sourcekey,
			Date sourceDate, String inputkey, String dataType, HashSet<String> keywords) {
		super(keyword);
		this.onlinedescription = onlinedescription;
		this.fulldescription = fulldescription;
		this.sourcekey = sourcekey;
		this.sourceDate = sourceDate;
		this.inputkey = inputkey;
		this.dataType = dataType;
		this.keywords = keywords;
	}

	public String getOnlinedescription() {
		return onlinedescription;
	}

	public String getFulldescription() {
		return fulldescription;
	}

	public String getSourcekey() {
		return sourcekey;
	}

	public Date getSourceDate() {
		return sourceDate;
	}

	public String getInputkey() {
		return inputkey;
	}

	public String getDataType() {
		return dataType;
	}

	public HashSet<String> getKeywords() {
		return keywords;
	}
	

}
