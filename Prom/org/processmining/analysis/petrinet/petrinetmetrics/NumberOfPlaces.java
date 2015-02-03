package org.processmining.analysis.petrinet.petrinetmetrics;

import org.processmining.framework.models.petrinet.PetriNet;

public class NumberOfPlaces implements ICalculator {
	private PetriNet net;
	private final static String TYPE = "Size";
	private final static String NAME = "Places";

	public NumberOfPlaces(PetriNet net) {
		super();
		this.net = net;
	}

	public String Calculate() {
		int result = net.getPlaces().size();
		String output = "" + result;
		return output;
	}

	public String getName() {
		return NumberOfPlaces.NAME;
	}

	public String getType() {
		return NumberOfPlaces.TYPE;
	}

	public String VerifyBasicRequirements() {
		return ".";
	}

}
