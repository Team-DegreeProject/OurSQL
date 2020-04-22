package com.ucd.oursql.sql.execution.other;

import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.Distinct;
import com.ucd.oursql.sql.table.type.PrimaryKey;

import java.util.HashMap;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.DISTINCT;
import static com.ucd.oursql.sql.parsing.SqlParserConstants.PRIMARY_KEY;
import static com.ucd.oursql.sql.table.type.SqlConstantImpl.sqlMap;

public class DistinctStatement {

    public static Table distinctImpl(Table t,List<String> names) throws ClassNotFoundException {
        if(names.size()==0){
            return t;
        }
        HashMap property=new HashMap();
        property.put("distinct",Class.forName(sqlMap.get(DISTINCT)));
        property.put("primary key",Class.forName(sqlMap.get(PRIMARY_KEY)));
        List datas=t.getTree().getDatas();
        BPlusTree tree=new BPlusTree();
        for(int i=0;i<datas.size();i++){
            CglibBean c= (CglibBean) datas.get(i);
            CglibBean nc=new CglibBean(property);
            Distinct dc=dc=new Distinct();
            for(int j=0;j<names.size();j++){
                Comparable com= (Comparable) c.getValue(names.get(j));
                dc.addDistinct(names.get(j),com);
            }
            nc.setValue("distinct",dc);
            nc.setValue("primary key",c.getValue("primary key"));
//            dc.printDistinct();
            tree.insert(nc,dc);
        }
//        BPlusTreeTool.printBPlusTree(tree,property);
        BPlusTree nt=new BPlusTree();
        BPlusTree ot=t.getTree();
        List sd=tree.getDatas();
//        BPlusTreeTool.printBPlusTree(ot,t.getPropertyMap());
//        t.printTable(null);
        for(int i=0;i<sd.size();i++){
            CglibBean c= (CglibBean) sd.get(i);
            PrimaryKey pk= (PrimaryKey) c.getValue("primary key");
            CglibBean co= (CglibBean) ot.select(pk);
//            System.out.println(pk);
//            pk.printPK();
//            System.out.println(co);
            if(co!=null){
                nt.insert(co,pk);
            }
        }
        t.setTree(nt);
//        t.printTable(null);
        return t;
    }

}
