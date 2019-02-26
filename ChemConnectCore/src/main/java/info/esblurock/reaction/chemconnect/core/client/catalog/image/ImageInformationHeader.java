package info.esblurock.reaction.chemconnect.core.client.catalog.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
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

	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	ImageInformation image;
	
	String imageType;
	String imageURL;

	public ImageInformationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	public ImageInformationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		hierarchy = item.getHierarchy();
		image = (ImageInformation) hierarchy.getObject();
		imageType = image.getImageType();
		type.setText(TextUtilities.removeNamespace(imageType));
		typetooltip.setText(image.getImageURL());
		imageURL = image.getImageURL();
		MaterialImage image = new MaterialImage(imageURL);
		imagePanel.add(image);
	}
	
	void init() {
		type.setText("Type");
		typetooltip.setText("Type of Image");
	}

	public void updateData() {
		image.setImageType(imageType);
		image.setImageURL(imageURL);
	}
}
