// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import java.util.Iterator;
import java.util.Collection;
import javax.sound.sampled.AudioFormat;
import org.tritonus.share.ArraySet;

public class AudioFormatSet extends ArraySet<AudioFormat>
{
    private static final long serialVersionUID = -5719082902387429921L;
	protected static final AudioFormat[] EMPTY_FORMAT_ARRAY;
    
    public AudioFormatSet() {
    }
    
    public AudioFormatSet(final Collection<AudioFormat> c) {
        super(c);
    }
    
    public boolean add(final AudioFormat elem) {
        return elem != null && elem instanceof AudioFormat && super.add(elem);
    }
    
    public boolean contains(final AudioFormat elem) {
        if (elem == null || !(elem instanceof AudioFormat)) {
            return false;
        }
        final Iterator<AudioFormat> it = this.iterator();
        while (it.hasNext()) {
            if (AudioFormats.equals(elem, (AudioFormat)it.next())) {
                return true;
            }
        }
        return false;
    }
    
    public AudioFormat get(final AudioFormat elem) {
        if (elem == null || !(elem instanceof AudioFormat)) {
            return null;
        }
        for (final AudioFormat thisElem : this) {
            if (AudioFormats.equals(elem, thisElem)) {
                return thisElem;
            }
        }
        return null;
    }
    
    public AudioFormat getAudioFormat(final AudioFormat elem) {
        return this.get(elem);
    }
    
    public AudioFormat matches(final AudioFormat elem) {
        if (elem == null) {
            return null;
        }
        for (final AudioFormat thisElem : this) {
            if (AudioFormats.matches(elem, thisElem)) {
                return thisElem;
            }
        }
        return null;
    }
    
    public AudioFormat[] toAudioFormatArray() {
        return this.toArray(AudioFormatSet.EMPTY_FORMAT_ARRAY);
    }
    
    public void add(final int index, final AudioFormat element) {
        throw new UnsupportedOperationException("unsupported");
    }
    
    public AudioFormat set(final int index, final AudioFormat element) {
        throw new UnsupportedOperationException("unsupported");
    }
    
    static {
        EMPTY_FORMAT_ARRAY = new AudioFormat[0];
    }
}
