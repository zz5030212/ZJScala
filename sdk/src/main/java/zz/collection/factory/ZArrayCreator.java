package zz.collection.factory;

import zz.collection.ZAbstractTraversable;
import zz.collection.ZArray;

public class ZArrayCreator implements ZCollectionCreator {
    @Override
    public ZAbstractTraversable create() {
        return new ZArray(0);
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return new ZArray(initialCapacity,true);
    }
}
