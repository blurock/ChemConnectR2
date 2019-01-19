package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DataManagementPlace extends Place {
	private String titleName;

	public DataManagementPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<DataManagementPlace> {
		@Override
		public String getToken(DataManagementPlace place) {
			return place.getTitleName();
		}
		@Override
		public DataManagementPlace  getPlace(String token)  {
			return new DataManagementPlace(token);
		}
	}

}
