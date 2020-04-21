package com.ucd.oursql.sql.execution.data;

import java.util.List;

public class DataStatements {

    public static String insertData(List tokens){
        String out="Wrong: Insert Data !";
        try {
            InsertDataStatement insertDataStatement=new InsertDataStatement(tokens);
            out=insertDataStatement.insertDataImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String deleteData(List tokens){
        String out="Wrong: Delete Data !";
        try {
            DeleteDataStatement deleteDataStatement=new DeleteDataStatement(tokens);
            out=deleteDataStatement.deleteDataImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String updateData(List tokens) {
        String out="Wrong: Update Data !";
        try {
            UpdateDataStatement updateDataStatement=new UpdateDataStatement(tokens);
            out=updateDataStatement.updateDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String selectData(List tokens){
        String out="Wrong: Select Data !";
        try {
            SelectDataStatement selectDataStatement=new SelectDataStatement(tokens);
            out=selectDataStatement.selectDataImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }
}
