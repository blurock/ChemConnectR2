package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.client.UpdateDataObjectHeaderInterface;
import info.esblurock.reaction.chemconnect.core.client.catalog.SetUpCollapsibleItem;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.ObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class MatrixSpecificationCorrespondenceSetHeader extends Composite 
	implements  UpdateDataObjectHeaderInterface {

	private static MatrixSpecificationCorrespondenceSetHeaderUiBinder uiBinder = GWT
			.create(MatrixSpecificationCorrespondenceSetHeaderUiBinder.class);

	interface MatrixSpecificationCorrespondenceSetHeaderUiBinder
			extends UiBinder<Widget, MatrixSpecificationCorrespondenceSetHeader> {
	}

	public MatrixSpecificationCorrespondenceSetHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink corrspecheader;
	@UiField
	MaterialLink addcorr;
	@UiField
	MaterialPanel corrspecpanel;

	
	MatrixSpecificationCorrespondenceSet speccorrset;
	DatabaseObjectHierarchy hierarchy;
	DatabaseObjectHierarchy matspechierarchy;
	ChemConnectCompoundMultiple specmult;
	StandardDatasetObjectHierarchyItem topitem;
	InputLineModal linemodal;
	ArrayList<String> orderedlist;
	HashMap<String, String> listmap;
	DatabaseObjectHierarchy attachedObservationsFromSpreadSheet;

	public MatrixSpecificationCorrespondenceSetHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.hierarchy = item.getHierarchy();
		initMatrixSpecificationCorrespondenceSet();
		this.topitem = item;
		displayCorrespondences();
	}
	/*
	 * MatrixSpecificationCorrespondenceSet hierarchy
	 */
	private void initMatrixSpecificationCorrespondenceSet() {
		speccorrset = (MatrixSpecificationCorrespondenceSet) hierarchy.getObject();
		matspechierarchy = hierarchy
				.getSubObject(speccorrset.getMatrixSpecificationCorrespondence());
		specmult = (ChemConnectCompoundMultiple) matspechierarchy.getObject();		
	}
	
	private void init() {
		corrspecheader.setText("Specification Correspondence Set");
		
		orderedlist = null;
		listmap = null;
		attachedObservationsFromSpreadSheet = null;
	}
	
	public void displayCorrespondences() {
		for(DatabaseObjectHierarchy mathier : matspechierarchy.getSubobjects()) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(topitem,mathier,
					topitem.getModalpanel());
			SetUpCollapsibleItem.MatrixSpecificationCorrespondence.addInformation(item);
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader) item.getHeader();
			
			if(orderedlist != null) {
				header.addChoices("Select Specification for Column", orderedlist, listmap, false);
			}
			corrspecpanel.add(header);
		}
	}

	/**
	 * @param catid  Catalog ID
	 * @param spechier Hierarchy for ObservationSpecification
	 * @param subs Hierachy for ObservationsFromSpreadSheet
	 */
	public void setupMatrix(DataCatalogID catid, DatabaseObjectHierarchy subs) {
		attachedObservationsFromSpreadSheet = subs;
		ObservationsFromSpreadSheet observations = (ObservationsFromSpreadSheet) attachedObservationsFromSpreadSheet.getObject();
		DatabaseObjectHierarchy matrixhierarchy = attachedObservationsFromSpreadSheet.getSubObject(observations.getObservationValueRowTitle());
		setInColumnCorrespondences(matrixhierarchy);
	}
	
	public void setUpListOfSpecifications(DatabaseObjectHierarchy spechier) {
		ObservationSpecification obsspec = (ObservationSpecification) spechier.getObject();
		String dimensionid = obsspec.getDimensionSpecifications();
		String measureid = obsspec.getMeasureSpecifications();
		DatabaseObjectHierarchy dimensionhier = spechier.getSubObject(dimensionid);
		DatabaseObjectHierarchy measurehier = spechier.getSubObject(measureid);
		orderedlist = new ArrayList<String>();
		listmap = new HashMap<String, String>();
		fillSpec(dimensionhier.getSubobjects());
		fillSpec(measurehier.getSubobjects());
	}

	private void fillSpec(ArrayList<DatabaseObjectHierarchy> specs) {
		for (DatabaseObjectHierarchy dimelement : specs) {
			ParameterSpecification spec = (ParameterSpecification) dimelement.getObject();
			
			DatabaseObjectHierarchy unithier = dimelement.getSubObject(spec.getUnits());
			
			ValueUnits units = (ValueUnits) unithier.getObject();
			String label = spec.getParameterLabel();
			orderedlist.add(label);
			String title = TextUtilities.removeNamespace(spec.getParameterLabel())
					+ ": (" 
					+ TextUtilities.removeNamespace(spec.getObservationParameterType())
					+ ", "
					+ TextUtilities.removeNamespace(units.getUnitsOfValue())
					+ ")";
			listmap.put(label, title);
		}
	}

	private void setInColumnCorrespondences(DatabaseObjectHierarchy titlehier) {
		ObservationValueRowTitle titles = (ObservationValueRowTitle) titlehier.getObject();
		ArrayList<String> coltitles = titles.getParameterLabel();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		FillMatrixSpecificationCorrespondenceCallback callback = new FillMatrixSpecificationCorrespondenceCallback(this);
		async.fillMatrixSpecificationCorrespondence(hierarchy,coltitles,callback);
	}
	
	public void fillMatrixSpecificationCorrespondence(DatabaseObjectHierarchy matspechierarchy) {
		hierarchy.replaceInfo(matspechierarchy);
		initMatrixSpecificationCorrespondenceSet();
		displayCorrespondences();
	}
	
	
	public boolean updateData() {
		for(Widget widget: corrspecpanel.getChildren()) {
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader) widget;
			header.updateData();
		}
		return true;
	}

}
