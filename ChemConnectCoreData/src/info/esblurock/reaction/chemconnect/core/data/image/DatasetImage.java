package info.esblurock.reaction.chemconnect.core.data.image;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class DatasetImage extends ChemConnectDataStructure {
	
	@Index
	String imageInformation;

	public DatasetImage() {
	}
	
	public DatasetImage(ChemConnectDataStructure structure) {
		super(structure);
	}
	
	public DatasetImage(ChemConnectDataStructure structure, String imageInformation) {
		super(structure);
		this.imageInformation = imageInformation;
	}

	public String getImageInformation() {
		return imageInformation;
	}

	public void setImageInformation(String imageInformation) {
		this.imageInformation = imageInformation;
	}
	
	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Image Information: " + imageInformation + "\n");
		return build.toString();
	}	
	

}
