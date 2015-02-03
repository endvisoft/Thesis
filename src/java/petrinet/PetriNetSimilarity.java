package petrinet;

import javax.servlet.ServletContext;
import org.processmining.framework.models.petrinet.PetriNet;
//import org.processmining.plugins.flexibilitymodel.wsdlsimilarity.PNML;

public abstract class PetriNetSimilarity {

	public abstract float similarityStructural(PetriNet pn1, PetriNet pn2);
	public abstract float similarityBehavioral(PetriNet pn1, PetriNet pn2);
	public abstract String getName();
	public abstract String getDesription();
	//public abstract float similarityStructural(PetriNet pn1, PetriNet pn2, PNML pnml1, PNML pnml2);
        public abstract float similarityStructural(PetriNet pn1, PetriNet pn2, ServletContext ctx);

}
