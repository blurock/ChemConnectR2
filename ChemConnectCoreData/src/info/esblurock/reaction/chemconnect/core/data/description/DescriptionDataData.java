package info.esblurock.reaction.chemconnect.core.data.description;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

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
	String sourceKey;
	@Index
	Date sourceDate;
	@Index
	String dataType;
	@Unindex
	String keywords;

	
	public DescriptionDataData() {
		this.onlinedescription = "";
		this.descriptionAbstract = "";
		this.sourceConceptID = "";
		this.sourceDate = new Date();
		this.dataType = "";
		this.keywords = "";
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
			String onlinedescription, String fulldescription, 
			String purposeConcept, Date sourceDate, String dataType,
			String keywords) {
		this.fill(compound,onlinedescription, fulldescription, purposeConcept, sourceDate, dataType,
			keywords);
	}
	public DescriptionDataData(String identifier, String owner, String access, String sourceID,
			String onlinedescription, String fulldescription, 
			String purposeConcept, Date sourceDate, String dataType,
			String keywords) {
		this.fill(identifier,owner,access,sourceID,onlinedescription, fulldescription, 
				purposeConcept, sourceDate, dataType,
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

	public void fill(String identifier, String owner, String access, String sourceID, 
			String onlinedescription, String fulldescription, 
			String sourceConceptID, Date sourceDate, String dataType, 
			String keywords) {
		super.fill(identifier, owner, access, sourceID);
		this.onlinedescription = onlinedescription;
		this.descriptionAbstract = fulldescription;
		this.sourceConceptID = sourceConceptID;
		this.sourceDate = sourceDate;
		this.dataType = dataType;
		this.keywords = keywords;
	}
	public void fill(ChemConnectCompoundDataStructure compound, 
			String onlinedescription, String fulldescription, 
			String sourceConceptID, Date sourceDate, String dataType, 
			String keywords) {
		super.fill(compound);
		this.onlinedescription = onlinedescription;
		this.descriptionAbstract = fulldescription;
		this.sourceConceptID = sourceConceptID;
		this.sourceDate = sourceDate;
		this.dataType = dataType;
		this.keywords = keywords;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		DescriptionDataData descr = (DescriptionDataData) object;
		this.onlinedescription = descr.getOnlinedescription();
		this.descriptionAbstract = descr.getDescriptionAbstract();
		this.sourceConceptID = descr.getSourceConcept();
		this.sourceDate = descr.getSourceDate();
		this.dataType = descr.getDataType();
		this.keywords = descr.getKeywords();
	}

	public String getOnlinedescription() {
		return onlinedescription;
	}

	public String getFulldescription() {
		return descriptionAbstract;
	}

	public String getSourceConcept() {
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
	public void setSourceConceptID(String sourceConceptID) {
		this.sourceConceptID = sourceConceptID;
	}
	public String getDescriptionAbstract() {
		return descriptionAbstract;
	}
	
	public void setDescriptionAbstract(String descriptionAbstract) {
		this.descriptionAbstract = descriptionAbstract;
	}
	public void setOnlinedescription(String onlinedescription) {
		this.onlinedescription = onlinedescription;
	}
	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
		builder.append("Source: " + sourceConceptID + "\n");
		builder.append(prefix);
		builder.append("Type: ");
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
