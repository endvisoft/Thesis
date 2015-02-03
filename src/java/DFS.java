
import edu.mit.jwi.item.ISynset;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.jlt.wordnet.WordNet;
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
public class DFS {
    public static void main(String[] args) throws Exception {
        WordNet wn = WordNet.getInstance();
        List<ISynset> listSynset1 = wn.getSynsets("payment");
        List<ISynset> listSynset2 = wn.getSynsets("vendor");
        List<List<ISynset>> list = new ArrayList<>(); 
        for(ISynset wnSynset1 : listSynset1){
            for(ISynset wnSynset2 : listSynset2){
            ISynset source = wnSynset1;
            ISynset target = wnSynset2;
            //System.out.println(wnSynset1);
            //System.out.println(wnSynset2);
            List<ISynset> path = wn.dfs(source, target);
            //System.out.println("path = "+path.toString());
            list.add(path);
        }
        }
        for (List<ISynset> list1 : list) {
            System.out.println("start : ");
            for (ISynset list11 : list1) {
                System.out.println(list11.toString());
            }
        }
    }
}
