package zz;

import java.util.NoSuchElementException;

public class ZNone<A> extends ZOption<A> {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public A get() {
        throw new NoSuchElementException("None.get");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"()";
    }


}
