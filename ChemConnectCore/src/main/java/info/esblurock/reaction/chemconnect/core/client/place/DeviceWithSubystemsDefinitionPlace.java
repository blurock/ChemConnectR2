package info.esblurock.reaction.chemconnect.core.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DeviceWithSubystemsDefinitionPlace extends Place {
	private String titleName;

	public DeviceWithSubystemsDefinitionPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<DeviceWithSubystemsDefinitionPlace> {
		@Override
		public String getToken(DeviceWithSubystemsDefinitionPlace place) {
			return place.getTitleName();
		}
		@Override
		public DeviceWithSubystemsDefinitionPlace  getPlace(String token)  {
			return new DeviceWithSubystemsDefinitionPlace(token);
		}
	}

}
