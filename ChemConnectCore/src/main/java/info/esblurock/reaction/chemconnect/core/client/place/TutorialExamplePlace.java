package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TutorialExamplePlace extends Place {
	private String titleName;

	public TutorialExamplePlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<TutorialExamplePlace> {
		@Override
		public String getToken(TutorialExamplePlace place) {
			return place.getTitleName();
		}
		@Override
		public TutorialExamplePlace  getPlace(String token)  {
			return new TutorialExamplePlace(token);
		}
	}

}
