package com.ucd.oursql.sql.table.BTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondIndex <T, V extends Comparable<V>>{

    public Map<T, List<V>> createIndex(List<T> data,String primaryKey,String key){
        Map<T, List<V>> map=new HashMap<>();
        for(int i = 0 ; i < data.size() ; i++) {
            CglibBean bean=(CglibBean) data.get(i);
            Object tempPrimary=bean.getValue(primaryKey);
            Object tempKey=bean.getValue(key);
            if(!map.containsKey(tempKey)){
                List list=new ArrayList();
                list.add(tempPrimary);
                map.put((T)tempKey,list);
            }else{
                map.get(tempKey).add((V)tempPrimary);
            }
        }
        return map;
    }
}
