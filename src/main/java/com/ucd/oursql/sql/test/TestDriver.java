package com.ucd.oursql.sql.test;
import com.ucd.oursql.sql.driver.OurSqlDriver;

import java.sql.*;

public class TestDriver {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String URL = "jdbc:OurSql";
        String USER = "xxx";
        String PASSWORD = "xxx";
        Connection conn=null;
        try {
            Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
//            OurSqlDriver osd=new OurSqlDriver();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement st=conn.createStatement();
//            st.executeQuery("create database tt;");
            String sql="create database ?;";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,"tt");
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
