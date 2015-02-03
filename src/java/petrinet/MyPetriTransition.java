package petrinet;

import petrinet.MyPetriObject;
import java.util.Vector;

public class MyPetriTransition extends MyPetriObject implements Cloneable {
	  private String participant; 

	  private Vector<String> inputplace;  
	  private Vector<String> outputplace; 

	public MyPetriTransition(String id,String name,String participant){
	  this.setid(id);
	  this.setname(name);
	  this.setParticipant(participant);
	  this.settype(MyPetriObject.TRANSITION);
	}

	  public void excution(){};   
	  
	  public boolean isenabled(){
		  return false;
	  }; 

	  public void setParticipant(String name){
	    participant=name;
	  }

	  public String getParticipant(){
	    return participant;
	  }

	@Override
	public String toString() {
		return name;
	}
	  
    public Object clone(){
    	MyPetriTransition obj = null;
        	obj = (MyPetriTransition)super.clone();
        return obj;
    }		   
}
