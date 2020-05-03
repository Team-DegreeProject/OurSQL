package com.ucd.oursql.sql.table.type.text;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.HashMap;

public class SqlChar implements SqlType {
    private String string="";
    private int scale=-1;
    private int realLength=0;

    public SqlChar(){}


    public SqlChar(String str){
        changeRange(str);
    }

    public SqlChar(int l, String str){
        scale=l;
        changeRange(str);
    }

    public void changeRange(String str){
        if(scale==-1){
            string=str;
            return;
        }
        int difference=scale-str.length();
        if(difference<0){
            string=str.substring(0,scale);
            realLength=scale;
        }else if(difference==0){
            string=str;
            realLength=scale;
        }else{
            string=str;
            for(int i=0;i<difference;i++){
                string.concat(" ");
            }
            realLength=str.length();
        }
    }

    public void setString(String str) {
        changeRange(str);
    }

    public String getString() {
        return string;
    }

    public void setLength(int length) {
        this.scale= length;
    }

    public int getLength() {
        return scale;
    }

    public int getRealLength() {
        return realLength;
    }

    public Boolean hasBlankChars() {
        if(realLength<scale){
            return true;
        }else{
            return false;
        }
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
        throw new Exception("Char do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {
        changeRange(this.string);
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) {
        setString(o);
    }

    @Override
    public int compareTo(Object o) {
        int re=this.string.compareTo(((SqlChar)o).string);
        return re;
    }
}
