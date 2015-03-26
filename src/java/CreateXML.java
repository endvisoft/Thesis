/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import info.bliki.api.Connector;
import info.bliki.api.PageInfo;
import info.bliki.api.User;
import info.bliki.api.XMLCategoryMembersParser;
import info.bliki.api.query.Query;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 *
 * @author User
 */
public class CreateXML {
    private static DocumentBuilderFactory dbFactory;
    private static Document doc;
    private static DocumentBuilder dBuilder;
    public static void main(String[] args)
    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("D://S2//Thesis//txtdir//Business terms.txt");
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
                getDetail(line);
                }   br.close();
                fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    
    public static void getDetail(String ID)
    {
        try {
            String[] valuePairs = new String[]{ "pageids", ID , "prop","extracts|info|categories|links|redirects|pageprops","ppprop","disambiguation","rdlimit","max","cllimit","max","pllimit","max","redirects",""};
            User user = new User("", "", "http://en.wikipedia.org/w/api.php");
            user.login();
            Connector connector = new Connector();
            /*HttpClient client = connector.getClient();
            HostConfiguration hostConfiguration = client.getHostConfiguration();
            hostConfiguration.setProxy("proxy.its.ac.id", 8080);
            client.setHostConfiguration(hostConfiguration);
            Credentials cred = new UsernamePasswordCredentials("divi.galih10@mhs.if.its.ac.id","prasetyoputri");
            client.getState().setProxyCredentials(AuthScope.ANY, cred);*/

            XMLCategoryMembersParser parser;
            Query q = new Query();
            //q.generator("http://en.wikipedia.org/w/api.php?action=query&pageids=11601783&prop=info|categories|links|redirects|pageprops|extracts&ppprop=disambiguation&format=xml&redirects");
            String responseBody = connector.queryXML(user, valuePairs);
          System.out.println(ID);
            
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(responseBody));
            doc = dBuilder.parse(is);
            writeFile(ID);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void writeFile(String ID)
    {
        doc.getDocumentElement().normalize();
        String id = doc.getElementsByTagName("page").item(0).getAttributes().getNamedItem("pageid").getTextContent();
        String title = doc.getElementsByTagName("page").item(0).getAttributes().getNamedItem("title").getTextContent();
        //String isRedirect = doc.getElementsByTagName("page").item(0).getAttributes().getNamedItem("redirect").getTextContent();
        System.out.println(id+" "+title);
        NodeList cllist = doc.getElementsByTagName("cl");
        NodeList rdlist = doc.getElementsByTagName("rd");
        NodeList linklist = doc.getElementsByTagName("pl");
        String teks = doc.getElementsByTagName("extract").item(0).getTextContent(); 
        try {
            File file = new File("D://S2//Thesis//txtdir//Business terms//"+ID+".xml");
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
            bufferedWriter.write("<id>"+id+"</id>");
            bufferedWriter.newLine();
            bufferedWriter.write("<title>"+title+"</title>");
            bufferedWriter.newLine();
            bufferedWriter.write("<lemma>"+title+"</lemma>");
            bufferedWriter.newLine();
            String sense="";
            if(title.contains("(") && !title.contains("disambiguation"))
            {
                String tmp = title.split("[(]")[1];
                sense = tmp.split("[)]")[0];
            }
            bufferedWriter.write("<sense>"+sense+"</sense>");
            bufferedWriter.newLine();
            bufferedWriter.write("<text>"+teks+"</text>");
            bufferedWriter.newLine();
            bufferedWriter.write("<redirection>");
            for(int a=0;a<rdlist.getLength();a++)
            {
                bufferedWriter.write(rdlist.item(a).getAttributes().getNamedItem("title").getTextContent()+"_");
            }
            bufferedWriter.write("</redirection>");
            bufferedWriter.newLine();
            bufferedWriter.write("<link>");
            for(int a=0;a<linklist.getLength();a++)
            {
                bufferedWriter.write(linklist.item(a).getAttributes().getNamedItem("title").getTextContent()+"_");
            }
            bufferedWriter.write("</link>");
            bufferedWriter.newLine();
            bufferedWriter.write("<category>");
            for(int a=0;a<cllist.getLength();a++)
            {
                String kategori = cllist.item(a).getAttributes().getNamedItem("title").getTextContent().split("Category:")[1];
                bufferedWriter.write(kategori+"_");
            }
            bufferedWriter.write("</category>");
            bufferedWriter.newLine();
            if(doc.getElementsByTagName("page").item(0).getAttributes().getNamedItem("redirect")!=null)
            {
                bufferedWriter.write("<isRedirect>yes</isRedirect>");
            }
            else bufferedWriter.write("<isRedirect>no</isRedirect>");
            bufferedWriter.newLine();
            if(doc.getElementsByTagName("pageprops").item(0)!=null)
            {
                if(doc.getElementsByTagName("pageprops").item(0).getAttributes().getNamedItem("disambiguation")!=null)
                bufferedWriter.write("<isDisambiguation>yes</isDisambiguation>");
            }
            else bufferedWriter.write("<isDisambiguation>no</isDisambiguation>");
            bufferedWriter.newLine();  
            bufferedWriter.write("<source>wikipedia</source>");
            bufferedWriter.newLine();
            bufferedWriter.write("</wikipage>");
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(CreateXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}
    