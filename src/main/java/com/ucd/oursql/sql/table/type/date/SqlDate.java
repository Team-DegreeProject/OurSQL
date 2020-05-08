package com.ucd.oursql.sql.table.type.date;


import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.sql.Date;
import java.util.HashMap;

//yyyy-[m]m-[d]d
public class SqlDate implements SqlType {

    private Date date=null;

    public SqlDate(){}

    public SqlDate(String date){
        this.date= Date.valueOf(date);
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName){
        o=DMLTool.removeQutationMark(o);
        this.date=Date.valueOf(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=date.compareTo(((SqlDate)o).getDate());
        return i;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "'"+date.toString()+"'";
    }

    @Override
    public SqlType addOne() {
        return this;
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("Date do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Date do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {

    }

    @Override
    public SqlType add(SqlType a) throws Exception {
        throw new Exception("Error:This type do not support ADD opertion.");
    }

    @Override
    public void ave(int num) throws Exception {
        throw new Exception("Error:This type do not support AVE opertion.");
    }
}
