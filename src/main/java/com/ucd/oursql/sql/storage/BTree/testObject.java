package com.ucd.oursql.sql.storage.BTree;

import java.sql.SQLOutput;

public class testObject {
    public String a;

    public testObject() {
    }

    public String getA() {
        return a;
    }

    public static void main(String[] args) {
        testObject testObject = new testObject();
        System.out.println(testObject.getA());
    }
}
