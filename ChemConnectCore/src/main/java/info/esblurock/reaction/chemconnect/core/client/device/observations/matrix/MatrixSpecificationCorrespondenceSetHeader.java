package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

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

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;

public class MatrixSpecificationCorrespondenceSetHeader extends Composite {

	private static MatrixSpecificationCorrespondenceSetHeaderUiBinder uiBinder = GWT
			.create(MatrixSpecificationCorrespondenceSetHeaderUiBinder.class);

	interface MatrixSpecificationCorrespondenceSetHeaderUiBinder
			extends UiBinder<Widget, MatrixSpecificationCorrespondenceSetHeader> {
	}

	public MatrixSpecificationCorrespondenceSetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink headername;
	@UiField
	MaterialLink startRow;
	@UiField
	MaterialLink endRow;
	@UiField
	MaterialLink startColumn;
	@UiField
	MaterialLink endColumn;
	@UiField
	MaterialLink corrspecheader;
	@UiField
	MaterialLink addcorr;
	@UiField
	MaterialPanel corrspecpanel;
	
	MatrixSpecificationCorrespondenceSet speccorrset;
	

	public MatrixSpecificationCorrespondenceSetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
