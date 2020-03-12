package WrapperPriitiveType;

public class DoubleWrapper extends PrimitiveType <Double,DoubleWrapper> {
    DoubleWrapper(Double value) {
        super(value);
    }

    @Override
    public int compareTo(DoubleWrapper o) {
        return value.compareTo(o.getValue());
    }
}
