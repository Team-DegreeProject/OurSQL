package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeLoader;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Database;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class RenameDatabaseStatement {
    List statement=null;
    public RenameDatabaseStatement(List l){
        statement=l;
    }
    public int renameDatabaseImpl() throws Exception {
        boolean bool=true;
        String databaseName=((Token)statement.get(2)).image;
        String newDatabaseName=((Token)statement.get(4)).image;
        String[] att={"databasename"};
        List values=new ArrayList();
//        att.add("database");
//        att.add("databasename");
        Table usa=ExecuteStatement.uad.getUserAccessedDatabase();

        PrimaryKey pk=new PrimaryKey();
        pk.addPrimaryKey("databasename",new SqlVarChar(databaseName));
        pk.addPrimaryKey("user",new SqlVarChar(ExecuteStatement.user.getUserName()));
        Table change=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"primary_key",EQ,pk);
//        Table change=WhereStatament.compare(usa,"databasename",EQ,new SqlVarChar(databaseName));
        List list=  change.getTree().getDatas();
        CglibBean c= (CglibBean) list.get(0);

//        usa.printTable(null);
        values.add(new SqlVarChar(newDatabaseName));
        bool=usa.updateTable(att,values,change,0);
        if(bool==false){
            throw new Exception("Error:Rename Database Wrong");
//            System.out.println("Rename Database Wrong!");
//            return 0;
        }


        descriptorLoader dl=new descriptorLoader();
        Database database=new Database(dl.loadFromFile(databaseName,ExecuteStatement.user.getUserName()));
        TreeSaver tl= new TreeSaver();
        System.out.println(databaseName);
        tl.deleteTable(databaseName);
        Table t=database.getDatabase();
        t.getTableDescriptor().setTableName(newDatabaseName);
        descriptorSaver ds=new descriptorSaver(t.getTd(),t.getPropertyMap(),t.getTree(),ExecuteStatement.user.getUserName());
        ds.saveAll();

//        String[] nameatt={"database"};
//        values=new ArrayList();
//        Database database= (Database) c.getValue("database");
//        values.add(database);
//        database.setDatabaseName(newDatabaseName);
//        bool=usa.updateTable(nameatt,values,change);
        String output=ExecuteStatement.uad.getUserAccessedDatabase().printTable(null);
        ExecuteStatement.updateUAD();
//        List l=new ArrayList();
//        l.add(new SqlInt(0));
//        PrimaryKey pk=new PrimaryKey(l);
//        CglibBean ct= (CglibBean) usa.getTree().select(pk);
//        System.out.println("===============================ct:"+((Database)ct.getValue("database")).getDatabaseName());
        return 1;
    }


}
