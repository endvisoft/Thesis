
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WikiParser {
    File fXmlFile;
    DocumentBuilderFactory dbFactory;
    Document doc;
    DocumentBuilder dBuilder;
    public WikiPage wikipage;
    
    public WikiParser(String file){
        try
	{
            fXmlFile = new File(file);
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            wikipage = new WikiPage();
	}
	catch(Exception ex)
	{
            ex.printStackTrace();
	}
    }
    
    public WikiPage readWikiPage()
    {
    try
        {
            doc.getDocumentElement().normalize();
	 		
            wikipage = new WikiPage();
            wikipage.setId(doc.getElementsByTagName("id").item(0).getTextContent());
            wikipage.setTitle(doc.getElementsByTagName("title").item(0).getTextContent());
            wikipage.setLemma(doc.getElementsByTagName("lemma").item(0).getTextContent());
            if(doc.getElementsByTagName("isDisambiguation").item(0).getTextContent().equals("no"))
            {
                wikipage.setIsDisambiguation(Boolean.FALSE);
            }
            else
            {
                wikipage.setIsDisambiguation(Boolean.TRUE);
            }
            if(doc.getElementsByTagName("isRedirect").item(0).getTextContent().equals("no"))
            {
                wikipage.setIsRedirection(Boolean.FALSE);
            }
            else
            {
                wikipage.setIsRedirection(Boolean.TRUE);
            }
            wikipage.setText(doc.getElementsByTagName("text").item(0).getTextContent());
            wikipage.setRedirection(doc.getElementsByTagName("redirection").item(0).getTextContent().split("_"));
            wikipage.setLinks(doc.getElementsByTagName("link").item(0).getTextContent().split("_"));
            wikipage.setCategories(doc.getElementsByTagName("category").item(0).getTextContent().split("_"));
	}
	catch(Exception ex)
	{
            ex.printStackTrace();
	}
	return wikipage;
    }
    
    public static void main(String[] args) throws Exception {
        WikiParser coba = new WikiParser("D:\\S2\\Thesis\\XML.xml");
        WikiPage wiki = new WikiPage();
        wiki = coba.readWikiPage();
        System.out.println("ID :"+wiki.getId());
        System.out.println("Tittle :"+wiki.getTitle());
        System.out.println("lemma :"+wiki.getLemma());
        System.out.println("Links :");
        for(int i=0; i<wiki.getLinks().length; i++){
            System.out.println(wiki.getLinks()[i]);
        }
        System.out.println("Category :");
        for(int i=0; i<wiki.getCategories().length; i++){
            System.out.println(wiki.getCategories()[i]);
        }
        System.out.println("Redirection :");
        for(int i=0; i<wiki.getRedirection().length; i++){
            System.out.println(wiki.getRedirection()[i]);
        }
      
    }
}
