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

	public DataCatalogID(ChemConnectCompoundDataStructure object, String catalogBaseName, String dataCatalog,
			String simpleCatalogName, ArrayList<String> path) {
		super(object);
		this.CatalogBaseName = catalogBaseName;
		this.DataCatalog = dataCatalog;
		this.SimpleCatalogName = simpleCatalogName;
		this.path = path;		
	}

	public void localFill(DataCatalogID catid) {
		this.CatalogBaseName = catid.getCatalogBaseName();
		this.DataCatalog = catid.getDataCatalog();
		this.SimpleCatalogName = catid.getSimpleCatalogName();
		this.path = catid.getPath();		
	}
	public String getCatalogBaseName() {
		return CatalogBaseName;
	}

	public void setCatalogBaseName(String catalogBaseName) {
		this.CatalogBaseName = catalogBaseName;
	}

	public String getDataCatalog() {
		return DataCatalog;
	}

	public void setDataCatalog(String dataCatalog) {
		this.DataCatalog = dataCatalog;
	}

	public String getSimpleCatalogName() {
		return SimpleCatalogName;
	}

	public void setSimpleCatalogName(String simpleCatalogName) {
		this.SimpleCatalogName = simpleCatalogName;
	}

	public String getFullName() {
		return getFullName("-");
	}

	public void setPath(ArrayList<String> path) {
		this.path = path;
	}

	public String getFullName(String delimitor) {
		StringBuilder build = new StringBuilder();
		if (path != null) {
			for (String pathelement : path) {
				build.append(pathelement);
				build.append(delimitor);
			}
		}
		String catalogpath = CatalogBaseName.replace("-", "/");
		build.append(catalogpath);
		build.append(delimitor);

		if (DataCatalog != null) {
			if (DataCatalog.length() > 0) {
				build.append(ChemConnectCompoundDataStructure.removeNamespace(DataCatalog));
				build.append(delimitor);
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

	public String blobFilenameFromCatalogID(String extension) {
		String filename = getFullName("-") + "." + extension;
		return filename;
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Path        : " + path + "\n");
		build.append(prefix + "Base        : " + CatalogBaseName + "\n");
		build.append(prefix + "Catagory    : " + DataCatalog + "\n");
		build.append(prefix + "Simple name : " + SimpleCatalogName + "\n");
		return build.toString();
	}

}
