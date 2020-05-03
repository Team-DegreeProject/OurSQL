package com.ucd.oursql.sql.test;


import com.ucd.oursql.sql.table.type.SqlType;

public class TestDataType {
    public static void main(String[] args){
        try {
            Class c=Class.forName("table.type.number.SqlInt");
            SqlType o=(SqlType)c.newInstance();
            o.setValue("1",null,null,null);
            System.out.println(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
