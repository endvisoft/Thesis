
import it.uniroma1.lcl.jlt.util.Files;
import it.uniroma1.lcl.jlt.util.Triple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class FileIndexer {
    
    //private static final String PATH_SEPARATOR = "|";
    
    public static void createIndex(File txtFileDir, File indexDir) throws IOException
    {
        if (!indexDir.exists()) indexDir.mkdirs();
	else if (!Files.isDirectoryEmpty(indexDir))
	{
            System.out.println(indexDir + " IS NOT EMPTY, BAILING OUT ...");
            return;
	}
		
	IndexWriter indexWriter = new IndexWriter(
		new SimpleFSDirectory(indexDir),
		new StandardAnalyzer(Version.LUCENE_29),
		true,
		IndexWriter.MaxFieldLength.UNLIMITED);
		
	int counter = 0;
	FilenameFilter filter = new FilenameFilter()
	{
            public boolean accept(File dir, String name)
            { return name.endsWith(".txt"); }
        };
	Collection<File> files = Files.listFiles(txtFileDir, filter, true);
		
	for (File file : files)
	{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            Set<List<String>> listSynset = new HashSet<List<String>>();
            while (reader.ready())
            {
                String line = reader.readLine();
		listSynset.add(Arrays.asList(line.split("\\^")));
            }
            reader.close();
            
            List<Document> docs = getRecords(listSynset);
            for (Document doc : docs) indexWriter.addDocument(doc);
			
            if ((counter%1000)==0)
            System.out.println("\tINDEXED PATHS FOR " +  counter + " CONCEPTS SO FAR ... ");
			counter++;
            }
	System.out.println("\tINDEXED " + counter + " CONCEPTS, DONE!");
		
	// closes the index writers
	indexWriter.optimize(); indexWriter.close();
    }
    private static List<Document> getRecords(Set<List<String>> listSynset)
    {
    // the Lucene records
    List<Document> docs = new ArrayList<Document>();
		
    for (List<String> synset : listSynset)
    {
        System.out.println("index : "+synset.get(0));
        String SynsetId = synset.get(0);
	String WordNetID = synset.get(1);
        String Title = synset.get(2);
        String Gloss = synset.get(3);
        System.out.println("Lemma : "+synset.get(4));
        String[] Lemma = synset.get(4).split("_");
        String[] RelatedLinks = synset.get(5).split("_");
        String Source = synset.get(6);
	//System.out.println(id+" "+title+" "+lemma+" "+category+" "+isRedirect);
			// log.info("PATH DOC FOR " + firstPathElement + 
			//		"\n\t\t START: " + firstPathElement +
			//		"\n\t\t START: " + lastPathElement +
			//		"\n\t\t PATHS: " + pathsString);
			
			// creates a new document
	Document doc = new Document();
	doc.add(new Field("SynsetID",
        SynsetId, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("WordNetID",
	WordNetID, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("Title",
	Title, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("Gloss",
	Gloss, Field.Store.YES, Field.Index.NOT_ANALYZED));
        for(int i=0; i<Lemma.length; i++){
	doc.add(new Field("Lemma", 
	Lemma[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
        //System.out.println(Lemma[i]);
        }
        for(int i=0; i<RelatedLinks.length; i++){
	doc.add(new Field("RelatedLinks", 
	RelatedLinks[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
        //System.out.println(Lemma[i]);
        }
	doc.add(new Field("Source", 
	Source, Field.Store.YES, Field.Index.NOT_ANALYZED));
		
	docs.add(doc);
        }
    return docs;
}
    public static void main(String[] args) throws Exception {
        File txtIndexDir = new File("D:\\S2\\Thesis\\txtdir\\Mapped\\txtdir\\");
        File indexDir = new File("D:\\S2\\Thesis\\txtdir\\cobatxt\\index\\");
        createIndex(txtIndexDir, indexDir);
    }
}
