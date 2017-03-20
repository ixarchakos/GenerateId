/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ioannisxar
 */
public class ReadPropertiesFile {

    private final String path;
    
    private String command, sourceInputPath, sourceColumns, targetInputPath, targetColumns, functionPerColumn, outputFile;
    
    public ReadPropertiesFile(String path){
        this.path = path;
    }
    
    public void getProperties() throws FileNotFoundException, IOException {
        try(BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = in.readLine()) != null) {
                String[] splitLine = line.split("=");
                switch (splitLine[0]) {
                    case "command":
                        this.command = splitLine[1];
                        break;
                    case "sourceInputPath":
                        this.sourceInputPath = splitLine[1];
                        break;
                    case "sourceColumns":
                        this.sourceColumns = splitLine[1];
                        break;
                    case "targetInputPath":
                        this.targetInputPath = splitLine[1];
                        break;
                    case "targetColumns":
                        this.targetColumns = splitLine[1]; 
                        break;
                    case "functionPerColumn":
                        this.functionPerColumn = splitLine[1];
                        break;
                    case "outputFile":
                        this.outputFile = splitLine[1];
                        break;
                    default:
                        System.err.println("Wrong command!");
                        System.exit(-1);
                        break;
                }
            }
        } catch(IOException ex){
            System.err.print(ex);
            System.exit(-1);
        }
    }
    
    public String getCommand(){
        return command;
    }
    
    public String getSourceInputPath(){
        return sourceInputPath;
    }
    
    public String getSourceColumns(){
        return sourceColumns;
    }
    
    public String getTargetInputPath(){
        return targetInputPath;
    }
    
    public String getTargetColumns(){
        return targetColumns;
    }
    
    public String getFunctionPerColumn(){
        return functionPerColumn;
    }
    
    public String getOutputFile(){
        return outputFile;
    }
}
