package info.esblurock.reaction.chemconnect.core.client.concepts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;

public class HierarchyConceptChoice implements ClickHandler {

	String concept;
	ChooseFromConceptHierarchies topstructure;
	
	public HierarchyConceptChoice(String concept, ChooseFromConceptHierarchies topstructure) {
		super();
		this.concept = concept;
		this.topstructure = topstructure;
	}

	@Override
	public void onClick(ClickEvent arg0) {
		topstructure.treeHierarchyCall(concept);
	}

}