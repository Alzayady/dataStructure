package WrapperPriitiveType;

public class StringWrapper extends PrimitiveType  <String , StringWrapper> {

    StringWrapper(String value) {
        super(value);
    }

    @Override
    public int compareTo(StringWrapper o) {
      return value.compareTo(o.value);
    }
}
