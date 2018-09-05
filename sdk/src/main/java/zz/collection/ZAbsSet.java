package zz.collection;

import zz.collection.factory.ZCollectionCreator;

import java.util.Set;

public abstract class ZAbsSet<A,P extends Set<A>, S extends ZCollectionCreator, ME, MINE extends ZAbsSet>
        extends ZAbstractTraversable<A,P,S, ME,MINE> {

    public ZAbsSet(S s, P j) {
        super(s, j);
    }

    @Override
    public A get(int n) {
        if(n<0 || n>=j.size())
            throw new IndexOutOfBoundsException(" "+n);
        int i = 0;
        for (A x : j) {
            if(i == n)
                return x;
            i++;
        }
        throw new IndexOutOfBoundsException(" "+n);
    }

    /** Tests whether this map contains a binding for a key.
     *
     *  @param key the key
     *  @return    `true` if there is a binding for `key` in this map, `false` otherwise.
     */
    boolean containsKey(A key) {
        return j.contains(key);
    }

    public ME a(A a) {
        return (ME) ((MINE) (s.create().adsQ(j))).adQ(a);
    }

    public ME aa(Iterable<A> as) {
        return (ME) ((MINE) (s.create().adsQ(j))).adsQ(as);
    }


}