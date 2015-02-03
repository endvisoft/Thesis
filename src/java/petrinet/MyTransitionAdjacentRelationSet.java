package petrinet;

import java.util.Vector;

import org.processmining.framework.models.petrinet.PetriNet;

public class MyTransitionAdjacentRelationSet implements Cloneable {

	public Vector<MyTransitionAdjacentRelation> tarSet;

	private MyPetriNet petri;
	private MyPetriPlace markedplace;
	private MyReachMarkingGraph rmg;

	public MyReachMarkingGraph getRmg() {
		return rmg;
	}
	public Object clone() {
		MyTransitionAdjacentRelationSet obj = null;
		try {
			obj = (MyTransitionAdjacentRelationSet) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public MyTransitionAdjacentRelationSet(MyPetriNet petri,
			MyPetriPlace markedplace) {
		tarSet = new Vector<MyTransitionAdjacentRelation>();
		this.petri = petri;
		this.markedplace = markedplace;
		bulidTARSet();
	}

	/**
	 * Default Constructor
	 */
	public MyTransitionAdjacentRelationSet() {
		tarSet = new Vector<MyTransitionAdjacentRelation>();
		this.petri = null;
		this.markedplace = null;
	}

	/**
	 * Constructor, the default marked place is the Source Place
	 * 
	 * @param petri
	 *            The MyPetriNet to be dealt with.
	 */

	public MyTransitionAdjacentRelationSet(MyPetriNet petri) {
		int mp = petri.getSourcePlace();
		this.markedplace = (MyPetriPlace) (petri.getPetriObject(mp));

		this.petri = petri;

		tarSet = new Vector<MyTransitionAdjacentRelation>();
		bulidTARSet();

	}
	
	//for experiment_2
	public MyTransitionAdjacentRelationSet(PetriNet pn) {
		MyPetriNet petri = MyPetriNet.fromProMPetriToMyPetri(pn);
		int mp = petri.getSourcePlace();
		this.markedplace = (MyPetriPlace) (petri.getPetriObject(mp));

		this.petri = petri;

		tarSet = new Vector<MyTransitionAdjacentRelation>();
		bulidMyTARSet();

	}
	
	public void bulidMyTARSet() {
		tarSet.removeAllElements();
		rmg = new MyReachMarkingGraph(petri, markedplace);
		MyReachMarkingGraph.RMGObject g;

		for (int i = 0; i < rmg.reachmarkinggraph.size(); i++) {
			g = rmg.reachmarkinggraph.get(i);
			if (g.isA() == MyReachMarkingGraph.RMGObject.MARKINGSTATE) {
				Vector<String> inputarc = new Vector<String>();
				Vector<String> outputarc = new Vector<String>();
				MyReachMarkingGraph.RMGObject r;
				for (int j = 0; j < rmg.reachmarkinggraph.size(); j++) {
					r = rmg.reachmarkinggraph.get(j);
					if (r.isA() == MyReachMarkingGraph.RMGObject.ARC) {
						if (((MyReachMarkingGraph.RMGArc) r).getTo().equals(
								((MyReachMarkingGraph.RMGMarkingState) g)
										.getstate())) {
							inputarc.add(((MyReachMarkingGraph.RMGArc) r)
									.getName());
						}
						if (((MyReachMarkingGraph.RMGArc) r).getFrom().equals(
								((MyReachMarkingGraph.RMGMarkingState) g)
										.getstate())) {
							outputarc.add(((MyReachMarkingGraph.RMGArc) r)
									.getName());
						}
					}
				}
				for (int k = 0; k < inputarc.size(); k++) {
					for (int l = 0; l < outputarc.size(); l++) {
						boolean bfound = false;
						MyTransitionAdjacentRelation tar;
						for (int m = 0; m < tarSet.size(); m++) {
							tar = tarSet.get(m);
							if (tar.transitionA.equals(inputarc.get(k))
									&& tar.transitionB.equals(outputarc.get(l))) {
								bfound = true;
								break;
							}
						}
						if (!bfound) {
							addTAR(inputarc.get(k), outputarc.get(l));
						}
					}
				}
			}
		}

	}

	/**
	 * Add a TAR
	 * 
	 * @param a
	 *            Transition A
	 * @param b
	 *            Transition B
	 */
	public void addTAR(String a, String b) {
		tarSet.add(new MyTransitionAdjacentRelation(a, b));
	}

	public void addTAR(MyTransitionAdjacentRelation r) {
		tarSet.add(r);
	}

	/**
	 * Build TARSet
	 */
	public void bulidTARSet() {
		tarSet.removeAllElements();
		rmg = new MyReachMarkingGraph(petri, markedplace);

		MyReachMarkingGraph.RMGObject g;

		for (int i = 0; i < rmg.reachmarkinggraph.size(); i++) {
			g = rmg.reachmarkinggraph.get(i);
			if (g.isA() == MyReachMarkingGraph.RMGObject.MARKINGSTATE) {
				Vector<String> inputarc = new Vector<String>();
				Vector<String> outputarc = new Vector<String>();
				MyReachMarkingGraph.RMGObject r;
				for (int j = 0; j < rmg.reachmarkinggraph.size(); j++) {
					r = rmg.reachmarkinggraph.get(j);
					if (r.isA() == MyReachMarkingGraph.RMGObject.ARC) {
						if (((MyReachMarkingGraph.RMGArc) r).getTo().equals(
								((MyReachMarkingGraph.RMGMarkingState) g)
										.getstate())) {
							String from = ((MyReachMarkingGraph.RMGArc) r).getName();
							if(!from.equals(""))
								inputarc.add(from);
						}
						if (((MyReachMarkingGraph.RMGArc) r).getFrom().equals(
								((MyReachMarkingGraph.RMGMarkingState) g)
										.getstate())) {
							String to = ((MyReachMarkingGraph.RMGArc) r).getName();
							if(!to.equals(""))
							{
								outputarc.add(to);
							}
							else
							{
								//traverse all arcs reachable from r's output state, find all first non-empty arcs
								Vector<String> alTos = findNonemptyArcs(((MyReachMarkingGraph.RMGArc) r).getTo());
								outputarc.addAll(alTos);
							}
						}
					}
				}
				for (int k = 0; k < inputarc.size(); k++) {
					for (int l = 0; l < outputarc.size(); l++) {
						boolean bfound = false;
						MyTransitionAdjacentRelation tar;
						for (int m = 0; m < tarSet.size(); m++) {
							tar = tarSet.get(m);
							if (tar.transitionA.equals(inputarc.get(k))
									&& tar.transitionB.equals(outputarc.get(l))) {
								bfound = true;
								break;
							}
						}
						if (!bfound) {
							addTAR(inputarc.get(k), outputarc.get(l));
						}
					}
				}
			}
		}

	}

	private Vector<String> findNonemptyArcs(String currState) {
		// TODO Auto-generated method stub
		Vector<String> vecReturn = new Vector<String>();
		
		MyReachMarkingGraph.RMGObject r;
		for (int i = 0; i < rmg.reachmarkinggraph.size(); i++)
		{
			r = rmg.reachmarkinggraph.get(i);
			if (r.isA() == MyReachMarkingGraph.RMGObject.ARC)
			{
				//traverse currState's output arcs
				if(((MyReachMarkingGraph.RMGArc) r).getFrom().equals(currState))
				{
					String name = ((MyReachMarkingGraph.RMGArc) r).getName();
					if(!name.equals(""))
					{
						vecReturn.add(name);
					}
					else
					{
						Vector<String> vecRetChild = findNonemptyArcs(((MyReachMarkingGraph.RMGArc) r).getTo());
						vecReturn.addAll(vecRetChild);
					}
				}
			}
		}
		return vecReturn;
	}

	/**
	 * Output TARSet by text
	 */
	public String toString() {
		String strTARSet = "";

		MyTransitionAdjacentRelation temp;
		for (int i = 0; i < tarSet.size(); i++) {
			temp = tarSet.get(i);

			strTARSet += '(' + temp.transitionA + ',' + temp.transitionB + ") ";
		}

		return strTARSet;
	}

}
