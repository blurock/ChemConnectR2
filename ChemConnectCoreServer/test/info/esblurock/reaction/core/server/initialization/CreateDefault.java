package info.esblurock.reaction.core.server.initialization;

//import static org.junit.Assert.*;

import org.junit.Test;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.data.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.data.transfer.structure.DatabaseObjectHierarchy;

public class CreateDefault {

	@Test
	public void test() {
		/*
		Storage storage = StorageOptions.getDefaultInstance().getService();
	    String bucketName = "chemconnect"; // Change this to something unique
	    Bucket bucket = storage.create(BucketInfo.of(bucketName));
	    // List the blobs in a particular bucket
	    System.out.println("My blobs:");
	    for (Blob currentBlob : bucket.list().iterateAll()) {
	    	System.out.println(currentBlob);
	    }
*/
		
		DatabaseObject obj = new DatabaseObject("AdministrationCatalog","Public","Administration","1" );
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"");
		DataCatalogID name = new DataCatalogID(structure,"Catalog-Base","Catalog","Simple");
		
		/*
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("createDatasetCatalogHierarchy\n");
		System.out.println("--------------------------------------------------------------------\n");
		DatabaseObjectHierarchy cat = CreateDefaultObjectsFactory.createDatasetCatalogHierarchy(obj);
		System.out.println(cat.toString());
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("fillOrganization\n");
		System.out.println("--------------------------------------------------------------------\n");
		String organizationid = "Blurock Consulting AB";
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillOrganization(obj, organizationid);
		System.out.println(org.toString());
		
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("fillMinimalPersonDescription\n");
		System.out.println("--------------------------------------------------------------------\n");
		String userClassification = "Company";
		String purpose = "";
		NameOfPerson person = new NameOfPerson(obj,"Mr.","Edward","Blurock");
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillMinimalPersonDescription(obj, userClassification, person);
		System.out.println(org.toString());
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("fillSubSystemDescription\n");
		System.out.println("--------------------------------------------------------------------\n");
		String devicename = "HeatFluxBurner";
		String purpose = "dataset:FlameVelocityMeasurements";
		String concept = "dataset:FlameStudies";
		DatabaseObjectHierarchy org = CreateDefaultObjectsFactory.fillSubSystemDescription(obj, devicename, purpose, concept);
		System.out.println(org.toString());

		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("fillCataogHierarchyForUser\n");
		System.out.println("--------------------------------------------------------------------\n");
		String userid = "Blurock";
		String organizationid = "Blurock Consulting AB";
		DatabaseObjectHierarchy usrcat = CreateDefaultObjectsFactory.fillCataogHierarchyForUser(obj,userid,organizationid);
		System.out.println(usrcat.toString());
		
		
		DatabaseObjectHierarchy param = CreateDefaultObjectsFactory.fillParameterSpecification(obj,"dataset:ThermocouplePositionInBurner");
		System.out.println(param.toString());
	*/	
		
		DatabaseObjectHierarchy hierarchy = CreateDefaultObjectsFactory.fillSetOfObservations(obj,"dataset:BurnerPlateObservations",
				"Set of burner plate observations","dataset:HeatFluxBurnerObservation","dataset:LaminarFlame",name);
		System.out.println("fillSetOfObservations\n" + hierarchy.toString());
		
		/*
		NameOfPerson person = new NameOfPerson(obj,"Mr.", "Edward", "Blurock");
		
		DatabaseObjectHierarchy hierarchy = 
		CreateDefaultObjectsFactory.createMinimalPersonDescription(obj, "UserClass", "Purpose",person);
		
		System.out.println(hierarchy);
		
		DatabaseObject orgobj = new DatabaseObject("BlurockAB","Public","Administration","1" );
		DatabaseObjectHierarchy hierarchy2 = 
		CreateDefaultObjectsFactory.createMinimalOrganization(orgobj,"Blurock Consulting AB", "dataset:Organization");

		System.out.println(hierarchy2);
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("createSubSystemDescription\n");
		System.out.println("--------------------------------------------------------------------\n");
		DatabaseObject subobj = new DatabaseObject("HeatFluxBurner","Public","Administration","1" );
		
		String devicename = "Heat Flux Burner";
		String purpose = "dataset:DeviceComponentSpecification";
		String concept = "dataset:HeatFluxBurnerConfiguration";
		DatabaseObjectHierarchy hierarchy3 = 
				CreateDefaultObjectsFactory.createSubSystemDescription(subobj,devicename, purpose, concept);
		System.out.println(hierarchy3);
		
		
		System.out.println("--------------------------------------------------------------------\n");
		System.out.println("createCataogHierarchyForUser\n");
		System.out.println("--------------------------------------------------------------------\n");
		DatabaseObject catobj = new DatabaseObject("Catalog", "Administration","Administration","1");
		DatabaseObjectHierarchy cat = CreateDefaultObjectsFactory.createCataogHierarchyForUser(catobj, "Administration", "BlurockConsultingAB");
		System.out.println(cat);
		*/
	}

}
