package info.esblurock.reaction.chemconnect.core.client.catalog.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.image.ImageInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class ImageInformationHeader extends Composite {

	private static ImageInformationHeaderUiBinder uiBinder = GWT.create(ImageInformationHeaderUiBinder.class);

	interface ImageInformationHeaderUiBinder extends UiBinder<Widget, ImageInformationHeader> {
	}

	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialLink type;
	@UiField
	MaterialPanel imagePanel;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink save;

	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	ImageInformation image;

	public ImageInformationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	public ImageInformationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		hierarchy = item.getHierarchy();
		image = (ImageInformation) hierarchy.getObject();
		type.setText(TextUtilities.removeNamespace(image.getImageType()));
		typetooltip.setText(image.getImageURL());
		String urlS = image.getImageURL();
		MaterialImage image = new MaterialImage(urlS);
		imagePanel.add(image);
	}
	
	void init() {
		type.setText("Type");
		typetooltip.setText("Type of Image");
	}

	@UiHandler("save")
	void onClickSave(ClickEvent e) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();		
	}
	
	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete!");
	}
	
	@UiHandler("type")
	void onClickHeader(ClickEvent e) {
		Window.alert("type!");
	}

}
