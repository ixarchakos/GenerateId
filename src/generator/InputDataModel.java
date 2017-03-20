/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ioannisxar
 */
public class InputDataModel {
    
    private int id;
    private ArrayList<String> values;
    public InputDataModel(){
        this.id = -1;
        this.values = new ArrayList<>();
    }
    
    public void addValue(String val){
        this.values.add(val);
    }
    
    public ArrayList<String> getValue(){
        return this.values;
    }
    
    public int size(){
        return this.values.size();
    }
    
    public int getId(){
        if (size()>0){
            id = Integer.valueOf(this.values.get(size()-1));
        }
        return id;
    }
    
    public static int getMaxValue(ArrayList<InputDataModel> targetValues){
        int max = 0;
        ArrayList<Integer> l = new ArrayList<>();
        for(int i=0;i<targetValues.size();i++){      
            l.add(targetValues.get(i).getId());
        }
        if (l.size()>0){
            max = Collections.max(l);
        }
        return max;
    }
    
}
