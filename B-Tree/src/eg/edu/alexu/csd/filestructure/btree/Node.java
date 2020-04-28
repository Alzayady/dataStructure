package eg.edu.alexu.csd.filestructure.btree;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Node <K extends Comparable<K>, V> implements IBTreeNode<K,V> {
    private IBTree<K,V> Tree;
    List<K> keys;
    List<V>values;
    List<IBTreeNode<K,V>>childes;
    private IBTreeNode<K,V>parent;
    public  Node(IBTree<K,V> Tree,IBTreeNode<K,V> parent){
        this.Tree=Tree;
        this.parent=parent;
    }
    Node(){

    }
    @Override
    public int getNumOfKeys() {
        return keys.size();
    }

    public IBTreeNode<K,V>getParent(){
        return parent;
    }
    @Override
    public void setNumOfKeys(int numOfKeys) {
//        keys.(numOfKeys);
//        values.setSize(numOfKeys);
    }

    @Override
    public boolean isLeaf() {
        return childes==null|| childes.isEmpty();
    }

    @Override
    public void setLeaf(boolean isLeaf) {

    }

    @Override
    public List<K> getKeys() {
        return keys ;
    }

    @Override
    public void setKeys(List<K> keys) {
     this.keys=keys;
    }

    @Override
    public List<V> getValues() {
        return values;
    }

    @Override
    public void setValues(List<V> values)
    {
        this.values=values;
    }

    @Override
    public List<IBTreeNode<K, V>> getChildren() {
        return childes;
    }

    @Override
    public void setChildren(List<IBTreeNode<K, V>> children) {
       this.childes=children;
    }
}
