package com.ucd.oursql.sql.table.type.text;

import com.ucd.oursql.sql.table.type.SqlType;

public class SqlVarChar implements SqlType {
    private String string="";
    private int scale=-1;

    public SqlVarChar(){

    }

    public SqlVarChar(String str){
        changeRange(str);
    }

    public SqlVarChar(int l, String str){
        scale=l;
        changeRange(str);
    }

    public void changeRange(String str){
        if(scale==-1){
            string=str;
            return;
        }
        if(str.length()<=scale){
            string=str;
        }else{
            string=str.substring(0,scale);
        }
    }

//    @Override
//    public int compareTo(SqlVarChar o) {
//        int re=this.string.compareTo(o.string);
//        return re;
//    }

    public int getLength(){
        return scale;
    }

    public String getString() {
        return string;
    }

    public void setLength(int length) {
        this.scale = length;
    }

    public void setString(String str) {
        changeRange(str);
    }

    @Override
    public String toString() {
        return string.toString();
    }

    @Override
    public SqlType addOne() throws Exception {
        return this;
    }

    @Override
    public void setScale(int i) throws Exception {
        this.scale=i;
        changeRange(string);
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Varchar do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {
        changeRange(this.string);
    }

    @Override
    public void setValue(String o) {
        setString(o);
    }

    @Override
    public int compareTo(Object o) {
        int re=this.string.compareTo(((SqlVarChar)o).string);
        return re;
    }
}
