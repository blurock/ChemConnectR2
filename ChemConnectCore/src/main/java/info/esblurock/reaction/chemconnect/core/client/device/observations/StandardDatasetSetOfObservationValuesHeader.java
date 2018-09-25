package info.esblurock.reaction.chemconnect.core.client.device.observations;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.DatasetStandardDataCatalogIDHeader;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.device.observations.matrix.MatrixSpecificationCorrespondenceSetHeader;
import info.esblurock.reaction.chemconnect.core.client.pages.catalog.observations.StandardDatasetObservationSpecificationHeader;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.SetOfObservationValues;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class StandardDatasetSetOfObservationValuesHeader extends Composite {

	private static StandardDatasetSetOfObservationValuesHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetSetOfObservationValuesHeaderUiBinder.class);

	interface StandardDatasetSetOfObservationValuesHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetSetOfObservationValuesHeader> {
	}

	@UiField
	MaterialTooltip observationtooltip;
	@UiField
	MaterialTooltip titletooltip;
	@UiField
	MaterialLink observationhead;
	@UiField
	MaterialLink observationtype;
	@UiField
	MaterialIcon save;
	@UiField
	MaterialIcon delete;
	@UiField
	MaterialTooltip attachtooltip;
	@UiField
	MaterialIcon attach;
	
	
	StandardDatasetObjectHierarchyItem item;
	SetOfObservationValues value;
	MatrixSpecificationCorrespondenceSetHeader matspecheader;
	DatasetStandardDataCatalogIDHeader catidheader;
	StandardDatasetObservationSpecificationHeader specheader;
	DataCatalogID catid;
	ObservationSpecification obsspec;
	MatrixSpecificationCorrespondenceSet matspec;

	public StandardDatasetSetOfObservationValuesHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public StandardDatasetSetOfObservationValuesHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		value = (SetOfObservationValues) hierarchy.getObject();
		observationtooltip.setText(value.getIdentifier());
		DatabaseObjectHierarchy spechier = hierarchy.getSubObject(value.getObservationSpecification());
		obsspec = (ObservationSpecification) spechier.getObject();
		observationtype.setText(TextUtilities.removeNamespace(obsspec.getObservationParameterType()));
		String descID = value.getDescriptionDataData();
		DatabaseObjectHierarchy  deschier = hierarchy.getSubObject(descID);
		DescriptionDataData description = (DescriptionDataData) deschier.getObject();
		observationhead.setText(description.getOnlinedescription());
		DatabaseObjectHierarchy cathier = hierarchy.getSubObject(value.getCatalogDataID());
		catid = (DataCatalogID) cathier.getObject();
		titletooltip.setText(TextUtilities.removeNamespace(catid.getCatalogBaseName()));
		attachtooltip.setText("Attach a observations from spreadsheet");
		matspecheader = null;
		catidheader = null;
		specheader = null;
		matspec = null;
	}
	
	@UiHandler("attach")
	void saveClick(ClickEvent event) {
		ArrayList<StandardDatasetObjectHierarchyItem> subs = item.getInfoSubitems();
		subs.addAll(item.getSubitems());
		for(StandardDatasetObjectHierarchyItem i: subs) {
			String classname = i.getObject().getClass().getSimpleName();
			if(classname.compareTo("MatrixSpecificationCorrespondenceSet") == 0) {
				matspecheader = (MatrixSpecificationCorrespondenceSetHeader) i.getHeader();
				matspec = (MatrixSpecificationCorrespondenceSet) i.getObject();
			} else if(classname.compareTo("DataCatalogID") == 0) {
				catidheader = (DatasetStandardDataCatalogIDHeader) i.getHeader();
			} else if(classname.compareTo("ObservationSpecification") == 0) {
				specheader = (StandardDatasetObservationSpecificationHeader) i.getHeader();
			}
		}
		
		matspecheader.setupMatrix(catid,obsspec);
	}

}
