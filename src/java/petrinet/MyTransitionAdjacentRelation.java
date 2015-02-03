package petrinet;

public class MyTransitionAdjacentRelation implements Cloneable {
	public String transitionA, transitionB;

	public MyTransitionAdjacentRelation(String a, String b) {
		this.transitionA = a;
		this.transitionB = b;
	}
	public MyTransitionAdjacentRelation() {

	}
	
	public MyTransitionAdjacentRelation(MyTransitionAdjacentRelation r) {
		this.transitionA = r.transitionA;
		this.transitionB = r.transitionB;
	}
	
    public Object clone(){
    	MyTransitionAdjacentRelation obj = null;
        try{
        	obj = (MyTransitionAdjacentRelation)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return obj;
    }
}