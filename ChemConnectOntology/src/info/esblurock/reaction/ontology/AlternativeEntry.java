package info.esblurock.reaction.ontology;

public abstract class AlternativeEntry {
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
