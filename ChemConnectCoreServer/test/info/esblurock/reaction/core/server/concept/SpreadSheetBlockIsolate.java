package info.esblurock.reaction.core.server.concept;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.db.InterpretData;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

public class SpreadSheetBlockIsolate {

	@Test
	public void test() {
		String spreadtype = "dataset:ChemConnectIsolateBlockEntireMatrix";
		
		DatabaseObject obj = new DatabaseObject();
		DatabaseObjectHierarchy hierarchy = InterpretData.SpreadSheetBlockIsolation.createEmptyObject(obj);
		SpreadSheetBlockIsolation block = (SpreadSheetBlockIsolation) hierarchy.getObject();
		
		System.out.println(block.toString());
		DatasetOntologyParsing.spreadSheetBlockType(spreadtype,block);
		System.out.println(block.toString());
	}

}
