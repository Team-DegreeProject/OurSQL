package com.ucd.oursql.sql.system;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.Database;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.number.SqlInt;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;
import static com.ucd.oursql.sql.table.TableSchema.SYSTEM_TABLE_TYPE;
import static com.ucd.oursql.sql.table.type.SqlConstantImpl.*;

public class UserAccessedDatabases {
    private User user;
    private Table userAccessedDatabase;
//    private TableDescriptor tableDescriptor;
//    int length=0;
    public UserAccessedDatabases(User user) throws ClassNotFoundException {
//        Table userAccessedDatabase=databaseList();
        //        length=userAccessedDatabase.size();
        this.user=user;
    }

    public UserAccessedDatabases() throws ClassNotFoundException {
//        Table userAccessedDatabase=databaseList();
//        length=userAccessedDatabase.size();
    }

    public UserAccessedDatabases(User user,Table userAccessedDatabase) throws ClassNotFoundException {
        this.user=user;
        this.userAccessedDatabase=userAccessedDatabase;
    }

    public Table databaseList() throws ClassNotFoundException {
        descriptorLoader dl=new descriptorLoader();
        Table temp=dl.loadFromFile("UserPermissionDatabaseScope", "root");
        if(temp!=null){
            System.out.println("load UPDS from xml");
            userAccessedDatabase=temp;
            return temp;
        }
        TableDescriptor tableDescriptor =null;
        String tableName="UserPermissionDatabaseScope";
        ColumnDescriptorList primaryKey=new ColumnDescriptorList();
        ColumnDescriptorList columns=new ColumnDescriptorList();
//        DataTypeDescriptor id= new DataTypeDescriptor(INT,false);
//        ColumnDescriptor column=new ColumnDescriptor("id",1,id);
//        column.setUnique(true);
//        primaryKey.add(column);
//        columns.add(column);
        DataTypeDescriptor user= new DataTypeDescriptor(VARCHAR,false);
        user.setPrimaryKey(true);
        ColumnDescriptor column=new ColumnDescriptor("user",2,user);
        columns.add(column);
        primaryKey.add(column);
//        DataTypeDescriptor t= new DataTypeDescriptor(DATABASE,false);
//        column=new ColumnDescriptor("database",3,t);
//        columns.add(column);
        DataTypeDescriptor tn= new DataTypeDescriptor(VARCHAR,false);
        tn.setPrimaryKey(true);
        column=new ColumnDescriptor("databasename",1,tn);
//        column.setUnique(true);
        primaryKey.add(column);
        columns.add(column);
        DataTypeDescriptor tp=new DataTypeDescriptor(PRIMARY_KEY,false);
        column=new ColumnDescriptor("primary_key",0,tp);
        column.setUnique(true);
        columns.add(column);
        tableDescriptor =new TableDescriptor(tableName,SYSTEM_TABLE_TYPE,columns,primaryKey);
        tableDescriptor .setTableInColumnDescriptor(tableDescriptor);
        tableDescriptor .printColumnName();
        userAccessedDatabase=new Table(tableDescriptor,true);
        descriptorSaver ds=new descriptorSaver(userAccessedDatabase.getTableDescriptor(),userAccessedDatabase.getPropertyMap(),userAccessedDatabase.getTree(),"root");
        ds.saveAll();
        return userAccessedDatabase;
    }

    public boolean insertDatabase(Database database) throws Exception {
        SqlVarChar n=new SqlVarChar(database.getDatabaseName());
        SqlVarChar u=new SqlVarChar(user.getUserName());
//        int id=length;
//        length++;
        PrimaryKey pk=new PrimaryKey();
//        SqlInt sqlid=new SqlInt(id);
        pk.addPrimaryKey("databasename",n);
        pk.addPrimaryKey("user",u);
        List values=new ArrayList();
        values.add(pk);
//        values.add(sqlid);
        values.add(database.getDatabaseName());
        values.add(user.getUserName());
//        values.add(database);
//        values.add(database.getDatabaseName());
        boolean instance = userAccessedDatabase.insertARow(values,0);

//        descriptorSaver ds=new descriptorSaver(userAccessedDatabase.getTableDescriptor(),userAccessedDatabase.getPropertyMap(),userAccessedDatabase.getTree());
//        ds.saveAll();
        return instance;
    }

    public void returnUserAccessedDatabaseNames(){
        List names=new ArrayList();
        BPlusTree tree=userAccessedDatabase.getTree();
        BPlusTreeTool.printBPlusTree(tree,"databasename");
    }

    public String printUserAccessedDatabase(){
//        BPlusTree b=userAccessedDatabase.getTree();
//        BPlusTreeTool.printBPlusTree(b);
        String str=userAccessedDatabase.printTable(null);
        return str;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

//    public int getLength() {
//        return length;
//    }
//
//    public void setLength(int length) {
//        this.length = length;
//    }

    public void setUserAccessedDatabase(Table userAccessedDatabase) {
        this.userAccessedDatabase = userAccessedDatabase;
    }

    public Table getUserAccessedDatabase() {
        return userAccessedDatabase;
    }
}
