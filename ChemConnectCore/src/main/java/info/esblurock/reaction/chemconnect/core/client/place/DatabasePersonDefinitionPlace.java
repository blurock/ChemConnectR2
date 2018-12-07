package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DatabasePersonDefinitionPlace extends Place {
	private String titleName;

	public DatabasePersonDefinitionPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<DatabasePersonDefinitionPlace> {
		@Override
		public String getToken(DatabasePersonDefinitionPlace place) {
			return place.getTitleName();
		}
		@Override
		public DatabasePersonDefinitionPlace  getPlace(String token)  {
			return new DatabasePersonDefinitionPlace(token);
		}
	}

}
