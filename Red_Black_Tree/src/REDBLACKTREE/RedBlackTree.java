package REDBLACKTREE;
import REDBLACKTREE.chain_deletion.*;
import REDBLACKTREE.chain_insertion.*;
import javax.management.RuntimeErrorException;

public class RedBlackTree <T extends Comparable<T>, V> implements IRedBlackTree<T,V> {

    private INode<T,V> root;
// chain of responsibility design pattern to insertion
    private Insertion<T,V> left_left_insertion;
    private Insertion<T,V> left_right_insertion;
    private Insertion<T,V> right_left_insertion;
    private Insertion<T,V> right_right_insertion;
    private Insertion<T,V> special_insertion;

    private Deletion<T,V> left_left_deletion;
    private Deletion<T,V> right_right_deletion;
    private Deletion<T,V> left_right_deletion;
    private Deletion<T,V> right_left_deletion;
    private Deletion<T,V>red_sibling_left;
    private Deletion<T,V>red_sibling_right;
    private Deletion<T,V>three_node_left;
    private Deletion<T,V>three_node_right;

   public RedBlackTree(){
       this.root=new Null_Node<>();
       set_chain_of_deletion();
       set_chain_of_insertion();
    }
    private void set_chain_of_insertion(){
        left_left_insertion=new Left_left_insertion<>(root,this);
        left_right_insertion=new Left_right_insertion<>(root,this);
        right_left_insertion=new Right_left_insertion<>(root,this);
        right_right_insertion=new Right_right_insertion<>(root,this);
        special_insertion=new Special_insertion<>(root,this);

        special_insertion.set_next(left_left_insertion);
        left_left_insertion.set_next(left_right_insertion);
        left_right_insertion.set_next(right_left_insertion);
        right_left_insertion.set_next(right_right_insertion);
    }
    private void set_chain_of_deletion(){
        left_left_deletion=new Left_left_deletion<>(root,this);
        right_right_deletion=new Right_right_deletion<>(root,this);
        left_right_deletion=new Left_right_deletion<>(root,this);
        right_left_deletion=new Right_left_deletion<>(root,this);
        red_sibling_left=new Red_sibling_left_deletion<>(root,this);
        red_sibling_right=new Red_sibling_right_deletion<>(root,this);
        three_node_left=new Three_Node_left_deletion<>(root,this);
        three_node_right=new Three_Node_right_deletion<>(root,this);

        left_left_deletion.set_next(right_right_deletion);
        right_right_deletion.set_next(left_right_deletion);
        left_right_deletion.set_next(right_left_deletion);
        right_left_deletion.set_next(red_sibling_left);
        red_sibling_left.set_next(red_sibling_right);
        red_sibling_right.set_next(three_node_left);
        three_node_left.set_next(three_node_right);
        three_node_right.set_next(left_left_deletion);
    }

    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root.isNull();
    }

    @Override
    public void clear() {
        set_Root(new Null_Node<>());
    }

    public void set_Root(INode<T, V> new_root){
       this.root=new_root;
       special_insertion.setRoot(new_root);
       left_right_insertion.setRoot(new_root);
       left_left_insertion.setRoot(new_root);
       right_left_insertion.setRoot(new_root);
       right_right_insertion.setRoot(new_root);

        left_left_deletion.setRoot(new_root);
        right_right_deletion.setRoot(new_root);
        left_right_deletion.setRoot(new_root);
        right_left_deletion.setRoot(new_root);
        red_sibling_left.setRoot(new_root);
        red_sibling_right.setRoot(new_root);
        three_node_left.setRoot(new_root);
        three_node_right.setRoot(new_root);
    }
    @Override
    public V search(T key)  {
        check_parameter(key);
        INode<T,V> pos=get_position(key);
        if(pos.isNull())return null;
        return pos.getValue();
    }

    private boolean greater(INode<T,V> node, T key) {
        return node.getKey().compareTo(key)>=0;
    }

    private boolean equal(INode<T,V> node , T key){
        return node.getKey().compareTo(key)==0;
    }

    @Override
    public boolean contains(T key)  {
        check_parameter(key);
        return search(key)!=null;
    }

    @Override
    public void insert(T key, V value) {
         check_parameter(key,value);
         INode<T,V>inserted_node=new Full_Node<>(new Null_Node<>(),key,value);

        if(root.isNull())
        {
              inserted_node.setColor(INode.BLACK);
              set_Root(inserted_node);
              return;
         }
        inserted_node.setColor(INode.RED);
        insert(inserted_node);
    }


    private  void insert( INode<T,V>inserted_node){
        INode<T,V> current_Node=get_position(inserted_node.getKey());
        if(!current_Node.isNull())
        { // update node
            current_Node.setValue(inserted_node.getValue());
            return;
        }
        set_child(current_Node.getParent(),inserted_node);
        fix_tree_after_insertion(inserted_node);
    }

    private void set_child(INode<T,V>parent , INode<T,V> child ){
        T key =child.getKey();

        if(greater(parent,key))
            parent.setLeftChild(child);
        else
            parent.setRightChild(child);

        child.setParent(parent);
    }

    private void fix_tree_after_insertion(INode<T,V> current_Node) {
        int Num_of_red_nodes=0;
        boolean fixed=false;
        while(!current_Node.isNull()){
            if(Num_of_red_nodes==2){
                special_insertion.fix(current_Node);
                Num_of_red_nodes=0;
                fixed=true;
            }
            if(current_Node.getColor()==INode.RED){
                Num_of_red_nodes++;
            }
            else if( fixed )break;           // if node is fixed and it's parent is't red it means the rest of tree is balanced
            else Num_of_red_nodes=0;
            current_Node=current_Node.getParent();
        }
    }


    private INode<T,V> get_position(T key ){
       INode<T,V> current_Node=root;

       while (!current_Node.isNull())
       {
           if(equal(current_Node,key))
                 return current_Node;

           if(greater(current_Node,key))current_Node=current_Node.getLeftChild();
           else current_Node=current_Node.getRightChild();
       }
       return current_Node;
    }

    @Override
    public boolean delete(T key) throws RuntimeErrorException {
        check_parameter(key);
        INode<T,V>deleted_node=get_position(key);

        if(deleted_node.isNull())
        return false;

        if(deleted_node==root&&root.getRightChild().isNull()&&root.getLeftChild().isNull()){ // last element in tree
            this.clear();
            return true;
        }
        INode<T,V> successor_of_deleted_node=get_successor(deleted_node);
        swap_nodes(deleted_node,successor_of_deleted_node);

        deleted_node=successor_of_deleted_node;



        if(deleted_node.getColor()==INode.RED)
        {
          delete_me(deleted_node);
         return true;
        }
        INode<T,V>parent=deleted_node.getParent();


        if(check_one_of_child_of_deleted_node_is_red_and_replace_it(deleted_node)) return true;


        INode<T,V>empty_node=new Null_Node<>();
        if(parent.getRightChild()==deleted_node)parent.setRightChild(empty_node);
        else parent.setLeftChild(empty_node);
        empty_node.setParent(parent);

        right_left_deletion.fix(empty_node);

        return true;

    }

    private boolean check_one_of_child_of_deleted_node_is_red_and_replace_it(INode<T,V> deleted_node) {
        INode<T,V>target=new Null_Node<>();
        INode<T,V>empty=new Null_Node<>();
        if(!deleted_node.getLeftChild().isNull())
        {  // it means the node is black and one of it's child is red so it takes it's place
            target=deleted_node.getLeftChild();
            deleted_node.setLeftChild(empty);
            empty.setParent(deleted_node);
        }else if(!deleted_node.getRightChild().isNull())
        {
            target=deleted_node.getRightChild();
            deleted_node.setRightChild(empty);
            empty.setParent(deleted_node);
        }

        if(!target.isNull())
        {
            swap_nodes(deleted_node,target);
            return true;
        }
        return false;
    }

    private void delete_me( INode<T,V> node ){
        INode<T,V>parent=node.getParent();
        INode<T,V>empty_node=new Null_Node<>();

        if(parent.getRightChild()==node)
            parent.setRightChild(empty_node);
        else
            parent.setLeftChild(empty_node);

        empty_node.setParent(parent);
    }

    public static <T extends Comparable<T>, V> void swap_nodes(INode<T,V> node1, INode<T,V> node2) {
      T temp_key=node1.getKey();
      V temp_value=node1.getValue();
      node1.setKey(node2.getKey());
      node1.setValue(node2.getValue());
      node2.setValue(temp_value);
      node2.setKey(temp_key);
    }

    private INode<T,V> get_successor(INode<T, V> node) {
       if(node.getLeftChild().isNull())return node;
       node=node.getLeftChild();
       while (!node.getRightChild().isNull())node=node.getRightChild();
       return node;
    }
    void check_parameter(Object param1){
      if(param1==null) throw new RuntimeErrorException(new Error());
    }
    void check_parameter(Object param1,Object param2){
      check_parameter(param1);
      check_parameter(param2);
    }
}
