package com.ucd.oursql.sql.test;//package test;
//
//import table.Table;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static parsing.SqlParserConstants.*;
//
//public class TestCreate {
//    public static void main(String[] args) throws ClassNotFoundException {
//        Table t=create();
//        System.out.println("----------------");
//    }
//
//    public  static List createStatment() {
//        List list=new ArrayList();
//        List variant=new ArrayList();
//        List v1=new ArrayList();
//        List v2=new ArrayList();
//        List v3=new ArrayList();
//        v1.add("id");
//        v1.add(INT);
//        v2.add("name");
//        v2.add(STRING);
//        v2.add("3");
//        v3.add("addr");
//        v3.add(STRING);
//        v3.add("5");
//        variant.add(v1);
//        variant.add(v2);
//        variant.add(v3);
//        list.add("testTable");
//        list.add(variant);
//        return list;
//    }
//
//    public static Table create() throws ClassNotFoundException {
//        List list=createStatment();
//        CreateTableStatement c=new CreateTableStatement(list);
//        Table t=c.createImpl();
//        return t;
//    }
//
//}
