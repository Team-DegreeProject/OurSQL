package com.ucd.oursql.sql.table.column;


import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.type.SqlType;

public class ColumnDescriptor{

    private TableDescriptor table;
    private String columnName;
    private int columnPosition;
    private DataTypeDescriptor columnType=null;
    private long autoincStart=0;
    private boolean autoincInc=false;
    private long autoincValue=0;
    private SqlType columnDefaultValue=null;
    private String comment=null;
    private boolean unique=false;


    public ColumnDescriptor(TableDescriptor table,String columnName,int columnPosition,DataTypeDescriptor columnType,long autoincStart,boolean autoincInc,long autoincValue,SqlType columnDefaultValue,String comment,boolean unique){
        this.table=table;
        this.columnName=columnName;
        this.columnPosition=columnPosition;
        this.columnType=columnType;
        this.autoincStart=autoincStart;
        this.autoincInc=autoincInc;
        this.autoincValue=autoincValue;
        this.columnDefaultValue=columnDefaultValue;
        this.comment=comment;
        this.unique=unique;
    }

    /**
     * ColumnDescriptor的构造器
     *@param columnDefaultInfo 列的默认信息
     * @param columnName        列的名字
     * @param columnPosition    列的位置
     * @param columnType        列的DataTypeDescriptor
     * @param table             列所在的表描述
     * @param autoincStart      默认自增长开始值
     * @param autoincInc        是否是自增长列
     */
    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType,
                            TableDescriptor table, long autoincStart, boolean autoincInc, SqlType columnDefaultInfo) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincStart = autoincStart;
        this.autoincValue = autoincStart;
        this.autoincInc = autoincInc;
        this.columnDefaultValue=columnDefaultInfo;
    }

    public  ColumnDescriptor(int columnPosition){
        this.columnPosition=columnPosition;
    }

    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType,
                            TableDescriptor table, long autoincStart, boolean autoincInc) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincStart = autoincStart;
        this.autoincValue = autoincStart;
        this.autoincInc = autoincInc;
    }

    public ColumnDescriptor(String columnName, int columnPosition,
                             DataTypeDescriptor columnType, TableDescriptor table) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincValue = autoincStart;
    }

//    long autoinc_create_or_modify_Start_Increment = -1;

    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
    }



    public DataTypeDescriptor getType() {
        return columnType;
    }


    /**
     * 自动增长的开始值
     */
    public long getAutoincStart() {
        return autoincStart;
    }

    public void setAutoincInc(boolean autoincInc) {
        this.autoincInc = autoincInc;
    }

    /**
     * 获取增长列当前的值
     */
    public long getAutoincValue() {
        return autoincValue;
    }

    public void setPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public boolean isAutoincInc() {
        return autoincInc;
    }

    public String getColumnName() {
        return columnName;
    }

    /**
     * 获取列的位置
     */
    public int getPosition() {
        return columnPosition;
    }

//    public long getAutoinc_create_or_modify_Start_Increment() {
//        return autoinc_create_or_modify_Start_Increment;
//    }

    /**
     * 获取列默认信息
     */
    public Object getDefaultInfo() {
        return columnDefaultValue;
    }


    public void setTableDescriptor(TableDescriptor tableDescriptor) {
        this.table = tableDescriptor;
    }

    public TableDescriptor getTableDescriptor(){ return table; }

    public DataTypeDescriptor getColumnType() {
        return columnType;
    }

    public SqlType getColumnDefaultValue() {
        return columnDefaultValue;
    }

    public int getColumnPosition() {
        return columnPosition;
    }


    public void setAutoincStart(long autoincStart) {
        this.autoincStart = autoincStart;
    }

    public void setAutoincValue(long autoincValue) {
        this.autoincValue = autoincValue;
    }

    public void setColumnDefaultValue(SqlType columnDefaultValue) {
        this.columnDefaultValue = columnDefaultValue;
    }

    public TableDescriptor getTable() {
        return table;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public void setColumnType(DataTypeDescriptor columnType) {
        this.columnType = columnType;
    }

    public void setTable(TableDescriptor table) {
        this.table = table;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
        if(unique==true){
            this.columnType.setNullable(false);
        }
    }

    public void printColumnDescriptor(){
        System.out.println(
                "ColumnDescriptor============"+
                        "ColumnName:"+columnName+","+
                        "ColumnPosition:"+columnPosition+","+
                        "autoIncrement:"+autoincInc+","+
                        "ColumnDefaultValue:"+columnDefaultValue+","+
                        "Comment:"+comment+","+
                        "unique"+unique
        );
        if (columnType == null) {
            System.out.println("DatatypeDescriptor==============null");
        }else{
            columnType.printDataTypeDescriptor();
        }
    }

    public ColumnDescriptor getNewColumnDescripter(){
        ColumnDescriptor c=new ColumnDescriptor(table,columnName,columnPosition,columnType.getNewDataTypeDescripter(),autoincStart,autoincInc,autoincValue,columnDefaultValue,comment,unique);
        return c;
    }
}
