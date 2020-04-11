package REDBLACKTREE;

public class Full_Node <T extends Comparable<T>, V> extends Node<T,V> {

    private Full_Node(Node<T,V> parent , Node<T,V> left_child , Node<T,V> right_child , T key , V value ) {
        this.parent=parent;
        this.left_child=left_child;
        this.right_child=right_child;
        right_child.setParent(this);
        left_child.setParent(this);
        this.key=key;
        this.value=value;
    }

    public Full_Node(Node<T,V> parent , T key , V value ) {
        this(parent,new Null_Node<T,V>() , new Null_Node<T,V>(),key,value);
    }


    @Override
    public void setLeftChild(INode<T, V> left_child) {
        this.left_child=left_child;
    }

    @Override
    public void setRightChild(INode<T, V> right_child) {
      this.right_child=right_child;
    }

    @Override
    public void setKey(T key) {
     this.key=key;
    }

    @Override
    public void setValue(V value) {
    this.value=value;
    }

    @Override
    public boolean getColor() {
        return color;
    }

    @Override
    public void setColor(boolean color) {
       this.color=color;
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
