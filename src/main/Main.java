/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import generator.GenerateId;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author ioannisxar
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        //GenerateId generator = new GenerateId("C:\\Users\\zannis\\Dropbox\\mapping.properties");
        GenerateId generator = new GenerateId("/home/ioannisxar/Dropbox/mappingu.properties");
        generator.performAction();
    }
    
}
