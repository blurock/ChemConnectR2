package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;


@Entity
@SuppressWarnings("serial")
public class DatasetCatalogHierarchy extends ChemConnectDataStructure {
	
	public static String createFullCatalogName(String base, String simpleCatalogName) {
		return base + "-" + simpleCatalogName;
	}
	/*
	@Index
	String simpleCatalogName;
*/
	public DatasetCatalogHierarchy() {
		super();
		//simpleCatalogName = "";
	}

	public DatasetCatalogHierarchy(String simpleCatalogName, ChemConnectDataStructure datastructure) {
		super(datastructure);
		//this.simpleCatalogName = simpleCatalogName;
	}
	public DatasetCatalogHierarchy(ChemConnectDataStructure datastructure) {
		super(datastructure);
		//this.simpleCatalogName = "";
	}
/*
	public String getSimpleCatalogName() {
		return simpleCatalogName;
	}
	
	public void setSimpleCatalogName(String simpleCatalogName) {
		this.simpleCatalogName = simpleCatalogName;
	}
*/
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
