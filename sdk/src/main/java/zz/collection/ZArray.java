package zz.collection;

import zz.ZF;
import zz.collection.factory.ZArrayCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ZArray<A> extends ZAbsList<A,ArrayList<A>,ZArrayCreator,ZArray<A>,ZArray> implements Serializable {

    public ZArray(Collection<A> j) {
        super(new ZArrayCreator(), new ArrayList<A>(j));
    }

    public ZArray(int initialCapacity, boolean creater) {
        super(new ZArrayCreator(), new ArrayList<A>(initialCapacity));
    }

    public ZArray(int length) {
        super(new ZArrayCreator(), (ArrayList<A>)createInitArrayList(length));
    }

    private static  ArrayList createInitArrayList(int length) {
        ArrayList j = new ArrayList(length);
        int t = length;
        while(t-->0){
            j.add(null);
        }
        return j;
    }

    public ZArray(A[] array) {
        super(new ZArrayCreator(), new ArrayList<A>(array.length));
        for(A x : array) {
            j.add(x);
        }
    }

    public ZArray<A> update(int index, A x) {
        j.set(index,x);
        return this;
    }

    public A[] toArray(A[] xs) {
        return j.toArray(xs);
    }

    public static ZArray<Integer> range(int start, int end) {
        ArrayList<Integer> r = new ArrayList<Integer>(end-start);
        for(;start<end;start++) {
            r.add(start);
        }
        return new ZArray<Integer>(r);
    }

    @Override
    public <B, E> ZArray<E> flatMap(Class<E> rE, ZF<A, B> f) {
        return super.flatMap(rE, f);
    }

    @Override
    public <B> ZArray<B> map(ZF<A, B> f) {
        return super.map(f);
    }
}
