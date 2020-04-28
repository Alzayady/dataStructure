package eg.edu.alexu.csd.filestructure.btree;

import org.junit.Assert;

import java.util.*;

public class FixingDeletedNode <K extends Comparable<K>, V>{
    private  final int MIN_DEGREE  ;
    private  Stack<IBTreeNode<K, V>> stack;
    private K left_key=null;
    private K right_key=null;
    private IBTreeNode<K,V>left_child=null;
    private IBTreeNode<K,V>right_child=null;
    private int index_of_left_parent;
    private int index_of_right_parent;
    private IBTreeNode<K,V>parent_node;
    private IBTreeNode<K,V>current_node;
    private BTree<K,V>tree;
    public FixingDeletedNode( Stack<IBTreeNode<K, V>> stack, int MIN_DEGREE, BTree<K,V>tree) {
        this.stack=stack;
        this.MIN_DEGREE=MIN_DEGREE;
        this.tree=tree;
    }

    public FixingDeletedNode( Stack<IBTreeNode<K, V>> stack, Queue<IBTreeNode<K, V>> track, int MIN_DEGREE,BTree<K,V>tree) {
        this(stack,MIN_DEGREE,tree);
        while (track.size()>0){
            stack.push(track.peek());
            track.remove();
        }
    }

    public void fix() {
        if (stack.peek().getKeys().size()>=MIN_DEGREE)return;
        if(stack.peek()==tree.getRoot()){
            if(stack.peek().getNumOfKeys()==0){
                if(stack.peek().getChildren().size()==1){
                    tree.set_root(stack.peek().getChildren().get(0));
                }
            }
            return;
        }
        current_node=stack.peek();
        stack.pop();
        parent_node=stack.peek();
        K least_key=current_node.getKeys().get(0);
        prepare_siblings_and_parent_keys(least_key);
        //Vector<IBTreeNode<K,V>>sibling=get_sibling(least_key,parent_node);
        if(borrow(left_child,index_of_left_parent,true)){
            return;
        }
        if(borrow(right_child,index_of_right_parent,false)){
            return;
        }
        if(left_child!=null){
            merge(left_child,index_of_left_parent,true);
            new FixingDeletedNode<K,V>(stack,MIN_DEGREE,tree).fix();
            return;
        }
        if(right_child!=null){
            merge(right_child,index_of_right_parent,false);
            new FixingDeletedNode<K,V>(stack,MIN_DEGREE,tree).fix();
            return;
        }
        Assert.fail();
    }




    private void prepare_siblings_and_parent_keys(K least_key) {
        Iterator<K>iterator_keys=parent_node.getKeys().iterator();
        int index=0;
        boolean found = false;
        while (iterator_keys.hasNext()){
            K next_key=iterator_keys.next();
            if(next_key.compareTo(least_key)>0){
                found=true;
                break;
            }else {
                index++;
            }
        }
        if(index==0){ // no left sibling
           right_child= parent_node.getChildren().get(1);
           right_key=parent_node.getKeys().get(0);
           index_of_right_parent=0;
        }else if(!found){ // no right sibling
            left_child =parent_node.getChildren().get(index-1);
            left_key   =parent_node.getKeys().get(index-1);
            index_of_left_parent=index-1;
        }else {
            left_child  =parent_node.getChildren().get(index-1);
            right_child =parent_node.getChildren().get(index+1);
            left_key    =parent_node.getKeys().get(index-1);
            right_key   =parent_node.getKeys().get(index);
            index_of_left_parent=index-1;
            index_of_right_parent=index;
        }
    }
    private boolean borrow(IBTreeNode<K,V>sibling, int index_of_parent, boolean is_left){
        if(sibling==null)return false;
        if(sibling.getKeys().size()==MIN_DEGREE)return false;

        int num_of_keys=sibling.getKeys().size();
        int index_of_target=num_of_keys-1;
        if(!is_left)index_of_target=0;
        K borrowed_key=sibling.getKeys().get(index_of_target);
        sibling.getKeys().remove(index_of_target);
        V borrowed_value=sibling.getValues().get(index_of_target);
        sibling.getValues().remove(index_of_target);
        IBTreeNode<K,V>borrowed_child=null;
        if(!sibling.isLeaf()){
            if(is_left) {
                borrowed_child = sibling.getChildren().get(num_of_keys);
                sibling.getChildren().remove(num_of_keys);
            }else {
                borrowed_child = sibling.getChildren().get(0);
                sibling.getChildren().remove(0);
            }
        }
        K parent_key=parent_node.getKeys().get(index_of_parent);
        V parent_value=parent_node.getValues().get(index_of_parent);
        // update parent
        parent_node.getKeys().set(index_of_parent,borrowed_key);
        parent_node.getValues().set(index_of_parent,borrowed_value);
        // update node

        int num_of_keys_current_node=current_node.getNumOfKeys();
        if(!is_left) {
            current_node.getKeys().add(num_of_keys_current_node, parent_key);
            current_node.getValues().add(num_of_keys_current_node, parent_value);
            if (!current_node.isLeaf()) {
                current_node.getChildren().add(num_of_keys_current_node + 1, borrowed_child);
            }
        }else {
            current_node.getKeys().add(0,parent_key);
            current_node.getValues().add(0,parent_value);
            if(!current_node.isLeaf()){
                current_node.getChildren().add(0,borrowed_child);
            }
        }
        return true;
    }
    private void merge(IBTreeNode<K,V>sibling , int index_of_parent, boolean is_left){
        //left -> index_of_parent
        // right -> index of parent
        List<K> merged_keys=new Vector<>();
        List<V>merged_values=new Vector<>();
        List<IBTreeNode<K,V>>merged_children=new Vector<>();

        if(is_left){
            merged_keys=sibling.getKeys();
            merged_values=sibling.getValues();
            if(!sibling.isLeaf())merged_children=sibling.getChildren();
            int size=sibling.getNumOfKeys();
            merged_keys.add(size,parent_node.getKeys().get(index_of_parent));
            merged_values.add(size,parent_node.getValues().get(index_of_parent));
            merged_keys.addAll(current_node.getKeys());
            merged_values.addAll(current_node.getValues());
            if(!sibling.isLeaf())merged_children.addAll(current_node.getChildren());
            sibling.setKeys(merged_keys);
            sibling.setValues(merged_values);
            if(!sibling.isLeaf())sibling.setChildren(merged_children);
        }else {
            merged_keys=current_node.getKeys();
            merged_values=current_node.getValues();
            if(!sibling.isLeaf())merged_children=current_node.getChildren();
            int size=current_node.getNumOfKeys();
            merged_keys.add(size,parent_node.getKeys().get(index_of_parent));
            merged_values.add(size,parent_node.getValues().get(index_of_parent));
            merged_keys.addAll(sibling.getKeys());
            merged_values.addAll(sibling.getValues());
            if(!sibling.isLeaf())merged_children.addAll(sibling.getChildren());

            current_node.setKeys(merged_keys);
            current_node.setValues(merged_values);
            if(!current_node.isLeaf())current_node.setChildren(merged_children);
        }
        parent_node.getValues().remove(index_of_parent);
        parent_node.getKeys().remove(index_of_parent);
        parent_node.getChildren().remove(index_of_parent+1);
    }

}
