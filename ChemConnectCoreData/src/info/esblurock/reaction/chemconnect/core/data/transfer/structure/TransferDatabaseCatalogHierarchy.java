package info.esblurock.reaction.chemconnect.core.data.transfer.structure;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;

public class TransferDatabaseCatalogHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	String username;
	List<DatasetCatalogHierarchy> catalogElements;
	List<DataObjectLink> objectLinks;
	Set<String> listedLinks;
	Set<String> nonCatalogLinks;
	
	DatabaseObjectHierarchy top;
	
	public TransferDatabaseCatalogHierarchy() {
		username = "";
		catalogElements = null;
		objectLinks = null;
		listedLinks = null;
		nonCatalogLinks = null;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<DatasetCatalogHierarchy> getCatalogElements() {
		return catalogElements;
	}
	public void setCatalogElements(List<DatasetCatalogHierarchy> catalogElements) {
		this.catalogElements = catalogElements;
	}
	public List<DataObjectLink> getObjectLinks() {
		return objectLinks;
	}
	public void setObjectLinks(List<DataObjectLink> objectLinks) {
		this.objectLinks = objectLinks;
	}
	public Set<String> getListedLinks() {
		return listedLinks;
	}
	public void setListedLinks(Set<String> listedLinks) {
		this.listedLinks = listedLinks;
	}
	public Set<String> getNonCatalogLinks() {
		return nonCatalogLinks;
	}
	public void setNonCatalogLinks(Set<String> nonCatalogLinks) {
		this.nonCatalogLinks = nonCatalogLinks;
	}
	
	public DatabaseObjectHierarchy getTop() {
		return top;
	}
	public void setTop(DatabaseObjectHierarchy top) {
		this.top = top;
	}
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		build.append(catalogElements.toString());
		build.append("/n");
		build.append(objectLinks.toString());
		build.append("/n");
		
		
		return build.toString();
	}
}
