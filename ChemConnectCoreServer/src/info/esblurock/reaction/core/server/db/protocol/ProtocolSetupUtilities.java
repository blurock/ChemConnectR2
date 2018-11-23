package info.esblurock.reaction.core.server.db.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;
import info.esblurock.reaction.core.server.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.ontology.dataset.ConceptParsing;

public class ProtocolSetupUtilities {
	
	public static ProtocolSetupTransfer observationsForProtocol(String protocolS, String user) throws IOException {
		Set<String> measureset = ConceptParsing.setOfObservationsForProtocol(protocolS,true);
		System.out.println(measureset);
		Set<String> dimensionset = ConceptParsing.setOfObservationsForProtocol(protocolS,false);
		System.out.println(dimensionset);

		HashMap<String,HierarchyNode> measurenodes = new HashMap<String,HierarchyNode>();
		for(String measure : measureset) {
			System.out.println("observationsForProtocol: " + measure);
			HierarchyNode measurehier = setUpHiearachyNodeChoicesForParameter(user, measure);
			System.out.println("observationsForProtocol: \n" + measurehier.toString("MeasureNodes: "));			
			measurenodes.put(measure,measurehier);
		}
		HashMap<String,HierarchyNode> dimensionnodes = new HashMap<String,HierarchyNode>();
		for(String dimension : dimensionset) {
			System.out.println("observationsForProtocol: " + dimension);
			HierarchyNode dimensionhier = setUpHiearachyNodeChoicesForParameter(user, dimension);
			System.out.println("observationsForProtocol: \n" + dimensionhier.toString("DimensionNodes: "));			
			dimensionnodes.put(dimension, dimensionhier);
		}
		ProtocolSetupTransfer transfer = new ProtocolSetupTransfer(protocolS, measurenodes,dimensionnodes);
		return transfer;
	}
	public static HierarchyNode setUpHiearachyNodeChoicesForParameter(String user, String parameter) throws IOException {
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogAndUser(user,parameter,MetaDataKeywords.observationCorrespondenceSpecification);
	}

}
