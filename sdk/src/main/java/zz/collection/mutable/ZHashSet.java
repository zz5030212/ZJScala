package zz.collection.mutable;

import zz.collection.ZAbsSet;
import zz.collection.mutable.creator.ZHashSetCreator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class ZHashSet<A> extends ZAbsSet<A,HashSet<A>,ZHashSetCreator,ZHashSet<A>,ZHashSet> implements Serializable {
    public ZHashSet(HashSet<A> j) {
        super(new ZHashSetCreator(), j);
    }

    public ZHashSet() {
        this(new HashSet<A>());
    }

    public ZHashSet(int initialCapacity) {
        this(new HashSet<A>(initialCapacity));
    }

    public ZHashSet(int initialCapacity, float loadFactor) {
        this(new HashSet<A>(initialCapacity,loadFactor));
    }

    public ZHashSet(Set<A> m){
        this(new HashSet<A>(m));
    }

    @Deprecated
    private ZHashSet(HashSet<A> j, boolean bind) {
        super(new ZHashSetCreator(), j);
    }

    public static <A> ZHashSet<A> bind(HashSet<A> j) {
        return new ZHashSet<A>(j,true);
    }

}
