
import java.util.Collection;
import java.util.Set;
import edu.jhu.nlp.wikipedia.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WikiReader {
     public static void main(String[] args) throws Exception {
         //it.uniroma1.lcl.jlt.wiki.data.WikiPage wiki;
         
         WikiXMLParser wxp = WikiXMLParserFactory.getSAXParser("D:\\S2\\Thesis\\Wiki\\wikidump.xml");
         /*try
         {
             wxp.setPageCallback(new PageCallbackHandler() {

                 @Override
                 public void process(WikiPage wp) {
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                     //System.out.println("Redirect : "+wp.getRedirectPage());
                     //System.out.println("Tittle : "+wp.getTitle());
                     //System.out.println("Links : "+wp.getLinks());
                     //System.out.println("Text : "+wp.getText());
                     System.out.println(wp.toString());
                 }

                 @Override
                 public void process(WikiPage wp) {
                     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 }
             });
             wxp.parse();
         }
         catch(Exception e){
            e.printStackTrace();
         }*/
                        
                 
     }
}
