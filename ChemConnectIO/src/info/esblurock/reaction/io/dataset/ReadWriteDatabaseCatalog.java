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

	public static String createUserCatalogName(String username ) {
		return username + "Catalog";
	}
	
	public static TransferDatabaseCatalogHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException {
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
		} catch (ClassNotFoundException e) {
			throw new IOException("Can't retrieve catalog hierarchy for user:\n" + e.toString());
		}
		return transfer;
		}

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
			createCatalogHierarchy(transfer);
		} catch (ClassNotFoundException e) {
			throw new IOException("Can't retrieve catalog hierarchy for user:\n" + e.toString());
		}
	}
	
	static void createCatalogHierarchy(TransferDatabaseCatalogHierarchy transfer) {
		String username = transfer.getUsername();
		String topCatalog = createUserCatalogName(username);
		Map<String, DatasetCatalogHierarchy> catalogmap = createCatalogMap(transfer.getCatalogElements());
		Map<String, DataObjectLink> linkmap = createDataObjectLinkgMap(transfer.getObjectLinks());
		DatasetCatalogHierarchy user = catalogmap.get(topCatalog);
		DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(user);
		System.out.println("createCatalogHierarchy:\n" + top.toString());		
		createHierarchy(top,catalogmap,linkmap);
		System.out.println("createCatalogHierarchy:\n" + top.toString());
		transfer.setTop(top);
	}
	
	static void createHierarchy(DatabaseObjectHierarchy top,
			Map<String, DatasetCatalogHierarchy> catalogmap, Map<String, DataObjectLink> linkmap) {
		System.out.println("createHierarchy:\n" + top);
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
