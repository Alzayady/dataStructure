import WrapperPriitiveType.PrimitiveType;

public class AVLTree <T extends PrimitiveType> {

    private Node root=NullNode.getInstance();

    public boolean insert(T value){

        Node node =new Node<T>(value);
        if(this.isEmpty()){ // tree is empty
            root=node;
            return true;
        }
        insert(node,root);
        return true;

    }

    public boolean delete(T value){
        if(this.isEmpty())return false;
        return delete(value, this.root);
    }

    private boolean delete(T value, Node root) {
        if(root.getValue().compareTo(value)<0){
            if(!root.hasLeft())return false;
            if(root.getLeft().getValue()==value){
                deleteLeft(root);
                return true;
            }
            return delete(value,root.getLeft());
        }
        if(!root.hasRight())return false;
        if(root.getLeft().getValue()==value){
            deleteRight(root);
            return true;
        }
        return delete(value,root.getRight());
    }

    private void deleteLeft(Node parent){
        Node node =   parent.resetLeft();
        parent.setLeft(node.getLeft());
        node.getLeft().getLastRight().setRight(node.getRight());
    }
    private void  deleteRight(Node parent){
        Node node = parent.resetRight();
        parent.setRight(node.getRight());
        node.getRight().getLastLeft().setLeft(node.getLeft());
    }

    private void  insert (Node insertedNode, Node parentNode){
        if (insertedNode.compareTo(parentNode)<0){
            if(parentNode.hasRight()){
                 insert(insertedNode,parentNode.getLeft());
                 return;
            }
            parentNode.setLeft(insertedNode);
            return;
        }

        if(parentNode.hasRight()){
            insert(insertedNode,parentNode.getRight());
            return;
        }

        parentNode.setRight(insertedNode);
    }

    public boolean isEmpty(){
        return root.isEmpty();
    }

    @Override
    public String toString(){
        return "[" + root.toString() + "]";
    }

}
