
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import it.uniroma1.lcl.jlt.wiki.data.WikiPage;
import it.uniroma1.lcl.jlt.wiki.data.WikiText;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class Mapping {
    
    public static void mapping(List<WikiPage> listWikiPage, List<String []> listWikiRed) throws MalformedURLException
    {
        WordNet wn = WordNet.getInstance();
        Map<WikiPage, ISynset> mapped = new HashMap<>();
        for (WikiPage wp : listWikiPage) {
            mapped.put(wp, null);
        }
         for (WikiPage wp : listWikiPage) {
            if(wn.isMonosemous(wp.getLemma(), POS.NOUN)){
                List<ISynset> dummy = new ArrayList<>();
                dummy = wn.getSynsets(wp.getLemma(), POS.NOUN);
                mapped.put(wp, dummy.get(0));
            }
        }
         for (WikiPage wp : listWikiPage) {
            if(mapped.get(wp) == null)
            {
                //if(wp.get)
            }
        }
         for (int i=0; i<listWikiPage.size(); i++) {
            if(mapped.get(i) == null){
                //BOW(listWikiPage.get(i), listWikiRed.get(i));
                contextGraph(listWikiPage.get(i), listWikiRed.get(i));
            }
        }
         
    }
    
    public static void contextGraph(WikiPage wp, String[] listWikiRed) throws MalformedURLException {
        URL url = new URL("file",null,"E:\\WordNet-3.0\\dict");
        IDictionary dict = new Dictionary (url);
        dict.open();
        List<String> contextWiki = new ArrayList<>();
        List<String> contextWordNet = new ArrayList<>();
        int maxDepth = 3;
        contextWiki.addAll(wp.getInfoboxLinks());
        contextWiki.addAll(wp.getLinkedPages());
        contextWiki.addAll(wp.getCategories());
        for(int i=0; i<listWikiRed.length; i++)
        {
            contextWiki.add(listWikiRed[i]);
        }
        /*for(int i=0; i<contextWiki.size(); i++){
            System.out.println(contextWiki.get(i));
        }*/
        IIndexWord idxWord1 = dict.getIndexWord (wp.getLemma(), POS.NOUN);
        Double resultScore [] = new Double[idxWord1.getWordIDs().size()];
        Integer sumWord [] = new Integer[idxWord1.getWordIDs().size()];
        for(int i=0; i<idxWord1.getWordIDs().size(); i++){
            resultScore[i]=0.0;
            sumWord[i] = 0;
        }
        for(int i=0; i<idxWord1.getWordIDs().size(); i++)
        {
            IWord word1 = dict.getWord(idxWord1.getWordIDs().get(i));
            ISynset source = word1.getSynset();
            Double score = 0.0;
            for(int j=0; j<contextWiki.size(); j++){
                IIndexWord idxWord2 = dict.getIndexWord(contextWiki.get(j),POS.NOUN);
                Double result = 0.0;
                if(idxWord2 != null){
                for(int k=0; k<idxWord2.getWordIDs().size(); k++){
                    IWord word2 = dict.getWord(idxWord2.getWordIDs().get(k));
                    ISynset target = word2.getSynset();
                    List<List<ISynset>> path = new ArrayList<>();
                    path = DFS.dls(dict, source, target, maxDepth);
                    double dummyWord = 0;
                    if(!path.isEmpty()){
                    for(int a=0; a<path.size(); a++)
                    {
                        dummyWord = dummyWord + 1/(2.7*path.get(a).size());
                        /*for(int b=0; b<path.get(a).size(); b++)
                        {
                            System.out.println(path.get(a).get(b));
                        }*/
                    }
                    }
                    else
                    {
                        dummyWord = 1/(2.7*(100+1));
                    }
                    result = result + dummyWord;
                }
                }
                score = score + result;
            }
            resultScore[i] = score;
            System.out.println("Selesai "+dict.getWord(idxWord1.getWordIDs().get(i)).getSynset()+" dengan score :"+resultScore[i]);
        }
        int index = 0;
        Double max = 0.0;
        for(int i=0; i< resultScore.length; i++){
            if(resultScore[i] > max){
                max = resultScore[i];
                index = i;
            }
        }
        IWord wordResult = dict.getWord(idxWord1.getWordIDs().get(index));
        ISynset result = wordResult.getSynset();
        System.out.println("Hasilnya adalah "+result+" dengan score : "+resultScore[index]);
    }
    
    public static void BOW(WikiPage wp, String[] listWikiRed) throws MalformedURLException {
        URL url = new URL("file",null,"E:\\WordNet-3.0\\dict");
        IDictionary dict = new Dictionary (url);
        dict.open();
        List<String> contextWiki = new ArrayList<>();
        int max = 0;
        contextWiki.addAll(wp.getInfoboxLinks());
        contextWiki.addAll(wp.getLinkedPages());
        contextWiki.addAll(wp.getCategories());
        for(int i=0; i<listWikiRed.length; i++)
        {
            contextWiki.add(listWikiRed[i]);
        }
        for(int i=0; i<contextWiki.size(); i++){
            System.out.println(contextWiki.get(i));
        }
        IIndexWord idxWord = dict.getIndexWord (wp.getLemma(), POS.NOUN);
        Double resultScore [] = new Double[idxWord.getWordIDs().size()];
        Integer sumWord [] = new Integer[idxWord.getWordIDs().size()];
        for(int i=0; i<idxWord.getWordIDs().size(); i++){
            resultScore[i]=0.0;
            sumWord[i] = 0;
        }
        int index = 0;
        for(int i=0; i<idxWord.getWordIDs().size(); i++)
        {
            IWord word = dict.getWord(idxWord.getWordIDs().get(i));
            ISynset context = word.getSynset();
            List<ISynsetID> synsetIDs = new ArrayList<>();
            List<IWord> words = new ArrayList<>();
            List<String> contextWordNet = new ArrayList<>();
            synsetIDs = context.getRelatedSynsets();
            for(int j=0; j<synsetIDs.size(); j++){
                ISynset synset = dict.getSynset(synsetIDs.get(j));
                List<IWord> relatedWord = synset.getWords();
                for(int k=0; k<relatedWord.size(); k++){
                    contextWordNet.add(relatedWord.get(k).getLemma());
                }
            }
            words = context.getWords();
            for(int j=0; j<words.size(); j++){
                List<IWordID> wordIDs = words.get(j).getRelatedWords(Pointer.DERIVATIONALLY_RELATED);
                for(int k=0; k<wordIDs.size(); k++){
                    contextWordNet.add(dict.getWord(wordIDs.get(k)).getLemma());
                }
            }
            int count = 0;
            for (int a=0; a<contextWiki.size(); a++) {
                if(contextWordNet.contains(contextWiki.get(a))) {
                count++;
            }
            }
            max = max + count;
            sumWord[i]=count;
            for(int iter=0; iter<contextWordNet.size(); iter++)
            System.out.print(contextWordNet.get(iter)+" ");
        }
        for(int i=0; i<sumWord.length; i++){
            resultScore[i] =sumWord[i].doubleValue()/max;
            System.out.println("result untuk"+ idxWord.getWordIDs().get(i) +" adalah "+resultScore[i]);
        }
        Double maxScore = 0.0;
        for(int i=0; i<sumWord.length; i++){
            if(resultScore[i]>maxScore){
                maxScore = resultScore[i];
                index = i;
            }
        }
        //for(int iter=0; iter<contextWiki.size(); iter++)
          //  System.out.print(contextWiki.get(iter)+" ");
        
        IWord result = dict.getWord(idxWord.getWordIDs().get(index));
        ISynset resultSynset = result.getSynset();
        System.out.println("Hasilnya"+ wp.getTitle() +"dengan Bag of Words : "+resultSynset);
    }
    
    public static void main(String[] args) throws Exception {
         WikiText text1 = new WikiText();
         WikiPage wiki1 = new WikiPage("wiki01", "Play(Theatre)", "play", text1, 
                                      new String[] {"cruelty","absurd","satyr","act","actor","comedy","drama","epic","erotic","romance","tragedy","performance","comedy","historical","literature","playwright","dialogue","character","theatrical","broadway","theatre","musical","shakespeare","farces","screenplay","dramatic","satirical"} ,
                                      new String[] {"literature","playwright","dialogue","character","theatrical","Broadway","theatre","Musical","Shakespeare","farces","drama","screenplay"}, 
                                      new String[] {"theatre", "art"}, 
                                      new String[] {}, 
                                      new String[] {}, 
                                      new String[] {}, 
                                      false);
         
         WikiText text2 = new WikiText();
         WikiPage wiki2 = new WikiPage("wiki02", "Play(Activity)", "play", text1, 
                                      new String[] {"fixed_rule","imagination","activity","phsycology","Piaget","Freud","child","children","goal","playground","happiness","museum","socialization","toy","prop","playmate","neuroscience","gameplay","childhood","friend","playwork","playspace","behavior"},
                                      new String[] {}, 
                                      new String[] {"learning"}, 
                                      new String[] {}, 
                                      new String[] {}, 
                                      new String[] {}, 
                                      false);
        
        String wikiRed1 [] = new String[] {"playlet","stageplay","stage","genre","theatrical"};
        String wikiRed2 [] = new String[] {"animal","behaviour"};
        List<WikiPage> listWiki = new ArrayList<>();
        List<String []> listWikiRed = new ArrayList<>();
        //listWiki.add(wiki1);
        listWiki.add(wiki2);
        //listWikiRed.add(wikiRed1);
        listWikiRed.add(wikiRed2);
        mapping(listWiki, listWikiRed);
        }
}
