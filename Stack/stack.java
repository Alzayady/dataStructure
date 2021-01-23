package eg.edu.alexu.csd.datastructure.stack.cs03;

import java.util.EmptyStackException;

public class stack implements IStack {
    int size = 0;
    node head;

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object t = head;
        head = head.next;
        size--;
        return ((node) t).Valiue;
    }


    public Object peek()  {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return head.Valiue;
    }

    public void push(Object element) {
        node t = new node(element, head);
        head = t;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }



    public int size() {
        return size;
    }


    private class node {
        Object Valiue;
        node next;

        public node(Object t, node next) {
            this.Valiue = t;
            this.next = next;
        }


        public void setnext(node next) {
            this.next = next;
        }
    }
}
