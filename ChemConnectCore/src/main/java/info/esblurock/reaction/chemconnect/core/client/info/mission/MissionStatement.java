package info.esblurock.reaction.chemconnect.core.client.info.mission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.resources.info.about.InfoAboutResources;
import info.esblurock.reaction.chemconnect.core.client.ui.view.MissionStatementView;

public class MissionStatement extends Composite implements MissionStatementView {

	Presenter listener;

	private static MissionStatementUiBinder uiBinder = GWT.create(MissionStatementUiBinder.class);
	interface MissionStatementUiBinder extends UiBinder<Widget, MissionStatement> {
	}

	InfoAboutResources about = GWT.create(InfoAboutResources.class);
	
	public MissionStatement() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiField
	MaterialPanel sitegoaltext;
	@UiField
	MaterialPanel fairdatasummarytext;
	@UiField
	MaterialPanel TOPtext;
	@UiField
	MaterialLink sitegoallink;
	@UiField
	MaterialLink fairdatasummarylink;
	@UiField
	MaterialLink TOPlink;

	private void init() {
		HTML sitegoalhtml = new HTML(about.SiteGoal().getText());
		sitegoaltext.add(sitegoalhtml);
		sitegoallink.setText("Site Goal");
		
		HTML fairdatasummaryhtml = new HTML(about.FAIRDataSummary().getText());
		fairdatasummarytext.add(fairdatasummaryhtml);
		fairdatasummarylink.setText("FAIR Data");
		
		HTML TOPhtml = new HTML(about.TransparencyAndOpennessPromotionSummary().getText());
		TOPtext.add(TOPhtml);
		TOPlink.setText("Transparency and Openness Promotion");
	}

	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
