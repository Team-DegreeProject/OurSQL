package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.execution.data.DataStatements;
import com.ucd.oursql.sql.execution.database.*;
import com.ucd.oursql.sql.execution.table.*;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.system.User;
import com.ucd.oursql.sql.system.UserAccessedDatabases;
import com.ucd.oursql.sql.table.Database;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class ExecuteStatement {

    public static User user=null;//%%
    public static UserAccessedDatabases uad=null;//%%
    public static Database db=null;

    public static User setUserTest(String name){
        User u=new User(name);
//        uad=user.getUserAccessedDatabases();
//        uad.setUser(user);
        return u;
//        return uad;
    }

    public static void setAll(){
        if(user == null){
            user=setUserTest("root");
        }
        if(uad==null){
            uad=getUserAccessedDatabases();
        }
    }

    public static void setUser(String name){
        user=new User(name);
//        uad=user.getUserAccessedDatabases();
//        return uad;
    }



    public static UserAccessedDatabases getUserAccessedDatabases(){
        UserAccessedDatabases u= null;
        try {
            u = new UserAccessedDatabases();
            u.setUser(user);
            descriptorLoader dl=new descriptorLoader();
            Table t=dl.loadFromFile("UserPermissionDatabaseScope");
            t.printTable(null);
            if(t==null){
                System.out.println("====null=====");
                u.databaseList();
            }else{
                System.out.println("====!null=====");
                u.setUserAccessedDatabase(t);
                u.printUserAccessedDatabase();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return u;
    }



    public static String rename(List tokens){
        setAll();
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
        setAll();
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
        setAll();
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
        setAll();
        String out=TableStatements.alterTable(tokens);
        return out;
    }

    public static String insert(List tokens){
        setAll();
        return DataStatements.insertData(tokens);
    }

    public static String delete(List tokens){
        setAll();
        return DataStatements.deleteData(tokens);
    }

    public static String update(List tokens){
        setAll();
        return DataStatements.updateData(tokens);
    }

    public static String truncate(List tokens){setAll();return TableStatements.truncateTable(tokens);};

    public static String select(List tokens){setAll();return DataStatements.selectData(tokens);}

    public static String show(List tokens){setAll();return DatabaseStatements.showDatabase(tokens);}

    public static String use(List tokens){
        System.out.println("use1");
        setAll();
        System.out.println("use2");
        return DatabaseStatements.useDatabase(tokens);
    }
}
