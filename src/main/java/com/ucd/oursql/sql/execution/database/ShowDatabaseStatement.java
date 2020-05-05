package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.driver.OurSqlResultset;
import com.ucd.oursql.sql.execution.ExecuteStatement;

import java.util.List;

public class ShowDatabaseStatement {
    List statement=null;
    public ShowDatabaseStatement(){}
    public ShowDatabaseStatement(List list){
        this.statement=list;
    }

    public OurSqlResultset showDatabaseStatementImpl(){
//        Table usa= ExecuteStatement.uad.getUserAccessedDatabase();
//        usa.printTable();
        String output=ExecuteStatement.uad.printUserAccessedDatabase();

        OurSqlResultset ourSqlResultset=new OurSqlResultset(ExecuteStatement.uad.getUserAccessedDatabase().getTree().getDatas(),ExecuteStatement.uad.getUserAccessedDatabase().getPropertyMap());

        return ourSqlResultset;
    }
}
