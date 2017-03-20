/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import generator.GenerateId;
import java.io.IOException;

/**
 *
 * @author ioannisxar
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        GenerateId generator = new GenerateId("C:\\Users\\zannis\\Dropbox\\mapping.properties");
        //GenerateId generator = new GenerateId("/home/ioannisxar/a/mapping.properties");
        generator.performAction();
    }
    
}
