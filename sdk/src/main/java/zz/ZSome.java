package zz;


public class ZSome<A> extends ZOption<A> {
    private final A x;
    public ZSome(A x) {
        this.x = x;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public A get() {
        return x;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"(" + x + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZSome<?> zSome = (ZSome<?>) o;

        return x != null ? x.equals(zSome.x) : zSome.x == null;

    }

    @Override
    public int hashCode() {
        return x != null ? x.hashCode() : 0;
    }
}
