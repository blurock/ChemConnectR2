package info.esblurock.reaction.chemconnect.core.client.device.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;

public class SetOfObservationsCollapsible extends Composite {

	private static SetOfObservationsCollapsibleUiBinder uiBinder = GWT
			.create(SetOfObservationsCollapsibleUiBinder.class);

	interface SetOfObservationsCollapsibleUiBinder extends UiBinder<Widget, SetOfObservationsCollapsible> {
	}


	String identifier;

	@UiField
	MaterialLabel typetitle;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialCollapsible infocollapsible;
	@UiField
	MaterialLink info;
	
	CardModal card;

	
	public SetOfObservationsCollapsible() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SetOfObservationsCollapsible(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		typetitle.setText(TextUtilities.removeNamespace(name));
	}

	public MaterialCollapsible getInfoCollapsible() {
		return infocollapsible;
	}

}
