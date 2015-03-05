
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
    
    public static List<WikiPage> readIndex(String path, String ID) throws IOException{
        IndexSearcher dictionary = new IndexSearcher(path, true);
        String reference = new StringBuffer(ID).toString();
        BooleanQuery q = new BooleanQuery();
        q.add(new BooleanClause(new TermQuery(
				new Term("ID",reference)),Occur.MUST));
        TopDocs docs = dictionary.search(q, 10);
        List<WikiPage> wikis = new ArrayList<>();
        for (ScoreDoc scoreDoc : docs.scoreDocs)
	{
	    Document doc = dictionary.doc(scoreDoc.doc);
	    WikiPage wiki = getWikiPage(doc);
	    wikis.add(wiki);
	}
        return wikis;
    }
    
    public static WikiPage getWikiPage(Document doc){
        WikiPage wiki = new WikiPage();
        wiki.setId(doc.get("ID"));
        wiki.setTitle(doc.get("Title"));
        wiki.setLemma(doc.get("Lemma"));
        wiki.setSense(doc.get("Sense"));
        wiki.setLinks(doc.get("Link").split("_"));
        wiki.setRedirection(doc.get("Redirection").split("_"));
        wiki.setCategories(doc.get("Category").split("_"));
        wiki.setText(doc.get("Text"));
        wiki.setIsDisambiguation(Boolean.TRUE);
        wiki.setIsRedirection(Boolean.TRUE);
        return wiki;
    }
    
    public static void main(String[] args) throws Exception {
        List<WikiPage> wikis = new ArrayList<>();
        wikis = readIndex("D:\\S2\\Thesis\\txtdir\\index", "wiki002");
        if(!wikis.isEmpty())
        System.out.println(wikis.get(0).getTitle());
        else
            System.out.println("Kosong");
    }
}
