package info.esblurock.reaction.chemconnect.core.client.administration;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;

public interface ChemConnectDataStructureInterface {
	public void setIdentifer(DatabaseObject obj);
	public MaterialPanel getModalPanel();
	public MaterialCollapsible getInfoContentCollapisble();
}
