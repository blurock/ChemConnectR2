package info.esblurock.reaction.io.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.data.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.data.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.data.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.TransferDatabaseCatalogHierarchy;
import info.esblurock.reaction.io.db.QueryBase;

public class ReadWriteDatabaseCatalog {

	public static String createCatalogName(String base, String suffix ) {
		return base + "-" + suffix;
	}
	/* This retrieves the DatasetCatalogHierarchy for the user (username) and put them in a tree
	 * 
	 * @param username  The user name
	 * @return The DatasetCatalogHierarchy for the user (in TransferDatabaseCatalogHierarchy)
	 * @throws IOException  If something goes wrong... shouldn't happen
	 * 
	 * Only the links of DatasetCatalogHierarchy are read in, not other underlying data (description and references)
	 * 
	 *  1. Query for all DatasetCatalogHierarchy that belongs to user (username)
	 *  2. For each DatasetCatalogHierarchy found:
	 *  2.1  Add to list (which will be transfer.setCatalogElements)
	 *  2.2  Add links of catalog to totallist (totalinks.addAll(links) ... transfer.setListedLinks(totallist))
	 *  3. After loop, read in from database all the catalog links (getCatalogDataObjectLinks)
	 *  4. Using the links, create the catalog hierarchy (createCatalogHierarchy(transfer))
	 */
	public static TransferDatabaseCatalogHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException {
		System.out.println("getUserDatasetCatalogHierarchy: username" + username); 
		
		TransferDatabaseCatalogHierarchy transfer = new TransferDatabaseCatalogHierarchy();
		SetOfQueryPropertyValues props = new SetOfQueryPropertyValues();
		QueryPropertyValue prop1 = new QueryPropertyValue("owner", username);
		props.add(prop1);
		QuerySetupBase query = new QuerySetupBase(DatasetCatalogHierarchy.class.getCanonicalName(),props);
		HashSet<String> totallinks = new HashSet<String>();
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			List<DatabaseObject> lst = result.getResults();
			List<DatasetCatalogHierarchy> ans = new ArrayList<DatasetCatalogHierarchy>();
			for(DatabaseObject obj : lst) {
				DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) obj;
				ans.add(catalog);
				HashSet<String> links = catalog.getChemConnectObjectLink();
				totallinks.addAll(links);
			}
			transfer.setUsername(username);
			transfer.setCatalogElements(ans);
			transfer.setListedLinks(totallinks);;
			getCatalogDataObjectLinks(transfer);
			createCatalogHierarchy(transfer);
		} catch (ClassNotFoundException e) {
			throw new IOException("Can't retrieve catalog hierarchy for user:\n" + e.toString());
		}
		return transfer;
		}
/*
 *  1. Read in all the links of the user that have the concept 'dataset:ChemConnectConceptSubCatalog' 
 *                     (MetaDataKeywords.linkSubCatalog)
 *  3. Get all the links that have been found for all the hierarchy (transfer.getListedLinks())
 *  3. For each link found:
 *  3.1  Add to set of links   (after transfer.setObjectLinks(ans))
 *  3.2  Remove this link id from the total list of links (at the end this list will be all non catalog links)
 *  
 */
	static void getCatalogDataObjectLinks(TransferDatabaseCatalogHierarchy transfer) throws IOException {
		SetOfQueryPropertyValues props = new SetOfQueryPropertyValues();
		QueryPropertyValue prop1 = new QueryPropertyValue("owner", transfer.getUsername());
		QueryPropertyValue prop2 = new QueryPropertyValue("linkConcept", MetaDataKeywords.linkSubCatalog);
		props.add(prop1);
		props.add(prop2);
		QuerySetupBase query = new QuerySetupBase(DataObjectLink.class.getCanonicalName(),props);
		List<DataObjectLink> ans = new ArrayList<DataObjectLink>();
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			List<DatabaseObject> lst = result.getResults();
			Set<String> noncataloglinks = transfer.getListedLinks();
			for(DatabaseObject obj : lst) {
				DataObjectLink link = (DataObjectLink) obj;
				ans.add(link);
				noncataloglinks.remove(link.getIdentifier());
			}
			transfer.setNonCatalogLinks(noncataloglinks);
			transfer.setObjectLinks(ans);
			System.out.println("getCatalogDataObjectLinks: " + ans.toString());
		} catch (ClassNotFoundException e) {
			throw new IOException("Can't retrieve catalog hierarchy for user:\n" + e.toString());
		}
	}
	/*
	 * 1. Create the top catagory id with username and "Catalog" (createFullCatalogName("Catalog", username);)
	 * 2. Create a map of the id to catalog elements (createCatalogMap)
	 * 3. Create a map of the link ids to links (createDataObjectLinkgMap)
	 * 4. Start with the top element  (catalogmap.get(topCatalog)
	 * 5. Recursive call (createHierarchy)
	 * 6. Put the tree into transfer (transfer.setTop(top);)
	 * 
	 */
	static void createCatalogHierarchy(TransferDatabaseCatalogHierarchy transfer) {
		String username = transfer.getUsername();
		String topCatalog = DatasetCatalogHierarchy.createFullCatalogName("Catalog", username);
		Map<String, DatasetCatalogHierarchy> catalogmap = createCatalogMap(transfer.getCatalogElements());
		Map<String, DataObjectLink> linkmap = createDataObjectLinkgMap(transfer.getObjectLinks());
		DatasetCatalogHierarchy user = catalogmap.get(topCatalog);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(user);
		createHierarchy(top,catalogmap,linkmap);
		transfer.setTop(top);
	}
	/*  Recursive call through tree using links
	 * 1. Get the current node (top.getObject())
	 * 2. For each link of the current node:
	 * 2.1 If link is non-null (if null, the link is not to a catalog member)
	 * 2.1.1 Get the catalog id
	 * 2.1.2 Retrieve subnode using map (catalogmap.get(sublink);)
	 * 2.1.3 Add as subnode of current node
	 * 2.1.4 Recursive call (createHierarchy)
	 */
	static void createHierarchy(DatabaseObjectHierarchy top,
			Map<String, DatasetCatalogHierarchy> catalogmap, Map<String, DataObjectLink> linkmap) {
		DatasetCatalogHierarchy obj = (DatasetCatalogHierarchy) top.getObject();
		for(String sub : obj.getChemConnectObjectLink()) {
			DataObjectLink link = linkmap.get(sub);
			if(link != null) {
				String sublink = link.getDataStructure();
				DatasetCatalogHierarchy subcatalog = catalogmap.get(sublink);
				DatabaseObjectHierarchy subhierarchy = new DatabaseObjectHierarchy(subcatalog);
				top.addSubobject(subhierarchy);
				createHierarchy(subhierarchy,catalogmap,linkmap);
			}
		}

	}
	static Map<String, DatasetCatalogHierarchy> createCatalogMap(List<DatasetCatalogHierarchy> lst) {
		Map<String, DatasetCatalogHierarchy> mapping = new HashMap<String, DatasetCatalogHierarchy>();
		for(DatasetCatalogHierarchy catalog: lst) {
			mapping.put(catalog.getIdentifier(), catalog);
		}
		
		return mapping;
	}
	static Map<String, DataObjectLink> createDataObjectLinkgMap(List<DataObjectLink> lst) {
		Map<String, DataObjectLink> mapping = new HashMap<String, DataObjectLink>();
		for(DataObjectLink catalog: lst) {
			mapping.put(catalog.getIdentifier(), catalog);
		}
		return mapping;
	}
	
	
	
	
}
