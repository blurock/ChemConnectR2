package info.esblurock.reaction.chemconnect.core.client.graph.hierarchy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialPanel;

public class HorizontalHierarchyPanel extends Composite implements HasText {

	private static HorizontalHierarchyPanelUiBinder uiBinder = GWT.create(HorizontalHierarchyPanelUiBinder.class);

	interface HorizontalHierarchyPanelUiBinder extends UiBinder<Widget, HorizontalHierarchyPanel> {
	}

	public HorizontalHierarchyPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLabel panelLabel;
	@UiField
	MaterialPanel panel;

	public HorizontalHierarchyPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		panelLabel.setText(firstName);
	}

	public void setPanel(Widget widget) {
		panel.clear();
		panel.add(widget);
	}
	
	public void mouseOver(String nodeName) {
	}

	public void mouseClick(String nodeName) {
		setText(nodeName);
	}

	public void setText(String text) {
		panelLabel.setText(text);
	}

	public String getText() {
		return panelLabel.getText();
	}

}
