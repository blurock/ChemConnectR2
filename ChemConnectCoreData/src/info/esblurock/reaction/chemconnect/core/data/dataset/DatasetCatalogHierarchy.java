package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class DatasetCatalogHierarchy extends ChemConnectDataStructure {

	public DatasetCatalogHierarchy() {
		super();
	}

	public DatasetCatalogHierarchy(ChemConnectDataStructure datastructure) {
		super(datastructure);
	}

	
}
