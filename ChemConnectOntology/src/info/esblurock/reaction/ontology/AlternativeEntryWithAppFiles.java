package info.esblurock.reaction.ontology;

public class AlternativeEntryWithAppFiles extends AlternativeEntry {

	public String getVcardLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/vcard.ttl";
	}
	public String getDCatLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/dcat.ttl";
	}
	public String getDcTermsLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/dcterms.ttl";
	}
	public String getDCITypeLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/dcterms.ttl";
	}
	public String getProvoLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/prov-o.ttl";
	}
	public String getOrgLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/org.ttl";
	}
	public String getFoafLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/foaf.rdf";
	}
	public String getSKOSLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/skos.rdf";
	}

	@Override
	public String getRDFSLocal() {
		return "classpath:info/esblurock/reaction/ontology/rdf-schema.owl";
	}
	public String getOwlLocal() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRDFLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/22-rdf-syntax-ns.owl";
	}

	public String getDataCiteLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources";
	}
	public String getGEOLocal() {
		return "classpath:info/esblurock/reaction/ontology/resources/wgs84_pos.rdf";
	}

	
	
	
	@Override
	public String getQUDTUnitLocal() {
		return "file:src/info/esblurock/reaction/ontology/resources/unit.owl";
	}

	@Override
	public String getQUDTQuantityLocal() {
		return "file:src/info/esblurock/reaction/ontology/resources/quantity.owl";
	}
	public String getQUDTQudtLocal() {
		return "file:src/info/esblurock/reaction/ontology/resources/qudt.owl";
	}

	@Override
	public String getQUDTDimensionLocal() {
		return "file:src/info/esblurock/reaction/ontology/resources/dimension.owl";
	}

}
