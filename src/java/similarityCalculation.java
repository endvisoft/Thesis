
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

import org.processmining.framework.models.petrinet.*;
import org.processmining.importing.pnml.PnmlImport;

import petrinet.MyPetriArc;
import petrinet.MyPetriPlace;
import petrinet.MyPetriTransition;
import petrinet.MyTransitionAdjacentRelationSet;
import petrinet.PetriNetSimilarity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
@ManagedBean
@SessionScoped
public class similarityCalculation extends petrinet.PetriNetSimilarity{
     @Override
        public String getDesription() {
                // TODO Auto-generated method stub
                return "structure based process similarity metric using Jaccard coefficient";
        }

        @Override
        public String getName() {
                // TODO Auto-generated method stub
                return "JaccardStructureSimilarity";
        }

        @Override
        public float similarityStructural(PetriNet pn1, PetriNet pn2, ServletContext ctx) {
                
                if(pn1==null || pn2==null) return -1; 
                // TODO Auto-generated method stub
                int cap=0;
                int cup=1;
                
         try {
             cap=calCap(pn1, pn2,ctx);
         } catch (IOException ex) {
             Logger.getLogger(similarityCalculation.class.getName()).log(Level.SEVERE, null, ex);
         } catch (URISyntaxException ex) {
             Logger.getLogger(similarityCalculation.class.getName()).log(Level.SEVERE, null, ex);
         } 
                
         try {
             cup=calCup(pn1, pn2, ctx);
         } catch (IOException ex) {
             Logger.getLogger(similarityCalculation.class.getName()).log(Level.SEVERE, null, ex);
         } catch (URISyntaxException ex) {
             Logger.getLogger(similarityCalculation.class.getName()).log(Level.SEVERE, null, ex);
         } 
                System.out.println("cap : "+cap+" cup : "+cup);
                return (cap*(float)1.0)/cup;
        }
        
        private int calCap(PetriNet pn1, PetriNet pn2, ServletContext ctx) throws IOException, URISyntaxException{
                sentenceSimilarity sim = new sentenceSimilarity();
                int cap=0;
                
                  ArrayList<Transition> transitions1, transitions2; /* the list of transition nodes */
                  ArrayList<Place> places1,places2; /* the list of place nodes */
                  ArrayList<PNEdge> edges1, edges2; /* the list of place nodes */
                  
                  transitions1=pn1.getTransitions();
                  places1=pn1.getPlaces();
                  edges1=pn1.getEdges();
                  
                  transitions2=pn2.getTransitions();
                  places2=pn2.getPlaces();
                  edges2=pn2.getEdges();                  
                  
                       
                  for(int i=0;i<transitions1.size();i++){
                          
                          Transition t=transitions1.get(i);                       
                  
                          boolean duplicated=false;
                          for(int j=i+1;j<transitions1.size();j++){
                                  double simi = sim.calculateSimilarity(t.getIdentifier(), transitions1.get(j).getIdentifier(), ctx);  
                                  if(t.getIdentifier().equals(transitions1.get(j).getIdentifier())||simi > 0.7){
                                          duplicated=true;
                                          break;
                                  }
                              //System.out.println("activity 1 : "+t.getIdentifier()+" activity 2 : "+transitions1.get(j).getIdentifier());
                              //System.out.println("cap act 1 : "+t.getName()+" act 2 : "+transitions1.get(j).getName()+" similarity : "+ sim.calculateSimilarity(t.getName(), transitions1.get(j).getName()));
                                  /*double simi = sim.calculateSimilarity(t.getIdentifier(), transitions1.get(j).getIdentifier());
                                  if(simi>0.7){
                                      System.out.println("cap act 1 : "+t.getIdentifier()+" act 2 : "+transitions1.get(j).getIdentifier()+" similarity : "+ simi);
                                      duplicated=true;
                                      break;
                                  } */       
                                                                 
                          }
                          
                          if(duplicated==true) continue;
                                  
                          for(int j=0;j<transitions2.size();j++){
                                  double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier(),ctx);  
                                  if(t.getIdentifier().equals(transitions2.get(j).getIdentifier())||simi > 0.7){
                                          cap++;
                                          break;
                                  } 
                              //System.out.println("cap act 1 : "+t.getName()+" act 2 : "+transitions2.get(j).getName()+" similarity : "+ sim.calculateSimilarity(t.getName(), transitions2.get(j).getName()));
                              /*double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier());
                              if(simi>0.7){
                                   System.out.println("cap act 1 : "+t.getIdentifier()+" act 2 : "+transitions2.get(j).getIdentifier()+" similarity : "+ simi);
                                   cap++;
                                   break;
                              }*/
                          }
                  }
             
                  for(int i=0;i<places1.size();i++){
                          
                          Place p=places1.get(i);                         
                
                          boolean duplicated=false;
                          for(int j=i+1;j<places1.size();j++){
                                  if(p.getIdentifier().equals(places1.get(j).getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
                                  
                          for(int j=0;j<places2.size();j++){
                                  if(p.getIdentifier().equals(places2.get(j).getIdentifier())){
                                          cap++;
                                          break;
                                  }                               
                          }
                  }
            
                  for(int i=0;i<edges1.size();i++){
                          
                          PNEdge e=edges1.get(i);                         
                  
                          boolean duplicated=false;
                          for(int j=i+1;j<edges1.size();j++){
                                  if(e.getSource().getIdentifier().equals(edges1.get(j).getSource().getIdentifier())&&
                                                  e.getDest().getIdentifier().equals(edges1.get(j).getDest().getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
                                  
                          for(int j=0;j<edges2.size();j++){
                                  if(e.getSource().getIdentifier().equals(edges2.get(j).getSource().getIdentifier())&&
                                                  e.getDest().getIdentifier().equals(edges2.get(j).getDest().getIdentifier())){
                                          cap++;
                                          break;
                                  }                               
                          }
                  }     
                
                return cap;
        }
        
        private int calCup(PetriNet pn1, PetriNet pn2, ServletContext ctx) throws IOException, URISyntaxException{
                sentenceSimilarity sim = new sentenceSimilarity();
                int cup=0;
                
                 ArrayList<Transition> transitions1, transitions2; /* the list of transition nodes */
                  ArrayList<Place> places1,places2; /* the list of place nodes */
                  ArrayList<PNEdge> edges1, edges2; /* the list of place nodes */
                  
                  transitions1=pn1.getTransitions();
                  places1=pn1.getPlaces();
                  edges1=pn1.getEdges();
                  
                  transitions2=pn2.getTransitions();
                  places2=pn2.getPlaces();
                  edges2=pn2.getEdges();                  
       
                  for(int i=0;i<transitions1.size();i++){
                          
                          Transition t=transitions1.get(i);                       
       
                          boolean duplicated=false;
                          for(int j=i+1;j<transitions1.size();j++){
                                  double simi = sim.calculateSimilarity(t.getIdentifier(), transitions1.get(j).getIdentifier(),ctx);
                                  if(t.getIdentifier().equals(transitions1.get(j).getIdentifier())||simi > 0.7){
                                          duplicated=true;
                                          break;
                                  }
                              //System.out.println("cup act 1 : "+t.getName()+" act 2 : "+transitions1.get(j).getName()+" similarity : "+ sim.calculateSimilarity(t.getName(), transitions1.get(j).getName()));
                              /*double simi = sim.calculateSimilarity(t.getIdentifier(), transitions1.get(j).getIdentifier());
                              if(simi>0.7){
                                  System.out.println("cup act 1 : "+t.getIdentifier()+" act 2 : "+transitions1.get(j).getIdentifier()+" similarity : "+ simi);
                                  duplicated=true;
                                  break;
                              }*/
                          }
                          
                          if(duplicated==true) continue;
                      
                          boolean found=false;
                          for(int j=0;j<transitions2.size();j++){
                                  double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier(),ctx);
                                  if(t.getIdentifier().equals(transitions2.get(j).getIdentifier())||simi > 0.7){
                                          found=true;
                                          break;
                                  }
                              //System.out.println("cup act 1 : "+t.getName()+" act 2 : "+transitions2.get(j).getName()+" similarity : "+ sim.calculateSimilarity(t.getName(), transitions2.get(j).getName()));
                              /*double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier());
                              if(simi>0.7){
                                  System.out.println("cup act 1 : "+t.getIdentifier()+" act 2 : "+transitions2.get(j).getIdentifier()+" similarity : "+ simi);
                                  found=true;
                                  break;
                              }*/
                          }
                          if(found==false) cup++;
                  }

                  for(int i=0;i<transitions2.size();i++){
                          
                          Transition t=transitions2.get(i);                       
            
                          boolean duplicated=false;
                          for(int j=i+1;j<transitions2.size();j++){
                                  double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier(),ctx);  
                                  if(t.getIdentifier().equals(transitions2.get(j).getIdentifier())||simi > 0.7){
                                          duplicated=true;
                                          break;
                                  }
                              //System.out.println("cup act 1 : "+t.getName()+" act 2 : "+transitions2.get(j).getName()+" similarity : "+ sim.calculateSimilarity(t.getName(), transitions2.get(j).getName()));
                              /*double simi = sim.calculateSimilarity(t.getIdentifier(), transitions2.get(j).getIdentifier());
                              if(simi>0.7){
                                  System.out.println("cup act 1 : "+t.getIdentifier()+" act 2 : "+transitions2.get(j).getIdentifier()+" similarity : "+ simi);
                                  duplicated=true;
                                  break;
                              }*/
                          }
                          
                          if(duplicated==true) continue;
                          cup++;
                  }
                  
                  
         
                  for(int i=0;i<places1.size();i++){
                          
                          Place p=places1.get(i);                         
             
                          boolean duplicated=false;
                          for(int j=i+1;j<places1.size();j++){
                                  if(p.getIdentifier().equals(places1.get(j).getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
              
                          boolean found=false;
                          for(int j=0;j<places2.size();j++){
                                  if(p.getIdentifier().equals(places2.get(j).getIdentifier())){
                                          found=true;
                                          break;
                                  }                               
                          }
                          if(found==false) cup++;
                  }
              
                  for(int i=0;i<places2.size();i++){
                          
                          Place p=places2.get(i);                         
                        
                          boolean duplicated=false;
                          for(int j=i+1;j<places2.size();j++){
                                  if(p.getIdentifier().equals(places2.get(j).getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
                          cup++;
                  }
               
                  for(int i=0;i<edges1.size();i++){
                          
                          PNEdge e=edges1.get(i);                         
                        
                          boolean duplicated=false;
                          for(int j=i+1;j<edges1.size();j++){
                                  if(e.getSource().getIdentifier().equals(edges1.get(j).getSource().getIdentifier())&&
                                                  e.getDest().getIdentifier().equals(edges1.get(j).getDest().getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
                         
                          boolean found=false;
                          for(int j=0;j<edges2.size();j++){
                                  if(e.getSource().getIdentifier().equals(edges2.get(j).getSource().getIdentifier())&&
                                                  e.getDest().getIdentifier().equals(edges2.get(j).getDest().getIdentifier())){
                                          found=true;
                                          break;
                                  }                               
                          }
                          if(found==false) cup++;
                  }
                
                  for(int i=0;i<edges2.size();i++){
                          
                          PNEdge e=edges2.get(i);                         
                         
                          boolean duplicated=false;
                          for(int j=i+1;j<edges2.size();j++){
                                  if(e.getSource().getIdentifier().equals(edges2.get(j).getSource().getIdentifier())&&
                                                  e.getDest().getIdentifier().equals(edges2.get(j).getDest().getIdentifier())){
                                          duplicated=true;
                                          break;
                                  }                               
                          }
                          
                          if(duplicated==true) continue;
                          cup++;
                  }
                
                return cup;
        }
        public static void main(String[] args){
                PetriNetSimilarity sim = new similarityCalculation();
                String file1="F:/Dataset Clone/dataset1.pnml";
                String file2="F:/Dataset Clone/dataset2.pnml";
                FileInputStream is1 = null;
                FileInputStream is2 = null;
                PetriNet pn1 =null;
                PetriNet pn2 =null;
                PnmlImport input = new PnmlImport();
                try {
                        is1 = new FileInputStream(file1);
                        is2 = new FileInputStream(file2);               
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                try {
                        pn1 = input.read(is1);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                try {
                        pn2 = input.read(is2);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                float res = sim.similarityStructural(pn1, pn2);
                System.out.println(res);
                
                
        }

   

    @Override
    public float similarityBehavioral(PetriNet pn1, PetriNet pn2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float similarityStructural(PetriNet pn1, PetriNet pn2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

