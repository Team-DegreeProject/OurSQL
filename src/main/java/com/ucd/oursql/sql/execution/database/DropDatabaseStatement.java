package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;


public class DropDatabaseStatement {
    List statement=null;
    public DropDatabaseStatement(){}
    public DropDatabaseStatement(List tokens){
        statement=tokens;
    }
    public String dropDatabaseStatementImpl() throws ClassNotFoundException {
        if(statement==null){
            return "Drop Database Wrong!";
        }
        String databaseName=((Token)statement.get(2)).image;
        Table delete=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"databasename",EQ,databaseName);
        ExecuteStatement.uad.getUserAccessedDatabase().deleteRows(delete);
        String output=ExecuteStatement.uad.printUserAccessedDatabase();
        return output;
    }
}
