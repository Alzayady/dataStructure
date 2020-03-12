package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Heap <T extends Comparable<T>> implements IHeap <T>{
    private int size ;
    private ArrayList<  INode<T>  > nodes ;
    public Heap(){
        nodes=new ArrayList<>();
        nodes.add(null);
        size=1;
    }

    public INode<T> getNode(int index){
        if(index>=size)return null;
        return nodes.get(index);
    }

    @Override
    public INode<T> getRoot() {
        if(size==1)return null;
        return nodes.get(1);
    }

    @Override
    public int size() {
        return size-1;
    }

    @Override
    public T extract() {
        if(size==1)return null;
        T value= nodes.get(1).getValue();
        nodes.get(1).setValue(nodes.get(size-1).getValue());
        nodes.get(size-1).setValue(value);
        size--;
        heapify(this.getRoot());
        return value;
    }

    @Override
    public void heapify(INode<T> iNode) {
        if(iNode==null)return;
        INode<T> node = getMax(getMax(iNode,iNode.getLeftChild()),iNode.getRightChild());
        if(node.getValue()==iNode.getValue())return;
        T v = iNode.getValue();
        iNode.setValue(node.getValue());
        node.setValue(v);
        heapify(node);
    }
    private INode<T> getMax(INode <T> n1 , INode <T> n2 ){
        //if(n1==null||!((Node)n1).isInHeap())return n2;
        if(n1==null)return n2;
        if(n2==null)return n1;
       // if(n2==null||!((Node)n2).isInHeap())return n1;
        if(n1.getValue().compareTo(n2.getValue())>0)return n1;
        return n2;
    }


    @Override
    public void insert(T value) {
        if(value==null)return;
         INode<T> node =new Node<T>(this,value,size);
         if(nodes.size()>size)nodes.set(size,node);
         else nodes.add(node);
         while (node.getParent()!=null&&node.getParent().getValue().compareTo(node.getValue())<0){
           T v = node.getValue();
           node.setValue(node.getParent().getValue());
           node.getParent().setValue(v);
           node=node.getParent(); // update Node
         }
         size++;
    }

    @Override
    public void build(Collection<T> collection) {
        if(collection==null){
            size=1;
            return;
        }
         int s  =collection.size();
        size=s+1;
        nodes.clear();
        nodes.add(null);
        Iterator<T> it = collection.iterator();
        int i = 1 ;
       while (it.hasNext()){
            nodes.add(new Node<>(this,it.next(),i++));
        }
         int end = s/2 ;
         int begin=getLastPowerOfTwo(s/2);
         while (begin>0){
          for(  i = begin ; i <=end ; i++){
              heapify(getNode(i));
          }
          end=begin-1;
          begin/=2;
      }

    }
    private int getLastPowerOfTwo(int mask){
        int ans= 0 ;
        while (mask/2!=0){
            mask/=2;
            ans++;
        }
        return 1<<ans;
    }



    public boolean IsEmpty(){
        return this.size==1;
    }
    @Override
    public String toString(){
        String s ="";
        for(int i = 1 ; i <size ; i++){
            //s+=nodes[i].getValue().toString()+" ";
        }
        return s;
    }
    void  setSize(int x){
        size=x+1;
    }
}
