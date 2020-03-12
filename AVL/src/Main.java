import WrapperPriitiveType.IntWrapper;

public class Main {

    public static void main(String[] args) {
       AVLTreeAdaptor avlTreeAdaptor = new AVLFactory().getIntegerAvlTree();
       avlTreeAdaptor.insert(5);
    }
}
