package zz.collection.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ZMapWrapper<K,V,MT extends Map<K,V>> implements Collection<Map.Entry<K,V>> {

    private final MT me;

    public ZMapWrapper(MT me) {
        this.me = me;
    }

    public MT Me() {
        return me;
    }

    @Override
    public boolean add(Map.Entry<K, V> object) {
        if(null == object)
            return true;
        return object.getValue() == me.put(object.getKey(),object.getValue());
    }

    @Override
    public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
        if(null == collection)
            return true;
        for(Map.Entry<K, V> x : collection) {
            add(x);
        }
        return true;
    }

    @Override
    public void clear() {
        me.clear();
    }

    @Override
    public boolean contains(Object object) {
        return me.containsKey(((Map.Entry<K, V>)object).getKey());
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object x : collection) {
            if(!contains(x))
                return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return me.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return me.entrySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        return ((Map.Entry<K, V>)object).getKey() == me.remove(me.remove(((Map.Entry<K, V>)object).getKey()));
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for (Object x : collection) {
            remove(x);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean result = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                result = true;
            }
        }
        return result;
    }

    @Override
    public int size() {
        return me.size();
    }

    @Override
    public Object[] toArray() {
        return me.entrySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return me.entrySet().toArray(array);
    }

    public static class ZHashMapWrapper<K,V> extends ZMapWrapper<K,V,HashMap<K,V>> {
        public ZHashMapWrapper(HashMap<K, V> me) {
            super(me);
        }
    }
}