package com.ucd.oursql.sql.execution;

import com.ucd.oursql.sql.execution.other.LeftJoinStatement;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.BPlusTree;
import com.ucd.oursql.sql.table.BTree.BPlusTreeTool;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.SqlType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class DMLTool {
//    public static void andCondition(HashMap condition, char t, int num){
//        if(t=='>'){
//            if(condition.get('>')==null){
//                condition.put('>',num);
//            }else{
//                int g=(int)condition.get('>');
//                if(num>g){
//                    condition.replace('>',num);
//                }
//            }
//        }else if(t=='<'){
//            if(condition.get('<')==null){
//                condition.put('<',num);
//            }else{
//                int g=(int)condition.get('<');
//                if(num<g){
//                    condition.replace('<',num);
//                }
//            }
//        }else if(t=='='){
//            if(condition.get('=')==null){
//                condition.put('=',num);
//            }else{
//                System.out.println("Warning:There are two =");
//            }
//        }
//
//    }
//
//    public static void orCondition(HashMap condition,char t,int num){
//        if(t=='>'){
//            if(condition.get('>')==null){
//                condition.put('>',num);
//            }else{
//                int g=(int)condition.get('>');
//                if(num<g){
//                    condition.replace('>',num);
//                }
//            }
//        }else if(t=='<'){
//            if(condition.get('<')==null){
//                condition.put('<',num);
//            }else{
//                int g=(int)condition.get('<');
//                if(num>g){
//                    condition.replace('<',num);
//                }
//            }
//        }else if(t=='='){
//            if(condition.get('=')==null){
//                condition.put('=',num);
//            }else{
//                System.out.println("Warning:There are two =");
//            }
//        }
//    }
    public static boolean checkManyToOne(List<List<Token>> columns) throws Exception {
        boolean isConvergeToOne=false;
        for(int i=0;i<columns.size();i++){
            List<Token> column=columns.get(i);
            if(column.get(0).kind==MAX||column.get(0).kind==MIN||column.get(0).kind==SUM||column.get(0).kind==AVG){
                isConvergeToOne=true;
                break;
            }
        }
        if(isConvergeToOne){
            for(int i=0;i<columns.size();i++){
                List<Token> column=columns.get(i);
                if(column.get(0).kind==ID){
                    throw new Exception("Error:Calculated columns can not be mixed with other normal clomuns!");
                }
            }
        }
        return isConvergeToOne;
    }

    public static BPlusTree dealWithCalculatedColumns(BPlusTree bPlusTree,List<List<Token>> columns,HashMap propertyMap,TableDescriptor td) throws Exception {
        List<CglibBean> datas=bPlusTree.getDatas();
        CglibBean start=datas.get(0);
        HashMap<String,Integer> pairs=new HashMap();
//        HashMap<String,Integer> types=new HashMap<>();
        BPlusTree newTree=new BPlusTree();
        List<String> countName=new ArrayList<>();
        List<String> aveName=new ArrayList<>();
        CglibBean cs=new CglibBean(DMLTool.convertPropertyMap(propertyMap));
        for(int i=0;i<columns.size();i++){
            String columnName=columns.get(i).get(1).image;
            int type=columns.get(i).get(0).kind;
            SqlType v= (SqlType) start.getValue(columnName);
            pairs.put(columnName,type);
            cs.setValue(columnName,v);
            if(type==COUNT){
                countName.add(columnName);
            }else if(type==AVG){
                aveName.add(columnName);
            }
//            types.put(columnName,columns.get(i).get(0).kind);
        }
        cs.setValue("primary_key",start.getValue("primary_key"));
        newTree.insert(cs, (Comparable) cs.getValue("primary_key"));

        BPlusTreeTool.printBPlusTree(newTree,propertyMap);

//        HashMap ca=new HashMap();

        if(datas.size()>1){
            for(int i=1;i<datas.size();i++){
                CglibBean cr=new CglibBean(DMLTool.convertPropertyMap(propertyMap));
                CglibBean cd= datas.get(i);
                CglibBean cn= (CglibBean) newTree.getDatas().get(0);
                Iterator it=pairs.keySet().iterator();
                while(it.hasNext()){
                    String name= (String) it.next();
                    SqlType vd= (SqlType) cd.getValue(name);
                    SqlType vn= (SqlType) cn.getValue(name);
                    int tp=pairs.get(name);
                    if(tp==MAX){
                        if(vd==null){
                            cn.setValue(name,vn);
                        }else if(vn==null){
                            cn.setValue(name,vd);
                        }else if(vd.compareTo(vn)>0){
                            cn.setValue(name,vd);
                        }
                    }else if(tp==MIN){
                        if(vd==null){
                            cn.setValue(name,vn);
                        }else if(vn==null){
                            cn.setValue(name,vd);
                        }else if(vd.compareTo(vn)<0){
                            cn.setValue(name,vd);
                        }
                    }else if(tp==AVG||tp==SUM){
//                        System.out.println(name);
//                        System.out.println("vn:"+vn);
//                        System.out.println("vd:"+vd);
                        if(vd==null){
                            cn.setValue(name,vn);
                        }else if(vn==null){
                            cn.setValue(name,vd);
                        }else{
                            vn=vn.add(vd);
                            cn.setValue(name,vn);
                        }
                    }
                }
            }
        }


        int count=datas.size();
        CglibBean cn= (CglibBean) newTree.getDatas().get(0);
        for(int i=0;i<aveName.size();i++){
            SqlType value= (SqlType) cn.getValue(aveName.get(i));
            if(value!=null){
                value.ave(count);
            }
        }


        return newTree;

//        for(int i=0;i<countName.size();i++){
//            cn.setValue(countName.get(i),count);
//        }

    }

    public static SqlType convertToValue(String att, String str, HashMap propertyMap,ColumnDescriptorList columnDescriptorList) throws Exception {
//        Iterator it=propertyMap.keySet().iterator();
//        while(it.hasNext()){
//            String s= (String) it.next();
//            System.out.println(s+"==="+propertyMap.get(s));
//        }
//        System.out.println(att);
//        columnDescriptorList.printColumnDescriptorList();
        Class c= Class.forName((String) propertyMap.get(att));
        SqlType value=(SqlType)c.newInstance();
//        System.out.println("convert"+str);
        if(str.equals("null")){
            value=null;
        }else{
            value.setValue(str,propertyMap,columnDescriptorList,att);
            ColumnDescriptor cd=columnDescriptorList.getColumnDescriptor(att);
            DataTypeDescriptor dataTypeDescriptor=cd.getType();
            if(dataTypeDescriptor.getScale()!=-1){
                value.setScale(dataTypeDescriptor.getScale());
            }
            if(dataTypeDescriptor.getPrecision()!=-1){
                value.setPrecision(dataTypeDescriptor.getPrecision());
            }
            value.updateValue();
        }
        return value;
    }

    public static SqlType forXMLConvertStringToValue(String att, String str, HashMap propertyMap,ColumnDescriptorList columnDescriptorList) throws Exception {
        Class c= Class.forName((String) propertyMap.get(att));
        SqlType value=(SqlType)c.newInstance();
        if(str.equals("null")){
            value=null;
        }else {
            value.setValue(str, propertyMap, columnDescriptorList, att);
            ColumnDescriptor cd = columnDescriptorList.getColumnDescriptor(att);
            DataTypeDescriptor dataTypeDescriptor = cd.getType();
            if (dataTypeDescriptor.getScale() != -1) {
                value.setScale(dataTypeDescriptor.getScale());
            }
            if (dataTypeDescriptor.getPrecision() != -1) {
                value.setPrecision(dataTypeDescriptor.getPrecision());
            }
            value.updateValue();
        }
        return value;
    }

    public static  HashMap convertPropertyMap(HashMap propertyMap) throws ClassNotFoundException {
        HashMap r=new HashMap();
        Iterator it=propertyMap.keySet().iterator();
        while(it.hasNext()){
            String s= (String) it.next();
            System.out.println(s+"    ===    "+propertyMap.get(s));
            Class c=Class.forName((String) propertyMap.get(s));
            r.put(s,c);
        }
//        System.out.println("44444444444444444");
//        Iterator i=r.keySet().iterator();
//        while(i.hasNext()){
//            String s= (String) i.next();
//            System.out.println(s+":"+r.get(s));
//        }
        return r;
    }

//    public static  HashMap convertPropertyMapBack(HashMap propertyMap){
//        HashMap r=new HashMap();
//        Iterator it=propertyMap.keySet().iterator();
//        while(it.hasNext()){
//            String s=(String)it.next();
//            Class c= (Class) propertyMap.get(s);
//            String v=c.getName();
//            r.put(s,v);
//        }
//        return r;
//    }


    public static ColumnDescriptor analyseOneRow(int k, List tokens,int position){
        DataTypeDescriptor dataType=null;
        ColumnDescriptor column=new ColumnDescriptor(position);
        boolean comment=false;
        String columnName=null;
        if(k==0){
            columnName=((Token)tokens.get(1)).image;
            dataType= new DataTypeDescriptor( ((Token)tokens.get(2)).kind  );
            for(int i=3;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                if(comment){
                    column.setComment(t.image);
                    comment=false;
                }else {
                    comment = setType(dataType, t, column);
                }
            }
        }else if(k==1){
            columnName=((Token)tokens.get(0)).image;
            dataType= new DataTypeDescriptor( ((Token)tokens.get(1)).kind  );
            for(int i=2;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                if(comment){
                    column.setComment(t.image);
                    comment=false;
                }else {
                    comment = setType(dataType, t, column);
                }
            }
        }
        column.setColumnName(columnName);
        column.setColumnType(dataType);
        return column;
    }

    public static boolean setType(DataTypeDescriptor d, Token t, ColumnDescriptor cd){
        if(t.kind==PRIMARY_KEY){
            d.setPrimaryKey(true);
        }else if(t.kind==NOT_NULL){
            d.setNullable(false);
        }else if(t.kind==NUMBER){
            if(d.getScale()==-1){
                d.setScale(Integer.parseInt(t.image));
            }else{
                d.setPrecision(Integer.parseInt(t.image));
            }
        }else if(t.kind==AUTO_INCREMENT){
            cd.setAutoincInc(true);
        }else if(t.kind==COMMENT){
            return true;
        }
        return false;
    }

    public static String removeQutationMark(String str){
        str=str.substring(1,str.length()-1);
        return str;
    }


    public static HashMap selectNewPropertyMap(HashMap propertyMap,List<List<Token>> tokens){
        HashMap newProperty=new HashMap();
        for(int i=0;i<tokens.size();i++){
            String name=tokens.get(i).get(0).image;
            Object o=propertyMap.get(name);
            newProperty.put(name,o);
        }
        Object pk=propertyMap.get("primary_key");
        newProperty.put("primary_key",pk);
        return newProperty;
    }

    //select中对于列操作的支持函数
    public static TableDescriptor changeTableDescriptor(TableDescriptor td,List<List<Token>> tokens) throws Exception {
        if(checkAllSelected(tokens)){
            TableDescriptor tt=new TableDescriptor(td.getName(),td.getSchema(),td.getColumnDescriptorList().getNewColumnDescriptorList());
            tt.updatePriamryKey();
            return tt;
        }
        ColumnDescriptorList list=td.getColumnDescriptorList();
        ColumnDescriptor pk=list.getColumnDescriptor("primary_key");
        ColumnDescriptorList newList=new ColumnDescriptorList();
        newList.add(pk);
        for(int i=0;i<list.size();i++){
            String name=list.elementAt(i).getColumnName();
            for(int j=0;j<tokens.size();j++){
                Token t=tokens.get(j).get(0);
                if(t.kind==ID){
                    String com=tokens.get(j).get(0).image;
                    if(name.equals(com)){
//                    ColumnDescriptor cc=list.elementAt(i);
                        newList.add(list.elementAt(i));
                        break;
                    }
                }else{
                    String com=tokens.get(j).get(1).image;
                    if(name.equals(com)){
                        newList.add(list.elementAt(i));
                        break;
                    }
                }

            }
        }
        newList.printColumnDescriptorList();
//        System.out.println(newList.);
        TableDescriptor tableDescriptor=new TableDescriptor(td.getName(),td.getSchema(),newList.getNewColumnDescriptorList());
        tableDescriptor.updatePriamryKey();
        tableDescriptor.printTableDescriptor();
        return tableDescriptor;
    }


    public static boolean  checkAllSelected(List<List<Token>> tokens){
        for(int i=0;i<tokens.size();i++){
            Token t=tokens.get(i).get(0);
            if(t.kind==ASTERISK ||t.kind==ALL){
                return true;
            }
        }
        return false;
    }

    public static void changeAs(TableDescriptor tableDescriptor,List<List<Token>> tokens){
        int type=0;
        List<List<Token>> change=new ArrayList<>();
        ColumnDescriptorList columnDescriptors=tableDescriptor.getColumnDescriptorList();
        for(int i=0;i<tokens.size();i++){
            if(tokens.get(i).size()>1){
                Token a=tokens.get(i).get(1);
                if(a.kind==AS){
                    change.add(tokens.get(i));
                    type=1;
                }else if(a.kind==ID){
                    change.add(tokens.get(i));
                    type=2;
                }
            }
        }
        if (type==0){
            return;
        }
        for(int i=0;i<change.size();i++){
            if(type==1){
                String name=change.get(i).get(0).image;
                String c=change.get(i).get(2).image;
                columnDescriptors.getColumnDescriptor(name).setColumnName(c);
            }else if(type==2){
                String name=change.get(i).get(0).image;
                String c=change.get(i).get(1).image;
                columnDescriptors.getColumnDescriptor(name).setColumnName(c);
            }
        }
    }

    public static List getColumnNamesFromPropertyMap(HashMap propertyMap){
        List names=new ArrayList();
        Iterator it=propertyMap.keySet().iterator();
        while(it.hasNext()){
            names.add(it.next());
        }
        return names;
    }

    public static void checkChangeTableName(Table t,List<List<Token>> from){
        if(from.size()>1){
            return;
        }
        for(int i=0;i<from.size();i++){
            List<Token> l=from.get(i);
            if(l.size()>1){
                Token a= l.get(1);
                if(a.kind==AS){
                    String oldname=l.get(0).image;
                    String newname=l.get(2).image;
                    if(oldname.equals(t.getTableDescriptor().getName())){
                        t.getTableDescriptor().setTableName(newname);
                    }

                }else if(a.kind==ID){
                    String oldname=l.get(0).image;
                    String newname=l.get(1).image;
                    if(oldname.equals(t.getTableDescriptor().getName())){
                        t.getTableDescriptor().setTableName(newname);
                    }
                }
            }
        }
    }

    public static TableDescriptor changeTD(TableDescriptor td1,TableDescriptor td2) throws Exception {
//        System.out.println("td1");
//        td1.printTableDescriptor();
//        System.out.println("td2");
//        td2.printTableDescriptor();
        ColumnDescriptorList columnDescriptors1=td1.getColumnDescriptorList();
        ColumnDescriptorList columnDescriptors2=td2.getColumnDescriptorList();
        ColumnDescriptorList newColums=columnDescriptors1.getNewColumnDescriptorList();
        for(int i=0;i<columnDescriptors2.size();i++){
            String name=columnDescriptors2.elementAt(i).getColumnName();
            if(columnDescriptors1.getColumnDescriptor(name)==null){
                newColums.add(columnDescriptors2.getColumnDescriptor(i).getNewColumnDescripter());
            }
        }
//        System.out.println("newColum");
//        newColums.printColumnDescriptorList();
        TableDescriptor td=new TableDescriptor(td1.getName()+"+"+td2.getName(),td1.getSchema(),newColums);
        td.updatePriamryKey();
//        System.out.println("td");
//        td.printTableDescriptor();
        return td;

    }

    public static void updateColumnPosition(Table t){
        ColumnDescriptorList cd=t.getTableDescriptor().getColumnDescriptorList();
        for(int i=0;i<cd.size();i++){
            cd.elementAt(i).setPosition(i);
        }
    }

    //    public void setTablePrimaryKey(TableDescriptor td){
//        ColumnDescriptorList pk=td.getPrimaryKey();
//        pk=new ColumnDescriptorList();
//        ColumnDescriptorList cdl=td.getColumnDescriptorList();
//        for(int i=0;i<cdl.size();i++){
//            ColumnDescriptor c=cdl.elementAt(i);
//            boolean b=c.getType().isPrimaryKey();
//            if(b){
//                pk.add(c);
//            }
//        }
//    }
}
