package com.ucd.oursql.sql.test;
import com.ucd.oursql.sql.driver.OurSqlDriver;

import java.sql.*;

public class TestDriver {
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        String URL = "jdbc:OurSql";
        String USER = "user1";
        String PASSWORD = "test";
        Connection conn=null;
        PreparedStatement pst=null;
        try {
            Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
//            OurSqlDriver osd=new OurSqlDriver();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement st=conn.createStatement();
//            st.executeQuery("create database tt;");
            String sql="create database ?;";
            pst=conn.prepareStatement(sql);
            pst.setString(1,"tt");
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(pst!=null){
            pst.close();
            if(conn!=null) {
                conn.close();
            }
        }
    }
}
