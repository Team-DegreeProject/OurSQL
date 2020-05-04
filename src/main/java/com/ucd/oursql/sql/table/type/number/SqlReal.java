package com.ucd.oursql.sql.table.type.number;


//real型数据的存储大小为4个字节，可精确到小数点后第7位数字。
//这种数据类型的数据存储范围为从-3.40E+38～3.40E+38。


import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.HashMap;

public class SqlReal implements  SqlType {

    private Float data=new Float(0);

    public SqlReal(float f){
        this.data=f;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public SqlReal(){}


    public void setData(float data) {
        this.data = data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getData() {
        return data;
    }

    public void changeRange() throws Exception {
        if(-3.40E+38<=data&&data<=3.40E+38){
        }else{
            throw new Exception("The number is not in the real range!");
        }

    }


    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public SqlType addOne() throws Exception {
        return new SqlReal(this.data+1);
    }

    @Override
    public void setScale(int i) throws Exception {
        throw new Exception("Real do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Real do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) {
        setData(Float.parseFloat(o));
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlReal)o).data);
    }
}
