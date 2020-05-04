package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.SqlType;
import javafx.scene.control.Tab;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static com.ucd.oursql.sql.execution.DMLTool.convertToValue;
import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class WhereStatament {

    public static Table whereImpl(Table table,List conditions) throws Exception {
        if(conditions==null){
//            table.printTable(null);
            System.out.println("return table");
            return table;
        }
        Table change=null;
        Object first=conditions.get(0);
        if(first instanceof Token){
            change=checkAType(conditions,table);
        }else if (first instanceof List){
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
            System.out.println("There is no change");
        }
        return change;
    }

    public static Table whereAnd(Table t1,Table t2) throws ClassNotFoundException {
        if(t1==null){
            return t2;
        }else if (t2==null){
            return t1;
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
//        System.out.println("or");
//        ((PrimaryKey)((CglibBean)t.getTree().getDatas().get(0)).getValue("primary key")).printPK();
//        t1.getTableDescriptor().printTableDescriptor();
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
                        returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                    }
                }
                break;
            }
            case RQ:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)<0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                    }
                }
                break;
            case LQ:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)>0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                    }
                }
                break;
            case GT:
                 for(int i=0;i<btree.size();i++){
                     CglibBean temp= (CglibBean) btree.get(i);
                     Comparable c= (Comparable) temp.getValue(attribute);
                     if(c.compareTo(compare)>=0){
                         returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                     }
                 }
                 break;
            case LT:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)<=0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                    }
                }
                break;
            case NE:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)!= 0){
                        returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
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

        //check not in
        boolean in=true;
        Token o= (Token) tokens.get(1);
        if(o.kind==NOT){
            in=false;
        }

        if(in ==true){
            System.out.println("IN");
            List<Token> conditions= (List) tokens.get(2);
            for(int i=0;i<conditions.size();i++){
                String str=conditions.get(i).image;
                SqlType value=convertToValue(att,str,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
                Table temp=compare(t, att, EQ, value);
                change=WhereStatament.whereOr(change,temp);
            }
        }else{
            System.out.println("NOT IN");
            List<Token> conditions= (List) tokens.get(3);
            for(int i=0;i<conditions.size();i++){
                String str=conditions.get(i).image;
                SqlType value=convertToValue(att,str,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
                Table temp=compare(t, att, NE, value);
                change=WhereStatament.whereAnd(change,temp);
                change.printTable(null);
            }
        }

        return change;
    }

    public static Table betweenCondition(Table t,List<Token> tokens) throws Exception {
        String att=((Token)tokens.get(0)).image;
        HashMap propertyMap=t.getPropertyMap();
        Table temp=t;
        if(tokens.get(1).kind==NOT){
            String str1=tokens.get(3).image;
            SqlType value1= convertToValue(att,str1,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
            String str2=tokens.get(5).image;
            SqlType value2= convertToValue(att,str2,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
            Table temp1=compare(temp, att, RQ, value1);
//            System.out.println("temp 1=====================");
//            temp1.printTable(null);
            Table temp2=compare(temp, att, LQ, value2);
//            temp2.printTable(null);
            temp=whereOr(temp1,temp2);
//            temp.printTable(null);
        }else{
            String str1=tokens.get(2).image;
            SqlType value1= convertToValue(att,str1,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
            String str2=tokens.get(4).image;
            SqlType value2= convertToValue(att,str2,propertyMap,t.getTableDescriptor().getColumnDescriptorList());
            temp=compare(temp, att, GT, value1);
            temp=compare(temp, att, LT, value2);
        }
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



    public static Table checkAType(List condition,Table table) throws Exception {
        Table change=null;
        for(int i=0;i<condition.size();i++){
            int type=((Token)condition.get(i)).kind;
            if(type==IN){
                System.out.println("In===========");
                change=inCondition(table,condition);
                break;
            }else if(type==EQ||type==LQ||type==RQ||type==LT||type==GT||type==NE){
                System.out.println("Basic===========");
                change=basicCondition(table,condition);
                break;
            }else if(type==BETWEEN){
                System.out.println("Between===========");
                change=betweenCondition(table,condition);
                break;
            }else if(type==NOT_NULL){
                System.out.println("Not Null===========");
                change=notNullCondition(table,condition);
                break;
            }else if(type==NULL){
                System.out.println("Null===========");
                change=nullCondition(table,condition);
                break;
            }else if(type==LIKE){
                System.out.println("Like===========");
                change=likeCondition(table,condition);
                break;
            }
        }

        return change;
    }

    public static Table likeCondition(Table t,List<Token> tokens) throws ClassNotFoundException {
        String attribute=tokens.get(0).image;
        String value=tokens.get(2).image;
        int type=t.getTableDescriptor().getColumnDescriptorList().getColumnDescriptor(attribute).getType().getTypeId();
        if(type==VARCHAR || type== CHAR){
            value=value.substring(1,value.length()-1);
        }
        String regular=value;
        String[] r=regular.split("%");
        for(int i=0;i<r.length;i++){
            System.out.println(i+":"+r[i]);
        }
        int ttype=-1;
        if(r.length==0){
            return t;
        }else if(r.length==1){
//                System.out.println("2222");
                regular=r[0];
                ttype=2;
        }else if(r.length==2){
            String sub=value.substring(value.length()-1,value.length());
//            System.out.println("sub:"+sub);
            if(sub.equals("%")){
//                System.out.println("3333");
                regular=r[1];
                ttype=3;
            }else{
//                System.out.println("1111");
                regular=r[1];
                ttype=1;
            }

        }
//        regular.replaceAll("\\%","");
//        value.replaceAll("\\%","(\\s*\\S*)");
//        String regular="/^"+value+"$/";
//        int length=regular.length();
//        regular.replaceAll("%","(\\s*\\S*)");
//        for(int i=0;i<value.length()-1;i++){
//            String v=value.substring(i,i+1);
//            if(v.equals("%")){
//                regular=regular.substring(0,i+2)+"(\\s+\\S*)"+regular.substring(i+1,length);
//            }
//        }
//        regular="^"+regular+"$";
        System.out.println("Regular Expression:"+regular);
        Table change=compareLike(t,attribute,regular,value,ttype);
        System.out.println("WhereLike");
        change.printTable(null);
        return change;
    }

    public static Table compareLike(Table table,String attribute,String regular,String value,int type) throws ClassNotFoundException {
        BPlusTree b=table.getTree();
        BPlusTree returnTree=new BPlusTree();
        List btree=b.getDatas();
        for(int i=0;i<btree.size();i++){
            CglibBean temp= (CglibBean) btree.get(i);
            SqlType c= (SqlType) temp.getValue(attribute);
            String s=c.toString();
//            System.out.println("S:"+s+";regular:"+regular);
            int num=s.indexOf(regular);
//            System.out.println("type:"+type+";num:"+num);
            if((type==1&&num>1)||(type==2&&num==1)||(type==3&&num>1&&(num+regular.length())<s.length())){
//                System.out.println("in-->"+s);
                returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
            }
        }
        Table t=new Table(table.getTableDescriptor(),returnTree);
        return t;
    }

    public static Table nullCondition(Table t,List<Token> tokens) throws ClassNotFoundException {
        String attribute=((Token)tokens.get(0)).image;
        Table table=compareNull(t,attribute,NULL);
        return table;
    }

    public static Table notNullCondition(Table t,List<Token> tokens) throws ClassNotFoundException {
        String attribute=((Token)tokens.get(0)).image;
        Table table=compareNull(t,attribute,NOT_NULL);
        return table;
    }

    public static Table compareNull(Table table,String attribute,int type) throws ClassNotFoundException {
        BPlusTree b=table.getTree();
        BPlusTree returnTree=new BPlusTree();
        List btree=b.getDatas();
        if(type==NOT_NULL){
            for(int i=0;i<btree.size();i++){
                CglibBean temp= (CglibBean) btree.get(i);
                Comparable c= (Comparable) temp.getValue(attribute);
                if(c!=null){
                    returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                }
            }
        }else if(type==NULL){
            for(int i=0;i<btree.size();i++){
                CglibBean temp= (CglibBean) btree.get(i);
                Comparable c= (Comparable) temp.getValue(attribute);
                if(c==null){
                    returnTree.insert(temp, (Comparable) temp.getValue("primary_key"));
                }
            }
        }
        Table t=new Table(table.getTableDescriptor(),returnTree);
        return t;
    }

//    public static Table basicCondition(Table t,List<Token> tokens) throws Exception {
//        String attribute=((Token)tokens.get(0)).image;
//        int type=((Token)tokens.get(1)).kind;
//        String str= ((Token) tokens.get(2)).image;
//        SqlType value= DMLTool.convertToValue(attribute,str,t.getPropertyMap(),t.getTableDescriptor().getColumnDescriptorList());
//        Table table=compare(t,attribute,type,value);
//        return table;
//    }




}
