package info.esblurock.reaction.core.server.protocol;

//import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.ProtocolSetupTransfer;
import info.esblurock.reaction.core.server.db.protocol.ProtocolSetupUtilities;

public class SetUpProtocolInformation {

	@Test
	public void test() {
		String user = "Administration";
		String protocol1 = "";
		try {
			ProtocolSetupTransfer transfer1 = ProtocolSetupUtilities.observationsForProtocol(protocol1, user);
			System.out.println(transfer1.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
