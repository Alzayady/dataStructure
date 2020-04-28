package eg.edu.alexu.csd.filestructure.btree;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class FindPosition<K extends Comparable<K>, V>  {
    private Stack<IBTreeNode<K,V>>track;
    private boolean exist=false;
    private K key;
    private V found_value;
    public FindPosition(K key, IBTreeNode<K,V>root){
        track=new Stack<>();
        this.key=key;
        track.push(root);

        while (root!=null){
            List<K> keys=root.getKeys();
            List<IBTreeNode<K,V>>children=root.getChildren();
            Iterator<K> iterator_keys=keys.iterator();
            int num_of_target_value=0;
            Iterator<IBTreeNode<K,V>>iterator_children=null;

            if(!root.isLeaf()){
                iterator_children=children.iterator();
            }


            while (iterator_keys.hasNext()){
                K current_key=iterator_keys.next();
                num_of_target_value++;
                if(current_key.compareTo(key)==0){
                    exist=true;
                    found_value=root.getValues().get(num_of_target_value-1);
                    return;
                }

                if(current_key.compareTo(key)>0){
                    if(iterator_children==null)return;
                    root=iterator_children.next();
                    track.push(root);
                    break;
                }else {
                    if(!iterator_keys.hasNext()&&root.isLeaf()){
                        return;
                    }
                    if(!iterator_keys.hasNext()){
                        root=children.get(children.size()-1);
                        track.push(root);
                        break;
                    }
                    if(!root.isLeaf())iterator_children.next();
                }
            }
        }
    }
    public boolean found_key(){
        return exist;
    }
    public Stack<IBTreeNode<K,V>> get_track(){
        return track;
    }
    public V getFound_value(){
        return found_value;
    }

}
