package com.ucd.oursql.sql.table.type;

public interface SqlType extends Comparable {
    public void setValue(String o) throws Exception;
    public String toString();
    public SqlType addOne() throws Exception;
    public void setScale(int i) throws Exception;
    public void setPrecision(int i) throws Exception;
    public void updateValue() throws Exception;
}
