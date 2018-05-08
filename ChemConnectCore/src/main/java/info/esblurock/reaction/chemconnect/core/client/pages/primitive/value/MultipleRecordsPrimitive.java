package info.esblurock.reaction.chemconnect.core.client.pages.primitive.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import gwt.material.design.client.constants.Color;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.CreatePrimitiveStructure;
import info.esblurock.reaction.chemconnect.core.client.pages.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.client.resources.TextUtilities;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccess;
import info.esblurock.reaction.chemconnect.core.common.client.async.ContactDatabaseAccessAsync;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveInterpretedInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.PrimitiveParameterValueInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubSystemParameters;
import info.esblurock.reaction.chemconnect.core.data.transfer.graph.SubsystemInformation;

public class MultipleRecordsPrimitive extends PrimitiveDataStructureBase {

	MultipleRecordsPrimitiveRow row;
	Map<String, PrimitiveDataStructureBase> structuremap;
	CreatePrimitiveStructure create;
	
	public MultipleRecordsPrimitive() {
		structuremap = new HashMap<String, PrimitiveDataStructureBase>();
		row = null;
		create = null;
	}
	public MultipleRecordsPrimitive(String structure,
			CreatePrimitiveStructure create) {
		this.create = create;
		row = new MultipleRecordsPrimitiveRow(structure,create);
		row.setIdentifier(getDatabaseObject());
		structuremap = new HashMap<String, PrimitiveDataStructureBase>();
		add(row);
		this.setRowColorMultiple(Color.GREY_LIGHTEN_1);
	}

	public void addPrimitive(ArrayList<String> lst, Map<String,DatabaseObject> objectmap) {
		for(String id : lst) {
			DatabaseObject obj = objectmap.get(id);
			PrimitiveDataStructureInformation base = new PrimitiveDataStructureInformation();
			PrimitiveInterpretedInformation info = new PrimitiveInterpretedInformation(base, obj);
			PrimitiveDataStructureBase structure = row.addStructure(info);
			structuremap.put(id,structure);
		}
	}
	
	public void fillInParameters(DatabaseObject obj, SubsystemInformation subsysteminfo) {
		ArrayList<String> parameternames = new ArrayList<String>();
		SubSystemParameters parameters = subsysteminfo.getAttributes();
		int count = 0;
		createParameterSet(obj, count, parameters.getDirect(), parameternames);
		createParameterSet(obj, count, parameters.getListedInhierated(), parameternames);
		createParameterSet(obj, count, parameters.getRestInhierated(), parameternames);
		
		ContactDatabaseAccessAsync async = ContactDatabaseAccess.Util.getInstance();
		ParameterSetInfoCallback callback = new ParameterSetInfoCallback(obj, structuremap);
		async.getParameterInfo(parameternames,callback);
	}
	
	public void fillInSpecifications(DatabaseObject obj, SubsystemInformation subsysteminfo) {
		Set<SetOfObservationsInformation> observations = subsysteminfo.getObservations();
		for(SetOfObservationsInformation obs : observations) {
			PrimitiveParameterValueInformation info = (PrimitiveParameterValueInformation) obs;
			String subid = obj.getIdentifier() + "-" + TextUtilities.removeNamespace(obs.getTopConcept());
			info.fill(subid, obj.getAccess(), obj.getOwner(), obj.getSourceID());
			row.addStructure(info);
		}
	}

	private void createParameterSet(DatabaseObject obj, int count, Set<String> set, ArrayList<String> parameternames) {
		parameternames.addAll(set);
		for(String parameter : set) {
			PrimitiveParameterValueInformation info = new PrimitiveParameterValueInformation();
			String subid = obj.getIdentifier() + "-" + TextUtilities.removeNamespace(parameter);
			info.fill(subid, obj.getAccess(), obj.getOwner(), obj.getSourceID());
			info.setPropertyType(parameter);
			PrimitiveDataStructureBase base = row.addStructure(info);
			structuremap.put(parameter, base);
		}
	}
	public void setIdentifier(DatabaseObject obj) {
		super.setIdentifier(obj);
		if(row != null) {
			row.setIdentifier(obj);
		}
	}
}
