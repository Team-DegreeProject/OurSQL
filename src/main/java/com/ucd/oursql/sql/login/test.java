package com.ucd.oursql.sql.login;

import com.ucd.oursql.sql.login.RegristrationFunc;
import com.ucd.oursql.sql.storage.Storage.TreeSaver;

public class test {
    public static void main(String[] args) {
        TreeSaver a = new TreeSaver();
        a.deleteTable("tt");
    }
}
