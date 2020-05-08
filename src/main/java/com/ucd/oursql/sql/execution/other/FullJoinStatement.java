package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.type.PrimaryKey;

import java.util.HashMap;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;

public class FullJoinStatement {
    public static Table fullJoinImpl(Table t1, Table t2, List<Token> on) throws ClassNotFoundException {
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
//            if(c2!=null){
            CglibBean cn=new CglibBean(DMLTool.convertPropertyMap(t3.getPropertyMap()));
            for(int j=0;j<columns.size();j++){
                String column=columns.get(j);
                if(!column.equals("primary_key")){
                    if(c2!=null&&c2.getValue(column)!=null){
                        cn.setValue(column,c2.getValue(column));
                    }else if(c1.getValue(column)!=null){
                        cn.setValue(column,c1.getValue(column));
                    }else{
                        cn.setValue(column,null);
                    }
                }
//                }
                PrimaryKey pk= (PrimaryKey) c1.getValue("primary_key");
                cn.setValue("primary_key",pk);
                b3.insert(cn, pk);
            }
        }
        for(int i=0;i<l2.size();i++){
            CglibBean c1=l2.get(i);
            Comparable com1= (Comparable) c1.getValue((String) hashMap.get(1));
            CglibBean c2=null;
            for(int j=0;j<l1.size();j++){
                CglibBean temp= l1.get(j);
                Comparable com2= (Comparable) temp.getValue((String) hashMap.get(2));
                if(com1.compareTo(com2)==0){
                    c2=temp;
                    break;
                }
            }
            if(c2==null){
                CglibBean cn=new CglibBean(DMLTool.convertPropertyMap(t3.getPropertyMap()));
                for(int j=0;j<columns.size();j++){
                    String column=columns.get(j);
                    if(!column.equals("primary_key")){
                        if(c1.getValue(column)!=null){
                            cn.setValue(column,c1.getValue(column));
                        }else{
                            cn.setValue(column,null);
                        }
                    }
                    PrimaryKey pk= (PrimaryKey) c1.getValue("primary_key");
                    cn.setValue("primary_key",pk);
                    b3.insert(cn, pk);
                }
            }

        }
        DMLTool.updateColumnPosition(t3);
        t3.updatePrimaryKey();
//        System.out.println("============full join===========");
//        t3.printTable(null);
        return t3;
    }

}
