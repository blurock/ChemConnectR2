package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


import gwt.material.design.client.constants.Color;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
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

	public void fillInParameters(SubsystemInformation subsysteminfo) {
		ArrayList<String> parameternames = new ArrayList<String>();
		SubSystemParameters parameters = subsysteminfo.getAttributes();
		createParameterSet(parameters.getDirect(), parameternames);
		createParameterSet(parameters.getListedInhierated(), parameternames);
		createParameterSet(parameters.getRestInhierated(), parameternames);
		
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ParameterSetInfoCallback callback = new ParameterSetInfoCallback(structuremap);
		async.getParameterInfo(parameternames,callback);
	}
	
	public void fillInSpecifications(SubsystemInformation subsysteminfo) {
		
		Set<SetOfObservationsInformation> observations = subsysteminfo.getObservations();
		for(SetOfObservationsInformation obs : observations) {
			PrimitiveParameterValueInformation info = (PrimitiveParameterValueInformation) obs;
			row.addStructure(info);

		}
		
	}

	private void createParameterSet(Set<String> set, ArrayList<String> parameternames) {
		parameternames.addAll(set);
		for(String parameter : set) {
			PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
			info.setPropertyType(parameter);
			PrimitiveDataStructureBase base = row.addStructure(info);
			structuremap.put(parameter, base);
		}
	}
}
