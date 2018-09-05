package zz.collection.mutable;


import zz.ZOption;
import zz.ZSome;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.factory.ZMapWrapper;

import java.util.Map;

public abstract class ZAbsMap2<K,V,MT extends Map<K,V>,P extends ZMapWrapper<K,V,MT>, S extends ZCollectionCreator, ME, MINE extends ZAbsMap2>
        extends zz.collection.ZAbsMap<K,V,MT,P,S,ME,MINE> {

    ZAbsMap2(S s, P j) {
        super(s, j);
    }

    /** Adds a new key/value pair to this map and optionally returns previously bound value.
     *  If the map already contains a
     *  mapping for the key, it will be overridden by the new value.
     *
     * @param key    the key to update
     * @param value  the new value
     * @return an option value containing the value associated with the key
     *         before the `put` operation was executed, or `None` if `key`
     *         was not defined in the map before.
     */
    public ME adQ(K key, V value) {
        return update(key, value);
    }

    @Override
    public ME adQ(Map.Entry<K, V> x) {
        return super.adQ(x);
    }

    @Override
    public ME adsQ(Map.Entry<K, V>... xs) {
        return super.adsQ(xs);
    }

    @Override
    public ME adsQ(Iterable<Map.Entry<K, V>> xs) {
        return super.adsQ(xs);
    }

    @Override
    public ME deQ(Map.Entry<K, V> x) {
        return super.deQ(x);
    }

    /** Adds a new key/value pair to this map.
     *  If the map already contains a
     *  mapping for the key, it will be overridden by the new value.
     *
     *  @param key    The key to update
     *  @param value  The new value
     */
    public ME  update(K key, V value) {
        me.put(key,value);
        return (ME)this;
    }

    /** If given key is already in this map, returns associated value.
     *
     *  Otherwise, computes value from given expression `op`, stores with key
     *  in map and returns that value.
     *
     *  Concurrent map implementations may evaluate the expression `op`
     *  multiple times, or may evaluate `op` without inserting the result.
     *
     *  @param  key the key to test
     *  @param  op  the computation yielding the value to associate with `key`, if
     *              `key` is previously unbound.
     *  @return     the value associated with key (either previously or as a result
     *              of executing the method).
     */
    public V getOrElseUpdate(K key, V op){
        ZOption<V> v = getOption(key);
        if(v instanceof ZSome)
            return v.get();

        update(key,op);
        return op;
    }


}
