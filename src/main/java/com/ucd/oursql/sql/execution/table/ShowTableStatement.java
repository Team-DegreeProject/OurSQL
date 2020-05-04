package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.ExecuteStatement;

import java.util.List;

public class ShowTableStatement {
    List statement=null;
    public ShowTableStatement(){}
    public ShowTableStatement(List list){
        this.statement=list;
    }

    public String showDatabaseStatementImpl(){
//        Table usa= ExecuteStatement.uad.getUserAccessedDatabase();
//        usa.printTable();
        String output= ExecuteStatement.db.printDatabase();
        return output;
    }
}
