package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.driver.OurSqlResultset;
import com.ucd.oursql.sql.execution.ExecuteStatement;

import java.util.List;

public class ShowTableStatement {
    List statement=null;
    public ShowTableStatement(){}
    public ShowTableStatement(List list){
        this.statement=list;
    }

    public Object showDatabaseStatementImpl(){
//        Table usa= ExecuteStatement.uad.getUserAccessedDatabase();
//        usa.printTable();
        String output= ExecuteStatement.db.printDatabase();
        OurSqlResultset ourSqlResultset=new OurSqlResultset(ExecuteStatement.db.getDatabase().getTree().getDatas(),ExecuteStatement.db.getDatabase().getPropertyMap());
        return ourSqlResultset;
    }
}
