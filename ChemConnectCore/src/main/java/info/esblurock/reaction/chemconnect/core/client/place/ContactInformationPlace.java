package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ContactInformationPlace extends Place {
	private String titleName;

	public ContactInformationPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ContactInformationPlace> {
		@Override
		public String getToken(ContactInformationPlace place) {
			return place.getTitleName();
		}
		@Override
		public ContactInformationPlace  getPlace(String token)  {
			return new ContactInformationPlace(token);
		}
	}

}
