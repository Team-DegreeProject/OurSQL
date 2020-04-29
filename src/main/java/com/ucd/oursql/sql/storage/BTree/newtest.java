package com.ucd.oursql.sql.storage.BTree;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;

import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class newtest {
    public static void main(String[] args) throws IOException {
        HashMap<String,String> propertyMap = new HashMap<>();
        propertyMap.put("one","one");
        propertyMap.put("two","two");

        descriptorSaver test = new descriptorSaver(new TableDescriptor("testTable",0,null),propertyMap,null);
        test.hashmapToXML();

        descriptorLoader testload = new descriptorLoader();
        HashMap<String,String> testMap = testload.loadPropertyFromFile("testTable");
        System.out.println(testMap.get("one"));
        System.out.println(testMap.get("two"));

        //测试tableDescriptor
        DataTypeDescriptor dataTypedes = new DataTypeDescriptor(0,false,true);
        TableDescriptor tableDes = null;
        ColumnDescriptor columnDescriptor = new ColumnDescriptor("Column1",0,dataTypedes,tableDes);
        ArrayList<ColumnDescriptor> columnList = new ArrayList<ColumnDescriptor>();
        columnList.add(columnDescriptor);
        ColumnDescriptorList columnDescriptorList = new ColumnDescriptorList();
        columnDescriptorList.add(columnDescriptor);

        DataTypeDescriptor dataTypedes2 = new DataTypeDescriptor(0,false,true);
        TableDescriptor tableDes2 = null;
        ColumnDescriptor columnDescriptor2 = new ColumnDescriptor("Column2",1,dataTypedes2,tableDes2);
        columnDescriptorList.add(columnDescriptor2);

        tableDes = new TableDescriptor("testTable", 'a',0,columnDescriptorList,columnDescriptorList);
        tableDes.setTableName("testTable");
        descriptorSaver test2 = new descriptorSaver(tableDes,propertyMap,null);
        test2.descriptorToXML();


        TableDescriptor loadDes = testload.loadDescriptorFromFile("testTable");
        System.out.println(loadDes.getTableName());
    }
}
