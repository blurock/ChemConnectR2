package info.esblurock.reaction.chemconnect.core.data.transfer;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
/*
 * 
 * idName:       dataset:ContactLocationInformation: 
 * identifier:   The identifier of the class 
 *                vcard:Location  
 * dataType:     The class with the sub-elements (from Type)
 *               ContactLocationInformation     
 * 
 * :ContactLocationInformation rdfs:isDefinedBy <http://www.w3.org/2006/vcard/ns#Location> ;
                            <http://purl.org/dc/elements/1.1/type> "ContactLocationInformation"^^xsd:string ;
                            rdfs:label "Contact Location Information"^^xsd:string .              
 * 
 * Classification: ID: dataset:ContactLocationInformation(dcat:record): vcard:Location  (ContactLocationInformation)
 * dataType:    ContactLocationInformation
 * idName:      dataset:ContactLocationInformation
 * link:        dcat:record
 * identifier:  vcard:Location
 * 
 * Source of identifier information:
 * ###  http://www.esblurock.info/dataset#ContactLocationInformation
:ContactLocationInformation rdf:type owl:NamedIndividual ;
                            <http://purl.org/dc/terms/identifier> "vcard:Location"^^xsd:string .

 */
public class ClassificationInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	String idName;
	String identifier;
	String dataType;
	String link;
	DatabaseObject top;
	
	public ClassificationInformation() {
	}
	public ClassificationInformation(DatabaseObject top, String link, String idName, String identifier, String dataType) {
		super();
		this.idName = idName;
		this.identifier = identifier;
		this.dataType = dataType;
		this.top = top;
		this.link = link;
	}
	public String getIdName() {
		return idName;
	}
	public String getIdentifier() {
		return identifier;
	}
	public String getDataType() {
		return dataType;
	}
	
	public DatabaseObject getTop() {
		return top;
	}
	
	
	public String getLink() {
		return link;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append("ID: ");
		build.append(idName);
		if(link != null) {
			build.append("(");
			build.append(link);
			build.append(")");
		}
		build.append(": ");
		build.append(identifier);
		build.append("  (");
		build.append(dataType);
		build.append(")");
		
		return build.toString();
	}
	
}
