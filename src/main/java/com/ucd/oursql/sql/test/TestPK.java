package com.ucd.oursql.sql.test;

import com.ucd.oursql.sql.login.RegristrationFunc;
import com.ucd.oursql.sql.login.account;
import com.ucd.oursql.sql.table.type.PrimaryKey;

public class TestPK {
    public static void main(String[] args) {
//        String str="name:wyx;type:1";
//        PrimaryKey pk=new PrimaryKey();
//        try {
//            pk.setValue(str,null,null,null);
//            System.out.println(pk.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        account acc=new account("user","test");
        RegristrationFunc rf=new RegristrationFunc();
        rf.register(acc);
    }
}
