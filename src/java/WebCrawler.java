
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class WebCrawler {
    private static class DummyAuthenticator extends Authenticator {
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               "asrama@its.ac.id", "asramaits".toCharArray()
	               );
	      }
	   }
    public static void main(String[] args) throws Exception {
    //System.setProperty("http.proxyHost", "proxy.its.ac.id");
    //System.setProperty("http.proxyPort", "8080");
    //Authenticator.setDefault(new DummyAuthenticator());
    Document doc = Jsoup.connect("http://www.businessdictionary.com/terms-by-subject.php?subject=20").get();
    Elements column = doc.getElementsByClass("column");
    for(int i=1; i<column.size(); i++){
        Elements links = column.get(i).getElementsByTag("a");
        for(int j=409; j<442; j++){
            String link = links.get(j).attr("href");
            String text = links.get(j).text();
            System.out.println(j);
            System.out.println(text);
            File file = new File("D://S2//Thesis//dictionary//HR, Recruiting, Teams, & Training//"+text+".xml");
            if(!file.exists()){
                file.createNewFile();
            }
            Writer fileWriter = null;
            BufferedWriter bufferedWriter = null;
            fileWriter = new FileWriter(file,true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            bufferedWriter.newLine();
            bufferedWriter.write("<wikipage>");
            bufferedWriter.newLine();
            bufferedWriter.write("<id>"+text+"</id>");
            bufferedWriter.newLine();
            bufferedWriter.write("<title>"+text+"</title>");
            bufferedWriter.newLine();
            bufferedWriter.write("<lemma>"+text+"</lemma>");
            bufferedWriter.newLine();
            bufferedWriter.write("<sense></sense>");
            bufferedWriter.newLine();
            if(link != ""){
                Document concept = Jsoup.connect(link).get();
                if(!concept.title().toString().equals("BusinessDictionary.com - Online Business Dictionary")){
                    Element related = concept.getElementById("terms_in_definition_container");
                    Element innerLink = concept.getElementById("definition_div");
                    Elements definitions = concept.getElementsByTag("meta");
                    //System.out.println(definitions.size());
                    for(int k=0; k<definitions.size(); k++){
                        //System.out.println(definitions.get(k).attr("name"));
                        if(definitions.get(k).attr("name").toString().equals("description"))
                        {
                            bufferedWriter.write("<text>"+definitions.get(k).attr("content")+"</text>");
                            bufferedWriter.newLine();
                        }
                    }
                    bufferedWriter.write("<redirection>");
                    if(related != null )
                    {
                        Elements relateds = related.getElementsByTag("a");
                        for(int k=0; k<relateds.size(); k++){
                        bufferedWriter.write(relateds.get(k).text()+"_");
                        }
                    }
                    bufferedWriter.write("</redirection>");
                    bufferedWriter.newLine();
                    bufferedWriter.write("<link>");
                    if(innerLink != null)
                    {
                        Elements innerLinks = innerLink.getElementsByTag("a");
                        for(int k=0; k<innerLinks.size(); k++){
                        bufferedWriter.write(innerLinks.get(k).text()+"_");
                        }
                    }
                    bufferedWriter.write("</link>");
                    bufferedWriter.newLine();
                }
                else
                {
                    bufferedWriter.write("<text></text>");
                    bufferedWriter.newLine();
                    bufferedWriter.write("<redirection></redirection>");
                    bufferedWriter.newLine();
                    bufferedWriter.write("<link></link>");
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.write("<category></category>");
            bufferedWriter.newLine();
            bufferedWriter.write("<isRedirect>no</isRedirect>");
            bufferedWriter.newLine();
            bufferedWriter.write("<isDisambiguation>no</isDisambiguation>");
            bufferedWriter.newLine();
            bufferedWriter.write("<source>businessdictionary.com</source>");
            bufferedWriter.newLine();
            bufferedWriter.write("</wikipage>");
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        }
    }
    }
}
