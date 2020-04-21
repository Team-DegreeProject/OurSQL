package com.ucd.oursql.sql.test;//package test;
//
//import execution.table.DeleteStatement;
//import table.BTree.BPlusTree;
//import table.BTree.BPlusTreeTool;
//import table.BTree.CglibBean;
//import table.type.SqlConstant;
//
//import java.util.HashMap;
//
//public class TestDelete implements SqlConstant {
//    public static void main(String[] args) throws ClassNotFoundException {
//        testWhereAndOr();
//    }
//    public static void testcompare() throws ClassNotFoundException {
//        BPlusTree b=createTree();
////        List list=DeleteStatement.compare(b,"address",EQ,"300");
////        BPlusTreeTool.printList(list,"address");
////        List list=DeleteStatement.compare(b,"id",EQ,300);
////        BPlusTreeTool.printList(list,"id");
////        List list=DeleteStatement.compare(b,"id",LESS_THAN_OPERATOR,300);
////        BPlusTreeTool.printList(list,"id");
//        BPlusTree list= DeleteStatement.compare(b,"address",LQ,"300","id");
//        BPlusTreeTool.printBPlusTree(list,"address");
//    }
//
//    public static void testWhereAndOr() throws ClassNotFoundException {
//        BPlusTree b=createTree();
//        BPlusTree b1= DeleteStatement.compare(b,"id",LQ,400,"id");
//        BPlusTree b2= DeleteStatement.compare(b,"id",RQ,100,"id");
//        BPlusTree b3= DeleteStatement.whereOr(b1,b2);
//        BPlusTreeTool.printBPlusTree(b3,"id");
//
//    }
//
//    public  static BPlusTree createTree() throws ClassNotFoundException {
//        HashMap propertyMap = new HashMap();
//        propertyMap.put("id", Class.forName("java.lang.Integer"));
//        propertyMap.put("name", Class.forName("java.lang.String"));
//        propertyMap.put("address", Class.forName("java.lang.String"));
//        BPlusTree<CglibBean, Integer> b = new BPlusTree<>(4);
//
//        for (int i = 500; i >=0; i--) {
//            CglibBean bean = new CglibBean(propertyMap);
//            bean.setValue("id", i);
//            bean.setValue("name", Integer.toString(i+1));
//            bean.setValue("address", Integer.toString(i+2));
//            b.insert(bean,(Integer) bean.getValue("id"));
//        }
//        return b;
//    }
//
//}
