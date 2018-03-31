package info.esblurock.reaction.chemconnect.core.client.blobstorage;

import info.esblurock.reaction.chemconnect.core.data.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.data.image.UploadedImage;

public interface BlobStorageUploadInterface {

	void fillUpload(ImageServiceInformation result);

	void addImage(UploadedImage imageinfo);

}
