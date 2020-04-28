package eg.edu.alexu.csd.filestructure.btree;

import org.junit.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Insert  <K extends Comparable<K>, V>{
    private int max_size;
    private IBTreeNode<K,V>node;
    private K key;
    private V value;
    private IBTreeNode<K,V>left;
    private IBTreeNode<K,V>right;
    private IBTreeNode<K,V>new_right_child;
    private IBTreeNode<K,V>new_left_child;
    private K new_key;
    private V new_value;
    Insert(int max_size,IBTreeNode<K,V> node, K key, V value, IBTreeNode<K,V> left, IBTreeNode<K,V> right){
        this.node=node;
        this.key=key;
        this.value=value;
        this.left=left;
        this.right=right;
        Assert.assertEquals(1,max_size%2);
        this.max_size=max_size;

    }
    public boolean try_insert() {
        List<K> keys=node.getKeys();
        List<V>values=node.getValues();
        if(keys.size()+1<=max_size){ //can dd
          return add_value();
        }
        return false;
    }
    private boolean add_value(){
        Vector<K> new_keys=new Vector<>();
        Iterator<K>iterator_keys=node.getKeys().iterator();
        Iterator<V>iterator_values=node.getValues().iterator();

        Iterator<IBTreeNode<K,V>>iterator_children=null;
        if(!node.isLeaf())iterator_children=node.getChildren().iterator();

        Vector<V> new_values=new Vector<>();
        Vector<IBTreeNode<K,V>>new_children=new Vector<>();
        boolean inserted=false;
            while (iterator_keys.hasNext()){
                K current_key=iterator_keys.next();
                if(inserted){
                    new_keys.add(current_key);
                    new_values.add(iterator_values.next());
                    continue;
                }
                if(current_key.compareTo(key)>0){// add new keys and value
                    new_values.add(value);
                    new_keys.add(key);
                    new_keys.add(current_key);
                    new_values.add(iterator_values.next());
                    inserted=true;
                    if(!node.isLeaf()){
                        new_children.add(left);
                        new_children.add(right);
                        iterator_children.next();
                        while (iterator_children.hasNext())new_children.add(iterator_children.next());
                    }
                    continue;
                }
                new_keys.add(current_key);
                new_values.add(iterator_values.next());
                if(!node.isLeaf())new_children.add(iterator_children.next());
            }
            if(!inserted){ //means it is the last elements in the node
                inserted=true;
                new_keys.add(key);
                new_values.add(value);
                if(!node.isLeaf()){
                    new_children.add(left);
                    new_children.add(right);
                }
            }

        node.setKeys(new_keys);
        node.setValues(new_values);
        if(!node.isLeaf())node.setChildren(new_children);
        return inserted;
    }
    public  boolean split()
    {
        int part_size=max_size/2;
        boolean inserted=false;
        List<IBTreeNode<K,V>>left_children=new Vector<>();
        List<IBTreeNode<K,V>>right_children=new Vector<>();

        List<K> left_keys=  node.getKeys().subList(0,part_size);
        List<V> left_values=  node.getValues().subList(0,part_size);
        List<K>right_keys=  node.getKeys().subList(part_size+1,part_size*2+1);
        List<V> right_values=  node.getValues().subList(part_size+1,part_size*2+1);
        if(!node.isLeaf()){
            left_children=  node.getChildren().subList(0,part_size+1);
            right_children=  node.getChildren().subList(part_size+1,part_size*2+2);
        }
        new_key=node.getKeys().get(part_size);
        new_value=node.getValues().get(part_size);

        new_left_child=new Node<>();
        new_left_child.setValues(left_values);
        new_left_child.setKeys(left_keys);
        new_right_child=new Node<>();
        new_right_child.setValues(right_values);
        new_right_child.setKeys(right_keys);
        if(!node.isLeaf()){
            new_right_child.setChildren(right_children);
            new_left_child.setChildren(left_children);
        }
        if(key.compareTo(new_key)<0){ //lest side
          insert_element_in_not_complete_node(new_left_child);
        }else {
            insert_element_in_not_complete_node(new_right_child);
        }
        return true;
    }
    private void insert_element_in_not_complete_node(IBTreeNode<K,V>node){
        Iterator<K> iterator_keys=node.getKeys().iterator();
        Iterator<V>iterator_values=node.getValues().iterator();
        Iterator<IBTreeNode<K,V>>iterator_children=null;
        List<V>new_values=new Vector<>();
        List<K>new_keys=new Vector<>();
        List<IBTreeNode<K,V>>new_children=new Vector<>();
        boolean inserted=false;
        if(!node.isLeaf()){
            iterator_children=node.getChildren().iterator();
        }
        while (iterator_keys.hasNext()){
            K next_key=iterator_keys.next();
            if(next_key.compareTo(key)>0){
                new_keys.add(key);
                new_values.add(value);
                if(!node.isLeaf()){
                    new_children.add(left);
                    new_children.add(right);
                    iterator_children.next();
                    while (iterator_children.hasNext())new_children.add(iterator_children.next());
                }
                new_keys.add(next_key);
                while (iterator_keys.hasNext())new_keys.add(iterator_keys.next());
                while (iterator_values.hasNext())new_values.add(iterator_values.next());
                inserted=true;
                break;
            }
            new_keys.add(next_key);
            new_values.add(iterator_values.next());
            if(!node.isLeaf()){
                new_children.add(iterator_children.next());
            }
        }
        if(!inserted){
            new_keys.add(key);
            new_values.add(value);
            if(!node.isLeaf()){
              //  new_children.remove(new_children.get(new_children.size()-1));
                new_children.add(left);
                new_children.add(right);
            }
        }
        node.setValues(new_values);
        node.setKeys(new_keys);
        if(!node.isLeaf())node.setChildren(new_children);
    }

    public IBTreeNode<K,V> get_left() {
        return new_left_child;
    }

    public  IBTreeNode<K,V> get_right() {
        return new_right_child;
    }

    public  K get_new_key() {
        return new_key;
    }

    public  V get_new_value() {
        return new_value;
    }
}
