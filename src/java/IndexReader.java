
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class IndexReader {
    
    public static List<MySynset> readIndex(String path, String ID) throws IOException{
        IndexSearcher dictionary = new IndexSearcher(path, true);
        String reference = new StringBuffer(ID).toString();
        BooleanQuery q = new BooleanQuery();
        q.add(new BooleanClause(new TermQuery(
				new Term("Title",reference)),Occur.MUST));
        TopDocs docs = dictionary.search(q, 10);
        List<MySynset> synsets = new ArrayList<>();
        for (ScoreDoc scoreDoc : docs.scoreDocs)
	{
	    Document doc = dictionary.doc(scoreDoc.doc);
	    MySynset synset = getMySynsets(doc);
	    synsets.add(synset);
	}
        return synsets;
    }
    
    public static MySynset getMySynsets(Document doc){
        MySynset synset = new MySynset();
        synset.setSynsetId(doc.get("SynsetID"));
        synset.setWordNetID(doc.get("WordNetID"));
        synset.setTitle(doc.get("Title"));
        synset.setLemma(doc.getValues("Lemma"));
        synset.setGloss(doc.get("Gloss"));
        synset.setRelatedLinks(doc.getValues("RelatedLinks"));
        synset.setSource(doc.get("Source"));
        return synset;
    }
    
    public static void main(String[] args) throws Exception {
        List<MySynset> synsets = new ArrayList<>();
        synsets = readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", "ACCA");
        for(int i=0; i<synsets.size(); i++){
            System.out.println(synsets.get(i).getSynsetId());
        }
    }
}
