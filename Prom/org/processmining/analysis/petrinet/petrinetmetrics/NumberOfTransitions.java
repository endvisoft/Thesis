package org.processmining.analysis.petrinet.petrinetmetrics;

import org.processmining.framework.models.petrinet.PetriNet;

public class NumberOfTransitions implements ICalculator {
	private PetriNet net;
	private final static String TYPE = "Size";
	private final static String NAME = "Transitions";

	public NumberOfTransitions(PetriNet net) {
		super();
		this.net = net;
	}

	public String Calculate() {
		int result = net.getTransitions().size();
		String output = "" + result;
		return output;
	}

	public String getName() {
		return NumberOfTransitions.NAME;
	}

	public String getType() {
		return NumberOfTransitions.TYPE;
	}

	public String VerifyBasicRequirements() {
		return ".";
	}

}
