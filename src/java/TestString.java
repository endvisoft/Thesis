
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class TestString {
    
    public static void main(String[] args) throws Exception {
        String coba = "make sales order entry for inventory stock";
        List<String> subset = new ArrayList<String>();
        String[] kata2 = coba.split(" ");
        for(int i=0; i<kata2.length; i++){
                String frasa = kata2[i];
                for(int j=i+1; j<kata2.length; j++){
                    frasa = frasa+" "+kata2[j];
                    subset.add(frasa);
                }
        }
        Collections.sort(subset, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int len1 = o1.length();
            int len2 = o2.length();
            
            if(len1 > len2){
                return -1;
            }
            else if(len1 < len2){
                return 1;
            }
            
            return 0;
            }
        });
        //for(String sub : subset){
            //System.out.println(subset.get(12));
        //}
        int flag = 1;
        for(int i=0; i< kata2.length; i++){
            if(subset.get(12).contains(kata2[i]) && flag == 1){
                System.out.println(subset.get(12));
                flag = 0;
            }
            else if(!subset.get(12).contains(kata2[i])){
                System.out.println(kata2[i]);
            }
        }
    }
}

