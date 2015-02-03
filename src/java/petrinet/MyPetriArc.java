package petrinet;


public class MyPetriArc extends MyPetriObject implements Cloneable {
	private String sourceid;
	private String targetid;
	private int weight;

	public MyPetriArc(String id, String sourceid, String targetid) {
		this.setid(id);
		this.sourceid = sourceid;
		this.targetid = targetid;
		this.weight = 1;
		this.settype(MyPetriObject.ARC);
	}

	public void setsourceid(String id) {
		sourceid = id;
	}

	public void settargetid(String id) {
		targetid = id;
	}

	public String getsourceid() {
		return sourceid;
	}

	public String gettargetid() {
		return targetid;
	}

	public int getweight() {
		return weight;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object clone() {
		MyPetriArc obj = null;
		obj = (MyPetriArc) super.clone();
		return obj;
	}
}
