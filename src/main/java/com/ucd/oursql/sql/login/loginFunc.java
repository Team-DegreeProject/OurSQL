package com.ucd.oursql.sql.login;

import com.ucd.oursql.sql.execution.ExecuteStatement;
import net.sf.cglib.reflect.FastClass;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.util.List;

public class loginFunc {

    public boolean accountExist(String accountid){
        SAXBuilder sb = new SAXBuilder();
        Document doc = null;
        try {
            doc = sb.build("userInformation/userInformation.xml");
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("account");
            for (Element el : list) {
                String id = el.getChildText("id");
                if(id.equals(accountid)){
                    ExecuteStatement.setUser(accountid);
                    return true;
                }
                System.out.println("There is no such account!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean login(account accountProvided){
        String idProvided = accountProvided.getUserId();
        String passwordProvided = accountProvided.getPassword();

        SAXBuilder sb = new SAXBuilder();
        Document doc = null;
        try {
            doc = sb.build("userInformation/userInformation.xml");
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren("account");
            for (Element el : list) {
                String id = el.getChildText("id");

                if(id.equals(idProvided)){
                    String password = SHAUtils.shaEncode(passwordProvided);
                    if (password.equals(el.getChildText("password"))){
                        return true;
                    }else {
                        System.out.println("Password Wrong! Please try again");
                        return false;

                    }
                }
                System.out.println("There is no such account!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
