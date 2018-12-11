package info.esblurock.reaction.core.server.concept;


import org.junit.Test;

public class TextUtilitiesTest {

	@Test
	public void test() {
		String name = "Administration-DatasetCatalogHierarchy-Administration-sethier-BlurockConsultingAB-sethier";
		int pos = name.indexOf("-");
		String simple = null;
		while(pos > 0) {
			name = name.substring(pos+1);
			pos = name.indexOf("-");
			if(pos > 0) {
				simple = name.substring(0,pos);
			}
		}
		System.out.println(simple);
	}

}
