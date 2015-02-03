package petrinet;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.processmining.framework.models.petrinet.PNEdge;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.Place;
import org.processmining.framework.models.petrinet.Transition;

public class MyPetriNet implements Cloneable {

	private String id;
	private String type;

	private int source, sink;
	public Vector<MyPetriObject> petri;
	public Vector<MyPetriTransition> petritransition;
	public Vector<MyPetriTransition> transitionSet;
	public Vector<MyPetriTransition> enabledTransitionSet;
	public Vector<MyPetriPlace> placeSet;
	public Vector<MyPetriArc> arcSet;
	
	private MyPetriMatrix IncidenceMatrix;
	private MyPetriMatrix forwardsIncidenceMatrix;
	private MyPetriMatrix backwardsIncidenceMatrix;
	private MyPetriMatrix inhibitionMatrix;

	boolean currentMarkingVectorChanged = true;
	boolean[] enabledTransitions;
	
	private int[] currentMarkingVector = null;
	
	public int[] getCurrentMarkingVector() {
	      if (currentMarkingVectorChanged)  {
	         createCurrentMarkingVector();
	      }
	      return currentMarkingVector;
	   }
	
	   /**
	    * Creates Current Marking Vector from current Petri-Net
	    */
	private void createCurrentMarkingVector(){
	      int placeSize = this.getPlaceSet().size();
	      
	      currentMarkingVector = new int[placeSize];
	      for (int placeNo = 0; placeNo < placeSize; placeNo++) {
	         currentMarkingVector[placeNo] = 
	                 ((MyPetriPlace)placeSet.get(placeNo)).getmarking();
	      }
	   }
	
	private void setCurrentPlaceMarking(int[] marking){
		int placeSize = this.getPlaceSet().size();
		
		for(int i = 0; i < placeSize; i++){
			this.getPlaceSet().get(i).marking(marking[i]);
		}
	}
	
   /**
    * Method to clone a MyPetriNet Object
    */
    public Object clone(){
    	MyPetriNet obj = null;
        try{
        	obj = (MyPetriNet)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return obj;
    }	
	
    /**
     * Constructor
     */
	public MyPetriNet() {
		petri = new Vector<MyPetriObject>();
		petritransition = new Vector<MyPetriTransition>();
		source = -1;
		sink = -1;
	}

	public void addObject(MyPetriObject e) {
		petri.addElement(e);
	}

	public void removeObject(String id) {
		MyPetriObject p;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			s = p.getid();
			if (s.equals(id)) {
				petri.remove(i);
				break;
			}
		}
	}

	public int getSourcePlace() {
		if (source != -1) {
			return source;
		}

		MyPetriObject p, q;

		int temp;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			temp = -1;
			for (int j = 0; j < petri.size(); j++) {
				q = petri.get(j);
				if (q.isA() != MyPetriObject.ARC) {
					continue;
				}
				s = ((MyPetriArc) q).gettargetid();
				if (s.equals(p.getid())) {
					temp = 0;
					break;
				}
			}
			if (temp == -1) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param index The location of the MyPetriNet Object in the vector
	 * @return The specified MyPetriNet Object according to the index
	 */
	public MyPetriObject getPetriObject(int index) {

		return petri.get(index);
	}
	
	public int getIndex(MyPetriObject petriobject){
		if(petriobject instanceof MyPetriPlace){
			for(int i = 0; i < getPlaceSet().size(); i++){
				if(petriobject.equals(getPlaceSet().get(i))){
					return i;
				}
			}
		}
		else if(petriobject instanceof MyPetriTransition){
			for(int i = 0; i < getTransitionSet().size(); i++){
				if(petriobject.equals(getTransitionSet().get(i))){
					return i;
				}
			}
		}
		else if(petriobject instanceof MyPetriArc){
			for(int i = 0; i < getArcSet().size(); i++){
				if(petriobject.equals(getArcSet().get(i))){
					return i;
				}
			}
		}
		return -1;
	}
	/**
	 * 
	 * @return The vector contains all Transitions in the MyPetriNet Object Vector
	 */
	public Vector<MyPetriTransition> getTransitionSet(){
		transitionSet = new Vector<MyPetriTransition>();
		
		for (int i = 0; i < petri.size(); i++) {
			if (((MyPetriObject) petri.get(i)).isA() == MyPetriObject.TRANSITION)
				transitionSet.add((MyPetriTransition) petri.get(i));
		}		
		return transitionSet;
	}
	
	public Vector<MyPetriTransition> getEnabledTransition(int[] markup){
		enabledTransitionSet = new Vector<MyPetriTransition>();
		enabledTransitions = new boolean[transitionSet.size()];
		
		this.setCurrentPlaceMarking(markup);
		for(int i = 0; i < transitionSet.size(); i++){
			if(beTransitionEnabled(transitionSet.get(i).getid())){
				enabledTransitionSet.add(transitionSet.get(i));
				enabledTransitions[i] = true;
			}
		}
		return enabledTransitionSet;
	}
	
	/**
	 * 
	 * @return The vector contains all Places in the MyPetriNet Object Vector
	 */
	public Vector<MyPetriPlace> getPlaceSet(){
		placeSet = new Vector<MyPetriPlace>();
		
		for (int i = 0; i < petri.size(); i++) {
			if (((MyPetriObject) petri.get(i)).isA() == MyPetriObject.PLACE)
				placeSet.add((MyPetriPlace) petri.get(i));
		}		
		return placeSet;
	}
	
	public Vector<MyPetriArc> getArcSet(){
		arcSet = new Vector<MyPetriArc>();
		
		for(int i = 0; i < petri.size(); i++){
			if(((MyPetriObject)petri.get(i)).isA() == MyPetriObject.ARC){
				arcSet.add((MyPetriArc)petri.get(i));
			}		
		}
		return arcSet;
	}
	
	
	/**
	 * 
	 * @return Sink Place
	 */
	public int getSinkPlace() {
		if (sink != -1) {
			return sink;
		}

		MyPetriObject p, q;

		int temp;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			temp = -1;
			for (int j = 0; j < petri.size(); j++) {
				q = petri.get(j);
				if (q.isA() != MyPetriObject.ARC) {
					continue;
				}
				s = ((MyPetriArc) q).getsourceid();
				if (s.equals(p.getid())) {
					temp = 0;
					break;
				}
			}
			if (temp == -1) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Add a Source Place to Transitions that don't have a Place input
	 * Keep the MyPetriNet Structure perfect 
	 */
	public void addSourcePlace() {
		MyPetriPlace sourceplace = new MyPetriPlace("_sourcePlace", "Source Place");

		MyPetriObject p, q;
		int count = 0;
		int temp;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.TRANSITION) {
				continue;
			}
			temp = -1;
			for (int j = 0; j < petri.size(); j++) {
				q = petri.get(j);
				if (q.isA() != MyPetriObject.ARC) {
					continue;
				}
				s = ((MyPetriArc) q).gettargetid();
				if (s.equals(p.getid())) {
					temp = 0;
					break;
				}
			}
			if (temp == -1) {
				// ˵��transitionû���������
				if (count == 0) {
					addObject(sourceplace);
				}
				MyPetriArc arc = new MyPetriArc("source_outarc" + count,
						sourceplace.getid(), p.getid());
				addObject(arc);
				count++;
			}

		}
	}

	/**
	 * Add a Sink Place to Transitions that don't have a Place output
	 * Keep the MyPetriNet Structure perfect 
	 */
	public void addSinkPlace() {	
		MyPetriPlace sinkplace = new MyPetriPlace("_sinkPlace", "Sink Place");

		MyPetriObject p, q;
		int count = 0;
		int temp;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.TRANSITION) {
				continue;
			}
			temp = -1;
			for (int j = 0; j < petri.size(); j++) {
				q = petri.get(j);
                                
				if (q.isA() != MyPetriObject.ARC) {
					continue;
				}
				s = ((MyPetriArc) q).getsourceid();
				if (s.equals(p.getid())) {
					temp = 0;
					break;
				}
			}
			if (temp == -1) {
				if (count == 0) {
					addObject(sinkplace);
				}
				MyPetriArc arc = new MyPetriArc("sink_inarc" + count, p.getid(),
						sinkplace.getid());
				addObject(arc);
				count++;
			}

		}

	}

	/**
	 * Add a token into a Place
	 * @param id The Place id
	 */
	public void produceToken(String id) {
		MyPetriObject p;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			s = p.getid();
			if (s.equals(id)) {
				((MyPetriPlace) p).addtoken(1);
				break;
			}
		}
	}

	/**
	 * Consume a token into a Place
	 * @param id The Place id
	 */
	public void consumeToken(String id) {
		MyPetriObject p;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			s = p.getid();
			if (s.equals(id)) {
				((MyPetriPlace) p).addtoken(-1);
				break;
			}
		}
	}

	/**
	 * Clear all tokens, then add a token into the Source Place
	 */
	public void marking() {
		MyPetriObject p;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.PLACE) {
				continue;
			}
			if (!((MyPetriPlace) p).isempty()) {
				((MyPetriPlace) p).empty();
			}
		}
		//
		((MyPetriPlace) petri.get(getSourcePlace())).marking(1);
	}

	/**
	 * Used for transferring a XPDL Object to MyPetriNet Object
	 * @param transtionid Id of specified Transition
	 * @return One Input Place of the specified Transition, if none, return null
	 */
	public MyPetriPlace getTranstionPlaceIn(String transtionid) {
		MyPetriObject p;
		MyPetriArc r;
		String s, placeid;

		placeid = null;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (!s.equals(transtionid)) {
				continue;
			}
			placeid = r.getsourceid();
		}

		if (placeid != null) {
			for (int i = 0; i < petri.size(); i++) {
				p = petri.get(i);
				if (p.isA() != MyPetriObject.PLACE) {
					continue;
				}
				s = p.getid();
				if (s.equals(placeid)) {
					return (MyPetriPlace) p;
				}
			}

		}
		return null;
	}
	
	/**
	 * Used for transferring a XPDL Object to a MyPetriNet Object
	 * @param transtionid id of specified transition
	 * @return One Output Place of the specified Transition, if none, return null
	 */
	public MyPetriPlace getTranstionPlaceOut(String transtionid) {
		MyPetriObject p;
		MyPetriArc r;
		String s, placeid;

		placeid = null;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.getsourceid();
			if (!s.equals(transtionid)) {
				continue;
			}
			placeid = r.gettargetid();
		}

		if (placeid != null) {
			for (int i = 0; i < petri.size(); i++) {
				p = petri.get(i);
				if (p.isA() != MyPetriObject.PLACE) {
					continue;
				}
				s = p.getid();
				if (s.equals(placeid)) {
					return (MyPetriPlace) p;
				}
			}

		}
		return null;
	}
	
	/**
	 * Used for transferring a XPDL Object to MyPetriNet Object
	 * @param placeid Id of specified Transition
	 * @return All Input Transitions of the specified Place, if none, return null
	 */
	public Vector<MyPetriTransition> getPlaceTransitionIn(String placeid){
		MyPetriObject p;
		MyPetriArc r;
		String s, transitionid;
		
		transitionid = null;
		for(int i = 0; i < petri.size(); i++){
			p = petri.get(i);
			if(p.isA() != MyPetriObject.ARC)
				continue;
			
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if(!s.equals(placeid)){
				continue;
			}
			transitionid = r.getsourceid();
		
			if(transitionid != null){
				for(int j = 0; j < petri.size(); j++){
					p = petri.get(j);
					if(p.isA() != MyPetriObject.TRANSITION){
						continue;
					}
					s = p.getid();
					if(s.equals(transitionid)){
						petritransition.addElement((MyPetriTransition)p);
					}	
				}
			}
		}
		return petritransition;
	}
	
	/**
	 * Used for transferring a XPDL Object to MyPetriNet Object
	 * @param placeid Id of specified Transition
	 * @return All Output Transitions of the specified Place, if none, return null
	 */
	public Vector<MyPetriTransition> getPlaceTransitionOut(String placeid){
		MyPetriObject p;
		MyPetriArc r;
		String s, transitionid;
		
		transitionid = null;
		for(int i = 0; i < petri.size(); i++){
			p = petri.get(i);
			if(p.isA() != MyPetriObject.ARC)
				continue;
			
			r = (MyPetriArc) p;
			s = r.getsourceid();
			if(!s.equals(placeid)){
				continue;
			}
			transitionid = r.gettargetid();
		
			if(transitionid != null){
				for(int j = 0; j < petri.size(); j++){
					p = petri.get(j);
					if(p.isA() != MyPetriObject.TRANSITION){
						continue;
					}
					s = p.getid();
					if(s.equals(transitionid)){
						petritransition.addElement((MyPetriTransition)p);
					}	
				}
			}
		}
		return petritransition;
	}
	
	/**
	 * 
	 * @param id The id of a Place or a Transition
	 * @return The number of Input Arcs for the specified Place or Transition
	 */
	public int getArcIn(String id) {
		int n = 0;
		MyPetriObject p;
		MyPetriArc r;
		String s;

		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (s.equals(id)) {
				n++;
			}
		}

		return n;
	}

	/**
	 * 
	 * @param id The id of a Place or a Transition
	 * @return The number of Output Arcs for the specified Place or Transition
	 */
	public int getArcOut(String id) {
		int n = 0;
		MyPetriObject p;
		MyPetriArc r;
		String s;

		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.getsourceid();
			if (s.equals(id)) {
				n++;
			}
		}

		return n;
	}

	public MyPetriObject getArcTarget(MyPetriArc arc){
		String targetid = arc.gettargetid();
		for(int i = 0; i < petri.size(); i ++){
			if(targetid.equals(petri.get(i).getid())){
				return petri.get(i);
			}
		}
		return null;
	}
	
	public MyPetriObject getArcSource(MyPetriArc arc){
		String souceid = arc.getsourceid();
		for(int i = 0; i < petri.size(); i ++){
			if(souceid.equals(petri.get(i).getid())){
				return petri.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param id Id of one Transition
	 * @return A boolean value, true means the Transition has been enabled
	 */
	public boolean beTransitionEnabled(String id) {
		MyPetriObject p, q;
		MyPetriArc r;
		String s;

		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (!s.equals(id)) {
				continue;
			}

			for (int j = 0; j < petri.size(); j++) {
				q = petri.get(j);
				if (q.isA() != MyPetriObject.PLACE) {
					continue;
				}
				s = r.getsourceid();
				if (!s.equals(q.getid())) {
					continue;
				}

				if (((MyPetriPlace) q).isempty()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Execute the specified Transition without log
	 * @param id Id of the Transition
	 */
	public void executetransition2(String id) {
		MyPetriObject p, q;
		MyPetriArc r;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (s.equals(id)) {
				for (int j = 0; j < petri.size(); j++) {
					q = petri.get(j);
					if (q.isA() != MyPetriObject.PLACE) {
						continue;
					}
					s = r.getsourceid();
					if (!s.equals(q.getid())) {
						continue;
					}
					consumeToken(q.getid());
				}
			}
			s = r.getsourceid();
			if (s.equals(id)) {
				for (int j = 0; j < petri.size(); j++) {
					q = petri.get(j);
					if (q.isA() != MyPetriObject.PLACE) {
						continue;
					}
					s = r.gettargetid();
					if (!s.equals(q.getid())) {
						continue;
					}
					produceToken(q.getid());
				}
			}

		}
	}

	/**
	 * Execute the specified Transition
	 * @param id Id of the Transition
	 */
	public void executeTransition(String id) {
		MyPetriObject p, q;
		MyPetriArc r;
		String s;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (s.equals(id)) {
				for (int j = 0; j < petri.size(); j++) {
					q = petri.get(j);
					if (q.isA() != MyPetriObject.PLACE) {
						continue;
					}
					s = r.getsourceid();
					if (!s.equals(q.getid())) {
						continue;
					}
					consumeToken(q.getid());
				}
			}
			s = r.getsourceid();
			if (s.equals(id)) {
				for (int j = 0; j < petri.size(); j++) {
					q = petri.get(j);
					if (q.isA() != MyPetriObject.PLACE) {
						continue;
					}
					s = r.gettargetid();
					if (!s.equals(q.getid())) {
						continue;
					}
					produceToken(q.getid());
				}
			}

		}

		for (int j = 0; j < petri.size(); j++) {
			p = petri.get(j);
			if (p.isA() != MyPetriObject.TRANSITION) {
				continue;
			}
			s = p.getid();
			if (s.equals(id)) {

			}
		}
	}

	public void run() {
		int caseid = 0;
		caseid++;
		Vector<MyPetriTransition> exev = new Vector<MyPetriTransition>();
		Random rand = new Random();
		for (int step = 0; step < 10000; step++) {
			MyPetriObject p;

			for (int i = 0; i < petri.size(); i++) {
				p = petri.get(i);
				if (p.isA() != MyPetriObject.TRANSITION) {
					continue;
				}

				if (beTransitionEnabled(p.getid())) {
					// executeTransition(p.getid());
					// break;
					exev.add((MyPetriTransition) p);
				}
			}
			if (exev.size() > 0) {
				// int j=exev.size();
				int i = rand.nextInt((exev.size()));
				MyPetriTransition ep = (MyPetriTransition) exev.get(i);
				executeTransition(ep.getid());
			} else {
				break;
			}
			exev.removeAllElements();
		}
	}

	public void setid(String id) {
		this.id = id;
	}

	public void settype(String type) {
		this.type = type;
	}

	public String getid() {
		return id;
	}

	public String gettype() {
		return type;
	}

	/**
	 * Export the MyPetriNet Object to a PNML File
	 * @param exportFileName the Filename exported
	 */
	//
	  public void export_pnml(String exportFileName) {
	    if (petri == null||exportFileName==null) {
	      return;
	    }

	    Element root;
	    root = new Element("pnml");
	    Document docJDOMexp = new Document(root);

	    Element net = new Element("net");
	    net.setAttribute("id","workflownet");
	    net.setAttribute("type","http://www.informatik.hu-berlin.de/top/pnml/basicPNML.rng");
	    root.addContent(net);

	    Element place, transition, arc;
	    Element name,value;
	    MyPetriObject p;
	    for (int i = 0; i < petri.size(); i++) {
	      p = petri.get(i);
	      if (p.isA() == MyPetriObject.TRANSITION) {
	        transition = new Element("transition");
	        transition.setAttribute("id", p.getid());
	        name = new Element("name");
	        value=new Element("value");
	        value.setText(p.getname());
	        name.addContent(value);
	        transition.addContent(name);
	        net.addContent(transition);
	      }
	      else if (p.isA() == MyPetriObject.PLACE) {
	        place = new Element("place");
	        place.setAttribute("id", p.getid());
	        name = new Element("name");
	        value=new Element("value");
	        value.setText(p.getname());
	        name.addContent(value);

	        place.addContent(name);

	        net.addContent(place);
	      }
	      else { //ARC
	        arc = new Element("arc");
	        arc.setAttribute("id", p.getid());
	        arc.setAttribute("source", ( (MyPetriArc) p).getsourceid());
	        arc.setAttribute("target", ( (MyPetriArc) p).gettargetid());
	        net.addContent(arc);
	      }

	    }
	    try {
	      XMLOutputter XMLOut = new XMLOutputter();
	      //XMLOut.setEncoding("gb2312");
	      XMLOut.output(docJDOMexp, new FileOutputStream(exportFileName));
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	

	  
	  
	/**
	 * Adjust the structure of And-join, used for transferring XPDL
	 * @param transid Id of a Transition
	 */
	public void adjustANDjoin(String transid) {
		MyPetriObject p;
		MyPetriArc r, newarc;
		MyPetriPlace newplace;

		String placeid, s;
		placeid = null;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (s.equals(transid)) {
				placeid = r.getsourceid();
				removeObject(r.getid());
				removeObject(placeid);
				break;
			}
		}
		//
		if (placeid == null) {
			return;
		}

		int count = 0;
		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			r = (MyPetriArc) p;
			s = r.gettargetid();
			if (s.equals(placeid)) {
				newplace = new MyPetriPlace(placeid + count, placeid + count);
				addObject(newplace);
				newarc = new MyPetriArc(r.getid() + count, newplace.getid(),
						transid);
				addObject(newarc);
				r.settargetid(newplace.getid());

				count++;
			}
		}
	}

	/**
	 * Adjust the structure of And-split, used for transferring XPDL
	 * @param transid Id of a Transition
	 */
	public void adjustXORsplit(String transid) {
		if (getArcOut(transid) < 2) {
			return;
		}
		MyPetriObject p;
		MyPetriArc r, newarc;
		MyPetriPlace newplace;

		String placeid, s;
		placeid = null;
		int count = 0;

		newplace = new MyPetriPlace(transid + "_newoutplace", transid
				+ "_newoutplace");
		addObject(newplace);

		newarc = new MyPetriArc(transid + "_newoutplace", transid, newplace
				.getid());
		addObject(newarc);

		for (int i = 0; i < petri.size(); i++) {
			p = petri.get(i);
			if (p.isA() != MyPetriObject.ARC) {
				continue;
			}
			s = p.getid();
			if (s.equals(newarc.getid())) {
				continue;
			}

			r = (MyPetriArc) p;
			s = r.getsourceid();
			if (s.equals(transid)) {
				placeid = r.gettargetid();
			} else {
				continue;
			}

			if (placeid == null) {
				continue;
			}
			if (getArcIn(placeid) < 2) {
				removeObject(placeid);
				removeObject(p.getid());

				for (int j = 0; j < petri.size(); j++) {
					p = petri.get(j);
					if (p.isA() != MyPetriObject.ARC) {
						continue;
					}
					r = (MyPetriArc) p;
					s = r.getsourceid();
					if (s.equals(placeid)) {

						r.setsourceid(newplace.getid());
						break;
					}
				}
			}
			else {
				MyPetriTransition tt = new MyPetriTransition(placeid + "_route"
						+ count, "ROUTE", null);
				addObject(tt);
				MyPetriArc tr = new MyPetriArc(placeid + "_arc" + count, newplace
						.getid(), tt.getid());
				addObject(tr);
				//
				// MyPetriArc tr2=new
				// MyPetriArc(placeid+"_arc2"+count,tt.getid(),placeid);
				// addObject(tr2);
				r.setsourceid(tt.getid());
			}
			count++;
		}
	}
	
   public int[][] getForwardsIncidenceMatrix() {
	      if (forwardsIncidenceMatrix == null 
	              || forwardsIncidenceMatrix.matrixChanged) {
	         createForwardIncidenceMatrix();
	      }
	      return (forwardsIncidenceMatrix != null
	               ? forwardsIncidenceMatrix.getArrayCopy() 
	               : null);
	   }
   
   private void createForwardIncidenceMatrix(){
	      int placeSize = this.getPlaceSet().size();
	      int transitionSize = this.getTransitionSet().size();
	      int arcSize = this.getArcSet().size();
	      
	      forwardsIncidenceMatrix = new MyPetriMatrix(placeSize, transitionSize);

	      for (int i = 0; i < arcSize; i++) {
	         MyPetriArc arc = (MyPetriArc)this.getArcSet().get(i);
	         if (arc != null ) {
	            MyPetriObject pnObject = this.getArcTarget(arc);
	            if (pnObject != null) {
	               if (pnObject instanceof MyPetriPlace) {
	            	   MyPetriPlace place = (MyPetriPlace)pnObject;
	                  pnObject = this.getArcSource(arc);
	                  if (pnObject != null) {
	                     if (pnObject instanceof MyPetriTransition) {
	                    	 MyPetriTransition transition = (MyPetriTransition)pnObject;
	                        int transitionNo = getIndex(transition);
	                        int placeNo = getIndex(place);
	                        try {
	                           forwardsIncidenceMatrix.set(
	                                   placeNo, transitionNo, arc.getweight());
	                        } catch (Exception e) {
	                           JOptionPane.showMessageDialog(null, 
	                                          "Problem in forwardsIncidenceMatrix");
	                           System.out.println("p:" + placeNo + ";t:" + transitionNo + ";w:" + arc.getweight());
	                        }
	                     }
	                  }
	               }
	            }
	         }
	      }
	   }
	   
   public int[][] getBackwardsIncidenceMatrix() {
	      if (backwardsIncidenceMatrix == null 
	              || backwardsIncidenceMatrix.matrixChanged) {
	         createBackwardsIncidenceMatrix();
	      }
	      return (backwardsIncidenceMatrix != null 
	              ? backwardsIncidenceMatrix.getArrayCopy()
	              : null);
	   }
   
   private void createBackwardsIncidenceMatrix(){
	      int placeSize = this.getPlaceSet().size();
	      int transitionSize = this.getTransitionSet().size();
	      int arcSize = this.getArcSet().size();
	      
	      backwardsIncidenceMatrix = new MyPetriMatrix(placeSize, transitionSize);

	      for (int i = 0; i < arcSize; i++) {
	         MyPetriArc arc = (MyPetriArc)this.getArcSet().get(i);
	         if (arc != null ) {
	            MyPetriObject pnObject = this.getArcSource(arc);
	            if (pnObject != null) {
	               if (pnObject instanceof MyPetriPlace) {
	            	   MyPetriPlace place = (MyPetriPlace)pnObject;
	                  pnObject = this.getArcTarget(arc);
	                  if (pnObject != null) {
	                     if (pnObject instanceof MyPetriTransition) {
	                    	 MyPetriTransition transition = (MyPetriTransition)pnObject;
	                        int transitionNo = getIndex(transition);
	                        int placeNo = getIndex(place);
	                        try {
	                        	backwardsIncidenceMatrix.set(
	                                   placeNo, transitionNo, arc.getweight());
	                        } catch (Exception e) {
	                           JOptionPane.showMessageDialog(null, 
	                                          "Problem in backwardsIncidenceMatrix");
	                           System.out.println("p:" + placeNo + ";t:" + transitionNo + ";w:" + arc.getweight());
	                        }
	                     }
	                  }
	               }
	            }
	         }
	      }
	   }
   
   /**
    * Return the Incidence Matrix for the Petri-Net
    * @return The Incidence Matrix for the Petri-Net
    */
//   public int[][] getInhibitionMatrix() {
//      if (inhibitionMatrix == null || inhibitionMatrix.matrixChanged) {
//         createInhibitionMatrix();
//      }
//      return (inhibitionMatrix != null ? inhibitionMatrix.getArrayCopy() : null);
//   } 
   /**
    * Creates Inhibition Matrix from current Petri-Net
    */
//   private void createInhibitionMatrix(){
//      int placeSize = placeSet.size();
//      int transitionSize = transitionSet.size();
//      inhibitionMatrix = new MyPetriMatrix(placeSize, transitionSize);
//      
//      for (int i = 0; i < inhibitorsArray.size(); i++) {
//         InhibitorArc inhibitorArc = (InhibitorArc)inhibitorsArray.get(i);
//         if (inhibitorArc != null) {
//            PetriNetObject pnObject = inhibitorArc.getSource();
//            if (pnObject != null) {
//               if (pnObject instanceof Place) {
//                  Place place = (Place)pnObject;
//                  pnObject = inhibitorArc.getTarget();
//                  if (pnObject != null) {
//                     if (pnObject instanceof Transition) {
//                        Transition transition = (Transition)pnObject;
//                        int transitionNo = getListPosition(transition);
//                        int placeNo = getListPosition(place);
//                        try {
//                           inhibitionMatrix.set(
//                                   placeNo, transitionNo, inhibitorArc.getWeight());
//                        } catch (Exception e) {
//                           JOptionPane.showMessageDialog(null, 
//                                          "Problema a inhibitionMatrix");                          
//                           System.out.println("p:" + placeNo + ";t:" + transitionNo + ";w:" + inhibitorArc.getWeight());
//                        }                        
//                     }
//                  }
//               }
//            }
//         }
//      }
//   }
   
	  /**
	   * ��һ��ProM�е�Petri�����ת��Ϊһ��MyPetriNet����
	   *
	   */
//	  public static MyPetriNet fromProMPetriToMyPetri(PetriNet promPetri){
//		  MyPetriNet myPetri=new MyPetriNet();
//		  
//		  ArrayList<Transition> transitions; /* the list of transition nodes */
//		  ArrayList<Place> places; /* the list of place nodes */
//		  ArrayList<PNEdge> edges; /* the list of place nodes */
//		  
//		  transitions=promPetri.getTransitions();
//		  places=promPetri.getPlaces();
//		  edges=promPetri.getEdges();
//		  
//		  Transition t;
//		  String name;
//		  for(int i=0;i<transitions.size();i++){
//			  t=transitions.get(i);
//			  name=t.getIdentifier();
//			  myPetri.addObject(new MyPetriTransition(name,name,null));
//		  }
//		  
//		  Place p;
//		  for(int i=0;i<places.size();i++){
//			  p=places.get(i);
//			  name=p.getIdentifier();
//			  myPetri.addObject(new MyPetriPlace(name,name));
//		  }
//		  
//		  PNEdge e;
//		  for(int i=0;i<edges.size();i++){
//			  e=edges.get(i);
//			  myPetri.addObject(new MyPetriArc(e.getName(),e.getSource().getIdentifier(),e.getDest().getIdentifier()));
//		  }
//		  return myPetri;
//	  }
	  public static MyPetriNet fromProMPetriToMyPetri(PetriNet promPetri){
		  MyPetriNet myPetri=new MyPetriNet();
		  
		  ArrayList<Transition> transitions; /* the list of transition nodes */
		  ArrayList<Place> places; /* the list of place nodes */
		  ArrayList<PNEdge> edges; /* the list of place nodes */
		  
		  transitions=promPetri.getTransitions();
		  places=promPetri.getPlaces();
		  edges=promPetri.getEdges();
		  
		  Transition t;
		  String name;
		  String id;
		  for(int i=0;i<transitions.size();i++){
			  t=transitions.get(i);
			  id = "" + t.getIdKey();
			  //id = t.getName();
			  name = t.getIdentifier();
			  if(t.isInvisibleTask())
				  name = "";
//			  if(t.isDuplicateTask())
//				  id += "00";
			  myPetri.addObject(new MyPetriTransition(id,name,null));
			 
		  }
		  
		  Place p;
		  for(int i=0;i<places.size();i++){
			  p=places.get(i);
			  id = "" + p.getIdKey();
			  name = p.getIdentifier();
			  myPetri.addObject(new MyPetriPlace(id,name));
		  }
		  
		  PNEdge e;
		  for(int i=0;i<edges.size();i++){
			  e=edges.get(i);
			  myPetri.addObject(new MyPetriArc(e.getName(), "" + e.getSource().getIdKey(), "" + e.getDest().getIdKey()));
		  }
		  return myPetri;
	  }
}
