package com.ucd.oursql.sql.storage.BTree;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.ucd.oursql.sql.storage.Storage.TreeLoader;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;

public class Test {
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public static void main(String[] args) throws Exception {


        // 设置类成员属性
        HashMap propertyMap = new HashMap();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));
        BPlusTree<CglibBean, Integer> b = new BPlusTree<>(4);
//        Product p;

        long time1 = System.nanoTime();

        for (int i = 0; i <  100; i++) {
            CglibBean bean = new CglibBean(propertyMap);
            bean.setValue("id", new Integer(i));
            bean.setValue("name", "test");
            bean.setValue("address", "789");
            b.insert(bean, (Integer) bean.getValue("id"));
//            p = new Product(i, "test", 1.0 * i);
//            b.insert(p, p.getId());
        }


        b.getNodes(b.getRoot());
        long time2 = System.nanoTime();

//        CglibBean b1 = b.select(5);
//        Object o = b1.getObject();
//        Class c = o.getClass();
//        Field[] fields = o.getClass().getDeclaredFields();
//        for(int i = 0 , len = fields.length; i < len; i++) {
//            // 对于每个属性，获取属性名
//            String varName = fields[i].getName();
//            System.out.println("name of the variable are : " + varName.substring(12,varName.length()));
//        }

        TreeSaver TS = new TreeSaver();
        TS.SaveAsXML(b);
        TreeLoader TL = new TreeLoader();
        BPlusTree testTree = TL.loadFromFile("test");
//        System.out.println((CglibBean)testTree.select(23));

//        System.out.println("field is :" + fields[0].getName());
//        System.out.println("name of fields[0] is: "+getMethodName(fields[1].getName()));
//        Method[] methods = c.getDeclaredMethods();
//        System.out.println("the name of the method is: "+fields[2].getName().substring(12));
//        Method m = (Method) o.getClass().getMethod("get" + getMethodName(fields[1].getName().substring(12)));
//        System.out.println("method is : "+methods[1].getName());
//        String val = (String) m.invoke(o);
//        System.out.println("the value of the variable is: "+val);
        long time3 = System.nanoTime();

//        for (int i = 10000; i >= 0; i--) {
//            b.delete(i);
//        }
//        long time4 = System.nanoTime();

//        System.out.println("插入耗时: " + (time2 - time1));
//        System.out.println("查询耗时: " + (time3 - time2));
//        System.out.println("删除耗时: " + (time4 - time3));
    }
}
