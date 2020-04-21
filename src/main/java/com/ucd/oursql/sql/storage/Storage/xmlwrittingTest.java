//package Storage;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.output.Format;
//import org.jdom2.output.XMLOutputter;
//
//import java.io.*;
//import java.util.List;
//
//public class xmlwrittingTest {
//    private void createXML() throws Exception{
//        String tableName = "Student Information";
//
//        //创建学生测试数组
//        Student[] ss = new Student[2];
//        ss[0] = new Student("n1","id1");
//        ss[1] = new Student("n2","id2");
//
//
//        //1.生成一个根节点
//        Element table=new Element("table");
//        //2.为节点添加属性
//        table.setAttribute("name", tableName);
//        //3.生成一个document的对象
//        Document document=new Document(table);
//        for(int i = 0; i< ss.length; i++){
//            Element student = new Element("student");
//            table.addContent(student);
//            Element name = new Element("name").setText(ss[i].getName());
//            Element id = new Element("id").setText(ss[i].getId());
//            student.addContent(name);
//            student.addContent(id);
//        }
////        Element student=new Element("channel");
////        rss.addContent(channel)    ;
////        Element title=new Element("title");
////        //title.setText("<国内最新新闻>");
////        //设置转义字符符号
////        title.setContent(new CDATA("<!CDATA上海移动互联网产业促进中心正式揭牌>"));
//        //<!CDATA上海移动互联网产业促进中心正式揭牌]]>
//        //title.setText(new CDATA("<国内最新新闻>"));//这样子可以处理特殊字符
////        channel.addContent(title);
//        //设置生成xml的格式
//        Format format=Format.getCompactFormat();
//        format.setIndent("");
//        //生成不一样的编码
//        format.setEncoding("GBK");
//        //4.创建XMLOutputter的对象
//        XMLOutputter outputter=new XMLOutputter(format);
//        //5.利用outputter将document对象转换成xml文档
//        outputter.output(document, new FileOutputStream(new File("rssnews.xml")));
//
//        SAXBuilder saxBuilder = new SAXBuilder();
//        //你也可以将demo.xml放在resources目录下，然后通过下面方式获取
//        //InputStream resourceAsStream = JDOMParseXml.class.getClassLoader().getResourceAsStream("demo.xml");
//        Document document1 = saxBuilder.build(new File("rssnews.xml"));
//        Element rootElement = document1.getRootElement();
//        List<Element> elementList = rootElement.getChildren();
//        for (Element element : elementList) {
//            System.out.println(element.getChild("name").getValue());
//            List<Element> elements = element.getChildren("student");
//
////            List<Element> children = element.getChildren();
////            for (Element child : children) {
////                System.out.println(child.getName()+":"+child.getValue());
////            }
//        }
//
//
//
//
//        //测试json
//        JSONObject result = new JSONObject();
//        result.put("success", true);
//        result.put("totalCount", "30");
//
//        JSONObject user1 = new JSONObject();
//        user1.put("id", "12");
//        user1.put("name", "张三");
//        user1.put("createTime", "2017-11-16 12:12:12");
//
//        JSONObject user2 = new JSONObject();
//        user2.put("id", "13");
//        user2.put("name", "李四");
//        user2.put("createTime", "2017-11-16 12:12:15");
//
//        JSONObject department = new JSONObject();
//        department.put("id", 1);
//        department.put("name","技术部");
//
//        user1.put("department", department);
//        user2.put("department", department);
//
//        // 返回一个JSONArray对象
//        JSONArray jsonArray = new JSONArray();
//
//        jsonArray.add(0, user1);
//        jsonArray.add(1, user2);
//        result.put("data", jsonArray);
//
//
//        String fullPath = "test.json";
//        File file = new File(fullPath);
//        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//        write.write(String.valueOf(result));
//        write.flush();
//        write.close();
//
//    }
//
//    public static String readJsonFile(String fileName) {
//        String jsonStr = "";
//        try {
//            File jsonFile = new File(fileName);
//            FileReader fileReader = new FileReader(jsonFile);
//
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String readjsonObjectTest(String jsonString){
//        String result = null;
//        JSONObject jobj = JSONObject.parseObject(jsonString);
//        JSONArray datas = jobj.getJSONArray("data");
//        JSONObject firstObject = datas.getJSONObject(1);
//        result = firstObject.getJSONObject("department").getString("name");
//        return result;
//    }
//    /**
//     * @param args
//     */
//    public static void main(String[] args) throws Exception{
//        new xmlwrittingTest().createXML();
//        System.out.println(readjsonObjectTest(readJsonFile("test.json")));
//
//    }
//
//
//}
