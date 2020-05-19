package com.ucd.oursql.sql.execution.table;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import com.ucd.oursql.sql.execution.other.WhereStatament;
import com.ucd.oursql.sql.parsing.Token;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;
import com.ucd.oursql.sql.storage.Storage.descriptorLoader;
import com.ucd.oursql.sql.storage.Storage.descriptorSaver;
import com.ucd.oursql.sql.table.Table;
import com.ucd.oursql.sql.table.type.text.SqlVarChar;

import java.util.ArrayList;
import java.util.List;

import static com.ucd.oursql.sql.parsing.SqlParserConstants.EQ;


//5 RENAME TABEL
//5.1 RENAME tbname TO tbname1；
public class RenameTableStatement {

    List statement;

    public RenameTableStatement(List tokens){
        statement=tokens;
    }

    public int renameTableImpl() throws Exception {

        Table database=ExecuteStatement.db.getDatabase();
        String oldName=((Token)statement.get(2)).image;
        String newName=((Token)statement.get(4)).image;
        String[] att={"tablename"};
        List values=new ArrayList();
//        att.add("table");
//        att.add("tablename");

        descriptorLoader descriptorLoader=new descriptorLoader();
        Table testDB=descriptorLoader.loadFromFile(newName,ExecuteStatement.user.getUserName());
        if(testDB!=null){
            throw new Exception("Error: There is a folder with the same name.");
        }

        Table change= WhereStatament.compare(database,"tablename",EQ,new SqlVarChar(oldName));
        values.add(new SqlVarChar(newName));
        boolean bool=database.updateTable(att,values,change,1);
        if(bool==false){
//            database.printTable(null);
            throw new Exception("Error:Rename");
//            return "Rename Table Wrong!";
        }

        descriptorLoader dl=new descriptorLoader();
        Table t=new Table(dl.loadFromFile(oldName,ExecuteStatement.user.getUserName()));
        t.getTableDescriptor().setTableName(newName);
        descriptorSaver ds=new descriptorSaver(t.getTd(),t.getPropertyMap(),t.getTree(),ExecuteStatement.user.getUserName());
        ds.saveAll();
        TreeSaver tl= new TreeSaver();
//        System.out.println(databaseName);
        tl.deleteTable(oldName,ExecuteStatement.user.getUserName());
//        Table t=database.getDatabase();


//        List list= (List) change.getTree().getDatas();
//        CglibBean c= (CglibBean) list.get(0);
//        Table table= new Table((Table) c.getValue("table"));
//        String[] namestt={"table"};
//        values=new ArrayList();
//        values.add(table);
//        table.getTableDescriptor().setTableName(newName);
//        bool=database.updateTable(namestt,values,change);
        ExecuteStatement.updateDB();
        String output=ExecuteStatement.db.printDatabase();
        return 1;
    }

}
