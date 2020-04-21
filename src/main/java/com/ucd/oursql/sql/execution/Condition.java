package com.ucd.oursql.sql.execution;

public class Condition {
    private String name;
    private String type;
    private String value;
    public Condition(String n,String t,String v){
        name=n;
        type=t;
        value=v;
    }
    public void setName(String n){
        name=n;
    }
    public void setType(String t){
        type=t;
    }
    public void setValue(String v){
        value=v;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getValue(){
        return value;
    }

}
