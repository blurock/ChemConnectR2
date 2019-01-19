package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ChemConnectPartnersPlace extends Place {
	private String titleName;

	public ChemConnectPartnersPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ChemConnectPartnersPlace> {
		@Override
		public String getToken(ChemConnectPartnersPlace place) {
			return place.getTitleName();
		}
		@Override
		public ChemConnectPartnersPlace  getPlace(String token)  {
			return new ChemConnectPartnersPlace(token);
		}
	}

}
