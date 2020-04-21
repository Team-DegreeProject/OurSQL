package com.ucd.oursql.sql.storage.Storage;


import com.ucd.oursql.sql.table.BTree.BPlusTree;

public class alterUnit {
    private String tableName;
    private String alterType;
    private BPlusTree Btree;

    //getter方法
    public String getAlterType() {
        return alterType;
    }
    public String getTableName() {
        return tableName;
    }
    public BPlusTree getBtree() {
        return Btree;
    }

    public alterUnit(String tn, String at,BPlusTree bt){
        tableName = tn;
        alterType = at;
        Btree = bt;

    }


}
