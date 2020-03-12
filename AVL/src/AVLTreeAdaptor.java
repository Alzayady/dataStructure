import WrapperPriitiveType.PrimitiveType;

import java.lang.reflect.ParameterizedType;

public class AVLTreeAdaptor  <T extends PrimitiveType,S>{
    private AVLTree<T> avlTree ;
    public AVLTreeAdaptor(){
        avlTree=new AVLTree<T>();
    }

    public boolean insert(S value)  {
        T wrapper = getInstanceOfGeneric();
        wrapper.setValue(value);
        return avlTree.insert(wrapper);
    }
    public boolean delete (S value){
         T wrapper =getInstanceOfGeneric();
         wrapper.setValue(value);
         return avlTree.delete(wrapper);
    }

    private T getInstanceOfGeneric(){
        T  instance =null;
        try {
            instance= (T)((Class)((ParameterizedType)this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return instance;
    }




}
