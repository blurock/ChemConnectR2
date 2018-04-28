package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModalInterface;

public class NewSubCatalogWizard extends CardModalInterface {

	private static NewSubCatalogWizardUiBinder uiBinder = GWT.create(NewSubCatalogWizardUiBinder.class);

	interface NewSubCatalogWizardUiBinder extends UiBinder<Widget, NewSubCatalogWizard> {
	}

	@UiField
	MaterialTextBox simpleName;
	@UiField
	MaterialTextBox oneline;
	
	CatalogHierarchyNode node;

	public NewSubCatalogWizard(CatalogHierarchyNode node) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.node = node;
	}
	
	private void init() {
		simpleName.setLabel("Simple Name");
		oneline.setLabel("One Line Description");
	}

	@Override
	public void actionOnOK() {
		node.insertInitialSubCatagoryInformation();
	}

	public String getSimpleName() {
		return simpleName.getText();
	}
	public String getOneLineDescription() {
		return oneline.getText();
	}
}
