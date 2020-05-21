package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class FromStatement {

    public static Table from(Table database,String tableName) throws Exception {
//        database.printTable(null);
//        BPlusTree databaseTable=database.getTree();
//        List<Table> tables=new ArrayList<>();
        Table com=WhereStatament.compare(database,"tablename",EQ,new SqlVarChar(tableName));
        if(com.getTree().getDataNumber()!=1){
            throw new Exception("Error: From statement!");
//            System.out.println("Wrong: From statement!");
//            return null;
        }
        descriptorLoader dl=new descriptorLoader();
        Table t=dl.loadFromFile(tableName,ExecuteStatement.user.getUserName());
        t.printTable(null);
//        System.out.println("====from====");
//        t.getTd().printTableDescriptor();
        return t;
//        for(int i=0;i<list.size();i++){
//            CglibBean c=list.get(i);
//
//            tables.add((Table) c.getValue("table"));
//        }
//        if(tables.size()!=1){
//
//        }
//        return tables;
    }

//    public static Table from(String name) throws ClassNotFoundException {
//        Table database= ExecuteStatement.db.getDatabase();
//        Table t=WhereStatament.compare(database,"tablename",EQ,new SqlVarChar(name));
//        return t;
//    }

}
