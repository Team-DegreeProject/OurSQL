package com.ucd.oursql.sql.storage.Storage;

public class Student {
    String name;
    int id;
    Student next;

    public Student(String n,int i){
        name = n;
        id = i;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setNext(Student n ){
        next = n;
    }
}
