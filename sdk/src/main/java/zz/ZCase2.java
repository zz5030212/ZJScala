package zz;


public class ZCase2<A,B> extends ZCase1<A> {
    public final B _2;

    public ZCase2(A _1, B _2) {
        super(_1);
        this._2 = _2;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+'('+_1+','+_2+')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ZCase2<?, ?> zCase2 = (ZCase2<?, ?>) o;

        return _2 != null ? _2.equals(zCase2._2) : zCase2._2 == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_2 != null ? _2.hashCode() : 0);
        return result;
    }
}
