package com.ucd.oursql.sql.storage.Storage;

import com.ucd.oursql.sql.execution.DMLTool;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.SqlType;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class descriptorLoader {

    public HashMap loadPropertyFromFile(String tn, String userName){
        HashMap propertyMap = new HashMap();
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            String filepath = "data/"+userName+"/" + tn + "/" + tn + "PropertyMap.xml";
            Document document1 = saxBuilder.build(new File(filepath));
            Element rootElement = document1.getRootElement();
            List<Element> elementList = rootElement.getChildren();
            for(Element eachElement : elementList){
                propertyMap.put(eachElement.getName(),eachElement.getText());
            }
        }catch (Exception e){
//            e.printStackTrace();
            System.out.println("is property map null !!!!!!!!!!!!!!!!!!");
        }
        return propertyMap;
    }


    public TableDescriptor loadDescriptorFromFile(String tn,String userName) {
        SAXBuilder saxBuilder = new SAXBuilder();
        TableDescriptor tableDescriptor = null;
        HashMap propertyMap = null;
        String filepath = "data/"+userName+"/" + tn + "/" + tn + "Descriptor.xml";
        File localfile = new File(filepath);
        try {
            //首先读取propertyMap
            propertyMap = loadPropertyFromFile(tn,userName);



            Document document1 = saxBuilder.build(localfile);
            Element rootElement = document1.getRootElement();
//            List<Element> elementList = rootElement.getChildren();

            //先得到最外层的节点们
            String tableName = tn;
            int scheme = Integer.parseInt(rootElement.getChild("schema").getText());
            char lockGranularity = rootElement.getChild("lockGranularity").getText().toCharArray()[0];
            //创建一个tabledescriptor
            ColumnDescriptorList columnDescriptorList = new ColumnDescriptorList();
            ColumnDescriptorList primaryKeyList = new ColumnDescriptorList();
            tableDescriptor = new TableDescriptor(tableName,lockGranularity,scheme,columnDescriptorList,primaryKeyList);
            tableDescriptor.setTableName(tableName);
            //获得所有的columnDescriptor
            Element columnDescriptorListElement = rootElement.getChild("columnDescriptorList");

            List<Element> columnDescriptors = columnDescriptorListElement.getChildren("columnDescriptor");
            for (Element eachColunm : columnDescriptors) {
                //除了dataType全部的部分

                String columnName = eachColunm.getChildText("columnName");
                if (columnName != null){
                    int columnPosition = Integer.valueOf(eachColunm.getChildText("columnPosition"));
                    long autoincStart = Long.valueOf(eachColunm.getChildText("autoincStart"));
                    boolean autoincInc = Boolean.valueOf(eachColunm.getChildText("autoincInc"));
                    long autoincValue = Long.valueOf(eachColunm.getChildText("autoincValue"));
                    String comment = eachColunm.getChildText("comment");
                    boolean unique = Boolean.parseBoolean(eachColunm.getChildText("Unique"));

                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                SqlType columnDefaultValue = DMLTool.convertToValue(columnName,eachColunm.getChildText("columnDefaultValue"),propertyMap);

                    //DateTypeDescriptor
                    Element dataTypeDescriptor = eachColunm.getChild("DataTypeDescriptor");
                    int typeId = Integer.valueOf(dataTypeDescriptor.getChildText("typeId"));
                    int precision = Integer.valueOf(dataTypeDescriptor.getChildText("precision"));
                    int scale = Integer.valueOf(dataTypeDescriptor.getChildText("scale"));
                    boolean isNullable = Boolean.parseBoolean(dataTypeDescriptor.getChildText("isNullable"));
                    boolean primaryKey = Boolean.parseBoolean(dataTypeDescriptor.getChildText("isPrimaryKey"));
                    DataTypeDescriptor dataTypeDescriptor1 = new DataTypeDescriptor(typeId,precision,scale,isNullable,primaryKey);
//                System.out.println("THE VALUE OF DATATYPE is:"+typeId);
//                System.out.println("THE VALUE OF PRIMARYKEY is:"+primaryKey);
                    ColumnDescriptor columnDescriptor = new ColumnDescriptor(tableDescriptor,columnName,columnPosition,dataTypeDescriptor1,autoincStart,autoincInc,autoincValue,null,comment,unique);
                    columnDescriptorList.add(columnDescriptor);
                }



            }
            Element primaryKeyListElement = rootElement.getChild("primaryKey");
            List<Element> primaryKeyElements = primaryKeyListElement.getChildren("primaryColumnName");
            for(Element eachPrimaryKey : primaryKeyElements){
                String primaryKeyName = eachPrimaryKey.getText();
                ColumnDescriptor primaryKeyDescriptor = columnDescriptorList.getColumnDescriptor(primaryKeyName);
                primaryKeyList.add(primaryKeyDescriptor);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tableDescriptor;
    }

    public Table loadFromFile(String tableName,String userName){
        try{
            TableDescriptor td = loadDescriptorFromFile(tableName,userName);
            HashMap propertyMap = loadPropertyFromFile(tableName, userName);
            TreeLoader tl = new TreeLoader();
            BPlusTree fileTree = tl.loadFromFile(tableName,propertyMap,td.getColumnDescriptorList(),userName);
            Table resultTable = new Table(td,fileTree,propertyMap);
            System.out.println("is table descriptor null:"+td==null);

            System.out.println("is tree null:"+fileTree==null);
            return resultTable;
        }
        catch (Exception e){
//            e.printStackTrace();
//            return null;
        }
        return null;
    }
}
