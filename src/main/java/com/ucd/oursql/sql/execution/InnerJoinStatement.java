package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;

import java.util.List;

public class InnerJoinStatement {

    public static Table innerJoinImpl(List<List<Token>> tokens) throws ClassNotFoundException {
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
                up=innerTwoTable(up,now);
            }
        }
        return up;
    }

    public static Table innerTwoTable(Table t1,Table t2) throws ClassNotFoundException {
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
        System.out.println("size:"+l1.size());
        for(int i=0;i<l1.size();i++){
            CglibBean c1=l1.get(i);
            Comparable pk= (Comparable) c1.getValue("primary key");
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
                b3.insert(cn, pk);
            }
        }
        DMLTool.updateColumnPosition(t3);
        return t3;

    }


}
