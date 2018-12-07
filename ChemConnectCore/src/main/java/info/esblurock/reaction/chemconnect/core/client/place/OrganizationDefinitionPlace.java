package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OrganizationDefinitionPlace extends Place {
	private String titleName;

	public OrganizationDefinitionPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<OrganizationDefinitionPlace> {
		@Override
		public String getToken(OrganizationDefinitionPlace place) {
			return place.getTitleName();
		}
		@Override
		public OrganizationDefinitionPlace  getPlace(String token)  {
			return new OrganizationDefinitionPlace(token);
		}
	}

}
