package com.ucd.oursql.sql.table.type.date;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.time.Year;
import java.util.HashMap;

public class SqlYear implements SqlType {

    private Year year=null;

    public SqlYear(){}

    public SqlYear(String s){
        this.year=Year.parse(s);
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) {
        o= DMLTool.removeQutationMark(o);
        this.year=Year.parse(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=year.compareTo(((SqlYear)o).getYear());
        return i;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "'"+year.toString()+"'";
    }

    @Override
    public SqlType addOne() {

        return this;
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("Year do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Year do not need precision.");
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
