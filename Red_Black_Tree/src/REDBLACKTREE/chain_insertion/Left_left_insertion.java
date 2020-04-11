package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Left_left_insertion  <T extends Comparable<T>, V> extends Insertion <T,V> {

    public Left_left_insertion(INode<T, V> root, RedBlackTree<T,V> redBlackTree) {
        super(root,redBlackTree);
    }


    @Override
    public void fix(INode<T, V> root_node) {

        if(!my_type(root_node)){
            next.fix(root_node);
            return;
        }

        INode<T,V>red_node=root_node.getLeftChild();
        INode<T,V>grand_father=root_node.getParent();
        INode<T,V>black_node=red_node.getRightChild();

        swap_parents(root_node,red_node,grand_father);

        red_node.setRightChild(root_node);
        root_node.setParent(red_node);

        root_node.setLeftChild(black_node);
        black_node.setParent(root_node);

        edit_color(red_node);

    }

    @Override
    public boolean my_type(INode<T, V> root_node) {
        return (root_node.getLeftChild().getColor()==INode.RED)&&(root_node.getLeftChild().getLeftChild().getColor()==INode.RED);
    }
}
