package eg.edu.alexu.csd.filestructure.sort;

public class Node  <T extends Comparable<T>>  implements INode<T>  {
    private int index ;
    private Heap heap ;
    private T value;
    public Node(Heap heap , T value , int index ){
        this.heap=heap;
        this.value=value;
        this.index=index;
    }
    @Override
    public INode<T> getLeftChild() {
        return heap.getNode(index*2);
    }

    @Override
    public INode<T>getRightChild() {
        return heap.getNode(index*2+1);
    }

    @Override
    public INode<T> getParent() {
        return   heap.getNode(index/2);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value=value;
    }

    public void setHeap(Heap heap) {
        this.heap = heap;
    }
}
