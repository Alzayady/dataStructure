package eg.edu.alexu.csd.filestructure.btree;

import org.junit.Assert;

import javax.management.RuntimeErrorException;
import java.util.*;

public class BTree<K extends Comparable<K>, V> implements IBTree<K,V> {
    private int DEGREE ; // depends on machine
    public IBTreeNode<K,V> root;

    public BTree(int DEGREE)
    {
        if(DEGREE<=1){
           throw new  RuntimeErrorException(new Error());
        }
        this.DEGREE=DEGREE;
    }
    @Override
    public int getMinimumDegree() {
        return DEGREE;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        check_parameter(key,value);
        if(root==null){
            make_root(key,value);
            return;
        }
        FindPosition<K,V> findPosition =new FindPosition<>(key,root);
        if(findPosition.found_key()){
            return;
        }
        Stack<IBTreeNode<K,V>>track=findPosition.get_track();
        IBTreeNode<K,V>left=null;
        IBTreeNode<K,V>right=null;
        while (track.size()>0){
            IBTreeNode<K,V>current_node=track.peek();
            track.pop();
            Insert <K,V>insert=new Insert(2*DEGREE-1,current_node,key,value,left,right);
            if(insert.try_insert()){
                return;
            }
            insert.split();
            left=insert.get_left();
            right=insert.get_right();
            key=insert.get_new_key();
            value= insert.get_new_value();
        }
        // if code reach here , that means the root is updated
        make_root(key,value,left,right);
    }
    private void make_root(K key,V value){
        root=new Node<>();
        Vector<V>values=new Vector<>();
        values.add(value);
        Vector<K>keys=new Vector<>();
        keys.add(key);
        root.setKeys(keys);
        root.setValues(values);
    }
    private void make_root(K key,V value,IBTreeNode<K,V>left,IBTreeNode<K,V>right){
        make_root(key, value);
        Vector<IBTreeNode<K,V>>new_root_children=new Vector<>();
        new_root_children.add(left);
        new_root_children.add(right);
        root.setChildren(new_root_children);
    }


    @Override
    public V search(K key)
    {
        check_parameter(key);
        FindPosition<K,V> findPosition=new FindPosition<>(key,root);
        return findPosition.getFound_value();
    }
    @Override
    public boolean delete(K key)
    {
        check_parameter(key);
        FindPosition<K,V>findPosition=new FindPosition<>(key,root);
        if(!findPosition.found_key())
        return false;
        Stack<IBTreeNode<K,V>>stack=findPosition.get_track();
        IBTreeNode<K,V>target_node=stack.peek();
        if(target_node.isLeaf()){
            DeleteLeafNode<K,V>deleteLeafNode=new DeleteLeafNode<>(target_node,key,DEGREE-1);
            Assert.assertTrue(deleteLeafNode.try_delete());
            if(root.getNumOfKeys()==0){
                root=null;
                return true;
            }
            if(deleteLeafNode.need_fix()){
                new FixingDeletedNode<K,V>(stack,DEGREE-1,this).fix();
            }
            return true;
        }
        // internal node
        DeleteInternalNode<K,V>deleteInternalNode=new DeleteInternalNode<>(target_node,key,DEGREE-1);
        boolean need_fix = !deleteInternalNode.try_delete();
        if(need_fix){
            new FixingDeletedNode<K,V>(stack,deleteInternalNode.getTrack(),DEGREE-1,this).fix();
        }
        return true;
    }
    private void check_parameter(Object o1, Object o2) {
        check_parameter(o1);
        check_parameter(o2);
    }

    private void check_parameter(Object o){
        if(o==null){
                throw new  RuntimeErrorException(new Error());
        }
    }

    public void set_root(IBTreeNode<K, V> root) {
        this.root=root;
    }
}

