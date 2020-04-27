//package com.ucd.oursql.sql.storage.Storage;
//
//
//import com.ucd.oursql.sql.storage.Configuration.Configuration;
//import com.ucd.oursql.sql.table.BTree.BPlusTree;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class alterTable {
//    ArrayList<alterUnit> alterUnitsList;
//
//    public alterTable(){
//        alterUnitsList = new ArrayList<>();
//    }
//
//    public void addAlterUnit(alterUnit newUnit){
//        boolean exist = false;
//        for(alterUnit eachUnit : alterUnitsList){
//            if(eachUnit.getTableName().equals(newUnit.getTableName())){
//                eachUnit = newUnit;
//                exist = !exist;
//            }
//        }
//        if(!exist){
//            alterUnitsList.add(newUnit);
//        }
//
//    }
//
//    public BPlusTree getBtree(String tableName){
//        for(alterUnit eachUnit: alterUnitsList){
//            if(eachUnit.getTableName().equals(tableName)){
//                return eachUnit.getBtree();
//            }
//        }
//        return null;
//    }
//
//    public void listSaver() throws Exception {
//        for(alterUnit eachUnit : alterUnitsList){
//            if(eachUnit.getAlterType().equals( Configuration.DElETE)){
//                String tableName = eachUnit.getTableName();
//                String filePath = tableName+".xml";
//
//                File file = new File(filePath);
//                if(file != null){
//                    file.delete();
//                }
//
//            }
//            else if(eachUnit.getAlterType().equals(Configuration.CREATE)){
//                TreeSaver ts = new TreeSaver();
//                ts.SaveAsXML(eachUnit.getBtree());
//            }
//            else if(eachUnit.getAlterType().equals(Configuration.UPDATE)){
//                String tableName = eachUnit.getTableName();
//                String filePath = tableName+".xml";
//
//                File file = new File(filePath);
//                if(file != null){
//                    file.delete();
//                }
//                TreeSaver ts = new TreeSaver();
//                ts.SaveAsXML(eachUnit.getBtree());
//            }
//
//        }
//    }
//}
