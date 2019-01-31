package info.esblurock.reaction.chemconnect.core.client.device.observations.matrix;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class FillMatrixSpecificationCorrespondenceCallback  implements AsyncCallback<DatabaseObjectHierarchy>{

	MatrixSpecificationCorrespondenceSetHeader corr;
	
	FillMatrixSpecificationCorrespondenceCallback(MatrixSpecificationCorrespondenceSetHeader corr) {
		this.corr = corr;
		MaterialLoader.loading(true);
	}

	@Override
	public void onFailure(Throwable ex) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Matrix Specification\n" + ex.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		MaterialLoader.loading(false);
		corr.fillMatrixSpecificationCorrespondence(hierarchy);
	}
	
}
