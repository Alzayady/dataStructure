package CompareSorting;
import java.util.ArrayList;
import java.util.Random;

public class CompareSorting {
    public  void compare(){

        Random random =new Random();
            ArrayList <Integer> unSortedArray= new ArrayList<>();
            ArrayList <Integer> unSortedArray1= new ArrayList<>();
            ArrayList <Integer> unSortedArray2= new ArrayList<>();
            for (int i = 0; i < 2000000 ; i++) {
                int x = random.nextInt();
                unSortedArray.add(x);
                unSortedArray1.add(x);
                unSortedArray2.add(x);
            }
            MergeSort<Integer>mergeSort = new MergeSort<>(unSortedArray);
            BubbleSort<Integer>bubbleSort = new BubbleSort<>(unSortedArray1);
            HeapSort<Integer>heapSort = new HeapSort<>(unSortedArray2);
            new Thread(mergeSort).start();
            new Thread(bubbleSort).start();
            new Thread(heapSort).start();
        }

    }



