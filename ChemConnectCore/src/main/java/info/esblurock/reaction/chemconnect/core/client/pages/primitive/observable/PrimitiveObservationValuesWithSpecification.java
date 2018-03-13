package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.ObservationsAndSpecificationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;

public class PrimitiveObservationValuesWithSpecification extends PrimitiveDataStructureBase {

	PrimitiveObservationVauesWithSpecificationRow row;
	
	public PrimitiveObservationValuesWithSpecification() {
		super();
		row = new PrimitiveObservationVauesWithSpecificationRow();
		add(row);
	}
	
	public PrimitiveObservationValuesWithSpecification(PrimitiveDataStructureInformation info) {
		super();
		SetOfObservationsInformation primitiveinfo = (SetOfObservationsInformation) info;
		Window.alert("PrimitiveObservationValuesWithSpecification:  " + info.getIdentifier());
		row = new PrimitiveObservationVauesWithSpecificationRow(primitiveinfo);
		add(row);
	}

	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		Window.alert("PrimitiveObservationValuesWithSpecification:  " + primitiveinfo.getIdentifier());
		ObservationsAndSpecificationsInformation info = (ObservationsAndSpecificationsInformation) primitiveinfo;
		row.fill(info);
		add(row);
	}

	public String getIdentifier() {
		return row.getIdentifier();
	}
	public void setIdentifier(DatabaseObject obj) {
		super.setIdentifier(obj);
		row.setIdentifier(obj);
	}

}
