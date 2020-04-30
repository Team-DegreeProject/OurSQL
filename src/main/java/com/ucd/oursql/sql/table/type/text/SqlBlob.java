package com.ucd.oursql.sql.table.type.text;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.sql.Blob;
import java.util.HashMap;

public class SqlBlob implements SqlType {

    private Blob blob;

    public SqlBlob(Blob b){
        this.blob=b;
    }

    public SqlBlob(){}

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl) {

    }

    @Override
    public SqlType addOne() throws Exception {
        throw new Exception("Blob can not auto increment.");
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("Blob do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Blob do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
