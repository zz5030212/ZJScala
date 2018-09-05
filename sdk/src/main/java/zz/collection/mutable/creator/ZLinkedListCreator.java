package zz.collection.mutable.creator;

import zz.collection.ZAbstractTraversable;
import zz.collection.factory.ZCollectionCreator;
import zz.collection.mutable.ZLinkedList;

public class ZLinkedListCreator implements ZCollectionCreator {

    @Override
    public ZAbstractTraversable create() {
        return new ZLinkedList();
    }

    @Override
    public ZAbstractTraversable create(int initialCapacity) {
        return create();
    }
}
