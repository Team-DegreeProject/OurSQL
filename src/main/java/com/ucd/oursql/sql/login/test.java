package com.ucd.oursql.sql.login;

import com.ucd.oursql.sql.login.RegristrationFunc;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;

public class test {
    public static void main(String[] args) {
        RegristrationFunc testRegristration = new RegristrationFunc();
        testRegristration.register(new account("user2","user2"));
        testRegristration.register(new account("user3","user3"));
        testRegristration.register(new account("user4","user4"));
    }
}
