package com.ucd.oursql.sql.test;//package test;
//
//import execution.database.CreateDatabaseStatement;
//import system.User;
//import system.UserAccessedDatabases;
//import table.BTree.CglibBean;
//import table.Database;
//import table.Table;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserTest {
//    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
//        User user=new User(0,"root");
//        UserAccessedDatabases usa=user.getUserAccessedDatabases();
//        usa.setUser(user);
//        Database t=create();
//        System.out.println("------------------");
//        insertTable(t);
//        System.out.println("------------------");
//        boolean insertAtable=usa.insertDatabase(t);
//        Table databaseList=usa.getUserAccessedDatabase();
//        databaseList.printTable();
//        System.out.println("------------------");
//        List<CglibBean> list=databaseList.getTree().getDatas();
//        CglibBean c=list.get(0);
//        Database db= (Database) c.getValue("database");
//        printDatabase(db);
//        System.out.println("------------------");
//
//    }
//
//    public  static List createDatabaseStatment() {
//        List list=new ArrayList();
//        list.add("CREATE");
//        list.add("DATABASE");
//        list.add("testDatabase");
//        return list;
//    }
//
//    public static Database create() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        List list=createDatabaseStatment();
//        CreateDatabaseStatement c=new CreateDatabaseStatement(list);
//        Database t=c.createDatabaseImpl(1);
//        return t;
//    }
//
//    public static void insertTable(Database db) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Table t=TestCreate.create();
//        db.insertTable(t);
//    }
//    public static void printDatabase(Database db){
//        Table t=db.getDatabase();
//        t.printTable();
//    }
//
//
//}
