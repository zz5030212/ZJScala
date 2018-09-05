package zz.collection.immutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.immutable.ZIHashMap;


public class ZIHashMapCreator implements ZCollectionCreator {

    @Override
    public ZAbstractTraversable create() {
        return new ZIHashMap();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return new ZIHashMap(initialCapacity);
    }
}


