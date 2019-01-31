package info.esblurock.reaction.chemconnect.core.client.administration;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.client.pages.MainDataStructureInstanceCollapsible;
import info.esblurock.reaction.chemconnect.core.client.pages.RecordStructureCollapsible;
import info.esblurock.reaction.chemconnect.core.data.transfer.CompoundDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.ElementsOfASetOfMainStructure;
import info.esblurock.reaction.chemconnect.core.data.transfer.RecordInformation;

public class CompoundDataStructureCallback implements AsyncCallback<RecordInformation> {

	MainDataStructureInstanceCollapsible main;

	public CompoundDataStructureCallback(MainDataStructureInstanceCollapsible main) {
		this.main = main;
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable arg0) {
		Window.alert(arg0.toString());
		MaterialLoader.loading(false);
	}

	@Override
	public void onSuccess(RecordInformation record) {
		ElementsOfASetOfMainStructure elements = record.getSubelements();
		ArrayList<CompoundDataStructureInformation> lst = elements.getElements();
		for (CompoundDataStructureInformation info : lst) {
			RecordStructureCollapsible collapsible = new RecordStructureCollapsible(record.getObject(), info);
			main.getCollapsible().add(collapsible);
		}
		MaterialLoader.loading(false);
	}

}
