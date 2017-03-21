/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import functions.Functions;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ExportCsv;
import utils.ReadCsv;
import utils.ReadDB;
import utils.ReadPropertiesFile;

/**
 *
 * @author ioannisxar
 */
public class GenerateId {
    
    private final ReadPropertiesFile rpf;
    
    public GenerateId(String path) throws IOException{
        rpf = new ReadPropertiesFile(path);
        rpf.getProperties();
    }
    
    public void performAction() throws SQLException, IOException, ClassNotFoundException{
        
        ColumnMatcher cm = new ColumnMatcher(rpf.getSourceColumns(), rpf.getTargetColumns(), rpf.getFunctionPerColumn());
        ArrayList<ColumnMatcherModel> cmmList = cm.getMatching();
        switch (rpf.getCommandSource()) {
            case "csv":
                getFromCsv(rpf, cmmList);
                break;
            case "db":
                getFromDB(rpf, cmmList);
                break;
            default:
                System.err.println("Wrong command!");
                System.exit(-1);
        }
    }

    private void getFromCsv(ReadPropertiesFile rpf, ArrayList<ColumnMatcherModel> cmmList) throws SQLException, IOException, ClassNotFoundException {

        String[] targetCols = rpf.getTargetColumns().substring(1, rpf.getTargetColumns().length()-1).split(",(?![^(]*\\))");
        String generatedColumn = targetCols[targetCols.length-1].trim();
        
        //target choice
        ArrayList<InputDataModel> targetValues = null;
        switch (rpf.getCommandTarget()) {
            case "csv":
                ReadCsv rcTarget = new ReadCsv(rpf.getTargetInputPath());
                targetValues = rcTarget.readTargetCsv(cmmList, generatedColumn);
                break;
            case "db":
                ReadDB rdb = new ReadDB(rpf.getTargetInputPath().split(",")[0], rpf.getTargetInputPath().split(",")[1]);
                targetValues = rdb.readTargetDatabase(targetCols);
                break;
            default:
                System.err.println("Wrong target command!");
                System.exit(-1);
        }

        ReadCsv rcSource = new ReadCsv(rpf.getSourceInputPath());
        ArrayList<InputDataModel> sourceValues = rcSource.readInputCsv(cmmList);
        targetValues = generateId(sourceValues, targetValues, cmmList);
        ExportCsv export = new ExportCsv(rpf.getOutputFile(), targetValues, targetCols);
        export.performAction();
    }

    private void getFromDB(ReadPropertiesFile rpf, ArrayList<ColumnMatcherModel> cmmList) {
        System.out.println("db");
    }
    
    private ArrayList<InputDataModel> generateId(ArrayList<InputDataModel> sourceValues, 
            ArrayList<InputDataModel> targetValues, ArrayList<ColumnMatcherModel> cmmList){
        int max_value = InputDataModel.getMaxValue(targetValues);
        
        ArrayList<InputDataModel> transformSourceValues = transformSourceValues(sourceValues, cmmList);
        
        // find if exists, if not assign a new id
        for(InputDataModel sourceInput: transformSourceValues){
            if(!sourceInput.exists(targetValues)){
                InputDataModel idm = new InputDataModel();
                for(String value: sourceInput.getValue()){
                    idm.addValue(value);
                }
                max_value = max_value + 1;
                idm.addValue(String.valueOf(max_value));
                targetValues.add(idm);
            }
        }
        return targetValues;   
    }
    
    private ArrayList<InputDataModel> transformSourceValues(ArrayList<InputDataModel> sourceValues, ArrayList<ColumnMatcherModel> cmmList){
        ArrayList<InputDataModel> sourceValuesTransformed = new ArrayList<>();
        
        for(InputDataModel idm: sourceValues){
            InputDataModel newIdm = new InputDataModel();
            for(int i=0; i<idm.getValue().size();i++){
                if(!cmmList.get(i).getSourceColumn().trim().equals(cmmList.get(i).getFunction())){
                    newIdm.addValue(getFunctionValue(idm.getValue().get(i), cmmList.get(i).getFunction(), cmmList.get(i).getFunctionProperties()));
                } else {
                    newIdm.addValue(idm.getValue().get(i));
                }
            }
            sourceValuesTransformed.add(newIdm);
        }
        return sourceValuesTransformed;        
    }

    private String getFunctionValue(String value, String function, List<String> functionProperties) {
        try{
            if(function.equalsIgnoreCase("split")){
                value = Functions.split(value, functionProperties.get(1), Integer.valueOf(functionProperties.get(2)));
            }
        } catch (NumberFormatException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return value;
    }
    
}
