package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Database;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class UseDatabaseStatement {
    List statement=null;
    public UseDatabaseStatement(){}
    public UseDatabaseStatement(List token){
        statement=token;
    }
    public  String useDatabaseStatementImpl() throws ClassNotFoundException {
//        descriptorLoader dl=new descriptorLoader();
//        dl.loadFromFile(databaseName);
        ExecuteStatement.uad.printUserAccessedDatabase();
        Table table=ExecuteStatement.uad.getUserAccessedDatabase();
        String databaseName=((Token)statement.get(1)).image;

        descriptorLoader dl=new descriptorLoader();
        Table t=dl.loadFromFile(databaseName);

//        Table t=WhereStatament.compare(table,"databasename",EQ,databaseName);
//        ExecuteStatement.uad.printUserAccessedDatabase();
//        CglibBean c= (CglibBean) t.getTree().getDatas().get(0);
        Database database= new Database();
        database.setDatabase(t);
//        database.getDatabase().printTable(null);
        ExecuteStatement.db=database;
        System.out.println("===================");
        String output=database.printDatabase();
        return output;
    }
}
