package eg.edu.alexu.csd.filestructure.sort;

import CompareSorting.CompareSorting;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        new CompareSorting().compare();
//        ISort<Integer> integerISort = new Sort<>();
//        ArrayList<Integer> arrayList=new ArrayList<>();
//       for(int i = 0 ; i < 1000000 ; i ++)arrayList.add(new Random().nextInt());
//        integerISort.sortFast(arrayList);
//        int Last =Integer.MIN_VALUE;
//        boolean f =true;
//        for (int i =0 ; i <arrayList.size();i++) {
//            if(Last>arrayList.get(i))f=false;
//            Last=arrayList.get(i);
//            System.out.println(arrayList.get(i));
//        }
//        System.out.println(f);
////        System.out.println("");
//        ArrayList<Integer> t = new ArrayList<>();
//
//
//
//
//        Heap<Integer> h = new Heap<>();
//        Random random = new Random();
//        for (int i = 0; i < 20; i++) {
//          t.add(random.nextInt());
//        }
//        h.build(t);
//        int Last = Integer.MAX_VALUE;
//        boolean f =true;
//        System.out.println(h);
//        while (h.size()>0) {
//            int tt = h.extract();
//            if(Last<tt)f=false;
//            Last=tt;
//            System.out.println(tt);
//        }
//        System.out.println(f);
    }
}
