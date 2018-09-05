package zz.collection;


import zz.*;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.factory.ZMapWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class ZAbsMap<K,V,MT extends Map<K,V>,P extends ZMapWrapper<K,V,MT>, S extends ZCollectionCreator, ME, MINE extends ZAbsMap>
        extends ZAbstractTraversable<Map.Entry<K,V>,P,S,ME,MINE> {

    protected final MT me;

    public ZAbsMap(S s, P j) {
        super(s, j);
        me = j.Me();
    }

    @Override
    public Map.Entry<K, V> get(int n) {
        if(n<0 || n>=j.size())
            throw new IndexOutOfBoundsException(" "+n);
        int i = 0;
        Iterator<Map.Entry<K,V>> iterator = j.iterator();
        Map.Entry<K,V> x = null;
        while (iterator.hasNext()) {
            x = iterator.next();
            if(i == n)
                return x;
            i++;
        }
        throw new IndexOutOfBoundsException(" "+n);
    }

    /** Optionally returns the value associated with a key.
     *
     *  @param  key    the key value
     *  @return an option value containing the value associated with `key` in this map,
     *          or `None` if none exists.
     */
    public ZOption<V> getOption(K key) {
        if(containsKey(key)) {
            return new ZSome<V>(me.get(key));
        }
        return new ZNone<V>();
    }

    public V getOrElse(K key,V defaultValue) {
        return getOption(key).getOrElse(defaultValue);
    }

    /** Tests whether this map contains a binding for a key.
     *
     *  @param key the key
     *  @return    `true` if there is a binding for `key` in this map, `false` otherwise.
     */
    public boolean containsKey(K key) {
        return me.containsKey(key);
    }

    public ZArray<V> values() {
        return new ZArray<V>(new ArrayList<V>(me.values()));
    }

    public ZArray<K> keys() {
        ArrayList<K> r = new ArrayList<K>(me.size());
        for(K key : me.keySet()) {
            r.add(key);
        }
        return new ZArray<K>(r);
    }

    private static void putObject(Map.Entry entry, ZAbsMap m, Object b) {
        if(b instanceof Map.Entry){
            m.adQ(b);
        } else if(b instanceof ZCase2) {
            ZCase2 t = (ZCase2) b;
            m.me.put(t._1,t._2);
        } else {
            m.me.put(entry.getKey(),b);
        }
    }

    private static void putArray(Map.Entry entry, Class<?> cls, ZAbsMap m, Object bs) {
        if(null == bs) {
            return;
        } else if(cls.isAssignableFrom(bs.getClass())) {
            putObject(entry,m,bs);
        } else if(bs.getClass().isArray()) {
            for(Object e : (Object[])bs) {
                putObject(entry,m,e);
            }
        } else if(bs instanceof Iterable) {
            for(Object e : (Iterable)bs) {
                putObject(entry,m,e);
            }
        } else {
            throw new NoSuchElementException("");
        }
    }

    @Override
    public <B> MINE map(ZF<Map.Entry<K, V>, B> f) {
        MINE r = (MINE)s.create(length());
        for(Map.Entry<K, V> e:j) {
            putObject(e,r,f.f(e));
        }
        return r;
    }

    public <K1,V1> MINE map(ZF2<K,V,ZCase2<K1,V1>> f) {
        MINE r = (MINE)s.create(length());
        for(Map.Entry<K, V> e:j) {
            putObject(e,r,f.f(e.getKey(),e.getValue()));
        }
        return r;
    }

    @Override
    public <B, E> MINE flatMap(Class<E> rE, ZF<Map.Entry<K, V>, B> f) {
        MINE r = (MINE)s.create();
        for(Map.Entry<K, V> e : j) {
            putArray(e,rE,r,f.f(e));
        }
        return r;
    }

    public <B, E> MINE flatMap(Class<E> rE, ZF2<K,V, B> f) {
        MINE r = (MINE)s.create();
        for(Map.Entry<K, V> e : j) {
            putArray(e,rE,r,f.f(e.getKey(),e.getValue()));
        }
        return r;
    }
}
