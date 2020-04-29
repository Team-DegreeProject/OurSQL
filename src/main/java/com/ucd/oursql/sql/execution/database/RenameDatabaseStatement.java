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
    public String renameDatabaseImpl() throws ClassNotFoundException {
        boolean bool=true;
        String databaseName=((Token)statement.get(2)).image;
        String newDatabaseName=((Token)statement.get(4)).image;
        String[] att={"databasename"};
        List values=new ArrayList();
//        att.add("database");
//        att.add("databasename");
        Table usa=ExecuteStatement.uad.getUserAccessedDatabase();
        Table change=WhereStatament.compare(usa,"databasename",EQ,new SqlVarChar(databaseName));
        List list=  change.getTree().getDatas();
        CglibBean c= (CglibBean) list.get(0);

//        usa.printTable(null);
        values.add(new SqlVarChar(newDatabaseName));
        bool=usa.updateTable(att,values,change);
        if(bool==false){
            return "Rename Database Wrong!";
        }


        descriptorLoader dl=new descriptorLoader();
        Database database=new Database(dl.loadFromFile(databaseName));
        TreeSaver tl= new TreeSaver();
        System.out.println(databaseName);
        tl.deleteTable(databaseName);
        Table t=database.getDatabase();
        t.getTableDescriptor().setTableName(newDatabaseName);
        descriptorSaver ds=new descriptorSaver(t.getTd(),t.getPropertyMap(),t.getTree());
        ds.saveAll();

//        String[] nameatt={"database"};
//        values=new ArrayList();
//        Database database= (Database) c.getValue("database");
//        values.add(database);
//        database.setDatabaseName(newDatabaseName);
//        bool=usa.updateTable(nameatt,values,change);
        String output=ExecuteStatement.uad.getUserAccessedDatabase().printTable(null);

//        List l=new ArrayList();
//        l.add(new SqlInt(0));
//        PrimaryKey pk=new PrimaryKey(l);
//        CglibBean ct= (CglibBean) usa.getTree().select(pk);
//        System.out.println("===============================ct:"+((Database)ct.getValue("database")).getDatabaseName());
        return output;
    }

}
