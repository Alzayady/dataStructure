package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;

public class Right_right_insertion <T extends Comparable<T>, V> extends Insertion <T,V> {

    public Right_right_insertion(Fixing_Tree<T,V> fixing_tree){
        super(fixing_tree);
    }

    @Override
    public void fix(INode<T, V> root_node) {

        if(!my_type(root_node)){
            next.fix(root_node);
            return;
        }

        INode<T,V>red_node=root_node.getRightChild();
        fixing_tree.left_Rotation(root_node);
        edit_color(red_node);
    }

    @Override
    public boolean my_type(INode<T, V> root_node) {
        return (root_node.getLeftChild().getColor()==INode.BLACK)&& (root_node.getRightChild().getLeftChild().getColor()==INode.BLACK);
    }
}
