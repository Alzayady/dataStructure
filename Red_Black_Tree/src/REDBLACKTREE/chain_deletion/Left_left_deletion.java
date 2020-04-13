package REDBLACKTREE.chain_deletion;

import REDBLACKTREE.Fixing_Tree;
import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Left_left_deletion  <T extends Comparable<T>, V> extends Deletion<T,V> {
    public Left_left_deletion( Fixing_Tree<T, V> fixing_tree) {
        super( fixing_tree);
    }

    @Override
   public void fix(INode<T, V> bad_node) {

        if(!my_type((bad_node))){
            next.fix(bad_node);
            return;
        }
        INode<T,V>parent=bad_node.getParent();
        INode<T,V>sibling=parent.getLeftChild();

        fixing_tree.right_Rotation(parent);

        if(parent.getColor()==INode.RED){
            sibling.setColor(INode.RED);
            sibling.getLeftChild().setColor(INode.BLACK);
            parent.setColor(INode.BLACK);
        }else {
            sibling.getLeftChild().setColor(INode.BLACK);
        }
    }

    @Override
    boolean my_type(INode<T, V> root_node) {

        INode<T,V>sibling=root_node.getParent().getLeftChild();
        if(sibling.isNull())return false;
        return fixing_tree.I_AM_RIGHT_CHILD(root_node)&&sibling.getColor()==INode.BLACK&&sibling.getLeftChild().getColor()==INode.RED;
    }


}
