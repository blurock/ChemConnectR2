package info.esblurock.reaction.core.server.initialization.yaml;

import java.util.ArrayList;

public class ListOfElementInformation extends ArrayList<YamlDatasetInformation> {
	
	String mainstructure;
	
	public ListOfElementInformation(String mainstructure) {
		this.mainstructure = mainstructure;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("-----------------------------------------------------\n");
		build.append(mainstructure);
		build.append("\n");
		for(YamlDatasetInformation info : this) {
			build.append(info.toString());
		}
		return build.toString();
	}
}
