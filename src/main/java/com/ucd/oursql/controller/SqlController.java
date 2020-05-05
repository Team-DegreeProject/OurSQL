package com.ucd.oursql.controller;

import com.ucd.oursql.sql.parsing.ParseException;
import com.ucd.oursql.sql.parsing.SqlParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


@RestController
public class SqlController {
    private static int permission=0;
    String URL = "jdbc:OurSql";
    String USER;
    String PASSWORD;
    Connection conn=null;
    Statement st=null;
    @PostMapping(value="/Guest/command")
    public Object GetDesText(@RequestParam("command") String text) throws BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ParseException, SQLException {
        if(permission==0){
            String[] stringArray=text.split(" ");
            if(stringArray.length==2){
                USER=stringArray[0];
                PASSWORD=stringArray[1];
            }else{
                return "username and password not correct";
            }
            try {
                Class.forName("com.ucd.oursql.sql.driver.OurSqlDriver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement st=conn.createStatement();
                permission=1;
                return "Welcome!";
//                String sql="create database ?;";
//                pst=conn.prepareStatement(sql);
//                pst.setString(1,"tt");
//                pst.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "username and password not correct";
            }


//            }
        }
        if (text.equals("exit;")) {
            permission = 0;
            if (st != null) {
                st.close();
                if (conn != null) {
                    conn.close();
                }
            }
            return "Bye!";
        }
    ResultSet rs=st.executeQuery(text);
//        String[][] s={{"a","b","c"},{"d","e","f"},{"h","i","j"}};
//        InputStream target = new ByteArrayInputStream(text.getBytes());
//        SqlParser parser = new SqlParser(target);
//        String result=parser.parse();
        String[][] s=(String[][]) rs.getObject(0);
        return s;

    }

}
