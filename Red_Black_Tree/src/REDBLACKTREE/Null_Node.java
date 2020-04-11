package REDBLACKTREE;

public class Null_Node  <T extends Comparable<T>, V> extends Node<T,V> {

    @Override
    public void setLeftChild(INode<T, V> leftChild) {
           this.left_child=leftChild;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild) {

        this.right_child=rightChild;
    }

    @Override
    public void setKey(T key) {

    }

    @Override
    public void setValue(V value) {

    }

    @Override
    public boolean getColor() {
        return BLACK;
    }

    @Override
    public void setColor(boolean color) {
    }

    @Override
    public boolean isNull() {
        return true;
    }

}
