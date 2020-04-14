package REDBLACKTREE;
import javax.management.RuntimeErrorException;
import java.util.*;

public class tree_map <T extends Comparable<T>, V> implements ITreeMap<T,V> {

    private IRedBlackTree<T,V>redBlackTree ;
    private int size;

    public tree_map(){
        redBlackTree=new RedBlackTree<>();
        size=0;
    }
    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        check_parameter(key);
        INode<T,V> position=get_position(key);

        if(!position.isNull()){

            return new AbstractMap.SimpleEntry<T,V>(position.getKey(),position.getValue());
        }
        while (!position.isNull()&&i_am_left_child(position)){
            position=position.getParent();
        }
        if(position.isNull())return null;
        return new AbstractMap.SimpleEntry<>(position.getParent().getKey(),position.getParent().getValue());
    }

    @Override
    public T ceilingKey(T key) {
        return ceilingEntry(key).getKey();
    }

    @Override
    public void clear() {
        size=0;
      this.redBlackTree.clear();
    }

    @Override
    public boolean containsKey(T key) {
        check_parameter(key);
        return this.redBlackTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        check_parameter(value);
        return dfs(redBlackTree.getRoot(),value);
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        Set<Map.Entry<T,V>> elements=new LinkedHashSet<>();
        Stack<INode<T,V>> stack=new Stack<>();
        INode<T,V>node = redBlackTree.getRoot();
        while (!node.isNull()||!stack.empty()){

            while (!node.isNull()){
                stack.push(node);
                node=node.getLeftChild();
            }
            node=stack.peek();
            stack.pop();
            if(!node.isNull())
            elements.add(new AbstractMap.SimpleEntry<>(node.getKey(),node.getValue()));
            node=node.getRightChild();
        }

        return elements;
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if(redBlackTree.isEmpty())return null;
        INode<T,V>node=redBlackTree.getRoot();
        while (!node.getLeftChild().isNull())node=node.getLeftChild();
        return new AbstractMap.SimpleEntry<>(node.getKey(),node.getValue());
    }

    @Override
    public T firstKey() {
        if(redBlackTree.isEmpty())
        return null;
        return firstEntry().getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        check_parameter(key);
        INode<T,V> position=get_position(key);
        if(!position.isNull())return new AbstractMap.SimpleEntry<>(position.getKey(),position.getValue());
        while (!position.isNull()&&!i_am_left_child(position)){
            position=position.getParent();
        }
        if(position.isNull())return null;
        return new AbstractMap.SimpleEntry<>(position.getParent().getKey(),position.getParent().getValue());
    }

    @Override
    public T floorKey(T key) {
        AbstractMap.SimpleEntry<T,V>temp=(AbstractMap.SimpleEntry<T, V>) floorEntry(key);
        if(temp==null)return null;
        return temp.getKey();
    }

    @Override
    public V get(T key) {
        if(redBlackTree.contains(key))return redBlackTree.search(key);
        return null;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        check_parameter(toKey);
        return inner_headMap(toKey,false);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        check_parameter(toKey);
        return inner_headMap(toKey,inclusive);
    }

    private ArrayList<Map.Entry<T, V>> inner_headMap(T toKey, boolean inclusive) {
        check_parameter(toKey);
        ArrayList<Map.Entry<T,V>> elements=new ArrayList<>();
        Stack<INode<T,V>> stack=new Stack<>();
        INode<T,V>node = redBlackTree.getRoot();
        while (!node.isNull()&&!stack.empty()){

            while (node.isNull()){
                stack.push(node);
                node=node.getLeftChild();
            }
            node=stack.peek();
            stack.pop();
            if(node.getKey().compareTo(toKey)<0||(inclusive&&node.getKey().compareTo(toKey)<=0))

            elements.add(new AbstractMap.SimpleEntry<>(node.getKey(),node.getValue()));
            node=node.getRightChild();
        }
        return elements;
    }

    @Override
    public Set<T> keySet() {
        Set<T> elements=new LinkedHashSet<>();
        Stack<INode<T,V>> stack=new Stack<>();
        INode<T,V>node = redBlackTree.getRoot();
        while (!node.isNull()||!stack.empty()){
            while (!node.isNull()){
                stack.push(node);
                node=node.getLeftChild();
            }
            node=stack.peek();
            stack.pop();
            if(!node.isNull())
                elements.add(node.getKey());
            node=node.getRightChild();
        }

        return elements;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        INode<T,V>root=redBlackTree.getRoot();
        if(root.isNull())return null;
        while (!root.getRightChild().isNull())root=root.getRightChild();
        return new AbstractMap.SimpleEntry<>(root.getKey(),root.getValue());
    }

    @Override
    public T lastKey() {
       Map.Entry<T, V>last_entry=lastEntry();
        if(last_entry==null)return null;
        return last_entry.getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        if(redBlackTree.isEmpty())return null;
        Map.Entry<T, V>first_entry=firstEntry();
        redBlackTree.delete(first_entry.getKey());
        size--;
        return first_entry;
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        if(redBlackTree.isEmpty())return null;
       Map.Entry<T, V>last_entry=lastEntry();
       size--;
        redBlackTree.delete(last_entry.getKey());
        return last_entry;
    }

    @Override
    public void put(T key, V value) {
        if(!redBlackTree.contains(key))size++;
       redBlackTree.insert(key,value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        check_parameter(map);
        Set<Map.Entry<T,V> > elements=map.entrySet();
        for(Map.Entry<T,V> ele : elements){
            put(ele.getKey(),ele.getValue());
        }
    }


    @Override
    public boolean remove(T key) {
        boolean temp= redBlackTree.delete(key);
        if(temp)size--;
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Collection<V> elements=new ArrayList<>();
        Stack<INode<T,V>> stack =new Stack<>();
        stack.push(redBlackTree.getRoot());
        while (!stack.empty()){
            INode<T,V> temp =stack.peek();
            stack.pop();
            if(temp.isNull())continue;
            elements.add(temp.getValue());
            stack.push(temp.getRightChild());
            stack.push(temp.getLeftChild());
        }
        return elements;
    }


    private boolean dfs(INode<T,V> node , V value) {
        if(node.isNull())return false;
        if(node.getValue().equals(value))return true;
        return dfs(node.getRightChild(),value)||dfs(node.getLeftChild(),value);
    }

    private INode<T,V> get_position(T key ){
        INode<T,V> current_Node=redBlackTree.getRoot();

        while (!current_Node.isNull())
        {
            if(equal(current_Node,key))
                return current_Node;

            if(greater(current_Node,key))current_Node=current_Node.getLeftChild();
            else current_Node=current_Node.getRightChild();
        }
        return current_Node;
    }

    private boolean greater(INode<T,V> node, T key) {
        return node.getKey().compareTo(key)>=0;
    }

    private boolean equal(INode<T,V> node , T key){
        return node.getKey().compareTo(key)==0;
    }

    private boolean i_am_left_child(INode<T,V> position) {
        INode<T,V> parent=position.getParent();
        return parent.getLeftChild()==position;
    }
    void check_parameter(Object param1){
        if(param1==null) throw new RuntimeErrorException(new Error());
    }

}
