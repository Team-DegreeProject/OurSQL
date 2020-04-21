package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.FromStatement;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

//4 TRUNCATE TABLE 删除表中所有数据
//4.1 TRUNCATE tbname；
public class TruncateTableStatement {

    public List statement;

    public TruncateTableStatement(List tokens){
        statement=tokens;
    }

    public String truncateTableImpl() throws Exception {
        String name=((Token)statement.get(1)).image;
        Table truncate=FromStatement.from(name);
        truncate.cleanAllData();
        String output=truncate.printTable(null);
        return output;
    }
}
