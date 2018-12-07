package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProtocolDefinitionPlace extends Place {
	private String titleName;

	public ProtocolDefinitionPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ProtocolDefinitionPlace> {
		@Override
		public String getToken(ProtocolDefinitionPlace place) {
			return place.getTitleName();
		}
		@Override
		public ProtocolDefinitionPlace  getPlace(String token)  {
			return new ProtocolDefinitionPlace(token);
		}
	}

}
