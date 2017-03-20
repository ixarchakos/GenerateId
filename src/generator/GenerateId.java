/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.IOException;
import java.util.ArrayList;
import utils.ReadCsv;
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
    
    public void performAction(){
        
        ColumnMatcher cm = new ColumnMatcher(rpf.getSourceColumns(), rpf.getTargetColumns(), rpf.getFunctionPerColumn());
        ArrayList<ColumnMatcherModel> cmmList = cm.getMatching();
        switch (rpf.getCommand()) {
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

    private void getFromCsv(ReadPropertiesFile rpf, ArrayList<ColumnMatcherModel> cmmList) {

        String[] targetCols = rpf.getTargetColumns().substring(1, rpf.getTargetColumns().length()-1).split(",(?![^(]*\\))");
        String generatedColumn = targetCols[targetCols.length-1].trim();
        
        //csv target
        ReadCsv rcTarget = new ReadCsv(rpf.getTargetInputPath());
        ArrayList<InputDataModel> targetValues = rcTarget.readTargetCsv(cmmList, generatedColumn);

        int MAX_VALUE = InputDataModel.getMaxValue(targetValues);
        
        ReadCsv rcSource = new ReadCsv(rpf.getSourceInputPath());
        ArrayList<InputDataModel> sourceValues = rcSource.readInputCsv(cmmList);

    }

    private void getFromDB(ReadPropertiesFile rpf, ArrayList<ColumnMatcherModel> cmmList) {
        System.out.println("db");
    }
    
}
