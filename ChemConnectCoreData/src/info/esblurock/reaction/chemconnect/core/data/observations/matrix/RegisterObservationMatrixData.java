package info.esblurock.reaction.chemconnect.core.data.observations.matrix;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.data.base.ResetDatabaseObjects;

public class RegisterObservationMatrixData {
	public static void register() {
		ObjectifyService.register(MatrixSpecificationCorrespondence.class);
		ObjectifyService.register(MatrixSpecificationCorrespondenceSet.class);
		ObjectifyService.register(ObservationValueRow.class);
		ObjectifyService.register(ObservationValueRowTitle.class);
		ObjectifyService.register(ObservationMatrixValues.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(MatrixSpecificationCorrespondence.class);
		ResetDatabaseObjects.resetClass(MatrixSpecificationCorrespondenceSet.class);
		ResetDatabaseObjects.resetClass(ObservationValueRow.class);
		ResetDatabaseObjects.resetClass(ObservationValueRowTitle.class);
		ResetDatabaseObjects.resetClass(ObservationMatrixValues.class);
	}
}
