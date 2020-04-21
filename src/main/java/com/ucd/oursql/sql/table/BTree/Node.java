package com.ucd.oursql.sql.table.BTree;

abstract class Node<T, V extends Comparable<V>>{
    //父节点
    protected Node<T, V> parent;
    //键（子节点）数量
    protected Integer keyNumber;
    //键
    protected Object keys[];
    //子节点
    protected Node<T, V>[] childs;
    //构造方法
    public Node(){
        this.keys = new Object[BPlusTree.getmaxKeyNumber()];
        this.keyNumber = 0;
        this.parent = null;
    }

    //查找
    public abstract T select(V key);

    //范围查找
    public abstract Node selectRange(V key);

    //插入
    public abstract Node<T, V> insert(T value, V key);

    //删除
    public abstract boolean delete(V key);

    //最左节点
    abstract LeafNode<T, V> refreshLeft();

    //最右节点
    abstract LeafNode<T, V> refreshRight();
}
