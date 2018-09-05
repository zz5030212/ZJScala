package zz;

import java.io.Serializable;

public class ZCase1<A> implements Serializable,Cloneable {
    public final A _1;
    public ZCase1(A _1) {
        this._1 = _1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+'('+_1+')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZCase1<?> zCase1 = (ZCase1<?>) o;

        return _1 != null ? _1.equals(zCase1._1) : zCase1._1 == null;

    }

    @Override
    public int hashCode() {
        return _1 != null ? _1.hashCode() : 0;
    }
}
