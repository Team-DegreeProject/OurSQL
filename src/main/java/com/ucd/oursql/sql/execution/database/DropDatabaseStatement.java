package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;


public class DropDatabaseStatement {
    List statement=null;
    public DropDatabaseStatement(){}
    public DropDatabaseStatement(List tokens){
        statement=tokens;
    }
    public int dropDatabaseStatementImpl() throws ClassNotFoundException {
        if(statement==null){
            System.out.println("Drop Database Wrong!");
            return 0;
        }
        String databaseName=((Token)statement.get(2)).image;
        Table delete=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"databasename",EQ,new SqlVarChar(databaseName));
        boolean b=ExecuteStatement.uad.getUserAccessedDatabase().deleteRows(delete);
        if(b){
            TreeSaver ts=new TreeSaver();
            ts.deleteTable(databaseName);
        }

        String output=ExecuteStatement.uad.printUserAccessedDatabase();
        return 1;
    }
}
