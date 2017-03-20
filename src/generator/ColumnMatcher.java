/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ioannisxar
 */
public class ColumnMatcher {
    
    private String sourceColumn, targetColumn, function;
    
    public ColumnMatcher(String sourceColumn, String targetColumn, String function){
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
        this.function = function;
    }
    
    public ArrayList<ColumnMatcherModel> getMatching(){
        ArrayList<ColumnMatcherModel> cmmList = new ArrayList<>();
        if(sourceColumn.charAt(0) == '[' && sourceColumn.charAt(sourceColumn.length()-1) == ']' 
                && targetColumn.charAt(0) == '[' && targetColumn.charAt(targetColumn.length()-1) == ']'
                && function.charAt(0) == '[' && function.charAt(function.length()-1) == ']'){
            
            //this type of split ignores commas inside the paranthesis
            String[] sourceCols = sourceColumn.substring(1, sourceColumn.length()-1).split(",(?![^(]*\\))");
            String[] targetCols = targetColumn.substring(1, targetColumn.length()-1).split(",(?![^(]*\\))");
            String[] functionCols = function.substring(1, function.length()-1).split(",(?![^(]*\\))");
            checkListParamCombatibility(sourceCols, targetCols, functionCols);
            for(int i=0; i< sourceCols.length; i++){
                ColumnMatcherModel cmm;
                if(functionCols[i].contains("(") && functionCols[i].contains(")")){
                    String functionName = functionCols[i].split("\\(")[0];
                    String[] functionProperties = functionCols[i].split("\\(")[1].split("\\)")[0].split(",");
                    cmm = new ColumnMatcherModel(sourceCols[i].trim(), targetCols[i].trim(), functionName.trim(), Arrays.asList(functionProperties));
                } else {
                    cmm = new ColumnMatcherModel(sourceCols[i].trim(), targetCols[i].trim(), functionCols[i].trim(), new ArrayList());
                }
                cmmList.add(cmm);
            }
        } else {
            System.err.println("Wrong list form!");
            System.exit(-1);
        }
        return cmmList;
    }
    
    private void checkListParamCombatibility(String[] sourceCols, String[] targetCols, String[] functionCols){
        if (sourceCols.length != targetCols.length-1){
            System.err.println("Missmatching input and output list size");
            System.exit(-1);
        }

        if (sourceCols.length != functionCols.length){
            System.err.println("Missmatching input and output list size");
            System.exit(-1);
        }
    
    }
}
