package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Right_left_insertion <T extends Comparable<T>, V> extends Insertion <T,V>  {

    public Right_left_insertion(INode<T, V> root, RedBlackTree<T,V> redBlackTree) {
        super(root,redBlackTree);
    }


    @Override
    public void fix(INode<T, V> root_node) {

        if(!my_type(root_node)){
            next.fix(root_node);
            return;
        }

        INode<T,V>red_node=root_node.getRightChild();
        INode<T,V>grand_father=root_node.getParent();
        INode<T,V> sec_red_node=red_node.getLeftChild();



        swap_parents(root_node,sec_red_node,grand_father);

        INode<T,V> temp_left= sec_red_node.getLeftChild();
        INode<T,V> temp_right=sec_red_node.getRightChild();


        sec_red_node.setLeftChild(root_node);
        root_node.setParent(sec_red_node);


        sec_red_node.setRightChild(red_node);
        red_node.setParent(sec_red_node);



        root_node.setRightChild(temp_left);
        temp_left.setParent(root_node);

        red_node.setLeftChild(temp_right);
        temp_right.setParent(red_node);

        edit_color(sec_red_node);
    }

    @Override
    public boolean my_type(INode<T, V> root_node) {
        return   !(root_node.getLeftChild().getColor()==INode.RED) &&(root_node.getRightChild().getLeftChild().getColor()==INode.RED);
    }
}
