package REDBLACKTREE.chain_insertion;

import REDBLACKTREE.INode;
import REDBLACKTREE.RedBlackTree;

public class Special_insertion <T extends Comparable<T>, V> extends Insertion <T,V> {

    public Special_insertion(INode<T, V> root, RedBlackTree<T,V> redBlackTree) {
        super(root,redBlackTree);
    }

    @Override
    public void fix(INode<T, V> root_node) {
        if(!my_type(root_node)){
            next.fix(root_node);
            return;
        }

        if(root_node==root)
            root_node.setColor(INode.BLACK);
        else
            root_node.setColor(INode.RED);

        root_node.getRightChild().setColor(INode.BLACK);
        root_node.getLeftChild().setColor(INode.BLACK);
    }

    @Override
    public boolean my_type(INode<T, V> root_node) {
        return (root_node.getLeftChild().getColor()==root_node.getRightChild().getColor());
    }
}
