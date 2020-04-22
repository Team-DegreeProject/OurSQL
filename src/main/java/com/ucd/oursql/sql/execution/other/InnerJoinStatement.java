package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.type.PrimaryKey;

import java.util.HashMap;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class InnerJoinStatement {

    public static Table innerJoinStartImpl(List<List<Token>> tokens) throws ClassNotFoundException {
        if(tokens.size()<2){
            String tablename= tokens.get(0).get(0).image;
            Table table= FromStatement.from(tablename);
            return table;
        }
        Table up=null;
        boolean first=true;
        for(int i=0;i<tokens.size();i++){
            List<Token> oneTable=tokens.get(i);
            if(first){
                up= FromStatement.from(oneTable.get(0).image);
                first=false;
            }else{
                Table now=FromStatement.from(oneTable.get(0).image);
                up=innerTwoStartTable(up,now);
            }
        }
        return up;
    }



    public static Table innerJoinImpl(Table t1,Table t2,List<Token> on) throws ClassNotFoundException {
        String tablename=t2.getTableDescriptor().getName();
        HashMap hashMap=new HashMap();
        for(int i=0;i<on.size();i++){
            Token t=on.get(i);
            if(t.kind!=EQ){
                String name=t.image;
                String[] temp=name.split("\\.");
//                System.out.println(name+"--->"+temp.length+"---------->"+temp.toString());
                if(temp[0].equals(tablename)){
                    hashMap.put(2,temp[1]);
                }else{
                    hashMap.put(1,temp[1]);
                }
            }
        }
        TableDescriptor newTD= DMLTool.changeTD(t1.getTd(),t2.getTd());
        Table t3=new Table();
        t3.setTd(newTD);
        t3.createTable();
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree b3=t3.getTree();
        TableDescriptor td=t3.getTableDescriptor();
        List<String> columns=td.getColumnNamesList();
        List<CglibBean> l1=b1.getDatas();
        List<CglibBean> l2=b2.getDatas();
        for(int i=0;i<l1.size();i++){
            CglibBean c1=l1.get(i);
            Comparable com1= (Comparable) c1.getValue((String) hashMap.get(1));
            CglibBean c2=null;
            for(int j=0;j<l2.size();j++){
                CglibBean temp= l2.get(j);
                Comparable com2= (Comparable) temp.getValue((String) hashMap.get(2));
                if(com1.compareTo(com2)==0){
                    c2=temp;
                    break;
                }
            }
            if(c2!=null){
                CglibBean cn=new CglibBean(t3.getPropertyMap());
                for(int j=0;j<columns.size();j++){
                    String column=columns.get(j);
                    if(!column.equals("primary key")){
                        if(c2.getValue(column)!=null){
                            cn.setValue(column,c2.getValue(column));
                        }else{
                            cn.setValue(column,c1.getValue(column));
                        }
                    }
                }
                PrimaryKey pk= (PrimaryKey) c1.getValue("primary key");
                cn.setValue("primary key",pk);
                b3.insert(cn, pk);
            }
        }
        DMLTool.updateColumnPosition(t3);
        t3.updatePrimaryKey();
        return t3;
    }



    public static Table innerTwoStartTable(Table t1,Table t2) throws ClassNotFoundException {
        TableDescriptor newTD=DMLTool.changeTD(t1.getTd(),t2.getTd());
        Table t3=new Table();
        t3.setTd(newTD);
        t3.createTable();
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree b3=t3.getTree();
        TableDescriptor td=t3.getTableDescriptor();
        List<String> columns=td.getColumnNamesList();
        List<CglibBean> l1=b1.getDatas();
        List<CglibBean> l2=b2.getDatas();
//        System.out.println("size:"+l1.size()+","+l2.size());
        for(int i=0;i<l1.size();i++){
            CglibBean c1=l1.get(i);
//            System.out.println("====================="+c1);
            Comparable pk= (Comparable) (c1.getValue("primary key"));
//            System.out.println("====================="+pk);
//            System.out.println(b2.select(pk)+"=============");
            CglibBean c2= (CglibBean) b2.select(pk);
            if(c2!=null){
                CglibBean cn=new CglibBean(t3.getPropertyMap());
                for(int j=0;j<columns.size();j++){
                    String column=columns.get(j);
                    if(!column.equals("primary key")){
                        if(c2.getValue(column)!=null){
                            cn.setValue(column,c2.getValue(column));
                        }else{
                            cn.setValue(column,c1.getValue(column));
                        }
                    }

                }
                cn.setValue("primary key",pk);
//                System.out.println(cn+"=============="+pk);
                b3.insert(cn, pk);
            }
        }
//        t3.printTable(null);
        DMLTool.updateColumnPosition(t3);
//        t3.printTable(null);
//        t3.setTree(b3);
        return t3;

    }


}
