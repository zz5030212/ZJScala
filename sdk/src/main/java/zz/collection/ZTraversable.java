package zz.collection;

import zz.ZCase2;
import zz.ZF;
import zz.ZF2;
import zz.ZOption;
import zz.collection.immutable.ZIHashMap;

import java.util.NoSuchElementException;

public interface ZTraversable<A, ME, MINE extends ZTraversable> extends Iterable<A> {
    void foreach(ZF<A, Void> f);

    boolean isEmpty();

    boolean nonEmpty();

    A get(int n);

    /**
     * Selects an interval of elements. The returned collection is made up of all elements x which satisfy the invariant:
     * from <= indexOf(x) < until
     * Note: might return different results for different runs, unless the underlying collection type is ordered.
     *
     * @param from
     * @param until
     * @return a iterable collection containing the elements greater than or equal to index from extending up to (but not including) index until of this iterable collection.
     */
    ME slice(int from, int until);

    /**
     * Selects first n elements.
     * Note: might return different results for different runs, unless the underlying collection type is ordered.
     *
     * @param n the number of elements to take from this iterable collection.
     * @return a iterable collection consisting only of the first n elements of this iterable collection, or else the whole iterable collection, if it has less than n elements.
     */
    ME take(int n);

    /**
     * Selects all elements except first n ones.
     * Note: might return different results for different runs, unless the underlying collection type is ordered.
     *
     * @param n the number of elements to drop from this iterable collection.
     * @return a iterable collection consisting of all elements of this iterable collection except the first n ones, or else the empty iterable collection, if this iterable collection has less than n elements.
     */
    ME drop(int n);

    /**
     * Iterates over the tails of this $coll. The first value will be this
     * $coll and the final one will be an empty $coll, with the intervening
     * values the results of successive applications of `tail`.
     *
     * @return an iterator over all the tails of this $coll
     * @example `List(1,2,3).tails = Iterator(List(1,2,3), List(2,3), List(3), Nil)`
     */
    ME tails();

    /**
     * Selects the first element of this $coll.
     * $orderDependent
     *
     * @return the first element of this $coll.
     * @throws NoSuchElementException if the $coll is empty.
     */
    A head();

    /**
     * Optionally selects the first element.
     * $orderDependent
     *
     * @return the first element of this $coll if it is nonempty,
     * `None` if it is empty.
     */
    ZOption<A> headOption();

    A last();

    ZOption<A> lastOption();

    int count(ZF<A, Boolean> p);

    /**
     * Selects all elements of this $coll which satisfy a predicate.
     *
     * @param p the predicate used to test elements.
     * @return a new $coll consisting of all elements of this $coll that satisfy the given
     * predicate `p`. The order of the elements is preserved.
     */
    ME filter(ZF<A, Boolean> p);

    /**
     * Selects all elements of this $coll which do not satisfy a predicate.
     *
     * @param p the predicate used to test elements.
     * @return a new $coll consisting of all elements of this $coll that do not satisfy the given
     * predicate `p`. The order of the elements is preserved.
     */
    ME filterNot(ZF<A, Boolean> p);
    ME filterNotNull();
    ME filterNotNone();
    /**
     * Builds a new $coll from this $coll without any duplicate elements.
     * $willNotTerminateInf
     *
     * @return A new $coll which contains the first occurrence of every element of this $coll.
     */
    ME distinct();
    <T> ME distinctBy(ZF<A, T> p);


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
    ME sortWith(ZF2<A,A,Integer>lt);

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
    <B extends Comparable> ME sortBy(ZF<A,B> f);

    /** Sorts this $coll according to an Ordering.
     *
     *  The sort is stable. That is, elements that are equal (as determined by
     *  `lt`) appear in the same order in the sorted sequence as in the original.
     *
     *  @see [[scala.math.Ordering]]
     *
     *  @return     a $coll consisting of the elements of this $coll
     *              sorted according to the ordering `ord`.
     */
    ME sorted();

    /**
     * Partitions this $coll in two ${coll}s according to a predicate.
     *
     * @param p the predicate on which to partition.
     * @return a pair of ${coll}s: the first $coll consists of all elements that
     * satisfy the predicate `p` and the second $coll consists of all elements
     * that don't. The relative order of the elements in the resulting ${coll}s
     * is the same as in the original $coll.
     */
    ZCase2<ME, ME> partition(ZF<A, Boolean> p);

    ZCase2<ME, ME> splitAt(int n);

    /**
     * Partitions this traversable collection into a map of traversable collections according to some discriminator function.
     * Note: this method is not re-implemented by views. This means when applied to a view it will always force the view and return a new traversable collection.
     * (xs groupBy f)(k) = xs filter (x => f(x) == k)
     * That is, every key k is bound to a traversable collection of those elements x for which f(x) equals k.
     *
     * @param f   the discriminator function.
     * @param <K> the type of keys returned by the discriminator function.
     * @return A map from keys to traversable collections such that the following invariant holds:
     */
    <K> ZIHashMap<K, ZArray<A>> groupBy(ZF<A, K> f);

    /**
     * Tests whether a predicate holds for all elements of this $coll.
     * <p/>
     * $mayNotTerminateInf
     *
     * @param p the predicate used to test elements.
     * @return `true`  if this $coll is empty, otherwise `true` if the given predicate `p`
     * holds for all elements of this $coll, otherwise `false`.
     */
    boolean forall(ZF<A, Boolean> p);

    /**
     * Tests whether a predicate holds for some of the elements of this $coll.
     * <p/>
     * $mayNotTerminateInf
     *
     * @param p the predicate used to test elements.
     * @return `false` if this $coll is empty, otherwise `true` if the given predicate `p`
     * holds for some of the elements of this $coll, otherwise `false`
     */
    boolean exists(ZF<A, Boolean> p);
    boolean contains(Object a);

    /**
     * Finds the first element of the $coll satisfying a predicate, if any.
     * <p/>
     * $mayNotTerminateInf
     * $orderDependent
     *
     * @param p the predicate used to test elements.
     * @return an option value containing the first element in the $coll
     * that satisfies `p`, or `None` if none exists.
     */
    ZOption<A> find(ZF<A, Boolean> p);

    /**
     * Produces the range of all indices of this sequence.
     *
     * @return a `Range` value from `0` to one less than the length of this $coll.
     */
    ZArray<Integer> indices();

    /**
     * Returns a sequence formed from this sequence and another iterable collection by combining corresponding elements in pairs. If one of the two collections is longer than the other, its remaining elements are ignored.
     *
     * @param that The iterable providing the second half of each result pair
     * @param <B>  the type of the second half of the returned pairs
     * @return a new sequence containing pairs consisting of corresponding elements of this sequence and that. The length of the returned collection is the minimum of the lengths of this sequence and that.
     */
    <B> ZArray<ZCase2<A, B>> zip(Iterable<B> that);

    /**
     * a new list containing pairs consisting of corresponding elements of this list and that. The length of the returned collection is the maximum of the lengths of this list and that. If this list is shorter than that, thisElem values are used to pad the result. If that is shorter than this list, thatElem values are used to pad the result.
     *
     * @param that     The iterable providing the second half of each result pair
     * @param thisElem the element to be used to fill up the result if this list is shorter than that.
     * @param thatElem the element to be used to fill up the result if that is shorter than this list.
     * @param <B>      the type of the second half of the returned pairs
     * @return Returns a list formed from this list and another iterable collection by combining corresponding elements in pairs. If one of the two collections is shorter than the other, placeholder elements are used to extend the shorter collection to the length of the longer.
     */
    <B> ZArray<ZCase2<A, B>> zipAll(Iterable<B> that, A thisElem, B thatElem);


    /**
     * Zips this sequence with its indices.
     *
     * @return A new sequence containing pairs consisting of all elements of this sequence paired with their index. Indices start at 0.
     */
    ZArray<ZCase2<A, Integer>> zipWithIndex();


    /**
     * Finds the smallest element.
     *
     * @return the smallest element of this $coll
     * @tparam A1    The type over which the ordering is defined.
     * @usecase def min: A
     * @inheritdoc
     */
    A min();

    /**
     * Finds the largest element.
     *
     * @return the largest element of this $coll.
     * @tparam A1    The type over which the ordering is defined.
     * @usecase def max: A
     * @inheritdoc
     */
    A max();

    /**
     * Finds the first element which yields the largest value measured by function f.
     *
     * @param <B> The result type of the function f.
     * @param f   The measuring function.
     * @return the first element of this $coll with the largest value measured by function f.
     * @usecase def maxBy[B](f: A => B): A
     * @inheritdoc
     */
    <B extends Comparable> A maxBy(ZF<A, B> f);

    /**
     * Finds the first element which yields the smallest value measured by function f.
     *
     * @param <B> The result type of the function f.
     * @param f   The measuring function.
     * @return the first element of this $coll with the smallest value measured by function f.
     * @usecase <B extends Comparable<B>> A minBy(ZF<A,B> f);
     * @inheritdoc
     */
    <B extends Comparable> A minBy(ZF<A, B> f);

    int length();

    int size();

    void clear();

    ME reverse();

    /**
     * Applies a binary operator to all elements of this $coll,
     * going left to right.
     * $willNotTerminateInf
     * $orderDependentFold
     *
     * @param op the binary operator.
     * @return the result of inserting `op` between consecutive elements of this $coll,
     * going left to right:
     * {{{
     * op( op( ... op(x_1, x_2) ..., x_{n-1}), x_n)
     * }}}
     * where `x,,1,,, ..., x,,n,,` are the elements of this $coll.
     * @throws UnsupportedOperationException if this $coll is empty.
     * @tparam A    the result type of the binary operator.
     */
    A reduceLeft(ZF2<A, A, A> op);

    A reduceRight(ZF2<A, A, A> op);

    ZOption<A> reduceLeftOption(ZF2<A, A, A> op);

    ZOption<A> reduceRightOption(ZF2<A, A, A> op);

    A reduce(ZF2<A, A, A> op);

    ZOption<A> reduceOption(ZF2<A, A, A> op);

    <B> boolean corresponds(MINE that,ZF2<A,B,Boolean>p);


    String mkString();

    String mkString(String sep);

    String mkString(String start, String sep, String end);

    StringBuilder addString(StringBuilder b);

    StringBuilder addString(StringBuilder b, String sep);

    StringBuilder addString(StringBuilder b, String start, String sep, String end);

    /**
     * Builds a new collection by applying a function to all elements of this traversable collection.
     *
     * @param f   the function to apply to each element.
     * @param <B> the element type of the returned collection.
     * @return a new traversable collection resulting from applying the given function f to each element of this traversable collection and collecting the results.
     */
    <B> MINE map(ZF<A, B> f);

    <B, E> MINE flatMap(Class<E> rE, ZF<A, B> f);

    ZArray<A> toArray();

}
