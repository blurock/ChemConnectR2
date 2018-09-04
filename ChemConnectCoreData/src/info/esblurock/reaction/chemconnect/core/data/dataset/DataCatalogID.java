package info.esblurock.reaction.chemconnect.core.data.dataset;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

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
	@Unindex
	ArrayList<String> path;
	
	public DataCatalogID() {
	}
	
	public DataCatalogID(ChemConnectCompoundDataStructure object, 
			String catalogBaseName, String dataCatalog, String simpleCatalogName,
			ArrayList<String> path) {
		super(object);
		CatalogBaseName = catalogBaseName;
		DataCatalog = dataCatalog;
		SimpleCatalogName = simpleCatalogName;
		this.path = path;
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
	
	public String getFullName() {
		return getFullName("-");
	}
	public String getFullName(String delimitor) {
		StringBuilder build = new StringBuilder();
		build.append(CatalogBaseName);
		build.append(delimitor);
		if(DataCatalog != null) {
			if(DataCatalog.length() > 0) {
				build.append(ChemConnectCompoundDataStructure.removeNamespace(DataCatalog));
				build.append("-");				
			}
		}
		build.append(SimpleCatalogName);
		return build.toString();
	}
	
	public String toString() {
		return toString("");
	}
	public ArrayList<String> getPath() {
		return path;
	}
	
	public String blobFilenameFromCatalogID() {
		return getFullName("/");
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
