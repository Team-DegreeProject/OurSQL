package com.ucd.oursql.sql.system;//package system;
//
//import table.BTree.BPlusTree;
//import table.BTree.BPlusTreeTool;
//import table.ColumnDescriptorList;
//import table.Table;
//import table.TableDescriptor;
//import table.column.ColumnDescriptor;
//import table.column.DataTypeDescriptor;
//import table.type.text.SqlVarChar;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static table.TableSchema.SYSTEM_TABLE_TYPE;
//
//public class UserAccessedTable {
//    private User user;
//    private Table userAccessedTable;
//    int length=0;
//    public UserAccessedTable(User user) throws ClassNotFoundException {
//        TableDescriptor td=tableDescriptor();
//        userAccessedTable =new Table(td);
//        length=userAccessedTable.size();
//        this.user=user;
//    }
//
//    public UserAccessedTable() throws ClassNotFoundException {
//        TableDescriptor td=tableDescriptor();
//        userAccessedTable =new Table(td);
//        length=userAccessedTable.size();
//    }
//
//    public boolean insertTable(Table t) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        TableDescriptor td=t.getTableDescriptor();
//        SqlVarChar userName=new SqlVarChar(user.getUserName());
//        int id=length+1;
//        length++;
//        List values=new ArrayList();
//        values.add(id);
//        values.add(userName);
//        values.add(td);
//        String[] attributes=userAccessedTable.getTableDescriptor().getColumnNamesArray();
//        return userAccessedTable.insertRows(attributes,values);
//    }
//
//
//    public TableDescriptor tableDescriptor(){
//        TableDescriptor table=null;
//        String tableName="UserPermissionScope";
//        ColumnDescriptorList columns=new ColumnDescriptorList();
//        DataTypeDescriptor id= new DataTypeDescriptor(100,false);//int
//        ColumnDescriptor column=new ColumnDescriptor("id",0,id);
//        columns.add(column);
//        DataTypeDescriptor user= new DataTypeDescriptor(102,false);//int
//        column=new ColumnDescriptor("user",1,user);
//        columns.add(column);
//        DataTypeDescriptor t= new DataTypeDescriptor(104,false);//int
//        column=new ColumnDescriptor("table",2,t);
//        columns.add(column);
//        String[] pk={"id"};
//        table=new TableDescriptor(tableName,SYSTEM_TABLE_TYPE,columns,pk);
//        table.setTableInColumnDescriptor(table);
//        table.printColumnName();
//        return table;
//    }
//
//    public void returnUserAccessedTableNames(){
//        List names=new ArrayList();
//        BPlusTree tree=userAccessedTable.getTree();
//        BPlusTreeTool.printBPlusTree(tree,"table");
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void printUserAccessedTable(){
//        BPlusTree b=userAccessedTable.getTree();
//        BPlusTreeTool.printBPlusTree(b);
//    }
//
//    public Table getUserAccessedTable(){ return userAccessedTable; }
//
//    public void setUserAccessedTable(Table userAccessedTable){ this.userAccessedTable=userAccessedTable; }
//}
