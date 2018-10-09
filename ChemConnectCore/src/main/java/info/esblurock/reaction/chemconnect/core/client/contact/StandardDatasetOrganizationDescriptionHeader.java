package info.esblurock.reaction.chemconnect.core.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.client.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.data.metadata.MetaDataKeywords;

public class StandardDatasetOrganizationDescriptionHeader extends Composite 
		implements SetLineContentInterface, ChooseFromConceptHeirarchy {

	private static StandardDatasetOrganizationDescriptionHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetOrganizationDescriptionHeaderUiBinder.class);

	interface StandardDatasetOrganizationDescriptionHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetOrganizationDescriptionHeader> {
	}

	public StandardDatasetOrganizationDescriptionHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip orgnametooltip;
	@UiField
	MaterialLink orgname;
	@UiField
	MaterialTooltip orgclasstooltip;
	@UiField
	MaterialLink orgclass;
	@UiField
	MaterialTooltip suborgtooltip;
	@UiField
	MaterialLink suborg;
	@UiField
	MaterialTooltip orgunittooltip;
	@UiField
	MaterialLink orgunit;
	
	StandardDatasetObjectHierarchyItem item;
	OrganizationDescription descr;
	InputLineModal line;
	String linequery;
	ChooseFromConceptHierarchies choosecat;
	String organizationClass;

	public StandardDatasetOrganizationDescriptionHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		descr = (OrganizationDescription) item.getObject();
		init();
		orgnametooltip.setText(descr.getIdentifier());
		organizationClass = descr.getOrganizationClassification();
		TextUtilities.setText(orgname,descr.getOrganizationName(),"OrganizationName");
		TextUtilities.setText(orgclass,TextUtilities.removeNamespace(organizationClass),"OrganizationClass");
		TextUtilities.setText(suborg,descr.getSubOrganizationOf(),"SubOrganizationOfName");
		TextUtilities.setText(orgunit,descr.getOrganizationUnit(),"TopOrganizationName");
	}
	private void init() {
		orgclasstooltip.setText("Organization Class (Group or sub-department within Institute, Company, University...)");
		suborgtooltip.setText("Is a sub-organization of (Insitute, Department...)");
		orgunittooltip.setText("Top organizational unit (University, Company...)");
	}
	
	public void updateInfo() {
		OrganizationDescription descr = (OrganizationDescription) item.getObject();
		descr.setOrganizationName(orgname.getText());
		descr.setOrganizationClassification(organizationClass);
		descr.setOrganizationUnit(orgunit.getText());
		descr.setSubOrganizationOf(suborg.getText());
	}
	
	public void setOrganizationName(String name) {
		orgname.setText(name);
	}
	
	@UiHandler("orgname")
	void onClickOrgName(ClickEvent event) {
		line = new InputLineModal("Input organization name","Combustion Kinetics Group",this);
		linequery = "orgname";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("suborg")
	void onClickSubOrg(ClickEvent event) {
		line = new InputLineModal("Input the direct sub-organization to which this organization belongs","Institute",this);
		linequery = "suborg";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("orgunit")
	void onClickOrganizationalUnit(ClickEvent event) {
		line = new InputLineModal("Input the top organizational unit","Whatsamatter University",this);
		linequery = "orgunit";
		item.getModalpanel().add(line);
		line.openModal();
	}
	@UiHandler("orgclass")
	void onClickOrganizationalClass(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.organizationClassificationChoices);
		choosecat = new ChooseFromConceptHierarchies(choices,this);
		item.getModalpanel().add(choosecat);
		choosecat.open();
	}
	@Override
	public void setLineContent(String line) {
		if(linequery.compareTo("orgname") == 0) {
			orgname.setText(line);
		} else if(linequery.compareTo("suborg") == 0) {
			suborg.setText(line);
		} else if(linequery.compareTo("orgunit") == 0) {
			orgunit.setText(line);
		} else if(linequery.compareTo("orgclass") == 0) {
			orgclass.setText(line);
		}		
	}

	public boolean updateData() {
		descr.setOrganizationClassification(organizationClass);
		descr.setOrganizationName(orgname.getText());
		descr.setOrganizationUnit(orgunit.getText());
		descr.setSubOrganizationOf(suborg.getText());
		return false;
	}
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		orgclass.setText(TextUtilities.removeNamespace(concept));
		organizationClass = concept;
	}

}
