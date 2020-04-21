package storage.Storage;

public class XMLUtils {

    public static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public static String convertToLetter(String sentence){
        String regex = "[^(A-Za-z)]";
        return sentence.replaceAll(regex,"");
    }

    //通过文件的目录来获取文件的名称
    public static String getFileName(String filepath){
        return filepath.substring(4);
    }
}
