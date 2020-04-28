package eg.edu.alexu.csd.filestructure.btree;

import java.util.Iterator;
import java.util.LinkedList;

public class DeleteLeafNode <K extends Comparable<K>, V> {

    private  int MIN_DEGREE  ;
    private final IBTreeNode<K, V> target_node;
    private  K deleted_key;

    public DeleteLeafNode(IBTreeNode<K,V> target_node , K deleted_key, int MIN_DEGREE) {
        this.deleted_key = deleted_key;
        this.target_node = target_node;
        this.MIN_DEGREE = MIN_DEGREE;
    }
    public boolean try_delete(){
        Iterator<K> iterator_keys=target_node.getKeys().iterator();
        boolean deleted=false;
        int index_of_deleted_node=0;
        while (iterator_keys.hasNext()){
            K next_key=iterator_keys.next();
            if(next_key.compareTo(deleted_key)!=0){
                index_of_deleted_node++;

            }else {
                deleted=true;
                break;
            }
        }
        target_node.getKeys().remove(index_of_deleted_node);
        target_node.getValues().remove(index_of_deleted_node);
        return deleted;
    }
    public boolean need_fix(){
        return target_node.getKeys().size()<MIN_DEGREE;
    }
}
