package com.ucd.oursql.sql.login;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class RegristrationFunc {

    public void register(account newUser){
        String filePath = "userInformation/userInformation.xml" ;
        File userInformationFile = new File(filePath);
        //当不存在的情况
        if(!userInformationFile.exists()){
            Document doc = new Document();
            Element infoEntity = new Element("account");
            infoEntity.addContent(new Element("id").setText(newUser.getUserId()));
            try {
                infoEntity.addContent(new Element("password").setText(SHAUtils.shaEncode(newUser.getPassword())));
            }
            catch (Exception e){
                e.printStackTrace();
            }


            Element accountDetail = new Element("accountDetail");
            accountDetail.addContent(infoEntity);
            doc.setRootElement(accountDetail);

            saveXML(doc);
        }
        //若当前存在这个文件的话，则需要在现在这个文件得基础上进行修改和添加
        else{
            SAXBuilder sb = new SAXBuilder();
            Document doc = null;
            try {
                doc = sb.build("userInformation/userInformation.xml");
                Element root = doc.getRootElement();

                //验证现在有没有当前账户
                List<Element> list = root.getChildren("account");
                boolean b = true;
                for (Element el : list) {
                    String id = el.getChildText("id");
                    if(id.equals(newUser.getUserId())){
                        b = !b;
                        System.out.println("Current Account Already Exist!");
                    }
                }
                if(b){
                    Element accountElement = new Element("account");
                    accountElement.addContent(new Element("id").setText(newUser.getUserId()));
                    accountElement.addContent(new Element("password").setText(SHAUtils.shaEncode(newUser.getPassword())));

                    root.addContent(accountElement);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            saveXML(doc);
        }

    }

    public static void saveXML(Document doc) {
        // 将doc对象输出到文件
        try {
            // 创建xml文件输出流
            XMLOutputter xmlopt = new XMLOutputter();

            // 创建文件输出流
            FileWriter writer = new FileWriter("userInformation/userInformation.xml");

            // 指定文档格式
            Format fm = Format.getPrettyFormat();
            // fm.setEncoding("GB2312");
            xmlopt.setFormat(fm);

            // 将doc写入到指定的文件中
            xmlopt.output(doc, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
