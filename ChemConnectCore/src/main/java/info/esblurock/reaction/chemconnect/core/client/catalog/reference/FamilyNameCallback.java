package info.esblurock.reaction.chemconnect.core.client.catalog.reference;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

public class FamilyNameCallback implements AsyncCallback<ArrayList<NameOfPerson>> {

	AuthorInformationHeader authorheader;
	
	FamilyNameCallback(AuthorInformationHeader authorheader) {
		this.authorheader = authorheader;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Family Name:\n" + arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<NameOfPerson> names) {
		MaterialLoader.loading(false);
		authorheader.setInNames(names);
	}

}
