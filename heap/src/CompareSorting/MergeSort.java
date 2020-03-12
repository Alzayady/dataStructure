package CompareSorting;

import eg.edu.alexu.csd.filestructure.sort.ISort;
import eg.edu.alexu.csd.filestructure.sort.Sort;
import java.util.ArrayList;
public class MergeSort <T extends Comparable<T>> implements  Runnable {
    public static final String ANSI_GREEN = "\u001B[32m";
    ArrayList <T> unSortedArray ;
    public  MergeSort (ArrayList <T>  unSortedArray){
        this.unSortedArray=unSortedArray;
    }
    @Override
    public void run() {
        ISort<T> sort=new Sort<>();
        long timeBefore =System.currentTimeMillis();
        System.out.println(ANSI_GREEN+"MERGE SORT STARTED");
        sort.sortFast(unSortedArray);
        System.out.println(ANSI_GREEN + "MERGE SORT ENDS at time " + (System.currentTimeMillis() - timeBefore )+  " MS");
//        for(Object i : unSortedArray) System.out.println(ANSI_GREEN+i);

    }
}
