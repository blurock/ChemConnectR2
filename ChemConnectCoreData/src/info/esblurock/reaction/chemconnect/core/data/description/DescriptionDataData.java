package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.Date;
import java.util.HashSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class DescriptionDataData extends ChemConnectCompoundDataStructure {
	@Unindex
	String onlinedescription;
	@Unindex
	String descriptionAbstract;
	@Index
	String sourceConceptID;
	@Index
	Date sourceDate;
	@Index
	String dataType;
	@Unindex
	String keywords;

	
	public DescriptionDataData() {
	}
	/**
	 * @param identifier:
	 *            The keyword name of the object
	 * @param owner:
	 *            The keyword name of the owner
	 * @param access:
	 *            The keyword name of the accessibility of the object.
	 * @param onlinedescription
	 *            A one line description of the object
	 * @param fulldescription
	 *            A longer description of the object
	 * @param sourcekey
	 *            The data set (organization and catalog) associated with the data
	 *            object
	 * @param sourceDate
	 *            The source data associated with the object
	 * @param dataType
	 *            The data type of the object
	 * @param keywords
	 *            The set of keywords associated with the
	 */
	public DescriptionDataData(ChemConnectCompoundDataStructure compound,
			String onlinedescription, String fulldescription, String sourcekey, 
			Date sourceDate, String dataType,
			String keywords) {
		fill(compound,onlinedescription, fulldescription, sourcekey, sourceDate, dataType,
			keywords);
	}
	public DescriptionDataData(String identifier, String owner, String access, String sourceID,
			String onlinedescription, String fulldescription, String sourcekey, Date sourceDate, String dataType,
			String keywords) {
		fill(identifier,owner,access,sourceID,onlinedescription, fulldescription, sourcekey, sourceDate, dataType,
			keywords);
	}

	public DescriptionDataData(String identifier, String sourceID) {
		super(identifier, sourceID);
		this.onlinedescription = "";
		this.descriptionAbstract = "";
		this.sourceConceptID = "";
		this.sourceDate = new Date();
		this.dataType = "";
		this.keywords = "";
	}

	public void fill(String identifier, String owner, String access, String sourceID, String onlinedescription,
			String fulldescription, String sourceConceptID, Date sourceDate, String dataType, String keywords) {
		super.fill(identifier, owner, access, sourceID);
		this.onlinedescription = onlinedescription;
		this.descriptionAbstract = fulldescription;
		this.sourceConceptID = sourceConceptID;
		this.sourceDate = sourceDate;
		this.dataType = dataType;
		this.keywords = keywords;
	}
	public void fill(ChemConnectCompoundDataStructure compound, String onlinedescription,
			String fulldescription, String sourceConceptID, Date sourceDate, String dataType, String keywords) {
		super.fill(compound);
		this.onlinedescription = onlinedescription;
		this.descriptionAbstract = fulldescription;
		this.sourceConceptID = sourceConceptID;
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
		return sourceConceptID;
	}

	public Date getSourceDate() {
		return sourceDate;
	}

	public String getDataType() {
		return dataType;
	}

	public String getKeywords() {
		return keywords;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Title: " + onlinedescription);
		builder.append("\n");
		builder.append(prefix);
		builder.append("Source: " + sourceConceptID);
		builder.append(", Type: ");
		builder.append(dataType);
		builder.append("\n");
		abstractType(prefix,descriptionAbstract,builder);
		builder.append(prefix);
		builder.append("Date: " + sourceDate + "\n");		
		builder.append(prefix);
		builder.append("Keywords: " + keywords + "\n");		
		return builder.toString();
	}
	private void abstractType(String prefix, String abstractS, StringBuilder builder) {
		String newprefix = prefix + "\t";
		builder.append(prefix + "Abstract: ");
		String text = abstractS;
		int next = text.indexOf('\n');
		while(next >= 0) {
			String txt = text.substring(0, next);
			builder.append(txt);
			builder.append("\n");
			text = text.substring(next+1);
			next = text.indexOf('\n');
			builder.append(newprefix);
		}
		builder.append(text);
		builder.append("\n");
	}
}
