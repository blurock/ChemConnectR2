package info.esblurock.reaction.chemconnect.core.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.HierarchyNode;

public class StructureHierarchyCollapsible extends Composite implements HasText {

	private static StructureHierarchyCollapsibleUiBinder uiBinder = GWT
			.create(StructureHierarchyCollapsibleUiBinder.class);

	interface StructureHierarchyCollapsibleUiBinder extends UiBinder<Widget, StructureHierarchyCollapsible> {
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialLink info;
	@UiField
	MaterialCollapsibleItem body;
	@UiField
	MaterialLink expand;
	CardModal card;

	public StructureHierarchyCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StructureHierarchyCollapsible(HierarchyNode node) {
		Window.alert("StructureHierarchyCollapsible(HierarchyNode node)");
		initWidget(uiBinder.createAndBindUi(this));
		title.setText(node.getIdentifier());
		if (node.getSubNodes().size() > 0) {
			for (HierarchyNode subnode : node.getSubNodes()) {
				StructureHierarchyCollapsible subnodeC = new StructureHierarchyCollapsible(subnode);
				contentcollapsible.add(subnodeC);
			}
		} else {
			if (node.getInfo() != null) {
				MainDataStructureCollapsible collapse = new MainDataStructureCollapsible(node.getInfo());
				contentcollapsible.add(collapse);
			} else {
				Window.alert("Classification null: " + node.getIdentifier());
			}
		}
	}

	public StructureHierarchyCollapsible(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
		title.setText(text);
	}

	public String getText() {
		return title.getText();
	}

}
