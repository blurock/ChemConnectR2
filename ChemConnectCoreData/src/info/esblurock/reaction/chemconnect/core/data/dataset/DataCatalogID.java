package info.esblurock.reaction.chemconnect.core.data.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class DataCatalogID extends ChemConnectCompoundDataStructure {
	
	@Index
	String CatalogBaseName;
	@Index
	String DataCatalog;
	@Index
	String SimpleCatalogName;
	
	public DataCatalogID(ChemConnectCompoundDataStructure object, 
			String catalogBaseName, String dataCatalog, String simpleCatalogName) {
		super(object);
		CatalogBaseName = catalogBaseName;
		DataCatalog = dataCatalog;
		SimpleCatalogName = simpleCatalogName;
	}
	public String getCatalogBaseName() {
		return CatalogBaseName;
	}
	public void setCatalogBaseName(String catalogBaseName) {
		CatalogBaseName = catalogBaseName;
	}
	public String getDataCatalog() {
		return DataCatalog;
	}
	public void setDataCatalog(String dataCatalog) {
		DataCatalog = dataCatalog;
	}
	public String getSimpleCatalogName() {
		return SimpleCatalogName;
	}
	public void setSimpleCatalogName(String simpleCatalogName) {
		SimpleCatalogName = simpleCatalogName;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Base        : " + CatalogBaseName + "\n");
		build.append(prefix + "Catagory    : " + DataCatalog + "\n");
		build.append(prefix + "Simple name : " + SimpleCatalogName + "\n");
		return build.toString();
	}
	
	
}
