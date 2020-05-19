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
        acc=new account("user2","user2");
        rf.register(acc);
        acc=new account("user3","user3");
        rf.register(acc);
        acc=new account("user4","user4");
        rf.register(acc);
    }
}
