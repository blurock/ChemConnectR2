package info.esblurock.reaction.ontology;

import java.net.URL;

public class AlternativeEntryWithAppFiles extends AlternativeEntry {

	public String getSKOSLocal() {
		String path = "file:info/esblurock/reaction/ontology/resources/skos.rdf";
		//URL url = getClass().getClassLoader().getResource(path);
		//return url.toString();
		return path;
	}
	public String getDataCubleLocal() {
		String path = "info/esblurock/reaction/ontology/resources/cube.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getVcardLocal() {
		String path = "info/esblurock/reaction/ontology/resources/vcard.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getDCatLocal() {
		String path = "info/esblurock/reaction/ontology/resources/dcat.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getElementsLocal() {
		String path = "info/esblurock/reaction/ontology/resources/dcelements.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getSSNLocal() {
		String path = "info/esblurock/reaction/ontology/resources/ssn.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getDcTermsLocal() {
		return "info/esblurock/reaction/ontology/resources/dcterms.ttl";
	}
	public String getDCITypeLocal() {
		return "info/esblurock/reaction/ontology/resources/dcterms.ttl";
	}
	public String getProvoLocal() {
		return "info/esblurock/reaction/ontology/resources/prov-o.ttl";
	}
	public String getOrgLocal() {
		return "info/esblurock/reaction/ontology/resources/org.ttl";
	}
	public String getFoafLocal() {
		String path = "info/esblurock/reaction/ontology/resources/foaf.rdf";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}

	@Override
	public String getRDFSLocal() {
		return "info/esblurock/reaction/ontology/rdf-schema.owl";
	}
	public String getOwlLocal() {
		return null;
	}

	public String getRDFLocal() {
		return "info/esblurock/reaction/ontology/resources/22-rdf-syntax-ns.owl";
	}

	public String getDataCiteLocal() {
		return "info/esblurock/reaction/ontology/resources";
	}
	public String getGEOLocal() {
		return "info/esblurock/reaction/ontology/resources/wgs84_pos.rdf";
	}

	
	
	
	@Override
	public String getQUDTUnitLocal() {
		String path = "info/esblurock/reaction/ontology/resources/unit.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getPhysicsUnitLocal() {
		String path = "info/esblurock/reaction/ontology/resources/VOCAB_QUDT-UNITS-PHYSICAL-CHEMISTRY-AND-MOLECULAR-PHYSICS-v2.0.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}

	@Override
	public String getQUDTQuantityLocal() {
		String path = "info/esblurock/reaction/ontology/resources/quantity.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	
	public String getQUDTOwlLocal() {
		String path = "info/esblurock/reaction/ontology/resources/qudt.owl";
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
	public String getQUDTUnitOwlLocal() {
		String path = "info/esblurock/reaction/ontology/resources/unit.owl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	public String getSOSALocal() {
		String path = "info/esblurock/reaction/ontology/resources/sosa.ttl";
		URL url = getClass().getClassLoader().getResource(path);
		return url.toString();
	}
	
}
