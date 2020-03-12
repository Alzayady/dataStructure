import WrapperPriitiveType.*;

public class AVLFactory {
    public AVLTreeAdaptor getIntegerAvlTree(){
        return new AVLTreeAdaptor<IntWrapper,Integer>();
    }
    public AVLTreeAdaptor getFloatAvlTree(){
        return new AVLTreeAdaptor<FloatWrapper,Float>();
    }
    public AVLTreeAdaptor getStringAvlTree(){
        return new AVLTreeAdaptor<StringWrapper,String>();
    }
    public AVLTreeAdaptor getDoubleAvlTree(){
        return new AVLTreeAdaptor<DoubleWrapper,Double>();
    }
}
