package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.system.Database;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class CreateDatabaseStatement{
    List statement=null;
    public CreateDatabaseStatement(List l){
        statement=l;
    }
    public CreateDatabaseStatement(){}

    public int createDatabaseImpl() throws Exception {
        if(statement==null){
            System.out.println("Error:Create Database Wrong!");
            throw new Exception("Error:Create Database Wrong!");
        }
        String databaseName=  ((Token)statement.get(2)).image;

        PrimaryKey pk=new PrimaryKey();
        pk.addPrimaryKey("databasename",new SqlVarChar(databaseName));
        pk.addPrimaryKey("user",new SqlVarChar(ExecuteStatement.user.getUserName()));
        Table check=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"primary_key",EQ,pk);
        int size=check.getTree().getDataNumber();
        if(size>0){
            System.out.println("Error:There is a database with the same name!");
            throw new Exception("Error:There is a database with the same name!");
        }

        descriptorLoader descriptorLoader=new descriptorLoader();
        Table testDB=descriptorLoader.loadFromFile(databaseName,ExecuteStatement.user.getUserName());
        if(testDB!=null){
            throw new Exception("Error: There is a folder with the same name.");
        }

        Database db=new Database(databaseName);
//        System.out.println("===========");
//        db.getDatabase().getTableDescriptor().printTableDescriptor();
//        ExecuteStatement.uad.getUserAccessedDatabase().getTableDescriptor().printTableDescriptor();
        ExecuteStatement.uad.insertDatabase(db);
        ExecuteStatement.updateUAD();
//        db.printDatabase();
//        descriptorSaver ds=new descriptorSaver(db.getDatabase().getTableDescriptor(),db.getDatabase().getPropertyMap(),db.getDatabase().getTree());
//        ds.saveAll();

        String output=ExecuteStatement.uad.getUserAccessedDatabase().printTable(null);
        return 0;
    }

//    public Database createDatabaseImpl(int i) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
////        TableDescriptor td=null;
//        String databasename="test";
//        Database db=new Database(databasename);
//        ExecuteStatement.uad.insertDatabase(db);
//        return db;
//    }

}
