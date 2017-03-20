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
//        System.out.println("csv");
//        for(int i=0;i<cmmList.size();i++){
//            System.out.println(cmmList.get(i).getSourceColumn());
//            System.out.println(cmmList.get(i).getTargetColumn());
//            System.out.println(cmmList.get(i).getFunction());
//            for(int j=0;j<cmmList.get(i).getFunctionProperties().size();j++){
//                System.out.println(cmmList.get(i).getFunctionProperties().get(j));
//            }
//        }
        ReadCsv rc = new ReadCsv(rpf.getSourceInputPath());
        ArrayList<InputDataModel> idmList = rc.readTargetCsv(cmmList);
        for(int i=0;i<idmList.size();i++){
            for(Object obj: idmList.get(i).getValue()){
                System.out.println(obj);
            }
        }
        
        //rc.readInputCsv();
        
    }

    private void getFromDB(ReadPropertiesFile rpf, ArrayList<ColumnMatcherModel> cmmList) {
        System.out.println("db");
    }
    
}
