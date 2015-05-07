
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetConfiguration;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.Configuration;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletConfig;
import org.apache.log4j.PropertyConfigurator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class sentenceSimilarity {
    
    public static void main(String[] args) throws Exception {
        Double a;
        sentenceSimilarity sen = new sentenceSimilarity();
        //a = sen.calculateSimilarity("sales order entry", "vendor payment entry");
        System.out.println("Similarity Score ");
    }
    @SuppressWarnings("null")
    public Double calculateSimilarity(String a, String b, ServletContext ctx) throws IOException, URISyntaxException
    {
        /*Double d = 0.0;
        Double similarityScore = 0.0;
        Double tempSynset, tempWn, tempBabel;
        Double maxWn=0.0, maxBabel=0.0;
        System.setProperty("http.proxyHost", "10.100.1.15");
        System.setProperty("http.proxyPort", "8080");
        Authenticator.setDefault(new sentenceSimilarity.DummyAuthenticator());
        Configuration jltConfiguration = Configuration.getInstance();
        jltConfiguration.setConfigurationFile(new File(ctx.getRealPath("/") + "WEB-INF/config/jlt.properties"));
        PropertyConfigurator.configure(ctx.getRealPath("/") + "WEB-INF/config/log4j.properties");

	BabelNetConfiguration bnconf = BabelNetConfiguration.getInstance();
        bnconf.setConfigurationFile(new File(ctx.getRealPath("/") + "/WEB-INF/config/babelnet.properties"));
        bnconf.setBasePath(ctx.getRealPath("/") + "/WEB-INF/");
        //bn =  BabelNet.getInstance();
	Babelfy bfy = Babelfy.getInstance(Babelfy.AccessType.ONLINE);
	Annotation annotations1 = bfy.babelfy("IijpZAL3A4D3Hh-SeHR1lUih8U7r068k", a,
		Babelfy.Matching.EXACT, Language.EN);
	Annotation annotations2 = bfy.babelfy("IijpZAL3A4D3Hh-SeHR1lUih8U7r068k", b, 
                Babelfy.Matching.EXACT, Language.EN);
        int comparator1, comparator2;
        List<BabelSynsetAnchor> comAnnotation1 = new ArrayList<BabelSynsetAnchor>();
        List<BabelSynsetAnchor> comAnnotation2 = new ArrayList<BabelSynsetAnchor>();        
        if(annotations1.getAnnotations().size()<annotations2.getAnnotations().size())
        {
            comparator1 = annotations1.getAnnotations().size();
            comparator2 = annotations2.getAnnotations().size();
            //Collections.copy(comAnnotation1, annotations1.getAnnotations());
            //Collections.copy(comAnnotation2, annotations2.getAnnotations());
            comAnnotation1.addAll(annotations1.getAnnotations());
            comAnnotation2.addAll(annotations2.getAnnotations());
        }
        else
        {
            comparator1 = annotations2.getAnnotations().size();
            comparator2 = annotations1.getAnnotations().size();
            //Collections.copy(comAnnotation1, annotations2.getAnnotations());
            //Collections.copy(comAnnotation2, annotations1.getAnnotations());
            comAnnotation1.addAll(annotations2.getAnnotations());
            comAnnotation2.addAll(annotations1.getAnnotations());
        }
        for(int i=0; i<comparator1; i++)
        {
            tempSynset = 0.0;
            tempWn = 0.0;
            tempBabel = 0.0;
            maxWn = 0.0;
            maxBabel = 0.0;
            for(int j=0; j<comparator2; j++)
            {
                BabelSynset synset1 = comAnnotation1.get(i).getBabelSynset();
                BabelSynset synset2 = comAnnotation2.get(j).getBabelSynset();
                tempSynset = getSynset(synset1, synset2);
                if(tempSynset.equals(1.0))
                {
                    break;
                }
                tempWn = getWnRelation(synset1, synset2);
                //System.out.println("Nilai temWn vs maxWn : "+tempWn+" vs "+maxWn+"antara :"+synset1.toString()+" dan "+synset2.toString());
                if(tempWn>maxWn)
                {
                    maxWn = tempWn;
                }
                tempBabel = getBabelRelation(synset1, synset2);
                //System.out.println("Nilai temBabel vs maxBabel : "+tempBabel+" vs "+maxBabel+"antara :"+synset1.toString()+" dan "+synset2.toString());
                if(tempBabel>maxBabel)
                {
                    maxBabel = tempBabel;
                }
            }
            if(tempSynset != 0.0){
                similarityScore = similarityScore + tempSynset;
            }
            else if(maxWn != 0.0){
                //System.out.println("Masukk WN" + tempWn);
                similarityScore = similarityScore + maxWn;
            }
            else if(maxBabel != 0.0){
                System.out.println("Masukk Babel" + maxBabel);
                similarityScore = similarityScore + maxBabel;
            }
            else{
                similarityScore = similarityScore + 0.0;
            }
            //System.out.println(similarityScore);
        }
        similarityScore = similarityScore/comparator2;
        return similarityScore; */
        return 0.0;
    }
    private Double getSynset(BabelSynset a, BabelSynset b)
    {
        /*Double sim = 0.0;
        List<String> wordnetOffsets1 = a.getWordNetOffsets();
        List<String> wordnetOffsets2 = b.getWordNetOffsets();
        WordNet wn = WordNet.getInstance();
        for(int i=0; i<wordnetOffsets1.size(); i++){
            for(int j=0; j<wordnetOffsets2.size(); j++){
            //if(wordnetOffsets1.isEmpty() || wordnetOffsets2.isEmpty())
                //return 0.0;
            ISynset wnSynset1 = wn.getSynsetFromOffset(wordnetOffsets1.get(i));
            ISynset wnSynset2 = wn.getSynsetFromOffset(wordnetOffsets2.get(j));
            //List<IWord> syns1 = wnSynset1.getWords();
            //List<IWord> syns2 = wnSynset2.getWords();
            //System.out.println(wnSynset1.getGloss()+" "+wnSynset2.getGloss());
            List<IWord> lemma1 = wnSynset1.getWords();
            List<IWord> lemma2 = wnSynset2.getWords();
           // System.out.println(wnSynset1.toString());
            //System.out.println(wnSynset2.toString());
            //for(int k=0; k<lemma1.size(); k++) System.out.println(lemma1.get(k).getLemma());
            //for(int k=0; k<lemma2.size(); k++) System.out.println(lemma2.get(k).getLemma());
            if(wnSynset1.getID()==wnSynset2.getID())
            {    
                sim = 1.0;
            }    
            else 
            {
                sim = 0.0;
            }
        }
        }
        return sim;*/
        return 0.0;
}
    
    private Double getWnRelation(BabelSynset a, BabelSynset b)
    {
        /*Double hyper=0.0, hypo=0.0;
        Double similarityScore = 0.0;
        List<String> wordnetOffsets1 = a.getWordNetOffsets();
        List<String> wordnetOffsets2 = b.getWordNetOffsets();
        WordNet wn = WordNet.getInstance();
        for(int i=0; i<wordnetOffsets1.size(); i++){
            for(int j=0; j<wordnetOffsets2.size(); j++){
            //if(wordnetOffsets1.isEmpty() || wordnetOffsets2.isEmpty())
                //return 0.0;    
            ISynset wnSynset1 = wn.getSynsetFromOffset(wordnetOffsets1.get(i));
            ISynset wnSynset2 = wn.getSynsetFromOffset(wordnetOffsets2.get(j));
            List<ISynset> hypernyme1 = wn.getHypernyms(wnSynset1);
            List<ISynset> hypernyme2 = wn.getHypernyms(wnSynset2);
            List<ISynset> hyponyme1 = wn.getHyponyms(wnSynset1);
            List<ISynset> hyponyme2 = wn.getHyponyms(wnSynset2);
            for(int k=0; k<hypernyme2.size(); k++)
            {
                if(wnSynset1.getID().toString().equals(hypernyme2.get(k).getID().toString()))
                    System.out.println("Hyper1");
                    //System.out.println(wnSynset1.toString()+" com "+hypernyme2.get(k).toString());
                    hyper = 0.7;
            }
            for(int l=0; l<hyponyme2.size(); l++)
            {
                //System.out.println(wnSynset1.toString()+" compare "+hyponyme2.get(l).toString());
                if(wnSynset1.getID().toString().equals(hyponyme2.get(l).getID().toString()))
                    hypo = 0.5;
            }
            for(int k=0; k<hypernyme1.size(); k++)
            {
                //System.out.println(wnSynset2.toString()+" com "+hypernyme1.toString());
                if(wnSynset2.getID().toString().equals(hypernyme1.get(k).getID().toString()))
                    //System.out.println("Hyper2");
                    System.out.println(wnSynset2.toString()+" com "+wnSynset1.toString());
                    hyper = 0.7;
            }
            for(int l=0; l<hyponyme1.size(); l++)
            {
                //System.out.println(wnSynset2.toString()+" compare "+hyponyme1.get(l).toString());
                if(wnSynset2.getID().toString().equals(hyponyme1.get(l).getID().toString())){
                    System.out.println("Masukkk");
                    hypo = 0.5;
                }
            }
            }
        }
        if(hyper>hypo) similarityScore = hyper;
        else similarityScore = hypo;
        //System.out.println("sim = "+hypo+" "+similarityScore);
        return similarityScore;*/
        return 0.0;
    }
    
    private Double getBabelRelation(BabelSynset a, BabelSynset b) throws IOException
    {
        /*BabelNet bn = BabelNet.getInstance();
        List<String> successor1 = bn.getSuccessors(a.getId());
        List<String> successor2 = bn.getSuccessors(b.getId());
        Double sim = 0.0, maxSim = 0.0;
        for(int i=0; i<successor1.size(); i++)
        {
            sim = 0.0;
            String [] single = successor1.get(i).split("_");
            //System.out.println(b.toString()+" com "+successor1.get(i));
            for(int j=0; j<single.length; j++)
            {
                if(b.getId().equals(single[2]))
                {
                    sim = Double.parseDouble(single[3]);
                    if(sim>maxSim)maxSim = sim;
                }
            }
            if(sim>maxSim) maxSim = sim;
        }
        
        for(int i=0; i<successor2.size(); i++)
        {
            sim = 0.0;
            String [] single = successor2.get(i).split("_");
           
            for(int j=0; j<single.length; j++)
            {
                if(a.getId().equals(single[2]))
                {
                    //System.out.println(a.getId()+" com "+successor2.get(i));
                    sim = Double.parseDouble(single[3]);
                    //System.out.println(a.getId()+" com "+successor2.get(i)+" sim :"+sim);
                    if(sim>maxSim)maxSim = sim;
                }
            }
            //if(sim>maxSim)maxSim = sim;
                
            
            
        }
        //System.out.println("Nilai max : "+maxSim);
        return maxSim;*/
        return 0.0;
    }
       
    
    
    
    private static class DummyAuthenticator extends Authenticator {
              @Override
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               "asrama@its.ac.id", "asramaits".toCharArray()
	               );
	      }
	   }
}
