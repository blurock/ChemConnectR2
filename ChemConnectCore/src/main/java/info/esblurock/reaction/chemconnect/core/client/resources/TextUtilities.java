package info.esblurock.reaction.chemconnect.core.client.resources;


import gwt.material.design.client.ui.MaterialLink;

public class TextUtilities {
	
	public static String extractSimpleNameFromCatalog(String name) {
		int pos = name.indexOf("-");
		String simple = null;
		while(pos > 0) {
			name = name.substring(pos+1);
			pos = name.indexOf("-");
			if(pos > 0) {
				simple = name.substring(0,pos);
			}
		}
		return simple;
	}

	public static String removeNamespace(String name) {
		int pos = name.indexOf(":");
		String ans = name;
		if(pos >= 0) {
			ans = name.substring(pos+1);
		}
		return ans;
	}
	
	public static void setText(MaterialLink link, String text,String defaultText) {
		if(text != null) {
			if(text.length() > 0) {
				link.setText(text);
			} else {
				link.setText(defaultText);
			}
		} else {
			link.setText(defaultText);
		}
	}

}
