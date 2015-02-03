package petrinet;

import petrinet.MyPetriObject;
import java.util.Vector;

public class MyReachMarkingGraph implements Cloneable {

	public Vector<MyPetriPlace> placearray;
	private MyPetriNet petri;
	private RMGMarkingState m0;

	public Vector<RMGObject> reachmarkinggraph;

	public Object clone() {
		MyReachMarkingGraph obj = null;
		try {
			obj = (MyReachMarkingGraph) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public MyReachMarkingGraph(MyPetriNet petri, MyPetriPlace markedplace) {
		placearray = new Vector<MyPetriPlace>();
		reachmarkinggraph = new Vector<RMGObject>();

		this.petri = petri;

		buildPlaceArray();
		buildM0(markedplace);
		bulidRMG();
	}

	private void buildPlaceArray() {
		if (petri == null) {
			return;
		}

		MyPetriObject p;
		for (int i = 0; i < petri.petri.size(); i++) {
			p = petri.petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			placearray.add((MyPetriPlace) p);
		}
	}

	/**
	 * Create the original marking m0 by the initial marked Place
	 * 
	 * @param markedplace
	 *            The initial marked Place
	 */
	private void buildM0(MyPetriPlace markedplace) {
		String m = "";
		MyPetriPlace p;
		for (int i = 0; i < placearray.size(); i++) {
			p = placearray.get(i);
			if (p.equals(markedplace)) {
				m += "1";
			} else {
				m += "0";
			}
		}

		m0 = new RMGMarkingState(m, RMGObject.MARKINGSTATE);
	}

	/**
	 * Judge whether the marking state is new
	 * 
	 * @param m
	 *            The marking state to be judged.
	 * @return The result judged
	 */
	private boolean isNewMarkingState(RMGMarkingState m) {
		boolean benew = true;
		for (int i = 0; i < reachmarkinggraph.size(); i++) {
			if (reachmarkinggraph.get(i).isA() == RMGObject.MARKINGSTATE) {
				if (((RMGMarkingState) reachmarkinggraph.get(i)).getstate()
						.equals(m.getstate())) {
					benew = false;
					break;
				}
			}
		}
		return benew;

	}

	/**
	 * Build the reach marking graph by m0
	 */
	public void bulidRMG() {
		this.reachmarkinggraph.clear();
		bulidRMG(this.m0);
	}

	/**
	 * Build the reach marking graph by m0
	 * 
	 * @param m0
	 *            The marking state
	 */
	private void bulidRMG(RMGMarkingState m0) {

		if (isNewMarkingState(m0)) {
			this.reachmarkinggraph.add(m0);
		}

		Vector<MyPetriTransition> enabledtransition = new Vector<MyPetriTransition>();

		MyPetriObject t;

		setMarking(m0);
		for (int i = 0; i < petri.petri.size(); i++) {
			t = petri.petri.get(i);
			if (t.isA() != MyPetriObject.TRANSITION) {
				continue;
			}
			if (petri.beTransitionEnabled(t.getid())) {
				enabledtransition.add((MyPetriTransition) t);
			}
		}

		for (int i = 0; i < enabledtransition.size(); i++) {
			setMarking(m0);
			petri.executetransition2(enabledtransition.get(i).getid());
			RMGMarkingState m1 = getMarkingState();
			String name = enabledtransition.get(i).getname();
			this.reachmarkinggraph.add(new RMGArc(m0.getstate(), m1.getstate(), name, RMGObject.ARC));
			if (isNewMarkingState(m1)) {
				bulidRMG(m1);
			}
		}

	}

	/**
	 * Output the reach marking graph by text
	 */
	public String toString() {
		String strRMG = "";
		strRMG = "�����б�(" + placearray.size() + "):\r\n";
		MyPetriPlace p;
		for (int i = 0; i < placearray.size(); i++) {
			p = placearray.get(i);
			strRMG += p.getname() + "\r\n";
		}

		strRMG += "��ʶ״̬�б?\r\n";
		RMGObject r;
		for (int i = 0; i < reachmarkinggraph.size(); i++) {
			r = reachmarkinggraph.get(i);
			if (r.isA() == RMGObject.MARKINGSTATE) {
				strRMG += ((RMGMarkingState) r).getstate() + "\r\n";
			}
		}

		strRMG += "ת�ƻ��б?\r\n";

		for (int i = 0; i < reachmarkinggraph.size(); i++) {
			r = reachmarkinggraph.get(i);
			if (r.isA() == RMGObject.ARC) {
				strRMG += ((RMGArc) r).getName() + ":" + ((RMGArc) r).getFrom()
						+ "->" + ((RMGArc) r).getTo() + "\r\n";
			}
		}
		return strRMG;

	}

	/**
	 * Clear all the object
	 */
	private void setUnmarking() {
		MyPetriPlace p;
		for (int i = 0; i < placearray.size(); i++) {
			p = placearray.get(i);
			p.empty();
		}
	}

	/**
	 * Mark the MyPetriNet by the marking state m
	 * 
	 * @param m
	 *            The marking state
	 */
	private void setMarking(RMGMarkingState m) {
		setUnmarking();

		String strmarking;
		strmarking = m.getstate();
		char c;
		for (int i = 0; i < strmarking.length(); i++) {
			c = strmarking.charAt(i);
			if (c == '1') {
				placearray.get(i).addtoken(1);
			}
		}
	}

	/**
	 * Get the current MyPetriNet marking state.
	 * 
	 * @return A RMBMarkingState Object
	 */
	private RMGMarkingState getMarkingState() {
		String m = "";
		MyPetriPlace p;
		for (int i = 0; i < placearray.size(); i++) {
			p = placearray.get(i);
			if (p.getmarking() > 0) {
				m += "1";
			} else {
				m += "0";
			}
		}
		return new RMGMarkingState(m, RMGObject.MARKINGSTATE);
	}

	/**
	 * The Arc in the reach marking graph
	 * 
	 * �ɴ�ͼת�ƻ���
	 * 
	 * @author zhp
	 * 
	 */
	public class RMGArc extends RMGObject implements Cloneable {
		private String statefrom;
		private String stateto;
		// private String transitionid;
		private String transitionname;

		public RMGArc(String from, String to, String transitionname, int type) {
			this.statefrom = from;
			this.stateto = to;
			this.transitionname = transitionname;
			this.settype(type);
		}

		public String getFrom() {
			return statefrom;
		}

		public String getTo() {
			return stateto;
		}

		public String getName() {
			return transitionname;
		}

		public Object clone() {
			RMGArc obj = null;

			obj = (RMGArc) super.clone();

			return obj;
		}
	}

	/**
	 * The vertex in the reach marking graph
	 * 
	 * �ɴ�ͼ���㣬����ʶ״̬��
	 * 
	 * @author zhp
	 * 
	 */
	public class RMGMarkingState extends RMGObject implements Cloneable {
		private String markingstate; 

		public RMGMarkingState(String state, int type) {
			this.markingstate = state;
			this.settype(type);
		}

		public void setstate(String state) {
			markingstate = state;
		}

		public String getstate() {
			return markingstate;
		}

		public Object clone() {
			RMGMarkingState obj = null;

			obj = (RMGMarkingState) super.clone();

			return obj;
		}

	}

	/**
	 * The super Class of reach marking graph.
	 * 
	 * �ɴ��ʶͼԪ�ض������
	 * 
	 * @author zhp
	 * 
	 */
	public class RMGObject implements Cloneable {
		public static final int MARKINGSTATE = 1;
		public static final int ARC = 2;

		int type; 

		public void settype(int type) {
			this.type = type;
		}

		public int isA() {
			return type;
		}

		public Object clone() {
			RMGObject obj = null;
			try {
				obj = (RMGObject) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return obj;
		}

	}

}
