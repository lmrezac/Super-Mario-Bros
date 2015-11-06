// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share;

import java.util.Iterator;
import java.util.Collection;

public class StringHashedSet<E> extends ArraySet<E>
{
    private static final long serialVersionUID = -3116589678919789699L;

	public StringHashedSet() {
    }
    
    public StringHashedSet(final Collection<E> c) {
        super(c);
    }
    
    public boolean add(final E elem) {
        return elem != null && super.add(elem);
    }
    
    public boolean contains(final Object elem) {
        if (elem == null) {
            return false;
        }
        final String comp = elem.toString();
        final Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            if (comp.equals(it.next().toString())) {
                return true;
            }
        }
        return false;
    }
    
    public E get(final E elem) {
        if (elem == null) {
            return null;
        }
        final String comp = elem.toString();
        for (final E thisElem : this) {
            if (comp.equals(thisElem.toString())) {
                return thisElem;
            }
        }
        return null;
    }
}
