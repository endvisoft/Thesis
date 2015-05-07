/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import it.uniroma1.lcl.babelfy.commons.BabelfyConfiguration;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetConfiguration;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.jlt.Configuration;
import it.uniroma1.lcl.jlt.util.Language;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/ControlServlet/*"})
public class ControlServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    class StringComparator implements Comparator<String>{
        
        @Override
        public int compare(String s1, String s2){
            int len1 = s1.length();
            int len2 = s2.length();
            
            if(len1 > len2){
                return -1;
            }
            else if(len1 < len2){
                return 1;
            }
            
            return 0;
        }
    }
    private static class DummyAuthenticator extends Authenticator {
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               "asrama@its.ac.id", "asramaits".toCharArray()
	               );
	      }
	   }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException  {
       
            MySynset hasilFrasa = new MySynset();
            List<Result> hasil = new ArrayList<>();
            List<String> subset = new ArrayList<String>();
            Map<String, MySynset> searchResult = new HashMap<>();
            Map<String, Integer> searchSize = new HashMap<>();
            String kalimat=req.getParameter("kalimat");
            String[] kata2 = kalimat.split(" ");
            for(int i=0; i<kata2.length; i++){
                String frasa = kata2[i];
                for(int j=i+1; j<kata2.length; j++){
                    frasa = frasa+" "+kata2[j];
                    subset.add(frasa);
                    //List<MySynset> synsets = IndexReader.readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", frasa);
                }
            }
            Collections.sort(subset, new StringComparator());
            boolean found = false;
            for(String sub : subset){
                System.out.println(sub);
                List<MySynset> synsets = IndexReader.readIndex("D:\\S2\\Thesis\\txtdir\\cobatxt\\index", sub);
                if(!synsets.isEmpty()){
                    hasilFrasa = synsets.get(0);
                    found = true;
                    break;
                    //searchResult.put(sub, synsets.get(0));
                }
                //else{
                  //  searchResult.put(sub, null);
                //}
                
            }
            
            String inputText = kalimat;
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext(); 
            Configuration jltConfiguration = Configuration.getInstance();
            jltConfiguration.setConfigurationFile(new File(ctx.getRealPath("/") + "WEB-INF/config/jlt.properties"));
            PropertyConfigurator.configure(ctx.getRealPath("/") + "WEB-INF\\config\\log4j.properties");

            BabelNetConfiguration bnconf = BabelNetConfiguration.getInstance();
            bnconf.setConfigurationFile(new File(ctx.getRealPath("/") + "/WEB-INF/config/babelnet.properties"));
            bnconf.setBasePath(ctx.getRealPath("/") + "/WEB-INF/");
            
            BabelfyConfiguration fyconf = BabelfyConfiguration.getInstance();
            fyconf.setConfigurationFile(new File(ctx.getRealPath("/") + "/WEB-INF/config/babelfy.properties"));

            //System.setProperty("http.proxyHost", "proxy.its.ac.id");
            //System.setProperty("http.proxyPort", "8080");
            //Authenticator.setDefault(new DummyAuthenticator());
            Babelfy bfy = new Babelfy();
            BabelNet bn = BabelNet.getInstance();
            //String inputText = "BabelNet is both a multilingual encyclopedic dictionary and a semantic network";
            List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN);
            int flag = 1;
            for(SemanticAnnotation annotation : bfyAnnotations){
                Result r = new Result();
                if(found == true){
                if(hasilFrasa.getTitle().toLowerCase().contains(inputText.substring(annotation.getCharOffsetFragment().getStart(),
		annotation.getCharOffsetFragment().getEnd() + 1).toLowerCase()) && flag == 1){
                    r.setKata(hasilFrasa.getTitle());
                    r.setSense(hasilFrasa.getGloss());
                    flag = 0;
                }
                else if(!hasilFrasa.getTitle().toLowerCase().contains(inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1).toLowerCase())){
                    r.setKata(inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1));
                    BabelSynset synset = bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
                    r.setSense(synset.getMainGloss(Language.EN).toString());
                }
                
                }
                else
                {
                    r.setKata(inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1));
                    BabelSynset synset = bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
                    r.setSense(synset.getMainGloss(Language.EN).toString());
                }
                if(r.getKata() != null)hasil.add(r);
            }
            /*for(int i=0; i<hasil.size(); i++){
                Result rs = null;
                if(hasil.get(i).equals(rs)){
                    hasil.remove(i);
                    i=i-1;
                }
            }*/
            for(int i=0; i<hasil.size(); i++){
                System.out.println(hasil.get(i).getKata());
            }
            for(int i=0; i<hasil.size()-1; i++){
                for(int j=i+1; j<hasil.size(); j++){
                    if(hasil.get(i).getKata().contains(hasil.get(j).getKata()) || hasil.get(j).getKata().contains(hasil.get(i).getKata())){
                        if(hasil.get(i).getKata().length()<hasil.get(j).getKata().length()){
                            hasil.remove(i);
                            i = i-1;
                            break;
                        }
                        else
                        {
                            hasil.remove(j);
                            i = i-1;
                            break;
                        }
                    }
                }
            }
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(hasil, new TypeToken<List<Result>>() {}.getType());
            
            JsonArray jsonArray = element.getAsJsonArray();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().print(jsonArray);
      
    }

}
