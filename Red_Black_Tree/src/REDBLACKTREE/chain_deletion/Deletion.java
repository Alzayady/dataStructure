package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public abstract class Deletion <T extends Comparable<T>, V>  {
     protected Deletion<T,V>next;
     protected Fixing_Tree<T,V> fixing_tree;

     public Deletion( Fixing_Tree<T, V> fixing_tree) {
          this.fixing_tree = fixing_tree;
     }

     public abstract void fix(INode<T,V> bad_node );
     abstract boolean my_type(INode<T, V> root_node);
     public void  set_next(Deletion<T,V>next){
         this.next=next;
     }


}
