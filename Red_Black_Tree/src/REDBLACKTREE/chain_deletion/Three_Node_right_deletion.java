package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public class Three_Node_right_deletion <T extends Comparable<T>, V> extends Deletion<T,V> {

    public Three_Node_right_deletion( INode<T, V> root,RedBlackTree<T, V> redBlackTree) {
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
          parent.getRightChild().setColor(INode.RED);
          return;
        }
        parent.getRightChild().setColor(INode.RED);
        if(parent==root){
            return;
        }
        next.fix(parent);
    }

    @Override
    boolean my_type(INode<T, V> root_node) {
        INode<T,V> sibling = root_node.getParent().getRightChild();
        if(sibling.isNull())return false;
        return sibling.getLeftChild().getColor()==INode.BLACK&&sibling.getRightChild().getColor()==INode.BLACK
                &&I_AM_LEFT_CHILD(root_node)&&sibling.getColor()==INode.BLACK;
    }
}
