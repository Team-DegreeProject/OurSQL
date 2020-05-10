package com.ucd.oursql.sql.execution.data;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.FromStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.Table;

import java.util.List;

//1.2 删除表中的多个行记录
//1.2.1 DELETE FROM departments WHERE employee_id IN ( 100, 101, 102);
//1.2.2 DELETE FROM departments WHERE employee_id BETWEEN 100 AND 102;
//1.2.3 DELETE FROM departments WHERE employee_id =/> /< 7 AND/OR C = ‘D’;
public class DeleteDataStatement {

    List statement;

    public DeleteDataStatement(List tokens){
        statement=tokens;
    }


    //1.1 SQL 删除表中的一行
    //1.1.1 DELETE FROM departments WHERE department_id = 16;
    //1.1.2 DELETE FROM departments WHERE department_name = ‘a’;
//    public Table deleteDataBasicImpl(List condition,Table table) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
////        String tablename=((Token)statement.get(2)).image;
////        Table table= FromStatement.from(tablename);
////        ColumnDescriptor cd=td.getPrimaryKey().getColumnDescriptor(name);
//        Table change= WhereStatament.basicCondition(table,condition);
//        return change;
//    }

    //1.2.1 DELETE FROM departments WHERE employee_id IN (100, 101, 102);
//    public Table deleteDataInImpl(List condition,Table table) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
//        Table change=WhereStatament.inCondition(table,condition);
//        return change;
//    }
//
//    //1.2.2 DELETE FROM departments WHERE employee_id BETWEEN 100 AND 102;
//    public  Table deleteDataBetweenImpl(List condition,Table table) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
//        Table change=WhereStatament.betweenCondition(table,condition);
//        return change;
//    }


    public int deleteDataImpl() throws Exception {
        String tablename=((Token)statement.get(2)).image;
        Table table= FromStatement.from(ExecuteStatement.db.getDatabase(),tablename);
        List conditions= (List) statement.get(4);
        Table change=WhereStatament.whereImpl(table,conditions);
//        Object first=conditions.get(0);
//        if(first instanceof Token){
//            System.out.println("one condition==========");
//            change=checkAType(conditions,table);
//        }else if (first instanceof List){
//            System.out.println("multiple condition==========");
//            boolean b=false;
//            for(int i=0;i<conditions.size();i++){
//                Object o=conditions.get(i);
//                if(o instanceof List){
//                    //DELETE FROM departments WHERE employee_id =/> /< 7 AND/OR C = ‘D’;
//                    Table temp=checkAType((List) o,table);
//                    if(b){
//                        change=WhereStatament.whereAnd(temp,change);
//                    }else{
//                        change=WhereStatament.whereOr(temp,change);
//                    }
//                }else if(o instanceof Token){
//                    int type=((Token)o).kind;
//                    if(type==AND){
//                        b=true;
//                    }else if(type==OR){
//                        b=false;
//                    }
//                }
//            }
//        }
        change.printTable(null);
        table.deleteRows(change,1);
        String output=table.printTable(null);
        return 1;
    }


//    public Table checkAType(List condition,Table table) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        int type=((Token)condition.get(1)).kind;
//        Table change=null;
//        if(type==IN){
//            System.out.println("In===========");
//            change=deleteDataInImpl(condition,table);
//        }else if(type==EQ||type==LQ||type==RQ){
//            System.out.println("Basic===========");
//            change=deleteDataBasicImpl(condition,table);
//        }else if(type==BETWEEN){
//            System.out.println("Between===========");
//            change=deleteDataBetweenImpl(condition,table);
//        }
//        return change;
//    }

}
