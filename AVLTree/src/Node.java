public class Node<T extends Comparable > implements Comparable<Node> {
    private T value;
    private Node left ,right;
    private int height ;
    public static final int ACCEPTABLE_RANGE = 1;


    public Node(){ this(null,NullNode.getInstance(),NullNode.getInstance()); }
    public Node (T value){ this(value,NullNode.getInstance(), NullNode.getInstance()); }
    public Node (T value, Node left , Node right){
     this.value=value;
     this.left=left;
     this.right=right;
    }


    public boolean setLeft(Node left){
        if(this.hasLeft())return false;
        if(left==NullNode.getInstance())return false;
        this.left=left;
        height=1+Math.max(left.getHeight(),right.getHeight());
        return true;
    }
    public boolean setRight(Node right){
        if(this.hasRight())return false;
        if(right==NullNode.getInstance())return false;
        this.right=right;
        height=1+Math.max(left.getHeight(),right.getHeight());
        return true;
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
        System.out.println(value);
        if(hasLeft())return left.getLastLeft();
        return this;
    }

    public Node getLastRight(){
        if(hasRight())return right.getLastRight();
        return this;
    }

    public T getValue(){ return value; }
    public int getHeight() { return height; }
    public boolean hasLeft(){ return left!=NullNode.getInstance(); }
    public boolean hasRight(){ return right!=NullNode.getInstance(); }
    public boolean isEmpty(){ return false; }
    public  boolean isBalanced(){ return Math.abs(left.getHeight()-right.getHeight())<=ACCEPTABLE_RANGE; }
    public Node getLeft(){ return left; }
    public Node getRight(){ return right; }
    @Override
    public int compareTo(Node o) { return value.compareTo(o.value); }
    @Override
    public String toString(){ return left.toString()  + value + " ,"+ right.toString(); }
}


class NullNode extends Node{
    private static NullNode node=new NullNode();
    public static NullNode getInstance(){
        return node;
    }
    private NullNode(){ } // dummy constructor to make singleton
    @Override
    public boolean isEmpty(){
        return true;
    }
    @Override
    public int getHeight(){
        return -1;
    }
    @Override
    public String toString(){
        return "";
    }
}


