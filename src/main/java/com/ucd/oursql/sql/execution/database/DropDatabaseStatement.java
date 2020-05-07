package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;


public class DropDatabaseStatement {
    List statement=null;
    public DropDatabaseStatement(){}
    public DropDatabaseStatement(List tokens){
        statement=tokens;
    }
    public int dropDatabaseStatementImpl() throws Exception {
        if(statement==null){
            throw new Exception("Error: Drop statement null");
//            System.out.println("Drop Database Wrong!");
//            return 0;
        }
        String databaseName=((Token)statement.get(2)).image;

        PrimaryKey pk=new PrimaryKey();
        pk.addPrimaryKey("databasename",new SqlVarChar(databaseName));
        pk.addPrimaryKey("user",new SqlVarChar(ExecuteStatement.user.getUserName()));
        Table delete=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"primary_key",EQ,pk);
//        Table delete=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"databasename",EQ,new SqlVarChar(databaseName));
        boolean b=ExecuteStatement.uad.getUserAccessedDatabase().deleteRows(delete,0);
        if(b){
            TreeSaver ts=new TreeSaver();
            ts.deleteTable(databaseName,ExecuteStatement.user.getUserName());
        }else{
            throw new Exception("Error:Drop database!");
        }
        ExecuteStatement.updateUAD();
        String output=ExecuteStatement.uad.printUserAccessedDatabase();
        return 1;
    }
}
