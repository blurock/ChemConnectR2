package info.esblurock.reaction.ontology.test.dataset;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.data.transfer.structure.ChemConnectDataStructure;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;
/*
 * getChemConnectDataStructure("dataset:ChemConnectDataStructure");
------------ SetOfChemConnectDataStructureElements ------------
DataType: dataset:ChemConnectDataStructure
	Records
		dataset:DescriptionDataData: dcat:record	 (singlet)
		dataset:DataSetReference: dcat:record	 (some)
	LinkedTo
		dataset:Consortium: org:linkedTo	 (some)
	Other

	MapToChemConnectCompoundDataStructure
		ChemConnectCompoundDataStructure: dataset:DataSetReference
			dataset:ReferenceTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DOI(dcterms:hasPart):  datacite:PrimaryResourceIdentifier  (ShortStringLabel):  single
			dataset:ReferenceString(dcterms:hasPart):  dcterms:description  (OneLine):  single
			dataset:NameOfPerson(dcterms:hasPart):  foaf:name  (NameOfPerson):  multiple
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:DescriptionDataData
			dataset:DescriptionKeyword(dcterms:hasPart):  dcat:keyword  (Keyword):  multiple
			dataset:DescriptionAbstract(dcterms:hasPart):  dcterms:description  (Paragraph):  single
			dataset:DataSpecification(dcterms:hasPart):  dataset:DataSpecification  (DataSpecification):  single
			dataset:DescriptionTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DateCreated(dcterms:hasPart):  dcterms:created  (DateObject):  single
			dataset:SourceKey(dcterms:hasPart):  dcat:dataset  (Classification):  single
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:NameOfPerson
			dataset:givenName(dcterms:hasPart):  foaf:givenName  (ShortStringLabel):  single
			dataset:familyName(dcterms:hasPart):  foaf:familyName  (ShortStringLabel):  single
			dataset:UserTitle(dcterms:hasPart):  foaf:title  (Classification):  single
		-------------------------------------------------------


getChemConnectDataStructure("dataset:Instrument");
------------ SetOfChemConnectDataStructureElements ------------
DataType: dataset:ChemConnectDataStructure
	Records
		dataset:DescriptionDataData: dcat:record	 (singlet)
		dataset:DataSetReference: dcat:record	 (some)
	LinkedTo
		dataset:Consortium: org:linkedTo	 (some)
	Other

	MapToChemConnectCompoundDataStructure
		ChemConnectCompoundDataStructure: dataset:DataSetReference
			dataset:ReferenceTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DOI(dcterms:hasPart):  datacite:PrimaryResourceIdentifier  (ShortStringLabel):  single
			dataset:ReferenceString(dcterms:hasPart):  dcterms:description  (OneLine):  single
			dataset:NameOfPerson(dcterms:hasPart):  foaf:name  (NameOfPerson):  multiple
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:DescriptionDataData
			dataset:DescriptionKeyword(dcterms:hasPart):  dcat:keyword  (Keyword):  multiple
			dataset:DescriptionAbstract(dcterms:hasPart):  dcterms:description  (Paragraph):  single
			dataset:DataSpecification(dcterms:hasPart):  dataset:DataSpecification  (DataSpecification):  single
			dataset:DescriptionTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DateCreated(dcterms:hasPart):  dcterms:created  (DateObject):  single
			dataset:SourceKey(dcterms:hasPart):  dcat:dataset  (Classification):  single
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:NameOfPerson
			dataset:givenName(dcterms:hasPart):  foaf:givenName  (ShortStringLabel):  single
			dataset:familyName(dcterms:hasPart):  foaf:familyName  (ShortStringLabel):  single
			dataset:UserTitle(dcterms:hasPart):  foaf:title  (Classification):  single
		-------------------------------------------------------


getChemConnectDataStructure("dataset:Instrument");
------------ SetOfChemConnectDataStructureElements ------------
DataType: dataset:ChemConnectDataStructure
	Records
		dataset:DescriptionDataData: dcat:record	 (singlet)
		dataset:DataSetReference: dcat:record	 (some)
	LinkedTo
		dataset:Consortium: org:linkedTo	 (some)
	Other

	MapToChemConnectCompoundDataStructure
		ChemConnectCompoundDataStructure: dataset:DataSetReference
			dataset:ReferenceTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DOI(dcterms:hasPart):  datacite:PrimaryResourceIdentifier  (ShortStringLabel):  single
			dataset:ReferenceString(dcterms:hasPart):  dcterms:description  (OneLine):  single
			dataset:NameOfPerson(dcterms:hasPart):  foaf:name  (NameOfPerson):  multiple
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:DescriptionDataData
			dataset:DescriptionKeyword(dcterms:hasPart):  dcat:keyword  (Keyword):  multiple
			dataset:DescriptionAbstract(dcterms:hasPart):  dcterms:description  (Paragraph):  single
			dataset:DataSpecification(dcterms:hasPart):  dataset:DataSpecification  (DataSpecification):  single
			dataset:DescriptionTitle(dcterms:hasPart):  dcterms:title  (OneLine):  single
			dataset:DateCreated(dcterms:hasPart):  dcterms:created  (DateObject):  single
			dataset:SourceKey(dcterms:hasPart):  dcat:dataset  (Classification):  single
		-------------------------------------------------------

		ChemConnectCompoundDataStructure: dataset:NameOfPerson
			dataset:givenName(dcterms:hasPart):  foaf:givenName  (ShortStringLabel):  single
			dataset:familyName(dcterms:hasPart):  foaf:familyName  (ShortStringLabel):  single
			dataset:UserTitle(dcterms:hasPart):  foaf:title  (Classification):  single
		-------------------------------------------------------



 */
public class ChemConnectDataStructureTest {

	@Test
	public void test() {
		ChemConnectDataStructure struct1 = DatasetOntologyParsing.getChemConnectDataStructure("dataset:DeviceDescription");
		System.out.println("getChemConnectDataStructure(\"dataset:Instrument\");");
		System.out.println(struct1);
		
		ChemConnectDataStructure struct2 = DatasetOntologyParsing.getChemConnectDataStructure("dataset:DeviceDescription");
		System.out.println("getChemConnectDataStructure(\"dataset:DeviceDescription\");");
		System.out.println(struct2);

		ChemConnectDataStructure struct3 = DatasetOntologyParsing.getChemConnectDataStructure("dataset:Organization");
		System.out.println("getChemConnectDataStructure(\"dataset:Organization\");");
		System.out.println(struct3);

		ChemConnectDataStructure struct4 = DatasetOntologyParsing.getChemConnectDataStructure("dataset:DatasetCatalogHierarchy");
		System.out.println("getChemConnectDataStructure(\"dataset:DatasetCatalogHierarchy\");");
		System.out.println(struct4);
	}

}
