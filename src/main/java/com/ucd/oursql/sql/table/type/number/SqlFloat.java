package com.ucd.oursql.sql.table.type.number;


import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.text.NumberFormat;
import java.util.HashMap;

//float型的数据存储大小为8个字节，可精确到小数点后第15位数字。
//这种数据类型的数据存储范围为从-1.79E+308～-2.23E-308，0和2.23E+308～1.79E+308。
//FLOAT(size,d) 带有浮动小数点的小数字。在括号中规定最大位数。在 d 参数中规定小数点右侧的最大位数。
public class SqlFloat implements  SqlType {
    private Float data =new Float(0);
    private int scale=-1;
    private int precision=-1;

    public SqlFloat(){}

    public SqlFloat(float f){
        this.data=f;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlFloat(float f,int scale,int precision){
        this.data=f;
        this.scale=scale;
        this.precision=precision;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

    public int getScale() {
        return scale;
    }

    public int getPrecision() {
        return precision;
    }

    public void setData(Float data) {
        this.data = data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void changeRange() throws Exception {
        if(scale==-1&&precision==-1){
        }else if(scale<=precision){
            throw new Exception("ERROR: For float(M,D),double(M,D) or decimal(M,D), M must be > D");
        }else if(precision==-1){
            int temp=data.intValue();
            int length=String.valueOf(temp).length();
            if(length>=scale){
                String str=String.valueOf(temp);
                str=str.substring(0,scale);
                data=Float.parseFloat(str);
            }else{
                int size=scale-String.valueOf(temp).length();
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setGroupingUsed(false);
                nf.setMaximumFractionDigits(size);
                data=Float.parseFloat(nf.format(data));
            }
        }else{
            int temp=data.intValue();
            int size=scale-String.valueOf(temp).length();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(false);
            if(size>=precision){
                nf.setMaximumFractionDigits(precision);
            }else{
                nf.setMaximumFractionDigits(size);
            }
            data=Float.parseFloat(nf.format(data));
        }
    }


    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public SqlType addOne() {
        return new SqlFloat(this.data+1);
    }

    @Override
    public void setScale(int i) throws Exception {
        this.scale=i;
    }

    @Override
    public void setPrecision(int i) throws Exception {
        this.precision=i;
    }

    @Override
    public void updateValue() throws Exception {
        changeRange();
    }

    @Override
    public SqlType add(SqlType a) throws Exception {
        this.data=this.data+(((SqlFloat)a).data);
        changeRange();
        return this;
    }

    @Override
    public void ave(int num) throws Exception {
        this.data=this.data/num;
        changeRange();
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) {
        setData(Float.parseFloat(o));
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlFloat)o).data);
    }
}
