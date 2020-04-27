package com.ucd.oursql.sql.table.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrimaryKey implements SqlType{
    HashMap pkmap=new HashMap();
    List<String> names=new ArrayList<>();
//    List<Comparable> list=null;
    public PrimaryKey(List names,List list){
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
        pkmap.put(name,t);
        names.add(name);
//        list.add(t);
    }

    public  Comparable getPrimaryKey(String name){
        return (Comparable) pkmap.get(name);
//        return list.get(i);
    }

//    public List<Comparable> getPrimaryKeys(){
//
//        return list;
//    }

    public List<String> getKeys(){
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
            outcome=c1.compareTo(c2);
            if(outcome!=0){
                return outcome;
            }

        }
        return outcome;
    }

    @Override
    public String toString(){
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
    public void setValue(String o) throws Exception {
        String[] pairs=o.split(";");
        for(int i=0;i<pairs.length;i++){
            String p= pairs[i];
            String[] l=p.split(":");
            String key=l[0];
            String value=l[1];
            pkmap.put(key,value);
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
}
