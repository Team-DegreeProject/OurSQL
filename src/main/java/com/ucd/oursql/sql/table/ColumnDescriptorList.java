package com.ucd.oursql.sql.table;

import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.table.type.SqlConstantImpl.sqlMap;

public class ColumnDescriptorList extends ArrayList<ColumnDescriptor> {

    public ColumnDescriptor elementAt(int n) {
        return get(n);
    }

    /**
     * 根据表id和列id获取列相关描述
     */
    public ColumnDescriptor getColumnDescriptor(int columnID) {
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if ((columnID == columnDescriptor.getPosition())) {
                returnValue = columnDescriptor;
                break;
            }
        }
        return returnValue;
    }

    public ColumnDescriptor getColumnDescriptor(String columnName) {
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if (columnName.equals(columnDescriptor.getColumnName()) ) {
                returnValue = columnDescriptor;
                break;
            }
        }
        return returnValue;
    }

    public void addColumns(List<ColumnDescriptor> list){
        for(int i=0;i<list.size();i++){
            this.add(list.get(i));
        }
    }

    public void dropColumn(String columnName){
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if (columnName.equals(columnDescriptor.getColumnName()) ) {
                returnValue = columnDescriptor;
                break;
            }
        }
        this.remove(returnValue);
    }


    public boolean checkHavePrimaryKey(List<Token> columnNames) {
//        this.printColumnDescriptorList();
//        for(int i=0;i<columnNames.size();i++){
//            System.out.print(columnNames.get(i).image+" ");
//        }
//        System.out.println();
        for (ColumnDescriptor columnDescriptor : this) {
            boolean in=false;
            for(int i=0;i<columnNames.size();i++){
                String columnName=columnNames.get(i).image;
                if (columnName.equals(columnDescriptor.getColumnName()) ) {
                    in=true;
                }
            }
            if(!in){
                return false;
            }
        }
        return true;
    }



    public boolean checkNotNull(List<Token> columnNames,List<List<Token>> values){
        for (ColumnDescriptor columnDescriptor : this) {
            DataTypeDescriptor dataTypeDescriptor=columnDescriptor.getType();
            if(columnDescriptor.getColumnName().compareTo("primary_key")!=0) {
                if (!dataTypeDescriptor.isNullable()) {
//                    System.out.println("===="+columnDescriptor.getColumnName());
                    boolean b = false;
                    for (int i = 0; i < columnNames.size(); i++) {
                        String name = columnNames.get(i).image;
                        if (name.equals(columnDescriptor.getColumnName())) {
                            if (values.get(i).get(0).image.equals("null")) {
                                return false;
                            }
                            b = true;
                            break;
                        }
                    }
                    if(b==false){
                        return false;
                    }
                }
            }
        }
        return true;
    }



    public ColumnDescriptorList getAutoIncrementList(){
        ColumnDescriptorList columnDescriptors=new ColumnDescriptorList();
        for (ColumnDescriptor columnDescriptor : this) {
            if(columnDescriptor.isAutoincInc()){
                columnDescriptors.add(columnDescriptor);
            }
        }
        return columnDescriptors;
    }



    public  ColumnDescriptorList getUniqueList(){
        ColumnDescriptorList columnDescriptors=new ColumnDescriptorList();
        for (ColumnDescriptor columnDescriptor : this) {
            if(columnDescriptor.isUnique()){
                columnDescriptors.add(columnDescriptor);
            }
        }
        return columnDescriptors;
    }


    public void printColumnDescriptorList(){
        for (ColumnDescriptor columnDescriptor : this) {
//            DataTypeDescriptor dataTypeDescriptor=columnDescriptor.getType();
//            System.out.println(columnDescriptor.getColumnName()+"-->"+sqlMap.get(dataTypeDescriptor.getTypeId()));
            columnDescriptor.printColumnDescriptor();
        }
    }

    public ColumnDescriptorList getNewColumnDescriptorList(){
        ColumnDescriptorList newl=new ColumnDescriptorList();
        for (ColumnDescriptor columnDescriptor : this) {
            newl.add(columnDescriptor.getNewColumnDescripter());
        }
        return newl;
    }

}
