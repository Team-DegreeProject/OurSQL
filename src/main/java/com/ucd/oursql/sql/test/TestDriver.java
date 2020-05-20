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
            st.executeUpdate("create TABLE person(id int primary key,date1 date,a1 bigint, a2 double, a3 float,a4 varchar);");
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            java.util.Date date = sdf.parse( "2015-5-6 10:30:00" );
            long lg = date.getTime(); // 日期 转 时间戳
            String sql="INSERT into person (id,date1,a1,a2,a3,a4) values (?,?,?,?,?,?);";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,3);
            pst.setDate( 2, new java.sql.Date( lg ) );
            pst.setLong(3,34);
            pst.setDouble(4,5.6);
            pst.setFloat(5,4.6f);
            pst.setString(6,"dfnvdjdfbmldbm");
            pst.executeUpdate();
            ResultSet rs=st.executeQuery("select * from person;");
            System.out.println(rs);
            st.close();
            pst.close();
            conn.close();
            while (rs.next()) {
                int id=rs.getInt("id");
                Date date1=rs.getDate("date1");
                Long A1=rs.getLong("a1");
                Double A2=rs.getDouble("a2");
                Float A3=rs.getFloat("a3");
                String A4=rs.getString("a4");
                System.out.println(id+" "+date1+" "+A1+" "+A2+" "+A3+" "+A4);
                System.out.println("id is: "+id);
            }
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
