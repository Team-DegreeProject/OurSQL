package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;


//3 DROP TABLE
//3.1 DROP TABLE 一个表
//3.1.1 DROP TABLE tbname；
//3.2 DROP TABLE 多个表
//3.2.1 DROP TABLE TBNAME1,TBNAME2,….;
public class DropTableStatement {
    List statement=null;
    public DropTableStatement(List tokens){
        statement=tokens;
    }

    public int dropTableImpl() throws Exception {
        Table database=ExecuteStatement.db.getDatabase();
        List names= (List) statement.get(2);
        for(int i=0;i<names.size();i++){
            String name=((Token)names.get(i)).image;
            Table delete= WhereStatament.compare(database,"tablename",EQ,new SqlVarChar(name));
            if(delete.getTree().getDataNumber()==0){
                throw new Exception("Error:Nothing to be deleted.");
            }
            database.deleteRows(delete,1);
            TreeSaver ts=new TreeSaver();
            ts.deleteTable(name,ExecuteStatement.user.getUserName());
        }
        ExecuteStatement.updateDB();
        String output=database.printTable(null);
        return 1;
    }
}
