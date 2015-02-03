package org.processmining.analysis.epc.epcmetrics;

import org.processmining.framework.models.epcpack.ConfigurableEPC;

public class Modularity implements ICalculator {
	private ConfigurableEPC epc;
	private final static String TYPE = "Modularity";
	private final static String NAME = "BA Tree";

	public Modularity(ConfigurableEPC aEpc) {
		epc = aEpc;
	}

	public String Calculate() {
		String output = "Not implemented";
		return output;
	}

	public String getName() {
		return Modularity.NAME;
	}

	public String getType() {
		return Modularity.TYPE;
	}

	public String VerifyBasicRequirements() {
		return ".";
	}

}
