package com.ucd.oursql.sql.table.type;

import com.ucd.oursql.sql.table.ColumnDescriptorList;

import java.util.HashMap;

public interface SqlType extends Comparable {
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) throws Exception;
    public String toString();
    public SqlType addOne() throws Exception;
    public void setScale(int i) throws Exception;
    public void setPrecision(int i) throws Exception;
    public void updateValue() throws Exception;
}
