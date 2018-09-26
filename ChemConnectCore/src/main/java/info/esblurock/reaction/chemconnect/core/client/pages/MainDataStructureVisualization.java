package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class MainDataStructureVisualization extends Composite {

	private static MainDataStructureVisualizationUiBinder uiBinder = GWT
			.create(MainDataStructureVisualizationUiBinder.class);

	interface MainDataStructureVisualizationUiBinder extends UiBinder<Widget, MainDataStructureVisualization> {
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialCollapsible contentcollapsible;
	
	public MainDataStructureVisualization() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setText("Main Data Structures");
		/*
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		CatalogClassificationInformationCallback callback = new CatalogClassificationInformationCallback(this);
		async.getCatalogHierarchy(callback);
		*/
	}

	public void addMainStructures(HierarchyNode node) {
		Window.alert("addMainStructures(HierarchyNode node)");
		StructureHierarchyCollapsible collapse = new StructureHierarchyCollapsible(node);
		contentcollapsible.add(collapse);
	}

}
