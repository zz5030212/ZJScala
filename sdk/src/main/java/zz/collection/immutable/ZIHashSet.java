package zz.collection.immutable;

import zz.collection.ZAbsSet;
import zz.collection.immutable.creator.ZIHashSetCreator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class ZIHashSet<A> extends ZAbsSet<A,HashSet<A>,ZIHashSetCreator,ZIHashSet<A>,ZIHashSet> implements Serializable {
    public ZIHashSet(HashSet<A> j) {
        super(new ZIHashSetCreator(), j);
    }

    public ZIHashSet() {
        this(new HashSet<A>());
    }

    public ZIHashSet(int initialCapacity) {
        this(new HashSet<A>(initialCapacity));
    }

    public ZIHashSet(int initialCapacity, float loadFactor) {
        this(new HashSet<A>(initialCapacity,loadFactor));
    }

    public ZIHashSet(Set<A> m){
        this(new HashSet<A>(m));
    }

    @Deprecated
    private ZIHashSet(HashSet<A> j, boolean bind) {
        super(new ZIHashSetCreator(), j);
    }

    public static <A> ZIHashSet<A> bind(HashSet<A> j) {
        return new ZIHashSet<A>(j,true);
    }

}
