package com.ucd.oursql.sql.table;

import com.sun.org.apache.xerces.internal.xs.StringList;
import com.ucd.oursql.sql.execution.DMLTool;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.PrimaryKey;
import com.ucd.oursql.sql.table.type.SqlConstantImpl;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.ucd.oursql.sql.execution.DMLTool.analyseOneRow;

public class Table extends SqlConstantImpl {

    private TableDescriptor td;
    private BPlusTree tree=new BPlusTree<>(4);;
    private HashMap propertyMap = new HashMap();


    public Table(){}

    public Table(TableDescriptor td, BPlusTree tree, HashMap propertyMap) {
        this.td = td;
        this.tree = tree;
        this.propertyMap = propertyMap;
    }

    public Table(TableDescriptor td, BPlusTree b) throws ClassNotFoundException {
        this.td=td;
        tree = b;
    }



    public Table(TableDescriptor td) throws ClassNotFoundException {
        this.td=td;
//        tree = new BPlusTree<>(4);
        createTable(td);
    }



    public Table(Table t){
        this.td=t.td;
        this.tree=t.tree;
        this.propertyMap=t.propertyMap;
    }



    public void setTableDescriptor(TableDescriptor td) { this.td = td; }

    public void setTree(BPlusTree tree) { this.tree = tree; }

    public TableDescriptor getTableDescriptor(){return td;}

    public BPlusTree getTree() { return this.tree; }

    public void setTd(TableDescriptor td) {
        this.td = td;
    }

    public TableDescriptor getTd() {
        return td;
    }

    public void setPropertyMap(HashMap propertyMap) {
        this.propertyMap = propertyMap;
    }


    public HashMap createTable(TableDescriptor table) throws ClassNotFoundException {
        ColumnDescriptorList list=table.getColumnDescriptorList();
//        System.out.println(list.size()+"================");
        propertyMap=new HashMap();
        for(int i=0;i<list.size();i++){
            ColumnDescriptor cd=list.getColumnDescriptor(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.getTypeId()));
            propertyMap.put(cd.getColumnName(),sqlMap.get(dtd.getTypeId()));
        }
        descriptorSaver ds=new descriptorSaver(td,propertyMap,tree);
        ds.saveAll();
        return propertyMap;
    }

    public HashMap createTable() throws ClassNotFoundException {
        ColumnDescriptorList list=td.getColumnDescriptorList();
//        list.printColumnDescriptorList();
        for(int i=0;i<list.size();i++){
            ColumnDescriptor cd=list.elementAt(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.getTypeId()));
            propertyMap.put(cd.getColumnName(), sqlMap.get(dtd.getTypeId()));
        }
        descriptorSaver ds=new descriptorSaver(td,propertyMap,tree);
        ds.saveAll();
        return propertyMap;
    }



//已知所有值以固定顺序排列好,通常用为插入系统表格
    public boolean insertARow(List values) throws Exception {
//        System.out.println("insertATable");
        String[] attributes=td.getColumnNamesArray();
        if(attributes.length!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }


        boolean ub=checkUniqueOperationInsert(values);
        if(ub==false){
            return false;
        }
//        System.out.println("5555555555");
//        Iterator it=propertyMap.keySet().iterator();
//        while(it.hasNext()){
//            String s= (String) it.next();
//            System.out.println(s+":"+propertyMap.get(s));
//        }
        CglibBean bean = new CglibBean(DMLTool.convertPropertyMap(propertyMap));
        for(int i=0;i<attributes.length;i++){
//            System.out.println(attributes[i]+"--->"+values.get(i));
            bean.setValue(attributes[i], DMLTool.convertToValue(attributes[i],values.get(i).toString(),propertyMap,td.getColumnDescriptorList()));
//            System.out.println(bean.getValue(attributes[i]));
        }
        tree.insert(bean, (Comparable) bean.getValue("primary_key"));//双primarykey

//        System.out.println("11111111111111");
//        this.printTable(null);

        descriptorSaver ds=new descriptorSaver(td,propertyMap,tree);
        ds.saveAll();
        return true;
    }



    public void insertRows(List<Token> attributes, List list, int start) throws Exception {
       for(int i=start;i<list.size();i++){
           List<List<Token>> l= (List<List<Token>>) list.get(i);
           insertARow(attributes,l);
       }
        descriptorSaver ds=new descriptorSaver(td,propertyMap,tree);
        ds.saveAll();
    }



    public boolean insertARow(List<Token> attributes,List<List<Token>> values) throws Exception {

        if(attributes.size()!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }

        boolean checkPk=td.getPrimaryKey().checkHavePrimaryKey(attributes);
        if(checkPk==false){
            System.out.println("There is no enough primaryKey values.");
            return false;
        }

        boolean checkNull=td.getColumnDescriptorList().checkNotNull(attributes,values);
        System.out.println(td.getColumnDescriptorList().size());
        if(checkNull==false){
            System.out.println("Some attribute is not null.");
            return false;
        }

        ColumnDescriptorList auto=td.getColumnDescriptorList().getAutoIncrementList();
        List maxValues=getMaxValues(auto);
        ColumnDescriptorList columnDescriptorList=td.getColumnDescriptorList();

        ColumnDescriptorList unique=td.getColumnDescriptorList().getUniqueList();
        int number=values.get(0).size();

        boolean ub=checkUniqueOperation(attributes,values);
        if(ub==false){
            return false;
        }

//        System.out.print("maxValue:");
//        for(int i=0;i<maxValues.size();i++){
//            System.out.print(maxValues.get(i));
//        }
//        System.out.println("");

        for(int j=0;j<number;j++){
            PrimaryKey pk=new PrimaryKey();
            CglibBean bean = new CglibBean(propertyMap);

            //处理输入
            for(int i=0;i<attributes.size();i++){
                String name=attributes.get(i).image;
                String v=values.get(i).get(j).image;
                SqlType value=DMLTool.convertToValue(name,v,propertyMap,columnDescriptorList);
                ColumnDescriptor cd=td.getPrimaryKey().getColumnDescriptor(name);
                if(cd!=null){
                    pk.addPrimaryKey(name,value);
                    System.out.println("primary_key: "+value);
                }
                bean.setValue(name, value);
                System.out.println(name+"--->>"+value);
            }

            //处理自增
            for(int i=0;i<auto.size();i++){
                String name=auto.elementAt(i).getColumnName();
                SqlType value= ((SqlType) maxValues.get(i)).addOne();
                bean.setValue(name,value);
            }


            bean.setValue("primary_key",pk);
            tree.insert(bean, (Comparable) bean.getValue("primary_key"));//双primarykey
        }
        return true;
    }



    public String printTable(List list){
        String str="||"+td.getName()+"||\n"+"-------------------------------------------------------\n";
        System.out.println("||"+td.getName()+"||");
        System.out.println("-------------------------------------------------------");
        if(list==null){
            String t=BPlusTreeTool.printBPlusTree(tree,td);
            str=str+t;
        }else{
            String t=BPlusTreeTool.printBPlusTree(list,td);
            str=str+t;
        }
        return str;

    }



    public boolean updateTable(String[] attributes,List values,Table t){
//        System.out.println(values.size()+"  ==  "+t.getTree().getDataNumber());
        if(t.getTree().getDataNumber()==0 ){
            System.out.println("No update");
            return false;
        }
        if(attributes.length!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }

        boolean ub=checkUniqueOperationUpdate(attributes,values);
        if(ub==false){
            return false;
        }

        List list1=t.getTree().getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            for(int j=0;j<attributes.length;j++){
                c.setValue(attributes[j],values.get(j));
            }
        }
        updatePrimaryKey();
        descriptorSaver ds=new descriptorSaver(this.td,this.propertyMap,this.tree);
        ds.saveAll();
        return true;
    }



    public boolean updateTable(List changes,Table t) throws Exception {
        if(t.getTree().getDataNumber()==0 ){
            System.out.println("No update");
            return false;
        }
//        t.getTableDescriptor().updatePriamryKey();
        ColumnDescriptorList columnDescriptors=td.getColumnDescriptorList();
        List list1=t.getTree().getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            for(int j=0;j<changes.size();j++){
                List now= (List) changes.get(j);
                String attribute=((Token)now.get(0)).image;
                String v=((Token)now.get(2)).image;
                SqlType value=DMLTool.convertToValue(attribute,v,propertyMap,columnDescriptors);
                c.setValue((String) attribute,value);
            }
        }
        updatePrimaryKey();
        return true;
    }



    public boolean updatePrimaryKey(){
        td.updatePriamryKey();
        ColumnDescriptorList pkn=td.getPrimaryKey();
//        System.out.println("update");
//        td.printTableDescriptor();
        List list1=tree.getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            PrimaryKey pk=new PrimaryKey();
            for(int k=0;k<pkn.size();k++){
                String name=pkn.elementAt(k).getColumnName();
                Comparable com= (Comparable) c.getValue( name);
                pk.addPrimaryKey(name,com);
            }
            c.setValue("primary_key",pk);
        }
        return true;
    }



    public boolean deleteRows(Table t){
        if(t.getTree().getDataNumber()==0){
            return false;
        }
//        this.printTable(null);
        List<CglibBean> list=t.getTree().getDatas();
        for(int i=0;i<t.size();i++){
            CglibBean c=list.get(i);
            Comparable pk= (Comparable) c.getValue("primary_key");
            tree.delete(pk);
        }
        descriptorSaver ds=new descriptorSaver(td,propertyMap,tree);
        ds.saveAll();
        return true;
    }



    public int size(){
        return tree.getDataNumber();
    }



    public boolean cleanAllData(){
        tree = new BPlusTree<>(4);
        return true;
    }



    public void addColumns(ColumnDescriptorList columns) throws ClassNotFoundException {
        BPlusTree newTree=new BPlusTree();
        ColumnDescriptorList list=td.getColumnDescriptorList();
        list.addColumns(columns);
        List<CglibBean> data=tree.getDatas();
        for(int i=0;i<columns.size();i++){
            ColumnDescriptor cd=columns.get(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.getTypeId()));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.getTypeId())));
        }
        for(int i=0;i<data.size();i++){
            CglibBean c=data.get(i);
            for(int j=0;j<columns.size();j++){
                c.setValue(columns.get(i).getColumnName(),null);
            }
            newTree.insert(c,(Comparable)c.getValue("primary_key"));
        }
        this.tree=newTree;
        td.updatePriamryKey();
        updatePrimaryKey();
    }



    public void modifyColumns(List<List> tokens) throws ClassNotFoundException {
        ColumnDescriptorList list=td.getColumnDescriptorList();
        ColumnDescriptorList primaryKeys=td.getPrimaryKey();
        for(int i=0;i<tokens.size();i++){
            String columnName=((Token)tokens.get(i).get(0)).image;
            ColumnDescriptor oldcd=list.getColumnDescriptor(columnName);
            int position=oldcd.getPosition();
            ColumnDescriptor column=analyseOneRow(1,tokens.get(i),position);
            DataTypeDescriptor dataTypeDescriptor=column.getType();
//            ColumnDescriptor column=new ColumnDescriptor(columnName,position,dataTypeDescriptor);
            list.dropColumn(columnName);
            list.add(column);
            propertyMap.replace(columnName,Class.forName(sqlMap.get(dataTypeDescriptor.getTypeId())));
            System.out.println(list.getColumnDescriptor(position).getType().toString());
        }
        td.updatePriamryKey();
        updatePrimaryKey();
    }



    public void dropColumns(List<List> tokens){
        ColumnDescriptorList list=td.getColumnDescriptorList();
        ColumnDescriptorList primaryKeys=td.getPrimaryKey();
        for(int i=0;i<tokens.size();i++){
            String columnName=((Token)tokens.get(i).get(2)).image;
            list.dropColumn(columnName);
            propertyMap.remove(columnName);
        }
        BPlusTree newTree=new BPlusTree();
        List<CglibBean> data=tree.getDatas();
        String[] columns=td.getColumnNamesArray();
        for(int i=0;i<data.size();i++){
            CglibBean c=data.get(i);
            CglibBean nc=new CglibBean();
            for(int j=0;j<columns.length;j++){
                Object o=c.getValue(columns[j]);
                nc.setValue(columns[j],o);
            }
            newTree.insert(nc,(Comparable)nc.getValue("primary_key"));
        }
        this.tree=newTree;
        td.updatePriamryKey();
        updatePrimaryKey();
    }



    public HashMap getPropertyMap(){
        return propertyMap;
    }



    public List getMaxValues(ColumnDescriptorList auto) throws IllegalAccessException, InstantiationException {
        List maxValues=new ArrayList();
        List<CglibBean> list=tree.getDatas();
//        System.out.println("==============size:"+list.size());
        for(int i=0;i<auto.size();i++){
            boolean first=true;
            String name=auto.get(i).getColumnName();
            Class cl= (Class) propertyMap.get(name);
            SqlType max=(SqlType)cl.newInstance();
            for(int j=0;j<list.size();j++){
                CglibBean c=list.get(j);
                SqlType temp= (SqlType) c.getValue(name);
                if(first){
                    max=temp;
                    first=false;
                }else{
                    int out=max.compareTo(temp);
                    if(out<0){
                        max=temp;
                    }
                }
            }
            maxValues.add(max);
        }
        return maxValues;
    }




    public boolean checkUniqueOperationInsert(List values){
        ColumnDescriptorList unique=td.getColumnDescriptorList().getUniqueList();
        String[] attributes=td.getColumnNamesArray();
        List list=tree.getDatas();
        for(int i=0;i<unique.size();i++){
            String name=unique.elementAt(i).getColumnName();
            int temp=-1;
            Object value=null;
            SqlType v=null;
            for(int j=0;j<attributes.length;j++){
                String str=attributes[j];
                if(str.equals(name)){
                    temp=j;
                    break;
                }
            }
            if(temp!=-1){
                value=  values.get(temp);
                for(int k=0;k<list.size();k++){
                    CglibBean bean = (CglibBean) list.get(k);
                    Comparable com= (Comparable) bean.getValue(name);
                    if(com.compareTo(value)==0){
                        System.out.println("Some attribute is unique");
                        return false;
                    }
                }
            }

        }
        return true;
    }




    public boolean checkUniqueOperationUpdate(String[] attributes,List values){
//        System.out.println(att);
//        List<String> attributes=td.getColumnNamesList();
        List list=tree.getDatas();
        for(int i=0;i<attributes.length;i++){
            boolean uq=td.getColumnDescriptorList().getColumnDescriptor(attributes[i]).isUnique();
            if(uq){
                Comparable value= (Comparable) values.get(i);
                for(int k=0;k<list.size();k++){
                    CglibBean bean = (CglibBean) list.get(k);
                    Comparable com= (Comparable) bean.getValue(attributes[i]);
                    if(com.compareTo(value)==0){
                        System.out.println("Some attribute is unique");
                        return false;
                    }
                }
            }
        }
        return true;
    }



    public boolean checkUniqueOperation(List<Token> attributes,List<List<Token>> values) throws Exception {
        ColumnDescriptorList unique=td.getColumnDescriptorList().getUniqueList();
        //处理unique
        for(int i=0;i<unique.size();i++){
            String name=unique.elementAt(i).getColumnName();
            int temp=-1;
            String value=null;
            SqlType v=null;
            for(int j=0;j<attributes.size();j++){
                String str=attributes.get(j).image;
                if(str.equals(name)){
                    temp=j;
                    break;
                }
            }
            if(temp!=-1){
                value=values.get(temp).get(0).image;
                v=DMLTool.convertToValue(name,value,propertyMap,td.getColumnDescriptorList());
                List list=tree.getDatas();
                for(int k=0;k<list.size();k++){
                    CglibBean bean = (CglibBean) list.get(k);
                    SqlType com= (SqlType) bean.getValue(name);
                    if(com.compareTo(v)==0){
                        System.out.println("Some attribute is unique");
                        return false;
                    }
                }
            }

        }
        return true;
    }



    public Table selectSomeColumns(List<List<Token>>from,List<List<Token>> tokens) throws ClassNotFoundException {
        TableDescriptor newTD=DMLTool.changeTableDescriptor(td,tokens);
//        System.out.println("NEWTD");
//        System.out.println("=================");
//        newTD.printTableDescriptor();
//        if(newTD==td){
//            return this;
//        }
        Table table=new Table();
        table.setTd(newTD);
//        System.out.println("NEWTABLE");
        DMLTool.changeAs(table.getTableDescriptor(),tokens);
//        System.out.println("NEWCOLUMN");
        HashMap property=table.createTable();
//        Iterator it=property.keySet().iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }
        BPlusTree ntree=BPlusTreeTool.getSubAttributes(td.getColumnDescriptorList(),newTD.getColumnDescriptorList(),tree,property);
        table.setTree(ntree);
//        System.out.println("NEWTREE");
        DMLTool.checkChangeTableName(table,from);
//        System.out.println("NEWNAME");
        return table;
    }


}
