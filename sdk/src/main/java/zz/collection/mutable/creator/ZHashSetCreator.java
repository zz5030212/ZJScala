package zz.collection.mutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.mutable.ZHashSet;

public class ZHashSetCreator implements ZCollectionCreator {
    @Override
    public ZAbstractTraversable create() {
        return new ZHashSet();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return new ZHashSet(initialCapacity);
    }
}
