
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.SynsetID;
import it.uniroma1.lcl.babelnet.BabelCategory;
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
import java.net.URL;
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
        URL url = new URL("file",null,"E:\\WordNet-3.0\\dict");
        IDictionary dict = new Dictionary (url);
        dict.open();
        IIndexWord idxWord = dict.getIndexWord ("drink", POS.VERB);
        for(int i=0; i<idxWord.getWordIDs().size(); i++)
        {
            IWord word = dict.getWord(idxWord.getWordIDs().get(i));
            System.out.println("Lemma :"+word);
        }
        }
    
    
}
