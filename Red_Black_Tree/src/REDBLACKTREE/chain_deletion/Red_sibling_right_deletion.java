package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Red_sibling_right_deletion <T extends Comparable<T>, V> extends Deletion<T,V> {
    public Red_sibling_right_deletion( INode<T, V> root,RedBlackTree<T, V> redBlackTree) {
        super(root, redBlackTree);
    }

    @Override
    public void fix(INode<T, V> bad_node) {
        if(!my_type(bad_node)){
            next.fix(bad_node);
            return;
        }
        INode<T,V>parent=bad_node.getParent();
        INode<T,V>sibling=parent.getRightChild();


        left_Rotation(bad_node.getParent());

        sibling.setColor(INode.BLACK);
        parent.setColor(INode.RED);

        next.fix(bad_node);

    }

    @Override
    boolean my_type(INode<T, V> root_node) {
        INode<T,V> sibling=root_node.getParent().getRightChild();
        if(sibling.isNull())return false;
        return sibling.getColor()==INode.RED&&I_AM_LEFT_CHILD(root_node);
    }
}