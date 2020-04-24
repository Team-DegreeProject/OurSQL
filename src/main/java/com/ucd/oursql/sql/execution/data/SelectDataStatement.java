package com.ucd.oursql.sql.execution.data;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.ucd.oursql.sql.execution.other.*;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.Table;
import javafx.scene.control.Tab;
import jdk.nashorn.internal.objects.annotations.Where;

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

    public String selectDataImpl() throws Exception {
//        HashMap from=getFrom();

        List<List<Token>> tablenames= (List<List<Token>>) statement.get(3);
//        String tablename= tablenames.get(0).get(0).image;
//        Table table= FromStatement.from(tablename);
        Table table= dealWithFrom();
//        table.printTable(null);

        List<List<Token>> whereConsition=getWhhereToken();
        table=WhereStatament.whereImpl(table,whereConsition);
//        table.printTable(null);

        List distinctNames=checkDistinct();
//        table.printTable(null);
        Table show=DistinctStatement.distinctImpl(table,distinctNames);
//        table.printTable(null);
//        show.printTable(null);

        show.printTable(null);
        List<List<Token>> columns= getColumns();
        show=show.selectSomeColumns(tablenames,columns);
//        show.printTable(null);


        List<List<Token>> orderbys=getOrderByLists();
        List datas=OrderByStatement.orderByImpl(show,orderbys,table);

        String output=show.printTable(datas);
//        table.printTable(null);

        System.out.println("=================12345=====================");
        System.out.println(output);
        return output;
    }

    public List checkDistinct(){
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



    public List<List<Token>> getColumns(){
        Object o=statement.get(1);
        if(o instanceof Token){
            if(((Token) o).kind==ASTERISK ||((Token) o).kind==ALL){
                return changeReturnList(o);
            }
        }else if(o instanceof List){
            return (List<List<Token>>) o;
        }
        return null;
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

    public HashMap getFrom(){
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
        return null;
    }

    public HashMap getJoin(int s){
        HashMap hashMap=new HashMap();
//        List names=new ArrayList();
        HashMap inner= new HashMap();
        HashMap left= new HashMap();
        HashMap right= new HashMap();
        List start= (List) statement.get(s+1);
//        names.add(start);

        for(int i=s+2;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==INNER){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
//                    names.add(rl);
                    inner.put(rl,on);
                }else if(t.kind==LEFT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
//                    names.add(rl);
                    left.put(rl,on);
                }else if(t.kind==RIGHT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
//                    names.add(rl);
                    right.put(rl,on);
                }
            }
        }
//        hashMap.put("names",names);
        hashMap.put("start",start);
        hashMap.put("inner",inner);
        hashMap.put("left",left);
        hashMap.put("right",right);
        return hashMap;
    }

    public Table dealWithFrom() throws ClassNotFoundException {
        HashMap from=getFrom();
        List<List<Token>> names= (List<List<Token>>) from.get("names");
        List<List<Token>> start= (List<List<Token>>) from.get("start");
        HashMap inner= (HashMap) from.get("inner");
        HashMap left= (HashMap) from.get("left");
        HashMap right= (HashMap) from.get("right");
        Table table= InnerJoinStatement.innerJoinStartImpl(start);
        Iterator iit= inner.keySet().iterator();
        Iterator lit=left.keySet().iterator();
        Iterator rit=right.keySet().iterator();
        while(iit.hasNext()){
            List<Token> name= (List<Token>) iit.next();
            List<Token> on= (List<Token>) inner.get(name);
            Table t2= FromStatement.from(name.get(0).image);
//            System.out.println("++++++++++++++"+on.size());
//            for(int i=0;i<on.size();i++){
//                System.out.println(on.get(i).image);
//            }
            table=InnerJoinStatement.innerJoinImpl(table,t2,on);
        }
        while(lit.hasNext()){
            List<Token> name= (List<Token>) lit.next();
            List<Token> on= (List<Token>) left.get(name);
            Table t2= FromStatement.from(name.get(0).image);
            table= LeftJoinStatement.leftJoinImpl(table,t2,on);
        }
        while(rit.hasNext()){
            List<Token> name= (List<Token>) lit.next();
            List<Token> on= (List<Token>) left.get(name);
            Table t2= FromStatement.from(name.get(0).image);
            table= RightJoinStatement.rightJoinImpl(table,t2,on);
        }
//        table.printTable(null);
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
