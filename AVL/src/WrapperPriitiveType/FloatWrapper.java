package WrapperPriitiveType;

public class FloatWrapper extends PrimitiveType<Float , FloatWrapper> {
    FloatWrapper(float value) {
        super(value);
    }

    @Override
    public int compareTo(FloatWrapper o) {
        return value.compareTo(o.getValue());
    }
}
