package info.esblurock.reaction.ontology.test.practice;

//import static org.junit.Assert.*;

import org.junit.Test;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;


public class CreateAnRDF {

	@Test
	public void test() {
		String NS = "http://www.esblurock.info/dataset#";
        String device    = NS+"Device";
        String instrument   = NS+"Instrument";
        //String rcm     = NS+"RapidCompressionMachine";

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        //Resource d = model.createResource(device);
        Resource i = model.createResource(instrument);

     // add the property
     i.addProperty(RDFS.subClassOf, device);
        
       
        model.write(System.out);
        
      }

}
