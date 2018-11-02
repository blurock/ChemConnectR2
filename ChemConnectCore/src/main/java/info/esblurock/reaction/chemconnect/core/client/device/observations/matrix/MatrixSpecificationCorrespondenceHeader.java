package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.ChooseFromListDialog;
import info.esblurock.reaction.chemconnect.core.client.modal.ChooseFromListInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;

public class MatrixSpecificationCorrespondenceHeader extends Composite implements ChooseFromListInterface {

	private static MatrixSpecificationCorrespondenceHeaderUiBinder uiBinder = GWT
			.create(MatrixSpecificationCorrespondenceHeaderUiBinder.class);

	interface MatrixSpecificationCorrespondenceHeaderUiBinder
			extends UiBinder<Widget, MatrixSpecificationCorrespondenceHeader> {
	}

	public MatrixSpecificationCorrespondenceHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink matrixcolumn;
	@UiField
	MaterialLink specificationLabel;
	@UiField
	MaterialCheckBox includesUncertainty;
	
	String fullSpecificationLabel;
	
	ChooseFromListDialog choose;
	StandardDatasetObjectHierarchyItem item;
	MatrixSpecificationCorrespondence matcorr;
	ArrayList<String> specifications;

	public MatrixSpecificationCorrespondenceHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		matcorr = (MatrixSpecificationCorrespondence) item.getObject();
		matrixcolumn.setText(matcorr.getMatrixColumn());
		fullSpecificationLabel = matcorr.getSpecificationLabel();
		specificationLabel.setText(TextUtilities.removeNamespace(fullSpecificationLabel));
		includesUncertainty.setValue(matcorr.isIncludesUncertaintyParameter());
		choose = null;
	}
	
	public void addChoices(String title, ArrayList<String> orderedlist, Map<String,String> maplst, boolean includeCount) {
		choose = new ChooseFromListDialog(matrixcolumn.getText(),orderedlist,maplst,includeCount,this);
	}
	
	public void updateData() {
		matcorr.setMatrixColumn(matrixcolumn.getText());
		matcorr.setSpecificationLabel(fullSpecificationLabel);
		boolean uncertainty = includesUncertainty.getValue().booleanValue();
		matcorr.setIncludesUncertaintyParameter(uncertainty);
	}
	
	@UiHandler("specificationLabel")
	void specificationLabelClicked(ClickEvent event) {
		if(choose != null) {
			item.getModalpanel().clear();
			item.getModalpanel().add(choose);
			choose.open();
		}
	}
	public void setSpecifications(ArrayList<String> specifications) {
		this.specifications = specifications;
	}

	@Override
	public void chosenLabel(String choice, boolean uncertainty) {
		fullSpecificationLabel = choice;
		specificationLabel.setText(TextUtilities.removeNamespace(choice));
		includesUncertainty.setValue(uncertainty);
		choose.close();
	}
}
