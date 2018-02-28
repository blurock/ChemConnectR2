package info.esblurock.reaction.core.server.ontology;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.observations.SetOfObservationsTransfer;
import info.esblurock.reaction.core.server.services.ContactDatabaseAccessImpl;

public class SetOfObservations {

	@Test
	public void test() {
		String obs = "dataset:BurnerPlateObservations";
		ContactDatabaseAccessImpl impl = new ContactDatabaseAccessImpl();
		SetOfObservationsTransfer transfer = impl.getSetOfObservationsInformation(obs);
		System.out.println(transfer);
	}

}
