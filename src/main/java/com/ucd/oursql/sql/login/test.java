package com.ucd.oursql.sql.login;

import com.ucd.oursql.sql.login.RegristrationFunc;

public class test {
    public static void main(String[] args) {
        RegristrationFunc test = new RegristrationFunc();
        test.register(new account("a","b"));
        loginFunc testlogin = new loginFunc();
        System.out.println(testlogin.login(new account("a","b")));


    }
}
