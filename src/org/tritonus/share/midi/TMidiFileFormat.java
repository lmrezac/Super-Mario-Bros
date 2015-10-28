// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import javax.sound.midi.MidiFileFormat;

public class TMidiFileFormat extends MidiFileFormat
{
    private int m_nTrackCount;
    
    public TMidiFileFormat(final int nType, final float fDivisionType, final int nResolution, final int nByteLength, final long lMicrosecondLength, final int nTrackCount) {
        super(nType, fDivisionType, nResolution, nByteLength, lMicrosecondLength);
        this.m_nTrackCount = nTrackCount;
    }
    
    public int getTrackCount() {
        return this.m_nTrackCount;
    }
}
