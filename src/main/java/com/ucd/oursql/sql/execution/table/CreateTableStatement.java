package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.TableDescriptor;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;
import com.ucd.oursql.sql.table.type.SqlConstant;

import java.util.List;

import static com.ucd.oursql.sql.execution.DMLTool.analyseOneRow;
import static com.ucd.oursql.sql.table.TableSchema.BASE_TABLE_TYPE;

public class CreateTableStatement implements SqlConstant {

    List statement;

    public CreateTableStatement(List l){
        statement=l;
    }

    public String createImpl() throws Exception {
        ColumnDescriptorList columns=new ColumnDescriptorList();
        DataTypeDescriptor tp=new DataTypeDescriptor(PRIMARY_KEY,false);
        ColumnDescriptor columnp=new ColumnDescriptor("primary_key",0,tp);
        columns.add(columnp);
        TableDescriptor td=null;
        if(statement==null){
            return null;
        }
        String tableName=((Token)statement.get(2)).image;
        List<List> attributes= (List) statement.get(3);
        for(int i=0;i<attributes.size();i++){
            boolean tc=isTableConstraint(attributes.get(i),columns);
            if(!tc) {
                ColumnDescriptor column = analyseOneRow(1, attributes.get(i), i + 1);
                columns.add(column);
//                DataTypeDescriptor dataTypeDescriptor = column.getType();
            }
        }
        td=new TableDescriptor(tableName,BASE_TABLE_TYPE,columns);
        td.setTableInColumnDescriptor(td);
        td.updatePriamryKey();
        Table table=new Table(td);
        ExecuteStatement.db.insertTable(table);
        String output=ExecuteStatement.db.printDatabase()+"\n"+table.printTable(null);
//        td.printTableDescriptor();
        return output;
    }

    public boolean isTableConstraint(List row,ColumnDescriptorList columnDescriptorList){
        boolean b=false;
        Token t= (Token) row.get(0);
        if(t.kind==PRIMARY_KEY){
            for(int i=1;i<row.size();i++){
                String columnname=((Token)row.get(i)).image;
                ColumnDescriptor cd=columnDescriptorList.getColumnDescriptor(columnname);
                cd.getType().setPrimaryKey(true);
            }
            b=true;
        }
        return b;
    }
}
