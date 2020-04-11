package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public class Left_right_deletion  <T extends Comparable<T>, V> extends Deletion<T,V> {
    public Left_right_deletion( INode<T, V> root,RedBlackTree<T, V> redBlackTree) {
        super(root, redBlackTree);
    }

    @Override
  public   void fix(INode<T, V> bad_node) {
        if(!my_type(bad_node)){
            next.fix(bad_node);
            return;
        }
        INode<T,V>parent=bad_node.getParent();
        INode<T,V>sibling=parent.getLeftChild();
        INode<T,V>grand_son_right=sibling.getRightChild();

        INode<T,V>temp_left=grand_son_right.getLeftChild();
        INode<T,V>temp_right=grand_son_right.getRightChild();

        swap_parent(parent,grand_son_right);

        grand_son_right.setLeftChild(sibling);
        sibling.setParent(grand_son_right);


        grand_son_right.setRightChild(parent);
        parent.setParent(grand_son_right);

        sibling.setRightChild(temp_left);
        temp_left.setParent(sibling);
        parent.setLeftChild(temp_right);
        temp_right.setParent(parent);

        if(parent.getColor()==INode.RED){
            parent.setColor(INode.BLACK);
        }else {
            grand_son_right.setColor(INode.BLACK);
            parent.setColor(INode.BLACK);
            sibling.setColor(INode.BLACK);
        }

    }

    @Override
    boolean my_type(INode<T, V> root_node) {
        INode<T,V>sibling = root_node.getParent().getLeftChild();
        if(sibling.isNull())return false;
        return I_AM_RIGHT_CHILD(root_node)&&sibling.getColor()==INode.BLACK&&
                sibling.getLeftChild().getColor()==INode.BLACK&&sibling.getRightChild().getColor()==INode.RED;
    }
}
