package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Left_left_insertion  <T extends Comparable<T>, V> extends Insertion <T,V> {

    public Left_left_insertion (Fixing_Tree<T,V> fixing_tree){
       super(fixing_tree);
    }


    @Override
    public void fix(INode<T, V> root_node) {

        if(!my_type(root_node)){
            next.fix(root_node);
            return;
        }

        INode<T,V>red_node=root_node.getLeftChild();
        fixing_tree.right_Rotation(root_node);
        edit_color(red_node);

    }

    @Override
    public boolean my_type(INode<T, V> root_node) {
        return (root_node.getLeftChild().getColor()==INode.RED)&&(root_node.getLeftChild().getLeftChild().getColor()==INode.RED);
    }
}
