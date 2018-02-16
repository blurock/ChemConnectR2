package info.esblurock.reaction.chemconnect.core.client.device;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.TotalSubsystemInformation;

public class DeviceHierarchyCallback implements AsyncCallback<TotalSubsystemInformation> {

	DeviceWithSubystemsDefinition top;
	
	public DeviceHierarchyCallback(DeviceWithSubystemsDefinition top) {
		super();
		this.top = top;
	}

	@Override
	public void onFailure(Throwable arg0) {
		System.out.println("Error: \n" + arg0);
	}

	@Override
	public void onSuccess(TotalSubsystemInformation hierarchy) {
		String topobject = hierarchy.getSubsystemtree().getIdentifier();
		String id = top.getTopCatagory() + "-" + TextUtilities.removeNamespace(topobject);
		top.addTopHierarchialModal(id,hierarchy.getSubsystemtree(),hierarchy);
	}

}
