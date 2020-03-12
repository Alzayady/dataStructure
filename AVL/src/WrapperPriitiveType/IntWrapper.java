package WrapperPriitiveType;

public class IntWrapper  extends PrimitiveType<Integer,IntWrapper>   {

    IntWrapper(Integer value) {
        super(value);
    }



    @Override
    public int compareTo(IntWrapper o) {
       return value.compareTo(o.value);
    }
}
