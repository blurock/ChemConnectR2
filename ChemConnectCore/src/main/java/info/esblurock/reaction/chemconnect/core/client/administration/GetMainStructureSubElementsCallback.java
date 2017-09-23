package info.esblurock.reaction.chemconnect.core.client.administration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.transfer.ListOfDataElementInformation;

public class GetMainStructureSubElementsCallback implements AsyncCallback<ListOfDataElementInformation>{

	MainDataStructureCollapsible main;
	
	public GetMainStructureSubElementsCallback(MainDataStructureCollapsible main) {
		this.main = main;
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
	}

	@Override
	public void onSuccess(ListOfDataElementInformation subelements) {
		main.setStructureSubElements(subelements);
	}

}
