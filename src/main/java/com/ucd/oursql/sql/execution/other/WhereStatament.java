package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.HashMap;
import java.util.List;

import static com.ucd.oursql.sql.execution.DMLTool.convertToValue;
import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class WhereStatament {

    public static Table whereAnd(Table t1,Table t2) throws ClassNotFoundException {
        if(t1==null||t2==null){
            return null;
        }
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree returnTree= BPlusTreeTool.mergeTreeAnd(b1,b2);
        Table t=new Table(t1.getTableDescriptor(),returnTree);
        return t;
    }


    public static Table whereOr(Table t1,Table t2) throws ClassNotFoundException {
        if(t1==null){
            return t2;
        }else if(t2==null){
            return t1;
        }
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree returnTree=BPlusTreeTool.mergeTreeOr(b1,b2);
        Table t=new Table(t1.getTableDescriptor(),returnTree);
        return t;
    }

    public static Table compare(Table table, String attribute, int type, Comparable compare) throws ClassNotFoundException {
        BPlusTree b=table.getTree();
        BPlusTree returnTree=new BPlusTree();
        List btree=b.getDatas();
        switch (type){
            case EQ:{
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)==0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary key"));
                    }
                }
                break;
            }
            case RQ:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)<0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary key"));
                    }
                }
                break;
            case LQ:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)>0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary key"));
                    }
                }
                break;
        }
        Table t=new Table(table.getTableDescriptor(),returnTree);
        return t;
    }

    public static Table inCondition(Table t,List tokens) throws Exception {
        String att=((Token)tokens.get(0)).image;
        HashMap propertyMap=t.getPropertyMap();
        Table change=null;
        List<Token> conditions= (List) tokens.get(2);
        for(int i=0;i<conditions.size();i++){
            String str=conditions.get(i).image;
            SqlType value=convertToValue(att,str,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
            Table temp=compare(t, att, EQ, value);
            change=WhereStatament.whereOr(change,temp);
        }
        return change;
    }

    public static Table betweenCondition(Table t,List<Token> tokens) throws Exception {
        String att=((Token)tokens.get(0)).image;
        HashMap propertyMap=t.getPropertyMap();
        Table temp=t;
        String str1=tokens.get(2).image;
        SqlType value1= convertToValue(att,str1,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
        String str2=tokens.get(4).image;
        SqlType value2= convertToValue(att,str2,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
        temp=compare(temp, att, LQ, value1);
        temp=compare(temp, att, RQ, value2);
        if(t.equals(temp)){
            System.out.println("There is no change.");
            return null;
        }
        return temp;
    }

    public static Table basicCondition(Table t,List<Token> tokens) throws Exception {
        String attribute=((Token)tokens.get(0)).image;
        int type=((Token)tokens.get(1)).kind;
        String str= ((Token) tokens.get(2)).image;
        SqlType value= DMLTool.convertToValue(attribute,str,t.getPropertyMap(),t.getTableDescriptor().getColumnDescriptorList());
        Table table=compare(t,attribute,type,value);
        return table;
    }

    public static Table whereImpl(Table table,List conditions) throws Exception {
        Table change=null;
        Object first=conditions.get(0);
        if(first instanceof Token){
            System.out.println("one condition==========");
            change=checkAType(conditions,table);
        }else if (first instanceof List){
            System.out.println("multiple condition==========");
            boolean b=false;
            for(int i=0;i<conditions.size();i++){
                Object o=conditions.get(i);
                if(o instanceof List){
                    Table temp=checkAType((List) o,table);
                    if(b){
                        change=whereAnd(temp,change);
                    }else{
                        change=whereOr(temp,change);
                    }
                }else if(o instanceof Token){
                    int type=((Token)o).kind;
                    if(type==AND){
                        b=true;
                    }else if(type==OR){
                        b=false;
                    }
                }
            }
        }
        if(change==null){
            throw new Exception("There is no change");
        }
        change.printTable(null);
        return change;
    }

    public static Table checkAType(List condition,Table table) throws Exception {
        int type=((Token)condition.get(1)).kind;
        Table change=null;
        if(type==IN){
            System.out.println("In===========");
            change=inCondition(table,condition);
        }else if(type==EQ||type==LQ||type==RQ){
            System.out.println("Basic===========");
            change=basicCondition(table,condition);
        }else if(type==BETWEEN){
            System.out.println("Between===========");
            change=betweenCondition(table,condition);
        }
        return change;
    }




}
