package com.ucd.oursql.sql.execution.data;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.ucd.oursql.sql.driver.OurSqlResultset;
import com.ucd.oursql.sql.execution.ExecuteStatement;

import com.ucd.oursql.sql.execution.other.*;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.BTree.CglibBean;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class SelectDataStatement {

    public List statement;

    public SelectDataStatement(List tokens){
        statement=tokens;
    }

    public OurSqlResultset selectDataImpl() throws Exception {
//        HashMap from=getFrom();

        List<List<Token>> tablenames= (List<List<Token>>) statement.get(3);
//        String tablename= tablenames.get(0).get(0).image;
//        Table table= FromStatement.from(tablename);
        Table table= dealWithFrom();
//        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary_key")).printPK();
//        table.printTable(null);

        List<List<Token>> whereConsition=getWhhereToken();
        table=WhereStatament.whereImpl(table,whereConsition);

//        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary_key")).printPK();
//        table.printTable(null);

        List distinctNames=checkDistinct();
//        table.printTable(null);
        Table show=DistinctStatement.distinctImpl(table,distinctNames);
//        System.out.println("====from===");
//        show.printTable(null);

//        System.out.println("==========after where===========");
//        show.printTable(null);
        List<List<Token>> columns= getColumns();
        show=show.selectSomeColumns(tablenames,columns);
//        System.out.println("===========after select==========");
//        show.printTable(null);
//        System.out.println("====from===");
//        show.printTable(null);
//        show.printTable(null);


        List<List<Token>> orderbys=getOrderByLists();
        List datas=OrderByStatement.orderByImpl(show,orderbys,table);

        Token off=checkOffset();
        Token limit=checkLimit();
        Token fetch=checkRowFetch();
        datas=dealWithLimit(datas,limit,off,fetch);


        if(datas==null){
            datas=show.getTree().getDatas();
        }
        String output=show.printTable(datas);

        System.out.println("=================12345=====================");
        System.out.println(output);

        OurSqlResultset rs=new OurSqlResultset(datas,show.getPropertyMap());
//        System.out.println("testRs:"+rs.getInt("id"));

        return rs;
    }

    public Table selectDataImplIn() throws Exception {
//        HashMap from=getFrom();

        List<List<Token>> tablenames= (List<List<Token>>) statement.get(3);
//        String tablename= tablenames.get(0).get(0).image;
//        Table table= FromStatement.from(tablename);
        Table table= dealWithFrom();
//        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary_key")).printPK();
//        table.printTable(null);

        List<List<Token>> whereConsition=getWhhereToken();
        table=WhereStatament.whereImpl(table,whereConsition);

//        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary_key")).printPK();
//        table.printTable(null);

        List distinctNames=checkDistinct();
//        table.printTable(null);
        Table show=DistinctStatement.distinctImpl(table,distinctNames);
//        System.out.println("====from===");
//        show.printTable(null);

//        System.out.println("==========after where===========");
//        show.printTable(null);
        List<List<Token>> columns= getColumns();
        show=show.selectSomeColumns(tablenames,columns);
//        System.out.println("===========after select==========");
//        show.printTable(null);
//        System.out.println("====from===");
//        show.printTable(null);
//        show.printTable(null);


        List<List<Token>> orderbys=getOrderByLists();
        List datas=OrderByStatement.orderByImpl(show,orderbys,table);

        Token off=checkOffset();
        Token limit=checkLimit();
        Token fetch=checkRowFetch();
        datas=dealWithLimit(datas,limit,off,fetch);


        if(datas==null){
            datas=show.getTree().getDatas();
        }
        String output=show.printTable(datas);

        System.out.println("=================12345=====================");
        System.out.println(output);

//        OurSqlResultset rs=new OurSqlResultset(datas,show.getPropertyMap());
//        System.out.println("testRs:"+rs.getInt("id"));

        return show;
    }


    public List dealWithLimit(List cs,Token l,Token off,Token rowFetch) throws Exception {
        if(off!=null){
            int offset=Integer.parseInt(off.image);
            if(l!=null){
                int limit=Integer.parseInt(l.image);
                List ncs=new ArrayList();
                for(int i=offset;i<limit+offset;i++){
                    ncs.add(cs.get(i));
                }
                return ncs;
            }else if(rowFetch!=null){
                int fetch=Integer.parseInt(rowFetch.image);
                List ncs=new ArrayList();
                for(int i=offset;i<offset+fetch;i++){
                    ncs.add(cs.get(i));
                }
                return ncs;
            } else{
                throw new Exception("Error: No limit or fetch");
//                return cs;
            }
        }else{
            if(l!=null){
                int limit=Integer.parseInt(l.image);
                List ncs=new ArrayList();
                for(int i=0;i<limit;i++){
                    ncs.add(cs.get(i));
                }
                return ncs;
            }else if(rowFetch!=null){
                int fetch=Integer.parseInt(rowFetch.image);
                List ncs=new ArrayList();
                for(int i=0;i<fetch;i++){
                    ncs.add(cs.get(i));
                }
                return ncs;
            } else{
                return cs;
            }
        }
    }

    public Token checkOffset(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==OFFSET){
                    return (Token) statement.get(i+1);
                }
            }
        }
        return null;
    }

    public Token checkLimit(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==LIMIT){
                    return (Token) statement.get(i+1);
                }
            }
        }
        return null;
    }

    public Token checkRowFetch(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==FETCH){
                    return (Token) statement.get(i+2);
                }
            }
        }
        return null;
    }


    public List checkDistinct() throws Exception {
        List<List<Token>> list= getColumns();
        List re=new ArrayList();
        for(int i=0;i<list.size();i++){
            List<Token> l=list.get(i);
            Token t=l.get(0);
            if(t.kind==DISTINCT){
                String name="";
                if(l.size()==2){
                    name=l.get(1).image;
                }else if(l.size()==3){
                    name=l.get(3).image;
                }else if(l.size()==4){
                    name=l.get(4).image;
                }
                re.add(name);
                l.remove(0);
            }
        }
        return re;
    }


    public List<List<Token>> getColumns() throws Exception {
        Object o=statement.get(1);
        if(o instanceof Token){
            if(((Token) o).kind==ASTERISK ||((Token) o).kind==ALL){
                return changeReturnList(o);
            }
        }else if(o instanceof List){
            return (List<List<Token>>) o;
        }
        throw new Exception("Error: No column");
//        return null;
    }

    public List changeReturnList(Object o){
        List ret=new ArrayList();
        List re=new ArrayList();
        re.add(o);
        ret.add(re);
        return ret;
    }

    public List getOrderByLists(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                if(((Token) o).kind==ORDER_BY){
                    return (List) statement.get(i+1);
                }
            }
        }
        return null;
    }

    public HashMap getFrom() throws Exception {
        List re=new ArrayList();
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                if(((Token) o).kind==FROM){
                    HashMap hm=getJoin(i);
                    return hm;
                }
            }
        }
        throw new Exception("Error:No from");
//        return null;
    }

    public HashMap getJoin(int s){
        HashMap hashMap=new HashMap();
        List names=new ArrayList();
        HashMap inner= new HashMap();
        HashMap left= new HashMap();
        HashMap right= new HashMap();
        HashMap full=new HashMap();
        List start= (List) statement.get(s+1);
        names.add(start);

        for(int i=s+2;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==INNER || t.kind==CROSS){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    inner.put(rl,on);
                }else if(t.kind==LEFT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    left.put(rl,on);
                }else if(t.kind==RIGHT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    right.put(rl,on);
                }else if(t.kind==FULL){
                    Token nt= (Token) statement.get(i+2);
                    List fl=new ArrayList();
                    fl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(fl);
                    full.put(fl,on);
                }
            }
        }
        hashMap.put("names",names);
        hashMap.put("start",start);
        hashMap.put("inner",inner);
        hashMap.put("left",left);
        hashMap.put("right",right);
        hashMap.put("full",full);
        return hashMap;
    }

    public Table dealWithFrom() throws Exception {
        HashMap from=getFrom();
        List<List<Token>> names= (List<List<Token>>) from.get("names");
        List<List<Token>> start= (List<List<Token>>) from.get("start");
        HashMap inner= (HashMap) from.get("inner");
        HashMap left= (HashMap) from.get("left");
        HashMap right= (HashMap) from.get("right");
        HashMap full=(HashMap)from.get("full");
        Table table= InnerJoinStatement.innerJoinStartImpl(start);
//        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary key")).printPK();

        for(int i=1;i<names.size();i++){
            List<Token> name=names.get(i);
            if(inner.get(name)!=null){
                List<Token> on= (List<Token>) inner.get(name);
                Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
                table=InnerJoinStatement.innerJoinImpl(table,t2,on);
//                System.out.println("inner");
//                table.printTable(null);
            }else if(left.get(name)!=null){
//                List<Token> name= (List<Token>) lit.next();
                List<Token> on= (List<Token>) left.get(name);
                Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
                table= LeftJoinStatement.leftJoinImpl(table,t2,on);
//                System.out.println("left");
//                table.printTable(null);
            }else if(right.get(name)!=null){
                List<Token> on= (List<Token>) right.get(name);
                Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
                table= RightJoinStatement.rightJoinImpl(table,t2,on);
//                System.out.println("right");
//                table.printTable(null);
            }else if(full.get(name)!=null){
                List<Token> on= (List<Token>) full.get(name);
                Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
                table= FullJoinStatement.fullJoinImpl(table,t2,on);
            }
        }

//        Iterator iit= inner.keySet().iterator();
//        Iterator lit=left.keySet().iterator();
//        Iterator rit=right.keySet().iterator();
//        while(iit.hasNext()){
//            List<Token> name= (List<Token>) iit.next();
//            List<Token> on= (List<Token>) inner.get(name);
//            Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
////            System.out.println("++++++++++++++"+on.size());
////            for(int i=0;i<on.size();i++){
////                System.out.println(on.get(i).image);
////            }
//            table=InnerJoinStatement.innerJoinImpl(table,t2,on);
//            System.out.println("inner");
//            table.printTable(null);
//        }
////        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary key")).printPK();
//        while(lit.hasNext()){
//            List<Token> name= (List<Token>) lit.next();
//            List<Token> on= (List<Token>) left.get(name);
//            Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
//            table= LeftJoinStatement.leftJoinImpl(table,t2,on);
//            System.out.println("left");
//            table.printTable(null);
//        }
////        ((PrimaryKey)((CglibBean)table.getTree().getDatas().get(0)).getValue("primary key")).printPK();
//        while(rit.hasNext()){
//            List<Token> name= (List<Token>) rit.next();
//            List<Token> on= (List<Token>) right.get(name);
//            Table t2= FromStatement.from(ExecuteStatement.db.getDatabase(),name.get(0).image);
//            table= RightJoinStatement.rightJoinImpl(table,t2,on);
//            System.out.println("right");
//            table.printTable(null);
//        }
////        table.printTable(null);
        return table;

    }

    public List<List<Token>> getWhhereToken(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==WHERE){
                    return (List<List<Token>>) statement.get(i+1);
                }
            }
        }
        return null;
    }



}
