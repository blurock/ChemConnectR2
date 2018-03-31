package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.data.observations.VisualizeObservationBase;

public class VisualizeMediaCallback implements AsyncCallback<VisualizeObservationBase> {

	String type;
	String title;
	VisualizationOfBlobStorage visual;
	
	public VisualizeMediaCallback(String type, String title, VisualizationOfBlobStorage visual) {
		this.type = type;
		this.visual = visual;
		this.title = title;
	}
	@Override
	public void onFailure(Throwable arg0) {
		Window.alert("Error in reading and interpreting blob\n" + arg0.toString());
	}

	@Override
	public void onSuccess(VisualizeObservationBase obj) {
		VisualizeMedia visualization = VisualizeMedia.valueOf(type);
		visualization.insertVisualization(obj, title, visual);
	}

}
