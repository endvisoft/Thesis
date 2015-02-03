package petrivisual;

import java.util.ArrayList;

import org.processmining.framework.models.petrinet.Transition;

public class ResourceTransitionList {
	ArrayList<ResourceTransition> resourcesTransitions = new ArrayList<ResourceTransition>();

	public ResourceTransition findResourceTransition(Transition tra) {
		ResourceTransition result = null;
		for (ResourceTransition resourceTransiton	:	resourcesTransitions)
		{
			if(resourceTransiton.getTransition().equals(tra))
			{
				result = resourceTransiton;
				break;
			}				
		}
		if (result == null)
		{
			result = new ResourceTransition(tra);
			resourcesTransitions.add(result);
		}
		return result;
	}

	public ResourceTransition getResourceTransition(Transition transition) {
		for (ResourceTransition resourceTransition	:	resourcesTransitions)
		{
			if (resourceTransition.getTransition().equals(transition))
				return resourceTransition;
		}
		return null;
	}

}
