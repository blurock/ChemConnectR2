package info.esblurock.reaction.chemconnect.core.client.resources;

import com.google.gwt.user.client.Window;

public class TextUtilities {

	public static String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String ans = name;
		if(pos >= 0) {
			ans = name.substring(pos+1);
		}
		return ans;
	}
}
