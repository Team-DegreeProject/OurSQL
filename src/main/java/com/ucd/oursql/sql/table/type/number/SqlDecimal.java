package com.ucd.oursql.sql.table.type.number;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.type.SqlType;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;

public class SqlDecimal implements SqlType {
    private BigDecimal data=new BigDecimal(0);
    private int scale=-1;
    private int precision=-1;

    public SqlDecimal(){}

    public SqlDecimal(BigDecimal data){

        this.data=data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlDecimal(BigDecimal data,int scale,int precision){
        this.data=data;
        this.scale=scale;
        this.precision=precision;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlDecimal(Double d,int scale,int precision){
        this.data=new BigDecimal(d);
        this.scale=scale;
        this.precision=precision;
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


    public void setData(BigDecimal data) {
        this.data = data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrecision(int precision) throws Exception {
        this.precision = precision;
//        changeRange();
    }

    @Override
    public void updateValue() throws Exception {
        changeRange();
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) throws Exception {
        this.scale = scale;
//        changeRange();
    }

    public BigDecimal getData(){
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public SqlType addOne() throws Exception {
        return new SqlDecimal((this.data.doubleValue()+1),scale,precision);
    }




    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl) {
        setData(new BigDecimal(o));
    }

    @Override
    public int compareTo(Object o) {
        return data.compareTo(((SqlDecimal)o).data);
    }
}
