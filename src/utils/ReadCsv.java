/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;

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
}
