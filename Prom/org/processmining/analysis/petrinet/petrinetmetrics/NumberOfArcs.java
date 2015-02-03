package org.processmining.analysis.petrinet.petrinetmetrics;

import org.processmining.framework.models.petrinet.PetriNet;

public class NumberOfArcs implements ICalculator {
	private PetriNet net;
	private final static String TYPE = "Size";
	private final static String NAME = "Arcs";

	public NumberOfArcs(PetriNet net) {
		super();
		this.net = net;
	}

	public String Calculate() {
		int n = net.getEdges().size();
		String output = "" + n;
		return output;
	}

	public String getName() {
		return NumberOfArcs.NAME;
	}

	public String getType() {
		return NumberOfArcs.TYPE;
	}

	public String VerifyBasicRequirements() {
		return ".";
	}

}
