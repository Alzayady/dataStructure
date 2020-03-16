package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class Sort <T extends Comparable<T>>  implements ISort<T> {

    @Override
    public IHeap<T> heapSort(ArrayList<T> arrayList) {
        if(arrayList==null)return new Heap<>();
        Heap<T> heap=new Heap<>();
        heap.build(arrayList);
        int i = arrayList.size()-1;
        while (heap.size()>0){ arrayList.set(i--,heap.extract()); }
       // heap.setSize(arrayList.size());
        return heap.clone();
    }

    @Override
    public void sortSlow(ArrayList<T> arrayList) {
        if(arrayList==null)return;
        for(int i = 0 ; i <arrayList.size() ; i++){
            boolean f =true;
            for(int j = 0 ; j < arrayList.size() - i -1  ; j++){
                if(arrayList.get(j).compareTo(arrayList.get(j+1))>0) {
                    T v = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, v);
                    f = false;
                }
            }
            if(f)break;
        }
    }

    @Override
     public void sortFast(ArrayList<T> arrayList) {
        if(arrayList==null)return;
        ArrayList<T> m =new ArrayList<>();
        for(int i = 0 ; i < arrayList.size() ; i++)m.add(null);
        for(int  start = 1 ; start<arrayList.size() ; start<<=1 )
        {
            int stopRange=start*2;
                    for(int i = 0 ; i <arrayList.size();i+=stopRange)
                    {
                        int p1 = i ;
                        int p2 = i + start ;
                        int in=0;
                        while (p2<arrayList.size()&&p1<i+start&&p2<i+stopRange)
                        {
                            if(arrayList.get(p1).compareTo(arrayList.get(p2))>0)
                            {
                                     m.set(in++,arrayList.get(p2++));
                            }else    m.set(in++,arrayList.get(p1++));
                        }
                        while (p1<arrayList.size()&&p1<i+start)m.set(in++,arrayList.get(p1++));
                        while (p2<arrayList.size()&&p2<i+stopRange)m.set(in++,arrayList.get(p2++));
                        in=0;
                        int stopHere=Math.min(i+stopRange,arrayList.size());
                        for(int j = i ; j <stopHere ; j++) arrayList.set(j,m.get(in++));
                    }
        }
    }
}
