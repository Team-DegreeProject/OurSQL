package com.ucd.oursql.sql.table.type.date;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.sql.Timestamp;
import java.util.HashMap;

//yyyy-[m]m-[d]d hh:mm:ss
public class SqlTimeStamp implements SqlType {

    private  Timestamp timestamp=null;

    public SqlTimeStamp(){}

    public SqlTimeStamp(String ts){
        this.timestamp=Timestamp.valueOf(ts);
    }

   @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName){
       o= DMLTool.removeQutationMark(o);
        this.timestamp=Timestamp.valueOf(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=this.timestamp.compareTo(((SqlTimeStamp)o).getTimestamp());
        return i;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "'"+timestamp.toString()+"'";
    }

    @Override
    public SqlType addOne() {

        return this;
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("TimeStamp do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("TimeStamp do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {

    }
}
