
import edu.mit.jwi.item.ISynset;
import it.uniroma1.lcl.babelnet.BabelSynset;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WriteTXT {
    
    public static void write(WikiPage wp, ISynset synset) throws IOException{
        File newTextFile = new File("D:\\S2\\Thesis\\txtdir\\Wiki\\Business Term\\"+wp.getId()+".txt");
        FileWriter fw = new FileWriter(newTextFile);
        fw.write("bn:"+wp.getId());
        fw.write("^");
        fw.write(synset.getID().toString());
        fw.write("^");
        fw.write(wp.getTitle());
        fw.write("^");
        fw.write(synset.getGloss());
        fw.write("^");
        fw.write(wp.getLemma());
        fw.write("_");
        for(int i=0; i<wp.getRedirection().length; i++){
            if(wp.getRedirection()[i] != ""){
            fw.write(wp.getRedirection()[i]);
            fw.write("_");
            }
        }
        for(int i=0; i<synset.getWords().size(); i++)
        {
            fw.write(synset.getWords().get(i).getLemma());
            fw.write("_");
        }
        fw.write("^");
        for(int i=0; i<wp.getLinks().length; i++){
            if(wp.getLinks()[i] != ""){
            fw.write(wp.getLinks()[i]);
            fw.write("_");
            }
        }
        /*for(int i=0; i<wp.getRedirection().length; i++){
            if(wp.getRedirection()[i] != ""){
            fw.write(wp.getRedirection()[i]);
            fw.write("_");
            }
        }*/
        for(int i=0; i<wp.getCategories().length; i++){
            if(wp.getCategories()[i] != ""){
            fw.write(wp.getCategories()[i]);
            fw.write("_");
            }
        }
        fw.write("^");
        fw.write(wp.getSource());
        fw.close();
    }
    public static void writeNew(WikiPage wp) throws IOException{
        File newTextFile = new File("D:\\S2\\Thesis\\txtdir\\Wiki\\Business Term\\"+wp.getId()+".txt");
        FileWriter fw = new FileWriter(newTextFile);
        fw.write("bn:"+wp.getId());
        fw.write("^");
        fw.write("null");
        fw.write("^");
        fw.write(wp.getTitle());
        fw.write("^");
        fw.write(wp.getText());
        fw.write("^");
        fw.write(wp.getLemma());
        fw.write("_");
        for(int i=0; i<wp.getRedirection().length; i++){
            if(wp.getRedirection()[i] != ""){
            fw.write(wp.getRedirection()[i]);
            fw.write("_");
            }
        }
        fw.write("^");
        for(int i=0; i<wp.getLinks().length; i++){
            if(wp.getLinks()[i] != ""){
            fw.write(wp.getLinks()[i]);
            fw.write("_");
            }
        }
        /*for(int i=0; i<wp.getRedirection().length; i++){
            if(wp.getRedirection()[i] != ""){
            fw.write(wp.getRedirection()[i]);
            fw.write("_");
            }
        }*/
        for(int i=0; i<wp.getCategories().length; i++){
            if(wp.getCategories()[i] != ""){
            fw.write(wp.getCategories()[i]);
            fw.write("_");
            }
        }
        fw.write("^");
        fw.write(wp.getSource());
        fw.close();
    }
    
    
    public static void main(String[] args) throws Exception {
        File txtIndexDir = new File("D:\\S2\\Thesis\\txtdir\\cobatxt");
    }
}
