package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.other.FromStatement;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.table.ColumnDescriptorList;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.column.ColumnDescriptor;
import com.ucd.oursql.sql.table.column.DataTypeDescriptor;

import java.util.List;

import static com.ucd.oursql.sql.execution.DMLTool.analyseOneRow;
import static com.ucd.oursql.sql.parsing.SqlParserConstants.*;

public class AlterTableStatement {

    List statement;

    public AlterTableStatement(List tokens){
        statement=tokens;
    }

    //2.1 ALTER TABLE ADD 列
    //2.1.1 ALTER TABLE tbname ADD new_column data_type [AFTER existing_column]
    //2.1.2 ALTER TABLE tbname ADD new_column INT NOT NULL;
    //2.1.3 ALTER TABLE tbname ADD new_column NUMERIC(20,3) AFTER course_name,
    //ADD max_limit INT AFTER course_name;
    //2.1.4 在 ALTER TABLE 子句之后指定要添加 table_name，表示列所在的表
    //2.1.5 将新列定义放在 ADD 子句之后。 如果要在表中指定新列的顺序，可以使用可选子句 AFTER existing_column
    public Table alterTableAddColumnStatement() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(3);
        Table change= FromStatement.from(name);
        ColumnDescriptorList columns=new ColumnDescriptorList();
        int size=change.getTableDescriptor().getMaxColumnID()+1;
        for(int i=0;i<newColumns.size();i++){
//            String columnName=((Token)newColumns.get(i).get(1)).image;
            ColumnDescriptor column=analyseOneRow(0,newColumns.get(i),size+i);
            columns.add(column);
            DataTypeDescriptor dataTypeDescriptor=column.getType();
        }
        change.addColumns(columns);
        change.getTableDescriptor().printColumnName();
        return change;
    }

    //2.2 ALTER TABLE MODIFY // MODIFY 子句用于更改现有列的某些属性
    //2.2.1 ALTER TABLE tbname MODIFY column_definition;
    //2.2.2 ALTER TABLE tbname MODIFY fee NUMERIC (10,2) NOT NULL;
    public Table alterModifyImpl() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(4);
        Table change= FromStatement.from(name);
        change.modifyColumns(newColumns);
        change.getTableDescriptor().printColumnName();
        return change;
    }


    //2.3 ALTER TABLE DROP
    //2.3.1 删除一个或者多个列
    //2.3.1.1 ALTER TABLE tbname DROP COLUMN column_name, DROP COLUMN
    //column_name.
    //2.3.1.2 ALTER TABLE tbname DROP COLUMN fee;
    public Table alterTableDropImpl() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(3);
        Table change= FromStatement.from(name);
        change.dropColumns(newColumns);
        change.getTableDescriptor().printColumnName();
        return change;
    }

    public String alterTableImpl() throws ClassNotFoundException {
        Table change=null;
        Object o=statement.get(3);
        if(o instanceof Token){
            int type=((Token)statement.get(3)).kind;
            if (type == MODIFY) {
                change=alterModifyImpl();
            }
        }else if (o instanceof List){
            List<List> l= (List) statement.get(3);
            int t=((Token)l.get(0).get(0)).kind;
            if(t== ADD){
                change=alterTableAddColumnStatement();
            }else if (t==DROP){
                change=alterTableDropImpl();
            }
        }
        change.getTableDescriptor().printTableDescriptor();
        return change.printTable(null);
    }

}
