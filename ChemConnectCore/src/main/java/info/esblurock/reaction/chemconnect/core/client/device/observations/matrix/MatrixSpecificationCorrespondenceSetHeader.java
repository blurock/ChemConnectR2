package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixBlockDefinition;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

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
	MaterialLink startrow;
	@UiField
	MaterialLink endrow;
	@UiField
	MaterialLink startcolumn;
	@UiField
	MaterialLink endcolumn;
	@UiField
	MaterialLink corrspecheader;
	@UiField
	MaterialLink addcorr;
	@UiField
	MaterialPanel corrspecpanel;
	
	MatrixSpecificationCorrespondenceSet speccorrset;
	DatabaseObjectHierarchy hierarchy;
	MatrixBlockDefinition blockdef;
	ChemConnectCompoundMultiple specmult;
	
	public MatrixSpecificationCorrespondenceSetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		hierarchy = item.getHierarchy();
		speccorrset = (MatrixSpecificationCorrespondenceSet) hierarchy.getObject();
		DatabaseObjectHierarchy matblockhierarchy = hierarchy.getSubObject(speccorrset.getMatrixBlockDefinition());
		DatabaseObjectHierarchy matspechierarchy = hierarchy.getSubObject(speccorrset.getMatrixSpecificationCorrespondence());
		blockdef = (MatrixBlockDefinition) matblockhierarchy.getObject();
		specmult = (ChemConnectCompoundMultiple) matspechierarchy.getObject();
		
		startrow.setText(blockdef.getStartRowInMatrix());
		endrow.setText(blockdef.getLastRowInMatrix());
		startcolumn.setText(blockdef.getStartColumnInMatrix());
		endcolumn.setText(blockdef.getLastColumnInMatrix());
		
		headername.setText("Matrix Specification Correspondence Set");
		corrspecheader.setText("Specification Correspondence Set");
	}

	@UiHandler("addcorr")
	void addClickEvent(ClickEvent event) {
		Window.alert("Add Click Event");
	}
}
