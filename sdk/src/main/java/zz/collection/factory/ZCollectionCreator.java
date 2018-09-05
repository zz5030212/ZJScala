package zz.collection.factory;


import zz.collection.ZAbstractTraversable;

import java.io.Serializable;

public interface ZCollectionCreator extends Serializable {
    ZAbstractTraversable create();
    ZAbstractTraversable create(int initialCapacity);
}
