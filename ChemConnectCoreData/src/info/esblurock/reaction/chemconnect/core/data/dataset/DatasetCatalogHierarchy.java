package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;


@Entity
@SuppressWarnings("serial")
public class DatasetCatalogHierarchy extends ChemConnectDataStructure {
	
	public static String createFullCatalogName(String base, String simpleCatalogName) {
		return base + "-" + simpleCatalogName;
	}
	public DatasetCatalogHierarchy() {
		super();
	}

	public DatasetCatalogHierarchy(ChemConnectDataStructure datastructure) {
		super(datastructure);
	}
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix);
		//build.append("Catalog name: " + simpleCatalogName);
		return build.toString();
	}	
	
}
