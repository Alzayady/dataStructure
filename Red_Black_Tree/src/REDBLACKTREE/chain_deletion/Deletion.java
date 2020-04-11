package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.INode;
import REDBLACKTREE.Null_Node;
import REDBLACKTREE.RedBlackTree;

public abstract class Deletion <T extends Comparable<T>, V>  {
     protected Deletion<T,V>next;
     protected RedBlackTree<T,V> redBlackTree;
     protected INode<T,V>root;

     public Deletion( INode<T, V> root,RedBlackTree<T, V> redBlackTree) {
          this.redBlackTree = redBlackTree;
          this.root = root;
     }


    public abstract void fix(INode<T,V> bad_node );
     abstract boolean my_type(INode<T, V> root_node);

     public void  set_next(Deletion<T,V>next){
         this.next=next;
     }
     public void setRoot(INode<T,V>root ){
          this.root=root;
     }

     protected boolean I_AM_LEFT_CHILD(INode<T,V> root_node) {
          return root_node.getParent().getLeftChild()==root_node;
     }
     protected boolean I_AM_RIGHT_CHILD(INode<T,V> root_node) {
          return root_node.getParent().getRightChild()==root_node;
     }
     protected void swap_parent(INode<T,V> parent, INode<T,V> sibling) {
          if(parent==root){
               redBlackTree.set_Root(sibling);
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
     protected void left_Rotation(INode<T,V>node){

         INode<T,V>right_child=node.getRightChild();
         INode<T,V>neglected=right_child.getLeftChild();

         swap_parent(node,right_child);

         right_child.setLeftChild(node);
         node.setParent(right_child);

         node.setRightChild(neglected);
         neglected.setParent(node);

     }
    protected void right_Rotation(INode<T,V>node){

        INode<T,V>left_child=node.getLeftChild();
        INode<T,V>neglected=left_child.getRightChild();

        swap_parent(node,left_child);

        left_child.setRightChild(node);
        node.setParent(left_child);

        node.setLeftChild(neglected);
        neglected.setParent(node);

    }

}
