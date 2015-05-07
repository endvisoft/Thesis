import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.wordnet.WordNet;

public class Example {
	public static void main(String[] args) throws Exception {
	/*	System.setProperty("http.proxyHost", "10.100.1.15");
        //System.setProperty("http.proxyPort", "8080");
        //Authenticator.setDefault(new DummyAuthenticator());
		Babelfy bfy = Babelfy.getInstance(AccessType.ONLINE);
		String inputText = "vendor payment entry";
		Annotation annotations = bfy.babelfy("", inputText,
			Matching.EXACT, Language.EN);
		System.out.println("inputText: "+inputText+"\nannotations:");
		for(BabelSynsetAnchor annotation : annotations.getAnnotations())
			System.out.println (annotation.getAnchorText()+"\t"+
				annotation.getBa belSynset().getId()+"\t"+
				annotation.getBabelSynset());
		for(BabelSynsetAnchor annotation : annotations.getAnnotations())
		{
			BabelSynset synset = annotation.getBabelSynset();
			BabelNet bn = BabelNet.getInstance();
			List<String> wordnetOffsets = synset.getWordNetOffsets();
			//System.out.println(synset.toString()+" "+synset.getId()+" " +synset.getMainSense());
			List<String> coba = BabelNet.getInstance().getSuccessors(synset.getId());
                        System.out.println(synset.getId());
			for(int i=0; i<coba.size(); i++)
				System.out.println(coba.get(i));
			WordNet wn = WordNet.getInstance();
				for (String offset : wordnetOffsets)
				{
                                        
					ISynset wnSynset = wn.getSynsetFromOffset(offset);
					List<IWord> syns = wnSynset.getWords();
                                        //System.out.println(wnSynset.toString());
					List<ISynset> synsets = wn.getHypernyms(wnSynset);
					//System.out.println("ID Utama "+wnSynset.getID());
                                        /*for(int i=0; i<synsets.size(); i++)
                                        {
                                            List<IWord> lemma = synsets.get(i).getWords();
                                            for(int j=0; j<lemma.size(); j++)
                                                System.out.println(lemma.get(j).getLemma());
                                        }*/
                                        
					//System.out.println("kata");
					/*for(int i=0; i<syns.size(); i++){
						System.out.println(syns.get(i).toString());
					}*/
					
		     // code logic here*/
				
			}
	
	/*private static class DummyAuthenticator extends Authenticator {
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               "asrama@its.ac.id", "asramaits".toCharArray()
	               );
	      }
	   }*/
}
