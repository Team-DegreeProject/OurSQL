package com.ucd.oursql.sql.table.BTree;

public class LeafNode <T, V extends Comparable<V>> extends Node<T, V> {

    protected Object values[];
    protected LeafNode left;
    protected LeafNode right;

    public LeafNode(){
        super();
        this.values = new Object[BPlusTree.getmaxKeyNumber()];
        this.left = null;
        this.right = null;
    }

    public LeafNode(Node parent,Object[] values,int keyNumber,Object[] keys){
        this.parent=parent;
        this.values=values;
        this.keyNumber=keyNumber;
        this.keys=keys;
    }
    //二分查找
    @Override
    public T select(V key) {
//        if(key instanceof PrimaryKey){
//            System.out.println("select :");
//            ((PrimaryKey) key).printPK();
//        }

        if(this.keyNumber <=0 || key.compareTo((V)this.keys[this.keyNumber-1])>0)
            return null;
        int low = 0;
        int up = this.keyNumber;
        int middle = (low + up) / 2;
        while(low < up){
//            System.out.println("low:"+low+"up:"+up+"middle: "+middle);
            V middleKey = (V) this.keys[middle];
            if(key.compareTo(middleKey) == 0) {
//                System.out.println("case1");
                return (T) this.values[middle];
            }else if(key.compareTo(middleKey) < 0) {
//                System.out.println("case2");
                up = middle;
            }else {
//                System.out.println("case3");
                low = middle;
            }
            middle = (low + up) / 2;
            if(middle==low){
//                System.out.println("low:"+low+"up:"+up+"middle: "+middle);
                middleKey=(V) this.keys[middle];
//                System.out.println(key+" "+this.values[middle+1]);
                if(key.compareTo(middleKey) == 0){
//                    System.out.println("case4");
                    return (T) this.values[middle];
                }
                else if(key.compareTo((V)this.keys[middle+1]) == 0){
                    return (T)this.values[middle+1];
                }else{
                    break;
                }

            }
        }
        return null;
    }

    @Override
    public Node selectRange(V key) {
        return this;
    }


    @Override
    public Node<T, V> insert(T value, V key) {
        //找到插入数据位置

        int i = this.keyNumber-1;
//        if(this.keyNumber==0){
//            this.keys[0]=key;
//            this.values[0]=value;
//            this.keyNumber++;
//            if(this.parent!=null){
//                V oldkey=null;
//                for(int z=this.parent.keyNumber-1;z>=0;z--){
//                    if(key.compareTo((V)this.parent.keys[z])>=0){
//                        oldkey=(V)this.parent.keys[z];
//                    }
//                }
//                changeParentKey(this,oldkey);
//            }
//            return null;
//        }
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) > 0){
                break;
            } else if(key.compareTo((V) this.keys[i]) == 0){
                return null;
            }
            i--;
        }
        //复制数组,完成添加
        Object tempKeys[] = new Object[BPlusTree.getmaxKeyNumber()];
        Object tempValues[] = new Object[BPlusTree.getmaxKeyNumber()];
        if(i==(-1)){
            System.arraycopy(this.keys, 0, tempKeys, 1, this.keyNumber);
            System.arraycopy(this.values, 0, tempValues, 1, this.keyNumber);
            tempKeys[0] = key;
            tempValues[0] = value;
        }else{
            System.arraycopy(this.keys, 0, tempKeys, 0, i+1);
            System.arraycopy(this.values, 0, tempValues, 0, i+1);
            System.arraycopy(this.keys, i+1, tempKeys, i + 2, this.keyNumber - i - 1);
            System.arraycopy(this.values, i+1, tempValues, i + 2, this.keyNumber - i - 1);
            tempKeys[i+1] = key;
            tempValues[i+1] = value;
        }
        this.keyNumber++;
//            System.out.println("插入完成,当前节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//                System.out.print(tempKeys[j] + " ");
//            System.out.println();
        //判断插入值是否小于父节点
        if(i==(-1)){
            Node node = this;
//            System.out.println("999当前节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//                System.out.print(node.keys[j] + " ");

            V tempkey=(V)tempKeys[0];
            while (node.parent != null){
                if(tempkey.compareTo((V)node.parent.keys[0]) < 0){
                    node.parent.keys[0] = tempkey;
                    node = node.parent;
                }
                else {
                    break;
                }
            }
        }
        //保存该节点储存在父节点的key值
        V oldKey = null;
        if(this.keyNumber > 0)
            oldKey = (V) tempKeys[0];
        //判断是否需要拆分
        //如果不需要拆分完成复制后直接返回
        if(this.keyNumber <= BPlusTree.getOrder()){
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber);
//                System.out.println("叶子节点,插入key: " + key + ",不需要拆分");
            return null;
        }

        //如果需要拆分,则从中间把节点拆分差不多的两部分
        int middle = this.keyNumber / 2;
        //新建叶子节点,作为拆分的右半部分
        LeafNode<T, V> tempNode = new LeafNode<T, V>();
        tempNode.keyNumber = this.keyNumber - middle;
        //如果父节点为空，新建父节点
        if(this.parent == null) {
            NonLeafNode<T, V> tempNonLeafNode = new NonLeafNode<T, V>();
            tempNode.parent = tempNonLeafNode;
            this.parent = tempNonLeafNode;
            oldKey = null;
        }else{
            tempNode.parent = this.parent;
        }

        System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.keyNumber);
        System.arraycopy(tempValues, middle, tempNode.values, 0, tempNode.keyNumber);
        //让原有叶子节点作为拆分的左半部分
        this.keyNumber = middle;
        this.keys = new Object[BPlusTree.getmaxKeyNumber()];
        this.values = new Object[BPlusTree.getmaxKeyNumber()];
        System.arraycopy(tempKeys, 0, this.keys, 0, middle);
        System.arraycopy(tempValues, 0, this.values, 0, middle);
        //刷新左右叶节点左右指针
        tempNode.right=this.right;
        if(this.right!=null){
            this.right.left=tempNode;
        }
        this.right = tempNode;
        tempNode.left = this;
//可能会出现最小值在父节点中不存在的情况
        if(this.parent.keyNumber!=0){
            V oldkey1=null;
            for(int z=this.parent.keyNumber-1;z>=0;z--){
                if(((V)this.keys[0]).compareTo((V)this.parent.keys[z])==0){
                    break;
                }else if(((V)this.keys[0]).compareTo((V)this.parent.keys[z])>0){
                    oldkey1=(V)this.parent.keys[z];
                    break;
                }
            }
            if(oldkey1!=null){
                changeParentKey(this,oldkey1);
            }

        }

//        System.out.println("当前叶节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//        System.out.println();
//        System.out.println("oldkey "+oldKey);
        //将新节点插入到父节点
        NonLeafNode<T, V> parentNode = (NonLeafNode<T, V>)this.parent;
//        System.out.println("当前父节点key为:");
//        for(int j = 0; j < parentNode.keyNumber; j++)
//            System.out.print(parentNode.keys[j] + " ");
//        System.out.println();
        return parentNode.insertNode(this, tempNode, oldKey);
    }

    @Override
    public boolean delete(V key) {
        V oldKey=(V)this.keys[0];

        //找到删除数据位置
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) == 0){
                break;
            }
            i--;
        }
        if(i==(-1)){
            System.out.println("not exist");
            return false;
        }

        //复制数组,完成删除
        Object tempKeys[] = new Object[BPlusTree.getmaxKeyNumber()];
        Object tempValues[] = new Object[BPlusTree.getmaxKeyNumber()];
        if(i==0){
            System.arraycopy(this.keys, 0, tempKeys, 0, i);
            System.arraycopy(this.values, 0, tempValues, 0, i);
        }else{
            System.arraycopy(this.keys, 0, tempKeys, 0, i);
            System.arraycopy(this.values, 0, tempValues, 0, i);
            System.arraycopy(this.keys, i+1, tempKeys, i , this.keyNumber - i -1);
            System.arraycopy(this.values, i+1, tempValues, i , this.keyNumber - i -1);
        }

        this.keyNumber--;
        System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
        System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber);
        if(i==0 && this.keyNumber!=0){
            changeParentKey(this,oldKey);
        }
        return false;
    }

    public void changeParentKey(Node node, V key){

        while (node.parent != null){
//            System.out.println("当前节点key为:");
//            for(int j = 0; j < node.keyNumber; j++)
//                System.out.print(node.keys[j] + " ");
//            System.out.println();
//
//            System.out.println("fu节点key为:");
//            for(int i = 0; i < node.parent.keyNumber; i++)
//                System.out.print(node.parent.keys[i] + " ");
//            System.out.println();
            int j = 0;
            while(j < node.parent.keyNumber){
                if(key.compareTo((V) node.parent.keys[j]) == 0){
                    break;
                }
                j++;
            }
            node.parent.keys[j]=node.keys[0];

//            System.out.println("改变后fu节点key为:");
//            for(int i = 0; i < node.parent.keyNumber; i++)
//                System.out.print(node.parent.keys[i] + " ");
//            System.out.println();

            if(j==0){
                node = node.parent;
            }
            else {
                break;
            }
        }
    }
    @Override
    LeafNode<T, V> refreshLeft() {
        return this;
    }

    @Override
    LeafNode<T, V> refreshRight() {
        return this;
    }


    public Object[] getValues() {
        return values;
    }
}
