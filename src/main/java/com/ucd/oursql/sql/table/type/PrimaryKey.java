package com.ucd.oursql.sql.table.type;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.table.ColumnDescriptorList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrimaryKey implements SqlType{
    HashMap pkmap=new HashMap();
    List<String> names=new ArrayList<>();
//    List<Comparable> list=null;
    public PrimaryKey(List names,List list){
//        System.out.println("newpk");
//        this.list=list;
        for(int i=0;i<names.size();i++){
            pkmap.put(names.get(i),list.get(i));
            names.add(names.get(i));
        }
    }


    public PrimaryKey(){
//        list=new ArrayList<Comparable>();
    }


    public void addPrimaryKey(String name,Comparable t){
//        System.out.println("addPK");
//        System.out.println("addPK:"+t.getClass().getName());
        pkmap.put(name,t);
//        System.out.println("addPK:"+pkmap.get(name).getClass().getName());
        names.add(name);
//        list.add(t);
    }

    public  SqlType getPrimaryKey(String name){
//        System.out.println("getpk");
//        System.out.println(pkmap.get(name).getClass().getName()+ "   "+name);
        return (SqlType) pkmap.get(name);
//        return list.get(i);
    }

//    public List<Comparable> getPrimaryKeys(){
//
//        return list;
//    }

    public List<String> getKeys(){
//        System.out.println("getPKKeys");
//        Iterator it=pkmap.keySet().iterator();
//        while(it.hasNext()){
//            names.add((String) it.next());
//        }
        return names;
    }

    @Override
    public int compareTo(Object o) {
        PrimaryKey pk2=(PrimaryKey)o;
        int outcome=0;
        for(int i=0;i<names.size();i++){
            Comparable c1= (Comparable) pkmap.get(names.get(i));
            Comparable c2= pk2.getPrimaryKey(names.get(i));
//            System.out.println("c1:"+c1+";c2:"+c2);
//            System.out.println(c1.getClass().getName()+"---"+c2.getClass().getName());
            outcome=c1.compareTo(c2);
            if(outcome!=0){
                return outcome;
            }
            System.out.println(c1+"  "+c2+"  "+outcome);

        }
        return outcome;
    }

    @Override
    public String toString(){
//        System.out.println("toStringPK");
        boolean first=true;
        String str="";
        for(int j=0;j<names.size();j++){
            if(first){
                str=names.get(j)+":"+pkmap.get(names.get(j));
                first=false;
            }else{
                str=str+";"+names.get(j)+":"+pkmap.get(names.get(j));
            }
        }
        return str;
    }

    @Override
    public void setValue(String o, HashMap propertyMap, ColumnDescriptorList cl,String columnName) throws Exception {
//        System.out.println("setValuePK");
        String[] pairs=o.split(";");
        for(int i=0;i<pairs.length;i++){
            String p= pairs[i];
            String[] l=p.split(":");
            String key=l[0];
            String value=l[1];
            SqlType v=DMLTool.convertToValue(key,value,propertyMap,cl);
            pkmap.put(key,v);
            names.add(key);
        }
    }

    @Override
    public SqlType addOne() throws Exception {
        return null;
    }

    @Override
    public void setScale(int i) throws Exception {
    }

    @Override
    public void setPrecision(int i) throws Exception {
    }

    @Override
    public void updateValue() throws Exception {

    }

    @Override
    public SqlType add(SqlType a) throws Exception {
        throw new Exception("Error:This type do not support ADD opertion.");
    }

    @Override
    public void ave(int num) throws Exception {
        throw new Exception("Error:This type do not support AVE opertion.");
    }
}
