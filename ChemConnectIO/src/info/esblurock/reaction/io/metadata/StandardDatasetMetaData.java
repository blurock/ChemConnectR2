package info.esblurock.reaction.io.metadata;

public class StandardDatasetMetaData {
	
	
	public static String identifierKeyS = "dc:identifier";
	public static String ownerKeyS = "dcterms:publisher";
	public static String accessKeyS = "dataset:accessibility";
	public static String sourceIDS = "dataset:sourceID";
	
	public static String recordS = "dcat:record";
	public static String linkS = "dcat:link";
	
	public static String chemConnectDataStructure = "dataset:ChemConnectDataStructure";
	
	/* DescriptionDataData
	 */
	public static String objectdescriptionKeyS = "dct:description";
	
	public static String dataSetReferenceS = "terms:BibliographicResource";
	public static String dataSetReferenceDOIS = "datacite:doi";
	public static String dataSetReferenceISBNS = "datacite:isbn";
	public static String dataSetReferenceHTTPS = "datacite:url";
	public static String descriptionDataDataS = "dc:description";
	public static String consortiumS = "dataset:Consortium";
	public static String parameterSetDescriptionsS = "rdf:Property";
	public static String parameterObjectLinkS = "skos:mappingRelation";
	public static String parameterPurposeConceptPairS = "prov:influenced";
	public static String measurementValues = "qb:MeasureProperty";
	public static String dimensionValues = "qb:DimensionProperty";
	public static String measureSpec = "qb:measure";
	public static String dimensionSpec = "qb:dimension";
	public static String chemConnectCompoundMultipleS = "dataset:ChemConnectCompoundMultiple";
	public static String elementType = "dataset:elementType";
	
	public static String CatalogBaseName = "skos:hasTopConcept";
	public static String DataCatalogIDID = "qb:ObservationGroup";
	public static String ContactHasSiteID = "foaf:page";
	public static String DataCatalog = "skos:inScheme";
	public static String SimpleCatalogName = "qb:DataSet";
	public static String catalogPath = "dataset:CatalogPath";
	public static String matrixSpecificationCorrespondenceSetID = "dataset:matrixcorr";
	public static String matrixSpecificationCorrespondenceSet = "dataset:MatrixSpecificationCorrespondenceSet";
	public static String observationMatrixValues = "dataset:ObservationMatrixValues";
	public static String observationMatrixValuesID = "dataset:matrix";
	public static String observationSpecificationID = "dataset:ObservationSpecs";
	public static String matrixBlockDefinitionID = "qb:sliceStructure";
	public static String matrixSpecificationCorrespondenceID = "qb:ObservationGroup";
	public static String matrixBlockDefinition = "dataset:MatrixBlockDefinition";
	public static String matrixSpecificationCorrespondence = "dataset:MatrixSpecificationCorrespondence";
	
	public static String lastColumnInMatrix = "dataset:lastColumnInMatrix";
	public static String startColumnInMatrix = "dataset:startColumnInMatrix";
	public static String lastRowInMatrix = "dataset:lastRowInMatrix";
	public static String startRowInMatrix = "dataset:startRowInMatrix";
	public static String matrixColumn = "dataset:matrixColumn";
	public static String observationValueRowTitleID = "qb:SliceKey";
	public static String observationValueRowID = "qb:Slice";
	public static String observationValueRowTitle = "dataset:ObservationValueRowTitle";
	public static String observationValueRow = "dataset:ObservationValueRow";
	public static String position = "qb:order";
	


	public static String hasSiteS = "foaf:homepage";
	public static String siteOfS = "dataset:HttpAddress";
	public static String siteTypeS = "dataset:HttpAddressType";
	
	public static String titleKeyS = "dcterms:title";
	public static String descriptionKeyS = "dcterms:description";
	public static String datasetKeyS = "dcat:dataset";
	public static String dataTypeKeyS = "dcterms:type";
	public static String keywordKeyS = "dcat:keyword";
	public static String sourceDateKeyS = "dcterms:created";
	public static String setOfKeywordsS = "dataset:SetOfKeywords";

	public static String parentCatalogS = "dcterms:CatalogRecord";
	public static String parameterDescriptionS = "qb:observation";
	
	public static String dataStructureIdentifierS = "qb:structure";
	public static String linkConceptTypeS = "skos:related";
	//public static String dataConceptTypeS = "skos:Concept";
	
	public static String orginfoKeyS = "org:Organization";
	public static String contactKeyS = "vcard:Contact";
	public static String contactTypeS = "dataset:ContactType";
	public static String locationKeyS = "vcard:Location";
	public static String userAccountS = "prov:SoftwareAgent";
	public static String databaseUserS = "vcard:Individual";
	public static String personS = "foaf:Person";
	public static String personalDescriptionS = "dataset:PersonalDescription";
		
	public static String  streetaddressKeyS = "vcard:street-address";           
	public static String  localityKeyS = "vcard:locality";
	public static String  postalcodeKeyS = "vcard:postal-code";
	public static String  countryKeyS = "vcard:country-name";
	public static String  gpsCoordinatesID = "geo:location";
	public static String  latitudeKeyS = "geo:lat";
	public static String  longitudeKeyS = "geo:long";

	public static String  emailKeyS = "vcard:email";
	public static String  hassiteKeyS = "foaf:homepage";
	public static String  siteofKeyS = "foaf:workInfoHomepage";
	
	public static String organizationUnit = "org:OrganizationalUnit";
	public static String organizationClassification = "org:role";
	public static String organizationName = "org:FormalOrganization";
	public static String subOrganizationOf = "org:unitOf";
	
	public static String titleName = "foaf:title";
	public static String givenName = "foaf:givenName";
	public static String familyName = "foaf:familyName";
	
	public static String userClassification = "vcard:role";
	public static String userNameID = "foaf:name";
	
	public static String password = "dataset:password";
	public static String userrole = "foaf:UserAccountRole";

	public static String organizationDataTypeS = "foaf:Organisation";
	
	public static String referenceTitle = "dcterms:title";
	public static String referenceBibliographicString = "dcterms:isReferencedBy";
	public static String referenceAuthors = "foaf:name";
	public static String referenceDOI = "datacite:PrimaryResourceIdentifier";

	public static String deviceSensorS = "ssn:Sensor";
	public static String instrumentS = "ssn:Device";
	public static String chemConnectActivityS = "prov:Activity";
	//public static String dataPointConceptS = "skos:related";

	
	public static String parameterValueS = "qb:ComponentProperty";
	public static String parameterSpecificationS = "qb:ComponentSpecification";
	public static String deviceTypeS = "dataset:SubSystemType";
	public static String methodologyTypeS = "dataset:MethodologyType";
	public static String subSystems = "ssn:hasSubSystem";
	public static String valueAsStringS = "dataset:ValueAsString";
	public static String valueUncertaintyS = "qudt:standardUncertainty";
	public static String parameterLabelS = "skos:prefLabel";
	public static String dataPointUncertaintyS = "dataset:uncertainty";
	public static String unitClassS = "qudt:SystemOfQuantities";
	public static String unitsS = "qudt:Unit";
	public static String unitsOfValueS = "qudt:QuantityKind";
	public static String purposeConceptPairS = "prov:influenced";
	//public static String dataTypeConceptS = "skos:Concept";
	public static String purposeS = "dataset:purpose";
	public static String dynamicTypeS = "dataset:dynamicType";
	
	/*
	public static String contactInfoDataID = "dataset:ContactInfoDataID";
	public static String organizationID = "dataset:OrganizationID";
	public static String personalDescriptionID = "dataset:personalDescriptionID";
	public static String contactLocationInformationID = "dataset:ContactLocationInformationID";
	public static String descriptionDataDataID = "dataset:DescriptionDataDataID";
	public static String organizationDescriptionID = "dataset:OrganizationDescriptionID";
	*/
	public static String DatabaseUserIDReadAccess = "dataset:userReadAccess";
	public static String DatabaseUserIDWriteAccess = "dataset:userWriteAccess";

	public static String UserAccountInformation = "dataset:UserAccountInformation";
	
	public static String conversionStructure = "dataset:conversionType";
	
	public static String propertyTypeS = "dataset:propertyType";
	public static String valueTypeS = "dataset:valueType";
	public static String valueS = "dataset:value";
	
	public static String unitS = "dataset:unit";
	public static String unitclassS = "dataset:unitClass";
	public static String conceptS = "dataset:concept";
	public static String uncertaintyValueS = "dataset:uncertaintyValue";
	public static String uncertaintyTypeS = "dataset:uncertaintyType";
	
	public static String inputTypeS = "dataset:inputType";
	public static String outputTypeS = "dataset:outputType";

	public static String yamlFileType = "dataset:FileTypeYaml";
	public static String fileTypeS = "dataset:fileType";
	public static String delimitorS = "dataset:delimitor";
	public static String observationSpecs = "dataset:ObservationSpecs";
	public static String parameterValues = "dataset:ParameterValues";
	public static String observationParameterType = "dataset:observationParameterType";
	public static String parameterSpecifications = "qb:ComponentSpecification";
	public static String specificationLabel = "dataset:specificationLabel";
	
	public static String purposeDefineSubCatagory = "dataset:ChemConnectDefineSubCatagory";
	public static String conceptUserDataCatagory = "dataset:UserDataCatagory";
	public static String conceptOrgDataCatagory = "dataset:OrganizationDataCatagory";
	public static String conceptPublishedResultsCatagory = "dataset:PublishedResultsCatagory";
}
