package com.ucd.oursql.sql.execution.table;

import java.util.List;


public class TableStatements {

    public static String createTable(List tokens){
        String out="Error: Create Table !";
        try {
            CreateTableStatement createTableStatement=new CreateTableStatement(tokens);
            out=createTableStatement.createImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String dropTable(List tokens){
        String out="Error: Drop Table !";
        try {
            DropTableStatement dropTableStatement=new DropTableStatement(tokens);
            out=dropTableStatement.dropTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }



    public static String renameTable(List tokens){
        String out="Error: Remove Table !";
        try {
            RenameTableStatement renameTableStatement = new RenameTableStatement(tokens);
            out=renameTableStatement.renameTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }


    public static String alterTable(List tokens){
        String out="Error: Alter Table !";
        try {
            AlterTableStatement alterTableStatement=new AlterTableStatement(tokens);
            out=alterTableStatement.alterTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return out;
//            Object o=tokens.get(3);
//            if(o instanceof Token){
//                int type=((Token)tokens.get(3)).kind;
//                if (type == MODIFY) {
//                    alterTableStatement.alterModifyImpl();
//                }
//            }else if (o instanceof List){
//                List<List> l= (List) tokens.get(3);
//                int t=((Token)l.get(0).get(0)).kind;
//                if(t== ADD){
//                    alterTableStatement.alterTableAddColumnStatement();
//                }else if (t==DROP){
//                    alterTableStatement.alterTableDropImpl();
//                }

//            }
    }

    public static String truncateTable(List tokens){
        String out="Error: Truncate Table !";
        try {
            TruncateTableStatement truncateTableStatement=new TruncateTableStatement(tokens);
            out=truncateTableStatement.truncateTableImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static Object showTable(List tokens){
        String out="Error: Show Table!";
        ShowTableStatement showTableStatement=new ShowTableStatement(tokens);
        return showTableStatement.showDatabaseStatementImpl();
//        return out;
    }
}
