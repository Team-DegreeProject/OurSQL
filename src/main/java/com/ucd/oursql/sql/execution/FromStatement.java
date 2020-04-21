package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;

import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class FromStatement {

    public static List<Table> from(Table database,String tableName){

        BPlusTree databaseTable=database.getTree();
        List<Table> tables=new ArrayList<>();
        List<CglibBean> list=BPlusTreeTool.getParticularAttribute(database,"tablename",tableName);
        for(int i=0;i<list.size();i++){
            CglibBean c=list.get(i);
            tables.add((Table) c.getValue("table"));
        }
        return tables;
    }

    public static Table from(String name) throws ClassNotFoundException {
        Table database= ExecuteStatement.db.getDatabase();
        Table t= (Table) ((CglibBean)((List)WhereStatament.compare(database,"tablename",EQ,name).getTree().getDatas()).get(0)).getValue("table");
        return t;
    }

}
