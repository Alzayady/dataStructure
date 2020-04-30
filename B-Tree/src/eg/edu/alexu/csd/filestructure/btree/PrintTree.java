package eg.edu.alexu.csd.filestructure.btree;

import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrintTree <K extends Comparable<K>, V> {
    public  static<K extends Comparable<K>,V> void print(IBTreeNode<K,V> node){
        String ans="";
        Queue<IBTreeNode<K,V>>bfs=new LinkedList<>();
        bfs.add(node);
        while (bfs.size()>0){
            int current_size=bfs.size();
            while (current_size-->0){
                IBTreeNode<K,V>out_node=bfs.peek();
                bfs.remove();
                ans+=out_node.getKeys().toString();
                ans+="                    ";
                if(out_node.isLeaf())continue;
                List < IBTreeNode<K,V>> childs=out_node.getChildren();
                Iterator<IBTreeNode<K,V>>iterator=childs.iterator();
                while (iterator.hasNext())bfs.add(iterator.next());
            }
            ans+="\n";
        }
        System.out.println(ans);
        System.out.println("=======================");
    }
}
