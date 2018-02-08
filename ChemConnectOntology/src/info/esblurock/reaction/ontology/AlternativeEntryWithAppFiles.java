package info.esblurock.reaction.ontology;

import java.net.URL;

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
		String path = "info/esblurock/reaction/ontology/resources/unit.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}

	@Override
	public String getQUDTQuantityLocal() {
		String path = "info/esblurock/reaction/ontology/resources/quantity.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getQUDTQudtLocal() {
		String path = "info/esblurock/reaction/ontology/resources/qudt.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}

	@Override
	public String getQUDTDimensionLocal() {
		String path = "info/esblurock/reaction/ontology/resources/dimension.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}

}
