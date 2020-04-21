package com.ucd.oursql.sql.table.type.number;

import com.ucd.oursql.sql.table.type.SqlType;

public class SqlInt implements SqlType {

    private int scale=-1;
    private Integer data=0;

    public SqlInt(int data){
        this.data=data;
        changeRange();
    }

    public SqlInt(){}

    public SqlInt(int data, int scale){
        this.data=data;
        this.scale=scale;
        changeRange();
    }


    public String intToString(){
        if(scale==-1){
            return Integer.toString(data);
        }else{
            String str=Integer.toString(data);
            if(scale<=str.length()){
                return str;
            }else{
                int zeroNum=scale-str.length();
                String zeros="";
                for(int i=0;i<zeroNum;i++){
                    zeros=zeros+"0";
                }
                return zeros+str;
            }
        }
    }



    public void changeRange(){
        if(scale!=-1){
            int length=String.valueOf(data).length();
            if(length>scale){
                String str=String.valueOf(data);
                str=str.substring(0,scale);
                data=Integer.parseInt(str);
            }
        }
    }

    public int getData() {
        return data;
    }

    public int getScale() {
        return scale;
    }

    public void setData(int data) {
        this.data = data;
        changeRange();
    }

    public void setScale(int scale) {
        this.scale = scale;
        changeRange();
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Int do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {
        changeRange();
    }


    @Override
    public String toString() {
        return intToString();
    }

    @Override
    public SqlType addOne() {
        return new SqlInt((this.data+1),this.scale);
    }

    @Override
    public void setValue(String o) {
        setData(Integer.parseInt(o));
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlInt)o).data);
    }
}
