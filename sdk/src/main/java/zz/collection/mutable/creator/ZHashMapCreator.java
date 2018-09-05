package zz.collection.mutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.mutable.ZHashMap;


public class ZHashMapCreator implements ZCollectionCreator {

    @Override
    public ZAbstractTraversable create() {
        return new ZHashMap();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return new ZHashMap(initialCapacity);
    }
}


