package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Three_Node_left_deletion <T extends Comparable<T>, V> extends Deletion<T,V>  {

    public Three_Node_left_deletion( INode<T, V> root,RedBlackTree<T, V> redBlackTree) {
        super(root, redBlackTree);
    }

    @Override
   public void fix(INode<T, V> bad_node) {
        if(!my_type(bad_node)){
            next.fix(bad_node);
            return;
        }
        INode<T,V>parent=bad_node.getParent();
        if(parent.getColor()==INode.RED){
            parent.setColor(INode.BLACK);
            parent.getLeftChild().setColor(INode.RED);
            return;
        }
        parent.getLeftChild().setColor(INode.RED);
       if(parent==root){
           return;
       }
        next.fix(parent);
    }

    @Override
    boolean my_type(INode<T, V> root_node) {
        INode<T,V>sibling=root_node.getParent().getLeftChild();
        if(sibling.isNull())return false;
        return I_AM_RIGHT_CHILD(root_node)&&sibling.getColor()==INode.BLACK
                &&sibling.getLeftChild().getColor()==INode.BLACK&&sibling.getRightChild().getColor()==INode.BLACK;
    }
}
