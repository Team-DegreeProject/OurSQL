package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.execution.data.DataStatements;
import com.ucd.oursql.sql.execution.database.*;
import com.ucd.oursql.sql.execution.table.*;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.system.User;
import com.ucd.oursql.sql.system.UserAccessedDatabases;
import com.ucd.oursql.sql.table.Database;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class ExecuteStatement {

    public static User user;//%%
    public static UserAccessedDatabases uad=getUserAccessedDatabases();//%%
    public static Database db=null;


    public static void setUser(String name){
        user=new User(name);
//        uad=user.getUserAccessedDatabases();
        uad.setUser(user);
//        return uad;
    }

    public static UserAccessedDatabases getUserAccessedDatabases(){
        UserAccessedDatabases u=null;
        try {
            u=new UserAccessedDatabases();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return u;
    }



    public static String rename(List tokens){
        String out="Wrong: Rename !";
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            out=DatabaseStatements.renameDatabase(tokens);
        }else{
            out=TableStatements.renameTable(tokens);
        }
        return out;
    }

    public static String create(List tokens){
        String out="Wrong: Create !";
        int name=((Token)tokens.get(1)).kind;
        if(name==DATABASE){
            out=DatabaseStatements.createDatabase(tokens);
        }else if(name==TABLE){
            try {
                if(db==null){
                    out="Wrong: There is no database !";
                }
                TableStatements.createTable(tokens);
                out=db.printDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public static String drop(List tokens){
        String out="Wrong: Drop !";
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            out=DatabaseStatements.dropDatabase(tokens);
        }else{
            out=TableStatements.dropTable(tokens);
        }
        return out;
    }

    public static String alter(List tokens){
        String out=TableStatements.alterTable(tokens);
        return out;
    }

    public static String insert(List tokens){
        return DataStatements.insertData(tokens);
    }

    public static String delete(List tokens){return DataStatements.deleteData(tokens);}

    public static String update(List tokens){return DataStatements.updateData(tokens);}

    public static String truncate(List tokens){return TableStatements.truncateTable(tokens);};

    public static String select(List tokens){return DataStatements.selectData(tokens);}

    public static String show(List tokens){return DatabaseStatements.showDatabase(tokens);}

    public static String use(List tokens){return DatabaseStatements.useDatabase(tokens);}

}
