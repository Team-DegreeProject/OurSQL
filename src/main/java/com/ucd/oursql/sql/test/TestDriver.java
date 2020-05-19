package com.ucd.oursql.sql.test;
import com.ucd.oursql.sql.driver.OurSqlDriver;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestDriver {
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        String URL = "jdbc:OurSql";
        String USER = "user2";
        String PASSWORD = "user2";
        Connection conn=null;
        PreparedStatement pst=null;
        try {
            Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
//            OurSqlDriver osd=new OurSqlDriver();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement st=conn.createStatement();
            Statement st=conn.createStatement();
//            st.executeUpdate("create database tt;");
            st.executeUpdate("use tt;");
//            st.executeUpdate("create TABLE person(ID int primary key,date1 date );");
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            java.util.Date date = sdf.parse( "2015-5-6 10:30:00" );
            long lg = date.getTime(); // 日期 转 时间戳
            String sql="INSERT into person (ID,date1) values (?,?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,3);
            pst.setDate( 2, new java.sql.Date( lg ) );
            pst.executeUpdate();
//            Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
//            OurSqlDriver osd=new OurSqlDriver();
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement st=conn.createStatement();
//            st.executeQuery("create database tt;");
//            String sql="create database ?;";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,"tt");
//            pst.executeUpdate();

        } catch (SQLException | ClassNotFoundException | ParseException e) {
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
