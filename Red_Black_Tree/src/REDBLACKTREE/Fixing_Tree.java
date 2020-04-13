package REDBLACKTREE;

import REDBLACKTREE.chain_deletion.*;
import REDBLACKTREE.chain_insertion.*;

public class Fixing_Tree <T extends Comparable<T>, V>  {
    RedBlackTree<T,V> redBlackTree;



    private Insertion<T,V> left_left_insertion;
    private Insertion<T,V> left_right_insertion;
    private Insertion<T,V> right_left_insertion;
    private Insertion<T,V> right_right_insertion;
    private Insertion<T,V> special_insertion;

    private Deletion<T,V> left_left_deletion;
    private Deletion<T,V> right_right_deletion;
    private Deletion<T,V> left_right_deletion;
    private Deletion<T,V> right_left_deletion;
    private Deletion<T,V>red_sibling_left;
    private Deletion<T,V>red_sibling_right;
    private Deletion<T,V>three_node_left;
    private Deletion<T,V>three_node_right;


    public Fixing_Tree (RedBlackTree<T,V> redBlackTree){
        this.redBlackTree=redBlackTree;
        set_chain_of_deletion();
        set_chain_of_insertion();
    }
    private void set_chain_of_insertion(){
        left_left_insertion=new Left_left_insertion<>(this);
        left_right_insertion=new Left_right_insertion<>(this);
        right_left_insertion=new Right_left_insertion<>(this);
        right_right_insertion=new Right_right_insertion<>(this);
        special_insertion=new Special_insertion<>(this);

        special_insertion.set_next(left_left_insertion);
        left_left_insertion.set_next(left_right_insertion);
        left_right_insertion.set_next(right_left_insertion);
        right_left_insertion.set_next(right_right_insertion);
    }

    private void set_chain_of_deletion(){
        left_left_deletion=new Left_left_deletion<>(this);
        right_right_deletion=new Right_right_deletion<>(this);
        left_right_deletion=new Left_right_deletion<>(this);
        right_left_deletion=new Right_left_deletion<>(this);
        red_sibling_left=new Red_sibling_left_deletion<>(this);
        red_sibling_right=new Red_sibling_right_deletion<>(this);
        three_node_left=new Three_Node_left_deletion<>(this);
        three_node_right=new Three_Node_right_deletion<>(this);

        left_left_deletion.set_next(right_right_deletion);
        right_right_deletion.set_next(left_right_deletion);
        left_right_deletion.set_next(right_left_deletion);
        right_left_deletion.set_next(red_sibling_left);
        red_sibling_left.set_next(red_sibling_right);
        red_sibling_right.set_next(three_node_left);
        three_node_left.set_next(three_node_right);
        three_node_right.set_next(left_left_deletion);
    }


    public void fix_after_insertion(INode<T, V> node) {
        special_insertion.fix(node);
    }

    public void fix_after_Deletion(INode<T, V> node) {
        right_left_deletion.fix(node);

    }


    public boolean is_root(INode<T, V> node) {
        return node==redBlackTree.getRoot();
    }

    public void set_Root(INode<T, V> node) {
        redBlackTree.set_Root(node);
    }
    public void left_Rotation(INode<T, V> node){

        INode<T,V>right_child=node.getRightChild();
        INode<T,V>neglected=right_child.getLeftChild();

        swap_parent(node,right_child);

        right_child.setLeftChild(node);
        node.setParent(right_child);

        node.setRightChild(neglected);
        neglected.setParent(node);

    }
    public void right_Rotation(INode<T, V> node){

        INode<T,V>left_child=node.getLeftChild();
        INode<T,V>neglected=left_child.getRightChild();

        swap_parent(node,left_child);

        left_child.setRightChild(node);
        node.setParent(left_child);

        node.setLeftChild(neglected);
        neglected.setParent(node);

    }

    public void swap_parent(INode<T, V> parent, INode<T, V> sibling) {
        if(is_root(parent)){
            set_Root(sibling);
            sibling.setParent(new Null_Node<>());
            return;
        }
        INode<T,V> grad_father=parent.getParent();
        if(grad_father.getLeftChild()==parent){
            grad_father.setLeftChild(sibling);
        }else
            grad_father.setRightChild(sibling);
        sibling.setParent(grad_father);
    }

    public boolean I_AM_LEFT_CHILD(INode<T,V> root_node) {
        return root_node.getParent().getLeftChild()==root_node;
    }

    public boolean I_AM_RIGHT_CHILD(INode<T,V> root_node) {
        return root_node.getParent().getRightChild()==root_node;
    }


}
