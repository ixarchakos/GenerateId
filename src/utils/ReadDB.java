/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import generator.InputDataModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author zannis
 */
public class ReadDB {
    
    private String path, table;
    
    public ReadDB(String path, String table){
        this.path = path;
        this.table = table;
    }
    
    public ArrayList<InputDataModel> readTargetDatabase(String[] targetColumns) throws SQLException, IOException, ClassNotFoundException{
        ArrayList<InputDataModel> inputData = new ArrayList<>();
        ArrayList<String> configurationProperties = getExportDatabaseConfig();
        Connection connection = null;
        try{
            connection = getConnectionToDatabase(configurationProperties.get(0), configurationProperties.get(1)+configurationProperties.get(4), configurationProperties.get(2), configurationProperties.get(3));
            Statement statement = connection.createStatement();
            
            String columnsToQuery = "";
            for(String column: targetColumns){
                columnsToQuery += column + ",";
            }
            
            ResultSet tableRows = statement.executeQuery("SELECT "+columnsToQuery.substring(0, columnsToQuery.length()-1)+
                    " FROM " + table + ";");
            
            ResultSetMetaData rsmd = tableRows.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while(tableRows.next()){
                InputDataModel idm = new InputDataModel();
                for(int i=1;i<=columnsNumber;i++){
                    idm.addValue(String.valueOf(tableRows.getObject(i)));
                }
                inputData.add(idm);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(-1);
        } finally {
            if (connection != null)
                connection.close();
        }
        return inputData;
    }
    
    private Connection getConnectionToDatabase(String driver, String uri, String login, String pass) throws ClassNotFoundException, SQLException{
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(uri, login, pass);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return connection;
    }
    
    private ArrayList<String> getExportDatabaseConfig() throws FileNotFoundException, IOException{
        ArrayList<String> config = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(this.path));
        try{
            String line;
            while((line = in.readLine()) != null) {
                config.add(line.split("=")[1].trim());
            }
        } catch(IOException ex){
            System.err.print(ex);
            System.exit(-1);
        } finally {
            in.close();
        }
        return config;
    }
    
}
