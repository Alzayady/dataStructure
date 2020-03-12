import javax.lang.model.type.PrimitiveType;

public class Node<T extends Comparable > implements Comparable<T> {
    private T value;
    private Node left ,right;
    private int height ;
    public static final int ACCEPTABLE_RANGE = 1;


    public Node(){
       this(null,NullNode.getInstance(),NullNode.getInstance());
    }
    public Node (T value){
        this(value,NullNode.getInstance(), NullNode.getInstance());
    }
    public Node (T value, Node left , Node right){
     this.value=value;
     this.left=left;
     this.right=right;
    }


    public boolean setLeft(Node left){
        if(this.hasLeft())return false;
        if(left==NullNode.getInstance())return false;
        this.left=left;
        height=Math.max(1+left.getHeight(),height);
        return true;
    }
    public boolean setRight(Node right){
        if(this.hasRight())return false;
        if(right==NullNode.getInstance())return false;
        this.right=right;
        height=Math.max(height,1+right.getHeight());

        return true;
    }

    @Override
    public String toString(){
        return left.toString() + value + right.toString();
    }
    public Node resetLeft(){
        Node node =left;
        left=NullNode.getInstance();
        return node;
    }
    public Node resetRight(){
        Node node =right;
        right=NullNode.getInstance();
        return node;
    }
    public Node getLastLeft(){
        if(hasLeft())return left.getLastLeft();
        return this;
    }

    public Node getLastRight(){
        if(hasRight())return left.getLastRight();
        return this;
    }




    public T getValue(){
        return value;
    }
    public int getHeight() {
        return height;
    }
    public boolean hasLeft(){
        return left!=NullNode.getInstance();
    }
    public boolean hasRight(){
        return right!=NullNode.getInstance();
    }
    public boolean isEmpty(){
        return false;
    }
    public  boolean isBalanced(){
        return Math.abs(left.getHeight()-right.getHeight())<=ACCEPTABLE_RANGE;
    }
    public Node getLeft(){
        return left;
    }
    public Node getRight(){
        return right;
    }


    @Override
    public int compareTo(T o) {
        return value.compareTo(o);
    }
}


class NullNode extends Node{
    private static NullNode node=new NullNode();
    public static NullNode getInstance(){
        return node;
    }
  //  private NullNode(){ } // dummy constructor to make singleton
    @Override
    public boolean isEmpty(){
        return true;
    }
    @Override
    public int getHeight(){
        return 0;
    }
    @Override
    public String toString(){
        return "";
    }

}


