package info.esblurock.reaction.ontology;

public abstract class AlternativeEntry {
	
	public String getDataCubeURL() {
		return "http://purl.org/linked-data/cube";
	}
	public String getVcardURL() {
		return "http://www.w3.org/2006/vcard/ns";
	}
	public String getDCatURL() {
		return "http://www.w3.org/ns/dcat";
	}	
	public String getDctermsURL() {
		return "http://purl.org/dc/terms/";
	}
	public String getDCITypeURL() {
		return "http://purl.org/dc/dcmitype";
	}
	public String getProvoURL() {
		return "http://www.w3.org/ns/prov-o-20130430";
	}
	public String getOrgURL() {
		return "http://www.w3.org/ns/org#";
	}
	public String getFoafURL() {
		return "http://xmlns.com/foaf/0.1/";
	}
	public String getSKOSURL() {
		return "http://xmlns.com/foaf/0.1/";
	}
	public String getDataCiteURL() {
		return "http://purl.org/spar/datacite/";
	}
	public String getGEOURL() {
		return "http://www.w3.org/2003/01/geo/wgs84_pos";
	}
	public String getDcTermsURL() {
		return "http://purl.org/dc/terms/";
	}
	
	public String getOwlURL() {
		return "http://www.w3.org/2002/07/owl#";
	}
	abstract public String getOwlLocal();
	public String getRDFSURL() {
		return "http://www.w3.org/2000/01/rdf-schema";
	}
	abstract public String getRDFSLocal();
	public String getXMLSchema() {
		return "http://www.w3.org/2001/XMLSchema";
	}
	
	public String getQUDTUnit() {
		return "http://data.nasa.gov/qudt/owl/unit";
	}
	abstract public String getQUDTUnitLocal();
	public String getQUDTUnitPrefix() {
		return "unit:";
	}
	public String getPhysicsUnit() {
		return "http://qudt.org/2.0/vocab/VOCAB_QUDT-UNITS-PHYSICAL-CHEMISTRY-AND-MOLECULAR-PHYSICS-v2.0.ttl";
	}
	abstract public String getPhysicsUnitLocal();
	public String getPhysicsUnitPrefix() {
		return "unit:";
	}
	
	public String getQUDTQuantity() {
		return "http://data.nasa.gov/qudt/owl/quantity";
	}
	abstract public String getQUDTQuantityLocal();
	
	public String getQUDTQudt() {
		return "http://data.nasa.gov/qudt/owl/qudt";
	}
	abstract public String getQUDTQudtLocal();
	public String getQUDTDimension() {
		return "http://data.qudt.org/qudt/owl/1.0.0/dimension.owl";
	}
	abstract public String getQUDTDimensionLocal();
}
