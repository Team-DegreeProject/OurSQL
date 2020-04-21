package com.ucd.oursql.sql.table.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Distinct implements Comparable{
    HashMap pkmap=new HashMap();
    List<String> names=new ArrayList<>();
    //    List<Comparable> list=null;
    public Distinct(List names,List list){
//        this.list=list;
        for(int i=0;i<names.size();i++){
            pkmap.put(names.get(i),list.get(i));
            names.add(names.get(i));
        }
    }
    public Distinct(){
//        list=new ArrayList<Comparable>();
    }

    public void addDistinct(String name,Comparable t){
        pkmap.put(name,t);
        names.add(name);
//        list.add(t);
    }

    public  Comparable getDistinct(String name){
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
        Distinct pk2=(Distinct) o;
        int outcome=0;
        for(int i=0;i<names.size();i++){
            Comparable c1= (Comparable) pkmap.get(names.get(i));
            Comparable c2= pk2.getDistinct(names.get(i));
            outcome=c1.compareTo(c2);
            if(outcome!=0){
                return outcome;
            }

        }
        return outcome;
    }

    public void printDistinct(){
        System.out.println("====Distinct====");
        for(int j=0;j<names.size();j++){
            System.out.println(names.get(j)+"--->"+pkmap.get(names.get(j)));
        }
        System.out.println("==========");
    }
}
