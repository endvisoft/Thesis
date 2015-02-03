package petrinet;


public class MyPetriObject implements Cloneable {
	public static final int PLACE = 1;
	public static final int TRANSITION = 2;
	public static final int ARC = 3;
	//
	private String id;

	protected String name;
	private int type;

	public void setid(String id) {
		this.id = id;
	}

	public String getid() {
		return id;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getname() {
		return name;
	}

	public void settype(int type) {
		this.type = type;
	}

	public int isA() {
		return type;
	}

	@Override
	public String toString() {
		return name;
	}
	
    public Object clone(){
    	MyPetriObject obj = null;
        try{
        	obj = (MyPetriObject)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return obj;
    }	
}
