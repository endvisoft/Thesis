

import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.jlt.util.Language;
import java.util.List;



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
	Babelfy bfy = new Babelfy();
        BabelNet bn = BabelNet.getInstance();
        String inputText = "production order information system";
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN);
	for (SemanticAnnotation annotation : bfyAnnotations)
        {
            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = inputText.substring(annotation.getCharOffsetFragment().getStart(),
		annotation.getCharOffsetFragment().getEnd() + 1).toLowerCase();
            BabelSynset synset = bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
            System.out.println(frag);
            System.out.println(synset.getMainSense());
            System.out.println(synset.getMainGloss(Language.EN));
        }
    }
}
