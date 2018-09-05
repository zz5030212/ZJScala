package zz.collection;

import zz.collection.factory.ZCollectionCreator;

import java.util.List;

public abstract class ZAbsList<A,P extends List<A>, S extends ZCollectionCreator, ME, MINE extends ZAbsList>
        extends ZAbstractTraversable<A,P,S, ME,MINE> {

    public ZAbsList(S s, P j) {
        super(s, j);
    }

    @Override
    public A get(int n) {
        return j.get(n);
    }

    public ME _a(A a) {
        return (ME) ((MINE) (s.create().adsQ(j))).adQ(a);
    }

    public ME a_(A a) {
        return (ME) ((MINE) (s.create().adQ(a))).adsQ(j);
    }

    public ME _aa(Iterable<A> as) {
        return (ME) ((MINE) (s.create().adsQ(j))).adsQ(as);
    }

    public ME aa_(Iterable<A> as) {
        return (ME) ((MINE) (s.create().adsQ(as))).adsQ(j);
    }

}