package petrinet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import org.processmining.framework.models.ModelGraphVertex;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.Place;
import org.processmining.framework.models.petrinet.Transition;
import org.processmining.importing.pnml.PnmlImport;
import org.processmining.mining.petrinetmining.PetriNetResult;

public class PetriNetUtil {
	private static final boolean debug = false;

	public static PetriNet getPetriNetFromPnmlFile(File file) {
		PetriNet pn = null;
                System.out.println("masuk1");
		try {
                    try (FileInputStream in = new FileInputStream(file)) {
                        pn = getPetriNetFromPnml(in);
                        System.out.println("masuk2");
                    }
                        
		} catch (Exception e) {
		}
		return pn;
	}

	public static PetriNet getPetriNetFromPnmlFile(String fileName) {
		PetriNet pn = null;
		try {
                    try (FileInputStream in = new FileInputStream(fileName)) {
                        pn = getPetriNetFromPnml(in);
                    }
		} catch (Exception e) {
		}
		return pn;
	}

	public static PetriNet getPetriNetFromPnml(InputStream pnml) {
		PetriNet pn = null;
		try {
			PnmlImport pnmlImport = new PnmlImport();
			PetriNetResult result;
                        System.out.println("masuk3");
			result = (PetriNetResult) pnmlImport.importFile(pnml);
                        pn = result.getPetriNet();
			result.destroy();
		} catch (IOException e) {
		}
		return pn;
	}

	public static boolean isInLengthOneLoop(Transition t) {
		HashSet inPlaces = t.getPredecessors();
		HashSet outPlaces = t.getSuccessors();
		if (inPlaces.isEmpty() || outPlaces.isEmpty()) {
			return false;
		}

		inPlaces.retainAll(outPlaces);
            return !inPlaces.isEmpty();

	}

	public static boolean isInLengthTwoLoop(Transition t1, Transition t2) {
		HashSet inPlaces1 = t1.getPredecessors();
		HashSet outPlaces1 = t1.getSuccessors();
		HashSet inPlaces2 = t2.getPredecessors();
		HashSet outPlaces2 = t2.getSuccessors();

		if (inPlaces1.size() == 0 || outPlaces1.size() == 0
				|| inPlaces2.size() == 0 || outPlaces2.size() == 0) {
			return false;
		}

		outPlaces1.retainAll(inPlaces2);
		outPlaces2.retainAll(inPlaces1);

		if (outPlaces1.size() == 0 || outPlaces2.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File dir = new File("e:/test");
		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				String pnFileName = f.getAbsolutePath();
				PetriNet pn = PetriNetUtil.getPetriNetFromPnmlFile(pnFileName);
			}
		}
	}

	/**
	 * Get the Number of Non-Free Choice of Petri Net.
	 * 
	 * @return the number
	 */
	public static int getNumberofNonFreeChoice(PetriNet pn) {
		int nonfreechoice = 0;
		for (Place p : pn.getPlaces())
			if (p.outDegree() > 1) {
				HashSet<ModelGraphVertex> sucNodes = p.getSuccessors();
				for (ModelGraphVertex tnode : sucNodes) {
					HashSet<ModelGraphVertex> preNodes = tnode
							.getPredecessors();
					if (preNodes.size() > 1)
						nonfreechoice++;

				}
			}
		return nonfreechoice;
	}

}
