package REDBLACKTREE;


public abstract  class Node <T extends Comparable<T>, V> implements INode<T,V> {

    protected T key ;
    protected V value;
    protected boolean color;

    protected INode<T,V> parent;
    protected INode<T,V> left_child;
    protected INode<T,V> right_child;


    @Override
    public void setParent(INode<T, V> parent) {
        this.parent=parent;
    }

    @Override
    public INode<T, V> getParent() {
        return parent;
    }

    @Override
    public INode<T, V> getLeftChild() {
        return left_child;
    }



    @Override
    public INode<T, V> getRightChild() {
        return right_child;
    }

    @Override
    public T getKey() {
        return key;
    }


    @Override
    public V getValue() {
        return value;
    }


    @Override
    public String toString(){
        if(this.getValue()==null)return "empty Node ";
        return String.valueOf(this.getValue());
    }


}
