package com.ucd.oursql.sql.table.BTree;

class NonLeafNode<T, V extends Comparable<V>> extends Node<T, V> {

    public NonLeafNode() {
        super();
        this.childs = new Node[BPlusTree.getmaxKeyNumber()];
    }

    public NonLeafNode(Node parent,Node[] childs,int keyNumber,Object[] keys){
        this.parent=parent;
        this.childs=childs;
        this.keyNumber=keyNumber;
        this.keys=keys;
    }

    @Override
    public T select(V key) {
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) >= 0)
                break;
            i--;
        }
        if(i==(-1))
            return null;
        return this.childs[i].select(key);
    }

    @Override
    public Node selectRange(V key) {
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) >= 0)
                break;
            i--;
        }
        if(i==(-1))
            return null;
        return this.childs[i].selectRange(key);
    }

    @Override
    public Node<T, V> insert(T value, V key) {
//        System.out.println("当前非叶节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//            System.out.println();
//        System.out.println("当前非叶节点插入： "+ key);
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) >= 0){
                break;
            }
            i--;
        }
        if(key.compareTo((V) this.keys[0]) < 0) {
            i++;
        }
//        System.out.println("当前非叶节点i： "+ i);
        return this.childs[i].insert(value, key);
    }

    @Override
    public boolean delete(V key) {
//            System.out.println("当前搜索节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//            System.out.println();
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) >= 0)
                break;
            i--;
        }
        if(i==(-1)) {
            System.out.println("not exist");
            return false;
        }
        return this.childs[i].delete(key);
    }


    public Node<T, V> insertNode(Node<T, V> node1, Node<T, V> node2, V key){
        V oldKey = null;
        if(this.keyNumber > 0)
            oldKey = (V) this.keys[0];
        //如果非叶节点为空,直接插入两个节点
        if(key == null || this.keyNumber <= 0){
//                System.out.println("非叶子节点,插入key: " + node1.keys[node1.keyNumber - 1] + " " + node2.keys[node2.keyNumber - 1] + "直接插入");
            this.keys[0] = node1.keys[0];
            this.keys[1] = node2.keys[0];
            this.childs[0] = node1;
            this.childs[1] = node2;
            this.keyNumber += 2;
            return this;
        }
        //如果非叶节点不为空,则应该先寻找原有节点的位置,然后将新的节点插入到原有节点中
//        System.out.println("当前节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//        System.out.print(this.keys[j] + " ");
//        System.out.println();
//        System.out.println(key);
//        System.out.println("当前非叶节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//        System.out.println();
//        System.out.println("当前非叶节点插入： "+ key);
        int i = 0;
        while(key.compareTo((V)this.keys[i]) != 0){
            i++;
        }
        //插入左节点
        this.keys[i] = node1.keys[0];
        this.childs[i] = node1;
        Object tempKeys[] = new Object[BPlusTree.getmaxKeyNumber()];
        Object tempChilds[] = new Node[BPlusTree.getmaxKeyNumber()];
        System.arraycopy(this.keys, 0, tempKeys, 0, i + 1);
        System.arraycopy(this.childs, 0, tempChilds, 0, i + 1);
        System.arraycopy(this.keys, i + 1, tempKeys, i + 2, this.keyNumber - i - 1);
        System.arraycopy(this.childs, i + 1, tempChilds, i + 2, this.keyNumber - i - 1);
        //插入右节点
        tempKeys[i + 1] = node2.keys[0];
        tempChilds[i + 1] = node2;
        this.keyNumber++;
        //判断是否需要拆分
        //如果不需要拆分,把数组复制回去,直接返回
        if(this.keyNumber <= BPlusTree.getOrder()){
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
            System.arraycopy(tempChilds, 0, this.childs, 0, this.keyNumber);
            return null;
        }
        //如果需要拆分,从中间拆开
        int middle = this.keyNumber / 2;
        //新建非叶子节点,作为拆分的右半部分
        NonLeafNode<T, V> tempNode = new NonLeafNode<T, V>();
        //非叶节点拆分后应该将其子节点的父节点指针更新为正确的指针
        tempNode.keyNumber = this.keyNumber - middle;
        //如果父节点为空,则新建一个非叶子节点作为父节点,并且让拆分成功的两个非叶子节点的指针指向父节点
        if(this.parent == null) {
            NonLeafNode<T, V> tempNonLeafNode = new NonLeafNode<>();
            tempNode.parent = tempNonLeafNode;
            this.parent = tempNonLeafNode;
            oldKey = null;
        }else{
            tempNode.parent = this.parent;
        }
        System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.keyNumber);
        System.arraycopy(tempChilds, middle, tempNode.childs, 0, tempNode.keyNumber);
        for(int j = 0; j < tempNode.keyNumber; j++){
            tempNode.childs[j].parent = tempNode;
        }
        //让原有非叶子节点作为左边节点
        this.keyNumber = middle;
        this.keys = new Object[BPlusTree.getmaxKeyNumber()];
        this.childs = new Node[BPlusTree.getmaxKeyNumber()];
        System.arraycopy(tempKeys, 0, this.keys, 0, middle);
        System.arraycopy(tempChilds, 0, this.childs, 0, middle);

        //叶子节点拆分成功后,需要把新生成的节点插入父节点
        NonLeafNode<T, V> parentNode = (NonLeafNode<T, V>)this.parent;
        return parentNode.insertNode(this, tempNode, oldKey);
    }

    public void deleteNode(){
        int threshold=BPlusTree.getOrder()/2;
        NonLeafNode left=null;
        NonLeafNode right=null;
        //记录原始最小值
        V oldkey=(V)this.keys[0];
        if(this.keyNumber>=threshold || this.parent==null ){
            return;
        }else if(this.keyNumber==1 && this.parent.keyNumber==1){
            if(this.parent!=null){
                this.parent.childs=this.childs;
                this.childs[0].parent=this.parent;
                NonLeafNode parentNode=(NonLeafNode) this.parent;
                parentNode.deleteNode();
            }
        }else{
            //找到此节点的兄弟节点
            int i = 0;
//            System.out.println("当前节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//            System.out.println();
//
//            System.out.println("父节点key为:");
//            for(int j = 0; j < this.parent.keyNumber; j++)
//                System.out.print(this.parent.keys[j] + " ");
//            System.out.println();
//            System.out.println("key[0]: "+this.keys[0]+"  this.parent.keynumber: "+this.parent.keyNumber);
            while(((V)this.keys[0]).compareTo((V)this.parent.keys[i]) != 0){
                i++;
//                System.out.println("i:  "+i);
            }

            //
//            System.out.println("父父节点key为:");
//            for(int j = 0; j < this.parent.keyNumber; j++)
//                System.out.print(this.parent.keys[j] + " ");
//            System.out.println();
//            System.out.println("i: "+i+" parent keynumber: "+this.parent.keyNumber);

            //
            if(i>0 && i<(this.parent.keyNumber-1)){
                left=(NonLeafNode) this.parent.childs[i-1];
                right=(NonLeafNode) this.parent.childs[i+1];
            }else if(i==0){
                left=null;
                right=(NonLeafNode) this.parent.childs[i+1];
            }else{
                left=(NonLeafNode) this.parent.childs[i-1];
                right=null;
            }
        }
        if(this.keyNumber<threshold && left!=null && left.keyNumber>threshold){
            Object lendKey=left.keys[left.keyNumber-1];
            Node lendChild=left.childs[left.keyNumber-1];
            lendChild.parent=this;
            left.keys[left.keyNumber-1]=null;
            left.childs[left.keyNumber-1]=null;
            left.keyNumber--;
            for(int i=this.keyNumber;i>0;i--){
                this.keys[i]=this.keys[i-1];
                this.childs[i]=this.childs[i-1];
            }
            this.keys[0]=lendKey;
            this.childs[0]=lendChild;
            this.keyNumber++;
            changeParentKey(this,oldkey);
//            System.out.println("caseA: ");
        }else if(this.keyNumber<threshold && right!=null && right.keyNumber>threshold){
            Object lendKey=right.keys[0];
            Node lendChild=right.childs[0];
            lendChild.parent=this;
            this.keys[this.keyNumber]=lendKey;
            this.childs[this.keyNumber]=lendChild;
            this.keyNumber++;
            for(int j=0;j<right.keyNumber-1;j++){
                right.keys[j]=right.keys[j+1];
                right.childs[j]=right.childs[j+1];
            }
            right.keyNumber--;
            changeParentKey(right,(V)lendKey);
//            System.out.println("caseB: ");
        }else if(this.keyNumber<threshold && right!=null && right.keyNumber<=threshold){//合并子节点

//            System.out.println("当前caseC节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//            System.out.println();
//
//            System.out.println("当前caseC右节点key为:");
//            for(int j = 0; j < right.keyNumber; j++)
//                System.out.print(right.keys[j] + " ");
//            System.out.println();
            System.arraycopy(right.keys, 0, this.keys, this.keyNumber, right.keyNumber);
            System.arraycopy(right.childs, 0, this.childs, this.keyNumber, right.keyNumber);
            this.keyNumber=right.keyNumber+this.keyNumber;
            for(int i=0;i<this.keyNumber;i++){
                this.childs[i].parent=this;
            }
            if(this.parent!=null){
//                System.out.println("合并前父节点key为:");
//                for(int z = 0; z < this.parent.keyNumber; z++)
//                    System.out.print(this.parent.keys[z] + " ");
//                System.out.println();
                //删除右节点对应的父节点的key和child
                int j = 0;
                while(j < this.parent.keyNumber){
                    if(((V)right.keys[0]).compareTo((V) this.parent.keys[j]) == 0){
//                        System.out.println("右节点最小key"+right.keys[0]);
                        break;
                    }
                    j++;
                }
                for(int x=j;x<this.parent.keyNumber-1;x++){
                    this.parent.keys[x]=this.parent.keys[x+1];
                    this.parent.childs[x]= this.parent.childs[x+1];
                }
                this.parent.keyNumber--;
//                System.out.println("合并后父节点key为:");
//                for(int z = 0; z < this.parent.keyNumber; z++)
//                    System.out.print(this.parent.keys[z] + " ");
//                System.out.println();
                //判断父节点是否需要合并
                NonLeafNode parentNode=(NonLeafNode) this.parent;
//                System.out.println("caseC: CCCCCCCCCCCCCCCCCCCCC");
                parentNode.deleteNode();
            }
//            System.out.println("caseC: ");
        }else if(this.keyNumber<threshold && left!=null && left.keyNumber<=threshold){
            System.arraycopy(this.keys, 0, left.keys, left.keyNumber, this.keyNumber);
            System.arraycopy(this.childs, 0, left.childs, left.keyNumber, this.keyNumber);
            left.keyNumber=left.keyNumber+this.keyNumber;
            for(int i=0;i<left.keyNumber;i++){
                left.childs[i].parent=left;
            }
            //删除此节点对应的父节点的key值和child
            if(this.parent!=null){
                int j = 0;
                while(j < this.parent.keyNumber){
                    if(((V)this.keys[0]).compareTo((V) this.parent.keys[j]) == 0){
                        break;
                    }
                    j++;
                }
                for(int x=j;x<this.parent.keyNumber-1;x++){
                    this.parent.keys[x]=this.parent.keys[x+1];
                    this.parent.childs[x]= this.parent.childs[x+1];
                }
                left.parent.keyNumber--;
                //判断父节点是否需要合并
                NonLeafNode parentNode=(NonLeafNode) left.parent;
                parentNode.deleteNode();
            }
//            System.out.println("caseD: ");
        }
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
        return this.childs[0].refreshLeft();
    }

    @Override
    LeafNode<T, V> refreshRight() {
        return this.childs[this.keyNumber-1].refreshRight();
    }
}
