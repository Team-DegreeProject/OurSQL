package com.ucd.oursql.sql.table.BTree;

import com.ucd.oursql.sql.table.type.SqlType;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {

        // 设置类成员属性
        HashMap propertyMap = new HashMap();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));

        boolean[] bl=new boolean[1000000];
        int[] key=new int[500000];
        int count=0;
        Random random=new Random();
        while(count<500000){
            int temp=random.nextInt(1000000);
            if(bl[temp]==false){
                bl[temp]=true;
                key[count]=temp;
                count++;
            }
        }
////////////////////////////////////////////////////////////////////////////////
        BPlusTree<CglibBean, Integer>[] bArray = new BPlusTree[15];
        for(int j=0;j<15;j++){
            bArray[j]=new BPlusTree<>(340);
            for (int i = 0; i <1000000; i++) {
                CglibBean bean = new CglibBean(propertyMap);
                bean.setValue("id", new Integer(i));
                bean.setValue("name", "test");
                bean.setValue("address", "789");
                bArray[j].insert(bean,(Integer) bean.getValue("id"));
            }
        }
        long[] time=new long[15];
        for (int j=0;j<15;j++){
            long time1=System.nanoTime();
            for(int i=0;i<500000;i++){
                bArray[j].delete(key[i]);
            }
            long time2=System.nanoTime();
            time[j]=time2-time1;
        }
        for(int j=0;j<15;j++){
            System.out.println("version3 第"+j+"次: "+time[j]);
        }
/////////////////////////////////////////////////////////////////////////////////
//        boolean[] bl=new boolean[1000000];
//        int[] key=new int[1000000];
//        int count=0;
//        Random random=new Random();
//        while(count<1000000){
//            int temp=random.nextInt(1000000);
//            if(bl[temp]==false){
//                bl[temp]=true;
//                key[count]=temp;
//                count++;
//            }
//        }
//        BPlusTree<CglibBean, Integer> b = new BPlusTree<>(4);
//        long time1 = System.nanoTime();
/////////////////////////////////////////////////////////////////////////////////////////////////
//        BPlusTree<CglibBean, Integer>[] bArray_34 = new BPlusTree[15];
//        long[] insertTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_34[j]=new BPlusTree<>(340);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", key[i]);
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_34[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 insert "+j+": "+insertTime_34[j]);
//        }
//        long[] selectTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_34[j].select(key[i]);
//            }
//            long time2=System.nanoTime();
//            selectTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 select "+j+": "+selectTime_34[j]);
//        }
//
//        long[] deleteTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_34[j].delete(key[i]);
//            }
//            long time2=System.nanoTime();
//            deleteTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 delete "+j+": "+deleteTime_34[j]);
//        }
//
//
//        BPlusTree<CglibBean, Integer>[] bArray_68 = new BPlusTree[15];
//        long[] insertTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_68[j]=new BPlusTree<>(680);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", key[i]);
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_68[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 insert "+j+": "+insertTime_68[j]);
//        }
//
//        long[] selectTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_68[j].select(key[i]);
//            }
//            long time2=System.nanoTime();
//            selectTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 select "+j+": "+selectTime_68[j]);
//        }
//
//        long[] deleteTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_68[j].delete(key[i]);
//            }
//            long time2=System.nanoTime();
//            deleteTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 delete "+j+": "+deleteTime_68[j]);
//        }
//
//        BPlusTree<CglibBean, Integer>[] bArray_13 = new BPlusTree[15];
//        long[] insertTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_13[j]=new BPlusTree<>(1360);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", key[i]);
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_13[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 insert "+j+": "+insertTime_13[j]);
//        }
//
//        long[] selectTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_13[j].select(key[i]);
//            }
//            long time2=System.nanoTime();
//            selectTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 select "+j+": "+selectTime_13[j]);
//        }
//
//        long[] deleteTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_13[j].delete(key[i]);
//            }
//            long time2=System.nanoTime();
//            deleteTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 delete "+j+": "+deleteTime_13[j]);
//        }
//
//        BPlusTree<CglibBean, Integer>[] bArray_27 = new BPlusTree[15];
//        long[] insertTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_27[j]=new BPlusTree<>(2720);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", key[i]);
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_27[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 insert "+j+": "+insertTime_27[j]);
//        }
//        long[] selectTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_27[j].select(key[i]);
//            }
//            long time2=System.nanoTime();
//            selectTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 select "+j+": "+selectTime_27[j]);
//        }
//
//        long[] deleteTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_27[j].delete(key[i]);
//            }
//            long time2=System.nanoTime();
//            deleteTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 delete "+j+": "+deleteTime_27[j]);
//        }
//
//
//        BPlusTree<CglibBean, Integer>[] bArray_54 = new BPlusTree[15];
//        long[] insertTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_54[j]=new BPlusTree<>(5440);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", key[i]);
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_54[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 insert "+j+": "+insertTime_54[j]);
//        }
//        long[] selectTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_54[j].select(key[i]);
//            }
//            long time2=System.nanoTime();
//            selectTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 select "+j+": "+selectTime_54[j]);
//        }
//        long[] deleteTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_54[j].delete(key[i]);
//            }
//            long time2=System.nanoTime();
//            deleteTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 delete "+j+": "+deleteTime_54[j]);
//        }
///////////////////////////////////////////////////////////////////////////////////////
//        BPlusTree<CglibBean, Integer>[] bArray_34 = new BPlusTree[15];
//        long[] insertTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_34[j]=new BPlusTree<>(340);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_34[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 insert "+j+": "+insertTime_34[j]);
//        }
//        long[] selectTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_34[j].select(i);
//            }
//            long time2=System.nanoTime();
//            selectTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 select "+j+": "+selectTime_34[j]);
//        }
//
//        long[] deleteTime_34=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_34[j].delete(i);
//            }
//            long time2=System.nanoTime();
//            deleteTime_34[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("340 delete "+j+": "+deleteTime_34[j]);
//        }
//
//
//        BPlusTree<CglibBean, Integer>[] bArray_68 = new BPlusTree[15];
//        long[] insertTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_68[j]=new BPlusTree<>(680);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_68[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 insert "+j+": "+insertTime_68[j]);
//        }
//
//        long[] selectTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_68[j].select(i);
//            }
//            long time2=System.nanoTime();
//            selectTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 select "+j+": "+selectTime_68[j]);
//        }
//
//        long[] deleteTime_68=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_68[j].delete(i);
//            }
//            long time2=System.nanoTime();
//            deleteTime_68[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("680 delete "+j+": "+deleteTime_68[j]);
//        }
//
//        BPlusTree<CglibBean, Integer>[] bArray_13 = new BPlusTree[15];
//        long[] insertTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_13[j]=new BPlusTree<>(1360);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_13[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 insert "+j+": "+insertTime_13[j]);
//        }
//
//        long[] selectTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_13[j].select(i);
//            }
//            long time2=System.nanoTime();
//            selectTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 select "+j+": "+selectTime_13[j]);
//        }
//
//        long[] deleteTime_13=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_13[j].delete(i);
//            }
//            long time2=System.nanoTime();
//            deleteTime_13[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("1360 delete "+j+": "+deleteTime_13[j]);
//        }
//
//        BPlusTree<CglibBean, Integer>[] bArray_27 = new BPlusTree[15];
//        long[] insertTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_27[j]=new BPlusTree<>(2720);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_27[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 insert "+j+": "+insertTime_27[j]);
//        }
//        long[] selectTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_27[j].select(i);
//            }
//            long time2=System.nanoTime();
//            selectTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 select "+j+": "+selectTime_27[j]);
//        }
//
//        long[] deleteTime_27=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_27[j].delete(i);
//            }
//            long time2=System.nanoTime();
//            deleteTime_27[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("2720 delete "+j+": "+deleteTime_27[j]);
//        }
//
//
//        BPlusTree<CglibBean, Integer>[] bArray_54 = new BPlusTree[15];
//        long[] insertTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            bArray_54[j]=new BPlusTree<>(5440);
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray_54[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            insertTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 insert "+j+": "+insertTime_54[j]);
//        }
//        long[] selectTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_54[j].select(i);
//            }
//            long time2=System.nanoTime();
//            selectTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 select "+j+": "+selectTime_54[j]);
//        }
//        long[] deleteTime_54=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 0; i <1000000; i++) {
//                bArray_54[j].delete(i);
//            }
//            long time2=System.nanoTime();
//            deleteTime_54[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("5440 delete "+j+": "+deleteTime_54[j]);
//        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//        for (int j=0;j<15;j++){
//            for(int i=0;i<999999;i=i+2){
//                bArray[j].delete(i);
//            }
//        }
//        long[] time=new long[15];
//        for(int j=0;j<15;j++){
//            long time1=System.nanoTime();
//            for (int i = 1999999; i >=1000000; i--) {
//                CglibBean bean = new CglibBean(propertyMap);
//                bean.setValue("id", new Integer(i));
//                bean.setValue("name", "test");
//                bean.setValue("address", "789");
//                bArray[j].insert(bean,(Integer) bean.getValue("id"));
//            }
//            long time2=System.nanoTime();
//            time[j]=time2-time1;
//        }
//        for(int j=0;j<15;j++){
//            System.out.println("version3 第"+j+"次: "+time[j]);
//        }
//        CglibBean bean = new CglibBean(propertyMap);
//        bean.setValue("id", 2);
//        b.insert(bean,(Integer) bean.getValue("id"));
//
//        CglibBean bean1 = new CglibBean(propertyMap);
//        bean.setValue("id", 8);
//        b.insert(bean,(Integer) bean.getValue("id"));
//
//        CglibBean bean2 = new CglibBean(propertyMap);
//        bean.setValue("id", 10);
//        b.insert(bean,(Integer) bean.getValue("id"));
//
//
//        b.getNodes(b.getRoot());
//        System.out.println("-----------------------------------------------------");
//        b.delete(2);
//        b.getNodes(b.getRoot());
//        System.out.println("-----------------------------------------------------");

        //测试大于小于
//        List l=b.getMiddleDatas(23,57);
//        for(int i=0;i<l.size();i++){
//            CglibBean bean=(CglibBean)l.get(i);
//            System.out.println((Integer) bean.getValue("id"));
//        }
//        long time2 = System.nanoTime();
//
//        CglibBean b1 = b.select(23);
//        long time3 = System.nanoTime();
//
//        for (int i = 100; i >=0; i--) {
//            b.delete(i);
//        }
//        long time4 = System.nanoTime();
//
//        for (int i = 100; i >=0; i--) {
//            CglibBean bean = new CglibBean(propertyMap);
//            bean.setValue("id", new Integer(i));
//            bean.setValue("name", "test");
//            bean.setValue("address", "789");
//            b.delete((Integer) bean.getValue("id"));
////            p = new Product(i, "test", 1.0 * i);
////            b.insert(p, p.getId());
//        }
////        b.getNodes(b.getRoot());
//
//        long time5 = System.nanoTime();
//        System.out.println("插入耗时: " + (time2 - time1));
//        System.out.println("查询耗时: " + (time3 - time2));
//        System.out.println("删除耗时: " + (time4 - time3));
//        System.out.println("再次插入耗时: " + (time5 - time4));


//        List<Object> data=b.getDatas();
//        SecondIndex <Object,Integer> m=new SecondIndex<>();
//        Map<Object,List<Integer>> testmap=m.createIndex(data,"id","address");
//        for (Object entry : testmap.keySet()) {
//            System.out.println("key : " + entry);
//            List templist=(List)testmap.get(entry);
//            System.out.println("value: ");
//            for(int j=0;j<templist.size();j++){
//                System.out.print(templist.get(j)+"  ");
//            }
//        }
//        List list= b.getDatas();
//        for(int i=0;i<list.size();i++){
//            CglibBean c= (CglibBean) list.get(i);
//            SqlType o=(SqlType)c.getValue("name");
//            o.toString();
//        }


    }
}
