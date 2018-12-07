package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ManageCatalogHierarchyPlace extends Place {
	private String titleName;

	public ManageCatalogHierarchyPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ManageCatalogHierarchyPlace> {
		@Override
		public String getToken(ManageCatalogHierarchyPlace place) {
			return place.getTitleName();
		}
		@Override
		public ManageCatalogHierarchyPlace  getPlace(String token)  {
			return new ManageCatalogHierarchyPlace(token);
		}
	}

}
