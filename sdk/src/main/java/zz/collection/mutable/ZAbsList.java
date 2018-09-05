package zz.collection.mutable;

import zz.collection.factory.ZCollectionCreator;

import java.util.List;

public abstract class ZAbsList<A,P extends List<A>, S extends ZCollectionCreator, ME, MINE extends ZAbsList>
        extends zz.collection.ZAbsList<A,P,S,ME,MINE> {

    public ZAbsList(S s, P j) {
        super(s, j);
    }

    @Override
    public ME adQ(A x) {
        return super.adQ(x);
    }

    @Override
    public ME adsQ(A... xs) {
        return super.adsQ(xs);
    }

    @Override
    public ME adsQ(Iterable<A> xs) {
        return super.adsQ(xs);
    }

    @Override
    public ME deQ(A x) {
        return super.deQ(x);
    }
}
