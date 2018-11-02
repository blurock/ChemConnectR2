package info.esblurock.reaction.core.server.db.extract;

import java.io.IOException;
import java.util.Iterator;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ExtractLinkObjectFromStructure {

	public static DatabaseObjectHierarchy extract(DatabaseObjectHierarchy hierarchy, String linktypeid) throws IOException {
		DatabaseObjectHierarchy objecthierarchy = null;
		
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy linkhier = hierarchy.getSubObject(structure.getChemConnectObjectLink());
		Iterator<DatabaseObjectHierarchy> iter = linkhier.getSubobjects().iterator();
		boolean notfound = true;
		String id = null;
		while(iter.hasNext() && notfound) {
			DatabaseObjectHierarchy subhier = iter.next();
			DataObjectLink link = (DataObjectLink) subhier.getObject();
			if(link.getLinkConcept().compareTo(linktypeid) == 0) {
				notfound = false;
				id = link.getDataStructure();
			}
		}
		if(id != null) {
			String structuretype = ConceptParsing.findObjectTypeFromLinkConcept(linktypeid);
			if(structuretype != null) {
				objecthierarchy = ExtractCatalogInformation.getCatalogObject(id, structuretype);
			} else {
				throw new IOException("Link structure type='" + linktypeid + "' not found");
				
			}
		} else {
			throw new IOException("Link structure (type=" + linktypeid + ") not found in links");
		}
		
		return objecthierarchy;
	}
}
