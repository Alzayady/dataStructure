package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Right_left_deletion  <T extends Comparable<T>, V> extends Deletion<T,V> {
    public Right_left_deletion( Fixing_Tree<T, V> fixing_tree) {
        super( fixing_tree);
    }

    @Override
   public void fix(INode<T, V> bad_node) {
        if(!my_type(bad_node)){
            next.fix(bad_node);
            return;
        }
        INode<T,V>parent=bad_node.getParent();
        INode<T,V>sibling=parent.getRightChild();
        INode<T,V>grand_son_left=sibling.getLeftChild();

        INode<T,V>temp_left=grand_son_left.getLeftChild();
        INode<T,V>temp_right=grand_son_left.getRightChild();

        fixing_tree.swap_parent(parent,grand_son_left);

        grand_son_left.setLeftChild(parent);
        parent.setParent(grand_son_left);

        grand_son_left.setRightChild(sibling);
        sibling.setParent(grand_son_left);

        sibling.setLeftChild(temp_right);
        temp_right.setParent(sibling);
        parent.setRightChild(temp_left);
        temp_left.setParent(parent);

        if(parent.getColor()==INode.RED){
            parent.setColor(INode.BLACK);
        }else {
            sibling.setColor(INode.BLACK);
            parent.setColor(INode.BLACK);
            grand_son_left.setColor(INode.BLACK);
        }


    }

    @Override
    boolean my_type(INode<T, V> root_node) {
        INode<T,V>sibling=root_node.getParent().getRightChild();
        if(sibling.isNull())return false;
        return fixing_tree.I_AM_LEFT_CHILD(root_node)&&sibling.getColor()==INode.BLACK
                &&sibling.getLeftChild().getColor()==INode.RED
                &&sibling.getRightChild().getColor()==INode.BLACK;
    }
}
