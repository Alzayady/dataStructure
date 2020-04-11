package eg.edu.alexu.csd.filestructure.sort;

import CompareSorting.CompareSorting;

import java.util.List;

public class Main {


    public static void main(String[] args) {
         final Class<?> heapInterfaceToTest = IHeap.class;
        System.out.println(   heapInterfaceToTest.getPackage());
        new CompareSorting().compare();

    }
}
