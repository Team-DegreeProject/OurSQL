package storage.Storage;

import storage.Configuration.Configuration;
import table.BTree.BPlusTree;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args){
        HashMap<Integer,Student> map = new HashMap<>();
        for(int i = 0; i < 2 ;i++){
            Student x = new Student("test",i);
            map.put(i,x);
        }

        Student root = new Student("root",100);
        root.setNext(map.get(1));
        map.get(1).setNext(map.get(0));


        ArrayList<String> testlist = new ArrayList<>();
        testlist.add("first");
        testlist.add("second");
        System.out.println(testlist.get(0));
        testlist.remove(0);
        System.out.println(testlist.get(0));


        alterUnit testUnit = new alterUnit("niubitable", Configuration.CREATE,new BPlusTree());
        System.out.println("the result is : "+testUnit.getAlterType().equals(Configuration.CREATE));
    }
}
