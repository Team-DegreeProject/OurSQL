package com.ucd.oursql.sql.execution.data;

import java.util.List;

public class DataStatements {

    public static Object insertData(List tokens){
//        String out="Error: Insert Data !";
        try {
            InsertDataStatement insertDataStatement=new InsertDataStatement(tokens);
            return insertDataStatement.insertDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object deleteData(List tokens){
        String out="Error: Delete Data !";
        try {
            DeleteDataStatement deleteDataStatement=new DeleteDataStatement(tokens);
            return deleteDataStatement.deleteDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object updateData(List tokens) {
        String out="Error: Update Data !";
        try {
            UpdateDataStatement updateDataStatement=new UpdateDataStatement(tokens);
            return updateDataStatement.updateDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object selectData(List tokens){
        String out="Error: Select Data !";
        try {
            SelectDataStatement selectDataStatement=new SelectDataStatement(tokens);
            return selectDataStatement.selectDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        return out;
    }
}
