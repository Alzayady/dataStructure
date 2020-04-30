package eg.edu.alexu.csd.filestructure.btree;

import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class DeleteInternalNode <K extends Comparable<K>, V>   {
    private IBTreeNode<K,V> target_node;
    private K deleted_key;
    private IBTreeNode<K,V>predecessor;
    private IBTreeNode<K,V>successor;
    private int MIN_DEGREE;
    private Queue<IBTreeNode<K,V>>track;
    private K new_key;
    private V new_value;
    private boolean deleted=false;
    public DeleteInternalNode(IBTreeNode<K,V> target_node ,K deleted_key,int MIN_DEGREE){
        this.deleted_key=deleted_key;
        this.target_node=target_node;
        this.MIN_DEGREE=MIN_DEGREE;
        track=new LinkedList<>();
    }
    public boolean try_delete(){
        Iterator<K>iterator_keys=target_node.getKeys().iterator();
        int index_of_deleting_target=0;
        IBTreeNode<K,V>left_child,right_child;
        while (iterator_keys.hasNext()){
            K current_key=iterator_keys.next();
            if(current_key.compareTo(deleted_key)!=0){
                index_of_deleting_target++;
            }else {
                break;
            }
        }
        left_child=target_node.getChildren().get(index_of_deleting_target);
        right_child=target_node.getChildren().get(index_of_deleting_target+1);
        track.add(left_child);

        while (!left_child.isLeaf()){
            left_child=left_child.getChildren().get(left_child.getChildren().size()-1);
            track.add(left_child);
        }

        while (!right_child.isLeaf())right_child=right_child.getChildren().get(0);
        predecessor=left_child;
        successor=right_child;

        if(predecessor.getKeys().size()>MIN_DEGREE){
           remove_from_predecessor();
        }else if(successor.getKeys().size()>MIN_DEGREE){
            remove_from_successor();
        }else {
            remove_from_predecessor();
            deleted=false;
        }

            target_node.getValues().set(index_of_deleting_target,new_value);
            target_node.getKeys().set(index_of_deleting_target,new_key);

         return deleted;
    }

    private void remove_from_predecessor(){
        int num_of_keys=predecessor.getKeys().size();
        new_key =predecessor.getKeys().get(num_of_keys-1);
        new_value=predecessor.getValues().get(num_of_keys-1);
        deleted  =true;
        predecessor.getKeys().remove(num_of_keys-1);
        predecessor.getValues().remove(num_of_keys-1);
    }

    private void remove_from_successor(){
        new_key =successor.getKeys().get(0);
        new_value=successor.getValues().get(0);
        deleted=true;
        successor.getKeys().remove(0);
        successor.getValues().remove(0);
    }

    public Queue<IBTreeNode<K,V>> getTrack(){
        return track;
    }
    public IBTreeNode<K,V>getPredecessor(){
        return predecessor;
    }

}
