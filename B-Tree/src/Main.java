import eg.edu.alexu.csd.filestructure.btree.BTree;
import eg.edu.alexu.csd.filestructure.btree.IBTree;
import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;
import eg.edu.alexu.csd.filestructure.btree.PrintTree;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
	// write your code here
        BTree<Integer,String> bTree=new BTree<>(3);
//        for(int i =0 ; i< 1000000 ; i++){
//            bTree.insert(i,i);
//           // PrintTree.print(bTree.getRoot());
//        }
        List<Integer> inp = Arrays.asList(new Integer[]{149, 119, 168, 153, 26, 48, 67, 109, 176, 29, 130, 92, 4, 127, 99, 55, 24, 82, 1, 11, 6, 105, 102});
        for (int i : inp){
            bTree.insert(i, "Soso" + i);
        }

//        149
//        119
//        168
//        153
//        26
//        48
//        67
//        109
//        176
//        29
//        130
//        92
//        4
//        127
//        99
//        55
//        24
//        82
//        1
//        11
//        6
//        105
//        102


        PrintTree.print(bTree.getRoot());
        Assert.assertTrue(bTree.delete(48));
        Assert.assertNull(bTree.search(48));
        PrintTree.print(bTree.getRoot());
        Assert.assertTrue(bTree.delete(82));
        Assert.assertNull(bTree.search(82));
        PrintTree.print(bTree.getRoot());
        Assert.assertTrue(bTree.delete(99));
        Assert.assertNull(bTree.search(99));
        PrintTree.print(bTree.getRoot());
        Assert.assertTrue(bTree.delete(149));
        Assert.assertNull(bTree.search(149));
        PrintTree.print(bTree.getRoot());

//        System.out.println(bTree.getRoot().getValues());
//        List<IBTreeNode<Integer,Integer>>ch=bTree.getRoot().getChildren();
//        Iterator<IBTreeNode<Integer, Integer>> ss=ch.iterator();
//        while (ss.hasNext()) System.out.println(ss.next().getKeys());
//
//        Vector<Integer>ss=new Vector<>();
//        ss.add(5);
//        ss.add(6);
//        ss.add(7);
//        ss.add(8);
//        System.out.println(ss.subList(1,2));
    }
}
