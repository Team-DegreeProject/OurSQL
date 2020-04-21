package com.ucd.oursql.sql.execution;


import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;

import java.util.*;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.ASC;
import static com.ucd.oursql.sql.parsing.SqlParserConstants.DESC;

public class OrderByStatement {
    static HashMap<Comparable,List> sameValue=new HashMap();

    public static List orderByImpl(Table table, List<List<Token>> lists, Table t) {


        if (lists == null) {
            System.out.println("case null");
            return null;
        }



        System.out.println("case change");
        List datas = t.getTree().getDatas();
        datas=sortData(datas,lists);
        datas=returnDatas(table,datas);
//        for(int i=0;i<lists.size();i++){
//            String name=lists.get(i).get(0).image;
//            if(i==0){
//                datas=sortData(datas,name);
//            }else{
//                int s=-1;
//                int e=-1;
//                boolean in=false;
//                boolean re=false;
//                for(int j=0;j<datas.size()-1;j++){
//                    String oname=lists.get(i-1).get(0).image;
//                    CglibBean c1= (CglibBean) datas.get(j);
//                    CglibBean c2=(CglibBean)datas.get(j+1);
//                    Comparable com= (Comparable) c1.getValue(oname);
//                    Comparable com2= (Comparable) c2.getValue(oname);
//                    if(com.compareTo(com2)==0&&s==-1&&in==false){
//                        s=j;
//                        in=true;
//                    }
//                    if(com.compareTo(com2)!=0&&in==true){
//                        e=j;
//                        in=false;
//                        re=true;
//                    }
//                    if(re==true){
//                        List temp=new ArrayList();
//                        for(int k=s;k<e;k++){
//                            temp.add(datas.get(k));
//                        }
//                        temp=sortData(temp,name);
//                        for(int k=s;k<e;k++){
//                            datas.
//                        }
//                    }
//                }
//            }
//
//        }

        return datas;


//        List datas=table.getTree().getDatas();
//        List<BPlusTree> trees = null;
//        BPlusTree tree=orderByOneElement(0,lists,table.getTree().getDatas());
//        trees.add(tree);
//        int j=1;
//        while(j<lists.size()){
//            String name=lists.get(j).get(0).image;
//            List l=sameValue.get(name);
//
//            tree=orderByOneElement(j,lists,tree.getDatas());
//            trees.add(tree);
//        }
//
//        List list;
//        if(type){
//            list=temp.getDatas();
//        }else {
//            list=temp.getReverseDatas();
//        }
//
//        Iterator it=sameValue.keySet().iterator();
//        while (it.hasNext()) {
//            String k= (String) it.next();
//            List l=sameValue.get(k);
//            l.add(temp.select(k));
//            Collections.sort(l, new Comparator<CglibBean>() {
//
//                @Override
//                public int compare(CglibBean o1, CglibBean o2) {
//                    String name="";
//                    // 升序
//                    //return o1.getAge()-o2.getAge();
//                    return ((Comparable)o1.getValue(name)).compareTo((Comparable)o2.getValue(name));
//                    // 降序
//                    // return o2.getAge()-o1.getAge();
//                    // return o2.getAge().compareTo(o1.getAge());
//                }
//            });
//        }
//
//        return list;
    }

    public static List returnDatas(Table t,List data){
        BPlusTree tree=t.getTree();
        List<CglibBean> list=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            CglibBean c= (CglibBean) data.get(i);
            Comparable pk= (Comparable) c.getValue("primary key");
            CglibBean c2= (CglibBean) tree.select(pk);
            if(c2!=null){
                list.add(c2);
            }
        }
        return list;
    }

    public static boolean checkAsc(List<Token> lists){
        for(int i=0;i<lists.size();i++){
            Token o=lists.get(i);
            if(o.kind==ASC){
                return true;
            }else if(o.kind==DESC){
                return false;
            }
        }
        return true;
    }

//    public static BPlusTree orderByOneElement(int j,List<List<Token>> lists,List datas){
//        BPlusTree temp=new BPlusTree();
//        sameValue=new HashMap();
//        String name=lists.get(j).get(0).image;
////        boolean type=checkAsc(lists.get(j));
//        for(int i=0;i<datas.size();i++){
//            CglibBean c= (CglibBean) datas.get(i);
//            Object t=temp.select((Comparable) c.getValue(name));
//            if(t==null){
//                temp.insert(c, (Comparable) c.getValue(name));
//            }else{
//                sameValue.get((Comparable) c.getValue(name)).add(c);
//            }
//        }
//        return temp;
//    }

    public static List sortData(List temp,List<List<Token>> names){
        Collections.sort(temp, new Comparator<CglibBean>() {
            @Override
            public int compare(CglibBean o1, CglibBean o2) {
                // 升序
                //return o1.getAge()-o2.getAge();
                int out=0;
                for(int i=0;i<names.size();i++){
                    String name=names.get(i).get(0).image;
                    boolean b=checkAsc(names.get(i));
                    if(b){
                        out=((Comparable) o1.getValue(name)).compareTo((Comparable) o2.getValue(name));
                    }else{
                        out=((Comparable) o2.getValue(name)).compareTo((Comparable) o1.getValue(name));
                    }
                    if(out!=0){
                        break;
                    }
                }
                return out;
                // 降序
                // return o2.getAge()-o1.getAge();
                // return o2.getAge().compareTo(o1.getAge());
            }
        });
        return temp;
    }
}
