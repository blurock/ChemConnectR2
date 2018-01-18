package info.esblurock.reaction.chemconnect.core.client.graph.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.graph.rdf.ForceGraphPanel;
import info.esblurock.reaction.chemconnect.core.client.graph.rdf.GraphSetOfKeywordRDFs;
import info.esblurock.reaction.chemconnect.core.client.graph.rdf.HierarchialConceptSelection;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class VisualizeGraphicalObjects extends Composite implements HasText {

	private static VisualizeGraphicalObjectsUiBinder uiBinder = GWT.create(VisualizeGraphicalObjectsUiBinder.class);

	interface VisualizeGraphicalObjectsUiBinder extends UiBinder<Widget, VisualizeGraphicalObjects> {
	}

	
	
	
	@UiField
	MaterialLink deviceConcepts;
	@UiField
	MaterialLink subsystemConcepts;
	@UiField
	MaterialLink componentConcepts;
	@UiField
	MaterialLink methodologyConcepts;
	@UiField
	MaterialLink parameterConcepts;
	
	@UiField
	ScrollPanel conceptPanel;
	@UiField
	MaterialPanel modalpanel;
	
	ForceGraphPanel forcesPanel; 
	
	String currentPick;
	public VisualizeGraphicalObjects() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public VisualizeGraphicalObjects(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	void init() {
		currentPick = null;
		forcesPanel = new ForceGraphPanel();
		conceptPanel.add(forcesPanel);
	}

	public void async(String concept) {
		String access = "Public";
		String owner = "Administration";
		String sourceID = "1";
		GraphSetOfKeywordRDFs callback = new GraphSetOfKeywordRDFs(forcesPanel);
		forcesPanel.setText(concept);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.subsystemInterconnections(concept,access,owner,sourceID,callback);
	}
	
	private void hierarchialModal(String name) {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ConceptHierarchyCallback callback = new ConceptHierarchyCallback(this);
		async.hierarchyOfConcepts(name,callback);
	}
	
	@UiHandler("deviceConcepts")
	void deviceConcepts(ClickEvent event) {
		hierarchialModal("dataset:DataTypeDevice");
		
	}
	@UiHandler("subsystemConcepts")
	void subsystemConcepts(ClickEvent event) {
		hierarchialModal("dataset:DataTypeSystem");
	}
	@UiHandler("componentConcepts")
	void componentConcepts(ClickEvent event) {
		hierarchialModal("dataset:DataTypeComponent");
	}
	@UiHandler("methodologyConcepts")
	void methodologyConcepts(ClickEvent event) {
		hierarchialModal("dataset:DataTypeMethodology");
	}
	@UiHandler("parameterConcepts")
	void parameterConcepts(ClickEvent event) {
		hierarchialModal("dataset:ChemConnectParameter");
	}

	public void setText(String text) {
	}

	public String getText() {
		return "Graphical";
	}

	public void addHierarchialModal(HierarchyNode hierarchy) {
		modalpanel.clear();
		HierarchialConceptSelection selection = new HierarchialConceptSelection(hierarchy,this);
		modalpanel.add(selection);
		selection.open();
	}

}
