package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MissionStatementPlace extends Place {
	private String titleName;

	public MissionStatementPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<MissionStatementPlace> {
		@Override
		public String getToken(MissionStatementPlace place) {
			return place.getTitleName();
		}
		@Override
		public MissionStatementPlace  getPlace(String token)  {
			return new MissionStatementPlace(token);
		}
	}

}
