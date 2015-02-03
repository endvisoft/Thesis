package petrivisual;

import java.util.ArrayList;

import org.processmining.framework.models.petrinet.Transition;

public class ResourceTransition {
	
	Transition transition;
	ArrayList<String> roles = new ArrayList<String>();
	
	public ResourceTransition(Transition tra) {
		transition = tra;
	}	

	public ArrayList<String> getRoles() {		
		return roles;
	}

	public void addResource(String resourceValue) {
		String[] resources;
		resources = resourceValue.split("/");
		for (String resource	:	resources)
		{
			roles.add(resource);
		}		
	}

	public Transition getTransition() {	
		return transition;
	}

	public String getIdentifier() {
		return transition.getIdentifier();		
	}
	
	
	

}
