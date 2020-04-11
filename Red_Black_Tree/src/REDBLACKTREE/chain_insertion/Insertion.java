package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public abstract class Insertion  <T extends Comparable<T>, V> {
    protected INode<T,V>root;
    protected RedBlackTree<T,V> redBlackTree;
    protected Insertion<T,V>next;

    Insertion(INode<T,V>root,RedBlackTree<T,V> redBlackTree){
        this.root=root;
        this.redBlackTree=redBlackTree;
    }
    public void setRoot(INode<T,V>root ){
        this.root=root;
    }
     public void  set_next(Insertion<T,V> next){
        this.next=next;
    }
    public abstract void fix(INode<T,V> bad_node );
    public abstract boolean my_type(INode<T, V> root_node);
    protected void edit_color(INode<T,V> local_root) {

        local_root.setColor(INode.BLACK);
        local_root.getRightChild().setColor(INode.RED);
        local_root.getLeftChild().setColor(INode.RED);

    }
    protected void swap_parents(INode<T, V> root_node , INode<T, V> new_parent ,INode<T, V> grand_father ){

        if(root!=root_node) {
            if (grand_father.getLeftChild()==root_node) {
                grand_father.setLeftChild(new_parent);
            } else {
                grand_father.setRightChild(new_parent);
            }
            new_parent.setParent(grand_father);
        }else{
            redBlackTree.set_Root(new_parent);
            new_parent.setParent(new Null_Node<>());
        }

    }
}
