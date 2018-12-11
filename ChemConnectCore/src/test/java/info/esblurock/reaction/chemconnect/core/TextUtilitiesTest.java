package info.esblurock.reaction.chemconnect.core;


import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;

public class TextUtilitiesTest {

	@Test
	public void test() {
		String name = "Administration-BlurockConsultingAB-RCMPublishedResults-rcm-matcorrs";
		String simple = TextUtilities.extractSimpleNameFromCatalog(name);
		System.out.println("extractSimpleNameFromCatalog '" +  simple + "'");
	}

}
