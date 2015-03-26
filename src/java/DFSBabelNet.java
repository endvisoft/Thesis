
import edu.mit.jwi.item.POS;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.util.Language;
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
public class DFSBabelNet {
    public static void DFS(){
        
    }
    public static void main(String[] args) throws Exception {
        BabelNet bn = BabelNet.getInstance();
        List<BabelSynset> synsets = bn.getSynsets(Language.EN, "play");
        for(int i=0; i<synsets.size(); i++){
            System.out.println(synsets.get(i).getGlosses());
        }
    }
}
