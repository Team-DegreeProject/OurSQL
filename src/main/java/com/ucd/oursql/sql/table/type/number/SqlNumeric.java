package com.ucd.oursql.sql.table.type.number;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;

public class SqlNumeric implements SqlType {
    private BigDecimal data=new BigDecimal(0);
    private int scale=-1;
    private int precision=-1;

    public SqlNumeric(){}

    public SqlNumeric(BigDecimal data){
        this.data=data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlNumeric(double data,int scale,int precision){
        this.data=new BigDecimal(data);
        this.scale=scale;
        this.precision=precision;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlNumeric(BigDecimal data,int scale,int precision){
        this.data=data;
        this.scale=scale;
        this.precision=precision;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRange() throws Exception {
        if(scale==-1&&precision==-1){
        }else if(scale<=precision){
            throw new Exception("Scale should not be smaller than or equal to precision.");
        }else if(precision==-1){
            int temp=data.intValue();
            int length=String.valueOf(temp).length();
            if(length>=scale){
                String str=String.valueOf(temp);
                str=str.substring(0,scale);
                data=new BigDecimal(str);
            }else{
                int size=scale-String.valueOf(temp).length();
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setGroupingUsed(false);
                nf.setMaximumFractionDigits(size);
                data=new BigDecimal(nf.format(data));
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
            data=new BigDecimal(nf.format(data));
        }
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl, String columnName) throws Exception {
        this.data=new BigDecimal(o);
        changeRange();
    }

    @Override
    public SqlType addOne() throws Exception {
        double d=data.doubleValue()+1;
        return new SqlNumeric(d,this.scale,this.precision);
    }

    public void setData(BigDecimal data) {
        this.data = data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public BigDecimal getData() {
        return data;
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
        this.data=this.data.add(((SqlNumeric)a).data);
        changeRange();
        return this;
    }

    @Override
    public void ave(int num) throws Exception {
        this.data=this.data.divide(new BigDecimal(num));
        changeRange();
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlNumeric)o).data);
    }
}
