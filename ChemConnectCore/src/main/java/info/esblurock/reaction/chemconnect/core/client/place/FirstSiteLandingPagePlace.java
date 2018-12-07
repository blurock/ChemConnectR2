package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FirstSiteLandingPagePlace extends Place {
	private String titleName;

	public FirstSiteLandingPagePlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<FirstSiteLandingPagePlace> {
		@Override
		public String getToken(FirstSiteLandingPagePlace place) {
			return place.getTitleName();
		}
		@Override
		public FirstSiteLandingPagePlace  getPlace(String token)  {
			return new FirstSiteLandingPagePlace(token);
		}
	}

}
