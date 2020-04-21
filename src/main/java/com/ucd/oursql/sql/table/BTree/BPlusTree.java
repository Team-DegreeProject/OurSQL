package com.ucd.oursql.sql.table.BTree;

import java.util.ArrayList;
import java.util.List;

//T为值
//V键值需要比较大小，所以要继承Comparable
public class BPlusTree <T, V extends Comparable<V>> {
    //B+树的阶
    private static int Order;
    //B+树节点拥有key的最大值
    private static int maxKeyNumber;
    //根节点
    private Node<T, V> root;

    //默认阶为3
    public BPlusTree() {
        this.Order = 3;
        this.maxKeyNumber = 4;
        this.root = new LeafNode<T, V>();
    }

    //构造器
    public BPlusTree(int Order) {
        this.Order = Order;
        //因为插入节点过程中可能出现超过上限的情况,所以这里要加1
        this.maxKeyNumber = Order + 1;
        this.root = new LeafNode<T, V>();
    }

    //查询
    public T select(V key) {
        T t = this.root.select(key);
        if (t == null) {
            System.out.println("Key " + key + " do not exist");
        }
        return t;
    }

    //范围查询
    public Node selectRange(V key) {
        Node t = this.root.selectRange(key);
        if (t == null) {
            System.out.println("Key " + key + " do not exist");
        }
        return t;
    }

    //插入
    public void insert(T value, V key) {
        if (key == null)
            return;
        Node<T, V> t = this.root.insert(value, key);
        if (t != null)
            this.root = t;
    }

    //删除
    public void delete(V key) {
        if (key == null) {
            return;
        }
//        System.out.println("根节点key为:");
//        for (int j = 0; j < root.keyNumber; j++)
//            System.out.print(root.keys[j] + " ");
//        System.out.println();
        boolean isnull=this.root.delete(key);
        if(isnull){
            this.root=new LeafNode<T, V>();
        }
    }

    public LeafNode<T, V> getLeft() {
        LeafNode node = this.root.refreshLeft();
        return node;
    }

    public LeafNode<T, V> getRight() {
        LeafNode node = this.root.refreshRight();
        return node;
    }

    public int getDataNumber(){
        LeafNode temp = this.getLeft();
        int count=0;
        while(temp!=null){
            count=count+temp.keyNumber;
            temp=temp.right;
        }
        return count;
    }

    public List<Object> getDatas() {
        List<Object> products = new ArrayList<>();
        LeafNode temp = this.getLeft();
        while (temp != null) {
            for (int j = 0; j < temp.keyNumber; j++) {
                products.add(temp.values[j]);
            }
            temp = temp.right;
        }
        return products;
    }

    public List<Object> getReverseDatas() {
        List<Object> products = new ArrayList<>();
        LeafNode temp = this.getRight();
        while (temp != null) {
            for (int j = temp.keyNumber-1; j>=0; j--) {
                products.add(temp.values[j]);
            }
            temp = temp.left;
        }
        return products;
    }


    public  List<LeafNode> getLeafNodes(){
        List<LeafNode> leafnodes = new ArrayList<>();
        LeafNode temp = this.getLeft();
        while (temp != null) {
            leafnodes.add(temp);
            temp = temp.right;
        }
        return leafnodes;
    }

    public List<Object> getBigDatas(V key) {
        List<Object> products = new ArrayList<>();
        LeafNode node=(LeafNode) selectRange(key);
        if(node.keyNumber==0){
            LeafNode temp = null;
            if(node.right!=null){
                temp=node.right;
            }
            while (temp != null) {
                for (int j = 0; j < temp.keyNumber; j++) {
                    products.add(temp.values[j]);
                }
                temp = temp.right;
            }
            return products;
        }else{
            int i;
            for(i=0;i<node.keyNumber;i++){
                if(key.compareTo((V) node.keys[i])<=0){
                    break;
                }
            }
            if(i==node.keyNumber){
                LeafNode temp = null;
                if(node.right!=null){
                    temp=node.right;
                }
                while (temp != null) {
                    for (int j = 0; j < temp.keyNumber; j++) {
                        products.add(temp.values[j]);
                    }
                    temp = temp.right;
                }
                return products;
            }else{
                for(int j=i;j<node.keyNumber;j++){
                    products.add(node.values[j]);
                }
                LeafNode temp = null;
                if(node.right!=null){
                    temp=node.right;
                }
                while (temp != null) {
                    for (int j = 0; j < temp.keyNumber; j++) {
                        products.add(temp.values[j]);
                    }
                    temp = temp.right;
                }
                return products;
            }
        }
    }


    public List<Object> getSmallDatas(V key) {
        List<Object> products = new ArrayList<>();
        LeafNode node=(LeafNode) selectRange(key);
        if(node.keyNumber==0){
            LeafNode temp=null;
            if(node.left!=null){
                temp= node.left;
            }
            while (temp != null) {
                for (int j = temp.keyNumber-1; j >=0; j--) {
                    products.add(temp.values[j]);
                }
                temp = temp.left;
            }
            return products;
        }else{
            int i;
            for(i=node.keyNumber-1;i>=0;i--){
                if(key.compareTo((V) node.keys[i])>=0){
                    break;
                }
            }
            if(i==-1){
                LeafNode temp=null;
                if(node.left!=null){
                    temp= node.left;
                }
                while (temp != null) {
                    for (int j = temp.keyNumber-1; j >=0; j--) {
                        products.add(temp.values[j]);
                    }
                    temp = temp.left;
                }
                return products;
            }else{
                for(int j=i;j>=0;j--){
                    products.add(node.values[j]);
                }
                LeafNode temp=null;
                if(node.left!=null){
                    temp= node.left;
                }
                while (temp != null) {
                    for (int j = temp.keyNumber-1; j >=0; j--) {
                        products.add(temp.values[j]);
                    }
                    temp = temp.left;
                }
                return products;
            }
        }
    }

    public List<Object> getMiddleDatas(V small,V big) {
        List<Object> bigger = new ArrayList<>();
        List<Object> smaller = new ArrayList<>();
        bigger=getBigDatas(small);
        smaller=getSmallDatas(big);
        return BPlusTreeTool.mergeListAnd(bigger,smaller);
    }

    public static int getOrder() {
        return Order;
    }

    public static int getmaxKeyNumber() {
        return maxKeyNumber;
    }

    public void getNodes(Node temp) {
//        System.out.println("temp.keynumber:" + temp.keyNumber);
        for (int z = 0; z < temp.keyNumber; z++) {
            System.out.print(temp.keys[z] + ", ");

        }
        System.out.println();
        if (temp instanceof NonLeafNode) {
            for (int i = temp.keyNumber - 1; i >= 0; i--) {
                getNodes(temp.childs[i]);
            }
        }
    }

    public List<Object> getNonLeafNodes(Node temp,List<Object> list) {
//        System.out.println("temp.keynumber:" + temp.keyNumber);

        if (temp instanceof NonLeafNode) {
            list.add(temp);
            if(temp.childs[0] instanceof NonLeafNode){
                for (int i = temp.keyNumber - 1; i >= 0; i--) {
                    getNonLeafNodes(temp.childs[i],list);
                }
            }
        }
        return list;
    }


    public Node<T, V> getRoot() {
        return root;
    }
}
