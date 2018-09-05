package zz.collection.mutable;


import zz.ZF;
import zz.collection.mutable.creator.ZLinkedListCreator;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public final class ZLinkedList<A> extends ZAbsList<A,LinkedList<A>,ZLinkedListCreator,ZLinkedList<A>,ZLinkedList> implements Serializable {

    public ZLinkedList(Collection<A> j) {
        super(new ZLinkedListCreator(),new LinkedList<A>(j));
    }

    public ZLinkedList() {
        this(new LinkedList<A>());
    }

    public ZLinkedList(A ... xs) {
        this(new LinkedList<A>());
        adsQ(xs);
    }

    @Override
    public <B> ZLinkedList<B> map(ZF<A, B> f) {
        return super.map(f);
    }

    @Override
    public <B,E> ZLinkedList<E> flatMap(Class<E> rE, ZF<A, B> f) {
        return super.flatMap(rE,f);
    }

    @Deprecated
    public ZLinkedList(LinkedList<A> j, boolean bind) {
        super(new ZLinkedListCreator(),j);
    }

    public static <A> ZLinkedList<A> bind(LinkedList<A> j) {
        return new ZLinkedList<A>(j,true);
    }

}
