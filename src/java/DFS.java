import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.commons.lang.time.StopWatch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */
public class DFS {
    /*public static List<ISynset> dfs(IDictionary dict, Collection<ISynset> srcSenses, Collection<ISynset> targetSenses, int depth)
    {
        Iterator localIterator2;
        for (Iterator localIterator1 = srcSenses.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
        {
            ISynset srcSense = (ISynset)localIterator1.next();
            localIterator2 = targetSenses.iterator(); 
           //continue;
            ISynset targetSense = (ISynset)localIterator2.next();
            List<ISynset> path = dfs(dict, srcSense, targetSense, depth);
        if (!path.isEmpty()) {
            return path;
        }
    }
    return new ArrayList();
  }*/
    public static List<List<ISynset>> dfs(IDictionary dict, ISynset srcSense, ISynset targetSense, int maxDepth)
    {
      
        return dls(dict, srcSense, targetSense, maxDepth);
    }
    
    public static List<List<ISynset>> dls(IDictionary dict, ISynset srcSense, ISynset targetSense, int maxDepth)
    {
        List<List<ISynset>> allPath = new ArrayList<List<ISynset>>();
        for(int iter = 0; iter <= maxDepth; iter++)
        {
        Stack<List<ISynset>> st = new Stack<>();
        List<ISynset> path = new ArrayList<>();
        int depth = 0;
        int max = iter;
        List<ISynset> root = new ArrayList<>();
        root.add(srcSense);
        st.push(root);
        while(!st.isEmpty())
        {
            List<ISynset> element = st.pop();

            if(element.get(element.size()-1).equals(targetSense))
            {
                
                int index = allPath.size();
                int flag=0;
                List<ISynset> dummy = new ArrayList<>();
                dummy.addAll(element);
                for(int i=0; i<index; i++)
                {
                    if(allPath.get(i).equals(dummy))
                        flag=1;
                }
                if(flag==0)
                {
                    allPath.add(dummy);
                }
               }
            
                
            
            if(depth < max)
            {
                depth++;
                List<ISynsetID> related = new ArrayList<>();
                related.addAll(element.get(element.size()-1).getRelatedSynsets());
                List<IWord> word = element.get(element.size()-1).getWords();
                for (IWord word1 : word) {
                List<IWordID> derivational = word1.getRelatedWords(Pointer.DERIVATIONALLY_RELATED);
                for(IWordID wid:derivational)
                {
                    ISynsetID sid = wid.getSynsetID();
                    related.add(sid);
                }
                }
                for(int i = 0; i < related.size(); i++)
                {
                    ISynset dummy = dict.getSynset(related.get(i));
                    List<ISynset> listDummy = new ArrayList<>();
                    if(!element.contains(dummy))
                    {
                        listDummy.addAll(element);
                        listDummy.add(dummy);
                        st.push(listDummy);
                    }
                }
                
            }
            else
            {
                if(!st.isEmpty())
                depth = st.peek().size()-1;
            }
        }
        }
         //System.out.println(allPath.size()+" : "+allPath.get(allPath.size()-1));
            
        return allPath;
    }
    
    public static void main(String[] args) throws Exception {
        URL url = new URL("file",null,"E:\\WordNet-3.0\\dict");
        IDictionary dict = new Dictionary (url);
        dict.open();
        List<List<Double>> result = new ArrayList<>();
        //WordNet wn = WordNet.getInstance();relationship
        IIndexWord idxWord1 = dict.getIndexWord ("calculate", POS.VERB);
        IIndexWord idxWord2 = dict.getIndexWord ("base", POS.NOUN);
        int maxDepth = 4;
        Double resultWord1 [] = new Double[idxWord1.getWordIDs().size()];
        Double resultWord2 [] = new Double[idxWord2.getWordIDs().size()];
        for(int i=0; i<idxWord1.getWordIDs().size(); i++)
            resultWord1[i]=0.0;
        for(int i=0; i<idxWord2.getWordIDs().size(); i++)  
            resultWord2[i]=0.0;
        
        for(int i=0; i<idxWord1.getWordIDs().size(); i++){
            for(int j=0; j<idxWord2.getWordIDs().size(); j++){
            IWord word1 = dict.getWord(idxWord1.getWordIDs().get(i));
            IWord word2 = dict.getWord(idxWord2.getWordIDs().get(j));
            ISynset source = word1.getSynset();
            ISynset target = word2.getSynset();
            //System.out.println(source+" ke "+target);
            List<List<ISynset>> path = new ArrayList<>();
            path = dls(dict, source, target, maxDepth);
            //System.out.println("Selesai");
            double dummyWord1 = 0;
            if(!path.isEmpty()){
                for(int k=0; k<path.size(); k++)
                {
                    //System.out.println(path.get(k).size());
                    int length = path.get(k).size()-2;
                    //System.out.println(length);
                    dummyWord1 = dummyWord1 + 1.0/Math.pow(2.7, (double)length);
                    
                    for(int l=0; l<path.get(k).size(); l++)
                    {
                        System.out.println(path.get(k).get(l));
                    }
                  //  System.out.println("dalam "+dummyWord1);
                }
                //System.out.println("luar "+dummyWord1);
            }
            else
            {
                dummyWord1 = 1/Math.pow(2.7,(100+1));
            }
            resultWord1[i] = resultWord1[i]+dummyWord1;
            resultWord2[j] = resultWord2[j]+dummyWord1;
            }
           
        }
        double max1 = 0.0;
        int index1=0;
        for(int i=0; i<resultWord1.length; i++)
        {
            if(resultWord1[i]>max1)
            {
                max1=resultWord1[i];
                index1=i;
            }
        }
        double max2 = 0.0;
        int index2=0;
        for(int i=0; i<resultWord2.length; i++)
        {
            if(resultWord2[i]>max2)
            {
                max2=resultWord2[i];
                index2=i;
            }
        }
        System.out.println("Sense 1 Terbaik adalah : "+dict.getWord(idxWord1.getWordIDs().get(index1)).getSynset()+" dan gloss "+dict.getWord(idxWord1.getWordIDs().get(index1)).getSynset().getGloss()+" dengan nilai "+resultWord1[index1]);
        System.out.println("Sense 2 Terbaik adalah : "+dict.getWord(idxWord2.getWordIDs().get(index2)).getSynset()+" dan gloss "+dict.getWord(idxWord2.getWordIDs().get(index2)).getSynset().getGloss()+" dengan nilai "+resultWord2[index2]);
    }
        
}
