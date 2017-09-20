package info.esblurock.reaction.chemconnect.core.client.mvp;



import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.client.place.ChemConnectAdministrationPlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
//@WithTokenizers( { HelloPlace.Tokenizer.class, GoodbyePlace.Tokenizer.class })
@WithTokenizers( { ChemConnectAdministrationPlace.Tokenizer.class}
)

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
