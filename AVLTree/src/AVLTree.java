
public class AVLTree <T extends Comparable> {

    private Node root=NullNode.getInstance();



    public boolean delete(T value){
        if(this.isEmpty())return false;
        if(root.getValue()==value){
            if(!root.hasLeft()&&!root.hasRight()){
                root=NullNode.getInstance();
                return true;
            }
            if(!root.hasLeft()){
                root=root.getRight();
                return true;
            }
            if(!root.hasRight()){
                root=root.getLeft();
                return true;
            }
            root.getLeft().getLastRight().setRight(root.getRight());
            root=root.getLeft();
            return true;
        }
        return delete(value, this.root);
    }

    private boolean delete(T value, Node root) {

        if(root.getValue().compareTo(value)>0){

            if(!root.hasLeft())return false;
            if(root.getLeft().getValue()==value){
                deleteLeft(root);
                return true;
            }
            return delete(value,root.getLeft());
        }

        if(!root.hasRight())return false;
        if(root.getRight().getValue()==value){
            deleteRight(root);
            return true;
        }
        return delete(value,root.getRight());
    }

    private void deleteLeft(Node parent){
        Node node =   parent.resetLeft();
        if(isLeave(node)){
            parent.setLeft(NullNode.getInstance());
            return;
        }
        if(node.getLeft()==NullNode.getInstance()){
            parent.setLeft(node.getRight());
            return;
        }
        if(node.getRight()==NullNode.getInstance()){
            parent.setLeft(node.getLeft());
            return;
        }
        parent.setLeft(node.getLeft());
        node.getLeft().getLastRight().setRight(node.getRight());
    }
    private void  deleteRight(Node parent){
        Node node = parent.resetRight();
        if(isLeave(node)){
            parent.setRight(NullNode.getInstance());
            return;
        }
        if(node.getRight()==NullNode.getInstance()){
            parent.setRight(node.getLastLeft());
            return;
        }
        if(node.getLeft()==NullNode.getInstance()){
            parent.setRight(node.getLastRight());
            return;
        }
        parent.setRight(node.getRight());
        node.getRight().getLastLeft().setLeft(node.getLeft());
    }
    private boolean isLeave(Node node){
        return node.getRight()==NullNode.getInstance()&&node.getLeft()==NullNode.getInstance();
    }

    public boolean insert(T value){

        Node <T>node =new Node(value);
        if(this.isEmpty()){ // tree is empty
            root=node;
            return true;
        }
        insert(node,root);
        return true;

    }


    private void  insert (Node <T>insertedNode, Node  <T>parentNode){

        if (insertedNode.compareTo(parentNode)<0){
            if(parentNode.hasLeft()){
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
        String rootString=root.toString();
        return "[" + rootString.substring(0,rootString.length()-2) + "]";
    }

}
