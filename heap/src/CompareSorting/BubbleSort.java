package CompareSorting;

import eg.edu.alexu.csd.filestructure.sort.ISort;
import eg.edu.alexu.csd.filestructure.sort.Sort;
import java.util.ArrayList;
public class BubbleSort <T extends Comparable<T>> implements  Runnable {
    public static final String ANSI_RED = "\u001B[31m";

    ArrayList <T> unSortedArray ;
    public  BubbleSort (ArrayList <T>  unSortedArray){
        this.unSortedArray=unSortedArray;
    }
    @Override
    public void run() {
        ISort<T> sort=new Sort<>();
        long timeBefore =System.currentTimeMillis();
        System.out.println(ANSI_RED+"BubbleSort STARTED");
        sort.sortSlow(unSortedArray);
        System.out.println(ANSI_RED+"Bubble Sort ENDS at time " + (System.currentTimeMillis() - timeBefore )+  " MS");
//        for(Object i : unSortedArray) System.out.println(ANSI_RED+i);
    }
}
