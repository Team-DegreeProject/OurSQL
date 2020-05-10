package com.ucd.oursql.sql.table.type.number;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.HashMap;

public class SqlBigInt implements SqlType {

    private int scale=-1;
    private Long data=new Long(0);

    public SqlBigInt(){}

    public SqlBigInt(long data){
        this.data=data;
        changeRange();
    }

    public SqlBigInt(long data,int scale){
        this.data=data;
        this.scale=scale;
        changeRange();
    }

    public int getScale() {
        return scale;
    }

    public void setData(long data) {
        this.data = data;
        changeRange();
    }

    public long getData() {
        return data;
    }

    public void changeRange(){
        if(scale!=-1){
            int length=String.valueOf(data).length();
            if(length>scale){
                String str=String.valueOf(data);
                str=str.substring(0,scale);
                data=Long.parseLong(str);
            }
        }
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl, String columnName) throws Exception {
        setData(Long.parseLong(o));
    }

    @Override
    public SqlType addOne() throws Exception {
        return new SqlBigInt(data+1,scale);
    }

    @Override
    public void setScale(int i) throws Exception {
        this.scale=i;
    }

    @Override
    public void setPrecision(int i) throws Exception {

    }

    @Override
    public void updateValue() throws Exception {
        changeRange();
    }

    @Override
    public SqlType add(SqlType a) throws Exception {
        this.data=this.data+((SqlBigInt)a).data;
        changeRange();
        return this;
    }

    @Override
    public void ave(int num) throws Exception {
        this.data=this.data/num;
        changeRange();
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlBigInt)o).data);
    }
}
