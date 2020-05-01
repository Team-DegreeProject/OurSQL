package com.ucd.oursql.sql.storage.Storage;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.BTree.LeafNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class TreeSaver {
//    BPlusTree BplusTree;
//    String[] nodePool;

    public TreeSaver() {
    }

//    public TreeSaver(BPlusTree T) {
//        this.BplusTree = T;
//    }

    public void SaveAsXML(BPlusTree BplusTree, String tableName, List<String> ColumnNameList) throws Exception {

        List<LeafNode> leafNodes = BplusTree.getLeafNodes();
        try {
            //xml的一些部分
            //1.生成一个根节点
            Element table = new Element("table");
            //2.为节点添加属性
            table.setAttribute("name", tableName);
            //3.生成一个document的对象
            Document document = new Document(table);

//        for (int i = 0; i < ss.length; i++) {
//            Element student = new Element("student");
//            table.addContent(student);
//            Element name = new Element("name").setText(ss[i].getName());
//            Element id = new Element("id").setText(ss[i].getId());
//            student.addContent(name);
//            student.addContent(id);
//        }
            List objectList = BplusTree.getDatas();
            HashMap<String, String> resultMap = new HashMap<>();
            for (Object o : objectList) {
                CglibBean cglibBean = (CglibBean) o;
                for (String name : ColumnNameList) {
                    String value = cglibBean.getValue(name).toString();
//                    System.out.println("the value is: "+value+" name: "+name);
                    resultMap.put(name, value);
                }
                Element entity = new Element("entity");
                table.addContent(entity);
                Iterator iter = resultMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();

                    //将对应的内容和key添加到xml中
                    Element XML_entity = new Element(key).setText(val);
                    System.out.println("!!!!!!!!!");
                    System.out.println("The value of the val is: "+val);
                    entity.addContent(XML_entity);


                }
            }


//            for (int i = 0; i < leafNodes.size(); i++) {
//                LeafNode positionNode = leafNodes.get(i);
//                Object[] objects = positionNode.getValues();
//                for (int j = 0; j < objects.length ; j++) {
//                        CglibBean cglb = (CglibBean) objects[j];
//                        Object cglbObject = cglb.getObject();
//                        String className = c.getSimpleName();
//                        Class c = cglbObject.getClass();
//                    if(objects[j] != null){
//                        Field[] fields = c.getDeclaredFields();
//                        HashMap<String, String> internalDatas = new HashMap<>();
//                        for (int h = 0, len = fields.length; h < len; h++) {
//                            // 对于每个属性，获取属性名
//                            String varName = fields[h].getName().substring(12);
////                        System.out.println("the varName is :"+varName);
//                            //获取每个方法名
//                            Method m = (Method) c.getMethod("get" + storage.Storage.XMLUtils.getMethodName(varName));
//                            //获取这个方法返回的值，然后一起存起来
//                            String varVal = (String) m.invoke(cglbObject).toString();
////                        System.out.println("the value of the data is: "+varVal);
//                            //以一个hashmap存下来，变量名-数据
//                            internalDatas.put(varName, varVal);
//                        }




            //利用迭代器来遍历hashmap



            Format format = Format.getCompactFormat();
            format.setIndent("");
            //生成不一样的编码
            format.setEncoding("GBK");
            //4.创建XMLOutputter的对象
            XMLOutputter outputter = new XMLOutputter(format);
            //5.利用outputter将document对象转换成xml文档

            //测试命名和创建文件夹
            String tn = tableName;
            File targetFile = new File(tn + ".xml");
            File test = new File("data/" + tn);
            //创建文件夹
            test.mkdir();
            FileOutputStream fileStream = new FileOutputStream(new File("data/" + tn + "/" + tn + ".xml"));
            outputter.output(document, fileStream);
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public boolean deleteTable(String tableName){

        File file = new File("data/" + tableName + "/");
        if(!file.exists()){
            return false;
        }
        if(file.isFile()){
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if(f.isFile()){
                if(!f.delete()){
                    System.out.println(f.getAbsolutePath()+" delete error!");
                    return false;
                }
            }else{
                if(!this.deleteTable(f.getAbsolutePath())){
                    return false;
                }
            }
        }
        return file.delete();
    }


}
