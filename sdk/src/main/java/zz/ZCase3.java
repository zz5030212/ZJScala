package zz;


public class ZCase3<A,B,C> extends ZCase2<A,B> {
    public final C _3;

    public ZCase3(A _1, B _2, C _3) {
        super(_1,_2);
        this._3 = _3;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+'('+_1+','+_2+','+_3+')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ZCase3<?, ?, ?> zCase3 = (ZCase3<?, ?, ?>) o;

        return _3 != null ? _3.equals(zCase3._3) : zCase3._3 == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_3 != null ? _3.hashCode() : 0);
        return result;
    }
}
