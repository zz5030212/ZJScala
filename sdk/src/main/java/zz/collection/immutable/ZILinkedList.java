package zz.collection.immutable;


import zz.ZF;
import zz.collection.ZAbsList;
import zz.collection.immutable.creator.ZILinkedListCreator;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public final class ZILinkedList<A> extends ZAbsList<A,LinkedList<A>,ZILinkedListCreator,ZILinkedList<A>,ZILinkedList> implements Serializable {

    public ZILinkedList(Collection<A> j) {
        super(new ZILinkedListCreator(),new LinkedList<A>(j));
    }

    public ZILinkedList() {
        this(new LinkedList<A>());
    }

    public ZILinkedList(A... xs) {
        this(new LinkedList<A>());
        adsQ(xs);
    }


    @Override
    public <B> ZILinkedList<B> map(ZF<A, B> f) {
        return super.map(f);
    }

    @Override
    public <B,E> ZILinkedList<E> flatMap(Class<E> rE, ZF<A, B> f) {
        return super.flatMap(rE,f);
    }

    @Deprecated
    public ZILinkedList(LinkedList<A> j, boolean bind) {
        super(new ZILinkedListCreator(),j);
    }

    public static <A> ZILinkedList<A> bind(LinkedList<A> j) {
        return new ZILinkedList<A>(j,true);
    }

}
