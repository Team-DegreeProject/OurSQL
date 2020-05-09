package com.ucd.oursql.sql.execution.database;

import com.ucd.oursql.sql.driver.OurSqlResultset;
import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class ShowDatabaseStatement {
    List statement=null;
    public ShowDatabaseStatement(){}
    public ShowDatabaseStatement(List list){
        this.statement=list;
    }

    public OurSqlResultset showDatabaseStatementImpl() throws ClassNotFoundException {
//        Table usa= ExecuteStatement.uad.getUserAccessedDatabase();
//        usa.printTable();
        Table show= WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"user",EQ,new SqlVarChar(ExecuteStatement.user.getUserName()));
        String output=show.printTable(null);

        OurSqlResultset ourSqlResultset=new OurSqlResultset(show.getTree().getDatas(),ExecuteStatement.getUserAccessedDatabases().getUserAccessedDatabase().getPropertyMap());

        return ourSqlResultset;
    }
}
