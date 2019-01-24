package info.esblurock.reaction.chemconnect.core.client.resources.info.tutorial;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface TutorialResources extends ClientBundle {
	@Source("01Introduction.html")
	  public TextResource Introdction();
	@Source("02explanation.html")
	  public TextResource explanation();
	@Source("03FileStaging.html")
	  public TextResource filestaging();
	@Source("UploadingFilesProcess.jpg")
	public ImageResource uploadingFilesProcess();
	@Source("FileStagingIdentification.jpg")
	public ImageResource fileStagingIdentification();
}
