package zz.collection.immutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.immutable.ZILinkedList;


public class ZILinkedListCreator implements ZCollectionCreator {
    @Override
    public ZAbstractTraversable create() {
        return new ZILinkedList();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return create();
    }

}
