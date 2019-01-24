package info.esblurock.reaction.chemconnect.core.client.info.tutorial;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.resources.info.tutorial.TutorialResources;
import info.esblurock.reaction.chemconnect.core.client.ui.view.TutorialExampleView;

public class TutorialExample extends Composite implements TutorialExampleView {

	private static TutorialExampleUiBinder uiBinder = GWT.create(TutorialExampleUiBinder.class);

	interface TutorialExampleUiBinder extends UiBinder<Widget, TutorialExample> {
	}
	
	TutorialResources tutorial = GWT.create(TutorialResources.class);
	Presenter listener;
	
	@UiField
	MaterialPanel introductiontext;
	@UiField
	MaterialLink introductionlink;
	@UiField
	MaterialPanel explanationtext;
	@UiField
	MaterialLink explanationlink;
	@UiField
	MaterialPanel filestagingtext;
	@UiField
	MaterialLink filestaginglink;
	public TutorialExample() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	void init() {
		HTML intoductionhtml = new HTML(" Text Example Summary");
		introductiontext.add(intoductionhtml);
		introductionlink.setText("Outline");
		HTML explanationhtml = new HTML(tutorial.explanation().getText());
		explanationtext.add(explanationhtml);
		explanationlink.setText("Overview");
		HTML filestaginghtml = new HTML(tutorial.filestaging().getText());
		ImageResource uploadimg = tutorial.uploadingFilesProcess();
		Image upload = new Image(uploadimg);
		filestagingtext.add(upload);
		ImageResource uploadimg2 = tutorial.fileStagingIdentification();
		Image upload2 = new Image(uploadimg2);
		filestagingtext.add(upload2);
		filestagingtext.add(filestaginghtml);
		filestaginglink.setText("File Staging");
	}

	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
