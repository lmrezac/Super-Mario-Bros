// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import javax.sound.sampled.LineUnavailableException;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Control;
import java.util.Collection;
import javax.sound.sampled.DataLine;

public abstract class TBaseDataLine extends TDataLine
{
    public TBaseDataLine(final TMixer mixer, final DataLine.Info info) {
        super(mixer, info);
    }
    
    public TBaseDataLine(final TMixer mixer, final DataLine.Info info, final Collection<Control> controls) {
        super(mixer, info, controls);
    }
    
    public void open(final AudioFormat format, final int nBufferSize) throws LineUnavailableException {
        if (TDebug.TraceDataLine) {
            TDebug.out("TBaseDataLine.open(AudioFormat, int): called with buffer size: " + nBufferSize);
        }
        this.setBufferSize(nBufferSize);
        this.open(format);
    }
    
    public void open(final AudioFormat format) throws LineUnavailableException {
        if (TDebug.TraceDataLine) {
            TDebug.out("TBaseDataLine.open(AudioFormat): called");
        }
        this.setFormat(format);
        this.open();
    }
    
    protected void finalize() {
        if (this.isOpen()) {
            this.close();
        }
    }
}
