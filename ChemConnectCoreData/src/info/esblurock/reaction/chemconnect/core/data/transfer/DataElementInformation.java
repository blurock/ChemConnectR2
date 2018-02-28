package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;
import java.util.Comparator;

import com.sun.media.jfxmedia.effects.EqualizerBand;
/*
 * 	dataset:GPSLocationID(dcterms:hasPart):  dataset:GPSLocationID  (ID):  single
 * 
 * dataElementName:       dataset:GPSLocationID
 * link:                  dcterms:hasPart
 * identifier:            dataset:GPSLocationID
 * chemconnectStructure:  ID
 * 
 * 
 */
public class DataElementInformation implements Serializable,Comparable<DataElementInformation> {
	
	private static final long serialVersionUID = 1L;

	String dataElementName;
	boolean singlet;
	int numberOfElements;
	String chemconnectStructure;
	String identifier;
	String link;
	String suffix;

	public DataElementInformation() {
	}
	public DataElementInformation(String dataElementName, String link, boolean singlet, int numberOfElements, 
			String chemconnectStructure, String identifier, String suffix) {
		super();
		this.dataElementName = dataElementName;
		this.singlet = singlet;
		this.numberOfElements = numberOfElements;
		this.chemconnectStructure = chemconnectStructure;
		this.identifier = identifier;
		this.link = link;
		this.suffix = suffix;
	}
	public String getDataElementName() {
		return dataElementName;
	}
	public boolean isSinglet() {
		return singlet;
	}
	public int numberOfElements() {
		return numberOfElements;
	}
	public String getChemconnectStructure() {
		return chemconnectStructure;
	}
	public String getIdentifier() {
		return identifier;
	}
	public boolean isID() {
		return isChemConnectType("ID");
	}
	public boolean isChemConnectType(String typename) {
		return chemconnectStructure.compareTo(typename) == 0;
	}
	public boolean isDataElementType(String typename) {
		return dataElementName.compareTo(typename) == 0;
	}
	public boolean isKeywords() {
		return isChemConnectType("Keywords");
	}
	
	public String getLink() {
		return link;
	}	
	
	public void setLink(String link) {
		this.link = link;
	}
	public String getSuffix() {
		return suffix;
	}
	public String toString() {
		return toString("");
	}
		public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + dataElementName);
		if(link != null) {
			build.append("(");
			build.append(link);
			build.append("):  ");
		} else {
			build.append(":  ");
		}
		build.append(identifier);
		build.append("   ");
		build.append(suffix);
		
		build.append("  (" + chemconnectStructure + "):  ");
		if(singlet) {
			build.append("single");
		} else {
			build.append("multiple");
		} 
		if(numberOfElements > 1) {
			build.append("# " + numberOfElements);
		}
		return build.toString();
	}
		@Override
		public int compareTo(DataElementInformation o) {
			return dataElementName.compareTo(o.getDataElementName());
		}
		@Override
		public boolean equals(Object o) {
			boolean ans = false;
			if(o instanceof DataElementInformation) {
				DataElementInformation element = (DataElementInformation) o;
				if(o != null) {
					ans = dataElementName.equals(element.getDataElementName());
				}
			}
			
			return ans;
		}
		@Override public int hashCode() {
	        return dataElementName.hashCode();
		}
}
