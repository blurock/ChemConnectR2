package info.esblurock.reaction.ontology;

public class AlternativeEntryWithAppFiles extends AlternativeEntry {

	@Override
	public String getOwlLocal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRDFSLocal() {
		// TODO Auto-generated method stub
		return null;
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
