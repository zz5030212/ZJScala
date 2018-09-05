package zz;


import java.io.Serializable;

public abstract class ZOption<A> extends ZAny implements Serializable {
    /** Returns true if the option is $none, false otherwise.
     */
    public abstract boolean isEmpty();

    /** Returns true if the option is an instance of $some, false otherwise.
     */
    public boolean  isDefined() {
        return !isEmpty();
    }

    /** Returns the option's value.
     *  @note The option must be nonEmpty.
     *  @throws java.util.NoSuchElementException if the option is empty.
     */
    public abstract A get();

    /** Returns the option's value if the option is nonempty, otherwise
     * return the result of evaluating `default`.
     *
     *  @param defaultV  the default expression.
     */
    public final A getOrElse(A defaultV){
        if (isEmpty())
            return defaultV;
        else
            return this.get();
    }


    /** Returns the option's value if it is nonempty,
     * or `null` if it is empty.
     * Although the use of null is discouraged, code written to use
     * $option must often interface with code that expects and returns nulls.
     * @example {{{
     * val initialText: Option[String] = getInitialText
     * val textField = new JComponent(initialText.orNull,20)
     * }}}
     */
    public final A orNull() {
        return getOrElse(null);
    }

    /** Returns a $some containing the result of applying $f to this $option's
     * value if this $option is nonempty.
     * Otherwise return $none.
     *
     *  @note This is similar to `flatMap` except here,
     *  $f does not need to wrap its result in an $option.
     *
     *  @param  f   the function to apply
     */
    public final <B> ZOption<B> map(ZF<A,B> f) {
        if (isEmpty())
            return new ZNone<B>();
        else
            return new ZSome(f.f(this.get()));
    }


    /** Returns the result of applying $f to this $option's
     *  value if the $option is nonempty.  Otherwise, evaluates
     *  expression `ifEmpty`.
     *
     *  @note This is equivalent to `$option map f getOrElse ifEmpty`.
     *
     *  @param  ifEmpty the expression to evaluate if empty.
     *  @param  f       the function to apply if nonempty.
     */
    public final <B> B fold(B ifEmpty,ZF<A,B> f) {
        if (isEmpty())
            return ifEmpty;
        else
            return f.f(this.get());
    }


    /** Returns the result of applying $f to this $option's value if
     * this $option is nonempty.
     * Returns $none if this $option is empty.
     * Slightly different from `map` in that $f is expected to
     * return an $option (which could be $none).
     *
     *  @param  f   the function to apply
     */
    public final <B> ZOption<B> flatMap(ZF<A,ZOption<B>> f){
        if (isEmpty())
            return new ZNone<B>();
        else
            return f.f(this.get());


    }

    /** Returns this $option if it is nonempty '''and''' applying the predicate $p to
     * this $option's value returns true. Otherwise, return $none.
     *
     *  @param  p   the predicate used for testing.
     */
    public final ZOption<A> filter(ZF<A,Boolean> p) {
        if (isEmpty() || p.f(this.get()))
            return this;
        else
            return new ZNone<A>();
    }


    /** Returns this $option if it is nonempty '''and''' applying the predicate $p to
     * this $option's value returns false. Otherwise, return $none.
     *
     *  @param  p   the predicate used for testing.
     */
    public final ZOption<A> filterNot(ZF<A,Boolean> p) {
        if (isEmpty() || !p.f(this.get()))
            return this;
        else
            return new ZNone<A>();
    }

    /** Returns false if the option is $none, true otherwise.
     *  @note   Implemented here to avoid the implicit conversion to Iterable.
     */
    public final boolean nonEmpty() {
        return isDefined();
    }


    /** Tests whether the option contains a given value as an element.
     *
     *  @example {{{
     *  // Returns true because Some instance contains string "something" which equals "something".
     *  Some("something") contains "something"
     *
     *  // Returns false because "something" != "anything".
     *  Some("something") contains "anything"
     *
     *  // Returns false when method called on None.
     *  None contains "anything"
     *  }}}
     *
     *  @param elem the element to test.
     *  @return `true` if the option has an element that is equal (as
     *  determined by `==`) to `elem`, `false` otherwise.
     */
    public final <A1 extends A> boolean contains(A1 elem) {
        return !isEmpty() && this.get().equals(elem);
    }


    /** Returns true if this option is nonempty '''and''' the predicate
     * $p returns true when applied to this $option's value.
     * Otherwise, returns false.
     *
     *  @param  p   the predicate to test
     */
    public final <A1 extends A> boolean exists(ZF<A,Boolean> p){
        return !isEmpty() && p.f(this.get());
    }


    /** Returns true if this option is empty '''or''' the predicate
     * $p returns true when applied to this $option's value.
     *
     *  @param  p   the predicate to test
     */
    public final boolean forall(ZF<A,Boolean> p){
        return isEmpty() || p.f(this.get());
    }

    /** Apply the given procedure $f to the option's value,
     *  if it is nonempty. Otherwise, do nothing.
     *
     *  @param  f   the procedure to apply.
     */
    public final void foreach(ZF<A,Void> f) {
        if (!isEmpty())
            f.f(this.get());
    }


}
