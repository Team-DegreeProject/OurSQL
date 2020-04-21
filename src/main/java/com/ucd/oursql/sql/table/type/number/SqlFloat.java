package com.ucd.oursql.sql.table.type.number;


import com.ucd.oursql.sql.table.type.SqlType;

//float型的数据存储大小为8个字节，可精确到小数点后第15位数字。
//这种数据类型的数据存储范围为从-1.79E+308～-2.23E-308，0和2.23E+308～1.79E+308。
//FLOAT(size,d) 带有浮动小数点的小数字。在括号中规定最大位数。在 d 参数中规定小数点右侧的最大位数。
public class SqlFloat implements  SqlType {
    private Float data =new Float(0);

    public SqlFloat(){}

    public SqlFloat(float f){
        this.data=f;
    }

    public void setData(float data) {
        this.data = data;
    }

    public float getData() {
        return data;
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
        throw new Exception("Float do not need scale.");
    }

    @Override
    public void setPrecision(int i) throws Exception {
        throw new Exception("Float do not need precision.");
    }

    @Override
    public void updateValue() throws Exception {
    }

    @Override
    public void setValue(String o) {
        setData(Float.parseFloat(o));
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlFloat)o).data);
    }
}
