package info.esblurock.reaction.chemconnect.core.client.catalog.reference;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;

public class FamilyNameCallback implements AsyncCallback<ArrayList<NameOfPerson>> {

	AuthorInformationHeader authorheader;
	
	FamilyNameCallback(AuthorInformationHeader authorheader) {
		this.authorheader = authorheader;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ArrayList<NameOfPerson> names) {
		authorheader.setInNames(names);
	}

}
