package com.ucd.oursql.sql.storage.Storage;


import org.apache.tomcat.util.buf.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.ColumnDescriptorList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class descriptorSaver {
    TableDescriptor tabledescriptor;
    HashMap propertyMap;
    BPlusTree btree;

    public descriptorSaver(TableDescriptor desToBeSaved,HashMap map, BPlusTree tree) {
        tabledescriptor = desToBeSaved;
        propertyMap = map;
        btree = tree;
    }


    public void saveAll(){
        try{
            String tableName = tabledescriptor.getTableName();
            hashmapToXML();
            descriptorToXML();
            List<String> ColumnNameList = tabledescriptor.getColumnNamesList();
            TreeSaver ts = new TreeSaver();
            ts.SaveAsXML(btree,tableName,ColumnNameList);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void hashmapToXML(){
//        try {
//            String tableName = tabledescriptor.getTableName();
//            Element table = new Element("table");
//            Document document = new Document(table);
//            Iterator entries = propertyMap.entrySet().iterator();
//
//            while (entries.hasNext()) {
//
//                Map.Entry entry = (Map.Entry) entries.next();
////                String key = String.valueOf(entry.getKey());
//                String key = (String)entry.getKey();
//                String value = (String) entry.getValue();
//                Element e = new Element(key);
//                e.setText(value);
//                table.addContent(e);
//            }
//            Format format=Format.getCompactFormat();
//            format.setIndent("");
//            //?????????????
//            format.setEncoding("GBK");
//            //4.????XMLOutputter?????
//            XMLOutputter outputter=new XMLOutputter(format);
//
//            File folderfile = new File("data/"+tableName+"/"+tableName+"PropertyMap.xml");
//            File fileParent = folderfile.getParentFile();//判断是否存在
//            if (!fileParent.exists()) {
//                fileParent.mkdirs();
//            }
//
//            outputter.output(document, new FileOutputStream(new File("data/"+tableName+"/"+tableName+"PropertyMap.xml")));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            String tableName = tabledescriptor.getTableName();
            org.dom4j.Document doc= DocumentHelper.createDocument();
            org.dom4j.Element table=doc.addElement("table");

            Iterator entries = propertyMap.entrySet().iterator();

            while (entries.hasNext()) {

                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                key = key.replace(" ","");
                System.out.println("============================");
                System.out.println("--"+key);
//                String key = (String)entry.getKey();
                String value = (String) entry.getValue();
                org.dom4j.Element e=table.addElement(key);

                e.setText(value);

                OutputFormat format=OutputFormat.createPrettyPrint();
                //生成不一样的编码
                format.setEncoding("GBK");
                //6.生成xml文件
                File file=new File("data/"+tableName+"/"+tableName+"PropertyMap.xml");

                File fileParent = file.getParentFile();//判断是否存在
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }

                XMLWriter writer=new XMLWriter(new FileOutputStream(file),format);
                //设置是否转义，默认设置是true,代表转义
                writer.setEscapeText(false);
                writer.write(doc);
                writer.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void descriptorToXML() throws IOException {
        try{
            String tableName = tabledescriptor.getTableName();
            Element table = new Element("table");

            String schema = String.valueOf(tabledescriptor.getSchema());
            char lockGranularity = tabledescriptor.getLockGranularity();
            table.setAttribute("name", tableName);
            Element tableNameElement = new Element("tableName").setText(tableName);
            Element tableSchemaElement = new Element("schema").setText(schema);
            Element lockGranularityElement = new Element("lockGranularity").setText(String.valueOf(lockGranularity));
            Document document = new Document(table);

            //????????е?element????table???
            table.addContent(tableNameElement);
            table.addContent(tableSchemaElement);
            table.addContent(lockGranularityElement);

            Element primaryELement = new Element("primaryKey");
            table.addContent(primaryELement);
            //??primarykey??????
            for(int j = 0 ; j < tabledescriptor.getPrimaryKey().size(); j++){
                String primaryColumnName = tabledescriptor.getPrimaryKey().get(j).getColumnName();
                Element primaryColumnNameELement = new Element("primaryColumnName").setText(primaryColumnName);
                primaryELement.addContent(primaryColumnNameELement);
            }

            //??????е?columnDescriptor
            ColumnDescriptorList allDescriptor = tabledescriptor.getColumnDescriptorList();
            Element columnListElement = new Element("columnDescriptorList");
            table.addContent(columnListElement);
            for (int i = 0; i < allDescriptor.size(); i++) {
                //columELement
                Element columnElement = new Element("columnDescriptor");
                columnListElement.addContent(columnElement);
                //??????е?descriptor
                ColumnDescriptor singleColumn = allDescriptor.getColumnDescriptor(i);
                String columnName = singleColumn.getColumnName();
                Element columnNameElement = new Element("columnName").setText(columnName);
                columnElement.addContent(columnNameElement);
                int columnPosition = singleColumn.getPosition();
                Element columnPositionElement = new Element("columnPosition").setText(String.valueOf(columnPosition));
                columnElement.addContent(columnPositionElement);

                //dataTypedescriptor???????е?????
                DataTypeDescriptor typeDescriptor = singleColumn.getType();
                Element DataTypeDescriptorElement = new Element("DataTypeDescriptor");
                int typeId = typeDescriptor.getTypeId();
                Element typeIdElement = new Element("typeId").setText(String.valueOf(typeId));
                DataTypeDescriptorElement.addContent(typeIdElement);
                int precision= typeDescriptor.getPrecision();
                Element precisionElement = new Element("precision").setText(String.valueOf(precision));
                DataTypeDescriptorElement.addContent(precisionElement);
                int scale= typeDescriptor.getScale();
                Element scaleElement = new Element("scale").setText(String.valueOf(scale));
                DataTypeDescriptorElement.addContent(scaleElement);
                boolean isNullable= typeDescriptor.isNullable();
                Element isNullableElement = new Element("isNullable").setText(String.valueOf(isNullable));
                DataTypeDescriptorElement.addContent(isNullableElement);
                boolean isPrimaryKey= typeDescriptor.isPrimaryKey();
                Element isPrimaryKeyElement = new Element("isPrimaryKey").setText(String.valueOf(isPrimaryKey));
                DataTypeDescriptorElement.addContent(isPrimaryKeyElement);

                //??dataDEscriptorELement????tableElement???
                columnElement.addContent(DataTypeDescriptorElement);

                long autoincStart = singleColumn.getAutoincStart();
                Element autoincStartElement = new Element("autoincStart").setText(String.valueOf(autoincStart));
                columnElement.addContent(autoincStartElement);
                boolean autoincInc = singleColumn.isAutoincInc();
                Element autoincIncElement = new Element("autoincInc").setText(String.valueOf(autoincInc));
                columnElement.addContent(autoincIncElement);

                long autoincValue = singleColumn.getAutoincValue();
                Element autoincValueElement = new Element("autoincValue").setText(String.valueOf(autoincValue));
                columnElement.addContent(autoincValueElement);

                if(singleColumn.getComment() != null){
                    String comment = singleColumn.getComment();
                    Element commentElement = new Element("comment").setText(String.valueOf(comment));
                    columnElement.addContent(commentElement);
                }


                if(singleColumn.getDefaultInfo() != null){
                    String columnDefaultValue = singleColumn.getDefaultInfo().toString();
                    Element columnDefaultValueElement = new Element("columnDefaultValue").setText(String.valueOf(columnDefaultValue));
                    columnElement.addContent(columnDefaultValueElement);
                }

            }
            //?????е?table???xml
            Format format=Format.getCompactFormat();
            format.setIndent("");
            //?????????????
            format.setEncoding("GBK");
            //4.????XMLOutputter?????
            XMLOutputter outputter=new XMLOutputter(format);

            File file = new File("data/"+tableName+"/"+tableName+"Descriptor.xml");

            File fileParent = file.getParentFile();//判断是否存在
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }

            outputter.output(document, new FileOutputStream(file));

        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
