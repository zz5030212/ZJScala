package zz.collection;

import zz.*;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.immutable.ZIHashMap;

import java.util.*;

public abstract class ZAbstractTraversable<A, P extends Collection<A>, S extends ZCollectionCreator, ME, MINE extends ZAbstractTraversable> extends ZAny implements ZTraversable<A, ME, MINE> {
    protected final S s;
    protected final P j;

    public ZAbstractTraversable(S s, P j) {
        this.s = s;
        this.j = j;
    }


    @Override
    public void foreach(ZF<A, Void> f) {
        if (null == f) {
            return;
        }

        for (A e : j) {
            f.f(e);
        }
    }

    @Override
    public boolean isEmpty() {
        return j.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return !j.isEmpty();
    }

    /**
     * Appends a single element to this buffer. This operation takes constant time.
     *
     * @param x the element to append.
     * @return this $coll.
     */
    protected ME adQ(A x) {
        j.add(x);
        return (ME) this;
    }

    /**
     * ${Add}s all elements produced by a TraversableOnce to this $coll.
     *
     * @param xs the TraversableOnce producing the elements to $add.
     * @return the $coll itself.
     */
    protected ME adsQ(A... xs) {
        if (null != xs) {
            for (A x : xs) {
                adQ(x);
            }
        }
        return (ME) this;
    }

    /**
     * ${Add}s all elements produced by a TraversableOnce to this $coll.
     *
     * @param xs the TraversableOnce producing the elements to $add.
     * @return the $coll itself.
     */
    protected ME adsQ(Iterable<A> xs) {
        if (null != xs) {
            for (A x : xs) {
                adQ(x);
            }
        }
        return (ME) this;
    }

    protected ME deQ(A x) {
        j.remove(x);
        return (ME) this;
    }


    @Override
    public ME slice(int from, int until) {
        ZAbstractTraversable r = s.create(until - from);
        int i = 0;
        for (A x : j) {
            if (i >= from && i < until) {
                r.adQ(x);
            }
            i++;
        }
        return (ME) r;
    }


    @Override
    public ME drop(int n) {
        ZAbstractTraversable r = s.create(length() - n);
        if (n >= length())
            return (ME) r;

        for (A x : j) {
            if (n > 0) {
                n--;
            } else {
                r.adQ(x);
            }
        }
        return (ME) r;
    }

    public ME tails() {
        return drop(1);
    }

    @Override
    public ME take(int n) {
        return slice(0, n);
    }

    @Override
    public A head() {
        if (isEmpty())
            throw new NoSuchElementException();
        for (A e : j) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public ZOption<A> headOption() {
        if (isEmpty())
            return new ZNone<A>();
        else
            return new ZSome<A>(head());
    }

    @Override
    public A last() {
        if (isEmpty())
            throw new NoSuchElementException();
        A r = null;
        for (A e : j) {
            r = e;
        }
        return r;
    }

    @Override
    public ZOption<A> lastOption() {
        if (isEmpty())
            return new ZNone<A>();
        else
            return new ZSome<A>(last());
    }

    private ME filterImpl(ZF<A, Boolean> p, boolean isFlipped) {
        ZAbstractTraversable r = s.create();
        for (A x : j) {
            if (p.f(x) != isFlipped) {
                r.adQ(x);
            }
        }
        return (ME) r;
    }

    @Override
    public ME filter(ZF<A, Boolean> p) {
        return filterImpl(p, false);
    }

    @Override
    public ME filterNot(ZF<A, Boolean> p) {
        return filterImpl(p, true);
    }

    @Override
    public ME filterNotNull() {
        return filterNot(new ZF<A, Boolean>() {
            @Override
            public Boolean f(A a) {
                return null==a;
            }
        });
    }

    @Override
    public ME filterNotNone() {
        return filterNot(new ZF<A, Boolean>() {
            @Override
            public Boolean f(A a) {
                return a instanceof ZNone;
            }
        });
    }

    @Override
    public int count(ZF<A, Boolean> p) {
        int cnt = 0;
        for (A x : j) {
            if (p.f(x))
                cnt += 1;
        }
        return cnt;
    }

    @Override
    public ME distinct() {
        ZAbstractTraversable b = s.create();
        HashSet<A> seen = new HashSet<A>();
        for (A x : j) {
            if (!seen.contains(x)) {
                b.adQ(x);
                seen.add(x);
            }
        }
        return (ME) b;
    }

    @Override
    public <T> ME distinctBy(ZF<A, T> p) {
        ZAbstractTraversable b = s.create();
        HashSet<T> seen = new HashSet<T>();
        for (A x : j) {
            T t = p.f(x);
            if (!seen.contains(t)) {
                b.adQ(x);
                seen.add(t);
            }
        }
        return (ME) b;
    }

    /** Sorts this $coll according to a comparison function.
     *  $willNotTerminateInf
     *
     *  The sort is stable. That is, elements that are equal (as determined by
     *  `lt`) appear in the same order in the sorted sequence as in the original.
     *
     *  @param  lt  the comparison function which tests whether
     *              its first argument precedes its second argument in
     *              the desired ordering.
     *  @return     a $coll consisting of the elements of this $coll
     *              sorted according to the comparison function `lt`.
     *  @example {{{
     *    List("Steve", "Tom", "John", "Bob").sortWith(_.compareTo(_) < 0) =
     *    List("Bob", "John", "Steve", "Tom")
     *  }}}
     */
    @Override
    public ME sortWith(final ZF2<A, A, Integer> lt) {
        List<A> list = new LinkedList<A>(j);
        Collections.sort(list, new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                return lt.f(o1,o2);
            }
        });

        return (ME) s.create(length()).adsQ(list);
    }


    /** Sorts this $Coll according to the Ordering which results from transforming
     *  an implicitly given Ordering with a transformation function.
     *  @see [[scala.math.Ordering]]
     *  $willNotTerminateInf
     *  @param   f the transformation function mapping elements
     *           to some other domain `B`.
     *  @tparam  B the target type of the transformation `f`, and the type where
     *           the ordering `ord` is defined.
     *  @return  a $coll consisting of the elements of this $coll
     *           sorted according to the ordering where `x < y` if
     *           `ord.lt(f(x), f(y))`.
     *
     *  @example {{{
     *    val words = "The quick brown fox jumped over the lazy dog".split(' ')
     *    // this works because scala.Ordering will implicitly provide an Ordering[Tuple2[Int, Char]]
     *    words.sortBy(x => (x.length, x.head))
     *    res0: Array[String] = Array(The, dog, fox, the, lazy, over, brown, quick, jumped)
     *  }}}
     */
    @Override
    public <B extends Comparable> ME sortBy(final ZF<A, B> f) {
        List<A> list = new LinkedList<A>(j);
        Collections.sort(list, new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                return f.f(o1).compareTo(f.f(o2));
            }
        });

        return (ME) s.create(length()).adsQ(list);
    }

    /** Sorts this $coll according to an Ordering.
     *
     *  The sort is stable. That is, elements that are equal (as determined by
     *  `lt`) appear in the same order in the sorted sequence as in the original.
     *
     *  @see [[scala.math.Ordering]]
     *  @return     a $coll consisting of the elements of this $coll
     *              sorted according to the ordering `ord`.
     */

    @Override
    public ME sorted() {
        List<A> list = new LinkedList<A>(j);
        Collections.sort(list, new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                return (null==o1 && o1==o2)? 0:((Comparable<A>)o1).compareTo(o2);
            }
        });

        return (ME) s.create(length()).adsQ(list);
    }

    @Override
    public ZCase2<ME, ME> partition(ZF<A, Boolean> p) {
        ZAbstractTraversable l = s.create();
        ZAbstractTraversable r = s.create();

        for (A x : j) {
            if (p.f(x))
                l.adQ(x);
            else
                r.adQ(x);
        }

        return new ZCase2((ME) l, (ME) r);
    }

    @Override
    public ZCase2<ME, ME> splitAt(int n) {
        ZAbstractTraversable l = s.create(n);
        ZAbstractTraversable r = s.create(length() - n);
        int i = 0;
        for (A x : j) {
            if (i < n)
                l.adQ(x);
            else
                r.adQ(x);
            i++;
        }

        return new ZCase2((ME) l, (ME) r);
    }


    @Override
    public <K> ZIHashMap<K, ZArray<A>> groupBy(ZF<A, K> f) {
        HashMap<K, ZArray<A>> m = new HashMap<K, ZArray<A>>();
        for (A elem : j) {
            K key = f.f(elem);
            if (m.containsKey(key)) {
                m.get(key).adQ(elem);
            } else {
                ZArray<A> t = new ZArray<A>(0);
                t.adQ(elem);
                m.put(key, t);
            }
        }
        return ZIHashMap.bind(m);
    }


    @Override
    public boolean forall(ZF<A, Boolean> p) {
        for (A x : j) {
            if (!p.f(x)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean exists(ZF<A, Boolean> p) {
        for (A x : j) {
            if (p.f(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object a) {
        for (A x : j) {
            if (a==x || (null!=x && x.equals(a))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ZOption<A> find(ZF<A, Boolean> p) {
        for (A x : j) {
            if (p.f(x)) {
                return new ZSome<A>(x);
            }
        }
        return new ZNone<A>();
    }

    @Override
    public ZArray<Integer> indices() {
        int length = length();
        ZArray<Integer> r = new ZArray<Integer>(length);
        for (int i = 0; i < length; i++) {
            r.update(i, i);
        }
        return r;
    }

    @Override
    public ZArray<ZCase2<A, Integer>> zipWithIndex() {
        ZArray<ZCase2<A, Integer>> b = new ZArray<ZCase2<A, Integer>>(length());
        int i = 0;
        for (A x : j) {
            b.update(i, new ZCase2<A, Integer>(x, i));
            i += 1;
        }
        return b;
    }

    @Override
    public <B> ZArray<ZCase2<A, B>> zip(Iterable<B> that) {
        LinkedList<ZCase2<A, B>> b = new LinkedList<ZCase2<A, B>>();
        Iterator<A> these = this.iterator();
        Iterator<B> those = that.iterator();
        while (these.hasNext() && those.hasNext()) {
            b.add(new ZCase2<A, B>(these.next(), those.next()));
        }

        return new ZArray<ZCase2<A, B>>(b);
    }

    @Override
    public <B> ZArray<ZCase2<A, B>> zipAll(Iterable<B> that, A thisElem, B thatElem) {
        LinkedList<ZCase2<A, B>> b = new LinkedList<ZCase2<A, B>>();
        Iterator<A> these = this.iterator();
        Iterator<B> those = that.iterator();
        while (these.hasNext() && those.hasNext())
            b.add(new ZCase2<A, B>(these.next(), those.next()));
        while (these.hasNext())
            b.add(new ZCase2<A, B>(these.next(), thatElem));
        while (those.hasNext())
            b.add(new ZCase2<A, B>(thisElem, those.next()));
        return new ZArray<ZCase2<A, B>>(b);
    }

    @Override
    public A min() {
        if (isEmpty() || !(head() instanceof Comparable))
            throw new UnsupportedOperationException("min");
        return reduce(new ZF2<A, A, A>() {
            @Override
            public A f(A a, A a2) {
 /*               if(null == a)
                    return a;
                else if(null == a2)
                    return a2;*/
                if (((Comparable<A>) a).compareTo(a2) <= 0)
                    return a;
                else
                    return a2;
            }
        });
    }

    @Override
    public A max() {
        if (isEmpty() || !(head() instanceof Comparable))
            throw new UnsupportedOperationException("max");
        return reduce(new ZF2<A, A, A>() {
            @Override
            public A f(A a, A a2) {
/*                if(null == a)
                    return a2;
                else if(null == a2)
                    return a;*/
                if (((Comparable<A>) a).compareTo(a2) >= 0)
                    return a;
                else
                    return a2;
            }
        });
    }

    @Override
    public <B extends Comparable> A maxBy(ZF<A, B> f) {
        if (isEmpty())
            throw new UnsupportedOperationException("empty.maxBy");

        B maxF = null;
        A maxElem = null;
        boolean first = true;

        for (A elem : j) {
            B fx = f.f(elem);
            if (first || fx.compareTo(maxF) > 0) {
                maxElem = elem;
                maxF = fx;
                first = false;
            }
        }
        return maxElem;
    }

    @Override
    public <B extends Comparable> A minBy(ZF<A, B> f) {
        if (isEmpty())
            throw new UnsupportedOperationException("empty.minBy");

        B minF = null;
        A minElem = null;
        boolean first = true;

        for (A elem : j) {
            B fx = f.f(elem);
            if (first || fx.compareTo(minF) < 0) {
                minElem = elem;
                minF = fx;
                first = false;
            }
        }
        return minElem;
    }

    @Override
    public int length() {
        return j.size();
    }

    @Override
    public int size() {
        return length();
    }

    @Override
    public void clear() {
        j.clear();
    }

    @Override
    public ME reverse() {
        ZAbstractTraversable r = s.create(length());
        if (isEmpty())
            return (ME) r;
        Stack<A> t = new Stack<A>();
        for (A x : j) {
            t.push(x);
        }

        while (!t.isEmpty()) {
            r.adQ(t.pop());
        }

        return (ME) r;
    }

    @Override
    public A reduceLeft(ZF2<A, A, A> op) {
        if (isEmpty())
            throw new UnsupportedOperationException("empty.reduceLeft");
        boolean first = true;
        A acc = null;
        for (A x : j) {
            if (first) {
                acc = x;
                first = false;
            } else {
                acc = op.f(acc, x);
            }
        }
        return acc;
    }

    @Override
    public A reduceRight(ZF2<A, A, A> op) {
        if (isEmpty())
            throw new UnsupportedOperationException("empty.reduceRight");
        return (A) ((ZAbstractTraversable) reverse()).reduceLeft(op);
    }

    @Override
    public ZOption<A> reduceLeftOption(ZF2<A, A, A> op) {
        if (isEmpty())
            return new ZNone<A>();
        else
            return new ZSome<A>(reduceLeft(op));
    }

    @Override
    public ZOption<A> reduceRightOption(ZF2<A, A, A> op) {
        if (isEmpty())
            return new ZNone<A>();
        else
            return new ZSome<A>(reduceRight(op));
    }

    @Override
    public A reduce(ZF2<A, A, A> op) {
        return reduceLeft(op);
    }

    @Override
    public ZOption<A> reduceOption(ZF2<A, A, A> op) {
        return reduceLeftOption(op);
    }

    @Override
    public <B> boolean corresponds(MINE that, ZF2<A, B, Boolean> p) {
        Iterator<A> i = this.iterator();
        Iterator<B> j = that.iterator();
        while (i.hasNext() && j.hasNext()) {
            if(!p.f(i.next(),j.next()))
                return false;
        }
        return !i.hasNext() && !j.hasNext();
    }

    @Override
    public ZArray<A> toArray() {
        return new ZArray<A>(j);
    }

    @Override
    public String mkString() {
        return mkString("");
    }

    @Override
    public String mkString(String sep) {
        return mkString("", sep, "");
    }

    @Override
    public String mkString(String start, String sep, String end) {
        return addString(new StringBuilder(), start, sep, end).toString();
    }

    @Override
    public StringBuilder addString(StringBuilder b) {
        return addString(b, "");
    }

    @Override
    public StringBuilder addString(StringBuilder b, String sep) {
        return addString(b, "", sep, "");
    }

    @Override
    public StringBuilder addString(StringBuilder b, String start, String sep, String end) {
        boolean first = true;

        b.append(start);
        for (A x : j) {
            if (first) {
                b.append(x);
                first = false;
            } else {
                b.append(sep);
                b.append(x);
            }
        }
        b.append(end);

        return b;
    }

    @Override
    public <B> MINE map(ZF<A, B> f) {
        MINE r = (MINE) s.create(length());
        for (A e : j) {
            r.adQ(f.f(e));
        }
        return r;
    }

    @Override
    public <B, E> MINE flatMap(Class<E> rE, ZF<A, B> f) {
        MINE r = (MINE) s.create();
        for (A e : j) {
            Object t = f.f(e);
            if (null == t) {
                continue;
            } else if (rE.isAssignableFrom(t.getClass())) {
                r.adQ(t);
            } else if (t.getClass().isArray()) {
                r.adsQ((E[]) t);
            } else if (t instanceof Iterable) {
                r.adsQ((Iterable) t);
            } else {
                throw new NoSuchElementException("None.get");
            }
        }
        return r;
    }

    @Override
    public Iterator<A> iterator() {
        return j.iterator();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + mkString("(", ",", ")");
    }
}
