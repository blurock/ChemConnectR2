package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ChemConnectAdministrationPlace extends Place {
	private String titleName;

	public ChemConnectAdministrationPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ChemConnectAdministrationPlace> {
		@Override
		public String getToken(ChemConnectAdministrationPlace place) {
			return place.getTitleName();
		}
		@Override
		public ChemConnectAdministrationPlace  getPlace(String token)  {
			return new ChemConnectAdministrationPlace(token);
		}
	}

}
