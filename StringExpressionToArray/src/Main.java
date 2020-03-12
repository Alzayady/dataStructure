public class Main {

    public static void main(String[] args) throws Exception {
        String [] dd = new ExtendExpression( "( 10 + 5 )  -2  and ").convert().split(" ");
        int t = 0;
        for(String o : dd){
            System.out.println(t+ " " + o);
            t++;
        }
    }
}
