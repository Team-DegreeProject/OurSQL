package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.table.Database;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class CreateDatabaseStatement{
    List statement=null;
    public CreateDatabaseStatement(List l){
        statement=l;
    }
    public CreateDatabaseStatement(){}

    public String createDatabaseImpl() throws Exception {
        if(statement==null){
            return "Create Database Wrong!";
        }
        String databaseName=  ((Token)statement.get(2)).image;
        Table check=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"databasename",EQ,new SqlVarChar(databaseName));
        int size=check.getTree().getDataNumber();
        if(size>0){
            return "There is a database with the same name!";
        }
        Database db=new Database(databaseName);
//        System.out.println("===========");
//        db.getDatabase().getTableDescriptor().printTableDescriptor();
        ExecuteStatement.uad.insertDatabase(db);
//        db.printDatabase();
//        descriptorSaver ds=new descriptorSaver(db.getDatabase().getTableDescriptor(),db.getDatabase().getPropertyMap(),db.getDatabase().getTree());
//        ds.saveAll();
        String output=ExecuteStatement.uad.getUserAccessedDatabase().printTable(null);
        return output;
    }

//    public Database createDatabaseImpl(int i) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
////        TableDescriptor td=null;
//        String databasename="test";
//        Database db=new Database(databasename);
//        ExecuteStatement.uad.insertDatabase(db);
//        return db;
//    }

}
