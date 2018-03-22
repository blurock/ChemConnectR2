package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BlobStorageInterface extends Composite {

	private static BlobStorageInterfaceUiBinder uiBinder = GWT.create(BlobStorageInterfaceUiBinder.class);

	interface BlobStorageInterfaceUiBinder extends UiBinder<Widget, BlobStorageInterface> {
	}

	public BlobStorageInterface() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public BlobStorageInterface(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
