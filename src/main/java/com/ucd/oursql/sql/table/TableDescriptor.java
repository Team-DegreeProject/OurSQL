package com.ucd.oursql.sql.table;

import com.ucd.oursql.sql.table.column.ColumnDescriptor;

import java.util.ArrayList;
import java.util.List;

public class TableDescriptor implements TableSchema {

    //表所对应的conglom的标识
//    private volatile long tableConglomNumber = -1;

    private char lockGranularity=ROW_LOCK_GRANULARITY;
//    private boolean onCommitDeleteRows; //true means on commit delete rows, false means on commit preserve rows of temporary table.
//    private boolean onRollbackDeleteRows; //true means on rollback delete rows. This is the only value supported.
//    private boolean indexStatsUpToDate = true;
//    private String indexStatsUpdateReason;
    private String tableName;
    //    private int tableType;
    private int schema;
    private ColumnDescriptorList primaryKey;
    ColumnDescriptorList columnDescriptorList;

    public  TableDescriptor(String tableName,int schema,ColumnDescriptorList columnDescriptorList){
        this.tableName=tableName;
        this.schema=schema;
        this.columnDescriptorList=columnDescriptorList;
    }

    public TableDescriptor(String tableName, char lockGranularity,int schema,ColumnDescriptorList primaryKey) {
        this.tableName = tableName;
//        this.tableType = tableType;
        this.lockGranularity = lockGranularity;
        this.columnDescriptorList = new ColumnDescriptorList();
        this.schema=schema;
        this.primaryKey=primaryKey;
    }


    public TableDescriptor( String tableName,  char lockGranularity,int schema,ColumnDescriptorList columnDescriptorList,ColumnDescriptorList primaryKey) {
//        this.tableName = tableName;
        this.lockGranularity = lockGranularity;
        this.columnDescriptorList = columnDescriptorList;
        this.schema=schema;
        this.primaryKey=primaryKey;
    }

    public TableDescriptor( String tableName,int schema,ColumnDescriptorList columnDescriptorList,ColumnDescriptorList primaryKey) {
        this.tableName = tableName;
//        this.tableType = tableType;
        this.columnDescriptorList = columnDescriptorList;
        this.schema=schema;
        this.primaryKey=primaryKey;
    }


    public ColumnDescriptorList getColumnDescriptorList() {
        return columnDescriptorList;
    }

    public char getLockGranularity() {
        return lockGranularity;
    }

    public String getName() {
        return tableName;
    }

//    public int getTableType() {
//        return tableType;
//    }



    public String[] getColumnNamesArray() {
        int size = getNumberOfColumns();
        String[] s = new String[size];
        for (int i = 0; i < size; i++) {
            s[i] = getColumnDescriptor(i).getColumnName();
        }

        return s;
    }

    public List getColumnNamesList() {
        int size = getNumberOfColumns();
        List s=new ArrayList();
        for (int i = 0; i < size; i++) {
            s.add(columnDescriptorList.elementAt(i).getColumnName()) ;
        }

        return s;
    }

    public int[] getColumnIdsArray() {
        int size = getNumberOfColumns();
        int[] s = new int[size];
        for (int i = 0; i < size; i++) {
            s[i] = getColumnDescriptor(i).getType().getTypeId();
        }
        return s;
    }


    //获取表中列的数目
    public int getNumberOfColumns() {
        return getColumnDescriptorList().size();
    }

    public ColumnDescriptor getColumnDescriptor(int columnNumber) {
        return columnDescriptorList.getColumnDescriptor(columnNumber);
    }

    public ColumnDescriptor getColumnDescriptor(String columnName) {
        return columnDescriptorList.getColumnDescriptor(columnName);
    }

    public String getSchemaName() { return schemaName[schema];}


    public int getMaxColumnID() {
        int maxColumnID = 1;
        for (ColumnDescriptor cd : columnDescriptorList) {
            maxColumnID = Math.max(maxColumnID, cd.getPosition());
        }
        return maxColumnID;
    }


    public void setTableInColumnDescriptor(TableDescriptor t){
        List l=getColumnDescriptorList();
        for(int i=0;i<l.size();i++){
            ColumnDescriptor temp= (ColumnDescriptor) l.get(i);
            temp.setTableDescriptor(t);
        }
    }

    public String printColumnName(){
        String str="";
        List columns=this.getColumnNamesList();
        for(int i=0;i<columns.size();i++){
            System.out.print(columns.get(i));
            str=str+columns.get(i);
            if(i!=columns.size()-1){
                System.out.print(",");
                str=str+",";
            }else{
                System.out.println(";");
                str=str+";\n";
            }
        }
        return str;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPrimaryKey(ColumnDescriptorList primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ColumnDescriptorList getPrimaryKey() {
        return primaryKey;
    }

    public void setColumnDescriptorList(ColumnDescriptorList columnDescriptorList) {
        this.columnDescriptorList = columnDescriptorList;
    }

    public void updatePriamryKey(){
        primaryKey=new ColumnDescriptorList();
        for(int i=0;i<columnDescriptorList.size();i++){
            ColumnDescriptor cd=columnDescriptorList.get(i);
            if(cd.getType().isPrimaryKey()){
                primaryKey.add(cd);
            }
        }
    }

    public void printTableDescriptor(){
        System.out.println("TableDescriptor:");
        for(int i=0;i<primaryKey.size();i++){
            ColumnDescriptor c=primaryKey.elementAt(i);
            System.out.print(c.getColumnName()+",");
        }
        System.out.println(' ');
        for(int i=0;i<columnDescriptorList.size();i++){
            ColumnDescriptor c=columnDescriptorList.elementAt(i);
            c.printColumnDescriptor();
        }
    }

    public int getSchema() {
        return schema;
    }
}

