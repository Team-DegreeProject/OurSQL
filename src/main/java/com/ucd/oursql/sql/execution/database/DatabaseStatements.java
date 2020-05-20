package com.ucd.oursql.sql.execution.database;

import java.util.List;

public class DatabaseStatements {

    public static Object createDatabase(List tokens){
        String out="Error: Create Database !";
        try {
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            return cds.createDatabaseImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object renameDatabase(List tokens){
        String out="Error: Rename Database !";
        try {
            RenameDatabaseStatement renameDatabaseStatement=new RenameDatabaseStatement(tokens);
            return renameDatabaseStatement.renameDatabaseImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object showDatabase(List tokens){
        String out="Error: Show Database !";
        ShowDatabaseStatement sds=new ShowDatabaseStatement();
        try {
            return sds.showDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
//        return out;
    }

    public static Object dropDatabase(List tokens){
        String out="Error: Drop Database !";
        try {
            DropDatabaseStatement dds=new DropDatabaseStatement(tokens);
            return dds.dropDatabaseStatementImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object useDatabase(List tokens){
        String out="Wrong: Use Database !";
        try {
            UseDatabaseStatement uds=new UseDatabaseStatement(tokens);
            return uds.useDatabaseStatementImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        System.out.println(out);
//        return 0;
    }
}
