package zz.collection.immutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.immutable.ZIHashSet;

public class ZIHashSetCreator implements ZCollectionCreator {
    @Override
    public ZAbstractTraversable create() {
        return new ZIHashSet();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return new ZIHashSet(initialCapacity);
    }
}
