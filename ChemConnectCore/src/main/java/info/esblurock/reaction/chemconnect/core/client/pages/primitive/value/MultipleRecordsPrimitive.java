package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.constants.Color;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubSystemParameters;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;

public class MultipleRecordsPrimitive extends PrimitiveDataStructureBase {

	MultipleRecordsPrimitiveRow row;
	Map<String, PrimitiveDataStructureBase> structuremap;
	
	public MultipleRecordsPrimitive() {
		structuremap = new HashMap<String, PrimitiveDataStructureBase>();
	}
	public MultipleRecordsPrimitive(String structure,
			CreatePrimitiveStructure create) {
		row = new MultipleRecordsPrimitiveRow(structure,create);
		structuremap = new HashMap<String, PrimitiveDataStructureBase>();
		add(row);
		this.setRowColorMultiple(Color.GREY_LIGHTEN_1);
	}

	public void fillInParameters(String id, SubsystemInformation subsysteminfo) {
		ArrayList<String> parameternames = new ArrayList<String>();
		SubSystemParameters parameters = subsysteminfo.getAttributes();
		int count = 0;
		createParameterSet(id, count, parameters.getDirect(), parameternames);
		createParameterSet(id, count, parameters.getListedInhierated(), parameternames);
		createParameterSet(id, count, parameters.getRestInhierated(), parameternames);
		
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ParameterSetInfoCallback callback = new ParameterSetInfoCallback(id, structuremap);
		async.getParameterInfo(parameternames,callback);
	}
	
	public void fillInSpecifications(String id, SubsystemInformation subsysteminfo) {
		
		Set<SetOfObservationsInformation> observations = subsysteminfo.getObservations();
		for(SetOfObservationsInformation obs : observations) {
			PrimitiveParameterValueInformation info = (PrimitiveParameterValueInformation) obs;
			String subid = id + "-" + TextUtilities.removeNamespace(obs.getTopConcept());
			info.setIdentifier(subid);
			row.addStructure(info);

		}
		
	}

	private void createParameterSet(String id, int count, Set<String> set, ArrayList<String> parameternames) {
		parameternames.addAll(set);
		for(String parameter : set) {
			PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
			String subid = id + "-" + TextUtilities.removeNamespace(parameter);
			info.setIdentifier(subid);
			info.setPropertyType(parameter);
			PrimitiveDataStructureBase base = row.addStructure(info);
			structuremap.put(parameter, base);
		}
	}
}
