package ocp;

import java.util.List;

/**
 * Created by williaz on 11/26/16.
 */
public class GenericType <T, U> {
    public T t;
    public List<U> us;
    //public static T st; //no static generic

    public void saveAndPrint(U local) {
        us.add(local);
        System.out.println(local);
    }

    public static <S> S getElement(S s) {
        return s;
    }

}
