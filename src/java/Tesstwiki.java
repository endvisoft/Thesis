/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import info.bliki.api.Connector;
import info.bliki.api.Page;
import info.bliki.api.PageInfo;
import info.bliki.api.User;
import info.bliki.api.XMLCategoryMembersParser;
import info.bliki.wiki.model.WikiModel;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import edu.jhu.nlp.wikipedia.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

/**
 *
 * @author User
 */
public class Tesstwiki {

    /**
     * @param args the command line arguments
     */
    private static List<PageInfo> resultCategoryMembers;
    private static String cmContinue="";
    private static Writer fileWriter = null;
    private static BufferedWriter bufferedWriter = null;
    public static void main(String[] args) throws FileNotFoundException{
    /*    System.setProperty("http.proxyHost", "proxy.its.ac.id");
        System.setProperty("http.proxyPort", "8080");
        Authenticator.setDefault(new DummyAuthenticator());
        */
        try {
            // TODO code application logic here
            resultCategoryMembers = new ArrayList<PageInfo>();
            File file = new File("D://S2//Thesis//txtdir//Small business.txt");
	    if(!file.exists()){
    			file.createNewFile();
    		}
            fileWriter = new FileWriter(file,true);
	    bufferedWriter = new BufferedWriter(fileWriter);
            testQueryCategoryMembers("Category:Small business");
            for (PageInfo page : resultCategoryMembers) {
                if(!page.getTitle().contains("Category:"))
                {
                       bufferedWriter.write(page.getPageid());
                       bufferedWriter.newLine();
                }
                
            }
            bufferedWriter.close();
            fileWriter.close();
            // getPage();
        } catch (IOException ex) {
            Logger.getLogger(Tesstwiki.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void QueryCategoryMembers(String kategori) {
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        String[] valuePairs = { "list", "categorymembers", "cmtitle", kategori };
        String[] valuePairsContinue = new String[6];
        for (int i = 0; i < valuePairs.length; i++) {
            valuePairsContinue[i] = valuePairs[i];
        }
        valuePairsContinue[4] = "cmcontinue";
        valuePairsContinue[5] = "";
        cmContinue="";
        Connector connector = new Connector();
        HttpClient client = connector.getClient();
            HostConfiguration hostConfiguration = client.getHostConfiguration();
            hostConfiguration.setProxy("proxy.its.ac.id", 8080);
            client.setHostConfiguration(hostConfiguration);
            Credentials cred = new UsernamePasswordCredentials("divi.galih10@mhs.if.its.ac.id","prasetyoputri");
            client.getState().setProxyCredentials(AuthScope.ANY, cred);

        XMLCategoryMembersParser parser;
        try {
            // get all categorymembers
            String responseBody = connector.queryXML(user, valuePairs);
            while (responseBody != null) {
                System.out.println("running "+kategori); 
                parser = new XMLCategoryMembersParser(responseBody);
                parser.parse();
                SAXParserFactory parserFactor = SAXParserFactory.newInstance();
                SAXParser parsers = parserFactor.newSAXParser();
	        CategoryMemberParser handler = new CategoryMemberParser();
	 
	        parsers.parse(new StringBufferInputStream(responseBody), handler);
                List<PageInfo> listOfPages = parser.getPagesList();
                if(!listOfPages.isEmpty())
                    if(resultCategoryMembers.contains(listOfPages.get(0)))
                        break;
                resultCategoryMembers.addAll(listOfPages);
                for (PageInfo categoryMember : listOfPages) {
                    System.out.println(categoryMember.toString());
                    if(categoryMember.getTitle().contains("Category:") && !listOfPages.contains(categoryMember.getTitle()))
                        QueryCategoryMembers(categoryMember.getTitle());
                }
                if (cmContinue.length() > 0) {
                    valuePairsContinue[5] = cmContinue;
                    responseBody = connector.queryXML(user, valuePairsContinue);
                } else {
                    break;
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Tesstwiki.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("TOTAL: "+resultCategoryMembers.size());
    }

    public static void testQueryCategoryMembers(String kategori) throws IOException {
        
        Stack<String> stack = new Stack<>();
        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
        user.login();
        stack.add(kategori);
      
        while(!stack.isEmpty())
        {
            System.out.println(stack.peek());
            String[] valuePairs = { "list", "categorymembers", "cmtitle", stack.pop()};
            String[] valuePairsContinue = new String[6];
            for (int i = 0; i < valuePairs.length; i++) {
                    valuePairsContinue[i] = valuePairs[i];
                }
            valuePairsContinue[4] = "cmcontinue";
            valuePairsContinue[5] = "";
            cmContinue="";
            Connector connector = new Connector();
            
            HttpClient client = connector.getClient();
            HostConfiguration hostConfiguration = client.getHostConfiguration();
            hostConfiguration.setProxy("proxy.its.ac.id", 8080);
            client.setHostConfiguration(hostConfiguration);
            Credentials cred = new UsernamePasswordCredentials("divi.galih10@mhs.if.its.ac.id","prasetyoputri");
            client.getState().setProxyCredentials(AuthScope.ANY, cred);

            
            XMLCategoryMembersParser parser;
            try {
                    String responseBody = connector.queryXML(user, valuePairs);
             while(responseBody!=null)
             {
                    for(int a=0;a<valuePairsContinue.length;a++)
                        System.out.print(valuePairsContinue[a]+" ");
                    System.out.println("");
                    parser = new XMLCategoryMembersParser(responseBody);
                    parser.parse();
                    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
                    SAXParser parsers = parserFactor.newSAXParser();
                    CategoryMemberParser handler = new CategoryMemberParser();
                    System.out.println("ini"+responseBody);
                    System.out.println(resultCategoryMembers.size());
                    parsers.parse(new StringBufferInputStream(responseBody), handler);
                    List<PageInfo> listOfPages = parser.getPagesList();
                    if(!listOfPages.isEmpty())
                        if(resultCategoryMembers.contains(listOfPages.get(0)))
                            break;
                    resultCategoryMembers.addAll(listOfPages);
                    for (PageInfo categoryMember : listOfPages) {
                        System.out.println(categoryMember.toString());
                        if(categoryMember.getTitle().contains("Category:"))
                            stack.add(categoryMember.getTitle());
                        
                    }
                    if (cmContinue.length() > 0) {
                        valuePairsContinue[5] = cmContinue;
                        responseBody = connector.queryXML(user, valuePairsContinue);
                    } else {
                        break;
                    }
                }
            } 
            catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Tesstwiki.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedByteSequenceException ex) {
                Logger.getLogger(Tesstwiki.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        }
             System.out.println("TOTAL: "+resultCategoryMembers.size());
    }
    
    /*public static void getPage()
    {
         
         WikiXMLParser wxp = WikiXMLParserFactory.getSAXParser("E:\\dadang\\thesis\\Wiki\\wikidump.xml");
         try
         {
             wxp.setPageCallback(new PageCallbackHandler() {

                 @Override
                 public void process(WikiPage wp) {
                     //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                     System.out.println("Redirect : "+wp.getRedirectPage());
                     System.out.println("Tittle : "+wp.getTitle());
                     System.out.println("Links : "+wp.getLinks());
                     System.out.println("Text : "+wp.getText());
                     System.out.println("Gantii....");
                 }
             });
             wxp.parse();
         }
         catch(Exception e){
            e.printStackTrace();
         }
                        
    }*/
    
    public static void getRedirect()
    {
            String[] valuePairs = { "pageids", "34344124", "prop","info|categories|redirects|pageprops","ppprop","disambiguation","rdlimit","max"};
            User user = new User("", "", "http://en.wikipedia.org/w/api.php");
            user.login();
            Connector connector = new Connector();
            XMLCategoryMembersParser parser;
            
            String responseBody = connector.queryXML(user, valuePairs);
    }
    
    /**
     * @return the cmContinue
     */
    public static String getCmContinue() {
        return cmContinue;
    }

    /**
     * @param cmContinue the cmContinue to set
     */
    public static void setCmContinue(String cmContinue) {
        Tesstwiki.cmContinue = cmContinue;
    }
    
    private static class DummyAuthenticator extends Authenticator {
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               "divi.galih10@mhs.if.its.ac.id", "prasetyoputri".toCharArray()
	               );
	      }
	   }
    
}
