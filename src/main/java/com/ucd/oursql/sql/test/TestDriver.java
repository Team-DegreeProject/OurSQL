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
            Statement st=conn.createStatement();
            st.executeUpdate("create database tt;");
            st.executeUpdate("use tt;");
//            st.executeUpdate("drop table person;");
            st.executeUpdate("create TABLE person(id int primary key,date1 date,date2 time,date3 timestamp ,a1 bigint, a2 double, a3 float,a4 varchar);");
            Date time1=new Date(System.currentTimeMillis());
            Time time2=new Time(System.currentTimeMillis());
            Timestamp time3=new Timestamp(System.currentTimeMillis());
            String sql="INSERT into person (id,date1,date2,date3,a1,a2,a3,a4) values (?,?,?,?,?,?,?,?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,3);
            pst.setDate( 2, time1);
            pst.setTime(3,time2);
            pst.setTimestamp(4,time3);
            pst.setLong(5,45);
            pst.setDouble(6,5.6);
            pst.setFloat(7,4.6f);
            pst.setString(8,"dfnvdjdfbmldbm");
            pst.executeUpdate();
            ResultSet rs=st.executeQuery("select * from person;");
            System.out.println(rs);
            while (rs.next()) {
                int id=rs.getInt("id");
                Date date1=rs.getDate("date1");
                Time date2=rs.getTime("date2");
                Timestamp date3=rs.getTimestamp(("date3"));
                Long A1=rs.getLong("a1");
                Double A2=rs.getDouble("a2");
                Float A3=rs.getFloat("a3");
                String A4=rs.getString("a4");
                System.out.println(id+" "+date1+" "+date2+" "+date3+" "+A1+" "+A2+" "+A3+" "+A4);
            }
//            Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
//            OurSqlDriver osd=new OurSqlDriver();
//            conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement st=conn.createStatement();
//            st.executeQuery("create database tt;");
            String sql2="create database ?;";
            pst=conn.prepareStatement(sql);
            pst.setString(1,"ttt");
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
