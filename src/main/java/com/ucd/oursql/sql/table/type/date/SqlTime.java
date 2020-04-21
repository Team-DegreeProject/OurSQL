package com.ucd.oursql.sql.table.type.date;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.table.type.SqlType;

import java.sql.Time;

//hh:mm:ss
public class SqlTime implements SqlType {

    private Time time=null;

    public SqlTime(){}

    public SqlTime(String s){
        this.time=Time.valueOf(s);
    }


    @Override
    public void setValue(String o) {
        o= DMLTool.removeQutationMark(o);
        this.time=Time.valueOf(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=time.compareTo(((SqlTime)o).getTime());
        return i;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "'"+time.toString()+"'";
    }

    @Override
    public SqlType addOne() {

        return this;
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("Time do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Time do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {

    }
}
