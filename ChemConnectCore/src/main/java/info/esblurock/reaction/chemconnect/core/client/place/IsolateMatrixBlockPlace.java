package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class IsolateMatrixBlockPlace extends Place {
	private String titleName;

	public IsolateMatrixBlockPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<IsolateMatrixBlockPlace> {
		@Override
		public String getToken(IsolateMatrixBlockPlace place) {
			return place.getTitleName();
		}
		@Override
		public IsolateMatrixBlockPlace  getPlace(String token)  {
			return new IsolateMatrixBlockPlace(token);
		}
	}

}
