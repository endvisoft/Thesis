
import it.uniroma1.lcl.babelfy.Babelfy;
import it.uniroma1.lcl.babelfy.Babelfy.AccessType;
import it.uniroma1.lcl.babelfy.Babelfy.Matching;
import it.uniroma1.lcl.babelfy.data.Annotation;
import it.uniroma1.lcl.babelfy.data.BabelSynsetAnchor;
import it.uniroma1.lcl.jlt.util.Language;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WordSenseDisambiguation {
    public static void main(String[] args) throws Exception {
        // get an instance of the Babelfy RESTful API manager
	Babelfy bfy = Babelfy.getInstance(AccessType.ONLINE);
	    // the string to be disambiguated
	String inputText = "sales order entry";
	    // the actual disambiguation call
	Annotation annotations = bfy.babelfy("", inputText,
	Matching.EXACT, Language.EN);
	    // printing the result
	System.out.println("inputText: "+inputText+"\nannotations:");
	for(BabelSynsetAnchor annotation : annotations.getAnnotations())
	    System.out.println(annotation.getAnchorText()+"\t"+
	        annotation.getBabelSynset().getId()+"\t"+
	        annotation.getBabelSynset());
    }
}
