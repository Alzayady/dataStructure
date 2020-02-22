public class Main {

    public static void main(String[] args) throws Exception {
        Object [] dd = new ExtendExpression( "-10----------------------5*-8").convert();
        for(Object o : dd){
            System.out.println(o);
        }
    }
}
