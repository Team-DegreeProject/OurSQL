package com.ucd.oursql.sql.execution.table;

import javax.jws.Oneway;
import java.util.List;


public class TableStatements {

    public static Object createTable(List tokens){
        String out="Error: Create Table !";
        try {
            CreateTableStatement createTableStatement=new CreateTableStatement(tokens);
            return createTableStatement.createImpl();
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }

    public static Object dropTable(List tokens){
        String out="Error: Drop Table !";
        try {
            DropTableStatement dropTableStatement=new DropTableStatement(tokens);
            return dropTableStatement.dropTableImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }



    public static Object renameTable(List tokens){
        String out="Error: Remove Table !";
        try {
            RenameTableStatement renameTableStatement = new RenameTableStatement(tokens);
            return renameTableStatement.renameTableImpl();
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
    }


    public static Object alterTable(List tokens){
        String out="Error: Alter Table !";
        try {
            AlterTableStatement alterTableStatement=new AlterTableStatement(tokens);
            return alterTableStatement.alterTableImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
//        return out;
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

    public static Object truncateTable(List tokens){
        String out="Error: Truncate Table !";
        try {
            TruncateTableStatement truncateTableStatement=new TruncateTableStatement(tokens);
            return truncateTableStatement.truncateTableImpl();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static Object showTable(List tokens){
        String out="Error: Show Table!";
        ShowTableStatement showTableStatement=new ShowTableStatement(tokens);
        return showTableStatement.showDatabaseStatementImpl();
//        return out;
    }
}
