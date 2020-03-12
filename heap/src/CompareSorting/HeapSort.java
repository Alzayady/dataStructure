package CompareSorting;

import eg.edu.alexu.csd.filestructure.sort.ISort;
import eg.edu.alexu.csd.filestructure.sort.Sort;
import java.util.ArrayList;
public class HeapSort <T extends Comparable<T>> implements  Runnable {
    public static final String ANSI_PURPLE = "\u001B[35m";


    ArrayList <T> unSortedArray ;
    public  HeapSort (ArrayList <T>  unSortedArray){
        this.unSortedArray=unSortedArray;
    }
    @Override
    public void run() {
        ISort<T> sort=new Sort<>();
        long timeBefore =System.currentTimeMillis();
        System.out.println(ANSI_PURPLE+"Heap Sort STARTED");
        sort.heapSort(unSortedArray);
        System.out.println(ANSI_PURPLE+"Heap Sort ENDS at time " + (System.currentTimeMillis() - timeBefore )+  " MS");
//        for(Object i : unSortedArray) System.out.println(ANSI_PURPLE+i);

    }
}
