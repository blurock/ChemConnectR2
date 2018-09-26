package info.esblurock.reaction.chemconnect.core.client.concepts;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;

public class ChooseFromConceptHierarchyFromDefinition extends ChooseFromConceptHierarchies {

	public ChooseFromConceptHierarchyFromDefinition(ArrayList<String> choices, ChooseFromConceptHeirarchy chosen) {
		super(choices, chosen);
	}
	@Override
	public void treeHierarchyCall(String topconcept) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ConceptHierarchyCallback callback = new ConceptHierarchyCallback(this);
		async.hierarchyFromPrimitiveStructure(topconcept,callback);
	}
	
}
