//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.sun.source.tree.Tree;
//import org.apache.log4j.Logger;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.output.XMLOutputter;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.w3c.dom.Node;
//
//import javax.xml.parsers.DocumentBuilder;
//
//
//public class XMLWritterTest {
//    private static String currentFolder="./config/HKVideoDate/";
//    public static String saveXMLTable(String tableName, Tree Btree){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String today=sdf.format(new Date());
//        String tablePath=currentFolder+today+"/"+tableName+"/";
//        String path=tablePath+tableName+"_"+System.currentTimeMillis()+".xml";
//
//        Element root = new Element("table");
//        root.setAttribute("name",tableName);
//        Element layer = new Element("layer");
//        root.addContent(layer);
//        Document doc = new Document(root);
//        if(Btree!=null){
//            if(Btree.getSysName()!=null&&Node.getSysName()!=""){
//                root.setAttribute("id",Node.getSysName());
//            }
//            if(Node.getChannelName()!=null&&Node.getChannelName()!=""){
//                root.setAttribute("parentNode",video.getChannelName());
//            }
//            if(Node.getStatus()!=null&&Node.getStatus()!=""){
//                root.setAttribute("childrenNode", video.getStatus());
//            }
//            if(Node.getVideoStatus()!=null&& Node.getVideoStatus()!=""){
//                root.setAttribute("brother", video.getVideoStatus());
//            }
//            if(Node.getSignStatus()!=null&& Node.getSignStatus()!=""){
//                root.setAttribute("M",Node.getSignStatus());
//            }
//
//
//            Element disk = new Element("disk");
//            Element diskEle = new Element("disk");
//            if(video.getDiskName()!=null&&video.getDiskName()!=""){
//                diskEle.setAttribute("硬盘名称",video.getDiskName());
//            }
//            if(video.getCapacity()>=0){
//                diskEle.setAttribute("磁盘总容量",String.valueOf(video.getCapacity()));
//            }
//            if(video.getFreeSpace()>=0){
//                diskEle.setAttribute("剩余空间",String.valueOf(video.getFreeSpace()));
//            }
//            if(video.getDiskStatus()!=null&&video.getDiskStatus()!=""){
//                diskEle.setAttribute("硬盘状态",video.getDiskStatus());
//            }
//            disk.addContent(diskEle);
//            root.addContent(disk);
//        }
//        XMLOutputter XMLOut = new XMLOutputter();
//        File file = new File(ipPath);
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream(path);
//            XMLOut.output(doc, fos);
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return path;
//    }
//
//
//
//}
