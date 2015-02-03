package petrivisual;

import java.util.ArrayList;

import org.processmining.framework.models.ModelGraphPanel;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.Transition;

public class ResourcePetriNet {
	
	PetriNet petriNet;
	RPetriNet rPetriNet;
	ResourceTransitionList resourceList = new ResourceTransitionList();	

	public ArrayList<ResourceTransition> getResourceTransitions() {
		return resourceList.resourcesTransitions;		
	}
	
	public ResourceTransition getResourceTransition(Transition transition)
	{
		return resourceList.getResourceTransition(transition);
	}
	
	public ResourcePetriNet(PetriNet pn)
	{
		petriNet = pn;
	}

	public void addResource(String nameValue, String resourceValue) {
		Transition tra = null;
		ResourceTransition resourceTransition = null;
		ArrayList<Transition> transitionList = petriNet.getTransitions();
		for (Transition transition	:	transitionList)
		{
			if (transition.getIdentifier().equals(nameValue))
			{
				tra = transition;
				break;
			}
		}
		if (tra != null)
		{			
			resourceTransition = resourceList.findResourceTransition(tra);
			resourceTransition.addResource(resourceValue);
		}
		
	}
	public PetriNet getPetriNet()
	{
		return petriNet;
	}

	public ModelGraphPanel getGrappaVisualization() {
		ModelGraphPanel result = petriNet.getGrappaVisualization();
		return result;
	}
}
