package com.ucd.oursql.sql.execution.database;

import java.util.List;

public class DatabaseStatements {

    public static String createDatabase(List tokens){
        String out="Error: Create Database !";
        try {
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            out=cds.createDatabaseImpl();
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

    public static String renameDatabase(List tokens){
        String out="Error: Rename Database !";
        try {
            RenameDatabaseStatement renameDatabaseStatement=new RenameDatabaseStatement(tokens);
            out=renameDatabaseStatement.renameDatabaseImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String showDatabase(List tokens){
        String out="Error: Show Database !";
        ShowDatabaseStatement sds=new ShowDatabaseStatement();
        out=sds.showDatabaseStatementImpl();
        return out;
    }

    public static String dropDatabase(List tokens){
        String out="Error: Drop Database !";
        try {
            DropDatabaseStatement dds=new DropDatabaseStatement(tokens);
            out=dds.dropDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static Object useDatabase(List tokens){
        String out="Wrong: Use Database !";
        try {
            UseDatabaseStatement uds=new UseDatabaseStatement(tokens);
            return uds.useDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(out);
        return 0;
    }
}
