package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public abstract class Insertion  <T extends Comparable<T>, V> {
    protected Fixing_Tree<T,V>fixing_tree;
    protected Insertion<T,V>next;

    Insertion(Fixing_Tree<T,V> fixing_tree){
        this.fixing_tree=fixing_tree;
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

}
