package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.FromStatement;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
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
        Table truncate=FromStatement.from(ExecuteStatement.db.getDatabase(),name);
        truncate.cleanAllData();
        descriptorSaver ds=new descriptorSaver(truncate.getTd(),truncate.getPropertyMap(),truncate.getTree(),ExecuteStatement.user.getUserName());
        ds.saveAll();
        String output=truncate.printTable(null);
        return output;
    }
}
