package info.esblurock.reaction.chemconnect.core.client.graph.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialLink;

public class ParameterCollapsible extends Composite implements HasText {

	private static ParameterCollapsibleUiBinder uiBinder = GWT.create(ParameterCollapsibleUiBinder.class);

	interface ParameterCollapsibleUiBinder extends UiBinder<Widget, ParameterCollapsible> {
	}

	public ParameterCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink parameter;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsibleBody bodycollapsible;
	
	VisualizeGraphicalObjects top;
	String concept;
	public ParameterCollapsible(String concept, String parametername, VisualizeGraphicalObjects top) {
		initWidget(uiBinder.createAndBindUi(this));
		parameter.setText(parametername);
		this.top = top;
		this.concept = concept;
	}

	@UiHandler("parameter")
	void onClick(ClickEvent e) {
		if(bodycollapsible == null) {
		top.async(concept);
		}
		contentcollapsible.closeAll();
	}

	public void noChildren() {
		bodycollapsible.clear();
		bodycollapsible = null;
	}
	public void setText(String text) {
		concept = text;
	}

	public String getText() {
		return concept;
	}

	public MaterialCollapsible getBody() {
		return contentcollapsible;
	}

	public void disableButton() {
		parameter.setEnabled(false);
	}
}
