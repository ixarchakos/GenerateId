/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.ArrayList;

/**
 *
 * @author ioannisxar
 */
public class InputDataModel {
    
    private int id;
    private ArrayList<Object> values;
    public InputDataModel(){
        this.id = -1;
        this.values = new ArrayList<>();
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void addValue(Object val){
        this.values.add(val);
    }
    
    public ArrayList<Object> getValue(){
        return this.values;
    }
    
    public int size(){
        return this.values.size();
    }
    
}
