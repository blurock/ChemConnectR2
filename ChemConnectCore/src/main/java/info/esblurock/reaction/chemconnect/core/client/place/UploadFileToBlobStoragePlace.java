package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UploadFileToBlobStoragePlace extends Place {
	private String titleName;

	public UploadFileToBlobStoragePlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<UploadFileToBlobStoragePlace> {
		@Override
		public String getToken(UploadFileToBlobStoragePlace place) {
			return place.getTitleName();
		}
		@Override
		public UploadFileToBlobStoragePlace  getPlace(String token)  {
			return new UploadFileToBlobStoragePlace(token);
		}
	}

}
