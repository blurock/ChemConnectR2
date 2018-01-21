package info.esblurock.reaction.chemconnect.core.client.graph.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.HorizontalHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.client.graph.hierarchy.HorizontalHierarchyPanel;
import info.esblurock.reaction.chemconnect.core.client.graph.rdf.ForceGraphPanel;
import info.esblurock.reaction.chemconnect.core.client.graph.rdf.HierarchialConceptSelection;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class VisualizeGraphicalObjects extends Composite implements HasText {

	private static VisualizeGraphicalObjectsUiBinder uiBinder = GWT.create(VisualizeGraphicalObjectsUiBinder.class);

	interface VisualizeGraphicalObjectsUiBinder extends UiBinder<Widget, VisualizeGraphicalObjects> {
	}

	
	
/*
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
	*/
	@UiField
	MaterialPanel parameters;
	@UiField
	ScrollPanel conceptPanel;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialCollapsible collapsiblebody;
	@UiField
	MaterialCollapsible conceptcollapse;
	
	ForceGraphPanel forcesPanel; 
	HorizontalHierarchyPanel hierarchyPanel;
	String currentPick;
	String topconcept;
	public VisualizeGraphicalObjects() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public VisualizeGraphicalObjects(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	void init() {
		topconcept = "dataset:ChemConnectDomainConcept";
		currentPick = null;
		forcesPanel = new ForceGraphPanel();
		//conceptPanel.add(forcesPanel);
		hierarchyPanel = new HorizontalHierarchyPanel();
		conceptPanel.add(hierarchyPanel);
		hierarchialParameters();
	}

	public void async(String concept) {		
		HorizontalHierarchyCallback callback = new HorizontalHierarchyCallback(hierarchyPanel);
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		async.hierarchyOfConcepts(concept, callback);
		MaterialToast.fireToast("Concepts of " + concept);
		conceptcollapse.close(1);
		collapsiblebody.close(1);
		collapsiblebody.close(2);
	}
	
	private void hierarchialParameters() {
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ConceptHierarchyCallback callback = new ConceptHierarchyCallback(this);
		async.hierarchyOfConceptsWithLevelLimit(topconcept,2,callback);
	}
	public void addParametersCollapsible(HierarchyNode hierarchy) {
		for(HierarchyNode subnode : hierarchy.getSubNodes()) {
			addCollapsible(subnode,collapsiblebody);
		}
	}
	private void addCollapsible(HierarchyNode node, MaterialCollapsible collapsible) {
		ParameterCollapsible parameter = new ParameterCollapsible(node.getIdentifier(),node.getIdentifier(),this);
		collapsible.add(parameter);
		if(node.getSubNodes().size() == 0) {
			parameter.noChildren();
		} else {
			parameter.disableButton();
		for(HierarchyNode sub : node.getSubNodes()) {
			addCollapsible(sub, parameter.getBody());
		}
		}
	}
	/*
	@UiHandler("deviceConcepts")
	void deviceConcepts(ClickEvent event) {
		async("dataset:DataTypeDevice");
		
	}
	@UiHandler("subsystemConcepts")
	void subsystemConcepts(ClickEvent event) {
		async("dataset:DataTypeSystem");
	}
	@UiHandler("componentConcepts")
	void componentConcepts(ClickEvent event) {
		async("dataset:DataTypeComponent");
	}
	@UiHandler("methodologyConcepts")
	void methodologyConcepts(ClickEvent event) {
		async("dataset:DataTypeMethodology");
	}
	@UiHandler("parameterConcepts")
	void parameterConcepts(ClickEvent event) {
		async("dataset:ChemConnectParameter");
	}
*/
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
