// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share;

import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;

public class ArraySet<E> extends ArrayList<E> implements Set<E>
{
    public ArraySet() {
    }
    
    public ArraySet(final Collection<E> c) {
        this();
        this.addAll((Collection<? extends E>)c);
    }
    
    public boolean add(final E element) {
        if (!this.contains(element)) {
            super.add(element);
            return true;
        }
        return false;
    }
    
    public void add(final int index, final E element) {
        throw new UnsupportedOperationException("ArraySet.add(int index, Object element) unsupported");
    }
    
    public E set(final int index, final E element) {
        throw new UnsupportedOperationException("ArraySet.set(int index, Object element) unsupported");
    }
}
