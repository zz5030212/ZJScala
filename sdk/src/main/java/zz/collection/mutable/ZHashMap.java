package zz.collection.mutable;

import zz.ZCase2;
import zz.ZF2;
import zz.ZNone;
import zz.ZOption;
import zz.collection.factory.ZMapWrapper;
import zz.collection.mutable.creator.ZHashMapCreator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public final class ZHashMap<K,V> extends ZAbsMap2<K,V,HashMap<K,V>,ZMapWrapper.ZHashMapWrapper<K,V>,ZHashMapCreator,ZHashMap<K,V>,ZHashMap> implements Serializable {

    public ZHashMap(HashMap<K,V> me) {
        super(new ZHashMapCreator(), new ZMapWrapper.ZHashMapWrapper<K,V>(new HashMap<K, V>(me)));
    }

    public ZHashMap() {
        this(new HashMap<K, V>());
    }

    public ZHashMap(int initialCapacity) {
        this(new HashMap<K, V>(initialCapacity));
    }

    public ZHashMap(int initialCapacity, float loadFactor) {
        this(new HashMap<K, V>(initialCapacity,loadFactor));
    }

    public ZHashMap(Map<? extends K,? extends V> m){
        this(new HashMap<K, V>(m));
    }

    public V getOrElseUpdate(K key,V defaultValue) {
        ZOption<V> v = getOption(key);
        if(v instanceof ZNone) {
            adQ(key,defaultValue);
            return defaultValue;
        }
        return v.getOrElse(defaultValue);
    }

    @Override
    public <K1, V1> ZHashMap<K1,V1> map(ZF2<K, V, ZCase2<K1, V1>> f) {
        return super.map(f);
    }

    @Deprecated
    private ZHashMap(HashMap<K,V> me, boolean bind) {
        super(new ZHashMapCreator(), new ZMapWrapper.ZHashMapWrapper<K,V>(me));
    }

    public static <K,V> ZHashMap<K,V> bind(HashMap<K,V> map) {
        return new ZHashMap<K,V>(map,true);
    }
}


