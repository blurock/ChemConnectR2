package info.esblurock.reaction.chemconnect.core.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactHasSite;

public class StandardDatasetContactHasSiteHeader extends Composite {

	private static StandardDatasetContactHasSiteHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetContactHasSiteHeaderUiBinder.class);

	interface StandardDatasetContactHasSiteHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetContactHasSiteHeader> {
	}

	public StandardDatasetContactHasSiteHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip httptooltip;
	@UiField
	MaterialLink httpaddress;
	@UiField
	MaterialTooltip linktypetooltip;
	@UiField
	MaterialLink linktype;

	StandardDatasetObjectHierarchyItem item;
	ContactHasSite  site;
	String httpType;
	
	public StandardDatasetContactHasSiteHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		site = (ContactHasSite ) item.getObject();
		httpType = site.getHttpAddressType();
		httpaddress.setText(site.getHttpAddress());
		linktype.setText(TextUtilities.removeNamespace(site.getHttpAddressType()));
	}
	private void init() {
		httptooltip.setText("Link Address");
		linktypetooltip.setText("Type of link");
	}
	
	
}
