package info.esblurock.reaction.chemconnect.core.client.resources.info.footer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.client.ui.view.AboutSummaryView;

public class AboutSummary extends Composite implements AboutSummaryView {

	Presenter listener;

	private static AboutSummaryUiBinder uiBinder = GWT.create(AboutSummaryUiBinder.class);

	interface AboutSummaryUiBinder extends UiBinder<Widget, AboutSummary> {
	}
	
	@UiField
	MaterialTitle title;
	@UiField
	MaterialLink chemconnect;
	@UiField
	MaterialLink chemconnectdescription;
	@UiField
	MaterialLink blurockconsultingab;
	@UiField
	MaterialLink blurockconsultingabdescription;
	@UiField
	MaterialLink blurock;
	@UiField
	MaterialLink blurockdescription;

	public AboutSummary() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		title.setTitle("Information about CHEMCONNECT");
		title.setDescription("The links below will open a new window");
		chemconnect.setText("about CHEMCONNECT");
		chemconnectdescription.setText("Structure and philosophy of CHEMCONNECT and tutorials about the use of CHEMCONNECT");
		blurockconsultingab.setText("Blurock Consulting AB");
		blurockconsultingabdescription.setText("Information about Blurock Consulting AB, data manaagement and Edward S. Blurock");
		blurock.setText("Edward S. Blurock");
		blurockdescription.setText("Information about the developer of CHEMCONNECT Edward S. Blurock");
	}
	
	@Override
	public void setName(String helloName) {
	}
	
	@UiHandler("chemconnect")
	public void chemconnectClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/chemconnect", "_blank", "");
	}

	@UiHandler("blurockconsultingab")
	public void blurockconsultingabClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/blurock-consulting-ab", "_blank", "");
	}
	@UiHandler("chemconnectdescription")
	public void chemconnectDescriptionClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/chemconnect", "_blank", "");
	}

	@UiHandler("blurockconsultingabdescription")
	public void blurockconsultingabDescriptionClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/blurock-consulting-ab", "_blank", "");
	}
	@UiHandler("blurock")
	public void blurockClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/blurock-consulting-ab/edward-s-blurock", "_blank", "");
	}
	@UiHandler("blurockdescription")
	public void blurockDescriptionClicked(ClickEvent event) {
		Window.open("https://sites.google.com/view/blurock-consulting-ab/edward-s-blurock", "_blank", "");
	}
	
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;	
	}


}
