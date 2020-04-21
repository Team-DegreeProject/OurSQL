package storage.Storage;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import table.BTree.BPlusTree;
import table.TableDescriptor;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.ColumnDescriptorList;

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
            //生成不一样的编码
            format.setEncoding("GBK");
            //4.创建XMLOutputter的对象
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

        //将前边所有的element添加到table里边
        table.addContent(tableNameElement);
        table.addContent(tableSchemaElement);
        table.addContent(lockGranularityElement);

        Element primaryELement = new Element("primaryKey");
        table.addContent(primaryELement);
        //存primarykey的名字
        for(int j = 0 ; j < tabledescriptor.getPrimaryKey().size(); j++){
            String primaryColumnName = tabledescriptor.getPrimaryKey().get(j).getColumnName();
            Element primaryColumnNameELement = new Element("primaryColumnName").setText(primaryColumnName);
            primaryELement.addContent(primaryColumnNameELement);
        }

        //获取所有的columnDescriptor
        ColumnDescriptorList allDescriptor = tabledescriptor.getColumnDescriptorList();
        for (int i = 0; i < allDescriptor.size(); i++) {
            //columELement
            Element columnElement = new Element("columnDescriptor");
            //获取每一列的descriptor
            ColumnDescriptor singleColumn = allDescriptor.getColumnDescriptor(i);
            String columnName = singleColumn.getColumnName();
            Element columnNameElement = new Element("columnName").setText(columnName);
            int columnPosition = singleColumn.getPosition();
            Element columnPositionElement = new Element("columnPosition").setText(String.valueOf(columnPosition));

            //dataTypedescriptor里边的所有的属性
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

            //将dataDEscriptorELement添加到tableElement里边
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
        //将现有的table存成xml
        Format format=Format.getCompactFormat();
        format.setIndent("");
        //生成不一样的编码
        format.setEncoding("GBK");
        //4.创建XMLOutputter的对象
        XMLOutputter outputter=new XMLOutputter(format);
        outputter.output(document, new FileOutputStream(new File("data/"+tableName+"/"+tableName+"Descriptor.xml")));



    }


}
