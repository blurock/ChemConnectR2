package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class FillMatrixSpecificationCorrespondenceCallback  implements AsyncCallback<DatabaseObjectHierarchy>{

	MatrixSpecificationCorrespondenceSetHeader corr;
	
	FillMatrixSpecificationCorrespondenceCallback(MatrixSpecificationCorrespondenceSetHeader corr) {
		this.corr = corr;
	}

	@Override
	public void onFailure(Throwable ex) {
		Window.alert(ex.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		corr.fillMatrixSpecificationCorrespondence(hierarchy);
	}
	
}
