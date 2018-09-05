package zz.collection.immutable;

import zz.collection.ZAbsMap;
import zz.collection.factory.ZMapWrapper;
import zz.collection.immutable.creator.ZIHashMapCreator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public final class ZIHashMap<K,V> extends ZAbsMap<K,V,HashMap<K,V>,ZMapWrapper.ZHashMapWrapper<K,V>,ZIHashMapCreator,ZIHashMap<K,V>,ZIHashMap> implements Serializable {
    public ZIHashMap(HashMap<K, V> me) {
        super(new ZIHashMapCreator(), new ZMapWrapper.ZHashMapWrapper<K,V>(new HashMap<K, V>(me)));
    }

    public ZIHashMap() {
        this(new HashMap<K, V>());
    }

    public ZIHashMap(int initialCapacity) {
        this(new HashMap<K, V>(initialCapacity));
    }

    public ZIHashMap(int initialCapacity, float loadFactor) {
        this(new HashMap<K, V>(initialCapacity,loadFactor));
    }

    public ZIHashMap(Map<? extends K, ? extends V> m){
        this(new HashMap<K, V>(m));
    }

    @Deprecated
    private ZIHashMap(HashMap<K, V> me, boolean bind) {
        super(new ZIHashMapCreator(), new ZMapWrapper.ZHashMapWrapper<K,V>(me));
    }

    public static <K,V> ZIHashMap<K,V> bind(HashMap<K,V> map) {
        return new ZIHashMap<K,V>(map,true);
    }

}


