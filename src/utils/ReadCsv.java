/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import au.com.bytecode.opencsv.CSVReader;
import generator.ColumnMatcherModel;
import generator.InputDataModel;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author ioannisxar
 */
public class ReadCsv {
    private String path;
    
    public ReadCsv(String path){
        this.path = path;
    }
    
    public void readInputCsv(){
        try{
           String[] nextLine;        
           // nextLine[] is an array of values from the line
           CSVReader reader = new CSVReader(new FileReader(this.path));
           System.out.println(this.path);
           //read only first line
           nextLine = reader.readNext();
           for (int i=0; i<nextLine.length; i++){             
               String columnName = nextLine[i].trim();
               System.out.println(columnName);
           }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<InputDataModel> readTargetCsv(ArrayList<ColumnMatcherModel> cmmList){
        ArrayList<InputDataModel> idmList = new ArrayList<>();
        try{
            String[] nextLine;   
            
            CSVReader reader = new CSVReader(new FileReader(this.path));

            nextLine = reader.readNext();
            
            ArrayList<Integer> colNums = new ArrayList<>();
            
            for(ColumnMatcherModel cmm : cmmList){
                System.out.println(cmm.getTargetColumn());
                for(int i=0;i<nextLine.length;i++){
                    if(nextLine[i].equals(cmm.getTargetColumn()) /*|| export column.equals... */){
                        colNums.add(i);
                        break;
                    }
                }
            }
            
            while((nextLine = reader.readNext()) != null){
                InputDataModel idm = new InputDataModel();
                for (int i=0; i<colNums.size(); i++){             
                    String value = nextLine[colNums.get(i)].trim();
                    idm.addValue(value);
                }
                idmList.add(idm);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return idmList;
    }
}
