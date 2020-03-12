package WrapperPriitiveType;

public abstract class PrimitiveType <T,S> implements Comparable<S>  {

   protected T value ;

    PrimitiveType( T value ){
        this.value=value;
    }
    PrimitiveType(  ){}

    public void setValue(T value) {
        this.value = value;
    }

    T getValue(){
        return value;
    }


}
