package com.ucd.oursql.sql.system;

public class User {
    private String userName;
    private int userId;
    private UserAccessedDatabases userAccessedDatabases;
    public User(int id,String name) {
        userId=id;
        userName=name;
        try {
            userAccessedDatabases=new UserAccessedDatabases();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserAccessedDatabases(UserAccessedDatabases userAccessedDatabases) {
        this.userAccessedDatabases = userAccessedDatabases;
    }

    public UserAccessedDatabases getUserAccessedDatabases() {
        return userAccessedDatabases;
    }
}
