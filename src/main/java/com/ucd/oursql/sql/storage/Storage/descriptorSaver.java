package com.ucd.oursql.sql.storage.Storage;


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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            TreeSaver ts = new TreeSaver();
            ts.SaveAsXML(btree);
            hashmapToXML();
            descriptorToXML();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void hashmapToXML(){
        try {
            String tableName = tabledescriptor.getTableName();
            Element table = new Element("table");
            Document document = new Document(table);
            Iterator entries = propertyMap.entrySet().iterator();

            while (entries.hasNext()) {

                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                Element e = new Element(key);
                e.setText(value);
                table.addContent(e);
            }
            Format format=Format.getCompactFormat();
            format.setIndent("");
            //���ɲ�һ���ı���
            format.setEncoding("GBK");
            //4.����XMLOutputter�Ķ���
            XMLOutputter outputter=new XMLOutputter(format);
            outputter.output(document, new FileOutputStream(new File("data/"+tableName+"/"+tableName+"PropertyMap.xml")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void descriptorToXML() throws IOException {
        String tableName = tabledescriptor.getTableName();
        Element table = new Element("table");

        String schema = tabledescriptor.getSchemaName();
        char lockGranularity = tabledescriptor.getLockGranularity();
        table.setAttribute("name", tableName);
        Element tableNameElement = new Element("tableName").setText(tableName);
        Element tableSchemaElement = new Element("schema").setText(schema);
        Element lockGranularityElement = new Element("lockGranularity").setText(String.valueOf(lockGranularity));
        Document document = new Document(table);

        //��ǰ�����е�element��ӵ�table���
        table.addContent(tableNameElement);
        table.addContent(tableSchemaElement);
        table.addContent(lockGranularityElement);

        Element primaryELement = new Element("primaryKey");
        table.addContent(primaryELement);
        //��primarykey������
        for(int j = 0 ; j < tabledescriptor.getPrimaryKey().size(); j++){
            String primaryColumnName = tabledescriptor.getPrimaryKey().get(j).getColumnName();
            Element primaryColumnNameELement = new Element("primaryColumnName").setText(primaryColumnName);
            primaryELement.addContent(primaryColumnNameELement);
        }

        //��ȡ���е�columnDescriptor
        ColumnDescriptorList allDescriptor = tabledescriptor.getColumnDescriptorList();
        for (int i = 0; i < allDescriptor.size(); i++) {
            //columELement
            Element columnElement = new Element("columnDescriptor");
            //��ȡÿһ�е�descriptor
            ColumnDescriptor singleColumn = allDescriptor.getColumnDescriptor(i);
            String columnName = singleColumn.getColumnName();
            Element columnNameElement = new Element("columnName").setText(columnName);
            int columnPosition = singleColumn.getPosition();
            Element columnPositionElement = new Element("columnPosition").setText(String.valueOf(columnPosition));

            //dataTypedescriptor��ߵ����е�����
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

            //��dataDEscriptorELement��ӵ�tableElement���
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
            String comment = singleColumn.getComment();
            Element commentElement = new Element("comment").setText(String.valueOf(comment));
            columnElement.addContent(commentElement);
            String columnDefaultValue = singleColumn.getDefaultInfo().toString();
            Element columnDefaultValueElement = new Element("columnDefaultValue").setText(String.valueOf(columnDefaultValue));
            columnElement.addContent(columnDefaultValueElement);
        }
        //�����е�table���xml
        Format format=Format.getCompactFormat();
        format.setIndent("");
        //���ɲ�һ���ı���
        format.setEncoding("GBK");
        //4.����XMLOutputter�Ķ���
        XMLOutputter outputter=new XMLOutputter(format);
        outputter.output(document, new FileOutputStream(new File("data/"+tableName+"/"+tableName+"Descriptor.xml")));



    }


}
