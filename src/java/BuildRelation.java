
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
public class BuildRelation {
    
    
    public static void writeRelation(List<WikiPage> listWikiPage) throws IOException{
        for(WikiPage wp : listWikiPage){
            File newTextFile = new File("D:\\S2\\Thesis\\txtdir\\Mapped\\relationdir\\"+wp.getId()+".txt");
            System.out.println(newTextFile.getAbsolutePath());
            FileWriter fw = new FileWriter(newTextFile);
            fw.write("bn:"+wp.getId());
            fw.write("^");
            String[] links = wp.getLinks();
            String[] redirections = wp.getRedirection();
            String[] categories = wp.getCategories();
            List<MySynset> synsets = new ArrayList<>();
            for(int i=0; i<links.length; i++){
                synsets = IndexReader.readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", links[i]);
                for(int j=0; j<synsets.size(); j++){
                    fw.write(synsets.get(j).getSynsetId());
                    fw.write("_");
                }
            }
            for(int i=0; i<redirections.length; i++){
                synsets = IndexReader.readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", redirections[i]);
                for(int j=0; j<synsets.size(); j++){
                    fw.write(synsets.get(j).getSynsetId());
                    fw.write("_");
                }
            }
            for(int i=0; i<categories.length; i++){
                synsets = IndexReader.readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", categories[i]);
                for(int j=0; j<synsets.size(); j++){
                    fw.write(synsets.get(j).getSynsetId());
                    fw.write("_");
                }
            }
            fw.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        File folder = new File("D:\\S2\\Thesis\\dictionary\\xmldir");
	File[] fileList = folder.listFiles();
	List<File> wikifile = new ArrayList<File>();
	for (File file : fileList)
	{
            if (file.getAbsolutePath().endsWith(".xml"))
            {
		wikifile.add(file);
            }
	}
        List<WikiPage> listWikiPage = new ArrayList<>();
        for(File file : wikifile){
            WikiParser parser = new WikiParser(file.getAbsolutePath());
            listWikiPage.add(parser.readWikiPage());
            //System.out.println(file.getAbsolutePath());
        }
        writeRelation(listWikiPage);
    }
}
