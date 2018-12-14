package info.esblurock.reaction.chemconnect.core.data.image;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ImageInformation  extends ChemConnectCompoundDataStructure {
	@Index
	String imageURL;
	@Index
	String imageType;

	public ImageInformation() {
	}

	public ImageInformation(ChemConnectCompoundDataStructure structure) {
		super(structure);
		this.imageURL = "";
		this.imageType = "";
	}
	
	public ImageInformation(ChemConnectCompoundDataStructure structure, String imageURL, String imageType) {
		super(structure);
		this.imageURL = imageURL;
		this.imageType = imageType;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Image Type: " + imageType + "\n");
		build.append(prefix + "Image URL : " + imageURL + "\n");
		return build.toString();
	}	

}
