
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.SynsetID;
import it.uniroma1.lcl.babelnet.BabelGloss;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.iterators.BabelLexiconIterator;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.wiki.EntityList;
import it.uniroma1.lcl.jlt.wiki.IndexWiki;
import it.uniroma1.lcl.jlt.wiki.SearchWiki;
import it.uniroma1.lcl.jlt.wiki.SearchWikiCategory;
import it.uniroma1.lcl.jlt.wiki.SearchWikiCategoryCentral;
import it.uniroma1.lcl.jlt.wiki.WikiDumpConverter;
import it.uniroma1.lcl.jlt.wiki.WikiMapper;
import it.uniroma1.lcl.jlt.wiki.data.WikiCategory;
import it.uniroma1.lcl.jlt.wiki.data.WikiPage;
import it.uniroma1.lcl.jlt.wiki.iterator.WikiDumpExtractor;
import it.uniroma1.lcl.jlt.wiki.iterator.WikiDumpIterator;
import it.uniroma1.lcl.jlt.wikico.WikicoDB;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class BuildBabelNet {
    public static void main(String[] args) throws Exception {
        BabelNet bn = BabelNet.getInstance();
        WordNet wn = WordNet.getInstance();
        WikiDumpExtractor ex = new WikiDumpExtractor(null);
        WikiDumpIterator iter = new WikiDumpIterator(null);
        WikiCategory cate;
        WikiPage page;
        WikiMapper map = new WikiMapper(null, null);
        SearchWiki search = new SearchWiki("");
        Set<WikiPage> list = search.getPagesByCategoryTitle("Business");
        Set<String> listcate= SearchWikiCategory.class.newInstance().getSubCategories("Business");
        BabelSynset listSynset = bn.getSynsetFromId("bn:00028604n");
        //for(BabelSynset wnSynset : listSynset){
                	/*System.out.println(wnSynset.toString());
                        if(!wn.isMonosemous("payment", POS.NOUN))
                        {
                            System.out.println("Bukan Mono Nih");
                        } else {
                            System.out.println("Mono Nih");
                        }
                        List<ISynsetID> derive = wnSynset.getRelatedSynsets(IPointer.class.newInstance());
        		List<ISynset> hypernymy = wn.getHypernyms(wnSynset);
        		List<ISynset> hyponymy = wn.getHyponyms(wnSynset);
        		List<ISynset> sibling = wn.getSiblings(wnSynset);
        		Set<ISynset> related = wn.getRelatedSynsets(wnSynset);
        		Set<ISynset> derivation = wn.getDescendants(wnSynset);
        		System.out.println(wnSynset.getGloss());
        		System.out.println("hypernymy");
        		for(int a=0; a<hypernymy.size(); a++){
        			System.out.println(hypernymy.get(a));
        		}
        		System.out.println("hyponymy");
        		for(int a=0; a<hyponymy.size(); a++){
        			System.out.println(hyponymy.get(a));
        		}
        		System.out.println("siblings");
        		for(int a=0; a<sibling.size(); a++){
        			System.out.println(sibling.get(a));
        		}
        		System.out.println("related");
        		for(ISynset s : related){
        			System.out.println(s);
        		}
        		System.out.println("derivation");
        		for(ISynset d : derivation){
        			System.out.println(d);
        		}*/
                        Map<IPointer,List<BabelSynset>> relation = listSynset.getRelatedMap();
                        for (IPointer key : relation.keySet()) {
                            List<BabelSynset> value = relation.get(key);
                            for(int i=0; i<value.size(); i++)
                            System.out.println("Key = " + key + ", Value = " + value.get(i));
                        }
          
        }
    
    
}
