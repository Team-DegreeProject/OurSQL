package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.execution.data.DataStatements;
import com.ucd.oursql.sql.execution.database.*;
import com.ucd.oursql.sql.execution.table.*;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.system.User;
import com.ucd.oursql.sql.system.UserAccessedDatabases;
import com.ucd.oursql.sql.system.Database;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class ExecuteStatement {

    public static User user=null;//%%
    public static UserAccessedDatabases uad=null;//%%
    public static Database db=null;

//    public static User setUserTest(String name){
//        User u=new User(name);
////        uad=user.getUserAccessedDatabases();
////        uad.setUser(user);
//        return u;
////        return uad;
//    }

//    public static void setAll(){
//        if(user == null){
//            user=setUserTest("root");
//        }
//        if(uad==null){
//            uad=getUserAccessedDatabases();
//        }
//    }

    public static void setUser(String name){
        if(name!=null){
            user=new User(name);
        }else{
            user=new User("root");
        }
        if(uad==null){
            uad=getUserAccessedDatabases();
        }
//        uad=user.getUserAccessedDatabases();
//        return uad;
    }


    public static void updateUAD(){
        descriptorLoader descriptorLoader=new descriptorLoader();
        Table t=descriptorLoader.loadFromFile("UserPermissionDatabaseScope","root");
        uad.setUserAccessedDatabase(t);
    }

    public static void updateDB(){
        Table d=db.getDatabase();
//        descriptorSaver descriptorSaver=new descriptorSaver(d.getTableDescriptor(),d.getPropertyMap(),d.getTree(),user.getUserName());
//        descriptorSaver.saveAll();
        descriptorLoader descriptorLoader=new descriptorLoader();
        Table t=descriptorLoader.loadFromFile(d.getTd().getTableName(),user.getUserName());
        db.setDatabase(t);
    }


    public static UserAccessedDatabases getUserAccessedDatabases(){
        UserAccessedDatabases u= null;
        try {
            u = new UserAccessedDatabases();
            u.setUser(user);
            descriptorLoader dl=new descriptorLoader();
            Table t=dl.loadFromFile("UserPermissionDatabaseScope","root");
//            System.out.println("UPDS===null");
//            t.printTable(null);
            if(t==null){
                System.out.println("====null=====");
//                Table t1=dl.loadFromFile("UserPermissionDatabaseScope",user.getUserName());
//                if(t1==null){
//                    System.out.println("Here null");
//                }
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



    public static Object rename(List tokens){
//        setAll();
        String out="Error: Rename !";
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            return DatabaseStatements.renameDatabase(tokens);
        }else{
           return TableStatements.renameTable(tokens);
        }
    }

    public static Object create(List tokens){
//        setAll();
        String out="Error: Create !";
        int name=((Token)tokens.get(1)).kind;
        if(name==DATABASE){
            return DatabaseStatements.createDatabase(tokens);
        }else if(name==TABLE){
            try {
                if(db==null){
                    out="Wrong: There is no database !";
                    return 0;
                }
                return TableStatements.createTable(tokens);
//                out=db.printDatabase();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return out;
    }

    public static Object drop(List tokens){
//        setAll();
        String out="Error: Drop !";
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            return DatabaseStatements.dropDatabase(tokens);
        }else{
            return TableStatements.dropTable(tokens);
        }
    }

    public static Object alter(List tokens){
//        setAll();
        return TableStatements.alterTable(tokens);
    }

    public static Object insert(List tokens){
//        setAll();
        return DataStatements.insertData(tokens);
    }

    public static Object delete(List tokens){
//        setAll();
        return DataStatements.deleteData(tokens);
    }

    public static Object update(List tokens){
//        setAll();
        return DataStatements.updateData(tokens);
    }

    public static Object truncate(List tokens){
//        setAll();
        return TableStatements.truncateTable(tokens);
    };

    public static Object select(List tokens){
//        setAll();
        return DataStatements.selectData(tokens);
    }

    public static Object show(List tokens){
//        setAll();
        String out="Error: Show !";
        Token t= (Token) tokens.get(1);
        if(t.kind==TABLES){
            return TableStatements.showTable(tokens);
        }else if(t.kind==DATABASES){
            return DatabaseStatements.showDatabase(tokens);
        }
        return out;
    }

    public static Object use(List tokens){
//        setAll();
        int r= (int) DatabaseStatements.useDatabase(tokens);
        System.out.println("====result====="+r);
        return r;
    }
}
