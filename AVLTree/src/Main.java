public class Main {

    public static void main(String[] args) {
        AVLTree <Integer > tree =new AVLTree<>();
       tree.insert(20);
       tree.insert(10);
       tree.insert(5);
       tree.insert(1);
       tree.insert(30);
        tree.insert(40);
        tree.insert(15);
        tree.insert(7);
        tree.insert(3);
        tree.insert(25);
        tree.insert(35);
        tree.insert(45);
        tree.insert(55);


        System.out.println(tree);
        System.out.println(tree.delete(55));
        System.out.println(tree.delete(155));
        System.out.println(tree);
        
    }
}
