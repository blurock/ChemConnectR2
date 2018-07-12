package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ChemConnectObservationPlace  extends Place {
	private String titleName;

	public ChemConnectObservationPlace(String titleName) {
		this.titleName = titleName;
	}

	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ChemConnectObservationPlace> {
		@Override
		public ChemConnectObservationPlace getPlace(String token) {
			return new ChemConnectObservationPlace(token);
		}
		@Override
		public String getToken(ChemConnectObservationPlace place) {
			return place.getTitleName();
		}
	}

}
